package scmu201819.fct

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Register Button
        buttonRegist.setOnClickListener{
            register()

        }

        // Already have an account?
        alreadyhaveaccount_textview.setOnClickListener{
            Log.d("MainActivity", "Try to show login activity")

            //Launch login activity
            finish()

        }
    }

    private fun register() {
        val username = usernameRegist.text.toString()
        val email = emailRegist.text.toString()
        val password = passwordRegist.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity", "Username is $username")
        Log.d("MainActivity", "Email is: "+email)
        Log.d("MainActivity", "Password is $password")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Toast.makeText(this, "User with email: ${it.result?.user?.email} created", Toast.LENGTH_SHORT).show()
                followRegister()
                Log.d("Main", "Successfully created a user with id: ${it.result?.user?.uid}")

            }
            .addOnFailureListener{
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()

            }
    }
    private fun followRegister() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
