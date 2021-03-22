package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.AlanineException;
import cz.cvut.fel.kolovjan.exception.DomainNameAlreadyInDatabaseException;
import cz.cvut.fel.kolovjan.exception.InvalidDomainNameException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class BlacklistCommand extends Command {
    public BlacklistCommand(CommandExecutorInterface commandExecutor) {
        super(commandExecutor);
    }

    public BlacklistCommand() {

    }


    public CommandResponse blacklistExactDomain(String domain) {


        return execute("pihole -b " + domain, domain);

    }

    public CommandResponse blacklistRegexDomain(String domain) {
        return execute("pihole --regex " + domain, domain);
    }

    public CommandResponse blacklistWildcardDomain(String domain) {
        return execute("pihole --wild " + domain, domain);
    }

    private CommandResponse execute(String command, String domain) {
        if (checkIfDomainNameIsMalicious(domain)) {
            // todo better message ? or security through obscurity
            log.error("malicious");
            throw new InvalidDomainNameException(domain);
        }
        command = sanitizeString(command);

        ExecutorReturnWrapper executorReturnWrapper = commandExecutor.execute(command);
        log.info(executorReturnWrapper.toString());

        if (executorReturnWrapper.getExitValue() == 0) {
            if (executorReturnWrapper.getOutput().contains("[i] Adding ")) {
                log.info("god");
                return new CommandResponse(true, domain + " successfully added to blacklist");
                /// already exists in blacklist, no need to add! || already exists in whitelist, no need to add!
            } else if (executorReturnWrapper.getOutput().matches("^.*already exists in (blacklist|whitelist).*\\n")) {

                throw new DomainNameAlreadyInDatabaseException(executorReturnWrapper.getOutput());
            } else if (executorReturnWrapper.getOutput().contains("is not a valid argument or domain name!")) {
                log.info("fuck");
                throw new InvalidDomainNameException(domain);
            } else {
                throw new AlanineException("Unknown output from blacklist command :" + executorReturnWrapper.getOutput());
            }
        } else {
            throw new AlanineException(executorReturnWrapper.getErrorOutput());
        }

    }
}
