package net.ilkinulas.adventofcode.day04

import java.io.File

val regex = Regex("(([a-z]+-)+)([0-9]+)\\[([a-z]+)\\]")

data class Room(val name: String, val sectorId: Int, val checksum: String) {

    companion object {
        fun create(rawData: String): Room {
            val match = regex.matchEntire(rawData)
            return match!!.let {
                Room(match.groups[1]!!.value, match.groups[3]!!.value.toInt(), match.groups[4]!!.value)
            }
        }
    }

    val nameWithoutDashes
        get() = name.filterNot { it == '-' }

    val occurrences by lazy {
        nameWithoutDashes.groupBy { it }
                .map { it.key to it.value.size }
                .sortedWith(compareBy({ 0 - it.second }, { it.first }))
    }

    val isReal by lazy {
        occurrences.take(5).map { it.first }.joinToString("") == checksum
    }

    val decryptedRoomName
        get() = name.map { if (it == '-') ' ' else it + (sectorId % 26) }.joinToString("")

}

fun main(args: Array<String>) {
    val rooms = File("src/main/resources/input04.txt").readLines().map { Room.create(it) }

    var sumSectorId = rooms.filter { it.isReal }.sumBy { it.sectorId }
    var northRoom = rooms.filter { it.isReal }.map { it.sectorId to it.decryptedRoomName }.filter { it.second.contains("north") }

    println(sumSectorId)
    println(northRoom)
}