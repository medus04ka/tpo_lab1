import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HorseTests {

    private val pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
    private val sky = AbstractLocation("Небо", LocationType.SKY)
    private val unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)

    @Test
    fun sky() {
        assertDoesNotThrow {
            Horse(sky, 500,  HorseType.WILD)
        }
    }

    @Test
    fun domH() {
        assertThrows<LocationRuleViolation> {
            Horse(sky, 500, HorseType.DOMESTIC)
        }
    }

    @Test
    fun addCargo() {
        val horse = Horse(pavement,  200,  HorseType.DOMESTIC)
        val cargo = Cargo("Доски", weight = 50)

        horse.addCargo(cargo)

        assertEquals(50, horse.currentLoadWeight())
        assertEquals(listOf("Доски"), horse.getBaggageSnapshot().map {
            it.name
        })
    }

    @Test
    fun addCargoRule() {
        val horse = Horse(pavement, 100, HorseType.DOMESTIC)
        val cargo = Cargo("Тяжёлое",  150)

        assertThrows<CargoRuleViolation> {
            horse.addCargo(cargo)
        }
        assertEquals(0, horse.currentLoadWeight())
    }

    @Test
    fun notCarry() {
        val domestic = Horse(pavement, 500, HorseType.DOMESTIC)
        val cargo = Cargo("Изгороди",120, Freshness.FRESH, 35)

        assertThrows<LocationRuleViolation> {
            domestic.carry(cargo, unknownLands)
        }
    }

    @Test
    fun carry() {
        val wild = Horse(pavement, 100, HorseType.WILD)
        val cargo = Cargo("Изгороди", 120,  Freshness.FRESH,  35)

        assertThrows<CargoRuleViolation> {
            wild.carry(cargo, unknownLands)
        }
    }

    @Test
    fun full() {
        val wild = Horse(pavement,500, HorseType.WILD)
        val cargo = Cargo("Армированные изгороди", 120, Freshness.FRESH, 35)

        wild.carry(cargo, unknownLands)

        assertEquals(unknownLands, wild.location)
        assertEquals(120, wild.currentLoadWeight())
        assertEquals(listOf("Армированные изгороди"), wild.getBaggageSnapshot().map { it.name })

        // baseNoise(WILD)=15 + cargoNoise(35) + actionNoise(10) = 60
        assertEquals(60, wild.currentNoiseLevel)
    }

    @Test
    fun testy() {
        val horse = Horse(pavement,100, HorseType.DOMESTIC)

        assertTrue(horse.canAdd(Cargo("Лёгкое", 40)))
        horse.addCargo(Cargo("Лёгкое", 40))

        assertTrue(horse.canAdd(Cargo("Ещё", 60)))
        assertFalse(horse.canAdd(Cargo("Слишком много", 61)))
    }
}