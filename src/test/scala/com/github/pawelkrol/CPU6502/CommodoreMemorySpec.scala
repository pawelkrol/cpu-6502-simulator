package com.github.pawelkrol.CPU6502

class CommodoreMemorySpec extends FunFunSpec {

  private var memory: CommodoreMemory = _

  before {
    memory = CommodoreMemory()
  }

  context("/CharEn = 1, /HiRam = 1, /LoRam = 1 ($37)") { memory.write(0x01, 0x37) } {
    it("reads from Basic ROM at $8000-$BFFF") {
      expect { memory.write(0xa000, 0x60); memory.read(0xa000) }.toEqual(0x94)
    }

    it("reads from I/O ROM at $D000-$DFFF") {
      expect { memory.write(0xd000, 0x60); memory.read(0xd000) }.toEqual(0x60)
    }

    it("reads from Kernal ROM at $E000-$FFFF") {
      expect { memory.write(0xe000, 0x60); memory.read(0xe000) }.toEqual(0x85)
    }
  }

  context("/CharEn = 1, /HiRam = 1, /LoRam = 0 ($36)") { memory.write(0x01, 0x36) } {
    it("reads from RAM at $8000-$BFFF") {
      expect { memory.write(0xa000, 0x60); memory.read(0xa000) }.toEqual(0x60)
    }

    it("reads from I/O ROM at $D000-$DFFF") {
      expect { memory.write(0xd000, 0x60); memory.read(0xd000) }.toEqual(0x60)
    }

    it("reads from Kernal ROM at $E000-$FFFF") {
      expect { memory.write(0xe000, 0x60); memory.read(0xe000) }.toEqual(0x85)
    }
  }

  context("/CharEn = 1, /HiRam = 0, /LoRam = 1 ($35)") { memory.write(0x01, 0x35) } {
    it("reads from RAM at $8000-$BFFF") {
      expect { memory.write(0xa000, 0x60); memory.read(0xa000) }.toEqual(0x60)
    }

    it("reads from I/O ROM at $D000-$DFFF") {
      expect { memory.write(0xd000, 0x60); memory.read(0xd000) }.toEqual(0x60)
    }

    it("reads from RAM at $E000-$FFFF") {
      expect { memory.write(0xe000, 0x60); memory.read(0xe000) }.toEqual(0x60)
    }
  }

  context("/CharEn = 1, /HiRam = 0, /LoRam = 0 ($34)") { memory.write(0x01, 0x34) } {
    it("reads from RAM at $8000-$BFFF") {
      expect { memory.write(0xa000, 0x60); memory.read(0xa000) }.toEqual(0x60)
    }

    it("reads from RAM at $D000-$DFFF") {
      expect { memory.write(0xd000, 0x60); memory.read(0xd000) }.toEqual(0x60)
    }

    it("reads from RAM at $E000-$FFFF") {
      expect { memory.write(0xe000, 0x60); memory.read(0xe000) }.toEqual(0x60)
    }
  }

  context("/CharEn = 0, /HiRam = 1, /LoRam = 1 ($33)") { memory.write(0x01, 0x33) } {
    it("reads from Basic ROM at $8000-$BFFF") {
      expect { memory.write(0xa000, 0x60); memory.read(0xa000) }.toEqual(0x94)
    }

    it("reads from Charset Generator ROM at $D000-$DFFF") {
      expect { memory.write(0xd000, 0x60); memory.read(0xd000) }.toEqual(0x3c)
    }

    it("reads from Kernal ROM at $E000-$FFFF") {
      expect { memory.write(0xe000, 0x60); memory.read(0xe000) }.toEqual(0x85)
    }
  }

  context("/CharEn = 0, /HiRam = 1, /LoRam = 0 ($32)") { memory.write(0x01, 0x32) } {
    it("reads from RAM at $8000-$BFFF") {
      expect { memory.write(0xa000, 0x60); memory.read(0xa000) }.toEqual(0x60)
    }

    it("reads from Charset Generator ROM at $D000-$DFFF") {
      expect { memory.write(0xd000, 0x60); memory.read(0xd000) }.toEqual(0x3c)
    }

    it("reads from Kernal ROM at $E000-$FFFF") {
      expect { memory.write(0xe000, 0x60); memory.read(0xe000) }.toEqual(0x85)
    }
  }

  context("/CharEn = 0, /HiRam = 0, /LoRam = 1 ($31)") { memory.write(0x01, 0x31) } {
    it("reads from RAM at $8000-$BFFF") {
      expect { memory.write(0xa000, 0x60); memory.read(0xa000) }.toEqual(0x60)
    }

    it("reads from Charset Generator ROM at $D000-$DFFF") {
      expect { memory.write(0xd000, 0x60); memory.read(0xd000) }.toEqual(0x3c)
    }

    it("reads from RAM at $E000-$FFFF") {
      expect { memory.write(0xe000, 0x60); memory.read(0xe000) }.toEqual(0x60)
    }
  }

  context("/CharEn = 0, /HiRam = 0, /LoRam = 0 ($30)") { memory.write(0x01, 0x30) } {
    it("reads from RAM at $8000-$BFFF") {
      expect { memory.write(0xa000, 0x60); memory.read(0xa000) }.toEqual(0x60)
    }

    it("reads from RAM at $D000-$DFFF") {
      expect { memory.write(0xd000, 0x60); memory.read(0xd000) }.toEqual(0x60)
    }

    it("reads from RAM at $E000-$FFFF") {
      expect { memory.write(0xe000, 0x60); memory.read(0xe000) }.toEqual(0x60)
    }
  }

  describe("VIC") {
    it("set I/O defaults") {
      expect { memory.read(0xd000, 0x30) }.toEqual(Seq[ByteVal](
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x00, 0x9b, 0x37, 0x00, 0x00, 0x00, 0x08, 0x00, 0x14, 0x0f, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x0e, 0x06, 0x01, 0x02, 0x03, 0x04, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x4c, 0x4f
      ))
    }
  }
}
