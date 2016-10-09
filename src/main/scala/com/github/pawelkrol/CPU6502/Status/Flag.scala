package com.github.pawelkrol.CPU6502
package Status

/** Status flag */
trait Flag {
  def srBits: Byte
}

/** Carry flag */
object CF extends Flag {
  val srBits: Byte = 0x01
}

/** Zero flag */
object ZF extends Flag {
  val srBits: Byte = 0x02
}

/** Interrupt flag */
object IF extends Flag {
  val srBits: Byte = 0x04
}

/** Decimal flag */
object DF extends Flag {
  val srBits: Byte = 0x08
}

/** Break flag */
object BF extends Flag {
  val srBits: Byte = 0x10
}

/** Overflow flag */
object OF extends Flag {
  val srBits: Byte = 0x40
}

/** Sign flag */
object SF extends Flag {
  val srBits: Byte = 0x80.toByte
}