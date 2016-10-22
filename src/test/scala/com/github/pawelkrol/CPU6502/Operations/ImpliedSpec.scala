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
        case OpCode_SEC =>
          executeSharedExamples("carry", () => CF, (value) => CF = value, flagValue)
        case OpCode_CLD =>
        case OpCode_SED =>
          executeSharedExamples("decimal", () => DF, (value) => DF = value, flagValue)
        case OpCode_CLI =>
        case OpCode_SEI =>
          executeSharedExamples("interrupt", () => IF, (value) => IF = value, flagValue)
        case OpCode_CLV =>
          executeSharedExamples("overflow", () => OF, (value) => OF = value, flagValue)
      }
    }
  }

  describe("implied addressing mode") {
    includeSharedExamples(OpCode_CLC, false)
    includeSharedExamples(OpCode_CLD, false)
    includeSharedExamples(OpCode_CLI, false)
    includeSharedExamples(OpCode_CLV, false)
    includeSharedExamples(OpCode_SEC, true)
    includeSharedExamples(OpCode_SED, true)
    includeSharedExamples(OpCode_SEI, true)
  }
}
