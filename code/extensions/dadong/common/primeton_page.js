// 为兼容多端，通过公共js引用判别程序运行环境

var page = {
	addEvent : function(name, fn) {
		var current = getCurrentPages[0];
		console.log("addevent" + name);
	},
	goTo : function(path, param, isDestory) {
		wx.redirectTo({url:'/pages/success/success?postData=huangjw'});
	}
}

function toast(content) {
	wx.showToast({
		title : content,
		duration : 1500,
		icon : "none"
	});
}

module.exports = {
	page : page,
	toast : toast
};