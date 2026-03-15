import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ObserverTest {

    private lateinit var observer: NarratorObserver
    private lateinit var pavement: CoordinateLocation
    private lateinit var beach: CoordinateLocation

    @BeforeEach
    fun setUp() {
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        beach = CoordinateLocation("Песок", LocationType.BEACH, 10, 5)
        observer = NarratorObserver(pavement, 170)
    }

    @Test
    fun sit() {
        observer.sit(pavement)

        assertTrue(observer.isSitting)
    }

    @Test
    fun sitChangesLocation() {
        observer.sit(beach)

        assertEquals(beach, observer.location)
    }

    @ParameterizedTest
    @CsvSource("0, false", "3, false", "6, false", "7, true", "10, true")
    fun look(noise: Int, expectedWorried: Boolean) {
        observer.look(noise)

        assertEquals(expectedWorried, observer.isWorried)
    }

    @Test
    fun lookCALM() {
        observer.look(2)
        assertFalse(observer.isWorried)

        observer.look(9)
        assertTrue(observer.isWorried)
    }
}