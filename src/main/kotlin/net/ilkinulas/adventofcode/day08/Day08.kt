package net.ilkinulas.adventofcode.day08

import net.ilkinulas.adventofcode.util.Matrix
import java.io.File

abstract class Operation {
    abstract fun perform(matrix: Matrix): Matrix

    companion object {
        fun parseRectangle(s: String) = s.split(' ')[1].split('x').let {
            RectangleOperation(it[0].toInt(), it[1].toInt())
        }

        fun parseRotateRow(s: String) = s.split(' ').let {
            RotateRowOperation(it[2].split('=')[1].toInt(), it[4].toInt())
        }

        fun parseRotateCol(s: String) = s.split(' ').let {
            RotateColOperation(it[2].split('=')[1].toInt(), it[4].toInt())
        }
    }
}

class RectangleOperation(val x: Int, val y: Int) : Operation() {
    override fun perform(matrix: Matrix) = matrix.apply {
        for (i in 0..x - 1) {
            for (j in 0..y - 1) {
                set(i, j, 1)
            }
        }
    }
}

class RotateRowOperation(val y: Int, val by: Int) : Operation() {
    override fun perform(matrix: Matrix) = matrix.apply {
        val tmpArray = IntArray(cols)
        for (i in 0..cols - 1) {
            tmpArray[((by % cols) + i) % cols] = get(i, y)
        }
        for (i in 0..cols - 1) {
            set(i, y, tmpArray[i])
        }
    }
}

class RotateColOperation(val x: Int, val by: Int) : Operation() {
    override fun perform(matrix: Matrix) = matrix.apply {
        val tmpArray = IntArray(rows)
        for (i in 0..rows - 1) {
            tmpArray[((by % rows) + i) % rows] = get(x, i)
        }
        for (i in 0..rows - 1) {
            set(x, i, tmpArray[i])
        }
    }
}

fun parseInput() = File("src/main/resources/input08.txt").readLines().map {
    when {
        it.startsWith("rect") -> Operation.parseRectangle(it)
        it.startsWith("rotate row") -> Operation.parseRotateRow(it)
        else -> Operation.parseRotateCol(it)
    }
}

fun main(args: Array<String>) {
    val result = parseInput().fold(Matrix(50, 6)) { matrix, operation -> operation.perform(matrix) }
    println(result.getNumberOf(1))
    println(result.toFormattedString())
}
