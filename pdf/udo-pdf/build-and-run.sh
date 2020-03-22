#!/usr/bin/bash
docker build --no-cache --tag udo-pdf:latest .
docker rm -f udo-pdf
docker run -p 127.0.0.1:8080:8080 --detach --name udo-pdf udo-pdf:latest
