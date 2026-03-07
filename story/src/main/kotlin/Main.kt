

/**
   Они сидели на мостовой и смотрели с некоторым беспокойством,
   как огромные дети тяжело прыгают по песку, а дикие лошади с грохотом везут
   по небу в Неизведанные Области свежие запасы армированных изгородей.
*/
fun main() {
    val pavement = CoordinateLocation("Мостовая", LocationType.PAVEMENT, 0, 0)
    val beach = CoordinateLocation("Песок", LocationType.BEACH, 10, 5)
    val sky = AbstractLocation("Небо", LocationType.SKY)
    val unknownLands = AbstractLocation("Неизведанные Области", LocationType.UNKNOWN_LANDS)

    val observer = NarratorObserver(pavement,170)
    val giantChild = GiantChild(beach,260)
    val horse = Horse(sky,200,HorseType.WILD)

    val fenceCargo = Cargo("Свежие запасы армированных изгородей",80,Freshness.FRESH,6)

    observer.sit(pavement)

    val jumpNoise = giantChild.jump(beach, JumpType.HEAVY)
    observer.look(beach,jumpNoise)

    horse.carry(fenceCargo, unknownLands)

    println("Поза наблюдателя: ${observer.pose}")
    println("Настроение наблюдателя: ${observer.mood}")
    println("Шум от прыжка: $jumpNoise")
    println("Шум лошади при перевозке: ${horse.currentNoiseLevel}")
    println("Локация груза: ${fenceCargo.location?.name}")
}