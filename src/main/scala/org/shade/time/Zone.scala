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

import org.joda.time.DateTimeZone

// TODO [JJS] TEST ZONE CLASS & OBJECT

final class Zone(val joda: DateTimeZone) {

  lazy val id = joda.getID

  override lazy val toString: String = id

  override def equals(other: Any) = other match {
    case Zone(otherId: String) if otherId == id => true
    case _ => false
  }

  override lazy val hashCode: Int = id.hashCode
}

object Zone {

  def apply(id: String): Zone = try {
    new Zone(DateTimeZone.forID(id))
  } catch {
    case e: IllegalArgumentException => throw new InvalidTimezoneException(id, e.getMessage)
  }

  val utc = Zone("UTC")

  def unapply(zone: Zone): Option[String] = Option(zone).map(_.id)
}

case class InvalidTimezoneException(id: String, message: String) extends RuntimeException(s"Invalid timezone '$id': $message")