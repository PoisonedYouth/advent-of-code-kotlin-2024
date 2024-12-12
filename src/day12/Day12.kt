package day12

import readInput

enum class Dir(val x: Int, val y: Int) {
    N(0, -1), E(1, 0), S(0, 1), W(-1, 0)  // y increases S
}
val ew = setOf(Dir.E, Dir.W)
val ns = setOf(Dir.N, Dir.S)
val perpendicular = mapOf(Dir.N to ew, Dir.S to ew, Dir.E to ns, Dir.W to ns)

data class Point(val x: Int, val y: Int) {
    fun move(d: Dir) = Point(x + d.x, y + d.y)
}

class Grid(lines: List<String>) {
    private val farm = lines.flatMapIndexed { y, line ->
        line.mapIndexed { x, c -> Point(x, y) to c }
    }.toMap()

    private fun neighbors(p: Point) = Dir.entries.map { p.move(it) }.filter { farm[p] == farm[it]  }

    private fun region(p: Point): Set<Point> {
        val frontier = ArrayDeque(listOf(p))
        val region = mutableSetOf<Point>()
        while (!frontier.isEmpty()) {
            val current = frontier.removeFirst()
            if (region.add(current)) {
                frontier.addAll(neighbors(current))
            }
        }
        return region
    }

    fun regions(): List<Set<Point>> = buildList {
        for (p in farm.keys) {
            if (none { p in it }) {
                add(region(p))
            }
        }
    }

    fun perimeter(region: Set<Point>) = region.sumOf { 4 - neighbors(it).size }

    fun sides(region: Set<Point>): Int {
        val perimeter = region.filter { neighbors(it).size < 4 }
        val fences = Dir.entries.associateWith { mutableSetOf<MutableSet<Point>>() }
        for (p in perimeter) {
            for (d in fences.keys.filter { p.move(it) !in region }) {
                val fence = mutableSetOf(p)
                for (perpD in perpendicular.getValue(d)) {
                    fence.addAll(generateSequence(p) {
                        val next = it.move(perpD)
                        if (next in perimeter && it.move(d) !in region) next else null
                    })
                }
                fences.getValue(d).add(fence)
            }
        }
        return fences.values.sumOf { it.size }
    }
}



fun main() {
    val lines = readInput("day12/Day12")
    val grid = Grid(lines)
    val regions = grid.regions()
    println(regions.sumOf { it.size * grid.perimeter(it) })
    println(regions.sumOf { it.size * grid.sides(it)  })
}
