package com.example.mindfulmoments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.net.Uri

class LearnFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_learn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        val videoList = listOf(
            Pair("10-Minute Meditation For Beginners", "U9YKY7fdwyg&amp;pp=ygUQbGVhcm4gbWVkaXRhdGlvbg%3D%3D"),
            Pair("How to Practice Mindfulness", "bLpChrgS0AY&amp;pp=ygUQbGVhcm4gbWVkaXRhdGlvbg%3D%3D"),
            Pair("How To Meditate For Beginners (Animated)", "JslvBcIVtDg&amp;pp=ygUZbGVhcm4gbWVkaXRhdGlvbiBhbmltYXRlZA%3D%3D"),
            Pair("What Is Mindfulness Meditation? | Piedmont Healthcare", "kAocOhefmSU&amp;pp=ygUkaG93IHRvIGRvIG1lZGl0YXRpb24gYW5kIG1pbmRmdWxuZXNz"),
            Pair("Meditation Video 5", "video_id_5"),
            Pair("Meditation Video 6", "video_id_6"),
            Pair("Meditation Video 7", "video_id_7"),
            Pair("Meditation Video 8", "video_id_8")
        )

        // Initialize RecyclerView and set up adapter
        videoAdapter = VideoAdapter(videoList) { videoId ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
            startActivity(intent)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = videoAdapter
        }
    }
}
