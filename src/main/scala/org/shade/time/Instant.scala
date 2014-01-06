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

case class Instant(millis: Long) extends Comparable[Instant] {

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


  def timeUntil(futureInstant: Instant) = Duration(futureInstant.millis - millis)
  def timeSince(pastInstant: Instant) = Duration(millis - pastInstant.millis)
  def timeBetween(otherInstant: Instant) = timeSince(otherInstant).abs

  def - (duration: Duration) = Instant(millis - duration.millis)

  def + (duration: Duration) = Instant(millis + duration.millis)

  override def compareTo(o: Instant): Int = millis.compareTo(o.millis)
}
