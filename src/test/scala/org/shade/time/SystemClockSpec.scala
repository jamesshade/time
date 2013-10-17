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

import org.scalatest.WordSpec
import org.scalatest.Matchers
import org.scalatest.mock.MockitoSugar

class SystemClockSpec extends WordSpec with Matchers with MockitoSugar {

  "Calling now" should {

    "return the current time" in {
      val before = System.currentTimeMillis
      val clockTime = SystemClock.now.millis
      val after = System.currentTimeMillis

      clockTime should (be >= before and be <= after)
    }
  }
}