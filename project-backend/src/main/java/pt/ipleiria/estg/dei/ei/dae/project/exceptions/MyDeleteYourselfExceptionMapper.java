package pt.ipleiria.estg.dei.ei.dae.project.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyDeleteYourselfExceptionMapper implements ExceptionMapper<MyDeleteYourselfException> {

    private static final Logger logger = Logger.getLogger("exceptions.MyDeleteYourselfExceptionMapper");

    @Override
    public Response toResponse(MyDeleteYourselfException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.CONFLICT).entity(errorMsg).build();
    }
}
