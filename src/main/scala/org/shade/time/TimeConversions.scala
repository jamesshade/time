package org.shade.time

import org.joda.time._

object TimeConversions {

  implicit def timeToLong(time: Time): Long = time.millis
  implicit def longToTime(time: Long): Time = Time(time)

  implicit def timeToJoda(time: Time): DateTime = time.joda
  implicit def timeFromJoda(time: ReadableInstant): Time = Time(time.getMillis)

  implicit def dateToJoda(date: Date): LocalDate = date.joda
  implicit def dateFromJoda(date: LocalDate): Date = Date(date)
}