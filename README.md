# Android Log Data Server

| distribute | Current Status |
|--------|--------|
| Travis-CI | [![Build Status](https://travis-ci.org/AndroidLogData/Logdata-Server-Spring.svg?branch=develop)](https://travis-ci.org/AndroidLogData/Logdata-Server-Spring) |
| CircleCI | [![CircleCI](https://circleci.com/gh/AndroidLogData/Logdata-Server-Spring/tree/develop.svg?style=svg)](https://circleci.com/gh/AndroidLogData/Logdata-Server-Spring/tree/develop) |

안드로이드 로그 데이터를 관리하기 위한 서버

## Installation
```gradle
gradle clean build
java -jar build/libs/(jar 파일명.jar)
```

* Spring Boot 에서 개발 되었음

## Development Stack
* Language : Java
* Database : MongoDB
* Server : Spring-Boot

## 사용된 오픈소스
* Java에서 시간 데이터를 얻기 위한 joda-time
	* https://github.com/JodaOrg/joda-time

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