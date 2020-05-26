@file:DependsOn("junit:junit:4.13")
@file:DependsOn("org.hamcrest:hamcrest:2.2")
@file:DependsOn("com.squareup.okhttp3:okhttp:4.7.2")
@file:DependsOn("com.squareup.moshi:moshi:1.9.2")
@file:DependsOn("com.squareup.moshi:moshi-kotlin:1.9.2")

@file:Import("NycOpenDataTest.kts")
@file:Import("OpenStreet.kts")
@file:Import("NYC.kts")

import org.junit.runner.JUnitCore

val results = JUnitCore.runClasses(
  NycOpenDataTest.OpenStreetTest::class.java
)

println(
  "Ran ${results.getRunCount()} tests " +
  "in ${results.getRunTime() / 1000.0} seconds " +
  "with ${results.getFailureCount()} failures."
)

if (results.getFailureCount() > 0) {
  results.getFailures().forEach {
    println(it.getMessage())
    println(it.getException())
  }
}

