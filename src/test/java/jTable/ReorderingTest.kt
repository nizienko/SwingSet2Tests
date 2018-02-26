package jTable

import org.junit.Test
import swingSet2.SwingSet2

class ReorderingTest : JTableTestSuite() {

    @Test
    fun reorderingOn(): Unit = with(SwingSet2.jTablePanel) {
        reorderingAllowedCheckBox.check()
        table.requireVisible()
    }
}