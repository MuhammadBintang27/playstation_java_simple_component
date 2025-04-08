// ---------------------- MODEL CLASSES ----------------------

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;


/**
 * Class untuk pelanggan
 */
class Customer {
    private int id;
    private String name;
    private String phone;
    
    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    @Override
    public String toString() {
        return String.format("Customer[id=%d, name=%s, phone=%s]", id, name, phone);
    }
}

/**
 * Class untuk PlayStation
 */
class PlayStation {
    private int id;
    private String model;
    private boolean isAvailable;
    private double hourlyRate;
    
    public PlayStation(int id, String model, double hourlyRate) {
        this.id = id;
        this.model = model;
        this.isAvailable = true;
        this.hourlyRate = hourlyRate;
    }
    
    public int getId() { return id; }
    public String getModel() { return model; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    
    @Override
    public String toString() {
        return String.format("PlayStation[id=%d, model=%s, available=%b, rate=%.2f]", 
                             id, model, isAvailable, hourlyRate);
    }
}


/**
 * Class untuk item makanan/minuman
 */
class FoodBeverage {
    private int id;
    private String name;
    private double price;
    private int stock;
    private String category; // "FOOD" or "BEVERAGE"
    
    public FoodBeverage(int id, String name, double price, int stock, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getCategory() { return category; }
    
    public boolean decreaseStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("FoodBeverage[id=%d, name=%s, price=%.2f, stock=%d, category=%s]", 
                             id, name, price, stock, category);
    }
}

/**
 * Class untuk transaksi penyewaan
 */
class RentalTransaction {
    private int id;
    private int customerId;
    private int playstationId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int hours;
    private double amount;
    private boolean active;
    
    public RentalTransaction(int id, int customerId, int playstationId, LocalDateTime startTime, int hours, double amount) {
        this.id = id;
        this.customerId = customerId;
        this.playstationId = playstationId;
        this.startTime = startTime;
        this.hours = hours;
        this.amount = amount;
        this.active = true; // Memastikan status aktif
    }
    
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public int getPlaystationId() { return playstationId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public int getHours() { return hours; }
    public double getAmount() { return amount; }
    public boolean isActive() { return active; }
    
    public void endRental() {
        this.endTime = LocalDateTime.now();
        this.active = false;
    }
    
    public LocalDate getTransactionDate() {
        return startTime.toLocalDate();
    }
}

/**
 * Class untuk transaksi makanan/minuman
 */
class FoodBeverageTransaction {
    private int id;
    private int customerId;
    private Map<Integer, Integer> items; // item ID -> quantity
    private LocalDateTime timestamp;
    private double totalAmount;
    
    public FoodBeverageTransaction(int id, int customerId, Map<Integer, Integer> items, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.timestamp = LocalDateTime.now();
        this.totalAmount = totalAmount;
    }
    
    // Getters
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public Map<Integer, Integer> getItems() { return items; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getTotalAmount() { return totalAmount; }
    
    public LocalDate getTransactionDate() {
        return timestamp.toLocalDate();
    }
    
    @Override
    public String toString() {
        return String.format("FBTransaction[id=%d, customerId=%d, timestamp=%s, amount=%.2f]", 
                             id, customerId, timestamp, totalAmount);
    }
}

/**
 * Class untuk pengeluaran
 */
class Expense {
    private int id;
    private String description;
    private double amount;
    private LocalDate date;
    private String category;
    
    public Expense(int id, String description, double amount, LocalDate date, String category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    @Override
    public String toString() {
        return String.format("Expense[id=%d, desc=%s, amount=%.2f, date=%s, category=%s]", 
                             id, description, amount, date, category);
    }
}

