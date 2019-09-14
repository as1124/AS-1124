var window = require('../../common/primeton_window.js');

var QQMapWX = require('../../common/qqmap-wx-jssdk.js');
var APP_REF = getApp();

Page({
    data: {
        indexAds: [
            'https://images.unsplash.com/photo-1551214012-84f95e060dee?w=640',
            'https://images.unsplash.com/photo-1551446591-142875a901a1?w=640',
            '/images/banner/banner3.jpeg',
            '/images/banner/banner4.jpg'
        ],
        gridRow: [{
            'rowKey': 'row1',
            'rowData': [{
                    'itemKey': 1,
                    'icon': '/images/banner/gird-company.png',
                    'text': '公司介绍'
                },
                {
                    'itemKey': 2,
                    'icon': '/images/banner/gird-contactus.png',
                    'text': '联系我们'
                }, {
                    'itemKey': 3,
                    'icon': '/images/banner/gird-vip.png',
                    'text': '会员中心'
                }
            ]
        }, {
            'rowKey': 'row2',
            'rowData': [{
                    'itemKey': 4,
                    'icon': '/images/banner/gird-recommend.png',
                    'text': '热门推荐'
                },
                {
                    'itemKey': 5,
                    'icon': '/images/banner/gird-ecar.png',
                    'text': '折叠电车'
                }, {
                    'itemKey': 6,
                    'icon': '/images/banner/gird-repairs.png',
                    'text': '电车配件'
                }
            ]
        }],
        productList: [{
                'previewIcon': 'https://upload-images.jianshu.io/upload_images/8550118-e6ad460ce55f642a.png',
                'shortName': '初级折叠电动车1',
                'price': '¥ 1299.00',
                'productid': 'aaa1'
            },
            {
                'previewIcon': 'https://upload-images.jianshu.io/upload_images/8550118-e6ad460ce55f642a.png',
                'shortName': '初级折叠电动车2',
                'price': '¥ 1299.00',
                'productid': 'aaa2'
            }, {
                'previewIcon': 'https://upload-images.jianshu.io/upload_images/8550118-e6ad460ce55f642a.png',
                'shortName': '初级折叠电动车3',
                'price': '¥ 1299.00',
                'productid': 'aaa3'
            }, {
                'previewIcon': 'https://upload-images.jianshu.io/upload_images/8550118-e6ad460ce55f642a.png',
                'shortName': '初级折叠电动车4',
                'price': '¥ 1299.00',
                'productid': 'aaa4'
            },
            {
                'previewIcon': 'https://upload-images.jianshu.io/upload_images/8550118-e6ad460ce55f642a.png',
                'shortName': '初级折叠电动车5',
                'price': '¥ 1299.00',
                'productid': 'aaa5'
            },
            {
                'previewIcon': 'https://upload-images.jianshu.io/upload_images/8550118-e6ad460ce55f642a.png',
                'shortName': '初级折叠电动车6',
                'price': '¥ 1299.00',
                'productid': 'aaa6'
            },
            {
                'previewIcon': 'https://upload-images.jianshu.io/upload_images/8550118-e6ad460ce55f642a.png',
                'shortName': '初级折叠电动车7',
                'price': '¥ 1299.00',
                'productid': 'aaa7'
            },
        ],
        detailImgs: [
            "https://upload-images.jianshu.io/upload_images/8550118-47eac7530b1d267c.jpeg",
            "https://upload-images.jianshu.io/upload_images/8550118-8da2c59edbe870e2.jpeg",
            "https://upload-images.jianshu.io/upload_images/8550118-f399a3289ce853e2.jpeg",
            "https://upload-images.jianshu.io/upload_images/8550118-e924ac8fe2ea3f69.jpeg"
        ]
    },
    onLoad: function(options) {
        pageload(options);
    },
    onReady: function() {},
    onShow: function() {
        pageload();
    },
    onHide: function() {},
    onUnload: function() {},
    onPullDownRefresh: function() {},
    onReachBottom: function() {},
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {},
    handleTapProxy: function(event) {
        if (!event.currentTarget.id) {
            return;
        }
        if (event.currentTarget.id == 'gridItem1') {
            wx.request({
				url: 'https://www.i-dadong.com/dadong/rest/user/state/2233',
                data: {},
                method: 'GET',
                header: {
                    'content-type': 'application/json'
                },
                success: (res) => {
                    console.log(res.data)
                },
                fail: (err) => {
                    wx.showToast({
                        "title": "网络请求失败"
                    });
                }
            });
        }
    },
    handleLongTapProxy: function(event) {},
    handleDataChangeProxy: function(event) {
        if (!event.target.id) {
            return;
        }
    }
});

function pageload(options) {}