package org.shade.time

import org.joda.time.{LocalDate, DateTime}
import org.joda.time.chrono.ISOChronology

case class Date(year: Int, month: Int, day: Int) {

  try {
    new LocalDate(year, month, day, isoUtc)
  } catch {
    case e: Throwable => throw InvalidDateException(year, month, day, e)
  }

  override lazy val toString = "%04d-%02d-%02d".format(year, month, day)
}

object Date {

  def dateOf(time: Time, zone: Zone): Date = {
    val instant = new DateTime(time.millis, ISOChronology.getInstance(zone.joda))
    Date(instant.getYear, instant.getMonthOfYear, instant.getDayOfMonth)
  }
}

case class InvalidDateException(year: Int, month: Int, day: Int, cause: Throwable)
  extends RuntimeException(s"Invalid ISO date: (Year: $year) (Month: $month) (Day: $day)")
