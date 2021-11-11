import utils.readInputLines

/** [https://adventofcode.com/2018/day/12] */
class Day12 : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val input = readInputLines("12.txt")
        val plants = input.first().split(": ")[1].withIndex()
            .associate { it.index to it.value }.toMutableMap().withDefault { '.' }
        val notes = input.drop(2).associate {
            val (state, result) = it.split(" => ")
            state to result.first()
        }

        fun grow(current: MutableMap<Int, Char>) = current.apply {
            val minIndex = keys.minOrNull()!!
            val maxIndex = keys.maxOrNull()!!
            set(minIndex - 1, '.')
            set(maxIndex + 1, '.')
        }.mapValues { (index) ->
            val nearby = (index - 2..index + 2).map { current.getValue(it) }.joinToString("")
            notes.getOrDefault(nearby, '.')
        }.toMutableMap().withDefault { '.' }

        var current = plants
        repeat(20) {
            current = grow(current)
        }

        return current.filterValues { it == '#' }.keys.sum()
    }
}

fun main() {
    println(Day12().run(part2 = false))
}