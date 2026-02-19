package org.example.creatures

import org.example.statements.ActionStatement
import org.example.statements.Statement

class GiantChildren(
    age: Int,
    intelligence: Int,
    val place: Place = Place.SAND
) : Creature(age, intelligence) {

    var jumpingHard: Boolean = false
        private set

    fun startJumpingHard(): Statement {
        jumpingHard = true
        return createStatement(1,5,ActionStatement("Огромные дети тяжело прыгают по песку")
        )
    }
}