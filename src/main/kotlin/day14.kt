import utils.readInputBlock

/** [https://adventofcode.com/2018/day/14] */
class Recipes : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val input = readInputBlock("14.txt").toInt()
        val recipes = mutableListOf(3, 7)
        var first = 0
        var second = 1

        fun makeRecipes() {
            val next = (recipes[first] + recipes[second]).toString().toList().map(Char::digitToInt)
            recipes.addAll(next)
            first = (first + recipes[first] + 1) % recipes.size
            second = (second + recipes[second] + 1) % recipes.size
        }

        return if (part2) {
            val value = input.toString()
            while (recipes.size <= value.length ||
                value !in recipes.subList(recipes.size - value.length - 1, recipes.size).joinToString("")
            ) {
                makeRecipes()
            }
            recipes.joinToString("").indexOf(value)
        } else {
            repeat(input + 8) { makeRecipes() }
            recipes.subList(input, input + 10).joinToString("").toInt()
        }
    }
}

fun main() {
    println(Recipes().run(part2 = true))
}