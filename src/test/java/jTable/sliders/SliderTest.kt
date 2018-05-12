package jTable.sliders

import configuration.app
import jTable.JTableTestSuite
import org.amshove.kluent.shouldEqual
import org.junit.Test

class SliderTest : JTableTestSuite() {
    @Test
    fun moveInterCellSpacingSliderToMinimum(): Unit = with(app.pageObject.jTablePanel) {
        interCellSpacingSlider.slideToMinimum()
        table.component().intercellSpacing.width shouldEqual 0
    }

    @Test
    fun moveInterCellSpacingSliderToMaximum(): Unit = with(app.pageObject.jTablePanel) {
        interCellSpacingSlider.slideToMaximum()
        table.component().intercellSpacing.width shouldEqual 10
    }

    @Test
    fun moveRowHeightSliderToMinimum(): Unit = with(app.pageObject.jTablePanel) {
        rowHeightSlider.slideToMinimum()
        table.component().rowHeight shouldEqual 5
    }

    @Test
    fun moveRowHeightSliderToMaximum(): Unit = with(app.pageObject.jTablePanel) {
        rowHeightSlider.slideToMaximum()
        table.component().rowHeight shouldEqual 100
    }
}