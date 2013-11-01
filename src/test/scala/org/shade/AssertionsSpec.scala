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
package org.shade

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar

class AssertionsSpec extends WordSpec with Matchers with MockitoSugar {

  import Assertions._

  private val nullString: String = null

  "Calling notNull" should {

    "Throw an AssertionError if the input is null" in {
      val nullSeq: Seq[(String, Any)] = null
      val thrown = evaluating(notNull(nullSeq:_*)) should produce [AssertionError]
      thrown.getMessage shouldBe "Parameters are null"
    }

    "Throw an AssertionError if any of the names in the input are null" in {
      val thrown1 = evaluating(notNull(nullString -> "blah")) should produce [AssertionError]
      thrown1.getMessage shouldBe "Parameter name is null"

      val thrown2 = evaluating(notNull("a" -> "blah", "b" -> 5, nullString -> "hello", "d" -> true)) should produce [AssertionError]
      thrown2.getMessage shouldBe "Parameter name is null"
    }

    "Do nothing if there are no parameters" in {
      notNull()
    }

    "Do nothing if all parameters are notNull" in {
      notNull("a" -> "blah", "b" -> 5, "c" -> "hello", "d" -> true)
    }

    "throw a NullPointerException with an appropriate message if any of the parameters is null" in {
      val thrown = evaluating(notNull("a" -> "blah", "b" -> 5, "c" -> null, "d" -> true)) should produce [NullPointerException]
      thrown.getMessage shouldBe "Parameter 'c' is null"
    }

  }
}
