import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2018/day/15] */
class Goblins : AdventOfCodeTask {

    data class Unit(val type: Char, var location: Coordinate, var damage: Int = 3, var health: Int = 200) {
        fun attack(other: Unit) {
            other.health -= damage
        }

        val dead: Boolean
            get() = health <= 0
    }

    companion object {
        val unitOrderComparator: Comparator<Unit> =
            Comparator.comparingInt<Unit> { it.location.y }.thenComparingInt { it.location.x }
        val attackOrderComparator: Comparator<Unit> =
            Comparator.comparingInt<Unit> { it.health }.thenComparing(unitOrderComparator)

        val originalUnits = mutableSetOf<Unit>()
        val originalMap = readInputLines("15.txt").flatMapIndexed { y, row ->
            row.trim().mapIndexedNotNull { x, element ->
                val coordinate = Coordinate(x, y)
                if (element in setOf('E', 'G')) {
                    originalUnits.add(Unit(element, coordinate))
                }
                coordinate to element
            }
        }.toMap()
    }

    private fun play(elvesMustWin: Boolean = false, elvesDamage: Int = 3): Int {
        val units = originalUnits.map { it.copy(damage = if (it.type == 'E') elvesDamage else 3) }.toSet()
        val map = originalMap.toMutableMap().withDefault { '#' }

        var gameOver = false

        fun processAttack(attacker: Unit, victim: Unit) {
            attacker.attack(victim)
            if (victim.dead) {
                map[victim.location] = '.'
                if (elvesMustWin && victim.type == 'E') {
                    gameOver = true
                }
                if (units.filterNot(Unit::dead).groupBy(Unit::type).size == 1) {
                    gameOver = true
                }
            }
        }

        fun processMove(unit: Unit, newLocation: Coordinate) {
            map[unit.location] = '.'
            unit.location = newLocation
            map[unit.location] = unit.type
        }

        fun attemptAttack(attacker: Unit, enemies: List<Unit>): Boolean {
            val adjacent = attacker.location.adjacent(true).values
            val reachable = enemies.filter { it.location in adjacent }.sortedWith(attackOrderComparator)
            return if (reachable.isNotEmpty()) {
                processAttack(attacker, reachable.first())
                true
            } else false
        }

        fun attemptMove(initial: Coordinate, destinations: Set<Coordinate>): Coordinate? {
            if (destinations.isEmpty()) {
                return null
            }
            val directNeighbours = initial.adjacentInOrder().filter { map.getValue(it) == '.' }
            directNeighbours.firstOrNull { it in destinations }?.let { return it }

            val seen = mutableSetOf(initial)
            val queue = ArrayDeque<Pair<Coordinate, Coordinate>>().apply { addAll(directNeighbours.map { it to it }) }

            while (queue.isNotEmpty()) {
                val (start, end) = queue.removeFirst()
                seen.add(end)
                val neighbours = end.adjacentInOrder().filter { it !in seen && map.getValue(it) == '.' }
                for (neighbour in neighbours) {
                    if (neighbour in destinations) {
                        return start
                    }
                    queue.addLast(start to neighbour)
                }
            }

            return null
        }

        var rounds = 0
        run game@{
            while (true) {
                units.sortedWith(unitOrderComparator).forEach { unit ->
                    if (gameOver) {
                        return@game
                    }
                    if (unit.dead) {
                        return@forEach
                    }

                    val enemies = units.living().getValue(if (unit.type == 'G') 'E' else 'G')

                    if (!attemptAttack(unit, enemies)) {
                        val destinations = enemies
                            .flatMap { it.location.adjacent(true).values }
                            .filter { map.getValue(it) == '.' }

                        attemptMove(unit.location, destinations.toSet())?.let {
                            processMove(unit, it)
                            attemptAttack(unit, enemies)
                        }
                    }
                }
                rounds++
            }
        }

        if (elvesMustWin && units.living().containsKey('G')) {
            return -1
        }

        return rounds * units.filterNot(Unit::dead).sumOf(Unit::health)
    }

    override fun run(part2: Boolean): Any {
        return if (part2) {
            generateSequence(4) { it + 1 }.map { play(elvesMustWin = true, elvesDamage = it) }
                .first { it != -1 }
        } else play()
    }

    private fun Set<Unit>.living() = filterNot(Unit::dead).groupBy(Unit::type)
    private fun Coordinate.adjacentInOrder() =
        listOf(Coordinate(x, y - 1), Coordinate(x - 1, y), Coordinate(x + 1, y), Coordinate(x, y + 1))
}

fun main() {
    println(Goblins().run(part2 = true))
}