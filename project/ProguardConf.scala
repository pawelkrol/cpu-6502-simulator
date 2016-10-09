object ProguardConf {

  val cpu6502Simulator =
"""
-keep public class ch.qos.logback.core.ConsoleAppender {
  *;
}

-keep public class ch.qos.logback.core.FileAppender {
  *;
}

-keep public class ch.qos.logback.classic.LoggerContext {
  *;
}

-keep public class ch.qos.logback.core.pattern.PatternLayoutEncoderBase {
  *;
}
"""
}
