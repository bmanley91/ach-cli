package cli.app.parser

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class FileControlParserTest {

    @Test
    fun `should return a File Control for the given line`() {
        val line = "9000003000002000000060050600106000000015000000000026820                                       "

        val fileControl = parseFileControlLine(line)

        assertThat(fileControl.recordTypeCode).isEqualTo("9")
        assertThat(fileControl.batchCount).isEqualTo(3)
        assertThat(fileControl.blockCount).isEqualTo(2)
        assertThat(fileControl.entryCount).isEqualTo(6)
        assertThat(fileControl.entryHash).isEqualTo("0050600106")
        assertThat(fileControl.totalDebit).isEqualTo(15000)
        assertThat(fileControl.totalCredit).isEqualTo(26820)
    }
}
