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
# on Mac OS, install Kotlin with Homebrew
$ brew install kotlin
```

## Run
```
$ kotlinc -script TestRunner.main.kts
```

## Tests

The tests are defined in **NycOpenDataTest.kts**. The test methods make requests to the "Open Streets" dataset,
tracking which streets are currently closed to car traffic, to allow for more space for walking.

**TestRunner.main.kts** contains a minimal test runner using [JUnitCore](https://junit.org/junit4/javadoc/latest/org/junit/runner/JUnitCore.html).
The file also contains special annotations like "@file:DependsOn" to specify 3rd-party dependencies, and
import the other .kts script files together into a shared environment using "@file:Import".

When you import a .kts script file with "@file:Import", the name of the file becomes the "package" namespace for
the contents of the file:

```
# Urls.kts
object DepartmentOfBuildings {
  val API = ...
}
==============================
# Runner.main.kts
@file:Import("Urls.kts")
Urls.DepartmentOfBuildings.API
...
```

## .main.kts

In order to use the conveniences offered by Kotlin scripting, at least one of the script files **must**
have the file extension ".main.kts". This special extension signals to Kotlin to apply the 
special scripting environment to the script. 

## Benefits

By specifying the project dependencies at the top of a script file, you can skip the usual
lengthy rituals of writing / generating a build script and dealing with the additional complexity
of a build tool.

By executing the Kotlin code as a script, you can skip the _Compile_ step of your usual 
workflow entirely, and just focus on writing and running working code, at a faster pace.

## Caveats

Scripting functionality is still experimental (as of version 1.3) and there are a few traps to watch for. 

Since Kotlin is not quite optimized for the scripting scenario, the start time of a script 
is noticeably slow on the first run, as you wait for the JVM to load up the many components 
of a Kotlin runtime environment. It's not quite the "get-up-and-go" experience of other
scripting environments. 

Every time you execute a .kts script file, the Kotlin compiler does still run on your code 
behind the scenes. The resulting .class files are generated and cached in a .jar in a random
temporary directory. Due to some bugs with the way Kotlin handles that cached output, 
sometimes the scripting runtime will **refuse** to update those .class files after you 
change the related code. The only work-around I have right now is to manually find
the cache location and delete the .jar of stale byte-code: 

```
# open up Kotlin REPL to find temporary directory with cached .jar
$ kotlin
>>> val tmpdir = System.getProperty("java.io.tmpdir")
>>> val paths = java.nio.file.Files.newDirectoryStream(tmpdir + "main.kts.compiled.cache", "*.jar")
>>> paths.forEach { it.toFile().delete() }
```

