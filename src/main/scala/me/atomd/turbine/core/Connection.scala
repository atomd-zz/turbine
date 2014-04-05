package me.atomd.turbine.core

import akka.actor.{ActorLogging, Actor}


class Connection extends Actor with ActorLogging {

  import akka.io.Tcp._

  def receive = {
    case Received(data) =>
      log.info("data")
  }
}