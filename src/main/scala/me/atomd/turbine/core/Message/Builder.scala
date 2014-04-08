package me.atomd.turbine.core.Message

import akka.util.ByteString
import java.nio.ByteBuffer
import org.openflow.protocol.OFMessage

object Builder {
  def apply() = new Builder
}

class Builder {
  private val buffer = ByteBuffer.allocate(1024 * 1024)

  def build(message: OFMessage): ByteString = {
    buffer.clear()
    message.writeTo(buffer)
    buffer.flip()
    ByteString(buffer)
  }
}