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

  private def opROL(value: ByteVal): ByteVal = {
    val carry = if (register.getStatusFlag(CF)) 0x01 else 0x00
    val valueRolled = (value() << 1) | carry
    register.testStatusFlag(ZF, valueRolled.toShort)
    register.testStatusFlag(SF, valueRolled.toShort)
    register.testStatusFlag(CF, valueRolled.toShort)
    valueRolled
  }

  /** [$2a] ROL A */
  private def opAccumulatorROL {
    register.AC = opROL(register.AC)
  }

  /** [$26] ROL $FF */
  private def opZeroPageROL {
    memory.write(get_addr_ZP, opROL(get_arg_ZP))
  }

  /** [$36] ROL $FF,X */
  private def opZeroPageXROL {
    memory.write(get_addr_ZPX, opROL(get_arg_ZPX))
  }

  /** [$2e] ROL $FFFF */
  private def opAbsoluteROL {
    memory.write(get_addr_ABS, opROL(get_arg_ABS))
  }

  /** [$3e] ROL $FFFF,X */
  private def opAbsoluteXROL {
    memory.write(get_addr_ABSX, opROL(get_arg_ABSX))
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

  /** [$66] ROR $FF */
  private def opZeroPageROR {
    memory.write(get_addr_ZP, opROR(get_arg_ZP))
  }

  /** [$6a] ROR A */
  private def opAccumulatorROR {
    register.AC = opROR(register.AC)
  }

  /** [$6e] ROR $FFFF */
  private def opAbsoluteROR {
    memory.write(get_addr_ABS, opROR(get_arg_ABS))
  }

  /** [$76] ROR $FF,X */
  private def opZeroPageXROR {
    memory.write(get_addr_ZPX, opROR(get_arg_ZPX))
  }

  /** [$7e] ROR $FFFF,X */
  private def opAbsoluteXROR {
    memory.write(get_addr_ABSX, opROR(get_arg_ABSX))
  }

  private def addPageCrossPenalty(offset: Int) {
    if (page_cross(get_addr_ABS, offset))
      cycleCount += 1
  }

  def eval(opCode: OpCode) {
    opCode match {
      case OpCode_ORA_INDX =>
        opIndirectX(_ | _)
      case OpCode_ORA_ZP =>
        opZeroPage(_ | _)
      case OpCode_ORA_IMM =>
        opImmediate(_ | _)
      case OpCode_ORA_ABS =>
        opAbsolute(_ | _)
      case OpCode_BPL_REL =>
        opRelative(!register.getStatusFlag(SF))
      case OpCode_ORA_INDY =>
        opIndirectY(_ | _)
      case OpCode_ORA_ZPX =>
        opZeroPageX(_ | _)
      case OpCode_ORA_ABSY =>
        opAbsoluteY(_ | _)
      case OpCode_ORA_ABSX =>
        opAbsoluteX(_ | _)
      case OpCode_AND_INDX =>
        opIndirectX(_ & _)
      case OpCode_AND_ZP =>
        opZeroPage(_ & _)
      case OpCode_ROL_ZP =>
        opZeroPageROL
      case OpCode_AND_IMM =>
        opImmediate(_ & _)
      case OpCode_ROL_AC =>
        opAccumulatorROL
      case OpCode_AND_ABS =>
        opAbsolute(_ & _)
      case OpCode_ROL_ABS =>
        opAbsoluteROL
      case OpCode_BMI_REL =>
        opRelative(register.getStatusFlag(SF))
      case OpCode_AND_INDY =>
        opIndirectY(_ & _)
      case OpCode_ROL_ZPX =>
        opZeroPageXROL
      case OpCode_AND_ZPX =>
        opZeroPageX(_ & _)
      case OpCode_AND_ABSY =>
        opAbsoluteY(_ & _)
      case OpCode_AND_ABSX =>
        opAbsoluteX(_ & _)
      case OpCode_ROL_ABSX =>
        opAbsoluteXROL
      case OpCode_EOR_INDX =>
        opIndirectX(_ ^ _)
      case OpCode_EOR_ZP =>
        opZeroPage(_ ^ _)
      case OpCode_EOR_IMM =>
        opImmediate(_ ^ _)
      case OpCode_EOR_ABS =>
        opAbsolute(_ ^ _)
      case OpCode_BVC_REL =>
        opRelative(!register.getStatusFlag(OF))
      case OpCode_EOR_INDY =>
        opIndirectY(_ ^ _)
      case OpCode_EOR_ZPX =>
        opZeroPageX(_ ^ _)
      case OpCode_EOR_ABSY =>
        opAbsoluteY(_ ^ _)
      case OpCode_EOR_ABSX =>
        opAbsoluteX(_ ^ _)
      case OpCode_ROR_ZP =>
        opZeroPageROR
      case OpCode_ADC_IMM =>
        opImmediateADC
      case OpCode_ROR_AC =>
        opAccumulatorROR
      case OpCode_ROR_ABS =>
        opAbsoluteROR
      case OpCode_BVS_REL =>
        opRelative(register.getStatusFlag(OF))
      case OpCode_ROR_ZPX =>
        opZeroPageXROR
      case OpCode_ROR_ABSX =>
        opAbsoluteXROR
      case OpCode_BCC_REL =>
        opRelative(!register.getStatusFlag(CF))
      case OpCode_BCS_REL =>
        opRelative(register.getStatusFlag(CF))
      case OpCode_CMP_IMM =>
        opImmediateCMP
      case OpCode_BNE_REL =>
        opRelative(!register.getStatusFlag(ZF))
      case OpCode_BEQ_REL =>
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
