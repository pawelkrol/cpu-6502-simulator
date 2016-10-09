package com.github.pawelkrol.CPU6502

case class Arguments(name: String = "World") {

  def validate {
    // name shall consist of alphabetical characters only
    if (!name.matches("\\A[\\p{Alpha}\\p{Space}]+\\Z")) {
      throw new IllegalArgumentException("Error: Invalid name: %s\n".format(name))
    }
  }
}
