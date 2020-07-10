package main.kotlin.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.pool.HikariProxyCallableStatement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import main.kotlin.data.TodoData
import main.kotlin.entity.Todos
import main.kotlin.service.HibernateUtil5
import org.hibernate.SessionFactory
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseInitializer {
    val session: SessionFactory = HibernateUtil5.buildSessionFactory();
    fun init() {
        /*Database.connect(HikariDataSource(hikariConfig()))
        transaction {
            create(Todos)
        }*/
        HikariDataSource(hikariConfig())

      //  TodoData
    }
}

private fun hikariConfig() =
    HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:test"
        maximumPoolSize = 3
        isAutoCommit = false
        username = "sa"
        password = "sa"
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }



/*
suspend fun <T> query(block: () -> T): T = withContext(Dispatchers.IO) {
    transaction {
        block()
    }
}*/
