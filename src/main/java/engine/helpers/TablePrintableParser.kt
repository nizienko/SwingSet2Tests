package engine.helpers

import java.awt.print.Printable
import java.text.MessageFormat
import javax.swing.JTable
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

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

    @Suppress("UNCHECKED_CAST")
    private fun <R : Any?> Any.readProperty(propertyName: String): R =
            this.javaClass.kotlin.declaredMemberProperties
                    .first { it.name == propertyName }
                    .apply { isAccessible = true }.get(this) as R
}

