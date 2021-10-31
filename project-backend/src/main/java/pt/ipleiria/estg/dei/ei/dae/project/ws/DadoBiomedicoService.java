package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DadoBiomedicoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DadoBiomedicoBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.DadoBiomedico;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("dadosbiomedicos")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class DadoBiomedicoService {

    @EJB
    DadoBiomedicoBean dadoBiomedicoBean;

    @GET
    @Path("/")
    public Response getAll() {
        return Response.ok(toDTOs(dadoBiomedicoBean.getAll())).build();
    }

    @POST
    @Path("/")
    public Response create(DadoBiomedicoDTO dadoBiomedicoDTO) throws Exception {
        DadoBiomedico dadoBiomedico = dadoBiomedicoBean.find(dadoBiomedicoDTO.getName());

        if(dadoBiomedico != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        dadoBiomedico = dadoBiomedicoBean.create(
                dadoBiomedicoDTO.getName(),
                dadoBiomedicoDTO.getMaximum(),
                dadoBiomedicoDTO.getMinimum(),
                dadoBiomedicoDTO.getMeasuringUnit()
        );

        return Response.ok(toDTO(dadoBiomedico)).build();
    }

    private List<DadoBiomedicoDTO> toDTOs(List<DadoBiomedico> dadoBiomedicos) {
        return dadoBiomedicos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private DadoBiomedicoDTO toDTO(DadoBiomedico dadoBiomedico) {
        return new DadoBiomedicoDTO(
                dadoBiomedico.getName(),
                dadoBiomedico.getMaximum(),
                dadoBiomedico.getMinimum(),
                dadoBiomedico.getMeasuringUnit()
        );
    }
}
