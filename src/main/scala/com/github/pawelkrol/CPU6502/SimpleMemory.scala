package com.github.pawelkrol.CPU6502

/** SimpleMemory
 *
 * @constructor Create a new simple memory representation
 */
class SimpleMemory extends Memory {

  /** Initialize the simple memory subsystem */
  override def init {
    super.init
    _MEMORY(0xfffc) = 0x00
    _MEMORY(0xfffd) = 0x02
  }

  def read(address: Short): ByteVal = _MEMORY(offset(address))

  def write(address: Short, value: ByteVal): Memory = {
    _MEMORY(offset(address.toShort)) = value
    this
  }
}

/** Factory for [[com.github.pawelkrol.CPU6502.SimpleMemory]] instances */
object SimpleMemory {

  /** Creates an empty simple memory representation */
  def apply() = new SimpleMemory()
}
