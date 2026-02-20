package org.example.actors

import org.example.base.Size
import org.example.base.Npc
import org.example.statements.EventStatement
import org.example.statements.EventType
import org.example.statements.Statement
import org.example.world.Place

class GiantChildren(
    intelligence: Int,
    size: Size = Size.GIANT
) : Npc(intelligence, size) {

    var jumpingHard: Boolean = false
        private set

    fun jumpHardOn(place: Place): Statement {
        require(place == Place.SAND)

        jumpingHard = true
        return createStatement(1,5,EventStatement(EventType.CHILDREN_JUMPED_HARD)
        )
    }
}