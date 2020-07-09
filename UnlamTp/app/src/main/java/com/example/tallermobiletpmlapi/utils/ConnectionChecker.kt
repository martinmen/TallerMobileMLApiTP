package com.example.tallermobiletpmlapi.utils

import com.example.tallermobiletpmlapi.R

class ConnectionChecker {
    companion object {
        fun getResponseCodeDesc(code: Int): Int {

            return when (code) {
                404 -> {
                    R.string.resource_not_found
                }

                400 -> {
                    R.string.bad_request
                }

                in 500..599 -> {
                    R.string.server_error
                }

                else -> {
                    R.string.unknown_error
                }
            }

        }
    }
}
/*/ CONSULTAR COMO PODRIA HACERLO: NECESITO EL CONTEXTO
    private  fun checkConnection(): Boolean {
        val cm = getSystemService(Context.ACCESSIBILITY_SERVICE as Context) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (!isConnected){
            return false
            //    Toast.makeText(this,R.string.InternetNotAviable, Toast.LENGTH_LONG).show()
        }else return true
    }
    */






    
