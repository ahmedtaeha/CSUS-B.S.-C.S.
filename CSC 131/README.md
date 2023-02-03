<a name="readme-top"></a>

<!-- PROJECT SHIELDS -->
<!--
*** Using MD thanks to https://github.com/othneildrew/Best-README-Template for the template
-->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">

  <h3 align="center">Team Blue </h3>
  <h2 align="center">Agent Call Service Application </h2>

  <p align="center"> 
Agent Call Service is a customer service application that provides call center supervisors the ability to monitor their agents. This application helps the supervisor stay organized and manage their time efficiently by monitoring a web page for changes. 
<br>
    <a href="https://github.com/ericjiang255/Team-Blue/issues">Report Bug</a>
    ·
    <a href="https://github.com/ericjiang255/Team-Blue/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
<li><a href="#to-do">Road Map</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About the Project

This project is a web-based application for state visualization of massive amounts of entities in real time. The project handles requests/entities via a REST API & renders real time changes to a client. The updates and render page are tied to a SQL database that stores agents/entities information. The use case of this application is for a human supervisor who will use a web browser to observe the state of thousands of agents/entities, each entity being represented as a colored dot. This application allows a human observer to derive a sense of the overall state and dynamics of the entity/agent population :smile:

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

-   [![Java][java.com]][java-url]
-   [![SQL][sql.com]][sql-url]
-   ![HTML][html.com]
-   ![CSS][css.com]
-   [![Bootstrap][bootstrap.com]][bootstrap-url]
-   [![JavaScript][javascript.com]][javascript-url]
-   [![JQuery][jquery.com]][jquery-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites

For building and running the application you need:

-   [JDK 1.8][java8]
-   [Maven 4][maven]
-   [mySQL][sql-dl]

### Installation

1. Clone the repo
    ```sh
    git clone hhttps://github.com/ericjiang255/Team-Blue.git
    ```
2. Change directory to where you have cloned the repository. Once you are in the directory of the cloned repository `cd "callservice/src/main/java/com/callservice/"`
3. There are several ways to run the Application on your local machine. One way is to execute the main method in the CallserviceApplication class from your IDE.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->

## Usage

### Documentation

https://app.swaggerhub.com/apis/J_2/Call-Service-Team-Blue/1.0.0#/

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## To-Do

### Backend

-   [x] REST API implementation
-   [x] Server Side Event to emit calls.
-   [x] Create SQL Models
-   [x] Integrate SQL with our frontend, so if client refreshes HTML changes stay persistent
-   [x] Tweak SSE to not time out, and make asynchronous code.

### Frontend

-   [x] Bootstrap fundamentals (think frontend got already?)
-   [x] Mockups to base ideas off of.
-   [x] Create Supervisor render page (view agents in grid, needs some work.)
-   [x] Integrate Vanilla JS to handle events that are emitted.

### Database

-   [x] Add an agent
-   [x] Update an agent
-   [x] Remove Agent
-   [x] Retrieve all active agents
-   [x] Filters for agents to retrieve if active, busy etc.
-   [x] Database should store for now ID, status & a full name & an updatedTime/currentTime field as well.
-   [ ] Handle UNIQUE attributes for when setting stringID
-   [ ] Clear database, clears existing after X hours

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT][license-url]

<!-- ACKNOWLEDGMENTS -->

## Acknowledgments

-   [Choose an Open Source License](https://choosealicense.com)
-   [Img Shields](https://shields.io)
-   [Best-README-Template](https://github.com/othneildrew/Best-README-Template)
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/ericjiang255/Team-Blue.svg?style=for-the-badge
[contributors-url]: https://github.com/ericjiang255/Team-Blue/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/ericjiang255/Team-Blue.svg?style=for-the-badge
[forks-url]: https://github.com/ericjiang255/Team-Blue/network/members
[stars-shield]: https://img.shields.io/github/stars/ericjiang255/Team-Blue.svg?style=for-the-badge
[stars-url]: https://github.com/ericjiang255/Team-Blue/stargazers
[issues-shield]: https://img.shields.io/github/issues/ericjiang255/Team-Blue.svg?style=for-the-badge
[issues-url]: https://github.com/ericjiang255/Team-Blue/issues
[license-shield]: https://img.shields.io/github/license/ericjiang255/Team-Blue.svg?style=for-the-badge
[license-url]: https://github.com/ericjiang255/Team-Blue/LICENSE.txt
[product-screenshot]: images/screenshot.png
[html.com]: https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white
[css.com]: https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white
[bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[bootstrap-url]: https://getbootstrap.com
[javascript.com]: https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black
[javascript-url]: https://www.javascript.com/
[jquery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[jquery-url]: https://jquery.com
[java.com]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white
[java-url]: https://www.java.com/en/
[sql.com]: https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white
[sql-url]: https://www.mysql.com/
[sql-dl]: https://dev.mysql.com/downloads/mysql/
[java8]: https://www.oracle.com/java/technologies/downloads/#java8
[maven]: http://maven.apache.org/POM/4.0.0
[best-readme-template]: https://github.com/othneildrew/Best-README-Template
