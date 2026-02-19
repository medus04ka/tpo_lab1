package org.example

import org.example.creatures.GiantChildren
import org.example.creatures.Observers
import org.example.creatures.WildHorses
import org.example.statements.Statement

class StoryEngine(
    private val observers: Observers,
    private val children: GiantChildren,
    private val horses: WildHorses
) {
    fun run(): List<Statement> {
        val timeline = ArrayList<Statement>()

        timeline += observers.sitDown()
        timeline += observers.keepWaiting()

        timeline += horses.tryAppear()

        if (!horses.appeared) {
            timeline += horses.neighAndReveal(observers)
        }

        if (horses.appeared) {
            timeline += observers.startWatchingWithWorry()
            timeline += children.startJumpingHard()
            timeline += horses.startHaulingWithRumble()
        }

        return timeline
    }
}