package net.ilkinulas.adventofcode.day01

//Day 1: No Time for a Taxicab
val input = "L1, L5, R1, R3, L4, L5, R5, R1, L2, L2, L3, R4, L2, R3, R1, L2, R5, R3, L4, R4, L3, R3, R3, L2, R1, L3, R2, L1, R4, L2, R4, L4, R5, L3, R1, R1, L1, L3, L2, R1, R3, R2, L1, R4, L4, R2, L189, L4, R5, R3, L1, R47, R4, R1, R3, L3, L3, L2, R70, L1, R4, R185, R5, L4, L5, R4, L1, L4, R5, L3, R2, R3, L5, L3, R5, L1, R5, L4, R1, R2, L2, L5, L2, R4, L3, R5, R1, L5, L4, L3, R4, L3, L4, L1, L5, L5, R5, L5, L2, L1, L2, L4, L1, L2, R3, R1, R1, L2, L5, R2, L3, L5, L4, L2, L1, L2, R3, L1, L4, R3, R3, L2, R5, L1, L3, L3, L3, L5, R5, R1, R2, L3, L2, R4, R1, R1, R3, R4, R3, L3, R3, L5, R2, L2, R4, R5, L4, L3, L1, L5, L1, R1, R2, L1, R3, R4, R5, R2, R3, L2, L1, L5"

enum class Turn(val i: Int) { L(-1), R(1) }
enum class Direction { N, E, S, W }

val DIRECTIONS = arrayOf(Direction.N, Direction.E, Direction.S, Direction.W)

class Move(val turn: Turn, val step: Int)

class Position(val x: Int = 0, val y: Int = 0, val direction: Direction = Direction.N) {
    val distance: Int
        get() = x + y

    val coordinates: Pair<Int, Int>
        get() = x to y
}

fun createMove(s: String): Move {
    val step = s.substring(1, s.length)
    return when (s[0]) {
        'L' -> Move(Turn.L, step.toInt())
        else -> Move(Turn.R, step.toInt())
    }
}

fun move(from: Position, move: Move): Position {
    val newDirection = calculateNewDirection(move, from)
    val xy = when (newDirection) {
        Direction.N -> 0 to move.step
        Direction.E -> move.step to 0
        Direction.S -> 0 to -move.step
        Direction.W -> -move.step to 0
    }
    return Position(from.x + xy.first, from.y + xy.second, newDirection)
}

fun wayPoints(from: Position, move: Move): List<Position> {
    val newDirection = calculateNewDirection(move, from)
    return (1..move.step).map {
        when (newDirection) {
            Direction.N -> Position(from.x, from.y + it, newDirection)
            Direction.E -> Position(from.x + it, from.y, newDirection)
            Direction.S -> Position(from.x, from.y - it, newDirection)
            Direction.W -> Position(from.x - it, from.y, newDirection)
        }
    }
}

private fun calculateNewDirection(move: Move, from: Position): Direction {
    return when (move.turn) {
        Turn.L -> DIRECTIONS[if (from.direction.ordinal == 0) 3 else from.direction.ordinal - 1]
        Turn.R -> DIRECTIONS[(from.direction.ordinal + 1) % 4]
    }
}

fun calculateDistance() = input.split(", ")
        .map { createMove(it) }
        .fold(Position()) { pos, element -> move(pos, element) }.distance

fun findRevisitedPosition(input: String, initialPosition: Position): Position? {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val moves = input.split(", ").map { createMove(it) }
    var tmpPosition = initialPosition
    moves.forEach {
        val wayPoints = wayPoints(tmpPosition, it)
        wayPoints.forEach {
            if (visited.contains(it.coordinates)) {
                return it
            }
        }
        visited.addAll(wayPoints.map { it.coordinates })
        tmpPosition = wayPoints.last()
    }
    return null
}


fun main(args: Array<String>) {
    println(calculateDistance())

    println(findRevisitedPosition(input, Position())?.distance)
}