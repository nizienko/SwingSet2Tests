package jTable.tableLines

import jTable.JTableTestSuite
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import swingSet2.SwingSet2

class TableLinesTest : JTableTestSuite() {

    @Test
    fun horizontalLinesShouldBeVisible(): Unit = with(SwingSet2.jTablePanel) {
        horizontalLinesCheckBox.check()
        table.component().showHorizontalLines.shouldBeTrue()
    }

    @Test
    fun horizontalLinesShouldBeHidden(): Unit = with(SwingSet2.jTablePanel) {
        horizontalLinesCheckBox.uncheck()
        table.component().showHorizontalLines.shouldBeFalse()
    }

    @Test
    fun verticalLinesShouldBeVisible(): Unit = with(SwingSet2.jTablePanel) {
        verticalLinesCheckBox.check()
        table.component().showVerticalLines.shouldBeTrue()
    }

    @Test
    fun verticalLinesShouldBeHidden(): Unit = with(SwingSet2.jTablePanel) {
        verticalLinesCheckBox.uncheck()
        table.component().showVerticalLines.shouldBeFalse()
    }
}