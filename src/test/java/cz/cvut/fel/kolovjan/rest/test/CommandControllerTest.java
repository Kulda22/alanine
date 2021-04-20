package cz.cvut.fel.kolovjan.rest.test;

import cz.cvut.fel.kolovjan.exception.PiholeAlreadyEnabledException;
import cz.cvut.fel.kolovjan.rest.CommandController;
import cz.cvut.fel.kolovjan.rest.test.util.cli.ManageListService;
import cz.cvut.fel.kolovjan.service.DnsBlockingService;
import cz.cvut.fel.kolovjan.service.LoggingService;
import cz.cvut.fel.kolovjan.service.StatusService;
import cz.cvut.fel.kolovjan.service.VersionService;
import cz.cvut.fel.kolovjan.utils.ListType;
import cz.cvut.fel.kolovjan.utils.TimeUnitEnum;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@QuarkusTest
@TestHTTPEndpoint(CommandController.class)
@Slf4j
class CommandControllerTest {
    @ConfigProperty(name = "alanine.version")
    String version;

    @Inject
    DnsBlockingService dnsBlockingService;
    @Inject
    StatusService statusService;
    @Inject
    LoggingService loggingService;
    @Inject
    ManageListService manageListService;
    @Inject
    VersionService versionService;

    @BeforeEach
    void beforeEach() {
        log.info("Cleaning");
        try {
            dnsBlockingService.enableBlocking();
            loggingService.enableLogging();
        } catch (PiholeAlreadyEnabledException ignored) {
        }
    }

    @Test
    void hearthBeat() {
        when().head()
              .then()
              .statusCode(200);
    }

    @Test
    void list() {

        Map<String, Boolean> statusMap = statusService.getStatusMap();

        when().get("/status")
              .then()
              .statusCode(200)
              .body("isBlockingEnabled", Matchers.is(statusMap.get("isBlockingEnabled")))
              .body("isDNSListening", Matchers.is(statusMap.get("isDNSListening")))
              .body("isLoggingEnabled", Matchers.is(statusMap.get("isLoggingEnabled")));
    }

    @Test
    void getVersion() {
        when().get("/version")
              .then()
              .statusCode(200)
              .body("message", Matchers.is(versionService.getVersion().getMessage()));

    }

    @Test
    void disableHappyPath() {

        when().put("/disable").then().statusCode(200).body("message", Matchers.containsString("Disabled"));
        Assertions.assertEquals(statusService.getStatusMap()
                                             .get("isBlockingEnabled"), false, "Pi-hole was not disabled");

    }

    @Test
    void disableAlreadyDisabled() {
        dnsBlockingService.disableBlocking(-1, TimeUnitEnum.SECONDS);

        when().put("/disable")
              .then()
              .statusCode(409)
              .body("message", Matchers.containsString("Pihole is already disabled!"))
              .and()
              .body("successful", Matchers.is(false));

        Assertions.assertEquals(statusService.getStatusMap()
                                             .get("isBlockingEnabled"), false, "Pi-hole disable on disable failed");

    }


    @Test
    void enableHappyPath() {
        dnsBlockingService.disableBlocking(-1, TimeUnitEnum.SECONDS);

        when().put("/enable").then().statusCode(200).body("message", Matchers.containsString("Enabled"));
        Assertions.assertEquals(statusService.getStatusMap().get("isBlockingEnabled"), true, "Pi-hole was not enabled");

    }

    @Test
    void enableAlreadyEnabled() {

        when().put("/enable")
              .then()
              .statusCode(409)
              .body("message", Matchers.containsString("Pihole is already enabled!"));
        Assertions.assertEquals(statusService.getStatusMap()
                                             .get("isBlockingEnabled"), true, "Pi-hole enable on enabled failed");
    }

    @Test
    void disableWithTime() {

        given().queryParam("time", "3")
               .queryParam("unit", "m")
               .when()
               .put("/disable")
               .then()
               .statusCode(200)
               .body("message", Matchers.containsString("Disabled"));


        Assertions.assertEquals(statusService.getStatusMap()
                                             .get("isBlockingEnabled"), false, "Pi-hole was not disabled");

        dnsBlockingService.enableBlocking();

    }

    @Test
    void enableLogging() {

        loggingService.disableLogging(-1);

        when().put("/log/enable").then().statusCode(200).body("message", Matchers.is("Logging has been enabled!"));
        Assertions.assertEquals(statusService.getStatusMap().get("isLoggingEnabled"), true, "Logging was not enabled");


    }

    @Test
    void disableLogging() {

        when().put("/log/disable").then().statusCode(200).body("message", Matchers.is("Logging has been disabled!"));
        Assertions.assertEquals(statusService.getStatusMap()
                                             .get("isLoggingEnabled"), false, "Logging was not disabled");
    }

    @Test
    void updateGravity() {
        when().put("/gravity/update")
              .then()
              .statusCode(200)
              .body("message", Matchers.is("Gravity database updated successfully!"));

    }

    @Test
    void restartDNS() {
        when().put("dns/restart")
              .then()
              .statusCode(200)
              .body("message", Matchers.is("Restart completed successfully!"));

    }

    @Test
    void whitelistDomain() {
        String domain = generateRandomDomain();

        given().queryParam("domain", domain)
               .when()
               .put("/whitelist")
               .then()
               .statusCode(200)
               .body("message", Matchers.is(domain + " successfully added to whitelist!"));


        Assertions.assertTrue(manageListService.isDomainInWhitelist(domain, ListType.EXACT), "domain not in whitelist !");

        manageListService.removeDomainFromWhitelist(domain, ListType.EXACT);
        Assertions.assertFalse(manageListService.isDomainInWhitelist(domain, ListType.EXACT), "clean up was not successful !");

    }

    @Test
    void whitelistRegexDomain() {
        String domain = generateRegexRandomDomain();

        given().queryParam("domain", domain)
               .when()
               .put("/whitelist/regex")
               .then()
               .statusCode(200)
               .body("message", Matchers.is(domain + " successfully added to whitelist!"));


        Assertions.assertTrue(manageListService.isDomainInWhitelist(domain, ListType.REGEX), "domain not in whitelist !");

        manageListService.removeDomainFromWhitelist(domain, ListType.REGEX);
        Assertions.assertFalse(manageListService.isDomainInWhitelist(domain, ListType.REGEX), "clean up was not successful !");

    }

    @Test
    void whitelistWildcardDomain() {
        log.info("starting whitelistWildcardDomain");

        String domain = generateRandomDomain();
        given().queryParam("domain", domain)
               .when()
               .put("/whitelist/wildcard")
               .then()
               .statusCode(200)
               .body("message", Matchers.is(domain + " successfully added to whitelist!"));


        String wildcardedDomain = domainToWildcard(domain);

        Assertions.assertTrue(manageListService.isDomainInWhitelist(wildcardedDomain, ListType.REGEX), "domain not in whitelist !");


        manageListService.removeDomainFromWhitelist(wildcardedDomain, ListType.REGEX);
        Assertions.assertFalse(manageListService.isDomainInWhitelist(wildcardedDomain, ListType.REGEX), "clean up was not successful !");
        log.info("ending whitelistWildcardDomain");

    }

    @Test
    void blacklistDomain() {
        String domain = generateRandomDomain();

        given().queryParam("domain", domain)
               .when()
               .put("/blacklist")
               .then()
               .statusCode(200)
               .body("message", Matchers.is(domain + " successfully added to blacklist!"));


        Assertions.assertTrue(manageListService.isDomainInBlacklist(domain, ListType.EXACT), "domain not in blacklist !");

        manageListService.removeDomainFromBlacklist(domain, ListType.EXACT);
        Assertions.assertFalse(manageListService.isDomainInBlacklist(domain, ListType.EXACT), "clean up was not successful !");

    }

    @Test
    void blacklistRegexDomain() {
        String domain = generateRegexRandomDomain();

        given().queryParam("domain", domain)
               .when()
               .put("/blacklist/regex")
               .then()
               .statusCode(200)
               .body("message", Matchers.is(domain + " successfully added to blacklist!"));


        Assertions.assertTrue(manageListService.isDomainInBlacklist(domain, ListType.REGEX), "domain not in blacklist !");

        manageListService.removeDomainFromBlacklist(domain, ListType.REGEX);
        Assertions.assertFalse(manageListService.isDomainInBlacklist(domain, ListType.REGEX), "clean up was not successful !");

    }

    // TODO
    @Test
    void blacklistWildcardDomain() {

        log.info("starting blacklistWildcardDomain");
        String domain = generateRandomDomain();
        given().queryParam("domain", domain)
               .when()
               .put("/blacklist/wildcard")
               .then()
               .statusCode(200)
               .body("message", Matchers.is(domain + " successfully added to blacklist!"));


        String wildcardedDomain = domainToWildcard(domain);

        Assertions.assertTrue(manageListService.isDomainInBlacklist(wildcardedDomain, ListType.REGEX), "domain not in blacklist !");


        manageListService.removeDomainFromBlacklist(wildcardedDomain, ListType.REGEX);
        Assertions.assertFalse(manageListService.isDomainInBlacklist(wildcardedDomain, ListType.REGEX), "clean up was not successful !");
        log.info("ending blacklistWildcardDomain");

    }

    @Test
    void getServerVersion() {
        when().get("/server/version").then().statusCode(200).body("message", Matchers.is(version));
    }

    private String generateRandomDomain() {
        Random random = new Random();
        String domain;
        while (true) {
            domain = "test" + random.nextInt(10000) + ".cz";
            if (!manageListService.isDomainInAnyList(domain)) {
                return domain;
            }
        }

    }

    private String generateRegexRandomDomain() {
        return "(^)" + generateRandomDomain();
    }

    private String domainToWildcard(String domain) {
        return "(^|\\.)" + domain.replace(".", "\\.") + "$";

    }

}