package day10

import println
import readInput


fun main() {

    data class Point(val x: Int, val y: Int) {
        fun neighbours(): List<Point> {
            return listOf(
                Point(x = x, y = y - 1),
                Point(x = x, y = y + 1),
                Point(x = x - 1, y = y),
                Point(x = x + 1, y = y),
            )
        }
    }

    fun walk(
        grid: Map<Point, Int?>,
        currentHeight: Int,
        point: Point,
        reachedNines: MutableCollection<Point>,
    ) {
        val heightAtPoint = grid[point] ?: return
        if (heightAtPoint == currentHeight + 1) {
            if (heightAtPoint == 9) {
                reachedNines += point
            } else {
                point.neighbours().forEach { adjacent ->
                    walk(grid, heightAtPoint, adjacent, reachedNines)
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val grid: Map<Point, Int?> = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                Point(x, y) to char.digitToIntOrNull()
            }
        }.toMap()
        return grid.toList()
            .filter { (_, height) -> height == 0 }
            .sumOf { (start, _) ->
                val reachedNines = mutableSetOf<Point>()
                walk(grid, -1, start, reachedNines)
               reachedNines.size
            }

    }

    fun part2(input: List<String>): Int {
        val grid: Map<Point, Int?> = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                Point(x, y) to char.digitToIntOrNull()
            }
        }.toMap()
        return grid.toList()
            .filter { (_, height) -> height == 0 }
            .sumOf { (start, _) ->
                val reachedNines = mutableListOf<Point>()
                walk(grid, -1, start, reachedNines)
                reachedNines.size
            }

    }

    val input = readInput("day10/Day10")
    part1(input).println()
    part2(input).println()
}
