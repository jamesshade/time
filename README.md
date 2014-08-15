Nice Time
=========

This library provides a highly simplified set of Date and Time classes for Scala.

It provides a very thin wrapper around Joda Time (http://www.joda.org/joda-time), and relies on Joda Time for handling all the complexity of Date and Time processing.

The intention of the library is two-fold:

1. Provide Scala-friendly Date and Time classes.
2. Reduce chronology and time zone related confusion and bugs.

There are four primary classes:

* Instant - An instant in time - similar to a Joda ReadableInstant and its subclasses (e.g. DateTime) or a JDK Date.  This has no concept of chronology or time zone.
* Date - A date in the calendar with no concept of time of day (a day, month and year) - similar to Joda's LocalDate.  There's no direct JDK "pure" date class.
* Time - A time of day (with no concept of date or timezone).  There's no direct JDK or Joda "pure" time-of-day class.
* DateAndTime - A combination of Date and Time into a single case class.  Similar to Joda's LocalDate.
* Zone - A time zone - wraps a Joda DateTimeZone, similar to the JDK's TimeZone class.
* Clock - Something that can provide the current time and date.  This is a trait, with a single concrete implementation "SystemClock".

The first intention, making time-handling Scala friendly is achieved (or at least helped) by:

* Representing Instant as a case class containing a Long - milliseconds since the Epoch UTC.
* Representing Date as a case class containing a year, month and a day.
* Avoid cluttering these classes with unnecessary concerns.  For example, conversion to Joda is provided in a separate Conversions object (JDK conversions perhaps to follow).

The second intention is more tricky, but some points are:

* Instant has no concept of chronology or time zone.  It's an instant in time with a clear definition.  Two times are equal if they represent the same instant (unlike Joda instants, which embed a timezone and aren't considered equal even if they represent the same instant - see the differences between equals and isEqual methods in Joda DateTime).  The only place we assume anything about chronology or timezone is in the "toString" method (with displays an ISO8601 version of the Time in the ISO chronology with UTC timezone).

* Date has no concept of time zone.  A date is just that - a representation of a day/month/year date in the ISO chronology unattached to any particular location (unfortunately the concept of a "date" has no meaning without chronology - in our case we use the chronology to validate the values in the Date upon construction).

* Any conversions or calculations that are timezone dependent require the zone to be provided, and are generally methods on the Zone class (aside from some specific cases - such as the "today" method on Clock).  There is no "default" or "local" timezone to confuse things - the developer must be specific.
