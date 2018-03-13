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

    val rowSelectionCheckBox by finder {
        checkBox(matcher { it.text == "Row selection" })
    }

    val columnSelectionCheckBox by finder {
        checkBox(matcher { it.text == "Column selection" })
    }

    val selectionModeComboBox by finder {
        comboBox(matcher { it.getItemAt(0).toString() == "Single" })
    }

    val table by finder {
        table()
    }
}