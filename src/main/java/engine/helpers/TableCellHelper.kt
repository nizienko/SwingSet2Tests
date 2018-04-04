package engine.helpers

import org.fest.swing.core.MouseButton
import org.fest.swing.data.TableCell
import org.fest.swing.fixture.JTableFixture
import java.awt.event.KeyEvent
import javax.swing.JTable

fun JTableFixture.clickCells(click: TableCellClicker.() -> Unit) {
    val clicker = TableCellClicker(this)
    clicker.click()
}

class TableCellClicker(private val table: JTableFixture) {
    fun clickCell(row: Int, column: Int) =
            table.click(TableCell
                    .row(row)
                    .column(column),
                    MouseButton.LEFT_BUTTON)!!

    fun pressingControl(click: TableCellClicker.() -> Unit) = pressingKey(KeyEvent.VK_CONTROL, click)

    fun pressingShift(click: TableCellClicker.() -> Unit) = pressingKey(KeyEvent.VK_SHIFT, click)

    fun pressingKey(keyCode: Int, click: TableCellClicker.() -> Unit = {}) {
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
    fun rowsShouldBeSelected(rows: List<Int>, exceptColumns: List<Int> = emptyList()) {
        with(table.component()) {
            rows.forEach { r ->
                for (c in 0 until columnCount) {
                    if (exceptColumns.contains(c)) {
                        cellShouldNotBeSelected(r, c)
                    } else {
                        cellShouldBeSelected(r, c)
                    }
                }
            }
        }
    }


    fun columnsShouldBeSelected(columns: List<Int>, exceptRows: List<Int> = emptyList()) {
        with(table.component()) {
            columns.forEach { c ->
                for (r in 0 until rowCount) {
                    if (exceptRows.contains(r)) {
                        cellShouldNotBeSelected(r, c)
                    } else {
                        cellShouldBeSelected(r, c)
                    }
                }
            }
        }
    }

    fun rowsShouldNotBeSelected(rows: List<Int>, exceptColumns: List<Int> = emptyList()) {
        with(table.component()) {
            rows.forEach { r ->
                for (c in 0 until columnCount) {
                    if (exceptColumns.contains(c)) {
                        cellShouldBeSelected(r, c)
                    } else {
                        cellShouldNotBeSelected(r, c)
                    }
                }
            }
        }
    }


    fun columnsShouldNotBeSelected(columns: List<Int>, exceptRows: List<Int> = emptyList()) {
        with(table.component()) {
            columns.forEach { c ->
                for (r in 0 until rowCount) {
                    if (exceptRows.contains(r)) {
                        cellShouldBeSelected(r, c)
                    } else {
                        cellShouldNotBeSelected(r, c)
                    }
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