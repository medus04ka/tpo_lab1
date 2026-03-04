import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HorseTests {

    private val pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
    private val beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 3)
    private val sky = AbstractLocation("Небо", LocationType.SKY)
    private val unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)

    @Test
    fun wildHorseCanTravel() {
        val wild = Horse(sky, 400, HorseType.WILD)
        val cargo = Cargo("Изгороди", 120, Freshness.FRESH, 35)

        wild.carry(cargo, unknownLands)

        assertEquals(unknownLands, wild.location)
        assertEquals(0, wild.currentLoadWeight(), "Груз выгружен по прибытии")
        assertEquals(60, wild.currentNoiseLevel, "base 15 + action(10+35)")
    }

    @Test
    fun domesticHorseCannotReach() {
        val domestic = Horse(pavement, 300, HorseType.DOMESTIC)
        val cargo = Cargo("Изгороди", 50, Freshness.FRESH, 10)

        assertThrows<LocationRuleViolation> {
            domestic.carry(cargo, sky)
        }
        assertEquals(pavement, domestic.location)
        assertEquals(0, domestic.currentLoadWeight())
        assertEquals(8, domestic.currentNoiseLevel)
    }

    @Test
    fun cargoWeightLimit() {
        val wild = Horse(beach, 100, HorseType.WILD)
        val cargo = Cargo("Тяжёлое", 150)

        assertThrows<CargoRuleViolation> {
            wild.carry(cargo, unknownLands)
        }
        assertEquals(beach, wild.location)
        assertEquals(0, wild.currentLoadWeight())
    }

    @Test
    fun cargoAffectsNotPosition() {
        val horse = Horse(pavement, 200, HorseType.DOMESTIC)
        val cargo = Cargo("Доски", 40, Freshness.FRESH, 7)

        horse.addCargo(cargo)

        assertEquals(pavement, horse.location)
        assertEquals(40, horse.currentLoadWeight())
        assertEquals(15, horse.currentNoiseLevel, "base 8 + cargoNoise 7")
    }

    @Test
    fun moveTo() {
        val wild = Horse(pavement, 200, HorseType.WILD)
        assertDoesNotThrow {
            wild.moveTo(sky)
        }
        assertEquals(sky, wild.location)

        val domestic = Horse(pavement, 200, HorseType.DOMESTIC)
        assertThrows<LocationRuleViolation> {
            domestic.moveTo(sky)
        }
    }
}