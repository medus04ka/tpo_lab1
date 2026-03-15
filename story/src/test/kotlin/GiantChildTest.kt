import org.junit.jupiter.api.Assertions.assertDoesNotThrow
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
    private lateinit var giantChild: GiantChild
    private lateinit var startBeach: CoordinateLocation

    @BeforeEach
    fun setUp() {
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        sky = AbstractLocation("Небо", LocationType.SKY)
        startBeach = CoordinateLocation("Старт", LocationType.BEACH, 0, 0)
        giantChild = GiantChild(startBeach, 260)

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

    @ParameterizedTest
    @CsvSource("3, 4, LIGHT, true", "6, 8, LIGHT, false", "6, 8, HEAVY, true", "12, 16, HEAVY, false")
    fun jumpOnDistance(x: Int, y: Int, jumpType: JumpType, shouldSucceed: Boolean) {
        val target = CoordinateLocation("Цель", LocationType.BEACH, x, y)

        if (shouldSucceed) {
            assertDoesNotThrow {
                giantChild.jump(target, jumpType)
            }
        } else {
            assertThrows<LocationRuleViolation> {
                giantChild.jump(target, jumpType)
            }
        }
    }
}