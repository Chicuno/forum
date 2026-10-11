#!/bin/sh
set -e

# Render inyecta $PORT. Si no está, usamos 80.
: "${PORT:=80}"
export PORT

# Genera la config de nginx sustituyendo ${PORT}
envsubst '${PORT}' < /etc/nginx/nginx.conf.template > /etc/nginx/nginx.conf

# Arranca Spring Boot en background
java -jar /app/app.jar &
BACKEND_PID=$!

# Si el backend muere, tumba nginx y sale
trap "kill $BACKEND_PID 2>/dev/null || true" EXIT

# Arranca nginx en foreground (proceso principal)
exec nginx -g 'daemon off;'