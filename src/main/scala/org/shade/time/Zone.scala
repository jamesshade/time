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
import org.joda.time.format.ISODateTimeFormat

// TODO [JJS] TEST ZONE CLASS & OBJECT
// TODO [JJS] IS THE CONCEPT OF EQUALITY OKAY HERE?  SHOULDN'T GMT == "+0000" == "+00:00" ?

case class Zone(id: String) {

  private[time] val joda = try {
    DateTimeZone.forID(id)
  } catch {
    case e: IllegalArgumentException => throw new InvalidTimeZoneException(id, e.getMessage)
  }

  override lazy val toString: String = id

  def apply(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int): Time = {
    try {
      Time(new DateTime(year, month, day, hour, minute, second, millisecond, ISOChronology.getInstance(joda)).getMillis)
    } catch {
      case cause: Throwable => throw new InvalidTimeException(year, month, day, hour, minute, second, millisecond, this, cause)
    }
  }

  def apply(dateAndTimeOfDay: DateAndTimeOfDay): Time = apply(dateAndTimeOfDay.date, dateAndTimeOfDay.timeOfDay)
  
  def apply(date: Date, timeOfDay: TimeOfDay): Time = {
    apply(date.year, date.month, date.day, timeOfDay.hour, timeOfDay.minute, timeOfDay.second, timeOfDay.millisecond)
  }

  def unapply(time: Time): Option[TimeInZone] = Option(time).map(new TimeInZone(_))

  def dateAndTimeOfDay(time: Time): DateAndTimeOfDay  = TimeInZone(time).dateAndTimeOfDay
  def dateOf(time: Time): Date = TimeInZone(time).date
  def timeOf(time: Time): TimeOfDay = TimeInZone(time).timeOfDay

  private case class TimeInZone(time: Time) {

    if (time == null) throw new NullPointerException("time is null")

    private val jodaInZone = new DateTime(time.millis, ISOChronology.getInstance(joda))

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

    override def equals(other: Any): Boolean = Option(other) match {
      case Some(t: TimeInZone) => time == t.time
      case Some(t: Time) => time == t
      case _ => false
    }

    override lazy val hashCode: Int = time.hashCode

    override def toString = ISODateTimeFormat.dateTime.print(jodaInZone)
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
