import utils.readInputLines

/** [https://adventofcode.com/2018/day/2] */
class Day2 : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val input = readInputLines("2.txt")

        return if (part2) {
            input.firstNotNullOf { first ->
                input.firstOrNull { first distanceTo it == 1 }?.let { second ->
                    first.filter { it in second }
                }
            }
        } else {
            val data = input.map { id ->
                val occurrences = id.groupingBy { it }.eachCount()
                occurrences.any { it.value == 2 } to occurrences.any { it.value == 3 }
            }

            data.count { it.first } * data.count { it.second }
        }
    }

    private infix fun String.distanceTo(other: String) =
        mapIndexed { index, c -> if (other[index] == c) 0 else 1 }.sum()
}

fun main() {
    println(Day2().run(part2 = true))
}