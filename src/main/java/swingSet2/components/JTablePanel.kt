package swingSet2.components

import engine.finder
import engine.helpers.matcher
import javax.swing.JLabel
import javax.swing.JTextField

class JTablePanel {
    val reorderingAllowedCheckBox by finder {
        checkBox(matcher { it.text == "Reordering allowed" })
    }

    val horizontalLinesCheckBox by finder {
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
    /**
     * Looking for second component in JPanel which has text 'Header' in first component
     */
    val printingHeaderTextField by finder {
        textBox(matcher {
            val firstComponent = it.parent.components[0]
            if (firstComponent is JLabel
                    && firstComponent.text == "Header"
                    && it is JTextField
                    && it == it.parent.components[1]) {
                return@matcher true
            }
            return@matcher false
        })
    }
    /**
     * Looking for fourth component in JPanel which has text 'Header' in first component
     */
    val printingFooterTextField by finder {
        textBox(matcher {
            val firstComponent = it.parent.components[0]
            if (firstComponent is JLabel
                    && firstComponent.text == "Header"
                    && it is JTextField
                    && it == it.parent.components[3]) {
                return@matcher true
            }
            return@matcher false
        })
    }

    val printingResult by finder {
        dialog(matcher { it.title == "Printing Result" && it.isShowing })
    }
}