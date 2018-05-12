package jTable.tableLines

import configuration.app
import jTable.JTableTestSuite
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test

class TableLinesTest : JTableTestSuite() {

    @Test
    fun horizontalLinesShouldBeVisible(): Unit = with(app.pageObject.jTablePanel) {
        horizontalLinesCheckBox.check()
        table.component().showHorizontalLines.shouldBeTrue()
    }

    @Test
    fun horizontalLinesShouldBeHidden(): Unit = with(app.pageObject.jTablePanel) {
        horizontalLinesCheckBox.uncheck()
        table.component().showHorizontalLines.shouldBeFalse()
    }

    @Test
    fun verticalLinesShouldBeVisible(): Unit = with(app.pageObject.jTablePanel) {
        verticalLinesCheckBox.check()
        table.component().showVerticalLines.shouldBeTrue()
    }

    @Test
    fun verticalLinesShouldBeHidden(): Unit = with(app.pageObject.jTablePanel) {
        verticalLinesCheckBox.uncheck()
        table.component().showVerticalLines.shouldBeFalse()
    }
}