package cli.app.model

data class BatchControl (
    val recordTypeCode: String,
    val serviceCode: String,
    val entryCount: Int,
    val entryHash: String,
    val totalDebitAmount: Int,
    val totalCreditAmount: Int,
    val companyIdentification: String,
    val messageAuthenticationCode: String?,
    val reserved: String?,
    val odfiIdentification: String,
    val batchNumber: Int
)
