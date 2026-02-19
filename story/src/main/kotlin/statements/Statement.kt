package org.example.statements

import org.example.creatures.Creature

class Statement(
    private val fame: Int,
    private val importance: Int,
    private val creator: Creature,
    private val content: StatementObject
) : Comparable<Statement> {

    fun getFame(): Int = fame
    fun getImportance(): Int = importance
    fun getCreator(): Creature = creator
    fun getContent(): StatementObject = content

    override fun compareTo(other: Statement): Int {
        return this.creator.intelligence - other.creator.intelligence
    }
}