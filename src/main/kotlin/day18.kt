import utils.Coordinate
import utils.readInputLines
import utils.toStringRepresentation

/** [https://adventofcode.com/2018/day/18] */
class Trees : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        var current = readInputLines("18.txt").flatMapIndexed { y, row ->
            row.mapIndexed { x, c -> Coordinate(x, y) to c }
        }.toMap().withDefault { 'X' }

        if (part2) {
            val seen = mutableSetOf<String>()
            var turn = 0
            while (true) {
                val hash = current.toStringRepresentation(offsetCoordinates = true)
                if (hash in seen) {
                    val cycle = turn - seen.indexOf(hash)
                    val remaining = (1_000_000_000 - turn) % cycle
                    repeat(remaining) {
                        current = current.turn()
                    }
                    break
                } else {
                    seen.add(hash)
                }
                turn += 1
                current = current.turn()
            }
        } else {
            repeat(10) {
                current = current.turn()
            }
        }

        return current.values.let { it.count { c -> c == '|' } * it.count { c -> c == '#' } }
    }

    private fun Map<Coordinate, Char>.turn() = map { (coord, c) ->
        val neighbours = coord.neighbours8().map { getValue(it) }
        coord to when (c) {
            '.' -> if (neighbours.count { it == '|' } >= 3) '|' else '.'
            '|' -> if (neighbours.count { it == '#' } >= 3) '#' else '|'
            '#' -> if ('#' in neighbours && '|' in neighbours) '#' else '.'
            else -> throw IllegalStateException()
        }
    }.toMap().withDefault { 'X' }
}

fun main() {
    println(Trees().run(part2 = true))
}
