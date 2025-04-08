public interface ICustomerMgt {
    int addCustomer(String name, String phone);
    Customer getCustomerById(int id);
}