package com.example.mobileevaluationproject

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileevaluationproject.BooksItem
import com.example.mobileevaluationproject.R
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class myAdapter(val context: Context, var bookList: List<BooksItem>?) :
    RecyclerView.Adapter<myAdapter.ViewHolder>() {

    companion object {
        var date: String? = null
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bookName: TextView = itemView.findViewById(R.id.book_name)
        var bookDate: TextView = itemView.findViewById(R.id.book_date)
        var imageButton: ImageButton = itemView.findViewById(R.id.image_book)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        bookList = bookList?.sortedByDescending {
            LocalDate.parse(
                it.date_released.take(10),
                dateTimeFormatter
            )
        }
        holder.bookName.text = bookList?.get(position)?.title


        /*
        Sort all books in a big group by year.
        We only show the date of the first book with a new year, the rest hide their date
        BUG: The date is visible on another item for no reason.
         */
        if (bookList?.get(position)?.date_released?.take(4) != date || position == 0) {
            date = bookList?.get(position)?.date_released?.take(4)
            holder.bookDate.text = bookList?.get(position)?.date_released?.take(4)
        } else {
            holder.bookDate.text = ""
        }

        /* We can do this to get the image from url but they are dummy images anyway
            //Picasso.get().load(bookList?.get(position)?.img_url).into(holder.imageButton)
         */


        //BUG: When clicking an item to download, another item is also downloaded.
        holder.imageButton.setOnClickListener {
            holder.imageButton.setImageResource(R.drawable.ic_check_w)
            //Download the PDF here.
            Toast.makeText(context, "Book Downloaded ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return bookList?.size ?: 0
    }
}