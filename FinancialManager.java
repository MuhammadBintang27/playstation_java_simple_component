import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk mengelola keuangan
 */
public class FinancialManager implements IFinancialDashboard {
    // Required interfaces
    private IRentalMgt rentalManager;
    private IFoodBeverageMgt fbManager;
    private IExpenseReport expenseManager;
    
    public FinancialManager() {
        // Required interfaces akan dikoneksikan melalui connectTo
    }
    
    public void connectTo(IRentalMgt rentalManager) {
        this.rentalManager = rentalManager;
    }
    
    public void connectTo(IFoodBeverageMgt fbManager) {
        this.fbManager = fbManager;
    }
    
    public void connectTo(IExpenseReport expenseManager) {
        this.expenseManager = expenseManager;
    }
    
    @Override
    public double getTotalRevenue(LocalDate startDate, LocalDate endDate) {
        return getTotalRentalRevenue(startDate, endDate) + getTotalFBRevenue(startDate, endDate);
    }
    
    @Override
    public double getTotalRentalRevenue(LocalDate startDate, LocalDate endDate) {
        if (rentalManager == null) return 0;
        
        double total = 0;
        for (RentalTransaction rental : rentalManager.getAllRentals()) {
            LocalDate date = rental.getTransactionDate();
            if ((date.isEqual(startDate) || date.isAfter(startDate)) && 
                (date.isEqual(endDate) || date.isBefore(endDate))) {
                total += rental.getAmount();
            }
        }
        return total;
    }
    
    @Override
    public double getTotalFBRevenue(LocalDate startDate, LocalDate endDate) {
        if (fbManager == null) return 0;
        
        double total = 0;
        for (FoodBeverageTransaction transaction : fbManager.getAllFBTransactions()) {
            LocalDate date = transaction.getTransactionDate();
            if ((date.isEqual(startDate) || date.isAfter(startDate)) && 
                (date.isEqual(endDate) || date.isBefore(endDate))) {
                total += transaction.getTotalAmount();
            }
        }
        return total;
    }
    
    @Override
    public double getTotalExpenses(LocalDate startDate, LocalDate endDate) {
        if (expenseManager == null) return 0;
        return expenseManager.getTotalExpenseAmount(startDate, endDate);
    }
    
    @Override
    public double getProfit(LocalDate startDate, LocalDate endDate) {
        return getTotalRevenue(startDate, endDate) - getTotalExpenses(startDate, endDate);
    }
    
    @Override
    public Map<LocalDate, Double> getDailyRevenue(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, Double> dailyRevenue = new HashMap<>();
        if (rentalManager == null || fbManager == null) return dailyRevenue;
        
        LocalDate current = startDate;
        
        while (!current.isAfter(endDate)) {
            double rentalRevenue = 0;
            for (RentalTransaction rental : rentalManager.getRentalsByDate(current)) {
                rentalRevenue += rental.getAmount();
            }
            
            double fbRevenue = 0;
            for (FoodBeverageTransaction transaction : fbManager.getFBTransactionsByDate(current)) {
                fbRevenue += transaction.getTotalAmount();
            }
            
            dailyRevenue.put(current, rentalRevenue + fbRevenue);
            current = current.plusDays(1);
        }
        
        return dailyRevenue;
    }
}