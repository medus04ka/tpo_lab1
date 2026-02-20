package org.example.statements

import org.example.base.Creature

class Statement(
    private val creator: Creature,
) : Comparable<Statement> {
    override fun compareTo(other: Statement): Int {
        return this.creator.intelligence - other.creator.intelligence
    }
}