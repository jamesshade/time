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

class DateSpec extends WordSpec with Matchers with MockitoSugar {

  "Constructing a Date object with a valid day, month and year" should {

    "return a Date object with the correct year, month and day" in {
      val date = Date(2013, 6, 22)
      date.year shouldBe 2013
      date.month shouldBe 6
      date.day shouldBe 22

      val date2 = Date(-3424, 12, 31)
      date2.year shouldBe -3424
      date2.month shouldBe 12
      date2.day shouldBe 31
    }
  }

  "The toString method on a date" should {

    "return the date in the expected yyyy-MM-dd format" in {
      Date(2013, 6, 22).toString shouldBe "2013-06-22"
      Date(1944, 11, 3).toString shouldBe "1944-11-03"
      Date(2024, 10, 30).toString shouldBe "2024-10-30"
    }

    "return the date in the correct format even for very low year numbers" in {
      Date(1, 1, 1).toString shouldBe "0001-01-01"
      Date(-1, 11, 4).toString shouldBe "-001-11-04"
      Date(-14344, 12, 31).toString shouldBe "-14344-12-31"
    }

    "return the date in the correct format for very large years" in {
      Date(11111, 12, 2).toString shouldBe "11111-12-02"
      Date(45435535, 8, 6).toString shouldBe "45435535-08-06"
    }
  }

  "Constructing a date" should {

    "throw an InvalidDateException if the day number is less than 1" in {
      Date(2013, 6, 1).day shouldBe 1
      evaluating(Date(2013, 6, 0)) should produce[InvalidDateException]

      Date(1985, 2, 1).day shouldBe 1
      evaluating(Date(1985, 2, -1)) should produce[InvalidDateException]

      Date(23, 12, 1).day shouldBe 1
      evaluating(Date(23, 12, -1000)) should produce[InvalidDateException]

      Date(-2333, 6, 1).day shouldBe 1
      evaluating(Date(-2333, 6, -23)) should produce[InvalidDateException]
    }

    "throw an InvalidDateException if the day number is invalid for the month" in {

      Date(2013, 1, 31).day shouldBe 31
      evaluating(Date(2013, 1, 32)) should produce[InvalidDateException]

      Date(2013, 2, 28).day shouldBe 28
      evaluating(Date(2013, 2, 29)) should produce[InvalidDateException]

      Date(2013, 3, 31).day shouldBe 31
      evaluating(Date(2013, 3, 32)) should produce[InvalidDateException]

      Date(2013, 4, 30).day shouldBe 30
      evaluating(Date(2013, 4, 31)) should produce[InvalidDateException]

      Date(2013, 5, 31).day shouldBe 31
      evaluating(Date(2013, 5, 32)) should produce[InvalidDateException]

      Date(2013, 6, 30).day shouldBe 30
      evaluating(Date(2013, 6, 31)) should produce[InvalidDateException]

      Date(2013, 7, 31).day shouldBe 31
      evaluating(Date(2013, 7, 32)) should produce[InvalidDateException]

      Date(2013, 8, 31).day shouldBe 31
      evaluating(Date(2013, 8, 32)) should produce[InvalidDateException]

      Date(2013, 9, 30).day shouldBe 30
      evaluating(Date(2013, 9, 31)) should produce[InvalidDateException]

      Date(2013, 10, 31).day shouldBe 31
      evaluating(Date(2013, 10, 32)) should produce[InvalidDateException]

      Date(2013, 11, 30).day shouldBe 30
      evaluating(Date(2013, 11, 31)) should produce[InvalidDateException]

      Date(2013, 12, 31).day shouldBe 31
      evaluating(Date(2013, 12, 32)) should produce[InvalidDateException]
    }

    "throw an InvalidDateException if the day number is invalid for February" in {

      Date(2013, 2, 28).day shouldBe 28
      evaluating(Date(2013, 2, 29)) should produce[InvalidDateException]

      Date(2014, 2, 28).day shouldBe 28
      evaluating(Date(2014, 2, 29)) should produce[InvalidDateException]

      Date(2015, 2, 28).day shouldBe 28
      evaluating(Date(2015, 2, 29)) should produce[InvalidDateException]

      Date(2016, 2, 29).day shouldBe 29
      evaluating(Date(2016, 2, 30)) should produce[InvalidDateException]

      Date(1948, 2, 29).day shouldBe 29
      evaluating(Date(1948, 2, 30)) should produce[InvalidDateException]

      Date(1996, 2, 29).day shouldBe 29
      evaluating(Date(1996, 2, 30)) should produce[InvalidDateException]

      Date(2000, 2, 29).day shouldBe 29
      evaluating(Date(2000, 2, 30)) should produce[InvalidDateException]

      Date(1896, 2, 29).day shouldBe 29
      evaluating(Date(1896, 2, 30)) should produce[InvalidDateException]

      Date(1900, 2, 28).day shouldBe 28
      evaluating(Date(1900, 2, 29)) should produce[InvalidDateException]
    }

    "return a valid date if the year is null (Scala converts it to zero)" in {
      Date(null.asInstanceOf[Int], 1, 1) shouldBe Date(0, 1, 1)
    }

    "throw an InvalidDateException if the month is null (Scala converts it to zero)" in {
      evaluating(Date(1999, null.asInstanceOf[Int], 1)) should produce [InvalidDateException]
    }

    "throw an InvalidDateException if the day is null (Scala converts it to zero)" in {
      evaluating(Date(1999, 1, null.asInstanceOf[Int])) should produce [InvalidDateException]
    }

    "throw an InvalidDateException if the month is not in the range 1 to 12" in {

      evaluating(Date(2013, 0, 1)) should produce[InvalidDateException]

      evaluating(Date(2013, -1, 1)) should produce[InvalidDateException]

      evaluating(Date(2013, -432, 1)) should produce[InvalidDateException]

      Date(2013, 1, 1).month shouldBe 1
      Date(2013, 2, 1).month shouldBe 2
      Date(2013, 3, 1).month shouldBe 3
      Date(2013, 4, 1).month shouldBe 4
      Date(2013, 5, 1).month shouldBe 5
      Date(2013, 6, 1).month shouldBe 6
      Date(2013, 7, 1).month shouldBe 7
      Date(2013, 8, 1).month shouldBe 8
      Date(2013, 9, 1).month shouldBe 9
      Date(2013, 10, 1).month shouldBe 10
      Date(2013, 11, 1).month shouldBe 11
      Date(2013, 12, 1).month shouldBe 12

      evaluating(Date(2013, 13, 1)) should produce[InvalidDateException]

      evaluating(Date(2013, 14, 1)) should produce[InvalidDateException]

      evaluating(Date(2013, 432, 1)) should produce[InvalidDateException]
    }
  }
}