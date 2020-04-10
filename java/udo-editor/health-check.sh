status=$(curl http://localhost:8090/actuator/health 2>/dev/null | jq .status -r)
if [ "$status" == "UP" ]; then
	exit 0	
else
	exit 1
fi
