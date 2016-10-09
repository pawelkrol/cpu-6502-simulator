package com.github.pawelkrol.CPU6502
package Operations

class AddWithCarrySpec extends FunOperationsSpec {

  describe("add memory to accumulator with carry") {
    describe("immediate addressing mode") {
      testOpCode(OpCode_ADC_IMM) {
        it { expect { operation }.toAdvancePC(0x02) }
        it { expect { operation }.toUseCycles(0x02) }

        context("with decimal flag") { DF = true; OF = false } {
          context("AC = $00") { AC = 0x00; ZF = true; SF = false } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x09) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x10) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x99) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x02) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x10) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x11) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x00) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }
            }
          }

          context("AC = $01") { AC = 0x01; ZF = false; SF = false } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x02) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x10) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x11) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x00) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x02) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x03) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x11) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x12) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x01) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }
            }
          }

          context("AC = $09") { AC = 0x09; ZF = false; SF = false } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x09).to(0x10) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x09).to(0x18) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x09).to(0x19) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x09).to(0x08) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x09).to(0x10) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x09).to(0x11) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x09).to(0x19) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x09).to(0x20) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x09).to(0x09) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }
            }
          }

          context("AC = $10") { AC = 0x10; ZF = false; SF = false } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x10).to(0x11) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x10).to(0x19) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x10).to(0x20) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x10).to(0x09) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x10).to(0x11) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x10).to(0x12) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x10).to(0x20) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x10).to(0x21) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x10).to(0x10) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }
            }
          }

          context("AC = $99") { AC = 0x99; ZF = false; SF = false } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x99).to(0x00) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x99).to(0x08) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x99).to(0x09) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x99).to(0x98) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.notToChange { SF } }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x99).to(0x00) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x99).to(0x01) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$09") { assignOpArg(0x09) } {
                it { expect { operation }.toChange { AC }.from(0x99).to(0x09) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$10") { assignOpArg(0x10) } {
                it { expect { operation }.toChange { AC }.from(0x99).to(0x10) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$99") { assignOpArg(0x99) } {
                it { expect { operation }.toChange { AC }.from(0x99).to(0x99) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.notToChange { SF } }
              }
            }
          }
        }

        context("without decimal flag") { DF = false; OF = false } {
          context("AC = $00") { AC = 0x00; ZF = true; SF = false } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x7f) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x80) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0xff) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x01) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x02) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x80) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x81) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.toChange { ZF }.from(true).to(false) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.toChange { AC }.from(0x00).to(0x00) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }
            }
          }

          context("AC = $01") { AC = 0x01; ZF = false; SF = false } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x01) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x02) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x80) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x81) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x00) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.toChange { ZF }.from(false).to(true) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x02) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x03) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x81) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0x01).to(0x82) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }
            }
          }

          context("AC = $7f") { AC = 0x7f; ZF = false; SF = false } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x7f).to(0x80) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0x7f).to(0xfe) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0x7f).to(0xff) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.toChange { AC }.from(0x7f).to(0x7e) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x7f).to(0x80) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x7f).to(0x81) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0x7f).to(0xff) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(false).to(true) }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0x7f).to(0x00) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(false).to(true) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }
            }
          }

          context("AC = $80") { AC = 0x80; ZF = false; SF = true } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x80).to(0x81) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0x80).to(0xff) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0x80).to(0x00) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.toChange { ZF }.from(false).to(true) }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.toChange { AC }.from(0x80).to(0x7f) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0x80).to(0x81) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0x80).to(0x82) }
                it { expect { operation }.toChange { CF }.from(true).to(false) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0x80).to(0x00) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(false).to(true) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0x80).to(0x01) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }
            }
          }

          context("AC = $ff") { AC = 0xff; ZF = false; SF = true } {
            context("without carry flag") { CF = false } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0xff).to(0x00) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.toChange { ZF }.from(false).to(true) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0xff).to(0x7e) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0xff).to(0x7f) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.toChange { OF }.from(false).to(true) }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.toChange { AC }.from(0xff).to(0xfe) }
                it { expect { operation }.toChange { CF }.from(false).to(true) }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }
            }

            context("with carry flag") { CF = true } {
              context("ADC #$00") { assignOpArg(0x00) } {
                it { expect { operation }.toChange { AC }.from(0xff).to(0x00) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.toChange { ZF }.from(false).to(true) }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }

              context("ADC #$01") { assignOpArg(0x01) } {
                it { expect { operation }.toChange { AC }.from(0xff).to(0x01) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }

              context("ADC #$7f") { assignOpArg(0x7f) } {
                it { expect { operation }.toChange { AC }.from(0xff).to(0x7f) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.toChange { SF }.from(true).to(false) }
              }

              context("ADC #$80") { assignOpArg(0x80) } {
                it { expect { operation }.toChange { AC }.from(0xff).to(0x80) }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }

              context("ADC #$ff") { assignOpArg(0xff) } {
                it { expect { operation }.notToChange { AC } }
                it { expect { operation }.notToChange { CF } }
                it { expect { operation }.notToChange { ZF } }
                it { expect { operation }.notToChange { OF } }
                it { expect { operation }.notToChange { SF } }
              }
            }
          }
        }
      }
    }

    describe("zeropage addressing mode") {
      testOpCode(OpCode_ADC_ZP) {
        // TODO
      }
    }

    describe("zeropage,x addressing mode") {
      testOpCode(OpCode_ADC_ZPX) {
        // TODO
      }
    }

    describe("absolute addressing mode") {
      testOpCode(OpCode_ADC_ABS) {
        // TODO
      }
    }

    describe("absolute,x addressing mode") {
      testOpCode(OpCode_ADC_ABSX) {
        // TODO
      }
    }

    describe("absolute,y addressing mode") {
      testOpCode(OpCode_ADC_ABSY) {
        // TODO
      }
    }

    describe("(indirect,x) addressing mode") {
      testOpCode(OpCode_ADC_INDX) {
        // TODO
      }
    }

    describe("(indirect),y addressing mode") {
      testOpCode(OpCode_ADC_INDY) {
        // TODO
      }
    }
  }
}
