window.onload = function () {

    var mapOptions = {
        zoom: 14,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("googleMap"), mapOptions);
    var taxis = [];

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(geolocationSuccess);

        function geolocationSuccess(position) {
            var location = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            map.setCenter(location);
        }

        function setMapOnAll(map) {
            for (var i = 0; i < taxis.length; i++) {
                if (taxis[i]['id'] !== "delete") {
                    taxis[i].location.setMap(map);
                }
            }
        }

        function clearMarkers() {
            setMapOnAll(null);
        }


        var connection = new WebSocket('ws://localhost:8080/ws/v1/locations/taxi/all');

        connection.onopen = function () {
            console.log("Connected to socket.");
        };

        connection.onclose = function () {
            console.log('Socket connection closed.');
            connection.close();
        }

        connection.onerror = function (error) {
            console.log('Error detected: ' + error);
        }


        connection.onmessage = function (e) {
            console.log(e.data);
            var div = document.getElementById('location_events');
            var obj = JSON.parse(e.data);

            var taxiId = obj['taxi_id'];
            var locationTimestamp = new Date(obj['timestamp'])

            var location = {
                lat: obj['location']['latitude'],
                lng: obj['location']['longitude']
            };



            var found = false;
            for (var i = 0; i < taxis.length; i++) {
                if (taxis[i]['id'] === taxiId) {
                    taxis[i].location.setMap(null);
                    var marker = new google.maps.Marker({
                        position: location,
                        map: map,
                        icon: "resources/images/img_taxi_icon.png"
                    });
                    taxis[i].location = marker;
                    found = true;

                }
            }

            if (!found) {
                var marker = new google.maps.Marker({
                    position: location,
                    map: map,
                    icon: "resources/images/img_taxi_icon.png"
                });
                var taxi = {
                    id: taxiId,
                    location: marker
                };

                taxis.push(taxi);
            }


        };


    } else {
        alert("This browser doesn't support geolocation.");
    }
}