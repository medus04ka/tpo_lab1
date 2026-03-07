import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreatureTest {

    private lateinit var creature: TestCreature
    private lateinit var pavement: CoordinateLocation
    private lateinit var beach: CoordinateLocation
    private lateinit var sky: AbstractLocation

    private class TestCreature(location: Location) : Creature(location)

    @BeforeEach
    fun setUp() {
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        sky = AbstractLocation("Небо", LocationType.SKY)
        creature = TestCreature(pavement)
    }

    @Test
    fun moveTo_ReachableLocation_ChangesLocation() {
        creature.moveTo(beach)

        assertEquals(beach, creature.location)
    }

    @Test
    fun moveTo_UnreachableLocation_ThrowsException() {
        assertThrows(IllegalStateException::class.java) {
            creature.moveTo(sky)
        }
    }
}