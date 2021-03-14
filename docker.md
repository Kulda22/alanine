### internal stuff

./mvnw package -Pnative -Dquarkus.native.container-build=true
 
docker build -f Dockerfile.native  -t kulda22/alanine:latest .



----- multiarch


docker run --rm --privileged multiarch/qemu-user-static --reset -p yes
docker buildx create --name multiarch --driver docker-container --use
docker buildx inspect --bootstrap

docker buildx  build -f Dockerfile.fastjar --platform linux/amd64,linux/arm64,linux/arm/v7  -t kulda22/alanine:latest --push .

CI CD Dle tohoto https://www.padok.fr/en/blog/multi-architectures-docker-iot


hodit dva typy images - jeden s javou, druhy s native

native docker buildx  build -f Dockerfile.native --platform linux/amd64  -t kulda22/alanine:native --push .
todo native on arm - https://www.nevernull.io/blog/building-a-native-java-application-for-arm64-with-quarkus/

jar 
prune - https://www.loicmathieu.fr/wordpress/en/informatique/quarkus-jlink-et-application-class-data-sharing-appcds/


jlink --no-header-files --no-man-pages --output target/customjdk --compress=2 --strip-debug --module-path $JAVA_HOME/jmods --add-modules java.base,java.compiler,java.datatransfer,java.desktop,java.instrument,java.logging,java.management,java.naming,java.rmi,java.sql,java.transaction.xa,java.xml,jdk.compiler,jdk.unsupported
/// worthless - 30 mb save ?                                                

