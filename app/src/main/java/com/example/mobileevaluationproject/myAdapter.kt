package com.example.mobileevaluationproject

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
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


        //BUG: When clicking an item to download, an item 7 positions next to it, changes the image as well.
        holder.imageButton.setOnClickListener {
            if (holder.adapterPosition == position) {
                holder.imageButton.setImageResource(R.drawable.ic_check_w)
                Log.d(
                    "Adapter", "Book with id: " + bookList?.get(position)?.id +
                            "changed. Position is: " + position + " Adapter position is: " + holder.adapterPosition
                )
                //Download the PDF here.
                Toast.makeText(context, "Book: " + bookList?.get(position)?.title +" has been downloaded.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return bookList?.size ?: 0
    }
}