package net.ilkinulas.adventofcode.util


class Matrix(val cols: Int, val rows: Int, val defaultValue: Int = 0) {
    private val array = IntArray(cols * rows) { defaultValue }

    fun get(x: Int, y: Int): Int = array[index(x, y)]
    fun set(x: Int, y: Int, value: Int) {
        array[index(x, y)] = value
    }

    private fun index(x: Int, y: Int) = x + (y * cols)

    fun toFormattedString() = StringBuilder().apply {
        for (i in 0..rows - 1) {
            for (j in 0..cols - 1) {
                if (array[index(j, i)] == 0) {
                    append(" ")
                } else {
                    append("#")
                }
            }
            append("\n")
        }
    }.toString()

    fun getNumberOf(x:Int) = array.count { it == x }
}