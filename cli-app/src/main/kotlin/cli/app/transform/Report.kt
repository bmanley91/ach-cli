package cli.app.transform

import cli.app.model.Batch
import cli.app.model.FileControl
import cli.app.model.FileHeader
import cli.app.util.convertCentsToDollars

fun generateReport(fileHeader: FileHeader, batches: List<Batch>, fileControl: FileControl, batchLinesEnabled: Boolean)  {
    val messageStrings = mutableListOf<String>()
    messageStrings.add(generateFileHeaderReport(fileHeader))
    messageStrings.add(generateFileControlReport(fileControl))
    messageStrings.add(generateBatchReport(batches, batchLinesEnabled))

    val report = aggregateReports(messageStrings)
    println(report)
}

private fun generateFileHeaderReport(fileHeader: FileHeader): String {
    return with (fileHeader) {
        """
            File Information
            -------------------
            Destination: $immediateDestinationName ($immediateDestination)
            Origin: $immediateOriginName ($immediateOrigin)
            File created on: $fileCreationDate $fileCreationTime
        """.trimIndent()
    }
}

private fun generateFileControlReport(fileControl: FileControl): String {
    return with (fileControl) {
        """
            File Control Information
            -------------------
            Credit Total: $${convertCentsToDollars(totalCredit)}
            Debit Total: $${convertCentsToDollars(totalDebit)}
            Number of Batches: $batchCount
            Number of Blocks: $blockCount
            Number of Entries: $entryCount
        """.trimIndent()
    }
}

private fun generateBatchReport(batches: List<Batch>, batchLinesEnabled: Boolean): String {
    val batchSummaries: List<String> = batches.map {
        val builder = StringBuilder()
        builder.appendLine(
            """
                Batch Number ${it.batchHeader.batchNumber} Summary
                -------------------
                Credits or Debits? ${headerServiceCodeToMessage(it.batchHeader)}
                Personal or Business? ${headerStandardEntryClassCodeToMessage(it.batchHeader)}
                Company: ${it.batchHeader.companyName} (${it.batchHeader.companyIdentification})
                Description: ${it.batchHeader.companyEntryDescription}
                Company Discretionary Data: ${it.batchHeader.companyDiscretionaryData}
                Company Descriptive Date: ${it.batchHeader.companyDescriptiveDate}
                Effective Entry Date: ${it.batchHeader.effectiveEntryDate}
                Settlement Date: ${it.batchHeader.settlementDate}
                Entry Count: ${it.batchControl.entryCount}
                Credit Total: ${it.batchControl.totalCreditAmount}
                Debit Total: ${it.batchControl.totalDebitAmount}
            """.trimIndent()
        )

        if (batchLinesEnabled) builder.append('\n')
            .appendLine(generateBatchLineReport(it))

        builder.toString()
    }

    return aggregateReports(batchSummaries)
}

private fun generateBatchLineReport(batch: Batch): String {
    val batchLineReports = batch.batchDetailRows.mapIndexed { index, detailRow ->
        """
            Batch ${batch.batchHeader.batchNumber}, Entry $index
            -------------------
            Transaction Code: ${detailRow.transactionCode}
            Amount: ${detailRow.amount}
            RDFI Routing Number: ${detailRow.rdfiIdentification}${detailRow.checkDigit}
            DFI Account Number: ${detailRow.dfiAccountNumber}
            Individual: ${detailRow.individualName} (${detailRow.individualIdentificationNumber})
            Trace Number: ${detailRow.traceNumber}
        """.trimIndent()
    }

    return aggregateReports(batchLineReports)
}

private fun aggregateReports(reports: List<String>): String {
    val builder = StringBuilder()
    reports.forEach {
        builder.appendLine(it)
            .append('\n')
    }

    return builder.toString()
}
