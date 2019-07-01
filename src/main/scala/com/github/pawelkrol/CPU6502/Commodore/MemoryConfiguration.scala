package com.github.pawelkrol.CPU6502
package Commodore

class MemoryConfiguration(ioRegister: ByteVal) {

  private val charEnSignal = (ioRegister & 0x04)() != 0x00

  private val hiRamSignal = (ioRegister & 0x02)() != 0x00

  private val loRamSignal = (ioRegister & 0x01)() != 0x00

  val hasBasic = hiRamSignal && loRamSignal

  val hasIO = charEnSignal && (hiRamSignal || loRamSignal)

  val hasChargen = !charEnSignal && (hiRamSignal || loRamSignal)

  val hasKernal = hiRamSignal

  def readFrom0000To9FFF(address: Int, ram: Array[ByteVal]) =
    ram(address)

  def readFromA000ToBFFF(address: Int, ram: Array[ByteVal], basic: Array[ByteVal]) =
    if (hasBasic)
      basic(address - 0xa000)
    else
      ram(address)

  def readFromC000ToCFFF(address: Int, ram: Array[ByteVal]) =
    ram(address)

  def readFromD000ToDFFF(address: Int, ram: Array[ByteVal], chargen: Array[ByteVal], io: Array[ByteVal]) =
    if (hasChargen)
      chargen(address - 0xd000)
    else
      if (hasIO)
        io(address - 0xd000)
      else
        ram(address)

  def readFromE000ToFFFF(address: Int, ram: Array[ByteVal], kernal: Array[ByteVal]) =
    if (hasKernal)
      kernal(address - 0xe000)
    else
      ram(address)

  def writeTo0000ToCFFF(address: Int, value: ByteVal, ram: Array[ByteVal]) =
    ram(address) = value

  def writeToD000ToDFFF(address: Int, value: ByteVal, ram: Array[ByteVal], io: Array[ByteVal]) =
    if (hasIO)
      io(address - 0xd000) = value
    else
      ram(address) = value

  def writeToE000ToFFFF(address: Int, value: ByteVal, ram: Array[ByteVal]) =
    ram(address) = value
}

object MemoryConfiguration {

  def apply(ioRegister: ByteVal) = new MemoryConfiguration(ioRegister)
}
