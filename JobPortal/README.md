# Job Magnet Application Folder

This folder contains the application code and Kubernetes deployment files.

## Main Components

- `JobPortal/JobPortal/JobPortal.BackEnd/JobPortal`: Spring Boot backend service
- `JobPortal/JobPortal.FrontEnd.New`: React + Vite frontend app
- `JobPortal/k8s`: Kubernetes manifests for Minikube

## Backend

- Java 17
- Maven build
- Dockerfile uses multi-stage build (builds JAR inside Docker)

## Frontend

- Node.js 20
- Vite build output served by Nginx Docker image

## Deployment

- Docker images are pushed to Docker Hub
- Kubernetes manifests in `k8s` use those images
- Full deployment steps: `JobPortal/k8s/README.md`

