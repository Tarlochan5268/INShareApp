package com.kishorenarang.ui.send.tabs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidbuffer.kotlinfilepicker.KotConstants
import com.androidbuffer.kotlinfilepicker.KotRequest
import com.androidbuffer.kotlinfilepicker.KotResult
import com.getbase.floatingactionbutton.FloatingActionButton
import com.kishorenarang.adapters.FilesAdapter
import com.kishorenarang.api.FileItem
import com.tarlochan.inshareapp.R
import kotlinx.android.synthetic.main.fragment_files.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    private val FilesList = arrayListOf<FileItem>()
    private var adapter:FilesAdapter? = null

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
        val root = inflater.inflate(R.layout.fragment_files, container, false)
        val recyclerView: RecyclerView =  root.findViewById<RecyclerView>(R.id.recyclerViewFiles)

        adapter = FilesAdapter(FilesList, context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter!!.notifyDataSetChanged()

        val toolBarButton: ImageButton = root.findViewById(R.id.toolbarBtn)
        toolBarButton.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,"Tool Bar Button Clicked ",Toast.LENGTH_SHORT).show()
        })

        val fabAddFiles:FloatingActionButton = root.findViewById<FloatingActionButton>(R.id.fabAddFiles)
        val fabAddMedia:FloatingActionButton = root.findViewById<FloatingActionButton>(R.id.fabAddMedia)
        fabAddFiles.setOnClickListener(View.OnClickListener {
            KotRequest.File(requireActivity(), REQUEST_FILE)
                .isMultiple(true)
                .setMimeType(KotConstants.FILE_TYPE_FILE_ALL)
                .pick()
            Toast.makeText(context,"Fab Button Files Clicked ",Toast.LENGTH_SHORT).show()
            Log.d("--> Result Check: ","Checking Files")
        })
        fabAddMedia.setOnClickListener(View.OnClickListener {

            Toast.makeText(context,"Fab Button Media Clicked ",Toast.LENGTH_SHORT).show()
        })
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_FILE == requestCode && resultCode == Activity.RESULT_OK) {
            val result = data?.getParcelableArrayListExtra<KotResult>(KotConstants.EXTRA_FILE_RESULTS)
            //Log.d("--> Result Got: ",result.toString())
            result!!.forEach { e ->
                data.data.let {
                    activity?.contentResolver?.query(e.uri, null, null, null, null)
                }?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    cursor.moveToFirst()
                    Log.d("--> Name: ",cursor.getString(nameIndex))
                    Log.d("--> Size: ",cursor.getLong(sizeIndex).toString())
                    FilesList.add(FileItem(cursor.getString(nameIndex),requireContext().resources.getDrawable(R.drawable.ic_file,requireContext().theme),e.uri.toString(),simplifySize(cursor.getLong(sizeIndex).toString())))
                }
            }

            adapter!!.notifyDataSetChanged()
        }
    }

    fun simplifySize(size:String) : String
    {
        return (size.toLong()/1000000).toString()+" MB";
    }
    fun addToList(data: Intent?)
    {
        val result = data?.getParcelableArrayListExtra<KotResult>(KotConstants.EXTRA_FILE_RESULTS)
        Log.d("--> Result Got: ",result.toString())
        result!!.forEach { e -> Log.d("--> File Got: ",e.toString()) }
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