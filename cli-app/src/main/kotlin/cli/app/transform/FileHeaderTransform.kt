package cli.app.transform

import cli.app.model.DATE_TIME_FORMAT
import cli.app.model.FileHeader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)

fun lineToFileHeader(line: String): FileHeader {
    val rawFileCreationDate = line.subSequence(23,29).toString()
    val rawRecordSize = line.subSequence(34,37).toString()
    val rawBlockingFactor = line.subSequence(37,39).toString()
    val rawFormatCode = line.subSequence(39,40).toString()
    val rawReferenceCode = line.subSequence(86, 94).toString()

    return FileHeader(
        recordTypeCode = line.subSequence(0,1).toString(),
        priorityCode = line.subSequence(1,3).toString(),
        immediateDestination = line.subSequence(3,13).trim().toString(),
        // Char 13 is skipped on purpose
        immediateOrigin = line.subSequence(14,23).toString(),
        fileCreationDate = LocalDate.parse(rawFileCreationDate, dateTimeFormatter),
        fileCreationTime = line.subSequence(29, 33).toString(),
        fileIdModifier = line.subSequence(33,34).toString(),
        recordSize = rawRecordSize.toInt(),
        blockingFactor = rawBlockingFactor.toInt(),
        formatCode = rawFormatCode.toInt(),
        immediateDestinationName = line.subSequence(40,63).toString().trim(),
        immediateOriginName = line.subSequence(63,86).toString().trim(),
        referenceCode =
            if (rawReferenceCode.trim().isEmpty()) {
                null
            } else {
                rawReferenceCode.trim()
            }
    )
}
