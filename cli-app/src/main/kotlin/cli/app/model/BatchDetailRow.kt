package cli.app.model

data class BatchDetailRow(
    val recordTypeCode: String,
    val transactionCode: String,
    val rdfiIdentification: String,
    val checkDigit: String,
    val dfiAccountNumber: String,
    val amount: Int,
    val individualIdentificationNumber: String,
    val individualName: String,
    val discretionaryData: String,
    val addendaRecordIndicator: String,
    val traceNumber: String
)
