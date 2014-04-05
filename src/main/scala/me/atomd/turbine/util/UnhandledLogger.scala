package me.atomd.turbine.util

import akka.actor.{Props, UnhandledMessage, ActorLogging, Actor}


object UnhandledLogger {
  def props() = Props(classOf[UnhandledLogger])
}

class UnhandledLogger extends Actor with ActorLogging {

  override def preStart = {
      context.system.eventStream.subscribe(self, classOf[UnhandledMessage])
  }

  def receive = {
    case UnhandledMessage(msg, sender, recipient) =>
      log.error(sender + " to " + recipient + ": " + msg)
  }
}
