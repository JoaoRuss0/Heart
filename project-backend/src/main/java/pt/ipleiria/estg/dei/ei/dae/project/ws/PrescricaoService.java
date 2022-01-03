package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DoenteDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PrescricaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DoenteBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PrescricaoBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ProfissionalDeSaudeBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("prescricoes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescricaoService {

    private static final Logger logger = Logger.getLogger("Prescricao.logger");

    @EJB

    PrescricaoBean prescricaoBean;

    @Context
    SecurityContext securityContext;

    @EJB
    DoenteBean doenteBean;

    @EJB
    ProfissionalDeSaudeBean profissionalDeSaudeBean;

    @GET
    @Path("/")
    public Response getAll() throws MyEntityNotFoundException {

        Principal principal = securityContext.getUserPrincipal();


        if((securityContext.isUserInRole("Doente"))){
           return Response.ok(toDTOs(doenteBean.findOrFail(principal.getName()).getPrescricoes())).build();
        }
        if((securityContext.isUserInRole("ProfissionalDeSaude"))){

            return Response.ok(toDTOs(profissionalDeSaudeBean.findOrFail(principal.getName()).getPrescricoes())).build();
        }
        return Response.ok(toDTOs(prescricaoBean.getAll())).build();
    }

    private List<PrescricaoDTO> toDTOs(List<Prescricao> prescricoes) {
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
                prescricao.getProfissionalDeSaude(),
                prescricao.getObservacao()
        );



        }

    @POST
    @Path("/")
    public Response createNewPrescricao(PrescricaoDTO prescricaoDTO) throws Exception {


        System.out.println(prescricaoDTO.getObservacaoID());

    Prescricao prescricao = prescricaoBean.create(
            prescricaoDTO.getCausa(),
            prescricaoDTO.getDoenteEmail(),
            prescricaoDTO.getDataInicio(),
            prescricaoDTO.getDataFinal(),
            prescricaoDTO.getTipoPrescricao(),
            securityContext.getUserPrincipal().getName(),
            prescricaoDTO.getObservacaoID()
    );
    return Response.status(Response.Status.CREATED).entity(toDTOPrescricao(prescricao)).build();


    }

    @PUT
    @Path("/{id}")
    public Response updatePrescricao(@PathParam("id") int id, PrescricaoDTO prescricaoDTO) throws Exception {
        Prescricao prescricao = prescricaoBean.find(id);

        if(prescricao == null) {
            throw new NotFoundException("Course with id " + id + " does not exist!");
        }

        prescricao = prescricaoBean.updatePrescricao(prescricao, prescricaoDTO.getCausa(), prescricaoDTO.getDataInicio(), prescricaoDTO.getDataFinal(), prescricaoDTO.getTipoPrescricao());
        return Response.ok(toDTOPrescricao(prescricao)).build();
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) throws Exception {
        prescricaoBean.deletePrescricao(id);

        if (prescricaoBean.find(id) != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/{id}")
    public Response getDadoBiomedico(@PathParam("id") int id){
        Prescricao prescricao = prescricaoBean.find(id);

        if(prescricao == null) {
            throw new NotFoundException("Dado Biomedico with name " + id + " does not exist!");
        }

        return Response.ok(toDTOPrescricao(prescricao)).build();
    }



  }

