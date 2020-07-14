package main.kotlin.proto

import main.kotlin.data.TodoData
import java.time.LocalDateTime
import com.google.protobuf.Timestamp

fun TimestampValueOf(timestamp: java.sql.Timestamp) =
    Timestamp.newBuilder().apply {
        nanos = timestamp.nanos
        seconds = timestamp.time
    }.build()

fun TimestampValueOf(localDateTime: LocalDateTime) =
    TimestampValueOf(java.sql.Timestamp.valueOf(localDateTime))

fun LocalDateTimeValueOf(timestamp: Timestamp) =
    java.sql.Timestamp.valueOf(LocalDateTime.now()).apply {
        nanos = timestamp.nanos
        time = timestamp.seconds
    }.toLocalDateTime()
fun TodoProtoBuild(todo: TodoData) =
    TodoResources.Todo2.newBuilder().apply {
        id = todo.id
        content = todo.content
        done = todo.done
        createdAt = TimestampValueOf(todo.createdAt)
        updatedAt = TimestampValueOf(todo.updatedAt)
    }.build()

//TodoProto[] 타입 빌드
fun TodoProtoBuild(todos: List<TodoData>) =
    TodoResources.Todo2Array.newBuilder().apply {
        todos.forEach {
            addTodos(TodoProtoBuild(it))
        }
    }.build()

