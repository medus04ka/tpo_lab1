import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HorseTest {

    private lateinit var horse: Horse
    private lateinit var sky: AbstractLocation
    private lateinit var unknownLands: AbstractLocation
    private lateinit var beach: CoordinateLocation
    private lateinit var cargo: Cargo

    @BeforeEach
    fun setUp() {
        sky = AbstractLocation("Небо", LocationType.SKY)
        unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)
        beach = CoordinateLocation("Песок", LocationType.BEACH, 10, 5)

        horse = Horse(
            location = sky,
            maxCarryWeight = 200,
            horseType = HorseType.WILD
        )

        cargo = Cargo(
            name = "Свежие запасы армированных изгородей",
            weight = 80,
            freshness = Freshness.FRESH,
            noiseContribution = 6
        )
    }

    @Test
    fun getBaggageSnapshot_WhenEmpty_ReturnsEmptyList() {
        assertTrue(horse.getBaggageSnapshot().isEmpty())
    }

    @Test
    fun getCurrentLoadWeight_WhenEmpty_ReturnsZero() {
        assertEquals(0, horse.getCurrentLoadWeight())
    }

    @Test
    fun canAdd_WithAcceptableCargo_ReturnsTrue() {
        val result = horse.canAdd(cargo)

        assertTrue(result)
    }

    @Test
    fun canAdd_WithTooHeavyCargo_ReturnsFalse() {
        val heavyCargo = Cargo(
            name = "Очень тяжелый груз",
            weight = 500,
            freshness = Freshness.FRESH,
            noiseContribution = 10
        )

        val result = horse.canAdd(heavyCargo)

        assertFalse(result)
    }

    @Test
    fun addCargo_AddsCargoToBaggage() {
        horse.addCargo(cargo)

        assertTrue(horse.getBaggageSnapshot().contains(cargo))
    }

    @Test
    fun addCargo_IncreasesCurrentLoadWeight() {
        horse.addCargo(cargo)

        assertEquals(80, horse.getCurrentLoadWeight())
    }

    @Test
    fun addCargo_WithTooHeavyCargo_ThrowsException() {
        val heavyCargo = Cargo(
            name = "Очень тяжелый груз",
            weight = 500,
            freshness = Freshness.FRESH,
            noiseContribution = 10
        )

        assertThrows(CargoRuleViolation::class.java) {
            horse.addCargo(heavyCargo)
        }
    }

    @Test
    fun carry_ChangesCargoLocationToDestination() {
        horse.carry(cargo, unknownLands)

        assertEquals(unknownLands, cargo.location)
    }

    @Test
    fun carry_SetsCargoLocation_NotNull() {
        horse.carry(cargo, unknownLands)

        assertNotNull(cargo.location)
    }

    @Test
    fun carry_IncreasesNoiseLevel() {
        horse.carry(cargo, unknownLands)

        assertTrue(horse.currentNoiseLevel > 0)
    }

    @Test
    fun carry_AddsCargoToBaggage() {
        horse.carry(cargo, unknownLands)

        assertTrue(horse.getBaggageSnapshot().contains(cargo))
    }

    @Test
    fun carry_WithSpoiledCargo_ThrowsException() {
        val spoiledCargo = Cargo(
            name = "Испорченный груз",
            weight = 50,
            freshness = Freshness.SPOILED,
            noiseContribution = 5
        )

        assertThrows(CargoRuleViolation::class.java) {
            horse.carry(spoiledCargo, unknownLands)
        }
    }

    @Test
    fun carry_WithDomesticHorse_ThrowsException() {
        val domesticHorse = Horse(
            location = sky,
            maxCarryWeight = 200,
            horseType = HorseType.DOMESTIC
        )

        assertThrows(CargoRuleViolation::class.java) {
            domesticHorse.carry(cargo, unknownLands)
        }
    }

    @Test
    fun carry_WithWrongDestination_ThrowsException() {
        assertThrows(CargoRuleViolation::class.java) {
            horse.carry(cargo, beach)
        }
    }

    @Test
    fun carry_WithTooHeavyCargo_ThrowsException() {
        val smallHorse = Horse(
            location = sky,
            maxCarryWeight = 30,
            horseType = HorseType.WILD
        )

        assertThrows(CargoRuleViolation::class.java) {
            smallHorse.carry(cargo, unknownLands)
        }
    }

    @Test
    fun baseNoise_WildHorse_ReturnsFive() {
        assertEquals(5, horse.baseNoise())
    }

    @Test
    fun baseNoise_WildHorse_GreaterThanDomesticHorse() {
        val domesticHorse = Horse(
            location = sky,
            maxCarryWeight = 200,
            horseType = HorseType.DOMESTIC
        )

        assertTrue(horse.baseNoise() > domesticHorse.baseNoise())
    }

    @Test
    fun recalcNoise_WithActionNoise_ReturnsExpectedValue() {
        val result = horse.recalcNoise(4)

        assertEquals(9, result)
    }

    @Test
    fun recalcNoise_AfterAddingCargo_TakesLoadIntoAccount() {
        horse.addCargo(cargo)

        val result = horse.recalcNoise(4)

        assertEquals(17, result)
    }
}