package com.kishorenarang.ui.send.tabs

import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidbuffer.kotlinfilepicker.KotConstants
import com.androidbuffer.kotlinfilepicker.KotRequest
import com.androidbuffer.kotlinfilepicker.KotResult
import com.getbase.floatingactionbutton.FloatingActionButton
import com.kishorenarang.adapters.FilesAdapter
import com.kishorenarang.api.FileItem
import com.kishorenarang.api.FileItems
import com.kishorenarang.ui.send.SendFragmentDirections
import com.tarlochan.inshareapp.R
import com.tarlochan.inshareapp.TransferActivity
import kotlinx.android.synthetic.main.fragment_files.*
import java.math.RoundingMode
import java.text.DecimalFormat


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private const val REQUEST_GALLERY = 102
private const val REQUEST_FILE = 103

/**
 * A simple [Fragment] subclass.
 * Use the [Files.newInstance] factory method to
 * create an instance of this fragment.
 */
class Files : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val FilesList = FileItems()
    private var adapter:FilesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarBtnSendToTransferFragment.setOnClickListener(View.OnClickListener {

            val navController = Navigation.findNavController(view)
            Toast.makeText(requireContext(), "Files being sent to connected device",Toast.LENGTH_SHORT).show()


        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_files, container, false)
        val recyclerView: RecyclerView =  root.findViewById<RecyclerView>(R.id.recyclerViewFiles)

        adapter = FilesAdapter(FilesList, context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter!!.notifyDataSetChanged()

        val fabSend:FloatingActionButton = root.findViewById<FloatingActionButton>(R.id.fabSend)
        fabSend.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,"Send Button Clicked ",Toast.LENGTH_SHORT).show()



            val navController = Navigation.findNavController(requireView())


           val action = SendFragmentDirections.actionSendFragmentToTransferFragment(FilesList as FileItems)

            navController.navigate(action)

            //FilesList -> Transfer to Tranfer Activity or Tranfer Fragment
        })


        val fabAddFiles:FloatingActionButton = root.findViewById<FloatingActionButton>(R.id.fabAddFiles)
        fabAddFiles.setOnClickListener(View.OnClickListener {
            tvMiddleText.isVisible = false
            KotRequest.File(requireActivity(), REQUEST_FILE)
                .isMultiple(true)
                .setMimeType(KotConstants.FILE_TYPE_FILE_ALL)
                .pick()
            Toast.makeText(context,"Fab Button Files Clicked ",Toast.LENGTH_SHORT).show()
            //Log.d("--> Result Check: ","Checking Files")
        })
        val fabAddMedia:FloatingActionButton = root.findViewById<FloatingActionButton>(R.id.fabAddMedia)
        fabAddMedia.setOnClickListener(View.OnClickListener {
            tvMiddleText.isVisible = false
            KotRequest.Gallery(requireActivity(), REQUEST_GALLERY)
                .isMultiple(true)
                .pick()
            Toast.makeText(context,"Fab Button Media Clicked ",Toast.LENGTH_SHORT).show()
        })
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            val result = data?.getParcelableArrayListExtra<KotResult>(KotConstants.EXTRA_FILE_RESULTS)
            //Log.d("--> Result Got: ",result.toString())
            result!!.forEach { e ->
                data.data.let {
                    activity?.contentResolver?.query(e.uri, null, null, null, null)
                }?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    cursor.moveToFirst()
                    //Log.d("--> Name: ",cursor.getString(nameIndex))
                    //Log.d("--> Size: ",cursor.getLong(sizeIndex).toString())
                    if(checkIfFileExistAlready(cursor.getString(nameIndex)))
                    {
                       Toast.makeText(context,"Some File Have Already Been Added Before",Toast.LENGTH_SHORT) .show()
                    }
                    else
                    {
                        if(requestCode == REQUEST_FILE)
                        {
                            FilesList.add(FileItem(cursor.getString(nameIndex),R.drawable.ic_file,formatLocation(e.uri.toString()),simplifySize(cursor.getLong(sizeIndex).toString())))
                        }
                        else
                        {
                            FilesList.add(FileItem(cursor.getString(nameIndex),R.drawable.ic_media_file,formatLocation(e.uri.toString()),simplifySize(cursor.getLong(sizeIndex).toString())))
                        }

                    }
                }
            }

            adapter!!.notifyDataSetChanged()
    }

    private fun simplifySize(size:String) : String
    {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        if(size.toLong()/1000000<1)
        {
            //return (df.format(size.toFloat()/1000)).toString()+" KB"
            return (size.toLong()/1000).toString()+" KB";
        }
        return (df.format(size.toFloat()/1000000)).toString()+" MB"
        //return (size.toFloat()/1000000).toString()+" MB";
    }

    private fun formatLocation(fileLoc: String):String
    {
        if(fileLoc.indexOf("/",11) == -1)
        {
            return fileLoc.substring(10)
        }
        return fileLoc.substring(fileLoc.indexOf("/",11))
    }

    private fun checkIfFileExistAlready(fileName:String):Boolean
    {
        var flag:Boolean = false
        FilesList.forEach {
            file ->
            //Log.d("--> In FileList : ",file.name+" - "+fileName)
            if(file.name!!.compareTo(fileName) == 0)
            {
                flag = true
                return flag
            }
            else
            {
                flag = false
            }
        }
        return flag
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Files.
         */

        val dataPassed: Intent? = null

        private  val TAG = "FilesFragment"

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Files().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}