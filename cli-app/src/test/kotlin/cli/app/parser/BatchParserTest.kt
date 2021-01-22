package cli.app.parser

import cli.app.model.BatchControl
import cli.app.model.BatchDetailRow
import cli.app.model.BatchHeader
import cli.app.model.BatchRange
import cli.app.model.STANDARD_DATE_TIME_FORMAT
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.Test

class BatchParserTest {

    val validLines = listOf(
        "101 081000032 0180362811503042207A094101Some Bank              Your Company Inc       #A000001",
        "5220Your Company Inc                    0018036281WEBTrnsNickna1812201503050011081000030000000",
        "6220810002105654221          0000002499RAj##765kn4    Adam Something        A10081000030000002",
        "6220810002105654221          0000001000RAj##3j43kj4   James Bond            A10081000030000003",
        "822000000200324000840000000000000000000093200018036281                         081000030000000",
        "5220Your Company Inc                    0018036281WEBTrnsNickna1812201503160011081000030000001",
        "6220810002105654221          0000017500RAj##8k765j4k32Luke Skywalker        A10081000030000004",
        "822000000100081000210000000000000000000175000018036281                         081000030000001",
        "5225Your Company Inc                    0018036281PPDTrnsNickna1812201503060011081000030000002",
        "627101000019923698412584     0000015000RAj##765432hj  Jane Doe              A10081000030000005",
        "822500000100101000010000000150000000000000000018036281                         081000030000002",
        "9000003000002000000060050600106000000015000000000026820                                       ",
        "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
        "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
        "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
        "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
        "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
        "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"
    )

    @Test
    fun `should return a list of batch ranges`() {
        val batchRanges = getBatchRanges(validLines)

        assertThat(batchRanges).containsExactlyElementsOf(listOf(
            BatchRange(1, 6),
            BatchRange(7, 9),
            BatchRange(10, 12)
        ))
    }

    @Test
    fun `should throw an exception if a batch header is found in an unexpected row`() {
        val lines = listOf(
            "101 081000032 0180362811503042207A094101Some Bank              Your Company Inc       #A000001",
            "5220Your Company Inc                    0018036281WEBTrnsNickna1812201503050011081000030000000",
            "622081000210123456789012345670000003521RAj##23920rjf31John Doe              A10081000030000000",
            "6220810002105654221          0000002300RAj##32b1kn1bb3Bob Dole              A10081000030000001",
            "6220810002105654221          0000002499RAj##765kn4    Adam Something        A10081000030000002",
            "6220810002105654221          0000001000RAj##3j43kj4   James Bond            A10081000030000003",
            "822000000400324000840000000000000000000093200018036281                         081000030000000",
            "5220Your Company Inc                    0018036281WEBTrnsNickna1812201503160011081000030000001",
            "6220810002105654221          0000017500RAj##8k765j4k32Luke Skywalker        A10081000030000004",
            "522000000100081000210000000000000000000175000018036281                         081000030000001",
            "5225Your Company Inc                    0018036281PPDTrnsNickna1812201503060011081000030000002",
            "627101000019923698412584     0000015000RAj##765432hj  Jane Doe              A10081000030000005",
            "822500000100101000010000000150000000000000000018036281                         081000030000002",
            "9000003000002000000060050600106000000015000000000026820                                       ",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"
        )
        val unboundHeaderLine = 7

        assertThatThrownBy {
            getBatchRanges(lines)
        }.hasMessage("Found batch header without batch control on line $unboundHeaderLine")
    }

    @Test
    fun `should throw an exception if a batch control is found without a header`() {
        val lines = listOf(
            "101 081000032 0180362811503042207A094101Some Bank              Your Company Inc       #A000001",
            "5220Your Company Inc                    0018036281WEBTrnsNickna1812201503050011081000030000000",
            "622081000210123456789012345670000003521RAj##23920rjf31John Doe              A10081000030000000",
            "6220810002105654221          0000002300RAj##32b1kn1bb3Bob Dole              A10081000030000001",
            "6220810002105654221          0000002499RAj##765kn4    Adam Something        A10081000030000002",
            "6220810002105654221          0000001000RAj##3j43kj4   James Bond            A10081000030000003",
            "822000000200324000840000000000000000000093200018036281                         081000030000000",
            "8220Your Company Inc                    0018036281WEBTrnsNickna1812201503160011081000030000001",
            "6220810002105654221          0000017500RAj##8k765j4k32Luke Skywalker        A10081000030000004",
            "822000000100081000210000000000000000000175000018036281                         081000030000001",
            "5225Your Company Inc                    0018036281PPDTrnsNickna1812201503060011081000030000002",
            "627101000019923698412584     0000015000RAj##765432hj  Jane Doe              A10081000030000005",
            "822500000100101000010000000150000000000000000018036281                         081000030000002",
            "9000003000002000000060050600106000000015000000000026820                                       ",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
            "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"
        )
        val badControlIndex = 7

        assertThatThrownBy {
            getBatchRanges(lines)
        }.hasMessage("Found batch control without preceding batch header on line $badControlIndex")
    }

    @Test
    fun `should return a Batch for a given range`() {
        val range = BatchRange(1,4)
        val expectedBatchHeader = BatchHeader(
            recordTypeCode = "5",
            serviceCode = "220",
            companyName = "Your Company Inc",
            companyDiscretionaryData = null,
            companyIdentification = "0018036281",
            standardEntryClassCode = "WEB",
            companyEntryDescription = "TrnsNickna",
            companyDescriptiveDate = LocalDate.parse("181220", DateTimeFormatter.ofPattern(STANDARD_DATE_TIME_FORMAT)),
            effectiveEntryDate = LocalDate.parse("150305", DateTimeFormatter.ofPattern(STANDARD_DATE_TIME_FORMAT)),
            settlementDate = LocalDate.parse("210101", DateTimeFormatter.ofPattern(STANDARD_DATE_TIME_FORMAT)),
            originatorStatusCode = "1",
            odfiIdentification = "08100003",
            batchNumber = 0
        )
        val expectedBatchControl = BatchControl(
            recordTypeCode = "8",
            serviceCode = "220",
            entryCount = 2,
            entryHash = "0032400084",
            totalDebitAmount = 0,
            totalCreditAmount = 9320,
            companyIdentification = "0018036281",
            messageAuthenticationCode = null,
            reserved = null,
            odfiIdentification = "08100003",
            batchNumber = 0
        )
        val expectedBatchDetails = listOf(
            BatchDetailRow(
                recordTypeCode = "6",
                transactionCode = "22",
                rdfiIdentification = "08100021",
                checkDigit = "0",
                dfiAccountNumber = "5654221",
                amount = 2499,
                individualIdentificationNumber = "RAj##765kn4",
                individualName = "Adam Something",
                discretionaryData = "A1",
                addendaRecordIndicator = "0",
                traceNumber = "081000030000002"
            ),
            BatchDetailRow(
                recordTypeCode = "6",
                transactionCode = "22",
                rdfiIdentification = "08100021",
                checkDigit = "0",
                dfiAccountNumber = "5654221",
                amount = 1000,
                individualIdentificationNumber = "RAj##3j43kj4",
                individualName = "James Bond",
                discretionaryData = "A1",
                addendaRecordIndicator = "0",
                traceNumber = "081000030000003"
            )
        )

        val batch = processLinesInRange(validLines, range)

        assertThat(batch.batchHeader).isEqualTo(expectedBatchHeader)
        assertThat(batch.batchControl).isEqualTo(expectedBatchControl)
        assertThat(batch.batchDetailRows).containsExactlyElementsOf(expectedBatchDetails)
    }
}
