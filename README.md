# Simple Time for Scala

[ ![Download](https://api.bintray.com/packages/jamesshade/releases/time/images/download.svg) ](https://bintray.com/jamesshade/releases/time/_latestVersion)

This library provides a simplified set of Date and Time classes for Scala.

It provides a very thin layer on top of the Java 8 time library (https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html#package.description), and relies on the JDK classes for 
handling all the complexity of Date and Time processing.

__Note that this branch (jdk1.8) is an experimental work in progress, and is a simple port of the master Joda version to use the new Java 8 classes.__

__It's early days with this and the library may change significantly before this Java 8 version is released.__

The intention of the library is two-fold:

1. Provide Scala-friendly Date and Time classes.

2. Reduce chronology and time zone related confusion and bugs.

## Contents

* [Primary Classes](#primary-classes)
* [Intention 1 - Scala Friendly Time Handling](#intention-1---scala-friendly-time-handling)
* [Intention 2 - Reducing time zone and chronology related confusion](#intention-2---reducing-time-zone-and-chronology-related-confusion)


## Primary Classes

* Instant - An instant in time - similar to a JDK Instant.
  
* Date - A date in the calendar with no concept of time of day or time zone (i.e. it is a day, a month and a year) -
  similar to the JDK LocalDate class.
  
* Time - A time of day with no concept of date or time zone (i.e. it is an hour, minute, second and millisecond) -
  similar to the JDK LocalTime class.
  
* DateAndTime - A combination of Date and Time into a single case class - similar to the JDK LocalDateTime class.

* Zone - A time zone - wraps a JDK ZoneId.

* Clock - Something that can provide the current time and date.  This is a trait, with a single concrete 
  implementation "SystemClock".  Similar to a JDK Clock.

* Duration - A length of time, wraps a Long representing a number of milliseconds - similar to a JDK Duration.  Note
  that for the purposes of time arithmetic, Durations are directed (i.e. a Duration can be negative).

## Intention 1 - Scala Friendly Time Handling

This is supported by:

* Representing Instant as a case class containing a Long - milliseconds since the Epoch UTC.

* Representing partial classes (e.g. Date) as case classes containing simple integer values.

* Avoiding cluttering these classes with unnecessary concerns.  For example, conversion to JDK classes is provided in a 
  separate JavaConversions object.

* Having a standard "equals" method that does the right thing - i.e. if two objects represent the same thing, they 
  are equal (no Joda-style "equals" and "isEqual" methods that return different values).

## Intention 2 - Reducing time zone and chronology related confusion

This is more tricky.  But some points are:

* Instant has no concept of chronology or time zone.  Two Instants are equal if they refer to the same point in time
  (unlike Joda instants, which embed a chronology and time zone and have an equals method that often returns "false" 
  even if they represent the same point in time - see the differences between equals and isEqual methods
  in Joda DateTime).  The only place in this class that time zone or chronology are used is in the "toString"
  method (which displays an ISO8601 version of the Time in the ISO chronology with UTC time zone).

* Date has no concept of time zone.  A date is just that - a representation of a day/month/year unattached to any 
  particular location.  Note that the concept of a "date" has no meaning without chronology - in our case we use
  the ISO chronology to validate the values in the Date upon construction.

* Time also has no concept of time zone.

* Any conversions or calculations that are time zone dependent require the zone to be provided and are generally 
  methods on the Zone class.  There is no "default" or "local" time zone to confuse things - the developer must 
  be specific.

