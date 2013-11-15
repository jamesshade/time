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

// TODO [JJS] TEST
// TODO [JJS] SHOULD THIS ALLOW NEGATIVE DURATIONS?

case class Duration(millis: Long) {

  override lazy val toString = millis + "ms"

  def < (other: Duration) = millis < other.millis

  def <= (other: Duration) = millis <= other.millis

  def >= (other: Duration) = millis >= other.millis

  def > (other: Duration) = millis > other.millis

  def + (other: Duration) = Duration(millis + other.millis)

  def - (other: Duration) = Duration(millis - other.millis)
}

object Duration {

  val aWeek = days(7)
  val aDay = hours(24)
  val anHour = minutes(60)
  val aMinute = seconds(60)
  val aSecond = milliseconds(1000)
  val aMillisecond = Duration(1L)

  def weeks(n: Long) = Duration(n * aWeek.millis)
  def days(n: Long) = Duration(n * aDay.millis)
  def hours(n: Long) = Duration(n * anHour.millis)
  def minutes(n: Long) = Duration(n * aMinute.millis)
  def seconds(n: Long) = Duration(n * aSecond.millis)
  def milliseconds(n: Long) = Duration(n)

  def apply(weeks: Long = 0L, days: Long = 0L, hours: Long = 0L, minutes: Long = 0L, seconds: Long = 0L, milliseconds: Long = 0L): Duration =
    this.weeks(weeks) +
    this.days(days) +
    this.hours(hours) +
    this.minutes(minutes) +
    this.seconds(seconds) +
    this.milliseconds(milliseconds)
}