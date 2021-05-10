package cz.cvut.fel.kolovjan.rest.test.util.cli;

import cz.cvut.fel.kolovjan.rest.test.util.ListType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@AllArgsConstructor
@ApplicationScoped
public class ManageListService {

    private ManageListCommand manageListCommand;

    public boolean isDomainInWhitelist(String domain, ListType list) {
        return manageListCommand.domainExistsInWhitelist(domain, list);
    }

    public boolean isDomainInBlacklist(String domain, ListType list) {
        return manageListCommand.domainExistsInBlacklist(domain, list);

    }

    public boolean isDomainInAnyList(String domain) {
        return isDomainInBlacklist(domain, ListType.REGEX) || isDomainInBlacklist(domain, ListType.EXACT) ||
                isDomainInWhitelist(domain, ListType.REGEX) || isDomainInWhitelist(domain, ListType.EXACT);
    }


    public void removeDomainFromWhitelist(String domain, ListType list) {
        manageListCommand.removeDomainFromWhitelist(domain, list);
    }

    public void removeDomainFromBlacklist(String domain, ListType list) {
        manageListCommand.removeDomainFromBlacklist(domain, list);

    }

}
