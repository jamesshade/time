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

class ConversionsSpec extends WordSpec with Matchers with MockitoSugar {

  import Conversions._

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
}
