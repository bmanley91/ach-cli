package cli.app.util

import cli.app.model.BatchControl
import cli.app.model.BatchDetailRow
import cli.app.model.BatchHeader
import cli.app.model.FileControl
import cli.app.model.FileHeader
import java.time.LocalDate

class FileHeaderBuilder {
    val recordTypeCode = "1"
    val priorityCode = "01"
    val immediateDestination = random10DigitString()
    val immediateOrigin = random10DigitString()
    val fileCreationDate = LocalDate.now()
    val fileCreationTime = "12:00"
    val fileIdModifier = "A"
    val recordSize = 94
    val blockingFactor = 10
    val formatCode = 1
    val immediateDestinationName = "Big Bank"
    val immediateOriginName = "My Bank"
    val referenceCode = "ref"

    fun build() = FileHeader(
        recordTypeCode,
        priorityCode,
        immediateDestination,
        immediateOrigin,
        fileCreationDate,
        fileCreationTime,
        fileIdModifier,
        recordSize,
        blockingFactor,
        formatCode,
        immediateDestinationName,
        immediateOriginName,
        referenceCode
    )
}

class FileControlBuilder {
    val recordTypeCode = "9"
    val batchCount = 1
    val blockCount = 1
    val entryCount = 1
    val entryHash = random10DigitString()
    val totalDebit = 12345
    val totalCredit = 98765

    fun build() = FileControl(
        recordTypeCode,
        batchCount,
        blockCount,
        entryCount,
        entryHash,
        totalDebit,
        totalCredit
    )
}

class BatchHeaderBuilder {
    val recordTypeCode = "5"
    val serviceCode = "220"
    val companyName = "My Company"
    val companyIdentification = random10DigitString()
    val standardEntryClassCode = "PPD"
    val companyEntryDescription = "tran"
    val companyDescriptiveDate = LocalDate.now()
    val effectiveEntryDate = LocalDate.now()
    val settlementDate = LocalDate.now()
    val originatorStatusCode = "1"
    val odfiIdentification = random10DigitString()
    val batchNumber = 0

    fun build() = BatchHeader(
        recordTypeCode,
        serviceCode,
        companyName,
        companyIdentification,
        companyIdentification,
        standardEntryClassCode,
        companyEntryDescription,
        companyDescriptiveDate,
        effectiveEntryDate,
        settlementDate,
        originatorStatusCode,
        odfiIdentification,
        batchNumber
    )
}

class BatchControlBuilder {
    val recordTypeCode = "8"
    val serviceCode = "220"
    val entryCount = 1
    val entryHash = random10DigitString()
    val totalDebitAmount = 12345
    val totalCreditAmount = 98765
    val companyIdentification = random10DigitString()
    val messageAuthenticationCode = null
    val reserved = null
    val odfiIdentification = random10DigitString()
    val batchNumber = 0

    fun build() = BatchControl(
        recordTypeCode,
        serviceCode,
        entryCount,
        entryHash,
        totalDebitAmount,
        totalCreditAmount,
        companyIdentification,
        messageAuthenticationCode,
        reserved,
        odfiIdentification,
        batchNumber
    )
}

class BatchDetailRowBuilder {
    val recordTypeCode = "6"
    val transactionCode = "22"
    val rdfiIdentification = random10DigitString()
    val checkDigit = "1"
    val dfiAccountNumber = random10DigitString()
    val amount = 123
    val individualIdentificationNumber = random10DigitString()
    val individualName = "Bob"
    val discretionaryData = ""
    val addendaRecordIndicator = "0"
    val traceNumber = "abc123"

    fun build() = BatchDetailRow(
        recordTypeCode,
        transactionCode,
        rdfiIdentification,
        checkDigit,
        dfiAccountNumber,
        amount,
        individualIdentificationNumber,
        individualName,
        discretionaryData,
        addendaRecordIndicator,
        traceNumber
    )
}

fun random10DigitString() = (1000000000..9999999999).random().toString()

fun buildFileHeader(
    block: FileHeaderBuilder.() -> Unit = {}
) = FileHeaderBuilder().apply(block).build()

fun buildFileControl(
    block: FileControlBuilder.() -> Unit = {}
) = FileControlBuilder().apply(block).build()

fun buildBatchHeader(
    block: BatchHeaderBuilder.() -> Unit = {}
) = BatchHeaderBuilder().apply(block).build()

fun buildBatchControl(
    block: BatchControlBuilder.() -> Unit = {}
) = BatchControlBuilder().apply(block).build()

fun buildBatchDetailRow(
    block: BatchDetailRowBuilder.() -> Unit = {}
) = BatchDetailRowBuilder().apply(block).build()
