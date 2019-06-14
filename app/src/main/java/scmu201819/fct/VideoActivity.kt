package scmu201819.fct

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {

    private var playbackPosition = 0
    private val realTimeStreamingURL = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov"
    // https://www.wowza.com/_h264/BigBuckBunny_115k.mov
    private lateinit var mediaController: MediaController

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        mediaController = MediaController(this)

        videoView.setOnPreparedListener{
            mediaController.setAnchorView(videoContainer)
            videoView.setMediaController(mediaController)
            videoView.seekTo(playbackPosition)
            videoView.start()
        }

        videoView.setOnInfoListener { player, what, extras ->
            if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                progressBar.visibility = View.INVISIBLE
            true
        }

        /*Log.d("Video", "Reached video layout!!")

        configVideoView()

        invokeAmazonServer()*/

    }

    override fun onStart() {
        super.onStart()

        val uri = Uri.parse(realTimeStreamingURL)
        videoView.setVideoURI(uri)
        progressBar.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()

        videoView.pause()
        playbackPosition = videoView.currentPosition
    }

    override fun onStop() {
        videoView.stopPlayback()

        super.onStop()

    }

    /*fun invokeAmazonServer(){
        InvokeServer {
            println(it);
        }.execute("GET", "http://ec2-3-8-6-174.eu-west-2.compute.amazonaws.com/robotSecurityCamera/member.php")
    }

    private var mediaController: MediaController? = null


    private fun configVideoView() {

        videoView.setVideoPath(
            "http://www.ebookfrenzy.com/android_book/movie.mp4")

        mediaController = MediaController(this)
        mediaController?.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
    }*/
}