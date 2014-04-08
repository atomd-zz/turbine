package me.atomd.turbine.core

import akka.actor.{Actor, ActorLogging, Props}
import akka.io.{ IO, Tcp, Inet }
import java.net.InetSocketAddress


case class Configuration(ip: String = "9.9.9.9", port: Int = 6633, backlog: Int = 128)

object Controller {
  def props(config: Configuration = Configuration()): Props = Props(classOf[Controller], config)
}

class Controller(config: Configuration) extends Actor with ActorLogging {

  import Tcp._
  import context.system

  override def preStart() {
    val options = List(Inet.SO.ReuseAddress(true))
    IO(Tcp) ! Bind(self, new InetSocketAddress(config.ip, config.port), config.backlog, options)
  }

  def receive = {
    case Bound(localAddress) =>
      log.info("Addr(" + localAddress + ") is bound.")

    case CommandFailed(_: Bind) =>
      log.info("Bind failed.")
      context stop self

    case Connected(remoteAddress, localAddress) =>
      log.info("Connection is established from Addr(" + remoteAddress + ") to Addr(" + localAddress + ")")
      val serverConnection = sender()
      val connectionActor = context.actorOf(Props(classOf[Connection], serverConnection))
      serverConnection ! Register(connectionActor)
  }
}
