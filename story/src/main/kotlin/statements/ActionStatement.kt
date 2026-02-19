package org.example.statements

class ActionStatement (
    private val text: String
) : StatementObject {
    override fun getContent(): Any = text
}