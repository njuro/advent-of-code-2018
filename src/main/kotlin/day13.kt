import utils.Coordinate
import utils.Direction
import utils.readInputLines

/** [https://adventofcode.com/2018/day/13] */
class Day13 : AdventOfCodeTask {

    data class Cart(var location: Coordinate, var direction: Direction, var nextTurn: Direction = Direction.LEFT) {
        fun generateNextTurn() {
            nextTurn = when (nextTurn) {
                Direction.LEFT -> Direction.UP
                Direction.UP -> Direction.RIGHT
                else -> Direction.LEFT
            }
        }
    }

    override fun run(part2: Boolean): Any {

        val carts = mutableSetOf<Cart>()
        val map = readInputLines("13.txt").flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                val track = with(c) {
                    when {
                        this in setOf('^', 'v') -> '|'
                        this in setOf('>', '<') -> '-'
                        this in setOf('|', '-', '/', '\\', '+') -> this
                        isWhitespace() -> null
                        else -> throw IllegalArgumentException("Unexpected character [$this]")
                    }
                }

                val coordinate = Coordinate(x, y)
                when (c) {
                    '^' -> Direction.UP
                    'v' -> Direction.DOWN
                    '>' -> Direction.RIGHT
                    '<' -> Direction.LEFT
                    else -> null
                }?.run { carts.add(Cart(coordinate, this)) }

                if (track == null) null else coordinate to track
            }
        }.toMap()

        var result: Coordinate? = null
        while (true) {
            carts.sortedWith(Comparator.comparingInt<Cart> { it.location.y }.thenBy { it.location.x })
                .forEach { cart ->
                    val newLocation = cart.location.move(cart.direction, offset = true)
                    carts.firstOrNull { it.location == newLocation }?.let {
                        if (part2) {
                            carts.removeIf { it.location in setOf(cart.location, newLocation) }
                        } else if (result == null) {
                            result = newLocation
                        }
                        return@forEach
                    }
                    cart.location = newLocation
                    cart.direction = when (map[newLocation]!!) {
                        '/' -> when (cart.direction) {
                            Direction.UP -> cart.direction.turnRight()
                            Direction.DOWN -> cart.direction.turnRight()
                            Direction.RIGHT -> cart.direction.turnLeft()
                            Direction.LEFT -> cart.direction.turnLeft()
                        }
                        '\\' -> when (cart.direction) {
                            Direction.UP -> cart.direction.turnLeft()
                            Direction.DOWN -> cart.direction.turnLeft()
                            Direction.RIGHT -> cart.direction.turnRight()
                            Direction.LEFT -> cart.direction.turnRight()
                        }
                        '+' -> when (cart.nextTurn) {
                            Direction.LEFT -> cart.direction.turnLeft()
                            Direction.RIGHT -> cart.direction.turnRight()
                            else -> cart.direction
                        }.also { cart.generateNextTurn() }
                        else -> cart.direction
                    }
                }

            if (part2 && carts.size == 1) {
                result = carts.first().location
            }

            if (result != null) {
                break
            }
        }

        return "${result!!.x},${result!!.y}"
    }
}

fun main() {
    println(Day13().run(part2 = true))
}