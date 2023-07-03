# Welcome to students-api
![students-api Maven Build](https://github.com/sainik73/students-api/workflows/students-api%20Maven%20Build/badge.svg)

This repository shows the working example of how to design and build an API using the API First Design approach.
The Design First approach advocates for designing the API's contract first before writing any implementation/code.

Summary
-----------------
Open API spec File 'students-api.yaml' [located under 'src/main/resources']

Any modification to students-api.yaml file, necessitates the build of the project as per below instructions to
regenerate the API and Model classes. This is done using the swagger-codegen-maven-plugin.
REST Controller 'StudentsApiController' consumes these generated classes.

Version
----------------
v1.0: 
The repository now supports Mongo DB as storage.
students-api repository connects to Mongo DB, which is run as a Docker container.
Java Logs (students-api-logger.log) are created under project root/logs directory. 
 
v2.0:
The repository now supports spring boot application and MongoDB deployment to Kubernetes local cluster.
MongoDB credentials are stored in a secret file.

v2.1:
The repository now supports application builds using helm charts.
 
Instructions
-----------------
#### v1.0 Build Instructions:
1. Clone this repository:

`git clone https://github.com/sainik73/students-api`

2. Checkout branch v1.0
3. Run Mongo docker instance by following instructions in mongo-data\RunMongo.txt file
4. Build the project with Maven:

```
> cd students-api/
> mvn clean install
```
5. Run the spring boot application 'StudentsApplication'
6. Test the application using postman [![Run in Postman](https://run.pstmn.io/button.svg)](https://god.postman.co/run-collection/04fec31e35710e9bdb41#?env%5Bstudents-api-local-env%5D=W3sia2V5IjoiYmFzZVVybCIsInZhbHVlIjoibG9jYWxob3N0OjgxODAiLCJlbmFibGVkIjp0cnVlfV0=)

#### v2.0 Build Instructions:
1. Clone this repository:

`git clone https://github.com/sainik73/students-api`

2. Checkout branch v2.0
3. Build the project with Maven:

```
> cd students-api/
> mvn clean install
```
The build will output a docker image.

4. Add image to minikube

4.1 Delete any existing image from minikube cache
```
> minikube cache delete <image-name>
```
4.2 Enter interactive terminal to minikube and remove image from docker in minikube
```
> docker exec minikube bash
> docker image rm <image-name>
> exit
```
4.3 Add image to minikube cache
```
> minikube cache add <image-name>
```
5. Setup Kubernetes resources (deployment, services etc)

5.1 Change directory to "k8s" in terminal [Its located under <project-root>/src/main/resources]
```
> kubectl apply -f .
```
6. Start service on windows host [It will expose port on host to access service]
```
> minikube service students-api
```
7. Test application 
 
7.1 Modify the postman environment for exposed port

7.2 Run postman collection [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/04fec31e35710e9bdb41#?env%5Bstudents-api-local-env%5D=W3sia2V5IjoiYmFzZVVybCIsInZhbHVlIjoibG9jYWxob3N0OjgxODAiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoidGV4dCJ9XQ==)


### v2.1 Build Instructions:
1. Clone this repository:

`git clone https://github.com/sainik73/students-api`

2. Checkout branch v2.1
3. Build the project with Maven:

```
> cd students-api/
> mvn clean install
```
The build will output a docker image.

4. Install helm repo
```
> helm repo add bitnami https://charts.bitnami.com/bitnami
```
5. Update helm chart dependencies and Install helm chart [Its located under <project-root>/src/main/resources]

```
> cd students-api/src/main/resources/helm
> helm dependency update students-api
> helm install students-api students-api
```
6. Start service on windows host [It will expose port on host to access service]
```
> minikube service students-api
```
7. Test application 
    
7.1 Modify the postman environment for exposed port
   
7.2 Run postman collection [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/04fec31e35710e9bdb41#?env%5Bstudents-api-local-env%5D=W3sia2V5IjoiYmFzZVVybCIsInZhbHVlIjoibG9jYWxob3N0OjgxODAiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoidGV4dCJ9XQ==)


Known Issues
-----------------
Version v2.0:
1) Constraints defined in yaml are not validated for request payload because of open issue (<a href= "https://github.com/swagger-api/swagger-codegen/issues/7058">#7058</a>) 
in swagger code gen plugin v 3.0.24
