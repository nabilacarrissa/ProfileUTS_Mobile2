package com.nabilacarissa.loginprofiluts

import android.util.Log
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nabilacarissa.loginprofiluts.databinding.ActivityRegisterBinding
import com.nabilacarissa.loginprofiluts.database.AppDatabase
import com.nabilacarissa.loginprofiluts.model.User
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi database
        db = AppDatabase.getDatabase(this)

        binding.btnRegister.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val noHp = binding.etNoHp.text.toString()
            val alamat = binding.etAlamat.text.toString()

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || noHp.isEmpty() || alamat.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val user = User(
                        nama = nama,
                        email = email,
                        password = password,
                        noHp = noHp,
                        alamat = alamat
                    )
                    db.userDao().insertUser(user)

                    // Cek isi database lewat Logcat
                    val allUsers = db.userDao().getAllUsers()
                    for (u in allUsers) {
                        Log.d("RoomDB_Check", "User: ${u.nama}, ${u.email}, ${u.noHp}, ${u.alamat}")
                    }

                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            }
        }
    }
}
