package net.ilkinulas.adventofcode.util

import org.junit.Assert
import org.junit.Test


class MatrixTest {

    @Test fun setAndgetValue() {
        val matrix = Matrix(3, 4, -1)
        Assert.assertEquals(-1, matrix.get(0, 0))

        matrix.set(2, 2, 10)
        Assert.assertEquals(10, matrix.get(2, 2))
    }
}
