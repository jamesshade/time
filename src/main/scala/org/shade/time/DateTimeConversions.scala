package org.shade.time

import org.joda.time._
import java.util.TimeZone

// TODO [JJS] Unit test

object DateTimeConversions {

  implicit def timeToLong(time: Time) = time.millis
  implicit def longToTime(time: Long) = Time(time)

  implicit def timeToJoda(time: Time) = new DateTime(time.millis, gregorianUtc)
  implicit def timeFromJoda(time: ReadableInstant) = Time(time.getMillis)

  implicit def dateToJoda(date: Date) = new LocalDate(date.year, date.month, date.day, gregorianUtc)
  implicit def dateFromJoda(date: LocalDate) = new Date(date.getYear, date.getMonthOfYear, date.getDayOfMonth)

  implicit def timezoneToJoda(timezone: TimeZone) = DateTimeZone.forTimeZone(timezone)
  implicit def timezoneFromJoda(timezone: DateTimeZone) = timezone.toTimeZone

  implicit def timezoneFromString(timezone: String) = DateTimeZone.forID(timezone)
}