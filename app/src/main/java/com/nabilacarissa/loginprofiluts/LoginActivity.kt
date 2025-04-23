package com.nabilacarissa.loginprofiluts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nabilacarissa.loginprofiluts.databinding.ActivityLoginBinding
import com.nabilacarissa.loginprofiluts.database.AppDatabase
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password wajib diisi!", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val user = db.userDao().login(email, password)
                    if (user != null) {
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                            // Kirim data lengkap ke HomeActivity
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.putExtra("ID", user.id)
                            intent.putExtra("NAMA", user.nama)
                            intent.putExtra("EMAIL", user.email)
                            intent.putExtra("NOHP", user.noHp)
                            intent.putExtra("ALAMAT", user.alamat)
                            startActivity(intent)

                            finish() // biar pas balik ga bisa ke login lagi
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.btnGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
