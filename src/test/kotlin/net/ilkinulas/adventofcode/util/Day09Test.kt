package net.ilkinulas.adventofcode.util

import net.ilkinulas.adventofcode.day09.decompressIterative
import net.ilkinulas.adventofcode.day09.decompressRecursive
import net.ilkinulas.adventofcode.day09.decompressRecursivePart2
import net.ilkinulas.adventofcode.day09.decompressedLength
import org.junit.Assert.assertEquals
import org.junit.Test


class Day09Test {
    @Test fun testDecompressRecursive() {
        assertEquals("ADVENT", decompressRecursive("ADVENT"))
        assertEquals("ABBBBBC", decompressRecursive("A(1x5)BC"))
        assertEquals("XYZXYZXYZ", decompressRecursive("(3x3)XYZ"))
        assertEquals("ABCBCDEFEFG", decompressRecursive("A(2x2)BCD(2x2)EFG"))
        assertEquals("(1x3)A", decompressRecursive("(6x1)(1x3)A"))
        assertEquals("X(3x3)ABC(3x3)ABCY", decompressRecursive("X(8x2)(3x3)ABCY"))
    }

    @Test fun testDecompressIterative() {
        assertEquals("ADVENT", decompressIterative("ADVENT"))
        assertEquals("ABBBBBC", decompressIterative("A(1x5)BC"))
        assertEquals("XYZXYZXYZ", decompressIterative("(3x3)XYZ"))
        assertEquals("ABCBCDEFEFG", decompressIterative("A(2x2)BCD(2x2)EFG"))
        assertEquals("(1x3)A", decompressIterative("(6x1)(1x3)A"))
        assertEquals("X(3x3)ABC(3x3)ABCY", decompressIterative("X(8x2)(3x3)ABCY"))
    }

    @Test fun testDecompressRecursivePart2() {
        assertEquals("XYZXYZXYZ", decompressRecursivePart2("(3x3)XYZ"))
        assertEquals("XABCABCABCABCABCABCY", decompressRecursivePart2("X(8x2)(3x3)ABCY"))
        assertEquals(241920, decompressRecursivePart2("(27x12)(20x12)(13x14)(7x10)(1x12)A").length)
        assertEquals(445, decompressRecursivePart2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN").length)
    }

    @Test fun testDecompressedLength() {
        assertEquals(9, decompressedLength("(3x3)XYZ", 0))
        assertEquals(20, decompressedLength("X(8x2)(3x3)ABCY", 0))
        assertEquals(241920, decompressedLength("(27x12)(20x12)(13x14)(7x10)(1x12)A", 0))
        assertEquals(445, decompressedLength("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN", 0))
    }
}