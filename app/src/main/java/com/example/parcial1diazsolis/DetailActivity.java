package com.example.parcial1diazsolis;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTituloRutina, tvEjerciciosRutina, tvTemporizador;
    private ImageView imgRutinaWeb;
    private Button btnIniciarDescanso;
    private CountDownTimer temporizador;
    private boolean descansando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvTituloRutina), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Mapeo de vistas
        tvTituloRutina = findViewById(R.id.tvTituloRutina);
        tvEjerciciosRutina = findViewById(R.id.tvEjerciciosRutina);
        tvTemporizador = findViewById(R.id.tvTemporizador);
        imgRutinaWeb = findViewById(R.id.imgRutinaWeb);
        btnIniciarDescanso = findViewById(R.id.btnIniciarDescanso);

        // 1. Recibir los datos (Extras) enviados desde el MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titulo = extras.getString("TITULO");
            String ejercicios = extras.getString("EJERCICIOS");
            String urlImagen = extras.getString("URL_IMAGEN");

            // Setear los textos
            tvTituloRutina.setText(titulo);
            tvEjerciciosRutina.setText(ejercicios);

            // 2. Cargar imagen desde URL con Picasso
            Picasso.get()
                    .load(urlImagen)
                    .placeholder(R.drawable.ic_launcher_background) // Imagen de carga
                    .error(R.drawable.ic_launcher_foreground) // Imagen si falla internet
                    .into(imgRutinaWeb);
        }

        // 3. Lógica del Temporizador (1 minuto y medio = 90,000 milisegundos)
        btnIniciarDescanso.setOnClickListener(v -> {
            if (!descansando) {
                iniciarTemporizador(90000);
            }
        });
    }

    private void iniciarTemporizador(long milisegundos) {
        descansando = true;
        btnIniciarDescanso.setEnabled(false);
        btnIniciarDescanso.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray, getTheme()));

        temporizador = new CountDownTimer(milisegundos, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long segundosRestantes = millisUntilFinished / 1000;
                long minutos = segundosRestantes / 60;
                long segundos = segundosRestantes % 60;

                // Formato 00:00
                String tiempoFormateado = String.format("%02d:%02d", minutos, segundos);
                tvTemporizador.setText(tiempoFormateado);
            }

            @Override
            public void onFinish() {
                tvTemporizador.setText("00:00");
                descansando = false;
                btnIniciarDescanso.setEnabled(true);
                btnIniciarDescanso.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_green_dark, getTheme()));
                btnIniciarDescanso.setText("Siguiente Serie\nDescansar 1:30");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (temporizador != null) {
            temporizador.cancel(); // Evita que el temporizador siga corriendo si el usuario vuelve atrás
        }
    }
}