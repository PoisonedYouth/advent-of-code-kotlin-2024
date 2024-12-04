package day04

import common.Direction.*
import common.Move
import common.Point
import println
import readInput

fun main() {
    fun getString(grid: List<List<Point>>, point: Point, steps: List<Move>): String {
        var string = ""
        var movingPoint = point
        steps.forEach {
            it.directions.forEach { direction ->
                val newPoint = movingPoint.moveDirection(direction, grid)
                if (newPoint == movingPoint) return ""
                movingPoint = newPoint
            }
            string += movingPoint.value.toString()
        }
        return string
    }

    fun part1(input: List<String>): Int {
        val grid: List<List<Point>> = input.mapIndexed { y, row ->
            row.toCharArray().mapIndexed { x, char ->
                Point(x, y, char)
            }
        }

        val searchString = "XMAS"
        var count = 0
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, point ->
                if (point.value == 'X') {
                    if (point.value + getString(grid, point, listOf(Move(listOf(UP)), Move(listOf(UP)), Move(listOf(UP)))) == searchString) count++
                    if (point.value + getString(grid, point, listOf(Move(listOf(DOWN)), Move(listOf(DOWN)), Move(listOf(DOWN)))) == searchString) count++
                    if (point.value + getString(grid, point, listOf(Move(listOf(LEFT)), Move(listOf(LEFT)), Move(listOf(LEFT)))) == searchString) count++
                    if (point.value + getString(grid, point, listOf(Move(listOf(RIGHT)), Move(listOf(RIGHT)), Move(listOf(RIGHT)))) == searchString) count++
                    if (point.value + getString(grid, point, listOf(Move(listOf(RIGHT, UP)), Move(listOf(RIGHT, UP)), Move(listOf(RIGHT, UP)))) == searchString) count++
                    if (point.value + getString(grid, point, listOf(Move(listOf(RIGHT, DOWN)), Move(listOf(RIGHT, DOWN)), Move(listOf(RIGHT, DOWN)))) == searchString) count++
                    if (point.value + getString(grid, point, listOf(Move(listOf(LEFT, UP)), Move(listOf(LEFT, UP)), Move(listOf(LEFT, UP)))) == searchString) count++
                    if (point.value + getString(grid, point, listOf(Move(listOf(LEFT, DOWN)), Move(listOf(LEFT, DOWN)), Move(listOf(LEFT, DOWN)))) == searchString) count++
                }
            }
        }
        return count
    }


    fun part2(input: List<String>): Int {
        val grid: List<List<Point>> = input.mapIndexed { y, row ->
            row.toCharArray().mapIndexed { x, char ->
                Point(x, y, char)
            }
        }

        var count = 0
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, point ->
                if (point.value == 'A') {
                    val first = getString(grid, point, listOf(Move(listOf(RIGHT, UP)))) + point.value + getString(grid, point, listOf(Move(listOf(LEFT, DOWN))))
                    val second = getString(grid, point, listOf(Move(listOf(RIGHT, DOWN)))) + point.value + getString(grid, point, listOf(Move(listOf(LEFT, UP))))

                    if (first == "MAS" && second == "MAS") count++
                    if (first == "MAS" && second == "SAM") count++
                    if (first == "SAM" && second == "MAS") count++
                    if (first == "SAM" && second == "SAM") count++

                }
            }
        }
        return count
    }

    val input = readInput("day04/Day04")
    part1(input).println()
    part2(input).println()
}
