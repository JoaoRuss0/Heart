package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.AdministradorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.AdministradorBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Administrador;
import pt.ipleiria.estg.dei.ei.dae.project.entities.ProfissionalDeSaude;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.*;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Path("administradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministradorService {

    @EJB
    private AdministradorBean administradorBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/{email}")
    public Response get(@PathParam("email") String email) throws MyEntityNotFoundException {
        Administrador administrador = administradorBean.find(email);

        if(administrador == null) {
            throw new MyEntityNotFoundException("Administrator with email = '" + email + "' not found.");
        }

        return Response.ok(toDTO(administrador)).build();
    }

    @POST
    @Path("/")
    public Response create(AdministradorDTO administradorDTO) throws MyConstraintViolationException, MyEntityExistsException, MyPasswordTooShortException {
        Administrador administrador = administradorBean.create(
                administradorDTO.getName(),
                administradorDTO.getEmail(),
                administradorDTO.getPassword()
        );

        return Response.ok(administrador.getEmail()).build();
    }

    @PUT
    @Path("/{email}")
    public Response update(@PathParam("email") String email, AdministradorDTO administradorDTO) throws MyConstraintViolationException, MyEntityNotFoundException, MyIncorrectPasswordException, MyPasswordTooShortException {
        Administrador administrador = administradorBean.update(administradorDTO.getName(), email, administradorDTO.getCurrentpassword(), administradorDTO.getNewpassword());
        return Response.ok(email).build();
    }

    @DELETE
    @Path("/{email}")
    public Response delete(@PathParam("email") String email) throws MyDeleteYourselfException, MyEntityNotFoundException{
        Principal principal = securityContext.getUserPrincipal();

        if(principal.getName().equals(email))
        {
            throw new MyDeleteYourselfException("Can not delete yourself.");
        }

        administradorBean.delete(email);
        return Response.ok(email).build();
    }

    private List<AdministradorDTO> toDTOs(List<Administrador> administradores) {
        return administradores.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private AdministradorDTO toDTO(Administrador administrador) {
        return new AdministradorDTO(
                administrador.getName(),
                administrador.getEmail()
        );
    }
}
