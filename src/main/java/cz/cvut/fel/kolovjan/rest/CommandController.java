package cz.cvut.fel.kolovjan.rest;


import cz.cvut.fel.kolovjan.service.*;
import cz.cvut.fel.kolovjan.utils.TimeUnitEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pi")
@Slf4j
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.WILDCARD)
public class CommandController {


    private DnsServerService dnsServerService;
    private GravityService gravityService;
    private DomainService domainService;
    private DnsBlockingService dnsBlockingService;
    private LoggingService loggingService;
    private StatusService statusService;
    private VersionService versionService;

    @HEAD
    public Response hearthBeat() {
        return Response.ok().build();
    }

    @GET
    @Path("/status")
    @NoCache
    public Response list() {
        return Response.ok(statusService.getStatusMap()).build();
    }

    @GET
    @Path("/version")
    public Response getVersion() {
        return Response.ok(versionService.getVersion()).build();
    }

    @PUT
    @Path("/disable")
    public Response disable(@QueryParam("time") @DefaultValue("-1") long time, @QueryParam("unit") @DefaultValue("m") TimeUnitEnum timeUnit) {
        return Response.ok(dnsBlockingService.disableLogging(time, timeUnit)).build();
    }

    @PUT
    @Path("/enable")
    public Response enable() {
        return Response.ok(dnsBlockingService.enableLogging()).build();
    }

    @PUT
    @Path("/log/disable")
    public Response disableLoggingWithTime(@QueryParam("time") @DefaultValue("-1") long time) {
        return Response.ok(loggingService.disableLogging(time)).build();
    }


    @PUT
    @Path("/log/enable")
    public Response enableLogging() {
        return Response.ok(loggingService.enableLogging()).build();
    }

    @PUT
    @Path("/gravity/update")
    public Response updateGravity() {
        return Response.ok(gravityService.updateGravity()).build();
    }

    @PUT
    @Path("dns/restart")
    public Response restartDNS() {
        return Response.ok(dnsServerService.restartDnsServer()).build();
    }

    @PUT
    @Path("/whitelist")
    public Response whitelistDomain(@QueryParam("domain") String domainName) {
        return Response.ok(domainService.whitelistExactDomain(domainName)).build();
    }

    @PUT
    @Path("/whitelist/regex")
    public Response whitelistRegexDomain(@QueryParam("domain") String domainName) {
        return Response.ok(domainService.whitelistRegexDomain(domainName)).build();
    }

    @PUT
    @Path("/whitelist/wildcard")
    public Response whitelistWildcardDomain(@QueryParam("domain") String domainName) {
        return Response.ok(domainService.whitelistWildcardDomain(domainName)).build();
    }

    @PUT
    @Path("/blacklist")
    public Response blacklistDomain(@QueryParam("domain") String domainName) {
        return Response.ok(domainService.blacklistExactDomain(domainName)).build();
    }

    @PUT
    @Path("/blacklist/regex")
    public Response blacklistRegexDomain(@QueryParam("domain") String domainName) {
        return Response.ok(domainService.blacklistRegexDomain(domainName)).build();
    }

    @PUT
    @Path("/blacklist/wildcard")
    public Response blacklistWildcardDomain(@QueryParam("domain") String domainName) {
        return Response.ok(domainService.blacklistWildcardDomain(domainName)).build();
    }
}