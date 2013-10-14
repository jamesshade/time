package org.shade

import org.joda.time.chrono.ISOChronology

package object time {
  private[time] val isoUtc = ISOChronology.getInstanceUTC
}

