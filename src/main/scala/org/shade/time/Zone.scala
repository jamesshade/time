/*
 *  Copyright 2013 James Shade
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

import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.chrono.ISOChronology
import org.shade.Asserts._

// TODO [JJS] TEST ZONE CLASS & OBJECT
// TODO [JJS] IS THE CONCEPT OF EQUALITY OKAY HERE?  SHOULDN'T GMT == "+0000" == "+00:00" ?

case class Zone(id: String) {

  private[time] val joda = try {
    DateTimeZone.forID(id)
  } catch {
    case e: IllegalArgumentException => throw new InvalidTimeZoneException(id, e.getMessage)
  }

  override lazy val toString: String = id

  def apply(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int): Instant = {
    try {
      Instant(new DateTime(year, month, day, hour, minute, second, millisecond, ISOChronology.getInstance(joda)).getMillis)
    } catch {
      case cause: Throwable => throw new InvalidTimeException(year, month, day, hour, minute, second, millisecond, this, cause)
    }
  }

  def today(implicit clock: Clock = SystemClock) = dateOf(clock.now)

  def apply(dateAndTimeOfDay: DateAndTimeOfDay): Instant = apply(dateAndTimeOfDay.date, dateAndTimeOfDay.timeOfDay)
  
  def apply(date: Date, timeOfDay: TimeOfDay): Instant = {
    apply(date.year, date.month, date.day, timeOfDay.hour, timeOfDay.minute, timeOfDay.second, timeOfDay.millisecond)
  }

  def unapply(instant: Instant): Option[(Int, Int, Int, Int, Int, Int, Int)] = Option(instant).map(time => {
    val t = dateAndTimeOfDay(instant)
    (t.year, t.month, t.day, t.hour, t.minute, t.second, t.millisecond)
  })

  def dateAndTimeOfDay(instant: Instant): DateAndTimeOfDay  = TimeInZone(instant).dateAndTimeOfDay // TODO [JJS] MUST BE A BETTER NAME FOR THIS
  def dateOf(instant: Instant): Date = TimeInZone(instant).date  // TODO [JJS] DO WE REALLY WANT THIS HERE?
  def timeOf(instant: Instant): TimeOfDay = TimeInZone(instant).timeOfDay // TODO [JJS] DO WE REALLY WANT THIS HERE?

  private case class TimeInZone(instant: Instant) {

    notNull("instant" -> instant)

    private val jodaInZone = new DateTime(instant.millis, ISOChronology.getInstance(joda))

    lazy val dateAndTimeOfDay = DateAndTimeOfDay(
      jodaInZone.getYear,
      jodaInZone.getMonthOfYear,
      jodaInZone.getDayOfMonth,
      jodaInZone.getHourOfDay,
      jodaInZone.getMinuteOfHour,
      jodaInZone.getSecondOfMinute,
      jodaInZone.getMillisOfSecond)

    lazy val date = dateAndTimeOfDay.date
    lazy val timeOfDay = dateAndTimeOfDay.timeOfDay
  }
}

object Zone {

  val utc = Zone("UTC")

  def unapply(zone: Zone): Option[String] = Option(zone).map(_.id)
}

case class InvalidTimeZoneException(id: String, message: String) extends RuntimeException(s"Invalid time zone '$id': $message")

case class InvalidTimeException(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int, zone: Zone, cause: Throwable)
  extends RuntimeException(s"Invalid time: [(Year: $year) (Month: $month) (Day: $day) " +
    s"(Hour: $hour) (Minute: $minute) (Second: $second) (Millisecond: $millisecond) (Zone: $zone)]: " + cause.getMessage, cause)
