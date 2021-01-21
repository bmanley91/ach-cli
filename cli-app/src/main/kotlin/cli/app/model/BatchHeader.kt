package cli.app.model

import java.time.LocalDate

data class BatchHeader (
    val recordTypeCode: String,
    val serviceCode: String,
    val companyName: String,
    val companyDiscretionaryData: String,
    val companyIdentification: String,
    val standardEntryClassCode: String,
    val companyEntryDescription: String,
    val companyDescriptiveDate: LocalDate,
    val effectiveEntryDate: LocalDate,
    val settlementDate: LocalDate,
    val originatorStatusCode: String,
    val odfiIdentification: String,
    val batchNumber: Int
)
