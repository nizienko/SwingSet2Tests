package swingSet2.components

import engine.finder
import engine.helpers.matcher

class JTablePanel {
    val reorderingAllowedCheckBox by finder {
        checkBox(matcher { it.text == "Reordering allowed" })
    }

    val table by finder {
        table()
    }
}