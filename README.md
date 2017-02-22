# vertx-lang-scala with Gradle

This is a very simple example to show how to get goin' with Vert.x (_3.4.0.Beta1_), Scala (_2.12.1_) and Gradle (_3.4_).
It contains two simple `ScalaVerticle` written in Scala and based on [`vertx-lang-scala`](https://github.com/vert-x3/vertx-lang-scala):

1. `com.example.Starter`
2. `com.example.HttpVerticle`

The `Starter` will deploy the `HttpVerticle`.
And the `HttpVerticle` will obviously start a HTTP server running on `8080` by default.

## Running it with Gradle

```
./gradlew run
```

It's as simple as that. The responsible part in the `build.gradle` is also fairly simple:

```
mainClassName = "io.vertx.core.Launcher"
def mainVerticle = "scala:com.example.Starter"

run {
  args = ["run", mainVerticle]
}
```

## Building a fat JAR

This example uses [Gradle Shadow](https://github.com/johnrengelman/shadow) to build a fat JAR.

```
./gradlew jar
```

... or `./gradlew build` which will also run the `jar` task.
The resulting fat JAR will be located at `build/libs/vertx3-gradle-scala-fat.jar`.