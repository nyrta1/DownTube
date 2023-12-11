# Use Node.js LTS version as the base image for building Angular
FROM node:latest as build-stage

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install Angular CLI globally
RUN npm install -g @angular/cli@latest

# Install project dependencies
RUN npm install

# Copy the entire project to the container
COPY . .

# Expose port 4200 to access the Angular development server
EXPOSE 4200

# Start the Angular development server when the container starts
CMD ["ng", "serve", "--host", "0.0.0.0", "--port", "4200"]
