package main.kotlin

import com.google.protobuf.Timestamp
import io.ktor.application.call
import io.ktor.features.BadRequestException
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI
import main.kotlin.model.TodoRequest
import main.kotlin.proto.TimestampValueOf
import main.kotlin.proto.TodoProtoBuild
import main.kotlin.service.TodoService
import main.kotlin.proto.TodoResources
import main.kotlin.service.TodoServiceForProto

@KtorExperimentalAPI
fun Routing.todo(service: TodoService) {

    route("todos") {
        get {
            call.respond(service.getAll())
        }
        get("/{id}") {
            //origin
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("Parameter id is null")
            call.respond(service.getById(id))
        }
        post {
            //origin
           val body = call.receive<TodoRequest>()
            service.new(body.content)
            call.response.status(HttpStatusCode.Created)
        }
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            ?: throw BadRequestException("Parameter id is null")
            val body = call.receive<TodoRequest>()
            service.renew(id, body)
            call.response.status(HttpStatusCode.NoContent)
        }
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("Parameter id is null")
            service.delete(id)
            call.response.status(HttpStatusCode.NoContent)
        }
    }
}

@KtorExperimentalAPI
fun Routing.todoProto(service: TodoServiceForProto){
    route("todoProto") {
        get {

               call.respond(service.getAll().run(::TodoProtoBuild))

        }
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("Parameter id is null")

            val body : TodoResources.Todo2 =TodoResources.Todo2.newBuilder().apply {
                service.getById(id).let {
                    this.id = it.id
                    content=it.content
                    done=it.done
                    createdAt=TimestampValueOf(it.createdAt)
                    updatedAt= TimestampValueOf(it.updatedAt)
                }
           }.build()

            call.respond(body)

        }
        post {
            val body =call.receive<TodoResources.Todo2>()
            service.new(body)
          //  call.respond(body)
          //  service.new(body.content)
            call.response.status(HttpStatusCode.Created)
        }
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("Parameter id is null")
            val body = call.receive<TodoResources.Todo2>()
            service.renew(id, body)
            call.response.status(HttpStatusCode.NoContent)
        }
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw BadRequestException("Parameter id is null")
            service.delete(id)
            call.response.status(HttpStatusCode.NoContent)
        }
    }
}

