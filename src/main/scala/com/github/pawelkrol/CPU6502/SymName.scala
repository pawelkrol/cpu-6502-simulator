package com.github.pawelkrol.CPU6502

/** Symbollic name */
sealed trait SymName {
}

trait SymName_BRK extends SymName {

  def symName = "BRK"
}

trait SymName_ADC extends SymName {

  def symName = "ADC"
}

trait SymName_AND extends SymName {

  def symName = "AND"
}

trait SymName_ASL extends SymName {

  def symName = "ASL"
}

trait SymName_BCC extends SymName {

  def symName = "BCC"
}

trait SymName_BCS extends SymName {

  def symName = "BCS"
}

trait SymName_BEQ extends SymName {

  def symName = "BEQ"
}

trait SymName_BIT extends SymName {

  def symName = "BIT"
}

trait SymName_BMI extends SymName {

  def symName = "BMI"
}

trait SymName_BNE extends SymName {

  def symName = "BNE"
}

trait SymName_BPL extends SymName {

  def symName = "BPL"
}

trait SymName_BVC extends SymName {

  def symName = "BVC"
}

trait SymName_BVS extends SymName {

  def symName = "BVS"
}

trait SymName_CLC extends SymName {

  def symName = "CLC"
}

trait SymName_CLD extends SymName {

  def symName = "CLD"
}

trait SymName_CLI extends SymName {

  def symName = "CLI"
}

trait SymName_CLV extends SymName {

  def symName = "CLV"
}

trait SymName_CMP extends SymName {

  def symName = "CMP"
}

trait SymName_CPX extends SymName {

  def symName = "CPX"
}

trait SymName_CPY extends SymName {

  def symName = "CPY"
}

trait SymName_DEC extends SymName {

  def symName = "DEC"
}

trait SymName_DEX extends SymName {

  def symName = "DEX"
}

trait SymName_DEY extends SymName {

  def symName = "DEY"
}

trait SymName_EOR extends SymName {

  def symName = "EOR"
}

trait SymName_INC extends SymName {

  def symName = "INC"
}

trait SymName_INX extends SymName {

  def symName = "INX"
}

trait SymName_INY extends SymName {

  def symName = "INY"
}

trait SymName_JMP extends SymName {

  def symName = "JMP"
}

trait SymName_JSR extends SymName {

  def symName = "JSR"
}

trait SymName_LDA extends SymName {

  def symName = "LDA"
}

trait SymName_LDX extends SymName {

  def symName = "LDX"
}

trait SymName_LDY extends SymName {

  def symName = "LDY"
}

trait SymName_LSR extends SymName {

  def symName = "LSR"
}

trait SymName_NOP extends SymName {

  def symName = "NOP"
}

trait SymName_ORA extends SymName {

  def symName = "ORA"
}

trait SymName_PHA extends SymName {

  def symName = "PHA"
}

trait SymName_PHP extends SymName {

  def symName = "PHP"
}

trait SymName_PLA extends SymName {

  def symName = "PLA"
}

trait SymName_PLP extends SymName {

  def symName = "PLP"
}

trait SymName_ROL extends SymName {

  def symName = "ROL"
}

trait SymName_ROR extends SymName {

  def symName = "ROR"
}

trait SymName_RTI extends SymName {

  def symName = "RTI"
}

trait SymName_RTS extends SymName {

  def symName = "RTS"
}

trait SymName_SBC extends SymName {

  def symName = "SBC"
}

trait SymName_SEC extends SymName {

  def symName = "SEC"
}

trait SymName_SED extends SymName {

  def symName = "SED"
}

trait SymName_SEI extends SymName {

  def symName = "SEI"
}

trait SymName_STA extends SymName {

  def symName = "STA"
}

trait SymName_STX extends SymName {

  def symName = "STX"
}

trait SymName_STY extends SymName {

  def symName = "STY"
}

trait SymName_TAX extends SymName {

  def symName = "TAX"
}

trait SymName_TAY extends SymName {

  def symName = "TAY"
}

trait SymName_TSX extends SymName {

  def symName = "TSX"
}

trait SymName_TXA extends SymName {

  def symName = "TXA"
}

trait SymName_TXS extends SymName {

  def symName = "TXS"
}

trait SymName_TYA extends SymName {

  def symName = "TYA"
}
