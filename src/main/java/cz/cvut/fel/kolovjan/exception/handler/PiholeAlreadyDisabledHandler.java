package cz.cvut.fel.kolovjan.exception.handler;

import cz.cvut.fel.kolovjan.exception.PiholeAlreadyDisabledException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PiholeAlreadyDisabledHandler implements ExceptionMapper<PiholeAlreadyDisabledException> {

    @Override
    public Response toResponse(PiholeAlreadyDisabledException exception) {
               return Response.status(Response.Status.CONFLICT)
                       .entity(new CommandResponse(false, "Pihole is already disabled!"))
                       .build();

    }
}
