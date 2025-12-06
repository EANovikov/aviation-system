# Aviation System

● Accepts HTTP requests to fetch airport details using an ICAO code.

● Queries the public aviation data API at https://aviationapi.com to retrieve the data.

● The response format is up to you, but it should be clean and documented, containing key airport information (e.g., name, location, ICAO/IATA, etc.).

● Handles upstream API failures gracefully (timeouts, retries, rate limits, etc.).

## Airport API integration

sample query
https://api.aviationapi.com/v1/airports?apt=AVL,KAVL
https://api.aviationapi.com/v1/airports?apt=AVL