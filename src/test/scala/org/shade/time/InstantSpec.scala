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

class InstantSpec extends WordSpec with Matchers with MockitoSugar {

  "Constructing an Instant with a particular millisecond value" should {

    "return a Instant object with that value" in {
      Instant(13243432434L).millis shouldBe 13243432434L
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
}

