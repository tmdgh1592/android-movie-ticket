package woowacourse.movie.domain

import java.io.Serializable
import java.time.LocalTime

class MovieTime private constructor(val hour: Int, val min: Int = DEFAULT_MIN) :
    Comparable<MovieTime>, Serializable {
    fun isDiscountTime(): Boolean =
        hour < AM_DISCOUNT_CLOSE_TIME || hour >= PM_DISCOUNT_OPEN_TIME

    override fun compareTo(other: MovieTime): Int =
        (hour * HOUR_TO_MIN + min) - (other.hour * HOUR_TO_MIN + other.min)

    companion object {
        private const val AM_DISCOUNT_CLOSE_TIME = 11
        private const val PM_DISCOUNT_OPEN_TIME = 20

        private const val WEEKEND_MIN_TIME = 9
        private const val WEEKEND_MAX_TIME = 24
        private const val WEEKEND_MOVIE_TIME_INTERVAL = 2

        private const val WEEKDAY_MIN_TIME = 10
        private const val WEEKDAY_MAX_TIME = 24
        private const val WEEKDAY_MOVIE_TIME_INTERVAL = 2

        private const val DEFAULT_MIN = 0
        private const val HOUR_TO_MIN = 60

        private val weekendTimes: List<MovieTime> =
            (WEEKEND_MIN_TIME until WEEKEND_MAX_TIME step WEEKEND_MOVIE_TIME_INTERVAL).map {
                MovieTime(it)
            }
        private val weekdayTimes: List<MovieTime> =
            (WEEKDAY_MIN_TIME until WEEKDAY_MAX_TIME step WEEKDAY_MOVIE_TIME_INTERVAL).map {
                MovieTime(it)
            }

        @JvmOverloads
        fun of(
            isWeekday: Boolean,
            isToday: Boolean = true,
            curHour: Int = LocalTime.now().hour,
            curMin: Int = LocalTime.now().minute,
        ): List<MovieTime> {
            val hour = if (isToday) curHour else 0
            val min = if (isToday) curMin else 0
            val times = if (isWeekday) weekdayTimes else weekendTimes
            return times.filter { MovieTime(it.hour, it.min) > MovieTime(hour, min) }
        }

        @JvmOverloads
        fun of(hour: Int, min: Int = DEFAULT_MIN): MovieTime = MovieTime(hour, min)
    }
}
