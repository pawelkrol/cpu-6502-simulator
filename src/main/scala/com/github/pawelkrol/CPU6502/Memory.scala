package com.github.pawelkrol.CPU6502

/** Memory
 *
 * @constructor Create a new memory representation
 */
class Memory {

  private var _MEMORY = Array.fill[ByteVal](0x10000)(0xff)

  def offset(address: Short) = Util.short2Int(address)

  def read(address: Int): ByteVal = read(address.toShort)

  def read(address: Short): ByteVal = _MEMORY(offset(address))

  def write(address: Short, value: ByteVal): Memory = {
    _MEMORY(offset(address)) = value
    this
  }

  def write(address: Int, value: ByteVal): Memory = write(address.toShort, value)

  def get_val_from_addr(address: Short) = Util.byteVals2Addr(Seq(read(address), read((address + 1).toShort)))
}

/** Factory for [[com.github.pawelkrol.CPU6502.Memory]] instances */
object Memory {

  /** Creates an empty memory representation */
  def apply() = new Memory()
}
