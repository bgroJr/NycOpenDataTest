import org.junit.Before
import org.junit.Test
import org.junit.Assert
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import org.hamcrest.Matchers.*
import org.hamcrest.MatcherAssert

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import okhttp3.OkHttpClient

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


@RunWith(JUnit4::class)
class OpenStreetTest {

  val baseUrl = "https://data.cityofnewyork.us"
  val client = OkHttpClient()

  val moshi = Moshi.Builder()
              .add(OpenStreet.OpenDateAdapter())
              .add(KotlinJsonAdapterFactory())
              .build()

  val openStreetsType = Types.newParameterizedType(
    List::class.java, OpenStreet.Model::class.java
  )
  val adapter: JsonAdapter<List<OpenStreet.Model>> = moshi.adapter(openStreetsType)

  lateinit var apiUrlComponents: HttpUrl.Builder

  @Before
  fun setUp() {
    apiUrlComponents = baseUrl.toHttpUrl().newBuilder()
                       .addPathSegments("resource/uiay-nctu.json")
  }

  @Test
  fun `Manhattan streets should be in Manhattan neighborhoods`() {
    val apiUrl = apiUrlComponents
                 .addQueryParameter("borough", NYC.Geography.boroughs[0])
                 .build()

    val request = Request.Builder().url(apiUrl).build()

    val response = client.newCall(request).execute()
    Assert.assertEquals(response.code, 200)

    val openStreets = adapter.fromJson(response.body!!.string())
    MatcherAssert.assertThat(openStreets!!.size, greaterThan(0))

    Assert.assertTrue(
      "Not all neighborhoods listed are in Manhattan",
      openStreets.all { it.nhoodname in NYC.Geography.manhattanNeighborhoods }
    )
  }

  @Test
  fun `Start Times should occur before End Times`() {
    val apiUrl = apiUrlComponents
                 .addQueryParameters("nhoodname", "Chelsea")
                 .build()

    val request = Request.Builder().url(apiUrl).build()

    val response = client.newCall(request).execute()
    Assert.assertEquals(response.code, 200)

    val openStreets = adapter.fromJson(response.body!!.string())
    MatcherAssert.assertThat(openStreets!!.size, greaterThan(0))

    Assert.assertTrue(
      "Not all Open Street start times occur before end times",
      openStreets.all { it.start_time.takeLast(2) == "AM" && it.end_time.takeLast(2) == "PM" }
    )
  }

  @Test
  fun `Open Streets should have valid street names`() {
    val apiUrl = apiUrlComponents
                 .addQueryParameters("borough", NYC.Geography.boroughs[1])
                 .build()

    val request = Request.Builder().url(apiUrl).build()

    val response = client.newCall(request).execute()
    Assert.assertEquals(response.code, 200)

    val openStreets = adapter.fromJson(response.body!!.string())
    MatcherAssert.assertThat(openStreets!!.size, greaterThan(0))

    Assert.assertTrue(
      "Not all Open Streets have valid street names",
      openStreets.all { it.on_street.split(" ").takeLast(1).first() in NYC.Geography.brooklynStreetTypes }
    )
  }
}

