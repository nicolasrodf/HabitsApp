package com.nicolasrf.home_data.extension

import com.nicolasrf.home_data.utils.Constants.ONE_SECOND_IN_MILLIS
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun ZonedDateTime.toStartOfDateTimestamp(): Long {
    return truncatedTo(ChronoUnit.DAYS).toEpochSecond() * ONE_SECOND_IN_MILLIS
}

fun Long.toZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
}

fun ZonedDateTime.toTimeStamp(): Long {
    return this.toInstant().toEpochMilli()
}

fun LocalDate.toZonedDateTime(): ZonedDateTime {
    return this.atStartOfDay(ZoneId.systemDefault())
}

fun LocalTime.toZonedDateTime(): ZonedDateTime {
    return this.atDate(LocalDate.now()).atZone(ZoneId.systemDefault())
}