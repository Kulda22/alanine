# This install script is heavily inspired from Pi-hole install script (https://github.com/pi-hole/pi-hole/blob/master/automated%20install/basic-install.sh).
#
# Installing via docker is the preffered way of installing Alanine.
# Or you can install it manually, just download right file and set cron after every boot.
#
# Prerequsities - if not on linux ARM64/AMD64 arch you have to have Java 8 or 11+ installed.
# Run this script :
# sudo ./install.sh
#
#

is_command() {
  # Checks for existence of string passed in as only function argument.
  local check_command="$1"

  command -v "${check_command}" >/dev/null 2>&1
}

installJava() {
  if ! is_command java; then
    printf "Java is not installed, or is not in PATH\\n"
    return
  fi
  #get file from github
  wget -qO- https://github.com/Kulda22/alanine/releases/latest/download/alanine.tar.xz | tar xJf -
  sudo chmod -R 755 /etc/.alanine/
  java_cron
  sudo java -jar /etc/.alanine/quarkus-app/*.jar >/dev/null 2>&1 &
}

installNative() {
  sudo chmod 755 /etc/.alanine /etc/.alanine/alanine-*-runner
  native_cron
  /etc/.alanine/alanine-*-runner >/dev/null 2>&1 &
}

native_cron() {
  #write out current crontab
  sudo crontab -l >/tmp/mycron
  #echo new cron into cron file
  echo "@reboot /etc/.alanine/alanine-*-runner" >>/tmp/mycron
  #install new cron file
  sudo crontab /tmp/mycron
}

java_cron() {
  #write out current crontab
  sudo crontab -l >/tmp/mycron
  #echo new cron into cron file
  echo "@reboot java -jar /etc/.alanine/quarkus-app/*.jar" >>/tmp/mycron
  #install new cron file
  sudo crontab /tmp/mycron
}

install_platform_dependent_version() {
  # This gives the machine architecture which may be different from the OS architecture...
  local machine
  machine=$(uname -m)

  local str="Detecting processor"
  printf "  %b %s..." "${INFO}" "${str}"
  # If the machine is arm or aarch
  if [[ "${machine}" == "arm"* || "${machine}" == *"aarch"* ]]; then
    # ARM
    local rev
    rev=$(uname -m | sed "s/[^0-9]//g;")
    #
    local lib
    lib=$(ldd /bin/ls | grep -E '^\s*/lib' | awk '{ print $1 }')
    #

    ### ARM64
    if [[ "${lib}" == "/lib/ld-linux-aarch64.so.1" ]]; then
      #get file from github -> alanine-arm64-runner
      printf "arm64 - installing native runner \\n"

      wget -q https://github.com/Kulda22/alanine/releases/latest/download/alanine-arm64-runner
      installNative

    else

      printf "32 bit arm - installing java version \\n"

      installJava
    fi
    # AMD64
  elif [[ "${machine}" == "x86_64" ]]; then
    # This gives the processor of packages dpkg installs (for example, "i386")
    local dpkgarch
    dpkgarch=$(dpkg --print-processor 2>/dev/null || dpkg --print-architecture 2>/dev/null)

    # Special case: This is a 32 bit OS, installed on a 64 bit machine
    # -> change machine processor to download the 32 bit executable
    # We only check this for Debian-based systems as this has been an issue
    # in the past (see https://github.com/pi-hole/pi-hole/pull/2004)
    if [[ "${dpkgarch}" == "i386" ]]; then
      printf "i386 - installing java version \\n"

      installJava
    else
      #get file from github -> alanine-amd64-runner
      printf "amd64 - installing native runner \\n "
      wget -q https://github.com/Kulda22/alanine/releases/latest/download/alanine-amd64-runner
      installNative
    fi
  else
    printf "unknown architecture - installing java version \\n"
    installJava
  fi

}
printf "Installing\\n"
printf "Checking for Pi-hole\\n"

if ! is_command pihole; then
  printf "Pihole is not installed, or is not in PATH\\n"
  return
fi

printf "Creating file structure\\n"

cd /etc
sudo mkdir .alanine
sudo chmod 777 .alanine
cd .alanine

install_platform_dependent_version
