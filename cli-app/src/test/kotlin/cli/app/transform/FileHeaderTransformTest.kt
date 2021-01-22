package cli.app.transform

import cli.app.model.STANDARD_DATE_TIME_FORMAT
import org.assertj.core.api.Assertions.assertThat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.Test

class FileHeaderTransformTest {
    @Test fun `should parse a file header`() {
        val line = "101 987654321 0123456782101141557A094101FooBar FEDERAL BANK    MyBank                  ABC123 "

        val parsedFileHeader = lineToFileHeader(line)

        assertThat(parsedFileHeader.recordTypeCode).isEqualTo("1")
        assertThat(parsedFileHeader.priorityCode).isEqualTo("01")
        assertThat(parsedFileHeader.immediateDestination).isEqualTo("987654321")
        assertThat(parsedFileHeader.immediateOrigin).isEqualTo("012345678")
        assertThat(parsedFileHeader.fileCreationDate).isEqualTo(
            LocalDate.parse("210114", DateTimeFormatter.ofPattern(STANDARD_DATE_TIME_FORMAT))
        )
        assertThat(parsedFileHeader.fileIdModifier).isEqualTo("A")
        assertThat(parsedFileHeader.recordSize).isEqualTo(94)
        assertThat(parsedFileHeader.blockingFactor).isEqualTo(10)
        assertThat(parsedFileHeader.formatCode).isEqualTo(1)
        assertThat(parsedFileHeader.immediateDestinationName).isEqualTo("FooBar FEDERAL BANK")
        assertThat(parsedFileHeader.immediateOriginName).isEqualTo("MyBank")
        assertThat(parsedFileHeader.referenceCode).isEqualTo("ABC123")
    }

    @Test fun `should have null reference code when appropriate column is empty`() {
        val line = "101 987654321 0123456782101141557A094101FooBar FEDERAL BANK    MyBank                         "

        val parsedFileHeader = lineToFileHeader(line)

        assertThat(parsedFileHeader.referenceCode).isNull()
    }
}
