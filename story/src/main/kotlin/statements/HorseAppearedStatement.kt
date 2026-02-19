package org.example.statements

import org.example.creatures.WildHorses


class HorseAppearedStatement(
    private val horses: WildHorses
) : StatementObject {
    override fun getContent(): Any = horses.appeared
}