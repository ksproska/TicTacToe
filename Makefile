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
	curl -d '{"username":"kamila", "password": "pass"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/create-user
	curl -d '{"username":"szymon", "password": "pass"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/create-user
	curl -d '{"username":"agnieszka", "password": "pass"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/create-user
	curl -d '{"player1": {"id": 1}, "player2": {"id": 2}, "winner": {"id": 1}}' -H "Content-Type: application/json" -X PUT http://localhost:8080/create-game
	curl -d '{"player1": {"id": 1}, "player2": {"id": 2}, "winner": {"id": 1}}' -H "Content-Type: application/json" -X PUT http://localhost:8080/create-game
	curl -d '{"player1": {"id": 1}, "player2": {"id": 2}, "winner": {"id": 1}}' -H "Content-Type: application/json" -X PUT http://localhost:8080/create-game
	curl -d '{"player1": {"id": 1}, "player2": {"id": 2}, "winner": {"id": 2}}' -H "Content-Type: application/json" -X PUT http://localhost:8080/create-game
	curl -d '{"player1": {"id": 2}, "player2": {"id": 3}, "winner": {"id": 2}}' -H "Content-Type: application/json" -X PUT http://localhost:8080/create-game
