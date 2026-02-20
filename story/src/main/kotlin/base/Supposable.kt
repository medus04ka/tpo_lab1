package org.example.base

import org.example.statements.Statement
import org.example.statements.StatementObject

interface Supposable {
    fun createStatement(fame: Int, importance: Int, statementObject: StatementObject): Statement
}