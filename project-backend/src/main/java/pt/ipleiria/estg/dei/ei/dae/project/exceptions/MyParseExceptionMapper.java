package pt.ipleiria.estg.dei.ei.dae.project.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class MyParseExceptionMapper implements ExceptionMapper<MyParseException> {

    private static final Logger logger = Logger.getLogger("exceptions.MyParseExceptionMapper");

    @Override
    public Response toResponse(MyParseException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMsg).build();
    }
}
