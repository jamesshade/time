Simple Time
===========

This library provides a highly simplified set of Date and Time classes for Scala.

It provides a very thin wrapper around Joda Time (http://www.joda.org/joda-time), and relies on Joda Time for 
handling all the complexity of Date and Time processing.

The intention of the library is two-fold:

1. Provide Scala-friendly Date and Time classes.

2. Reduce chronology and time zone related confusion and bugs.

The primary classes are:

* Instant - An instant in time - similar to a Joda ReadableInstant and its subclasses (e.g. DateTime) or a JDK Date.  
  This has no concept of chronology or time zone.
  
* Date - A date in the calendar with no concept of time of day (a day, month and year) - similar to Joda's LocalDate.
  There's no direct JDK "pure" date class.
  
* Time - A time of day (with no concept of date or time zone) - similar to Joda's LocalTime.  There's no direct JDK 
  "pure" time-of-day class.
  
* DateAndTime - A combination of Date and Time into a single case class - similar to Joda's LocalDateTime.

* Zone - A time zone - wraps a Joda DateTimeZone, similar to the JDK's TimeZone class.

* Clock - Something that can provide the current time and date.  This is a trait, with a single concrete 
  implementation "SystemClock".

The first intention, making time-handling Scala friendly is achieved (or at least helped) by:

* Representing Instant as a case class containing a Long - milliseconds since the Epoch UTC.

* Representing partial classes (e.g. Date) as case classes containing simple integer values.

* Avoiding cluttering these classes with unnecessary concerns.  For example, conversion to Joda is provided in a 
  separate Conversions object (JDK conversions perhaps to follow).

* Having a standard "equals" method that does the right thing - i.e. if two objects represent the same thing, they 
  are equal (no Joda-style "equals" and "isEqual" methods that return different values).

The second intention is more tricky, but some points are:

* Instant has no concept of chronology or time zone.  Two Instants are equal if they refer to the same point in time
  (unlike Joda instants, which embed a chronology and time zone and have an equals method that often returns "false" 
  even if they represent the same instant - see the differences between equals and isEqual methods 
  in Joda DateTime).  The only place we assume anything about chronology or time zone is in the "toString" 
  method (which displays an ISO8601 version of the Time in the ISO chronology with UTC time zone).

* Date has no concept of time zone.  A date is just that - a representation of a day/month/year unattached to any 
  particular location (unfortunately the concept of a "date" has no meaning without chronology - in our case we use 
  the ISO chronology to validate the values in the Date upon construction).

* Any conversions or calculations that are time zone dependent require the zone to be provided and are generally 
  methods on the Zone class.  There is no "default" or "local" time zone to confuse things - the developer must 
  be specific.
