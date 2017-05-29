package net.ilkinulas.adventofcode.util

import net.ilkinulas.adventofcode.day08.Operation
import org.junit.Assert.assertEquals
import org.junit.Test


class Day08Test {
    @Test fun parseRectange() {
        val rect = Operation.parseRectangle("rect 3x5")
        assertEquals(3, rect.x)
        assertEquals(5, rect.y)
    }

    @Test fun parseRotateRow() {
        val rotateRow = Operation.parseRotateRow("rotate row y=1 by 7")
        assertEquals(1, rotateRow.y)
        assertEquals(7, rotateRow.by)
    }

    @Test fun parseRotateCol() {
        val rotateRow = Operation.parseRotateCol("rotate column x=3 by 2")
        assertEquals(3, rotateRow.x)
        assertEquals(2, rotateRow.by)
    }

    @Test fun rectOperationPerform() {
        val matrix = Matrix(6, 4)
        val rect = Operation.parseRectangle("rect 3x2")
        val result = rect.perform(matrix)
        assertEquals(1, result.get(0, 0))
        assertEquals(1, result.get(1, 0))
        assertEquals(1, result.get(2, 0))
        assertEquals(1, result.get(0, 1))
        assertEquals(1, result.get(1, 1))
        assertEquals(1, result.get(2, 1))
        assertEquals(0, result.get(2, 2))

        println(result.toFormattedString())
    }

    @Test fun rotateColOperationPerform() {
        val matrix = Matrix(3, 3)
        Operation.parseRectangle("rect 3x1").perform(matrix)
        Operation.parseRotateCol("rotate column x=0 by 5").perform(matrix)
        Operation.parseRotateCol("rotate column x=1 by 1").perform(matrix)
        Operation.parseRotateCol("rotate column x=2 by 2").perform(matrix)
        for (i in 0..2) {
            for (j in 0..2) {
                if ((i==0 && j==2) || (i==1 && j==1) || (i==2 && j==2)) {
                    assertEquals(1, matrix.get(i, j))
                } else {
                    assertEquals(0, matrix.get(i, j))
                }
            }
        }
    }

    @Test fun rotateRowOperationPerform() {
        val matrix = Matrix(3, 3)
        Operation.parseRectangle("rect 1x3").perform(matrix)
        Operation.parseRotateRow("rotate row y=0 by 5").perform(matrix)
        Operation.parseRotateRow("rotate row y=1 by 1").perform(matrix)
        Operation.parseRotateRow("rotate row y=2 by 2").perform(matrix)
        println(matrix.toFormattedString())
        for (i in 0..2) {
            for (j in 0..2) {
                if ((i==2 && j==0) || (i==1 && j==1) || (i==2 && j==2)) {
                    assertEquals(1, matrix.get(i, j))
                } else {
                    assertEquals(0, matrix.get(i, j))
                }
            }
        }
    }
}

