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

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.chrono.ISOChronology

case class Time(millis: Long) {
  override lazy val toString = ISODateTimeFormat.dateTime.print(new DateTime(millis, isoUtc))
}

object Time {

  def apply(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int, zone: Zone): Time = {
    try {
      apply(new DateTime(year, month, day, hour, minute, second, millisecond, ISOChronology.getInstance(zone.joda)).getMillis)
    } catch {
      case cause: Throwable => throw new InvalidTimeException(year, month, day, hour, minute, second, millisecond, zone, cause)
    }
  }
}

case class InvalidTimeException(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int, zone: Zone, cause: Throwable)
  extends RuntimeException(s"Invalid time: [(Year: $year) (Month: $month) (Day: $day) " +
    s"(Hour: $hour) (Minute: $minute) (Second: $second) (Millisecond: $millisecond) (Zone: $zone)]: " + cause.getMessage, cause)

