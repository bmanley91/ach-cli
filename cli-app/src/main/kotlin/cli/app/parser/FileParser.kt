package cli.app.parser

import cli.app.model.FailureReason
import cli.app.model.FileHeader
import cli.app.transform.lineToFileHeader
import cli.app.util.isFileControlLine
import cli.app.validateFile
import cli.app.validation.validateFileHeader
import java.io.File

fun processFile(fileName: String) {
    val lines = readFile(fileName)
    val validationFailures = validateFile(lines)
    if (validationFailures.isNotEmpty()) {
        failProcessing(validationFailures)
    }

    val fileHeader = parseFileHeader(lines.first())
    println(fileHeader)

    val batches = processBatches(lines)
    println(batches)

    val fileControl = parseFileControlLine(lines.first {
        isFileControlLine(it)
    })
    println(fileControl)
}

fun readFile(fileName: String): List<String> = File(fileName).readLines()

fun parseFileHeader(line: String): FileHeader {
    val validationFailures = validateFileHeader(line)
    if (validationFailures.isNotEmpty()) {
        failProcessing(validationFailures)
    }

    return lineToFileHeader(line)
}

fun failProcessing(reasons: List<FailureReason>) {
    throw RuntimeException("File processing failed:\n $reasons")
}
