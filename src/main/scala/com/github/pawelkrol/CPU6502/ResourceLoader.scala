package com.github.pawelkrol.CPU6502

import scala.io.Codec.ISO8859
import scala.io.Source.fromInputStream

trait ResourceLoader {

  protected def loadResource(fileName: String) = {

    val inputStream = getClass.getResourceAsStream(fileName)

    val source = fromInputStream(inputStream)(ISO8859).toArray

    source.map(_.toByte)
  }
}
