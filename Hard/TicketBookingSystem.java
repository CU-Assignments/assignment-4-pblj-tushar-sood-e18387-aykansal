import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class TicketBookingSystem {
    private static final int TOTAL_SEATS = 10;
    private int[] seats;
    private ReentrantLock lock = new ReentrantLock();
    private Random random = new Random();

    public TicketBookingSystem() {
        seats = new int[TOTAL_SEATS];
        Arrays.fill(seats, 0);
    }

    class BookingThread extends Thread {
        private String userName;
        private boolean isVIP;
        private int requestedSeats;

        public BookingThread(String userName, boolean isVIP, int requestedSeats) {
            this.userName = userName;
            this.isVIP = isVIP;
            this.requestedSeats = requestedSeats;
        }

        @Override
        public void run() {
            if (isVIP) {
                setPriority(Thread.MAX_PRIORITY);
            } else {
                setPriority(Thread.NORM_PRIORITY - 1);
            }

            try {
                Thread.sleep(random.nextInt(1000));
                bookSeats(userName, isVIP, requestedSeats);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(userName + " booking interrupted!");
            }
        }
    }

    private void bookSeats(String userName, boolean isVIP, int requestedSeats) {
        lock.lock();
        try {
            int availableSeats = countAvailableSeats();
            if (availableSeats < requestedSeats) {
                System.out.println(userName + " (VIP: " + isVIP + "): Sorry, only " + availableSeats + " seats available. Cannot book " + requestedSeats + " seats.");
                return;
            }

            boolean booked = false;
            for (int i = 0; i < TOTAL_SEATS; i++) {
                if (seats[i] == 0) {
                    seats[i] = 1;
                    requestedSeats--;
                    if (requestedSeats == 0) {
                        booked = true;
                        break;
                    }
                }
            }

            if (booked) {
                System.out.println(userName + " (VIP: " + isVIP + "): Successfully booked " + (requestedSeats == 0 ? requestedSeats : (requestedSeats - requestedSeats)) + " seats. Remaining seats: " + countAvailableSeats());
                displaySeats();
            } else {
                System.out.println(userName + " (VIP: " + isVIP + "): Failed to book " + requestedSeats + " seats due to unavailability.");
            }
        } finally {
            lock.unlock();
        }
    }

    private int countAvailableSeats() {
        int count = 0;
        for (int seat : seats) {
            if (seat == 0) count++;
        }
        return count;
    }

    private void displaySeats() {
        System.out.print("Seat Status [0 = Available, 1 = Booked]: ");
        for (int seat : seats) {
            System.out.print(seat + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TicketBookingSystem tbs = new TicketBookingSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Starting Ticket Booking System with " + TOTAL_SEATS + " seats...");
        tbs.displaySeats(); 

        Thread vip1 = tbs.new BookingThread("VIP User 1", true, 2);
        Thread vip2 = tbs.new BookingThread("VIP User 2", true, 3);
        Thread regular1 = tbs.new BookingThread("Regular User 1", false, 2);
        Thread regular2 = tbs.new BookingThread("Regular User 2", false, 3);

        vip1.start();
        vip2.start();
        regular1.start();
        regular2.start();

        try {
            vip1.join();
            vip2.join();
            regular1.join();
            regular2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted!");
        }

        System.out.println("\nFinal Seat Status:");
        tbs.displaySeats(); // Call using the instance 'tbs'
        scanner.close();
    }
}