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

trait SymName_CMP extends SymName {

  def symName = "CMP"
}

trait SymName_EOR extends SymName {

  def symName = "EOR"
}

trait SymName_LSR extends SymName {

  def symName = "LSR"
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

trait SymName_SBC extends SymName {

  def symName = "SBC"
}
