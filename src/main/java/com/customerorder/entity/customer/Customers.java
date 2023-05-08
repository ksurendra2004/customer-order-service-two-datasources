package com.customerorder.entity.customer;

import com.customerorder.util.HashMapConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.persistence.*;
import java.io.IOException;
import java.util.Map;

@Data
@Entity
@Table(name = "customer_details", schema = "customers")
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "customer_attribute_json")
    private String customerAttributeJSON;
    @Column(name = "customer_attributes")
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> customerAttributes;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void serializeCustomerAttributes() throws JsonProcessingException {
        this.customerAttributeJSON = objectMapper.writeValueAsString(customerAttributes);
    }

    public void deserializeCustomerAttributes() throws IOException {
        this.customerAttributes = objectMapper.readValue(customerAttributeJSON,  new TypeReference<Map<String, Object>>() {});
    }

}

