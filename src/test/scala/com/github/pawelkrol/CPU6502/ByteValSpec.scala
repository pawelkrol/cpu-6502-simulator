package com.github.pawelkrol.CPU6502

class ByteValSpec extends FunFunSpec {

  var byteVal: ByteVal = _

  describe("byte value addition") {
    describe("byte value $00") {
      before { byteVal = 0x00 }

      it { expect { byteVal + ByteVal(0x00) }.toEqual(ByteVal(0x00)) }
      it { expect { byteVal + ByteVal(0x01) }.toEqual(ByteVal(0x01)) }
      it { expect { byteVal + ByteVal(0x80) }.toEqual(ByteVal(0x80)) }
    }
  }

  describe("byte value subtraction") {
    describe("byte value $00") {
      before { byteVal = 0x00 }

      it { expect { byteVal - ByteVal(0x00) }.toEqual(ByteVal(0x00)) }
      it { expect { byteVal - ByteVal(0x01) }.toEqual(ByteVal(0xff)) }
      it { expect { byteVal - ByteVal(0x80) }.toEqual(ByteVal(0x80)) }
    }
  }

  describe("binary AND operator") {
    describe("byte value $00") {
      before { byteVal = 0x00 }

      it { expect { byteVal & 0x00 }.toEqual(0x00) }
      it { expect { byteVal & 0x01 }.toEqual(0x00) }
      it { expect { byteVal & 0x80 }.toEqual(0x00) }
    }

    describe("byte value $01") {
      before { byteVal = 0x01 }

      it { expect { byteVal & 0x00 }.toEqual(0x00) }
      it { expect { byteVal & 0x01 }.toEqual(0x01) }
      it { expect { byteVal & 0x80 }.toEqual(0x00) }
    }

    describe("byte value $80") {
      before { byteVal = 0x80 }

      it { expect { byteVal & 0x00 }.toEqual(0x00) }
      it { expect { byteVal & 0x01 }.toEqual(0x00) }
      it { expect { byteVal & 0x80 }.toEqual(0x80) }
    }
  }

  describe("binary OR operator") {
    describe("byte value $00") {
      before { byteVal = 0x00 }

      it { expect { byteVal | 0x00 }.toEqual(0x00) }
      it { expect { byteVal | 0x01 }.toEqual(0x01) }
      it { expect { byteVal | 0x80 }.toEqual(0x80) }
    }

    describe("byte value $01") {
      before { byteVal = 0x01 }

      it { expect { byteVal | 0x00 }.toEqual(0x01) }
      it { expect { byteVal | 0x01 }.toEqual(0x01) }
      it { expect { byteVal | 0x80 }.toEqual(0x81) }
    }

    describe("byte value $80") {
      before { byteVal = 0x80 }

      it { expect { byteVal | 0x00 }.toEqual(0x80) }
      it { expect { byteVal | 0x01 }.toEqual(0x81) }
      it { expect { byteVal | 0x80 }.toEqual(0x80) }
    }
  }

  describe("binary XOR operator") {
    describe("byte value $00") {
      before { byteVal = 0x00 }

      it { expect { byteVal ^ 0x00 }.toEqual(0x00) }
      it { expect { byteVal ^ 0x01 }.toEqual(0x01) }
      it { expect { byteVal ^ 0x80 }.toEqual(0x80) }
    }

    describe("byte value $01") {
      before { byteVal = 0x01 }

      it { expect { byteVal ^ 0x00 }.toEqual(0x01) }
      it { expect { byteVal ^ 0x01 }.toEqual(0x00) }
      it { expect { byteVal ^ 0x80 }.toEqual(0x81) }
    }

    describe("byte value $80") {
      before { byteVal = 0x80 }

      it { expect { byteVal ^ 0x00 }.toEqual(0x80) }
      it { expect { byteVal ^ 0x01 }.toEqual(0x81) }
      it { expect { byteVal ^ 0x80 }.toEqual(0x00) }
    }
  }

  describe("binary ones complement operator") {
    describe("byte value $00") {
      before { byteVal = 0x00 }

      it { expect { ~byteVal }.toEqual(0xff) }
    }

    describe("byte value $01") {
      before { byteVal = 0x01 }

      it { expect { ~byteVal }.toEqual(0xfe) }
    }

    describe("byte value $80") {
      before { byteVal = 0x80 }

      it { expect { ~byteVal }.toEqual(0x7f) }
    }
  }

  describe("binary left shift operator") {
    describe("byte value $00") {
      before { byteVal = 0x00 }

      it { expect { byteVal << 0x00 }.toEqual(0x00) }
      it { expect { byteVal << 0x01 }.toEqual(0x00) }
      it { expect { byteVal << 0x07 }.toEqual(0x00) }
    }

    describe("byte value $01") {
      before { byteVal = 0x01 }

      it { expect { byteVal << 0x00 }.toEqual(0x01) }
      it { expect { byteVal << 0x01 }.toEqual(0x02) }
      it { expect { byteVal << 0x07 }.toEqual(0x80) }
    }

    describe("byte value $80") {
      before { byteVal = 0x80 }

      it { expect { byteVal << 0x00 }.toEqual(0x80) }
      it { expect { byteVal << 0x01 }.toEqual(0x00) }
      it { expect { byteVal << 0x07 }.toEqual(0x00) }
    }
 }

  describe("binary right shift operator") {
    describe("byte value $00") {
      before { byteVal = 0x00 }

      it { expect { byteVal >> 0x00 }.toEqual(0x00) }
      it { expect { byteVal >> 0x01 }.toEqual(0x00) }
      it { expect { byteVal >> 0x07 }.toEqual(0x00) }
    }

    describe("byte value $01") {
      before { byteVal = 0x01 }

      it { expect { byteVal >> 0x00 }.toEqual(0x01) }
      it { expect { byteVal >> 0x01 }.toEqual(0x00) }
      it { expect { byteVal >> 0x07 }.toEqual(0x00) }
    }

    describe("byte value $80") {
      before { byteVal = 0x80 }

      it { expect { byteVal >> 0x00 }.toEqual(0x80) }
      it { expect { byteVal >> 0x01 }.toEqual(0xc0) }
      it { expect { byteVal >> 0x07 }.toEqual(0xff) }
    }
  }

  describe("shift right zero fill operator") {
    describe("byte value $00") {
      before { byteVal = 0x00 }

      it { expect { byteVal >>> 0x00 }.toEqual(0x00) }
      it { expect { byteVal >>> 0x01 }.toEqual(0x00) }
      it { expect { byteVal >>> 0x07 }.toEqual(0x00) }
    }

    describe("byte value $01") {
      before { byteVal = 0x01 }

      it { expect { byteVal >>> 0x00 }.toEqual(0x01) }
      it { expect { byteVal >>> 0x01 }.toEqual(0x00) }
      it { expect { byteVal >>> 0x07 }.toEqual(0x00) }
    }

    describe("byte value $80") {
      before { byteVal = 0x80 }

      it { expect { byteVal >>> 0x00 }.toEqual(0x80) }
      it { expect { byteVal >>> 0x01 }.toEqual(0x40) }
      it { expect { byteVal >>> 0x07 }.toEqual(0x01) }
    }
  }

  describe("implicit conversions") {
    describe("integer to byte value") {
      before { byteVal = 0x7f }

      it { expect { byteVal + 0x00 }.toEqual(0x7f) }
      it { expect { byteVal + 0x01 }.toEqual(0x80) }
      it { expect { byteVal + 0x02 }.toEqual(0x81) }
      it { expect { byteVal + 0x80 }.toEqual(0xff) }
    }

    describe("byte value to integer") {
      before { byteVal = 0x7f }

      it { expect { 0x00 + byteVal }.toEqual(0x7f) }
      it { expect { 0x01 + byteVal }.toEqual(0x80) }
      it { expect { 0x02 + byteVal }.toEqual(0x81) }
      it { expect { 0x80 + byteVal }.toEqual(0xff) }
    }

    describe("integer equality test") {
      describe("byte value $00") {
        before { byteVal = 0x00 }

        it { expect { byteVal == 0x00 }.toEqual(true) }
        it { expect { byteVal == 0x01 }.toEqual(false) }
      }

      describe("byte value $01") {
        before { byteVal = 0x01 }

        it { expect { byteVal == 0x00 }.toEqual(false) }
        it { expect { byteVal == 0x01 }.toEqual(true) }
      }

      describe("byte value $80") {
        before { byteVal = 0x80 }

        it { expect { byteVal == 0x00 }.toEqual(false) }
        it { expect { byteVal == 0x80 }.toEqual(true) }
      }
    }
  }
}
