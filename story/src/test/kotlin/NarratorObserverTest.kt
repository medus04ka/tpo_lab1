import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NarratorObserverTest {

    private lateinit var observer: NarratorObserver
    private lateinit var pavement: CoordinateLocation

    @BeforeEach
    fun setUp() {
        pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
        observer = NarratorObserver(pavement, 180)
    }

    @Test
    fun constructor_SetsInitialLocation() {
        assertEquals(pavement, observer.location)
    }

    @Test
    fun constructor_SetsHeight() {
        assertEquals(180, observer.heightCm)
    }
}