package com.github.pawelkrol.CPU6502

class Error(message: String) extends RuntimeException(message)

class NotImplementedError(message: String) extends Error(message)

object NotImplementedError {

  def apply() = new NotImplementedError("Not implemented")
}

class IllegalOpCodeError(message: String) extends Error(message)

object IllegalOpCodeError {

  def apply(value: ByteVal) =
    new IllegalOpCodeError("Illegal opcode $%02x at address $%04x".format(value(), Application.core.register.PC))
}
