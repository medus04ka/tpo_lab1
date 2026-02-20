package org.example

import org.example.actors.GiantChildren
import org.example.world.Place
import org.example.world.Cargo
import org.example.world.Territory
import org.example.actors.Observers
import org.example.actors.WildHorses
import org.example.statements.Statement

class StoryEngine(
    private val observers: Observers,
    private val children: GiantChildren,
    private val horses: WildHorses,
    private val destination: Territory,
    private val cargo: Cargo
) {
    fun run(): List<Statement> {
        val timeline = ArrayList<Statement>()

        timeline += observers.sitOn(Place.PAVEMENT)
        timeline += observers.keepWaiting()

        timeline += horses.tryAppear()

        if (!horses.appeared) {
            timeline += horses.neighAndReveal(observers)
        }

        timeline += observers.watchStory(children, Place.SAND, horses, Place.SKY, destination, cargo)

        return timeline
    }
}