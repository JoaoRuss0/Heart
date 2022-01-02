package pt.ipleiria.estg.dei.ei.dae.project.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyValueOutOfBoundsExceptionMapper  implements ExceptionMapper<MyValueOutOfBoundsException> {

    private static final Logger logger = Logger.getLogger("exceptions.MyValueOutOfBoundsExceptionMapper");

    @Override
    public Response toResponse(MyValueOutOfBoundsException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMsg).build();
    }
}