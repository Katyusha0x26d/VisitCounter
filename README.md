# VisitCounter

Markdown page visit counter

## Installation

### Docker

[Install Docker Engine](https://docs.docker.com/engine/install/) if you are using Linux 
or [install Docker Desktop](https://docs.docker.com/engine/install/) if you are using Microsoft Windows and prefer graphics user interface.

### Environment

Build the project, `.jar` file is at `build/libs`,

```shell
./gradlew bootJar
```

You can then launch the server via (prepare mariadb and redis first): 

```shell
java -jar build/libs/VisitCounter-{version}.jar
```

Or simply use docker-compose to deploy and launch: 

```shell
docker compose up -d --build
```

## Usage

Open `http://localhost:8080/count/svg/{resource}/{theme}.svg`, 
replace `resource` and `theme` with your own values, but resource
is required to be unique of each page.

> For example
> 
> http://localhost:8080/count/svg/Katyusha0x26d/for-the-badge_dark.svg

You can refer to this link in your markdown file:

```markdown
![counter](https://example.com/count/svg/page-123456/for-the-badge_dark.svg)
```

## Themes

![for-the-badge dark](src/main/resources/templates/for-the-badge_dark.svg "for-the-badge dark")

![for-the-badge light](src/main/resources/templates/for-the-badge_light.svg "for-the-badge light")