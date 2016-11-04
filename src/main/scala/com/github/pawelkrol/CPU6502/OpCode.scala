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
    case ByteVal(0x00) => OpCode_BRK_IMM
    case ByteVal(0x01) => OpCode_ORA_INDX
    case ByteVal(0x05) => OpCode_ORA_ZP
    case ByteVal(0x06) => OpCode_ASL_ZP
    case ByteVal(0x08) => OpCode_PHP
    case ByteVal(0x09) => OpCode_ORA_IMM
    case ByteVal(0x0a) => OpCode_ASL_AC
    case ByteVal(0x0d) => OpCode_ORA_ABS
    case ByteVal(0x0e) => OpCode_ASL_ABS
    case ByteVal(0x10) => OpCode_BPL_REL
    case ByteVal(0x11) => OpCode_ORA_INDY
    case ByteVal(0x15) => OpCode_ORA_ZPX
    case ByteVal(0x16) => OpCode_ASL_ZPX
    case ByteVal(0x18) => OpCode_CLC
    case ByteVal(0x19) => OpCode_ORA_ABSY
    case ByteVal(0x1d) => OpCode_ORA_ABSX
    case ByteVal(0x1e) => OpCode_ASL_ABSX
    case ByteVal(0x20) => OpCode_JSR_ABS
    case ByteVal(0x21) => OpCode_AND_INDX
    case ByteVal(0x24) => OpCode_BIT_ZP
    case ByteVal(0x25) => OpCode_AND_ZP
    case ByteVal(0x26) => OpCode_ROL_ZP
    case ByteVal(0x28) => OpCode_PLP
    case ByteVal(0x29) => OpCode_AND_IMM
    case ByteVal(0x2a) => OpCode_ROL_AC
    case ByteVal(0x2c) => OpCode_BIT_ABS
    case ByteVal(0x2d) => OpCode_AND_ABS
    case ByteVal(0x2e) => OpCode_ROL_ABS
    case ByteVal(0x30) => OpCode_BMI_REL
    case ByteVal(0x31) => OpCode_AND_INDY
    case ByteVal(0x35) => OpCode_AND_ZPX
    case ByteVal(0x36) => OpCode_ROL_ZPX
    case ByteVal(0x38) => OpCode_SEC
    case ByteVal(0x39) => OpCode_AND_ABSY
    case ByteVal(0x3d) => OpCode_AND_ABSX
    case ByteVal(0x3e) => OpCode_ROL_ABSX
    case ByteVal(0x40) => OpCode_RTI
    case ByteVal(0x41) => OpCode_EOR_INDX
    case ByteVal(0x45) => OpCode_EOR_ZP
    case ByteVal(0x46) => OpCode_LSR_ZP
    case ByteVal(0x48) => OpCode_PHA
    case ByteVal(0x49) => OpCode_EOR_IMM
    case ByteVal(0x4a) => OpCode_LSR_AC
    case ByteVal(0x4c) => OpCode_JMP_ABS
    case ByteVal(0x4d) => OpCode_EOR_ABS
    case ByteVal(0x4e) => OpCode_LSR_ABS
    case ByteVal(0x50) => OpCode_BVC_REL
    case ByteVal(0x51) => OpCode_EOR_INDY
    case ByteVal(0x55) => OpCode_EOR_ZPX
    case ByteVal(0x56) => OpCode_LSR_ZPX
    case ByteVal(0x58) => OpCode_CLI
    case ByteVal(0x59) => OpCode_EOR_ABSY
    case ByteVal(0x5d) => OpCode_EOR_ABSX
    case ByteVal(0x5e) => OpCode_LSR_ABSX
    case ByteVal(0x60) => OpCode_RTS
    case ByteVal(0x61) => OpCode_ADC_INDX
    case ByteVal(0x65) => OpCode_ADC_ZP
    case ByteVal(0x66) => OpCode_ROR_ZP
    case ByteVal(0x68) => OpCode_PLA
    case ByteVal(0x69) => OpCode_ADC_IMM
    case ByteVal(0x6a) => OpCode_ROR_AC
    case ByteVal(0x6c) => OpCode_JMP_IND
    case ByteVal(0x6d) => OpCode_ADC_ABS
    case ByteVal(0x6e) => OpCode_ROR_ABS
    case ByteVal(0x70) => OpCode_BVS_REL
    case ByteVal(0x71) => OpCode_ADC_INDY
    case ByteVal(0x75) => OpCode_ADC_ZPX
    case ByteVal(0x76) => OpCode_ROR_ZPX
    case ByteVal(0x78) => OpCode_SEI
    case ByteVal(0x79) => OpCode_ADC_ABSY
    case ByteVal(0x7d) => OpCode_ADC_ABSX
    case ByteVal(0x7e) => OpCode_ROR_ABSX
    case ByteVal(0x81) => OpCode_STA_INDX
    case ByteVal(0x84) => OpCode_STY_ZP
    case ByteVal(0x85) => OpCode_STA_ZP
    case ByteVal(0x86) => OpCode_STX_ZP
    case ByteVal(0x88) => OpCode_DEY
    case ByteVal(0x8a) => OpCode_TXA
    case ByteVal(0x8c) => OpCode_STY_ABS
    case ByteVal(0x8d) => OpCode_STA_ABS
    case ByteVal(0x8e) => OpCode_STX_ABS
    case ByteVal(0x90) => OpCode_BCC_REL
    case ByteVal(0x91) => OpCode_STA_INDY
    case ByteVal(0x94) => OpCode_STY_ZPX
    case ByteVal(0x95) => OpCode_STA_ZPX
    case ByteVal(0x96) => OpCode_STX_ZPY
    case ByteVal(0x98) => OpCode_TYA
    case ByteVal(0x99) => OpCode_STA_ABSY
    case ByteVal(0x9a) => OpCode_TXS
    case ByteVal(0x9d) => OpCode_STA_ABSX
    case ByteVal(0xa0) => OpCode_LDY_IMM
    case ByteVal(0xa2) => OpCode_LDX_IMM
    case ByteVal(0xa4) => OpCode_LDY_ZP
    case ByteVal(0xa6) => OpCode_LDX_ZP
    case ByteVal(0xa8) => OpCode_TAY
    case ByteVal(0xaa) => OpCode_TAX
    case ByteVal(0xac) => OpCode_LDY_ABS
    case ByteVal(0xae) => OpCode_LDX_ABS
    case ByteVal(0xb0) => OpCode_BCS_REL
    case ByteVal(0xb4) => OpCode_LDY_ZPX
    case ByteVal(0xb6) => OpCode_LDX_ZPY
    case ByteVal(0xb8) => OpCode_CLV
    case ByteVal(0xba) => OpCode_TSX
    case ByteVal(0xbc) => OpCode_LDY_ABSX
    case ByteVal(0xbe) => OpCode_LDX_ABSY
    case ByteVal(0xc1) => OpCode_CMP_INDX
    case ByteVal(0xc5) => OpCode_CMP_ZP
    case ByteVal(0xc8) => OpCode_INY
    case ByteVal(0xc9) => OpCode_CMP_IMM
    case ByteVal(0xca) => OpCode_DEX
    case ByteVal(0xcd) => OpCode_CMP_ABS
    case ByteVal(0xd0) => OpCode_BNE_REL
    case ByteVal(0xd1) => OpCode_CMP_INDY
    case ByteVal(0xd5) => OpCode_CMP_ZPX
    case ByteVal(0xd8) => OpCode_CLD
    case ByteVal(0xd9) => OpCode_CMP_ABSY
    case ByteVal(0xdd) => OpCode_CMP_ABSX
    case ByteVal(0xe1) => OpCode_SBC_INDX
    case ByteVal(0xe5) => OpCode_SBC_ZP
    case ByteVal(0xe8) => OpCode_INX
    case ByteVal(0xe9) => OpCode_SBC_IMM
    case ByteVal(0xed) => OpCode_SBC_ABS
    case ByteVal(0xf0) => OpCode_BEQ_REL
    case ByteVal(0xf1) => OpCode_SBC_INDY
    case ByteVal(0xf5) => OpCode_SBC_ZPX
    case ByteVal(0xf8) => OpCode_SED
    case ByteVal(0xf9) => OpCode_SBC_ABSY
    case ByteVal(0xfd) => OpCode_SBC_ABSX
    case _ => throw IllegalOpCodeError(value)
  }
}

trait OpCode_SP extends OpCode {

  def memSize = 0x01
}

trait OpCodePop_SP extends OpCode_SP {

  val cycles = 0x04
}

trait OpCodePush_SP extends OpCode_SP {

  val cycles = 0x03
}

trait OpCode_IMP extends OpCode {

  val cycles = 0x02

  def memSize = 0x01
}

trait OpCodeReturn_IMP extends OpCode_IMP {

  override val cycles = 0x06
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

trait OpCode_ZPY extends OpCode {

  val cycles = 0x04

  def memSize = 0x02
}

trait OpCode_ABS extends OpCode {

  val cycles = 0x04

  def memSize = 0x03
}

trait OpCodeCall_ABS extends OpCode_ABS {

  override val cycles = 0x06
}

trait OpCodeJump_ABS extends OpCode_ABS {

  override val cycles = 0x03
}

trait OpCode_IND extends OpCode {

  val cycles = 0x05

  def memSize = 0x03
}

trait OpCodeJump_IND extends OpCode_IND

trait OpCode_ABSX extends OpCode {

  val cycles = 0x04

  def memSize = 0x03
}

trait OpCodeStore_ABSX extends OpCode_ABSX {

  override val cycles = 0x05
}

trait OpCode_ABSY extends OpCode {

  val cycles = 0x04

  def memSize = 0x03
}

trait OpCodeStore_ABSY extends OpCode_ABSY {

  override val cycles = 0x05
}

trait OpCode_INDX extends OpCode {

  val cycles = 0x06

  def memSize = 0x02
}

trait OpCode_INDY extends OpCode {

  val cycles = 0x05

  def memSize = 0x02
}

trait OpCodeStore_INDY extends OpCode_INDY {

  override val cycles = 0x06
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

object OpCode_BRK_IMM extends OpCode_IMM with SymName_BRK {

  override val cycles = 0x07
}

object OpCode_ORA_INDX extends OpCode_INDX with SymName_ORA

object OpCode_ORA_ZP extends OpCode_ZP with SymName_ORA

object OpCode_ASL_ZP extends OpCodeRotate_ZP with SymName_ASL

object OpCode_PHP extends OpCodePush_SP with SymName_PHP

object OpCode_ORA_IMM extends OpCode_IMM with SymName_ORA

object OpCode_ASL_AC extends OpCode_AC with SymName_ASL

object OpCode_ORA_ABS extends OpCode_ABS with SymName_ORA

object OpCode_ASL_ABS extends OpCodeRotate_ABS with SymName_ASL

object OpCode_BPL_REL extends OpCode_REL with SymName_BPL

object OpCode_ORA_INDY extends OpCode_INDY with SymName_ORA

object OpCode_ORA_ZPX extends OpCode_ZPX with SymName_ORA

object OpCode_ASL_ZPX extends OpCodeRotate_ZPX with SymName_ASL

object OpCode_CLC extends OpCode_IMP with SymName_CLC

object OpCode_ORA_ABSY extends OpCode_ABSY with SymName_ORA

object OpCode_ORA_ABSX extends OpCode_ABSX with SymName_ORA

object OpCode_ASL_ABSX extends OpCodeRotate_ABSX with SymName_ASL

object OpCode_JSR_ABS extends OpCodeCall_ABS with SymName_JSR

object OpCode_AND_INDX extends OpCode_INDX with SymName_AND

object OpCode_BIT_ZP extends OpCode_ZP with SymName_BIT

object OpCode_AND_ZP extends OpCode_ZP with SymName_AND

object OpCode_ROL_ZP extends OpCodeRotate_ZP with SymName_ROL

object OpCode_PLP extends OpCodePop_SP with SymName_PLP

object OpCode_AND_IMM extends OpCode_IMM with SymName_AND

object OpCode_ROL_AC extends OpCode_AC with SymName_ROL

object OpCode_BIT_ABS extends OpCode_ABS with SymName_BIT

object OpCode_AND_ABS extends OpCode_ABS with SymName_AND

object OpCode_ROL_ABS extends OpCodeRotate_ABS with SymName_ROL

object OpCode_BMI_REL extends OpCode_REL with SymName_BMI

object OpCode_AND_INDY extends OpCode_INDY with SymName_AND

object OpCode_AND_ZPX extends OpCode_ZPX with SymName_AND

object OpCode_ROL_ZPX extends OpCodeRotate_ZPX with SymName_ROL

object OpCode_SEC extends OpCode_IMP with SymName_SEC

object OpCode_AND_ABSY extends OpCode_ABSY with SymName_AND

object OpCode_AND_ABSX extends OpCode_ABSX with SymName_AND

object OpCode_ROL_ABSX extends OpCodeRotate_ABSX with SymName_ROL

object OpCode_RTI extends OpCodeReturn_IMP with SymName_RTI

object OpCode_EOR_INDX extends OpCode_INDX with SymName_EOR

object OpCode_EOR_ZP extends OpCode_ZP with SymName_EOR

object OpCode_LSR_ZP extends OpCodeRotate_ZP with SymName_LSR

object OpCode_PHA extends OpCodePush_SP with SymName_PHA

object OpCode_EOR_IMM extends OpCode_IMM with SymName_EOR

object OpCode_LSR_AC extends OpCode_AC with SymName_LSR

object OpCode_JMP_ABS extends OpCodeJump_ABS with SymName_JMP

object OpCode_EOR_ABS extends OpCode_ABS with SymName_EOR

object OpCode_LSR_ABS extends OpCodeRotate_ABS with SymName_LSR

object OpCode_BVC_REL extends OpCode_REL with SymName_BVC

object OpCode_EOR_INDY extends OpCode_INDY with SymName_EOR

object OpCode_EOR_ZPX extends OpCode_ZPX with SymName_EOR

object OpCode_LSR_ZPX extends OpCodeRotate_ZPX with SymName_LSR

object OpCode_CLI extends OpCode_IMP with SymName_CLI

object OpCode_EOR_ABSY extends OpCode_ABSY with SymName_EOR

object OpCode_EOR_ABSX extends OpCode_ABSX with SymName_EOR

object OpCode_LSR_ABSX extends OpCodeRotate_ABSX with SymName_LSR

object OpCode_RTS extends OpCodeReturn_IMP with SymName_RTS

object OpCode_ADC_INDX extends OpCode_INDX with SymName_ADC

object OpCode_ADC_ZP extends OpCode_ZP with SymName_ADC

object OpCode_ROR_ZP extends OpCodeRotate_ZP with SymName_ROR

object OpCode_PLA extends OpCodePop_SP with SymName_PLA

object OpCode_ADC_IMM extends OpCode_IMM with SymName_ADC

object OpCode_ROR_AC extends OpCode_AC with SymName_ROR

object OpCode_JMP_IND extends OpCodeJump_IND with SymName_JMP

object OpCode_ADC_ABS extends OpCode_ABS with SymName_ADC

object OpCode_ROR_ABS extends OpCodeRotate_ABS with SymName_ROR

object OpCode_BVS_REL extends OpCode_REL with SymName_BVS

object OpCode_ADC_INDY extends OpCode_INDY with SymName_ADC

object OpCode_ADC_ZPX extends OpCode_ZPX with SymName_ADC

object OpCode_ROR_ZPX extends OpCodeRotate_ZPX with SymName_ROR

object OpCode_SEI extends OpCode_IMP with SymName_SEI

object OpCode_ADC_ABSY extends OpCode_ABSY with SymName_ADC

object OpCode_ADC_ABSX extends OpCode_ABSX with SymName_ADC

object OpCode_ROR_ABSX extends OpCodeRotate_ABSX with SymName_ROR

object OpCode_STA_INDX extends OpCode_INDX with SymName_STA

object OpCode_STY_ZP extends OpCode_ZP with SymName_STY

object OpCode_STA_ZP extends OpCode_ZP with SymName_STA

object OpCode_STX_ZP extends OpCode_ZP with SymName_STX

object OpCode_DEY extends OpCode_IMP with SymName_DEY

object OpCode_TXA extends OpCode_IMP with SymName_TXA

object OpCode_STY_ABS extends OpCode_ABS with SymName_STY

object OpCode_STA_ABS extends OpCode_ABS with SymName_STA

object OpCode_STX_ABS extends OpCode_ABS with SymName_STX

object OpCode_BCC_REL extends OpCode_REL with SymName_BCC

object OpCode_STA_INDY extends OpCodeStore_INDY with SymName_STA

object OpCode_STY_ZPX extends OpCode_ZPX with SymName_STY

object OpCode_STA_ZPX extends OpCode_ZPX with SymName_STA

object OpCode_STX_ZPY extends OpCode_ZPY with SymName_STX

object OpCode_TYA extends OpCode_IMP with SymName_TYA

object OpCode_STA_ABSY extends OpCodeStore_ABSY with SymName_STA

object OpCode_TXS extends OpCode_IMP with SymName_TXS

object OpCode_STA_ABSX extends OpCodeStore_ABSX with SymName_STA

object OpCode_LDY_IMM extends OpCode_IMM with SymName_LDY

object OpCode_LDX_IMM extends OpCode_IMM with SymName_LDX

object OpCode_LDY_ZP extends OpCode_ZP with SymName_LDY

object OpCode_LDX_ZP extends OpCode_ZP with SymName_LDX

object OpCode_TAY extends OpCode_IMP with SymName_TAY

object OpCode_TAX extends OpCode_IMP with SymName_TAX

object OpCode_LDY_ABS extends OpCode_ABS with SymName_LDY

object OpCode_LDX_ABS extends OpCode_ABS with SymName_LDX

object OpCode_BCS_REL extends OpCode_REL with SymName_BCS

object OpCode_LDY_ZPX extends OpCode_ZPX with SymName_LDY

object OpCode_LDX_ZPY extends OpCode_ZPY with SymName_LDX

object OpCode_CLV extends OpCode_IMP with SymName_CLV

object OpCode_TSX extends OpCode_IMP with SymName_TSX

object OpCode_LDY_ABSX extends OpCode_ABSX with SymName_LDY

object OpCode_LDX_ABSY extends OpCode_ABSY with SymName_LDX

object OpCode_CMP_INDX extends OpCode_INDX with SymName_CMP

object OpCode_CMP_ZP extends OpCode_ZP with SymName_CMP

object OpCode_INY extends OpCode_IMP with SymName_INY

object OpCode_CMP_IMM extends OpCode_IMM with SymName_CMP

object OpCode_DEX extends OpCode_IMP with SymName_DEX

object OpCode_CMP_ABS extends OpCode_ABS with SymName_CMP

object OpCode_BNE_REL extends OpCode_REL with SymName_BNE

object OpCode_CMP_INDY extends OpCode_INDY with SymName_CMP

object OpCode_CMP_ZPX extends OpCode_ZPX with SymName_CMP

object OpCode_CLD extends OpCode_IMP with SymName_CLD

object OpCode_CMP_ABSY extends OpCode_ABSY with SymName_CMP

object OpCode_CMP_ABSX extends OpCode_ABSX with SymName_CMP

object OpCode_SBC_INDX extends OpCode_INDX with SymName_SBC

object OpCode_SBC_ZP extends OpCode_ZP with SymName_SBC

object OpCode_INX extends OpCode_IMP with SymName_INX

object OpCode_SBC_IMM extends OpCode_IMM with SymName_SBC

object OpCode_SBC_ABS extends OpCode_ABS with SymName_SBC

object OpCode_BEQ_REL extends OpCode_REL with SymName_BEQ

object OpCode_SBC_INDY extends OpCode_INDY with SymName_SBC

object OpCode_SBC_ZPX extends OpCode_ZPX with SymName_SBC

object OpCode_SED extends OpCode_IMP with SymName_SED

object OpCode_SBC_ABSY extends OpCode_ABSY with SymName_SBC

object OpCode_SBC_ABSX extends OpCode_ABSX with SymName_SBC
