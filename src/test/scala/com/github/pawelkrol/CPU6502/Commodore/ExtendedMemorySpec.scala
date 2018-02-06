package com.github.pawelkrol.CPU6502
package Commodore

class ExtendedMemorySpec extends FunFunSpec {

  private var memory: ExtendedMemory = _

  before {
    memory = ExtendedMemory()
  }

  describe("shared memory space ($0000-$0fff)") {
    it("flipping memory banks does not affect target memory selection") {
      memory.write(0x0fff, 0x01)
      memory.write(0xd100, 0x80)
      expect { memory.read(0x0fff) }.toEqual(0x01)
      memory.write(0x0fff, 0x02)
      memory.write(0xd100, 0x00)
      expect { memory.read(0x0fff) }.toEqual(0x02)
    }
  }

  describe("special memory space ($1000-$ffff)") {
    it("flipping memory banks affects target memory selection") {
      memory.write(0x1000, 0x01)
      memory.write(0xd100, 0x80)
      expect { memory.read(0x1000) }.toEqual(0xff)
      memory.write(0x1000, 0x02)
      memory.write(0xd100, 0x00)
      expect { memory.read(0x1000) }.toEqual(0x01)
      memory.write(0x1000, 0x03)
      memory.write(0xd100, 0x80)
      expect { memory.read(0x1000) }.toEqual(0x02)
    }

    it("control register always returns $ff on read") {
      memory.write(0xd100, 0x80)
      expect { memory.read(0xd100) }.toEqual(0xff)
      memory.write(0xd100, 0x00)
      expect { memory.read(0xd100) }.toEqual(0xff)
    }
  }
}
