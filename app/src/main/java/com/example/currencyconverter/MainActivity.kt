import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyconverter.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var baseCurrency: EditText
    private lateinit var targetCurrency: EditText
    private lateinit var convertButton: Button
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        baseCurrency = findViewById(R.id.baseCurrency)
        targetCurrency = findViewById(R.id.targetCurrency)
        convertButton = findViewById(R.id.convertButton)
        resultText = findViewById(R.id.resultText)

        convertButton.setOnClickListener {
            val base = baseCurrency.text.toString().uppercase()
            val target = targetCurrency.text.toString().uppercase()

            if (base.isNotEmpty() && target.isNotEmpty()) {
                getExchangeRate(base, target)
            } else {
                Toast.makeText(this, "Please enter both currencies", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getExchangeRate(base: String, target: String) {
        val apiKey = "2d36c537b530ce2804390252493d95dd95bad4a3"

        val call = RetrofitClient.apiService.getExchangeRate(apiKey, base, target)
        call.enqueue(object : Callback<CurrencyResponse> {
            override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                if (response.isSuccessful) {
                    val exchangeRate = response.body()?.rates?.get(target)
                    if (exchangeRate != null) {
                        resultText.text = "1 $base = $exchangeRate $target"
                    } else {
                        resultText.text = "Invalid target currency"
                    }
                } else {
                    resultText.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                resultText.text = "Failed to retrieve data"
            }
        })
    }
}
