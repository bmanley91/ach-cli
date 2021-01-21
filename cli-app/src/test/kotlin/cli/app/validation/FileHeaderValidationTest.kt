package cli.app.validation

import cli.app.model.FailureReason
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class FileHeaderValidationTest {

    @Test
    fun `should fail when a batch header doesn't start with '1'`() {
        val inputLine = "901 987654321 0123456782101141557A094101FooBar FEDERAL BANK    MyBank                         "

        val failureReasons = validateFileHeader(inputLine)

        assertThat(failureReasons).containsExactly(FailureReason.FILE_HEADER_RECORD_TYPE_CODE)
    }

    @Test
    fun `should fail when a batch header doesn't have the correct priority code`() {
        val inputLine = "106 987654321 0123456782101141557A094101FooBar FEDERAL BANK    MyBank                         "

        val failureReasons = validateFileHeader(inputLine)

        assertThat(failureReasons).containsExactly(FailureReason.FILE_HEADER_PRIORITY_CODE)
    }

    @Test
    fun `should return multiple failure reasons`() {
        val inputLine = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"

        val failureReasons = validateFileHeader(inputLine)

        assertThat(failureReasons).containsExactlyElementsOf(
            listOf(
                FailureReason.FILE_HEADER_RECORD_TYPE_CODE,
                FailureReason.FILE_HEADER_PRIORITY_CODE
            )
        )
    }

    @Test
    fun `should return an empty list for a valid line`() {
        val inputLine = "101 987654321 0123456782101141557A094101FooBar FEDERAL BANK    MyBank                         "

        val failureReasons = validateFileHeader(inputLine)

        assertThat(failureReasons).isEmpty()
    }
}
