package com.example.traziusbiz.kotlinchat

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

class Adapter(private val items: MutableList<Message>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if (item.isOwner) {
            VIEW_TYPE_MESSAGE_OWNER
        } else {
            VIEW_TYPE_MESSAGE_OPPONENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var messageView = R.layout.owner_message
        if (viewType == VIEW_TYPE_MESSAGE_OPPONENT) {
            messageView = R.layout.opponent_message
        }
        val view = LayoutInflater.from(parent.context).inflate(messageView, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.messageText.setText(items[position].message)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        val messageText: TextView
        val messageEdit: TextView

        private val onEditMenu = MenuItem.OnMenuItemClickListener { menuItem ->
            val position = adapterPosition

            when (menuItem.itemId) {
                1 -> editItem()
                2 -> deleteItem(position)
                3 -> {
                }
            }
            true
        }

        init {
            messageText = itemView.findViewById(R.id.message_text)
            messageEdit = itemView.findViewById(R.id.message_edit)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            contextMenu: ContextMenu,
            view: View,
            contextMenuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val Edit = contextMenu.add(Menu.NONE, 1, 1, "Edit")
            val Delete = contextMenu.add(Menu.NONE, 2, 2, "Delete")
            val Close = contextMenu.add(Menu.NONE, 3, 3, "Close")
            Edit.setOnMenuItemClickListener(onEditMenu)
            Delete.setOnMenuItemClickListener(onEditMenu)
            Close.setOnMenuItemClickListener(onEditMenu)
        }

        private fun deleteItem(position: Int) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }

        private fun editItem() {
            messageText.visibility = View.GONE
            messageEdit.visibility = View.VISIBLE
            messageEdit.text = messageText.text
            messageEdit.setOnKeyListener { view, i, keyEvent ->
                println(i)
                if (i == KeyEvent.KEYCODE_ENTER) {
                    val updatedText = messageEdit.text.toString()
                    messageEdit.visibility = View.GONE
                    messageText.visibility = View.VISIBLE
                    messageText.text = updatedText
                    hideSoftKeyboard(view.context as Activity)
                }
                true
            }
        }

        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        }

    }

    companion object {
        private val VIEW_TYPE_MESSAGE_OWNER = 1
        private val VIEW_TYPE_MESSAGE_OPPONENT = 2
    }

}
