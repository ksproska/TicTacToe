define create-env-file
rm -f domain.env
echo "BASE_URL=http://${1}:8080/" >> domain.env
echo "APP_API_SETTINGS_CROSS_ORIGIN_URLS=http://${1}" >> domain.env
echo "BASE_WEBSOCKET=ws://${1}:8080/websocket" >> domain.env
endef

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
	$(call create-env-file,localhost)
	docker compose up -d

stop:
	docker compose down

#domain_name = ec2-54-80-196-162.compute-1.amazonaws.com
test-if-domain-exists:
	curl $(domain_name):8080/healthcheck
	@echo

connect-to-aws: #test-if-domain-exists
	ssh -i ./secret/tictactoe-key-pair.pem ec2-user@$(domain_name)

send-docker-compose-and-env-file-to-lab: #test-if-domain-exists
	$(call create-env-file,$(domain_name))
	scp -i ./secret/tictactoe-key-pair.pem ./*.env ec2-user@$(domain_name):/home/ec2-user/
	scp -i ./secret/tictactoe-key-pair.pem ./docker-compose.yaml ec2-user@$(domain_name):/home/ec2-user/
