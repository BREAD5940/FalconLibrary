package org.ghrobotics.lib.mathematics.units

import org.ghrobotics.lib.mathematics.epsilonEquals
import org.ghrobotics.lib.mathematics.kEpsilon

val Number.radian get() = Rotation2d(toDouble())
val Number.degree get() = Math.toRadians(toDouble()).radian

class Rotation2d : SIUnit<Rotation2d> {

    override val value: Double
    val cos: Double
    val sin: Double

    constructor(value: Double) : this(value, true)

    constructor(x: Double, normalize: Boolean) {
        value = value
    }

    val radian get() = value // should be between -PI and PI already. // % (Math.PI * 2)
    val degree get() = Math.toDegrees(value)

    fun isParallel(rotation: Rotation2d) = (this - rotation).radian epsilonEquals 0.0

    override fun plus(other: Rotation2d): Rotation2d {
        return Rotation2d(
            this.value + other.value
        )
    }

    override fun minus(other: Rotation2d) = plus(-other)

    override fun createNew(newValue: Double) = Rotation2d(newValue)

    override fun equals(other: Any?) = other is Rotation2d && this.value epsilonEquals other.value

    override fun hashCode() = this.value.hashCode()

    companion object {
        val kZero = Rotation2d(0.0)
        val kRotation = 360.degree
    }
}