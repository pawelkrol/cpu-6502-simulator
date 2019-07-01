package com.github.pawelkrol.CPU6502

import io.Codec.ISO8859
import io.Source.fromFile
import java.io.File

object Runner {

  private def loadFile(file: File, core: Core): Unit = {
    val source = fromFile(file)(ISO8859).toArray

    val loadBytes = source.take(2).map(_.toInt).map(ByteVal(_))
    val loadAddr = Util.byteVals2Addr(loadBytes.toSeq)

    val data = source.drop(2).map(ByteVal(_)).toSeq
    core.memory.write(loadAddr, data)

    Application.logInfo("Loaded '%s' at $%04X-$%04X".format(file.getCanonicalPath, loadAddr, (loadAddr + data.size - 1) & 0xffff))
  }

  def go(core: Core, file: File, cycleCount: Option[Int]): Unit = {
    loadFile(file, core)

    Application.logRegisters(core)

    while(true) {
      core.executeInstruction
      Application.logRegisters(core)

      cycleCount match {
        case Some(maxCycles) =>
          if (core.totalCycles >= maxCycles) {
            Application.logWarning("Maximum number of cycles (%d) reached".format(maxCycles))
            System.exit(0)
          }
        case None =>
      }
    }
  }
}
