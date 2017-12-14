package demo.hibernate;


import demo.hibernate.entity.Book;
import demo.hibernate.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import static demo.Application.getConfig;

public class HibernateUtils {

    private static StandardServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;
    private static HibernateUtils instance = null;

    /**
     * <property name="hibernate.dialect">org.hibernate.dialect.Oracle10gDialect</property>
     * <property name="hibernate.connection.driver_class">oracle.jdbc.driver.OracleDriver</property>
     * <property name="hibernate.connection.url">jdbc:oracle:thin:@10.0.0.189:9521/orcl</property>
     * <property name="hibernate.connection.username">C##SYNCDATA</property>
     * <property name="hibernate.connection.password">Oracle_123456a#</property>
     * <property name="hibernate.show_sql">true</property>
     * <property name="hibernate.connection.release_mode">auto</property>
     * <property name="current_session_context_class">thread</property>
     * <property name="hibernate.connection.autoReconnect">true</property>
     * <property name="connection.pool_size">10</property>
     * <p>
     * <mapping class="main.soapservice.hibernate.entity.TableCommonCategory"></mapping>
     */
    private HibernateUtils() {
        Configuration configuration = new Configuration();
        // configuration.configure();
        configuration.setProperty("hibernate.dialect", getConfig("hibernate.dialect"));
        configuration.setProperty("hibernate.connection.driver_class", getConfig("hibernate.connection.driver_class"));
        configuration.setProperty("hibernate.connection.url", getConfig("hibernate.connection.url"));
        configuration.setProperty("hibernate.connection.username", getConfig("hibernate.connection.username"));
        configuration.setProperty("hibernate.connection.password", getConfig("hibernate.connection.password"));
        configuration.setProperty("hibernate.show_sql", getConfig("hibernate.show_sql"));
        configuration.setProperty("hibernate.connection.release_mode", getConfig("hibernate.connection.release_mode"));
        configuration.setProperty("current_session_context_class", getConfig("current_session_context_class"));
        configuration.setProperty("hibernate.connection.autoReconnect", getConfig("hibernate.connection.autoReconnect"));
        configuration.setProperty("connection.pool_size", getConfig("connection.pool_size"));
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Book.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public synchronized static HibernateUtils getInstance() {
        if (instance == null) {
            instance = new HibernateUtils();
        }
        return instance;
    }
//    private static SessionFactory buildSessionFactory() {
//        try {
//            // Create the SessionFactory from hibernate.cfg.xml
//            //
//            // return new Configuration().configure().buildSessionFactory();
//            Configuration configuration = new Configuration().configure();
//            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//            sessionFactory = configuration.configure().buildSessionFactory(serviceRegistry);
//            return  sessionFactory;
//        }
//        catch (Throwable ex) {
//            // Make sure you log the exception, as it might be swallowed
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public static void close() {
        getSessionFactory().getCurrentSession().close();
        getSessionFactory().close();
    }
}
