package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.AdministradorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.AdministradorBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Administrador;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("administradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministradorService {

    @EJB
    private AdministradorBean administradorBean;

    @EJB
    private UserBean userBean;

    @GET
    @Path("/")
    public Response getAll()
    {
        return Response.ok(toDTOsAdministradores(administradorBean.getAll())).build();
    }

    @POST
    @Path("/")
    public Response create(UserDTO userDTO) throws Exception {
        User user = userBean.find(userDTO.getEmail());

        if(user != null) {
            throw new Exception("Found user with email='" + userDTO.getEmail() + "'");
        }

        String tipo = userDTO.getTipo();
        if(tipo.equals("Administrador")) {
            user = administradorBean.create(
                    userDTO.getName(),
                    userDTO.getEmail(),
                    userDTO.getPassword()
            );
        } else if(tipo.equals("ProfissionalDeSaude")) {

        } else {
            throw new Exception("Type of user does not match any creatable user types.");
        }

        return Response.ok(toDTOUser(user)).build();
    }

    private List<AdministradorDTO> toDTOsAdministradores(List<Administrador> administradores) {
        return administradores.stream().map(this::toDTOAdministrador).collect(Collectors.toList());
    }

    private AdministradorDTO toDTOAdministrador(Administrador administrador) {
        return new AdministradorDTO(
                administrador.getName(),
                administrador.getEmail()
        );
    }

    private UserDTO toDTOUser(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getClass().getSimpleName()
        );
    }
}
