package main.kotlin.model

import main.kotlin.proto.LocalDateTimeValueOf
import main.kotlin.proto.TodoResources
import java.time.LocalDateTime

data class TodoRequest(val content: String,
                       val done: Boolean?,
                       val createdAt: LocalDateTime?,
                       val updatedAt: LocalDateTime?,
                       var id:Int?){
    companion object{
        fun of(todo: TodoResources.Todo2) =
            TodoRequest(
                id = todo.id,
                content = todo.content,
                done = todo.done,
                createdAt = LocalDateTimeValueOf(todo.createdAt),
                updatedAt = LocalDateTimeValueOf(todo.updatedAt)

            )
    }
}