package me.atomd.turbine.core

import akka.util.ByteString
import akka.actor.{ActorRef, ActorLogging, Actor}
import org.openflow.protocol._
import me.atomd.turbine.core.Message.{Parser, Builder, factory}


class Connection(serverConnection: ActorRef) extends Actor with ActorLogging {

  import akka.io.Tcp._
  private val parser = Parser()
  private val builder = Builder()

  def receive = {

    case Received(data) =>
      parser.onReceive(data) {

        case msg: OFHello =>
          log.info("0 OFHello Received: " + msg.toString)
          send(factory.getMessage(OFType.HELLO))

        case msg: OFError               => log.info("1 OFError Received: " + msg.toString + " Type:" + msg.getErrorType + " Code:" + msg.getErrorCode)
        case msg: OFEchoReply           => log.info("3 OFEchoReply Received: " + msg.toString)
        case msg: OFEchoRequest         => log.info("2 OFEchoRequest Received: " + msg.toString)
        case msg: OFVendor              => log.info("4 OFVendor Received: " + msg.toString)
        case msg: OFFeaturesRequest     => log.info("5 OFFeaturesRequest Received: " + msg.toString)
        case msg: OFFeaturesReply       => log.info("6 OFFeaturesReply Received: " + msg.toString)
        case msg: OFGetConfigRequest    => log.info("7 OFGetConfigRequest Received: " + msg.toString)
        case msg: OFGetConfigReply      => log.info("8 OFGetConfigReply Received: " + msg.toString)
        case msg: OFSetConfig           => log.info("9 OFSetConfig Received: " + msg.toString)
        case msg: OFPacketIn            => log.info("10 OFPacketIn Received: " + msg.toString)
        case msg: OFFlowRemoved         => log.info("11 OFFlowRemoved Received: " + msg.toString)
        case msg: OFPortStatus          => log.info("12 OFPortStatus Received: " + msg.toString)
        case msg: OFPacketOut           => log.info("13 OFPacketOut Received: " + msg.toString)
        case msg: OFFlowMod             => log.info("14 OFFlowMod Received: " + msg.toString)
        case msg: OFPortMod             => log.info("15 OFPortMod Received: " + msg.toString)
        case msg: OFStatisticsRequest   => log.info("16 OFStatisticsRequest Received: " + msg.toString)
        case msg: OFStatisticsReply     => log.info("17 OFStatisticsReply Received: " + msg.toString)
        case msg: OFBarrierRequest      => log.info("18 OFBarrierRequest Received: " + msg.toString)
        case msg: OFBarrierReply        => log.info("19 OFBarrierReply Received: " + msg.toString)
        case msg: OFQueueConfigRequest  => log.info("20 OFQueueConfigRequest Received: " + msg.toString)
        case msg: OFQueueConfigReply    => log.info("21 OFQueueConfigReply Received: " + msg.toString)
        case msg: OFUnknownMessage      => log.info("0xff OFUnknownMessage Received: " + msg.toString)
        // case msg: OFMessage             => log.info("Unexpected Message")
      }

    case PeerClosed =>
      log.info("peerClosed Received")
      context stop self
  }

  def send(data: ByteString) {
    serverConnection ! Write(data)
  }

  def send(message: OFMessage) {
    send(builder.build(message))
  }
}
