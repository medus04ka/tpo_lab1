package org.example

import org.example.actors.GiantChildren
import org.example.actors.Observers
import org.example.world.Territory
import org.example.world.Cargo
import org.example.base.Size
import org.example.actors.WildHorses

/**
   Они сидели на мостовой и смотрели с некоторым беспокойством,
   как огромные дети тяжело прыгают по песку, а дикие лошади с грохотом везут
   по небу в Неизведанные Области свежие запасы армированных изгородей.
*/
fun main() {
    val destination = Territory("Неизведанные Области",true)
    val cargo = Cargo()

    val observers = Observers(5, Size.NORMAL)
    val children = GiantChildren(2)
    val horses = WildHorses(3)

    val timeline = StoryEngine(observers, children, horses, destination, cargo).run()

    println("событий=${timeline.size}, лошади=${horses.appeared}, состояние=${observers.mood}")
}