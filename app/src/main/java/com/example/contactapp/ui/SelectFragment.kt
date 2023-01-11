package com.example.contactapp.ui

import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.contactapp.R
import com.example.contactapp.databinding.FragmentSelectBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectFragment : Fragment() {
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
    private lateinit var binding: FragmentSelectBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectBinding.inflate(inflater,container,false)

        val name = arguments?.getString("names")
        val number = arguments?.getString("number")

        binding.sendMsgBtn.setOnClickListener {
            try {
                val number = arguments?.getString("number")
                // on below line we are initializing sms manager.
                val smsManager: SmsManager = SmsManager.getDefault()

                // on below line we are sending text message.
                smsManager.sendTextMessage(number, null, binding.editSms.text.toString(), null, null)

                // on below line we are displaying a toast message for message send,
                Toast.makeText(requireContext(), "Message Sent", Toast.LENGTH_LONG).show()

            } catch (e: Exception) {

                // on catch block we are displaying toast message for error.
                Toast.makeText(requireContext(), "Please enter all the data.."+e.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }
            findNavController().popBackStack()
        }

        binding.apply {

            names.text = name
            numbers.text = number

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}