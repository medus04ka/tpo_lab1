package org.example.statements

import org.example.creatures.Mood

class MoodStatement(
    private val mood: Mood
) : StatementObject {
    override fun getContent(): Any = mood
}