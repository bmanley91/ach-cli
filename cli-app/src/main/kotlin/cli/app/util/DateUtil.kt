package cli.app.util

import cli.app.model.STANDARD_DATE_TIME_FORMAT
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun dateFromStandardFormat(input: String): LocalDate =
    LocalDate.parse(input,  DateTimeFormatter.ofPattern(STANDARD_DATE_TIME_FORMAT))

// TODO: Fix year detection bug
fun dateFromJulianFormat(input: String): LocalDate =
    LocalDate.ofYearDay(LocalDate.now().year, input.toInt())
