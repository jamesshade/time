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

import org.joda.time._

object Conversions {

  implicit def instantToLong(instant: Instant): Long = instant.millis

  implicit def instantFromLong(instant: Long): Instant = Instant(instant)

  implicit def instantToJoda(instant: Instant): DateTime = new DateTime(instant.millis, IsoUtc)

  implicit def instantFromJoda(instant: ReadableInstant): Instant = Instant(instant.getMillis)

  implicit def dateToJoda(date: Date): LocalDate = new LocalDate(date.year, date.month, date.day, IsoUtc)

  implicit def dateFromJoda(joda: LocalDate): Date = {

    val date = if (joda.getChronology == IsoUtc) joda else {
      val jodaDateTime = new DateTime(joda.toDateTimeAtStartOfDay(DateTimeZone.UTC).plusHours(12).getMillis, IsoUtc)
      new LocalDate(jodaDateTime, IsoUtc)
    }

    Date(date.getYear, date.getMonthOfYear, date.getDayOfMonth)
  }

  implicit def zoneToJoda(zone: Zone): DateTimeZone = zone.joda

  implicit def zoneFromJoda(joda: DateTimeZone): Zone = Zone(joda.getID)
}
