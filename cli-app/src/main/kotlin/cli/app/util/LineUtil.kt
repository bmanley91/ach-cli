package cli.app.util

fun isBatchHeader(line: String) = line.first() == '5'
fun isBatchDetail(line: String) = line.first() == '6'
fun isBatchControl(line: String) = line.first() == '8'
fun isFileControlLine(line: String) = line.first() == '9'
