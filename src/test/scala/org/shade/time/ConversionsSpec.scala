package org.shade.time

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import grizzled.slf4j.Logging
import org.joda.time.{DateMidnight, LocalDate, DateTimeZone, DateTime}
import org.joda.time.chrono.{BuddhistChronology, IslamicChronology, GregorianChronology, CopticChronology}
import java.util.TimeZone

class ConversionsSpec extends WordSpec with Matchers with MockitoSugar with Logging {

  import Conversions._

  private val copticCaracas = CopticChronology.getInstance(DateTimeZone.forID("America/Caracas"))
  private val islamicIstanbul = IslamicChronology.getInstance(DateTimeZone.forID("Europe/Istanbul"))
  private val gregorianGuatemala = GregorianChronology.getInstance(DateTimeZone.forID("America/Guatemala"))
  private val buddhistBudapest = BuddhistChronology.getInstance(DateTimeZone.forID("Europe/Budapest"))

  private val sydney = DateTimeZone.forID("Australia/Sydney")

  "Assigning a Time to a Long" should {

    "produce the right value #1" in {
      val value1: Long = Time(100000L)
      value1 shouldBe 100000L
    }

    "produce the right value #2" in {
      val value2: Long = Time(1381386682010L)
      value2 shouldBe 1381386682010L
    }

    "produce the right value #3" in {
      val value3: Long = Time(-1381386682010L)
      value3 shouldBe -1381386682010L
    }
  }

  "Assigning a Long to a Time" should {

    "produce the right Time #1" in {
      val time: Time = 100000L
      time shouldBe Time(100000L)
    }

    "produce the right Time #2" in {
      val time: Time = 1381386682010L
      time shouldBe Time(1381386682010L)
    }

    "produce the right Time #3" in {
      val time: Time = -1381386682010L
      time shouldBe Time(-1381386682010L)
    }
  }

  "Assigning a Time from a Joda Instant" should {

    "produce the right Time (regardless of the chronology and timezone #1)" in {
      val time: Time = new DateTime(100000L, isoUtc)
      time shouldBe Time(100000L)
    }

    "produce the right Time (regardless of the chronology and timezone #2)" in {
      val time: Time = new DateTime(100000L, copticCaracas)
      time shouldBe Time(100000L)
    }

    "produce the right Time (regardless of the chronology and timezone #3)" in {
      val time: Time = new DateTime(100000L, islamicIstanbul)
      time shouldBe Time(100000L)
    }

    "produce the right Time (regardless of the chronology and timezone #4)" in {
      val time: Time = new DateTime(100000L, gregorianGuatemala)
      time shouldBe Time(100000L)
    }

    "produce the right Time (regardless of the chronology and timezone #5)" in {
      val time: Time = new DateTime(100000L, buddhistBudapest)
      time shouldBe Time(100000L)
    }

    "return a Time object representing the same instant for a simply constructed DateTime in system Chronology #1" in {
      val time: Time = new DateTime(45345435367L)
      time shouldBe Time(45345435367L)
    }

    "return a Time object representing the same instant for a simply constructed DateTime in system Chronology #2" in {
      val time: Time = new DateTime(1000L)
      time shouldBe Time(1000L)
    }

    "return a Time object representing the same instant" in {
      val time: Time = new DateTime(45345435367L, isoUtc)
      time shouldBe Time(45345435367L)
    }

    "return a Time object representing the same instant for a DateTime in a different chronology and time zone" in {
      val time: Time = new DateTime(45345435367L, CopticChronology.getInstance(DateTimeZone.forID("Europe/London")))
      time shouldBe Time(45345435367L)
    }

    "return a Time object representing the same instant for some other ReadableInstant subclass in the PST timezone" in {
      val time: Time = new DateMidnight(1375080285918L, DateTimeZone.forTimeZone(TimeZone.getTimeZone("PST")))
      time shouldBe Time(1374994800000L)
    }

    "return a Time object representing the same instant for some other ReadableInstant subclass in the UK timezone" in {
      val time: Time = new DateMidnight(1375080285918L, DateTimeZone.forID("Europe/London"))
      time shouldBe Time(1375052400000L)
    }
  }

  "Assigning a Joda DateTime from a Time" should {

    "produce the right DateTime with ISO chronology and UTC timezone #1" in {
      val joda: DateTime = Time(100000L)
      joda shouldBe new DateTime(100000L, isoUtc)
    }

    "produce the right DateTime with ISO chronology and UTC timezone #2" in {
      val joda: DateTime = Time(2013, 10, 22, 11, 44, 33, 234, Zone("Europe/London"))
      joda should not be new DateTime(2013, 10, 22, 11, 44, 33, 234, DateTimeZone.forID("Europe/London"))
      joda shouldBe new DateTime(2013, 10, 22, 10, 44, 33, 234, isoUtc)
    }

    "produce the right DateTime with ISO chronology and UTC timezone #3" in {
      val joda: DateTime = Time(381386682010L)
      joda shouldBe new DateTime(381386682010L, isoUtc)
    }

    "produce the right DateTime with ISO chronology and UTC timezone #4" in {
      val joda: DateTime = Time(-381386682010L)
      joda shouldBe new DateTime(-381386682010L, isoUtc)
    }

    "produce the right DateTime with ISO chronology and UTC timezone #5" in {
      val joda: DateTime = Time(1231231232L)
      joda shouldBe new DateTime(1231231232L, isoUtc)
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

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #1" in {
      val date: Date = new LocalDate(2013, 4, 22, GregorianChronology.getInstanceUTC)
      date shouldBe Date(2013, 4, 22)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #2" in {
      val date: Date = new LocalDate(2013, 4, 22, GregorianChronology.getInstance(sydney, 5))
      date shouldBe Date(2013, 4, 22)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #3" in {
      val date: Date = new LocalDate(2013, 2, 21, GregorianChronology.getInstanceUTC)
      date shouldBe Date(2013, 2, 21)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #4" in {
      val date: Date = new LocalDate(2013, 2, 21, GregorianChronology.getInstance(sydney, 2))
      date shouldBe Date(2013, 2, 21)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #5" in {
      val date: Date = new LocalDate(2013, 4, 22, CopticChronology.getInstanceUTC)
      date shouldBe Date(2297, 1, 2)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #6" in {
      val date: Date = new LocalDate(2013, 4, 22, CopticChronology.getInstance(sydney, 1))
      date shouldBe Date(2297, 1, 2)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #7" in {
      val date: Date = new LocalDate(2013, 2, 21, CopticChronology.getInstanceUTC)
      date shouldBe Date(2296, 11, 2)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #8" in {
      val date: Date = new LocalDate(2013, 2, 21, CopticChronology.getInstance(sydney, 1))
      date shouldBe Date(2296, 11, 2)
    }
  }

  "Assigning a Joda LocalDate from a Date" should {

    "produce the right LocalDate with ISO chronology and UTC timezone #1" in {
      val joda: LocalDate = Date(2013, 11, 22)
      joda shouldBe new LocalDate(2013, 11, 22, isoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #2" in {
      val joda: LocalDate = Date(1945, 12, 1)
      joda shouldBe new LocalDate(1945, 12, 1, isoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #3" in {
      val joda: LocalDate = Date(1066, 1, 11)
      joda shouldBe new LocalDate(1066, 1, 11, isoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #4" in {
      val joda: LocalDate = Date(1492, 12, 22)
      joda shouldBe new LocalDate(1492, 12, 22, isoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #5" in {
      val joda: LocalDate = Date(2013, 6, 22)
      joda shouldBe new LocalDate(2013, 6, 22, isoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #6" in {
      val joda: LocalDate = Date(-3424, 12, 31)
      joda shouldBe new LocalDate(-3424, 12, 31, isoUtc)
    }


    "produce the right LocalDate with ISO chronology and UTC timezone #7" in {
      val joda: LocalDate = Date(1959, 1, 1)
      joda shouldBe new LocalDate(1959, 1, 1, isoUtc)
    }
  }
}
