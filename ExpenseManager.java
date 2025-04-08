import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class untuk mengelola pengeluaran
 */
public class ExpenseManager implements IExpenseMgt, IExpenseReport {
    private List<Expense> expenses;
    private static int nextExpenseId = 1;
    
    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }
    
    @Override
    public boolean addExpense(String description, double amount, LocalDate date) {
        return addExpense(description, amount, date, "GENERAL");
    }
    
    @Override
    public boolean addExpense(String description, double amount, LocalDate date, String category) {
        if (amount <= 0 || description == null || description.trim().isEmpty()) {
            return false;
        }
        
        expenses.add(new Expense(nextExpenseId++, description, amount, date, category));
        return true;
    }
    
    @Override
    public boolean updateExpense(int expenseId, String description, double amount, LocalDate date) {
        return expenses.stream()
                .filter(expense -> expense.getId() == expenseId)
                .findFirst()
                .map(expense -> {
                    if (description != null && !description.trim().isEmpty()) {
                        expense.setDescription(description);
                    }
                    if (amount > 0) {
                        expense.setAmount(amount);
                    }
                    if (date != null) {
                        expense.setDate(date);
                    }
                    return true;
                })
                .orElse(false);
    }
    
    @Override
    public boolean deleteExpense(int expenseId) {
        return expenses.removeIf(expense -> expense.getId() == expenseId);
    }
    
    @Override
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }
    
    @Override
    public List<Expense> getExpensesByDate(LocalDate startDate, LocalDate endDate) {
        return expenses.stream()
                .filter(expense -> {
                    LocalDate date = expense.getDate();
                    return (date.isEqual(startDate) || date.isAfter(startDate)) && 
                           (date.isEqual(endDate) || date.isBefore(endDate));
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Expense> getExpensesByCategory(String category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory().equals(category))
                .collect(Collectors.toList());
    }
    
    @Override
    public double getTotalExpenseAmount(LocalDate startDate, LocalDate endDate) {
        return getExpensesByDate(startDate, endDate).stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}