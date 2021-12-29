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

    @GET
    @Path("/{name}")
    public Response getDadoBiomedico(@PathParam("name") String name){
        DadoBiomedico dado = dadoBiomedicoBean.find(name);

        if(dado == null) {
            throw new NotFoundException("Dado Biomedico with name " + name + " does not exist!");
        }

        return Response.ok(toDTO(dado)).build();
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
                dadoBiomedicoDTO.getDescricao(),
                dadoBiomedicoDTO.getMaximum(),
                dadoBiomedicoDTO.getMinimum(),
                dadoBiomedicoDTO.getMeasuringUnit(),
                dadoBiomedicoDTO.getQualificadores()
        );

        return Response.ok(toDTO(dadoBiomedico)).build();
    }

    @DELETE
    @Path("/{name}")
    public Response deleteDadoBiomedico(@PathParam("name") String name){
        dadoBiomedicoBean.deleteDado(name);

        if (dadoBiomedicoBean.find(name) != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @PUT
    @Path("/{name}/update")
    public Response updateCourse(@PathParam("name") String name, DadoBiomedicoDTO dadoBiomedicoDTO){
        DadoBiomedico dado = dadoBiomedicoBean.find(name);

        if(dado == null) {
            throw new NotFoundException("Dado Biomedico with name " + name + " does not exist!");
        }

        dado = dadoBiomedicoBean.update(dado, dadoBiomedicoDTO);
        return Response.ok(toDTO(dado)).build();
    }


    private List<DadoBiomedicoDTO> toDTOs(List<DadoBiomedico> dadoBiomedicos) {
        return dadoBiomedicos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private DadoBiomedicoDTO toDTO(DadoBiomedico dadoBiomedico) {
        return new DadoBiomedicoDTO(
                dadoBiomedico.getName(),
                dadoBiomedico.getDescricao(),
                dadoBiomedico.getMaximum(),
                dadoBiomedico.getMinimum(),
                dadoBiomedico.getMeasuringUnit(),
                dadoBiomedico.getQualificadores()
        );
    }
}
