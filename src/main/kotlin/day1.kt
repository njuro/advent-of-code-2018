import utils.readInputLines

/** [https://adventofcode.com/2018/day/1] */
class Frequencies : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val frequencies = readInputLines("1.txt").map(String::toInt)

        return if (part2) {
            val buffer = mutableSetOf<Int>()
            generateSequence(frequencies) { it }
                .flatten()
                .runningFold(0) { acc, i -> acc + i }
                .first { !buffer.add(it) }
        } else frequencies.sum()
    }
}

fun main() {
    println(Frequencies().run(part2 = true))
}