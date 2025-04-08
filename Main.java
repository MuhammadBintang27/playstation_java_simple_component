import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    private static PlayStationRentalSystem system;
    private static Scanner scanner;
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static void main(String[] args) {
        system = new PlayStationRentalSystem();
        scanner = new Scanner(System.in);
        
        boolean running = true;
        while (running) {
            System.out.println("\n===== PLAYSTATION RENTAL SYSTEM =====");
            System.out.println("1. Kasir Menu");
            System.out.println("2. Pemilik Menu");
            System.out.println("0. Exit");
            System.out.print("Pilih menu: ");
            
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    showCashierMenu();
                    break;
                case 2:
                    showOwnerMenu();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        
        System.out.println("Terima kasih telah menggunakan PlayStation Rental System!");
        scanner.close();
    }
    
    private static void showCashierMenu() {
        boolean backToMainMenu = false;
        
        while (!backToMainMenu) {
            System.out.println("\n===== MENU KASIR =====");
            System.out.println("1. Mencatat Transaksi Sewa PS");
            System.out.println("2. Mengakhiri Sewa PS");
            System.out.println("3. Mencatat Transaksi Pembelian Makanan/Minuman");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");
            
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    createRentalTransaction();
                    break;
                case 2:
                    endRentalTransaction();
                    break;
                case 3:
                    createFBTransaction();
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    private static void showOwnerMenu() {
        boolean backToMainMenu = false;
        
        while (!backToMainMenu) {
            System.out.println("\n===== MENU PEMILIK =====");
            System.out.println("1. Melihat Dashboard Keuangan");
            System.out.println("2. Mencatat Pengeluaran");
            System.out.println("3. Melihat Catatan Pengeluaran");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");
            
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    showFinancialDashboard();
                    break;
                case 2:
                    addExpense();
                    break;
                case 3:
                    viewExpenses();
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    private static void createRentalTransaction() {
        System.out.println("\n===== MENCATAT TRANSAKSI SEWA PS =====");
        
        // Show available PlayStations
        List<PlayStation> available = system.getAvailablePlayStations();
        if (available.isEmpty()) {
            System.out.println("Tidak ada PlayStation yang tersedia saat ini.");
            return;
        }
        
        System.out.println("PlayStation yang tersedia:");
        available.forEach(ps -> System.out.printf("%d. %s (Rp %.0f/jam)\n", 
                                                  ps.getId(), ps.getModel(), ps.getHourlyRate()));
        
        // Get customer info
        System.out.print("ID Pelanggan (1 untuk Walk-in): ");
        int customerId = getIntInput();
        
        // Get PlayStation selection
        System.out.print("ID PlayStation yang akan disewa: ");
        int psId = getIntInput();
        
        // Get rental duration
        System.out.print("Durasi sewa (jam): ");
        int hours = getIntInput();
        
        // Create rental transaction
        System.out.println(system.createRental(customerId, psId, hours) ? 
                          "Transaksi sewa berhasil dicatat!" : 
                          "Gagal mencatat transaksi sewa. Pastikan data valid.");
    }
    
    private static void endRentalTransaction() {
        System.out.println("\n===== MENGAKHIRI SEWA PS =====");
    
        List<RentalTransaction> activeRentals = system.getActiveRentals();
        if (activeRentals.isEmpty()) {
            System.out.println("Tidak ada transaksi sewa yang sedang berlangsung.");
            return;
        }
        
        System.out.println("Transaksi sewa yang sedang berlangsung:");
        activeRentals.forEach(rental -> {
            PlayStation ps = system.getPlayStationById(rental.getPlaystationId());
            System.out.printf("ID: %d, PlayStation: %s, Pelanggan ID: %d, Durasi: %d jam\n", 
                              rental.getId(), 
                              (ps != null ? ps.getModel() : "Unknown"), 
                              rental.getCustomerId(), 
                              rental.getHours());
        });
        
        System.out.print("Masukkan ID Transaksi Sewa yang akan diakhiri: ");
        int rentalId = getIntInput();
        
        System.out.println(system.endRental(rentalId) ? 
                          "Transaksi sewa berhasil diakhiri." : 
                          "Gagal mengakhiri transaksi. Pastikan ID valid.");
    }
    
    
    private static void createFBTransaction() {
        System.out.println("\n===== MENCATAT TRANSAKSI PEMBELIAN MAKANAN/MINUMAN =====");
        
        // Menampilkan daftar makanan dan minuman
        List<FoodBeverage> items = system.getAllFBItems();
        if (items.isEmpty()) {
            System.out.println("Tidak ada item yang tersedia.");
            return;
        }
        
        System.out.println("Makanan dan Minuman yang tersedia:");
        for (FoodBeverage item : items) {
            System.out.printf("%d. %s (Rp %.0f) - Stok: %d\n", item.getId(), item.getName(), item.getPrice(), item.getStock());
        }
        
        // Memilih pelanggan
        System.out.print("ID Pelanggan (1 untuk Walk-in): ");
        int customerId = getIntInput();
        
        // Memilih item yang dibeli
        Map<Integer, Integer> order = new HashMap<>();
        boolean addingItems = true;
        while (addingItems) {
            System.out.print("Masukkan ID Item (0 untuk selesai): ");
            int itemId = getIntInput();
            if (itemId == 0) {
                addingItems = false;
                continue;
            }
            
            System.out.print("Jumlah: ");
            int quantity = getIntInput();
            order.put(itemId, quantity);
        }
        
        // Membuat transaksi
        boolean success = system.createFBTransaction(customerId, order);
        if (success) {
            System.out.println("Transaksi pembelian berhasil dicatat!");
        } else {
            System.out.println("Gagal mencatat transaksi pembelian. Pastikan data valid.");
        }
    }
    
    private static void showFinancialDashboard() {
        System.out.println("\n===== DASHBOARD KEUANGAN =====");
        
        System.out.print("Masukkan tanggal mulai (dd/MM/yyyy): ");
        LocalDate startDate = getDateInput();
        
        System.out.print("Masukkan tanggal akhir (dd/MM/yyyy): ");
        LocalDate endDate = getDateInput();
        
        double revenue = system.getTotalRevenue(startDate, endDate);
        double profit = system.getProfit(startDate, endDate);
        double totalExpense = system.getTotalExpenses(startDate, endDate);
        System.out.printf("Total Pengeluaran: Rp %.0f\n", totalExpense);
        System.out.printf("Total Pendapatan: Rp %.0f\n", revenue);
        System.out.printf("Total Keuntungan: Rp %.0f\n", profit);
    }
    
    private static void addExpense() {
        System.out.println("\n===== MENCATAT PENGELUARAN =====");
        
        System.out.print("Deskripsi pengeluaran: ");
        String description = scanner.nextLine();
        
        System.out.print("Jumlah pengeluaran: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Tanggal pengeluaran (dd/MM/yyyy): ");
        LocalDate date = getDateInput();
        
        System.out.print("Kategori pengeluaran: ");
        String category = scanner.nextLine();
        
        boolean success = system.addExpense(description, amount, date, category);
        if (success) {
            System.out.println("Pengeluaran berhasil dicatat!");
        } else {
            System.out.println("Gagal mencatat pengeluaran.");
        }
    }
    
    private static void viewExpenses() {
        System.out.println("\n===== MELIHAT CATATAN PENGELUARAN =====");
        
        System.out.print("Masukkan tanggal mulai (dd/MM/yyyy): ");
        LocalDate startDate = getDateInput();
        
        System.out.print("Masukkan tanggal akhir (dd/MM/yyyy): ");
        LocalDate endDate = getDateInput();
        
        List<Expense> expenses = system.getExpensesByDate(startDate, endDate);
        if (expenses.isEmpty()) {
            System.out.println("Tidak ada catatan pengeluaran pada periode ini.");
            return;
        }
        
        System.out.println("Daftar Pengeluaran:");
        for (Expense exp : expenses) {
            System.out.printf("%s - %s: Rp %.0f (%s)\n", exp.getDate(), exp.getDescription(), exp.getAmount(), exp.getCategory());
        }
        
        double totalExpense = system.getTotalExpenses(startDate, endDate);
        System.out.printf("Total Pengeluaran: Rp %.0f\n", totalExpense);
    }
    
    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid. Masukkan angka: ");
            }
        }
    }
    
    private static LocalDate getDateInput() {
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine().trim(), dateFormatter);
            } catch (Exception e) {
                System.out.print("Format tanggal salah. Masukkan kembali (dd/MM/yyyy): ");
            }
        }
    }
}