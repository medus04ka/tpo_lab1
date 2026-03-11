import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

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

    @ParameterizedTest
    @CsvSource("LIGHT, 3", "HEAVY, 9")
    fun jumpLVL(type: JumpType, expectedNoise: Int) {
        val actualNoise = child.jump(beach, type)

        assertEquals(expectedNoise, actualNoise)
    }

    @Test
    fun jumpLVLDOTOSHNO() {
        val lightNoise = child.jump(beach, JumpType.LIGHT)
        val heavyNoise = child.jump(beach, JumpType.HEAVY)

        assertTrue(heavyNoise > lightNoise)
    }

    @Test
    fun jumpONLYBeachChild() {
        child.jump(beach, JumpType.HEAVY)

        assertEquals(beach, child.location)
    }

    @Test
    fun jumpNOTBeachChild() {
        assertThrows<LocationRuleViolation> {
            child.jump(pavement, JumpType.HEAVY)
        }
    }

    @Test
    fun jumpSKy() {
        assertThrows<LocationRuleViolation> {
            child.jump(sky, JumpType.LIGHT)
        }
    }
}