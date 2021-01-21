package cli.app.model

enum class FailureReason(val message: String) {
    FILE_EMPTY("File is empty"),
    FILE_LINE_COUNT("Total number of lines in file must be divisible by 10"),
    FILE_HEADER_RECORD_TYPE_CODE("File Header must start with '1'"),
    FILE_HEADER_PRIORITY_CODE("Priority code in File Header (chars 2-3) must be '01'")
}
