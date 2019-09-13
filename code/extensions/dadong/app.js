//app.js

	// "usingComponents": {
	// 	"mp-badge": "/common/weui/badge/badge",
	// 	"mp-cell": "/common/weui/cell/cell",
	// 	"mp-cells": "/common/weui/cells/cells",
	// 	"mp-checkbox": "/common/weui/checkbox/checkbox",
	// 	"mp-checkbox-group": "/common/weui/checkbox-group/checkbox-group",
	// 	"mp-gallery": "/common/weui/gallery/gallery",
	// 	"mp-loading": "/common/weui/loading/loading",
	// 	"mp-searchbar": "/common/weui/searchbar/searchbar",
	// 	"mp-slideview": "/common/weui/slideview/slideview"
	// }, 

var window = require('./common/primeton_window.js');
App({
    onLaunch: function(options) {
        //     wx.login({
        //         success: res => {
        //             // 发送 res.code 到后台换取 openId, sessionKey, unionId
        //         }
        //     });
        //     // 获取用户信息
        //     wx.getSetting({
        //         success: res => {
        //             if (res.authSetting['scope.userInfo']) {
        //                 // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
        //                 wx.getUserInfo({
        //                     success: res => {
        //                         // 可以将 res 发送给后台解码出 unionId
        //                         this.globalData.userInfo = res.userInfo;
        //                         // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
        //                         // 所以此处加入 callback 以防止这种情况
        //                         if (this.userInfoReadyCallback) {
        //                             this.userInfoReadyCallback(res);
        //                         }
        //                     }
        //                 });
        //             }
        //         }
        //     });
    },
    onShow: function(options) {},
    onHide: function() {},
    globalData: {
        userInfo: null
    }
});