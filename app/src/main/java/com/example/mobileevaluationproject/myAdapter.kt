package com.example.uniapi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileevaluationproject.BooksItem
import com.example.mobileevaluationproject.R

class myAdapter(val context: Context, val bookList: List<BooksItem>?): RecyclerView.Adapter<myAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var bookid: TextView = itemView.findViewById(R.id.book_id)
        var bookName: TextView = itemView.findViewById(R.id.book_name)
        var bookDate: TextView = itemView.findViewById(R.id.book_date)
        var imageButton: ImageButton = itemView.findViewById(R.id.image_country)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bookList?.sortedByDescending { it.date_released }
        holder.bookName.text = bookList?.get(position)?.title
        holder.bookid.text = bookList?.get(position)?.id.toString()
        holder.bookDate.text = bookList?.get(position)?.date_released?.take(10)
        holder.imageButton.setOnClickListener{holder.imageButton.setImageResource(R.drawable.ic_check_w)
            Toast.makeText(context, "Book Downloaded ", Toast.LENGTH_SHORT).show()}
    }

    override fun getItemCount(): Int {
        return bookList?.size ?: 0
    }
}