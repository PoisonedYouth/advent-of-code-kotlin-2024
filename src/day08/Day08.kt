package day08

import common.Point
import println
import readInput


fun main() {

    data class Boundaries(val width: IntRange, val height: IntRange) {
        operator fun contains(point: Point): Boolean {
            return point.x in width && point.y in height
        }
    }

    fun buildFrequenciesMap(input: List<String>): Map<Char, MutableList<Point>> {
        val frequencies: Map<Char, MutableList<Point>> = buildMap {
            input.mapIndexed { y, row ->
                row.toCharArray().mapIndexed { x, char ->
                    if (char != '.') {
                        getOrPut(char, { mutableListOf() }).add(Point(x, y, ' '))
                    }
                }
            }
        }
        return frequencies
    }

    fun part1(input: List<String>): Int {
        val boundaries = Boundaries(input[0].indices, input.indices)
        val frequencies: Map<Char, MutableList<Point>> = buildFrequenciesMap(input)

        val antiNodes = mutableSetOf<Point>()
        frequencies.values.forEach { points ->
            if (points.size > 1) {
                for (i in 0..points.lastIndex) {
                    for (j in (i + 1)..points.lastIndex) {
                        val diff = points[i] - points[j]
                        val potentialPoint1 = points[i] + diff
                        if (boundaries.contains(potentialPoint1)) {
                            antiNodes.add(potentialPoint1)
                        }
                        val potentialPoint2 = points[j] - diff
                        if (boundaries.contains(potentialPoint2)) {
                            antiNodes.add(potentialPoint2)
                        }
                    }
                }
            }
        }
        return antiNodes.size
    }

    fun part2(input: List<String>): Int {
        val boundaries = Boundaries(0..input[0].lastIndex, 0..input.lastIndex)
        val frequencies: Map<Char, MutableList<Point>> = buildFrequenciesMap(input)

        val antiNodes = mutableSetOf<Point>()
        frequencies.values.forEach { points ->
            if (points.size > 1) {
                for (i in 0..points.lastIndex) {
                    for (j in (i + 1)..points.lastIndex) {
                        antiNodes.add(points[i])
                        antiNodes.add(points[j])
                        val diff = points[i] - points[j]
                        var potentialPoint1 = points[i] + diff
                        while (boundaries.contains(potentialPoint1)) {
                            antiNodes.add(potentialPoint1)
                            potentialPoint1 += diff
                        }
                        var potentialPoint2 = points[j] - diff
                        while (boundaries.contains(potentialPoint2)) {
                            antiNodes.add(potentialPoint2)
                            potentialPoint2 -= diff
                        }
                    }
                }
            }
        }
        return antiNodes.size
    }

    val input = readInput("day08/Day08")
    part1(input).println()
    part2(input).println()
}
