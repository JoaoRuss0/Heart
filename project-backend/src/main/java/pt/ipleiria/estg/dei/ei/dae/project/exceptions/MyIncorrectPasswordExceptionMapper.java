package pt.ipleiria.estg.dei.ei.dae.project.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyIncorrectPasswordExceptionMapper implements ExceptionMapper<MyIncorrectPasswordException> {

    private static final Logger logger = Logger.getLogger("exceptions.MyIncorrectPasswordExceptionMapper");

    @Override
    public Response toResponse(MyIncorrectPasswordException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.CONFLICT).entity(errorMsg).build();
    }
}