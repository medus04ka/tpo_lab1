package org.example

import org.example.creatures.GiantChildren
import org.example.creatures.Observers
import org.example.creatures.Place
import org.example.creatures.WildHorses

/**
   Они сидели на мостовой и смотрели с некоторым беспокойством,
   как огромные дети тяжело прыгают по песку, а дикие лошади с грохотом везут
   по небу в Неизведанные Области свежие запасы армированных изгородей.
*/
fun main() {
    val observers = Observers(20,5, Place.PAVEMENT)
    val children = GiantChildren(10, 1, Place.SAND)
    val horses = WildHorses(7, 3,Place.SKY, Place.UNKNOWN_REGIONS)

    val engine = StoryEngine(observers, children, horses)
    val timeline = engine.run()

    println("кол-во событий: ${timeline.size}, Лошади есть?=${horses.appeared}, состояние=${observers.mood}")
}