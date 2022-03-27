# Alanine Server

## Overview

This project aims to simplify controlling of your Pi-hole. It consists of two parts 
- Server part which is a simple stand-alone REST server that you can use to control Pi-hole via your own app.
 
- Browser plugin which acts as simple user interface.

Under the hood this project uses This project uses Quarkus, the Supersonic Subatomic Java Framework. This enables to compile Java to native code, which is useful for not-so-powerful hardware (e.g. Raspberry Pi). Unfortunately, only linux binaries for AMD64 and ARM64 are available at the moment. So if you want to run Alanine on other architecture, you have to use Java JVM.  


## Installation

You can install Alanine in two ways. Docker or install script.

The preferred way is to install docker container, since it is easier to manage and upgrade.

### Docker install

Alanine docker image extends official Pi-hole docker image (that means in one docker you have your standart Pi-hole and Alanine server), so should you use same parameters/script as you do with official docker image. Just add `-p 8221:8221` and use `kulda22/alanine` as an image.

Simple example of bash command to create and run Alanine in docker:

```shell script

    # Note: ServerIP should be replaced with your external ip.
    docker run -d \
        --name alanine \
        -p 53:53/tcp -p 53:53/udp \
        -p 80:80 \
        -p 443:443 \
        -p 8221:8221 \
        -e TZ="America/Chicago" \
        -v "~/etc-pihole/:/etc/pihole/" \
        -v "~/etc-dnsmasq.d/:/etc/dnsmasq.d/" \
        --dns=127.0.0.1 --dns=1.1.1.1 \
        --restart=unless-stopped \
        --hostname pi.hole \
        -e VIRTUAL_HOST="pi.hole" \
        -e PROXY_LOCATION="pi.hole" \
        -e ServerIP="127.0.0.1" \
        kulda22/alanine:latest
```

### Manual / Script install

With install script things get a little more difficult. Again, please consider using docker.

You can use script to install Alanine, or you can install it manually. You only have to download the right file from https://github.com/Kulda22/alanine/releases/latest, change file permissions and install cron to run the file after boot. If you have AMD64/ARM64 architecture download right binary file and add to cron `@reboot /<path to binary/alanine-*-runner`, if not use `@reboot java -jar /<path to jar>/quarkus-run.jar`.



#### Prerequisites 


If you are running linux ARM64 or AMD64 architecture you only need to have Pi-hole installed.

For any other, java 8 or 11+ is a necessity along with Pi-hole.

You can verify your Pi-hole instalation by running `$ pihole -v`

#### Installation

Just download the install script (`install.sh`) and run `$ sudo ./install.sh`.
#### Uninstall 

For uninstall just use uninstall script (`$ sudo ./uninstall`)

### Update
Sorry, but we chose the easiest way to update - just uninstall and install again!


## Browser plugin 
Alanine plugin is web browser plugin that uses this server. 
See [plugin repository](https://github.com/Kulda22/alanine-plugin)


## Open api specification
To be done. 


## Contributing

Any kind of PR, wishes for feature or feedback is welcome!

## Versions
**X.Y.Z**

- **Z** - Bugfixes, small changes that doesn't change the REST API structure.
- **Y** - Bigger change in request/response structure - for example adding or removing parameter. 
- **X** - Adding/removing endpoint.

## Pi-hole version
Pi-hole FTL v5.14, Web v5.11 and Core v5.9

