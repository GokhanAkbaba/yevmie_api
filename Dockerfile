# Build aşaması
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Maven ayarlarını optimize edelim
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

# Maven repository'sini cache'lemek için
COPY ./.m2 /root/.m2

# Önce sadece pom.xml'i kopyalayalım
COPY pom.xml .

# Bağımlılıkları indirelim
RUN mvn dependency:go-offline -B

# Kaynak kodları kopyalayıp build edelim
COPY src ./src
RUN mvn clean package -B -DskipTests

# Çalışma aşaması
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Build aşamasından JAR dosyasını kopyalayalım
COPY --from=build /app/target/*.jar app.jar

# curl'ü healthcheck için yükleyelim
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

# Healthcheck yapılandırması
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
