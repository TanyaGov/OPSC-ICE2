import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest")
    fun getExchangeRate(
        @Query("access_key") apiKey: String,
        @Query("base") baseCurrency: String,
        @Query("symbols") targetCurrency: String
    ): Call<CurrencyResponse>
}
