import utils.readInputBlock

/** [https://adventofcode.com/2018/day/8] */
class Nodes : AdventOfCodeTask {

    data class Node(val children: List<Node>, val metadata: List<Int>) {
        fun checksum(part2: Boolean = false): Int = if (part2) {
            if (children.isEmpty()) metadata.sum() else metadata.sumOf {
                children.getOrNull(it - 1)?.checksum(true) ?: 0
            }
        } else {
            metadata.sum() + children.sumOf(Node::checksum)
        }
    }

    override fun run(part2: Boolean): Any {

        fun parse(iterator: Iterator<Int>): Node {
            val childrenCount = iterator.next()
            val metadataCount = iterator.next()
            val children = (0 until childrenCount).map { parse(iterator) }
            val metadata = (0 until metadataCount).map { iterator.next() }
            return Node(children, metadata)
        }

        return parse(readInputBlock("8.txt").split(" ").map(String::toInt).iterator()).checksum(part2)
    }
}

fun main() {
    println(Nodes().run(part2 = true))
}