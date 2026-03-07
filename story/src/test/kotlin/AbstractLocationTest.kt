import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AbstractLocationTest {

    private lateinit var sky: AbstractLocation
    private lateinit var unknownLands: AbstractLocation
    private lateinit var child: GiantChild
    private lateinit var beach: CoordinateLocation

    @BeforeEach
    fun setUp() {
        sky = AbstractLocation("Небо", LocationType.SKY)
        unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        child = GiantChild(beach, 250)
    }

    @Test
    fun constructor_SetsName() {
        assertEquals("Небо", sky.name)
    }

    @Test
    fun constructor_SetsType() {
        assertEquals(LocationType.SKY, sky.type)
    }

    @Test
    fun isAbstract_ReturnsTrue() {
        assertTrue(sky.isAbstract)
    }

    @Test
    fun isReachableBy_ForSky_ReturnsFalse() {
        assertFalse(sky.isReachableBy(child))
    }

    @Test
    fun isReachableBy_ForUnknownLands_ReturnsTrue() {
        assertTrue(unknownLands.isReachableBy(child))
    }
}