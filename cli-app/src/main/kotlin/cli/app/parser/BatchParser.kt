package cli.app.parser

import cli.app.model.BatchRange
import cli.app.model.error.BatchParsingException

fun getBatchRanges(lines: List<String>): List<BatchRange> {
    // Start at the second line since the first line should be a file header
    var lineIndex = 1
    var inBatch = false
    val batches = mutableListOf<BatchRange>()
    var currentBatchStart = lineIndex

    while (!isFileControlLine(lines[lineIndex]) && lineIndex < lines.size) {
        val currentLine = lines[lineIndex]
        if (isBatchHeader(currentLine)) {
            if (inBatch) throw BatchParsingException("Found batch header without batch control on line $currentBatchStart")

            inBatch = true
            currentBatchStart = lineIndex
        }

        if (isBatchControl(currentLine)) {
            if (!inBatch) throw BatchParsingException("Found batch control without preceding batch header on line $lineIndex")

            inBatch = false
            batches.add(BatchRange(currentBatchStart, lineIndex))
        }
        lineIndex++
    }

    if (inBatch) throw BatchParsingException("Found batch header without batch control on line $currentBatchStart")

    return batches
}

fun isBatchHeader(line: String) = line.first() == '5'
fun isBatchControl(line: String) = line.first() == '8'
fun isFileControlLine(line: String) = line.first() == '9'
