package cli.app.transform

import cli.app.model.Batch
import cli.app.model.BatchControl
import cli.app.model.BatchDetailRow
import cli.app.model.BatchHeader
import cli.app.model.BatchRange
import cli.app.util.dateFromJulianFormat
import cli.app.util.dateFromStandardFormat

fun getBatchForRange(lines: List<String>, batchRange: BatchRange): Batch {
    val header = lineToBatchHeader(lines[batchRange.startIndex])
    val control = lineToBatchControl(lines[batchRange.endIndex])

    val batchDetailLines = lines.subList(batchRange.startIndex + 1, batchRange.endIndex)
    val batchDetailRows = batchDetailLines.map {
        lineToBatchDetail(it)
    }

    return Batch(
        batchHeader = header,
        batchDetailRows = batchDetailRows,
        batchControl = control
    )
}

fun lineToBatchHeader(line: String): BatchHeader {
    val companyDiscretionaryData = line.substring(20,40).trim()
    val rawCompanyDescriptiveDate = line.substring(63,69)
    val rawEffectiveEntryDate = line.substring(69, 75)

    return BatchHeader(
        recordTypeCode = line.substring(0,1),
        serviceCode = line.substring(1,4),
        companyName = line.substring(4,20).trim(),
        companyDiscretionaryData =
            if (companyDiscretionaryData.isEmpty()) null
            else companyDiscretionaryData,
        companyIdentification = line.substring(40,50),
        standardEntryClassCode = line.substring(50,53),
        companyEntryDescription = line.substring(53,63).trim(),
        companyDescriptiveDate = dateFromStandardFormat(rawCompanyDescriptiveDate),
        effectiveEntryDate = dateFromStandardFormat(rawEffectiveEntryDate),
        settlementDate = dateFromJulianFormat(line.substring(75,78)),
        originatorStatusCode = line.substring(78,79),
        odfiIdentification = line.substring(79,87),
        batchNumber = line.substring(87,94).toInt()
    )
}

fun lineToBatchControl(line: String): BatchControl {
    val messageAuthenticationCode = line.substring(54,73).trim()
    val reserved = line.substring(73,79).trim()

    return BatchControl(
        recordTypeCode = line.substring(0,1),
        serviceCode = line.substring(1,4),
        entryCount = line.substring(4,10).toInt(),
        entryHash = line.substring(10,20),
        totalDebitAmount = line.substring(20,32).trim().toInt(),
        totalCreditAmount = line.substring(32,44).trim().toInt(),
        companyIdentification = line.substring(44,54),
        messageAuthenticationCode =
            if (messageAuthenticationCode.isEmpty()) null
            else messageAuthenticationCode,
        reserved =
            if (reserved.isEmpty()) null
            else reserved,
        odfiIdentification = line.substring(79,87),
        batchNumber = line.substring(87,94).toInt()
    )
}

fun lineToBatchDetail(line: String): BatchDetailRow {
    return BatchDetailRow(
        recordTypeCode = line.substring(0,1),
        transactionCode = line.substring(1,3),
        rdfiIdentification = line.substring(3,11),
        checkDigit = line.substring(11,12),
        dfiAccountNumber = line.substring(12,29).trim(),
        amount = line.substring(29,39).trim().toInt(),
        individualIdentificationNumber = line.substring(39,54).trim(),
        individualName = line.substring(54,76).trim(),
        discretionaryData = line.substring(76,78),
        addendaRecordIndicator = line.substring(78,79),
        traceNumber = line.substring(79,94)
    )
}

fun headerServiceCodeToMessage(header: BatchHeader): String =
    if (header.serviceCode == "220") "Credits"
    else "Debits"

fun headerStandardEntryClassCodeToMessage(header: BatchHeader): String =
    if (header.serviceCode == "PPD") "Personal"
    else "Business"
