package com.nabilacarissa.loginprofiluts

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nabilacarissa.loginprofiluts.database.AppDatabase
import com.nabilacarissa.loginprofiluts.model.User
import com.nabilacarissa.loginprofiluts.databinding.ActivityUpdateProfileBinding
import kotlinx.coroutines.launch

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var db: AppDatabase
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol back di action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db = AppDatabase.getDatabase(this)

        // Ambil data dari intent
        userId = intent.getIntExtra("ID", 0)
        val nama = intent.getStringExtra("NAMA")
        val email = intent.getStringExtra("EMAIL")
        val noHp = intent.getStringExtra("NOHP")
        val alamat = intent.getStringExtra("ALAMAT")

        // Tampilkan data lama ke EditText
        binding.etNama.setText(nama)
        binding.etEmail.setText(email)
        binding.etNoHp.setText(noHp)
        binding.etAlamat.setText(alamat)

        // Klik tombol update
        binding.btnUpdate.setOnClickListener {
            val updatedNama = binding.etNama.text.toString()
            val updatedEmail = binding.etEmail.text.toString()
            val updatedNoHp = binding.etNoHp.text.toString()
            val updatedAlamat = binding.etAlamat.text.toString()
            val updatedPassword = binding.etPassword.text.toString()

            if (updatedNama.isEmpty() || updatedEmail.isEmpty() || updatedNoHp.isEmpty() || updatedAlamat.isEmpty() || updatedPassword.isEmpty()) {
                Toast.makeText(this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val updatedUser = User(userId, updatedNama, updatedEmail, updatedNoHp, updatedAlamat, updatedPassword)
                    db.userDao().update(updatedUser)
                    runOnUiThread {
                        Toast.makeText(this@UpdateProfileActivity, "Update berhasil!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    // Handle tombol back di action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
