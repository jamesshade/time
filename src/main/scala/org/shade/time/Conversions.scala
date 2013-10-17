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

  implicit def timeToLong(time: Time): Long = time.millis

  implicit def timeFromLong(time: Long): Time = Time(time)

  implicit def timeToJoda(time: Time): DateTime = new DateTime(time.millis, isoUtc)

  implicit def timeFromJoda(time: ReadableInstant): Time = Time(time.getMillis)

  implicit def dateToJoda(date: Date): LocalDate = new LocalDate(date.year, date.month, date.day, isoUtc)

  implicit def dateFromJoda(joda: LocalDate): Date = {

    val date = if (joda.getChronology == isoUtc) joda else {
      val time = new DateTime(joda.toDateTimeAtStartOfDay(DateTimeZone.UTC).plusHours(12).getMillis, isoUtc)
      new LocalDate(time, isoUtc)
    }

    Date(date.getYear, date.getMonthOfYear, date.getDayOfMonth)
  }
}