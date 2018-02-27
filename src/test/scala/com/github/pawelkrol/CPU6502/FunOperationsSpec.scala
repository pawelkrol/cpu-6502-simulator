package com.github.pawelkrol.CPU6502

trait FunOperationsSpec extends FunFunSpec {

  private var memory: Memory = _
  private var register: Register = _
  protected var core: Core = _

  var opCode: OpCode = _

  protected var zp: ByteVal = _

  protected var cycleCount: Map[OpCode, Int] = _

  def assignOpArg(values: ByteVal*) = opCode match {
    case op: OpCode_IMM => memory.write(0xc001, values(0))
    case op: OpCode_ZP => memory.write(0xc001, values(0)).write(values(0), values(1))
    case op: OpCode_ZPX => memory.write(0xc001, values(0)).write(values(0) + values(1)(), values(2))
    case op: OpCode_ZPY => memory.write(0xc001, values(0)).write(values(0) + values(1)(), values(2))
    case op: OpCode_ABS => memory.write(0xc001, values(0)).write(0xc002, values(1)).write(Util.byteVals2Addr(values.take(2)), values(2))
    case op: OpCode_ABSX => memory.write(0xc001, values(0)).write(0xc002, values(1)).write(Util.byteVals2Addr(values.take(2)) + values(2)(), values(3))
    case op: OpCode_ABSY => memory.write(0xc001, values(0)).write(0xc002, values(1)).write(Util.byteVals2Addr(values.take(2)) + values(2)(), values(3))
    case op: OpCode_INDX => memory.write(0xc001, values(0)).write(values(0) + values(1)(), values(2)).write(values(0) + values(1)() + 1, values(3)).write(Util.byteVals2Addr(values.drop(2).take(2)), values(4))
    case op: OpCode_INDY => memory.write(0xc001, values(0)).write(values(0), values(2)).write(values(0) + 1, values(3)).write(Util.byteVals2Addr(values.drop(2).take(2)) + values(1), values(4))
    case op: OpCode_REL => memory.write(0xc001, values(0))
    case op: OpCode_IND => {
      val target = Util.byteVals2Addr(values.take(2))
      memory.write(0xc001, values(0)).write(0xc002, values(1)).write(target, values(2)).write(target + 1, values(3))
    }
    case op: OpCode_AC =>
    case _ => throw new RuntimeException("invalid opcode: " + opCode)
  }

  before {
    memory = SimpleMemory()
    register = Register(0x00, 0x00, 0x00, 0x20, 0xf9, 0xc000)
    core = Core(memory, register)
  }

  def operation { core.eval(opCode) }

  def testOpCode(op: OpCode)(test: => Any) {
    context(op.toString) { opCode = op } { test }
  }

  def testOpCode(op: OpCode, memSize: Short, cycles: Short)(test: => Any) {
    testOpCode(op) {
      it("advances PC by " + memSize + " byte(s)") { expect { operation }.toAdvancePC(memSize) }
      it("uses " + cycles + " CPU cycles") { expect { operation }.toUseCycles(cycles) }
      test
    }
  }

  def AC = register.AC

  def AC_=(value: ByteVal) { register.AC = value }

  def XR = register.XR

  def XR_=(value: ByteVal) { register.XR = value }

  def YR = register.YR

  def YR_=(value: ByteVal) { register.YR = value }

  def SR = register.status

  def SP = register.SP

  def SP_=(value: ByteVal) { register.SP = value }

  def PC = register.PC

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

    def toSetSR(value: ByteVal) = {
      new ChangeValidator(code, register.status).to(value)
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

  protected def setupAbsOpArg(address: Short, value: ByteVal) { assignOpArg((Util.addr2ByteVals(address) :+ value): _*) }

  protected def setupAbsIndexedOpArg(address: Short, index: ByteVal, value: ByteVal) { assignOpArg((Util.addr2ByteVals(address) :+ index :+ value): _*) }

  protected def setupIndirectOpArg(zp: ByteVal, index: ByteVal, zpAddr: Short, value: ByteVal) { assignOpArg((zp +: index +: Util.addr2ByteVals(zpAddr) :+ value): _*) }

  protected def assertCycleCount(cycles: Int) {
    it("uses " + cycles + " CPU cycles") { expect { operation }.toUseCycles(cycles.toShort) }
  }

  protected def pageBoundaryCrossCheck(opCode: OpCode, symName: String) {
    opCode match {
      case _: OpCode_ABSX => {
        describe("absolute,x addressing mode") {
          assertPageBoundaryCycleCount(opCode) { (address, offset, assertionCallback) =>
            context("XR = $%02X".format(offset)) { XR = offset } {
              context("%s $%04X,X".format(symName, address)) { setupAbsIndexedOpArg(address.toShort, offset, 0xff) } {
                assertionCallback()
              }
            }
          }
        }
      }
      case _: OpCode_ABSY => {
        describe("absolute,y addressing mode") {
          assertPageBoundaryCycleCount(opCode) { (address, offset, assertionCallback) =>
            context("YR = $%02X".format(offset)) { YR = offset } {
              context("%s $%04X,Y".format(symName, address)) { setupAbsIndexedOpArg(address.toShort, offset, 0xff) } {
                assertionCallback()
              }
            }
          }
        }
      }
      case _: OpCode_INDY => {
        describe("(indirect),y addressing mode") {
          assertPageBoundaryCycleCount(opCode) { (address, offset, assertionCallback) =>
            context("YR = $%02X".format(offset)) { YR = offset } {
              context("%s ($%02X),Y".format(symName, address)) { setupIndirectOpArg(zp.toShort, offset, address.toShort, 0xff) } {
                assertionCallback()
              }
            }
          }
        }
      }
    }
  }

  protected def assertPageBoundaryCycleCount(op: OpCode)(setupCallback: (Int, Int, () => Unit) => Unit) {
    describe("cycle count when page boundary is crossed") {
      val extraCycleCount = Map[Tuple2[Int, Int], Int](
        (0xc800, 0x00) -> 0,
        (0xc800, 0x01) -> 0,
        (0xc800, 0xff) -> 0,
        (0xc801, 0x00) -> 0,
        (0xc801, 0x01) -> 0,
        (0xc801, 0xff) -> 1,
        (0xc8ff, 0x00) -> 0,
        (0xc8ff, 0x01) -> 1,
        (0xc8ff, 0xff) -> 1
      )

      testOpCode(op) {
        extraCycleCount.foreach({ case (data, extraCycles) =>
          val (address, offset) = data
          setupCallback(address, offset, () => {
            assertCycleCount(cycleCount(op) + extraCycles)
          })
        })
      }
    }
  }
}
