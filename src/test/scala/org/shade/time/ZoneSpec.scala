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
import org.joda.time.{DateTime, DateTimeZone}

class ZoneSpec extends WordSpec with Matchers with MockitoSugar {

  import Zone.utc

  private val london = Zone("Europe/London")
  private val addisAbaba = Zone("Africa/Addis_Ababa")

  "The Zone instance apply method" should {

    "return the correct instant for this zone #1" in {
      london(2013, 5, 22, 11, 43, 23, 222) shouldBe Instant(1369219403222L)
    }

    "return the correct instant #2" in {
      // 1600-06-12T10:44:44.111+02:34:48 =  (In UTC = -11661974115889 - 9288000 )
      addisAbaba(1600, 6, 12, 10, 44, 44, 111) shouldBe Instant(-11661974115889L - 9288000L)
    }

    "throw an exception if the time is invalid due to general invalid numbers" in {
      evaluating(utc(1900, 2, 29, 11, 33, 22, 222)) should produce [InvalidTimeException]
      evaluating(utc(2000, 2, 30, 11, 33, 22, 222)) should produce [InvalidTimeException]
      evaluating(utc(2013, 8, 25, 12, 60, 22, 222)) should produce [InvalidTimeException]
      evaluating(utc(2013, 0, 22, 11, 33, 22, 222)) should produce [InvalidTimeException]
    }

    "throw an exception if the time is invalid due to daylight savings time changes in a given zone" in {
      evaluating(london(2013, 3, 31, 1, 30, 25, 222)) should produce [InvalidTimeException]
    }
  }

  "The Zone instance unapply method" should {

    "match a time from the appropriate zone" in {
      val instant = Instant(new DateTime(2013, 6, 22, 11, 55, 22, 666, DateTimeZone.UTC).getMillis)

      val x = instant match {
        case london(2013, 6, 22, 11, 55, 22, 666) => throw new AssertionError("This is the UTC time not the UK time")
        case london(2013, 6, 22, 12, 55, 22, 666) => "correct!"
        case _ => throw new AssertionError("Didn't match")
      }

      x shouldBe "correct!"
    }
  }


  "The today method" should {

    val utc = Zone.utc
    val london = Zone("Europe/London")
    val stockholm = Zone("Europe/Stockholm")
    val sydney = Zone("Australia/Sydney")
    val honolulu = Zone("Pacific/Honolulu")
    
    "return the date of 'now' in the supplied timezone for Tue 30 Jul 2013 08:07:13.370 BST" in {
      
      implicit val clock = new Clock {override val now = 1375168033370L}
      
      utc.today shouldBe Date(2013, 7, 30)
      london.today shouldBe Date(2013, 7, 30)
      stockholm.today shouldBe Date(2013, 7, 30)
      sydney.today shouldBe Date(2013, 7, 30)
      honolulu.today shouldBe Date(2013, 7, 29)
    }

    "return the date of 'now' in the supplied timezone for Tue 29 Feb 2000 23:30:00.000 GMT" in {
      
      implicit val clock = new Clock {override val now = 951867000000L}
      
      utc.today shouldBe Date(2000, 2, 29)
      london.today shouldBe Date(2000, 2, 29)
      stockholm.today shouldBe Date(2000, 3, 1)
      sydney.today shouldBe Date(2000, 3, 1)
      honolulu.today shouldBe Date(2000, 2, 29)
    }

    "return the date of 'now' in the supplied timezone for Mon 21 Mar 1977 00:00:00.000 BST" in {
      
      implicit val clock = new Clock {override val now = 227746800000L}
      
      utc.today shouldBe Date(1977, 3, 20)
      london.today shouldBe Date(1977, 3, 21)
      stockholm.today shouldBe Date(1977, 3, 21)
      sydney.today shouldBe Date(1977, 3, 21)
      honolulu.today shouldBe Date(1977, 3, 20)
    }
  }

  "dateOf" should {

    "return the correct Date in this zone for the supplied instant" in {

      val utc = Zone.utc
      val london = Zone("Europe/London")
      val stockholm = Zone("Europe/Stockholm")
      val sydney = Zone("Australia/Sydney")
      val honolulu = Zone("Pacific/Honolulu")

      val instant1 = Instant(1375168033370L) // Tue 30 Jul 2013 08:07:13.370 BST
      utc.dateOf(instant1) should equal(Date(2013, 7, 30))
      london.dateOf(instant1) should equal(Date(2013, 7, 30))
      stockholm.dateOf(instant1) should equal(Date(2013, 7, 30))
      sydney.dateOf(instant1) should equal(Date(2013, 7, 30))
      honolulu.dateOf(instant1) should equal(Date(2013, 7, 29))

      val instant2 = Instant(951867000000L) // Tue 29 Feb 2000 23:30:00.000 GMT
      utc.dateOf(instant2) should equal(Date(2000, 2, 29))
      london.dateOf(instant2) should equal(Date(2000, 2, 29))
      stockholm.dateOf(instant2) should equal(Date(2000, 3, 1))
      sydney.dateOf(instant2) should equal(Date(2000, 3, 1))
      honolulu.dateOf(instant2) should equal(Date(2000, 2, 29))

      val instant3 = Instant(227746800000L) // Mon 21 Mar 1977 00:00:00.000 BST
      utc.dateOf(instant3) should equal(Date(1977, 3, 20))
      london.dateOf(instant3) should equal(Date(1977, 3, 21))
      stockholm.dateOf(instant3) should equal(Date(1977, 3, 21))
      sydney.dateOf(instant3) should equal(Date(1977, 3, 21))
      honolulu.dateOf(instant3) should equal(Date(1977, 3, 20))
    }
  }
}