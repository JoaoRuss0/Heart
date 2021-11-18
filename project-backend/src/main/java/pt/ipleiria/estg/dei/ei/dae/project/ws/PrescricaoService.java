package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DoenteDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PrescricaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PrescricaoBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("prescricoes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescricaoService {

    @EJB
    PrescricaoBean prescricaoBean;

    @GET
    @Path("/")
    public Response getAll()
    {
        return Response.ok(toDTOs(prescricaoBean.getAll())).build();
    }

    private List<PrescricaoDTO> toDTOs(List<Prescricao> prescricoes) {
        return prescricoes.stream().map(this::toDTOPrescricao).collect(Collectors.toList());
    }

    private PrescricaoDTO toDTOPrescricao(Prescricao prescricao) {
        return new PrescricaoDTO(
                prescricao.getId(),
                prescricao.getCausa()
        );
    }

  }
