package org.shade.time

trait Period {
  def addTo(instant: Instant): Instant
}

case class Years(number: Long) extends Period

case class Months(number: Long) extends Period

case class Days(number: Long) extends Period

case class Hours(number: Long) extends Period

case class Minutes(number: Long) extends Period

case class Seconds(number: Long) extends Period

case class Milliseconds(number: Long) extends Period
