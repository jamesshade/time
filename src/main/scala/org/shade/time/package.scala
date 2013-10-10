package org.shade

import org.joda.time.chrono.GregorianChronology

package object time {

  private[time] val gregorianUtc = GregorianChronology.getInstanceUTC
}
