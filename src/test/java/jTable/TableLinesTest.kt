package jTable

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import swingSet2.SwingSet2

class TableLinesTest : JTableTestSuite() {

    @Test
    fun horizontalLinesVisible(): Unit = with(SwingSet2.jTablePanel) {
        horizonalLinesCheckBox.check()
        table.component().showHorizontalLines.shouldBeTrue()
    }

    @Test
    fun horizontalLinesHidden(): Unit = with(SwingSet2.jTablePanel) {
        horizonalLinesCheckBox.uncheck()
        table.component().showHorizontalLines.shouldBeFalse()
    }

    @Test
    fun verticalLinesVisible(): Unit = with(SwingSet2.jTablePanel) {
        verticalLinesCheckBox.check()
        table.component().showVerticalLines.shouldBeTrue()
    }

    @Test
    fun verticalLinesHidden(): Unit = with(SwingSet2.jTablePanel) {
        verticalLinesCheckBox.uncheck()
        table.component().showVerticalLines.shouldBeFalse()
    }
}