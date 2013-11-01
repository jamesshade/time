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

case class Time(hour: Int, minute: Int, second: Int, millisecond: Int) {

  if (hour < 0 || hour >= 24 ||  minute < 0 || minute >= 60 ||
    second < 0 || second >= 60 || millisecond < 0 || millisecond >= 1000) {
    throw InvalidTimeException(hour, minute, second, millisecond)
  }

  override lazy val toString = "%02d:%02d:%02d.%03d".format(hour, minute, second, millisecond)
}

case class InvalidTimeException(hour: Int, minute: Int, second: Int, millisecond: Int)
  extends TimeException(s"Invalid time of day: (Hour: $hour) (Minute: $minute) (Second: $second) (Millisecond: $millisecond)")
