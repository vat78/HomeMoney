package ru.vat78.homeMoney.model;

import com.sun.istack.internal.NotNull;
import ru.vat78.homeMoney.model.dictionaries.Category;
import ru.vat78.homeMoney.model.dictionaries.Person;
import ru.vat78.homeMoney.model.dictionaries.Tag;
import ru.vat78.homeMoney.model.transactions.Bill;

import javax.persistence.*;

@Entity
@Table(name = Defenitions.TABLES.PAYMENTS)
public class Payment {

    @Id
    @Column(name = Defenitions.FIELDS.ID)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.CATEGORY_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private Category category;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.BILL_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private Bill bill;

    @NotNull
    @Column(name = Defenitions.FIELDS.SUM)
    private float sum;

    @Column(name = Defenitions.FIELDS.QUANTITY)
    private float quantity;

    @Column(name = Defenitions.FIELDS.PRICE)
    private float price;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.TAG_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private Tag tag;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name= Defenitions.FIELDS.PERSON_ID, referencedColumnName = Defenitions.FIELDS.ID)
    private Person person;

    @Column(name = Defenitions.FIELDS.COMMENTS)
    String comments;

    public Category getCategory() {
        return category;
    }

    public float getSum() {
        return sum;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public Tag getTag() {
        return tag;
    }

    public Person getPerson() {
        return person;
    }

    public String getComments() {
        return comments;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
