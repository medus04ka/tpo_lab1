import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CargoTest {

    private lateinit var cargo: Cargo
    private lateinit var sky: AbstractLocation
    private lateinit var unknownLands: AbstractLocation
    private lateinit var pavement: CoordinateLocation

    @BeforeEach
    fun setUp() {
        sky = AbstractLocation("Небо", LocationType.SKY)
        unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)

        cargo = Cargo(
            name = "Запасы армированных изгородей",
            weight = 80,
            freshness = Freshness.FRESH,
            noiseContribution = 6
        )
    }

    @Test
    fun constructor_SetsName() {
        assertEquals("Запасы армированных изгородей", cargo.name)
    }

    @Test
    fun constructor_SetsWeight() {
        assertEquals(80, cargo.weight)
    }

    @Test
    fun constructor_SetsFreshness() {
        assertEquals(Freshness.FRESH, cargo.freshness)
    }

    @Test
    fun constructor_SetsNoiseContribution() {
        assertEquals(6, cargo.noiseContribution)
    }

    @Test
    fun constructor_InitialLocationIsNull() {
        assertNull(cargo.location)
    }

    @Test
    fun moveTo_ChangesLocationToSky() {
        cargo.moveTo(sky)

        assertEquals(sky, cargo.location)
    }

    @Test
    fun moveTo_ChangesLocationToUnknownLands() {
        cargo.moveTo(unknownLands)

        assertEquals(unknownLands, cargo.location)
    }

    @Test
    fun moveTo_MultipleMoves_ChangesLocationToLastOne() {
        cargo.moveTo(sky)
        cargo.moveTo(pavement)

        assertEquals(pavement, cargo.location)
    }
}