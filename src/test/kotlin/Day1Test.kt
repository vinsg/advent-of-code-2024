import ca.vinsg.Day1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun `part 1`() {
        assertEquals(1319616, Day1.part1())
    }

    @Test
    fun `part 2`() {
        assertEquals(27267728, Day1.part2())
    }
}