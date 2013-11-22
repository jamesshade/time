package org.shade.time

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import Duration._

class DurationSpec extends WordSpec with Matchers with MockitoSugar {

  "The Duration class" should {

    "Return the millisecond duration it is constructed with" in {
      Duration(1L).millis shouldBe 1L
      Duration(43345L).millis shouldBe 43345L
      Duration(45435435455L).millis shouldBe 45435435455L
      Duration(87967867554353L).millis shouldBe 87967867554353L
      Duration(Long.MaxValue).millis shouldBe Long.MaxValue
    }

    "Allow zero size" in {
      Duration(0L).millis shouldBe 0L
    }

    "Allow negative durations" in {
      Duration(-1L).millis shouldBe -1L
      Duration(-43345L).millis shouldBe -43345L
      Duration(-45435435455L).millis shouldBe -45435435455L
      Duration(-87967867554353L).millis shouldBe -87967867554353L
      Duration(Long.MinValue).millis shouldBe Long.MinValue
    }
  }

  "The < operator" should {

    "return true if the left operand is less than the right operand" in {
      Duration(0L) < Duration(1L) shouldBe true
      Duration(0L) < Duration(17798798L) shouldBe true
      Duration(4543534543L) < Duration(4543534544L) shouldBe true
      Duration(-4354350L) < Duration(1L) shouldBe true
      Duration(-1L) < Duration(1L) shouldBe true
      Duration(-1L) < Duration(0L) shouldBe true
    }

    "return false if the left operand is equal to the right operand" in {
      val d = Duration(435345L)
      d < d shouldBe false
      Duration(0L) < Duration(0L) shouldBe false
      Duration(17798798L) < Duration(17798798L) shouldBe false
      Duration(4543534543L) < Duration(4543534543L) shouldBe false
      Duration(-4354350L) < Duration(-4354350L) shouldBe false
      Duration(-1L) < Duration(-1L) shouldBe false
    }

    "return false if the left operand is greater than the right operand" in {
      Duration(1L) < Duration(0L)  shouldBe false
      Duration(17798798L) < Duration(0L) shouldBe false
      Duration(4543534544L) < Duration(4543534543L) shouldBe false
      Duration(1L) < Duration(-4354350L) shouldBe false
      Duration(1L) < Duration(-1L) shouldBe false
      Duration(0L) < Duration(-1L) shouldBe false
    }
  }

  "The <= operator" should {

    "return true if the left operand is less than the right operand" in {
      Duration(0L) <= Duration(1L) shouldBe true
      Duration(0L) <= Duration(17798798L) shouldBe true
      Duration(4543534543L) <= Duration(4543534544L) shouldBe true
      Duration(-4354350L) <= Duration(1L) shouldBe true
      Duration(-1L) <= Duration(1L) shouldBe true
      Duration(-1L) <= Duration(0L) shouldBe true
    }

    "return true if the left operand is equal to the right operand" in {
      val d = Duration(435345L)
      d <= d shouldBe true
      Duration(0L) <= Duration(0L) shouldBe true
      Duration(17798798L) <= Duration(17798798L) shouldBe true
      Duration(4543534543L) <= Duration(4543534543L) shouldBe true
      Duration(-4354350L) <= Duration(-4354350L) shouldBe true
      Duration(-1L) <= Duration(-1L) shouldBe true
    }

    "return false if the left operand is greater than the right operand" in {
      Duration(1L) <= Duration(0L)  shouldBe false
      Duration(17798798L) <= Duration(0L) shouldBe false
      Duration(4543534544L) <= Duration(4543534543L) shouldBe false
      Duration(1L) <= Duration(-4354350L) shouldBe false
      Duration(1L) <= Duration(-1L) shouldBe false
      Duration(0L) <= Duration(-1L) shouldBe false
    }
  }


  "The >= operator" should {

    "return false if the left operand is less than the right operand" in {
      Duration(0L) >= Duration(1L) shouldBe false
      Duration(0L) >= Duration(17798798L) shouldBe false
      Duration(4543534543L) >= Duration(4543534544L) shouldBe false
      Duration(-4354350L) >= Duration(1L) shouldBe false
      Duration(-1L) >= Duration(1L) shouldBe false
      Duration(-1L) >= Duration(0L) shouldBe false
    }

    "return true if the left operand is equal to the right operand" in {
      val d = Duration(435345L)
      d >= d shouldBe true
      Duration(0L) >= Duration(0L) shouldBe true
      Duration(17798798L) >= Duration(17798798L) shouldBe true
      Duration(4543534543L) >= Duration(4543534543L) shouldBe true
      Duration(-4354350L) >= Duration(-4354350L) shouldBe true
      Duration(-1L) >= Duration(-1L) shouldBe true
    }

    "return true if the left operand is greater than the right operand" in {
      Duration(1L) >= Duration(0L)  shouldBe true
      Duration(17798798L) >= Duration(0L) shouldBe true
      Duration(4543534544L) >= Duration(4543534543L) shouldBe true
      Duration(1L) >= Duration(-4354350L) shouldBe true
      Duration(1L) >= Duration(-1L) shouldBe true
      Duration(0L) >= Duration(-1L) shouldBe true
    }
  }

  "The > operator" should {

    "return false if the left operand is less than the right operand" in {
      Duration(0L) > Duration(1L) shouldBe false
      Duration(0L) > Duration(17798798L) shouldBe false
      Duration(4543534543L) > Duration(4543534544L) shouldBe false
      Duration(-4354350L) > Duration(1L) shouldBe false
      Duration(-1L) > Duration(1L) shouldBe false
      Duration(-1L) > Duration(0L) shouldBe false
    }

    "return false if the left operand is equal to the right operand" in {
      val d = Duration(435345L)
      d > d shouldBe false
      Duration(0L) > Duration(0L) shouldBe false
      Duration(17798798L) > Duration(17798798L) shouldBe false
      Duration(4543534543L) > Duration(4543534543L) shouldBe false
      Duration(-4354350L) > Duration(-4354350L) shouldBe false
      Duration(-1L) > Duration(-1L) shouldBe false
    }

    "return true if the left operand is greater than the right operand" in {
      Duration(1L) > Duration(0L)  shouldBe true
      Duration(17798798L) > Duration(0L) shouldBe true
      Duration(4543534544L) > Duration(4543534543L) shouldBe true
      Duration(1L) > Duration(-4354350L) shouldBe true
      Duration(1L) > Duration(-1L) shouldBe true
      Duration(0L) > Duration(-1L) shouldBe true
    }
  }

  "The + operator" should {

    "return the result of adding the millisecond values of the two operands" in {

      Duration(0L) + Duration(0L) shouldBe Duration(0L)
      Duration(4543534543L) + Duration(4543534544L) shouldBe Duration(9087069087L)

      Duration(0L) + Duration(-1L) shouldBe Duration(-1L)
      Duration(-1L) + Duration(0L) shouldBe Duration(-1L)

      Duration(1L) + Duration(0L) shouldBe Duration(1L)
      Duration(0L) + Duration(1L) shouldBe Duration(1L)

      Duration(17798798L) + Duration(0L) shouldBe Duration(17798798L)
      Duration(0L) + Duration(17798798L) shouldBe Duration(17798798L)

      Duration(1L) + Duration(-4354350L) shouldBe Duration(-4354349L)
      Duration(-4354350L) + Duration(1L) shouldBe Duration(-4354349L)

      Duration(-87545634) + Duration(87545634) shouldBe Duration(0L)
      Duration(87545634) + Duration(-87545634) shouldBe Duration(0L)

      Duration(1L) + Duration(-1L) shouldBe Duration(0L)
      Duration(-1L) + Duration(1L) shouldBe Duration(0L)
    }
  }

  "The - operator" should {

    "return the result of subttracting the second millisecond value from the first" in {

      Duration(0L) - Duration(0L) shouldBe Duration(0L)
      Duration(4543534543L) - Duration(4543534544L) shouldBe Duration(-1L)

      Duration(0L) - Duration(-1L) shouldBe Duration(1L)
      Duration(-1L) - Duration(0L) shouldBe Duration(-1L)

      Duration(1L) - Duration(0L) shouldBe Duration(1L)
      Duration(0L) - Duration(1L) shouldBe Duration(-1L)

      Duration(17798798L) - Duration(0L) shouldBe Duration(17798798L)
      Duration(0L) - Duration(17798798L) shouldBe Duration(-17798798L)

      Duration(1L) - Duration(-4354350L) shouldBe Duration(4354351L)
      Duration(-4354350L) - Duration(1L) shouldBe Duration(-4354351L)

      Duration(-87545634) - Duration(87545634) shouldBe Duration(-175091268L)
      Duration(87545634) - Duration(-87545634) shouldBe Duration(175091268L)

      Duration(1L) - Duration(-1L) shouldBe Duration(2L)
      Duration(-1L) - Duration(1L) shouldBe Duration(-2L)
    }
  }

  "The size method" should {
    "return the absolute value of the milliseconds value" in {
      Duration(0L).size shouldBe 0L
      Duration(1L).size shouldBe 1L
      Duration(-1L).size shouldBe 1L
      Duration(453454353L).size shouldBe 453454353L
      Duration(-453454353L).size shouldBe 453454353L
    }
  }

  "The abs method" should {
    "return the a Duration containing the absolute value of the milliseconds value" in {
      Duration(0L).abs shouldBe Duration(0L)
      Duration(1L).abs shouldBe Duration(1L)
      Duration(-1L).abs shouldBe Duration(1L)
      Duration(453454353L).abs shouldBe Duration(453454353L)
      Duration(-453454353L).abs shouldBe Duration(453454353L)
    }
  }
}
