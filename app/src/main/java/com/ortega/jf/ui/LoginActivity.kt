package com.ortega.jf.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.ortega.jf.R
import com.ortega.jf.core.My_Applicacion
import com.ortega.jf.databinding.ActivityLoginBinding
import com.ortega.jf.logic.login.SingIn
import com.ortega.jf.ui.core.Constants

class LoginActivity : AppCompatActivity() {

    private val singIn:SingIn=SingIn(My_Applicacion.getConnectionDB()!!)
    private lateinit var binding: ActivityLoginBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }

    override fun onStart() {
        super.onStart()
        initControls()


    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun initControls(){

        binding.lyRoot.setOnClickListener {

        }


        binding.btnLogin.setOnClickListener {
            val check:Int=SingIn(My_Applicacion.getConnectionDB()!!).checkUserAndPasswordForma4(
                binding.txtUsuario.text.toString(),
                binding.txtPass.text.toString()
            )


            //if (!singIn.checkUserAndPasswordForma3(u,p)){
            if (check<0){
                //Toast.makeText(this, u, Toast.LENGTH_LONG).show()
                //Snackbar.make(binding.btnLogin,u,Snackbar.LENGTH_LONG).show()
                Snackbar.make(binding.btnLogin,"usuario o" +
                        "password incorrecto",Snackbar.LENGTH_LONG).show()
            }else{
                // aca se activa la llamada al activity main
                val intent= Intent(this,MainActivity::class.java)
                Log.d(Constants.TAG,"Se creo el intent")
                intent.putExtra(Constants.USER_ID,check)
                startActivity(intent)
            }




        }

        binding.btnLogin.setOnClickListener {
            val intentImpl=Intent()
            intentImpl.action=Intent.ACTION_SEND
            intentImpl.putExtra(Intent.EXTRA_TEXT
                ,"Mi primera chamba")
            intentImpl.type="text/plain"
            startActivity(intentImpl)
        }
    }




}