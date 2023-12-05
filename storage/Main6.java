package com.aurickcode;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // It means that we can have methods that handles HTTP requests.
public class Main {
    // db
    private static List<Customer> customers;
    
    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(1, "Alex", "alex@gmail.com", 21);
        customers.add(alex);
        Customer jamila = new Customer(2, "Jamila", "jamila@gmail.com", 19);
        customers.add(jamila);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/api/v1/customers")
    public List<Customer> getCustomers() {
        return customers;
    }

    @GetMapping("/api/v1/customers/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Integer customerId) { // The way we capture that from the path and pass it without our method is
        Customer customer = customers.stream()
        .filter(c -> c.id.equals(customerId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Customer"));
        
        return customer;
    }

    // @GetMapping("/api/v1/customers/{id}")
    // public Customer getCustomers(int id) {
    //     for(Customer customer : customers) {
    //         if(customer.id == id) {
    //             return customer;
    //         }
    //     }
    //     return customers.get(id);
    // }

    static class Customer {
        private Integer id;
        private String name;
        private String email;
        private Integer age;
        
        public Customer() {
        }

        public Customer(Integer id, String name, String email, Integer age) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((email == null) ? 0 : email.hashCode());
            result = prime * result + ((age == null) ? 0 : age.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Customer other = (Customer) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (email == null) {
                if (other.email != null)
                    return false;
            } else if (!email.equals(other.email))
                return false;
            if (age == null) {
                if (other.age != null)
                    return false;
            } else if (!age.equals(other.age))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", age=" + age + "]";
        }
    }
}
