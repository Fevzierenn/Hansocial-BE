# Uygun bir Java sürümü içeren base image seçin
FROM openjdk:17-jdk-slim

WORKDIR /app
# Uygulamanın çalıştırılacağı port
EXPOSE 8080

# Maven ile build edilen .jar dosyasını container'a kopyala
COPY target/hansocial-bulutbilisim.jar /app/uygulama.jar

# Uygulamanın başlatılması için gerekli komut
ENTRYPOINT ["java", "-jar", "/app/uygulama.jar"]
