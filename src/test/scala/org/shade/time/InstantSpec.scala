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

class InstantSpec extends WordSpec with Matchers with MockitoSugar {

  "Constructing an Instant with a particular millisecond value" should {

    "return a Instant object with that value" in {
      Instant(13243432434L).millis shouldBe 13243432434L
    }
  }

  "The isBefore and < methods" should {

    val millis = 435345345L
    val thisInstant = Instant(millis)

    "return false if the other instant is long before this instant" in {
      val otherInstant = Instant(millis - 22332232L)
      thisInstant isBefore otherInstant shouldBe false
      thisInstant < otherInstant shouldBe false
    }

    "return false if the other instant is one millisecond before this instant" in {
      val otherInstant = Instant(millis - 1L)
      thisInstant isBefore otherInstant shouldBe false
      thisInstant < otherInstant shouldBe false
    }

    "return false if the other instant is the same as this instant" in {
      val otherInstant = Instant(millis)
      thisInstant isBefore otherInstant shouldBe false
      thisInstant < otherInstant shouldBe false
    }

    "return true if the other instant is one millisecond after this instant" in {
      val otherInstant = Instant(millis + 1L)
      thisInstant isBefore otherInstant shouldBe true
      thisInstant < otherInstant shouldBe true
    }

    "return true if the other instant is long after this instant" in {
      val otherInstant = Instant(millis + 5345435435L)
      thisInstant isBefore otherInstant shouldBe true
      thisInstant < otherInstant shouldBe true
    }
  }

  "The isAtOrBefore and <= methods" should {

    val millis = 435345345L
    val thisInstant = Instant(millis)

    "return false if the other instant is long before this instant" in {
      val otherInstant = Instant(millis - 22332232L)
      thisInstant isAtOrBefore otherInstant shouldBe false
      thisInstant <= otherInstant shouldBe false
    }

    "return false if the other instant is one millisecond before this instant" in {
      val otherInstant = Instant(millis - 1L)
      thisInstant isAtOrBefore otherInstant shouldBe false
      thisInstant <= otherInstant shouldBe false
    }

    "return true if the other instant is the same as this instant" in {
      val otherInstant = Instant(millis)
      thisInstant isAtOrBefore otherInstant shouldBe true
      thisInstant <= otherInstant shouldBe true
    }

    "return true if the other instant is one millisecond after this instant" in {
      val otherInstant = Instant(millis + 1L)
      thisInstant isAtOrBefore otherInstant shouldBe true
      thisInstant <= otherInstant shouldBe true
    }

    "return true if the other instant is long after this instant" in {
      val otherInstant = Instant(millis + 5345435435L)
      thisInstant isAtOrBefore otherInstant shouldBe true
      thisInstant <= otherInstant shouldBe true
    }
  }


  "The isAtOrAfter and >= methods" should {

    val millis = 435345345L
    val thisInstant = Instant(millis)

    "return true if the other instant is long before this instant" in {
      val otherInstant = Instant(millis - 22332232L)
      thisInstant isAtOrAfter otherInstant shouldBe true
      thisInstant >= otherInstant shouldBe true
    }

    "return true if the other instant is one millisecond before this instant" in {
      val otherInstant = Instant(millis - 1L)
      thisInstant isAtOrAfter otherInstant shouldBe true
      thisInstant >= otherInstant shouldBe true
    }

    "return true if the other instant is the same as this instant" in {
      val otherInstant = Instant(millis)
      thisInstant isAtOrAfter otherInstant shouldBe true
      thisInstant >= otherInstant shouldBe true
    }

    "return false if the other instant is one millisecond after this instant" in {
      val otherInstant = Instant(millis + 1L)
      thisInstant isAtOrAfter otherInstant shouldBe false
      thisInstant >= otherInstant shouldBe false
    }

    "return false if the other instant is long after this instant" in {
      val otherInstant = Instant(millis + 5345435435L)
      thisInstant isAtOrAfter otherInstant shouldBe false
      thisInstant >= otherInstant shouldBe false
    }
  }

  "The isAfter and > methods" should {

    val millis = 435345345L
    val thisInstant = Instant(millis)

    "return true if the other instant is long before this instant" in {
      val otherInstant = Instant(millis - 22332232L)
      thisInstant isAfter otherInstant shouldBe true
      thisInstant > otherInstant shouldBe true
    }

    "return true if the other instant is one millisecond before this instant" in {
      val otherInstant = Instant(millis - 1L)
      thisInstant isAfter otherInstant shouldBe true
      thisInstant > otherInstant shouldBe true
    }

    "return false if the other instant is the same as this instant" in {
      val otherInstant = Instant(millis)
      thisInstant isAfter otherInstant shouldBe false
      thisInstant > otherInstant shouldBe false
    }

    "return false if the other instant is one millisecond after this instant" in {
      val otherInstant = Instant(millis + 1L)
      thisInstant isAfter otherInstant shouldBe false
      thisInstant > otherInstant shouldBe false
    }

    "return false if the other instant is long after this instant" in {
      val otherInstant = Instant(millis + 5345435435L)
      thisInstant isAfter otherInstant shouldBe false
      thisInstant > otherInstant shouldBe false
    }
  }

  "The isAt and == methods" should {

    val millis = 435345345L
    val thisInstant = Instant(millis)

    "return false if the other instant is long before this instant" in {
      val otherInstant = Instant(millis - 22332232L)
      thisInstant isAt otherInstant shouldBe false
      thisInstant == otherInstant shouldBe false
    }

    "return false if the other instant is one millisecond before this instant" in {
      val otherInstant = Instant(millis - 1L)
      thisInstant isAt otherInstant shouldBe false
      thisInstant == otherInstant shouldBe false
    }

    "return true if the other instant is the same as this instant" in {
      val otherInstant = Instant(millis)
      thisInstant isAt otherInstant shouldBe true
      thisInstant == otherInstant shouldBe true
    }

    "return false if the other instant is one millisecond after this instant" in {
      val otherInstant = Instant(millis + 1L)
      thisInstant isAt otherInstant shouldBe false
      thisInstant == otherInstant shouldBe false
    }

    "return false if the other instant is long after this instant" in {
      val otherInstant = Instant(millis + 5345435435L)
      thisInstant isAt otherInstant shouldBe false
      thisInstant == otherInstant shouldBe false
    }
  }

  "The string representation of an Instant object" should {

    "be the ISO8601 format with milliseconds, in the UTC timezone" in {

      Instant(432342455L).toString shouldBe "1970-01-06T00:05:42.455Z"
      Instant(-1375081323539L).toString shouldBe "1926-06-05T16:57:56.461Z"
      Instant(1375081385429L).toString shouldBe "2013-07-29T07:03:05.429Z"
      Instant(4L).toString shouldBe "1970-01-01T00:00:00.004Z"
      Instant(564564566445L).toString shouldBe "1987-11-22T07:29:26.445Z"
    }
  }

  "The timeUntil method" should {

    "return zero for two copies of the same instant" in {
      val instant = Instant(45435435435L)
      instant.timeUntil(instant) shouldBe Duration(0)
    }

    "return zero for two identical instants" in {
      val instant1 = Instant(45435435435L)
      val instant2 = Instant(45435435435L)
      instant1.timeUntil(instant2) shouldBe Duration(0)
    }

    "return a Duration representing the number of milliseconds until the supplied instant" in {
      Instant(1000L).timeUntil(Instant(1001L)) shouldBe Duration(1L)
      Instant(176865765L).timeUntil(Instant(435435435L)) shouldBe Duration(258569670L)
      Instant(-4334L).timeUntil(Instant(0L)) shouldBe Duration(4334L)
    }

    "return a negative Duration if the time is before the time this instant holds" in {
      Instant(1001L).timeUntil(Instant(1000L)) shouldBe Duration(-1L)
      Instant(435435435L).timeUntil(Instant(176865765L)) shouldBe Duration(-258569670L)
      Instant(0L).timeUntil(Instant(-4334L)) shouldBe Duration(-4334L)
    }
  }

  "The timeSince method" should {

    "return zero for two copies of the same instant" in {
      val instant = Instant(45435435435L)
      instant.timeSince(instant) shouldBe Duration(0)
    }

    "return zero for two identical instants" in {
      val instant1 = Instant(45435435435L)
      val instant2 = Instant(45435435435L)
      instant1.timeSince(instant2) shouldBe Duration(0)
    }

    "return a Duration representing the number of milliseconds since the supplied instant" in {
      Instant(1001L).timeSince(Instant(1000L)) shouldBe Duration(1L)
      Instant(435435435L).timeSince(Instant(176865765L)) shouldBe Duration(258569670L)
      Instant(0L).timeSince(Instant(-4334L)) shouldBe Duration(4334L)
    }

    "return a negative Duration if the time is after the time this instant holds" in {
      Instant(1000L).timeSince(Instant(1001L)) shouldBe Duration(-1L)
      Instant(176865765L).timeSince(Instant(435435435L)) shouldBe Duration(-258569670L)
      Instant(-4334L).timeSince(Instant(0L)) shouldBe Duration(-4334L)
    }
  }

  "The timeBetween method" should {

    "return zero for two copies of the same instant" in {
      val instant = Instant(45435435435L)
      instant.timeSince(instant) shouldBe Duration(0)
    }

    "return zero for two identical instants" in {
      val instant1 = Instant(45435435435L)
      val instant2 = Instant(45435435435L)
      instant1.timeSince(instant2) shouldBe Duration(0)
    }

    "return a Duration representing the size of the gap between the two instants, always positive regardless of which instant comes first" in {
      Instant(1001L).timeBetween(Instant(1000L)) shouldBe Duration(1L)
      Instant(1000L).timeBetween(Instant(1001L)) shouldBe Duration(1L)

      Instant(435435435L).timeBetween(Instant(176865765L)) shouldBe Duration(258569670L)
      Instant(176865765L).timeBetween(Instant(435435435L)) shouldBe Duration(258569670L)

      Instant(0L).timeBetween(Instant(-4334L)) shouldBe Duration(4334L)
      Instant(-4334L).timeBetween(Instant(0L)) shouldBe Duration(4334L)
    }
  }

  "The instant minus duration operator" should {

    "Return an identical instant if the duration is zero" in {
      Instant(435435L) - Duration(0L) shouldBe Instant(435435L)
    }

    "Return an instant earlier than this if the duration is positive" in {
      Instant(435435L) - Duration(1L) shouldBe Instant(435434L)
      Instant(435435L) - Duration(435435L) shouldBe Instant(0L)
      Instant(-1000L) - Duration(1000L) shouldBe Instant(-2000L)
    }

    "Return an instant later than this if the duration is positive" in {
      Instant(435435L) - Duration(-1L) shouldBe Instant(435436L)
      Instant(435435L) - Duration(-435435L) shouldBe Instant(870870L)
      Instant(-1000L) - Duration(-1000L) shouldBe Instant(0L)
    }
  }

  "The instant plus duration operator" should {

    "Return an identical instant if the duration is zero" in {
      Instant(435435L) + Duration(0L) shouldBe Instant(435435L)
    }

    "Return an instant later than this if the duration is positive" in {
      Instant(435435L) + Duration(1L) shouldBe Instant(435436L)
      Instant(435435L) + Duration(435435L) shouldBe Instant(870870L)
      Instant(-1000L) + Duration(1000L) shouldBe Instant(0L)
    }

    "Return an instant earlier than this if the duration is positive" in {
      Instant(435435L) + Duration(-1L) shouldBe Instant(435434L)
      Instant(435435L) + Duration(-435435L) shouldBe Instant(0L)
      Instant(-1000L) + Duration(-1000L) shouldBe Instant(-2000L)
    }
  }

  "The compareTo method" should {

    "return -1 if this Instant is earlier than that passed in" in {
      Instant(-1).compareTo(Instant(0)) shouldBe -1
      Instant(0).compareTo(Instant(1)) shouldBe -1
      Instant(-14345435).compareTo(Instant(45345435)) shouldBe -1
      Instant(-1000).compareTo(Instant(-999)) shouldBe -1
      Instant(999).compareTo(Instant(1000)) shouldBe -1
      Instant(-10000).compareTo(Instant(1000)) shouldBe -1
    }

    "return 0 if this Instant is the same as that passed in" in {
      Instant(-1).compareTo(Instant(-1)) shouldBe 0
      Instant(0).compareTo(Instant(0)) shouldBe 0
      Instant(1).compareTo(Instant(1)) shouldBe 0
      Instant(-14345435).compareTo(Instant(-14345435)) shouldBe 0
      Instant(-45345435).compareTo(Instant(-45345435)) shouldBe 0
      Instant(-1000).compareTo(Instant(-1000)) shouldBe 0
      Instant(-999).compareTo(Instant(-999)) shouldBe 0
      Instant(999).compareTo(Instant(999)) shouldBe 0
      Instant(1000).compareTo(Instant(1000)) shouldBe 0
      Instant(-10000).compareTo(Instant(-10000)) shouldBe 0
      Instant(1000).compareTo(Instant(1000)) shouldBe 0
    }

    "return 1 if this Instant is later than that passed in" in {
      Instant(0).compareTo(Instant(-1)) shouldBe 1
      Instant(1).compareTo(Instant(0)) shouldBe 1
      Instant(45345435).compareTo(Instant(-14345435)) shouldBe 1
      Instant(-999).compareTo(Instant(-1000)) shouldBe 1
      Instant(1000).compareTo(Instant(999)) shouldBe 1
      Instant(1000).compareTo(Instant(-10000)) shouldBe 1
    }
  }

  "The parse method on the Instant object" should {

    "Correctly parse valid date/times (as per Joda's ISODateTimeFormat.dateTime)" in {

      Instant.parse("-0928-10-22T00:29:03.087Z") shouldBe Instant(-91426577456913L)
      Instant.parse("1924-10-17T16:29:03.087Z") shouldBe Instant(-1426577456913L)
      Instant.parse("1927-11-03T16:29:03.087Z") shouldBe Instant(-1330500656913L)
      Instant.parse("1969-12-31T11:56:04.655Z") shouldBe Instant(-43435345L)
      Instant.parse("1969-12-31T23:59:59.999Z") shouldBe Instant(-1L)
      Instant.parse("1970-01-01T00:00:00.000Z") shouldBe Instant(0L)
      Instant.parse("1970-01-01T00:00:00.001Z") shouldBe Instant(1L)
      Instant.parse("1970-01-01T12:03:55.345Z") shouldBe Instant(43435345L)
      Instant.parse("2012-02-29T07:30:56.913Z") shouldBe Instant(1330500656913L)
      Instant.parse("2015-03-17T07:30:56.913Z") shouldBe Instant(1426577456913L)
      Instant.parse("4867-03-11T23:30:56.913Z") shouldBe Instant(91426577456913L)
    }

    "Be lenient on the lengths of the numbers as long as they are valid" in {
      Instant.parse("196-1-3T2:9:5.9Z") shouldBe Instant(-55981835454100L)
      Instant.parse("19693-1-3T2:9:5.9Z") shouldBe Instant(559284142145900L)
    }

    "Correctly interpret UTC offsets" in {
      Instant.parse("2015-03-29T00:00:00.000+00:00") shouldBe Instant(1427587200000L)
      Instant.parse("2015-03-29T00:00:00.000-00:00") shouldBe Instant(1427587200000L)
      Instant.parse("2015-03-29T00:00:00.000+01:00") shouldBe Instant(1427583600000L)
      Instant.parse("2015-03-29T00:00:00.000-07:00") shouldBe Instant(1427612400000L)
      Instant.parse("2015-03-29T00:00:00.000+05:30") shouldBe Instant(1427567400000L)
    }

    "Correctly parse any timestamp string created with the Instant.toString method" in {

      def test(ms: Long) = Instant.parse(Instant(ms).toString) shouldBe Instant(ms)

      test(-91426577456913L)
      test(-1426577456913L)
      test(-43435345L)
      test(-1L)
      test(0L)
      test(1L)
      test(43435345L)
      test(1426577456913L)
      test(91426577456913L)
    }

    "Throw an InstantParseException if the string is null, empty or blank" in {
      an [InstantParseException] should be thrownBy Instant.parse(null)
      an [InstantParseException] should be thrownBy Instant.parse("")
      an [InstantParseException] should be thrownBy Instant.parse("    ")
    }

    "Throw an InstantParseException if the string is malformed" in {
      an [InstantParseException] should be thrownBy Instant.parse("abc")
      an [InstantParseException] should be thrownBy Instant.parse("2015-03-17T07:30:56.913Y")
      an [InstantParseException] should be thrownBy Instant.parse("2015-0b-17T07:30:56.913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2015-03-17T07:30:56:913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2015/03/17T07:30:56:913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2015-03-17T07-30-56-913Z")
      an [InstantParseException] should be thrownBy Instant.parse("   2015-03-17T07:30:56.913Z  ")
      an [InstantParseException] should be thrownBy Instant.parse("2015-03-17T07:30:56.913")
    }

    "Throw an InstantParseException if the instant is correctly formatted but doesn't represent a valid instant" in {
      an [InstantParseException] should be thrownBy Instant.parse("2015-13-17T07:30:56.913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2015--1-17T07:30:56.913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2015-03-32T07:30:56.913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2015-03-00T07:30:56.913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2015-02-29T07:30:56.913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2012-02-30T07:30:56.913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2012-02-15T24:00:00:000Z")
      an [InstantParseException] should be thrownBy Instant.parse("2012-02-30T23:60:00:000Z")
      an [InstantParseException] should be thrownBy Instant.parse("2012-02-30T07:30:60.913Z")
      an [InstantParseException] should be thrownBy Instant.parse("2012-02-30T07:30:56.1000Z")
    }
  }
}

