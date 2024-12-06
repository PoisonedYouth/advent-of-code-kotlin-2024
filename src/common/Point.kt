package common

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun rotate() = when (this) {
        UP -> LEFT
        LEFT -> DOWN
        DOWN -> RIGHT
        RIGHT -> UP
    }
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

    fun moveDirectionInverse(direction: Direction, grid: List<List<Point>>) = when (direction) {
        Direction.DOWN -> {
            if (y == grid.size - 1) this else Point(x, y + 1, grid[y + 1][x].value)
        }

        Direction.UP -> {
            if (y == 0) this else Point(x, y - 1, grid[y - 1][x].value)
        }

        Direction.RIGHT -> {
            if (x == 0) this else Point(x - 1, y, grid[y][x - 1].value)
        }

        Direction.LEFT -> {
            if (x == grid[y].size - 1) this else Point(x + 1, y, grid[y][x + 1].value)
        }
    }
}


class Move(val repeat: Int = 1, vararg val direction: Direction)