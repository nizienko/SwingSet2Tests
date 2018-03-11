package swingSet2.components

import engine.finder
import engine.helpers.matcher
import javax.swing.ImageIcon
import javax.swing.JToggleButton

class TopToolBar {
    private fun matchedByIcon(icon: String) =
            matcher<JToggleButton> {
                (it.icon as ImageIcon).description.contains(icon)
            }

    val jTableButton by finder(cachingEnabled = true) {
        toggleButton(matchedByIcon("JTable.gif"))
    }

    val jTreeButton by finder(cachingEnabled = true) {
        toggleButton(matchedByIcon("JTree.gif"))
    }
}