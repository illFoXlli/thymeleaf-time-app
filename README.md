# Thymeleaf Time App

Java Servlet application that renders the current time with Thymeleaf and remembers the last valid timezone in the `lastTimezone` cookie.

## Implemented

- Thymeleaf is connected to the project.
- Time rendering is moved to HTML templates.
- `TimeServlet` calculates the current time for the requested timezone.
- `TimeServlet` passes data to the Thymeleaf template and returns rendered HTML.
- The last valid timezone from the `timezone` query parameter is saved to the `lastTimezone` cookie.
- If `timezone` is missing, the application tries to read `lastTimezone` from cookie.
- If neither query parameter nor cookie is available, the application uses `UTC`.
- Invalid timezone values return `400 Bad Request` with `Invalid timezone`.

## Technologies

- Java 21
- Maven
- Java Servlet API
- Thymeleaf
- Apache Tomcat 9

## Project Structure

```text
src/main/java/org/fox/servlet/IndexServlet.java
src/main/java/org/fox/servlet/TimeServlet.java
src/main/java/org/fox/filter/TimezoneValidateFilter.java
src/main/java/org/fox/service/TimeService.java
src/main/resources/templates/index.html
src/main/resources/templates/time.html
```

## Build

```bash
mvn package
```

Generated artifact:

```text
target/thymeleaf-time-app-1.0-SNAPSHOT.war
```

## Run

Deploy the WAR file to Apache Tomcat 9.

If Tomcat deploys the application with the default context path, use:

```text
http://localhost:8080/thymeleaf-time-app/
http://localhost:8080/thymeleaf-time-app/time
http://localhost:8080/thymeleaf-time-app/time?timezone=UTC+2
```

If the application is deployed as `ROOT`, use:

```text
http://localhost:8080/
http://localhost:8080/time
http://localhost:8080/time?timezone=UTC+2
```

## Example Scenario

```text
1. GET /time
   Response: current time in UTC

2. GET /time?timezone=UTC+2
   Response: current time in UTC+2
   Side effect: saves lastTimezone=UTC+2 in cookie

3. GET /time
   Response: current time in UTC+2 from cookie
```

## Invalid Timezone Example

```text
GET /time?timezone=test
```

Response:

```text
Invalid timezone
```

HTTP status:

```text
400 Bad Request
```
