package com.project.qnaproject


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 */
class GameWonFragment : Fragment() {
    lateinit var tryBtn: Button
    lateinit var logoutBtn: Button
    lateinit var score: TextView
    lateinit var mAuth: FirebaseAuth
    lateinit var img: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_game_won, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tryBtn = view.findViewById(R.id.try_again)
        logoutBtn = view.findViewById(R.id.log_out)
        score = view.findViewById(R.id.score_txt)
        img = view.findViewById(R.id.game_over_img)
        val bounceAnimation = AnimationUtils.loadAnimation(context, R.anim.bounce_anim)
        img.startAnimation(bounceAnimation)


        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        val points_txt = args.points.toString()
        val total_txt = args.questionSize.toString()
        val sb = StringBuilder()
        sb.append(points_txt)
        sb.append("/")
        sb.append(total_txt)
        score.text = sb.toString()

        tryBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_gameWonFragment_to_questionFragment2)
        }

        logoutBtn.setOnClickListener {
            mAuth.signOut()
            if (mAuth.currentUser == null) {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
