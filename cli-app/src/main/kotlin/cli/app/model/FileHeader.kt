package cli.app.model

import java.time.LocalDate

data class FileHeader(
    val recordTypeCode: String,
    val priorityCode: String,
    val immediateDestination: String,
    val immediateOrigin: String,
    val fileCreationDate: LocalDate,
    val fileCreationTime: String,
    val fileIdModifier: String,
    val recordSize: Int,
    val blockingFactor: Int,
    val formatCode: Int,
    val immediateDestinationName: String,
    val immediateOriginName: String,
    val referenceCode: String?
)
