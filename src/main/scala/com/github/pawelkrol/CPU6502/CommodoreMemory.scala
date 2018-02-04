package com.github.pawelkrol.CPU6502

import Commodore.MemoryConfiguration

/** CommodoreMemory
 *
 * @constructor Create a new Commodore 64C memory representation
 */
class CommodoreMemory extends Memory with ResourceLoader {

  private def loadROM(name: String) = loadResource("/rom/%s".format(name)).map(ByteVal(_))

  private val basic = loadROM("basic")

  private val chargen = loadROM("chargen")

  private val io = Array.fill[ByteVal](0x1000)(0xff)

  private val kernal = loadROM("kernal")

  def read(address: Short): ByteVal = {
    val memoryConfiguration = MemoryConfiguration(_MEMORY(0x0001))
    offset(address) match {
      case addr if (0x0000 <= addr && addr < 0xa000) =>
        memoryConfiguration.readFrom0000To9FFF(addr, _MEMORY)
      case addr if (0xa000 <= addr && addr < 0xc000) =>
        memoryConfiguration.readFromA000ToBFFF(addr, _MEMORY, basic)
      case addr if (0xc000 <= addr && addr < 0xd000) =>
        memoryConfiguration.readFromC000ToCFFF(addr, _MEMORY)
      case addr if (0xd000 <= addr && addr < 0xe000) =>
        memoryConfiguration.readFromD000ToDFFF(addr, _MEMORY, chargen, io)
      case addr if (0xe000 <= addr && addr < 0x10000) =>
        memoryConfiguration.readFromE000ToFFFF(addr, _MEMORY, kernal)
      case _ =>
        throw new RuntimeException("invalid address: $%04x".format(address))
    }
  }

  def write(address: Short, value: ByteVal): Memory = {
    val memoryConfiguration = MemoryConfiguration(_MEMORY(0x0001))
    offset(address) match {
      case addr if (0x0000 <= addr && addr < 0xd000) =>
        memoryConfiguration.writeTo0000ToCFFF(addr, value, _MEMORY)
      case addr if (0xd000 <= addr && addr < 0xe000) =>
        memoryConfiguration.writeToD000ToDFFF(addr, value, _MEMORY, io)
      case addr if (0xe000 <= addr && addr < 0x10000) =>
        memoryConfiguration.writeToE000ToFFFF(addr, value, _MEMORY)
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
