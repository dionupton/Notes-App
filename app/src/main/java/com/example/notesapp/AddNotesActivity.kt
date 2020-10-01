package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm

class AddNotesActivity : AppCompatActivity() {

    private lateinit var  titleED:EditText
    private lateinit var descriptionED:EditText
    private lateinit var saveNotesBtn:Button
    private lateinit var realm:Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        //init views
        realm = Realm.getDefaultInstance()
        titleED = findViewById(R.id.title_EditText)
        descriptionED = findViewById(R.id.description_EditText)
        saveNotesBtn = findViewById(R.id.saveNotesButton)

        //onclick listener

        saveNotesBtn.setOnClickListener{
            addNotes()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun addNotes() {

        try {

            realm.beginTransaction()

            val currentIdNumber:Number? = realm.where(Note::class.java).max("id")
            val nextID:Int

            nextID = if(currentIdNumber == null){
                1
            }else{
                currentIdNumber.toInt() + 1
            }

            val note = Note()

            note.title = titleED.text.toString();
            note.description = descriptionED.text.toString()
            note.id = nextID

            //copy this transaction and commit
            realm.copyToRealmOrUpdate(note)
            realm.commitTransaction()

            Toast.makeText(this, "Note successfully added", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }catch(e:Exception){
            Toast.makeText(this, "Note failed to add to realm " + e, Toast.LENGTH_LONG).show()

        }
    }
}