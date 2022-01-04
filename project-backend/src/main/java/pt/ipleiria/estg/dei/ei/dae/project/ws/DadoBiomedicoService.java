package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DadoBiomedicoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DadoBiomedicoBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.DadoBiomedico;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("dadosbiomedicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DadoBiomedicoService {

    @EJB
    DadoBiomedicoBean dadoBiomedicoBean;

    @GET
    @Path("/")
    public Response getAll() {
        return Response.ok(toDTOs(dadoBiomedicoBean.getAll())).build();
    }

    @GET
    @Path("/{nome}")
    public Response get(@PathParam("nome") String nome){
        DadoBiomedico dado = dadoBiomedicoBean.find(nome);

        if(dado == null) {
            throw new NotFoundException("Dado Biomedico with name " + nome + " does not exist!");
        }
        return Response.ok(toDTO(dado)).build();
    }

    @POST
    @Path("/")
    public Response create(DadoBiomedicoDTO dadoBiomedicoDTO) throws Exception {
        DadoBiomedico dadoBiomedico = dadoBiomedicoBean.create(
                dadoBiomedicoDTO.getNome(),
                dadoBiomedicoDTO.getDescricao(),
                dadoBiomedicoDTO.getMaximo(),
                dadoBiomedicoDTO.getMinimo(),
                dadoBiomedicoDTO.getUnidadeMedida(),
                dadoBiomedicoDTO.getQualificadores()
        );

        return Response.ok(dadoBiomedico.getNome()).build();
    }

    @PUT
    @Path("/{nome}")
    public Response update(@PathParam("nome") String nome, DadoBiomedicoDTO dadoBiomedicoDTO) throws MyConstraintViolationException, MyEntityNotFoundException {
        dadoBiomedicoBean.update(
                nome,
                dadoBiomedicoDTO.getDescricao(),
                dadoBiomedicoDTO.getMaximo(),
                dadoBiomedicoDTO.getMinimo(),
                dadoBiomedicoDTO.getUnidadeMedida(),
                dadoBiomedicoDTO.getQualificadores()
        );

        return Response.ok(nome).build();
    }

    @DELETE
    @Path("/{nome}")
    public Response delete(@PathParam("nome") String nome) throws MyEntityNotFoundException {
        dadoBiomedicoBean.delete(nome);

        if (dadoBiomedicoBean.find(nome) != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    private List<DadoBiomedicoDTO> toDTOs(List<DadoBiomedico> dadoBiomedicos) {
        return dadoBiomedicos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private DadoBiomedicoDTO toDTO(DadoBiomedico dadoBiomedico) {
        return new DadoBiomedicoDTO(
                dadoBiomedico.getNome(),
                dadoBiomedico.getDescricao(),
                dadoBiomedico.getMaximo(),
                dadoBiomedico.getMinimo(),
                dadoBiomedico.getUnidadeMedida(),
                dadoBiomedico.getQualificadores()
        );
    }
}
