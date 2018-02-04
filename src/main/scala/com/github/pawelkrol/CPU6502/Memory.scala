package com.github.pawelkrol.CPU6502

/** Memory
 *
 * @constructor Create a new memory representation
 */
trait Memory {

  protected var _MEMORY: Array[ByteVal] = _

  init

  def offset(address: Short) = Util.short2Int(address)

  def read(address: Int, count: Int): Seq[ByteVal] = (0 until count).map(index => read((address + index).toShort))

  def read(address: Int): ByteVal = read(address.toShort)

  def write(address: Short, values: Seq[ByteVal]): Memory = {
    values.zipWithIndex.foreach({ case (value, index) =>
      write(address + index, value)
    })
    this
  }

  def write(address: Int, value: ByteVal): Memory = write(address.toShort, value)

  def write(address: Int, values: ByteVal*): Memory = write(address.toShort, values)

  def get_val_from_addr(address: Short) = Util.byteVals2Addr(Seq(read(address), read((address + 1).toShort)))

  def get_val_from_zp(address: Short) = Util.byteVals2Addr(Seq(read(address), read(((address + 1) & 0xff).toShort)))

  /** Initialize the memory subsystem */
  def init {
    _MEMORY = Array.fill[ByteVal](Memory.size)(0xff)
  }

  def read(address: Short): ByteVal

  def write(address: Short, value: ByteVal): Memory
}

object Memory {

  val size = 0x10000
}
