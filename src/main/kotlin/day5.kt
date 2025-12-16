import utils.readInputBlock

/** [https://adventofcode.com/2018/day/5] */
class Reactions : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val polymer = readInputBlock("5.txt")
        val pattern =
            ('a'..'z').flatMap { setOf("$it${it.uppercase()}", "${it.uppercase()}$it") }.joinToString("|").toRegex()

        fun react(polymer: String): Int =
            polymer.replace(pattern, "").let { next -> if (next.length == polymer.length) next.length else react(next) }

        return if (part2) {
            ('a'..'z').minOf { react(polymer.replace("$it|${it.uppercase()}".toRegex(), "")) }
        } else react(polymer)
    }
}

fun main() {
    println(Reactions().run(part2 = true))
}
