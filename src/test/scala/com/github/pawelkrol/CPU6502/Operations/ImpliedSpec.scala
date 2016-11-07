package com.github.pawelkrol.CPU6502
package Operations

class ImpliedSpec extends FunOperationsSpec {

  private def setupShareExamples(flag: String) {
    sharedExamples(flag, (args) => {
      val fetchValue: () => Boolean = args(0).asInstanceOf[() => Boolean]
      val expectedValue: Boolean = args(1).asInstanceOf[Boolean]

      val opType = if (expectedValue) "sets" else "clears"

      it(opType + " " + flag + " flag") {
        expect { operation }.toChange { fetchValue() }.to(expectedValue)
      }
    })
  }

  setupShareExamples("carry")
  setupShareExamples("decimal")
  setupShareExamples("interrupt")
  setupShareExamples("overflow")

  sharedExamples("no operation", (args) => {
    val flags: List[() => Boolean] = List(() => CF, () => ZF, () => IF, () => DF, () => BF, () => OF, () => SF)
    val registers: List[() => ByteVal] = List(() => AC, () => XR, () => YR, () => SR, () => SP)

    it("does not change registers") {
      registers.foreach(fetchRegister =>
        expect { operation }.notToChange { println("fetched value="+fetchRegister()); fetchRegister() }
      )
    }

    it("does not change flags") {
      flags.foreach(fetchFlag =>
        expect { operation }.notToChange { println("fetched flag="+fetchFlag()); fetchFlag() }
      )
    }
  })

  private def executeSharedExamples(flagName: String, reader: () => Boolean, writer: (Boolean) => Unit, flagValue: => Boolean) {
    context(flagName + " = false") { writer(false) } {
      includeExamples(flagName, List[Any](reader, flagValue))
    }

    context(flagName + " = true") { writer(true) } {
      includeExamples(flagName, List[Any](reader, flagValue))
    }
  }

  private def includeSharedExamples(opCode: OpCode, flagValue: => Boolean) {
    testOpCode(opCode, memSize = 1, cycles = 2) {
      val flagName = opCode match {
        case OpCode_CLC =>
          executeSharedExamples("carry", () => CF, (value) => CF = value, flagValue)
        case OpCode_SEC =>
          executeSharedExamples("carry", () => CF, (value) => CF = value, flagValue)
        case OpCode_CLD =>
          executeSharedExamples("decimal", () => DF, (value) => DF = value, flagValue)
        case OpCode_SED =>
          executeSharedExamples("decimal", () => DF, (value) => DF = value, flagValue)
        case OpCode_CLI =>
          executeSharedExamples("interrupt", () => IF, (value) => IF = value, flagValue)
        case OpCode_SEI =>
          executeSharedExamples("interrupt", () => IF, (value) => IF = value, flagValue)
        case OpCode_CLV =>
          executeSharedExamples("overflow", () => OF, (value) => OF = value, flagValue)
        case OpCode_NOP =>
          includeExamples("no operation", List[Any]())
      }
    }
  }

  describe("implied addressing mode") {
    includeSharedExamples(OpCode_CLC, false)
    includeSharedExamples(OpCode_CLD, false)
    includeSharedExamples(OpCode_CLI, false)
    includeSharedExamples(OpCode_CLV, false)
    includeSharedExamples(OpCode_NOP, true)
    includeSharedExamples(OpCode_SEC, true)
    includeSharedExamples(OpCode_SED, true)
    includeSharedExamples(OpCode_SEI, true)
  }
}
