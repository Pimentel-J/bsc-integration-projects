# 1. Overview
The Intermunicipal Transport Authority (ITA) wants a management and planning system for public transport that allows the management as well as consultation by the general public of different transport networks, lines and trips, as well as the planning of the services of vehicles and crew to be performed on these lines.

The system must be composed of a **Single Page Application (SPA)** web application that allows authorized users to access the different modules of the application, as well as a set of services that implement the business rules components necessary for the operation of the web application.

## 1.1. Main Features of Each Module of the System

* **Master Data** - allows the management of information related to the network (nodes, routes), vehicle types, crew types, lines and trips.
* **Planning** - plan crew exchanges at surrender points based on existing routes. Planning the crew services based on the vehicle services. Consumes the information managed in the master data module and publishes planning information to the visualization module.
* **Visualizer** - allows 2D and 3D network visualization, scene navigation and graphical querying of trip information. It consumes the information managed in the master data module and the planning module.
* **UI** - user interface
* **Customers + GDPR** - management of end-user "customer" information and their consents under the GDPR.
Although it is not in the current scope of the project, future extension to mobile applications should be taken into account in the solution architecture. The solution should contemplate three types of users:

## 1.2. Types of Users

* **Data Administrators** - use the master data modules.
* **Client** - registers and uses the view module.
* **Manager** - uses the planning module.
