package com.example.ecommerceapppaypal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*

class CartProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartProsuctsURL = "http://192.168.43.104/OnlineEcommerceStorePaypal/fetch_temporaray_order.php?email=${Person.email}"
        var cartProductsList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonAR = JsonArrayRequest(Request.Method.GET, cartProsuctsURL, null, Response.Listener {
            response ->
            for (jsonIndex in 0.until(response.length())){
                cartProductsList.add("${response.getJSONObject(jsonIndex).getInt("id")}" +
                        " \n ${response.getJSONObject(jsonIndex).getString("name")} " +
                        "\n ${response.getJSONObject(jsonIndex).getInt("price")} " +
                        "\n ${response.getJSONObject(jsonIndex).getString("email")} " +
                        "\n ${response.getJSONObject(jsonIndex).getInt("amount")}")

            }
            var cartProductsAdapter = ArrayAdapter(this@CartProductsActivity, android.R.layout.simple_list_item_1, cartProductsList)
            cartProductsListView.adapter = cartProductsAdapter
        }, Response.ErrorListener {
            error ->

        })
        requestQ.add(jsonAR)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.continueShoppingItem){
            var intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }else if (item?.itemId == R.id.declineOrderItem){
            var deleteURL = "http://192.168.43.104/OnlineEcommerceStorePaypal/decline_order.php?email=${Person.email}"
            var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            var stringRequest = StringRequest(Request.Method.GET, deleteURL, Response.Listener {
                response ->
                var intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)
            }, Response.ErrorListener {
                error ->

            })
            requestQ.add(stringRequest)
        }
        return super.onOptionsItemSelected(item)
    }
}
