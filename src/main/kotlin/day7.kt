import utils.readInputLines

/** [https://adventofcode.com/2018/day/7] */
class Instructions : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("Step (\\w) must be finished before step (\\w) can begin\\.")
        val steps = mutableSetOf<String>()
        val requirements = mutableMapOf<String, Set<String>>().withDefault { emptySet() }

        readInputLines("7.txt").forEach { line ->
            val (requirement, target) = pattern.matchEntire(line)!!.destructured
            steps.add(requirement)
            steps.add(target)
            requirements.merge(target, setOf(requirement)) { a, b -> a + b }
        }


        if (part2) {
            val tasks = mutableMapOf<String, Int>()
            var seconds = 0
            while (true) {
                tasks.toMutableMap().forEach { (task, timer) ->
                    if (timer == 0) {
                        tasks.remove(task)
                        steps.remove(task)
                    } else {
                        tasks[task] = tasks.getValue(task) - 1
                    }
                }

                val candidates = steps.filter {
                    it !in tasks.keys
                        && requirements.getValue(it).all { req -> req !in steps }
                }.sorted().toMutableList()

                if (candidates.isEmpty() && tasks.isEmpty()) {
                    break
                }

                while (candidates.isNotEmpty() && tasks.size < 5) {
                    val task = candidates.removeFirst()
                    tasks[task] = task.first() - 'A' + 60
                }

                seconds++
            }

            return seconds
        } else {
            val completed = StringBuilder()

            while (steps.isNotEmpty()) {
                val next = steps.filter { requirements.getValue(it).all { req -> req !in steps } }.minOf { it }
                completed.append(next)
                steps.remove(next)
            }

            return completed.toString()
        }
    }
}

fun main() {
    println(Instructions().run(part2 = true))
}