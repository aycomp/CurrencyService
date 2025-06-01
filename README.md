# Currency Exchange Rest Service

This project is a Spring Boot application for currency conversion and exchange rate queries.

## Features

- Get live exchange rates
- Currency conversion between multiple currencies
- Upload file to process batch currency conversions
- Unit tested with JUnit and Mockito

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+

### Running the Application

```bash
mvn spring-boot:run
```

### Running Tests

```bash
mvn test
```

## Project Structure

- `src/main/java` - Application sources
- `src/test/java` - Unit tests

## Usage

- Start the application and access endpoints as described in your API documentation or controller classes.
- To process currency conversions from a file, upload a CSV with the format:
  ```
  amount;source;target
  100;USD;TRY
  ```
## Postman Collection

A Postman collection for testing the API is included in the `postman` directory:
- [postman/CurrencyService.postman_collection.json](postman/CurrencyService.postman_collection.json)

## API Documentation

API documentation with request and response examples is provided in [API_DOCUMENTATION.md](API_DOCUMENTATION.md).

## Notes

- A 1-second delay (`Thread.sleep(1000)`) was added after each API call due to trial mode limitations, to avoid repeated request errors during demos.


## License

This project is for educational purposes.


## Author

Developed by Ayse Ozdemir