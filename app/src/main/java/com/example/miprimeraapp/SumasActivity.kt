package com.example.miprimeraapp


import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SumasActivity : AppCompatActivity() {

    private lateinit var tvNum1: TextView
    private lateinit var tvNum2: TextView
    private lateinit var etRespuesta: EditText
    private lateinit var tvPuntaje: TextView
    private lateinit var tvVidas: TextView
    private lateinit var tvTiempo: TextView
    private lateinit var tvNivel: TextView

    private var puntaje = 0
    private var vidas = 3
    private var nivel = 1
    private var num1 = 0
    private var num2 = 0
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sumas)

        // Vincular vistas
        tvNum1 = findViewById(R.id.tvNum1)
        tvNum2 = findViewById(R.id.tvNum2)
        etRespuesta = findViewById(R.id.etRespuesta)
        tvPuntaje = findViewById(R.id.tvPuntaje)
        tvVidas = findViewById(R.id.tvVidas)
        tvTiempo = findViewById(R.id.tvTiempo)
        tvNivel = findViewById(R.id.tvNivel)

        // Configurar botón
        findViewById<Button>(R.id.btnVerificar).setOnClickListener {
            verificarRespuesta()
        }

        generarNuevaSuma()
        iniciarTemporizador()
    }

    private fun generarNuevaSuma() {
        val rango = nivel * 10 // Aumenta el rango según el nivel
        num1 = (1..rango).random()
        num2 = (1..rango).random()

        tvNum1.text = num1.toString()
        tvNum2.text = num2.toString()
        etRespuesta.text.clear()
    }

    private fun verificarRespuesta() {
        val respuesta = etRespuesta.text.toString().toIntOrNull()

        if (respuesta == num1 + num2) {
            puntaje += 10
            tvPuntaje.text = "Puntos: $puntaje"
            if (puntaje % 30 == 0) { // Subir nivel cada 3 aciertos
                nivel++
                tvNivel.text = "Nivel $nivel"
            }
            generarNuevaSuma()
        } else {
            vidas--
            tvVidas.text = "Vidas: $vidas"
            if (vidas <= 0) {
                Toast.makeText(this, "¡Game Over!", Toast.LENGTH_SHORT).show()
                findViewById<Button>(R.id.btnVerificar).isEnabled = false
            } else {
                generarNuevaSuma()
            }
        }
    }

    private fun iniciarTemporizador() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTiempo.text = "Tiempo: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                vidas--
                tvVidas.text = "Vidas: $vidas"
                if (vidas > 0) {
                    generarNuevaSuma()
                    iniciarTemporizador()
                } else {
                    Toast.makeText(this@SumasActivity, "¡Se acabó el tiempo!", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}