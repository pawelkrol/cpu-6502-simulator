package com.github.pawelkrol.CPU6502

import java.io.File
import scopt.OptionParser

object Application extends Logging {

  private val appVersion = "0.02-SNAPSHOT"

  private val core = Core()

  private val parser = new OptionParser[Arguments]("cpu-6502-simulator") {
    head("cpu-6502-simulator", appVersion)
    help("help") text("prints out this usage text")
    opt[Unit]("verbose") optional() action { (_, option: Arguments) =>
      option.copy(verbose = true) } text("produces verbose output, including all CPU instructions that are executed")
    opt[Int]("cycle-count") optional() action { (value: Int, option: Arguments) =>
      option.copy(cycleCount = Some(value)) } text("instructs program to exit simulator after a given number of cycles")
    opt[String]("start-address") optional() action { (value: String, option: Arguments) =>
      option.copy(startAddress = Some(Integer.parseInt(value, 16))) } text("overwrites the default (i.e., $0200) start address, must be given as a number consisting of 4 hexadecimal digits (e.g., \"c000\")")
    arg[File]("<filename>") unbounded() required() action { (file: File, option: Arguments) =>
      option.copy(file = Some(file)) } text("program file to execute (e.g., \"main.prg\")")
  }

  def main(args: Array[String]) = {
    println("\nCPU 6502 Simulator %s (2016-12-31)\nCopyright (C) 2016 Pawel Krol (DJ Gruby/Protovision/TRIAD)\n".format(appVersion))

    parser.parse(args, Arguments()) match {
      case Some(arguments) => {
        try {
          arguments.validate
        } catch {
          case e: Exception => {
            println(e.getMessage)
            System.exit(1)
          }
        }
        runWith(arguments)
      }
      case None =>
        // arguments are invalid, usage message will have been displayed
    }

    System.exit(0)
  }

  def runWith(arguments: Arguments) {
    core.reset

    // Initialize program counter with a custom start address
    core.register.PC = arguments.startAddress match {
      case Some(addr) =>
        addr.toShort
      case None =>
        core.memory.get_val_from_addr(0xfffc.toShort)
    }

    verbose = arguments.verbose

    Runner.go(core, arguments.file.get, arguments.cycleCount)
  }
}
