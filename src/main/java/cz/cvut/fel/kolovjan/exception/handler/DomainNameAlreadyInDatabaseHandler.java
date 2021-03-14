package cz.cvut.fel.kolovjan.exception.handler;

import cz.cvut.fel.kolovjan.exception.DomainNameAlreadyInDatabaseException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DomainNameAlreadyInDatabaseHandler implements ExceptionMapper<DomainNameAlreadyInDatabaseException> {

    @Override
    public Response toResponse(DomainNameAlreadyInDatabaseException exception) {
        return Response.status(Response.Status.CONFLICT)
                       .entity(new CommandResponse(false, exception.getMessage()))
                       .build();

    }
}
