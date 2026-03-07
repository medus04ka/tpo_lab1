import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CoordinateLocationTest {

    private lateinit var pavement: CoordinateLocation
    private lateinit var beach: CoordinateLocation
    private lateinit var skyCoordinate: CoordinateLocation
    private lateinit var child: GiantChild

    @BeforeEach
    fun setUp() {
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        skyCoordinate = CoordinateLocation("Высота", LocationType.SKY, 100, 200)
        child = GiantChild(beach, 250)
    }

    @Test
    fun constructor_SetsName() {
        assertEquals("Мостовая", pavement.name)
    }

    @Test
    fun constructor_SetsType() {
        assertEquals(LocationType.PAVEMENT, pavement.type)
    }

    @Test
    fun constructor_SetsCoordinates() {
        assertEquals(0, pavement.x)
        assertEquals(0, pavement.y)
    }

    @Test
    fun isAbstract_ReturnsFalse() {
        assertFalse(pavement.isAbstract)
    }

    @Test
    fun isReachableBy_ForPavement_ReturnsTrue() {
        assertTrue(pavement.isReachableBy(child))
    }

    @Test
    fun isReachableBy_ForBeach_ReturnsTrue() {
        assertTrue(beach.isReachableBy(child))
    }

    @Test
    fun isReachableBy_ForSky_ReturnsFalse() {
        assertFalse(skyCoordinate.isReachableBy(child))
    }
}