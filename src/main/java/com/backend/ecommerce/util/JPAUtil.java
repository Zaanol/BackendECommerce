package com.backend.ecommerce.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class JPAUtil {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ecommerceQualidade");
	
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

        Query q = em.createQuery("SELECT G FROM " + objClass.getCanonicalName() + " G", objClass);
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