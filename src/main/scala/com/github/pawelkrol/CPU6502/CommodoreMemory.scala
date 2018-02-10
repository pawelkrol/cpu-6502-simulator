package com.github.pawelkrol.CPU6502

import Commodore.MemoryConfiguration

/** CommodoreMemory
 *
 * @constructor Create a new Commodore 64C memory representation
 */
class CommodoreMemory extends Memory with ResourceLoader {

  private def loadROM(name: String) = loadResource("/rom/%s".format(name)).map(ByteVal(_))

  private val basic = loadROM("basic")

  val chargen = loadROM("chargen")

  val io = Array.fill[ByteVal](0x1000)(0xff)

  private val kernal = loadROM("kernal")

  def read(address: Short): ByteVal = readFrom(offset(address), _MEMORY)

  protected def readFrom(address: Int, memory: Array[ByteVal]): ByteVal = {
    val memoryConfiguration = MemoryConfiguration(_MEMORY(0x0001))
    address match {
      case addr if (0x0000 <= addr && addr < 0xa000) =>
        memoryConfiguration.readFrom0000To9FFF(addr, memory)
      case addr if (0xa000 <= addr && addr < 0xc000) =>
        memoryConfiguration.readFromA000ToBFFF(addr, memory, basic)
      case addr if (0xc000 <= addr && addr < 0xd000) =>
        memoryConfiguration.readFromC000ToCFFF(addr, memory)
      case addr if (0xd000 <= addr && addr < 0xe000) =>
        memoryConfiguration.readFromD000ToDFFF(addr, memory, chargen, io)
      case addr if (0xe000 <= addr && addr < 0x10000) =>
        memoryConfiguration.readFromE000ToFFFF(addr, memory, kernal)
      case _ =>
        throw new RuntimeException("invalid address: $%04x".format(address))
    }
  }

  def write(address: Short, value: ByteVal): Memory = writeTo(offset(address), value, _MEMORY)

  def writeTo(address: Int, value: ByteVal, memory: Array[ByteVal]): Memory = {
    val memoryConfiguration = MemoryConfiguration(_MEMORY(0x0001))
    address match {
      case addr if (0x0000 <= addr && addr < 0xd000) =>
        memoryConfiguration.writeTo0000ToCFFF(addr, value, memory)
      case addr if (0xd000 <= addr && addr < 0xe000) =>
        memoryConfiguration.writeToD000ToDFFF(addr, value, memory, io)
      case addr if (0xe000 <= addr && addr < 0x10000) =>
        memoryConfiguration.writeToE000ToFFFF(addr, value, memory)
      case _ =>
        throw new RuntimeException("invalid address: $%04x".format(address))
    }
    this
  }
}

/** Factory for [[com.github.pawelkrol.CPU6502.CommodoreMemory]] instances */
object CommodoreMemory {

  /** Creates an empty Commodore 64C memory representation */
  def apply() = new CommodoreMemory()
}
