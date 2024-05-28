# Establecer la imagen base
FROM node:22.1.0 as build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos de package.json y package-lock.json
COPY package*.json ./

# Instalar las dependencias del proyecto
RUN npm install

# Copiar el resto del código de la aplicación en el contenedor
COPY . .

# Compilar la aplicación de Ionic
RUN npm run build

# Usa una imagen de nginx para servir la aplicación
FROM nginx:alpine

# Instalar Node.js y npm en la imagen de nginx
RUN apk add --update nodejs npm

# Copiar el directorio generado en la etapa de construcción a la imagen de nginx
COPY --from=build /app/www /usr/share/nginx/html

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8100

# Comando para iniciar la aplicación
CMD ["npm", "run", "serve", "start"]