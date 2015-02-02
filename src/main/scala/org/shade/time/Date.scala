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

import org.joda.time.LocalDate

case class Date(year: Int, month: Int, day: Int) {

  try {
    new LocalDate(year, month, day, IsoUtc)
  } catch {
    case e: Exception => throw InvalidDateException(year, month, day, e)
  }

  override lazy val toString = "%04d-%02d-%02d".format(year, month, day)
}

case class InvalidDateException(year: Int, month: Int, day: Int, cause: Throwable)
  extends TimeException(s"Invalid ISO date: (Year: $year) (Month: $month) (Day: $day): ${cause.getMessage}", cause)
