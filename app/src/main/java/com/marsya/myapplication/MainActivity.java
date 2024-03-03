package com.marsya.myapplication;

// Import statements
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private EditText editTextCustomerName, editTextQuantityPulsa, editTextQuantityToken, editTextQuantityEmoney;
    private RadioGroup radioGroupMembership;
    private Button buttonProcess;
    private TextView textViewReceipt, textViewDateTime;

    //biaya item
    private final int PRICE_PULSA = 50000;
    private final int PRICE_TOKEN = 100000;
    private final int PRICE_EMONEY = 50000;
    private final double DISCOUNT_RATE = 0.05;

    // Metode onCreate untuk menginisialisasi aktivitas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextQuantityPulsa = findViewById(R.id.editTextQuantityPulsa);
        editTextQuantityToken = findViewById(R.id.editTextQuantityToken);
        editTextQuantityEmoney = findViewById(R.id.editTextQuantityEmoney);
        radioGroupMembership = findViewById(R.id.radioGroupMembership);
        buttonProcess = findViewById(R.id.buttonProcess);
        textViewReceipt = findViewById(R.id.textViewReceipt);
        textViewDateTime = findViewById(R.id.textViewDateTime);

        // Tetapkan tanggal dan waktu saat ini
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        textViewDateTime.setText(dateTime);

        // Atur OnClickListener untuk tombol proses
        buttonProcess.setOnClickListener(new View.OnClickListener() {
            // Panggil metode untuk menampilkan struk
            @Override
            public void onClick(View v) {
                showReceipt();
            }
        });
    }

    //Show RECEIPT !!!
    private void showReceipt() {
        // Mendapatkan data dari input pengguna
        String customerName = editTextCustomerName.getText().toString();
        int quantityPulsa = Integer.parseInt(editTextQuantityPulsa.getText().toString());
        int quantityToken = Integer.parseInt(editTextQuantityToken.getText().toString());
        int quantityEmoney = Integer.parseInt(editTextQuantityEmoney.getText().toString());
        boolean isMember = ((RadioButton)findViewById(radioGroupMembership.getCheckedRadioButtonId())).getText().toString().equals("Membership (5% Diskon)");

        // Hitung total harga setiap barang
        int totalPulsaPrice = PRICE_PULSA * quantityPulsa;
        int totalTokenPrice = PRICE_TOKEN * quantityToken;
        int totalEmoneyPrice = PRICE_EMONEY * quantityEmoney;

        // Hitung biaya admin
        int adminFeePulsa = (quantityPulsa > 0) ? 2000 : 0;
        int adminFeeToken = (quantityToken > 0) ? 2500 : 0;
        int adminFeeEmoney = (quantityEmoney > 0) ? 3000 : 0;

        // Hitung total harga setiap barang dengan biaya admin
        int totalPulsaPriceWithAdmin = totalPulsaPrice + adminFeePulsa;
        int totalTokenPriceWithAdmin = totalTokenPrice + adminFeeToken;
        int totalEmoneyPriceWithAdmin = totalEmoneyPrice + adminFeeEmoney;

        // Menyimpan detail harga barang dalam string
        String itemDetails = "";
        if (quantityPulsa > 0) {
            itemDetails += "Pulsa: " + quantityPulsa + " pcs x Rp. " + PRICE_PULSA + " = Rp. " + totalPulsaPriceWithAdmin + "\n";
        }
        if (quantityToken > 0) {
            itemDetails += "Token: " + quantityToken + " pcs x Rp. " + PRICE_TOKEN + " = Rp. " + totalTokenPriceWithAdmin + "\n";
        }
        if (quantityEmoney > 0) {
            itemDetails += "E-Money: " + quantityEmoney + " pcs x Rp. " + PRICE_EMONEY + " = Rp. " + totalEmoneyPriceWithAdmin + "\n";
        }

        // Hitung subtotal
        int subtotal = totalPulsaPriceWithAdmin + totalTokenPriceWithAdmin + totalEmoneyPriceWithAdmin;

        // Terapkan diskon jika pelanggan adalah member
        double discount = 0;
        if (isMember) {
            discount = subtotal * DISCOUNT_RATE;
        }

        // Hitung total pembayaran
        int totalPayment = (int)(subtotal - discount);

        // Menampilkan struk
        String receipt = "No Struk: " + generateReceiptNumber() + "\n"  + "-------------------------------------------------------------\n"+ "Nama Pelanggan: " + customerName + "\n"
                + "-------------------------------------------------------------\n"
                + "Detail Pembelian:\n"
                + itemDetails
                + "-------------------------------------------------------------\n"
                + "[ Subtotal: Rp. " + subtotal + " ]\n"+
                "-------------------------------------------------------------\n"
                + "Info Biaya Admin:\n"
                + "Pulsa: Rp. " + adminFeePulsa + "\n"
                + "Token: Rp. " + adminFeeToken + "\n"
                + "E-Money: Rp. " + adminFeeEmoney + "\n"
                + "Diskon: Rp. " + discount + "\n";


        textViewReceipt.setText(receipt);
    }

    private String generateReceiptNumber() {
        return UUID.randomUUID().toString();
    }
}
