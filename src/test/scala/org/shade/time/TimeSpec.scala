package org.shade.time

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import grizzled.slf4j.Logging

class TimeSpec extends WordSpec with Matchers with MockitoSugar with Logging {

  "Constructing a Time object with a particular millisecond value" should {

    "return a Time object with that time" in {
      Time(13243432434L).millis shouldBe 13243432434L
    }
  }

  "The all-fields apply method" should {

    "return the correct time object #1" in {
      Time(2013, 5, 22, 11, 43, 23, 222, Zone("Europe/London")) shouldBe Time(1369219403222L)
    }

    "return the correct time object #2" in {
      // 1600-06-12T10:44:44.111+02:34:48 =  (In UTC = -11661974115889 - 9288000 )
      Time(1600, 6, 12, 10, 44, 44, 111, Zone("Africa/Addis_Ababa")) shouldBe Time(-11661974115889L - 9288000L)
    }

    "throw an exception if the time is invalid due to general invalid numbers" in {
      evaluating(Time(1900, 2, 29, 11, 33, 22, 222, Zone.utc)) should produce [InvalidTimeException]
      evaluating(Time(2000, 2, 30, 11, 33, 22, 222, Zone.utc)) should produce [InvalidTimeException]
      evaluating(Time(2013, 8, 25, 12, 60, 22, 222, Zone.utc)) should produce [InvalidTimeException]
      evaluating(Time(2013, 0, 22, 11, 33, 22, 222, Zone.utc)) should produce [InvalidTimeException]
    }

    "throw an exception if the time is invalid due to daylight savings time changes in a given zone" in {
      evaluating(Time(2013, 3, 31, 1, 30, 25, 222, Zone("Europe/London"))) should produce [InvalidTimeException]
    }
  }

  "The string representation of a Time object" should {

    "be the ISO8601 format with milliseconds, in the UTC timezone" in {

      Time(432342455L).toString shouldBe "1970-01-06T00:05:42.455Z"
      Time(-1375081323539L).toString shouldBe "1926-06-05T16:57:56.461Z"
      Time(1375081385429L).toString shouldBe "2013-07-29T07:03:05.429Z"
      Time(4L).toString shouldBe "1970-01-01T00:00:00.004Z"
      Time(564564566445L).toString shouldBe "1987-11-22T07:29:26.445Z"
    }
  }
}
