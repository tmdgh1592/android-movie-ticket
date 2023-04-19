package woowacourse.movie.presentation.activities.movielist.adapter.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.model.movieitem.Movie

class MovieViewHolder(
    view: View,
    private val onBookBtnClick: (Movie) -> Unit = {},
) : RecyclerView.ViewHolder(view) {
    private val posterImageView: ImageView = view.findViewById(R.id.iv_poster)
    private val titleTextView: TextView = view.findViewById(R.id.tv_title)
    private val dateTextView: TextView = view.findViewById(R.id.tv_date)
    private val runningTimeTextView: TextView = view.findViewById(R.id.tv_running_time)
    private val bookButton: Button = view.findViewById(R.id.btn_book)

    fun bind(item: Movie) {
        val context = posterImageView.context

        with(item) {
            posterImageView.setImageResource(thumbnail)
            titleTextView.text = title
            dateTextView.text = context.getString(
                R.string.movie_release_date,
                startDate.formattedDate,
                endDate.formattedDate
            )
            runningTimeTextView.text =
                context.getString(R.string.movie_running_time, runningTime)
            bookButton.setOnClickListener { onBookBtnClick(item) }
        }
    }
}
