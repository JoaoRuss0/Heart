package pt.ipleiria.estg.dei.ei.dae.project.ws;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DoenteDTO;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.PrescricaoDTO;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.PrescricaoBean;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("prescricoes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescricaoService {

    private static final Logger logger = Logger.getLogger("Prescricao.logger");

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
                prescricao.getCausa(),
                prescricao.getDoente().getEmail(),
                prescricao.getDataInicio(),
                prescricao.getDataFinal(),
                prescricao.getTipoPrescricao()
        );



        }

    @POST
    @Path("/")
    public Response createNewPrescricao(PrescricaoDTO prescricaoDTO) throws Exception {
        logger.log(Level.SEVERE, prescricaoDTO.getDoenteEmail() + prescricaoDTO.getCausa());
    Prescricao prescricao = prescricaoBean.create(
            prescricaoDTO.getCausa(),
            prescricaoDTO.getDoenteEmail(),
            prescricaoDTO.getDataInicio(),
            prescricaoDTO.getDataFinal(),
            prescricaoDTO.getTipoPrescricao()
    );
    return Response.status(Response.Status.CREATED).entity(toDTOPrescricao(prescricao)).build();


    }

    @PUT
    @Path("/{id}")
    public Response updatePrescricao(@PathParam("id") int id, PrescricaoDTO prescricaoDTO) throws Exception {
        Prescricao prescricao = prescricaoBean.find(id);

        if(prescricao == null) {
            throw new NotFoundException("Course with id " + id + " does not exist!");
        }

        prescricao = prescricaoBean.updatePrescricao(prescricao, prescricaoDTO.getCausa());
        return Response.ok(toDTOPrescricao(prescricao)).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteCourse(@PathParam("id") int id) throws Exception {
        prescricaoBean.deletePrescricao(id);

        if (prescricaoBean.find(id) != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }





  }

