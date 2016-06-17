
## Find a Ride: Project Overview

An Android client and Java server was developed for a final year project to enable a user to book a taxi and provide real-time updates on the status of the booking and the locations of taxis by integrating with the Google Maps Application Programming Interfaces (APIs). The locations of taxis were simulated.

The server provide RESTful web services for the pull-based interaction between the client and server for the stateless components and WebSockets for streaming taxi location updates events and Google Cloud Messenger (GCM) for pushed-based events such as taxi booking updates.

See [here](https://github.com/RobertNorthard/dtbs-android-client) for the Android moible client source code which is used to interface with the services provided by the server application.

Please see documentation folder for application design.

## Server Architecture

### Three tier overview

![GitHub Logo](/documentation/images/three-tier-architecture.png)

### Third-Party APIs

### Google

The Google Maps, directions and geocoding APIs were used for address revere lookups, route planning and calculating estimated arrival times.

