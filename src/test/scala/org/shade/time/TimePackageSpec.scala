package org.shade.time

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.joda.time.chrono.GregorianChronology
import org.joda.time.DateTimeZone

class TimePackageSpec extends WordSpec with ShouldMatchers {

  "The gregorianUtc contstant" should {
    "have Gregorian chronolgy in the UTC timezone" in {
      gregorianUtc.isInstanceOf[GregorianChronology] should be (true)
      gregorianUtc.getZone should be (DateTimeZone.UTC)
    }
  }
}
