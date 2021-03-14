package cz.cvut.fel.kolovjan.exception.handler;

import cz.cvut.fel.kolovjan.exception.GravityUpdateFailException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GravityUpdateFailHandler implements ExceptionMapper<GravityUpdateFailException> {
    @Override
    public Response toResponse(GravityUpdateFailException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity(new CommandResponse(false, exception.getMessage()))
                       .build();
    }
}
