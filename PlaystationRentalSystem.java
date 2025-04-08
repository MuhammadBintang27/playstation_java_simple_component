// ---------------------- MAIN SYSTEM CLASS ----------------------
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Class utama yang menggabungkan semua komponen sistem
 */
class PlayStationRentalSystem {
    // Components with provided interfaces
    private RentalManager rentalManager;
    private FoodBeverageManager fbManager;
    private ExpenseManager expenseManager;
    private FinancialManager financialManager;
    private CustomerManager customerManager;
    
    public PlayStationRentalSystem() {
        this.rentalManager = new RentalManager();
        this.fbManager = new FoodBeverageManager();
        this.expenseManager = new ExpenseManager();
        this.financialManager = new FinancialManager();
        this.customerManager = new CustomerManager();
        
        // Connect required interfaces
        connectComponents();
        
        // Initialize system with sample data
        initializeSystem();
    }
    
    private void connectComponents() {
        // Connect FinancialManager to its required interfaces
        financialManager.connectTo((IRentalMgt) rentalManager);
        financialManager.connectTo((IFoodBeverageMgt) fbManager);
        financialManager.connectTo((IExpenseReport) expenseManager);
    }
    
    private void initializeSystem() {
        // Add sample PlayStations
        rentalManager.addPlayStation(new PlayStation(1, "PS5", 15000));
        rentalManager.addPlayStation(new PlayStation(2, "PS5", 15000));
        rentalManager.addPlayStation(new PlayStation(3, "PS4", 10000));
        rentalManager.addPlayStation(new PlayStation(4, "PS4", 10000));
        
        // Add sample food and beverages
        fbManager.addFoodBeverage(new FoodBeverage(1, "Mie Instan", 7000, 20, "FOOD"));
        fbManager.addFoodBeverage(new FoodBeverage(2, "Nugget", 12000, 15, "FOOD"));
        fbManager.addFoodBeverage(new FoodBeverage(3, "Coca Cola", 7000, 30, "BEVERAGE"));
        fbManager.addFoodBeverage(new FoodBeverage(4, "Air Mineral", 5000, 40, "BEVERAGE"));
        
        // Add sample customers
        customerManager.addCustomer("Walk-in Customer", "Anonymous");
        customerManager.addCustomer("Budi", "08123456789");
        customerManager.addCustomer("Ani", "08987654321");
    }
    
    // Delegasi ke CustomerManager (Provided Interface)
    public int addCustomer(String name, String phone) {
        return customerManager.addCustomer(name, phone);
    }
    
    public Customer getCustomerById(int id) {
        return customerManager.getCustomerById(id);
    }
    
    // Delegasi ke RentalManager (Provided Interface)
    public List<RentalTransaction> getActiveRentals() {
        return rentalManager.getActiveRentals();
    }
    
    public boolean createRental(int customerId, int playstationId, int hours) {
        return rentalManager.createRental(customerId, playstationId, hours);
    }
    
    public boolean endRental(int rentalId) {
        return rentalManager.endRental(rentalId);
    }
    
    public List<PlayStation> getAvailablePlayStations() {
        return rentalManager.getAvailablePlayStations();
    }
    
    public PlayStation getPlayStationById(int playstationId) {
        return rentalManager.getPlayStationById(playstationId);
    }
    
    // Delegasi ke FoodBeverageManager (Provided Interface)
    public boolean createFBTransaction(int customerId, Map<Integer, Integer> items) {
        return fbManager.createFoodBeverageTransaction(customerId, items);
    }
    
    public List<FoodBeverage> getAllFBItems() {
        return fbManager.getAllItems();
    }
    
    // Delegasi ke FinancialManager (Provided Interface)
    public double getTotalRevenue(LocalDate startDate, LocalDate endDate) {
        return financialManager.getTotalRevenue(startDate, endDate);
    }
    
    public double getProfit(LocalDate startDate, LocalDate endDate) {
        return financialManager.getProfit(startDate, endDate);
    }
    
    public Map<LocalDate, Double> getDailyRevenue(LocalDate startDate, LocalDate endDate) {
        return financialManager.getDailyRevenue(startDate, endDate);
    }
    
    // Delegasi ke ExpenseManager (Provided Interface)
    public boolean addExpense(String description, double amount, LocalDate date, String category) {
        return expenseManager.addExpense(description, amount, date, category);
    }
    
    public List<Expense> getAllExpenses() {
        return expenseManager.getAllExpenses();
    }
    
    public List<Expense> getExpensesByDate(LocalDate startDate, LocalDate endDate) {
        return expenseManager.getExpensesByDate(startDate, endDate);
    }
    
    public double getTotalExpenses(LocalDate startDate, LocalDate endDate) {
        return expenseManager.getTotalExpenseAmount(startDate, endDate);
    }
}