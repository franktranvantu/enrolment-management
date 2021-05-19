# Build local docker image via docker
```bash
docker build -t enrolment-management .
docker run --name enrolment-management enrolment-management
```

# Build local docker image via Jib
```bash
./mvnw clean install -D skipTests jib:dockerBuild -Djib.to.image=enrolment-management
```