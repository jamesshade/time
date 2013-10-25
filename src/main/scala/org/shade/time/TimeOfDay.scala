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

 // TODO [JJS] TEST TIME OF DAY

case class TimeOfDay(hour: Int, minute: Int, second: Int, millisecond: Int) {

  (Option(hour), Option(minute), Option(second), Option(millisecond)) match {
    case (Some(h), Some(m), Some(s), Some(ms))
      if h >= 0 && h < 24 && m >= 0 && m < 60 && s >= 0 && s < 60 && ms >= 0 && ms < 1000 => // Okay
    case _ => throw InvalidTimeOfDayException(hour, minute, second, millisecond)
  }

  override lazy val toString = "%02d:%02d:%02d.%03d".format(hour, minute, second, millisecond)
}

case class InvalidTimeOfDayException(hour: Int, minute: Int, second: Int, millisecond: Int)
  extends RuntimeException(s"Invalid time of day: (Hour: $hour) (Minute: $minute) (Second: $second) (Millisecond: $millisecond)")
