package jTable

import engine.helpers.clickCells
import org.junit.Test
import swingSet2.SwingSet2

class EditCellsTest: JTableTestSuite() {

    @Test
    fun editText() : Unit = with(SwingSet2.jTablePanel) {
        table.clickCells {
            double { clickCell(1,0) }
        }
    }
}