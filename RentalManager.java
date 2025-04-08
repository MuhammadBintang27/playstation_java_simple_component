import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// ---------------------- MANAGER CLASSES ----------------------

/**
 * Class untuk mengelola penyewaan PlayStation
 */
public class RentalManager implements IRentalMgt {
    private List<RentalTransaction> rentals;
    private List<PlayStation> playstations;
    private static int nextRentalId = 1;
    
    public RentalManager() {
        this.rentals = new ArrayList<>();
        this.playstations = new ArrayList<>();
    }
    
    public void addPlayStation(PlayStation ps) {
        playstations.add(ps);
    }
    
    @Override
    public List<PlayStation> getAvailablePlayStations() {
        return playstations.stream()
                .filter(PlayStation::isAvailable)
                .collect(Collectors.toList());
    }
    
    @Override
    public PlayStation getPlayStationById(int id) {
        return playstations.stream()
                .filter(ps -> ps.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public boolean createRental(int customerId, int playstationId, int hours) {
        PlayStation ps = getPlayStationById(playstationId);
        if (ps == null || !ps.isAvailable()) {
            System.out.println("Error: PlayStation tidak tersedia atau tidak ditemukan!");
            return false;
        }
        
        double amount = ps.getHourlyRate() * hours;
        RentalTransaction rental = new RentalTransaction(
            nextRentalId++, customerId, playstationId, LocalDateTime.now(), hours, amount
        );
        
        rentals.add(rental);
        ps.setAvailable(false);
        System.out.println("Debug: Transaksi berhasil ditambahkan. Total transaksi aktif: " + rentals.size());
        return true;
    }
    
    @Override
    public boolean endRental(int rentalId) {
        Optional<RentalTransaction> rentalOpt = rentals.stream()
                .filter(r -> r.getId() == rentalId && r.isActive())
                .findFirst();
        
        if (rentalOpt.isPresent()) {
            RentalTransaction rental = rentalOpt.get();
            rental.endRental();
            System.out.println("Debug: Rental ID " + rental.getId() + " selesai pada " + rental.getEndTime());
            
            PlayStation ps = getPlayStationById(rental.getPlaystationId());
            if (ps != null) {
                ps.setAvailable(true);
            }
            return true;
        }
        System.out.println("Error: Rental tidak ditemukan atau sudah selesai!");
        return false;
    }
    
    @Override
    public List<RentalTransaction> getActiveRentals() {
        List<RentalTransaction> activeRentals = rentals.stream()
                .filter(r -> {
                    System.out.println("Debug: Rental ID " + r.getId() + ", Status: " + r.isActive());
                    return r.isActive();
                })
                .collect(Collectors.toList());
    
        System.out.println("Debug: Active rentals count = " + activeRentals.size());
        return activeRentals;
    }
    
    @Override
    public List<RentalTransaction> getAllRentals() {
        return new ArrayList<>(rentals);
    }
    
    @Override
    public List<RentalTransaction> getRentalsByDate(LocalDate date) {
        return rentals.stream()
                .filter(rental -> rental.getTransactionDate().equals(date))
                .collect(Collectors.toList());
    }
}