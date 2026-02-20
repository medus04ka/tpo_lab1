

import org.example.statements.EventType
import org.example.statements.Statement

fun eventType(st: Statement): EventType =
    st.getContent().getContent() as EventType