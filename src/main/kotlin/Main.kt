fun main() {
    val scene = Scene(
        observers = Observers(
            place = Place.PAVEMENT,
            feeling = State.WITH_SOME_WORRY,
        ),
        children = Children(
            size = "огромные",
            action = "прыгают",
            intensity = State.HEAVILY,
            place = Place.SAND
        ),
        horses = Horses(
            type = "дикие",
            sound = State.WITH_RUMBLE,
            action = "везут",
            route = Place.SKY,
            destination = Place.UNKNOWN_REGIONS,
            cargo = Cargo(
                freshness = State.FRESH,
                name = State.REINFORCED
            )
        )
    )

    Narrator.tell(scene)
}