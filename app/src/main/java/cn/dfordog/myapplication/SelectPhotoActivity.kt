package cn.dfordog.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.dfordog.myapplication.adapter.GridImageAdapter
import cn.dfordog.myapplication.utils.SelectPhotoUtil
import com.luck.picture.lib.config.PictureMimeType


class SelectPhotoActivity : AppCompatActivity() {

    private lateinit var  mAdapter: GridImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_photo)

        mAdapter = GridImageAdapter(this, onAddPicClickListener)
        if (savedInstanceState?.getParcelableArrayList<Parcelable>("selectorList") != null) {
            mAdapter.setList(savedInstanceState.getParcelableArrayList("selectorList"))
        }

        val mRecyclerView = findViewById<RecyclerView>(R.id.selectPhoto)
        val manager = FullyGridLayoutManager(
            this,
            4, GridLayoutManager.VERTICAL, false
        )
        mRecyclerView.layoutManager = manager
        mRecyclerView.adapter = mAdapter


    }
//    private val chooseMode = PictureMimeType.ofAll() //全部
    private val chooseMode = PictureMimeType.ofImage() //图片
//    private val chooseMode = PictureMimeType.ofVideo() //视频
//    private val chooseMode = PictureMimeType.ofAudio() //音频
    private val onAddPicClickListener: GridImageAdapter.onAddPicClickListener = GridImageAdapter.onAddPicClickListener {

        SelectPhotoUtil.selectPhoto(this,chooseMode,mAdapter)

    }





}