package com.github.pawelkrol.CPU6502

/** Operation code */
trait OpCode {

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
    case ByteVal(0x10) => OpCode_BPL_REL
    case ByteVal(0x11) => OpCode_ORA_INDY
    case ByteVal(0x15) => OpCode_ORA_ZPX
    case ByteVal(0x19) => OpCode_ORA_ABSY
    case ByteVal(0x1d) => OpCode_ORA_ABSX
    case ByteVal(0x21) => OpCode_AND_INDX
    case ByteVal(0x25) => OpCode_AND_ZP
    case ByteVal(0x26) => OpCode_ROL_ZP
    case ByteVal(0x29) => OpCode_AND_IMM
    case ByteVal(0x2a) => OpCode_ROL_AC
    case ByteVal(0x2d) => OpCode_AND_ABS
    case ByteVal(0x2e) => OpCode_ROL_ABS
    case ByteVal(0x30) => OpCode_BMI_REL
    case ByteVal(0x31) => OpCode_AND_INDY
    case ByteVal(0x35) => OpCode_AND_ZPX
    case ByteVal(0x36) => OpCode_ROL_ZPX
    case ByteVal(0x39) => OpCode_AND_ABSY
    case ByteVal(0x3d) => OpCode_AND_ABSX
    case ByteVal(0x3e) => OpCode_ROL_ABSX
    case ByteVal(0x41) => OpCode_EOR_INDX
    case ByteVal(0x45) => OpCode_EOR_ZP
    case ByteVal(0x49) => OpCode_EOR_IMM
    case ByteVal(0x4d) => OpCode_EOR_ABS
    case ByteVal(0x50) => OpCode_BVC_REL
    case ByteVal(0x51) => OpCode_EOR_INDY
    case ByteVal(0x55) => OpCode_EOR_ZPX
    case ByteVal(0x59) => OpCode_EOR_ABSY
    case ByteVal(0x5d) => OpCode_EOR_ABSX
    case ByteVal(0x61) => OpCode_ADC_INDX
    case ByteVal(0x65) => OpCode_ADC_ZP
    case ByteVal(0x66) => OpCode_ROR_ZP
    case ByteVal(0x69) => OpCode_ADC_IMM
    case ByteVal(0x6a) => OpCode_ROR_AC
    case ByteVal(0x6d) => OpCode_ADC_ABS
    case ByteVal(0x6e) => OpCode_ROR_ABS
    case ByteVal(0x70) => OpCode_BVS_REL
    case ByteVal(0x71) => OpCode_ADC_INDY
    case ByteVal(0x75) => OpCode_ADC_ZPX
    case ByteVal(0x76) => OpCode_ROR_ZPX
    case ByteVal(0x79) => OpCode_ADC_ABSY
    case ByteVal(0x7d) => OpCode_ADC_ABSX
    case ByteVal(0x7e) => OpCode_ROR_ABSX
    case ByteVal(0x90) => OpCode_BCC_REL
    case ByteVal(0xb0) => OpCode_BCS_REL
    case ByteVal(0xc1) => OpCode_CMP_INDX
    case ByteVal(0xc5) => OpCode_CMP_ZP
    case ByteVal(0xc9) => OpCode_CMP_IMM
    case ByteVal(0xcd) => OpCode_CMP_ABS
    case ByteVal(0xd0) => OpCode_BNE_REL
    case ByteVal(0xd1) => OpCode_CMP_INDY
    case ByteVal(0xd5) => OpCode_CMP_ZPX
    case ByteVal(0xd9) => OpCode_CMP_ABSY
    case ByteVal(0xdd) => OpCode_CMP_ABSX
    case ByteVal(0xf0) => OpCode_BEQ_REL
    case _ => throw NotImplementedError()
  }
}

trait OpCode_IMM extends OpCode {

  val cycles = 0x02

  def memSize = 0x02
}

trait OpCode_ZP extends OpCode {

  val cycles = 0x03

  def memSize = 0x02
}

trait OpCode_ZPX extends OpCode {

  val cycles = 0x04

  def memSize = 0x02
}

trait OpCode_ABS extends OpCode {

  val cycles = 0x04

  def memSize = 0x03
}

trait OpCode_ABSX extends OpCode {

  val cycles = 0x04

  def memSize = 0x03
}

trait OpCode_ABSY extends OpCode {

  val cycles = 0x04

  def memSize = 0x03
}

trait OpCode_INDX extends OpCode {

  val cycles = 0x06

  def memSize = 0x02
}

trait OpCode_INDY extends OpCode {

  val cycles = 0x05

  def memSize = 0x02
}

trait OpCode_REL extends OpCode {

  val cycles = 0x02

  def memSize = 0x02
}

trait OpCode_AC extends OpCode {

  val cycles = 0x02

  def memSize = 0x01
}

trait OpCodeRotate_ZP extends OpCode_ZP {

  override val cycles = 0x05
}

trait OpCodeRotate_ABS extends OpCode_ABS {

  override val cycles = 0x06
}

trait OpCodeRotate_ZPX extends OpCode_ZPX {

  override val cycles = 0x06
}

trait OpCodeRotate_ABSX extends OpCode_ABSX {

  override val cycles = 0x07
}

object OpCode_ORA_INDX extends OpCode_INDX with SymName_ORA

object OpCode_ORA_ZP extends OpCode_ZP with SymName_ORA

object OpCode_ORA_IMM extends OpCode_IMM with SymName_ORA

object OpCode_ORA_ABS extends OpCode_ABS with SymName_ORA

object OpCode_BPL_REL extends OpCode_REL with SymName_BPL

object OpCode_ORA_INDY extends OpCode_INDY with SymName_ORA

object OpCode_ORA_ZPX extends OpCode_ZPX with SymName_ORA

object OpCode_ORA_ABSY extends OpCode_ABSY with SymName_ORA

object OpCode_ORA_ABSX extends OpCode_ABSX with SymName_ORA

object OpCode_AND_INDX extends OpCode_INDX with SymName_AND

object OpCode_AND_ZP extends OpCode_ZP with SymName_AND

object OpCode_ROL_ZP extends OpCodeRotate_ZP with SymName_ROL

object OpCode_AND_IMM extends OpCode_IMM with SymName_AND

object OpCode_ROL_AC extends OpCode_AC with SymName_ROL

object OpCode_AND_ABS extends OpCode_ABS with SymName_AND

object OpCode_ROL_ABS extends OpCodeRotate_ABS with SymName_ROL

object OpCode_BMI_REL extends OpCode_REL with SymName_BMI

object OpCode_AND_INDY extends OpCode_INDY with SymName_AND

object OpCode_AND_ZPX extends OpCode_ZPX with SymName_AND

object OpCode_ROL_ZPX extends OpCodeRotate_ZPX with SymName_ROL

object OpCode_AND_ABSY extends OpCode_ABSY with SymName_AND

object OpCode_AND_ABSX extends OpCode_ABSX with SymName_AND

object OpCode_ROL_ABSX extends OpCodeRotate_ABSX with SymName_ROL

object OpCode_EOR_INDX extends OpCode_INDX with SymName_EOR

object OpCode_EOR_ZP extends OpCode_ZP with SymName_EOR

object OpCode_EOR_IMM extends OpCode_IMM with SymName_EOR

object OpCode_EOR_ABS extends OpCode_ABS with SymName_EOR

object OpCode_BVC_REL extends OpCode_REL with SymName_BVC

object OpCode_EOR_INDY extends OpCode_INDY with SymName_EOR

object OpCode_EOR_ZPX extends OpCode_ZPX with SymName_EOR

object OpCode_EOR_ABSY extends OpCode_ABSY with SymName_EOR

object OpCode_EOR_ABSX extends OpCode_ABSX with SymName_EOR

object OpCode_ADC_INDX extends OpCode_INDX with SymName_ADC

object OpCode_ADC_ZP extends OpCode_ZP with SymName_ADC

object OpCode_ROR_ZP extends OpCodeRotate_ZP with SymName_ROR

object OpCode_ADC_IMM extends OpCode_IMM with SymName_ADC

object OpCode_ROR_AC extends OpCode_AC with SymName_ROR

object OpCode_ADC_ABS extends OpCode_ABS with SymName_ADC

object OpCode_ROR_ABS extends OpCodeRotate_ABS with SymName_ROR

object OpCode_BVS_REL extends OpCode_REL with SymName_BVS

object OpCode_ADC_INDY extends OpCode_INDY with SymName_ADC

object OpCode_ADC_ZPX extends OpCode_ZPX with SymName_ADC

object OpCode_ROR_ZPX extends OpCodeRotate_ZPX with SymName_ROR

object OpCode_ADC_ABSY extends OpCode_ABSY with SymName_ADC

object OpCode_ADC_ABSX extends OpCode_ABSX with SymName_ADC

object OpCode_ROR_ABSX extends OpCodeRotate_ABSX with SymName_ROR

object OpCode_BCC_REL extends OpCode_REL with SymName_BCC

object OpCode_BCS_REL extends OpCode_REL with SymName_BCS

object OpCode_CMP_INDX extends OpCode_INDX with SymName_CMP

object OpCode_CMP_ZP extends OpCode_ZP with SymName_CMP

object OpCode_CMP_IMM extends OpCode_IMM with SymName_CMP

object OpCode_CMP_ABS extends OpCode_ABS with SymName_CMP

object OpCode_BNE_REL extends OpCode_REL with SymName_BNE

object OpCode_CMP_INDY extends OpCode_INDY with SymName_CMP

object OpCode_CMP_ZPX extends OpCode_ZPX with SymName_CMP

object OpCode_CMP_ABSY extends OpCode_ABSY with SymName_CMP

object OpCode_CMP_ABSX extends OpCode_ABSX with SymName_CMP

object OpCode_BEQ_REL extends OpCode_REL with SymName_BEQ
