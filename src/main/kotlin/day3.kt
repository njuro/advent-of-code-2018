import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2018/day/3] */
class Areas : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")
        val areas = readInputLines("3.txt").associate { line ->
            val (id, offsetX, offsetY, width, height) = pattern.matchEntire(line)!!.destructured
            id.toInt() to (offsetX.toInt() until offsetX.toInt() + width.toInt()).flatMap { x ->
                (offsetY.toInt() until offsetY.toInt() + height.toInt()).map { y ->
                    Coordinate(x, y)
                }
            }.toSet()
        }

        return if (part2) {
            areas.firstNotNullOf { (id, coords) ->
                id.takeIf {
                    areas.filterKeys { it != id }.values.none { otherCoords ->
                        coords.intersect(otherCoords).isNotEmpty()
                    }
                }
            }
        } else {
            areas.values.flatten().groupingBy { it }.eachCount().count { it.value > 1 }
        }
    }
}

fun main() {
    println(Areas().run(part2 = true))
}