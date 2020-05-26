import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.squareup.moshi.ToJson
import com.squareup.moshi.FromJson


data class Model(
  val borough: String,
  val location_p: String,
  val nhoodname: String,
  val on_street: String,
  val from_stree: String,
  val to_street: String,
  val type: String,
  val start_time: String,
  val end_time: String,
  val day_of_wee: String,
  val open_date: OpenDate,
  val segmentid: Int,
  val length_in_: Double,
  val shape_leng: Double,
  @Transient val the_geom: Map<String, String> = mapOf()
)

data class OpenDate(val dateTime: LocalDateTime)

class OpenDateAdapter {
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

  @FromJson
  fun fromJson(unparsed: String): OpenDate {
    return OpenDate(LocalDateTime.parse(unparsed, formatter))
  }

  @ToJson
  fun toJson(parsed: OpenDate): String {
    return parsed.dateTime.format(formatter)
  }
}

