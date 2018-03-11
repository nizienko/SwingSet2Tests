package swingSet2.components

import engine.finder
import engine.helpers.matcher

class JTablePanel {
    val reorderingAllowedCheckBox by finder {
        checkBox(matcher { it.text == "Reordering allowed" })
    }

    val horizonalLinesCheckBox by finder {
        checkBox(matcher { it.text == "Horiz. Lines" })
    }

    val verticalLinesCheckBox by finder {
        checkBox(matcher { it.text == "Vert. Lines" })
    }

    val table by finder {
        table()
    }
}