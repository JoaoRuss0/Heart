package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DoenteDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PrescricaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DoenteBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyDeleteYourselfException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
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
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoenteService {

    @EJB
    DoenteBean doenteBean;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getAll()
    {
        return Response.ok(toDTOsDoente(doenteBean.getAll())).build();
    }

    @GET
    @Path("/{email}")
    public Response get(@PathParam("email") String email) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        // Only an Administrator, an Health Professional or the respective Ill Person can see himself
        if(!(securityContext.isUserInRole("Administrador") ||
            securityContext.isUserInRole("ProfissionalDeSaude") ||
            principal.getName().equals(email)))
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Doente doente = doenteBean.find(email);

        if(doente == null) {
            throw new MyEntityNotFoundException("Doente with email = '" + email + "' not found.");
        }

        return Response.ok(toDTODoente(doente)).build();
    }

    @POST
    @Path("/")
    public Response create(DoenteDTO doenteDTO) throws MyEntityExistsException, MyConstraintViolationException {
        doenteBean.create(
                doenteDTO.getName(),
                doenteDTO.getEmail(),
                doenteDTO.getPassword(),
                doenteDTO.getIdade(),
                doenteDTO.getPeso(),
                doenteDTO.getAltura());

        return Response.ok(doenteDTO.getEmail()).build();
    }

    @PUT
    @Path("/{email}")
    public Response update(@PathParam("email") String email, DoenteDTO doenteDTO) throws MyConstraintViolationException, MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        // Only an Health Professional or the actual Ill Person can update themselves
        if(!(securityContext.isUserInRole("ProfissionalDeSaude") || principal.getName().equals(email)))
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        doenteBean.update(
                doenteDTO.getName(),
                email,
                doenteDTO.getIdade(),
                doenteDTO.getPeso(),
                doenteDTO.getAltura());

        return Response.ok(email).build();
    }

    @DELETE
    @Path("/{email}")
    public Response delete(@PathParam("email") String email) throws MyDeleteYourselfException, MyEntityNotFoundException {
        doenteBean.delete(email);
        return Response.ok(email).build();
    }

    private List<DoenteDTO> toDTOsDoente(List<Doente> doentes) {
        return doentes.stream().map(this::toDTODoente).collect(Collectors.toList());
    }

    private DoenteDTO toDTODoente(Doente doente) {
        return new DoenteDTO(
                doente.getName(),
                doente.getEmail(),
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
                prescricao.getTipoPrescricao(),
                prescricao.getProfissionalDeSaude()
        );
    }
}
