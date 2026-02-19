package org.example.creatures

import org.example.Cargo
import org.example.statements.ActionStatement
import org.example.statements.HorseAppearedStatement
import org.example.statements.Statement
import kotlin.random.Random

class WildHorses(
    age: Int,
    intelligence: Int,
    val route: Place = Place.SKY,
    val destination: Place = Place.UNKNOWN_REGIONS,
    val cargo: Cargo = Cargo("свежие","запасы","армированных изгородей")
) : Creature(age, intelligence) {

    var appeared: Boolean = false
        private set

    var hauling: Boolean = false
        private set

    fun tryAppear(): Statement {
        appeared = Random.nextBoolean()
        return createStatement(2, 3, HorseAppearedStatement(this))
    }

    fun neighAndReveal(observers: Observers): List<Statement> {
        val res = ArrayList<Statement>()

        res += createStatement(2, 4, ActionStatement("Где-то рядом раздалось лошадиное ржание"))
        res += observers.hearNeigh()
        res += observers.lookAnnoyed()

        appeared = true
        res += observers.realizeHorseAppeared()

        return res
    }

    fun startHaulingWithRumble(): Statement {
        hauling = true
        return createStatement(4,8,ActionStatement("Дикие лошади с грохотом везут по небу в Неизведанные Области "  + "${cargo.freshness} ${cargo.supplies} ${cargo.fences}"
            )
        )
    }
}