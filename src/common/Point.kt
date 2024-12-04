package common

enum class Direction {
    UP, DOWN, LEFT, RIGHT;
}

data class Point(val x: Int, val y: Int, val value: Char) {
    fun moveDirection(direction: Direction, grid: List<List<Point>>) = when (direction) {
        Direction.UP -> {
            if (y == grid.size - 1) this else Point(x, y + 1, grid[y + 1][x].value)
        }

        Direction.DOWN -> {
            if (y == 0) this else Point(x, y - 1, grid[y - 1][x].value)
        }

        Direction.LEFT -> {
            if (x == 0) this else Point(x - 1, y, grid[y][x - 1].value)
        }

        Direction.RIGHT -> {
            if (x == grid[y].size - 1) this else Point(x + 1, y, grid[y][x + 1].value)
        }
    }
}

data class Move(val directions: List<Direction>)