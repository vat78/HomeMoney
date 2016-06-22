package ru.vat78.homeMoney.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="category", referencedColumnName = "id")
    private Category category;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="bill", referencedColumnName = "id")
    private Bill bill;

    @NotNull
    @Column
    private float sum;

    @Column
    private float quantity;

    @Column
    private float price;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="tag", referencedColumnName = "id")
    private Tag tag;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="person", referencedColumnName = "id")
    private Person person;

    @Column
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
