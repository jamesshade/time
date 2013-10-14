package org.shade.time

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import grizzled.slf4j.Logging
import org.joda.time.chrono.{CopticChronology, GregorianChronology}
import org.joda.time.LocalDate
import Date.dateOf

class DateSpec extends WordSpec with Matchers with MockitoSugar with Logging {

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
      Date(11111, 12, 2).toString shouldBe "11111-12-02"
      Date(45435535, 8, 6).toString shouldBe "45435535-08-06"
    }
  }

  "Constructing a date" should {

    "throw an InvalidDateException if the day number is less than 1" in {
      Date(2013, 6, 1).day shouldBe 1
      evaluating(Date(2013, 6, 0)) should produce[InvalidDateException]

      Date(1985, 2, 1).day shouldBe 1
      evaluating(Date(1985, 2, -1)) should produce[InvalidDateException]

      Date(23, 12, 1).day shouldBe 1
      evaluating(Date(23, 12, -1000)) should produce[InvalidDateException]

      Date(-2333, 6, 1).day shouldBe 1
      evaluating(Date(-2333, 6, -23)) should produce[InvalidDateException]
    }

    "throw an InvalidDateException if the day number is invalid for the month" in {

      Date(2013, 1, 31).day shouldBe 31
      evaluating(Date(2013, 1, 32)) should produce[InvalidDateException]

      Date(2013, 2, 28).day shouldBe 28
      evaluating(Date(2013, 2, 29)) should produce[InvalidDateException]

      Date(2013, 3, 31).day shouldBe 31
      evaluating(Date(2013, 3, 32)) should produce[InvalidDateException]

      Date(2013, 4, 30).day shouldBe 30
      evaluating(Date(2013, 4, 31)) should produce[InvalidDateException]

      Date(2013, 5, 31).day shouldBe 31
      evaluating(Date(2013, 5, 32)) should produce[InvalidDateException]

      Date(2013, 6, 30).day shouldBe 30
      evaluating(Date(2013, 6, 31)) should produce[InvalidDateException]

      Date(2013, 7, 31).day shouldBe 31
      evaluating(Date(2013, 7, 32)) should produce[InvalidDateException]

      Date(2013, 8, 31).day shouldBe 31
      evaluating(Date(2013, 8, 32)) should produce[InvalidDateException]

      Date(2013, 9, 30).day shouldBe 30
      evaluating(Date(2013, 9, 31)) should produce[InvalidDateException]

      Date(2013, 10, 31).day shouldBe 31
      evaluating(Date(2013, 10, 32)) should produce[InvalidDateException]

      Date(2013, 11, 30).day shouldBe 30
      evaluating(Date(2013, 11, 31)) should produce[InvalidDateException]

      Date(2013, 12, 31).day shouldBe 31
      evaluating(Date(2013, 12, 32)) should produce[InvalidDateException]
    }

    "throw an InvalidDateException if the day number is invalid for February" in {

      Date(2013, 2, 28).day shouldBe 28
      evaluating(Date(2013, 2, 29)) should produce[InvalidDateException]

      Date(2014, 2, 28).day shouldBe 28
      evaluating(Date(2014, 2, 29)) should produce[InvalidDateException]

      Date(2015, 2, 28).day shouldBe 28
      evaluating(Date(2015, 2, 29)) should produce[InvalidDateException]

      Date(2016, 2, 29).day shouldBe 29
      evaluating(Date(2016, 2, 30)) should produce[InvalidDateException]

      Date(1948, 2, 29).day shouldBe 29
      evaluating(Date(1948, 2, 30)) should produce[InvalidDateException]

      Date(1996, 2, 29).day shouldBe 29
      evaluating(Date(1996, 2, 30)) should produce[InvalidDateException]

      Date(2000, 2, 29).day shouldBe 29
      evaluating(Date(2000, 2, 30)) should produce[InvalidDateException]

      Date(1896, 2, 29).day shouldBe 29
      evaluating(Date(1896, 2, 30)) should produce[InvalidDateException]

      Date(1900, 2, 28).day shouldBe 28
      evaluating(Date(1900, 2, 29)) should produce[InvalidDateException]
    }

    "throw an InvalidDateException if the month is not in the range 1 to 12" in {

      evaluating(Date(2013, 0, 1)) should produce[InvalidDateException]

      evaluating(Date(2013, -1, 1)) should produce[InvalidDateException]

      evaluating(Date(2013, -432, 1)) should produce[InvalidDateException]

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

      evaluating(Date(2013, 13, 1)) should produce[InvalidDateException]

      evaluating(Date(2013, 14, 1)) should produce[InvalidDateException]

      evaluating(Date(2013, 432, 1)) should produce[InvalidDateException]
    }
  }

  "The joda method" should {

    "return the date as a LocalDate (with ISO chronology and UTC zone)" in {
      Date(2013, 6, 22).joda should equal(new LocalDate(2013, 6, 22, isoUtc))
      Date(-3424, 12, 31).joda should equal(new LocalDate(-3424, 12, 31, isoUtc))
      Date(1959, 1, 1).joda should equal(new LocalDate(1959, 1, 1, isoUtc))
    }
  }

  "Constructing a Date using the Joda-time apply method" should {

    val sydney = Zone("Australia/Sydney")

    "Create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #1" in {
      Date(new LocalDate(2013, 4, 22, GregorianChronology.getInstanceUTC)) shouldBe Date(2013, 4, 22)
    }

    "Create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #2" in {
      Date(new LocalDate(2013, 4, 22, GregorianChronology.getInstance(sydney.joda, 5))) shouldBe Date(2013, 4, 22)
    }

    "Create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #3" in {
      Date(new LocalDate(2013, 2, 21, GregorianChronology.getInstanceUTC)) shouldBe Date(2013, 2, 21)
    }

    "Create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #4" in {
      Date(new LocalDate(2013, 2, 21, GregorianChronology.getInstance(sydney.joda, 2))) shouldBe Date(2013, 2, 21)
    }

    "Create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #5" in {
      Date(new LocalDate(2013, 4, 22, CopticChronology.getInstanceUTC)) shouldBe Date(2297, 1, 2)
    }

    "Create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #6" in {
      Date(new LocalDate(2013, 4, 22, CopticChronology.getInstance(sydney.joda, 1))) shouldBe Date(2297, 1, 2)
    }

    "Create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #7" in {
      Date(new LocalDate(2013, 2, 21, CopticChronology.getInstanceUTC)) shouldBe Date(2296, 11, 2)
    }

    "Create the correct date, ignoring the timezone associated with the LocalDate and converting from the chronology assigned to the LocalDate to the equivalent ISO chronology date #8" in {
      Date(new LocalDate(2013, 2, 21, CopticChronology.getInstance(sydney.joda, 1))) shouldBe Date(2296, 11, 2)
    }
  }

  "Constructing a Date using the dateOf method" should {

    "return the correct Date for the time in the supplied Timezone according to ISO Chronology" in {

      val utc = Zone.utc
      val london = Zone("Europe/London")
      val stockholm = Zone("Europe/Stockholm")
      val sydney = Zone("Australia/Sydney")
      val honolulu = Zone("Pacific/Honolulu")

      val time1 = Time(1375168033370L) // Tue 30 Jul 2013 08:07:13.370 BST
      Date dateOf(time1, utc) should equal(Date(2013, 7, 30))
      dateOf(time1, london) should equal(Date(2013, 7, 30))
      dateOf(time1, stockholm) should equal(Date(2013, 7, 30))
      dateOf(time1, sydney) should equal(Date(2013, 7, 30))
      dateOf(time1, honolulu) should equal(Date(2013, 7, 29))

      val time2 = Time(951867000000L) // Tue 29 Feb 2000 23:30:00.000 GMT
      dateOf(time2, utc) should equal(Date(2000, 2, 29))
      dateOf(time2, london) should equal(Date(2000, 2, 29))
      dateOf(time2, stockholm) should equal(Date(2000, 3, 1))
      dateOf(time2, sydney) should equal(Date(2000, 3, 1))
      dateOf(time2, honolulu) should equal(Date(2000, 2, 29))

      val time3 = Time(227746800000L) // Mon 21 Mar 1977 00:00:00.000 BST
      dateOf(time3, utc) should equal(Date(1977, 3, 20))
      dateOf(time3, london) should equal(Date(1977, 3, 21))
      dateOf(time3, stockholm) should equal(Date(1977, 3, 21))
      dateOf(time3, sydney) should equal(Date(1977, 3, 21))
      dateOf(time3, honolulu) should equal(Date(1977, 3, 20))
    }
  }
}