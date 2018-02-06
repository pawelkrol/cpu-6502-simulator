package com.github.pawelkrol.CPU6502
package Commodore

/** ExtendedMemory
 *
 * @constructor Create a new extended Commodore 64C memory representation
 * @param plus60kBaseAddress Base address of the +60k expansion (e.g. `0xd040` or `0xd100`)
 */
class ExtendedMemory(
  plus60kBaseAddress: Short = 0xd100.toShort
) extends CommodoreMemory {

  private var _EXTENDED_MEMORY: Array[ByteVal] = _

  private var plus60kEnabled: Boolean = false

  /** Initialize the memory subsystem */
  override def init {
    super.init
    _EXTENDED_MEMORY = Array.fill[ByteVal](Memory.size)(0xff)
  }

  private def selectMemory(address: Short) =
    if (plus60kEnabled && 0x1000 <= address && address < 0x10000)
      _EXTENDED_MEMORY
    else
      _MEMORY

  override def read(address: Short): ByteVal = {
    val memoryAddress = offset(address)

    if (0xd100 <= memoryAddress && memoryAddress < 0xd400)
      0xff
    else
      readFrom(memoryAddress, selectMemory(address))
  }

  override def write(address: Short, value: ByteVal): Memory = {
    writeTo(offset(address), value, selectMemory(address))

    if (address == plus60kBaseAddress)
      plus60kEnabled = (io(plus60kBaseAddress - 0xd000.toShort) & 0x80) != 0x00

    this
  }
}

/** Factory for [[com.github.pawelkrol.CPU6502.Commodore.ExtendedMemory]] instances */
object ExtendedMemory {

  /** Creates an empty extended Commodore 64C memory representation */
  def apply() = new ExtendedMemory()
}
