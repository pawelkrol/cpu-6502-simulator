import org.scalatest.FunSpec

import com.github.pawelkrol.CPU6502.Arguments

class ArgumentsSpec extends FunSpec {

  describe("validate") {
    it("passes default name validation") {
      Arguments().validate
    }

    it("passes alphabetical name validation") {
      Arguments("Sunn").validate
    }

    it("passes name with empty space validation") {
      Arguments("Sunn O").validate
    }

    it("fails to validate non-alphabetical name") {
      intercept[IllegalArgumentException] {
        Arguments("Sunn O)))").validate
      }
    }
  }
}
