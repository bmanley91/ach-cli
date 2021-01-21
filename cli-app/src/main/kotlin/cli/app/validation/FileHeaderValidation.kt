package cli.app.validation

import cli.app.model.FailureReason

fun validateFileHeader(line: String): List<FailureReason> {
    val failureReasons = mutableListOf<FailureReason>()
    if (line[0] != '1') {
        failureReasons.add(FailureReason.FILE_HEADER_RECORD_TYPE_CODE)
    }
    if (line.subSequence(1, 3) != ("01")) {
        failureReasons.add(FailureReason.FILE_HEADER_PRIORITY_CODE)
    }

    return failureReasons
}
