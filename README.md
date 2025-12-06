# aviation-system
Demonstrates how to use a 3rd party system efficiently: focused on scalability, resilience, extendibility and observability

## Read More About the Aviation API Service
### Features and requirements
[aviation-api](aviation-api/README.md)

## Setup and run

### Prerequisites
- Docker installed on your machine [docker](https://docs.docker.com/get-started/get-docker/)
- Clone the repository
  ```bash
  git clone git@github.com:EANovikov/aviation-system.git
  
### Running the Service
- go to the project directory, i.e:
  ```bash
  cd aviation-system
  ```
- The application and monitoring services can be run with Docker Compose
[docker-compose](https://docs.docker.com/compose/gettingstarted/)
- Build the Docker containers with Docker Compose
  ```bash
  docker-compose build
  ```
- Run the Docker containers
  ```bash
  docker-compose up -d
  ```
- The service will be accessible at `http://localhost:8092`
- To interact with aviation-api service run the test call in your browser: 
  ```http
  GET  http://localhost:8092/v1/airports/KJFK
  ``` 
  http://localhost:8092/v1/airports/KJFK
- Prometheus will be accessible at `http://localhost:9090`
- Grafana dashboard will be accessible at `http://localhost:3000` (default login: admin/admin). To explore the data go to: Home > Connections > Data sources
- To stop the services, run:
  ```bash
  docker-compose stop
  ```
- To remove the containers, run:
  ```bash
  docker-compose down
  ```
  
## Testing
### Running Tests Locally
#### Prerequisites
- Git Bash [gitbash](https://git-scm.com/install/)
- Clone the repository
  ```bash
  git clone git@github.com:EANovikov/aviation-system.git
  ```
- Install JDK 25 [jdk](https://jdk.java.net/25/) and update Path environment variable
- Maven installed on your machine [maven](https://maven.apache.org/install.html) and update Path environment variable

#### Run Tests
- Go to the project directory, i.e:
  ```bash
  cd aviation-system/aviation-api
    ``` 
- Run the tests with Maven
- ```bash
  mvn clean test
  ```
#### To run application locally without Docker
- Go to the project directory, i.e:
-  ```bash
  cd aviation-system/aviation-api
    ```     
- Build the application with Maven
   ```bash
  mvn clean install
    ```
- Go to the target directory
   ```bash
  cd aviation-api-servic/target
    ```
- Run the application
  ```bash
  java -jar aviation-api-service-0.0.1-SNAPSHOT.jar
    ```
- The actuator health endpoint will be accessible at `http://localhost:8092/actuator/health`
- API endpoint will be accessible at `http://localhost:8092/v1/airports/KJFK`

## Architecture & Design
### Overview
The Aviation System is designed to provide reliable and efficient access to airport details using ICAO codes. The system integrates with the public aviation data API at https://aviationapi.com to fetch the required information. The architecture focuses on scalability, resilience, extendibility, and observability.    
### Components
- aviation-api service: A Spring Boot application that handles HTTP requests, interacts with the aviation API, and processes responses. It exposes RESTful endpoints for clients to fetch airport details: /v1/airports/{icao}
- prometheus: is an open-source monitoring and alerting toolkit, widely used in cloud-native environments, that collects metrics
- grafana: is an open-source platform for monitoring and observability that allows you to visualize and analyze metrics collected from various sources, including Prometheus
- Loki monitoring is the process of tracking and analyzing the performance and health of a Loki log aggregation system. It involves using tools like Prometheus and Grafana to collect and visualize metrics from Loki
- Alloy: Grafana Alloy collects, processes, and exports telemetry signals 

### Observability
- The system incorporates observability features to monitor its performance and health. Prometheus is used to collect metrics, Loki is used to collect logs, Alloy gathers the monitoring data, while Grafana provides a dashboard for visualizing these metrics. Key metrics include request latency, error rates, and upstream API response times.
- Grafana dashboard provides access to the metrics: http://localhost:3000 (see 'Running the Service' section)
### Resilience
  - The system is designed to handle upstream API failures gracefully. It implements timeouts and retries at RetryableAirportClient
  - Additionally RetryableAirportClient uses caching to reduce the number of requests to the upstream API, improving performance and reducing the impact of rate limits
### Scalability & Extendibility
- airport-client-dto is a separate module which builds DTO classes for the airport-client service. This modular approach allows for easy extension and maintenance of the codebase
- airport-client-dto module utiled openAPI generator to generate DTO classes based on the OpenAPI specification provided by the aviation API. This ensures that the DTOs are always up-to-date with the latest API changes and reduces manual coding effort
- airport-client is a feign client module that handles communication with the aviation API. It is designed to be easily extendable, allowing for the addition of new features or modifications to existing functionality without impacting other parts of the system
- aviation-api service is the main module which uses Spring Boot, exposing RESTful endpoints and handling business logic. It can be configured with application properties, making it adaptable to different environments and requirements
- the whole system is containerized using Docker, allowing for easy deployment and scaling across different environments. Docker Compose is used to manage multi-container applications, simplifying the orchestration of the various components