import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AdventOfCodeTasksTest {

    @Test
    fun day1() {
        runTaskTest(Frequencies(), 433, 256)
    }

    @Test
    fun day2() {
        runTaskTest(Day2(), 5704, "umdryabviapkozistwcnihjqx")
    }

    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}