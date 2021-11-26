package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.AdministradorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.DoenteDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.AdministradorBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Administrador;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

    @EJB
    private UserBean userBean;

    @GET
    @Path("/")
    public Response getAll() {
        return Response.ok(toDTOs(userBean.getAll())).build();
    }

    @GET
    @Path("/{email}")
    public Response get(@PathParam("email") String email) {
        User user = userBean.findOrFail(email);

        if(user instanceof Administrador) {
            Administrador administrador = (Administrador) user;

            return Response.ok(new AdministradorDTO(administrador.getName(), administrador.getEmail())).build();
        } else if(user instanceof Doente) {
            Doente doente = (Doente) user;

            return Response.ok(new DoenteDTO(doente.getName(), doente.getEmail(), doente.getIdade(), doente.getPeso(), doente.getAltura())).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    private List<UserDTO> toDTOs(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getClass().getSimpleName()
        );
    }
}
