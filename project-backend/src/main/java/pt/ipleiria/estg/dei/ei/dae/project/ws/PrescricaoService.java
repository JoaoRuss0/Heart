package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.PrescricaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DoenteBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PrescricaoBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ProfissionalDeSaudeBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;
import pt.ipleiria.estg.dei.ei.dae.project.entities.ProfissionalDeSaude;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyParseException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Path("prescricoes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescricaoService {

    @EJB
    PrescricaoBean prescricaoBean;

    @EJB
    DoenteBean doenteBean;

    @EJB
    UserBean userBean;

    @EJB
    ProfissionalDeSaudeBean profissionalDeSaudeBean;

    @Context
    SecurityContext securityContext;

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
        if((securityContext.isUserInRole("Administrador"))){
            return Response.ok(toDTOs(prescricaoBean.getAll())).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        Prescricao prescricao = prescricaoBean.findOrFail(id);

        if(principal == null)
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(toDTO(prescricao)).build();
    }

    @POST
    @Path("/")
    public Response create(PrescricaoDTO prescricaoDTO) throws MyConstraintViolationException, MyParseException, MyEntityNotFoundException {
        Prescricao prescricao = prescricaoBean.create(
                prescricaoDTO.getComentario(),
                prescricaoDTO.getDoenteEmail(),
                prescricaoDTO.getDataInicio(),
                prescricaoDTO.getDataFinal(),
                prescricaoDTO.getTipoPrescricao(),
                securityContext.getUserPrincipal().getName(),
                prescricaoDTO.getObservacaoID());

        return Response.status(Response.Status.CREATED).entity(toDTO(prescricao)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, PrescricaoDTO prescricaoDTO) throws MyEntityNotFoundException, MyParseException, MyConstraintViolationException {
        prescricaoBean.update(id,
                prescricaoDTO.getComentario(),
                prescricaoDTO.getDataInicio(),
                prescricaoDTO.getDataFinal(),
                prescricaoDTO.getTipoPrescricao());

        return Response.ok(id).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) throws MyEntityNotFoundException, MyEntityExistsException {
        prescricaoBean.delete(id);

        if (prescricaoBean.find(id) != null) {
            throw new MyEntityExistsException("Error deleting prescrição, prescrição still exists.");
        }

        return Response.ok().build();
    }

    private List<PrescricaoDTO> toDTOs(List<Prescricao> prescricoes) {
        return prescricoes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private PrescricaoDTO toDTO(Prescricao prescricao) {
        return new PrescricaoDTO(
                prescricao.getId(),
                prescricao.getComentario(),
                prescricao.getDoente().getEmail(),
                prescricao.getDataInicio(),
                prescricao.getDataFinal(),
                prescricao.getTipoPrescricao(),
                prescricao.getProfissionalDeSaude(),
                prescricao.getObservacao()
        );
    }
}

