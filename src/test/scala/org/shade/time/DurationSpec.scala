package org.shade.time

import org.joda.time.{Duration => JodaDuration}
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar

class DurationSpec extends WordSpec with Matchers with MockitoSugar {

  "The Joda Duration object" should {

    "Not allow negative durations" in {

      val d = new JodaDuration(1000L)
      d.getMillis shouldBe 1000L

      val e = new JodaDuration(-1000L)
      e.getMillis shouldBe -1000L
    }
  }
}
