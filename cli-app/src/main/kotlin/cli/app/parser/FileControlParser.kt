package cli.app.parser

import cli.app.model.FileControl
import cli.app.transform.lineToFileControl
import cli.app.validation.validateFileControl

fun parseFileControlLine(line: String): FileControl {
    validateFileControl(line)
    return lineToFileControl(line)
}
