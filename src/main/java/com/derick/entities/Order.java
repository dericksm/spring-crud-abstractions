package com.derick.entities;

import com.derick.utils.FormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity(name = "order_table")
public class Order implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date date;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name="shipment_address_id")
    private Address shipmentAddress;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();

    public Order() {
    }

    public Order(Integer id, Date date, Client client, Address shipmentAddress) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.shipmentAddress = shipmentAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(Address shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public double getTotal(){
        double sum = items.stream().map(item -> item.getSubTotal()).reduce(0D, (a, b) -> a + b);
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Pedido Número: ");
        sb.append(getId());
        sb.append(", Data: ");
        sb.append(FormatUtils.formatDate(getDate()));
        sb.append(", Cliente: ");
        sb.append(getClient().getName());
        sb.append(", Pagamento: ");
        sb.append(getPayment().getPaymentStatus().getDescription());
        sb.append("\nDetalhes:\n");
        for (OrderItem item : getItems()) {
            sb.append(item.toString());
        }
        sb.append("Valor total: ");
        sb.append(FormatUtils.formatToBrazilianMonetaryPattern(getTotal()));
        return sb.toString();
    }
}
