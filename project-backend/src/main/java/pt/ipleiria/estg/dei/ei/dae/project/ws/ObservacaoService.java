package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.ObservacaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DoenteBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ObservacaoBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ProfissionalDeSaudeBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Observacao;
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


@Path("observacoes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ObservacaoService {

    @EJB
    ObservacaoBean observacaoBean;

    @Context
    SecurityContext securityContext;

    @EJB
    DoenteBean doenteBean;

    @EJB
    ProfissionalDeSaudeBean profissionalDeSaudeBean;

    @GET
    @Path("/")
    public Response getAll() throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        if((securityContext.isUserInRole("Doente"))){
            return Response.ok(toDTOs(doenteBean.findOrFail(principal.getName()).getObservacoes())).build();
        }
        if((securityContext.isUserInRole("ProfissionalDeSaude"))){

            return Response.ok(toDTOs(profissionalDeSaudeBean.findOrFail(principal.getName()).getObservacoes())).build();
        }
        if((securityContext.isUserInRole("Administrador"))){

            return Response.ok(toDTOs(observacaoBean.getAll())).build();
        }

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) throws MyEntityNotFoundException {
        Observacao observacao = observacaoBean.findOrFail(id);
        return Response.ok(toDTO(observacao)).build();
    }

    @POST
    @Path("/")
    public Response create(ObservacaoDTO observacaoDTO) throws MyEntityNotFoundException, MyValueNotFoundException, MyValueOutOfBoundsException, MyConstraintViolationException, MyParseException {
        Observacao observacao = observacaoBean.create(
                observacaoDTO.getDoenteEmail(),
                securityContext.getUserPrincipal().getName(),
                observacaoDTO.getNomeDadoBiomedico(),
                observacaoDTO.getData(),
                observacaoDTO.getValorQuantitativo(),
                observacaoDTO.getValorQualitativo());

        return Response.status(Response.Status.CREATED).entity(toDTO(observacao)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, ObservacaoDTO observacaoDTO) throws Exception {
        observacaoBean.updateObservacao(id, observacaoDTO);
        return Response.ok(id).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) throws MyEntityExistsException, MyEntityNotFoundException {
        observacaoBean.delete(id);

        if (observacaoBean.find(id) != null) {
            throw new MyEntityExistsException("Error deleting observação, observação still exists.");
        }

        return Response.status(Response.Status.ACCEPTED).build();
    }

    private List<ObservacaoDTO> toDTOs(List<Observacao> prescricoes) {
        return prescricoes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ObservacaoDTO toDTO(Observacao observacao) {
        return new ObservacaoDTO(
                observacao.getId(),
                observacao.getDoente().getEmail(),
                observacao.getProfissionalDeSaude().getEmail(),
                observacao.getNomeDadoBiomedico(),
                observacao.getData(),
                observacao.getValorQuantitativo(),
                observacao.getValorQualitativo(),
                observacao.getPrescricao()
        );
    }
}
