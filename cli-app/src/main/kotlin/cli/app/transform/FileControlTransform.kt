package cli.app.transform

import cli.app.model.FileControl

fun lineToFileControl(line: String): FileControl {
    return FileControl(
        recordTypeCode = line.substring(0,1),
        batchCount = line.substring(1,7).toInt(),
        blockCount = line.substring(7,13).toInt(),
        entryCount = line.substring(13,21).toInt(),
        entryHash = line.substring(21,31),
        totalDebit = line.substring(31,43).toInt(),
        totalCredit = line.substring(43,55).toInt()
    )
}
