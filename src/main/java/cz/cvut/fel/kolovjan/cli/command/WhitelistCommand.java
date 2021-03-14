package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.DomainNameAlreadyInDatabaseException;
import cz.cvut.fel.kolovjan.exception.InvalidDomainNameException;
import cz.cvut.fel.kolovjan.exception.PluginException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class WhitelistCommand extends Command {
    public WhitelistCommand(CommandExecutorInterface commandExecutor) {
        super(commandExecutor);
    }

    public WhitelistCommand() {

    }

    /**
     * check if domain could contain some sort of injection
     * <p>
     * <p>
     * bad characters  ! ( ) | > < ; & \
     *
     * @param domain
     * @return
     */
    private boolean checkIfDomainNameIsMalicioud(String domain) {
        return domain.contains(" ");
    }

    public CommandResponse whitelistExactDomain(String domain) {


        return execute("pihole -w " + domain, domain);

    }

    public CommandResponse whitelistRegexDomain(String domain) {
        return execute("pihole --white-regex " + domain, domain);
    }

    public CommandResponse whitelistWildcardDomain(String domain) {
        return execute("pihole --white-wild " + domain, domain);
    }

    private CommandResponse execute(String command, String domain) {
        log.error("WGITELIST :" + command);
        if (checkIfDomainNameIsMalicioud(domain)) {
            // todo better message ? or security through obscurity
            log.error("malicious");
            throw new InvalidDomainNameException(domain);
        }
        command = sanitizeString(command);

        ExecutorReturnWrapper executorReturnWrapper = commandExecutor.execute(command);
        log.info(executorReturnWrapper.toString());

        if (executorReturnWrapper.getExitValue() == 0) {
            if (executorReturnWrapper.getOutput().contains("[i] Adding ")) {
                return new CommandResponse(true, domain + " successfully added to whitelist");
                /// already exists in blacklist, no need to add! || already exists in whitelist, no need to add!

            } else if (executorReturnWrapper.getOutput().matches("^.*already exists in (blacklist|whitelist).*\\n")) {
                throw new DomainNameAlreadyInDatabaseException(executorReturnWrapper.getOutput());
            } else if (executorReturnWrapper.getOutput().contains("is not a valid argument or domain name!")) {
                throw new InvalidDomainNameException(domain);
            } else {
                throw new PluginException("Unknown output from blacklist command :" + executorReturnWrapper.getOutput());
            }
        } else {
            throw new PluginException(executorReturnWrapper.getErrorOutput());
        }

    }
}
