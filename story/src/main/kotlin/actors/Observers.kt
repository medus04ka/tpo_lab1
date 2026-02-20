package org.example.actors

import org.example.base.Size
import org.example.base.Human
import org.example.statements.EventStatement
import org.example.statements.EventType
import org.example.world.Territory
import org.example.world.Cargo

import org.example.statements.Statement
import org.example.world.Place

class Observers(
    intelligence: Int,
    size: Size
) : Human(intelligence, size) {

    var mood: Mood = Mood.CALM
        private set

    var isWatching: Boolean = false
        private set

    var worryLevel: Int = 0
        private set

    private fun changeWorry(delta: Int): Statement {
        worryLevel = (worryLevel + delta).coerceIn(0, 10)
        return createStatement(2, 3, EventStatement(EventType.WORRY_CHANGED))
    }

    fun sitOn(place: Place): Statement {
        require(place == Place.PAVEMENT)
        mood = Mood.WAITING
        val st = createStatement(1, 2, EventStatement(EventType.SAT_ON_PAVEMENT))
        return st
    }

    fun keepWaiting(): Statement {
        mood = Mood.WAITING
        return createStatement(1, 2, EventStatement(EventType.KEPT_WAITING))
    }

    fun hearNeigh(): List<Statement> {
        mood = Mood.ANNOYED
        val res = ArrayList<Statement>()
        res += createStatement(2, 4, EventStatement(EventType.HEARD_NEIGH))
        res += changeWorry(+2)
        return res
    }

    fun lookAnnoyed(): Statement {
        mood = Mood.ANNOYED
        return createStatement(2, 4, EventStatement(EventType.LOOKED_ANNOYED))
    }

    fun realizeHorseAppeared(): List<Statement> {
        mood = Mood.WORRIED
        val res = ArrayList<Statement>()
        res += createStatement(3, 6, EventStatement(EventType.REALIZED_HORSE_APPEARED))
        res += changeWorry(+2)
        return res
    }

    fun startWatchingWithWorry(): List<Statement> {
        mood = Mood.WORRIED
        isWatching = true
        val res = ArrayList<Statement>()
        res += createStatement(3, 6, EventStatement(EventType.WATCHED_WITH_WORRY))
        res += changeWorry(+1)
        return res
    }

    fun watchStory(
        children: GiantChildren,
        sand: Place,
        horses: WildHorses,
        sky: Place,
        destination: Territory,
        cargo: Cargo
    ): List<Statement> {
        val res = ArrayList<Statement>()

        res += startWatchingWithWorry()

        res += children.jumpHardOn(sand)
        res += changeWorry(+1)

        res += horses.haulWithRumble(sky, destination, cargo)
        res += changeWorry(+2)

        res += changeWorry(worryFromCargo(cargo))

        return res
    }

    private fun worryFromCargo(cargo: Cargo): Int {
        return ((cargo.heaviness + cargo.clatter) / 4).coerceIn(0, 5)
    }
}