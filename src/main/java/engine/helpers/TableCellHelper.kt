package engine.helpers

import engine.extensions.pressingKey
import org.fest.swing.core.MouseButton
import org.fest.swing.fixture.JTableFixture
import java.awt.event.KeyEvent
import javax.swing.JTable

fun JTableFixture.clickCells(click: TableCellClicker.() -> Unit) {
    val clicker = TableCellClicker(this)
    clicker.click()
}

class TableCellClicker(private val table: JTableFixture) {
    fun cell(row: Int, column: Int) =
            table.click(org.fest.swing.data.TableCell
                    .row(row)
                    .column(column),
                    MouseButton.LEFT_BUTTON)!!

    fun pressingControl(click: TableCellClicker.() -> Unit) = pressingKey(KeyEvent.VK_CONTROL, click)

    fun pressingShift(click: TableCellClicker.() -> Unit) = pressingKey(KeyEvent.VK_SHIFT, click)

    fun pressingKey(keyCode: Int, click: TableCellClicker.() -> Unit) {
        val cellClicker = this
        table.robot.pressingKey(keyCode) {
            cellClicker.click()
        }
    }
}

fun JTableFixture.checkCellsSelection(check: TableCellSelectedChecker.() -> Unit) {
    val checker = TableCellSelectedChecker(this)
    checker.check()
}

class TableCellSelectedChecker(private val table: JTableFixture) {
    fun rowSelected(row: Int, exceptCellAtColumn: Int? = null) {
        with(table.component()) {
            for (c in 0 until columnCount) {
                if (exceptCellAtColumn != null && c == exceptCellAtColumn) {
                    cellShouldNotBeSelected(row, c)
                } else {
                    cellShouldBeSelected(row, c)
                }
            }
        }
    }

    fun columnSelected(column: Int, exceptCellAtRow: Int? = null) {
        with(table.component()) {
            for (r in 0 until rowCount) {
                if (exceptCellAtRow != null && r == exceptCellAtRow) {
                    cellShouldNotBeSelected(r, column)
                } else {
                    cellShouldBeSelected(r, column)
                }
            }
        }
    }

    fun rowIsNotSelected(row: Int, exceptCellAtColumn: Int? = null) {
        with(table.component()) {
            for (c in 0 until columnCount) {
                if (exceptCellAtColumn != null && c == exceptCellAtColumn) {
                    cellShouldBeSelected(row, c)
                } else {
                    cellShouldNotBeSelected(row, c)
                }
            }
        }
    }

    fun columnIsNotSelected(column: Int, exceptCellAtRow: Int? = null) {
        with(table.component()) {
            for (r in 0 until rowCount) {
                if (exceptCellAtRow != null && r == exceptCellAtRow) {
                    cellShouldBeSelected(r, column)
                } else {
                    cellShouldNotBeSelected(r, column)
                }
            }
        }
    }

    fun cellSelected(row: Int, column: Int) {
        table.component().cellShouldBeSelected(row, column)
    }

    fun cellIsNotSelected(row: Int, column: Int) {
        table.component().cellShouldNotBeSelected(row, column)
    }
}

fun JTable.cellShouldBeSelected(row: Int, column: Int) {
    if (isCellSelected(row, column).not()) {
        throw AssertionError("Cell($row, $column) expected to be selected, but it is not")
    }
}

fun JTable.cellShouldNotBeSelected(row: Int, column: Int) {
    if (isCellSelected(row, column)) {
        throw AssertionError("Cell($row, $column) expected NOT to be selected, but it is selected")
    }
}