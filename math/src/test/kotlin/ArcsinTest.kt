import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.asin
import kotlin.math.PI

class ArcsinTest {

    private val impl = Arcsin()

    private fun assertCloseToRef(x: Double, iters: Int, eps: Double) {
        val expected = asin(x)
        val actual = impl.approximate(x, iters)
        assertEquals(expected, actual, eps, "x=$x iters=$iters")
    }

    @ParameterizedTest(name = "x={0} -> NaN")
    @DisplayName("out of domain -> NaN")
    @ValueSource(doubles = [
        -2.0, -1.000001, 1.000001, 2.0,
        Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY
    ])
    fun outOfDomain(x: Double) {
        assertTrue(impl.approximate(x, 20).isNaN(), "x=$x")
    }

    @Test
    @DisplayName("NaN -> NaN")
    fun nan() {
        assertTrue(impl.approximate(Double.NaN, 20).isNaN())
    }

    @Test
    @DisplayName("граничные проверки")
    fun iterations() {
        assertThrows<IllegalArgumentException> {
            impl.approximate(0.0, 0)
        }
        assertThrows<IllegalArgumentException> {
            impl.approximate(0.0, -1)
        }
    }

    @Test
    @DisplayName("x = -1 and x = 1")
    fun extremes() {
        assertEquals(-PI / 2.0, impl.approximate(-1.0, 60), 1e-12)
        assertEquals( PI / 2.0, impl.approximate( 1.0, 60), 1e-12)
    }

    @Test
    @DisplayName("Integration steps")
    fun partialSums() {
        val x = 0.5
        val t0 = x
        val t1 = (x * x * x) / 6.0
        val t2 = (3.0 * x * x * x * x * x) / 40.0

        assertEquals(t0, impl.approximate(x, 1), 1e-15)
        assertEquals(t0 + t1, impl.approximate(x, 2), 1e-15)
        assertEquals(t0 + t1 + t2, impl.approximate(x, 3), 1e-15)
    }

    @ParameterizedTest(name = "around 0: x={0}")
    @DisplayName("around zero")
    @ValueSource(doubles = [-1e-6, -1e-4, -0.0, 0.0, 1e-4, 1e-6])
    fun aroundZero(x: Double) {
        assertCloseToRef(x, iters = 30, eps = 1e-12)
    }

    @Test
    @DisplayName("arcsin(-0.0) == -0.0")
    fun signedZero() {
        val r = impl.approximate(-0.0, 10)
        assertTrue(1.0 / r == Double.NEGATIVE_INFINITY, "Expected -0.0 result")
    }

    @ParameterizedTest(name = "middle: x={0}")
    @DisplayName("middle regions (|x| < 0.9)")
    @ValueSource(doubles = [-0.75, -0.5, -0.25, -0.1, 0.1, 0.25, 0.5, 0.75])
    fun middleRegions(x: Double) {
        assertCloseToRef(x, iters = 60, eps = 1e-7)
    }

    @ParameterizedTest(name = "near edges: x={0}")
    @DisplayName("near edges (|x| >= 0.9)")
    @ValueSource(doubles = [-0.9, -0.99, -0.999999, 0.9, 0.99, 0.999999])
    fun nearEdges(x: Double) {
        assertCloseToRef(x, iters = 120, eps = 1e-5)
    }

    @ParameterizedTest(name = "class boundary: x={0}")
    @DisplayName("around |x| = 0.9")
    @ValueSource(doubles = [0.899999, 0.9, 0.900001, -0.899999, -0.9, -0.900001])
    fun boundaryAtPointNine(x: Double) {
        assertCloseToRef(x,120,1e-5)
    }

    @Test
    fun sqrt() {
        val method = Arcsin::class.java.getDeclaredMethod("sqrt", Double::class.java)
        method.isAccessible = true
        val arcsin = Arcsin()
        val nanResult = method.invoke(arcsin, Double.NaN) as Double
        assertTrue(nanResult.isNaN())
        val negativeResult = method.invoke(arcsin, -1.0) as Double
        assertTrue(negativeResult.isNaN())
        val zeroResult = method.invoke(arcsin, 0.0) as Double
        assertEquals(0.0, zeroResult)
        val bigResult = method.invoke(arcsin, 4.0) as Double
        assertEquals(2.0, bigResult, 1e-6)
        val smallResult = method.invoke(arcsin, 0.25) as Double
        assertEquals(0.5, smallResult, 1e-6)
    }
}