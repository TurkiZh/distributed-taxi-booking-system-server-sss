#!/bin/bash
#
# Example usage:
# ./simulate_gps.sh \
#   -f simulate_coordinates_1.txt \ 
#   -t 151 -u john.smith -p password -i 
#

echo "Injecting GPS updates."

while getopts ":f:t:u:p:i:" o; do
    case "${o}" in
        f)
            file=${OPTARG}
            ;;
        t)
            taxi_id=${OPTARG}
            ;;
        u)
            username=${OPTARG}
            ;;
        p)
            password=${OPTARG}
            ;;
        i)
            interval=${OPTARG}
            ;;
        
        *)
            echo "Usage ${0} 
                        -f <simulator-data> 
                        -t <taxi-id> 
                        -u <username> 
                        -p <password>
                        -i <update-interval>"
            ;;
    esac
done

while IFS="," read -r lat lng 
do 
	echo $line
	echo "Sending GPS coordinates: ${lat},${lng} taxi:${taxi_id}."

    URL="http://localhost:8080/api/v1/taxi/${taxi_id}/location"
    AUTH_TOKEN=$(echo -n "${username}:${password}" | base64 )

	curl "${URL}" \
			-XPOST -H 'Content-Type: application/json' \
			-H "Authorization: Basic ${AUTH_TOKEN}" \
			--data-binary "{\"latitude\":${lat}, \"longitude\":${lng}}" 
	sleep ${interval}s
done < ${file}