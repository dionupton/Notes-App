package com.example.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.row.view.*

class NoteAdapter(private val context: Context?, private val notesList:RealmResults<Note>)

    :RecyclerView.Adapter<NoteAdapter.NoteVH>(){

    class NoteVH(itemView: View) : RecyclerView.ViewHolder(itemView){

        var title : TextView = itemView.findViewById<TextView>(R.id._title)
        var desc : TextView = itemView.findViewById<TextView>(R.id._description)
        var linearLayout : LinearLayout = itemView.findViewById(R.id.linearLayout)
        var expandable_layout : RelativeLayout = itemView.findViewById(R.id.expandable_layout)
        var removeButton : Button = itemView.findViewById(R.id._removeButton)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)

        return NoteVH(view)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        holder.itemView._title.text = notesList[position]!!.title
        holder.itemView._description.text = notesList[position]!!.description

        holder.removeButton.setOnClickListener{
            val note = notesList[position]
            var realm: Realm
            realm = Realm.getDefaultInstance()

            realm.beginTransaction()

            if (note != null) {
                note.deleteFromRealm()
                notifyDataSetChanged()
            }

            realm.commitTransaction()
            notifyItemChanged(position)
        }

        val isExpandable : Boolean = notesList[position]!!.expandable
        holder.expandable_layout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener{
            val note = notesList[position]

            var realm: Realm
            realm = Realm.getDefaultInstance()

            realm.beginTransaction()

            note!!.expandable = !note.expandable

            realm.copyToRealmOrUpdate(note)
            realm.commitTransaction()
            notifyItemChanged(position)
        }

    }

}