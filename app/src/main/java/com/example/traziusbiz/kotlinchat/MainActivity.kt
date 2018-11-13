package com.example.traziusbiz.kotlinchat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var radioGroup: RadioGroup
    lateinit var radioOwner: RadioButton
    lateinit var radioOpponent: RadioButton
    lateinit var messages: ArrayList<Message>
    lateinit var submitButton: Button
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findElements()
        initItems()
        initEvents()
    }

    fun findElements() {
        editText = findViewById(R.id.edit_text)
        submitButton = findViewById(R.id.submit_button)
        recyclerView = findViewById(R.id.recycler_view)
        radioGroup = findViewById(R.id.radio_group)
        radioOwner = findViewById(R.id.radio_owner)
        radioOpponent = findViewById(R.id.radio_opponent)

        radioOwner.isChecked = true
    }

    fun initItems() {
        messages = ArrayList<Message>()
        adapter = Adapter(messages)
        recyclerView.adapter = adapter

        val decoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    fun initEvents() {
        val clickListener = View.OnClickListener {
            println("click")
            submit()
        }

        submitButton.setOnClickListener(clickListener)
    }

    fun submit() {
        val selectedButton = radioGroup.checkedRadioButtonId
        var isOwner = false
        if (selectedButton == R.id.radio_owner) {
            isOwner = true
        }

        val text = editText.text.toString()
        if (TextUtils.isEmpty(text)) {
            showMessage(resources.getString(R.string.hint_message))
        } else {
            messages.add(Message(text, isOwner))
            editText.setText("")
            adapter.notifyDataSetChanged()
        }
    }

    fun showMessage(message: String) {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}
