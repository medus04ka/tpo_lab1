package org.example.creatures

import org.example.statements.Statement
import org.example.statements.StatementObject

abstract class Creature(
    val age: Int,
    val intelligence: Int
) : Supposable {

    override fun createStatement(fame: Int, importance: Int, statementObject: StatementObject): Statement {
        return Statement(fame, importance, this, statementObject)
    }
}