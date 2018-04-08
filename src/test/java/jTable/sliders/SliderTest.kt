package jTable.sliders

import jTable.JTableTestSuite
import org.amshove.kluent.shouldEqual
import org.junit.Test
import swingSet2.SwingSet2

class SliderTest : JTableTestSuite() {
    @Test
    fun moveInterCellSpacingSliderToMinimum(): Unit = with(SwingSet2.jTablePanel) {
        interCellSpacingSlider.slideToMinimum()
        table.component().intercellSpacing.width shouldEqual 0
    }

    @Test
    fun moveInterCellSpacingSliderToMaximum(): Unit = with(SwingSet2.jTablePanel) {
        interCellSpacingSlider.slideToMaximum()
        table.component().intercellSpacing.width shouldEqual 10
    }

    @Test
    fun moveRowHeightSliderToMinimum(): Unit = with(SwingSet2.jTablePanel) {
        rowHeightSlider.slideToMinimum()
        table.component().rowHeight shouldEqual 5
    }

    @Test
    fun moveRowHeightSliderToMaximum(): Unit = with(SwingSet2.jTablePanel) {
        rowHeightSlider.slideToMaximum()
        table.component().rowHeight shouldEqual 100
    }
}