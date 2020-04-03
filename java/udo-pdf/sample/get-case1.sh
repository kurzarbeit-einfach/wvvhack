#!/usr/bin/bash
curl -X POST -H "Content-Type: application/json" -d @./case1.json http://localhost:8080 > out.pdf
