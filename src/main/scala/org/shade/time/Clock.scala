package org.shade.time

import org.joda.time.DateTimeZone
import Date.dateOf

trait Clock {

  def now: Time

  final def today(timezone: DateTimeZone) = dateOf(now, timezone)
}

object SystemClock extends Clock {
  override def now = Time(System.currentTimeMillis)
}
