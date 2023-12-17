FROM node:20.9.0 AS build
WORKDIR /usr/src/app
COPY tic-tac-toe/package.json tic-tac-toe/package-lock.json ./
RUN npm install
COPY tic-tac-toe ./
RUN npm run build

FROM nginx:1.17.1-alpine AS frontend
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/tic-tac-toe/browser /usr/share/nginx/html
CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/assets/env.sample.js > /usr/share/nginx/html/assets/env.js && exec nginx -g 'daemon off;'"]
