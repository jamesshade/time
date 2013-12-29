/*
 *  Copyright 2013 James Shade
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

class StandardDurationSpec extends WordSpec with Matchers with MockitoSugar {

  private val week = 7L * 24L * 60L * 60L * 1000L
  private val day = 24L * 60L * 60L * 1000L
  private val hour = 60L * 60L * 1000L
  private val minute = 60L * 1000L
  private val second = 1000L
  private val millisecond = 1L

  import StandardDuration._

  "The duration constants" should {

    "have the correct value for the standard understanding of the time span" in {
      aWeek shouldBe Duration(week)
      aDay shouldBe Duration(day)
      anHour shouldBe Duration(hour)
      aMinute shouldBe Duration(minute)
      aSecond shouldBe Duration(second)
      aMillisecond shouldBe Duration(1L)
    }
  }

  "The weeks method" should {

    "return the duration of the number of weeks requested" in {
      weeks(0L) shouldBe Duration(0L)
      weeks(1L) shouldBe Duration(week)
      weeks(-1L) shouldBe Duration(-week)
      weeks(23424342L) shouldBe Duration(week * 23424342L)
      weeks(-4535L) shouldBe Duration(-week * 4535L)
    }
  }

  "The days method" should {

    "return the duration of the number of days requested" in {
      days(0L) shouldBe Duration(0L)
      days(1L) shouldBe Duration(day)
      days(-1L) shouldBe Duration(-day)
      days(23424342L) shouldBe Duration(day * 23424342L)
      days(-4535L) shouldBe Duration(-day * 4535L)
    }
  }

  "The hours method" should {

    "return the duration of the number of hours requested" in {
      hours(0L) shouldBe Duration(0L)
      hours(1L) shouldBe Duration(hour)
      hours(-1L) shouldBe Duration(-hour)
      hours(23424342L) shouldBe Duration(hour * 23424342L)
      hours(-4535L) shouldBe Duration(-hour * 4535L)
    }
  }

  "The minutes method" should {

    "return the duration of the number of minutes requested" in {
      minutes(0L) shouldBe Duration(0L)
      minutes(1L) shouldBe Duration(minute)
      minutes(-1L) shouldBe Duration(-minute)
      minutes(23424342L) shouldBe Duration(minute * 23424342L)
      minutes(-4535L) shouldBe Duration(-minute * 4535L)
    }
  }

  "The seconds method" should {

    "return the duration of the number of seconds requested" in {
      seconds(0L) shouldBe Duration(0L)
      seconds(1L) shouldBe Duration(second)
      seconds(-1L) shouldBe Duration(-second)
      seconds(23424342L) shouldBe Duration(second * 23424342L)
      seconds(-4535L) shouldBe Duration(-second * 4535L)
    }
  }

  "The milliseconds method" should {

    "return the duration of the number of milliseconds requested" in {
      milliseconds(0L) shouldBe Duration(0L)
      milliseconds(1L) shouldBe Duration(millisecond)
      milliseconds(-1L) shouldBe Duration(-millisecond)
      milliseconds(23424342L) shouldBe Duration(23424342L * millisecond)
      milliseconds(-4535L) shouldBe Duration(-4535L * millisecond)
    }
  }

  "The apply method" should {

    "return zero without parameters" in {
      StandardDuration() shouldBe Duration(0L)
    }

    "if just weeks are set, return the duration of the number of weeks requested" in {
      StandardDuration(weeks = 0L) shouldBe Duration(0L)
      StandardDuration(weeks = 1L) shouldBe Duration(week)
      StandardDuration(weeks = -1L) shouldBe Duration(-week)
      StandardDuration(weeks = 23424342L) shouldBe Duration(week * 23424342L)
      StandardDuration(weeks = -4535L) shouldBe Duration(-week * 4535L)
    }

    "if just days are set, return the duration of the number of days requested" in {
      StandardDuration(days = 0L) shouldBe Duration(0L)
      StandardDuration(days = 1L) shouldBe Duration(day)
      StandardDuration(days = -1L) shouldBe Duration(-day)
      StandardDuration(days = 23424342L) shouldBe Duration(day * 23424342L)
      StandardDuration(days = -4535L) shouldBe Duration(-day * 4535L)
    }

    "if just hours are set, return the duration of the number of hours requested" in {
      StandardDuration(hours = 0L) shouldBe Duration(0L)
      StandardDuration(hours = 1L) shouldBe Duration(hour)
      StandardDuration(hours = -1L) shouldBe Duration(-hour)
      StandardDuration(hours = 23424342L) shouldBe Duration(hour * 23424342L)
      StandardDuration(hours = -4535L) shouldBe Duration(-hour * 4535L)
    }

    "if just minutes are set, return the duration of the number of minutes requested" in {
      StandardDuration(minutes = 0L) shouldBe Duration(0L)
      StandardDuration(minutes = 1L) shouldBe Duration(minute)
      StandardDuration(minutes = -1L) shouldBe Duration(-minute)
      StandardDuration(minutes = 23424342L) shouldBe Duration(minute * 23424342L)
      StandardDuration(minutes = -4535L) shouldBe Duration(-minute * 4535L)
    }

    "if just seconds are set, return the duration of the number of seconds requested" in {
      StandardDuration(seconds = 0L) shouldBe Duration(0L)
      StandardDuration(seconds = 1L) shouldBe Duration(second)
      StandardDuration(seconds = -1L) shouldBe Duration(-second)
      StandardDuration(seconds = 23424342L) shouldBe Duration(second * 23424342L)
      StandardDuration(seconds = -4535L) shouldBe Duration(-second * 4535L)
    }

    "if a combination of fields are set, produce the appropriate duration" in {

      StandardDuration(0, 0, 0, 0, 0, 0) shouldBe Duration(0L)

      StandardDuration(1, 1, 1, 1, 1, 1) shouldBe Duration(week + day + hour + minute + second + millisecond)

      StandardDuration(10, 10, 10, 10, 10, 10) shouldBe Duration(10 * (week + day + hour + minute + second + millisecond))

      StandardDuration(-1, -1, -1, -1, -1, -1) shouldBe Duration(-(week + day + hour + minute + second + millisecond))

      StandardDuration(-10, -10, -10, -10, -10, -10) shouldBe Duration(- 10 * (week + day + hour + minute + second + millisecond))

      StandardDuration(-5, 5, -5, 5, -5, 5) shouldBe Duration(5 * (-week + day - hour + minute - second + millisecond))

      StandardDuration(5, -5, 5, -5, 5, -5) shouldBe Duration(5 * (week - day + hour - minute + second - millisecond))

      StandardDuration(days = 1, milliseconds = -86400000L) shouldBe Duration(0)

      StandardDuration(weeks = 2L, days = 3L, hours = 27L, minutes = 353L, seconds = -435435L, milliseconds = 4543545L) shouldBe Duration(
        2L * week + 3L * day + 27L * hour + 353L * minute - 435435L * second + 4543545L * millisecond)

      StandardDuration(weeks = -43535L, days = 643L, hours = -12313L, minutes = 887L, seconds = 65765L, milliseconds = 986743L) shouldBe Duration(
        -43535L * week + 643L * day - 12313L * hour + 887L * minute + 65765L * second + 986743L * millisecond)
    }
  }
}
