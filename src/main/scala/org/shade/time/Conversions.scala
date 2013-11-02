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

import org.joda.time._

object Conversions {

  implicit def instantToLong(instant: Instant): Long = instant.millis

  implicit def instantFromLong(instant: Long): Instant = Instant(instant)

  implicit def instantToJoda(instant: Instant): DateTime = new DateTime(instant.millis, isoUtc)

  implicit def instantFromJoda(instant: ReadableInstant): Instant = Instant(instant.getMillis)

  implicit def dateToJoda(date: Date): LocalDate = new LocalDate(date.year, date.month, date.day, isoUtc)

  implicit def dateFromJoda(joda: LocalDate): Date = {

    val date = if (joda.getChronology == isoUtc) joda else {
      val jodaDateTime = new DateTime(joda.toDateTimeAtStartOfDay(DateTimeZone.UTC).plusHours(12).getMillis, isoUtc)
      new LocalDate(jodaDateTime, isoUtc)
    }

    Date(date.getYear, date.getMonthOfYear, date.getDayOfMonth)
  }

  implicit def zoneToJoda(zone: Zone) = zone.joda

  implicit def zoneFromJoda(joda: DateTimeZone) = Zone(joda.getID)
}

object Joda {

  import Conversions._

  // TODO [JJS] PERHAPS MOVE CONVERSIONS IN HERE AND MAKE IMPLICIT?

  def unapply(zone: Zone): Option[DateTimeZone] = Option(zone).map(zoneToJoda) // TODO [JJS] TEST

  def unapply(instant: Instant): Option[DateTime] = Option(instant).map(instantToJoda) // TODO [JJS] TEST

  def unapply(date: Date): Option[LocalDate] = Option(date).map(dateToJoda) // TODO [JJS] TEST
}