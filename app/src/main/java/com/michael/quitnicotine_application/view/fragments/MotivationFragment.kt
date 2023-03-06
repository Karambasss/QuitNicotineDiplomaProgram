package com.michael.quitnicotine_application.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.adapters.CategoryAdapter
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.data.Category
import com.michael.quitnicotine_application.data.UserData
import kotlinx.android.synthetic.main.fragment_motivation.*


class MotivationFragment : Fragment(), CategoryAdapter.RecyclerViewInterface {
    private var categoryList = mutableListOf<Category>()
    private lateinit var sharedPreferences: SharedPreferences
    private var savedMoney : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_motivation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(
            ShConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE
        )
        updateSavedMoney(getSharedPreferencesParsedObject())
        setUpMotivationData()
        initRecycler()
    }

    private fun checkSharedPreferencesData() = sharedPreferences.contains(ShConstants.KEY_NAME_USER_DATA)

    private fun getSharedPreferencesParsedObject(): UserData?{
        if (checkSharedPreferencesData()){
            val jsonData = sharedPreferences.getString(ShConstants.KEY_NAME_USER_DATA, null)
            val gson = Gson()
            return gson.fromJson(jsonData, UserData::class.java)
        }
        return null
    }

    private fun updateSavedMoney(userData: UserData?){
        savedMoney = if (userData?.getSavedMoney()!=null){
            userData.getSavedMoney()
        }
        else{
            0.0
        }
        motivationSavedMoney.text = savedMoney.toString()
    }

    private fun setUpMotivationData(){
        val categoryNames = resources.getStringArray(R.array.category_names)
        val categoryImages = arrayOf(
            R.drawable.ic_baseline_directions_car_24,
            R.drawable.ic_baseline_local_florist_24,
            R.drawable.ic_baseline_directions_bike_24,
            R.drawable.ic_baseline_sensor_window_24,
            R.drawable.ic_baseline_school_24,
            R.drawable.lipstick_svgrepo_com,
            R.drawable.ic_baseline_child_care_24,
            R.drawable.ic_baseline_menu_book_24,
            R.drawable.perfume_svgrepo_com,
            R.drawable.clothes_svgrepo_com,
            R.drawable.washing_machine_svgrepo_com,
            R.drawable.chemicals_flasks_svgrepo_com,
            R.drawable.furniture_svgrepo_com,
            R.drawable.cat_4_svgrepo_com,
            R.drawable.flowers_flower_svgrepo_com,
            R.drawable.music_speaker_svgrepo_com,
            R.drawable.laptop_svgrepo_com,
            R.drawable.ic_baseline_phone_iphone_24,
            R.drawable.gaming_pad_01_svgrepo_com,
            R.drawable.music_svgrepo_com
        )
        val categoryLinks = arrayOf(
            "https://market.yandex.ru/catalog--aksessuary-i-oborudovanie/54454/list?cvredirect=3&hid=90461&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--snegouborochnye-lopaty-i-dvizhki-dlia-snega/72079/list?hid=15706546&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--sportivnaia-zashchita/26090051/list?hid=294675&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--kukhonnaia-posuda/44273790/list?allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--penaly-i-pismennye-prinadlezhnosti/17984806/list?allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--sredstva-dlia-umyvaniia/17437155/list?was_redir=1&hid=8476098&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--miagkie-igrushki/59699/list?hid=10682610&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--detektivy-trillery-uzhasy/19420490/list?hid=18540110&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--parfiumeriia/16677491/list?hid=15927546&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--muzhskaia-verkhniaia-odezhda/55681/list?hid=7812139&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--bytovaia-tekhnika/29003530/list?allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--chistiashchie-i-moiushchie-sredstva/21448852/list?hid=13333816&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--gotovye-komplekty-mebeli/18049653/list?hid=7286147&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--tovary-dlia-koshek/62806/list?allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--vse-tsvety/28617034/list?hid=91284&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--umnye-kolonki-yandex/39557770/list?hid=15553892&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--igrovye-noutbuki/18072622/list?hid=91013&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--smartfony/26893750/list?hid=91491&allowCollapsing=1&local-offers-first=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--igry-dlia-pristavok-i-pk/41813553/list?allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney",
            "https://market.yandex.ru/catalog--muzykalnye-diski-i-plastinki/38849931/list?hid=17725598&allowCollapsing=1&local-offers-first=0&pricefrom=0&priceto=$savedMoney&resale_goods=resale_resale"
        )

        for (i in categoryNames.indices){
            categoryList.add(Category(categoryNames[i],categoryImages[i], Uri.parse(categoryLinks[i])))
        }
    }

    private fun initRecycler(){
        val adapter = CategoryAdapter(categoryList, this)
        motivation_recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val linkURI = categoryList[position].getCategoryLink()
        val launchBrowser = Intent(Intent.ACTION_VIEW, linkURI)
        startActivity(launchBrowser)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MotivationFragment()
    }
}