# Kubernetes (Minikube) Guide

This folder contains Kubernetes manifests for deploying Job Magnet backend and frontend on Minikube.

## Prerequisites

- Docker Desktop running (Linux engine)
- Minikube
- kubectl

If Minikube fails with `dockerDesktopLinuxEngine` pipe errors, start Docker Desktop first, then run:

```powershell
docker version
docker info
minikube start --driver=docker
```

## Images Used

- Backend deployment currently uses: `e20376/job-magnet-backend:latest`
- Frontend deployment should use your Docker Hub frontend image

## 1) Start Minikube

```powershell
minikube start --driver=docker
minikube addons enable ingress
kubectl config use-context minikube
```

## 2) Create namespace and configs

```powershell
kubectl apply -f JobPortal/k8s/namespace.yaml
kubectl apply -f JobPortal/k8s/backend-configmap.yaml
kubectl apply -f JobPortal/k8s/backend-secrets.yaml
```

If secrets file does not exist yet:

```powershell
Copy-Item JobPortal/k8s/backend-secrets.example.yaml JobPortal/k8s/backend-secrets.yaml
```

## 3) Deploy workloads

```powershell
kubectl apply -f JobPortal/k8s/backend-deployment.yaml
kubectl apply -f JobPortal/k8s/backend-service.yaml
kubectl apply -f JobPortal/k8s/frontend-deployment.yaml
kubectl apply -f JobPortal/k8s/frontend-service.yaml
kubectl apply -f JobPortal/k8s/ingress.yaml
```

## 4) Verify rollout

```powershell
kubectl -n job-magnet rollout status deployment/job-magnet-backend
kubectl -n job-magnet rollout status deployment/job-magnet-frontend
kubectl -n job-magnet get pods,svc,ingress
```

## 5) Access app

Get Minikube IP:

```powershell
minikube ip
```

Add hosts entry in:

`C:\Windows\System32\drivers\etc\hosts`

```text
<minikube-ip> job-magnet.local
```

Open:

`http://job-magnet.local`

## CI/CD to Docker Hub

Root workflows:

- `.github/workflows/backend-ci.yml` (PR to `main`)
- `.github/workflows/backend-cd.yml` (PR to `Deployment-Branch`)
- `.github/workflows/frontend-ci.yml` (PR to `main`)
- `.github/workflows/frontend-cd.yml` (PR to `Deployment-Branch`)

CD workflows build and push images with `docker/build-push-action@v6`.

## Useful Commands

```powershell
kubectl -n job-magnet logs deployment/job-magnet-backend
kubectl -n job-magnet logs deployment/job-magnet-frontend
kubectl -n job-magnet rollout restart deployment/job-magnet-backend
kubectl -n job-magnet rollout restart deployment/job-magnet-frontend
```
