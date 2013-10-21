package org.shade.time

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import org.joda.time.{DateTime, DateTimeZone}

class ZoneSpec extends WordSpec with Matchers with MockitoSugar {

  import Zone.utc

  private val london = Zone("Europe/London")
  private val addisAbaba = Zone("Africa/Addis_Ababa")

  "The Zone instance apply method" should {

    "return the correct time object for this zone #1" in {
      london(2013, 5, 22, 11, 43, 23, 222) shouldBe Time(1369219403222L)
    }

    "return the correct time object #2" in {
      // 1600-06-12T10:44:44.111+02:34:48 =  (In UTC = -11661974115889 - 9288000 )
      addisAbaba(1600, 6, 12, 10, 44, 44, 111) shouldBe Time(-11661974115889L - 9288000L)
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
      val time = Time(new DateTime(2013, 6, 22, 11, 55, 22, 666, DateTimeZone.UTC).getMillis)

      val x = time match {
        case london(2013, 6, 22, 11, 55, 22, 666) => throw new AssertionError("This is the UTC time not the UK time")
        case london(2013, 6, 22, 12, 55, 22, 666) => "correct!"
      }

      x shouldBe "correct!"
    }
  }

  "The dateOf" should {

    "return the correct Date in this zone for the supplied time" in {

      val utc = Zone.utc
      val london = Zone("Europe/London")
      val stockholm = Zone("Europe/Stockholm")
      val sydney = Zone("Australia/Sydney")
      val honolulu = Zone("Pacific/Honolulu")

      val time1 = Time(1375168033370L) // Tue 30 Jul 2013 08:07:13.370 BST
      utc.dateOf(time1) should equal(Date(2013, 7, 30))
      london.dateOf(time1) should equal(Date(2013, 7, 30))
      stockholm.dateOf(time1) should equal(Date(2013, 7, 30))
      sydney.dateOf(time1) should equal(Date(2013, 7, 30))
      honolulu.dateOf(time1) should equal(Date(2013, 7, 29))

      val time2 = Time(951867000000L) // Tue 29 Feb 2000 23:30:00.000 GMT
      utc.dateOf(time2) should equal(Date(2000, 2, 29))
      london.dateOf(time2) should equal(Date(2000, 2, 29))
      stockholm.dateOf(time2) should equal(Date(2000, 3, 1))
      sydney.dateOf(time2) should equal(Date(2000, 3, 1))
      honolulu.dateOf(time2) should equal(Date(2000, 2, 29))

      val time3 = Time(227746800000L) // Mon 21 Mar 1977 00:00:00.000 BST
      utc.dateOf(time3) should equal(Date(1977, 3, 20))
      london.dateOf(time3) should equal(Date(1977, 3, 21))
      stockholm.dateOf(time3) should equal(Date(1977, 3, 21))
      sydney.dateOf(time3) should equal(Date(1977, 3, 21))
      honolulu.dateOf(time3) should equal(Date(1977, 3, 20))
    }
  }
}