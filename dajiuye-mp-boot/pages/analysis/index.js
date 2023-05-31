import * as echarts from '../../components/ec-canvas/echarts';
Page({
    data: {
        ec: {
            onInit: initChart
        },
        ec2: {
            onInit: initChart2
        },
        ec3: {
            onInit: initChart3
        },
        ec4: {
            onInit: initChart4
        }
    },
    onLoad: function() {
        // let myComponnet=this.selectComponent('#mychart-dom');
        // console.log("myComponnet:"+myComponnet);
        // let ec={};
        // ec.onInit=initChart();
        // // console.log("myCharts:"+myCharts);
        // this.setData({
        //   ec:ec
        // })
    },
})

function initChart(canvas, width, height) {
    const chart = echarts.init(canvas, null, {
        width: width,
        height: height
    });
    canvas.setChart(chart);
    const option = getOption();
    chart.setOption(option);
    return chart;
}

function initChart2(canvas, width, height) {
    const chart = echarts.init(canvas, null, {
        width: width,
        height: height
    });
    canvas.setChart(chart);
    const option = getOption2();
    chart.setOption(option);
    return chart;
}

function initChart3(canvas, width, height) {
    const chart = echarts.init(canvas, null, {
        width: width,
        height: height
    });
    canvas.setChart(chart);
    const option = getOption3();
    chart.setOption(option);
    return chart;
}

function initChart4(canvas, width, height) {
    const chart = echarts.init(canvas, null, {
        width: width,
        height: height
    });
    canvas.setChart(chart);
    const option = getOption4();
    chart.setOption(option);
    return chart;
}

function getOption() {
    // 指定图表的配置项和数据
    var option = {
        // ----  标题 -----
        title: {
            text: '数据分析岗学历要求',
            textStyle: {
                // color: 'red'
            },
            // subtext: '副标题',
            subtextStyle: {
                color: 'blue'
            },
            padding: [20, 0, 0, 70] // 位置
        },
        // ---- legend ----
        legend: {
            type: 'plain', // 图列类型，默认为 'plain'
            top: '1%', // 图列相对容器的位置 top\bottom\left\right
            selected: {
                // '销量': true  // 图列选择，图形加载出来会显示选择的图列，默认为true
            },
            textStyle: { // 图列内容样式
                color: '#fff', // 字体颜色
                backgroundColor: 'black' // 字体背景色
            },
            tooltip: { // 图列提示框，默认不显示
                show: true,
                // color: 'red'
            },
            data: [ // 图列内容
                {
                    name: '销量',
                    // icon: 'circle',
                    textStyle: {
                        // color: 'red', // 单独设置某一个图列的颜色
                        backgroundColor: 'red' // 单独设置某一个图列的字体背景色
                    }
                }
            ]
        },
        // ---  提示框 ----
        tooltip: {
            // show: true, // 是否显示提示框，默认为true
            // trigger: 'item', // 数据项图形触发
            axisPointer: { // 指示样式
                type: 'shadow',
                axis: 'auto'
            },
            padding: 5,
            textStyle: { // 提示框内容的样式
                // color: '#fff'
            }
        },
        // ---- gird区域 ---
        gird: {
            show: false, // 是否显示直角坐标系网格
            top: 80, // 相对位置 top\bottom\left\right
            containLabel: false, // gird 区域是否包含坐标轴的刻度标签
            tooltip: {
                show: true,
                trigger: 'item', // 触发类型
                textStyle: {
                    color: '#666'
                }
            }
        },
        //  ------  X轴 ------
        xAxis: {
            show: true, // 是否显示
            position: 'bottom', // x轴的位置
            offset: 0, // x轴相对于默认位置的偏移
            type: 'category', // 轴类型， 默认为 'category'
            name: '学历', // 轴名称
            nameLocation: 'end', // 轴名称相对位置
            nameTextStyle: { // 坐标轴名称样式
                // color: 'green',
                padding: [8, 0, 0, -8]
                    // padding: [1, 0, 0, 0]
            },
            nameGap: 15, // 坐标轴名称与轴线之间的距离
            nameRotate: 0, // 坐标轴名字旋转
            axisLine: { // 坐标轴 轴线
                show: true, // 是否显示
                symbol: ['none', 'arrow'], // 是否显示轴线箭头
                symbolSize: [8, 8], // 箭头大小
                symbolOffset: [0, 7], // 箭头位置
                // ------   线 ---------
                lineStyle: {
                    // color: 'green',
                    width: 1,
                    type: 'solid'
                }
            },
            axisTick: { // 坐标轴 刻度
                show: true, // 是否显示
                inside: true, // 是否朝内
                length: 1, // 长度
                lineStyle: { // 默认取轴线的样式
                    // color: 'red',
                    width: 1,
                    type: 'solid'
                }
            },
            axisLabel: { // 坐标轴标签
                show: true, // 是否显示
                inside: false, // 是否朝内
                rotate: 40, // 旋转角度
                margin: 5, // 刻度标签与轴线之间的距离
                // color: 'red' // 默认取轴线的颜色 
            },
            splitLine: { // gird区域中的分割线
                show: false, // 是否显示
                lineStyle: {
                    // color: 'red',
                    // width: 1,
                    // type: 'solid'
                }
            },
            splitArea: { // 网格区域
                show: false // 是否显示，默认为false
            },
            data: ["本科及以上", "统招本科", "大专及以上", "学历不限", "硕士及以上", "中专中技及以上", "本科"]
        },
        //   ------   y轴  ----------
        yAxis: {
            show: true, // 是否显示
            position: 'left', // y轴位置
            offset: 0, // y轴相对于默认位置的偏移
            type: 'value', // 轴类型，默认为 ‘category’
            name: '人数', // 轴名称
            nameLocation: 'end', // 轴名称相对位置value
            nameTextStyle: { // 坐标轴名称样式
                // color: '#fff',
                padding: [5, 0, 0, 5] // 坐标轴名称相对位置
            },
            nameGap: 15, // 坐标轴名称与轴线之间的距离
            nameRotate: 270, // 坐标轴名字旋转

            axisLine: { // 坐标轴 轴线
                show: true, // 是否显示
                //  -----   箭头 -----
                symbol: ['none', 'arrow'], // 是否显示轴线箭头
                symbolSize: [8, 8], // 箭头大小
                symbolOffset: [0, 7], // 箭头位置

                // ----- 线 -------
                lineStyle: {
                    // color: 'blue',
                    width: 1,
                    type: 'solid'
                }
            },
            axisTick: { // 坐标轴的刻度
                show: true, // 是否显示
                inside: true, // 是否朝内
                length: 3, // 长度
                lineStyle: {
                    // color: 'red', // 默认取轴线的颜色
                    width: 1,
                    type: 'solid'
                }
            },
            axisLabel: { // 坐标轴的标签
                show: true, // 是否显示
                inside: false, // 是否朝内
                rotate: 0, // 旋转角度
                margin: 8, // 刻度标签与轴线之间的距离
                // color: 'red', // 默认轴线的颜色
            },
            splitLine: { // gird 区域中的分割线
                show: true, // 是否显示
                lineStyle: {
                    color: '#666',
                    width: 1,
                    type: 'dashed'
                }
            },
            splitArea: { // 网格区域
                show: false // 是否显示，默认为false
            }
        },
        //  -------   内容数据 -------
        series: [{
            // name: '销量', // 序列名称
            type: 'bar', // 类型
            // legendHoverLink: true, // 是否启用图列 hover 时的联动高亮
            label: { // 图形上的文本标签
                show: true,
                position: 'top', // 相对位置
                rotate: 0, // 旋转角度
                color: '#eee'
            },
            itemStyle: { // 图形的形状
                // color: 'blue',
                // barBorderRadius: [18, 18, 0, 0]
            },
            barWidth: 20, // 柱形的宽度
            barCategoryGap: '20%', // 柱形的间距
            data: [209, 95, 50, 20, 6, 1, 1]
        }]
    };
    return option;
}

function getOption2() {
    return {
        xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: [10, 20, 10, 80, 70, 10, 30],
            type: 'line'
        }]
    }
}

function getOption3() {
    const option = {
        title: {
            text: '数据分析岗薪酬情况',
            subtext: 'Fake Data',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        // legend: {
        //   orient: 'vertical',
        //   left: 'left',
        //   width:'1rpx'
        // },
        series: [{
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
                { value: 1048, name: '4k-13k' },
                { value: 735, name: '13k-22k' },
                { value: 580, name: '22k-31k' },
                { value: 484, name: '面议' },
                { value: 300, name: '31k-40k' },
                { value: 200, name: '40k-49k' },
                { value: 100, name: '85k及以上' },
                { value: 70, name: '58kk-67k' },
                { value: 30, name: '49k-58k' },
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    };
    return option;
}

function getOption4() {
    const option = {
        title: {
            text: '数据分析工作经验要求',
            textStyle: {
                // color: 'red'
            },
            // subtext: '副标题',
            subtextStyle: {
                color: 'blue'
            },
            padding: [20, 0, 0, 70] // 位置
        },
        dataset: {
            source: [
                ['score', 'amount', 'product'],
                [89.7, 1, '一年以下'],
                [50.1, 5, '十年以上'],
                [50.1, 44, '经验不限'],
                [74.4, 55, '5-10年'],
                [57.1, 124, '1-3年'],
                [89.3, 154, '3-5年']
            ]
        },
        grid: { 
          containLabel: true, 
          left:'5%',
          right:'20%'
        },
        xAxis: {
            name: 'amount'
        },
        yAxis: {
            type: 'category',
            axisLabel: { // 坐标轴标签
                interval: 0,
                //     show: true, // 是否显示
                //     inside: false, // 是否朝内
                //     rotate: 40, // 旋转角度
                //     margin: 5, // 刻度标签与轴线之间的距离
                //     // color: 'red' // 默认取轴线的颜色 
            },
        },
        visualMap: {
            orient: 'horizontal',
            // left: 'start',
            // min: 10,
            // max: 100,
            text: ['High Score', 'Low Score'],
            // Map the score column to color
            dimension: 0,
            inRange: {
                color: ['#65B581', '#FFCE34', '#FD665F']
            }
        },
        series: [{
            type: 'bar',
            encode: {
                // Map the "amount" column to X axis.
                x: 'amount',
                // Map the "product" column to Y axis
                y: 'product'
            }
        }]
    };
    return option;
}