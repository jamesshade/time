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

import org.joda.time.{DateTime, DateTimeZone}
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

class ZoneSpec extends WordSpec with Matchers with MockitoSugar {

  private val london = Zone("Europe/London")
  private val addisAbaba = Zone("Africa/Addis_Ababa")
  private val utc = Zone.UTC
  private val stockholm = Zone("Europe/Stockholm")
  private val sydney = Zone("Australia/Sydney")
  private val honolulu = Zone("Pacific/Honolulu")

  "The Zone constructor" should {

    "construct the Zone with correct details if the Zone ID is valid - e.g. Paris" in {
      val paris = Zone("Europe/Paris")
      paris.id shouldBe "Europe/Paris"
    }

    "construct the Zone with correct details if the Zone ID is valid - e.g. UTC-05:00" in {
      val minus5 = Zone("-05:00")
      minus5.id shouldBe "-05:00"
    }

    "construct the Zone with correct details if the Zone ID is valid - e.g. UTC+0500" in {
      val plus5 = Zone("+0500")
      plus5.id shouldBe "+0500"
    }

    "throw a NullPointerException if the Zone is null" in {
      val str: String = null
      val thrown = the [NullPointerException] thrownBy Zone(str)
      thrown.getMessage shouldBe "Time zone id is null"
    }

    "throw an InvalidZoneException if the Zone is empty" in {
      val thrown = the [InvalidZoneException] thrownBy Zone("")
      thrown.getMessage should startWith ("Unknown/invalid time zone ''")
    }

    "throw an InvalidZoneException if the Zone is invalid #1" in {
      val thrown = the [InvalidZoneException] thrownBy Zone("+blah:blah")
      thrown.getMessage should startWith ("Unknown/invalid time zone '+blah:blah'")
    }

    "throw an InvalidZoneException if the Zone is invalid #2" in {
      val thrown = the [InvalidZoneException] thrownBy Zone("+55:00")
      thrown.getMessage should startWith ("Unknown/invalid time zone '+55:00'")
    }

    "throw an InvalidZoneException if the Zone is invalid #3" in {
      val thrown = the [InvalidZoneException] thrownBy Zone("Europe/Atlantis")
      thrown.getMessage should startWith ("Unknown/invalid time zone 'Europe/Atlantis'")
    }
  }

  "The (package private) joda method" should {

    "return the Joda DateTimeZone corresponding to the Zone ID - e.g. for Paris" in {
      val paris = Zone("Europe/Paris")
      paris.joda shouldBe DateTimeZone.forID("Europe/Paris")
    }

    "return the Joda DateTimeZone corresponding to the Zone ID - e.g. for UTC-05:00" in {
      val minus5 = Zone("-05:00")
      minus5.joda shouldBe DateTimeZone.forID("-05:00")
    }

    "return the Joda DateTimeZone corresponding to the Zone ID - e.g. for UTC+0500" in {
      val plus5 = Zone("+0500")
      plus5.joda shouldBe DateTimeZone.forID("+0500")
    }
  }

  "The toString method" should {

    "return the ID of the time zone - e.g. Paris" in {
      Zone("Europe/Paris").toString shouldBe "Europe/Paris"
    }

    "return the ID of the time zone - e.g. UTC-05:00" in {
      Zone("-05:00").toString shouldBe "-05:00"
    }

    "return the ID of the time zone - e.g. UTC+0500" in {
      Zone("+0500").toString shouldBe "+0500"
    }
  }

  "The Zone instance apply method taking time fields" should {

    "return the correct instant for the zone" in {
      london(2013, 5, 22, 11, 43, 23, 222) shouldBe Instant(1369219403222L)
    }

    "return the correct instant #2" in {
      // 1600-06-12T10:44:44.111+02:34:48 =  (In UTC = -11661974115889 - 9288000 )
      addisAbaba(1600, 6, 12, 10, 44, 44, 111) shouldBe Instant(-11661974115889L - 9288000L)
    }

    "throw an InvalidDateException if there are invalid values in the date fields" in {
      an [InvalidDateException] should be thrownBy utc(1900, 2, 29, 11, 33, 22, 222)
      an [InvalidDateException] should be thrownBy utc(2000, 2, 30, 11, 33, 22, 222)
    }

    "throw an InvalidTimeException if there are invalid values in the time fields" in {
      an [InvalidTimeException] should be thrownBy utc(2013, 8, 25, 12, 60, 22, 222)
      an [InvalidTimeException] should be thrownBy utc(2013, 4, 22, 11, 33, 22, 1000)
    }

    "throw an InvalidDateException if there are invalid values in both the date fields and the time fields" in {
      an [InvalidDateException] should be thrownBy utc(1900, 2, 29, 24, 33, 22, 222)
      an [InvalidDateException] should be thrownBy utc(2000, 2, 30, 11, -1, 22, 222)
    }

    "throw an InvalidTimeInZone exception if the time is invalid due to daylight savings time changes in a given zone" in {
      an [InvalidTimeInZoneException] should be thrownBy london(2013, 3, 31, 1, 30, 25, 222)
    }
  }

  "The Zone instance apply method taking a DateAndTime object" should {

    "return the correct instant for this zone #1" in {
      london(DateAndTime(2013, 5, 22, 11, 43, 23, 222)) shouldBe Instant(1369219403222L)
    }

    "return the correct instant for this zone #2" in {
      // 1600-06-12T10:44:44.111+02:34:48 =  (In UTC = -11661974115889 - 9288000 )
      addisAbaba(DateAndTime(1600, 6, 12, 10, 44, 44, 111)) shouldBe Instant(-11661974115889L - 9288000L)
    }

    "throw an exception if the time is invalid due to daylight savings time changes in a given zone" in {
      an [InvalidTimeInZoneException] should be thrownBy london(DateAndTime(2013, 3, 31, 1, 30, 25, 222))
    }
  }

  "The Zone instance apply method taking a Date object and a Time object" should {

    "return the correct instant for this zone #1" in {
      london(Date(2013, 5, 22), Time(11, 43, 23, 222)) shouldBe Instant(1369219403222L)
    }

    "return the correct instant for this zone #2" in {
      // 1600-06-12T10:44:44.111+02:34:48 =  (In UTC = -11661974115889 - 9288000 )
      addisAbaba(Date(1600, 6, 12), Time(10, 44, 44, 111)) shouldBe Instant(-11661974115889L - 9288000L)
    }

    "throw an exception if the time is invalid due to daylight savings time changes in a given zone" in {
      an [InvalidTimeInZoneException] should be thrownBy london(Date(2013, 3, 31), Time(1, 30, 25, 222))
    }
  }

  "The Zone instance unapply method" should {

    "match a time from the appropriate zone (e.g. London)" in {
      val instant = Instant(new DateTime(2013, 6, 22, 11, 55, 22, 666, DateTimeZone.UTC).getMillis)

      val x = instant match {
        case london(2013, 6, 22, 11, 55, 22, 666) => fail("This is the UTC time not the UK time")
        case london(2013, 6, 22, 12, 55, 22, 666) => "correct!"
        case _ => fail("Didn't match")
      }

      x shouldBe "correct!"
    }

    "match a time from the appropriate zone (e.g. Sydney)" in {
      val instant = Instant(new DateTime(2013, 6, 22, 15, 55, 22, 666, DateTimeZone.UTC).getMillis)

      val x = instant match {
        case sydney(2013, 6, 22, 11, 55, 22, 666) => fail("This is the UTC time not the Australian time")
        case sydney(2013, 6, 23, 1, 55, 22, 666) => "correct!"
        case _ => fail("Didn't match")
      }

      x shouldBe "correct!"
    }
  }

  "The today method" should {

    "return the current date in the zone when the time on the clock corresponds to Tue 30 Jul 2013 08:07:13.370 BST" in {

      implicit val clock = new Clock {override val now = Instant(1375168033370L)}

      utc.today shouldBe Date(2013, 7, 30)
      london.today shouldBe Date(2013, 7, 30)
      stockholm.today shouldBe Date(2013, 7, 30)
      sydney.today shouldBe Date(2013, 7, 30)
      honolulu.today shouldBe Date(2013, 7, 29)
    }

    "return the current date in the zone when the time on the clock corresponds to Tue 29 Feb 2000 23:30:00.000 GMT" in {

      implicit val clock = new Clock {override val now = Instant(951867000000L)}

      utc.today shouldBe Date(2000, 2, 29)
      london.today shouldBe Date(2000, 2, 29)
      stockholm.today shouldBe Date(2000, 3, 1)
      sydney.today shouldBe Date(2000, 3, 1)
      honolulu.today shouldBe Date(2000, 2, 29)
    }

    "return the current date in the zone when the time on the clock corresponds to Mon 21 Mar 1977 00:00:00.000 BST" in {

      implicit val clock = new Clock {override val now = Instant(227746800000L)}

      utc.today shouldBe Date(1977, 3, 20)
      london.today shouldBe Date(1977, 3, 21)
      stockholm.today shouldBe Date(1977, 3, 21)
      sydney.today shouldBe Date(1977, 3, 21)
      honolulu.today shouldBe Date(1977, 3, 20)
    }
  }

  "The dateAndTimeOf method" should {

    "return the correct DateAndTime in this zone for the supplied instant #1" in {

      val instant = Instant(1375168033370L) // Tue 30 Jul 2013 08:07:13.370 BST

      utc.dateAndTimeOf(instant) should equal(DateAndTime(2013, 7, 30, 7, 7, 13, 370))
      london.dateAndTimeOf(instant) should equal(DateAndTime(2013, 7, 30, 8, 7, 13, 370))
      stockholm.dateAndTimeOf(instant) should equal(DateAndTime(2013, 7, 30, 9, 7, 13, 370))
      sydney.dateAndTimeOf(instant) should equal(DateAndTime(2013, 7, 30, 17, 7, 13, 370))
      honolulu.dateAndTimeOf(instant) should equal(DateAndTime(2013, 7, 29, 21, 7, 13, 370))
    }

    "return the correct DateAndTime in this zone for the supplied instant #2" in {

      val instant = Instant(951867000000L) // Tue 29 Feb 2000 23:30:00.000 GMT

      utc.dateAndTimeOf(instant) should equal(DateAndTime(2000, 2, 29, 23, 30, 0, 0))
      london.dateAndTimeOf(instant) should equal(DateAndTime(2000, 2, 29, 23, 30, 0, 0))
      stockholm.dateAndTimeOf(instant) should equal(DateAndTime(2000, 3, 1, 0, 30, 0, 0))
      sydney.dateAndTimeOf(instant) should equal(DateAndTime(2000, 3, 1, 10, 30, 0, 0))
      honolulu.dateAndTimeOf(instant) should equal(DateAndTime(2000, 2, 29, 13, 30, 0, 0))
    }

    "return the correct DateAndTime in this zone for the supplied instant #3" in {

      val instant = Instant(227746800000L) // Mon 21 Mar 1977 00:00:00.000 BST

      utc.dateAndTimeOf(instant) should equal(DateAndTime(1977, 3, 20, 23, 0, 0, 0))
      london.dateAndTimeOf(instant) should equal(DateAndTime(1977, 3, 21, 0, 0, 0, 0))
      stockholm.dateAndTimeOf(instant) should equal(DateAndTime(1977, 3, 21, 0, 0, 0, 0))
      sydney.dateAndTimeOf(instant) should equal(DateAndTime(1977, 3, 21, 9, 0, 0, 0))
      honolulu.dateAndTimeOf(instant) should equal(DateAndTime(1977, 3, 20, 13, 0, 0, 0))
    }
  }

  "The dateOf method" should {

    "return the correct Date in this zone for the supplied instant #1" in {

      val instant = Instant(1375168033370L) // Tue 30 Jul 2013 08:07:13.370 BST

      utc.dateOf(instant) should equal(Date(2013, 7, 30))
      london.dateOf(instant) should equal(Date(2013, 7, 30))
      stockholm.dateOf(instant) should equal(Date(2013, 7, 30))
      sydney.dateOf(instant) should equal(Date(2013, 7, 30))
      honolulu.dateOf(instant) should equal(Date(2013, 7, 29))
    }

    "return the correct Date in this zone for the supplied instant #2" in {

      val instant = Instant(951867000000L) // Tue 29 Feb 2000 23:30:00.000 GMT

      utc.dateOf(instant) should equal(Date(2000, 2, 29))
      london.dateOf(instant) should equal(Date(2000, 2, 29))
      stockholm.dateOf(instant) should equal(Date(2000, 3, 1))
      sydney.dateOf(instant) should equal(Date(2000, 3, 1))
      honolulu.dateOf(instant) should equal(Date(2000, 2, 29))
    }

    "return the correct Date in this zone for the supplied instant #3" in {

      val instant = Instant(227746800000L) // Mon 21 Mar 1977 00:00:00.000 BST

      utc.dateOf(instant) should equal(Date(1977, 3, 20))
      london.dateOf(instant) should equal(Date(1977, 3, 21))
      stockholm.dateOf(instant) should equal(Date(1977, 3, 21))
      sydney.dateOf(instant) should equal(Date(1977, 3, 21))
      honolulu.dateOf(instant) should equal(Date(1977, 3, 20))
    }
  }

  "The timeOf method" should {

    "return the correct Time in this zone for the supplied instant #1" in {

      val instant = Instant(1375168033370L) // Tue 30 Jul 2013 08:07:13.370 BST

      utc.timeOf(instant) should equal(Time(7, 7, 13, 370))
      london.timeOf(instant) should equal(Time(8, 7, 13, 370))
      stockholm.timeOf(instant) should equal(Time(9, 7, 13, 370))
      sydney.timeOf(instant) should equal(Time(17, 7, 13, 370))
      honolulu.timeOf(instant) should equal(Time(21, 7, 13, 370))
    }

    "return the correct Time in this zone for the supplied instant #2" in {

      val instant = Instant(951867000000L) // Tue 29 Feb 2000 23:30:00.000 GMT

      utc.timeOf(instant) should equal(Time(23, 30, 0, 0))
      london.timeOf(instant) should equal(Time(23, 30, 0, 0))
      stockholm.timeOf(instant) should equal(Time(0, 30, 0, 0))
      sydney.timeOf(instant) should equal(Time(10, 30, 0, 0))
      honolulu.timeOf(instant) should equal(Time(13, 30, 0, 0))
    }

    "return the correct Time in this zone for the supplied instant #3" in {

      val instant = Instant(227746800000L) // Mon 21 Mar 1977 00:00:00.000 BST

      utc.timeOf(instant) should equal(Time(23, 0, 0, 0))
      london.timeOf(instant) should equal(Time(0, 0, 0, 0))
      stockholm.timeOf(instant) should equal(Time(0, 0, 0, 0))
      sydney.timeOf(instant) should equal(Time(9, 0, 0, 0))
      honolulu.timeOf(instant) should equal(Time(13, 0, 0, 0))
    }
  }

  "Requesting UTC from the Zone object" should {
    "Return a Zone containing UTC" in {
      Zone.UTC.id shouldBe "UTC"
      Zone.UTC.joda shouldBe DateTimeZone.UTC
    }
  }

  "The system time zone provided by the Zone object" should {

    "be the current default (Joda) system time zone" in {
      Zone.System.id shouldBe DateTimeZone.getDefault.getID
    }
  }
}