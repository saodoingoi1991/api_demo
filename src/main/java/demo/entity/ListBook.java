package demo.entity;

import demo.hibernate.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class ListBook {
    private int total;
    private List<Book> lstBook;

    public ListBook() {
        lstBook = new ArrayList<>();
        total = 0;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Book> getLstBook() {
        return lstBook;
    }

    public void setLstBook(List<Book> lstBook) {
        this.lstBook = lstBook;
    }
}
