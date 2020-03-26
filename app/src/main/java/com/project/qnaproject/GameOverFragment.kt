package com.project.qnaproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GameOverFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GameOverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameOverFragment : Fragment() {
    lateinit var img: ImageView
    lateinit var tryBtn: Button
    lateinit var logBtn: Button
    lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_game_over, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img = view.findViewById(R.id.game_over_img)
        tryBtn = view.findViewById(R.id.try_again)
        logBtn = view.findViewById(R.id.log_out)

        val bounceAnimation = AnimationUtils.loadAnimation(context, R.anim.bounce_anim)
        img.startAnimation(bounceAnimation)

        tryBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_gameOverFragment_to_questionFragment2)
        }

        logBtn.setOnClickListener {
            mAuth.signOut()
            if (mAuth.currentUser == null) {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }
}
