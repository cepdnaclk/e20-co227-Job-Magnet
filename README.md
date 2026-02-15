# Job Magnet

Job Magnet is a full-stack job portal with a React frontend, Spring Boot backend, Dockerized deployment, and Kubernetes manifests for Minikube.

## Project Structure

- `JobPortal/JobPortal/JobPortal.BackEnd/JobPortal`: Spring Boot backend
- `JobPortal/JobPortal.FrontEnd.New`: React + Vite frontend
- `JobPortal/k8s`: Kubernetes manifests (namespace, deployments, services, ingress)
- `.github/workflows`: GitHub Actions pipelines (CI/CD)

## CI/CD Workflows

This repository uses 4 separate workflows:

- `.github/workflows/backend-ci.yml`
- `.github/workflows/backend-cd.yml`
- `.github/workflows/frontend-ci.yml`
- `.github/workflows/frontend-cd.yml`

Trigger rules:

- CI (backend + frontend): runs on `pull_request` to `main`
- CD (backend + frontend): runs on `pull_request` to `Deployment-Branch`

CD pushes Docker images to Docker Hub using:

- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

## Docker Images

- Backend: `${DOCKERHUB_USERNAME}/job-magnet-backend:latest`
- Frontend: `${DOCKERHUB_USERNAME}/job-magnet-frontend:latest`

## Local Kubernetes (Minikube)

See `JobPortal/k8s/README.md` for the complete Minikube deployment guide.

