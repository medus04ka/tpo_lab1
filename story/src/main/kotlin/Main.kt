

/**
   Они сидели на мостовой и смотрели с некоторым беспокойством,
   как огромные дети тяжело прыгают по песку, а дикие лошади с грохотом везут
   по небу в Неизведанные Области свежие запасы армированных изгородей.
*/
fun main() {
    val pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 10, 5)
    val beach = CoordinateLocation("Песок", LocationType.BEACH,14,8)
    val sky = AbstractLocation("Небо", LocationType.SKY)
    val unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)

    val observers = listOf(
        NarratorObserver(pavement, 168),
        NarratorObserver(pavement, 172)
    )
    observers.forEach { it.sit(pavement) }

    val kids = listOf(
        GiantChild(beach,210),
        GiantChild(beach,205)
    )
    val kidsImpact = kids.sumOf {
        it.jump(beach, JumpType.HEAVY)
    }

    val horses = listOf(
        Horse(sky, 500,HorseType.WILD),
        Horse( sky,450,HorseType.WILD)
    )

    val fences = Cargo("Армированные изгороди",120,Freshness.FRESH,35)

    horses.forEach {
        it.carry(fences, unknownLands)
    }

    val horsesNoise = horses.maxOf {
        it.currentNoiseLevel
    }
    val observedNoise = horsesNoise + kidsImpact

    observers.forEach {
        it.look(observedNoise)
    }

    println("Локации: ${pavement.name}, ${beach.name}, ${sky.name}, ${unknownLands.name}")
    println("Шум сцены: $observedNoise (лошади=$horsesNoise, прыжки=$kidsImpact)")
    println()

    observers.forEachIndexed { i, o ->
        println("Observer #${i + 1}: pose=${o.pose}, mood=${o.mood}, location=${o.location.name}")
    }

    println()
    kids.forEachIndexed { i, k ->
        println("GiantChild #${i + 1}: height=${k.heightCm}cm, location=${k.location.name}")
    }

    println()
    horses.forEachIndexed { i, h ->
        println("Horse #${i + 1}: type=${h.horseType}, location=${h.location.name}, noise=${h.currentNoiseLevel}")
        println(
            "  load=${h.currentLoadWeight()}/${h.maxCarryWeight}, baggage=${
                h.getBaggageSnapshot().map { it.name }
            }"
        )
    }
}