import utils.readInputBlock

/** [https://adventofcode.com/2018/day/16] */
class Registers : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val (samples, program) = readInputBlock("16.txt").split("\n\n\n").map(String::trim)
        var registers = mutableListOf<Int>()
        val operations: Map<String, ((Pair<Int, Int>) -> Int)> = mapOf(
            "addr" to { registers[it.first] + registers[it.second] },
            "addi" to { registers[it.first] + it.second },
            "mulr" to { registers[it.first] * registers[it.second] },
            "muli" to { registers[it.first] * it.second },
            "banr" to { registers[it.first] and registers[it.second] },
            "bani" to { registers[it.first] and it.second },
            "borr" to { registers[it.first] or registers[it.second] },
            "bori" to { registers[it.first] or it.second },
            "setr" to { registers[it.first] },
            "seti" to { it.first },
            "gtir" to { if (it.first > registers[it.second]) 1 else 0 },
            "gtri" to { if (registers[it.first] > it.second) 1 else 0 },
            "gtrr" to { if (registers[it.first] > registers[it.second]) 1 else 0 },
            "equir" to { if (it.first == registers[it.second]) 1 else 0 },
            "equri" to { if (registers[it.first] == it.second) 1 else 0 },
            "equrr" to { if (registers[it.first] == registers[it.second]) 1 else 0 },
        )

        val candidates = (0..15).associateWith { mutableSetOf<String>() }
        val ambiguous = samples.split("\n\n").count { sample ->
            val (before, query, after) = sample.split("\n")
            val start = before.parseRegisters()
            val end = after.parseRegisters()
            val instruction = query.parseInstruction()
            val matching = operations.filterValues { operation ->
                registers = start.toMutableList()
                registers.execute(instruction, operation)
                registers == end
            }.also { it.keys.forEach { opcode -> candidates[instruction.first()]!!.add(opcode) } }
            matching.size >= 3
        }

        return if (part2) {
            while (candidates.any { it.value.size > 1 }) {
                candidates.filterValues { it.size == 1 }.values.forEach { determined ->
                    candidates.values.filter { it.size > 1 }.forEach { it.remove(determined.first()) }
                }
            }
            val opcodes = candidates.mapValues { it.value.first() }

            registers = mutableListOf(0, 0, 0, 0)
            program.trim().split("\n").map { it.parseInstruction() }.forEach { instruction ->
                val operation = operations[opcodes[instruction.first()]]!!
                registers.execute(instruction, operation)
            }
            registers[0]
        } else ambiguous
    }

    private fun String.parseRegisters() =
        substringAfter('[').substringBefore(']').split(", ").map(String::toInt).toMutableList()

    private fun String.parseInstruction() = split(" ").map(String::toInt)

    private fun MutableList<Int>.execute(instruction: List<Int>, operation: ((Pair<Int, Int>) -> Int)) =
        set(instruction.last(), operation(instruction[1] to instruction[2]))
}

fun main() {
    println(Registers().run(part2 = true))
}