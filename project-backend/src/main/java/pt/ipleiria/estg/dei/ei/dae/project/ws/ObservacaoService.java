package pt.ipleiria.estg.dei.ei.dae.project.ws;


import pt.ipleiria.estg.dei.ei.dae.project.dtos.ObservacaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PrescricaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.DoenteBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ObservacaoBean;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.ProfissionalDeSaudeBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Observacao;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyValueNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyValueOutOfBoundsException;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.text.ParseException;
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

        return Response.ok(toDTOs(observacaoBean.getAll())).build();
    }

    @POST
    @Path("/")
    public Response createNewObservacao(ObservacaoDTO observacaoDTO) throws MyEntityNotFoundException, ParseException, MyValueNotFoundException, MyValueOutOfBoundsException {

        Observacao observacao = observacaoBean.create(
                observacaoDTO.getDoenteEmail(),
                securityContext.getUserPrincipal().getName(),
                observacaoDTO.getNomeDadoBiomedico(),
                observacaoDTO.getData(),
                observacaoDTO.getValorQuantitativo(),
                observacaoDTO.getValorQualitativo()
        );
        return Response.status(Response.Status.CREATED).entity(toDTOObservacao(observacao)).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteObservacao(@PathParam("id") int id) throws Exception {
        observacaoBean.delete(id);

        if (observacaoBean.find(id) != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/{id}")
    public Response getObservacao(@PathParam("id") int id) throws MyEntityNotFoundException {
        Observacao observacao = observacaoBean.findOrFail(id);

        return Response.ok(toDTOObservacao(observacao)).build();
    }


    @PUT
    @Path("/{id}/update")
    public Response updatePrescricao(@PathParam("id") int id, ObservacaoDTO observacaoDTO) throws Exception {
        Observacao observacao = observacaoBean.find(id);

        if(observacao == null) {
            throw new NotFoundException("Course with id " + id + " does not exist!");
        }

        observacao = observacaoBean.updateObservacao(observacao, observacaoDTO);
        return Response.ok(toDTOObservacao(observacao)).build();
    }



    private List<ObservacaoDTO> toDTOs(List<Observacao> prescricoes) {
        return prescricoes.stream().map(this::toDTOObservacao).collect(Collectors.toList());
    }

    private ObservacaoDTO toDTOObservacao(Observacao observacao) {
        System.out.println(observacao.getPrescricao());
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
