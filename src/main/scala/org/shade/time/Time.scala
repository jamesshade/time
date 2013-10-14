package org.shade.time

import org.joda.time._
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.chrono.ISOChronology

case class Time(millis: Long) {

  import Time.format

  lazy val joda = new DateTime(millis, isoUtc)

  lazy val jdk = new java.util.Date(millis)

  override lazy val toString = format.print(joda)
}

object Time {

  private[time] lazy val format = ISODateTimeFormat.dateTime

  def apply(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int, zone: Zone): Time = {
    try {
      apply(new DateTime(year, month, day, hour, minute, second, millisecond, ISOChronology.getInstance(zone.joda)))
    } catch {
      case cause: Throwable => throw new InvalidTimeException(year, month, day, hour, minute, second, millisecond, zone, cause)
    }
  }

  def apply(joda: ReadableInstant): Time = Time(joda.getMillis)

  def apply(jdk: java.util.Date): Time = new Time(jdk.getTime)
}

case class InvalidTimeException(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int, zone: Zone, cause: Throwable)
  extends RuntimeException(s"Invalid time: [(Year: $year) (Month: $month) (Day: $day) " +
    s"(Hour: $hour) (Minute: $minute) (Second: $second) (Millisecond: $millisecond) (Zone: $zone)]: " + cause.getMessage, cause)
