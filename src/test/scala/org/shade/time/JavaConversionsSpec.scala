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

import java.time.{LocalDate, ZoneId}

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

class JavaConversionsSpec extends WordSpec with Matchers with MockitoSugar {

  import JavaConversions._

  "Assigning an Instant from a JDK Instant" should {

    "produce the right instant" in {
      val instant: Instant = java.time.Instant.ofEpochMilli(100000L)
      instant shouldBe Instant(100000L)
    }

    "produce the right instant, rounding nanoseconds down to the nearest millisecond" in {
      val instant: Instant = java.time.Instant.ofEpochSecond(100L, 999999999)
      instant shouldBe Instant(100999L)
    }
  }

  "Assigning a JDK Instant from an Instant" should {

    "produce the right DateTime with ISO chronology and UTC timezone #1" in {
      val jdk: java.time.Instant = Instant(100000L)
      jdk shouldBe java.time.Instant.ofEpochMilli(100000L)
    }
  }


  "Assigning a Date from a JDK LocalDate" should {

    "create a Date containing the correct day, month and year" in {
      val date: Date = LocalDate.of(1730, 2, 7)
      date shouldBe Date(1730, 2, 7)
    }
  }

  "Assigning a JDK LocalDate from a Date" should {

    "produce the right LocalDate" in {
      val jdk: LocalDate = Date(2013, 11, 22)
      jdk shouldBe LocalDate.of(2013, 11, 22)
    }

    "produce the right LocalDate #2" in {
      val jdk: LocalDate = Date(1945, 12, 1)
      jdk shouldBe LocalDate.of(1945, 12, 1)
    }

    "produce the right LocalDate #3" in {
      val joda: LocalDate = Date(1066, 1, 11)
      joda shouldBe LocalDate.of(1066, 1, 11)
    }

    "produce the right LocalDate #4" in {
      val joda: LocalDate = Date(1492, 12, 22)
      joda shouldBe LocalDate.of(1492, 12, 22)
    }

    "produce the right LocalDate #5" in {
      val joda: LocalDate = Date(2013, 6, 22)
      joda shouldBe LocalDate.of(2013, 6, 22)
    }

    "produce the right LocalDate #6" in {
      val joda: LocalDate = Date(-3424, 12, 31)
      joda shouldBe LocalDate.of(-3424, 12, 31)
    }


    "produce the right LocalDate #7" in {
      val joda: LocalDate = Date(1959, 1, 1)
      joda shouldBe LocalDate.of(1959, 1, 1)
    }
  }

  "Assigning Zone from a JDK ZoneId" should {

    "produce the right Zone #1" in {
      val id = "Europe/London"
      val zoneId = ZoneId.of(id)
      val zone: Zone = zoneId
      zone.id shouldBe id
      zone.zoneId shouldBe zoneId
    }

    "produce the right Zone #2" in {
      val id = "+05:00"
      val zoneId = ZoneId.of(id)
      val zone: Zone = zoneId
      zone.id shouldBe id
      zone.zoneId shouldBe zoneId
    }

    "produce the right Zone #3" in {
      val id = "-05:00"
      val zoneId = ZoneId.of(id)
      val zone: Zone = zoneId
      zone.id shouldBe id
      zone.zoneId shouldBe zoneId
    }

    "produce the right Zone #4" in {
      val id = "Pacific/Honolulu"
      val zoneId = ZoneId.of(id)
      val zone: Zone = zoneId
      zone.id shouldBe id
      zone.zoneId shouldBe zoneId
    }
  }

  "Assigning ZoneId from a Zone" should {

    "produce the right ZoneId #1" in {
      val id = "Europe/London"
      val zone: Zone = Zone(id)
      val zoneId: ZoneId = zone
      zoneId.getId shouldBe id
      zoneId shouldBe zone.zoneId
    }

    "produce the right ZoneId #2" in {
      val id = "+05:00"
      val zone: Zone = Zone(id)
      val zoneId: ZoneId = zone
      zoneId.getId shouldBe id
      zoneId shouldBe zone.zoneId
    }

    "produce the right ZoneId #3" in {
      val id = "-05:00"
      val zone: Zone = Zone(id)
      val zoneId: ZoneId = zone
      zoneId.getId shouldBe id
      zoneId shouldBe zone.zoneId
    }

    "produce the right ZoneId #4" in {
      val id = "Pacific/Honolulu"
      val zone: Zone = Zone(id)
      val zoneId: ZoneId = zone
      zoneId.getId shouldBe id
      zoneId shouldBe zone.zoneId
    }
  }
}


