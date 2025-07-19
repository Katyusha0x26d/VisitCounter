# VisitCounter

Markdown page visit counter

![counter](https://counter.katyusha.me/count/svg/VisitCounter/for-the-badge_dark.svg)

## Installation

### Docker

[Install Docker Engine](https://docs.docker.com/engine/install/) if you are using Linux 
or [install Docker Desktop](https://docs.docker.com/engine/install/) if you are using Microsoft Windows and prefer graphics user interface.

### Build and Deploy

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
docker-compose up -d --build
```

## Usage

Open `http://localhost:8080/count/svg/{resource}/{theme}.svg`, 
replace `resource` and `theme` with your own values, but resource
is required to be unique of each page.

> For example
> 
> http://localhost:8080/count/svg/Katyusha0x26d/for-the-badge_dark.svg

You can use links like this in your markdown file:

```markdown
![counter](https://example.com/count/svg/page-123456/for-the-badge_dark.svg)
```

## Themes

| ![for-the-badge dark](src/main/resources/templates/for-the-badge_dark.svg "for-the-badge dark")  | ![for-the-badge light](src/main/resources/templates/for-the-badge_light.svg "for-the-badge light") |
|--------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| for-the-badge dark                                                                               | for-the-badge light                                                                                |

## Available Servers

Currently these servers are public and free to use:

- [Katyusha0x26d's Server](https://counter.katyusha.me/count/svg/Katyusha0x26d/for-the-badge_dark.svg)

## Theme Development

This project uses XML SVG and Thymeleaf template, to develop your own theme,
edit `src/main/resources/templates/your-theme_color.svg`, for example:

```svg
<svg xmlns="http://www.w3.org/2000/svg"
     xmlns:th="http://www.thymeleaf.org"
     width="200" height="30" viewBox="0 0 200 30">
    <rect x="0%" y="0%" width="50%" height="100%" fill="#21262d"/>
    <rect x="50%" y="0%" width="50%" height="100%" fill="#161b22"/>
    <text x="25%" y="50%" font-size="14" font-family="Ubuntu Mono" fill="#7ce38b" text-anchor="middle" dominant-baseline="middle" font-weight="bold" letter-spacing="3">
        VISITORS
    </text>
    <text th:text="${#numbers.formatInteger(count, 8)}" x="75%" y="50%" font-size="14" font-family="Ubuntu Mono" fill="#7ce38b" text-anchor="middle" dominant-baseline="middle" font-weight="bold" letter-spacing="3">
        01234567
    </text>
</svg>
```

Notice that this is not normal SVG file and there is a thymeleaf text attribute `th:text="${#numbers.formatInteger(count, 8)}"`,
you can customize your own svg styles and integer display formats, but you need to keep the `count` variable.

## Contribution

You can contribute to this project in any way, for example you can fix bugs, contribute new themes, 
or simply contribute publicly available servers.

## License

This project uses `the MIT License`

```text
MIT License

Copyright (c) 2025 Maxwell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```