start:
	docker compose up -d --build

start-dev:
	docker compose -f ./docker-compose-dev.yaml up -d --build

stop:
	docker compose down

stop-dev:
	docker compose -f ./docker-compose-dev.yaml down
