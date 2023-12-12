start-dev:
	docker compose -f ./docker-compose-dev.yaml up -d --build

stop-dev:
	docker compose -f ./docker-compose-dev.yaml down

build-frontend-image:
	docker build -t ksproska/tictactoe-aws-frontend ./frontend

build-backend-image:
	docker build -t ksproska/tictactoe-aws-backend ./backend

build-images: build-frontend-image build-backend-image

push-images: build-images
	docker push ksproska/tictactoe-aws-frontend
	docker push ksproska/tictactoe-aws-backend

start: build-images
	docker compose up -d --build

stop:
	docker compose down
