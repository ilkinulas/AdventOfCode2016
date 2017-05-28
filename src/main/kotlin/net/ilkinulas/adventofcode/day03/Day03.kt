package net.ilkinulas.adventofcode.day03

import java.io.File

fun main(args: Array<String>) {
    val regex = Regex("\\s+")
    val allNumbers = File("src/main/resources/input03.txt").readText().split(regex).filter { it.isNotEmpty() }.map { it.toInt() }

    val part1 = (0..allNumbers.size - 1 step 3).mapTo(mutableListOf<Triple<Int, Int, Int>>()) {
        Triple(allNumbers[it], allNumbers[it + 1], allNumbers[it + 2])
    }.filter { isValidTriangle(it) }.size
    println(part1)


    val lines = File("src/main/resources/input03.txt").readLines()
    val intArray = IntArray(lines.size * 3)
    lines.mapIndexed { index, element ->
        element.trim().split(regex).let {
            intArray[index] = it[0].toInt()
            intArray[index + lines.size] = it[1].toInt()
            intArray[index + lines.size * 2] = it[2].toInt()
        }
    }
    val part2 = (0..intArray.size - 1 step 3).mapTo(mutableListOf<Triple<Int, Int, Int>>()) {
        Triple(intArray[it], intArray[it + 1], intArray[it + 2])
    }.filter { isValidTriangle(it) }.size
    println(part2)
}

private fun isValidTriangle(it: Triple<Int, Int, Int>): Boolean {
    return (it.first + it.second > it.third
            && it.first + it.third > it.second
            && it.second + it.third > it.first)
}
