package pt.ipleiria.estg.dei.ei.dae.project.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.Logger;

public class MyPasswordTooShortExceptionMapper implements ExceptionMapper<MyPasswordTooShortException> {

    private static final Logger logger = Logger.getLogger("exceptions.MyPasswordTooShortExceptionMapper");

    @Override
    public Response toResponse(MyPasswordTooShortException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.NOT_FOUND).entity(errorMsg).build();
    }
}
