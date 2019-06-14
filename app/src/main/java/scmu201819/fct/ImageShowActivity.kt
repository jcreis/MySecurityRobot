package scmu201819.fct

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image.*
import utils.InvokeServer
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.schedule
import android.widget.Toast
import android.os.AsyncTask



class ImageShowActivity : AppCompatActivity() {

    internal lateinit var img_1: ImageView
    var url_image1 = ""
    var lastImagePos : Int = -1
    val firstImagePos : Int?= 1
    var currentPos = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val b = intent.extras
        println("Img Activity received isAdmin = ${b.getBoolean("admin")}")
        if(b.getBoolean("admin")) {
            img_1 = findViewById<View>(R.id.image_1) as ImageView

            startNotifySwitch()
            configGif()

            Thread {
                while (true) {
                    invokeAmazonServer()
                    sleep(10000)
                }
            }.start()


            Log.d("IMAGE ID", "Image id received is $lastImagePos")


//after(2000, configImageView(lastImagePos))


            button_nextImage.setOnClickListener {
                println("posNext: " + currentPos)
                if (currentPos < lastImagePos) {
                    currentPos++
                    configImageView(currentPos)
                }
            }

            button_prevImage.setOnClickListener {
                println("posPrev: " + currentPos)
                if (currentPos > 1) {
                    currentPos--
                    configImageView(currentPos)
                }
            }

            button_left.setOnClickListener{
                // move motor to left
                InvokeServer{

                }.execute("POST", "http://ec2-3-8-6-174.eu-west-2.compute.amazonaws.com/robotSecurityCamera/")
            }
            button_right.setOnClickListener{
                // move motor to left
                InvokeServer{

                }.execute("POST", "http://ec2-3-8-6-174.eu-west-2.compute.amazonaws.com/robotSecurityCamera/")
            }
            photo_button.setOnClickListener{
                // REST request to arduino
                InvokeServer{

                }.execute("GET", "http://ec2-3-8-6-174.eu-west-2.compute.amazonaws.com/robotSecurityCamera/")

            }

            notifications_switch.setOnCheckedChangeListener { buttonView, isChecked ->
                var numNotifications = 0
                if (isChecked) {
                    // The switch is enabled/checked
                    numberNotifications.setText("Number of occurrences: $numNotifications")
                } else {
                    // The switch is disabled
                    numberNotifications.setText("Turn on the notifications")
                }
            }
        }
        else {
            button_nextImage.visibility = View.INVISIBLE
            button_prevImage.visibility = View.INVISIBLE

            img_1 = findViewById<View>(R.id.image_1) as ImageView

            startNotifySwitch()
            configGif()

            Thread {
                while (true) {
                    invokeAmazonServer()
                    sleep(10000)
                }
            }.start()


            Log.d("IMAGE ID", "Image id received is $lastImagePos")

            notifications_switch.setOnCheckedChangeListener { buttonView, isChecked ->
                var numNotifications = 0
                if (isChecked) {
                    // The switch is enabled/checked
                    numberNotifications.setText("Number of occurrences: $numNotifications")
                } else {
                    // The switch is disabled
                    numberNotifications.setText("Turn on the notifications")
                }
            }
        }


    }


    fun invokeAmazonServer(){
        InvokeServer {
            lastImagePos=it?.toInt() ?: 1
            if(currentPos < lastImagePos -1){
                if(currentPos == 0){
                    currentPos = lastImagePos
                    configImageView(currentPos)
                }
            }else{
                currentPos = lastImagePos
                configImageView(currentPos)
            }


            Log.d("it return", "-> $it")
        }.execute("GET", "http://ec2-3-8-6-174.eu-west-2.compute.amazonaws.com/robotSecurityCamera/lastImage_id.php")

    }

    private fun startNotifySwitch(){
        numberNotifications.setText("Turn on the notifications")
    }

    private fun configImageView(counter: Int?) {

        println("Requested "+counter)
        url_image1 =
            "http://ec2-3-8-6-174.eu-west-2.compute.amazonaws.com/robotSecurityCamera/images/image_$counter.jpg"
        Glide.with(applicationContext)
            .load(url_image1)
            .into(img_1)

    }

    private fun configGif(){
        url_image1 =
            "http://ec2-3-8-6-174.eu-west-2.compute.amazonaws.com/robotSecurityCamera/images/placeholder.gif"
        Glide.with(applicationContext)
            .load(url_image1)
            .into(img_1)


    }
}






/*timer("changeImage", false, 0, 2000){
    // Chega ao fim das imagens, volta ao inicio
    if(counter>2){
        counter = 1
    }



    counter++
}*/

/*img_2 = findViewById<View>(R.id.image_2) as ImageView
val url_image2 = "https://wallpapershome.com/images/pages/ico_h/21504.jpg"
}
*/