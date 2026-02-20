import org.example.StoryEngine
import org.example.actors.GiantChildren
import org.example.actors.Mood
import org.example.actors.Observers
import org.example.actors.WildHorses
import org.example.base.Size
import org.example.statements.EventType
import org.example.world.Cargo
import org.example.world.Place
import org.example.world.Territory
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Tests {
    @Test
    fun `observers sit on pavement produces SAT_ON_PAVEMENT and sets mood WAITING`() {
        val observers = Observers(5, Size.NORMAL)

        val st = observers.sitOn(Place.PAVEMENT)

        assertEquals(EventType.SAT_ON_PAVEMENT, eventType(st))
        assertEquals(Mood.WAITING, observers.mood)
    }

    @Test
    fun `children jump on sand produces CHILDREN_JUMPED_HARD and sets jumpingHard`() {
        val children = GiantChildren(2, Size.GIANT)

        val st = children.jumpHardOn(Place.SAND)

        assertEquals(EventType.CHILDREN_JUMPED_HARD, eventType(st))
        assertTrue(children.jumpingHard)
    }

    @Test
    fun `engine run includes watching children and hauling events`() {
        val observers = Observers(5, Size.NORMAL)
        val children = GiantChildren(2, Size.GIANT)
        val horses = WildHorses(3)

        val destination = Territory("Неизведанные Области", true)
        val cargo = Cargo(6,7)

        val timeline = StoryEngine(observers, children, horses, destination, cargo).run()
        val types = timeline.map { eventType(it) }.toSet()

        assertTrue(EventType.WATCHED_WITH_WORRY in types)
        assertTrue(EventType.CHILDREN_JUMPED_HARD in types)
        assertTrue((EventType.HORSES_HAULED_WITH_RUMBLE in types) || (EventType.ERROR in types))
    }

    @Test
    fun `haulWithRumble throws if destination is not unknown`() {
        val observers = Observers(intelligence = 5, size = Size.NORMAL)
        val horses = WildHorses(intelligence = 3)
        horses.neighAndReveal(observers)

        val notUnknown = Territory("Известные области", false)
        val cargo = Cargo(heaviness = 1, clatter = 1)

        assertFailsWith<IllegalArgumentException> {
            horses.haulWithRumble(Place.SKY, notUnknown, cargo)
        }
    }

}