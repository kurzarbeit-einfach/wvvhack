#!/usr/bin/bash
docker rm -f udo-pdf
docker run -p 127.0.0.1:8080:8080 --volume /var/log/udo:/usr/app/log:Z --detach --name udo-pdf udo-pdf:latest
