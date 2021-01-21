package cli.app

import cli.app.model.FailureReason

fun validateFile(lines: List<String>): List<FailureReason> {
    val failureReasons = mutableListOf<FailureReason>()

    if (lines.isEmpty()) {
        failureReasons.add(FailureReason.FILE_EMPTY)
    }
    if (lines.size % 10 != 0) {
        failureReasons.add(FailureReason.FILE_LINE_COUNT)
    }

    return failureReasons
}
