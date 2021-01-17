# Welcome to students-api

This repository shows the working example of how to design and build an API using the API First Design approach.
The Design First approach advocates for designing the API's contract first before writing any implementation/code.

Summary
-----------------
Open API spec File 'students-api.yaml' [located under 'src/main/resources']

Any modification to students-api.yaml file, necessitates the build of the project as per below instructions to
regenerate the API and Model classes. This is done using the swagger-codegen-maven-plugin.
REST Controller 'StudentsApiController' consumes these generated classes.

 
Instructions
-----------------

1. Clone this repository:

`git clone https://github.com/sainik73/students-api`

2. Build the project with Maven:

```
cd students-api/
mvn clean install
```

3. Run the spring boot application 'StudentsApplication'
4. Test the application using postman
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/04fec31e35710e9bdb41#?env%5Bstudents-api-local-env%5D=W3sia2V5IjoiYmFzZVVybCIsInZhbHVlIjoibG9jYWxob3N0OjgwODAiLCJlbmFibGVkIjp0cnVlfV0=)