package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ProfissionalDeSaudeDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ProfissionalDeSaudeBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.ProfissionalDeSaude;
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
import java.util.List;
import java.util.stream.Collectors;

@Path("profissionaisdesaude")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfissionalDeSaudeService {

    @EJB
    private ProfissionalDeSaudeBean profissionalDeSaudeBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/{email}")
    public Response get(@PathParam("email") String email) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        // Only an Administrator or the respective Health Professional can see himself
        if(!(securityContext.isUserInRole("Administrador") || principal.getName().equals(email)))
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        ProfissionalDeSaude profissionalDeSaude = profissionalDeSaudeBean.find(email);

        if(profissionalDeSaude == null) {
            throw new MyEntityNotFoundException("Health Professional with email = '" + email + "' not found.");
        }

        return Response.ok(toDTO(profissionalDeSaude)).build();
    }

    @POST
    @Path("/")
    public Response create(ProfissionalDeSaudeDTO profissionalDeSaudeDTO) throws MyConstraintViolationException, MyEntityExistsException {
        ProfissionalDeSaude profissionalDeSaude = profissionalDeSaudeBean.create(
                profissionalDeSaudeDTO.getName(),
                profissionalDeSaudeDTO.getEmail(),
                profissionalDeSaudeDTO.getPassword()
        );

        return Response.ok(profissionalDeSaude.getEmail()).build();
    }

    @PUT
    @Path("/{email}")
    public Response update(@PathParam("email") String email, ProfissionalDeSaudeDTO profissionalDeSaudeDTO) throws MyConstraintViolationException, MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        // Only an Administrator or the respective Health Professional can update himself
        if(!(securityContext.isUserInRole("Administrador") || principal.getName().equals(email)))
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        ProfissionalDeSaude profissionalDeSaude = profissionalDeSaudeBean.update(profissionalDeSaudeDTO.getName(), email);
        return Response.ok(email).build();
    }

    @DELETE
    @Path("/{email}")
    public Response delete(@PathParam("email") String email) throws MyDeleteYourselfException, MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        if(principal.getName().equals(email))
        {
            throw new MyDeleteYourselfException("Can not delete yourself.");
        }

        profissionalDeSaudeBean.delete(email);
        return Response.ok(email).build();
    }

    private List<ProfissionalDeSaudeDTO> toDTOs(List<ProfissionalDeSaude> profissionaisDeSaude) {
        return profissionaisDeSaude.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ProfissionalDeSaudeDTO toDTO(ProfissionalDeSaude profissionalDeSaude) {
        return new ProfissionalDeSaudeDTO(
                profissionalDeSaude.getName(),
                profissionalDeSaude.getEmail()
        );
    }
}
