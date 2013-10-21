Nice Time
=========

This library provides a highly simplified set of Date and Time classes for Scala.

It provides a very thin wrapper around Joda Time (http://www.joda.org/joda-time), and relies on Joda Time for handling all the complexity of Date and Time processing.

The intention of the library is two-fold:

1. Provide Scala-friendly Date and Time classes.
2. Reduce chronology and time zone related confusion and bugs.

There are four primary classes:

* Time - An instant in time - similar to a Joda ReadableInstant (e.g. DateTime) or a JDK Date.
* Date - A date in the calendar with no concept of time of day (a day, month and year) - similar to Joda's LocalDate.  There's no direct JDK "pure" date class.
* Zone - A time zone - wraps a Joda DateTimeZone, similar to the JDK's TimeZone class.
* Clock - Something that can provide the current time and date.  This is a trait, with a single concrete implementation "SystemClock".

The first intention, making time-handling Scala friendly is achieved (or at least helped) by:

* Representing Date as a case class containing a year, month and a day
* Representing Time as a case class containing a Long - milliseconds since the Epoch UTC.

The functionality in the classes is intentionally thin.  I'll add functionality as it's needed.  For now easy conversion to Joda and JDK classes is provided.

The second is more tricky:

* Time has no concept of chronology or time zone.  It's an instant in time with a clear definition.  Two times are equal if they represent the same instant (unlike Joda instants, which embed a date time and aren't considered equal even if they represent the same instance - see the differences between equals and isEqual methods in Joda DateTime).

* Date necessarily has to have a chronology to be meaningful.  Date represents a date in the ISO chronology. However it has no concept of time zone.  A date is just that - a representation of a day/month/year date.

* Any conversions or calculations that are timezone dependent require the zone to be provided, and are generally methods on the Zone class (aside from some specific cases - such as the "today" method on Clock).  There is no "default" or "local" timezone to confuse things - the developer must be specific.
