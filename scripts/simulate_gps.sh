#!/bin/bash

echo "Starting simulator."

while IFS="," read -r lat lng 
do 
	echo $line
	echo "Sending updated GPS coordinates, ${lat}, ${lng} for taxi 51."
	curl 'http://localhost:8080/api/v1/taxi/51/location' -XPOST -H 'Content-Type: application/json' -H 'Authorization: Basic am9obi5zbWl0aDpwYXNzd29yZA==' --data-binary "{\"latitude\":${lat}, \"longitude\":${lng}}" 
	sleep 1s
done < simulate_coordinates.txt