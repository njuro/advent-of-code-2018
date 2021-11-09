import utils.readInputBlock
import kotlin.math.absoluteValue

/** [https://adventofcode.com/2018/day/9] */
class Marbles : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("(\\d+) players; last marble is worth (\\d+) points")
        val (players, maxMarble) = pattern.matchEntire(readInputBlock("9.txt"))!!.destructured.toList()
            .map(String::toInt)

        var currentPlayer = 0
        val marbles = ArrayDeque<Int>().apply { add(0) }
        val scores = (0 until players).associateWith { 0L }.toMutableMap()
        (1..maxMarble.let { if (part2) it.times(100) else it }).forEach { currentMarble ->
            if (currentMarble % 23 == 0) {
                marbles.shift(-7)
                scores[currentPlayer % marbles.size] =
                    scores.getValue(currentPlayer) + currentMarble + marbles.removeFirst()
                marbles.shift(1)
            } else {
                marbles.shift(1)
                marbles.addFirst(currentMarble)
            }
            currentPlayer = (currentPlayer + 1) % players
        }

        return scores.maxOf { it.value }
    }

    private fun <T> ArrayDeque<T>.shift(n: Int) =
        when {
            n < 0 -> repeat(n.absoluteValue) {
                addLast(removeFirst())
            }
            else -> repeat(n) {
                addFirst(removeLast())
            }
        }
}

fun main() {
    println(Marbles().run(part2 = true))
}