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

case class DateAndTime(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int) {

  val date: Date = Date(year, month, day)
  val time: Time = Time(hour, minute, second, millisecond)

  override def toString = date.toString + "T" + time.toString
}

object DateAndTime {
  def apply(date: Date, time: Time): DateAndTime = {
    DateAndTime(date.year, date.month, date.day, time.hour, time.minute, time.second, time.millisecond)
  }
}