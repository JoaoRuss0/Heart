package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DoenteDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DoenteBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("doentes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoenteService {

    @EJB
    DoenteBean doenteBean;

    @GET
    @Path("/")
    public Response getAll()
    {
        return Response.ok(toDTOs(doenteBean.getAll())).build();
    }

    private List<DoenteDTO> toDTOs(List<Doente> doentes) {
        return doentes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private DoenteDTO toDTO(Doente doente) {
        return new DoenteDTO(
                doente.getName(),
                doente.getEmail(),
                doente.getIdade(),
                doente.getPeso(),
                doente.getAltura()
        );
    }
}