package com.github.pawelkrol.CPU6502

/** Operation code */
sealed trait OpCode {

  def cycles: Int

  def memSize: Int

  def symName: String

  override def toString = symName
}

object OpCode {

  def apply(value: ByteVal) = value match {
    case ByteVal(0x01) => OpCode_ORA_INDX
    case ByteVal(0x05) => OpCode_ORA_ZP
    case ByteVal(0x09) => OpCode_ORA_IMM
    case ByteVal(0x0d) => OpCode_ORA_ABS
    case ByteVal(0x11) => OpCode_ORA_INDY
    case ByteVal(0x15) => OpCode_ORA_ZPX
    case ByteVal(0x19) => OpCode_ORA_ABSY
    case ByteVal(0x1d) => OpCode_ORA_ABSX
    case ByteVal(0x21) => OpCode_AND_INDX
    case ByteVal(0x25) => OpCode_AND_ZP
    case ByteVal(0x29) => OpCode_AND_IMM
    case ByteVal(0x2d) => OpCode_AND_ABS
    case ByteVal(0x31) => OpCode_AND_INDY
    case ByteVal(0x35) => OpCode_AND_ZPX
    case ByteVal(0x39) => OpCode_AND_ABSY
    case ByteVal(0x3d) => OpCode_AND_ABSX
    case ByteVal(0x41) => OpCode_EOR_INDX
    case ByteVal(0x45) => OpCode_EOR_ZP
    case ByteVal(0x49) => OpCode_EOR_IMM
    case ByteVal(0x4d) => OpCode_EOR_ABS
    case ByteVal(0x51) => OpCode_EOR_INDY
    case ByteVal(0x55) => OpCode_EOR_ZPX
    case ByteVal(0x59) => OpCode_EOR_ABSY
    case ByteVal(0x5d) => OpCode_EOR_ABSX
    case ByteVal(0x61) => OpCode_ADC_INDX
    case ByteVal(0x65) => OpCode_ADC_ZP
    case ByteVal(0x69) => OpCode_ADC_IMM
    case ByteVal(0x6d) => OpCode_ADC_ABS
    case ByteVal(0x71) => OpCode_ADC_INDY
    case ByteVal(0x75) => OpCode_ADC_ZPX
    case ByteVal(0x79) => OpCode_ADC_ABSY
    case ByteVal(0x7d) => OpCode_ADC_ABSX
    case _ => throw NotImplementedError()
  }
}

sealed trait OpCode_IMM extends OpCode {

  val cycles = 0x02

  def memSize = 0x02
}

sealed trait OpCode_ZP extends OpCode {

  val cycles = 0x03

  def memSize = 0x02
}

sealed trait OpCode_ZPX extends OpCode {

  val cycles = 0x04

  def memSize = 0x02
}

sealed trait OpCode_ABS extends OpCode {

  val cycles = 0x04

  def memSize = 0x03
}

sealed trait OpCode_ABSX extends OpCode {

  val cycles = 0x04

  def memSize = 0x03
}

sealed trait OpCode_ABSY extends OpCode {

  val cycles = 0x04

  def memSize = 0x03
}

sealed trait OpCode_INDX extends OpCode {

  val cycles = 0x06

  def memSize = 0x02
}

sealed trait OpCode_INDY extends OpCode {

  val cycles = 0x05

  def memSize = 0x02
}

object OpCode_ORA_INDX extends OpCode_INDX with SymName_ORA

object OpCode_ORA_ZP extends OpCode_ZP with SymName_ORA

object OpCode_ORA_IMM extends OpCode_IMM with SymName_ORA

object OpCode_ORA_ABS extends OpCode_ABS with SymName_ORA

object OpCode_ORA_INDY extends OpCode_INDY with SymName_ORA

object OpCode_ORA_ZPX extends OpCode_ZPX with SymName_ORA

object OpCode_ORA_ABSY extends OpCode_ABSY with SymName_ORA

object OpCode_ORA_ABSX extends OpCode_ABSX with SymName_ORA

object OpCode_AND_INDX extends OpCode_INDX with SymName_AND

object OpCode_AND_ZP extends OpCode_ZP with SymName_AND

object OpCode_AND_IMM extends OpCode_IMM with SymName_AND

object OpCode_AND_ABS extends OpCode_ABS with SymName_AND

object OpCode_AND_INDY extends OpCode_INDY with SymName_AND

object OpCode_AND_ZPX extends OpCode_ZPX with SymName_AND

object OpCode_AND_ABSY extends OpCode_ABSY with SymName_AND

object OpCode_AND_ABSX extends OpCode_ABSX with SymName_AND

object OpCode_EOR_INDX extends OpCode_INDX with SymName_EOR

object OpCode_EOR_ZP extends OpCode_ZP with SymName_EOR

object OpCode_EOR_IMM extends OpCode_IMM with SymName_EOR

object OpCode_EOR_ABS extends OpCode_ABS with SymName_EOR

object OpCode_EOR_INDY extends OpCode_INDY with SymName_EOR

object OpCode_EOR_ZPX extends OpCode_ZPX with SymName_EOR

object OpCode_EOR_ABSY extends OpCode_ABSY with SymName_EOR

object OpCode_EOR_ABSX extends OpCode_ABSX with SymName_EOR

object OpCode_ADC_INDX extends OpCode_INDX with SymName_ADC

object OpCode_ADC_ZP extends OpCode_ZP with SymName_ADC

object OpCode_ADC_IMM extends OpCode_IMM with SymName_ADC

object OpCode_ADC_ABS extends OpCode_ABS with SymName_ADC

object OpCode_ADC_INDY extends OpCode_INDY with SymName_ADC

object OpCode_ADC_ZPX extends OpCode_ZPX with SymName_ADC

object OpCode_ADC_ABSY extends OpCode_ABSY with SymName_ADC

object OpCode_ADC_ABSX extends OpCode_ABSX with SymName_ADC
