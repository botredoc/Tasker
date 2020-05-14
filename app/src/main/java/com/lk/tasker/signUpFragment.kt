package com.lk.tasker

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lk.tasker.databinding.FragmentSignUpBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private var _binding: FragmentSignUpBinding? = null
private var fragmentContext: Context? = null
private val binding get() = _binding!!
private lateinit var auth: FirebaseAuth

/**
 * A simple [Fragment] subclass.
 * Use the [signUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class signUpFragment : Fragment() {
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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        if (container != null) {
            fragmentContext=container.getContext()

        }
        auth = Firebase.auth
        auth = FirebaseAuth.getInstance();

        binding.signupBtn.setOnClickListener{
            show_progressBar()

            createAccount(binding.etEmail.text.toString(),binding.etPassword.text.toString())
        }
        binding.toLogin.setOnClickListener{
            val vp = activity?.findViewById<View>(R.id.viewPager) as ViewPager
            binding.etEmail.setText("")
            binding.etPassword.setText("")

            vp.setCurrentItem(0, true);
        }
        return view
    }

    private fun createAccount(toString: String, toString1: String) {
        if (!validateForm()) {
            hide_progressBar()

            return

      }
             auth.createUserWithEmailAndPassword(toString, toString1).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showSnackbar1("verification link sent")
                                Log.d("TAG", "Email sent.")
                            } else {
                                showSnackbar(task.exception!!.message.toString())

                            }
                            hide_progressBar()
                            showSnackbar1("Sign up Sucessfull")
                            val vp = activity?.findViewById<View>(R.id.viewPager) as ViewPager
                            binding.etEmail.setText("")
                            binding.etPassword.setText("")
                            vp.setCurrentItem(0, true);

                        }
                } else {
                    hide_progressBar()
                    showSnackbar1(task.exception!!.message.toString())
                }

            }

    }






companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment signUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            signUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun newInstance(): signUpFragment {
            return signUpFragment()

        }
    }
}

private fun validateForm(): Boolean {
    var valid = true

    val email = binding.etEmail.text.toString()
    if (TextUtils.isEmpty(email)) {
        binding.etEmail.error = "Required."
        showSnackbar1("Please provide email")

        valid = false
    } else {
        binding.etEmail.error = null
    }

    val password = binding.etPassword.text.toString()
    if (TextUtils.isEmpty(password)) {
        binding.etPassword.error = "Required."
        showSnackbar1("Please provide password")

        valid = false
    } else {
        binding.etPassword.error = null
    }

    return valid
}
fun show_progressBar(){
    binding.signupBtn.isClickable = false
    binding.toLogin.isClickable=false
    binding.signupBtn.visibility = View.GONE
    binding.progressBar.visibility = View.VISIBLE
}
fun hide_progressBar(){
    binding.signupBtn.isClickable = true
    binding.toLogin.isClickable=true
    binding.signupBtn.visibility = View.VISIBLE
    binding.progressBar.visibility = View.GONE
}

fun showSnackbar1(string:String){
    var snackbar: Snackbar = Snackbar.make(binding.toast2, string, Snackbar.LENGTH_LONG)
    fragmentContext?.let { ContextCompat.getColor(it, R.color.colorPrimary) }?.let {
        snackbar.view.setBackgroundColor(it)
    }
    snackbar.setActionTextColor(Color.WHITE)
    snackbar.show()
}
