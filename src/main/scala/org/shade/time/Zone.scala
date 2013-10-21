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

// TODO [JJS] TEST ZONE CLASS & OBJECT
// TODO [JJS] REMOVE EXPOSURE OF JODA STUFF FROM HERE (in line with Date and Time) (?)

final class Zone(val joda: DateTimeZone) {

  lazy val id = joda.getID

  override lazy val toString: String = id

  override def equals(other: Any) = other match {
    case Zone(otherId: String) if otherId == id => true
    case _ => false
  }

  override lazy val hashCode: Int = id.hashCode

  def apply(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int): Time = {
    try {
      Time(new DateTime(year, month, day, hour, minute, second, millisecond, ISOChronology.getInstance(joda)).getMillis)
    } catch {
      case cause: Throwable => throw new InvalidTimeException(year, month, day, hour, minute, second, millisecond, this, cause)
    }
  }

  def unapply(time: Time): Option[(Int, Int, Int, Int, Int, Int, Int)] = {
    val zoneTime = timeInZone(time)
    Some((zoneTime.getYear, zoneTime.getMonthOfYear, zoneTime.getDayOfMonth,
      zoneTime.getHourOfDay, zoneTime.getMinuteOfHour, zoneTime.getSecondOfMinute, zoneTime.getMillisOfSecond))
  }

  def dateOf(time: Time): Date = {
    val instant = timeInZone(time)
    Date(instant.getYear, instant.getMonthOfYear, instant.getDayOfMonth)
  }
  
  private def timeInZone(time: Time) = new DateTime(time.millis, ISOChronology.getInstance(joda))
}

object Zone {

  def apply(id: String): Zone = try {
    new Zone(DateTimeZone.forID(id))
  } catch {
    case e: IllegalArgumentException => throw new InvalidTimezoneException(id, e.getMessage)
  }

  val utc = Zone("UTC")

  def unapply(zone: Zone): Option[String] = Option(zone).map(_.id)

  object Joda {
    def unapply(zone: Zone): Option[DateTimeZone] = Option(zone).map(_.joda)
  }
}

case class InvalidTimezoneException(id: String, message: String) extends RuntimeException(s"Invalid timezone '$id': $message")

case class InvalidTimeException(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int, zone: Zone, cause: Throwable)
  extends RuntimeException(s"Invalid time: [(Year: $year) (Month: $month) (Day: $day) " +
    s"(Hour: $hour) (Minute: $minute) (Second: $second) (Millisecond: $millisecond) (Zone: $zone)]: " + cause.getMessage, cause)

