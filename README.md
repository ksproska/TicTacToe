# TicTacToe
## Starting backend and frontend
Starting all services in [docker-compose.yaml](docker-compose.yaml).
```bash
docker compose up -d --build
```
Populating database:
```bash
make populate-db
```
Main page is available under [http://localhost:4200/home](http://localhost:4200/home).

Stopping all services:
```bash
docker dompose down
```

--------
## Założenia zadania
1. ~~Stwórz aplikację webową do gry w kółko i krzyżyk (3x3)~~, aplikacja powinna umożliwiać:
   1. Autentykację z wykorzystaniem AWS Cognito. 
   2. Grę z losowym przeciwnikiem 
   3. Podejrzenie globalnej listy graczy wraz z ich wynikami 
   4. Aplikacja powinna być zintegrowana z relacyjną bazą danych (Amazon RDS)
2. ~~Utwórz plik Dockerfile dla frontendu i backendu.~~
3. Utwórz plik docker-compose.yml dla konfiguracji całego środowiska.
4. Skonfiguruj VPC (Virtual Private Cloud) i ustal reguły bezpieczeństwa.
5. Wdróż aplikację za pomocą EC2 lub AWS Fargate.
6. Opisz cały proces w instrukcji

## Użyteczne linki:
- https://aws.amazon.com/codecommit/
- https://aws.amazon.com/pm/cognito/
- https://aws.amazon.com/rds/
- https://aws.amazon.com/blogs/opensource/using-a-postgresql-database-with-amazon-rds-and-springboot/
- https://aws.amazon.com/blogs/containers/optimize-your-spring-boot-application-for-aws-fargate/
- https://aws.amazon.com/blogs/containers/deploy-applications-on-amazon-ecs-using-docker-compose/
- https://medium.com/@abhishekranjandev/integrating-amazon-cognito-for-authentication-andauthorization-in-a-spring-boot-application-fe5fe7d78db
- https://mydeveloperplanet.com/2021/10/12/how-to-deploy-a-spring-boot-app-on-aws-fargate/
- https://richygreat.medium.com/create-virtual-private-cloud-vpc-in-aws-step-4-e303f21f48e8
- https://jrakibi.medium.com/deploy-your-application-with-ec2-docker-spring-boot-using-aws-clicb9f81260d29

## YouTube
- [How to integrate Java Spring Boot application with AWS Cognito using OIDC?](https://www.youtube.com/watch?v=o2IM9oI6Eqk)
- [AWS RDS with Spring Boot - A Step-By-Step Guide @ashokit](https://www.youtube.com/watch?v=GSu1g9jvFhY)
- [Deploy Spring boot application to AWS Cloud](https://www.youtube.com/watch?v=ua0cb2LjCW4)
- [How to Easily Deploy a Spring Boot Application to AWS EC2](https://www.youtube.com/watch?v=_vOInY6SRVE)
