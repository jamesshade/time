package org.shade.time

import org.joda.time.DateTimeZone
import java.util.TimeZone

// TODO [JJS] TEST ZONE CLASS & OBJECT

final class Zone(val joda: DateTimeZone) {

  lazy val id = joda.getID

  lazy val jdk = joda.toTimeZone // TODO [JJS] Review: This is risky - defaults to GMT if the Joda zone ID isn't recognised by the JDK
    // Can we assert that the returned timezone has the same properties as we expect?

  override lazy val toString: String = id

  override def equals(other: Any) = other match {
    case Zone(otherId: String) if otherId == id => true
    case _ => false
  }

  override lazy val hashCode: Int = id.hashCode
}

object Zone {

  def apply(joda: DateTimeZone) = new Zone(joda)

  def apply(id: String): Zone = try {
    new Zone(DateTimeZone.forID(id))
  } catch {
    case e: IllegalArgumentException => throw new InvalidTimezoneException(id, e.getMessage)
  }

  def apply(jdk: TimeZone): Zone = Zone(jdk.getID)

  val utc = Zone(DateTimeZone.UTC)

  // TODO [JJS] REVIEW UNAPPLY METHODS

  def unapply(zone: Zone): Option[String] = Option(zone).map(_.id)

//  def unapply(zone: Zone): Option[DateTimeZone] = Option(zone).map(_.joda)
//
//  def unapply(zone: Zone): Option[TimeZone] = Some(zone.jdk)
}

case class InvalidTimezoneException(id: String, message: String) extends RuntimeException(s"Invalid timezone '$id': $message")