# Kubernetes (Minikube) Guide

This folder contains Kubernetes manifests for running the Job Magnet frontend and backend on Minikube.

## Prerequisites

- Docker Desktop or Docker engine
- Minikube
- kubectl

## 1) Start Minikube and enable Ingress

```powershell
minikube start
minikube addons enable ingress
```

## 2) Apply base resources

Create the namespace, configmap, and secrets:

```powershell
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/backend-configmap.yaml
```

Copy the example secrets file and fill real values:

```powershell
Copy-Item k8s/backend-secrets.example.yaml k8s/backend-secrets.yaml
notepad k8s/backend-secrets.yaml
```

Then apply the secrets:

```powershell
kubectl apply -f k8s/backend-secrets.yaml
```

## 3) Deploy backend and frontend

```powershell
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/backend-service.yaml
kubectl apply -f k8s/frontend-deployment.yaml
kubectl apply -f k8s/frontend-service.yaml
kubectl apply -f k8s/ingress.yaml
```

## 3.1) Build and push images (optional)

If you want to build images locally and push to Docker Hub:

```powershell
cd JobPortal\JobPortal.BackEnd\JobPortal
mvn -DskipTests package
docker build -t e20376/job-magnet-backend:latest .
docker push e20376/job-magnet-backend:latest
```

```powershell
cd ..\..\..\JobPortal.FrontEnd.New
docker build -t e20376/job-magnet-frontend:latest --build-arg VITE_API_BASE=/api .
docker push e20376/job-magnet-frontend:latest
```

Then restart the deployments to pull new images:

```powershell
kubectl rollout restart deployment/job-magnet-backend -n job-magnet
kubectl rollout restart deployment/job-magnet-frontend -n job-magnet
```

## 4) Map the host name

Get the Minikube IP:

```powershell
minikube ip
```

Edit hosts file and map the IP:

```
<minikube-ip> job-magnet.local
```

Path on Windows:
```
C:\Windows\System32\drivers\etc\hosts
```

## 5) Open the app

```
http://job-magnet.local
```

## Updating deployments after new images

```powershell
kubectl rollout restart deployment/job-magnet-backend -n job-magnet
kubectl rollout restart deployment/job-magnet-frontend -n job-magnet
```

Check status:

```powershell
kubectl get pods -n job-magnet
kubectl get svc -n job-magnet
kubectl get ingress -n job-magnet
```

## Logs

```powershell
kubectl logs -n job-magnet deployment/job-magnet-backend
kubectl logs -n job-magnet deployment/job-magnet-frontend
```

## Scale up/down

```powershell
kubectl scale deployment/job-magnet-backend -n job-magnet --replicas=2
kubectl scale deployment/job-magnet-frontend -n job-magnet --replicas=2
```

## Rollback

```powershell
kubectl rollout undo deployment/job-magnet-backend -n job-magnet
kubectl rollout undo deployment/job-magnet-frontend -n job-magnet
```

## Troubleshooting

### 1) Ingress returns 404

- Make sure the ingress addon is enabled:
  ```powershell
  minikube addons enable ingress
  ```
- Check the ingress resource:
  ```powershell
  kubectl describe ingress -n job-magnet
  ```
- Verify the hosts file entry matches `job-magnet.local`.

### 2) Pods stuck in ImagePullBackOff

- Check image names in `k8s/backend-deployment.yaml` and `k8s/frontend-deployment.yaml`.
- Confirm Docker Hub images exist and are public or that you have imagePullSecrets set.
- Describe the pod for details:
  ```powershell
  kubectl describe pod -n job-magnet <pod-name>
  ```

### 3) Backend not reachable

- Confirm backend Service is running:
  ```powershell
  kubectl get svc -n job-magnet
  ```
- Check backend logs:
  ```powershell
  kubectl logs -n job-magnet deployment/job-magnet-backend
  ```
- Verify `/api` routing in `k8s/ingress.yaml`.

### 4) Frontend loads but API calls fail

- Ensure frontend is built with `VITE_API_BASE=/api`.
- Check browser DevTools network tab for the exact URL being called.

### 5) Database connection errors

- Confirm secrets are set in `k8s/backend-secrets.yaml`.
- Verify the DB is reachable from your machine or network.

## Flow diagram

```
  +-------------------------+                +---------------------------+
  |     GitHub Actions CI    |                |        Docker Hub         |
  |  backend + frontend build|----push------->|  e20376/job-magnet-*      |
  +------------+-------------+                +-------------+-------------+
               |                                            |
               |                                            |
               |                                   pull     |
               v                                            v
  +---------------------------+                 +---------------------------+
  |      Local Developer      |                 |     Minikube Cluster      |
  | kubectl apply/rollout     |                 | namespace: job-magnet     |
  +------------+--------------+                 +-------------+-------------+
               |                                            |
               |                                            |
               |                         +------------------+------------------+
               |                         |  Ingress (nginx) ingressClassName   |
               |                         |  host: job-magnet.local             |
               |                         |  /api  -> backend service           |
               |                         |  /     -> frontend service          |
               |                         +-----------+--------------+----------+
               |                                     |              |
               |                             / (web) |              | /api (REST)
               |                                     |              |
               |                         +-----------v---+   +------v----------+
               |                         | frontend svc  |   | backend svc     |
               |                         | port 80       |   | port 8080       |
               |                         +-------+-------+   +--------+--------+
               |                                 |                    |
               |                                 |                    |
               |                         +-------v-------+   +-------v--------+
               |                         | frontend pod  |   | backend pod    |
               |                         | nginx + SPA   |   | spring boot    |
               |                         +-------+-------+   +-------+--------+
               |                                 |                    |
               |                                 |                    |
               |                           VITE_API_BASE=/api          |
               |                                 |                    |
               |                                 |                    |
               |                        +--------v----------------+    |
               |                        |  backend config & secret |    |
               |                        |  ConfigMap: SPRING_*     |    |
               |                        |  Secret: DB/JWT/MAIL     |    |
               |                        +--------+-----------------+    |
               |                                 |                    |
               |                                 |                    |
               |                            +----v-----+              |
               |                            |  MySQL   |<-------------+
               |                            +----------+
```
