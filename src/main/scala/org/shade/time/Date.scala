package org.shade.time

import org.joda.time.{DateTimeZone, LocalDate, DateTime}
import org.joda.time.chrono.ISOChronology

case class Date(year: Int, month: Int, day: Int) {

  val joda = try {
    new LocalDate(year, month, day, isoUtc)
  } catch {
    case cause: Throwable => throw InvalidDateException(year, month, day, cause)
  }

  override lazy val toString = "%04d-%02d-%02d".format(year, month, day)
}

object Date {

  def dateOf(time: Time, zone: Zone): Date = {
    val instant = new DateTime(time.millis, ISOChronology.getInstance(zone.joda))
    Date(instant.getYear, instant.getMonthOfYear, instant.getDayOfMonth)
  }

  def apply(joda: LocalDate): Date = {

    val date = if (joda.getChronology == isoUtc) joda else {
      val time = new DateTime(joda.toDateTimeAtStartOfDay(DateTimeZone.UTC).plusHours(12).getMillis, isoUtc)
      new LocalDate(time, isoUtc)
    }

    Date(date.getYear, date.getMonthOfYear, date.getDayOfMonth)
  }
}

case class InvalidDateException(year: Int, month: Int, day: Int, cause: Throwable)
  extends RuntimeException(s"Invalid ISO date: (Year: $year) (Month: $month) (Day: $day)")
