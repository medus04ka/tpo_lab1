package org.example.creatures

import org.example.statements.ActionStatement
import org.example.statements.MoodStatement
import org.example.statements.Statement


class Observers(
    age: Int,
    intelligence: Int,
    val place: Place = Place.PAVEMENT
) : Creature(age, intelligence) {

    var mood: Mood = Mood.CALM
        private set

    var isWatching: Boolean = false
        private set

    fun sitDown(): Statement {
        mood = Mood.WAITING
        return createStatement(1, 2,ActionStatement("Они сидели на мостовой")
        )
    }

    fun keepWaiting(): Statement {
        mood = Mood.WAITING
        return createStatement(1, 2,ActionStatement("Они продолжили ждать")
        )
    }

    fun hearNeigh(): Statement {
        mood = Mood.ANNOYED
        return createStatement(2, 4,ActionStatement("«Ииии-го-го!!!!!!!!!»")
        )
    }

    fun lookAnnoyed(): Statement {
        mood = Mood.ANNOYED
        return createStatement(2, 4,ActionStatement("Они недовольно посмотрели")
        )
    }

    fun realizeHorseAppeared(): Statement {
        mood = Mood.WORRIED
        return createStatement(3, 6,ActionStatement("Они поняли, что лошадь появилась")
        )
    }

    fun startWatchingWithWorry(): Statement {
        mood = Mood.WORRIED
        isWatching = true
        return createStatement(3, 6,MoodStatement(mood)
        )
    }
}