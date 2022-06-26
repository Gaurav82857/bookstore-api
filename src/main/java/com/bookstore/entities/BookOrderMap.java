package com.bookstore.entities;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bookstore.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "book_order_details")
public class BookOrderMap {
	
    @Id
    private String id;
    
    @OneToOne
    @JoinTable(name = "book_order_mapping", joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "orderdetail_id", referencedColumnName = "id"))
    private BookOrderDetails book;
    
    @OneToOne
    private Discount discount;
    private Double discountedPrice;
    private OrderStatus orderStatus;

    public BookOrderMap(){
        this.setId(UUID.randomUUID().toString());
        this.setOrderStatus(OrderStatus.PENDING);
    }
}
