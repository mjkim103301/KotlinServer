package main.kotlin

import com.google.protobuf.MessageLite
import io.ktor.application.ApplicationCall
import io.ktor.features.ContentConverter
import io.ktor.http.ContentType
import io.ktor.http.content.ByteArrayContent
import io.ktor.request.ApplicationReceiveRequest
import io.ktor.util.pipeline.PipelineContext
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.readBytes
import java.nio.charset.Charset
import io.ktor.utils.io.readRemaining
import java.lang.reflect.InvocationTargetException

class ProtobufConverter :ContentConverter{
    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any? {
       // println("ttt")
       if(value !is MessageLite){
            return null;
        }
       // println(value.toByteArray())
        return ByteArrayContent(value.toByteArray(), contentType)

    }

    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request=context.subject
        val channel=request.value as? ByteReadChannel ?: return null
        return channel.readRemaining().readBytes() // 페이로드를 획득합니다.
            .let {
                /*
                 * 결국 특정 타입만 변환하는 코드는 살아남지 못합니다.
                 * 어떤 타입이든 애플리케이션이 원하는 자료형(request.type)으로
                 * 변환할 수 있도록 리플렉션을 활용합니다.*/
                println("convertForReceive"+request.type.javaObjectType.getMethod("parseFrom", ByteArray::class.java))
                try {
                    request.type.javaObjectType.getMethod("parseFrom", ByteArray::class.java).invoke(null, it)
                }
                catch ( e: InvocationTargetException) {
                    e.getTargetException().printStackTrace(); //getTargetException
                }

               // request.type.javaObjectType.getMethod("parseFrom", ByteArray::class.java).invoke(null, it)
            }

    }


}
