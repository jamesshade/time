package org.shade.time

import org.joda.time._

object Conversions {

  implicit def timeToLong(time: Time): Long = time.millis

  implicit def longToTime(time: Long): Time = Time(time)

  implicit def timeToJoda(time: Time): DateTime = new DateTime(time.millis, isoUtc)

  implicit def timeFromJoda(time: ReadableInstant): Time = Time(time.getMillis)

  implicit def dateToJoda(date: Date): LocalDate = new LocalDate(date.year, date.month, date.day, isoUtc)

  implicit def dateFromJoda(joda: LocalDate): Date = {

    val date = if (joda.getChronology == isoUtc) joda else {
      val time = new DateTime(joda.toDateTimeAtStartOfDay(DateTimeZone.UTC).plusHours(12).getMillis, isoUtc)
      new LocalDate(time, isoUtc)
    }

    Date(date.getYear, date.getMonthOfYear, date.getDayOfMonth)
  }
}