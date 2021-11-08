import utils.readInputBlock
import kotlin.math.abs

/** [https://adventofcode.com/2018/day/9] */
class Day9 : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("(\\d+) players; last marble is worth (\\d+) points")
        val (players, maxMarble) = pattern.matchEntire(readInputBlock("9.txt"))!!.destructured.toList()
            .map(String::toInt)

        var currentPlayer = 0
        var currentIndex = 0
        var currentMarble = 1
        val marbles = mutableListOf(0)
        val scores = (0 until players).associateWith { 0 }.toMutableMap()
        while (currentMarble <= maxMarble) {
            // println("[${currentPlayer + 1}] ${marbles.joinToString(" ")}")
            if (currentMarble % 23 == 0) {
                val indexToRemove =
                    ((currentIndex - 7) % marbles.size).let { if (it < 0) marbles.size - abs(it) else it }
                scores[currentPlayer] = scores.getValue(currentPlayer) + currentMarble + marbles.removeAt(indexToRemove)
                currentIndex = indexToRemove
            } else {
                currentIndex = ((currentIndex + 1) % marbles.size) + 1
                marbles.add(currentIndex, currentMarble)
            }
            currentMarble++
            currentPlayer = (currentPlayer + 1) % players
        }

        return scores.maxOf { it.value }
    }
}

fun main() {
    println(Day9().run(part2 = false))
}