import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GiantChildTest {

    private lateinit var child: GiantChild
    private lateinit var beach: CoordinateLocation
    private lateinit var pavement: CoordinateLocation
    private lateinit var sky: AbstractLocation

    @BeforeEach
    fun setUp() {
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        sky = AbstractLocation("Небо", LocationType.SKY)
        child = GiantChild(beach, 250)
    }

    @Test
    fun jump_OnBeach_ChangesLocation() {
        child.jump(beach, JumpType.HEAVY)

        assertEquals(beach, child.location)
    }

    @Test
    fun jump_WithLightType_ReturnsLightNoise() {
        val noise = child.jump(beach, JumpType.LIGHT)

        assertEquals(3, noise)
    }

    @Test
    fun jump_WithHeavyType_ReturnsHeavyNoise() {
        val noise = child.jump(beach, JumpType.HEAVY)

        assertEquals(9, noise)
    }

    @Test
    fun jump_HeavyNoiseGreaterThanLightNoise() {
        val lightNoise = child.jump(beach, JumpType.LIGHT)
        val heavyNoise = child.jump(beach, JumpType.HEAVY)

        assertTrue(heavyNoise > lightNoise)
    }

    @Test
    fun jump_OnPavement_ThrowsException() {
        assertThrows(LocationRuleViolation::class.java) {
            child.jump(pavement, JumpType.HEAVY)
        }
    }

    @Test
    fun jump_OnSky_ThrowsException() {
        assertThrows(LocationRuleViolation::class.java) {
            child.jump(sky, JumpType.LIGHT)
        }
    }
}