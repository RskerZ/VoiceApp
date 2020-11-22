package com.example.voiceko.chartFragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.voiceko.Controller.CharController
import com.example.voiceko.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class PipChartFragment() : Fragment() {

    private lateinit var totalText: TextView
    private lateinit var expandTypeList: ListView
    private lateinit var expandMessageImage: ImageView
    private lateinit var expandMessage : TextView
    private var controller = CharController.instance
    private val pieEntryList = ArrayList<PieEntry>()
    private val colors = ArrayList<Int>()
    private val cateNames = ArrayList<String>()
    private val cateAmounts = ArrayList<Int>()
    private var type= ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pie_chart, container, false)
        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        arguments?.takeIf { it.containsKey("type") }?.apply {
            this@PipChartFragment.type = getString("type").toString()
        }
        controller.loadEachCateAmountFromDB(type)


        totalText = view.findViewById(R.id.expand_Total)
        expandTypeList = view.findViewById(R.id.expand_typeList)
        expandMessageImage = view.findViewById(R.id.expand_message_image)
        expandMessageImage.setImageResource(R.mipmap.koko)
        expandMessage = view.findViewById(R.id.expand_message_body)
        //載入圖表
        var mChart = view.findViewById<PieChart>(R.id.expand_PieChart)
        createChart(mChart)

        var recordData = ArrayList<Map<String, Any>>()

        setRecordList(recordData)

        expandTypeList.setOnItemClickListener{ parent, view, position, id ->
            val element = expandTypeList.adapter.getItem(position) as HashMap<*, *> // The item that was clicked
        }
    }

    private fun formatDataToPieData(){
        val eachCateAmount = controller.getEachCateAmount()
        if (eachCateAmount.size == 0){
            setVoiceKoText("這個月沒有紀錄呦!!")
        }
        colors.clear()
        pieEntryList.clear()
        cateNames.clear()
        cateAmounts.clear()
        for (cate in eachCateAmount){
            val color = getRandomColor()
            colors.add(color)
            val cateamount = cate["amount"]!!.toFloat()
            val cateName = cate["cate"]
            val temp =  PieEntry(cateamount, cateName)
            pieEntryList.add(temp)
            cateNames.add(cateName!!)
            cateAmounts.add(cateamount.toInt())
        }
    }

    private fun getRandomColor():Int{
        val rnd = Random()
        return Color.argb(225, rnd.nextInt(180), rnd.nextInt(180), rnd.nextInt(180))
    }
    private fun createChart(mChart: PieChart){
        // 设置 pieChart 图表基本属性
        mChart.setUsePercentValues(false) //使用百分比显示
        mChart.description.isEnabled = false //设置pieChart图表的描述
        mChart.setBackgroundColor(Color.WHITE) //设置pieChart图表背景色
        mChart.setExtraOffsets(5f, 10f, 30f, 10f) //设置pieChart图表上下左右的偏移，类似于外边距
        mChart.dragDecelerationFrictionCoef = 0.65f //设置pieChart图表转动阻力摩擦系数[0,1]
        mChart.rotationAngle = 0f //设置pieChart图表起始角度
        mChart.isRotationEnabled = false //设置pieChart图表是否可以手动旋转
        mChart.isHighlightPerTapEnabled = true //设置piecahrt图表点击Item高亮是否可用
        mChart.animateY(1400, Easing.EaseInOutQuad) // 设置pieChart图表展示动画效果


// 设置 pieChart 图表Item文本属性
        mChart.setDrawEntryLabels(false) //设置pieChart是否只显示饼图上百分比不显示文字（true：下面属性才有效果）
        mChart.setEntryLabelColor(Color.BLACK) //设置pieChart图表文本字体颜色
        mChart.setEntryLabelTextSize(10f) //设置pieChart图表文本字体大小

// 设置 pieChart 内部圆环属性
        mChart.isDrawHoleEnabled = true //是否显示PieChart内部圆环(true:下面属性才有意义)
        mChart.holeRadius = 14f //设置PieChart内部圆的半径(这里设置28.0f)
        mChart.transparentCircleRadius = 20f //设置PieChart内部透明圆的半径(这里设置31.0f)
        mChart.setTransparentCircleColor(Color.GRAY) //设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
        mChart.setTransparentCircleAlpha(50) //设置PieChart内部透明圆与内部圆间距(31f-28f)透明度[0~255]数值越小越透明

        setData(mChart)

// 获取pieCahrt图列
        val l = mChart.legend
        l.isEnabled = true //是否启用图列（true：下面属性才有意义）
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.form = Legend.LegendForm.DEFAULT //设置图例的形状
        l.formSize = 10f //设置图例的大小
        l.formToTextSpace = 10f //设置每个图例实体中标签和形状之间的间距
        l.setDrawInside(false)
        l.isWordWrapEnabled = true //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
        l.xEntrySpace = 10f //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
        l.yEntrySpace = 8f //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
        l.xOffset = 10f
        l.yOffset = 80f //设置比例块Y轴偏移量
        l.textSize = 16f //设置图例标签文本的大小
        l.textColor = Color.parseColor("#000000") //设置图例标签文本的颜色

    }
    private fun setData(mChart: PieChart) {

        formatDataToPieData()

        //饼状图数据集 PieDataSet
        val pieDataSet = PieDataSet(pieEntryList, "")
        pieDataSet.sliceSpace = 3f //设置饼状Item之间的间隙
        pieDataSet.selectionShift = 10f //设置饼状Item被选中时变化的距离
        pieDataSet.colors = colors //为DataSet中的数据匹配上颜色集(饼图Item颜色)
        //最终数据 PieData
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true) //设置是否显示数据实体(百分比，true:以下属性才有意义)
        pieData.setValueTextColor(Color.BLACK) //设置所有DataSet内数据实体（百分比）的文本颜色
        pieData.setValueTextSize(12f) //设置所有DataSet内数据实体（百分比）的文本字体大小
        //pieData.setValueTypeface(mTfLight) //设置所有DataSet内数据实体（百分比）的文本字体样式
        pieData.setValueFormatter(PercentFormatter()) //设置所有DataSet内数据实体（百分比）的文本字体格式

        mChart.data = pieData
        mChart.highlightValues(null)
        mChart.invalidate() //将图表重绘以显示设置的属性和数据
    }
    //載入類別紀錄資料
    private fun setRecordList(recordData: ArrayList<Map<String, Any>>){
        //仔入的資料
        val typename = cateNames
        val amount = cateAmounts

        val totalAmount = cateAmounts.sum()
        totalText.text = "合計:${totalAmount}元"
        val percentage = ArrayList<String>()

        for(number in cateAmounts){
            val temp = (number/totalAmount.toFloat())*10000
            percentage.add("${temp.toInt() / 100.0F}%")
        }


        for (i in typename.indices){
            var map = HashMap<String, Any>()
            map["typename"] = typename[i]
            map["amount"] = amount[i]
            map["percentage"] = percentage[i]
            recordData.add(map)
        }

        var adapter = SimpleAdapter(
            activity, recordData, R.layout.piechart_typelist, arrayOf(
                "typename",
                "amount",
                "percentage"
            ), intArrayOf(
                R.id.pieChartList_typeName,
                R.id.pieChartList_amount,
                R.id.pieChartList_percentage
            )
        )
        expandTypeList.adapter = adapter

    }

    private fun setVoiceKoText(msg:String){
        expandMessage.text = msg
    }

    companion object{
        fun newInstance(type:String):PipChartFragment{
            val instance = PipChartFragment()
            instance.arguments = Bundle().apply { putString("type",type) }
            return instance
        }
    }

}