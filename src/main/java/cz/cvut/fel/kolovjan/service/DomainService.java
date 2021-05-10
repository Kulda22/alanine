package cz.cvut.fel.kolovjan.service;

import cz.cvut.fel.kolovjan.cli.command.BlacklistCommand;
import cz.cvut.fel.kolovjan.cli.command.WhitelistCommand;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@AllArgsConstructor
@ApplicationScoped
public class DomainService {
    private WhitelistCommand whitelistCommand;
    private BlacklistCommand blacklistCommand;

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
}
