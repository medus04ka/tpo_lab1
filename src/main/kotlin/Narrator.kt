object Narrator {
    fun tell(scene: Scene) {
        val o = scene.observers
        val c = scene.children
        val h = scene.horses
        val g = h.cargo

        val text =
            "Они сидели ${o.place.text} и смотрели ${o.feeling.text}, " +
                    "как ${c.size} дети ${c.intensity.text} ${c.action} ${c.place.text}, " +
                    "а ${h.type} лошади ${h.sound.text} ${h.action} ${h.route.text} ${h.destination.text} " +
                    "${g.freshness.text} ${g.name.text}."

        println(text)
    }
}
