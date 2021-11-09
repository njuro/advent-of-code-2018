import utils.readInputBlock

/** [https://adventofcode.com/2018/day/11] */
class Fuel : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val gridNumber = readInputBlock("11.txt").toInt()
        val dimension = 300
        val powers = Array(dimension) { IntArray(dimension) }
        (0 until dimension).flatMap { y ->
            (0 until dimension).map { x ->
                val rackId = x + 10
                val powerLevel = (((((rackId * y) + gridNumber) * rackId) / 100) % 10) - 5
                val up = if (y == 0) 0 else powers[y - 1][x]
                val left = if (x == 0) 0 else powers[y][x - 1]
                val upperLeft = if (x == 0 || y == 0) 0 else powers[y - 1][x - 1]
                powers[y][x] = powerLevel + up + left - upperLeft
            }
        }

        return (1..dimension).let { if (part2) it else listOf(4) }.map { square ->
            val maxPower = (0..dimension - square).flatMap { y ->
                (0..dimension - square).map { x -> x to y }
            }.map { (x0, y0) ->
                val x1 = x0 + square - 1
                val y1 = y0 + square - 1
                val power = powers[y1][x1] + powers[y0][x0] - powers[y0][x1] - powers[y1][x0]
                Triple(x0 + 1, y0 + 1, power)
            }.maxByOrNull { it.third }!!
            square to maxPower
        }.maxByOrNull { it.second.third }!!
            .let {
                if (part2)
                    "${it.second.first},${it.second.second},${it.first - 1}"
                else
                    "${it.second.first},${it.second.second}"
            }
    }
}

fun main() {
    println(Fuel().run(part2 = false))
}