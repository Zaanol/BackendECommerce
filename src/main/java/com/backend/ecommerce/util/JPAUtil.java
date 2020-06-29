package com.backend.ecommerce.util;

import com.backend.ecommerce.service.ServerInit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class JPAUtil {
	private static EntityManagerFactory emf;

    static {
        try {
            Properties prop = ServerInit.getConfig();
            prop.remove("server.port");
            emf = Persistence.createEntityManagerFactory("ecommerceQualidade", prop);
        } catch (IOException e) {
            System.out.println("[BackendECommerce - ERROR] - Could not create settings file!");
        }
    }

    private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static Object persist(Object obj){
        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();

        em.close();

        return obj;
    }

    public static List<Object> persist(List<Object> listObj){
        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        em.persist(listObj);
        em.getTransaction().commit();

        em.close();

        return listObj;
    }

    public static Object getObject(Class<?> objClass, Object id){
        EntityManager em = new JPAUtil().getEntityManager();

        Object result = em.find(objClass, id);
        em.close();

        return result;
    }

    public static List<Object> getObjects(Class<?> objClass){
        EntityManager em = new JPAUtil().getEntityManager();

        Query q = em.createQuery("SELECT G FROM " + objClass.getCanonicalName() + " G ORDER BY G.id", objClass);
        @SuppressWarnings("unchecked")
        List<Object> listObj = q.getResultList();
        em.close();

        return listObj;
    }

    public static void remove(Object obj, Integer id){
        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        em.remove(em.getReference(obj.getClass(), id));
        em.getTransaction().commit();

        em.close();
    }

    public static void remove(List<Object> listObj){
        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        for(Object obj : listObj){
            em.remove(obj);
        }
        em.getTransaction().commit();

        em.close();
    }

    public static Object update(Object obj){
        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();

        em.close();

        return obj;
    }

    public static List<Object> update(List<Object> listObj){
        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        em.merge(listObj);
        em.getTransaction().commit();
        em.close();

        return listObj;
    }
}