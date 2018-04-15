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

    val autoresizeModeComboBox by finder {
        comboBox(matcher { it.getItemAt(1).toString() == "Column boundaries" })
    }

    val interCellSpacingSlider by finder {
        slider(matcher { it.maximum == 10 })
    }

    val rowHeightSlider by finder {
        slider(matcher { it.maximum == 100 })
    }

    val table by finder {
        table()
    }

    val printButton by finder {
        button(matcher { it.text == "Print" })
    }

    val fitWidthModeComboBox by finder {
        checkBox(matcher { it.text == "Fit Width" })
    }

    val printingHeaderTextField by finder {
        textBox(matcher {
            if (it.parent.componentCount == 5) {
                it == it.parent.getComponent(1)
            } else {
                false
            }
        })
    }

    val printingFooterTextField by finder {
        textBox(matcher {
            if (it.parent.componentCount == 5) {
                it == it.parent.getComponent(3)
            } else {
                false
            }
        })
    }

    val printingResult by finder {
        dialog(matcher { it.title == "Printing Result" && it.isShowing })
    }
}