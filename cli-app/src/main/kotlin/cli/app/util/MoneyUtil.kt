package cli.app.util

import java.math.BigDecimal

fun convertCentsToDollars(cents: Int) =
    BigDecimal(cents/100).setScale(2)

