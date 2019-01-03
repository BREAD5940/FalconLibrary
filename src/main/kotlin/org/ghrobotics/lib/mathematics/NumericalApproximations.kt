package org.ghrobotics.lib.mathematics

class NumericalApproximations {
    companion object {
        fun newtonsMethod(
            numIterations: Int,
            x: Double,
            f: (x: Double) -> Double,
            fPrime: (x: Double) -> Double
        ): Double =
            if (numIterations < 1) x
            else newtonsMethod(numIterations - 1, x - (f(x) / fPrime(x)), f, fPrime)
    }
}

