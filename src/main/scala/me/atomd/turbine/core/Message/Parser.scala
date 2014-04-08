package me.atomd.turbine.core.Message

import akka.util.ByteString
import org.openflow.protocol.OFMessage

object Parser {
  def apply() = new Parser()
}

class Parser {

  import scala.collection.JavaConverters._
  private var input = ByteString.empty

  def onReceive(newInput: ByteString, limit: Int = 0)(packetListener: PartialFunction[OFMessage, Unit]) {
    input = input ++ newInput
    val byteBuffer = input.asByteBuffer
    factory.parseMessages(byteBuffer, limit).asScala.foreach(packetListener)
    input.drop(byteBuffer.position())
  }
}
