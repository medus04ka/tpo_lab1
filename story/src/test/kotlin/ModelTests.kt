import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ModelTests {

    private val pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
    private val beach = CoordinateLocation("Песок", LocationType.BEACH, 2, 2)
    private val sky = AbstractLocation("Небо", LocationType.SKY)
    private val unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)

    @Test
    fun domainStory() {
        val observers = listOf(
            NarratorObserver(pavement, 168),
            NarratorObserver(pavement, 172)
        )
        observers.forEach {
            it.sit(pavement)
        }

        val kids = listOf(
            GiantChild(beach, 210),
            GiantChild(beach, 205)
        )
        val kidsImpact = kids.sumOf {
            it.jump(beach, JumpType.HEAVY)
        }

        val horses = listOf(
            Horse(sky, 500, HorseType.WILD),
            Horse(sky, 450, HorseType.WILD)
        )
        val fences = Cargo("Армированные изгороди", 120, Freshness.FRESH, 35)
        horses.forEach {
            it.carry(fences, unknownLands)
        }

        val horsesNoise = horses.maxOf {
            it.currentNoiseLevel
        }
        val observedNoise = horsesNoise + kidsImpact

        observers.forEach {
            it.look(observedNoise)
        }

        assertTrue(observedNoise >= 30, "Суммарный шум должен тревожить наблюдателей")
        observers.forEach {
            assertEquals(Mood.WORRIED, it.mood)
        }
        horses.forEach {
            assertEquals(unknownLands, it.location)
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
    fun cargoValid() {
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
    fun cannotReachAbstract() {
        val creature = object : Creature(pavement) {}

        assertTrue(creature.canReach(pavement))
        assertFalse(creature.canReach(sky))
        assertFalse(creature.canReach(unknownLands))

        assertThrows<LocationRuleViolation> { creature.moveTo(sky) }
    }

    @Test
    fun observerMood() {
        val observer = NarratorObserver(pavement, 170)
        val horse = Horse(pavement, 200, HorseType.WILD)
        val cargo = Cargo("Груз", 50, Freshness.FRESH, 12)

        observer.look(horse.currentNoiseLevel)
        assertEquals(Mood.CALM, observer.mood, "До шума действия")

        horse.carry(cargo, unknownLands)
        observer.look(horse.currentNoiseLevel)
        assertEquals(Mood.WORRIED, observer.mood, "После шума действия")
    }
}
