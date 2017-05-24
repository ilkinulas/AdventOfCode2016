package net.ilkinulas.adventofcode

import java.io.File

fun main(args: Array<String>) {
    val context = Array(8) { mutableListOf<Char>() }

    File("src/main/resources/input06.txt").forEachLine { line ->
        line.forEachIndexed { index, char ->
            context[index].add(char)
        }
    }

    val answer1 = context.map { processList(it).first().first }.joinToString("")
    println(answer1)
    val answer2 = context.map { processList(it).last().first }.joinToString("")
    println(answer2)
}

private fun processList(list: List<Char>) = list.groupBy { it }.map { it.key to it.value.size }.sortedByDescending { it.second }

