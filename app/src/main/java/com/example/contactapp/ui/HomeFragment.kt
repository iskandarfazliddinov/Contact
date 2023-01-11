package com.example.contactapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.contactapp.Adapters.UserAdapter
import com.example.contactapp.Models.UserData
import com.example.contactapp.R
import com.example.contactapp.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: UserAdapter
    private lateinit var userData: ArrayList<UserData>

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        var resolver = requireActivity().contentResolver
        val contacts = resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        userData = ArrayList()
        while (contacts!!.moveToNext()) {
            val name =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            val user = UserData(name, number)
            userData.add(user)
        }
        adapter = UserAdapter(userData, {
            val bundle = Bundle()
            bundle.putString("names", it.name)
            bundle.putString("number", it.phoneNumber)

            findNavController().navigate(R.id.action_homeFragment_to_selectFragment, bundle)
        }) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel: ${it.phoneNumber}"))
            startActivity(intent)
        }
        binding.Recycler.adapter = adapter

        adapter.notifyDataSetChanged()


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}