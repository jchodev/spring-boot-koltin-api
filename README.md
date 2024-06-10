## create project 
https://start.spring.io/

## login docker
docker login

## Build Docker (Normal)
### build docker image
docker build -t {image name} .
e.g. docker build -t springboot-koltin-gradle .

### run docker image at local
docker run -p 8080:8080 {image name}
e.g. docker run -p 8080:8080 springboot-koltin-gradle

## Build docker image (other option)
e.g. docker build -t cho17bee2/springboot-koltin-gradle:latest .
### run docker image at local
e.g. docker run -p 8080:8080 cho17bee2/springboot-koltin-gradle:latest

## on browser
http://localhost:8080/api/hello

## tag docker image
docker tag {image name} {Repositories}/{image name}:{tag name}
e.g. docker tag springboot-koltin-gradle cho17bee2/springboot-koltin-gradle:latest

## push the image to hub
docker push <image name> <version name>
e.g. docker push cho17bee2/springboot-koltin-gradle:latest

## after deploy to render
https://springboot-koltin-gradle-latest.onrender.com/api/hello