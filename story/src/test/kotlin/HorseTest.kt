import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class HorseTest {

    private lateinit var wildHorse: Horse
    private lateinit var sky: AbstractLocation
    private lateinit var unknownLands: AbstractLocation
    private lateinit var beach: CoordinateLocation
    private lateinit var freshCargo: Cargo

    @BeforeEach
    fun setUp() {
        sky = AbstractLocation("Небо", LocationType.SKY)
        unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)
        beach = CoordinateLocation("Песок", LocationType.BEACH, 10, 5)

        wildHorse = Horse(sky, 200, HorseType.WILD)

        freshCargo = Cargo("Свежие запасы армированных изгородей", 80, Freshness.FRESH, 6)
    }

    @ParameterizedTest
    @CsvSource("10, true", "80, true", "200, true", "201, false")
    fun canAddPOVes(weight: Int, expected: Boolean) {
        val cargo = Cargo("Груз", weight, Freshness.FRESH, 5)

        assertEquals(expected, wildHorse.canAdd(cargo))
    }

    @Test
    fun carryScenarioComplete() {
        wildHorse.carry(freshCargo, unknownLands)

        assertEquals(unknownLands, freshCargo.location)
    }

    @Test
    fun carryBaggageComplete() {
        wildHorse.carry(freshCargo, unknownLands)

        assertTrue(wildHorse.getBaggageSnapshot().contains(freshCargo))
    }

    @Test
    fun carryLVL() {
        wildHorse.carry(freshCargo, unknownLands)

        assertTrue(wildHorse.currentNoiseLevel > 0)
    }

    @Test
    fun carrySPOILED() {
        val spoiledCargo = Cargo("Испорченные изгороди", 50, Freshness.SPOILED, 5)

        assertThrows<CargoRuleViolation> {
            wildHorse.carry(spoiledCargo, unknownLands)
        }
    }

    @Test
    fun carryDOMESTICvsWILD() {
        val domesticHorse = Horse(sky, 200, HorseType.DOMESTIC)

        assertThrows<CargoRuleViolation> {
            domesticHorse.carry(freshCargo, unknownLands)
        }
    }

    @Test
    fun carryNOTUNKNWN() {
        assertThrows<CargoRuleViolation> {
            wildHorse.carry(freshCargo, beach)
        }
    }

    @Test
    fun carryThrows() {
        val heavyCargo = Cargo("Слишком тяжёлый груз", 300, Freshness.FRESH, 8)

        assertThrows<CargoRuleViolation> {
            wildHorse.carry(heavyCargo, unknownLands)
        }
    }

    @Test
    fun baseNoiseWHHigher() {
        val domesticHorse = Horse(sky, 200, HorseType.DOMESTIC)

        assertTrue(wildHorse.baseNoise() > domesticHorse.baseNoise())
    }

    @Test
    fun u4etCargo() {
        wildHorse.addCargo(freshCargo)

        val recalculatedNoise = wildHorse.recalcNoise(4)

        assertEquals(17, recalculatedNoise)
    }

    @Test
    fun canAddU4etCargo() {
        val firstCargo = Cargo("Первый груз", 150, Freshness.FRESH, 3)
        val secondCargo = Cargo("Второй груз", 60, Freshness.FRESH, 3)

        wildHorse.addCargo(firstCargo)

        assertFalse(wildHorse.canAdd(secondCargo))
    }
}