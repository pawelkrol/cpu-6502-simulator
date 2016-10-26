package com.github.pawelkrol.CPU6502

import com.typesafe.scalalogging.StrictLogging

import Status._

abstract class Operation(memory: Memory, register: Register) extends StrictLogging {

  var cycleCount = 0

  private def get_val_from_addr(zp: Short) = Util.byteVals2Addr(Seq(memory.read(zp), memory.read(zp + 1)))

  private def get_addr_ABS = get_val_from_addr((register.PC + 1).toShort)

  private def get_addr_ABSX = get_addr_ABS + register.XR()

  private def get_addr_ABSY = get_addr_ABS + register.YR()

  private def get_addr_ZP = memory.read(register.PC + 1)

  private def get_addr_ZPX = get_addr_ZP + register.XR()

  private def get_addr_ZPY = get_addr_ZP + register.YR()

  private def get_addr_INDX = get_val_from_addr(get_addr_ZPX)

  private def get_addr_INDY = (get_val_from_addr(get_addr_ZP) + register.YR()).toShort

  private def get_arg_IMM = memory.read(register.PC + 1)

  private def get_arg_ZP = memory.read(get_addr_ZP)

  private def get_arg_ZPX = memory.read(get_addr_ZPX)

  private def get_arg_ABS = memory.read(get_addr_ABS)

  private def get_arg_ABSX = memory.read(get_addr_ABSX)

  private def get_arg_ABSY = memory.read(get_addr_ABS + register.YR())

  private def get_arg_INDX = memory.read(get_addr_INDX)

  private def get_arg_INDY = memory.read(get_addr_INDY)

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

  private def pushProgramCounterToStack {
    val (pcl, pch) = Util.word2Nibbles((register.PC + 2).toShort)
    register.push(memory, pch)
    register.push(memory, pcl)
  }

  /** [$00] BRK */
  private def opBRK {
    register.setStatusFlag(BF, true)
    pushProgramCounterToStack
    register.push(memory, register.status)
    register.setStatusFlag(IF, true)
    register.setPC(get_val_from_addr(0xfffe.toShort))
    register.advancePC(-OpCode_BRK_IMM.memSize) // additionally compensate for an advancement in "eval"
  }

  /** [$20] JSR $FFFF */
  private def opJSR {
    val address = get_addr_ABS
    pushProgramCounterToStack
    register.setPC(address)
    register.advancePC(-OpCode_JSR_ABS.memSize) // additionally compensate for an advancement in "eval"
  }

  /** [$4c] JMP $FFFF */
  private def opAbsoluteJMP {
    register.setPC(get_addr_ABS)
    register.advancePC(-OpCode_JMP_ABS.memSize) // additionally compensate for an advancement in "eval"
  }

  /** [$6c] JMP ($FFFF) */
  private def opIndirectJMP {
    val pc = register.PC
    val lo = get_addr_ABS
    val hi = (lo & 0xff00) | ((lo + 1) & 0x00ff)
    register.setPC(get_arg_ABS() | (memory.read(hi)() << 8))
    if (hi.toShort != (lo + 1).toShort)
      logger.info("6502 indirect jump bug triggered at $%04x, indirect address = $%04x".format(pc, lo))
    register.advancePC(-OpCode_JMP_IND.memSize) // additionally compensate for an advancement in "eval"
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

  /** [$08] PHP */
  private def opPHP {
    register.push(memory, register.status & ~BF.srBits)
  }

  /** [$28] PLP */
  private def opPLP {
    register.status = register.pop(memory) & ~BF.srBits
  }

  /** [$48] PHA */
  private def opPHA {
    register.push(memory, register.AC)
  }

  /** [$68] PLA */
  private def opPLA {
    val valuePopped = register.pop(memory)
    register.AC = valuePopped
    register.testStatusFlag(ZF, valuePopped.toShort)
    register.testStatusFlag(SF, valuePopped.toShort)
  }

  /** [$18] CLC */
  /** [$58] CLI */
  /** [$b8] CLV */
  /** [$d8] CLD */
  private def opClearFlag(flag: Flag) {
    register.setStatusFlag(flag, false)
  }

  /** [$38] SEC */
  /** [$78] SEI */
  /** [$f8] SED */
  private def opSetFlag(flag: Flag) {
    register.setStatusFlag(flag, true)
  }

  /** [$24] BIT $FF */
  /** [$2c] BIT $FFFF */
  private def opBIT(bits: ByteVal) {
    register.setStatusFlag(SF, (bits & 0x80) == 0x80)
    register.setStatusFlag(OF, (bits & 0x40) == 0x40)
    register.setStatusFlag(ZF, (bits & register.AC) == 0x00)
  }

  private def pullProgramCounterFromStack {
    val pcl = register.pop(memory)
    val pch = register.pop(memory)
    register.PC = Util.nibbles2Word(pcl, pch)
  }

  /** [$40] RTI */
  private def opRTI {
    register.status = register.pop(memory)
    pullProgramCounterFromStack
    register.advancePC(-OpCode_RTI.memSize) // additionally compensate for an advancement in "eval"
  }

  /** [$60] RTS */
  private def opRTS {
    pullProgramCounterFromStack
    register.advancePC(-OpCode_RTS.memSize) // additionally compensate for an advancement in "eval"
  }

  /** [$81] STA ($FF,X) */
  /** [$85] STA $FF */
  /** [$8d] STA $FFFF */
  /** [$91] STA ($FF),Y */
  /** [$95] STA $FF,X */
  /** [$99] STA $FFFF,Y */
  /** [$9d] STA $FFFF,X */
  private def opSTA(address: => Short) {
    memory.write(address, register.AC)
  }

  /** [$84] STY $FF */
  /** [$8c] STY $FFFF */
  /** [$94] STY $FF,X */
  private def opSTY(address: => Short) {
    memory.write(address, register.YR)
  }

  /** [$86] STX $FF */
  /** [$8e] STX $FFFF */
  /** [$96] STX $FF,Y */
  private def opSTX(address: => Short) {
    memory.write(address, register.XR)
  }

  /** [$88] DEY */
  private def opDEY {
    register.YR -= 1
    register.testStatusFlag(ZF, register.YR)
    register.testStatusFlag(SF, register.YR)
  }

  /** [$ca] DEX */
  private def opDEX {
    register.XR -= 1
    register.testStatusFlag(ZF, register.XR)
    register.testStatusFlag(SF, register.XR)
  }

  /** [$c8] INY */
  private def opINY {
    register.YR += 1
    register.testStatusFlag(ZF, register.YR)
    register.testStatusFlag(SF, register.YR)
  }

  /** [$e8] INX */
  private def opINX {
    register.XR += 1
    register.testStatusFlag(ZF, register.XR)
    register.testStatusFlag(SF, register.XR)
  }

  /** [$8a] TXA */
  private def opTXA {
    register.AC = register.XR
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
  }

  /** [$98] TYA */
  private def opTYA {
    register.AC = register.YR
    register.testStatusFlag(ZF, register.AC)
    register.testStatusFlag(SF, register.AC)
  }

  /** [$9a] TXS */
  private def opTXS {
    register.SP = register.XR
  }

  /** [$a8] TAY */
  private def opTAY {
    register.YR = register.AC
    register.testStatusFlag(ZF, register.YR)
    register.testStatusFlag(SF, register.YR)
  }

  /** [$aa] TAX */
  private def opTAX {
    register.XR = register.AC
    register.testStatusFlag(ZF, register.XR)
    register.testStatusFlag(SF, register.XR)
  }

  /** [$ba] TSX */
  private def opTSX {
    register.XR = register.SP
    register.testStatusFlag(ZF, register.XR)
    register.testStatusFlag(SF, register.XR)
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
      case OpCode_PHP =>      // $08
        opPHP
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
      case OpCode_CLC =>      // $18
        opClearFlag(CF)
      case OpCode_ORA_ABSY => // $19
        opAbsoluteY(_ | _)
      case OpCode_ORA_ABSX => // $1d
        opAbsoluteX(_ | _)
      case OpCode_ASL_ABSX => // $1e
        opAbsoluteX(opASL(_))
      case OpCode_JSR_ABS =>  // $20
        opJSR
      case OpCode_AND_INDX => // $21
        opIndirectX(_ & _)
      case OpCode_BIT_ZP =>   // $24
        opBIT(get_arg_ZP)
      case OpCode_AND_ZP =>   // $25
        opZeroPage(_ & _)
      case OpCode_ROL_ZP =>   // $26
        opZeroPage(opROL(_))
      case OpCode_PLP =>      // $28
        opPLP
      case OpCode_AND_IMM =>  // $29
        opImmediate(_ & _)
      case OpCode_ROL_AC =>   // $2a
        opAccumulator(opROL(_))
      case OpCode_BIT_ABS =>  // $2c
        opBIT(get_arg_ABS)
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
      case OpCode_SEC =>      // $38
        opSetFlag(CF)
      case OpCode_AND_ABSY => // $39
        opAbsoluteY(_ & _)
      case OpCode_AND_ABSX => // $3d
        opAbsoluteX(_ & _)
      case OpCode_ROL_ABSX => // $3e
        opAbsoluteX(opROL(_))
      case OpCode_RTI =>      // $40
        opRTI
      case OpCode_EOR_INDX => // $41
        opIndirectX(_ ^ _)
      case OpCode_EOR_ZP =>   // $45
        opZeroPage(_ ^ _)
      case OpCode_LSR_ZP =>   // $46
        opZeroPage(opLSR(_))
      case OpCode_PHA =>      // $48
        opPHA
      case OpCode_EOR_IMM =>  // $49
        opImmediate(_ ^ _)
      case OpCode_LSR_AC =>   // $4a
        opAccumulator(opLSR(_))
      case OpCode_JMP_ABS =>  // $4c
        opAbsoluteJMP
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
      case OpCode_CLI =>      // $58
        opClearFlag(IF)
      case OpCode_EOR_ABSY => // $59
        opAbsoluteY(_ ^ _)
      case OpCode_EOR_ABSX => // $5d
        opAbsoluteX(_ ^ _)
      case OpCode_LSR_ABSX => // $5e
        opAbsoluteX(opLSR(_))
      case OpCode_RTS =>      // $60
        opRTS
      case OpCode_ADC_ZP =>   // $65
        opZeroPageADC
      case OpCode_ROR_ZP =>   // $66
        opZeroPage(opROR(_))
      case OpCode_PLA =>      // $68
        opPLA
      case OpCode_ADC_IMM =>  // $69
        opImmediateADC
      case OpCode_ROR_AC =>   // $6a
        opAccumulator(opROR(_))
      case OpCode_JMP_IND =>  // $6c
        opIndirectJMP
      case OpCode_ROR_ABS =>  // $6e
        opAbsolute(opROR(_))
      case OpCode_BVS_REL =>  // $70
        opRelative(register.getStatusFlag(OF))
      case OpCode_ROR_ZPX =>  // $76
        opZeroPageX(opROR(_))
      case OpCode_SEI =>      // $78
        opSetFlag(IF)
      case OpCode_ROR_ABSX => // $7e
        opAbsoluteX(opROR(_))
      case OpCode_STA_INDX => // $81
        opSTA(get_addr_INDX)
      case OpCode_STY_ZP =>   // $84
        opSTY(get_addr_ZP)
      case OpCode_STA_ZP =>   // $85
        opSTA(get_addr_ZP)
      case OpCode_STX_ZP =>   // $86
        opSTX(get_addr_ZP)
      case OpCode_DEY =>      // $88
        opDEY
      case OpCode_TXA =>      // $8a
        opTXA
      case OpCode_STY_ABS =>  // $8c
        opSTY(get_addr_ABS)
      case OpCode_STA_ABS =>  // $8d
        opSTA(get_addr_ABS)
      case OpCode_STX_ABS =>  // $8e
        opSTX(get_addr_ABS)
      case OpCode_BCC_REL =>  // $90
        opRelative(!register.getStatusFlag(CF))
      case OpCode_STA_INDY => // $91
        opSTA(get_addr_INDY)
      case OpCode_STY_ZPX =>  // $94
        opSTY(get_addr_ZPX)
      case OpCode_STA_ZPX =>  // $95
        opSTA(get_addr_ZPX)
      case OpCode_STX_ZPY =>  // $96
        opSTX(get_addr_ZPY)
      case OpCode_TYA =>      // $98
        opTYA
      case OpCode_STA_ABSY => // $99
        opSTA(get_addr_ABSY.toShort)
      case OpCode_TXS =>      // $9a
        opTXS
      case OpCode_STA_ABSX => // $9d
        opSTA(get_addr_ABSX.toShort)
      case OpCode_TAY =>      // $a8
        opTAY
      case OpCode_TAX =>      // $aa
        opTAX
      case OpCode_BCS_REL =>  // $b0
        opRelative(register.getStatusFlag(CF))
      case OpCode_CLV =>      // $b8
        opClearFlag(OF)
      case OpCode_TSX =>      // $ba
        opTSX
      case OpCode_INY =>      // $c8
        opINY
      case OpCode_CMP_IMM =>  // $c9
        opImmediateCMP
      case OpCode_DEX =>      // $ca
        opDEX
      case OpCode_BNE_REL =>  // $d0
        opRelative(!register.getStatusFlag(ZF))
      case OpCode_CLD =>      // $d8
        opClearFlag(DF)
      case OpCode_SBC_ZP =>   // $e5
        opZeroPageSBC
      case OpCode_INX =>      // $e8
        opINX
      case OpCode_SBC_IMM =>  // $e9
        opImmediateSBC
      case OpCode_BEQ_REL =>  // $f0
        opRelative(register.getStatusFlag(ZF))
      case OpCode_SED =>      // $f8
        opSetFlag(DF)
      case _ =>
        throw NotImplementedError()
    }
    cycleCount += opCode.cycles
    register.advancePC(opCode.memSize)
  }

  /** Test for page cross */
  private def page_cross(address: Short, offset: ByteVal) = (address & 0xff) + offset >= 0x100
}
