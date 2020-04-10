#!/usr/bin/bash
docker build --tag udo-editor:latest .
docker rm -f udo-editor
docker run -p 8080:8080 -p 8090:8090 --detach --name udo-editor udo-editor:latest
