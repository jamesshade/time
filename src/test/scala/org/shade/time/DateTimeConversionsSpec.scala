package org.shade.time

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import grizzled.slf4j.Logging
import org.joda.time.{LocalDate, DateTimeZone, DateTime}
import org.joda.time.chrono.{BuddhistChronology, IslamicChronology, GregorianChronology, CopticChronology}

class DateTimeConversionsSpec extends WordSpec with Matchers with MockitoSugar with Logging {

  import TimeConversions._

  private val copticCaracas = CopticChronology.getInstance(DateTimeZone.forID("America/Caracas"))
  private val islamicIstanbul = IslamicChronology.getInstance(DateTimeZone.forID("Europe/Istanbul"))
  private val gregorianGuatemala = GregorianChronology.getInstance(DateTimeZone.forID("America/Guatemala"))
  private val buddhistBudapest = BuddhistChronology.getInstance(DateTimeZone.forID("Europe/Budapest"))

  "Assigning a Time to a Long" should {

    "Produce the right value #1" in {
      val value1: Long = Time(100000L)
      value1 shouldBe 100000L
    }

    "Produce the right value #2" in {
      val value2: Long = Time(1381386682010L)
      value2 shouldBe 1381386682010L
    }

    "Produce the right value #3" in {
      val value3: Long = Time(-1381386682010L)
      value3 shouldBe -1381386682010L
    }
  }

  "Assigning a Long to a Time" should {

    "Produce the right Time #1" in {
      val time: Time = 100000L
      time shouldBe Time(100000L)
    }

    "Produce the right Time #2" in {
      val time: Time = 1381386682010L
      time shouldBe Time(1381386682010L)
    }

    "Produce the right Time #3" in {
      val time: Time = -1381386682010L
      time shouldBe Time(-1381386682010L)
    }
  }

  "Assigning a Time from a Joda DateTime" should {

    "Produce the right Time (regardless of the chronology and timezone #1)" in {
      val time: Time = new DateTime(100000L, isoUtc)
      time shouldBe Time(100000L)
    }

    "Produce the right Time (regardless of the chronology and timezone #2)" in {
      val time: Time = new DateTime(100000L, copticCaracas)
      time shouldBe Time(100000L)
    }

    "Produce the right Time (regardless of the chronology and timezone #3)" in {
      val time: Time = new DateTime(100000L, islamicIstanbul)
      time shouldBe Time(100000L)
    }

    "Produce the right Time (regardless of the chronology and timezone #4)" in {
      val time: Time = new DateTime(100000L, gregorianGuatemala)
      time shouldBe Time(100000L)
    }

    "Produce the right Time (regardless of the chronology and timezone #5)" in {
      val time: Time = new DateTime(100000L, buddhistBudapest)
      time shouldBe Time(100000L)
    }
  }

  "Assigning a Joda DateTime from a Time" should {

    "Produce the right DateTime with ISO chronology and UTC timezone #1" in {
      val joda: DateTime = Time(100000L)
      joda shouldBe new DateTime(100000L, isoUtc)
    }

    "Produce the right DateTime with ISO chronology and UTC timezone #2" in {
      val joda: DateTime = Time(2013, 10, 22, 11, 44, 33, 234, Zone("Europe/London"))
      joda should not be new DateTime(2013, 10, 22, 11, 44, 33, 234, DateTimeZone.forID("Europe/London"))
      joda shouldBe new DateTime(2013, 10, 22, 10, 44, 33, 234, isoUtc)
    }

    "Produce the right DateTime with ISO chronology and UTC timezone #3" in {
      val joda: DateTime = Time(381386682010L)
      joda shouldBe new DateTime(381386682010L, isoUtc)
    }

    "Produce the right DateTime with ISO chronology and UTC timezone #4" in {
      val joda: DateTime = Time(-381386682010L)
      joda shouldBe new DateTime(-381386682010L, isoUtc)
    }
  }

  "Assigning a Date from a Joda LocalDate" should {

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #1" in {
      val date: Date = new LocalDate(1730, 2, 7, copticCaracas)
      date shouldBe Date(2013, 10, 17)
    }

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #2a" in {
      val date: Date = new LocalDate(2013, 11, 22, islamicIstanbul)
      date shouldBe Date(2575, 7, 2)
    }

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #2b" in {
      val date: Date = new LocalDate(1434, 12, 12, islamicIstanbul)
      date shouldBe Date(2013, 10, 17)
    }

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #3" in {
        val date: Date = new LocalDate(2013, 11, 22, gregorianGuatemala)
        date shouldBe Date(2013, 11, 22)
    }

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #5" in {
      val date: Date = new LocalDate(2556, 10, 17, buddhistBudapest)
      date shouldBe Date(2013, 10, 17)
    }
  }

  "Assigning a Joda LocalDate from a Date" should {

    // TODO [JJS] COMPLETE TEST
    "Produce the right LocalDate with ISO chronology and UTC timezone #1" in {
      val joda: LocalDate = Date(2013, 11, 22)
      joda shouldBe new LocalDate(2013, 11, 22, isoUtc)
    }

    "Produce the right LocalDate with ISO chronology and UTC timezone #2" in {
      val joda: LocalDate = Date(1945, 12, 1)
      joda shouldBe new LocalDate(1945, 12, 1, isoUtc)
    }

    "Produce the right LocalDate with ISO chronology and UTC timezone #3" in {
      val joda: LocalDate = Date(1066, 1, 11)
      joda shouldBe new LocalDate(1066, 1, 11, isoUtc)
    }

    "Produce the right LocalDate with ISO chronology and UTC timezone #4" in {
      val joda: LocalDate = Date(1492, 12, 22)
      joda shouldBe new LocalDate(1492, 12, 22, isoUtc)
    }
  }
}
