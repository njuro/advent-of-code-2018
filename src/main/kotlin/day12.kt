import utils.readInputLines

/** [https://adventofcode.com/2018/day/12] */
class Plants : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {

        val input = readInputLines("12.txt")

        val notes = input.drop(2).associate {
            val (state, result) = it.split(" => ")
            state to result.first()
        }

        fun MutableMap<Int, Char>.grow() = apply {
            set(keys.minOrNull()!! - 1, '.')
            set(keys.maxOrNull()!! + 1, '.')
        }.mapValues { (index) ->
            notes[(index - 2..index + 2).map { getValue(it) }.joinToString("")]!!
        }.toMutableMap().withDefault { '.' }

        fun MutableMap<Int, Char>.checksum() = filterValues { it == '#' }.keys.sum()

        var plants = input.first().split(": ")[1].withIndex()
            .associate { it.index to it.value }.toMutableMap().withDefault { '.' }

        return if (part2) {
            var generation = 0
            var previousSize = 0
            var previousDiff = 0
            while (true) {
                generation++
                plants = plants.grow()
                val diff = plants.checksum() - previousSize
                if (diff == previousDiff) {
                    break
                }
                previousDiff = diff
                previousSize = plants.checksum()
            }
            previousSize + previousDiff * (50_000_000_000L - generation + 1)
        } else {
            repeat(20) {
                plants = plants.grow()
            }
            plants.checksum()
        }
    }
}

fun main() {
    println(Plants().run(part2 = true))
}