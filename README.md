
# Find a Ride: Server Application

A Distributed Taxi Booking System server application for my final year software engineering project

An Android client and Java server was developed for a distributed taxi system to enable a user to book a taxi and provide real-time updates on the status of the booking and the locations of taxis by integrating with the Google Maps Application Programming Interfaces (APIs). The locations of taxis were simulated.

The server provide RESTful web services for the pull-based interaction between the client and server for the stateless components and WebSockets for streaming taxi location updates events and Google Cloud Messenger (GCM) for pushed-based events such as taxi booking updates.

## Server Architecture

### Three tier overview

![GitHub Logo](/documentation/images/three-tier-architecture.png)