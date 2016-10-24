package com.github.pawelkrol.CPU6502

object Util {

  def byte2Int(value: Byte): Int = (0x00 until 0x08).foldLeft[Int](0x00)((result, bit) => {
    val bitMask = 0x80 >> bit
    if ((value.toInt & bitMask) > 0)
      result + bitMask
    else
      result
  })

  def byte2Short(value: Byte): Short = (0x00 until 0x08).foldLeft[Short](0x00)((result, bit) => {
    val bitMask = 0x80 >> bit
    if ((value.toInt & bitMask) > 0)
      (result + bitMask).toShort
    else
      result
  })

  def short2Int(address: Short) = (0x00 until 0x10).foldLeft[Int](0x00)((result, bit) => {
    val bitMask = 0x8000 >> bit
    if ((address.toInt & bitMask) > 0)
      result + bitMask
    else
      result
  })

  def byteVal2Short(value: ByteVal): Short = value().toShort

  def addr2ByteVals(addr: Short): Seq[ByteVal] = Seq(addr & 0xff, (addr >> 8) & 0xff).map(ByteVal(_))

  def byteVals2Addr(byteVals: Seq[ByteVal]): Short = (byteVals(0)() + (byteVals(1)() << 8)).toShort

  def word2Nibbles(addr: Short) = (addr & 0xff, (addr >> 8) & 0xff)

  def nibbles2Word(nibbles: Tuple2[ByteVal, ByteVal]) = byteVals2Addr(Seq[ByteVal](nibbles._1, nibbles._2))
}
