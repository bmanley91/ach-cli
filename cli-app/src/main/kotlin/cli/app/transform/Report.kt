package cli.app.transform

import cli.app.model.Batch
import cli.app.model.FileControl
import cli.app.model.FileHeader
import cli.app.util.convertCentsToDollars

fun generateReport(fileHeader: FileHeader, batches: List<Batch>, fileControl: FileControl)  {
    val headerReport = generateFileHeaderReport(fileHeader)
    val batchReport = generateBatchReport(batches)
    val fileControlReport = generateFileControlReport(fileControl)

    val reports = aggregateReports(headerReport, batchReport, fileControlReport)
    println(reports)
}

fun generateFileHeaderReport(fileHeader: FileHeader): String {
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

fun generateBatchReport(batches: List<Batch>): String {
    return """
        Batch Information
        -------------------
        foo!
    """.trimIndent()
}

fun generateFileControlReport(fileControl: FileControl): String {
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

fun aggregateReports(headerReport: String, batchReport: String, controlReport: String): String =
    StringBuilder()
        .appendLine(headerReport)
        .append('\n')
        .appendLine(batchReport)
        .append('\n')
        .appendLine(controlReport)
        .toString()
