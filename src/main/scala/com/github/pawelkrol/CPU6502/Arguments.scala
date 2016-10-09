package com.github.pawelkrol.CPU6502

import java.io.File

case class Arguments(
  cycleCount: Option[Int] = None,
  file: Option[File] = None,
  startAddress: Option[Int] = None,
  verbose: Boolean = false
) {

  def validate {
    // startAddress shall be a 16-bit hexadecimal number
    startAddress match {
      case Some(addr) =>
        if (addr < 0x0000 || addr >= Memory.size)
          throw new IllegalArgumentException("Error: invalid start address: $%04x".format(addr))
      case None =>
    }

    // file shall exist
    file match {
      case Some(f) =>
        if (!f.exists)
          throw new IllegalArgumentException("Error: program file does not exist: %s".format(f.getCanonicalPath))
      case None =>
        throw new IllegalArgumentException("Error: missing program file")
    }
  }
}
