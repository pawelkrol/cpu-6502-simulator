package com.github.pawelkrol.CPU6502
package Status

trait Register {

  protected var _SR: ByteVal = _

  /** Return the flag as a boolean value (true/false)
   *
   * @param flag Status flag
   * @return `true` if a status flag is set, otherwise `false`
   */
  def getStatusFlag(flag: Flag) = (_SR & flag.srBits) != 0x00

  /** Set/clear the flag
   *
   * @param flag Status flag
   * @param status A boolean value (true/false) that says if a status flag is set or cleared
   * @return a new set of registers with a status flag set to a new value
   */
  def setStatusFlag(flag: Flag, status: Boolean): Unit = { _SR = if (status) _SR | flag.srBits else _SR & ~flag.srBits }

  def testStatusFlag(flag: Flag, value: Short): Unit = {
    val status = flag match {
      case ZF => (value & 0xff) == 0
      case SF => (value & 0x80) != 0
      case CF => (value & 0xFF00) != 0
    }
    setStatusFlag(flag, status)
  }

  def statusFlags = {
    val flags = List[Tuple2[Flag, String]](
      SF -> "N", OF -> "V", BF -> "B", DF -> "D", IF -> "I", ZF -> "Z", CF -> "C"
    ).map({ case (flag, letter) =>
      if (getStatusFlag(flag)) letter else "."
    })
    ((flags.take(2) :+ "-") ++ flags.drop(2)).mkString
  }
}
