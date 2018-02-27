package com.github.pawelkrol.CPU6502

import java.io.ByteArrayOutputStream

import scala.Console.withOut
import scala.util.matching.Regex

class LoggingSpec extends FunOperationsSpec {

  private def testBranchLog(pc: Int, opArg: Int) {
    val address = "$%04X".format(pc + opArg)
    val offset =
      if (opArg < 0x00)
        "*-$%02x".format((~(opArg & 0xff) + 0x01) & 0xff)
      else
        "*+$%02x".format(opArg & 0xff)

    context("PC = $%04X".format(pc)) { core.register.PC = pc.toShort } {
      context("BNE " + offset) { core.memory.write(pc + 0x0001, opArg - 0x02) } {
        it("properly logs an argument value for a relative opcode") {
          val stream = new ByteArrayOutputStream
          Application.verbose = true
          withOut(stream) {
            Application.logInstruction(opCode, core)
          }
          val regex = "BNE \\%s".format(address).r.unanchored
          assert(stream.toString match {
            case regex(_*) => true
            case _ => false
          })
        }
      }
    }
  }

  describe("logInstruction") {
    testOpCode(OpCode_BNE_REL) {
      testBranchLog(0x7fff, -0x01)
      testBranchLog(0x7fff, 0x00)
      testBranchLog(0x7fff, 0x01)
      testBranchLog(0x7fff, 0x02)
      testBranchLog(0x7fff, 0x03)
      testBranchLog(0x8000, -0x01)
      testBranchLog(0x8000, 0x00)
      testBranchLog(0x8000, 0x01)
      testBranchLog(0x8000, 0x02)
      testBranchLog(0x8000, 0x03)
    }
  }
}
