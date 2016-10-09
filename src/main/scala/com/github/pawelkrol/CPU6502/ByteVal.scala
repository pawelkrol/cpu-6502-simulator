package com.github.pawelkrol.CPU6502

import scala.language.implicitConversions

class ByteVal(val value: Byte) {

  def apply() = Util.byte2Int(value)

  def +(other: Int): ByteVal = apply() + other

  def +(other: ByteVal): ByteVal = apply() + other()

  def -(other: Int): ByteVal = apply() - other

  def -(other: ByteVal): ByteVal = apply() - other()

  def &(other: ByteVal): ByteVal = value & other()

  def |(other: ByteVal): ByteVal = value | other()

  def ^(other: ByteVal): ByteVal = value ^ other()

  def unary_~ : ByteVal = ~value

  def <<(move: Int): ByteVal = value << move

  def >>(move: Int): ByteVal = value >> move

  def >>>(move: Int): ByteVal = (0x00 until move).foldLeft[ByteVal](value)((result, bit) => { (result >> 1) & 0x7f })

  def canEqual(that: Any) = that.isInstanceOf[ByteVal] || that.isInstanceOf[Int]

  override def equals(other: Any) = other match {
    case that: ByteVal =>
      this() == that()
    case that: Int =>
      this() == that
    case _ =>
      false
  }

  override def toString = apply().toString
}

object ByteVal {

  def apply(value: Byte) = new ByteVal(value)

  def apply(value: Int) = int2ByteVal(value & 0xff)

  def unapply(byteVal: ByteVal): Option[Int] = Some(byteVal())

  implicit def int2ByteVal(value: Int): ByteVal = new ByteVal(value.toByte)

  implicit def byteVal2Short(value: ByteVal): Short = Util.byteVal2Short(value)
}
