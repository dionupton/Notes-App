package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.RealmResults

class MainActivity : AppCompatActivity() {



    val notesList = ArrayList<Note>()
    private lateinit var realm:Realm
    private lateinit var notesRV:RecyclerView
    private lateinit var addNotes: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()
        notesRV = findViewById(R.id.recyclerView)
        addNotes = findViewById(R.id.addNotes)


        setRecyclerView()

        //onclick add notes btn

        addNotes.setOnClickListener{
            startActivity(Intent(this,AddNotesActivity::class.java))
            finish()
        }

    }

    private fun setRecyclerView() {
        val results:RealmResults<Note> = realm.where<Note>(Note::class.java).findAll()

        notesRV.adapter = NoteAdapter(this, results)

        notesRV.adapter!!.notifyDataSetChanged()
        notesRV = findViewById(R.id.recyclerView)
        notesRV.setHasFixedSize(true)
    }


}