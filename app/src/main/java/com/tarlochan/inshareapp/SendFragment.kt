package com.tarlochan.inshareapp

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var runnable:Runnable

/**
 * A simple [Fragment] subclass.
 * Use the [SendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SendFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_send, container, false)

        val im1: ImageView =  root.findViewById<ImageView>(R.id.im1)
        val im2: ImageView =  root.findViewById<ImageView>(R.id.im2)
        val im3: ImageView =  root.findViewById<ImageView>(R.id.im3)
        val im4: ImageView =  root.findViewById<ImageView>(R.id.im4)
        val im5: ImageView =  root.findViewById<ImageView>(R.id.im5)
        val im6: ImageView =  root.findViewById<ImageView>(R.id.im6)

        val handler: Handler = Handler()
        runnable = Runnable {
            run() {
                im1.animate().scaleX(5f).scaleY(5f).alpha(0f).setDuration(3900).withEndAction(
                    Runnable {
                        run() {
                            im1.scaleX = 1f
                            im1.scaleY = 1f
                            im1.alpha = 1f
                        }
                    }
                )

                im2.animate().scaleX(5f).scaleY(5f).alpha(0f).setDuration(3600).withEndAction(
                    Runnable {
                        run() {
                            im2.scaleX = 1f
                            im2.scaleY = 1f
                            im2.alpha = 1f
                            im2.elevation = 4F
                        }
                    }
                )

                im3.animate().scaleX(5f).scaleY(5f).alpha(0f).setDuration(3300).withEndAction(
                    Runnable {
                        run() {
                            im3.scaleX = 1f
                            im3.scaleY = 1f
                            im3.alpha = 1f
                            im3.elevation = 7F
                        }
                    }
                )
                im4.animate().scaleX(5f).scaleY(5f).alpha(0f).setDuration(3000).withEndAction(
                    Runnable {
                        run() {
                            im4.scaleX = 1f
                            im4.scaleY = 1f
                            im4.alpha = 1f
                            im4.elevation = 10F
                        }
                    }
                )
                im5.animate().scaleX(5f).scaleY(5f).alpha(0f).setDuration(2700).withEndAction(
                    Runnable {
                        run() {
                            im5.scaleX = 1f
                            im5.scaleY = 1f
                            im5.alpha = 1f
                            im5.elevation = 12F
                        }
                    }
                )
                im6.animate().scaleX(5f).scaleY(5f).alpha(0f).setDuration(2400).withEndAction(
                    Runnable {
                        run() {
                            im6.scaleX = 1f
                            im6.scaleY = 1f
                            im6.alpha = 1f
                            im6.elevation = 15F
                        }
                    }
                )

                handler.postDelayed(runnable,3900)
            }
        }

        runnable.run()

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SendFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SendFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}