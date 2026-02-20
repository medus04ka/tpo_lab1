package org.example.statements

class EventStatement(
    private val type: EventType
) : StatementObject {
    override fun getContent(): Any = type
}