import org.junit.runner.JUnitCore

val results = JUnitCore.runClasses(
  ...
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

