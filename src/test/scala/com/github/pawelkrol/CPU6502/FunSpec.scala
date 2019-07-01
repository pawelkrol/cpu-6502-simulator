package com.github.pawelkrol.CPU6502

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.Tag

import scala.collection.mutable.Stack

trait FunSpec extends AnyFunSpec {

  private var beforeEach: () => Any = _

  private var expression: () => Any = _

  private var testCount: Int = 0

  def before(code: => Any): Unit = { beforeEach = () => code }

  class FromToValidator[T](code: => Any, predicate: => T, initial: T) {
    def to(ending: T): Unit = {
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

  val internalIt: FunSpec.this.ItWord = it

  def it(message: String)(test: => Any): Unit = {
    val beforeIt = applyContext
    internalIt(message)({ beforeIt(); test })
  }

  private def applyContext = {
    val stack = beforeStack.reverse.clone
    val beforeCallback = beforeEach
    () => { if (beforeCallback != null) beforeCallback(); beforeAll(stack) }
  }

  def it(test: => Any): Unit = {
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

  private def beforeAll(stack: Stack[() => Any]): Unit = { stack.foreach(code => code()) }

  private var beforeStack = new Stack[() => Any]

  def context(description: String)(init: => Any)(tests: => Any): Unit = {
    beforeStack.push(() => init)
    describe(description) { tests }
    beforeStack.pop()
  }

  private var examples = scala.collection.mutable.HashMap[String, (List[Any]) => Unit]()

  def includeExamples(name: String, args: => List[Any]): Unit = {
    applyContext()
    examples(name)(args)
  }

  def sharedExamples(name: String, func: (List[Any]) => Unit): Unit = {
    examples.put(name, func)
  }

  protected def xdescribe(description: String)(fun: => Unit): Unit = {
    ignore(description) {}
  }

  protected def xit(specText: String, testTags: Tag*)(testFun: => Any): Unit = {
    ignore(specText) {}
  }

  protected def xit(testTags: Tag*)(testFun: => Any): Unit = {
    testCount += 1
    xit("no message (test #" + testCount + ")", testTags: _*)(testFun)
  }

  private var _subject: () => Unit = _

  def subject(operation: => Unit ): Unit = { _subject = () => operation }

  def subject: Unit = { _subject() }
}
