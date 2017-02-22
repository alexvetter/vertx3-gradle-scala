package com.example

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.http.HttpServer
import io.vertx.scala.ext.web.{Router, RoutingContext}

import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}

object HttpVerticle {

  val HTTP_HOST = "http_host"
  val HTTP_PORT = "http_port"

  val DEFAULT_HTTP_HOST = "localhost"
  val DEFAULT_HTTP_PORT = 8080
}

class HttpVerticle extends ScalaVerticle {

  var server: HttpServer = _

  override final def start(): Future[Unit] = {
    val p = Promise[Unit]()

    val router = Router.router(vertx)
    router.route("/").handler(routeRoot)

    val port = config.getInteger(HttpVerticle.HTTP_PORT, HttpVerticle.DEFAULT_HTTP_PORT)
    val host = config.getString(HttpVerticle.HTTP_HOST, HttpVerticle.DEFAULT_HTTP_HOST)

    vertx
      .createHttpServer()
      .requestHandler(router.accept)
      .listenFuture(port, host)
      .onComplete({
        case Success(startedServer) =>
          println(s"Server successfully started on port: $port")
          this.server = startedServer
          p.success(())
        case Failure(ex) =>
          println(s"Server failed to start on port: $port, b/c ${ex.getCause}")
          p.failure(ex)
      })

    p.future
  }

  private def routeRoot(ctx: RoutingContext) = {
    val currentThread = "Current thread " + Thread.currentThread().getId
    ctx.response().end(currentThread)
  }

  override final def stop(): Future[Unit] = {
    for {
      _ <- server.closeFuture()
      _ <- vertx.undeployFuture(this.deploymentID)
    } yield ()
  }
}