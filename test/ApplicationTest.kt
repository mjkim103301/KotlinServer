package com.example

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import main.kotlin.data.TodoData
import main.kotlin.main
import main.kotlin.model.TodoResponse
import main.kotlin.proto.TodoProtoBuild
import main.kotlin.proto.TodoResources

class ApplicationTest {

    @BeforeTest
    fun setUp() {

    }

    @Test
    fun testRoot() {
        withTestApplication({ main(testing = true) }) {

            handleRequest(HttpMethod.Post, "/todoProto"){
                addHeader(HttpHeaders.ContentType, "application/x-protobuf")
                setBody(TodoProtoBuild(TodoData( content="testPost2222")).toByteArray())
            }.apply {
                println("post test complete")
            }

           handleRequest(HttpMethod.Put, "/todoProto/132"){
                addHeader(HttpHeaders.ContentType, "application/x-protobuf")
                setBody(TodoProtoBuild(TodoData(content="changed333")).toByteArray())
            }.apply {
                println("put test complete")
            }
            handleRequest(HttpMethod.Get, "/todoProto/198"){
               // addHeader(HttpHeaders.ContentType, "application/x-protobuf")
                //setBody(TodoProtoBuild(TodoData()).toByteArray())
            }.apply {
                println(TodoResponse.of2(TodoResources.Todo2.parseFrom(response.byteContent)))

                println("get test complete")
            }
            handleRequest(HttpMethod.Delete, "/todoProto/197"){
                //addHeader(HttpHeaders.ContentType, "application/x-protobuf")
                //setBody(TodoProtoBuild(TodoData()).toByteArray())
            }.apply {
                println("delete test complete")
            }

        }
    }






}
