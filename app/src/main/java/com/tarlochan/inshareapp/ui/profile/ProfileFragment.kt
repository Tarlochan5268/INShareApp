package com.tarlochan.inshareapp.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.tarlochan.inshareapp.R
import com.tarlochan.inshareapp.ui.Constants

lateinit var pref: SharedPreferences
class ProfileFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val imgAvatar: ImageView = root.findViewById(R.id.imageViewAvatarSend)
        val tvUserName: TextView = root.findViewById(R.id.tvUserName)
        pref = requireContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        tvUserName.setText(pref.getString(Constants.USER_NAME,"Username")).toString()
        val id = requireContext().resources.getIdentifier(pref.getString(Constants.IMG_AVATAR,"boy")!!, "drawable", requireContext().packageName)
        imgAvatar.setImageResource(id)

        val btnDevice: Button = root.findViewById(R.id.btnEdit)
        val deviceSpannableText = SpannableString("Device     "+android.os.Build.DEVICE)
        deviceSpannableText.setSpan(ForegroundColorSpan(Color.rgb(255,153,52)), 11, deviceSpannableText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        deviceSpannableText.setSpan(AbsoluteSizeSpan(20, true), 11, deviceSpannableText.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        btnDevice.setText(deviceSpannableText)

        val btnModel: Button = root.findViewById(R.id.btnModel)
        val modelSpannableText = SpannableString("Model     "+android.os.Build.MODEL)
        modelSpannableText.setSpan(ForegroundColorSpan(Color.rgb(255,153,52)), 10, modelSpannableText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        modelSpannableText.setSpan(AbsoluteSizeSpan(20, true), 10, modelSpannableText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        btnModel.setText(modelSpannableText)

        val btnBrand: Button = root.findViewById(R.id.btnBrand)
        val brandSpannableText = SpannableString("Brand     "+android.os.Build.BRAND)
        brandSpannableText.setSpan(ForegroundColorSpan(Color.rgb(255,153,52)), 10, brandSpannableText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        brandSpannableText.setSpan(AbsoluteSizeSpan(20, true), 10, brandSpannableText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        btnBrand.setText(brandSpannableText)

        val btnTranfer: Button = root.findViewById(R.id.btnTransfer)
        val transferSpannableText = SpannableString("Transfers   "+pref.getString(Constants.TRANSFERS,"20"))
        transferSpannableText.setSpan(ForegroundColorSpan(Color.rgb(255,153,52)), 11, transferSpannableText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        transferSpannableText.setSpan(AbsoluteSizeSpan(20, true), 11, transferSpannableText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        btnTranfer.setText(transferSpannableText)


        return root
    }
}