package com.travelme.driver.fragments.profile_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.travelme.driver.R
import com.travelme.driver.extensions.gone
import com.travelme.driver.extensions.visible
import com.travelme.driver.models.Driver
import com.travelme.driver.utilities.Constants
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile){

    private val profileViewModel : ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.listenToState().observer(viewLifecycleOwner, Observer { handleUI(it) })
        profileViewModel.listenToDriver().observe(viewLifecycleOwner, Observer { handleDriver(it) })
    }

    private fun handleUI(it : ProfileState){
        when(it){
            is ProfileState.IsLoading -> {
                if (it.state){
                    pb_profile.visible()
                    pb_profile.isIndeterminate = true
                }else{
                    pb_profile.gone()
                    pb_profile.isIndeterminate = true
                }
            }
            is ProfileState.ShowToast -> toast(it.message)
        }
    }

    private fun handleDriver(it : Driver){
        txt_name.text = it.name
        txt_email.text = it.email
        txt_telp.text = it.telp
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.profile(Constants.getToken(requireActivity()))
    }

    private fun toast(message: String) { Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show() }
}