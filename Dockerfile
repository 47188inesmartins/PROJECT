# Define a imagem base com o Java instalado
FROM openjdk:17

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR do backend para o diretório de trabalho
COPY ./build/libs/project-0.0.1-SNAPSHOT.jar .

# Expõe a porta em que o servidor do backend irá executar (geralmente a porta 8080)
EXPOSE 8080

# Comando para iniciar o servidor do backend
CMD ["java", "-jar", "project-0.0.1-SNAPSHOT.jar"]
