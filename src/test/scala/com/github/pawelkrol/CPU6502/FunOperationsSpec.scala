package com.github.pawelkrol.CPU6502

trait FunOperationsSpec extends FunFunSpec {

  private var memory: Memory = _
  private var register: Register = _
  private var core: Core = _

  var opCode: OpCode = _

  def assignOpArg(values: ByteVal*) = opCode match {
    case op: OpCode_IMM => memory.write(0xc001, values(0))
    case op: OpCode_ZP => memory.write(0xc001, values(0)).write(values(0), values(1))
    case op: OpCode_ZPX => memory.write(0xc001, values(0)).write(values(0) + values(1)(), values(2))
    case op: OpCode_ABS => memory.write(0xc001, values(0)).write(0xc002, values(1)).write(Util.byteVals2Addr(values.take(2)), values(2))
    case op: OpCode_ABSX => memory.write(0xc001, values(0)).write(0xc002, values(1)).write(Util.byteVals2Addr(values.take(2)) + values(2)(), values(3))
    case op: OpCode_ABSY => memory.write(0xc001, values(0)).write(0xc002, values(1)).write(Util.byteVals2Addr(values.take(2)) + values(2)(), values(3))
    case op: OpCode_INDX => memory.write(0xc001, values(0)).write(values(0) + values(1)(), values(2)).write(values(0) + values(1)() + 1, values(3)).write(Util.byteVals2Addr(values.drop(2).take(2)), values(4))
    case op: OpCode_INDY => memory.write(0xc001, values(0)).write(values(0), values(2)).write(values(0) + 1, values(3)).write(Util.byteVals2Addr(values.drop(2).take(2)) + values(1), values(4))
    case op: OpCode_REL => memory.write(0xc001, values(0))
    case op: OpCode_AC =>
    case _ => throw new RuntimeException("invalid opcode: " + opCode)
  }

  before {
    memory = Memory()
    register = Register(0x00, 0x00, 0x00, 0x20, 0xf9, 0xc000)
    core = Core(memory, register)
  }

  def operation { core.eval(opCode) }

  def testOpCode(op: OpCode)(test: => Any) {
    context(op.toString) { opCode = op } { test }
  }

  def AC = register.AC

  def AC_=(value: ByteVal) { register.AC = value }

  def XR = register.XR

  def XR_=(value: ByteVal) { register.XR = value }

  def YR = register.YR

  def YR_=(value: ByteVal) { register.YR = value }

  def SP = register.SP

  def SP_=(value: ByteVal) { register.SP = value }

  private def getStatusFlag(flag: Status.Flag) = register.getStatusFlag(flag)

  def CF = getStatusFlag(Status.CF)

  def CF_=(value: Boolean) { register.setStatusFlag(Status.CF, value) }

  def ZF = getStatusFlag(Status.ZF)

  def ZF_=(value: Boolean) { register.setStatusFlag(Status.ZF, value) }

  def IF = getStatusFlag(Status.IF)

  def IF_=(value: Boolean) { register.setStatusFlag(Status.IF, value) }

  def DF = getStatusFlag(Status.DF)

  def DF_=(value: Boolean) { register.setStatusFlag(Status.DF, value) }

  def BF = getStatusFlag(Status.BF)

  def BF_=(value: Boolean) { register.setStatusFlag(Status.BF, value) }

  def OF = getStatusFlag(Status.OF)

  def OF_=(value: Boolean) { register.setStatusFlag(Status.OF, value) }

  def SF = getStatusFlag(Status.SF)

  def SF_=(value: Boolean) { register.setStatusFlag(Status.SF, value) }

  class ExtendedExpectation[T](code: => T) extends Expectation[T](code) {
    def toAdvancePC(offset: Short) = {
      val value = (register.PC + offset).toShort
      new ChangeValidator(code, register.PC).to(value)
    }

    def toSetPC(address: Short) = {
      new ChangeValidator(code, register.PC).to(address)
    }

    def toUseCycles(offset: Short) = {
      val value = (core.cycleCount + offset).toShort
      new ChangeValidator(code, core.cycleCount).to(value)
    }
  }

  override def expect[T](code: => T) = new ExtendedExpectation(code)

  def memoryRead(address: Int): ByteVal = memoryRead(address.toShort)

  def memoryRead(address: Short): ByteVal = memory.read(address)

  def memoryWrite(address: Int, value: ByteVal) { memoryWrite(address.toShort, value) }

  def memoryWrite(address: Short, value: ByteVal) { memory.write(address, value) }
}
