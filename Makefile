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
