# Build local docker image via docker
```bash
docker build -t enrolment-management .
docker run --name enrolment-management enrolment-management:latest
```

# Build local docker image via Jib
```bash
./mvnw clean install -D skipTests jib:dockerBuild -Djib.to.image=enrolment-management:latest
```

# Push image to docker hup
```bash
./mvnw clean install -D skipTests jib:build -Djib.to.image=franktranvantu/enrolment-management:latest
```

# Pull image to local
```bash
docker pull franktranvantu/enrolment-management
```

# Run container
```bash
docker run -p 8080:8080 franktranvantu/enrolment-management
```