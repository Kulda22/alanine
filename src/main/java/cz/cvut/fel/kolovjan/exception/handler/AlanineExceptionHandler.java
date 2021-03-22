package cz.cvut.fel.kolovjan.exception.handler;

import cz.cvut.fel.kolovjan.exception.AlanineException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class AlanineExceptionHandler implements ExceptionMapper<AlanineException> {

    @Override
    public Response toResponse(AlanineException exception) {
        log.error("Caught exception with message {}, returning 500, stack is {}", exception.getMessage(), exception.getStackTrace());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity(new CommandResponse(false, exception.getMessage()))
                       .build();
    }
}
