package net.ilkinulas.adventofcode.day09

import java.io.File

val regex = """\((\d+)x(\d+)\)""".toRegex()

/**
 * Iterative solution for part1
 */
fun decompressIterative(input: String): String {
    val result = StringBuilder()
    var rest = input
    var match = regex.find(rest)
    while (match != null) {
        val numChars = match.groupValues[1].toInt()
        val repeat = match.groupValues[2].toInt()
        result.append(rest.substring(0, match.range.first))
        rest = rest.substring(match.range.last + 1)
        result.append(rest.take(numChars).repeat(repeat))
        rest = rest.substring(numChars)
        match = regex.find(rest)
        if (match == null) {
            result.append(rest)
        }
    }
    return if (result.isEmpty()) input else result.toString()
}

/**
 * Recursive solution for part1
 */
fun decompressRecursive(input: String): String {
    var match = regex.find(input) ?: return input
    val numChars = match.groupValues[1].toInt()
    val repeat = match.groupValues[2].toInt()
    var rest = input.substring(match.range.last + 1)
    val decompressed = input.substring(0, match.range.first) + rest.take(numChars).repeat(repeat)
    return decompressed + decompressRecursive(rest.substring(numChars))
}

/**
 * This function calculates the decompressed value of the input string.
 * It takes too much time to decompress long input values.-_()
 */
tailrec fun decompressRecursivePart2(input: String): String {
    var match = regex.find(input) ?: return input
    val numChars = match.groupValues[1].toInt()
    val repeat = match.groupValues[2].toInt()
    var rest = input.substring(match.range.last + 1)

    return decompressRecursivePart2(
            input.substring(0, match.range.first) +
                    rest.take(numChars).repeat(repeat) +
                    rest.substring(numChars))
}

/**
 * This version does not try to findout the final decompressed string. Just calculates the length
 */
tailrec fun decompressedLength(input: String, carry: Long = 0): Long {
    var match = regex.find(input) ?: return carry + input.length
    val numChars = match.groupValues[1].toInt()
    val repeat = match.groupValues[2].toInt()
    var rest = input.substring(match.range.last + 1)
    return decompressedLength(rest.take(numChars)) * repeat + decompressedLength(rest.substring(numChars), match.range.first + carry)
}


fun main(args: Array<String>) {
    val input = File("src/main/resources/input09.txt").readText()

    println(decompressIterative(input).length)
    println(decompressRecursive(input).length)

    println(decompressedLength(input))
}

