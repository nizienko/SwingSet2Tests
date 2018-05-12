package jTable.printing

import com.github.nizienko.test.swing.printer.PrinterJobStub
import com.nhaarman.mockito_kotlin.*
import configuration.app
import engine.helpers.TablePrintableParser
import engine.helpers.matcher
import jTable.JTableTestSuite
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.BeforeClass
import org.junit.Test
import java.awt.print.PrinterJob
import javax.swing.JTable.PrintMode.FIT_WIDTH
import javax.swing.JTable.PrintMode.NORMAL


class PrintingTest : JTableTestSuite() {
    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeAll() {
            System.setProperty("java.awt.printerjob", "com.github.nizienko.test.swing.printer.PrinterJobStub")
        }
    }

    @Test
    fun printingComplete(): Unit = with(app.pageObject.jTablePanel) {
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
    fun printingCancelled(): Unit = with(app.pageObject.jTablePanel) {
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

    @Test
    fun fitWidthOn(): Unit = with(app.pageObject.jTablePanel) {
        val printerJob = mock<PrinterJob> {
            on { printDialog() } doReturn true
        }
        PrinterJobStub.printerJobMock = printerJob

        fitWidthModeComboBox.check()
        printButton.click()
        printingResult.button(matcher { it.text == "OK" }).click()

        verify(printerJob).setPrintable(
                check {
                    TablePrintableParser(it).apply {
                        printMode shouldBe FIT_WIDTH
                        table shouldBe app.pageObject.jTablePanel.table.component()
                    }
                })
    }

    @Test
    fun fitWidthOff(): Unit = with(app.pageObject.jTablePanel) {
        val printerJob = mock<PrinterJob> {
            on { printDialog() } doReturn true
        }
        PrinterJobStub.printerJobMock = printerJob

        fitWidthModeComboBox.uncheck()
        printButton.click()
        printingResult.button(matcher { it.text == "OK" }).click()

        verify(printerJob).setPrintable(
                check {
                    TablePrintableParser(it).apply {
                        printMode shouldBe NORMAL
                        table shouldBe app.pageObject.jTablePanel.table.component()
                    }
                })
    }

    @Test
    fun checkFooter(): Unit = with(app.pageObject.jTablePanel) {
        val printerJob = mock<PrinterJob> {
            on { printDialog() } doReturn true
        }
        PrinterJobStub.printerJobMock = printerJob

        printingFooterTextField.setText("Test footer {0}")
        printButton.click()
        printingResult.button(matcher { it.text == "OK" }).click()

        verify(printerJob).setPrintable(
                check {
                    TablePrintableParser(it).apply {
                        footerFormat.toPattern() shouldBeEqualTo "Test footer {0}"
                        table shouldBe app.pageObject.jTablePanel.table.component()
                    }
                })
    }

    @Test
    fun checkHeader(): Unit = with(app.pageObject.jTablePanel) {
        val printerJob = mock<PrinterJob> {
            on { printDialog() } doReturn true
        }
        PrinterJobStub.printerJobMock = printerJob

        printingHeaderTextField.setText("Test header {0}")
        printButton.click()
        printingResult.button(matcher { it.text == "OK" }).click()

        verify(printerJob).setPrintable(
                check {
                    TablePrintableParser(it).apply {
                        headerFormat.toPattern() shouldBeEqualTo "Test header {0}"
                        table shouldBe app.pageObject.jTablePanel.table.component()
                    }
                })
    }
}