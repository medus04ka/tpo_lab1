import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
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
    @CsvSource("true, 3", "false, 9")
    fun jumpLVL(type: Boolean, expectedNoise: Int) {
        val actualNoise = child.jump(beach, type)
        assertEquals(expectedNoise, actualNoise)
    }

    @Test
    fun jumpLVLDOTOSHNO() {
        val lightNoise = child.jump(beach, true)
        val heavyNoise = child.jump(beach, false)

        assertTrue(heavyNoise > lightNoise)
    }

    @Test
    fun jumpONLYBeachChild() {
        child.jump(beach, false)
        assertEquals(beach, child.location)
    }

    @Test
    fun jumpNOTBeachChild() {
        assertThrows<LocationRuleViolation> {
            child.jump(pavement, false)
        }
    }

    @Test
    fun jumpSKy() {
        assertThrows<LocationRuleViolation> {
            child.jump(sky, true)
        }
    }

    @ParameterizedTest
    @CsvSource("3, 4, true, true", "6, 8, true, false", "6, 8, false, true", "12, 16, false, false")
    fun jumpOnDistance(x: Int, y: Int, type: Boolean, shouldSucceed: Boolean) {
        val target = CoordinateLocation("Цель", LocationType.BEACH, x, y)

        if (shouldSucceed) {
            assertDoesNotThrow {
                giantChild.jump(target, type)
            }
        } else {
            assertThrows<LocationRuleViolation> {
                giantChild.jump(target, type)
            }
        }
    }
}

class AAAA {
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

    @Execution(ExecutionMode.CONCURRENT)
    @Test
    fun jumpNotAbstract() {
        val child = GiantChild(beach, 250)
        val abstractTarget = AbstractLocation("Абстракт", LocationType.BEACH)

        assertDoesNotThrow {
            child.jump(abstractTarget, false)
        }
    }

    @Test
    fun jumpAbstract() {
        val abstractStart = AbstractLocation("Абстракт", LocationType.BEACH)
        val child = GiantChild(abstractStart, 250)

        val target = CoordinateLocation("Цель", LocationType.BEACH, 100, 100)

        assertDoesNotThrow {
            child.jump(target, true)
        }
    }
}
