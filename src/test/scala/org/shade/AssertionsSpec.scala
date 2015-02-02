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
package org.shade

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar

class AssertionsSpec extends WordSpec with Matchers with MockitoSugar {

  import Assertions._

  private val NullString: String = null

  "Calling notNull" should {

    "Throw an AssertionError if the input is null" in {
      val nullSeq: Seq[(String, Any)] = null
      val thrown = the [AssertionError] thrownBy notNull(nullSeq:_*)
      thrown.getMessage shouldBe "Parameters are null"
    }

    "Throw an AssertionError if any of the tuples in the sequence are null" in {
      val NullTuple: (String, String) = null
      val thrown = the [AssertionError] thrownBy notNull("a" -> "blah", NullTuple, "b" -> 5)
      thrown.getMessage shouldBe "Parameter tuple is null"
    }

    "Throw an AssertionError if any of the names in the input are null" in {
      val thrown1 = the [AssertionError] thrownBy notNull(NullString -> "blah")
      thrown1.getMessage shouldBe "Parameter name is null"

      val thrown2 = the [AssertionError] thrownBy notNull("a" -> "blah", "b" -> 5, NullString -> "hello", "d" -> true)
      thrown2.getMessage shouldBe "Parameter name is null"
    }

    "Do nothing if there are no parameters" in {
      notNull()
    }

    "Do nothing if all parameters are notNull" in {
      notNull("a" -> "blah", "b" -> 5, "c" -> "hello", "d" -> true)
    }

    "throw a NullPointerException with an appropriate message if any of the parameters is null" in {
      val thrown = the [NullPointerException] thrownBy notNull("a" -> "blah", "b" -> 5, "c" -> null, "d" -> true)
      thrown.getMessage shouldBe "Parameter 'c' is null"
    }
  }
}
