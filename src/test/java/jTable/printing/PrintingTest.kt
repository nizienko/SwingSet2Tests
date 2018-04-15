package jTable.printing

import com.github.nizienko.test.swing.printer.PrinterJobStub
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import engine.helpers.matcher
import jTable.JTableTestSuite
import org.amshove.kluent.shouldBeEqualTo
import org.junit.BeforeClass
import org.junit.Test
import swingSet2.SwingSet2
import java.awt.print.PrinterJob

/*
Run this tests with -Xbootclasspath/a:stubs/PrintJobService.jar to stub the printer
 */
class PrintingTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeAll() {
            System.setProperty("java.awt.printerjob", "com.github.nizienko.test.swing.printer.PrinterJobStub")
        }
    }

    @Test
    fun printingComplete(): Unit = with(SwingSet2.jTablePanel) {
        val printerJob = mock<PrinterJob> {
            on { printDialog() } doReturn true
        }
        PrinterJobStub.printerJobMock = printerJob

        printButton.click()
        with(printingResult) {
            label("OptionPane.label").text() shouldBeEqualTo "Printing Complete"
            button(matcher { it.text == "OK" }).click()
        }
        verify(printerJob, atLeastOnce()).printDialog()
    }

    @Test
    fun printingCancelled(): Unit = with(SwingSet2.jTablePanel) {
        val printerJob = mock<PrinterJob> {
            on { printDialog() } doReturn false
        }
        PrinterJobStub.printerJobMock = printerJob

        printButton.click()
        with(printingResult) {
            label("OptionPane.label").text() shouldBeEqualTo "Printing Cancelled"
            button(matcher { it.text == "OK" }).click()
        }
        verify(printerJob, atLeastOnce()).printDialog()
    }
}