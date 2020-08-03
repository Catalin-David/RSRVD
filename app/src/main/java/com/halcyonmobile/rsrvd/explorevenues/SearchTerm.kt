package com.halcyonmobile.rsrvd.explorevenues

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.halcyonmobile.rsrvd.BR

class SearchTerm : BaseObservable() {
    var term = MutableLiveData<String>()
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.search_term)
        }
}
