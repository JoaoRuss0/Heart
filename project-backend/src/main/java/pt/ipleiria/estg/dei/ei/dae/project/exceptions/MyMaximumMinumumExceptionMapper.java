package pt.ipleiria.estg.dei.ei.dae.project.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class MyMaximumMinumumExceptionMapper implements ExceptionMapper<MyMaximumMinumumException> {

    private static final Logger logger = Logger.getLogger("exceptions.MyMaximumMinumumExceptionMapper");

    @Override
    public Response toResponse(MyMaximumMinumumException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.NOT_FOUND).entity(errorMsg).build();
    }
}