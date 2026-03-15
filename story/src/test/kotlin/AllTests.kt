import org.junit.jupiter.api.Test
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite

@Suite
@SelectClasses(CreatureTest::class, HorseTest::class, ObserverTest::class, ScenarioTest::class, GiantChildTest::class, LocationTest::class)
class AllTests {
    @Test
    fun mainRun() {
        main()
    }
}