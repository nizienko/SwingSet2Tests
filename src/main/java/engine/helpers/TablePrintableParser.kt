package engine.helpers

import java.awt.print.Printable
import java.text.MessageFormat
import javax.swing.JTable

class TablePrintableParser(printable: Printable) {
    private val printDelegate = printable.readProperty<Any>("printDelegatee")
            .readProperty<kotlin.Any>("printDelegate")

    val printMode
        get() = printDelegate.readProperty<JTable.PrintMode>("printMode")

    val headerFormat
        get() = printDelegate.readProperty<MessageFormat>("headerFormat")

    val footerFormat
        get() = printDelegate.readProperty<MessageFormat>("footerFormat")

    val table
        get() = printDelegate.readProperty<JTable>("table")
}