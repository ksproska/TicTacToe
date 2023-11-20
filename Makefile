nvm-setup:
	nvm list available
	nvm install 20.9.0
	nvm use 20.9.0
	# Now using node v20.9.0 (npm v10.1.0)

npm-install:
	npm install -g @angular/cli
	npm install -g npm@10.2.3
	npm install

create-new:
	ng new tic-tac-toe

populate-db:
	curl -d '{"username":"kamila", "password": "pass"}' -H "Content-Type: application/json" -X POST http://localhost:8080/signup
	curl -d '{"username":"szymon", "password": "pass"}' -H "Content-Type: application/json" -X POST http://localhost:8080/signup
	curl -d '{"username":"agnieszka", "password": "pass"}' -H "Content-Type: application/json" -X POST http://localhost:8080/signup

get-game:
	curl -d '{"id": 2}' -H "Content-Type: application/json" -X PUT http://localhost:8080/get-game
	curl -d '{"id": 2}' -H "Content-Type: application/json" -X PUT http://localhost:8080/get-game
	curl -d '{"id": 3}' -H "Content-Type: application/json" -X PUT http://localhost:8080/get-game
