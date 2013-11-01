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

import org.scalatest.{WordSpec, Matchers}

class TimeSpec extends WordSpec with Matchers {

  "Constructing a Time object" should {

    "return a Time object if all values are zero" in {
      assertTime(Time(0, 0, 0, 0), 0, 0, 0, 0)
    }

    "throw an InvalidTimeException if the hour is less than zero" in {
      assertException(evaluating(Time(-1, 0, 0, 0)) should produce [InvalidTimeException], -1, 0, 0, 0)
      assertException(evaluating(Time(-100, 0, 0, 0)) should produce [InvalidTimeException], -100, 0, 0, 0)
    }

    "throw an InvalidTimeException if the minute is less than zero" in {
      assertException(evaluating(Time(0, -1, 0, 0)) should produce [InvalidTimeException], 0, -1, 0, 0)
      assertException(evaluating(Time(0, -100, 0, 0)) should produce [InvalidTimeException], 0, -100, 0, 0)
    }

    "throw an InvalidTimeException if the second is less than zero" in {
      assertException(evaluating(Time(0, 0, -1, 0)) should produce [InvalidTimeException], 0, 0, -1, 0)
      assertException(evaluating(Time(0, 0, -100, 0)) should produce [InvalidTimeException], 0, 0, -100, 0)
    }

    "throw an InvalidTimeException if the millisecond is less than zero" in {
      assertException(evaluating(Time(0, 0, 0, -1)) should produce [InvalidTimeException], 0, 0, 0, -1)
      assertException(evaluating(Time(0, 0, 0, -100)) should produce [InvalidTimeException], 0, 0, 0, -100)
    }

    "return a Time object if all values are set to their maximum legal values" in {
      assertTime(Time(23, 59, 59, 999), 23, 59, 59, 999)
    }

    "throw an InvalidTimeException if the hour is greater than 23" in {
      assertException(evaluating(Time(24, 59, 59, 999)) should produce [InvalidTimeException], 24, 59, 59, 999)
      assertException(evaluating(Time(100, 59, 59, 999)) should produce [InvalidTimeException], 100, 59, 59, 999)
    }

    "throw an InvalidTimeException if the minute is greater than 59" in {
      assertException(evaluating(Time(23, 60, 59, 999)) should produce [InvalidTimeException], 23, 60, 59, 999)
      assertException(evaluating(Time(23, 100, 59, 999)) should produce [InvalidTimeException], 23, 100, 59, 999)
    }

    "throw an InvalidTimeException if the second is greater than 59" in {
      assertException(evaluating(Time(23, 59, 60, 999)) should produce [InvalidTimeException], 23, 59, 60, 999)
      assertException(evaluating(Time(23, 59, 100, 999)) should produce [InvalidTimeException], 23, 59, 100, 999)
    }

    "throw an InvalidTimeException if the millisecond is greater than 999" in {
      assertException(evaluating(Time(23, 59, 59, 1000)) should produce [InvalidTimeException], 23, 59, 59, 1000)
      assertException(evaluating(Time(23, 59, 59, 100000)) should produce [InvalidTimeException], 23, 59, 59, 100000)
    }

    "return a Time object with the correct values for valid times of day" in {
      assertTime(Time(11, 22, 55, 222), 11, 22, 55, 222)
      assertTime(Time(0, 0, 0, 323), 0, 0, 0, 323)
      assertTime(Time(16, 25, 12, 912), 16, 25, 12, 912)
    }
  }

  "The toString method" should {
    "return the expected String format of the time" in {
      Time(0, 0, 0, 0).toString shouldBe "00:00:00.000"
      Time(11, 22, 55, 222).toString shouldBe "11:22:55.222"
      Time(1, 3, 4, 5).toString shouldBe "01:03:04.005"
      Time(16, 25, 12, 13).toString shouldBe "16:25:12.013"
      Time(23, 59, 59, 999).toString shouldBe "23:59:59.999"
    }
  }

  private def assertTime(time: Time, hour: Int, minute: Int, second: Int, millisecond: Int) {
    time.hour shouldBe hour
    time.minute shouldBe minute
    time.second shouldBe second
    time.millisecond shouldBe millisecond
  }

  private def assertException(e: InvalidTimeException, hour: Int, minute: Int, second: Int, millisecond: Int) {
    e.hour shouldBe hour
    e.minute shouldBe minute
    e.second shouldBe second
    e.millisecond shouldBe millisecond
  }
}
