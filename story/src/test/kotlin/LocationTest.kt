import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocationTest {

    private lateinit var child: GiantChild
    private lateinit var pavement: CoordinateLocation
    private lateinit var beach: CoordinateLocation
    private lateinit var sky: AbstractLocation
    private lateinit var unknownLands: AbstractLocation

    @BeforeEach
    fun setUp() {
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        sky = AbstractLocation("Небо", LocationType.SKY)
        unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)
        child = GiantChild(beach, 250)
    }

    @Test
    fun isReachableBy_Pavement_ReturnsTrue() {
        assertTrue(pavement.isReachableBy(child))
    }

    @Test
    fun isReachableBy_Beach_ReturnsTrue() {
        assertTrue(beach.isReachableBy(child))
    }

    @Test
    fun isReachableBy_Sky_ReturnsFalse() {
        assertFalse(sky.isReachableBy(child))
    }

    @Test
    fun isReachableBy_UnknownLands_ReturnsTrue() {
        assertTrue(unknownLands.isReachableBy(child))
    }
}