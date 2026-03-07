import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HumanTest {

    private lateinit var human: TestHuman
    private lateinit var pavement: CoordinateLocation

    private class TestHuman(
        override var location: Location,
        override val heightCm: Int
    ) : Human(location, heightCm)

    @BeforeEach
    fun setUp() {
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        human = TestHuman(pavement, 175)
    }

    @Test
    fun constructor_SetsLocation() {
        assertEquals(pavement, human.location)
    }

    @Test
    fun constructor_SetsHeight() {
        assertEquals(175, human.heightCm)
    }
}