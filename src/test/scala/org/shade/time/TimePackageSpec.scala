package org.shade.time

import org.scalatest.WordSpec
import org.scalatest.Matchers
import org.joda.time.chrono.ISOChronology
import org.joda.time.DateTimeZone

class TimePackageSpec extends WordSpec with Matchers {

  "The isoUtc contstant" should {
    "have ISO chronolgy in the UTC timezone" in {
      isoUtc.isInstanceOf[ISOChronology] shouldBe true
      isoUtc.getZone shouldBe DateTimeZone.UTC
    }
  }
}
