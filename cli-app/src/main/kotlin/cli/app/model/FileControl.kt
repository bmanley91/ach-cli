package cli.app.model

data class FileControl(
    val recordTypeCode: String,
    val batchCount: Int,
    val blockCount: Int,
    val entryCount: Int,
    val entryHash: String,
    val totalDebit: Int,
    val totalCredit: Int
)
