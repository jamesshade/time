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
      Date(11111, 12, 2).toString shouldBe "+11111-12-02"
      Date(45435535, 8, 6).toString shouldBe "+45435535-08-06"
    }
  }

  "Constructing a date" should {

    "throw an InvalidDateException if the day number is less than 1" in {
      Date(2013, 6, 1).day shouldBe 1
      an [InvalidDateException] should be thrownBy Date(2013, 6, 0)

      Date(1985, 2, 1).day shouldBe 1
      an [InvalidDateException] should be thrownBy Date(1985, 2, -1)

      Date(23, 12, 1).day shouldBe 1
      an [InvalidDateException] should be thrownBy Date(23, 12, -1000)

      Date(-2333, 6, 1).day shouldBe 1
      an [InvalidDateException] should be thrownBy Date(-2333, 6, -23)
    }

    "throw an InvalidDateException if the day number is invalid for the month" in {

      Date(2013, 1, 31).day shouldBe 31
      an [InvalidDateException] should be thrownBy Date(2013, 1, 32)

      Date(2013, 2, 28).day shouldBe 28
      an [InvalidDateException] should be thrownBy Date(2013, 2, 29)

      Date(2013, 3, 31).day shouldBe 31
      an [InvalidDateException] should be thrownBy Date(2013, 3, 32)

      Date(2013, 4, 30).day shouldBe 30
      an [InvalidDateException] should be thrownBy Date(2013, 4, 31)

      Date(2013, 5, 31).day shouldBe 31
      an [InvalidDateException] should be thrownBy Date(2013, 5, 32)

      Date(2013, 6, 30).day shouldBe 30
      an [InvalidDateException] should be thrownBy Date(2013, 6, 31)

      Date(2013, 7, 31).day shouldBe 31
      an [InvalidDateException] should be thrownBy Date(2013, 7, 32)

      Date(2013, 8, 31).day shouldBe 31
      an [InvalidDateException] should be thrownBy Date(2013, 8, 32)

      Date(2013, 9, 30).day shouldBe 30
      an [InvalidDateException] should be thrownBy Date(2013, 9, 31)

      Date(2013, 10, 31).day shouldBe 31
      an [InvalidDateException] should be thrownBy Date(2013, 10, 32)

      Date(2013, 11, 30).day shouldBe 30
      an [InvalidDateException] should be thrownBy Date(2013, 11, 31)

      Date(2013, 12, 31).day shouldBe 31
      an [InvalidDateException] should be thrownBy Date(2013, 12, 32)
    }

    "throw an InvalidDateException if the day number is invalid for February" in {

      Date(2013, 2, 28).day shouldBe 28
      an [InvalidDateException] should be thrownBy Date(2013, 2, 29)

      Date(2014, 2, 28).day shouldBe 28
      an [InvalidDateException] should be thrownBy Date(2014, 2, 29)

      Date(2015, 2, 28).day shouldBe 28
      an [InvalidDateException] should be thrownBy Date(2015, 2, 29)

      Date(2016, 2, 29).day shouldBe 29
      an [InvalidDateException] should be thrownBy Date(2016, 2, 30)

      Date(1948, 2, 29).day shouldBe 29
      an [InvalidDateException] should be thrownBy Date(1948, 2, 30)

      Date(1996, 2, 29).day shouldBe 29
      an [InvalidDateException] should be thrownBy Date(1996, 2, 30)

      Date(2000, 2, 29).day shouldBe 29
      an [InvalidDateException] should be thrownBy Date(2000, 2, 30)

      Date(1896, 2, 29).day shouldBe 29
      an [InvalidDateException] should be thrownBy Date(1896, 2, 30)

      Date(1900, 2, 28).day shouldBe 28
      an [InvalidDateException] should be thrownBy Date(1900, 2, 29)
    }

    "throw an InvalidDateException if the month is not in the range 1 to 12" in {

      an [InvalidDateException] should be thrownBy Date(2013, 0, 1)

      an [InvalidDateException] should be thrownBy Date(2013, -1, 1)

      an [InvalidDateException] should be thrownBy Date(2013, -432, 1)

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

      an [InvalidDateException] should be thrownBy Date(2013, 13, 1)

      an [InvalidDateException] should be thrownBy Date(2013, 14, 1)

      an [InvalidDateException] should be thrownBy Date(2013, 432, 1)
    }
  }

  "Parsing a Date" should {

    "return the Date if the string is valid" in {
      Date.parse("2014-11-12") shouldBe Date(2014, 11, 12)
      Date.parse("2011-02-22") shouldBe Date(2011, 2, 22)
      Date.parse("0001-01-01") shouldBe Date(1, 1, 1)
      Date.parse("2220-02-29") shouldBe Date(2220, 2, 29)
//      TODO [JJS] Not valid with JDK - Date.parse("11111-12-31") shouldBe Date(11111, 12, 31)
//      TODO [JJS] Not valid with JDK - Date.parse("1-12-31") shouldBe Date(1, 12, 31)
      Date.parse("-1000-12-31") shouldBe Date(-1000, 12, 31)
    }

    "always work if the string was created by the toString method" in {

      def test(date: Date) = Date.parse(date.toString) shouldBe date

      test(Date(2014, 11, 22))
      test(Date(-5000, 1, 22))
      test(Date(2000, 2, 29))
      test(Date(100000, 12, 31))
    }

    "throw a parse exception if the string is null, empty or blank" in {
      a [DateParseException] should be thrownBy Date.parse(null)
      a [DateParseException] should be thrownBy Date.parse("")
      a [DateParseException] should be thrownBy Date.parse("   ")
    }

    "throw a parse exception if the string is malformed" in {
      a [DateParseException] should be thrownBy Date.parse("abc")
      a [DateParseException] should be thrownBy Date.parse("aaaa-bb-cc")
      a [DateParseException] should be thrownBy Date.parse("   2011-02-22  ")
      a [DateParseException] should be thrownBy Date.parse("   2011 - 02 - 22  ")
      a [DateParseException] should be thrownBy Date.parse(" 2011-02-22")
      a [DateParseException] should be thrownBy Date.parse("2011-02-22  ")
      a [DateParseException] should be thrownBy Date.parse("2011/02/22")
      a [DateParseException] should be thrownBy Date.parse("11 Mar 2015")
    }

    "throw a parse exception if the string is formatted correctly but doesn't represent a valid date" in {
      a [DateParseException] should be thrownBy Date.parse("2016-00-01")
      a [DateParseException] should be thrownBy Date.parse("2016-13-01")
      a [DateParseException] should be thrownBy Date.parse("2016-01-00")
      a [DateParseException] should be thrownBy Date.parse("2016-01-32")
      a [DateParseException] should be thrownBy Date.parse("2001-02-29")
    }
  }
}