package vincegeralddelaccerna.ezwheels;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String reservationReceived = "reservationReceived";
    public static final String myReservations = "myReservations";
    public static final String tradesReceived = "tradesReceived";
    public static final String myTrades = "myTrades";
    public static final String loanreqReceived = "loanreqReceived";
    public static final String myLoanreq = "myLoanreq";
    public static final String paymentReceived = "paymentReceived";


    @Override
    public void onCreate() {
        super.onCreate();

        notificationChannels();
    }
    

    private void notificationChannels() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(reservationReceived, "Approved/Declined Reservations", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Approved/Declined Reservations");

            NotificationChannel channel1 = new NotificationChannel(myReservations, "Received Reservations", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Received Reservations");

            NotificationChannel channel2 = new NotificationChannel(tradesReceived, "Approved/Declined Trade", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Approved/Declined Trade");

            NotificationChannel channel3 = new NotificationChannel(myTrades, "Received Trade", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Received Trade");

            NotificationChannel channel4 = new NotificationChannel(loanreqReceived, "Approved/Declined Loan Requests", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Approved/Declined Loan Requests");

            NotificationChannel channel5 = new NotificationChannel(myLoanreq, "Received Loan Requests", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Received Loan Requests");

            NotificationChannel channel6 = new NotificationChannel(paymentReceived, "Received Payment", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Received Payment");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);
            manager.createNotificationChannel(channel4);
            manager.createNotificationChannel(channel5);
            manager.createNotificationChannel(channel6);
        }
    }
}
