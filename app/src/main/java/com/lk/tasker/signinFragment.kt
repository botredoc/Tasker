package com.lk.tasker

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lk.tasker.databinding.FragmentSigninBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var _binding: FragmentSigninBinding? = null
private var fragmentContext: Context? = null
private val binding get() = _binding!!
private lateinit var auth: FirebaseAuth
/**
 * A simple [Fragment] subclass.
 * Use the [siginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class siginFragment : Fragment() {
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
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        val view = binding.root
        if (container != null) {
            fragmentContext = container.context

        }
        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()

        binding.signinBtn.setOnClickListener {
           show_Progress()
           signIn(binding.etEmail.text.toString(),binding.etPassword.text.toString())
        }
        binding.toRegister.setOnClickListener{
            val vp = activity?.findViewById<View>(R.id.viewPager) as ViewPager
            binding.etPassword.setText("")
            binding.etEmail.setText("")
            vp.setCurrentItem(1, true)
        }
        binding.forgotPass.setOnClickListener {
            binding.etPassword.visibility = View.GONE
            binding.sendResetLink.visibility = View.VISIBLE
            binding.forgotPass.visibility = View.GONE
            binding.backBtn.visibility = View.VISIBLE
            binding.toRegister.visibility = View.GONE
            binding.signinBtn.visibility = View.GONE
            if (binding.backBtn.visibility == View.VISIBLE) {
                binding.backBtn.setOnClickListener {
                    binding.progressBar.visibility = View.GONE
                    binding.etPassword.visibility = View.VISIBLE
                    binding.sendResetLink.visibility = View.GONE
                    binding.forgotPass.visibility = View.VISIBLE
                    binding.backBtn.visibility = View.GONE
                    binding.toRegister.visibility = View.VISIBLE
                    binding.signinBtn.visibility = View.VISIBLE
                }
            }
            if (binding.sendResetLink.visibility == View.VISIBLE) {
                binding.sendResetLink.setOnClickListener {
                    val email = binding.etEmail.text.toString()
                    if (TextUtils.isEmpty(email)) {
                        binding.etEmail.error = "Required."
                        showSnackbar("Please Provide Email")
                        return@setOnClickListener
                    } else {
                        binding.etEmail.error = null
                    }
                    binding.backBtn.isClickable = false
                    binding.sendResetLink.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    Firebase.auth.sendPasswordResetEmail(binding.etEmail.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showSnackbar("password reset link is sent")

                                binding.progressBar.visibility = View.GONE
                                binding.sendResetLink.visibility = View.VISIBLE
                            }else{
                                showSnackbar( task.exception!!.message.toString())

                            }
                            binding.backBtn.isClickable = true

                        }
                }
            }
        }

        return view
    }

//    private fun forgotPassword() {
//        binding.etPassword.visibility = View.GONE
//        binding.signinBtn.visibility=Vi
//        Firebase.auth.sendPasswordResetEmail(binding.etEmail.text.toString())
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                   showSnackbar("Password reset link is sent")
//                }
//
//    }


    private fun signIn(email: String, password: String) {
        Log.d("TAG", "signIn:$email")
        if (!validateForm()) {
           hide_Progress()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth.currentUser
                    if(user?.isEmailVerified!!){
                        showSnackbar("Login Successful")
                        startActivity(Intent(fragmentContext,HomeActivity::class.java))
                        binding.signinBtn.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE


                    }
                    else{

                        showSnackbar("Please varify email before further use")
                        user.sendEmailVerification()
                    }

                } else {
                    Log.e("TAG", "signInWithEmail:failure"+ task.exception!!.message)
                    showSnackbar( task.exception!!.message.toString())
                }
                hide_Progress()

            }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment siginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            siginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun newInstance(): siginFragment {
         return siginFragment()
        }
    }

    override fun setUserVisibleHint(visible: Boolean) {
        super.setUserVisibleHint(visible)
        if (visible && isResumed) {
            onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!userVisibleHint) {
            return
        }
showSnackbar("heeleelele")
        //Add your code this section
    }
}
private fun validateForm(): Boolean {
    var valid = true

    val email = binding.etEmail.text.toString()
    if (TextUtils.isEmpty(email)) {
        binding.etEmail.error = "Required."
        showSnackbar("Please Provide Email")

        valid = false
    } else {
        binding.etEmail.error = null
    }

    val password = binding.etPassword.text.toString()
    if (TextUtils.isEmpty(password)) {
        binding.etPassword.error = "Required."
        showSnackbar("Please Provide Password")
        valid = false
    } else {
        binding.etPassword.error = null
    }

    return valid
}
fun showSnackbar(string:String){
    var snackbar:Snackbar = Snackbar.make(binding.toast, string, Snackbar.LENGTH_LONG)
    fragmentContext?.let { ContextCompat.getColor(it, R.color.colorPrimary) }?.let {
        snackbar.view.setBackgroundColor(it)
    }
    snackbar.setActionTextColor(Color.WHITE)
    snackbar.show()
}
fun show_Progress(){
    binding.forgotPass.isClickable=false
    binding.toRegister.isClickable=false
    binding.signinBtn.isClickable=false
    binding.signinBtn.visibility = View.GONE
    binding.progressBar.visibility = View.VISIBLE
}
fun hide_Progress(){
    binding.forgotPass.isClickable=true
    binding.toRegister.isClickable=true
    binding.signinBtn.isClickable=true
    binding.signinBtn.visibility = View.VISIBLE
    binding.progressBar.visibility = View.GONE
}