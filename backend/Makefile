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

signup:
	curl -d '{"username":"kamila", "password": "pass"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/signup

see-users:
	curl http://localhost:8080/list-users
