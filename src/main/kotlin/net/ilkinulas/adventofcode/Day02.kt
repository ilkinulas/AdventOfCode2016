package net.ilkinulas.adventofcode.day2

import java.io.File

enum class Command(val x: Int, val y: Int) {
    U(0, -1), L(-1, 0), D(0, 1), R(1, 0)
}

val part1 = arrayOf(
        arrayOf("1", "2", "3"),
        arrayOf("4", "5", "6"),
        arrayOf("7", "8", "9"))

val part2 = arrayOf(
        arrayOf(null, null, "1", null, null),
        arrayOf(null, "2",  "3", "4",  null),
        arrayOf("5",  "6",  "7", "8", "9"),
        arrayOf(null, "A",  "B", "C",  null),
        arrayOf(null, null, "D", null, null))


private fun processLinePart1(initial: Pair<Int, Int>, line: String) = line.map { Command.valueOf(it.toString()) }
        .fold(initial) {
            (first, second), command ->
            (first + command.x).coerceIn(0, 2) to (second + command.y).coerceIn(0, 2)
        }

private fun processLinePart2(initial: Pair<Int, Int>, line: String) = line.map { Command.valueOf(it.toString()) }
        .fold(initial) {
            (first, second), command ->
            val x = (first + command.x).coerceIn(0, 4)
            val y = (second + command.y).coerceIn(0, 4)
            if (part2[y][x] == null) first to second else x to y
        }

fun main(args: Array<String>) {

    val lines = File("src/main/resources/input02.txt").readLines()
    var startPos = 1 to 1
    lines.map {
        startPos = processLinePart1(startPos, it)
        part1[startPos.second][startPos.first]
    }.forEach(::print)

    println()

    startPos = 2 to 2
    lines.map {
        startPos = processLinePart2(startPos, it)
        part2[startPos.second][startPos.first]
    }.forEach(::print)
}
