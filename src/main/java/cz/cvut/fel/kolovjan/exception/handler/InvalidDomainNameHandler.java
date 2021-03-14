package cz.cvut.fel.kolovjan.exception.handler;

import cz.cvut.fel.kolovjan.exception.InvalidDomainNameException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidDomainNameHandler implements ExceptionMapper<InvalidDomainNameException> {
    @Override
    public Response toResponse(InvalidDomainNameException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(new CommandResponse(false, exception.getMessage()))
                       .build();
    }
}
