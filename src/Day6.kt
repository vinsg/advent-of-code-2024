private const val Obstacle = '#'

object Day6 {

    enum class Orientation {
        UP, RIGHT, DOWN, LEFT
    }

    class Guard(var position: Pair<Int, Int>, var orientation: Orientation, val mapLimit: Pair<Int, Int>) {

        fun move() {
            when (orientation) {
                Orientation.UP -> {
                    position = Pair(position.first, position.second - 1)
                }

                Orientation.RIGHT -> {
                    position = Pair(position.first + 1, position.second)
                }

                Orientation.DOWN -> {
                    position = Pair(position.first, position.second + 1)
                }

                Orientation.LEFT -> {
                    position = Pair(position.first - 1, position.second)
                }
            }
        }

        fun rotate() {
            orientation = when (orientation) {
                Orientation.UP -> Orientation.RIGHT
                Orientation.RIGHT -> Orientation.DOWN
                Orientation.DOWN -> Orientation.LEFT
                Orientation.LEFT -> Orientation.UP
            }
        }

        fun isStillOnMap() =
            (position.first >= 0 && position.first <= mapLimit.first) && (position.second >= 0 && position.second <= mapLimit.second)

        fun isObstructed(graph: Graph): Boolean {
            return when (orientation) {
                Orientation.UP -> graph.getOrNull(position.second - 1)?.getOrNull(position.first) == Obstacle
                Orientation.RIGHT -> graph.getOrNull(position.second)?.getOrNull(position.first + 1) == Obstacle
                Orientation.DOWN -> graph.getOrNull(position.second + 1)?.getOrNull(position.first) == Obstacle
                Orientation.LEFT -> graph.getOrNull(position.second)?.getOrNull(position.first - 1) == Obstacle
            }
        }
    }

    fun part1(): Long {
        val fileAsList = readFile("day6").also { println("input: $it") }
        val graph = fileAsList.map { it.toList() }.toList()

        val recordedPositions = mutableSetOf<Position>()

        // get starting position
        val startingPosition = graph.getStartingPosition()
        requireNotNull(startingPosition) { "startingPosition is null" }

        val guard = Guard(
            position = startingPosition,
            orientation = Orientation.UP,
            mapLimit = graph.getMapLimits()
        )

        while (guard.isStillOnMap()) {
            recordedPositions.add(guard.position)

            val guardPos = guard.position
            if (!guard.isObstructed(graph)) {
                guard.move()
            } else {
                println("guard is obstructed at $guardPos")
                guard.rotate()
            }
        }

        println("numberOfUniquePos = ${recordedPositions.size}")
        return recordedPositions.size.toLong()
    }

    fun part2(): Long {
        val fileAsList = readFile("day6").also { println("input: $it") }

        val graph = fileAsList.map { it.toList() }.toList()
        var sum = 0L
        val moveList = mutableSetOf<Position>()

        // find start position
        val startingPosition = graph.getStartingPosition()
        requireNotNull(startingPosition) { "startingPosition is null" }

        val mapLimit = graph.getMapLimits()
        val guard = Guard(
            position = startingPosition,
            orientation = Orientation.UP,
            mapLimit = mapLimit
        )

        // collect all the moves
        while (guard.isStillOnMap()) {
            moveList.add(guard.position)
            if (!guard.isObstructed(graph)) {
                guard.move()
            } else {
                guard.rotate()
            }
        }

        // go through each graph with an obstacle on each move point
        moveList.forEach { pair ->
            // copy graph
            val graphCopy = graph.map { it.toMutableList() }.toMutableList()
            // set obstacle
            graphCopy[pair.second][pair.first] = Obstacle
            // check map
            sum += graphCopy.checkLoop(
                guard = Guard(
                    position = startingPosition,
                    orientation = Orientation.UP,
                    mapLimit = mapLimit
                ),
            )
        }

        println("sum of all moves ${moveList.size}")
        println("sum of all obstacle maps with a loop: $sum")
        return sum
    }

}

/**
 * Takes a starting guard position
 * Returns 1 if the traversal loops on itself else 0
 */
private fun Graph.checkLoop(guard: Day6.Guard): Int {
    val positionList = mutableSetOf<Pair<Position, Day6.Orientation>>()
    while (guard.isStillOnMap()) {
        positionList.add(Pair(guard.position, guard.orientation))
        if (!guard.isObstructed(this)) {
            guard.move()

            if (positionList.contains(Pair(guard.position, guard.orientation))) {
                return 1
            }
        } else {
            guard.rotate()
        }
    }
    return 0
}

private const val Start = '^'

private fun Graph.getStartingPosition(): Position? {
    this.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == Start) {
                return Position(x, y)
            }
        }
    }
    return null
}

private fun Graph.getMapLimits(): Pair<Int, Int> = Pair(this.first().lastIndex, this.lastIndex)

private typealias Graph = List<List<Char>>
private typealias Position = Pair<Int, Int>