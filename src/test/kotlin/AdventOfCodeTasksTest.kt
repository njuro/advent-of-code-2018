import org.junit.jupiter.api.Assertions.assertEquals

class AdventOfCodeTasksTest {
    
    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}