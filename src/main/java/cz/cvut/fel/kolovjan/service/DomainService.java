package cz.cvut.fel.kolovjan.service;

import cz.cvut.fel.kolovjan.cli.command.BlacklistCommand;
import cz.cvut.fel.kolovjan.cli.command.ManageListCommand;
import cz.cvut.fel.kolovjan.cli.command.WhitelistCommand;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import cz.cvut.fel.kolovjan.utils.ListType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@AllArgsConstructor
@ApplicationScoped
public class DomainService {
    private WhitelistCommand whitelistCommand;
    private BlacklistCommand blacklistCommand;
    private ManageListCommand manageListCommand;

    public CommandResponse whitelistExactDomain(String domainName) {
        return whitelistCommand.whitelistExactDomain(domainName);
    }

    public CommandResponse whitelistWildcardDomain(String domainName) {
        return whitelistCommand.whitelistWildcardDomain(domainName);
    }

    public CommandResponse whitelistRegexDomain(String domainName) {
        return whitelistCommand.whitelistRegexDomain(domainName);
    }

    public CommandResponse blacklistExactDomain(String domainName) {
        return blacklistCommand.blacklistExactDomain(domainName);
    }

    public CommandResponse blacklistWildcardDomain(String domainName) {
        return blacklistCommand.blacklistWildcardDomain(domainName);
    }

    public CommandResponse blacklistRegexDomain(String domainName) {
        return blacklistCommand.blacklistRegexDomain(domainName);
    }

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
