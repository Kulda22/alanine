printf "Uninstalling Alanine\\n"
printf "Removing cron\\n"

# in order to not having to detect architecture we remove both variants of cron command
(sudo crontab -l | grep -v -F "@reboot /etc/.alanine/alanine-*-runner") | sudo crontab -
(sudo crontab -l | grep -v -F "@reboot java -jar /etc/.alanine/quarkus-app/*.jar") | sudo crontab -

printf "Deleting files...\\n"
sudo rm -rf /etc/.alanine/
