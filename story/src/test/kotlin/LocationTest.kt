import org.junit.jupiter.api.Assertions.assertEquals
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
    private lateinit var first: CoordinateLocation
    private lateinit var second: CoordinateLocation
    private lateinit var samePoint: CoordinateLocation

    @BeforeEach
    fun setUp() {
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        sky = AbstractLocation("Небо", LocationType.SKY)
        child = GiantChild(beach,250)
        first = CoordinateLocation("Точка 1", LocationType.BEACH, 0, 0)
        second = CoordinateLocation("Точка 2", LocationType.BEACH, 3, 4)
        samePoint = CoordinateLocation("Та же точка", LocationType.BEACH, 0, 0)
    }

    @ParameterizedTest
    @EnumSource(names = ["PAVEMENT", "BEACH", "UNKNOWN_LANDS"])
    fun creatureLOcOK(type: LocationType) {
        val location = CoordinateLocation("TestLocation", type, 0, 0)

        assertTrue(location.isReachableBy(child))
    }

    @Test
    fun distanceEuclidean() {
        assertEquals(5.0, first.distanceTo(second), 0.0001)
    }

    @Test
    fun distanceIsZero() {
        assertEquals(0.0, first.distanceTo(samePoint), 0.0001)
    }

    @Test
    fun distanceIsSymmetric() {
        assertEquals(first.distanceTo(second), second.distanceTo(first), 0.0001)
    }
    @Test
    fun skyNOTChild() {
        assertFalse(sky.isReachableBy(child))
    }

    @Test
    fun skyIsNotReachable() {
        val coordinateSky = CoordinateLocation("Координатное небо", LocationType.SKY, 1, 1)

        assertFalse(coordinateSky.isReachableBy(child))
    }

    @Test
    fun locationIsNotAbstract() {
        assertFalse(first.isAbstract)
    }

    @Test
    fun abstractLocation() {
        assertTrue(sky.isAbstract)
    }
}