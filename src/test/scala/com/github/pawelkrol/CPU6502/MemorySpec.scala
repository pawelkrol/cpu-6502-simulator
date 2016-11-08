package com.github.pawelkrol.CPU6502

class MemorySpec extends FunFunSpec {

  private var memory: Memory = _

  before {
    memory = Memory()
  }

  describe("get_val_from_addr") {
    context("$1001 = $1D; $1002 = $10") { memory.write(0x1001, 0x1d); memory.write(0x1002, 0x10) } {
      it("reads a word from a memory location") {
        expect { memory.get_val_from_addr(0x1001.toShort) }.toEqual(0x101d.toShort)
      }
    }

    context("$C000 = $00; $C001 = $C8") { memory.write(0xc000, 0x00); memory.write(0xc001, 0xc8) } {
      it("reads a word from a memory location") {
        expect { memory.get_val_from_addr(0xc000.toShort) }.toEqual(0xc800.toShort)
      }
    }
  }
}
