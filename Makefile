# defaul shell
SHELL = /bin/bash

# Rule "help"
.PHONY: help
.SILENT: help
help:
	echo " to build and push docker images"
	echo ""
	echo "help		      - show this message"

push-image:
	docker tag orderapi:latest jfrossetto/orderapi:latest
	docker push jfrossetto/orderapi:latest

build-image:
	./gradlew clean build
	docker rmi -f jfrossetto/orderapi:latest
	docker build --force-rm -t orderapi:latest .

kill-nones:
	docker images | grep none | awk '{print $3}' | xargs docker rmi
