package org.shade.time

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import grizzled.slf4j.Logging
import org.joda.time.DateTimeZone

class ClockSpec extends WordSpec with ShouldMatchers with MockitoSugar with Logging {

  "Calling today" should {

    "return the date of 'now' in the supplied timezone" in {

      val utc = DateTimeZone.UTC
      val london = DateTimeZone.forID("Europe/London")
      val stockholm = DateTimeZone.forID("Europe/Stockholm")
      val sydney = DateTimeZone.forID("Australia/Sydney")
      val honolulu = DateTimeZone.forID("Pacific/Honolulu")

      val clock1 = clock(1375168033370L) // Tue 30 Jul 2013 08:07:13.370 BST
      clock1.today(utc) should equal (Date(2013, 7, 30))
      clock1.today(london) should equal (Date(2013, 7, 30))
      clock1.today(stockholm) should equal (Date(2013, 7, 30))
      clock1.today(sydney) should equal (Date(2013, 7, 30))
      clock1.today(honolulu) should equal (Date(2013, 7, 29))

      val clock2 = clock(951867000000L) // Tue 29 Feb 2000 23:30:00.000 GMT
      clock2.today(utc) should equal (Date(2000, 2, 29))
      clock2.today(london) should equal (Date(2000, 2, 29))
      clock2.today(stockholm) should equal (Date(2000, 3, 1))
      clock2.today(sydney) should equal (Date(2000, 3, 1))
      clock2.today(honolulu) should equal (Date(2000, 2, 29))

      val clock3 = clock(227746800000L) // Mon 21 Mar 1977 00:00:00.000 BST
      clock3.today(utc) should equal (Date(1977, 3, 20))
      clock3.today(london) should equal (Date(1977, 3, 21))
      clock3.today(stockholm) should equal (Date(1977, 3, 21))
      clock3.today(sydney) should equal (Date(1977, 3, 21))
      clock3.today(honolulu) should equal (Date(1977, 3, 20))
    }
  }

  def clock(time: Long): Clock = new Clock {
    override val now = Time(time)
  }

}