package day04

import common.Direction.*
import common.Move
import common.Point
import println
import readInput

fun main() {
    fun getString(grid: List<List<Point>>, point: Point, vararg moves: Move): String {
        var string = point.value.toString()
        moves.forEach { move ->
            var movingPoint = point
            repeat(move.repeat) {
                move.direction.forEach { direction ->
                    val newPoint = movingPoint.moveDirection(direction, grid)
                    if (newPoint == movingPoint) return "-"
                    movingPoint = newPoint
                }
                string += movingPoint.value.toString()
            }
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
                    if (getString(grid, point, Move(3, UP)) == searchString) count++
                    if (getString(grid, point, Move(3, DOWN)) == searchString) count++
                    if (getString(grid, point, Move(3, RIGHT)) == searchString) count++
                    if (getString(grid, point, Move(3, LEFT)) == searchString) count++
                    if (getString(grid, point, Move(3, RIGHT, UP)) == searchString) count++
                    if (getString(grid, point, Move(3, RIGHT, DOWN)) == searchString) count++
                    if (getString(grid, point, Move(3, LEFT, UP)) == searchString) count++
                    if (getString(grid, point, Move(3, LEFT, DOWN)) == searchString) count++
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
                    val first = getString(grid, point, Move(1, RIGHT, UP), Move(1, LEFT, DOWN))
                    val second = getString(grid, point, Move(1, RIGHT, DOWN), Move(1, LEFT, UP))

                    if (first == "AMS" && second == "AMS") count++
                    if (first == "AMS" && second == "ASM") count++
                    if (first == "ASM" && second == "AMS") count++
                    if (first == "ASM" && second == "ASM") count++

                }
            }
        }
        return count
    }

    val input = readInput("day04/Day04")
    part1(input).println()
    part2(input).println()
}
