package net.ilkinulas.adventofcode.day07

import java.io.File

fun main(args: Array<String>) {

    val inputLines = File("src/main/resources/input07.txt").readLines()
    part1Solution(inputLines)
    part2Solution(inputLines)
}

private fun part1Solution(inputLines: List<String>) {
    val globalRegex = """(([a-z])([a-z])\3\2)""".toRegex()
    val abbaInsideSquareBracketsRegex = """\[[a-z]*(([a-z])([a-z])\3\2)[a-z]*\]""".toRegex()
    val count = inputLines.filter {
        val globalMatch = globalRegex.find(it)
        var filtered = false
        if (globalMatch != null) {
            val abba = globalMatch.groups[1]?.value
            if (abba != null && abba[0] != abba[2]) {
                filtered = abbaInsideSquareBracketsRegex.find(it) == null
            }
        }
        filtered
    }.count()

    println(count)
}

private fun part2Solution(inputLines: List<String>) {
    val hypernetRegex = """\[([a-z]*)\]""".toRegex() // matches [...] blocks
    val babRegex = """(?=((.).\2))""".toRegex() // positive look ahead
    var count = 0
    inputLines.forEach {
        val supernets = it.split(hypernetRegex)
        hypernetRegex.findAll(it).forEach hypernets@ { hypernetMatch ->
            val hypernet = hypernetMatch.groups[1]!!.value
            babRegex.findAll(hypernet).forEach { babMatch ->
                val bab = babMatch.groups[1]?.value
                if (bab != null && bab[0] != bab[1]) {
                    val aba = "${bab[1]}${bab[0]}${bab[1]}"
                    if (supernets.filter { it.contains(aba) }.isNotEmpty()) {
                        count++
                        return@hypernets
                    }
                }
            }
        }
    }
    println(count)
}