package cz.cvut.fel.kolovjan.rest.test.util.cli;


import cz.cvut.fel.kolovjan.cli.command.Command;
import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.AlanineException;
import cz.cvut.fel.kolovjan.exception.InvalidDomainNameException;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import cz.cvut.fel.kolovjan.utils.ListType;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class ManageListCommand extends Command {
    public ManageListCommand(CommandExecutorInterface commandExecutor) {
        super(commandExecutor);
    }

    public ManageListCommand() {

    }

    public boolean domainExistsInBlacklist(String domain, ListType list) {
        if (list.equals(ListType.EXACT)) {
            return domainExists(domain, "-b");
        } else {
            return domainExists(domain, "--regex");
        }
    }

    public boolean domainExistsInWhitelist(String domain, ListType list) {
        if (list.equals(ListType.EXACT)) {
            return domainExists(domain, "-w");
        } else {
            return domainExists(domain, "--white-regex");
        }
    }

    private boolean domainExists(String domain, String command) {

        ExecutorReturnWrapper executorReturnWrapper = commandExecutor.execute("pihole " + command + " -l");

        if (executorReturnWrapper.getExitValue() == 0) {
            return executorReturnWrapper.getOutput().contains(domain);
        } else {
            throw new AlanineException("Nonzero return code from domain list - output is " + executorReturnWrapper.getOutput() + " error output is " + executorReturnWrapper
                    .getErrorOutput());
        }

    }

    public void removeDomainFromBlacklist(String domain, ListType listType) {
        if (listType.equals(ListType.EXACT)) {
            removeDomainFromList(domain, "-b");
        } else {
            removeDomainFromList(domain, "--regex");
        }
    }

    public void removeDomainFromWhitelist(String domain, ListType listType) {
        if (listType.equals(ListType.EXACT)) {
            removeDomainFromList(domain, "-w");
        } else {
            removeDomainFromList(domain, "--white-regex");
        }
    }

    private void removeDomainFromList(String domain, String flag) {
        if (checkIfDomainNameIsMalicious(domain)) {
            // todo better message ? or security through obscurity
            log.error("Domain may be malicious");
            throw new InvalidDomainNameException(domain);
        }
        domain = sanitizeString(domain);

        String command = "pihole " + flag + " -d " + domain;

        ExecutorReturnWrapper executorReturnWrapper = commandExecutor.execute(command);

        if (executorReturnWrapper.getExitValue() != 0) {
            throw new AlanineException("Nonzero return code from remove from domain list - output is " + executorReturnWrapper
                    .getOutput() + " error output is " + executorReturnWrapper
                    .getErrorOutput());
        }

    }


}
