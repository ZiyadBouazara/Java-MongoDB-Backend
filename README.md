# Restalo

Restalo is an API for managing restaurant reservations. It provides functionalities for creating and managing
reservations, searching for restaurants and reservations, and reviewing restaurants.

[![Java](https://img.shields.io/badge/java-21-blue)](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
[![junit](https://img.shields.io/badge/junit-4.13.2-blue)](https://junit.org/junit4/)
[![Maven Central](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven.svg)](https://maven.apache.org)
[![Known Vulnerabilities](https://snyk.io/test/github/samiusss/restalo/badge.svg)](https://snyk.io/test/github/samiusss/restalo)
[![GitHub Actions](https://github.com/samiusss/restalo/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/samiusss/restalo/actions)
[![Checkstyle](https://img.shields.io/badge/checkstyle-8.45-blue)](https://checkstyle.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing
purposes.

### Prerequisites

- Java 21
- Maven 3.x

### Installation

Clone the repository:

```bash
git clone https://github.com/username/restalo.git
cd restalo
```

Build the project:

```bash
mvn clean install
```

Run the project:

```bash
mvn spring-boot:run
```

Docker build image

```bash
docker build -t restalo .
```

Docker run image

```bash
docker compose up -d
```

Docker stop image

```bash
docker compose down -v
```

## Open Source files

For more information about the project and how to contribute, please refer to the following files:

- [CONTRIBUTING](CONTRIBUTING.md)
- [CODE_OF_CONDUCT](CODE_OF_CONDUCT.md)
- [LICENSE](LICENSE)

## License

This project is lisenced under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

Thanks to all contributors who participated in this project and helped make it better.
