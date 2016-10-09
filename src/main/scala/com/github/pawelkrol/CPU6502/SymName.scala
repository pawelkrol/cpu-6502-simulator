package com.github.pawelkrol.CPU6502

/** Symbollic name */
sealed trait SymName {
}

trait SymName_ORA extends SymName {

  def symName = "ORA"
}

trait SymName_AND extends SymName {

  def symName = "AND"
}

trait SymName_EOR extends SymName {

  def symName = "EOR"
}

trait SymName_ADC extends SymName {

  def symName = "ADC"
}
