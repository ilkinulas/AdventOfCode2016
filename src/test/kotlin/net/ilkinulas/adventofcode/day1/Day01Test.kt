package net.ilkinulas.adventofcode.day1

import net.ilkinulas.adventofcode.*
import org.junit.Assert.assertEquals
import org.junit.Test

class Day01 {
    @Test
    fun testWayPoints() {
        var wayPoints = wayPoints(Position(0, 0, Heading.N), Move(Direction.R, 5))
        assertEquals(5, wayPoints.size)
        assertEquals(Position(5, 0, Heading.E), wayPoints.last())


        wayPoints = wayPoints(Position(3, 3, Heading.E), Move(Direction.R, 3))
        assertEquals(Position(3, 0, Heading.S), wayPoints.last())


        val position = findRevisitedPosition("R8, R4, R4, R8", Position())
        assertEquals(4, position?.x)
        assertEquals(0, position?.y)
        assertEquals(Heading.N, position?.heading)
    }
}

