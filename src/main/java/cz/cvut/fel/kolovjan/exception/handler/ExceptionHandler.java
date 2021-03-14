package cz.cvut.fel.kolovjan.exception.handler;

import cz.cvut.fel.kolovjan.exception.PluginException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class ExceptionHandler<D extends PluginException> implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        log.error("Caught exception with message {}, returning 500, stack is {}", exception.getMessage(), exception.getStackTrace());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity(new CommandResponse(false, "Exception  with message " + exception.getMessage() + " was thrown while executing command"))
                       .build();
    }
}
