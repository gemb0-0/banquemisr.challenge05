package com.example.banquemisrchallenge05.data.remoteDS

import android.util.Log
import com.example.banquemisrchallenge05.data.network.ApiService
import javax.inject.Inject

class RemoteDSImpl @Inject constructor(private  val api: ApiService) : RemoteDS {
    override suspend fun getPopularMovies() {
//        var res =  api.getPopularMovies()
//        if (res.isSuccessful){
//            res.body()?.let {
//                println("ggggggggggggggggggggg"+it)
//            }
//        }
    }


}