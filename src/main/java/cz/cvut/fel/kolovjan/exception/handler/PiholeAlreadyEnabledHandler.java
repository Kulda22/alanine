package cz.cvut.fel.kolovjan.exception.handler;

import cz.cvut.fel.kolovjan.exception.PiholeAlreadyEnabledException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PiholeAlreadyEnabledHandler implements ExceptionMapper<PiholeAlreadyEnabledException> {


    @Override
    public Response toResponse(PiholeAlreadyEnabledException exception) {
        return Response.status(Response.Status.CONFLICT)
                       .entity(new CommandResponse(false, "Pihole is already enabled!"))
                       .build();

    }
}
