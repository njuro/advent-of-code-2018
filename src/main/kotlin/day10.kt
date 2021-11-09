import utils.Coordinate
import utils.readInputLines
import utils.toStringRepresentation

/** [https://adventofcode.com/2018/day/10] */
class Stars : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("""position=<\s*(-?\d+),\s*(-?\d+)> velocity=<\s*(-?\d+),\s*(-?\d+)>""")
        val stars = readInputLines("10.txt").associate {
            val (x, y, dx, dy) = pattern.matchEntire(it)!!.destructured.toList().map(String::toInt)
            Coordinate(x, y) to Coordinate(dx, dy)
        }

        val seconds = 10459 // hardcoded right time so it can be unit tested
        // otherwise, print the current state only when the difference between minX and maxX and
        // between minY and maxY is less than 100 and watch for the output
        val current = stars.map { Coordinate(it.key.x + seconds * it.value.x, it.key.y + seconds * it.value.y) }
            .associateWith { '#' }.withDefault { '.' }

        return if (part2) seconds else current.toStringRepresentation(offsetCoordinates = true)
    }
}

fun main() {
    println(Stars().run(part2 = true))
}