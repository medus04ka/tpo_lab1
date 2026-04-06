import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

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
    fun changesLocation() {
        creature.moveTo(beach)

        assertEquals(beach, creature.location)
    }

    @Test
    fun throwsException() {
        assertThrows<IllegalStateException> {
            creature.moveTo(sky)
        }
    }

    @Test
    fun throwsWhenLocation() {
        val start = CoordinateLocation("Старт", LocationType.PAVEMENT, 0, 0)
        val far = CoordinateLocation("Далеко", LocationType.BEACH, 10, 0)

        val limitedCreature = object : Creature(start) {
            override fun maxDistance() = 5.0
        }

        assertThrows<IllegalStateException> {
            limitedCreature.moveTo(far)
        }
    }

    @Test
    fun moveToAbstract() {
        val start = CoordinateLocation("Старт", LocationType.BEACH, 0, 0)
        val target = AbstractLocation("Абстракт", LocationType.BEACH)

        val creature = object : Creature(start) {}

        assertDoesNotThrow {
            creature.moveTo(target)
        }
    }


}