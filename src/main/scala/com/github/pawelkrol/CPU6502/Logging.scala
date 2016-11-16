package com.github.pawelkrol.CPU6502

import com.typesafe.scalalogging.StrictLogging

trait Logging extends StrictLogging {

  val core: Core

  val log = logger

  protected var verbose = false

  def logInstruction(opCode: OpCode) {
    val register = core.register

    val bytes = opCode.bytes.map(byte => "%02X".format(byte())).mkString(" ")
    val instruction = ".C:%04X %-11s %s %-10s - A:%02X X:%02X Y:%02X SP:%02X %s".format(
      register.PC,
      bytes,
      opCode.symName,
      opCode.argValue,
      register.AC(),
      register.XR(),
      register.YR(),
      register.SP(),
      register.statusFlags
    )

    log.info(instruction)

    if (verbose)
      println(instruction)
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

  def logInfo(message: String) {
    log.info(message)

    if (verbose)
      println(message)
  }

  def logWarning(message: String) {
    log.warn(message)

    println(message)
  }
}
