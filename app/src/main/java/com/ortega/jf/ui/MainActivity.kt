package com.ortega.jf.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.creative.ipfyandroid.Ipfy
import com.creative.ipfyandroid.IpfyClass
import com.google.android.material.snackbar.Snackbar
import com.ortega.jf.R
import com.ortega.jf.core.My_Applicacion
import com.ortega.jf.databinding.ActivityMainBinding
import com.ortega.jf.logic.login.SingIn
import com.ortega.jf.ui.core.Constants
import com.ortega.jf.ui.fragments.FragmentFavorites
import com.ortega.jf.ui.fragments.List1Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        checkDataBase()


    }

    private fun checkDataBase() {
        lifecycleScope.launch(Dispatchers.Main) {
            val usrs= withContext(Dispatchers.IO){
                SingIn(My_Applicacion.getConnectionDB()!!)
                    .getAllUsers()
            }
            Log.d(Constants.TAG,usrs.toString())

        }


    }

    private fun initListeners() {
        // uso del ipify
        Ipfy.init(this) // this is a context of application
        //or you can also pass IpfyClass type to get either IPv4 address only or universal address IPv4/v6 as
        Ipfy.init(this,IpfyClass.IPv4) //to get only IPv4 address
        //and
        Ipfy.init(this,IpfyClass.UniversalIP) //to get Universal address in IPv4/v6

        getIpAddress()

        intent.extras.let{

            lifecycleScope.launch (Dispatchers.IO){
                intentConecction()
            }


            val userId=it?.getInt(Constants.USER_ID)
            if(userId!=null){
                val user=SingIn(My_Applicacion.getConnectionDB()!!)
                    .getUserName3(userId) //ojo estaba
                binding.textView.text=user.id.toString()
            }else{
                // se deberia mandar a un activity de error.
                Snackbar.make(binding.textView,"error",Snackbar.LENGTH_LONG).show()
            }

        }


        //////////FRAGMENTS
        val list1Fragment=List1Fragment()
        val fragmentFavorites=FragmentFavorites()

        //transaccion.replace(binding.frmContainer.id,list1Fragment)
        //transaccion.replace(binding.frmContainer2.id,fragmentFavorites)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val manager=supportFragmentManager
            when(item.itemId) {
                R.id.it_home -> {
                    val transaccion=manager.beginTransaction()

                    transaccion.replace(binding.frmContainer.id,List1Fragment())
                    transaccion.commit()
                    // Respond to navigation item 1 click
                    true
                }
                R.id.it_fav -> {
                    val transaccion=manager.beginTransaction()

                    transaccion.replace(binding.frmContainer.id,fragmentFavorites)
                    transaccion.commit()
                    // Respond to navigation item 2 click
                    true
                }
                else -> {

                    lifeScopeCorrutinas();
                    false
                }
            }

        }

    }

    private fun lifeScopeCorrutinas() {
        lifecycleScope.launch(Dispatchers.IO) {
            val a = "daniel"
            withContext(Dispatchers.Main) {
                binding.textView4.text = a
            }

        }

        lifecycleScope.launch(Dispatchers.Main) {
            val name = withContext(Dispatchers.IO){
                val a="Bayron"
                val b=a+"Torres"

                b
            }
            /*
            val s =async {
                val a=""
                a
            }
            val s2 =async {
                val a=""
                a
            }



            val deferreds: List<Deferred<String>> = mutableListOf(s,s2)

            deferreds.awaitAll()
*/
            val w= withContext(Dispatchers.Default){
                val listC=listOf(
                    async { getName() },
                    async { getName() }
                )
                val w1= listC.awaitAll()
            }


            val name1 = withContext(Dispatchers.IO){
                getName()
            }

            binding.textView4.text=name1.toString()
        }
    }


    suspend fun getName():String{
        val a="Carlos"
        val b= a+"Mapache"
        return b
    }
    suspend fun intentConecction(){
        My_Applicacion.getConnectionDB()!!.getUserDAO().getUser(3)
    }

    private fun getIpAddress(){
        Ipfy.getInstance().getPublicIpObserver().observe(this, { ipData ->
            binding.txtIp.text=ipData.currentIpAddress // this is a value which is your current public IP address, null if no/lost internet connection


        })
    }

    override fun onStart() {
        super.onStart()
        initControls()


    }

    fun initControls(){

        binding.btnlogout.setOnClickListener {
                // aca se activa la llamada al activity main
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)





        }
    }




}