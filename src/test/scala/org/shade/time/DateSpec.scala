package org.shade.time

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import grizzled.slf4j.Logging
import org.joda.time.chrono.{CopticChronology, GregorianChronology}
import org.joda.time.{DateTimeZone, LocalDate}
import Date.dateOf

class DateSpec extends WordSpec with ShouldMatchers with MockitoSugar with Logging {

  "Constructing a Date object with a valid day, month and year" should {

    "return a Date object with the correct year, month and day" in {
      val date = Date(2013, 6, 22)
      date.year should equal(2013)
      date.month should equal(6)
      date.day should equal(22)

      val date2 = Date(-3424, 12, 31)
      date2.year should equal(-3424)
      date2.month should equal(12)
      date2.day should equal(31)
    }
  }
  
  "The toString method on a date" should {
    
    "return the date in the expected yyyy-MM-dd format" in {
      Date(2013, 6, 22).toString should equal("2013-06-22")
      Date(1944, 11, 3).toString should equal("1944-11-03")
      Date(2024, 10, 30).toString should equal("2024-10-30")
    }
    
    "return the date in the correct format even for very low year numbers" in {
      Date(1, 1, 1).toString should equal("0001-01-01")
      Date(-1, 11, 4).toString should equal("-001-11-04")
      Date(-14344, 12, 31).toString should equal("-14344-12-31")
    }
    
    "return the date in the correct format for very large years" in {
      Date(11111, 12, 2).toString should equal("11111-12-02")
      Date(45435535, 8, 6).toString should equal("45435535-08-06")
    }
  }

  "Constructing a date" should {

    "throw an InvalidDateException if the day number is less than 1" in {
      Date(2013, 6, 1).day should equal(1)
      val e1 = evaluating(Date(2013, 6, 0)) should produce [InvalidDateException]
      e1 should equal(InvalidDateException(2013, 6 ,0))

      Date(1985, 2, 1).day should equal(1)
      val e2 = evaluating(Date(1985, 2, -1)) should produce [InvalidDateException]
      e2 should equal(InvalidDateException(1985, 2, -1))

      Date(23, 12, 1).day should equal(1)
      val e3 = evaluating(Date(23, 12, -1000)) should produce [InvalidDateException]
      e3 should equal(InvalidDateException(23, 12, -1000))

      Date(-2333, 6, 1).day should equal(1)
      val e4 = evaluating(Date(-2333, 6, -23)) should produce [InvalidDateException]
      e4 should equal(InvalidDateException(-2333, 6 ,-23))
    }

    "throw an InvalidDateException if the day number is invalid for the month" in {

      Date(2013, 1, 31).day should equal(31)
      val e1 = evaluating(Date(2013, 1, 32)) should produce [InvalidDateException]
      e1 should equal(InvalidDateException(2013, 1, 32))

      Date(2013, 2, 28).day should equal(28)
      val e2 = evaluating(Date(2013, 2, 29)) should produce [InvalidDateException]
      e2 should equal(InvalidDateException(2013, 2, 29))

      Date(2013, 3, 31).day should equal(31)
      val e3 = evaluating(Date(2013, 3, 32)) should produce [InvalidDateException]
      e3 should equal(InvalidDateException(2013, 3, 32))

      Date(2013, 4, 30).day should equal(30)
      val e4 = evaluating(Date(2013, 4, 31)) should produce [InvalidDateException]
      e4 should equal(InvalidDateException(2013, 4, 31))

      Date(2013, 5, 31).day should equal(31)
      val e5 = evaluating(Date(2013, 5, 32)) should produce [InvalidDateException]
      e5 should equal(InvalidDateException(2013, 5, 32))

      Date(2013, 6, 30).day should equal(30)
      val e6 = evaluating(Date(2013, 6, 31)) should produce [InvalidDateException]
      e6 should equal(InvalidDateException(2013, 6, 31))

      Date(2013, 7, 31).day should equal(31)
      val e7 = evaluating(Date(2013, 7, 32)) should produce [InvalidDateException]
      e7 should equal(InvalidDateException(2013, 7, 32))

      Date(2013, 8, 31).day should equal(31)
      val e8 = evaluating(Date(2013, 8, 32)) should produce [InvalidDateException]
      e8 should equal(InvalidDateException(2013, 8, 32))

      Date(2013, 9, 30).day should equal(30)
      val e9 = evaluating(Date(2013, 9, 31)) should produce [InvalidDateException]
      e9 should equal(InvalidDateException(2013, 9, 31))

      Date(2013, 10, 31).day should equal(31)
      val e10 = evaluating(Date(2013, 10, 32)) should produce [InvalidDateException]
      e10 should equal(InvalidDateException(2013, 10, 32))

      Date(2013, 11, 30).day should equal(30)
      val e11 = evaluating(Date(2013, 11, 31)) should produce [InvalidDateException]
      e11 should equal(InvalidDateException(2013, 11, 31))

      Date(2013, 12, 31).day should equal(31)
      val e12 = evaluating(Date(2013, 12, 32)) should produce [InvalidDateException]
      e12 should equal(InvalidDateException(2013, 12, 32))
    }

    "throw an InvalidDateException if the day number is invalid for February" in {

      Date(2013, 2, 28).day should equal(28)
      val e1 = evaluating(Date(2013, 2, 29)) should produce [InvalidDateException]
      e1 should equal(InvalidDateException(2013, 2, 29))

      Date(2014, 2, 28).day should equal(28)
      val e2 = evaluating(Date(2014, 2, 29)) should produce [InvalidDateException]
      e2 should equal(InvalidDateException(2014, 2, 29))

      Date(2015, 2, 28).day should equal(28)
      val e3 = evaluating(Date(2015, 2, 29)) should produce [InvalidDateException]
      e3 should equal(InvalidDateException(2015, 2, 29))

      Date(2016, 2, 29).day should equal(29)
      val e4 = evaluating(Date(2016, 2, 30)) should produce [InvalidDateException]
      e4 should equal(InvalidDateException(2016, 2, 30))

      Date(1948, 2, 29).day should equal(29)
      val e5 = evaluating(Date(1948, 2, 30)) should produce [InvalidDateException]
      e5 should equal(InvalidDateException(1948, 2, 30))

      Date(1996, 2, 29).day should equal(29)
      val e6 = evaluating(Date(1996, 2, 30)) should produce [InvalidDateException]
      e6 should equal(InvalidDateException(1996, 2, 30))

      Date(2000, 2, 29).day should equal(29)
      val e7 = evaluating(Date(2000, 2, 30)) should produce [InvalidDateException]
      e7 should equal(InvalidDateException(2000, 2, 30))

      Date(1896, 2, 29).day should equal(29)
      val e8 = evaluating(Date(1896, 2, 30)) should produce [InvalidDateException]
      e8 should equal(InvalidDateException(1896, 2, 30))

      Date(1900, 2, 28).day should equal(28)
      val e9 = evaluating(Date(1900, 2, 29)) should produce [InvalidDateException]
      e9 should equal(InvalidDateException(1900, 2, 29))
    }

    "throw an InvalidDateException if the month is not in the range 1 to 12" in {

      val e1 = evaluating(Date(2013, 0, 1)) should produce [InvalidDateException]
      e1 should equal(InvalidDateException(2013, 0, 1))

      val e2 = evaluating(Date(2013, -1, 1)) should produce [InvalidDateException]
      e2 should equal(InvalidDateException(2013, -1, 1))

      val e3 = evaluating(Date(2013, -432, 1)) should produce [InvalidDateException]
      e3 should equal(InvalidDateException(2013, -432, 1))

      Date(2013, 1, 1).month should equal(1)
      Date(2013, 2, 1).month should equal(2)
      Date(2013, 3, 1).month should equal(3)
      Date(2013, 4, 1).month should equal(4)
      Date(2013, 5, 1).month should equal(5)
      Date(2013, 6, 1).month should equal(6)
      Date(2013, 7, 1).month should equal(7)
      Date(2013, 8, 1).month should equal(8)
      Date(2013, 9, 1).month should equal(9)
      Date(2013, 10, 1).month should equal(10)
      Date(2013, 11, 1).month should equal(11)
      Date(2013, 12, 1).month should equal(12)

      val e4 = evaluating(Date(2013, 13, 1)) should produce [InvalidDateException]
      e4 should equal(InvalidDateException(2013, 13, 1))

      val e5 = evaluating(Date(2013, 14, 1)) should produce [InvalidDateException]
      e5 should equal(InvalidDateException(2013, 14, 1))

      val e6 = evaluating(Date(2013, 432, 1)) should produce [InvalidDateException]
      e6 should equal(InvalidDateException(2013, 432, 1))
    }
  }

  "The joda method" should {

    "return the date as a LocalDate (with Gregorian UTC chronology)" in {
      Date(2013, 6, 22).joda should equal (new LocalDate(2013, 6, 22, GregorianChronology.getInstanceUTC))
      Date(-3424, 12, 31).joda should equal (new LocalDate(-3424, 12, 31, GregorianChronology.getInstanceUTC))
      Date(1959, 1, 1).joda should equal (new LocalDate(1959, 1, 1, GregorianChronology.getInstanceUTC))
    }
  }

  "Constructing a Date using the Joda-time apply method" should {

    val sydney = DateTimeZone.forID("Australia/Sydney")

    "Create the correct date, ignoring any arbitrary chronology assigned to the LocalDate" in {
      Date(new LocalDate(2013, 4, 22, GregorianChronology.getInstanceUTC)) should equal(Date(2013, 4, 22))
      Date(new LocalDate(2013, 4, 22, GregorianChronology.getInstance(sydney, 5))) should equal(Date(2013, 4, 22))
      Date(new LocalDate(2013, 2, 21, GregorianChronology.getInstanceUTC)) should equal(Date(2013, 2, 21))
      Date(new LocalDate(2013, 2, 21, GregorianChronology.getInstance(sydney, 2))) should equal(Date(2013, 2, 21))
      Date(new LocalDate(2013, 4, 22, CopticChronology.getInstanceUTC)) should equal(Date(2013, 4, 22))
      Date(new LocalDate(2013, 4, 22, CopticChronology.getInstance(sydney, 1))) should equal(Date(2013, 4, 22))
      Date(new LocalDate(2013, 2, 21, CopticChronology.getInstanceUTC)) should equal(Date(2013, 2, 21))
      Date(new LocalDate(2013, 2, 21, CopticChronology.getInstance(sydney, 1))) should equal(Date(2013, 2, 21))
    }
  }

  "Constructing a Date using the dateOf method" should {

    "return the correct Date for the time in the supplied Timezone according to Gregorian Chronology" in {

      val utc = DateTimeZone.UTC
      val london = DateTimeZone.forID("Europe/London")
      val stockholm = DateTimeZone.forID("Europe/Stockholm")
      val sydney = DateTimeZone.forID("Australia/Sydney")
      val honolulu = DateTimeZone.forID("Pacific/Honolulu")

      val time1 = Time(1375168033370L) // Tue 30 Jul 2013 08:07:13.370 BST
      Date dateOf(time1, utc) should equal (Date(2013, 7, 30))
      dateOf(time1, london) should equal (Date(2013, 7, 30))
      dateOf(time1, stockholm) should equal (Date(2013, 7, 30))
      dateOf(time1, sydney) should equal (Date(2013, 7, 30))
      dateOf(time1, honolulu) should equal (Date(2013, 7, 29))

      val time2 = Time(951867000000L) // Tue 29 Feb 2000 23:30:00.000 GMT
      dateOf(time2, utc) should equal (Date(2000, 2, 29))
      dateOf(time2, london) should equal (Date(2000, 2, 29))
      dateOf(time2, stockholm) should equal (Date(2000, 3, 1))
      dateOf(time2, sydney) should equal (Date(2000, 3, 1))
      dateOf(time2, honolulu) should equal (Date(2000, 2, 29))

      val time3 = Time(227746800000L) // Mon 21 Mar 1977 00:00:00.000 BST
      dateOf(time3, utc) should equal (Date(1977, 3, 20))
      dateOf(time3, london) should equal (Date(1977, 3, 21))
      dateOf(time3, stockholm) should equal (Date(1977, 3, 21))
      dateOf(time3, sydney) should equal (Date(1977, 3, 21))
      dateOf(time3, honolulu) should equal (Date(1977, 3, 20))
    }
  }
}
