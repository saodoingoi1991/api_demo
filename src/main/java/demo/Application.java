package demo;

import demo.hibernate.DatabaseProcess;
import demo.hibernate.entity.User;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    private static final Logger log = Logger.getLogger(Application.class);
    public static Properties properties;
    public static Collection<GrantedAuthority> authorities = new HashSet<>();

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        try {
            BasicConfigurator.configure();
            properties = new Properties();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("config.properties");
            properties.load(is);
        } catch (IOException e) {
            log.error("get config error:" + e);
        }
        SpringApplication.run(Application.class, args);
//        User user = DatabaseProcess.findBySSO("long@gmail.com");
//        System.out.printf("user");
    }


    public static String getConfig(String key) {
        return properties.getProperty(key);

    }
}
