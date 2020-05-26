# :statue_of_liberty: :bar_chart: NycOpenData API Test Examples

Example API tests for datasets from [NYC Open Data](https://opendata.cityofnewyork.us).

Automated API tests are written in [Kotlin](https://kotlinlang.org) (script),
with [OkHttp](https://square.github.io/okhttp/) for HTTP requests, and 
[Moshi](https://github.com/square/moshi/) for strongly-typed JSON de-serialization. 

## Purpose

Proof of concept for using the experimental 
[scripting capabilities](https://kotlinlang.org/docs/tutorials/command-line.html#using-the-command-line-to-run-scripts)
of Kotlin to write test scripts (.kts files) in a faster, lighter weight way. 
Bringing the workflow of test development in Kotlin closer in line to the workflows I have
for test projects in Python and JavaScript. 

## Setup

```
# Download the Kotlin Main KTS .jar file and put it in your working directory
$ wget https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-main-kts/1.3.72/kotlin-main-kts-1.3.72.jar
```

