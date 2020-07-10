package main.kotlin.model

import main.kotlin.data.TodoData
import java.time.LocalDateTime

data class TodoResponse(val id: Int,
                        val content: String,
                        val done: Boolean,
                        val createdAt: LocalDateTime,
                        val updatedAt: LocalDateTime) {

    companion object {

        fun of(todo: TodoData) =
            TodoResponse(
                id = todo.id,
                content = todo.content,
                done = todo.done,
                createdAt = todo.createdAt,
                updatedAt = todo.updatedAt
            )
    }
}