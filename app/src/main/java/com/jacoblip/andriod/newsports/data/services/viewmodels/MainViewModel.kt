package com.jacoblip.andriod.newsports.data.services.viewmodels

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jacoblip.andriod.newsports.data.services.repositorys.MainRepository
import com.jacoblip.andriod.newsports.ui.main.fragments.matches.MatchesMainFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val context: Context, private val repository: MainRepository
):ViewModel() {

    private var _mainFragmentCurrentFragment:MutableLiveData<Fragment> = MutableLiveData(
        MatchesMainFragment.newInstance())
    var mainFragmentCurrentFragment: LiveData<Fragment> = _mainFragmentCurrentFragment
}