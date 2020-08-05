package com.tarlochan.inshareapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.kishorenarang.api.NewsAPIService
import kotlinx.android.synthetic.main.fragment_news.*
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment(), View.OnClickListener, Callback<ResponseBody> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private  val TAG = "NewsFragment"

    var chipGroup:ChipGroup? = null

    val categories:Array<String> = arrayOf("Technology","Politics","Sports","Entertainment","Business","General","Science")

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
        return inflater.inflate(R.layout.fragment_news, container, false)



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var id:Int = 0
        for(category in categories)
        {

            val c = Chip(context)
            c.id = ++id
            c.text = category
            c.chipBackgroundColor = getColorStateList(requireContext(), R.color.colorChip)

            c.setOnClickListener(this);
            filter_chips.addView(c)
            val newsData = NewsAPIService.create()
            newsData.getGeneralNews().enqueue(this)

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




    override fun onClick(p0: View?) {


        val chipClicked = p0 as Chip


        if(chipClicked.isSelected)
        {
            chipClicked.chipIcon = null
            chipClicked.isSelected = false
chipClicked.chipBackgroundColor = getColorStateList(requireContext(), R.color.colorChip)
            chipClicked.setTextColor(Color.BLACK)
        }
        else
        {
            chipClicked.isSelected = true

            chipClicked.chipIcon = resources.getDrawable(R.drawable.tick_icon,null)
            chipClicked.chipBackgroundColor = getColorStateList(requireContext(), R.color.colorPrimary)
            chipClicked.setTextColor(Color.WHITE)

        }

        Log.d(TAG, "onClick: "+chipClicked.text)

    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        Toast.makeText(context, "Failed to fetch the news for your country",Toast.LENGTH_LONG).show()
    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


        Log.d(TAG, "onResponse: "+NewsAPIService.parse(response.body()!!.string()).size)
    }
}