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

case class DateAndTimeOfDay(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int) {
  val date: Date = Date(year, month, day)
  val timeOfDay: TimeOfDay = TimeOfDay(hour, minute, second, millisecond)
}

object DateAndTimeOfDay {
  def apply(date: Date, timeOfDay: TimeOfDay): DateAndTimeOfDay = {
    DateAndTimeOfDay(date.year, date.month, date.day, timeOfDay.hour, timeOfDay.minute, timeOfDay.second, timeOfDay.millisecond)
  }
}