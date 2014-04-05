package me.atomd.turbine

import akka.actor.ActorSystem

object Turbine extends App {
  val system = ActorSystem("turbine")
  system.actorOf(util.UnhandledLogger.props(), "unhandledlogger")

  system.actorOf(core.Controller.props(), "controller")
  readLine("Hit ENTER to exit ...\n")
  system.shutdown()
  system.awaitTermination()
}