package com.example.notesapp

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Note : RealmObject() {

    @PrimaryKey
    var id:Int?=null
    var title:String? = null
    var description:String? = null
    var expandable:Boolean = false

}