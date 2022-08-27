# app-mailbook-tests

Java + Maven + TestNG + Selenium WebDriver + Docker + Selenoid + Allure

## Running with [docker-compose](https://www.docker.com/) + [Selenoid](https://aerokube.com/)

1. Install [Docker](https://www.docker.com/);
2. For using video recording you should set the directory with absolute path for storing video in `docker-compose.yml` file:

```
  environment:
    - OVERRIDE_VIDEO_OUTPUT_DIR=\your\absolute\path\app-mailbook-tests\selenoid\video
```

3. For configuring browsers you can change parameter `Dbrowser` in `docker-compose.yml` file. The available options are `chrome`, `firefox` for.

```
  tests_runner:
    image: maven:latest
    container_name: tests_works
    network_mode: host
    working_dir: /tests
    volumes:
      - ./:/tests/
      - /var/run/docker.sock:/var/run/docker.sock
      # Maven cache (optional)
      - ~/.m2:/root/.m2
      - ./allure-results/:/tests/allure-results/
    command: mvn test -q -Dbrowser=chrome -DdriverType=remote -Dit.test=ContactSuite
```

4. Open the project directory and run docker-compose:
```
docker-compose up --build
```
Open http://localhost:8080/ and wait while Maven downloads the dependencies and runs the tests.

https://user-images.githubusercontent.com/36141536/187022851-9d304693-7556-4fea-b5ea-876a2254a75f.mp4
