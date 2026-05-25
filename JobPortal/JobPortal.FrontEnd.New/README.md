# Job Magnet Frontend

React + Vite frontend for Job Magnet.

## Requirements

- Node.js 20+
- npm

## Local Development

```bash
npm ci
npm run dev
```

## Production Build

```bash
npm ci
npm run build
```

## Docker

Build image locally:

```bash
docker build -t <dockerhub-username>/job-magnet-frontend:latest --build-arg VITE_API_BASE=/api .
```

Push image:

```bash
docker push <dockerhub-username>/job-magnet-frontend:latest
```

## CI/CD

- CI: `.github/workflows/frontend-ci.yml` (PR to `main`)
- CD: `.github/workflows/frontend-cd.yml` (PR to `Deployment-Branch`)
