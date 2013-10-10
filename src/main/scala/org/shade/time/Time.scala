package org.shade.time

import org.joda.time._
import org.joda.time.format.ISODateTimeFormat

case class Time(millis: Long) {

  import Time.format

  lazy val joda = new DateTime(millis, gregorianUtc)

  lazy val jdk = new java.util.Date(millis)

  override lazy val toString = format.print(joda)
}

object Time {

  private[time] lazy val format = ISODateTimeFormat.dateTime

  def apply(joda: ReadableInstant): Time = Time(joda.getMillis)

  def apply(jdk: java.util.Date): Time = new Time(jdk.getTime)
}

