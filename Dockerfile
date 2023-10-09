# Stage 1: Compile and Build angular codebase

# Use official node image as the base image
FROM node:alpine as node

# Set the working directory
WORKDIR /usr/local/app

# Add the source code to app
COPY ./ /usr/local/app/

# Install all the dependencies
RUN npm install


# Generate the build of the application
RUN npm run build


# Stage 2: Serve app with nginx server

# Use official nginx image as the base image
FROM nginx:mainline-alpine3.18-slim

# Copy the build output to replace the default nginx contents.
COPY --from=node /usr/local/app/dist/tasks-angular /usr/share/nginx/html

COPY nginx-custom.conf /etc/nginx/conf.d/default.conf

# Expose port 443
EXPOSE 443
