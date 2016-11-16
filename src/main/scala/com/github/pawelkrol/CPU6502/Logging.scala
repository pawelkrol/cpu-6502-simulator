package com.github.pawelkrol.CPU6502

import com.typesafe.scalalogging.StrictLogging

trait Logging extends StrictLogging {

  val core: Core

  val log = logger

  protected var verbose = false

  def logInstruction(opCode: OpCode) {
    val register = core.register

    // TODO
    // val instruction = ".C:0902 8D 20 D0    STA $D020      - A:00 X:FF Y:FF SP:F9 ..-...Z."

    val bytes = opCode.bytes.map(byte => "%02X".format(byte())).mkString(" ")
    val instruction = ".C:%04X %-11s %s %-10s".format(
      register.PC,
      bytes,
      opCode.symName,
      opCode.argValue
    )

    log.info(instruction)
  }

  def logRegisters {
    val memory = core.memory
    val register = core.register

    val registers = "\n  ADDR AC XR YR SP 00 01 NV-BDIZC\n.;%04x %02x %02x %02x %02x %02x %02x %s".format(
      register.PC,
      register.AC(),
      register.XR(),
      register.YR(),
      register.SP(),
      memory.read(0x0000)(),
      memory.read(0x0001)(),
      Util.binaryString(register.status)
    )

    log.debug(registers)
  }
}
