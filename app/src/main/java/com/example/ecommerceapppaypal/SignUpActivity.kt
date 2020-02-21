package com.example.ecommerceapppaypal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.sign_up_layout.*
import okhttp3.Request
import okhttp3.Response

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)

        sign_up_layout_btnSignUp.setOnClickListener {
            if(sign_up_layout_edtPassword.text.toString().equals(sign_up_layout_edtComfirmPassword.text.toString())){
                val signUpURL = "http://192.168.43.104/OnlineEcommerceStorePaypal/join_new_user.php?email=" + sign_up_layout_edtEmail.text.toString() + "&username=" + sign_up_layout_edtUsername.text.toString() + "&pass=" + sign_up_layout_edtPassword.text.toString()
                val requestQ = Volley.newRequestQueue(this@SignUpActivity)
                val strRequest = StringRequest(com.android.volley.Request.Method.GET, signUpURL, com.android.volley.Response.Listener {
                    response ->
                    if(response.equals("A user with this email already exists")){
                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Message")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()
                    }else{
//                        val dialogBuilder = AlertDialog.Builder(this)
//                        dialogBuilder.setTitle("Message")
//                        dialogBuilder.setMessage(response)
//                        dialogBuilder.create().show()
                        Person.email = sign_up_layout_edtEmail.text.toString()
                        Toast.makeText(this@SignUpActivity, response, Toast.LENGTH_SHORT).show()
                        val homeIntent = Intent(this@SignUpActivity, HomeScreen::class.java)
                        startActivity(homeIntent)
                    }
                }, com.android.volley.Response.ErrorListener { error ->
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()
                })
                requestQ.add(strRequest)
            }else{
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("Password Mismatch")
                dialogBuilder.create().show()
            }
        }
        sign_up_layout_btnLogin.setOnClickListener {
            finish()
        }
    }
}
