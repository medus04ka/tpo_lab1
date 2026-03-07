import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AnimalTest {

    private lateinit var animal: TestAnimal
    private lateinit var beach: CoordinateLocation

    private class TestAnimal(
        override var location: Location
    ) : Animal(location)

    @BeforeEach
    fun setUp() {
        beach = CoordinateLocation("Песок", LocationType.BEACH, 5, 5)
        animal = TestAnimal(beach)
    }

    @Test
    fun constructor_SetsLocation() {
        assertEquals(beach, animal.location)
    }
}