import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class LocationTest {

    private lateinit var child: GiantChild
    private lateinit var beach: CoordinateLocation
    private lateinit var sky: AbstractLocation

    @BeforeEach
    fun setUp() {
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        sky = AbstractLocation("Небо", LocationType.SKY)
        child = GiantChild(beach,250)
    }

    @ParameterizedTest
    @EnumSource(names = ["PAVEMENT", "BEACH", "UNKNOWN_LANDS"])
    fun creatureLOcOK(type: LocationType) {
        val location = CoordinateLocation("TestLocation", type, 0, 0)

        assertTrue(location.isReachableBy(child))
    }

    @Test
    fun skyNOTChild() {
        assertFalse(sky.isReachableBy(child))
    }
}