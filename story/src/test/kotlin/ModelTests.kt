import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ModelTests {

    private val pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
    private val beach = CoordinateLocation("Песок", LocationType.BEACH, 1, 1)
    private val sky = AbstractLocation("Небо", LocationType.SKY)
    private val unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)

    @Test
    fun cargoVal() {
        assertThrows<IllegalArgumentException> {
            Cargo("", 1)
        }
        assertThrows<IllegalArgumentException> {
            Cargo("Груз", 0)
        }
        assertThrows<IllegalArgumentException> {
            Cargo("Груз", 1, Freshness.FRESH, -1)
        }
        assertDoesNotThrow {
            Cargo("Груз", 1, Freshness.STALE, 0)
        }
    }

    @Test
    fun locationTypeVal() {
        assertThrows<IllegalArgumentException> {
            AbstractLocation("Берег", LocationType.BEACH)
        }
        assertThrows<IllegalArgumentException> {
            CoordinateLocation("Небо", LocationType.SKY, 0, 0)
        }
        assertDoesNotThrow {
            AbstractLocation("Небо", LocationType.SKY)
            AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)
            CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
            CoordinateLocation("Песок", LocationType.BEACH, 1, 1)
        }
    }

    @Test
    fun Reachability() {
        val creature = object : Creature(pavement) {}

        assertTrue(creature.canReach(pavement))
        assertFalse(creature.canReach(sky))
        assertFalse(creature.canReach(unknownLands))

        assertThrows<LocationRuleViolation> {
            creature.moveTo(sky)
        }
    }

    @Test
    fun observerLook() {
        val o = NarratorObserver(pavement, 170)

        assertNull(o.lastObservedLocation)
        o.look(beach, 0)
        assertEquals(beach, o.lastObservedLocation)
    }

    @Test
    fun horseNoiseUpd() {
        val horse = Horse(pavement, 200, HorseType.DOMESTIC)
        assertEquals(8, horse.currentNoiseLevel)

        horse.addCargo(Cargo("Тихое", 10, Freshness.FRESH, 3))
        assertEquals(11, horse.currentNoiseLevel)

        horse.carry(Cargo("Громкое", 10, Freshness.FRESH, 7), pavement)
        //baseNoise 8 + cargoNoise (3 + 7) + actionNoise 10 = 28
        assertEquals(28, horse.currentNoiseLevel)
    }

    @Test
    fun horseWeightVal() {
        assertThrows<IllegalArgumentException> {
            Horse(pavement, 0, HorseType.DOMESTIC)
        }
    }
}
