package com.github.pawelkrol.CPU6502

/** Symbollic name */
sealed trait SymName {
}

trait SymName_ADC extends SymName {

  def symName = "ADC"
}

trait SymName_AND extends SymName {

  def symName = "AND"
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

trait SymName_ORA extends SymName {

  def symName = "ORA"
}

trait SymName_ROL extends SymName {

  def symName = "ROL"
}

trait SymName_ROR extends SymName {

  def symName = "ROR"
}
