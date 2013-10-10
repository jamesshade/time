package org.shade.time

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import grizzled.slf4j.Logging

class SystemClockSpec extends WordSpec with ShouldMatchers with MockitoSugar with Logging {

  "Calling now" should {

    "return the current time" in {
      val before = System.currentTimeMillis
      val clockTime = SystemClock.now.millis
      val after = System.currentTimeMillis

      clockTime should (be >= before and be <= after)
    }
  }
}