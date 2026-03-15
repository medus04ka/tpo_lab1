class Cargo(
    val name: String,
    val weight: Int,
    val isFresh: Boolean,
    val noiseContribution: Int,
    var location: Location? = null
) {
    fun moveTo(target: Location) {
        location = target
    }
}