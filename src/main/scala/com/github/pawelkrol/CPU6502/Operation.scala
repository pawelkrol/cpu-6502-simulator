package com.github.pawelkrol.CPU6502

import Status._

abstract class Operation(memory: Memory, register: Register) {

  var cycleCount = 0

  private def get_val_from_addr(zp: Short) = Util.byteVals2Addr(Seq(memory.read(zp), memory.read(zp + 1)))

  private def get_addr_ABS = get_val_from_addr((register.PC + 1).toShort)

  private def get_addr_ABSX = get_addr_ABS + register.XR()

  private def get_addr_ZP = memory.read(register.PC + 1)

  private def get_addr_ZPX = get_addr_ZP + register.XR()

  private def get_addr_INDX = get_val_from_addr(get_addr_ZPX)

  private def get_addr_INDY = get_val_from_addr(get_addr_ZP)

  private def get_arg_IMM = memory.read(register.PC + 1)

  private def get_arg_ZP = memory.read(get_addr_ZP)

  private def get_arg_ZPX = memory.read(get_addr_ZPX)

  private def get_arg_ABS = memory.read(get_addr_ABS)

  private def get_arg_ABSX = memory.read(get_addr_ABSX)

  private def get_arg_ABSY = memory.read(get_addr_ABS + register.YR())

  private def get_arg_INDX = memory.read(get_addr_INDX)

  private def get_arg_INDY = memory.read(get_addr_INDY + register.YR())

  private def get_arg_REL = memory.read(register.PC + 1)

  /** [$09] ORA #$FF */
  /** [$29] AND #$FF */
  /** [$49] EOR #$FF */
  private def opImmediate(op: (ByteVal, ByteVal) => ByteVal) {
    register.AC = op(register.AC, get_arg_IMM)
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
  }

  /** [$05] ORA $FF */
  /** [$25] AND $FF */
  /** [$45] EOR $FF */
  private def opZeroPage(op: (ByteVal, ByteVal) => ByteVal) {
    register.AC = op(register.AC, get_arg_ZP)
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
  }

  /** [$15] ORA $FF */
  /** [$35] AND $FF */
  /** [$55] EOR $FF */
  private def opZeroPageX(op: (ByteVal, ByteVal) => ByteVal) {
    register.AC = op(register.AC, get_arg_ZPX)
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
  }

  /** [$0d] ORA $FFFF */
  /** [$2d] AND $FFFF */
  /** [$4d] EOR $FFFF */
  private def opAbsolute(op: (ByteVal, ByteVal) => ByteVal) {
    register.AC = op(register.AC, get_arg_ABS)
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
  }

  /** [$1d] ORA $FFFF,X */
  /** [$3d] AND $FFFF,X */
  /** [$5d] EOR $FFFF,X */
  private def opAbsoluteX(op: (ByteVal, ByteVal) => ByteVal) {
    register.AC = op(register.AC, get_arg_ABSX)
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
    addPageCrossPenalty(register.XR())
  }

  /** [$19] ORA $FFFF,Y */
  /** [$39] AND $FFFF,Y */
  /** [$59] EOR $FFFF,Y */
  private def opAbsoluteY(op: (ByteVal, ByteVal) => ByteVal) {
    register.AC = op(register.AC, get_arg_ABSY)
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
    addPageCrossPenalty(register.YR())
  }

  /** [$01] ORA ($FF,X) */
  /** [$21] AND ($FF,X) */
  /** [$41] EOR ($FF,X) */
  private def opIndirectX(op: (ByteVal, ByteVal) => ByteVal) {
    register.AC = op(register.AC, get_arg_INDX)
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
  }

  /** [$11] ORA ($FF),Y */
  /** [$31] AND ($FF),Y */
  /** [$51] EOR ($FF),Y */
  private def opIndirectY(op: (ByteVal, ByteVal) => ByteVal) {
    register.AC = op(register.AC, get_arg_INDY)
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
  }

  private def opADC(term: ByteVal) {
    val carry = if (register.getStatusFlag(CF)) 0x01 else 0x00
    val old = register.AC()
    val rhs = term & 0xff

    if (register.getStatusFlag(DF)) {
      val lo = (old & 0x0f) + (rhs & 0x0f) + carry
      val loDec = if (lo >= 0x0a) ((lo + 0x06) & 0x0f) + 0x10 else lo
      val sum = (old & 0xf0) + (rhs & 0xf0) + loDec
      val res = (old & 0xf0).toByte + (rhs & 0xf0).toByte + loDec.toByte
      register.testStatusFlag(ZF, (old + rhs + carry).toShort)
      register.testStatusFlag(SF, sum.toShort)
      val decSum = if (sum >= 0xa0) sum + 0x60 else sum
      register.testStatusFlag(CF, decSum.toShort)
      register.setStatusFlag(OF, (res < -128) || (res > 127))
      register.AC = decSum
    }
    else {
      val sum = old + rhs + carry
      register.testStatusFlag(ZF, sum.toShort)
      register.testStatusFlag(SF, sum.toShort)
      register.testStatusFlag(CF, sum.toShort)
      register.setStatusFlag(OF, (((old ^ rhs) & 0x80) != 0x80) && (((old ^ sum) & 0x80) == 0x80))
      register.AC = sum
    }
  }

  /** [$69] ADC #$FF */
  private def opImmediateADC {
    opADC(get_arg_IMM)
  }

  /** [$65] ADC $FF */
  private def opZeroPageADC {
    opADC(get_arg_ZP)
  }

  /** [$10] BPL *-1 */
  /** [$30] BMI *-1 */
  /** [$50] BVC *-1 */
  /** [$70] BVS *-1 */
  /** [$90] BCC *-1 */
  /** [$b0] BCS *-1 */
  /** [$d0] BNE *-1 */
  /** [$f0] BEQ *-1 */
  private def opRelative(expr: Boolean) {
    if (expr) {
      cycleCount += 1
      val offset = get_arg_REL.value
      val oldPCH = Util.word2Nibbles(register.PC)._2
      register.advancePC(offset)
      val newPCH = Util.word2Nibbles((register.PC + 2).toShort)._2
      if (oldPCH != newPCH)
        cycleCount += 1
    }
  }

  private def opCMP(accu: ByteVal, term: ByteVal) {
    val result = accu() - term()
    register.testStatusFlag(ZF, (result & 0xff).toShort)
    register.testStatusFlag(SF, result.toShort)
    register.setStatusFlag(CF, result >= 0x00)
  }

  /** [$c9] CMP #$FF */
  private def opImmediateCMP {
    opCMP(register.AC, get_arg_IMM)
  }

  private def opSBC(term: ByteVal) {
    if (register.getStatusFlag(DF)) {
      val carry = if (register.getStatusFlag(CF)) 0x00 else 0x01
      val old = register.AC()
      val src = term()
      val tmp = old - src - carry
      val tmp_a = (old & 0x0f) - (src & 0x0f) - carry
      val tmp_b = if ((tmp_a & 0x10) == 0x10)
        ((tmp_a - 0x06) & 0x0f) | ((old & 0xf0) - (src & 0xf0) - 0x10)
      else
        (tmp_a & 0x0f) | ((old & 0xf0) - (src & 0xf0))
      val tmp_c = if ((tmp_b & 0x100) == 0x100) tmp_b - 0x60 else tmp_b
      register.testStatusFlag(SF, tmp_c.toShort)
      register.setStatusFlag(CF, (tmp & 0x1ff) < 0x100)
      register.setStatusFlag(ZF, (tmp & 0xff) == 0x00)
      register.setStatusFlag(OF, (((old ^ tmp) & 0x80) == 0x80) && (((old ^ src) & 0x80) == 0x80))
      register.AC = tmp_c
    }
    else {
      val carry = if (register.getStatusFlag(CF)) 0x01 else 0x00
      val rhs = term & 0xff
      val old = register.AC()
      val diff = old - rhs - (carry ^ 0x01)
      register.testStatusFlag(ZF, diff.toShort)
      register.testStatusFlag(SF, diff.toShort)
      register.setStatusFlag(CF, diff <= 0xff && diff >= 0x00)
      register.setStatusFlag(OF, ((old ^ rhs).toByte & (old ^ diff).toByte & 0x80) == 0x80)
      register.AC = diff
    }
  }

  /** [$e9] SBC #$FF */
  private def opImmediateSBC {
    opSBC(get_arg_IMM)
  }

  /** [$e5] SBC $FF */
  private def opZeroPageSBC {
    opSBC(get_arg_ZP)
  }

  /** [$00] BRK */
  private def opBRK {
    register.setStatusFlag(BF, true)
    val (pcl, pch) = Util.word2Nibbles((register.PC + 2).toShort)
    register.push(memory, pch)
    register.push(memory, pcl)
    register.push(memory, register.status)
    register.setStatusFlag(IF, true)
    register.setPC(get_val_from_addr(0xfffe.toShort))
    register.advancePC(-OpCode_BRK_IMM.memSize) // additionally compensate for an advancement in "eval"
  }

  private def opASL(value: ByteVal): ByteVal = {
    val valueRolled = value() << 1
    register.testStatusFlag(ZF, valueRolled.toShort)
    register.testStatusFlag(SF, valueRolled.toShort)
    register.setStatusFlag(CF, (valueRolled & 0x0100) == 0x0100)
    valueRolled
  }

  private def opLSR(value: ByteVal): ByteVal = {
    register.setStatusFlag(CF, (value() & 0x01) == 0x01)
    val valueRolled = value() >> 1
    register.testStatusFlag(ZF, valueRolled.toShort)
    register.testStatusFlag(SF, valueRolled.toShort)
    valueRolled
  }

  private def opROL(value: ByteVal): ByteVal = {
    val carry = if (register.getStatusFlag(CF)) 0x01 else 0x00
    val valueRolled = (value() << 1) | carry
    register.testStatusFlag(ZF, valueRolled.toShort)
    register.testStatusFlag(SF, valueRolled.toShort)
    register.testStatusFlag(CF, valueRolled.toShort)
    valueRolled
  }

  private def opROR(value: ByteVal): ByteVal = {
    val carry = if (register.getStatusFlag(CF)) 0x0100 else 0x0000
    val valueToRoll = value() | carry
    register.setStatusFlag(CF, (valueToRoll & 0x0001) > 0)
    val valueRolled = valueToRoll >> 1
    register.testStatusFlag(ZF, valueRolled.toShort)
    register.testStatusFlag(SF, valueRolled.toShort)
    valueRolled
  }

  /** [$06] ASL $FF */
  /** [$26] ROL $FF */
  /** [$46] LSR $FF */
  /** [$66] ROR $FF */
  private def opZeroPage(op: (ByteVal) => ByteVal) {
    memory.write(get_addr_ZP, op(get_arg_ZP))
  }

  /** [$0a] ASL A */
  /** [$2a] ROL A */
  /** [$4a] LSR A */
  /** [$6a] ROR A */
  private def opAccumulator(op: (ByteVal) => ByteVal) {
    register.AC = op(register.AC)
  }

  /** [$0e] ASL $FFFF */
  /** [$2e] ROL $FFFF */
  /** [$4e] LSR $FFFF */
  /** [$6e] ROR $FFFF */
  private def opAbsolute(op: (ByteVal) => ByteVal) {
    memory.write(get_addr_ABS, op(get_arg_ABS))
  }

  /** [$16] ASL $FF,X */
  /** [$36] ROL $FF,X */
  /** [$56] LSR $FF,X */
  /** [$76] ROR $FF,X */
  private def opZeroPageX(op: (ByteVal) => ByteVal) {
    memory.write(get_addr_ZPX, op(get_arg_ZPX))
  }

  /** [$1e] ASL $FFFF,X */
  /** [$3e] ROL $FFFF,X */
  /** [$5e] LSR $FFFF,X */
  /** [$7e] ROR $FFFF,X */
  private def opAbsoluteX(op: (ByteVal) => ByteVal) {
    memory.write(get_addr_ABSX, op(get_arg_ABSX))
  }

  private def addPageCrossPenalty(offset: Int) {
    if (page_cross(get_addr_ABS, offset))
      cycleCount += 1
  }

  def eval(opCode: OpCode) {
    opCode match {
      case OpCode_BRK_IMM =>  // $00
        opBRK
      case OpCode_ORA_INDX => // $01
        opIndirectX(_ | _)
      case OpCode_ORA_ZP =>   // $05
        opZeroPage(_ | _)
      case OpCode_ASL_ZP =>   // $06
        opZeroPage(opASL(_))
      case OpCode_ORA_IMM =>  // $09
        opImmediate(_ | _)
      case OpCode_ASL_AC =>   // $0a
        opAccumulator(opASL(_))
      case OpCode_ORA_ABS =>  // $0d
        opAbsolute(_ | _)
      case OpCode_ASL_ABS =>  // $0e
        opAbsolute(opASL(_))
      case OpCode_BPL_REL =>  // $10
        opRelative(!register.getStatusFlag(SF))
      case OpCode_ORA_INDY => // $11
        opIndirectY(_ | _)
      case OpCode_ORA_ZPX =>  // $15
        opZeroPageX(_ | _)
      case OpCode_ASL_ZPX =>  // $16
        opZeroPageX(opASL(_))
      case OpCode_ORA_ABSY => // $19
        opAbsoluteY(_ | _)
      case OpCode_ORA_ABSX => // $1d
        opAbsoluteX(_ | _)
      case OpCode_ASL_ABSX => // $1e
        opAbsoluteX(opASL(_))
      case OpCode_AND_INDX => // $21
        opIndirectX(_ & _)
      case OpCode_AND_ZP =>   // $25
        opZeroPage(_ & _)
      case OpCode_ROL_ZP =>   // $26
        opZeroPage(opROL(_))
      case OpCode_AND_IMM =>  // $29
        opImmediate(_ & _)
      case OpCode_ROL_AC =>   // $2a
        opAccumulator(opROL(_))
      case OpCode_AND_ABS =>  // $2d
        opAbsolute(_ & _)
      case OpCode_ROL_ABS =>  // $2e
        opAbsolute(opROL(_))
      case OpCode_BMI_REL =>  // $30
        opRelative(register.getStatusFlag(SF))
      case OpCode_AND_INDY => // $31
        opIndirectY(_ & _)
      case OpCode_AND_ZPX =>  // $35
        opZeroPageX(_ & _)
      case OpCode_ROL_ZPX =>  // $36
        opZeroPageX(opROL(_))
      case OpCode_AND_ABSY => // $39
        opAbsoluteY(_ & _)
      case OpCode_AND_ABSX => // $3d
        opAbsoluteX(_ & _)
      case OpCode_ROL_ABSX => // $3e
        opAbsoluteX(opROL(_))
      case OpCode_EOR_INDX => // $41
        opIndirectX(_ ^ _)
      case OpCode_EOR_ZP =>   // $45
        opZeroPage(_ ^ _)
      case OpCode_LSR_ZP =>   // $46
        opZeroPage(opLSR(_))
      case OpCode_EOR_IMM =>  // $49
        opImmediate(_ ^ _)
      case OpCode_LSR_AC =>   // $4a
        opAccumulator(opLSR(_))
      case OpCode_EOR_ABS =>  // $4d
        opAbsolute(_ ^ _)
      case OpCode_LSR_ABS =>  // $4e
        opAbsolute(opLSR(_))
      case OpCode_BVC_REL =>  // $50
        opRelative(!register.getStatusFlag(OF))
      case OpCode_EOR_INDY => // $51
        opIndirectY(_ ^ _)
      case OpCode_EOR_ZPX =>  // $55
        opZeroPageX(_ ^ _)
      case OpCode_LSR_ZPX =>  // $56
        opZeroPageX(opLSR(_))
      case OpCode_EOR_ABSY => // $59
        opAbsoluteY(_ ^ _)
      case OpCode_EOR_ABSX => // $5d
        opAbsoluteX(_ ^ _)
      case OpCode_LSR_ABSX => // $5e
        opAbsoluteX(opLSR(_))
      case OpCode_ADC_ZP =>   // $65
        opZeroPageADC
      case OpCode_ROR_ZP =>   // $66
        opZeroPage(opROR(_))
      case OpCode_ADC_IMM =>  // $69
        opImmediateADC
      case OpCode_ROR_AC =>   // $6a
        opAccumulator(opROR(_))
      case OpCode_ROR_ABS =>  // $6e
        opAbsolute(opROR(_))
      case OpCode_BVS_REL =>  // $70
        opRelative(register.getStatusFlag(OF))
      case OpCode_ROR_ZPX =>  // $76
        opZeroPageX(opROR(_))
      case OpCode_ROR_ABSX => // $7e
        opAbsoluteX(opROR(_))
      case OpCode_BCC_REL =>  // $90
        opRelative(!register.getStatusFlag(CF))
      case OpCode_BCS_REL =>  // $b0
        opRelative(register.getStatusFlag(CF))
      case OpCode_CMP_IMM =>  // $c9
        opImmediateCMP
      case OpCode_BNE_REL =>  // $d0
        opRelative(!register.getStatusFlag(ZF))
      case OpCode_SBC_ZP =>   // $e5
        opZeroPageSBC
      case OpCode_SBC_IMM =>  // $e9
        opImmediateSBC
      case OpCode_BEQ_REL =>  // $f0
        opRelative(register.getStatusFlag(ZF))
      case _ =>
        throw NotImplementedError()
    }
    cycleCount += opCode.cycles
    register.advancePC(opCode.memSize)
  }

  /** Test for page cross */
  private def page_cross(address: Short, offset: ByteVal) = (address & 0xff) + offset >= 0x100
}
