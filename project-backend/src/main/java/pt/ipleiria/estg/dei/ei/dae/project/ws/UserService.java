package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
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
