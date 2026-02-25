class Cargo(
    val name: String,
    val weight: Int,
    val freshness: Freshness = Freshness.FRESH,
    val noiseContribution: Int = 0
) {
    init {
        require(name.isNotBlank())
        require(weight > 0)
        require(noiseContribution >= 0)
    }
}