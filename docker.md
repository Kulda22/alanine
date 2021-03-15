### internal stuff

./mvnw clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=kulda22/builder:latest
 
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



NATIVE MULTIARCH 

builder -  docker build -f Dockerfile.nativebuilder -t kulda22/alanine:native-builder-arm64 .



 ./mvnw package -Pnative -Dquarkus.native.container-build=true 
docker build -t kulda22/alanine:native-amd64 -f Dockerfile.native  --build-arg ARCH=amd64/ .
docker push kulda22/alanine:native-amd64

./mvnw clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=kulda22/alanine:native-builder-arm64 
docker build -t kulda22/alanine:native-arm64 -f Dockerfile.native --build-arg ARCH=arm64/ .
docker push kulda22/alanine:native-arm64

//docker buildx  build -f Dockerfile.native --platform linux/arm64 -t kulda22/alanine:native-arm64 . --push



docker manifest create kulda22/alanine:native-latest --amend kulda22/alanine:native-arm64 --amend kulda22/alanine:native-amd64

docker manifest push kulda22/alanine:native-latest

ARM -> mvn package s builderem 
AMD -> default mvn package 


