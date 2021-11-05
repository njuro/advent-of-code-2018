import utils.readInputBlock

/** [https://adventofcode.com/2018/day/5] */
class Reactions : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val input = readInputBlock("5.txt").toMutableList()

        return if (part2) {
            ('A'..'Z').zip('a'..'z')
                .minOf { (l, u) -> react(input.filter { it != l && it != u }.toMutableList()) }
        } else react(input)
    }

    private fun react(input: MutableList<Char>): Int {
        while (true) {
            input.withIndex().zipWithNext()
                .firstOrNull { (a, b) ->
                    a.value.equals(b.value, ignoreCase = true) && !a.value.equals(
                        b.value,
                        ignoreCase = false
                    )
                }
                ?.also { (a, _) -> input.removeAt(a.index); input.removeAt(a.index) }
                ?: break
        }

        return input.size
    }
}

fun main() {
    println(Reactions().run(part2 = true))
}