package day06

import common.Direction
import common.Point
import println
import readInput

fun main() {

    fun getVisitedPoints(grid: List<List<Point>>): Set<Point> {
        val startingPoint = grid.flatten().first { it.value == '^' } to Direction.UP
        var nextStep = startingPoint
        val visitedPoints = mutableSetOf<Point>()
        while (true) {
            val nextPoint = nextStep.first.moveDirectionInverse(nextStep.second, grid)
            if (nextPoint == nextStep.first) {
                break
            }
            if (nextPoint.value == '#') {
                nextStep = nextStep.first to nextStep.second.rotate()
            } else {
                visitedPoints.add(nextPoint)
                nextStep = nextPoint to nextStep.second
            }
        }
        return visitedPoints
    }

    fun getVisitedPointsReturnOnCycle(grid: List<List<Point>>, start: Point): Set<Point> {
        val startingPoint = start to Direction.UP
        var nextStep = startingPoint
        val visitedPoints = mutableSetOf(startingPoint)
        while (true) {
            val nextPoint = nextStep.first.moveDirectionInverse(nextStep.second, grid)
            if (nextPoint == nextStep.first) {
                break
            }
            if (nextPoint.value == '#') {
                nextStep = nextStep.first to nextStep.second.rotate()
            } else {
                nextStep = nextPoint to nextStep.second
                if (nextStep in visitedPoints) {
                    return emptySet()
                }
                visitedPoints.add(nextStep)
            }
        }
        return visitedPoints.map { it.first }.toSet()
    }

    fun part1(input: List<String>): Int {
        val grid = input.mapIndexed { y, row ->
            row.toCharArray().mapIndexed { x, char ->
                Point(x, y, char)
            }
        }
        return getVisitedPoints(grid).count()
    }

    fun part2(input: List<String>): Int {
        val grid = input.mapIndexed { y, row ->
            row.toCharArray().mapIndexed { x, char ->
                Point(x, y, char)
            }
        }
        val visitedPoints = getVisitedPoints(grid)

        val startingPoint = grid.flatten().first { it.value == '^' }
        val count = (visitedPoints - startingPoint).count { point ->
            val updatedGrid = grid.toMutableList().apply {
                val row = this[point.y].toMutableList()
                row[point.x] = point.copy(
                    value = '#',
                )
                this[point.y] = row
            }
            getVisitedPointsReturnOnCycle(updatedGrid, startingPoint).isEmpty()
        }
        return count
    }

    val input = readInput("day06/Day06")
    part1(input).println()
    part2(input).println()
}
