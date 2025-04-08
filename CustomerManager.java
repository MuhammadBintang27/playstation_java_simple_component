import java.util.ArrayList;
import java.util.List;

/**
 * Class untuk manajemen customer
 */
public class CustomerManager implements ICustomerMgt {
    private List<Customer> customers;
    private static int nextCustomerId = 1;
    
    public CustomerManager() {
        this.customers = new ArrayList<>();
    }
    
    @Override
    public int addCustomer(String name, String phone) {
        Customer customer = new Customer(nextCustomerId++, name, phone);
        customers.add(customer);
        return customer.getId();
    }
    
    @Override
    public Customer getCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }
}