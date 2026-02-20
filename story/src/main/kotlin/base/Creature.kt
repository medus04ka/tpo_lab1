package org.example.base

import org.example.statements.Statement
import org.example.statements.StatementObject

abstract class Creature(
    val intelligence: Int
) : Supposable {

    override fun createStatement(fame: Int, importance: Int, statementObject: StatementObject): Statement {
        return Statement(this)
    }
}