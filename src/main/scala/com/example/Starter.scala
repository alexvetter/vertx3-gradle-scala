package com.example

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.DeploymentOptions

import scala.concurrent.Future

class Starter extends ScalaVerticle {

  override final def start(): Future[Unit] = {
    val httpOptions = DeploymentOptions()
      .setConfig(config)
      .setInstances(Runtime.getRuntime.availableProcessors())

    vertx.deployVerticleFuture("scala:com.example.HttpVerticle", httpOptions).map(_ => ())
  }

  override final def stop(): Future[Unit] = {
    vertx.undeployFuture(this.deploymentID).map(_ => ())
  }
}
