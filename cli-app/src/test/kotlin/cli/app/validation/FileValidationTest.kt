package cli.app.validation

import cli.app.model.FailureReason
import cli.app.validateFile
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class FileValidationTest {
    @Test fun `should fail when a file is empty`() {
        val inputLines = listOf<String>()

        val failureReasons = validateFile(inputLines)

        assertThat(failureReasons).containsExactly(FailureReason.FILE_EMPTY)
    }

    @Test fun `should fail when a file has an invalid number of lines`() {
        val inputLines = listOf("foo", "bar")

        val failureReasons = validateFile(inputLines)

        assertThat(failureReasons).containsExactly(FailureReason.FILE_LINE_COUNT)
    }
}
