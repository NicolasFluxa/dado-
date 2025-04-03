package com.example.miprimeraapp

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var numeroSeleccionado: Int = 0
    private var puntaje: Int = 0
    private lateinit var diceImage: ImageView
    private lateinit var tvResultado: TextView
    private lateinit var tvPuntaje: TextView
    private lateinit var tvTiempo: TextView
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        diceImage = findViewById(R.id.diceImage)
        tvResultado = findViewById(R.id.tvResultado)
        tvPuntaje = findViewById(R.id.tvPuntaje)
        tvTiempo = findViewById(R.id.tvTiempo)

        // Configurar botones numéricos
        configurarBotonesNumeros()

        // Botón para lanzar el dado
        findViewById<Button>(R.id.btnLanzar).setOnClickListener {
            if (numeroSeleccionado == 0) {
                Toast.makeText(this, "¡Elige un número primero!", Toast.LENGTH_SHORT).show()
            } else {
                lanzarDado()
            }
        }

        // Iniciar temporizador
        iniciarTemporizador()
    }

    private fun configurarBotonesNumeros() {
        val botones = listOf(
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4),
            findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6)
        )

        botones.forEachIndexed { index, boton ->
            boton.setOnClickListener {
                numeroSeleccionado = index + 1
                Toast.makeText(this, "Seleccionaste: ${numeroSeleccionado}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun lanzarDado() {
        // Generar número aleatorio (1-6)
        val numeroAleatorio = (1..6).random()

        // Actualizar imagen del dado
        val resourceId = resources.getIdentifier(
            "dice_$numeroAleatorio",
            "drawable",
            packageName
        )
        diceImage.setImageResource(resourceId)

        // Verificar si acertó
        if (numeroAleatorio == numeroSeleccionado) {
            puntaje += 10
            tvResultado.text = "¡Ganaste! 🎉"
        } else {
            tvResultado.text = "Intenta de nuevo 😢"
        }

        // Actualizar puntaje
        tvPuntaje.text = "Puntaje: $puntaje"
    }

    private fun iniciarTemporizador() {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTiempo.text = "Tiempo: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                tvTiempo.text = "¡Tiempo!"
                findViewById<Button>(R.id.btnLanzar).isEnabled = false
                tvResultado.text = "Puntaje final: $puntaje"
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}