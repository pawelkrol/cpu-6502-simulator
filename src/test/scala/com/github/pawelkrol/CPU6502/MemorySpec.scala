package com.github.pawelkrol.CPU6502

class MemorySpec extends FunFunSpec {

  private var memory: Memory = _

  before {
    memory = Memory()
  }

  describe("get_val_from_addr") {
    context("$1001 = $1D; $1002 = $10") { memory.write(0x1001, 0x1d, 0x10) } {
      it("reads a word from a memory location") {
        expect { memory.get_val_from_addr(0x1001.toShort) }.toEqual(0x101d.toShort)
      }
    }

    context("$C000 = $00; $C001 = $C8") { memory.write(0xc000, 0x00, 0xc8) } {
      it("reads a word from a memory location") {
        expect { memory.get_val_from_addr(0xc000.toShort) }.toEqual(0xc800.toShort)
      }
    }
  }

  describe("get_val_from_zp") {
    context("$00FB = $00; $00FC = $28") { memory.write(0x00fb, 0x00, 0x28) } {
      it("reads a word from a memory location") {
        expect { memory.get_val_from_zp(0x00fb.toShort) }.toEqual(0x2800.toShort)
      }
    }

    context("$00FF = $00; $0100 = $90") { memory.write(0x00ff, 0x00, 0x90) } {
      it("reads a word from a memory location") {
        expect { memory.get_val_from_zp(0x00ff.toShort) }.toEqual(0xff00.toShort)
      }
    }
  }

  describe("read") {
    describe("fetching sequence of bytes") {
      context("$C000 = $00; $C001 = $C8; $C002 = $FF") { memory.write(0xc000, 0x00, 0xc8, 0xff) } {
        it("reads a sequence of bytes starting from a memory location") {
          expect { memory.read(0xc000, 0x03) }.toEqual(Seq[ByteVal](0x00, 0xc8, 0xff))
        }
      }
    }
  }

  describe("write") {
    describe("variable number of arguments") {
      it("writes a variable number of bytes starting from a memory location") {
        expect {
          memory.write(0xc000, 0xa9, 0x00, 0x8d, 0x11, 0xd0, 0x60)
          memory.read(0xc000, 0x06)
        }.toEqual(Seq[ByteVal](0xa9, 0x00, 0x8d, 0x11, 0xd0, 0x60))
      }
    }
  }

  context("init") { subject { memory.init } } {
    it("fills memory with an illegal opcode") {
      expect { subject }.toChange { memory.read(0x0000, Memory.size) }.to(
        List.fill[ByteVal](Memory.size)(0xff).updated(0xfffc, ByteVal(0x00)).updated(0xfffd, ByteVal(0x02))
      )
    }

    it("sets RESET vector to $0200") {
      expect { subject }.toChange { memory.read(0xfffc) }.to(0x00)
      expect { subject }.toChange { memory.read(0xfffd) }.to(0x02)
    }
  }
}
