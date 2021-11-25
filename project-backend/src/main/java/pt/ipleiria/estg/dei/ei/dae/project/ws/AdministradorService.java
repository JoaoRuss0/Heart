package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.AdministradorDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.AdministradorBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Administrador;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("administradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministradorService {

    @EJB
    AdministradorBean administradorBean;

    @GET
    @Path("/")
    public Response getAll()
    {
        return Response.ok(toDTOs(administradorBean.getAll())).build();
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
