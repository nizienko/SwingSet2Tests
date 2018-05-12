package jTable

import configuration.app
import engine.helpers.waiting
import org.junit.AfterClass
import org.junit.BeforeClass

abstract class JTableTestSuite {
    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass(): Unit = with(app.pageObject.topToolBar) {
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