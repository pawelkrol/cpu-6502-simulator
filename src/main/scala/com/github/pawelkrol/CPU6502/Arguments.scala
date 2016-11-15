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
    // TODO
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
