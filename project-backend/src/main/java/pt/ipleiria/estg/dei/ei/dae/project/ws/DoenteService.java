package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DoenteDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PrescricaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DoenteBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Administrador;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("doentes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class DoenteService {

    @EJB
    DoenteBean doenteBean;

    @GET
    @Path("/")
    public Response getAll()
    {
        return Response.ok(toDTOsDoente(doenteBean.getAll())).build();
    }

    private List<DoenteDTO> toDTOsDoente(List<Doente> doentes) {
        return doentes.stream().map(this::toDTODoente).collect(Collectors.toList());
    }

    private DoenteDTO toDTODoente(Doente doente) {
        return new DoenteDTO(
                doente.getName(),
                doente.getEmail(),
                doente.getPassword(),
                doente.getIdade(),
                doente.getPeso(),
                doente.getAltura(),
                toDTOsPrescricao(doente.getPrescricoes())
        );
    }

    private List<PrescricaoDTO> toDTOsPrescricao(ArrayList<Prescricao> prescricoes) {
        return prescricoes.stream().map(this::toDTOPrescricao).collect(Collectors.toList());
    }

    private PrescricaoDTO toDTOPrescricao(Prescricao prescricao) {
        return new PrescricaoDTO(
                prescricao.getId(),
                prescricao.getCausa(),
                prescricao.getDoente().getEmail(),
                prescricao.getDataInicio(),
                prescricao.getDataFinal(),
                prescricao.getTipoPrescricao()
        );
    }


    @Context
    private SecurityContext securityContext;
    private EntityManager em;

    @GET
    @Path("/{email}")
    public Response getDoente(@PathParam("email") String email) throws Exception{


        if(!(securityContext.isUserInRole("Administrador"))){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Doente doente = doenteBean.find(email);

        if(doente == null) {
            throw new NotFoundException("Dado Biomedico with name " + email + " does not exist!");
        }
        return Response.ok(toDTODoente(doente)).build();
    }


}
