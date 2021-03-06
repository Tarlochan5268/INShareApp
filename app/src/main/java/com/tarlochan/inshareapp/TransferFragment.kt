package com.tarlochan.inshareapp

import android.content.Intent.getIntent
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kishorenarang.adapters.TransferAdapter
import com.kishorenarang.api.FileItem
import com.kishorenarang.api.FileItems
import com.kishorenarang.api.TransferItem
import java.util.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransferFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //private var FilesList = java.util.ArrayList<FileItem>()

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

        val roott =  inflater.inflate(R.layout.fragment_transfer, container, false)
        Log.d("-->GotList : ",TransferFragmentArgs.fromBundle(requireArguments()).listOfFiles.toString())
        TransferFragmentArgs.fromBundle(requireArguments()).listOfFiles.forEach {
            file -> Log.d("-->got Files: ",file.name+" "+file.size+" "+file.icon+" "+file.location)
        }
        var transferList:ArrayList<TransferItem> = convertToTransferList(TransferFragmentArgs.fromBundle(requireArguments()).listOfFiles)

        var handler: Handler? = null
        var isStarted:Boolean = true
        val delay:Long = 100
        var progressStatus:Int = 0
        val sizeList = transferList.size
        val sizefILE = 0
        var totalSize = 0
        transferList.forEach { file ->
            var sizze = file.size.substring(0,file.size.lastIndexOf("."))
            Log.d("-->Sizee: ",sizze)
            totalSize+=sizze.toInt() }
        val totalDelay = delay + totalSize/sizeList

        val recyclerview = roott.findViewById<RecyclerView>(R.id.recyclerViewTransfer)

        val adapter = TransferAdapter(transferList, context)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(context)
        adapter.notifyDataSetChanged()

        val progressBar = roott.findViewById<ProgressBar>(R.id.progressBarHorizontal)
        val progressHeader = roott.findViewById<TextView>(R.id.tvTransferHead)
        var progressCount:Int = 0
        var totalUpdate:Int = 100/sizeList
        var stepper:Int = 0 + totalUpdate
        handler = Handler(Handler.Callback {


            if (isStarted) {
                progressHeader.text = "Sending ( ${progressStatus} %)"
                if(progressStatus == stepper)
                {
                    transferList[progressCount].icon = requireContext().resources.getDrawable(R.drawable.ic_done,requireContext().theme)
                    transferList[progressCount].status = "Done"
                    adapter.notifyDataSetChanged()
                    progressCount+=1
                    stepper += totalUpdate
                }
                progressStatus += 1
                if(progressStatus == 100)
                {
                    transferList[sizeList-1].icon = requireContext().resources.getDrawable(R.drawable.ic_done,requireContext().theme)
                    transferList[sizeList-1].status = "Done"
                    adapter.notifyDataSetChanged()
                    isStarted = false
                    progressStatus = 100
                    progressHeader.text = "Sent ( ${progressStatus} %)"
                }
            }
            handler?.removeCallbacksAndMessages(null)
            progressBar.progress = progressStatus
            //Log.d("-->Progress: ","Sending : ${progressStatus}/${progressBarHorizontal.max}")

            handler?.sendEmptyMessageDelayed(0, totalDelay)
            progressHeader.text = "Transfer Complete"
            true
        })

        progressHeader.text = "Transfer Complete"
        handler.sendEmptyMessage(0)
        progressHeader.text = "Transfer Complete"
        return roott

    }

    private  val TAG = "TransferFragment"

    fun convertToTransferList(fileList: FileItems?) : ArrayList<TransferItem>
    {
        val transferList:ArrayList<TransferItem>? = ArrayList<TransferItem>()
        fileList!!.forEach { file ->
            transferList!!.add(TransferItem(file.name,requireContext().resources.getDrawable(R.drawable.ic_file,requireContext().theme),"In Queue",file.size))
        }
        Log.d("-->Size of List:",transferList!!.size.toString())
        return transferList!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = TransferFragmentArgs.fromBundle(requireArguments()).listOfFiles
        Log.d(TAG, "onViewCreated: "+list)

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TransferFragment.
         */


        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransferFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}