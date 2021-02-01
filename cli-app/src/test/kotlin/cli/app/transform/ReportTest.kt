package cli.app.transform

import cli.app.model.Batch
import cli.app.model.BatchControl
import cli.app.model.BatchDetailRow
import cli.app.model.BatchHeader
import cli.app.model.FileControl
import cli.app.model.FileHeader
import cli.app.util.buildBatchControl
import cli.app.util.buildBatchDetailRow
import cli.app.util.buildBatchHeader
import cli.app.util.buildFileControl
import cli.app.util.buildFileHeader
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import kotlin.test.Test

class ReportTest {
    private lateinit var fileHeader: FileHeader
    private lateinit var fileControl: FileControl
    private lateinit var batchHeader: BatchHeader
    private lateinit var batchControl: BatchControl
    private lateinit var batchDetailRow: BatchDetailRow
    private lateinit var batch: Batch

    @Before
    fun setup() {
        fileHeader = buildFileHeader()
        fileControl = buildFileControl()
        batchHeader = buildBatchHeader()
        batchControl = buildBatchControl()
        batchDetailRow = buildBatchDetailRow()
        batch = Batch(batchHeader, listOf(batchDetailRow), batchControl)
    }

    @Test
    fun `should generate a report containing the file header, file control, and batch details`() {
        val report = generateReport(fileHeader, listOf(batch), fileControl, true)

        assertThat(report).contains("File Information")
        assertThat(report).contains("File Control Information")
        assertThat(report).contains("Batch Number 0 Summary")
        assertThat(report).contains("Batch 0, Entry 0")
    }

    @Test
    fun `should not generate batch row details if the verbose argument is not passed`() {
        val report = generateReport(fileHeader, listOf(batch), fileControl, false)

        assertThat(report).doesNotContain("Batch 0, Entry 0")
    }
}
