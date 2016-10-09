package com.github.pawelkrol.CPU6502

import org.scalatest.FunSpec

import scala.collection.mutable.Stack

class FunFunSpec extends FunSpec {

  private var beforeEach: () => Any = _

  private var expression: () => Any = _

  private var testCount: Int = 0

  def before(code: => Any) { beforeEach = () => code }

  class FromToValidator[T](code: => Any, predicate: => T, initial: T) {
    def to(ending: T) {
      assert(predicate == initial)
      code
      assert(predicate == ending)
    }
  }

  class ChangeValidator[T](code: => Any, predicate: => T) {
    def from(initial: T) = new FromToValidator(code, predicate, initial)
    def to(value: T) = {
      val initial = predicate
      code
      val ending = predicate
      assert(ending == value)
    }
  }

  class NoChangeValidator[T](code: => Any, predicate: => T) {
    val initial = predicate
    code
    val ending = predicate
    assert(initial == ending)
  }

  class Expectation[T](code: => T) {
    def notToChange[T](predicate: => T) = new NoChangeValidator(code, predicate)
    def toChange[T](predicate: => T) = new ChangeValidator(code, predicate)
    def toEqual(predicate: => T) = assert(code == predicate)
  }

  def expect[T](code: => T) = new Expectation(code)

  val internalIt: FunFunSpec.this.ItWord = it

  def it(message: String)(test: => Any) {
    val stack = beforeStack.reverse.clone
    val beforeCallback = beforeEach
    val beforeIt = () => { if (beforeCallback != null) beforeCallback(); beforeAll(stack) }
    internalIt(message)({ beforeIt(); test })
  }

  def it(test: => Any) {
    testCount += 1
    it("no message (test #" + testCount + ")")(test)
  }

  class EqualityValidator[T](init: => Any, expr: => T) {
    init; expression = () => expr
  }

  class Provisioning(beforeEach: () => Any, init: => Any, test: => Any) {
    testCount += 1
    val stack = beforeStack.reverse.clone
    val beforeIt = () => { beforeEach(); beforeAll(stack) }
    internalIt("assertion with provisioned values (test #" + testCount + ")")({ beforeIt(); init; test })
  }

  def provided(init: => Any)(test: => Any) = new Provisioning(beforeEach, init, test)

  private def beforeAll(stack: Stack[() => Any]) { stack.foreach(code => code()) }

  private var beforeStack = new Stack[() => Any]

  def context(description: String)(init: => Any)(tests: => Any) {
    beforeStack.push(() => init)
    describe(description) { tests }
    beforeStack.pop
  }
}