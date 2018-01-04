package demo.hibernate;


import demo.hibernate.entity.Book;
import demo.hibernate.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.ArrayList;
import java.util.List;

public class DatabaseProcess {
    private static final Logger log = Logger.getLogger(DatabaseProcess.class);

    public static User findBySSO(String username) {
        SessionFactory factory = HibernateUtils.getInstance().getSessionFactory();
        Session session = factory.openSession();
        List<User> lst = new ArrayList<User>();
        try {
            session.getTransaction().begin();
            String sql = " SELECT * FROM APP_USER where 1=1 and username=:p_email";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.setParameter("p_email", username);

            lst = query.list();
            if (lst.size() > 0) {
                return lst.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error getListContract:" + e);
        } finally {
            session.close();
        }
        return null;
    }

    public static List<User> getAllUser() {
        SessionFactory factory = HibernateUtils.getInstance().getSessionFactory();
        Session session = factory.openSession();
        List<User> lst = new ArrayList<User>();
        try {
            session.getTransaction().begin();
            String sql = " SELECT * FROM APP_USER where 1=1";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            lst = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error getListContract:" + e);
        } finally {
            session.close();
        }
        return lst;
    }

    public static boolean InsertOrUpdateUser(User user) {
        SessionFactory factory = HibernateUtils.getInstance().getSessionFactory();
        Session session = factory.openSession();
        session.getTransaction().begin();
        try {
            if (user.getId() == null || user.getId() <= 0) {
                int id = getSequenseCommonCategory(session, "USER_SEQ");
                user.setId(id);
            }
            session.saveOrUpdate(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(" insertConfigCategory :", e);
        } finally {
            session.close();
        }
        return false;
    }

    public static int getSequenseCommonCategory(Session session, String sequence) {
        String sql = " SELECT " + sequence + ".nextval FROM dual ";
        try {
            Query query = session.createSQLQuery(sql);
            int key = Integer.parseInt(query.uniqueResult().toString());
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getSeqence: ", e);
        }
        return 0;
    }

    public static List<Book> getAllBook(String title) {
        SessionFactory factory = HibernateUtils.getInstance().getSessionFactory();
        Session session = factory.openSession();
        List<Book> lst = new ArrayList<Book>();
        try {
            session.getTransaction().begin();
            String sql = " SELECT * FROM BOOK where 1=1 and lower(KEY_SEARCH) like lower(:title)";
            Query query = session.createSQLQuery(sql).addEntity(Book.class);
            query.setParameter("title", "%" + title + "%");
            lst = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error getListContract:" + e);
        } finally {
            session.close();
        }
        return lst;
    }
}
