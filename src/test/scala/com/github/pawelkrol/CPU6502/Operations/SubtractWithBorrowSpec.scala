package com.github.pawelkrol.CPU6502
package Operations

class SubtractWithBorrowSpec extends ArithmeticSpec {

  cycleCount = Map[OpCode, Int](
    OpCode_SBC_ABSX -> 4,
    OpCode_SBC_ABSY -> 4,
    OpCode_SBC_INDY -> 5
  )

  protected def setupSharedExamples {
    sharedExamples("SBC", (args) => {
      val carry: Boolean = args(0).asInstanceOf[Boolean]
      val decimal: Boolean = args(1).asInstanceOf[Boolean]
      val accumulator: Int = args(2).asInstanceOf[Int]
      val fetchValue: () => Int = args(3).asInstanceOf[() => Int]

      val argumentValue = fetchValue()

      val message = "(Minuend [accumulator] = $%02x, Subtrahend = $%02x, CF = %s, DF = %s) => assert ".format(accumulator, argumentValue, carry, decimal)

      decimal match {
        case false => {
          accumulator match {
            case 0x00 => {
              carry match {
                case false => {
                  // without decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xff) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xfe) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x80) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7f) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
                case true => {
                  // without decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xff) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x81) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x80) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x01) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
              }
            }
            case 0x01 => {
              carry match {
                case false => {
                  // without decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xff) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x81) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x80) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x01) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
                case true => {
                  // without decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x01) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x82) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x81) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x02) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
              }
            }
            case 0x7f => {
              carry match {
                case false => {
                  // without decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7e) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7d) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xff) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xfe) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7f) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
                case true => {
                  // without decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7f) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7e) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xff) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x80) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                  }
                }
              }
            }
            case 0x80 => {
              carry match {
                case false => {
                  // without decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7f) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7e) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xff) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x80) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                  }
                }
                case true => {
                  // without decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x80) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7f) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x01) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x81) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                  }
                }
              }
            }
            case 0xff => {
              carry match {
                case false => {
                  // without decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xfe) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xfd) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7f) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(true) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7e) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xff) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                  }
                }
                case true => {
                  // without decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xff) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0xfe) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x7f => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x80) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x80 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x7f) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0xff => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
              }
            }
          }
        }
        case true => {
          accumulator match {
            case 0x00 => {
              carry match {
                case false => {
                  // with decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x99) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x98) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x90) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x89) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
                case true => {
                  // with decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x99) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x91) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x90) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x01) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
              }
            }
            case 0x01 => {
              carry match {
                case false => {
                  // with decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x99) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x91) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x90) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x01) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
                case true => {
                  // with decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x01) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x92) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x91) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x02) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
              }
            }
            case 0x09 => {
              carry match {
                case false => {
                  // with decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x08) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x07) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x99) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x98) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x09) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
                case true => {
                  // with decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x09) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x08) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x99) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x10) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
              }
            }
            case 0x10 => {
              carry match {
                case false => {
                  // with decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x09) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x08) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x99) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x10) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
                case true => {
                  // with decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x10) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x09) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x01) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x11) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
              }
            }
            case 0x99 => {
              carry match {
                case false => {
                  // with decimal flag, without carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x98) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x97) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x89) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x88) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x99) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(false) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                  }
                }
                case true => {
                  // with decimal flag, with carry flag
                  argumentValue match {
                    case 0x00 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x99) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x01 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x98) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x09 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x90) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x10 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x89) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(false) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(true) }
                    }
                    case 0x99 => {
                      it(message + "AC") { expect { operation }.toChange { AC }.to(0x00) }
                      it(message + "CF") { expect { operation }.toChange { CF }.to(true) }
                      it(message + "ZF") { expect { operation }.toChange { ZF }.to(true) }
                      it(message + "OF") { expect { operation }.toChange { OF }.to(false) }
                      it(message + "SF") { expect { operation }.toChange { SF }.to(false) }
                    }
                  }
                }
              }
            }
          }
        }
      }
    })
  }

  describe("add memory to accumulator with carry") {
    applySharedExamples("SBC", OpCode_SBC_IMM)
    applySharedExamples("SBC", OpCode_SBC_ZP)
    applySharedExamples("SBC", OpCode_SBC_ZPX)
    applySharedExamples("SBC", OpCode_SBC_ABS)
    applySharedExamples("SBC", OpCode_SBC_ABSX)
    applySharedExamples("SBC", OpCode_SBC_ABSY)
    applySharedExamples("SBC", OpCode_SBC_INDX)
    applySharedExamples("SBC", OpCode_SBC_INDY)

    pageBoundaryCrossCheck(OpCode_SBC_ABSX, "SBC")
    pageBoundaryCrossCheck(OpCode_SBC_ABSY, "SBC")
    pageBoundaryCrossCheck(OpCode_SBC_INDY, "SBC")
  }
}
