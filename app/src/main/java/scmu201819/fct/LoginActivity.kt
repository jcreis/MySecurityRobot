package scmu201819.fct

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Login Button
        buttonLogin.setOnClickListener{
           login()
        }

        // Don't have an account?
        dont_have_an_account_textview.setOnClickListener{
            // go back to Register
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }

    private fun login() {
        val email = emailLogin.text.toString()
        val password = passwordLogin.text.toString()

        Log.d("LoginActivity", "Attempt to login with email $email and password $password")

        //followLogin()


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Successfully logged in")
                if(email.equals("joao@mail.com")){
                    println("Email is: $email")
                    followLogin(true)
                }
                else {
                    followLogin(false)
                }
            }
            .addOnFailureListener{
                Log.d("Main", "Failed to login: ${it.message}")
            }
    }

    private fun followLogin(admin:Boolean) {
        val intent = Intent(this, ImageShowActivity::class.java)
        val b = Bundle()
        println("Is admin? $admin")
        b.putBoolean("admin", admin) //Your id
        intent.putExtras(b)
        startActivity(intent)
    }

}