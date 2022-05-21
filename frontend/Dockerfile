### STAGE 1: Build ###
FROM node:14-bullseye AS build
WORKDIR /opt/app/frontend
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build:prod

### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /opt/app/frontend/dist/frontend /usr/share/nginx/html
