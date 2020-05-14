package com.lk.tasker

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lk.tasker.databinding.ActivityLoginBinding
import java.util.*


class loginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager(binding.viewPager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        auth = Firebase.auth
//        binding.signupBtn.setOnClickListener{
//           createAccount(binding.etEmail.text.toString(),binding.etPassword.text.toString())
//            }
//        binding.signinBtn.setOnClickListener{
//           signIn(binding.etEmail.text.toString(),binding.etPassword.text.toString())
//                   }
//        binding.signOut.setOnClickListener{
//            signOut()
////            signIn(binding.etEmail.text.toString(),binding.etPassword.text.toString())
//        }
        binding.forgotPass.setOnClickListener{
            Firebase.auth.sendPasswordResetEmail(binding.etEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "password reset link is sent",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

//    private fun setupViewPager(viewPager: ViewPager) {
//        val adapter = SectionsPagerAdapter(supportFragmentManager)
//        PlaceholderFragment.newInstance(0)?.let { adapter.addFragment(it, "FEEDS") }
//        PlaceholderFragment.newInstance(1)?.let { adapter.addFragment(it, "FRIENDS") }
//        PlaceholderFragment.newInstance(2)?.let { adapter.addFragment(it, "CHATS") }
//        viewPager.adapter = adapter
//    }
    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.etEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = "Required."
            valid = false
        } else {
            binding.etEmail.error = null
        }

        val password = binding.etPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "Required."
            valid = false
        } else {
            binding.etPassword.error = null
        }

        return valid
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        startActivity(Intent(baseContext,HomeActivity::class.java))
    }
////    public override fun onStart() {
////        super.onStart()
////        // Check if user is signed in (non-null) and update UI accordingly.
////        val currentUser = auth.currentUser
//////        updateUI(currentUser)
////        val user = Firebase.auth.currentUser
////    }
//private fun createAccount(email: String, password: String) {
////    Log.d(TAG, "createAccount:$email")
//    if (!validateForm()) {
//        return
//    }
//
//    // [START create_user_with_email]
//    auth.createUserWithEmailAndPassword(email, password)
//        .addOnCompleteListener(this) { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
//                Log.d("TAG", "createUserWithEmail:success")
//                val user = auth.currentUser
//                user!!.sendEmailVerification()
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Log.d("TAG", "Email sent.")
//                        }
//                    }
//                updateUI(user)
//            } else {
//                // If sign in fails, display a message to the user.
////                Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                Toast.makeText(baseContext, task.exception!!.message,
//                    Toast.LENGTH_SHORT).show()
//                updateUI(null)
//            }
//
//            // [START_EXCLUDE]
////            hideProgressBar()
//            // [END_EXCLUDE]
//        }
//    // [END create_user_with_email]
//}
//    private fun signIn(email: String, password: String) {
//        Log.d("TAG", "signIn:$email")
//        if (!validateForm()) {
//            return
//        }
//
////        showProgressBar()
//
//        // [START sign_in_with_email]
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d("TAG", "signInWithEmail:success")
//                    val user = auth.currentUser
//                    if(user?.isEmailVerified!!){
//                        updateUI(user)
//                    }
//                    else{
//                        Toast.makeText(baseContext, "please varify email before further use",
//                            Toast.LENGTH_SHORT).show()
//                        user!!.sendEmailVerification()
//
//                    }
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.e("TAG", "signInWithEmail:failure"+ task.exception!!.message)
//                    Toast.makeText(baseContext, task.exception!!.message,
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
//                    // [START_EXCLUDE]
////                    checkForMultiFactorFailure(task.exception!!)
//                    // [END_EXCLUDE]
//                }
//
//                // [START_EXCLUDE]
////                if (!task.isSuccessful) {
////                    .status.setText(R.string.auth_failed)
////                }
////                hideProgressBar()
//                // [END_EXCLUDE]
//            }
//        // [END sign_in_with_email]
//    }
//    private fun signOut() {
//        auth.signOut()
//        updateUI(null)
//    }
//
//    private fun updateUI(user: FirebaseUser?) {
////        hideProgressBar()
//        if (user != null) {
//            binding.status.text =
//                user.email+user.isEmailVerified
//
//            binding.etEmail.visibility = View.GONE
//            binding.etPassword.visibility = View.GONE
//
////            if (user.isEmailVerified) {
////                ifyEmailButton.visibility = View.GONE
////            } else {
////                binding.verifyEmailButton.visibility = View.VISIBLE
////            }
//        } else {
//            binding.status.setText("signedOut")
//
//            binding.etEmail.visibility = View.VISIBLE
//            binding.etPassword.visibility = View.VISIBLE
//        }
//    }
    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPagerAdapter(supportFragmentManager)
        adapter.addFragment(siginFragment.newInstance(), "SING IN")
        adapter.addFragment(signUpFragment.newInstance(), "SIGN UP")
        viewPager.setAdapter(adapter)
    }
     class SectionsPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> =
            ArrayList<Fragment>()
        private val mFragmentTitleList: MutableList<String> =
            ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size

        }
        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}
