package pt.ipleiria.estg.dei.ei.dae.project.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class MyQualificadoresEmptyMapper implements ExceptionMapper<MyQualificadoresEmpty> {

    private static final Logger logger = Logger.getLogger("exceptions.MyQualificadoresEmptyMapper");

    @Override
    public Response toResponse(MyQualificadoresEmpty e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.NOT_FOUND).entity(errorMsg).build();
    }
}
