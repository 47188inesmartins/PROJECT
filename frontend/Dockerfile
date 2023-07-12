# Define a imagem base
FROM node:latest

# Define o diretório de trabalho
WORKDIR /app

# Copia o package.json e o package-lock.json para o diretório de trabalho
COPY package*.json ./

# Instala as dependências do projeto
RUN npm install --legacy-peer-deps

# Copia o restante do código-fonte para o diretório de trabalho
COPY . .

# Compila o código do frontend
RUN npm run build

# Expõe a porta em que o servidor do frontend irá executar
EXPOSE 8000

# Comando para iniciar o servidor do frontend
CMD ["npm", "start"]
