package swingSet2.components

import engine.finder
import helpers.matcher

class JTablePanel {
    val reorderingAllowedCheckBox by finder {
        checkBox(matcher { it.text == "Reordering allowed" })
    }

    val table by finder {
        println("searching table")
        table()
    }
}