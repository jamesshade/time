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

case class Instant(millis: Long) {
  override lazy val toString = ISODateTimeFormat.dateTime.print(new DateTime(millis, isoUtc))

  def isBefore(instant: Instant) = millis < instant.millis
  val < = isBefore _

  def isAtOrBefore(instant: Instant) = millis <= instant.millis
  val <= = isAtOrBefore _

  def isAt(instant: Instant) = millis == instant.millis

  def isAtOrAfter(instant: Instant) = millis >= instant.millis
  val >= = isAtOrAfter _

  def isAfter(instant: Instant) = millis > instant.millis
  val > = isAfter _

  // TODO [JJS] Instant should be pure - it should know nothing of durations (or should it) - why should a duration know about instants?
  def timeSince(instant: Instant) = Duration(millis - instant.millis)
  def - = timeSince _

}
