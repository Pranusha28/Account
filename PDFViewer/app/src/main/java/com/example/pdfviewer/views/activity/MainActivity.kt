package com.example.pdfviewer.views.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfviewer.views.adapter.AccountAdapter
import com.example.pdfviewer.database.AccountDatabase
import com.example.pdfviewer.viewModel.AccountViewModel
import com.example.pdfviewer.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var adapter: AccountAdapter
    private lateinit var recyclerView: RecyclerView
    private var currentEditText: EditText?=null
    private val REQUEST_SPEECH=100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewAccounts)
        val btnFetchAccounts = findViewById<Button>(R.id.btnFetchAccounts)
        val btnViewPdf = findViewById<Button>(R.id.btnViewPdf)
        val btnCaptureImage = findViewById<Button>(R.id.btnCaptureImage)
        val btnSpeechToText = findViewById<Button>(R.id.btnSpeechToText)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val dao = AccountDatabase.getDatabase(this).accountDao()

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        adapter = AccountAdapter(emptyList(),accountViewModel,)
        recyclerView.adapter = adapter

        accountViewModel.accounts.observe(this) { accounts ->
            adapter.updateAccounts(accounts)
        }

        btnFetchAccounts.setOnClickListener { accountViewModel.fetchAccounts() }

        btnViewPdf.setOnClickListener {
            startActivity(Intent(this, PdfViewerActivity::class.java))
        }

        btnCaptureImage.setOnClickListener {
            startActivity(Intent(this, ImageCaptureActivity::class.java))
        }

        btnSpeechToText.setOnClickListener { startSpeechRecognition() }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode== REQUEST_SPEECH) {
            val result=data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if(!result.isNullOrEmpty()){
                currentEditText?.setText(result[0])
            }
        }
    }

    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak alternate name")

        try {
            startActivityForResult(intent,REQUEST_SPEECH)
        }catch (e:ActivityNotFoundException){
            Toast.makeText(this,"Speach to text not supported",Toast.LENGTH_SHORT).show()
        }
    }
}
