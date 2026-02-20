package org.example.actors

import org.example.world.Cargo
import org.example.base.Animal
import org.example.world.Sound
import org.example.statements.EventStatement
import org.example.statements.EventType
import org.example.statements.Statement
import org.example.world.Place
import org.example.world.Territory
import kotlin.random.Random

class WildHorses(
    intelligence: Int
) : Animal(intelligence) {

    var appeared: Boolean = false
        private set

    var hauling: Boolean = false
        private set

    var lastSound: Sound = Sound.NONE
        private set

    fun tryAppear(): Statement {
        appeared = Random.nextBoolean()
        return createStatement(2,3,EventStatement(EventType.REALIZED_HORSE_APPEARED)
        )
    }

    fun neighAndReveal(observers: Observers): List<Statement> {
        val res = ArrayList<Statement>()

        lastSound = Sound.NEIGH
        res += createStatement(2, 4, EventStatement(EventType.HEARD_NEIGH))

        res += observers.hearNeigh()
        res += observers.lookAnnoyed()

        appeared = true
        res += observers.realizeHorseAppeared()

        return res
    }

    fun haulWithRumble(route: Place, destination: Territory, cargo: Cargo): Statement {
        if (!appeared) {
            return createStatement(5,10,EventStatement(EventType.ERROR))
        }

        require(route == Place.SKY)
        require(destination.isUnknown)

        hauling = true
        lastSound = Sound.RUMBLE

        val extraImportance = (cargo.heaviness / 3).coerceIn(0, 3)
        return createStatement(4, 8 + extraImportance, EventStatement(EventType.HORSES_HAULED_WITH_RUMBLE))
    }
}