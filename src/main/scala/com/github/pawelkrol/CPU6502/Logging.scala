package com.github.pawelkrol.CPU6502

import com.typesafe.scalalogging.StrictLogging

trait Logging extends StrictLogging {

  val core: Core

  protected var verbose = false

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

    logger.debug(registers)
  }
}
