package main.kotlin.service;

import main.kotlin.data.TodoData

import main.kotlin.model.TodoRequest
import main.kotlin.model.TodoResponse
import main.kotlin.proto.TodoResources
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import java.time.LocalDateTime

class TodoServiceForProto {
    var todoData=TodoData()
   // var list:ArrayList<TodoData> = ArrayList()
    var sessionFactory: SessionFactory =HibernateUtil5.getSessionFactory()

    fun getAll() : List<TodoData> {

        var session: Session =sessionFactory.getCurrentSession()
        var tx: Transaction?=null
        try{
            tx=session.beginTransaction()

            val q = session.createQuery("from TodoData")
            val reviews = (q.list() as? List<TodoData> ?: throw Exception())//?.map(TodoResponse.Companion::of)
            //println(reviews)



            tx?.commit()
            return reviews
        }catch (e:Exception) {
            if (tx != null) {
                tx!!.rollback()
            } else {
                println("tx warn tx가 null입니다.")
            }
            throw Exception("Transaction getAll faild", e)

        }
    }

    fun getById(id: Int) : TodoData {
        //Todo.findById(id)?.run(TodoResponse.Companion::of) ?: throw NotFoundException()
        var session: Session = sessionFactory.getCurrentSession()

        var tx: Transaction? = null
        try {
            tx = session.beginTransaction()

            val q = session.createQuery("from TodoData where id=${id}")
            val reviews = (q.list() as? List<TodoData> ?: throw Exception())//?.map(TodoResponse.Companion::of)
            println(reviews)


            tx?.commit()
            return reviews[0]
        } catch (e: Exception) {
            if (tx != null) {
                tx!!.rollback()
            } else {
                println("tx warn tx가 null입니다.")
            }
            throw Exception("Transaction getById faild", e)

        }
    }

    fun new(content: TodoResources.Todo2) {



        var session: Session = sessionFactory.getCurrentSession()
        var tx: Transaction? = null
        try {
            tx = session.beginTransaction()

            todoData = TodoData().apply {

                this.content = content.toString()
                createdAt=LocalDateTime.now()
                updatedAt=LocalDateTime.now()

            }
            session.save(todoData)

            tx?.commit()
        } catch (e: Exception) {
            if (tx != null) {
                tx!!.rollback()
            } else {
                println("tx warn tx가 null입니다.")
            }
            throw Exception("Transaction getById faild", e)

        }

    }

    fun renew(id: Int, req: TodoResources.Todo2) {

        var session: Session = sessionFactory.getCurrentSession()
        var tx: Transaction? = null


        try {
            tx = session.beginTransaction()

            todoData=(session.get(TodoData::class.java, id) as TodoData)
            todoData.apply {
                content=req.content
                done=req.done?:false
                updatedAt=LocalDateTime.now()
            }


            session.saveOrUpdate(todoData)
            tx?.commit()
        } catch (e: Exception) {
            if (tx != null) {
                tx!!.rollback()
            } else {
                println("tx warn tx가 null입니다.")
            }
            throw Exception("Transaction renew faild", e)

        }

    }

    fun delete(id: Int) {
        //Todo.findById(id)?.delete() ?: throw NotFoundException()
        var session: Session = sessionFactory.getCurrentSession()
        var tx: Transaction? = null
        try {
            tx = session.beginTransaction()


            todoData=session.get(TodoData::class.java, id) as TodoData
            session.delete(todoData)
            tx?.commit()
        } catch (e: Exception) {
            if (tx != null) {
                tx!!.rollback()
            } else {
                println("tx warn tx가 null입니다.")
            }
            throw Exception("Transaction delete faild", e)

        }
    }
}
