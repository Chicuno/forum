# syntax=docker/dockerfile:1

# ---------- Etapa 1: build del frontend ----------
FROM node:20-alpine AS frontend-build
WORKDIR /frontend
COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci
COPY frontend/ .
RUN npm run build

# ---------- Etapa 2: build del backend ----------
FROM maven:3.9-eclipse-temurin-17 AS backend-build
WORKDIR /workspace
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline
COPY src/ src/
RUN ./mvnw package -DskipTests

# ---------- Etapa 3: imagen final nginx + JRE ----------
FROM eclipse-temurin:17-jre

RUN apt-get update \
 && apt-get install -y --no-install-recommends nginx gettext-base curl \
 && rm -rf /var/lib/apt/lists/* \
 && rm -f /etc/nginx/sites-enabled/default

WORKDIR /app

COPY --from=backend-build /workspace/target/app.jar /app/app.jar
COPY --from=frontend-build /frontend/dist /usr/share/nginx/html
COPY nginx.conf.template /etc/nginx/nginx.conf.template
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENV PORT=80
EXPOSE 80

ENTRYPOINT ["/entrypoint.sh"]