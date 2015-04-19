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

import org.scalatest.{WordSpec, Matchers}

class DateAndTimeSpec extends WordSpec with Matchers {
  
  private val minYear = -292275054
  private val maxYear = 292278993
  private val maxLeapYear = 292278992

  "Constructing a DateAndTime object" should {

    "return a valid DateAndTime object for a general valid date and time" in {
      assertValid(DateAndTime(2013, 11, 2, 16, 23, 45, 345), 2013, 11, 2, 16, 23, 45, 345)
      assertValid(DateAndTime(2013, 2, 28, 16, 23, 45, 345), 2013, 2, 28, 16, 23, 45, 345)
      assertValid(DateAndTime(2012, 2, 29, 16, 23, 45, 345), 2012, 2, 29, 16, 23, 45, 345)
    }

    "return a valid DateAndTime object if all values are set to their legal minimum" in {
      assertValid(DateAndTime(minYear, 1, 1, 0, 0, 0, 0), minYear, 1, 1, 0, 0, 0, 0)
    }

    "return a valid DateAndTime object if all values are set to their legal maximum" in {
      assertValid(DateAndTime(maxYear, 1, 31, 23, 59, 59, 999), maxYear, 1, 31, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 2, 28, 23, 59, 59, 999), maxYear, 2, 28, 23, 59, 59, 999)
      assertValid(DateAndTime(maxLeapYear, 2, 29, 23, 59, 59, 999), maxLeapYear, 2, 29, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 3, 31, 23, 59, 59, 999), maxYear, 3, 31, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 4, 30, 23, 59, 59, 999), maxYear, 4, 30, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 5, 31, 23, 59, 59, 999), maxYear, 5, 31, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 6, 30, 23, 59, 59, 999), maxYear, 6, 30, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 7, 31, 23, 59, 59, 999), maxYear, 7, 31, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 8, 31, 23, 59, 59, 999), maxYear, 8, 31, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 9, 30, 23, 59, 59, 999), maxYear, 9, 30, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 10, 31, 23, 59, 59, 999), maxYear, 10, 31, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 11, 30, 23, 59, 59, 999), maxYear, 11, 30, 23, 59, 59, 999)
      assertValid(DateAndTime(maxYear, 12, 31, 23, 59, 59, 999), maxYear, 12, 31, 23, 59, 59, 999)
    }

    "throw an InvalidDateException if the month is less than the minimum" in {
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 0, 2, 16, 23, 45, 345), 2013, 0, 2)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, -100, 2, 16, 23, 45, 345), 2013, -100, 2)
    }

    "throw an InvalidDateException if the day is less than the minimum" in {
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 11, 0, 16, 23, 45, 345), 2013, 11, 0)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 11, -100, 16, 23, 45, 345), 2013, 11, -100)
    }

    "throw an InvalidDateException if the month is greater than the maximum" in {
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 13, 2, 16, 23, 45, 345), 2013, 13, 2)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 100, 2, 16, 23, 45, 345), 2013, 100, 2)
    }

    "throw an InvalidDateException if the day is greater than the maximum" in {

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 1, 32, 16, 23, 45, 345), 2013, 1, 32)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 1, 100, 16, 23, 45, 345), 2013, 1, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 2, 29, 16, 23, 45, 345), 2013, 2, 29)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 2, 100, 16, 23, 45, 345), 2013, 2, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2012, 2, 30, 16, 23, 45, 345), 2012, 2, 30)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2012, 2, 100, 16, 23, 45, 345), 2012, 2, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 3, 32, 16, 23, 45, 345), 2013, 3, 32)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 3, 100, 16, 23, 45, 345), 2013, 3, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 4, 31, 16, 23, 45, 345), 2013, 4, 31)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 4, 100, 16, 23, 45, 345), 2013, 4, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 5, 32, 16, 23, 45, 345), 2013, 5, 32)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 5, 100, 16, 23, 45, 345), 2013, 5, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 6, 31, 16, 23, 45, 345), 2013, 6, 31)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 6, 100, 16, 23, 45, 345), 2013, 6, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 7, 32, 16, 23, 45, 345), 2013, 7, 32)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 7, 100, 16, 23, 45, 345), 2013, 7, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 8, 32, 16, 23, 45, 345), 2013, 8, 32)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 8, 100, 16, 23, 45, 345), 2013, 8, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 9, 31, 16, 23, 45, 345), 2013, 9, 31)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 9, 100, 16, 23, 45, 345), 2013, 9, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 10, 32, 16, 23, 45, 345), 2013, 10, 32)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 10, 100, 16, 23, 45, 345), 2013, 10, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 11, 31, 16, 23, 45, 345), 2013, 11, 31)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 11, 100, 16, 23, 45, 345), 2013, 11, 100)

      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 12, 32, 16, 23, 45, 345), 2013, 12, 32)
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 12, 100, 16, 23, 45, 345), 2013, 12, 100)
    }

    "throw an InvalidTimeException if the hour is less than the minimum" in {
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, -1, 23, 45, 345), -1, 23, 45, 345)
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, -100, 23, 45, 345), -100, 23, 45, 345)
    }

    "throw an InvalidTimeException if the minute is less than the minimum" in {
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, -1, 45, 345), 16, -1, 45, 345)
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, -100, 45, 345), 16, -100, 45, 345)
    }

    "throw an InvalidTimeException if the second is less than the minimum" in {
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 23, -1, 345), 16, 23, -1, 345)
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 23, -100, 345), 16, 23, -100, 345)
    }

    "throw an InvalidTimeException if the millisecond is less than the minimum" in {
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 23, 45, -1), 16, 23, 45, -1)
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 23, 45, -1000), 16, 23, 45, -1000)
    }

    "throw an InvalidTimeException if the hour is greater than the maximum" in {
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 24, 23, 45, 345), 24, 23, 45, 345)
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 100, 23, 45, 345), 100, 23, 45, 345)
    }

    "throw an InvalidTimeException if the minute is greater than the maximum" in {
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 60, 45, 345), 16, 60, 45, 345)
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 100, 45, 345), 16, 100, 45, 345)
    }

    "throw an InvalidTimeException if the second is greater than the maximum" in {
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 23, 60, 345), 16, 23, 60, 345)
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 23, 100, 345), 16, 23, 100, 345)
    }

    "throw an InvalidTimeException if the millisecond is greater than the maximum" in {
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 23, 45, 1000), 16, 23, 45, 1000)
      assertTimeException(the [InvalidTimeException] thrownBy DateAndTime(2013, 11, 2, 16, 23, 45, 10000), 16, 23, 45, 10000)
    }

    "throw an InvalidDateException if both the date and time are invalid" in {
      assertDateException(the [InvalidDateException] thrownBy DateAndTime(2013, 0, 22, 24, 44, 33, 1000), 2013, 0, 22)
    }
  }

  "The date method" should {
    "return the Date part of this DateAndTime" in {
      DateAndTime(minYear, 1, 1, 0, 0, 0, 0).date shouldBe Date(minYear, 1, 1)
      DateAndTime(1955, 12, 6, 11, 22, 55, 222).date shouldBe Date(1955, 12, 6)
      DateAndTime(0, 1, 2, 1, 3, 4, 5).date shouldBe Date(0, 1, 2)
      DateAndTime(1970, 8, 31, 16, 25, 12, 13).date shouldBe Date(1970, 8, 31)
      DateAndTime(1985, 3, 22, 23, 59, 59, 999).date shouldBe Date(1985, 3, 22)
      DateAndTime(12345, 3, 22, 23, 59, 59, 999).date shouldBe Date(12345, 3, 22)
    }
  }

  "The time method" should {
    "return the Time part of this DateAndTime" in {
      DateAndTime(minYear, 1, 1, 0, 0, 0, 0).time shouldBe Time(0, 0, 0, 0)
      DateAndTime(1955, 12, 6, 11, 22, 55, 222).time shouldBe Time(11, 22, 55, 222)
      DateAndTime(0, 1, 2, 1, 3, 4, 5).time shouldBe Time(1, 3, 4, 5)
      DateAndTime(1970, 8, 31, 16, 25, 12, 13).time shouldBe Time(16, 25, 12, 13)
      DateAndTime(1985, 3, 22, 23, 59, 59, 999).time shouldBe Time(23, 59, 59, 999)
      DateAndTime(12345, 3, 22, 23, 59, 59, 999).time shouldBe Time(23, 59, 59, 999)
    }
  }

  "The toString method" should {
    "return the expected String format of the time" in {
      DateAndTime(minYear, 1, 1, 0, 0, 0, 0).toString shouldBe s"$minYear-01-01T00:00:00.000"
      DateAndTime(1955, 12, 6, 11, 22, 55, 222).toString shouldBe "1955-12-06T11:22:55.222"
      DateAndTime(0, 1, 2, 1, 3, 4, 5).toString shouldBe "0000-01-02T01:03:04.005"
      DateAndTime(1970, 8, 31, 16, 25, 12, 13).toString shouldBe "1970-08-31T16:25:12.013"
      DateAndTime(1985, 3, 22, 23, 59, 59, 999).toString shouldBe "1985-03-22T23:59:59.999"
      DateAndTime(12345, 3, 22, 23, 59, 59, 999).toString shouldBe "+12345-03-22T23:59:59.999"
    }
  }

  private def assertValid(dt: DateAndTime, year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int) {
    dt.year shouldBe year
    dt.month shouldBe month
    dt.day shouldBe day
    dt.hour shouldBe hour
    dt.minute shouldBe minute
    dt.second shouldBe second
    dt.millisecond shouldBe millisecond
  }

  private def assertTimeException(e: InvalidTimeException, hour: Int, minute: Int, second: Int, millisecond: Int) {
    e.hour shouldBe hour
    e.minute shouldBe minute
    e.second shouldBe second
    e.millisecond shouldBe millisecond
  }

  private def assertDateException(e: InvalidDateException, year: Int, month: Int, day: Int) {
    e.year shouldBe year
    e.month shouldBe month
    e.day shouldBe day
  }
}
