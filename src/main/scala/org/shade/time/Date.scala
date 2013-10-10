package org.shade.time

import org.joda.time.{IllegalFieldValueException, LocalDate, DateTime, DateTimeZone}
import org.joda.time.chrono.GregorianChronology

case class Date(year: Int, month: Int, day: Int) {

  val joda = try {
    new LocalDate(year, month, day, gregorianUtc)
  } catch {
    case e: IllegalFieldValueException => throw InvalidDateException(year, month, day)
  }

  override lazy val toString = "%04d-%02d-%02d".format(year, month, day)
}

object Date {

  def dateOf(time: Time, timezone: DateTimeZone): Date = {
    val instant = new DateTime(time.millis, GregorianChronology.getInstance(timezone))
    Date(instant.getYear, instant.getMonthOfYear, instant.getDayOfMonth)
  }

  def apply(joda: LocalDate): Date = Date(joda.getYear, joda.getMonthOfYear, joda.getDayOfMonth)
}

case class InvalidDateException(year: Int, month: Int, day: Int) extends RuntimeException(s"'Invalid date: (Year: $year) (Month: $month) (Day: $day)'")
