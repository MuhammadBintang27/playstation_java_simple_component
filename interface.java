// ---------------------- INTERFACES ----------------------
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface untuk manajemen penyewaan PlayStation (Provided Interface)
 */
interface IRentalMgt {
    boolean createRental(int customerId, int playstationId, int hours);
    boolean endRental(int rentalId);
    List<RentalTransaction> getAllRentals();
    List<RentalTransaction> getRentalsByDate(LocalDate date);
    List<PlayStation> getAvailablePlayStations();
    PlayStation getPlayStationById(int id);
    List<RentalTransaction> getActiveRentals();
}

/**
 * Interface untuk manajemen makanan dan minuman (Provided Interface)
 */
interface IFoodBeverageMgt {
    boolean createFoodBeverageTransaction(int customerId, Map<Integer, Integer> items);
    List<FoodBeverageTransaction> getAllFBTransactions();
    List<FoodBeverageTransaction> getFBTransactionsByDate(LocalDate date);
    List<FoodBeverage> getAllItems();
    FoodBeverage getItemById(int id);
}

/**
 * Interface untuk dashboard keuangan (Provided Interface)
 */
interface IFinancialDashboard {
    double getTotalRevenue(LocalDate startDate, LocalDate endDate);
    double getTotalRentalRevenue(LocalDate startDate, LocalDate endDate);
    double getTotalFBRevenue(LocalDate startDate, LocalDate endDate);
    double getTotalExpenses(LocalDate startDate, LocalDate endDate);
    double getProfit(LocalDate startDate, LocalDate endDate);
    Map<LocalDate, Double> getDailyRevenue(LocalDate startDate, LocalDate endDate);
}

/**
 * Interface untuk manajemen pengeluaran (Provided Interface)
 */
interface IExpenseMgt {
    boolean addExpense(String description, double amount, LocalDate date);
    boolean addExpense(String description, double amount, LocalDate date, String category);
    boolean updateExpense(int expenseId, String description, double amount, LocalDate date);
    boolean deleteExpense(int expenseId);
}

/**
 * Interface untuk laporan pengeluaran (Provided Interface)
 */
interface IExpenseReport {
    List<Expense> getAllExpenses();
    List<Expense> getExpensesByDate(LocalDate startDate, LocalDate endDate);
    List<Expense> getExpensesByCategory(String category);
    double getTotalExpenseAmount(LocalDate startDate, LocalDate endDate);
}

/**
 * Interface untuk customer management (Provided Interface)
 */
interface ICustomerMgt {
    int addCustomer(String name, String phone);
    Customer getCustomerById(int id);
}