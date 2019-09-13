var QQMapWX = require('../../common/qqmap-wx-jssdk.js');
const APP_REF = getApp();

Page({
    data: {
        userInfo: null,
        isLogin: false,
        orderButtons: [{
                orderStatus: 'unpay',
                buttonIcon: '/images/orders/order_unpay.png',
                buttonText: '待付款'
            },
            {
                orderStatus: 'unsend',
                buttonIcon: '/images/orders/order_unsigned.png',
                buttonText: '待发货'
            }, {
                orderStatus: 'unsigned',
                buttonIcon: '/images/orders/order_unsigned1.png',
                buttonText: '待签收'
            }, {
                orderStatus: 'done',
                buttonIcon: '/images/orders/order_done.png',
                buttonText: '已完成'
            }, {
                orderStatus: 'aftersale',
                buttonIcon: '/images/orders/order_aftersale.png',
                buttonText: '售后'
            }
        ],
        functionalBtns: [{
                btnKey: 'my_favorite',
                btnIcon: '/images/meActions/me_favorite.png',
                btnText: '我的收藏'
            }, {
                btnKey: 'my_ticket',
                btnIcon: '/images/meActions/me_ticket.png',
                btnText: '我的卡券'
            }, {
                btnKey: 'my_address',
                btnIcon: '/images/meActions/me_address.png',
                btnText: '我的地址'
            }, {
                btnKey: 'online_service',
                btnIcon: '/images/meActions/online_service.png',
                btnText: '在线客服'
            }, {
                btnKey: 'contact_us',
				btnIcon: '/images/meActions/contact_us.png',
                btnText: '联系我们'
            }, {
                btnKey: 'join_us',
				btnIcon: '/images/meActions/join_us.png',
                btnText: '成为分销商'
            }, {
                btnKey: 'help_center',
				btnIcon: '/images/meActions/help_center.png',
                btnText: '帮助中心'
            }, {
                btnKey: 'about_us',
				btnIcon: '/images/meActions/about_us.png',
                btnText: '关于我们'
            }
        ]
    },
    onLoad: function(options) {
        wx.checkSession({
            'success': () => {
                //session_key 未过期，并且在本生命周期一直有效
                console.log("登录状态正常");
                checkUserState();
            },
            'fail': (err) => {
                // session_key 已经失效，需要重新执行登录流程
                wx.login({
                    'success': (res) => {
                        if (res.code) {
                            console.log('登录成功==' + res.code);
                            checkUserState();
                        } else {
                            console.log('登录失败！' + res.errMsg)
                        }
                    },
                    'fail': (err) => {
                        console.log('登录失败！')
                    }
                });
            }
        });
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
        if (!event.target.id) {
            return;
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

function checkUserState() {
    if (APP_REF.globalData.userInfo) {
        this.setData({
            userInfo: APP_REF.globalData.userInfo
        });
        return;
    }
    // 获取用户信息
    wx.getSetting({
        success: res => {
            if (res.authSetting['scope.userInfo']) {
                // 已经授权，可以直接调用 getUserInfo 获取头像昵称
                doGetUserInfo();
            } else {
                // 先授权，再获取用户信息
                wx.authorize({
                    scope: 'scope.userInfo',
                    success: () => {
                        doGetUserInfo();
                    },
                    fail: (errcode, errMsg) => {
                        console.log("au======" + errcode)
                    }
                });
            }
        }
    });
}

function doGetUserInfo() {
    wx.getUserInfo({
        withCredentials: false,
        success: res => {
			APP_REF.globalData.userInfo = res.userInfo;
			var currentPage = getCurrentPages()[0];
			currentPage.setData({
                userInfo: res.userInfo
            });
            var userInfo = res.userInfo;
            var nickName = userInfo.nickName
            var avatarUrl = userInfo.avatarUrl
            var gender = userInfo.gender //性别 0：未知、1：男、2：女
            var province = userInfo.province
            var city = userInfo.city
            var country = userInfo.country

            // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
            // 所以此处加入 callback 以防止这种情况
            // if (this.userInfoReadyCallback) {
            //     this.userInfoReadyCallback(res)
            // }
        },
        fail: err => {
            wx.showToast({
                title: '获取用户信息失败',
            });
        }
    });
}