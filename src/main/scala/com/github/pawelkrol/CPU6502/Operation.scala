package com.github.pawelkrol.CPU6502

import Status._

abstract class Operation(memory: Memory, register: Register) {

  var cycleCount = 0

  private def get_val_from_addr(zp: Short) = Util.byteVals2Addr(Seq(memory.read(zp), memory.read(zp + 1)))

  private def get_addr_ABS = get_val_from_addr((register.PC + 1).toShort)

  private def get_addr_ZP = memory.read(register.PC + 1)

  private def get_addr_ZPX = get_addr_ZP + register.XR()

  private def get_addr_INDX = get_val_from_addr(get_addr_ZPX)

  private def get_addr_INDY = get_val_from_addr(get_addr_ZP)

  private def get_arg_IMM = memory.read(register.PC + 1)

  private def get_arg_ZP = memory.read(get_addr_ZP)

  private def get_arg_ZPX = memory.read(get_addr_ZPX)

  private def get_arg_ABS = memory.read(get_addr_ABS)

  private def get_arg_ABSX = memory.read(get_addr_ABS + register.XR())

  private def get_arg_ABSY = memory.read(get_addr_ABS + register.YR())

  private def get_arg_INDX = memory.read(get_addr_INDX)

  private def get_arg_INDY = memory.read(get_addr_INDY + register.YR())

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
      case OpCode_AND_IMM =>
        opImmediate(_ & _)
      case OpCode_AND_ABS =>
        opAbsolute(_ & _)
      case OpCode_AND_INDY =>
        opIndirectY(_ & _)
      case OpCode_AND_ZPX =>
        opZeroPageX(_ & _)
      case OpCode_AND_ABSY =>
        opAbsoluteY(_ & _)
      case OpCode_AND_ABSX =>
        opAbsoluteX(_ & _)
      case OpCode_EOR_INDX =>
        opIndirectX(_ ^ _)
      case OpCode_EOR_ZP =>
        opZeroPage(_ ^ _)
      case OpCode_EOR_IMM =>
        opImmediate(_ ^ _)
      case OpCode_EOR_ABS =>
        opAbsolute(_ ^ _)
      case OpCode_EOR_INDY =>
        opIndirectY(_ ^ _)
      case OpCode_EOR_ZPX =>
        opZeroPageX(_ ^ _)
      case OpCode_EOR_ABSY =>
        opAbsoluteY(_ ^ _)
      case OpCode_EOR_ABSX =>
        opAbsoluteX(_ ^ _)
      case _ =>
        throw NotImplementedError()
    }
    cycleCount += opCode.cycles
    register.advancePC(opCode.memSize)
  }

  /** Test for page cross */
  private def page_cross(address: Short, offset: ByteVal) = (address & 0xff) + offset >= 0x100
}
