package com.github.pawelkrol.CPU6502

import com.typesafe.scalalogging.StrictLogging
import scopt.OptionParser

object Application extends StrictLogging {

  private val parser = new OptionParser[Arguments]("cpu-6502-simulator") {
    head("cpu-6502-simulator", "0.01-SNAPSHOT")
    help("help") text("prints out this usage text")
    // opt[String]("name") optional() action { (value: String, option: Arguments) =>
    //   option.copy(name = value) } text("defines whom do you want to say \"Hello\" (e.g., \"Pawel\", defaults to \"World\")")
  }

  def main(args: Array[String]) = {
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
  }

  def runWith(arguments: Arguments) {
    // logger.debug("Printing out hello message")
    // println(Message.hello(arguments.name))
  }
}
