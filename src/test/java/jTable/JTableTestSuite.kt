package jTable

import configuration.app
import engine.helpers.waiting
import org.junit.AfterClass
import org.junit.BeforeClass
import swingSet2.SwingSet2

abstract class JTableTestSuite {
    companion object {

        @JvmStatic
        @BeforeClass
        fun beforeClass(): Unit = with(SwingSet2.topToolBar) {
            waiting { jTreeButton.requireVisible() }
            jTableButton.click()
        }

        @JvmStatic
        @AfterClass
        fun afterClass() {
            app.stop()
        }
    }
}