package com.example.meetup.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetup.model.GetStoreDetailMenuInfoResponseModel
import com.example.meetup.model.GetStoreDetailReviewResponseModel
import com.example.meetup.model.store.GetStoreDetailResponseModel
import com.example.meetup.model.store.GetStoreListResponseModel
import com.example.meetup.model.store.GetStoreListStores
import com.example.meetup.retrofit2.APIS
import com.example.meetup.retrofit2.RetrofitInstance
import com.kakao.sdk.auth.TokenManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreListViewModel : ViewModel()
{
    private lateinit var API : APIS


    private var _storeList = MutableLiveData<GetStoreListResponseModel>()
    var storeList : LiveData<GetStoreListResponseModel> = _storeList

    private var _storeDatail = MutableLiveData<GetStoreDetailResponseModel>()
    var storeDetail :LiveData<GetStoreDetailResponseModel> = _storeDatail

    private var _storeDatailReview = MutableLiveData<GetStoreDetailReviewResponseModel>()
    var storeDatailReview :LiveData<GetStoreDetailReviewResponseModel> = _storeDatailReview

    private var _storeDatailMenu = MutableLiveData<GetStoreDetailMenuInfoResponseModel>()
    var storeDatailMenu :LiveData<GetStoreDetailMenuInfoResponseModel> = _storeDatailMenu



    //가게 목록 가져오기
    fun getStoreList(context : Context, keyword :String, field : String, direction : String) {
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getStoreList(tokenManager.getAccessToken().toString(),keyword,field,direction).enqueue(
                    object : Callback<GetStoreListResponseModel> {

                        override fun onResponse(call: Call<GetStoreListResponseModel>, response: Response<GetStoreListResponseModel>) {
                            if (response.isSuccessful) {

//                                _storeList.value = response.body()?.result?.stores
                                _storeList.value = response.body()

                                Log.d("_storeList : " , " success , ${_storeList.value}")

                                Log.d("GetStoreListResponseModel : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("GetStoreListResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<GetStoreListResponseModel>, t: Throwable) {
                            Log.d("GetStoreListResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("GetStoreListResponseModel response : ", " fail 3 , ${e.message}")
            }
        }
    }


    //가게 상세 정보
    fun getStoreDetail(context : Context, storeId : Long) {
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getStoreDetail(tokenManager.getAccessToken().toString(),storeId).enqueue(
                    object : Callback<GetStoreDetailResponseModel> {

                        override fun onResponse(call: Call<GetStoreDetailResponseModel>, response: Response<GetStoreDetailResponseModel>) {
                            if (response.isSuccessful) {

//                                _storeList.value = response.body()?.result?.stores
                                _storeDatail.value = response.body()

                                Log.d("_storeList : " , " success , ${_storeList.value}")

                                Log.d("GetStoreDetailResponseModel : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("GetStoreDetailResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<GetStoreDetailResponseModel>, t: Throwable) {
                            Log.d("GetStoreDetailResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("GetStoreDetailResponseModel response : ", " fail 3 , ${e.message}")
            }
        }
    }

    fun getStoreDetailMenu(context : Context, storeId : Long) {
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getStoreDetailMenu(tokenManager.getAccessToken().toString(),storeId.toInt()).enqueue(
                    object : Callback<GetStoreDetailMenuInfoResponseModel> {

                        override fun onResponse(call: Call<GetStoreDetailMenuInfoResponseModel>, response: Response<GetStoreDetailMenuInfoResponseModel>) {
                            if (response.isSuccessful) {

//                                _storeList.value = response.body()?.result?.stores
                                _storeDatailMenu.value = response.body()

                                Log.d("_storeList : " , " success , ${_storeList.value}")

                                Log.d("GetStoreDetailMenuInfoResponseModel : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("GetStoreDetailMenuInfoResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<GetStoreDetailMenuInfoResponseModel>, t: Throwable) {
                            Log.d("GetStoreDetailMenuInfoResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("GetStoreDetailMenuInfoResponseModel response : ", " fail 3 , ${e.message}")
            }
        }
    }

    fun getStoreReview(context : Context, storeId : Int) {
        API = RetrofitInstance.retrofitInstance().create(APIS::class.java)

        val tokenManager = com.example.meetup.sharedPreference.TokenManager(context)   //가게 목록 가져오기

//        val accessToken = MyApplication.preferences.getString("accessToken", "")

        Log.d("tokenManager", tokenManager.getAccessToken().toString())
        viewModelScope.launch {
            try{
                API.getStoreDetailReview(tokenManager.getAccessToken().toString(),storeId.toInt()).enqueue(
                    object : Callback<GetStoreDetailReviewResponseModel> {

                        override fun onResponse(call: Call<GetStoreDetailReviewResponseModel>, response: Response<GetStoreDetailReviewResponseModel>) {
                            if (response.isSuccessful) {

//
                                _storeDatailReview.value = response.body()

                                Log.d("_storeDatailReview : " , " success , ${_storeDatailReview.value}")

                                Log.d("GetStoreDetailReviewResponseModel : " , " success , ${response.body().toString()}")
                            } else {

                                Log.d("GetStoreDetailReviewResponseModel Response : ", "fail 1 ${response.body().toString()} , ${response.message()}, ${response.errorBody().toString()}")
                            }
                        }

                        override fun onFailure(call: Call<GetStoreDetailReviewResponseModel>, t: Throwable) {
                            Log.d("GetStoreDetailReviewResponseModel Response : ", " fail 2 , ${t.message.toString()}")
                        }
                    })
            } catch (e:Exception) {
                Log.d("GetStoreDetailReviewResponseModel response : ", " fail 3 , ${e.message}")
            }
        }
    }
}