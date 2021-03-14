package cz.cvut.fel.kolovjan.exception.handler;

import cz.cvut.fel.kolovjan.exception.WrongTimeFormatException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class WrongTimeFormatHandler implements ExceptionMapper<WrongTimeFormatException> {

    @Override
    public Response toResponse(WrongTimeFormatException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(new CommandResponse(false, exception.getMessage()))
                       .build();
    }
}
