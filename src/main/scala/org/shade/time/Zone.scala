/*
 *  Copyright 2013-2015 James Shade
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.shade.time

import org.joda.time.chrono.ISOChronology
import org.joda.time.{DateTime, DateTimeZone}

case class Zone(id: String) {

  if (id == null) throw new NullPointerException("Time zone id is null")

  private[time] val joda = try {
    DateTimeZone.forID(id)
  } catch {
    case e: IllegalArgumentException => throw new InvalidZoneException(id, e.getMessage, e)
  }

  override lazy val toString: String = id

  def apply(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int): Instant = {
    apply(Date(year, month, day), Time(hour, minute, second, millisecond))
  }

  def apply(dateAndTime: DateAndTime): Instant = {
    apply(dateAndTime.date, dateAndTime.time)
  }
  
  def apply(date: Date, time: Time): Instant = {
    try {
      Instant(new DateTime(date.year, date.month, date.day, time.hour, time.minute, time.second, time.millisecond, ISOChronology.getInstance(joda)).getMillis)
    } catch {
      case cause: Exception => throw new InvalidTimeInZoneException(date.year, date.month, date.day, time.hour, time.minute, time.second, time.millisecond, this, cause)
    }
  }

  def unapply(instant: Instant): Option[(Int, Int, Int, Int, Int, Int, Int)] = Option(instant).map(time => {
    val t = dateAndTimeOf(instant)
    (t.year, t.month, t.day, t.hour, t.minute, t.second, t.millisecond)
  })

  def today(implicit clock: Clock = SystemClock) = dateOf(clock.now)

  def dateAndTimeOf(instant: Instant): DateAndTime  = {

    val jodaInZone = new DateTime(instant.millis, ISOChronology.getInstance(joda))

    DateAndTime(
      jodaInZone.getYear,
      jodaInZone.getMonthOfYear,
      jodaInZone.getDayOfMonth,
      jodaInZone.getHourOfDay,
      jodaInZone.getMinuteOfHour,
      jodaInZone.getSecondOfMinute,
      jodaInZone.getMillisOfSecond)
  }

  def dateOf(instant: Instant): Date = dateAndTimeOf(instant).date
  def timeOf(instant: Instant): Time = dateAndTimeOf(instant).time
}

object Zone {
  val UTC = Zone("UTC")
}

case class InvalidZoneException(id: String, message: String, cause: Throwable = null) extends TimeException(s"Unknown/invalid time zone '$id': $message", cause)

case class InvalidTimeInZoneException(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int, zone: Zone, cause: Throwable)
  extends TimeException(s"Invalid time: (Year: $year) (Month: $month) (Day: $day) " +
    s"(Hour: $hour) (Minute: $minute) (Second: $second) (Millisecond: $millisecond) (Zone: $zone): " + cause.getMessage, cause)

