# Catalog Multi-Module Prototype

A three-module Maven prototype with a shared OpenAPI contract, Spring Boot server, and generated Spring HTTP Interface consumer integration.

## Contents

- `catalog-contract/src/main/resources/catalog-api.yaml` — OpenAPI 3.0.3 contract.
- `catalog-server/src/main/java/com/example/catalog/server/CatalogServerApplication.java` — server entry point.
- `catalog-server/src/main/java/com/example/catalog/server/CatalogController.java` — `GET /api/catalog/items/{id}`; `demo` returns JSON, other IDs return 404.
- `catalog-consumer/src/main/java/com/example/catalog/consumer/CatalogClientAutoConfiguration.java` — auto-configuration that imports the generated `com.example.catalog.client.api.DefaultApi` and creates its HTTP Interface proxy with `HttpServiceProxyFactory`.
- `catalog-consumer/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` — registers `CatalogClientAutoConfiguration`.

The generated client is intentionally not committed. The consumer POM runs OpenAPI Generator in `generate-sources`, using the official `generatorName=spring`, `library=spring-http-interface`, `interfaceOnly=true`, API package `com.example.catalog.client.api`, and model package `com.example.catalog.client.model`. With this contract (no operation tags), the generated API interface is expected to be `DefaultApi`; the auto-configuration imports that exact name.

## Build profiles and ports

Boot 3 is the default: Java 17 / Spring Boot 3.5.6. Boot 4 is opt-in: Java 21 / Spring Boot 4.0.0. The server profile selects the corresponding web starter and Boot Maven plugin.

```bash
mvn -Pboot3 clean verify
mvn -Pboot3 -pl catalog-server spring-boot:run
# In a second shell, run the consumer against the server:
mvn -Pboot3 -pl catalog-consumer spring-boot:run -Dspring-boot.run.arguments="--catalog.client.base-url=http://localhost:8080"

mvn -Pboot4 clean verify
mvn -Pboot4 -pl catalog-server spring-boot:run
```

The server defaults to port 8080 and accepts `SERVER_PORT`, for example:

```bash
SERVER_PORT=8081 mvn -Pboot3 -pl catalog-server spring-boot:run
# In a second shell, run the consumer against the server:
mvn -Pboot3 -pl catalog-consumer spring-boot:run -Dspring-boot.run.arguments="--catalog.client.base-url=http://localhost:8080"
curl -i http://localhost:8080/api/catalog/items/demo
```

The consumer base URL is configurable with `catalog.client.base-url` and defaults to `http://localhost:8080`. The consumer module is also runnable as a standalone Boot application for a smoke test; it starts `CatalogConsumerApplication` and exposes the generated `DefaultApi` bean. Set `catalog.client.base-url` to the server URL.

## Validation status

The archive layout, ZIP integrity, XML parsing, source presence, profile values, generator options, API name alignment, port settings, and auto-configuration registration were statically checked. Maven/JDK execution was not available in the review sandbox, so dependency resolution, OpenAPI generation, compilation, profile builds, startup, and the HTTP smoke test remain untested. Static repair verified the official Spring generator setting, contract path, profile-specific Boot 3/4 generator flags, generated-source wiring, and runnable consumer main class.
