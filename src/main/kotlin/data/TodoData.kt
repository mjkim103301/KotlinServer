package main.kotlin.data

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class TodoData(
    @Id
    @GeneratedValue
    var id:Int=0,
    var content:String="",
    var done:Boolean=false,
    var createdAt:LocalDateTime=LocalDateTime.now(),
    var updatedAt: LocalDateTime =LocalDateTime.now()


)
{
    override fun toString(): String {
        return "id= ${id}, content= ${content}, done=${done}, createdAt=${createdAt}, updatedAt=${updatedAt}"
    }
}