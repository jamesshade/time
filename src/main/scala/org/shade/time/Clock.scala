package org.shade.time

import Date.dateOf

trait Clock {

  def now: Time

  final def today(zone: Zone) = dateOf(now, zone)
}

object SystemClock extends Clock {
  override def now = Time(System.currentTimeMillis)
}
