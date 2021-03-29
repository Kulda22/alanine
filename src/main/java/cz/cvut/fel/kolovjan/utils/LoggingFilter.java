package cz.cvut.fel.kolovjan.utils;

import io.vertx.core.http.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;


@Provider
@Slf4j
public class LoggingFilter implements ContainerRequestFilter {



    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;


    @Override
    public void filter(ContainerRequestContext context) {
        final String method = context.getMethod();
        final String path = info.getPath();
        final String address = request.remoteAddress().toString();
        log.debug("Request {} {} from IP {}", method, path, address);
    }
}
