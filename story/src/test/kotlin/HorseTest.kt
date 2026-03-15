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

        freshCargo = Cargo("Свежие запасы армированных изгородей", 80, true, 6)
    }

    @ParameterizedTest
    @CsvSource("10, true", "80, true", "200, true", "201, false")
    fun canAddPOVes(weight: Int, expected: Boolean) {
        val cargo = Cargo("Груз", weight, true, 5)

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

        assertTrue(wildHorse.getBaggageList().contains(freshCargo))
    }

    @Test
    fun carryLVL() {
        wildHorse.carry(freshCargo, unknownLands)

        assertTrue(wildHorse.currentNoiseLevel > 0)
    }

    @Test
    fun carrySPOILED() {
        val spoiledCargo = Cargo("Испорченные изгороди", 50, false, 5)

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
        val heavyCargo = Cargo("Слишком тяжёлый груз", 300, true, 8)

        assertThrows<CargoRuleViolation> {
            wildHorse.carry(heavyCargo, unknownLands)
        }
    }

    @Test
    fun addCargoThrows() {
        val heavyCargo = Cargo("Тяжёлый груз", 300, true, 5)

        assertThrows<CargoRuleViolation> {
            wildHorse.addCargo(heavyCargo)
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
        val firstCargo = Cargo("Первый груз", 150, true, 3)
        val secondCargo = Cargo("Второй груз", 60, true, 3)

        wildHorse.addCargo(firstCargo)

        assertFalse(wildHorse.canAdd(secondCargo))
    }

    @Test
    fun horseMaxDistance() {
        val start = CoordinateLocation("Старт", LocationType.BEACH, 0, 0)
        val target = CoordinateLocation("Цель", LocationType.BEACH, 20, 0)
        val horse = Horse(start, 200, HorseType.WILD)

        horse.moveTo(target)

        assertEquals(target, horse.location)
    }

    @Test
    fun horseThrows() {
        val start = CoordinateLocation("Старт", LocationType.BEACH, 0, 0)
        val target = CoordinateLocation("Очень далеко", LocationType.BEACH, 100, 0)
        val horse = Horse(start, 200, HorseType.WILD)

        assertThrows<IllegalStateException> {
            horse.moveTo(target)
        }
    }
}