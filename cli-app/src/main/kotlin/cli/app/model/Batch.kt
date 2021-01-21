package cli.app.model

data class Batch(
    val batchHeader: BatchHeader,
    val batchDetailRows: List<BatchDetailRow>,
    val batchControl: BatchControl
)
