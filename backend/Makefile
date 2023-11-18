run-db-container:
	docker run \
	--name db \
	--rm \
	-e MYSQL_ROOT_PASSWORD=password \
	-e MYSQL_DATABASE=tictactoe \
	-e MYSQL_USER=mysql \
	-e MYSQL_PASSWORD=password \
	-p 3306:3306 \
	mysql

create-user:
	curl -d '{"username":"kamila", "password": "pass"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/create-user

see-users:
	curl http://localhost:8080/list-users
