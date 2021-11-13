import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AdventOfCodeTasksTest {

    @Test
    fun day1() {
        runTaskTest(Frequencies(), 433, 256)
    }

    @Test
    fun day2() {
        runTaskTest(Boxes(), 5704, "umdryabviapkozistwcnihjqx")
    }

    @Test
    fun day3() {
        runTaskTest(Areas(), 118539, 1270)
    }

    @Test
    fun day4() {
        runTaskTest(Guards(), 146622, 31848)
    }

    @Test
    fun day5() {
        runTaskTest(Reactions(), 11042, 6872)
    }

    @Test
    fun day6() {
        runTaskTest(Locations(), 4589, 40252)
    }

    @Test
    fun day7() {
        runTaskTest(Instructions(), "BFLNGIRUSJXEHKQPVTYOCZDWMA", 880)
    }

    @Test
    fun day8() {
        runTaskTest(Nodes(), 42501, 30857)
    }

    @Test
    fun day9() {
        runTaskTest(Marbles(), 393229L, 3273405195L)
    }

    @Test
    fun day10() {
        runTaskTest(
            Stars(), """
# . . . . # . . # # # # # # . . # . . . . # . . # # # # # . . . # . . . . . . . # # # # # . . . # . . . . # . . # . . . . # 
# # . . . # . . # . . . . . . . # . . . . # . . # . . . . # . . # . . . . . . . # . . . . # . . # . . . . # . . # . . . # . 
# # . . . # . . # . . . . . . . . # . . # . . . # . . . . # . . # . . . . . . . # . . . . # . . . # . . # . . . # . . # . . 
# . # . . # . . # . . . . . . . . # . . # . . . # . . . . # . . # . . . . . . . # . . . . # . . . # . . # . . . # . # . . . 
# . # . . # . . # # # # # . . . . . # # . . . . # # # # # . . . # . . . . . . . # # # # # . . . . . # # . . . . # # . . . . 
# . . # . # . . # . . . . . . . . . # # . . . . # . . . . . . . # . . . . . . . # . . # . . . . . . # # . . . . # # . . . . 
# . . # . # . . # . . . . . . . . # . . # . . . # . . . . . . . # . . . . . . . # . . . # . . . . # . . # . . . # . # . . . 
# . . . # # . . # . . . . . . . . # . . # . . . # . . . . . . . # . . . . . . . # . . . # . . . . # . . # . . . # . . # . . 
# . . . # # . . # . . . . . . . # . . . . # . . # . . . . . . . # . . . . . . . # . . . . # . . # . . . . # . . # . . . # . 
# . . . . # . . # # # # # # . . # . . . . # . . # . . . . . . . # # # # # # . . # . . . . # . . # . . . . # . . # . . . . #
""".trimIndent(), 10459
        )
    }

    @Test
    fun day11() {
        runTaskTest(Fuel(), "235,48", "285,113,11")
    }

    @Test
    fun day12() {
        runTaskTest(Plants(), 4110, 2_650_000_000_466L)
    }

    @Test
    fun day13() {
        runTaskTest(Day13(), "41,22", "84,90")
    }

    @Test
    fun day14() {
        runTaskTest(Recipes(), 1631191756, 20219475)
    }

    @Test
    fun day15() {
        runTaskTest(Goblins(), 224370, 45539)
    }

    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}