import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2018/day/6] */
class Locations : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val locations = readInputLines("6.txt").map {
            val (x, y) = it.split(", ")
            Coordinate(x.toInt(), y.toInt())
        }

        val topLeft = Coordinate(locations.minOf { it.x }, locations.minOf { it.y })
        val bottomRight = Coordinate(locations.maxOf { it.x }, locations.maxOf { it.y })
        fun Coordinate.isEdge() = x == topLeft.x || x == bottomRight.x || y == topLeft.y || y == bottomRight.y

        val cells = (topLeft.x..bottomRight.x).flatMap { x ->
            (topLeft.y..bottomRight.y).map { y ->
                Coordinate(x, y)
            }
        }

        val distances =
            cells.associateWith { cell -> locations.associateWith { location -> cell.distanceTo(location) } }

        return if (part2) {
            distances.map { it.value.values.sum() }.count { it < 10000 }
        } else {
            val infinite = mutableSetOf<Coordinate>()
            cells.mapNotNull { cell ->
                distances.getValue(cell).closest()?.also {
                    if (cell.isEdge()) {
                        infinite.add(it)
                    }
                }
            }
                .filterNot { it in infinite }
                .groupingBy { it }
                .eachCount()
                .maxOf { it.value }
        }
    }

    private fun Map<Coordinate, Int>.closest(): Coordinate? =
        filterValues { it == values.minOrNull() }.keys.takeIf { it.size == 1 }?.firstOrNull()
}

fun main() {
    println(Locations().run(part2 = true))
}