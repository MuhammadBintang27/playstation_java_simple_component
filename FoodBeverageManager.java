import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class untuk mengelola transaksi makanan/minuman
 */
public class FoodBeverageManager implements IFoodBeverageMgt {
    private List<FoodBeverageTransaction> transactions;
    private List<FoodBeverage> inventory;
    private static int nextTransactionId = 1;
    
    public FoodBeverageManager() {
        this.transactions = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }
    
    public void addFoodBeverage(FoodBeverage item) {
        inventory.add(item);
    }
    
    @Override
    public FoodBeverage getItemById(int id) {
        for (FoodBeverage item : inventory) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
    
    @Override
    public List<FoodBeverage> getAllItems() {
        return new ArrayList<>(inventory);
    }
    
    @Override
    public boolean createFoodBeverageTransaction(int customerId, Map<Integer, Integer> items) {
        double total = 0;
        Map<Integer, Integer> validItems = new HashMap<>();
        
        // Verify all items are in stock and calculate total
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            
            FoodBeverage item = getItemById(itemId);
            if (item == null || !item.decreaseStock(quantity)) {
                // Rollback any stock changes made so far
                for (Map.Entry<Integer, Integer> validEntry : validItems.entrySet()) {
                    FoodBeverage validItem = getItemById(validEntry.getKey());
                    validItem.setStock(validItem.getStock() + validEntry.getValue());
                }
                return false;
            }
            
            validItems.put(itemId, quantity);
            total += item.getPrice() * quantity;
        }
        
        // Create transaction
        FoodBeverageTransaction transaction = new FoodBeverageTransaction(
            nextTransactionId++, customerId, validItems, total
        );
        transactions.add(transaction);
        return true;
    }
    
    @Override
    public List<FoodBeverageTransaction> getAllFBTransactions() {
        return new ArrayList<>(transactions);
    }
    
    @Override
    public List<FoodBeverageTransaction> getFBTransactionsByDate(LocalDate date) {
        List<FoodBeverageTransaction> result = new ArrayList<>();
        for (FoodBeverageTransaction transaction : transactions) {
            if (transaction.getTransactionDate().equals(date)) {
                result.add(transaction);
            }
        }
        return result;
    }
}