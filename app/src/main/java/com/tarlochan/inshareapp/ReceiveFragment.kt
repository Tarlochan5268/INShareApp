package com.tarlochan.inshareapp

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var runnable:Runnable

/**
 * A simple [Fragment] subclass.
 * Use the [ReceiveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReceiveFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        val root =  inflater.inflate(R.layout.fragment_receive, container, false)

        val im1: ImageView =  root.findViewById<ImageView>(R.id.im1)
        val im2: ImageView =  root.findViewById<ImageView>(R.id.im2)
        val im3: ImageView =  root.findViewById<ImageView>(R.id.im3)
        val im4: ImageView =  root.findViewById<ImageView>(R.id.im4)
        val im5: ImageView =  root.findViewById<ImageView>(R.id.im5)
        val im6: ImageView =  root.findViewById<ImageView>(R.id.im6)

        val handler: Handler = Handler()
        runnable = Runnable {
            run() {

                imgAnimate(im1,3900,0F)
                imgAnimate(im2,3600,4F)
                imgAnimate(im3,3300,7F)
                imgAnimate(im4,3000,10F)
                imgAnimate(im5,2700,12F)
                imgAnimate(im6,2400,15F)

                handler.postDelayed(runnable,3900)
            }
        }

        runnable.run()

        return root
    }

    private fun imgAnimate(img: ImageView, duration: Long, elevation: Float)
    {
        img.animate().scaleX(5f).scaleY(5f).alpha(0f).setDuration(duration).withEndAction(
            Runnable {
                run() {
                    img.scaleX = 1f
                    img.scaleY = 1f
                    img.alpha = 1f
                    img.elevation = elevation
                }
            }
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReceiveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReceiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}