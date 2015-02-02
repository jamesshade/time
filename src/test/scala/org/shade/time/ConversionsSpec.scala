/*
 *  Copyright 2013-2015 James Shade
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.shade.time

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import org.joda.time.{DateMidnight, LocalDate, DateTimeZone, DateTime}
import org.joda.time.chrono.{BuddhistChronology, IslamicChronology, GregorianChronology, CopticChronology}
import java.util.TimeZone

class ConversionsSpec extends WordSpec with Matchers with MockitoSugar {

  import Conversions._

  private val CopticCaracas = CopticChronology.getInstance(DateTimeZone.forID("America/Caracas"))
  private val IslamicIstanbul = IslamicChronology.getInstance(DateTimeZone.forID("Europe/Istanbul"))
  private val GregorianGuatemala = GregorianChronology.getInstance(DateTimeZone.forID("America/Guatemala"))
  private val BuddhistBudapest = BuddhistChronology.getInstance(DateTimeZone.forID("Europe/Budapest"))

  private val Sydney = DateTimeZone.forID("Australia/Sydney")

  "Assigning a Time to a Long" should {

    "produce the right value #1" in {
      val value1: Long = Instant(100000L)
      value1 shouldBe 100000L
    }

    "produce the right value #2" in {
      val value2: Long = Instant(1381386682010L)
      value2 shouldBe 1381386682010L
    }

    "produce the right value #3" in {
      val value3: Long = Instant(-1381386682010L)
      value3 shouldBe -1381386682010L
    }
  }

  "Assigning a Long to a Time" should {

    "produce the right Time #1" in {
      val instant: Instant = 100000L
      instant shouldBe Instant(100000L)
    }

    "produce the right Time #2" in {
      val instant: Instant = 1381386682010L
      instant shouldBe Instant(1381386682010L)
    }

    "produce the right Time #3" in {
      val instant: Instant = -1381386682010L
      instant shouldBe Instant(-1381386682010L)
    }
  }

  "Assigning a Time from a Joda Instant" should {

    "produce the right Time (regardless of the chronology and timezone #1)" in {
      val instant: Instant = new DateTime(100000L, IsoUtc)
      instant shouldBe Instant(100000L)
    }

    "produce the right Time (regardless of the chronology and timezone #2)" in {
      val instant: Instant = new DateTime(100000L, CopticCaracas)
      instant shouldBe Instant(100000L)
    }

    "produce the right Time (regardless of the chronology and timezone #3)" in {
      val instant: Instant = new DateTime(100000L, IslamicIstanbul)
      instant shouldBe Instant(100000L)
    }

    "produce the right Time (regardless of the chronology and timezone #4)" in {
      val instant: Instant = new DateTime(100000L, GregorianGuatemala)
      instant shouldBe Instant(100000L)
    }

    "produce the right Time (regardless of the chronology and timezone #5)" in {
      val instant: Instant = new DateTime(100000L, BuddhistBudapest)
      instant shouldBe Instant(100000L)
    }

    "return a Time object representing the same instant for a simply constructed DateTime in system Chronology #1" in {
      val instant: Instant = new DateTime(45345435367L)
      instant shouldBe Instant(45345435367L)
    }

    "return a Time object representing the same instant for a simply constructed DateTime in system Chronology #2" in {
      val instant: Instant = new DateTime(1000L)
      instant shouldBe Instant(1000L)
    }

    "return a Time object representing the same instant" in {
      val instant: Instant = new DateTime(45345435367L, IsoUtc)
      instant shouldBe Instant(45345435367L)
    }

    "return an Instant object representing the same instant for a DateTime in a different chronology and time zone" in {
      val instant: Instant = new DateTime(45345435367L, CopticChronology.getInstance(DateTimeZone.forID("Europe/London")))
      instant shouldBe Instant(45345435367L)
    }

    "return an Instant object representing the same instant for some other ReadableInstant subclass in the PST timezone" in {
      val instant: Instant = new DateMidnight(1375080285918L, DateTimeZone.forTimeZone(TimeZone.getTimeZone("PST")))
      instant shouldBe Instant(1374994800000L)
    }

    "return a Instant object representing the same instant for some other ReadableInstant subclass in the UK timezone" in {
      val instant: Instant = new DateMidnight(1375080285918L, DateTimeZone.forID("Europe/London"))
      instant shouldBe Instant(1375052400000L)
    }
  }

  "Assigning a Joda DateTime from an Instant" should {

    "produce the right DateTime with ISO chronology and UTC timezone #1" in {
      val joda: DateTime = Instant(100000L)
      joda shouldBe new DateTime(100000L, IsoUtc)
    }

    "produce the right DateTime with ISO chronology and UTC timezone #2" in {
      val joda: DateTime = Zone("Europe/London")(2013, 10, 22, 11, 44, 33, 234)
      joda should not be new DateTime(2013, 10, 22, 11, 44, 33, 234, DateTimeZone.forID("Europe/London"))
      joda shouldBe new DateTime(2013, 10, 22, 10, 44, 33, 234, IsoUtc)
    }

    "produce the right DateTime with ISO chronology and UTC timezone #3" in {
      val joda: DateTime = Instant(381386682010L)
      joda shouldBe new DateTime(381386682010L, IsoUtc)
    }

    "produce the right DateTime with ISO chronology and UTC timezone #4" in {
      val joda: DateTime = Instant(-381386682010L)
      joda shouldBe new DateTime(-381386682010L, IsoUtc)
    }

    "produce the right DateTime with ISO chronology and UTC timezone #5" in {
      val joda: DateTime = Instant(1231231232L)
      joda shouldBe new DateTime(1231231232L, IsoUtc)
    }
  }

  "Assigning a Date from a Joda LocalDate" should {

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #1" in {
      val date: Date = new LocalDate(1730, 2, 7, CopticCaracas)
      date shouldBe Date(2013, 10, 17)
    }

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #2a" in {
      val date: Date = new LocalDate(2013, 11, 22, IslamicIstanbul)
      date shouldBe Date(2575, 7, 2)
    }

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #2b" in {
      val date: Date = new LocalDate(1434, 12, 12, IslamicIstanbul)
      date shouldBe Date(2013, 10, 17)
    }

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #3" in {
        val date: Date = new LocalDate(2013, 11, 22, GregorianGuatemala)
        date shouldBe Date(2013, 11, 22)
    }

    "create a Date containing the correct day, month and year (converting to ISO Chronology and ignoring timezone) #5" in {
      val date: Date = new LocalDate(2556, 10, 17, BuddhistBudapest)
      date shouldBe Date(2013, 10, 17)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #1" in {
      val date: Date = new LocalDate(2013, 4, 22, GregorianChronology.getInstanceUTC)
      date shouldBe Date(2013, 4, 22)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #2" in {
      val date: Date = new LocalDate(2013, 4, 22, GregorianChronology.getInstance(Sydney, 5))
      date shouldBe Date(2013, 4, 22)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #3" in {
      val date: Date = new LocalDate(2013, 2, 21, GregorianChronology.getInstanceUTC)
      date shouldBe Date(2013, 2, 21)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #4" in {
      val date: Date = new LocalDate(2013, 2, 21, GregorianChronology.getInstance(Sydney, 2))
      date shouldBe Date(2013, 2, 21)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #5" in {
      val date: Date = new LocalDate(2013, 4, 22, CopticChronology.getInstanceUTC)
      date shouldBe Date(2297, 1, 2)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #6" in {
      val date: Date = new LocalDate(2013, 4, 22, CopticChronology.getInstance(Sydney, 1))
      date shouldBe Date(2297, 1, 2)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #7" in {
      val date: Date = new LocalDate(2013, 2, 21, CopticChronology.getInstanceUTC)
      date shouldBe Date(2296, 11, 2)
    }

    "create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #8" in {
      val date: Date = new LocalDate(2013, 2, 21, CopticChronology.getInstance(Sydney, 1))
      date shouldBe Date(2296, 11, 2)
    }
  }

  "Assigning a Joda LocalDate from a Date" should {

    "produce the right LocalDate with ISO chronology and UTC timezone #1" in {
      val joda: LocalDate = Date(2013, 11, 22)
      joda shouldBe new LocalDate(2013, 11, 22, IsoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #2" in {
      val joda: LocalDate = Date(1945, 12, 1)
      joda shouldBe new LocalDate(1945, 12, 1, IsoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #3" in {
      val joda: LocalDate = Date(1066, 1, 11)
      joda shouldBe new LocalDate(1066, 1, 11, IsoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #4" in {
      val joda: LocalDate = Date(1492, 12, 22)
      joda shouldBe new LocalDate(1492, 12, 22, IsoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #5" in {
      val joda: LocalDate = Date(2013, 6, 22)
      joda shouldBe new LocalDate(2013, 6, 22, IsoUtc)
    }

    "produce the right LocalDate with ISO chronology and UTC timezone #6" in {
      val joda: LocalDate = Date(-3424, 12, 31)
      joda shouldBe new LocalDate(-3424, 12, 31, IsoUtc)
    }


    "produce the right LocalDate with ISO chronology and UTC timezone #7" in {
      val joda: LocalDate = Date(1959, 1, 1)
      joda shouldBe new LocalDate(1959, 1, 1, IsoUtc)
    }
  }

  "Assigning Zone from a Joda DateTimeZone" should {

    "produce the right Zone #1" in {
      val id = "Europe/London"
      val joda = DateTimeZone.forID(id)
      val zone: Zone = joda
      zone.id shouldBe id
      zone.joda shouldBe joda
    }

    "produce the right Zone #2" in {
      val id = "+05:00"
      val joda = DateTimeZone.forID(id)
      val zone: Zone = joda
      zone.id shouldBe id
      zone.joda shouldBe joda
    }

    "produce the right Zone #3" in {
      val id = "-05:00"
      val joda = DateTimeZone.forID(id)
      val zone: Zone = joda
      zone.id shouldBe id
      zone.joda shouldBe joda
    }

    "produce the right Zone #4" in {
      val id = "Pacific/Honolulu"
      val joda = DateTimeZone.forID(id)
      val zone: Zone = joda
      zone.id shouldBe id
      zone.joda shouldBe joda
    }
  }

  "Assigning Joda DateTimeZone from a Zone" should {

    "produce the right DateTimeZone #1" in {
      val id = "Europe/London"
      val zone: Zone = Zone(id)
      val joda: DateTimeZone = zone
      joda.getID shouldBe id
      joda shouldBe zone.joda
    }

    "produce the right DateTimeZone #2" in {
      val id = "+05:00"
      val zone: Zone = Zone(id)
      val joda: DateTimeZone = zone
      joda.getID shouldBe id
      joda shouldBe zone.joda
    }

    "produce the right DateTimeZone #3" in {
      val id = "-05:00"
      val zone: Zone = Zone(id)
      val joda: DateTimeZone = zone
      joda.getID shouldBe id
      joda shouldBe zone.joda
    }

    "produce the right DateTimeZone #4" in {
      val id = "Pacific/Honolulu"
      val zone: Zone = Zone(id)
      val joda: DateTimeZone = zone
      joda.getID shouldBe id
      joda shouldBe zone.joda
    }
  }
}


