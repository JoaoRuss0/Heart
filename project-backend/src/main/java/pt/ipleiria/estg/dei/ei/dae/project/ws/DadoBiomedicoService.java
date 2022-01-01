package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DadoBiomedicoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DadoBiomedicoBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.DadoBiomedico;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("dadosbiomedicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DadoBiomedicoService {

    @EJB
    DadoBiomedicoBean dadoBiomedicoBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getAll() {

        if(!(securityContext.isUserInRole("Administrador")||
                securityContext.isUserInRole("ProfissionalDeSaude"))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(toDTOs(dadoBiomedicoBean.getAll())).build();

    }

    @GET
    @Path("/{nome}")
    public Response getDadoBiomedico(@PathParam("nome") String nome){

        if(!(securityContext.isUserInRole("Administrador")||
                securityContext.isUserInRole("ProfissionalDeSaude"))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }


        DadoBiomedico dado = dadoBiomedicoBean.find(nome);

        if(dado == null) {
            throw new NotFoundException("Dado Biomedico with name " + nome + " does not exist!");
        }
        return Response.ok(toDTO(dado)).build();
    }

    @POST
    @Path("/")
    public Response create(DadoBiomedicoDTO dadoBiomedicoDTO) throws Exception {

        if(!(securityContext.isUserInRole("Administrador")||
                securityContext.isUserInRole("ProfissionalDeSaude"))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }


        DadoBiomedico dadoBiomedico = dadoBiomedicoBean.find(dadoBiomedicoDTO.getNome());

        if(dadoBiomedico != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        dadoBiomedico = dadoBiomedicoBean.create(
                dadoBiomedicoDTO.getNome(),
                dadoBiomedicoDTO.getDescricao(),
                dadoBiomedicoDTO.getMaximo(),
                dadoBiomedicoDTO.getMinimo(),
                dadoBiomedicoDTO.getUnidadeMedida(),
                dadoBiomedicoDTO.getQualificadores()
        );

        return Response.ok(toDTO(dadoBiomedico)).build();
    }

    @DELETE
    @Path("/{nome}")
    public Response deleteDadoBiomedico(@PathParam("nome") String nome){

        if(!(securityContext.isUserInRole("Administrador")||
                securityContext.isUserInRole("ProfissionalDeSaude"))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        dadoBiomedicoBean.deleteDado(nome);

        if (dadoBiomedicoBean.find(nome) != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @PUT
    @Path("/{nome}/update")
    public Response updateDado(@PathParam("nome") String nome, DadoBiomedicoDTO dadoBiomedicoDTO){

        if(!(securityContext.isUserInRole("Administrador")||
                securityContext.isUserInRole("ProfissionalDeSaude"))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        DadoBiomedico dado = dadoBiomedicoBean.find(nome);

        if(dado == null) {
            throw new NotFoundException("Dado Biomedico with name " + nome + " does not exist!");
        }

        dado = dadoBiomedicoBean.update(dado, dadoBiomedicoDTO);
        return Response.ok(toDTO(dado)).build();
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
