# Android Log Data Server

|  | Current Status |
|--------|--------|
| Travis-CI | [![Build Status](https://travis-ci.org/AndroidLogData/Logdata-Server-Spring.svg?branch=develop)](https://travis-ci.org/AndroidLogData/Logdata-Server-Spring) |
| CircleCI | [![CircleCI](https://circleci.com/gh/AndroidLogData/Logdata-Server-Spring/tree/develop.svg?style=svg)](https://circleci.com/gh/AndroidLogData/Logdata-Server-Spring/tree/develop) |
| Coveralls | [![Coverage Status](https://coveralls.io/repos/github/AndroidLogData/Logdata-Server-Spring/badge.svg?branch=develop)](https://coveralls.io/github/AndroidLogData/Logdata-Server-Spring?branch=develop) |

안드로이드 로그 데이터를 관리하기 위한 서버

## Installation
```text
cd frontend
npm install
```
* npm 모듈을 다운받는다

### 개발용 서버
```text
cd frontend
npm run develop
```
```text
cd backend
gradle bootRun
```

* webpack-dev-server와 spring-boot서버를 동시 구동해야하기 때문에 터미널이 두개가 필요함
* localhost:3000에 접속하면 개발용 서버에 접속할 수 있다.
* react-hot-loader가 적용되어 있음

### 배포용 빌드
```text
cd frontend
npm run build
cd ..
cd backend
gradle bootRun
```

* ```backend/src/main/resources/static/react/bundle.js```가 생성됨

### 서버 실행
```gradle
gradle clean build
java -jar Logdata-Server-Api/build/libs/Logdata-Server-Api-{lasted version}-SNAPSHOT.jar
java -jar Logdata-Server-Web/build/libs/Logdata-Server-Web-{lasted version}-SNAPSHOT.jar
```

* localhost:8080에 접속하면 서버에 접속이 가능
* Spring Boot 에서 개발 되었음

## Development Stack
* Language : Java
* Database : MongoDB
* Server : Spring-Boot

## 사용된 오픈소스
* Java에서 시간 데이터를 얻기 위한 joda-time
	* https://github.com/JodaOrg/joda-time
* Test를 하기 위한 spring-security
	* https://github.com/spring-projects/spring-security
* Test Covarage를 보기 위한 jacoco
	* https://github.com/jacoco/jacoco
* Coveralls를 사용하기 위한 coveralls-gradle-plugin
	* https://github.com/kt3k/coveralls-gradle-plugin
* Spring을 쉽게 사용하기 위한 spring boot
	* https://github.com/spring-projects/spring-boot
* Thymeleaf의 부가 적인 도구
	* https://github.com/thymeleaf/thymeleaf-extras-springsecurity
* frontend의 js파일을 통합하기 위한 webpack
	* https://github.com/webpack/webpack
* 실시간으로 데이터를 보여주기 위한 react
	* https://github.com/facebook/react
* react.js에서 차트를 사용하기 위한 react-chartjs-2
	* https://github.com/jerairrest/react-chartjs-2
* react.js에서 json viewer를 위한 react-json-view
	* https://github.com/mac-s-g/react-json-view
* 개발 서버에서 실시간 업데이트를 위한 react-hot-loader
	* https://github.com/gaearon/react-hot-loader
* ES5를 사용하기 위한 babel
	* https://github.com/babel/babel

## License
```
MIT License

Copyright (c) 2018 AndroidLogData

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
