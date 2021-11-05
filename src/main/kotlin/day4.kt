import utils.readInputLines
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/** [https://adventofcode.com/2018/day/4] */
class Guards : AdventOfCodeTask {

    data class Stats(val total: Int, val mostProbable: Pair<Int, Int>)

    override fun run(part2: Boolean): Any {
        val pattern = Regex("\\[(.+)] (.+)")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val data = readInputLines("4.txt").associate {
            val (timestamp, message) = pattern.matchEntire(it)!!.destructured
            LocalDateTime.parse(timestamp, formatter) to message
        }.toSortedMap()

        val shifts = mutableMapOf<Int, MutableList<Int>>().withDefault { mutableListOf() }
        var currentId = -1
        var currentSleep = -1

        data.forEach { (timestamp, message) ->
            with(message) {
                when {
                    startsWith("Guard") -> currentId = message.split(" ")
                        .first { it.startsWith("#") }.substring(1).toInt()
                    startsWith("falls") -> currentSleep = timestamp.minute
                    startsWith("wakes") -> shifts[currentId] = shifts.getValue(currentId)
                        .apply { addAll(currentSleep until timestamp.minute) }
                }
            }
        }

        val stats = shifts.mapValues { (_, minutes) ->
            Stats(minutes.size, minutes.groupingBy { it }.eachCount().maxByOrNull { it.value }!!.toPair())
        }

        val selector: (Map.Entry<Int, Stats>) -> Int = if (part2) {
            { it.value.mostProbable.second }
        } else {
            { it.value.total }
        }
        return stats.maxByOrNull(selector)!!.let { it.key * it.value.mostProbable.first }
    }
}

fun main() {
    println(Guards().run(part2 = true))
}