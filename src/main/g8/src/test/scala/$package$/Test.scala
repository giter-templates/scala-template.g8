package $package$

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Test extends AnyFlatSpec with Matchers {
  "Sample test" should "complete" in {
    "test" shouldEqual "test"
  }
}