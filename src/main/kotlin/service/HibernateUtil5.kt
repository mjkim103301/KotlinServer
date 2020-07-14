package main.kotlin.service


import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.registry.StandardServiceRegistryBuilder


class HibernateUtil5{
    companion object{
        val session:SessionFactory=buildSessionFactory();

        fun buildSessionFactory(): SessionFactory {
            val registry:StandardServiceRegistry=StandardServiceRegistryBuilder()
                .configure()
                .build()

            try{
                return MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory()
            }catch(e:Exception){
                StandardServiceRegistryBuilder.destroy(registry)
                System.err.println("Initial SeesionFactory creation faild."+e)
                throw ExceptionInInitializerError(e);
            }

        }


        fun getSessionFactory():SessionFactory{
            return session
        }

        //yck
        fun close(){
            session.close()
        }
    }

}