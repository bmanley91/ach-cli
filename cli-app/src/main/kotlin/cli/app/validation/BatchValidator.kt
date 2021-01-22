package cli.app.validation

import cli.app.model.BatchRange
import cli.app.model.FailureReason

fun validateBatch(lines: List<String>, batchRange: BatchRange): List<FailureReason> {
    val failureReasons = mutableListOf<FailureReason>()

    failureReasons.addAll(
        validateBatchHeader(lines[batchRange.startIndex])
    )
    failureReasons.addAll(
        validateBatchDetailRows(lines, batchRange.startIndex + 1, batchRange.endIndex - 1)
    )
    failureReasons.addAll(
        validateBatchControlRow(lines[batchRange.endIndex])
    )

    return failureReasons
}

fun validateBatchHeader(line: String): List<FailureReason> {
    return listOf<FailureReason>()
}

fun validateBatchDetailRows(lines: List<String>, start: Int, end: Int): List<FailureReason> {
    return listOf<FailureReason>()
}

fun validateBatchControlRow(line: String): List<FailureReason> {
    return listOf<FailureReason>()
}
