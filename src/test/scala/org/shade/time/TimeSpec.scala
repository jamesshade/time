package org.shade.time

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import grizzled.slf4j.Logging
import org.joda.time.chrono.{CopticChronology, GregorianChronology}
import org.joda.time.{DateMidnight, DateTimeZone, DateTime}
import java.util.TimeZone

class TimeSpec extends WordSpec with ShouldMatchers with MockitoSugar with Logging {

  "Constructing a Time object with a particular millisecond value" should {

    "return a Time object with that time" in {
      Time(13243432434L).millis should equal (13243432434L)
    }
  }

  "The joda method" should {

    "return a Joda DateTime object with the correct time and Gregorian UTC chronology" in {

      val expected = new DateTime(1231231232L, GregorianChronology.getInstanceUTC)

      Time(1231231232L).joda should equal(expected)
    }
  }

  "The jdk method" should {

    "return a java.util.Date object with the correct time" in {

      val expected = new java.util.Date(234242342342L)

      Time(234242342342L).jdk should equal(expected)
    }
  }

  "Constructing a Time object from a Joda time instant using the apply method" should {

    "return a Time object representing the same instant for a simply constructed DateTime in system Chronology" in {
      Time(new DateTime(45345435367L)).millis should equal(45345435367L)
      Time(new DateTime(1000L)).millis should equal(1000L)
    }

    "return a Time object representing the same instant for a UTC DateTime" in {
      Time(new DateTime(45345435367L, GregorianChronology.getInstanceUTC)).millis should equal(45345435367L)
    }

    "return a Time object representing the same instant for a DateTime in a different Chronology" in {
      Time(new DateTime(45345435367L, CopticChronology.getInstance(DateTimeZone.forID("Europe/London")))).millis should equal(45345435367L)
    }

    "return a Time object representing the same instant for some other ReadableInstant subclass in the PST timezone" in {
      Time(new DateMidnight(1375080285918L, DateTimeZone.forTimeZone(TimeZone.getTimeZone("PST")))).millis should equal(1374994800000L)
    }

    "return a Time object representing the same instant for some other ReadableInstant subclass in the UK timezone" in {
      Time(new DateMidnight(1375080285918L, DateTimeZone.forID("Europe/London"))).millis should equal(1375052400000L)
    }
  }

  "Constructing a Time object from a JDK java.util.Date using the apply method" should {

    "return a Time object representing the same instant" in {
      Time(new java.util.Date(45345435367L)).millis should equal(45345435367L)
      Time(new java.util.Date(1000L)).millis should equal(1000L)
    }
  }

  "The string representation of a Time object" should {

    "be the ISO8601 format with milliseconds, in the UTC timezone" in {

      Time(432342455L).toString should equal("1970-01-06T00:05:42.455Z")
      Time(-1375081323539L).toString should equal("1926-06-05T16:57:56.461Z")
      Time(1375081385429L).toString should equal("2013-07-29T07:03:05.429Z")
      Time(4L).toString should equal("1970-01-01T00:00:00.004Z")
      Time(564564566445L).toString should equal("1987-11-22T07:29:26.445Z")
    }
  }
}
