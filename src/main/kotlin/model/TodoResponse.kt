package main.kotlin.model

import main.kotlin.data.TodoData
import main.kotlin.proto.LocalDateTimeValueOf
import main.kotlin.proto.TodoResources
import java.time.LocalDateTime

data class TodoResponse(
    val id: Int,
    val content: String,
    val done: Boolean,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?

) {

    companion object {

        fun of(todo: TodoData) =
            TodoResponse(
                id = todo.id,
                content = todo.content,
                done = todo.done,
                createdAt = todo.createdAt,
                updatedAt = todo.updatedAt
            )

        fun of2(todo: TodoResources.Todo2) =
            TodoResponse(
                id = todo.id,
                content = todo.content,
                done = todo.done,
                createdAt = LocalDateTimeValueOf(todo.createdAt),
                updatedAt = LocalDateTimeValueOf(todo.updatedAt)
            )


    }
}