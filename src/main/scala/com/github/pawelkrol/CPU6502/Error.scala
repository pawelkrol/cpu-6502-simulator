package com.github.pawelkrol.CPU6502

class Error(message: String) extends RuntimeException(message)

class NotImplementedError(message: String) extends Error(message)

object NotImplementedError {

  def apply() = new NotImplementedError("not implemented")
}