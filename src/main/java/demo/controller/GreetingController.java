package demo.controller;

import demo.entity.Greeting;
import demo.entity.ListBook;
import demo.entity.Parent;
import demo.entity.Response;
import demo.hibernate.DatabaseProcess;
import demo.hibernate.entity.Book;
import demo.hibernate.entity.User;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Parent greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Parent tem = new Parent();
        tem.setIt(1);
        tem.setNameParent("NameParent");
        List<Greeting> lst = new ArrayList<>();
        lst.add(new Greeting(counter.incrementAndGet(),
                String.format(template, name)));
        tem.setLst(lst);
        return tem;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestParam(value = "firstName") String firstName,
                             @RequestParam(value = "lastName") String lastName,
                             @RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        boolean tem = DatabaseProcess.InsertOrUpdateUser(user);
        Response response = new Response();
        if (tem) {
            response.setCode("00");
            response.setMessage("Success");
        } else {
            response.setCode("01");
            response.setMessage("fail");
        }
        return response;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public void index() {

    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        if (getPrincipal() == null) {
            return "login";
        } else {
            return "index";
        }
    }

    private User getPrincipal() {
        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        User user = DatabaseProcess.findBySSO(email);
        return user;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public ListBook searchBook(@RequestParam(value = "title", defaultValue = "") String title) {
        ListBook lstBook = new ListBook();
        List<Book> lst = DatabaseProcess.getAllBook(title);
        lstBook.setLstBook(lst);
        return lstBook;
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/jpg")
    public byte[] getImage(@RequestParam(value = "filename", defaultValue = "") String filename) {
        try {
            String filePath = "static/img_book/" + filename + ".jpg";
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(filePath);
            // Prepare buffered image.
            BufferedImage img = ImageIO.read(is);
            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            // Write to output stream
            ImageIO.write(img, "jpg", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
