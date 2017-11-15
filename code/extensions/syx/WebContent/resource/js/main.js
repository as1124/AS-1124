var requirejs;
var require;
var define;
(function(ba) {
    function G(a) {
        return "[object Function]" === K.call(a)
    }
    function H(a) {
        return "[object Array]" === K.call(a)
    }
    function v(a, b) {
        if (a) {
            var c;
            for (c = 0; c < a.length && (!a[c] || !b(a[c], c, a)); c += 1);
        }
    }
    function T(a, b) {
        if (a) {
            var c;
            for (c = a.length - 1; - 1 < c && (!a[c] || !b(a[c], c, a)); c -= 1);
        }
    }
    function t(a, b) {
        return fa.call(a, b)
    }
    function m(a, b) {
        return t(a, b) && a[b]
    }
    function B(a, b) {
        for (var c in a) if (t(a, c) && b(a[c], c)) break
    }
    function U(a, b, c, d) {
        b && B(b,
        function(b, e) {
            if (c || !t(a, e)) d && "object" === typeof b && b && !H(b) && !G(b) && !(b instanceof RegExp) ? (a[e] || (a[e] = {}), U(a[e], b, c, d)) : a[e] = b
        });
        return a
    }
    function u(a, b) {
        return function() {
            return b.apply(a, arguments)
        }
    }
    function ca(a) {
        throw a
    }
    function da(a) {
        if (!a) return a;
        var b = ba;
        v(a.split("."),
        function(a) {
            b = b[a]
        });
        return b
    }
    function C(a, b, c, d) {
        b = Error(b + "\nhttp://requirejs.org/docs/errors.html#" + a);
        b.requireType = a;
        b.requireModules = d;
        c && (b.originalError = c);
        return b
    }
    function ga(a) {
        function b(a, b, c) {
            var d, e, f, g, h, i, j, k, b = b && b.split("/"),
            l = D.map,
            n = l && l["*"];
            if (a) {
                a = a.split("/");
                e = a.length - 1;
                D.nodeIdCompat && Q.test(a[e]) && (a[e] = a[e].replace(Q, ""));
                "." === a[0].charAt(0) && b && (e = b.slice(0, b.length - 1), a = e.concat(a));
                e = a;
                for (f = 0; f < e.length; f++) if (g = e[f], "." === g) e.splice(f, 1),
                f -= 1;
                else if (".." === g && !(0 === f || 1 == f && ".." === e[2] || ".." === e[f - 1]) && 0 < f) e.splice(f - 1, 2),
                f -= 2;
                a = a.join("/")
            }
            if (c && l && (b || n)) {
                e = a.split("/");
                f = e.length;
                a: for (; 0 < f; f -= 1) {
                    h = e.slice(0, f).join("/");
                    if (b) for (g = b.length; 0 < g; g -= 1) if (c = m(l, b.slice(0, g).join("/"))) if (c = m(c, h)) {
                        d = c;
                        i = f;
                        break a
                    } ! j && (n && m(n, h)) && (j = m(n, h), k = f)
                } ! d && j && (d = j, i = k);
                d && (e.splice(0, i, d), a = e.join("/"))
            }
            return (d = m(D.pkgs, a)) ? d: a
        }
        function c(a) {
            z && v(document.getElementsByTagName("script"),
            function(b) {
                if (b.getAttribute("data-requiremodule") === a && b.getAttribute("data-requirecontext") === x.contextName) return b.parentNode.removeChild(b),
                !0
            })
        }
        function d(a) {
            var b = m(D.paths, a);
            if (b && H(b) && 1 < b.length) return b.shift(),
            x.require.undef(a),
            x.makeRequire(null, {
                skipMap: !0
            })([a]),
            !0
        }
        function e(a) {
            var b, c = a ? a.indexOf("!") : -1; - 1 < c && (b = a.substring(0, c), a = a.substring(c + 1, a.length));
            return [b, a]
        }
        function f(a, c, d, f) {
            var g, h, i = null,
            j = c ? c.name: null,
            k = a,
            l = !0,
            n = "";
            a || (l = !1, a = "_@r" + (P += 1));
            a = e(a);
            i = a[0];
            a = a[1];
            i && (i = b(i, j, f), h = m(K, i));
            a && (i ? n = h && h.normalize ? h.normalize(a,
            function(a) {
                return b(a, j, f)
            }) : -1 === a.indexOf("!") ? b(a, j, f) : a: (n = b(a, j, f), a = e(n), i = a[0], n = a[1], d = !0, g = x.nameToUrl(n)));
            d = i && !h && !d ? "_unnormalized" + (S += 1) : "";
            return {
                prefix: i,
                name: n,
                parentMap: c,
                unnormalized: !!d,
                url: g,
                originalName: k,
                isDefine: l,
                id: (i ? i + "!" + n: n) + d
            }
        }
        function h(a) {
            var b = a.id,
            c = m(E, b);
            c || (c = E[b] = new x.Module(a));
            return c
        }
        function i(a, b, c) {
            var d = a.id,
            e = m(E, d);
            if (t(K, d) && (!e || e.defineEmitComplete))"defined" === b && c(K[d]);
            else if (e = h(a), e.error && "error" === b) c(e.error);
            else e.on(b, c)
        }
        function j(a, b) {
            var c = a.requireModules,
            d = !1;
            if (b) b(a);
            else if (v(c,
            function(b) {
                if (b = m(E, b)) b.error = a,
                b.events.error && (d = !0, b.emit("error", a))
            }), !d) g.onError(a)
        }
        function k() {
            R.length && (ha.apply(J, [J.length, 0].concat(R)), R = [])
        }
        function l(a) {
            delete E[a];
            delete F[a]
        }
        function n(a, b, c) {
            var d = a.map.id;
            a.error ? a.emit("error", a.error) : (b[d] = !0, v(a.depMaps,
            function(d, e) {
                var f = d.id,
                g = m(E, f);
                g && (!a.depMatched[e] && !c[f]) && (m(b, f) ? (a.defineDep(e, K[f]), a.check()) : n(g, b, c))
            }), c[d] = !0)
        }
        function o() {
            var a, b, e = (a = 1e3 * D.waitSeconds) && x.startTime + a < (new Date).getTime(),
            f = [],
            g = [],
            h = !1,
            i = !0;
            if (!s) {
                s = !0;
                B(F,
                function(a) {
                    var j = a.map,
                    k = j.id;
                    if (a.enabled && (j.isDefine || g.push(a), !a.error)) if (!a.inited && e) d(k) ? h = b = !0 : (f.push(k), c(k));
                    else if (!a.inited && (a.fetched && j.isDefine) && (h = !0, !j.prefix)) return i = !1
                });
                if (e && f.length) return a = C("timeout", "Load timeout for modules: " + f, null, f),
                a.contextName = x.contextName,
                j(a);
                i && v(g,
                function(a) {
                    n(a, {},
                    {})
                });
                if ((!e || b) && h) if ((z || ea) && !A) A = setTimeout(function() {
                    A = 0;
                    o()
                },
                50);
                s = !1
            }
        }
        function p(a) {
            t(K, a[0]) || h(f(a[0], null, !0)).init(a[1], a[2])
        }
        function q(a) {
            var a = a.currentTarget || a.srcElement,
            b = x.onScriptLoad;
            a.detachEvent && !Y ? a.detachEvent("onreadystatechange", b) : a.removeEventListener("load", b, !1);
            b = x.onScriptError; (!a.detachEvent || Y) && a.removeEventListener("error", b, !1);
            return {
                node: a,
                id: a && a.getAttribute("data-requiremodule")
            }
        }
        function r() {
            var a;
            for (k(); J.length;) {
                a = J.shift();
                if (null === a[0]) return j(C("mismatch", "Mismatched anonymous define() module: " + a[a.length - 1]));
                p(a)
            }
        }
        var s, w, x, y, A, D = {
            waitSeconds: 7,
            baseUrl: "./",
            paths: {},
            bundles: {},
            pkgs: {},
            shim: {},
            config: {}
        },
        E = {},
        F = {},
        I = {},
        J = [],
        K = {},
        L = {},
        O = {},
        P = 1,
        S = 1;
        y = {
            require: function(a) {
                return a.require ? a.require: a.require = x.makeRequire(a.map)
            },
            exports: function(a) {
                a.usingExports = !0;
                if (a.map.isDefine) return a.exports ? K[a.map.id] = a.exports: a.exports = K[a.map.id] = {}
            },
            module: function(a) {
                return a.module ? a.module: a.module = {
                    id: a.map.id,
                    uri: a.map.url,
                    config: function() {
                        return m(D.config, a.map.id) || {}
                    },
                    exports: a.exports || (a.exports = {})
                }
            }
        };
        w = function(a) {
            this.events = m(I, a.id) || {};
            this.map = a;
            this.shim = m(D.shim, a.id);
            this.depExports = [];
            this.depMaps = [];
            this.depMatched = [];
            this.pluginMaps = {};
            this.depCount = 0
        };
        w.prototype = {
            init: function(a, b, c, d) {
                d = d || {};
                if (!this.inited) {
                    this.factory = b;
                    if (c) this.on("error", c);
                    else this.events.error && (c = u(this,
                    function(a) {
                        this.emit("error", a)
                    }));
                    this.depMaps = a && a.slice(0);
                    this.errback = c;
                    this.inited = !0;
                    this.ignore = d.ignore;
                    d.enabled || this.enabled ? this.enable() : this.check()
                }
            },
            defineDep: function(a, b) {
                this.depMatched[a] || (this.depMatched[a] = !0, this.depCount -= 1, this.depExports[a] = b)
            },
            fetch: function() {
                if (!this.fetched) {
                    this.fetched = !0;
                    x.startTime = (new Date).getTime();
                    var a = this.map;
                    if (this.shim) x.makeRequire(this.map, {
                        enableBuildCallback: !0
                    })(this.shim.deps || [], u(this,
                    function() {
                        return a.prefix ? this.callPlugin() : this.load()
                    }));
                    else return a.prefix ? this.callPlugin() : this.load()
                }
            },
            load: function() {
                var a = this.map.url;
                L[a] || (L[a] = !0, x.load(this.map.id, a))
            },
            check: function() {
                if (this.enabled && !this.enabling) {
                    var a, b, c = this.map.id;
                    b = this.depExports;
                    var d = this.exports,
                    e = this.factory;
                    if (this.inited) if (this.error) this.emit("error", this.error);
                    else {
                        if (!this.defining) {
                            this.defining = !0;
                            if (1 > this.depCount && !this.defined) {
                                if (G(e)) {
                                    if (this.events.error && this.map.isDefine || g.onError !== ca) try {
                                        d = x.execCb(c, e, b, d)
                                    } catch(f) {
                                        a = f
                                    } else d = x.execCb(c, e, b, d);
                                    this.map.isDefine && void 0 === d && ((b = this.module) ? d = b.exports: this.usingExports && (d = this.exports));
                                    if (a) return a.requireMap = this.map,
                                    a.requireModules = this.map.isDefine ? [this.map.id] : null,
                                    a.requireType = this.map.isDefine ? "define": "require",
                                    j(this.error = a)
                                } else d = e;
                                this.exports = d;
                                if (this.map.isDefine && !this.ignore && (K[c] = d, g.onResourceLoad)) g.onResourceLoad(x, this.map, this.depMaps);
                                l(c);
                                this.defined = !0
                            }
                            this.defining = !1;
                            this.defined && !this.defineEmitted && (this.defineEmitted = !0, this.emit("defined", this.exports), this.defineEmitComplete = !0)
                        }
                    } else this.fetch()
                }
            },
            callPlugin: function() {
                var a = this.map,
                c = a.id,
                d = f(a.prefix);
                this.depMaps.push(d);
                i(d, "defined", u(this,
                function(d) {
                    var e, k;
                    k = m(O, this.map.id);
                    var n = this.map.name,
                    o = this.map.parentMap ? this.map.parentMap.name: null,
                    p = x.makeRequire(a.parentMap, {
                        enableBuildCallback: !0
                    });
                    if (this.map.unnormalized) {
                        if (d.normalize && (n = d.normalize(n,
                        function(a) {
                            return b(a, o, !0)
                        }) || ""), d = f(a.prefix + "!" + n, this.map.parentMap), i(d, "defined", u(this,
                        function(a) {
                            this.init([],
                            function() {
                                return a
                            },
                            null, {
                                enabled: !0,
                                ignore: !0
                            })
                        })), k = m(E, d.id)) {
                            this.depMaps.push(d);
                            if (this.events.error) k.on("error", u(this,
                            function(a) {
                                this.emit("error", a)
                            }));
                            k.enable()
                        }
                    } else k ? (this.map.url = x.nameToUrl(k), this.load()) : (e = u(this,
                    function(a) {
                        this.init([],
                        function() {
                            return a
                        },
                        null, {
                            enabled: !0
                        })
                    }), e.error = u(this,
                    function(a) {
                        this.inited = !0;
                        this.error = a;
                        a.requireModules = [c];
                        B(E,
                        function(a) {
                            0 === a.map.id.indexOf(c + "_unnormalized") && l(a.map.id)
                        });
                        j(a)
                    }), e.fromText = u(this,
                    function(b, d) {
                        var i = a.name,
                        k = f(i),
                        l = M;
                        d && (b = d);
                        l && (M = !1);
                        h(k);
                        t(D.config, c) && (D.config[i] = D.config[c]);
                        try {
                            g.exec(b)
                        } catch(m) {
                            return j(C("fromtexteval", "fromText eval for " + c + " failed: " + m, m, [c]))
                        }
                        l && (M = !0);
                        this.depMaps.push(k);
                        x.completeLoad(i);
                        p([i], e)
                    }), d.load(a.name, p, e, D))
                }));
                x.enable(d, this);
                this.pluginMaps[d.id] = d
            },
            enable: function() {
                F[this.map.id] = this;
                this.enabling = this.enabled = !0;
                v(this.depMaps, u(this,
                function(a, b) {
                    var c, d;
                    if ("string" === typeof a) {
                        a = f(a, this.map.isDefine ? this.map: this.map.parentMap, !1, !this.skipMap);
                        this.depMaps[b] = a;
                        if (c = m(y, a.id)) {
                            this.depExports[b] = c(this);
                            return
                        }
                        this.depCount += 1;
                        i(a, "defined", u(this,
                        function(a) {
                            this.defineDep(b, a);
                            this.check()
                        }));
                        this.errback ? i(a, "error", u(this, this.errback)) : this.events.error && i(a, "error", u(this,
                        function(a) {
                            this.emit("error", a)
                        }))
                    }
                    c = a.id;
                    d = E[c]; ! t(y, c) && (d && !d.enabled) && x.enable(a, this)
                }));
                B(this.pluginMaps, u(this,
                function(a) {
                    var b = m(E, a.id);
                    b && !b.enabled && x.enable(a, this)
                }));
                this.enabling = !1;
                this.check()
            },
            on: function(a, b) {
                var c = this.events[a];
                c || (c = this.events[a] = []);
                c.push(b)
            },
            emit: function(a, b) {
                v(this.events[a],
                function(a) {
                    a(b)
                });
                "error" === a && delete this.events[a]
            }
        };
        x = {
            config: D,
            contextName: a,
            registry: E,
            defined: K,
            urlFetched: L,
            defQueue: J,
            Module: w,
            makeModuleMap: f,
            nextTick: g.nextTick,
            onError: j,
            configure: function(a) {
                a.baseUrl && "/" !== a.baseUrl.charAt(a.baseUrl.length - 1) && (a.baseUrl += "/");
                var b = D.shim,
                c = {
                    paths: !0,
                    bundles: !0,
                    config: !0,
                    map: !0
                };
                B(a,
                function(a, b) {
                    c[b] ? (D[b] || (D[b] = {}), U(D[b], a, !0, !0)) : D[b] = a
                });
                a.bundles && B(a.bundles,
                function(a, b) {
                    v(a,
                    function(a) {
                        a !== b && (O[a] = b)
                    })
                });
                a.shim && (B(a.shim,
                function(a, c) {
                    H(a) && (a = {
                        deps: a
                    });
                    if ((a.exports || a.init) && !a.exportsFn) a.exportsFn = x.makeShimExports(a);
                    b[c] = a
                }), D.shim = b);
                a.packages && v(a.packages,
                function(a) {
                    var b, a = "string" === typeof a ? {
                        name: a
                    }: a;
                    b = a.name;
                    a.location && (D.paths[b] = a.location);
                    D.pkgs[b] = a.name + "/" + (a.main || "main").replace(ia, "").replace(Q, "")
                });
                B(E,
                function(a, b) { ! a.inited && !a.map.unnormalized && (a.map = f(b))
                });
                if (a.deps || a.callback) x.require(a.deps || [], a.callback)
            },
            makeShimExports: function(a) {
                return function() {
                    var b;
                    a.init && (b = a.init.apply(ba, arguments));
                    return b || a.exports && da(a.exports)
                }
            },
            makeRequire: function(d, e) {
                function i(b, c, k) {
                    var l, m;
                    e.enableBuildCallback && (c && G(c)) && (c.__requireJsBuild = !0);
                    if ("string" === typeof b) {
                        if (G(c)) return j(C("requireargs", "Invalid require call"), k);
                        if (d && t(y, b)) return y[b](E[d.id]);
                        if (g.get) return g.get(x, b, d, i);
                        l = f(b, d, !1, !0);
                        l = l.id;
                        return ! t(K, l) ? j(C("notloaded", 'Module name "' + l + '" has not been loaded yet for context: ' + a + (d ? "": ". Use require([])"))) : K[l]
                    }
                    r();
                    x.nextTick(function() {
                        r();
                        m = h(f(null, d));
                        m.skipMap = e.skipMap;
                        m.init(b, c, k, {
                            enabled: !0
                        });
                        o()
                    });
                    return i
                }
                e = e || {};
                U(i, {
                    isBrowser: z,
                    toUrl: function(a) {
                        var c, e = a.lastIndexOf("."),
                        f = a.split("/")[0];
                        if ( - 1 !== e && (!("." === f || ".." === f) || 1 < e)) c = a.substring(e, a.length),
                        a = a.substring(0, e);
                        return x.nameToUrl(b(a, d && d.id, !0), c, !0)
                    },
                    defined: function(a) {
                        return t(K, f(a, d, !1, !0).id)
                    },
                    specified: function(a) {
                        a = f(a, d, !1, !0).id;
                        return t(K, a) || t(E, a)
                    }
                });
                d || (i.undef = function(a) {
                    k();
                    var b = f(a, d, !0),
                    e = m(E, a);
                    c(a);
                    delete K[a];
                    delete L[b.url];
                    delete I[a];
                    T(J,
                    function(b, c) {
                        b[0] === a && J.splice(c, 1)
                    });
                    e && (e.events.defined && (I[a] = e.events), l(a))
                });
                return i
            },
            enable: function(a) {
                m(E, a.id) && h(a).enable()
            },
            completeLoad: function(a) {
                var b, c, e = m(D.shim, a) || {},
                f = e.exports;
                for (k(); J.length;) {
                    c = J.shift();
                    if (null === c[0]) {
                        c[0] = a;
                        if (b) break;
                        b = !0
                    } else c[0] === a && (b = !0);
                    p(c)
                }
                c = m(E, a);
                if (!b && !t(K, a) && c && !c.inited) {
                    if (D.enforceDefine && (!f || !da(f))) return d(a) ? void 0 : j(C("nodefine", "No define call for " + a, null, [a]));
                    p([a, e.deps || [], e.exportsFn])
                }
                o()
            },
            nameToUrl: function(a, b, c) {
                var d, e, f; (d = m(D.pkgs, a)) && (a = d);
                if (d = m(O, a)) return x.nameToUrl(d, b, c);
                if (g.jsExtRegExp.test(a)) d = a + (b || "");
                else {
                    d = D.paths;
                    a = a.split("/");
                    for (e = a.length; 0 < e; e -= 1) if (f = a.slice(0, e).join("/"), f = m(d, f)) {
                        H(f) && (f = f[0]);
                        a.splice(0, e, f);
                        break
                    }
                    d = a.join("/");
                    d += b || (/^data\:|\?/.test(d) || c ? "": ".js");
                    d = ("/" === d.charAt(0) || d.match(/^[\w\+\.\-]+:/) ? "": D.baseUrl) + d
                }
                return D.urlArgs ? d + (( - 1 === d.indexOf("?") ? "?": "&") + D.urlArgs) : d
            },
            load: function(a, b) {
                g.load(x, a, b)
            },
            execCb: function(a, b, c, d) {
                return b.apply(d, c)
            },
            onScriptLoad: function(a) {
                if ("load" === a.type || ja.test((a.currentTarget || a.srcElement).readyState)) N = null,
                a = q(a),
                x.completeLoad(a.id)
            },
            onScriptError: function(a) {
                var b = q(a);
                if (!d(b.id)) return j(C("scripterror", "Script error for: " + b.id, a, [b.id]))
            }
        };
        x.require = x.makeRequire();
        return x
    }
    var g, x, y, D, I, E, N, J, s, O, ka = /(\/\*([\s\S]*?)\*\/|([^:]|^)\/\/(.*)$)/gm,
    la = /[^.]\s*require\s*\(\s*["']([^'"\s]+)["']\s*\)/g,
    Q = /\.js$/,
    ia = /^\.\//;
    x = Object.prototype;
    var K = x.toString,
    fa = x.hasOwnProperty,
    ha = Array.prototype.splice,
    z = !!("undefined" !== typeof window && "undefined" !== typeof navigator && window.document),
    ea = !z && "undefined" !== typeof importScripts,
    ja = z && "PLAYSTATION 3" === navigator.platform ? /^complete$/: /^(complete|loaded)$/,
    Y = "undefined" !== typeof opera && "[object Opera]" === opera.toString(),
    F = {},
    q = {},
    R = [],
    M = !1;
    if ("undefined" === typeof define) {
        if ("undefined" !== typeof requirejs) {
            if (G(requirejs)) return;
            q = requirejs;
            requirejs = void 0
        }
        "undefined" !== typeof require && !G(require) && (q = require, require = void 0);
        g = requirejs = function(a, b, c, d) {
            var e, f = "_"; ! H(a) && "string" !== typeof a && (e = a, H(b) ? (a = b, b = c, c = d) : a = []);
            e && e.context && (f = e.context); (d = m(F, f)) || (d = F[f] = g.s.newContext(f));
            e && d.configure(e);
            return d.require(a, b, c)
        };
        g.config = function(a) {
            return g(a)
        };
        g.nextTick = "undefined" !== typeof setTimeout ?
        function(a) {
            setTimeout(a, 4)
        }: function(a) {
            a()
        };
        require || (require = g);
        g.version = "2.1.16";
        g.jsExtRegExp = /^\/|:|\?|\.js$/;
        g.isBrowser = z;
        x = g.s = {
            contexts: F,
            newContext: ga
        };
        g({});
        v(["toUrl", "undef", "defined", "specified"],
        function(a) {
            g[a] = function() {
                var b = F._;
                return b.require[a].apply(b, arguments)
            }
        });
        if (z && (y = x.head = document.getElementsByTagName("head")[0], D = document.getElementsByTagName("base")[0])) y = x.head = D.parentNode;
        g.onError = ca;
        g.createNode = function(a) {
            var b = a.xhtml ? document.createElementNS("http://www.w3.org/1999/xhtml", "html:script") : document.createElement("script");
            b.type = a.scriptType || "text/javascript";
            b.charset = "utf-8";
            b.async = !0;
            return b
        };
        g.load = function(a, b, c) {
            var d = a && a.config || {};
            if (z) return d = g.createNode(d, b, c),
            d.setAttribute("data-requirecontext", a.contextName),
            d.setAttribute("data-requiremodule", b),
            d.attachEvent && !(d.attachEvent.toString && 0 > d.attachEvent.toString().indexOf("[native code")) && !Y ? (M = !0, d.attachEvent("onreadystatechange", a.onScriptLoad)) : (d.addEventListener("load", a.onScriptLoad, !1), d.addEventListener("error", a.onScriptError, !1)),
            d.src = c,
            J = d,
            D ? y.insertBefore(d, D) : y.appendChild(d),
            J = null,
            d;
            if (ea) try {
                importScripts(c),
                a.completeLoad(b)
            } catch(e) {
                a.onError(C("importscripts", "importScripts failed for " + b + " at " + c, e, [b]))
            }
        };
        z && !q.skipDataMain && T(document.getElementsByTagName("script"),
        function(a) {
            y || (y = a.parentNode);
            if (I = a.getAttribute("data-main")) return s = I,
            q.baseUrl || (E = s.split("/"), s = E.pop(), O = E.length ? E.join("/") + "/": "./", q.baseUrl = O),
            s = s.replace(Q, ""),
            g.jsExtRegExp.test(s) && (s = I),
            q.deps = q.deps ? q.deps.concat(s) : [s],
            !0
        });
        define = function(a, b, c) {
            var d, e;
            "string" !== typeof a && (c = b, b = a, a = null);
            H(b) || (c = b, b = null); ! b && G(c) && (b = [], c.length && (c.toString().replace(ka, "").replace(la,
            function(a, c) {
                b.push(c)
            }), b = (1 === c.length ? ["require"] : ["require", "exports", "module"]).concat(b)));
            if (M) {
                if (! (d = J)) N && "interactive" === N.readyState || T(document.getElementsByTagName("script"),
                function(a) {
                    if ("interactive" === a.readyState) return N = a
                }),
                d = N;
                d && (a || (a = d.getAttribute("data-requiremodule")), e = F[d.getAttribute("data-requirecontext")])
            } (e ? e.defQueue: R).push([a, b, c])
        };
        define.amd = {
            jQuery: !0
        };
        g.exec = function(b) {
            return eval(b)
        };
        g(q)
    }
})(this);
define("requireLib",
function() {}); (function(a, b) {
    if (typeof module === "object" && typeof module.exports === "object") {
        module.exports = a.document ? b(a, true) : function(a) {
            if (!a.document) {
                throw new Error("jQuery requires a window with a document")
            }
            return b(a)
        }
    } else {
        b(a)
    }
})(typeof window !== "undefined" ? window: this,
function(a, b) {
    var c = [];
    var d = c.slice;
    var e = c.concat;
    var f = c.push;
    var g = c.indexOf;
    var h = {};
    var i = h.toString;
    var j = h.hasOwnProperty;
    var k = {};
    var l = "1.11.2",
    m = function(a, b) {
        return new m.fn.init(a, b)
    },
    n = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g,
    o = /^-ms-/,
    p = /-([\da-z])/gi,
    q = function(a, b) {
        return b.toUpperCase()
    };
    m.fn = m.prototype = {
        jquery: l,
        constructor: m,
        selector: "",
        length: 0,
        toArray: function() {
            return d.call(this)
        },
        get: function(a) {
            return a != null ? a < 0 ? this[a + this.length] : this[a] : d.call(this)
        },
        pushStack: function(a) {
            var b = m.merge(this.constructor(), a);
            b.prevObject = this;
            b.context = this.context;
            return b
        },
        each: function(a, b) {
            return m.each(this, a, b)
        },
        map: function(a) {
            return this.pushStack(m.map(this,
            function(b, c) {
                return a.call(b, c, b)
            }))
        },
        slice: function() {
            return this.pushStack(d.apply(this, arguments))
        },
        first: function() {
            return this.eq(0)
        },
        last: function() {
            return this.eq( - 1)
        },
        eq: function(a) {
            var b = this.length,
            c = +a + (a < 0 ? b: 0);
            return this.pushStack(c >= 0 && c < b ? [this[c]] : [])
        },
        end: function() {
            return this.prevObject || this.constructor(null)
        },
        push: f,
        sort: c.sort,
        splice: c.splice
    };
    m.extend = m.fn.extend = function() {
        var a, b, c, d, e, f, g = arguments[0] || {},
        h = 1,
        i = arguments.length,
        j = false;
        if (typeof g === "boolean") {
            j = g;
            g = arguments[h] || {};
            h++
        }
        if (typeof g !== "object" && !m.isFunction(g)) {
            g = {}
        }
        if (h === i) {
            g = this;
            h--
        }
        for (; h < i; h++) {
            if ((e = arguments[h]) != null) {
                for (d in e) {
                    a = g[d];
                    c = e[d];
                    if (g === c) {
                        continue
                    }
                    if (j && c && (m.isPlainObject(c) || (b = m.isArray(c)))) {
                        if (b) {
                            b = false;
                            f = a && m.isArray(a) ? a: []
                        } else {
                            f = a && m.isPlainObject(a) ? a: {}
                        }
                        g[d] = m.extend(j, f, c)
                    } else if (c !== undefined) {
                        g[d] = c
                    }
                }
            }
        }
        return g
    };
    m.extend({
        expando: "jQuery" + (l + Math.random()).replace(/\D/g, ""),
        isReady: true,
        error: function(a) {
            throw new Error(a)
        },
        noop: function() {},
        isFunction: function(a) {
            return m.type(a) === "function"
        },
        isArray: Array.isArray ||
        function(a) {
            return m.type(a) === "array"
        },
        isWindow: function(a) {
            return a != null && a == a.window
        },
        isNumeric: function(a) {
            return ! m.isArray(a) && a - parseFloat(a) + 1 >= 0
        },
        isEmptyObject: function(a) {
            var b;
            for (b in a) {
                return false
            }
            return true
        },
        isPlainObject: function(a) {
            var b;
            if (!a || m.type(a) !== "object" || a.nodeType || m.isWindow(a)) {
                return false
            }
            try {
                if (a.constructor && !j.call(a, "constructor") && !j.call(a.constructor.prototype, "isPrototypeOf")) {
                    return false
                }
            } catch(c) {
                return false
            }
            if (k.ownLast) {
                for (b in a) {
                    return j.call(a, b)
                }
            }
            for (b in a) {}
            return b === undefined || j.call(a, b)
        },
        type: function(a) {
            if (a == null) {
                return a + ""
            }
            return typeof a === "object" || typeof a === "function" ? h[i.call(a)] || "object": typeof a
        },
        globalEval: function(b) {
            if (b && m.trim(b)) { (a.execScript ||
                function(b) {
                    a["eval"].call(a, b)
                })(b)
            }
        },
        camelCase: function(a) {
            return a.replace(o, "ms-").replace(p, q)
        },
        nodeName: function(a, b) {
            return a.nodeName && a.nodeName.toLowerCase() === b.toLowerCase()
        },
        each: function(a, b, c) {
            var d, e = 0,
            f = a.length,
            g = r(a);
            if (c) {
                if (g) {
                    for (; e < f; e++) {
                        d = b.apply(a[e], c);
                        if (d === false) {
                            break
                        }
                    }
                } else {
                    for (e in a) {
                        d = b.apply(a[e], c);
                        if (d === false) {
                            break
                        }
                    }
                }
            } else {
                if (g) {
                    for (; e < f; e++) {
                        d = b.call(a[e], e, a[e]);
                        if (d === false) {
                            break
                        }
                    }
                } else {
                    for (e in a) {
                        d = b.call(a[e], e, a[e]);
                        if (d === false) {
                            break
                        }
                    }
                }
            }
            return a
        },
        trim: function(a) {
            return a == null ? "": (a + "").replace(n, "")
        },
        makeArray: function(a, b) {
            var c = b || [];
            if (a != null) {
                if (r(Object(a))) {
                    m.merge(c, typeof a === "string" ? [a] : a)
                } else {
                    f.call(c, a)
                }
            }
            return c
        },
        inArray: function(a, b, c) {
            var d;
            if (b) {
                if (g) {
                    return g.call(b, a, c)
                }
                d = b.length;
                c = c ? c < 0 ? Math.max(0, d + c) : c: 0;
                for (; c < d; c++) {
                    if (c in b && b[c] === a) {
                        return c
                    }
                }
            }
            return - 1
        },
        merge: function(a, b) {
            var c = +b.length,
            d = 0,
            e = a.length;
            while (d < c) {
                a[e++] = b[d++]
            }
            if (c !== c) {
                while (b[d] !== undefined) {
                    a[e++] = b[d++]
                }
            }
            a.length = e;
            return a
        },
        grep: function(a, b, c) {
            var d, e = [],
            f = 0,
            g = a.length,
            h = !c;
            for (; f < g; f++) {
                d = !b(a[f], f);
                if (d !== h) {
                    e.push(a[f])
                }
            }
            return e
        },
        map: function(a, b, c) {
            var d, f = 0,
            g = a.length,
            h = r(a),
            i = [];
            if (h) {
                for (; f < g; f++) {
                    d = b(a[f], f, c);
                    if (d != null) {
                        i.push(d)
                    }
                }
            } else {
                for (f in a) {
                    d = b(a[f], f, c);
                    if (d != null) {
                        i.push(d)
                    }
                }
            }
            return e.apply([], i)
        },
        guid: 1,
        proxy: function(a, b) {
            var c, e, f;
            if (typeof b === "string") {
                f = a[b];
                b = a;
                a = f
            }
            if (!m.isFunction(a)) {
                return undefined
            }
            c = d.call(arguments, 2);
            e = function() {
                return a.apply(b || this, c.concat(d.call(arguments)))
            };
            e.guid = a.guid = a.guid || m.guid++;
            return e
        },
        now: function() {
            return + new Date
        },
        support: k
    });
    m.each("Boolean Number String Function Array Date RegExp Object Error".split(" "),
    function(a, b) {
        h["[object " + b + "]"] = b.toLowerCase()
    });
    function r(a) {
        var b = a.length,
        c = m.type(a);
        if (c === "function" || m.isWindow(a)) {
            return false
        }
        if (a.nodeType === 1 && b) {
            return true
        }
        return c === "array" || b === 0 || typeof b === "number" && b > 0 && b - 1 in a
    }
    var s = function(a) {
        var b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u = "sizzle" + 1 * new Date,
        v = a.document,
        w = 0,
        x = 0,
        y = ha(),
        z = ha(),
        A = ha(),
        B = function(a, b) {
            if (a === b) {
                l = true
            }
            return 0
        },
        C = 1 << 31,
        D = {}.hasOwnProperty,
        E = [],
        F = E.pop,
        G = E.push,
        H = E.push,
        I = E.slice,
        J = function(a, b) {
            var c = 0,
            d = a.length;
            for (; c < d; c++) {
                if (a[c] === b) {
                    return c
                }
            }
            return - 1
        },
        K = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",
        L = "[\\x20\\t\\r\\n\\f]",
        M = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+",
        N = M.replace("w", "w#"),
        O = "\\[" + L + "*(" + M + ")(?:" + L + "*([*^$|!~]?=)" + L + "*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|(" + N + "))|)" + L + "*\\]",
        P = ":(" + M + ")(?:\\((" + "('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|" + "((?:\\\\.|[^\\\\()[\\]]|" + O + ")*)|" + ".*" + ")\\)|)",
        Q = new RegExp(L + "+", "g"),
        R = new RegExp("^" + L + "+|((?:^|[^\\\\])(?:\\\\.)*)" + L + "+$", "g"),
        S = new RegExp("^" + L + "*," + L + "*"),
        T = new RegExp("^" + L + "*([>+~]|" + L + ")" + L + "*"),
        U = new RegExp("=" + L + "*([^\\]'\"]*?)" + L + "*\\]", "g"),
        V = new RegExp(P),
        W = new RegExp("^" + N + "$"),
        X = {
            ID: new RegExp("^#(" + M + ")"),
            CLASS: new RegExp("^\\.(" + M + ")"),
            TAG: new RegExp("^(" + M.replace("w", "w*") + ")"),
            ATTR: new RegExp("^" + O),
            PSEUDO: new RegExp("^" + P),
            CHILD: new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + L + "*(even|odd|(([+-]|)(\\d*)n|)" + L + "*(?:([+-]|)" + L + "*(\\d+)|))" + L + "*\\)|)", "i"),
            bool: new RegExp("^(?:" + K + ")$", "i"),
            needsContext: new RegExp("^" + L + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + L + "*((?:-\\d)?\\d*)" + L + "*\\)|)(?=[^-]|$)", "i")
        },
        Y = /^(?:input|select|textarea|button)$/i,
        Z = /^h\d$/i,
        $ = /^[^{]+\{\s*\[native \w/,
        _ = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/,
        aa = /[+~]/,
        ba = /'|\\/g,
        ca = new RegExp("\\\\([\\da-f]{1,6}" + L + "?|(" + L + ")|.)", "ig"),
        da = function(a, b, c) {
            var d = "0x" + b - 65536;
            return d !== d || c ? b: d < 0 ? String.fromCharCode(d + 65536) : String.fromCharCode(d >> 10 | 55296, d & 1023 | 56320)
        },
        ea = function() {
            m()
        };
        try {
            H.apply(E = I.call(v.childNodes), v.childNodes);
            E[v.childNodes.length].nodeType
        } catch(fa) {
            H = {
                apply: E.length ?
                function(a, b) {
                    G.apply(a, I.call(b))
                }: function(a, b) {
                    var c = a.length,
                    d = 0;
                    while (a[c++] = b[d++]) {}
                    a.length = c - 1
                }
            }
        }
        function ga(a, b, d, e) {
            var f, h, j, k, l, o, r, s, w, x;
            if ((b ? b.ownerDocument || b: v) !== n) {
                m(b)
            }
            b = b || n;
            d = d || [];
            k = b.nodeType;
            if (typeof a !== "string" || !a || k !== 1 && k !== 9 && k !== 11) {
                return d
            }
            if (!e && p) {
                if (k !== 11 && (f = _.exec(a))) {
                    if (j = f[1]) {
                        if (k === 9) {
                            h = b.getElementById(j);
                            if (h && h.parentNode) {
                                if (h.id === j) {
                                    d.push(h);
                                    return d
                                }
                            } else {
                                return d
                            }
                        } else {
                            if (b.ownerDocument && (h = b.ownerDocument.getElementById(j)) && t(b, h) && h.id === j) {
                                d.push(h);
                                return d
                            }
                        }
                    } else if (f[2]) {
                        H.apply(d, b.getElementsByTagName(a));
                        return d
                    } else if ((j = f[3]) && c.getElementsByClassName) {
                        H.apply(d, b.getElementsByClassName(j));
                        return d
                    }
                }
                if (c.qsa && (!q || !q.test(a))) {
                    s = r = u;
                    w = b;
                    x = k !== 1 && a;
                    if (k === 1 && b.nodeName.toLowerCase() !== "object") {
                        o = g(a);
                        if (r = b.getAttribute("id")) {
                            s = r.replace(ba, "\\$&")
                        } else {
                            b.setAttribute("id", s)
                        }
                        s = "[id='" + s + "'] ";
                        l = o.length;
                        while (l--) {
                            o[l] = s + ra(o[l])
                        }
                        w = aa.test(a) && pa(b.parentNode) || b;
                        x = o.join(",")
                    }
                    if (x) {
                        try {
                            H.apply(d, w.querySelectorAll(x));
                            return d
                        } catch(y) {} finally {
                            if (!r) {
                                b.removeAttribute("id")
                            }
                        }
                    }
                }
            }
            return i(a.replace(R, "$1"), b, d, e)
        }
        function ha() {
            var a = [];
            function b(c, e) {
                if (a.push(c + " ") > d.cacheLength) {
                    delete b[a.shift()]
                }
                return b[c + " "] = e
            }
            return b
        }
        function ia(a) {
            a[u] = true;
            return a
        }
        function ja(a) {
            var b = n.createElement("div");
            try {
                return !! a(b)
            } catch(c) {
                return false
            } finally {
                if (b.parentNode) {
                    b.parentNode.removeChild(b)
                }
                b = null
            }
        }
        function ka(a, b) {
            var c = a.split("|"),
            e = a.length;
            while (e--) {
                d.attrHandle[c[e]] = b
            }
        }
        function la(a, b) {
            var c = b && a,
            d = c && a.nodeType === 1 && b.nodeType === 1 && (~b.sourceIndex || C) - (~a.sourceIndex || C);
            if (d) {
                return d
            }
            if (c) {
                while (c = c.nextSibling) {
                    if (c === b) {
                        return - 1
                    }
                }
            }
            return a ? 1 : -1
        }
        function ma(a) {
            return function(b) {
                var c = b.nodeName.toLowerCase();
                return c === "input" && b.type === a
            }
        }
        function na(a) {
            return function(b) {
                var c = b.nodeName.toLowerCase();
                return (c === "input" || c === "button") && b.type === a
            }
        }
        function oa(a) {
            return ia(function(b) {
                b = +b;
                return ia(function(c, d) {
                    var e, f = a([], c.length, b),
                    g = f.length;
                    while (g--) {
                        if (c[e = f[g]]) {
                            c[e] = !(d[e] = c[e])
                        }
                    }
                })
            })
        }
        function pa(a) {
            return a && typeof a.getElementsByTagName !== "undefined" && a
        }
        c = ga.support = {};
        f = ga.isXML = function(a) {
            var b = a && (a.ownerDocument || a).documentElement;
            return b ? b.nodeName !== "HTML": false
        };
        m = ga.setDocument = function(a) {
            var b, e, g = a ? a.ownerDocument || a: v;
            if (g === n || g.nodeType !== 9 || !g.documentElement) {
                return n
            }
            n = g;
            o = g.documentElement;
            e = g.defaultView;
            if (e && e !== e.top) {
                if (e.addEventListener) {
                    e.addEventListener("unload", ea, false)
                } else if (e.attachEvent) {
                    e.attachEvent("onunload", ea)
                }
            }
            p = !f(g);
            c.attributes = ja(function(a) {
                a.className = "i";
                return ! a.getAttribute("className")
            });
            c.getElementsByTagName = ja(function(a) {
                a.appendChild(g.createComment(""));
                return ! a.getElementsByTagName("*").length
            });
            c.getElementsByClassName = $.test(g.getElementsByClassName);
            c.getById = ja(function(a) {
                o.appendChild(a).id = u;
                return ! g.getElementsByName || !g.getElementsByName(u).length
            });
            if (c.getById) {
                d.find["ID"] = function(a, b) {
                    if (typeof b.getElementById !== "undefined" && p) {
                        var c = b.getElementById(a);
                        return c && c.parentNode ? [c] : []
                    }
                };
                d.filter["ID"] = function(a) {
                    var b = a.replace(ca, da);
                    return function(a) {
                        return a.getAttribute("id") === b
                    }
                }
            } else {
                delete d.find["ID"];
                d.filter["ID"] = function(a) {
                    var b = a.replace(ca, da);
                    return function(a) {
                        var c = typeof a.getAttributeNode !== "undefined" && a.getAttributeNode("id");
                        return c && c.value === b
                    }
                }
            }
            d.find["TAG"] = c.getElementsByTagName ?
            function(a, b) {
                if (typeof b.getElementsByTagName !== "undefined") {
                    return b.getElementsByTagName(a)
                } else if (c.qsa) {
                    return b.querySelectorAll(a)
                }
            }: function(a, b) {
                var c, d = [],
                e = 0,
                f = b.getElementsByTagName(a);
                if (a === "*") {
                    while (c = f[e++]) {
                        if (c.nodeType === 1) {
                            d.push(c)
                        }
                    }
                    return d
                }
                return f
            };
            d.find["CLASS"] = c.getElementsByClassName &&
            function(a, b) {
                if (p) {
                    return b.getElementsByClassName(a)
                }
            };
            r = [];
            q = [];
            if (c.qsa = $.test(g.querySelectorAll)) {
                ja(function(a) {
                    o.appendChild(a).innerHTML = "<a id='" + u + "'></a>" + "<select id='" + u + "-\f]' msallowcapture=''>" + "<option selected=''></option></select>";
                    if (a.querySelectorAll("[msallowcapture^='']").length) {
                        q.push("[*^$]=" + L + "*(?:''|\"\")")
                    }
                    if (!a.querySelectorAll("[selected]").length) {
                        q.push("\\[" + L + "*(?:value|" + K + ")")
                    }
                    if (!a.querySelectorAll("[id~=" + u + "-]").length) {
                        q.push("~=")
                    }
                    if (!a.querySelectorAll(":checked").length) {
                        q.push(":checked")
                    }
                    if (!a.querySelectorAll("a#" + u + "+*").length) {
                        q.push(".#.+[+~]")
                    }
                });
                ja(function(a) {
                    var b = g.createElement("input");
                    b.setAttribute("type", "hidden");
                    a.appendChild(b).setAttribute("name", "D");
                    if (a.querySelectorAll("[name=d]").length) {
                        q.push("name" + L + "*[*^$|!~]?=")
                    }
                    if (!a.querySelectorAll(":enabled").length) {
                        q.push(":enabled", ":disabled")
                    }
                    a.querySelectorAll("*,:x");
                    q.push(",.*:")
                })
            }
            if (c.matchesSelector = $.test(s = o.matches || o.webkitMatchesSelector || o.mozMatchesSelector || o.oMatchesSelector || o.msMatchesSelector)) {
                ja(function(a) {
                    c.disconnectedMatch = s.call(a, "div");
                    s.call(a, "[s!='']:x");
                    r.push("!=", P)
                })
            }
            q = q.length && new RegExp(q.join("|"));
            r = r.length && new RegExp(r.join("|"));
            b = $.test(o.compareDocumentPosition);
            t = b || $.test(o.contains) ?
            function(a, b) {
                var c = a.nodeType === 9 ? a.documentElement: a,
                d = b && b.parentNode;
                return a === d || !!(d && d.nodeType === 1 && (c.contains ? c.contains(d) : a.compareDocumentPosition && a.compareDocumentPosition(d) & 16))
            }: function(a, b) {
                if (b) {
                    while (b = b.parentNode) {
                        if (b === a) {
                            return true
                        }
                    }
                }
                return false
            };
            B = b ?
            function(a, b) {
                if (a === b) {
                    l = true;
                    return 0
                }
                var d = !a.compareDocumentPosition - !b.compareDocumentPosition;
                if (d) {
                    return d
                }
                d = (a.ownerDocument || a) === (b.ownerDocument || b) ? a.compareDocumentPosition(b) : 1;
                if (d & 1 || !c.sortDetached && b.compareDocumentPosition(a) === d) {
                    if (a === g || a.ownerDocument === v && t(v, a)) {
                        return - 1
                    }
                    if (b === g || b.ownerDocument === v && t(v, b)) {
                        return 1
                    }
                    return k ? J(k, a) - J(k, b) : 0
                }
                return d & 4 ? -1 : 1
            }: function(a, b) {
                if (a === b) {
                    l = true;
                    return 0
                }
                var c, d = 0,
                e = a.parentNode,
                f = b.parentNode,
                h = [a],
                i = [b];
                if (!e || !f) {
                    return a === g ? -1 : b === g ? 1 : e ? -1 : f ? 1 : k ? J(k, a) - J(k, b) : 0
                } else if (e === f) {
                    return la(a, b)
                }
                c = a;
                while (c = c.parentNode) {
                    h.unshift(c)
                }
                c = b;
                while (c = c.parentNode) {
                    i.unshift(c)
                }
                while (h[d] === i[d]) {
                    d++
                }
                return d ? la(h[d], i[d]) : h[d] === v ? -1 : i[d] === v ? 1 : 0
            };
            return g
        };
        ga.matches = function(a, b) {
            return ga(a, null, null, b)
        };
        ga.matchesSelector = function(a, b) {
            if ((a.ownerDocument || a) !== n) {
                m(a)
            }
            b = b.replace(U, "='$1']");
            if (c.matchesSelector && p && (!r || !r.test(b)) && (!q || !q.test(b))) {
                try {
                    var d = s.call(a, b);
                    if (d || c.disconnectedMatch || a.document && a.document.nodeType !== 11) {
                        return d
                    }
                } catch(e) {}
            }
            return ga(b, n, null, [a]).length > 0
        };
        ga.contains = function(a, b) {
            if ((a.ownerDocument || a) !== n) {
                m(a)
            }
            return t(a, b)
        };
        ga.attr = function(a, b) {
            if ((a.ownerDocument || a) !== n) {
                m(a)
            }
            var e = d.attrHandle[b.toLowerCase()],
            f = e && D.call(d.attrHandle, b.toLowerCase()) ? e(a, b, !p) : undefined;
            return f !== undefined ? f: c.attributes || !p ? a.getAttribute(b) : (f = a.getAttributeNode(b)) && f.specified ? f.value: null
        };
        ga.error = function(a) {
            throw new Error("Syntax error, unrecognized expression: " + a)
        };
        ga.uniqueSort = function(a) {
            var b, d = [],
            e = 0,
            f = 0;
            l = !c.detectDuplicates;
            k = !c.sortStable && a.slice(0);
            a.sort(B);
            if (l) {
                while (b = a[f++]) {
                    if (b === a[f]) {
                        e = d.push(f)
                    }
                }
                while (e--) {
                    a.splice(d[e], 1)
                }
            }
            k = null;
            return a
        };
        e = ga.getText = function(a) {
            var b, c = "",
            d = 0,
            f = a.nodeType;
            if (!f) {
                while (b = a[d++]) {
                    c += e(b)
                }
            } else if (f === 1 || f === 9 || f === 11) {
                if (typeof a.textContent === "string") {
                    return a.textContent
                } else {
                    for (a = a.firstChild; a; a = a.nextSibling) {
                        c += e(a)
                    }
                }
            } else if (f === 3 || f === 4) {
                return a.nodeValue
            }
            return c
        };
        d = ga.selectors = {
            cacheLength: 50,
            createPseudo: ia,
            match: X,
            attrHandle: {},
            find: {},
            relative: {
                ">": {
                    dir: "parentNode",
                    first: true
                },
                " ": {
                    dir: "parentNode"
                },
                "+": {
                    dir: "previousSibling",
                    first: true
                },
                "~": {
                    dir: "previousSibling"
                }
            },
            preFilter: {
                ATTR: function(a) {
                    a[1] = a[1].replace(ca, da);
                    a[3] = (a[3] || a[4] || a[5] || "").replace(ca, da);
                    if (a[2] === "~=") {
                        a[3] = " " + a[3] + " "
                    }
                    return a.slice(0, 4)
                },
                CHILD: function(a) {
                    a[1] = a[1].toLowerCase();
                    if (a[1].slice(0, 3) === "nth") {
                        if (!a[3]) {
                            ga.error(a[0])
                        }
                        a[4] = +(a[4] ? a[5] + (a[6] || 1) : 2 * (a[3] === "even" || a[3] === "odd"));
                        a[5] = +(a[7] + a[8] || a[3] === "odd")
                    } else if (a[3]) {
                        ga.error(a[0])
                    }
                    return a
                },
                PSEUDO: function(a) {
                    var b, c = !a[6] && a[2];
                    if (X["CHILD"].test(a[0])) {
                        return null
                    }
                    if (a[3]) {
                        a[2] = a[4] || a[5] || ""
                    } else if (c && V.test(c) && (b = g(c, true)) && (b = c.indexOf(")", c.length - b) - c.length)) {
                        a[0] = a[0].slice(0, b);
                        a[2] = c.slice(0, b)
                    }
                    return a.slice(0, 3)
                }
            },
            filter: {
                TAG: function(a) {
                    var b = a.replace(ca, da).toLowerCase();
                    return a === "*" ?
                    function() {
                        return true
                    }: function(a) {
                        return a.nodeName && a.nodeName.toLowerCase() === b
                    }
                },
                CLASS: function(a) {
                    var b = y[a + " "];
                    return b || (b = new RegExp("(^|" + L + ")" + a + "(" + L + "|$)")) && y(a,
                    function(a) {
                        return b.test(typeof a.className === "string" && a.className || typeof a.getAttribute !== "undefined" && a.getAttribute("class") || "")
                    })
                },
                ATTR: function(a, b, c) {
                    return function(d) {
                        var e = ga.attr(d, a);
                        if (e == null) {
                            return b === "!="
                        }
                        if (!b) {
                            return true
                        }
                        e += "";
                        return b === "=" ? e === c: b === "!=" ? e !== c: b === "^=" ? c && e.indexOf(c) === 0 : b === "*=" ? c && e.indexOf(c) > -1 : b === "$=" ? c && e.slice( - c.length) === c: b === "~=" ? (" " + e.replace(Q, " ") + " ").indexOf(c) > -1 : b === "|=" ? e === c || e.slice(0, c.length + 1) === c + "-": false
                    }
                },
                CHILD: function(a, b, c, d, e) {
                    var f = a.slice(0, 3) !== "nth",
                    g = a.slice( - 4) !== "last",
                    h = b === "of-type";
                    return d === 1 && e === 0 ?
                    function(a) {
                        return !! a.parentNode
                    }: function(b, c, i) {
                        var j, k, l, m, n, o, p = f !== g ? "nextSibling": "previousSibling",
                        q = b.parentNode,
                        r = h && b.nodeName.toLowerCase(),
                        s = !i && !h;
                        if (q) {
                            if (f) {
                                while (p) {
                                    l = b;
                                    while (l = l[p]) {
                                        if (h ? l.nodeName.toLowerCase() === r: l.nodeType === 1) {
                                            return false
                                        }
                                    }
                                    o = p = a === "only" && !o && "nextSibling"
                                }
                                return true
                            }
                            o = [g ? q.firstChild: q.lastChild];
                            if (g && s) {
                                k = q[u] || (q[u] = {});
                                j = k[a] || [];
                                n = j[0] === w && j[1];
                                m = j[0] === w && j[2];
                                l = n && q.childNodes[n];
                                while (l = ++n && l && l[p] || (m = n = 0) || o.pop()) {
                                    if (l.nodeType === 1 && ++m && l === b) {
                                        k[a] = [w, n, m];
                                        break
                                    }
                                }
                            } else if (s && (j = (b[u] || (b[u] = {}))[a]) && j[0] === w) {
                                m = j[1]
                            } else {
                                while (l = ++n && l && l[p] || (m = n = 0) || o.pop()) {
                                    if ((h ? l.nodeName.toLowerCase() === r: l.nodeType === 1) && ++m) {
                                        if (s) { (l[u] || (l[u] = {}))[a] = [w, m]
                                        }
                                        if (l === b) {
                                            break
                                        }
                                    }
                                }
                            }
                            m -= e;
                            return m === d || m % d === 0 && m / d >= 0
                        }
                    }
                },
                PSEUDO: function(a, b) {
                    var c, e = d.pseudos[a] || d.setFilters[a.toLowerCase()] || ga.error("unsupported pseudo: " + a);
                    if (e[u]) {
                        return e(b)
                    }
                    if (e.length > 1) {
                        c = [a, a, "", b];
                        return d.setFilters.hasOwnProperty(a.toLowerCase()) ? ia(function(a, c) {
                            var d, f = e(a, b),
                            g = f.length;
                            while (g--) {
                                d = J(a, f[g]);
                                a[d] = !(c[d] = f[g])
                            }
                        }) : function(a) {
                            return e(a, 0, c)
                        }
                    }
                    return e
                }
            },
            pseudos: {
                not: ia(function(a) {
                    var b = [],
                    c = [],
                    d = h(a.replace(R, "$1"));
                    return d[u] ? ia(function(a, b, c, e) {
                        var f, g = d(a, null, e, []),
                        h = a.length;
                        while (h--) {
                            if (f = g[h]) {
                                a[h] = !(b[h] = f)
                            }
                        }
                    }) : function(a, e, f) {
                        b[0] = a;
                        d(b, null, f, c);
                        b[0] = null;
                        return ! c.pop()
                    }
                }),
                has: ia(function(a) {
                    return function(b) {
                        return ga(a, b).length > 0
                    }
                }),
                contains: ia(function(a) {
                    a = a.replace(ca, da);
                    return function(b) {
                        return (b.textContent || b.innerText || e(b)).indexOf(a) > -1
                    }
                }),
                lang: ia(function(a) {
                    if (!W.test(a || "")) {
                        ga.error("unsupported lang: " + a)
                    }
                    a = a.replace(ca, da).toLowerCase();
                    return function(b) {
                        var c;
                        do {
                            if (c = p ? b.lang: b.getAttribute("xml:lang") || b.getAttribute("lang")) {
                                c = c.toLowerCase();
                                return c === a || c.indexOf(a + "-") === 0
                            }
                        } while (( b = b . parentNode ) && b.nodeType === 1);
                        return false
                    }
                }),
                target: function(b) {
                    var c = a.location && a.location.hash;
                    return c && c.slice(1) === b.id
                },
                root: function(a) {
                    return a === o
                },
                focus: function(a) {
                    return a === n.activeElement && (!n.hasFocus || n.hasFocus()) && !!(a.type || a.href || ~a.tabIndex)
                },
                enabled: function(a) {
                    return a.disabled === false
                },
                disabled: function(a) {
                    return a.disabled === true
                },
                checked: function(a) {
                    var b = a.nodeName.toLowerCase();
                    return b === "input" && !!a.checked || b === "option" && !!a.selected
                },
                selected: function(a) {
                    if (a.parentNode) {
                        a.parentNode.selectedIndex
                    }
                    return a.selected === true
                },
                empty: function(a) {
                    for (a = a.firstChild; a; a = a.nextSibling) {
                        if (a.nodeType < 6) {
                            return false
                        }
                    }
                    return true
                },
                parent: function(a) {
                    return ! d.pseudos["empty"](a)
                },
                header: function(a) {
                    return Z.test(a.nodeName)
                },
                input: function(a) {
                    return Y.test(a.nodeName)
                },
                button: function(a) {
                    var b = a.nodeName.toLowerCase();
                    return b === "input" && a.type === "button" || b === "button"
                },
                text: function(a) {
                    var b;
                    return a.nodeName.toLowerCase() === "input" && a.type === "text" && ((b = a.getAttribute("type")) == null || b.toLowerCase() === "text")
                },
                first: oa(function() {
                    return [0]
                }),
                last: oa(function(a, b) {
                    return [b - 1]
                }),
                eq: oa(function(a, b, c) {
                    return [c < 0 ? c + b: c]
                }),
                even: oa(function(a, b) {
                    var c = 0;
                    for (; c < b; c += 2) {
                        a.push(c)
                    }
                    return a
                }),
                odd: oa(function(a, b) {
                    var c = 1;
                    for (; c < b; c += 2) {
                        a.push(c)
                    }
                    return a
                }),
                lt: oa(function(a, b, c) {
                    var d = c < 0 ? c + b: c;
                    for (; --d >= 0;) {
                        a.push(d)
                    }
                    return a
                }),
                gt: oa(function(a, b, c) {
                    var d = c < 0 ? c + b: c;
                    for (; ++d < b;) {
                        a.push(d)
                    }
                    return a
                })
            }
        };
        d.pseudos["nth"] = d.pseudos["eq"];
        for (b in {
            radio: true,
            checkbox: true,
            file: true,
            password: true,
            image: true
        }) {
            d.pseudos[b] = ma(b)
        }
        for (b in {
            submit: true,
            reset: true
        }) {
            d.pseudos[b] = na(b)
        }
        function qa() {}
        qa.prototype = d.filters = d.pseudos;
        d.setFilters = new qa;
        g = ga.tokenize = function(a, b) {
            var c, e, f, g, h, i, j, k = z[a + " "];
            if (k) {
                return b ? 0 : k.slice(0)
            }
            h = a;
            i = [];
            j = d.preFilter;
            while (h) {
                if (!c || (e = S.exec(h))) {
                    if (e) {
                        h = h.slice(e[0].length) || h
                    }
                    i.push(f = [])
                }
                c = false;
                if (e = T.exec(h)) {
                    c = e.shift();
                    f.push({
                        value: c,
                        type: e[0].replace(R, " ")
                    });
                    h = h.slice(c.length)
                }
                for (g in d.filter) {
                    if ((e = X[g].exec(h)) && (!j[g] || (e = j[g](e)))) {
                        c = e.shift();
                        f.push({
                            value: c,
                            type: g,
                            matches: e
                        });
                        h = h.slice(c.length)
                    }
                }
                if (!c) {
                    break
                }
            }
            return b ? h.length: h ? ga.error(a) : z(a, i).slice(0)
        };
        function ra(a) {
            var b = 0,
            c = a.length,
            d = "";
            for (; b < c; b++) {
                d += a[b].value
            }
            return d
        }
        function sa(a, b, c) {
            var d = b.dir,
            e = c && d === "parentNode",
            f = x++;
            return b.first ?
            function(b, c, f) {
                while (b = b[d]) {
                    if (b.nodeType === 1 || e) {
                        return a(b, c, f)
                    }
                }
            }: function(b, c, g) {
                var h, i, j = [w, f];
                if (g) {
                    while (b = b[d]) {
                        if (b.nodeType === 1 || e) {
                            if (a(b, c, g)) {
                                return true
                            }
                        }
                    }
                } else {
                    while (b = b[d]) {
                        if (b.nodeType === 1 || e) {
                            i = b[u] || (b[u] = {});
                            if ((h = i[d]) && h[0] === w && h[1] === f) {
                                return j[2] = h[2]
                            } else {
                                i[d] = j;
                                if (j[2] = a(b, c, g)) {
                                    return true
                                }
                            }
                        }
                    }
                }
            }
        }
        function ta(a) {
            return a.length > 1 ?
            function(b, c, d) {
                var e = a.length;
                while (e--) {
                    if (!a[e](b, c, d)) {
                        return false
                    }
                }
                return true
            }: a[0]
        }
        function ua(a, b, c) {
            var d = 0,
            e = b.length;
            for (; d < e; d++) {
                ga(a, b[d], c)
            }
            return c
        }
        function va(a, b, c, d, e) {
            var f, g = [],
            h = 0,
            i = a.length,
            j = b != null;
            for (; h < i; h++) {
                if (f = a[h]) {
                    if (!c || c(f, d, e)) {
                        g.push(f);
                        if (j) {
                            b.push(h)
                        }
                    }
                }
            }
            return g
        }
        function wa(a, b, c, d, e, f) {
            if (d && !d[u]) {
                d = wa(d)
            }
            if (e && !e[u]) {
                e = wa(e, f)
            }
            return ia(function(f, g, h, i) {
                var j, k, l, m = [],
                n = [],
                o = g.length,
                p = f || ua(b || "*", h.nodeType ? [h] : h, []),
                q = a && (f || !b) ? va(p, m, a, h, i) : p,
                r = c ? e || (f ? a: o || d) ? [] : g: q;
                if (c) {
                    c(q, r, h, i)
                }
                if (d) {
                    j = va(r, n);
                    d(j, [], h, i);
                    k = j.length;
                    while (k--) {
                        if (l = j[k]) {
                            r[n[k]] = !(q[n[k]] = l)
                        }
                    }
                }
                if (f) {
                    if (e || a) {
                        if (e) {
                            j = [];
                            k = r.length;
                            while (k--) {
                                if (l = r[k]) {
                                    j.push(q[k] = l)
                                }
                            }
                            e(null, r = [], j, i)
                        }
                        k = r.length;
                        while (k--) {
                            if ((l = r[k]) && (j = e ? J(f, l) : m[k]) > -1) {
                                f[j] = !(g[j] = l)
                            }
                        }
                    }
                } else {
                    r = va(r === g ? r.splice(o, r.length) : r);
                    if (e) {
                        e(null, g, r, i)
                    } else {
                        H.apply(g, r)
                    }
                }
            })
        }
        function xa(a) {
            var b, c, e, f = a.length,
            g = d.relative[a[0].type],
            h = g || d.relative[" "],
            i = g ? 1 : 0,
            k = sa(function(a) {
                return a === b
            },
            h, true),
            l = sa(function(a) {
                return J(b, a) > -1
            },
            h, true),
            m = [function(a, c, d) {
                var e = !g && (d || c !== j) || ((b = c).nodeType ? k(a, c, d) : l(a, c, d));
                b = null;
                return e
            }];
            for (; i < f; i++) {
                if (c = d.relative[a[i].type]) {
                    m = [sa(ta(m), c)]
                } else {
                    c = d.filter[a[i].type].apply(null, a[i].matches);
                    if (c[u]) {
                        e = ++i;
                        for (; e < f; e++) {
                            if (d.relative[a[e].type]) {
                                break
                            }
                        }
                        return wa(i > 1 && ta(m), i > 1 && ra(a.slice(0, i - 1).concat({
                            value: a[i - 2].type === " " ? "*": ""
                        })).replace(R, "$1"), c, i < e && xa(a.slice(i, e)), e < f && xa(a = a.slice(e)), e < f && ra(a))
                    }
                    m.push(c)
                }
            }
            return ta(m)
        }
        function ya(a, b) {
            var c = b.length > 0,
            e = a.length > 0,
            f = function(f, g, h, i, k) {
                var l, m, o, p = 0,
                q = "0",
                r = f && [],
                s = [],
                t = j,
                u = f || e && d.find["TAG"]("*", k),
                v = w += t == null ? 1 : Math.random() || .1,
                x = u.length;
                if (k) {
                    j = g !== n && g
                }
                for (; q !== x && (l = u[q]) != null; q++) {
                    if (e && l) {
                        m = 0;
                        while (o = a[m++]) {
                            if (o(l, g, h)) {
                                i.push(l);
                                break
                            }
                        }
                        if (k) {
                            w = v
                        }
                    }
                    if (c) {
                        if (l = !o && l) {
                            p--
                        }
                        if (f) {
                            r.push(l)
                        }
                    }
                }
                p += q;
                if (c && q !== p) {
                    m = 0;
                    while (o = b[m++]) {
                        o(r, s, g, h)
                    }
                    if (f) {
                        if (p > 0) {
                            while (q--) {
                                if (! (r[q] || s[q])) {
                                    s[q] = F.call(i)
                                }
                            }
                        }
                        s = va(s)
                    }
                    H.apply(i, s);
                    if (k && !f && s.length > 0 && p + b.length > 1) {
                        ga.uniqueSort(i)
                    }
                }
                if (k) {
                    w = v;
                    j = t
                }
                return r
            };
            return c ? ia(f) : f
        }
        h = ga.compile = function(a, b) {
            var c, d = [],
            e = [],
            f = A[a + " "];
            if (!f) {
                if (!b) {
                    b = g(a)
                }
                c = b.length;
                while (c--) {
                    f = xa(b[c]);
                    if (f[u]) {
                        d.push(f)
                    } else {
                        e.push(f)
                    }
                }
                f = A(a, ya(e, d));
                f.selector = a
            }
            return f
        };
        i = ga.select = function(a, b, e, f) {
            var i, j, k, l, m, n = typeof a === "function" && a,
            o = !f && g(a = n.selector || a);
            e = e || [];
            if (o.length === 1) {
                j = o[0] = o[0].slice(0);
                if (j.length > 2 && (k = j[0]).type === "ID" && c.getById && b.nodeType === 9 && p && d.relative[j[1].type]) {
                    b = (d.find["ID"](k.matches[0].replace(ca, da), b) || [])[0];
                    if (!b) {
                        return e
                    } else if (n) {
                        b = b.parentNode
                    }
                    a = a.slice(j.shift().value.length)
                }
                i = X["needsContext"].test(a) ? 0 : j.length;
                while (i--) {
                    k = j[i];
                    if (d.relative[l = k.type]) {
                        break
                    }
                    if (m = d.find[l]) {
                        if (f = m(k.matches[0].replace(ca, da), aa.test(j[0].type) && pa(b.parentNode) || b)) {
                            j.splice(i, 1);
                            a = f.length && ra(j);
                            if (!a) {
                                H.apply(e, f);
                                return e
                            }
                            break
                        }
                    }
                }
            } (n || h(a, o))(f, b, !p, e, aa.test(a) && pa(b.parentNode) || b);
            return e
        };
        c.sortStable = u.split("").sort(B).join("") === u;
        c.detectDuplicates = !!l;
        m();
        c.sortDetached = ja(function(a) {
            return a.compareDocumentPosition(n.createElement("div")) & 1
        });
        if (!ja(function(a) {
            a.innerHTML = "<a href='#'></a>";
            return a.firstChild.getAttribute("href") === "#"
        })) {
            ka("type|href|height|width",
            function(a, b, c) {
                if (!c) {
                    return a.getAttribute(b, b.toLowerCase() === "type" ? 1 : 2)
                }
            })
        }
        if (!c.attributes || !ja(function(a) {
            a.innerHTML = "<input/>";
            a.firstChild.setAttribute("value", "");
            return a.firstChild.getAttribute("value") === ""
        })) {
            ka("value",
            function(a, b, c) {
                if (!c && a.nodeName.toLowerCase() === "input") {
                    return a.defaultValue
                }
            })
        }
        if (!ja(function(a) {
            return a.getAttribute("disabled") == null
        })) {
            ka(K,
            function(a, b, c) {
                var d;
                if (!c) {
                    return a[b] === true ? b.toLowerCase() : (d = a.getAttributeNode(b)) && d.specified ? d.value: null
                }
            })
        }
        return ga
    } (a);
    m.find = s;
    m.expr = s.selectors;
    m.expr[":"] = m.expr.pseudos;
    m.unique = s.uniqueSort;
    m.text = s.getText;
    m.isXMLDoc = s.isXML;
    m.contains = s.contains;
    var t = m.expr.match.needsContext;
    var u = /^<(\w+)\s*\/?>(?:<\/\1>|)$/;
    var v = /^.[^:#\[\.,]*$/;
    function w(a, b, c) {
        if (m.isFunction(b)) {
            return m.grep(a,
            function(a, d) {
                return !! b.call(a, d, a) !== c
            })
        }
        if (b.nodeType) {
            return m.grep(a,
            function(a) {
                return a === b !== c
            })
        }
        if (typeof b === "string") {
            if (v.test(b)) {
                return m.filter(b, a, c)
            }
            b = m.filter(b, a)
        }
        return m.grep(a,
        function(a) {
            return m.inArray(a, b) >= 0 !== c
        })
    }
    m.filter = function(a, b, c) {
        var d = b[0];
        if (c) {
            a = ":not(" + a + ")"
        }
        return b.length === 1 && d.nodeType === 1 ? m.find.matchesSelector(d, a) ? [d] : [] : m.find.matches(a, m.grep(b,
        function(a) {
            return a.nodeType === 1
        }))
    };
    m.fn.extend({
        find: function(a) {
            var b, c = [],
            d = this,
            e = d.length;
            if (typeof a !== "string") {
                return this.pushStack(m(a).filter(function() {
                    for (b = 0; b < e; b++) {
                        if (m.contains(d[b], this)) {
                            return true
                        }
                    }
                }))
            }
            for (b = 0; b < e; b++) {
                m.find(a, d[b], c)
            }
            c = this.pushStack(e > 1 ? m.unique(c) : c);
            c.selector = this.selector ? this.selector + " " + a: a;
            return c
        },
        filter: function(a) {
            return this.pushStack(w(this, a || [], false))
        },
        not: function(a) {
            return this.pushStack(w(this, a || [], true))
        },
        is: function(a) {
            return !! w(this, typeof a === "string" && t.test(a) ? m(a) : a || [], false).length
        }
    });
    var x, y = a.document,
    z = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]*))$/,
    A = m.fn.init = function(a, b) {
        var c, d;
        if (!a) {
            return this
        }
        if (typeof a === "string") {
            if (a.charAt(0) === "<" && a.charAt(a.length - 1) === ">" && a.length >= 3) {
                c = [null, a, null]
            } else {
                c = z.exec(a)
            }
            if (c && (c[1] || !b)) {
                if (c[1]) {
                    b = b instanceof m ? b[0] : b;
                    m.merge(this, m.parseHTML(c[1], b && b.nodeType ? b.ownerDocument || b: y, true));
                    if (u.test(c[1]) && m.isPlainObject(b)) {
                        for (c in b) {
                            if (m.isFunction(this[c])) {
                                this[c](b[c])
                            } else {
                                this.attr(c, b[c])
                            }
                        }
                    }
                    return this
                } else {
                    d = y.getElementById(c[2]);
                    if (d && d.parentNode) {
                        if (d.id !== c[2]) {
                            return x.find(a)
                        }
                        this.length = 1;
                        this[0] = d
                    }
                    this.context = y;
                    this.selector = a;
                    return this
                }
            } else if (!b || b.jquery) {
                return (b || x).find(a)
            } else {
                return this.constructor(b).find(a)
            }
        } else if (a.nodeType) {
            this.context = this[0] = a;
            this.length = 1;
            return this
        } else if (m.isFunction(a)) {
            return typeof x.ready !== "undefined" ? x.ready(a) : a(m)
        }
        if (a.selector !== undefined) {
            this.selector = a.selector;
            this.context = a.context
        }
        return m.makeArray(a, this)
    };
    A.prototype = m.fn;
    x = m(y);
    var B = /^(?:parents|prev(?:Until|All))/,
    C = {
        children: true,
        contents: true,
        next: true,
        prev: true
    };
    m.extend({
        dir: function(a, b, c) {
            var d = [],
            e = a[b];
            while (e && e.nodeType !== 9 && (c === undefined || e.nodeType !== 1 || !m(e).is(c))) {
                if (e.nodeType === 1) {
                    d.push(e)
                }
                e = e[b]
            }
            return d
        },
        sibling: function(a, b) {
            var c = [];
            for (; a; a = a.nextSibling) {
                if (a.nodeType === 1 && a !== b) {
                    c.push(a)
                }
            }
            return c
        }
    });
    m.fn.extend({
        has: function(a) {
            var b, c = m(a, this),
            d = c.length;
            return this.filter(function() {
                for (b = 0; b < d; b++) {
                    if (m.contains(this, c[b])) {
                        return true
                    }
                }
            })
        },
        closest: function(a, b) {
            var c, d = 0,
            e = this.length,
            f = [],
            g = t.test(a) || typeof a !== "string" ? m(a, b || this.context) : 0;
            for (; d < e; d++) {
                for (c = this[d]; c && c !== b; c = c.parentNode) {
                    if (c.nodeType < 11 && (g ? g.index(c) > -1 : c.nodeType === 1 && m.find.matchesSelector(c, a))) {
                        f.push(c);
                        break
                    }
                }
            }
            return this.pushStack(f.length > 1 ? m.unique(f) : f)
        },
        index: function(a) {
            if (!a) {
                return this[0] && this[0].parentNode ? this.first().prevAll().length: -1
            }
            if (typeof a === "string") {
                return m.inArray(this[0], m(a))
            }
            return m.inArray(a.jquery ? a[0] : a, this)
        },
        add: function(a, b) {
            return this.pushStack(m.unique(m.merge(this.get(), m(a, b))))
        },
        addBack: function(a) {
            return this.add(a == null ? this.prevObject: this.prevObject.filter(a))
        }
    });
    function D(a, b) {
        do {
            a = a[b]
        } while ( a && a . nodeType !== 1 );
        return a
    }
    m.each({
        parent: function(a) {
            var b = a.parentNode;
            return b && b.nodeType !== 11 ? b: null
        },
        parents: function(a) {
            return m.dir(a, "parentNode")
        },
        parentsUntil: function(a, b, c) {
            return m.dir(a, "parentNode", c)
        },
        next: function(a) {
            return D(a, "nextSibling")
        },
        prev: function(a) {
            return D(a, "previousSibling")
        },
        nextAll: function(a) {
            return m.dir(a, "nextSibling")
        },
        prevAll: function(a) {
            return m.dir(a, "previousSibling")
        },
        nextUntil: function(a, b, c) {
            return m.dir(a, "nextSibling", c)
        },
        prevUntil: function(a, b, c) {
            return m.dir(a, "previousSibling", c)
        },
        siblings: function(a) {
            return m.sibling((a.parentNode || {}).firstChild, a)
        },
        children: function(a) {
            return m.sibling(a.firstChild)
        },
        contents: function(a) {
            return m.nodeName(a, "iframe") ? a.contentDocument || a.contentWindow.document: m.merge([], a.childNodes)
        }
    },
    function(a, b) {
        m.fn[a] = function(c, d) {
            var e = m.map(this, b, c);
            if (a.slice( - 5) !== "Until") {
                d = c
            }
            if (d && typeof d === "string") {
                e = m.filter(d, e)
            }
            if (this.length > 1) {
                if (!C[a]) {
                    e = m.unique(e)
                }
                if (B.test(a)) {
                    e = e.reverse()
                }
            }
            return this.pushStack(e)
        }
    });
    var E = /\S+/g;
    var F = {};
    function G(a) {
        var b = F[a] = {};
        m.each(a.match(E) || [],
        function(a, c) {
            b[c] = true
        });
        return b
    }
    m.Callbacks = function(a) {
        a = typeof a === "string" ? F[a] || G(a) : m.extend({},
        a);
        var b, c, d, e, f, g, h = [],
        i = !a.once && [],
        j = function(l) {
            c = a.memory && l;
            d = true;
            f = g || 0;
            g = 0;
            e = h.length;
            b = true;
            for (; h && f < e; f++) {
                if (h[f].apply(l[0], l[1]) === false && a.stopOnFalse) {
                    c = false;
                    break
                }
            }
            b = false;
            if (h) {
                if (i) {
                    if (i.length) {
                        j(i.shift())
                    }
                } else if (c) {
                    h = []
                } else {
                    k.disable()
                }
            }
        },
        k = {
            add: function() {
                if (h) {
                    var d = h.length; (function f(b) {
                        m.each(b,
                        function(b, c) {
                            var d = m.type(c);
                            if (d === "function") {
                                if (!a.unique || !k.has(c)) {
                                    h.push(c)
                                }
                            } else if (c && c.length && d !== "string") {
                                f(c)
                            }
                        })
                    })(arguments);
                    if (b) {
                        e = h.length
                    } else if (c) {
                        g = d;
                        j(c)
                    }
                }
                return this
            },
            remove: function() {
                if (h) {
                    m.each(arguments,
                    function(a, c) {
                        var d;
                        while ((d = m.inArray(c, h, d)) > -1) {
                            h.splice(d, 1);
                            if (b) {
                                if (d <= e) {
                                    e--
                                }
                                if (d <= f) {
                                    f--
                                }
                            }
                        }
                    })
                }
                return this
            },
            has: function(a) {
                return a ? m.inArray(a, h) > -1 : !!(h && h.length)
            },
            empty: function() {
                h = [];
                e = 0;
                return this
            },
            disable: function() {
                h = i = c = undefined;
                return this
            },
            disabled: function() {
                return ! h
            },
            lock: function() {
                i = undefined;
                if (!c) {
                    k.disable()
                }
                return this
            },
            locked: function() {
                return ! i
            },
            fireWith: function(a, c) {
                if (h && (!d || i)) {
                    c = c || [];
                    c = [a, c.slice ? c.slice() : c];
                    if (b) {
                        i.push(c)
                    } else {
                        j(c)
                    }
                }
                return this
            },
            fire: function() {
                k.fireWith(this, arguments);
                return this
            },
            fired: function() {
                return !! d
            }
        };
        return k
    };
    m.extend({
        Deferred: function(a) {
            var b = [["resolve", "done", m.Callbacks("once memory"), "resolved"], ["reject", "fail", m.Callbacks("once memory"), "rejected"], ["notify", "progress", m.Callbacks("memory")]],
            c = "pending",
            d = {
                state: function() {
                    return c
                },
                always: function() {
                    e.done(arguments).fail(arguments);
                    return this
                },
                then: function() {
                    var a = arguments;
                    return m.Deferred(function(c) {
                        m.each(b,
                        function(b, f) {
                            var g = m.isFunction(a[b]) && a[b];
                            e[f[1]](function() {
                                var a = g && g.apply(this, arguments);
                                if (a && m.isFunction(a.promise)) {
                                    a.promise().done(c.resolve).fail(c.reject).progress(c.notify)
                                } else {
                                    c[f[0] + "With"](this === d ? c.promise() : this, g ? [a] : arguments)
                                }
                            })
                        });
                        a = null
                    }).promise()
                },
                promise: function(a) {
                    return a != null ? m.extend(a, d) : d
                }
            },
            e = {};
            d.pipe = d.then;
            m.each(b,
            function(a, f) {
                var g = f[2],
                h = f[3];
                d[f[1]] = g.add;
                if (h) {
                    g.add(function() {
                        c = h
                    },
                    b[a ^ 1][2].disable, b[2][2].lock)
                }
                e[f[0]] = function() {
                    e[f[0] + "With"](this === e ? d: this, arguments);
                    return this
                };
                e[f[0] + "With"] = g.fireWith
            });
            d.promise(e);
            if (a) {
                a.call(e, e)
            }
            return e
        },
        when: function(a) {
            var b = 0,
            c = d.call(arguments),
            e = c.length,
            f = e !== 1 || a && m.isFunction(a.promise) ? e: 0,
            g = f === 1 ? a: m.Deferred(),
            h = function(a, b, c) {
                return function(e) {
                    b[a] = this;
                    c[a] = arguments.length > 1 ? d.call(arguments) : e;
                    if (c === i) {
                        g.notifyWith(b, c)
                    } else if (!--f) {
                        g.resolveWith(b, c)
                    }
                }
            },
            i,
            j,
            k;
            if (e > 1) {
                i = new Array(e);
                j = new Array(e);
                k = new Array(e);
                for (; b < e; b++) {
                    if (c[b] && m.isFunction(c[b].promise)) {
                        c[b].promise().done(h(b, k, c)).fail(g.reject).progress(h(b, j, i))
                    } else {--f
                    }
                }
            }
            if (!f) {
                g.resolveWith(k, c)
            }
            return g.promise()
        }
    });
    var H;
    m.fn.ready = function(a) {
        m.ready.promise().done(a);
        return this
    };
    m.extend({
        isReady: false,
        readyWait: 1,
        holdReady: function(a) {
            if (a) {
                m.readyWait++
            } else {
                m.ready(true)
            }
        },
        ready: function(a) {
            if (a === true ? --m.readyWait: m.isReady) {
                return
            }
            if (!y.body) {
                return setTimeout(m.ready)
            }
            m.isReady = true;
            if (a !== true && --m.readyWait > 0) {
                return
            }
            H.resolveWith(y, [m]);
            if (m.fn.triggerHandler) {
                m(y).triggerHandler("ready");
                m(y).off("ready")
            }
        }
    });
    function I() {
        if (y.addEventListener) {
            y.removeEventListener("DOMContentLoaded", J, false);
            a.removeEventListener("load", J, false)
        } else {
            y.detachEvent("onreadystatechange", J);
            a.detachEvent("onload", J)
        }
    }
    function J() {
        if (y.addEventListener || event.type === "load" || y.readyState === "complete") {
            I();
            m.ready()
        }
    }
    m.ready.promise = function(b) {
        if (!H) {
            H = m.Deferred();
            if (y.readyState === "complete") {
                setTimeout(m.ready)
            } else if (y.addEventListener) {
                y.addEventListener("DOMContentLoaded", J, false);
                a.addEventListener("load", J, false)
            } else {
                y.attachEvent("onreadystatechange", J);
                a.attachEvent("onload", J);
                var c = false;
                try {
                    c = a.frameElement == null && y.documentElement
                } catch(d) {}
                if (c && c.doScroll) { (function e() {
                        if (!m.isReady) {
                            try {
                                c.doScroll("left")
                            } catch(a) {
                                return setTimeout(e, 50)
                            }
                            I();
                            m.ready()
                        }
                    })()
                }
            }
        }
        return H.promise(b)
    };
    var K = typeof undefined;
    var L;
    for (L in m(k)) {
        break
    }
    k.ownLast = L !== "0";
    k.inlineBlockNeedsLayout = false;
    m(function() {
        var a, b, c, d;
        c = y.getElementsByTagName("body")[0];
        if (!c || !c.style) {
            return
        }
        b = y.createElement("div");
        d = y.createElement("div");
        d.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px";
        c.appendChild(d).appendChild(b);
        if (typeof b.style.zoom !== K) {
            b.style.cssText = "display:inline;margin:0;border:0;padding:1px;width:1px;zoom:1";
            k.inlineBlockNeedsLayout = a = b.offsetWidth === 3;
            if (a) {
                c.style.zoom = 1
            }
        }
        c.removeChild(d)
    }); (function() {
        var a = y.createElement("div");
        if (k.deleteExpando == null) {
            k.deleteExpando = true;
            try {
                delete a.test
            } catch(b) {
                k.deleteExpando = false
            }
        }
        a = null
    })();
    m.acceptData = function(a) {
        var b = m.noData[(a.nodeName + " ").toLowerCase()],
        c = +a.nodeType || 1;
        return c !== 1 && c !== 9 ? false: !b || b !== true && a.getAttribute("classid") === b
    };
    var M = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/,
    N = /([A-Z])/g;
    function O(a, b, c) {
        if (c === undefined && a.nodeType === 1) {
            var d = "data-" + b.replace(N, "-$1").toLowerCase();
            c = a.getAttribute(d);
            if (typeof c === "string") {
                try {
                    c = c === "true" ? true: c === "false" ? false: c === "null" ? null: +c + "" === c ? +c: M.test(c) ? m.parseJSON(c) : c
                } catch(e) {}
                m.data(a, b, c)
            } else {
                c = undefined
            }
        }
        return c
    }
    function P(a) {
        var b;
        for (b in a) {
            if (b === "data" && m.isEmptyObject(a[b])) {
                continue
            }
            if (b !== "toJSON") {
                return false
            }
        }
        return true
    }
    function Q(a, b, d, e) {
        if (!m.acceptData(a)) {
            return
        }
        var f, g, h = m.expando,
        i = a.nodeType,
        j = i ? m.cache: a,
        k = i ? a[h] : a[h] && h;
        if ((!k || !j[k] || !e && !j[k].data) && d === undefined && typeof b === "string") {
            return
        }
        if (!k) {
            if (i) {
                k = a[h] = c.pop() || m.guid++
            } else {
                k = h
            }
        }
        if (!j[k]) {
            j[k] = i ? {}: {
                toJSON: m.noop
            }
        }
        if (typeof b === "object" || typeof b === "function") {
            if (e) {
                j[k] = m.extend(j[k], b)
            } else {
                j[k].data = m.extend(j[k].data, b)
            }
        }
        g = j[k];
        if (!e) {
            if (!g.data) {
                g.data = {}
            }
            g = g.data
        }
        if (d !== undefined) {
            g[m.camelCase(b)] = d
        }
        if (typeof b === "string") {
            f = g[b];
            if (f == null) {
                f = g[m.camelCase(b)]
            }
        } else {
            f = g
        }
        return f
    }
    function R(a, b, c) {
        if (!m.acceptData(a)) {
            return
        }
        var d, e, f = a.nodeType,
        g = f ? m.cache: a,
        h = f ? a[m.expando] : m.expando;
        if (!g[h]) {
            return
        }
        if (b) {
            d = c ? g[h] : g[h].data;
            if (d) {
                if (!m.isArray(b)) {
                    if (b in d) {
                        b = [b]
                    } else {
                        b = m.camelCase(b);
                        if (b in d) {
                            b = [b]
                        } else {
                            b = b.split(" ")
                        }
                    }
                } else {
                    b = b.concat(m.map(b, m.camelCase))
                }
                e = b.length;
                while (e--) {
                    delete d[b[e]]
                }
                if (c ? !P(d) : !m.isEmptyObject(d)) {
                    return
                }
            }
        }
        if (!c) {
            delete g[h].data;
            if (!P(g[h])) {
                return
            }
        }
        if (f) {
            m.cleanData([a], true)
        } else if (k.deleteExpando || g != g.window) {
            delete g[h]
        } else {
            g[h] = null
        }
    }
    m.extend({
        cache: {},
        noData: {
            "applet ": true,
            "embed ": true,
            "object ": "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
        },
        hasData: function(a) {
            a = a.nodeType ? m.cache[a[m.expando]] : a[m.expando];
            return !! a && !P(a)
        },
        data: function(a, b, c) {
            return Q(a, b, c)
        },
        removeData: function(a, b) {
            return R(a, b)
        },
        _data: function(a, b, c) {
            return Q(a, b, c, true)
        },
        _removeData: function(a, b) {
            return R(a, b, true)
        }
    });
    m.fn.extend({
        data: function(a, b) {
            var c, d, e, f = this[0],
            g = f && f.attributes;
            if (a === undefined) {
                if (this.length) {
                    e = m.data(f);
                    if (f.nodeType === 1 && !m._data(f, "parsedAttrs")) {
                        c = g.length;
                        while (c--) {
                            if (g[c]) {
                                d = g[c].name;
                                if (d.indexOf("data-") === 0) {
                                    d = m.camelCase(d.slice(5));
                                    O(f, d, e[d])
                                }
                            }
                        }
                        m._data(f, "parsedAttrs", true)
                    }
                }
                return e
            }
            if (typeof a === "object") {
                return this.each(function() {
                    m.data(this, a)
                })
            }
            return arguments.length > 1 ? this.each(function() {
                m.data(this, a, b)
            }) : f ? O(f, a, m.data(f, a)) : undefined
        },
        removeData: function(a) {
            return this.each(function() {
                m.removeData(this, a)
            })
        }
    });
    m.extend({
        queue: function(a, b, c) {
            var d;
            if (a) {
                b = (b || "fx") + "queue";
                d = m._data(a, b);
                if (c) {
                    if (!d || m.isArray(c)) {
                        d = m._data(a, b, m.makeArray(c))
                    } else {
                        d.push(c)
                    }
                }
                return d || []
            }
        },
        dequeue: function(a, b) {
            b = b || "fx";
            var c = m.queue(a, b),
            d = c.length,
            e = c.shift(),
            f = m._queueHooks(a, b),
            g = function() {
                m.dequeue(a, b)
            };
            if (e === "inprogress") {
                e = c.shift();
                d--
            }
            if (e) {
                if (b === "fx") {
                    c.unshift("inprogress")
                }
                delete f.stop;
                e.call(a, g, f)
            }
            if (!d && f) {
                f.empty.fire()
            }
        },
        _queueHooks: function(a, b) {
            var c = b + "queueHooks";
            return m._data(a, c) || m._data(a, c, {
                empty: m.Callbacks("once memory").add(function() {
                    m._removeData(a, b + "queue");
                    m._removeData(a, c)
                })
            })
        }
    });
    m.fn.extend({
        queue: function(a, b) {
            var c = 2;
            if (typeof a !== "string") {
                b = a;
                a = "fx";
                c--
            }
            if (arguments.length < c) {
                return m.queue(this[0], a)
            }
            return b === undefined ? this: this.each(function() {
                var c = m.queue(this, a, b);
                m._queueHooks(this, a);
                if (a === "fx" && c[0] !== "inprogress") {
                    m.dequeue(this, a)
                }
            })
        },
        dequeue: function(a) {
            return this.each(function() {
                m.dequeue(this, a)
            })
        },
        clearQueue: function(a) {
            return this.queue(a || "fx", [])
        },
        promise: function(a, b) {
            var c, d = 1,
            e = m.Deferred(),
            f = this,
            g = this.length,
            h = function() {
                if (!--d) {
                    e.resolveWith(f, [f])
                }
            };
            if (typeof a !== "string") {
                b = a;
                a = undefined
            }
            a = a || "fx";
            while (g--) {
                c = m._data(f[g], a + "queueHooks");
                if (c && c.empty) {
                    d++;
                    c.empty.add(h)
                }
            }
            h();
            return e.promise(b)
        }
    });
    var S = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source;
    var T = ["Top", "Right", "Bottom", "Left"];
    var U = function(a, b) {
        a = b || a;
        return m.css(a, "display") === "none" || !m.contains(a.ownerDocument, a)
    };
    var V = m.access = function(a, b, c, d, e, f, g) {
        var h = 0,
        i = a.length,
        j = c == null;
        if (m.type(c) === "object") {
            e = true;
            for (h in c) {
                m.access(a, b, h, c[h], true, f, g)
            }
        } else if (d !== undefined) {
            e = true;
            if (!m.isFunction(d)) {
                g = true
            }
            if (j) {
                if (g) {
                    b.call(a, d);
                    b = null
                } else {
                    j = b;
                    b = function(a, b, c) {
                        return j.call(m(a), c)
                    }
                }
            }
            if (b) {
                for (; h < i; h++) {
                    b(a[h], c, g ? d: d.call(a[h], h, b(a[h], c)))
                }
            }
        }
        return e ? a: j ? b.call(a) : i ? b(a[0], c) : f
    };
    var W = /^(?:checkbox|radio)$/i; (function() {
        var a = y.createElement("input"),
        b = y.createElement("div"),
        c = y.createDocumentFragment();
        b.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>";
        k.leadingWhitespace = b.firstChild.nodeType === 3;
        k.tbody = !b.getElementsByTagName("tbody").length;
        k.htmlSerialize = !!b.getElementsByTagName("link").length;
        k.html5Clone = y.createElement("nav").cloneNode(true).outerHTML !== "<:nav></:nav>";
        a.type = "checkbox";
        a.checked = true;
        c.appendChild(a);
        k.appendChecked = a.checked;
        b.innerHTML = "<textarea>x</textarea>";
        k.noCloneChecked = !!b.cloneNode(true).lastChild.defaultValue;
        c.appendChild(b);
        b.innerHTML = "<input type='radio' checked='checked' name='t'/>";
        k.checkClone = b.cloneNode(true).cloneNode(true).lastChild.checked;
        k.noCloneEvent = true;
        if (b.attachEvent) {
            b.attachEvent("onclick",
            function() {
                k.noCloneEvent = false
            });
            b.cloneNode(true).click()
        }
        if (k.deleteExpando == null) {
            k.deleteExpando = true;
            try {
                delete b.test
            } catch(d) {
                k.deleteExpando = false
            }
        }
    })(); (function() {
        var b, c, d = y.createElement("div");
        for (b in {
            submit: true,
            change: true,
            focusin: true
        }) {
            c = "on" + b;
            if (! (k[b + "Bubbles"] = c in a)) {
                d.setAttribute(c, "t");
                k[b + "Bubbles"] = d.attributes[c].expando === false
            }
        }
        d = null
    })();
    var X = /^(?:input|select|textarea)$/i,
    Y = /^key/,
    Z = /^(?:mouse|pointer|contextmenu)|click/,
    $ = /^(?:focusinfocus|focusoutblur)$/,
    _ = /^([^.]*)(?:\.(.+)|)$/;
    function aa() {
        return true
    }
    function ba() {
        return false
    }
    function ca() {
        try {
            return y.activeElement
        } catch(a) {}
    }
    m.event = {
        global: {},
        add: function(a, b, c, d, e) {
            var f, g, h, i, j, k, l, n, o, p, q, r = m._data(a);
            if (!r) {
                return
            }
            if (c.handler) {
                i = c;
                c = i.handler;
                e = i.selector
            }
            if (!c.guid) {
                c.guid = m.guid++
            }
            if (! (g = r.events)) {
                g = r.events = {}
            }
            if (! (k = r.handle)) {
                k = r.handle = function(a) {
                    return typeof m !== K && (!a || m.event.triggered !== a.type) ? m.event.dispatch.apply(k.elem, arguments) : undefined
                };
                k.elem = a
            }
            b = (b || "").match(E) || [""];
            h = b.length;
            while (h--) {
                f = _.exec(b[h]) || [];
                o = q = f[1];
                p = (f[2] || "").split(".").sort();
                if (!o) {
                    continue
                }
                j = m.event.special[o] || {};
                o = (e ? j.delegateType: j.bindType) || o;
                j = m.event.special[o] || {};
                l = m.extend({
                    type: o,
                    origType: q,
                    data: d,
                    handler: c,
                    guid: c.guid,
                    selector: e,
                    needsContext: e && m.expr.match.needsContext.test(e),
                    namespace: p.join(".")
                },
                i);
                if (! (n = g[o])) {
                    n = g[o] = [];
                    n.delegateCount = 0;
                    if (!j.setup || j.setup.call(a, d, p, k) === false) {
                        if (a.addEventListener) {
                            a.addEventListener(o, k, false)
                        } else if (a.attachEvent) {
                            a.attachEvent("on" + o, k)
                        }
                    }
                }
                if (j.add) {
                    j.add.call(a, l);
                    if (!l.handler.guid) {
                        l.handler.guid = c.guid
                    }
                }
                if (e) {
                    n.splice(n.delegateCount++, 0, l)
                } else {
                    n.push(l)
                }
                m.event.global[o] = true
            }
            a = null
        },
        remove: function(a, b, c, d, e) {
            var f, g, h, i, j, k, l, n, o, p, q, r = m.hasData(a) && m._data(a);
            if (!r || !(k = r.events)) {
                return
            }
            b = (b || "").match(E) || [""];
            j = b.length;
            while (j--) {
                h = _.exec(b[j]) || [];
                o = q = h[1];
                p = (h[2] || "").split(".").sort();
                if (!o) {
                    for (o in k) {
                        m.event.remove(a, o + b[j], c, d, true)
                    }
                    continue
                }
                l = m.event.special[o] || {};
                o = (d ? l.delegateType: l.bindType) || o;
                n = k[o] || [];
                h = h[2] && new RegExp("(^|\\.)" + p.join("\\.(?:.*\\.|)") + "(\\.|$)");
                i = f = n.length;
                while (f--) {
                    g = n[f];
                    if ((e || q === g.origType) && (!c || c.guid === g.guid) && (!h || h.test(g.namespace)) && (!d || d === g.selector || d === "**" && g.selector)) {
                        n.splice(f, 1);
                        if (g.selector) {
                            n.delegateCount--
                        }
                        if (l.remove) {
                            l.remove.call(a, g)
                        }
                    }
                }
                if (i && !n.length) {
                    if (!l.teardown || l.teardown.call(a, p, r.handle) === false) {
                        m.removeEvent(a, o, r.handle)
                    }
                    delete k[o]
                }
            }
            if (m.isEmptyObject(k)) {
                delete r.handle;
                m._removeData(a, "events")
            }
        },
        trigger: function(b, c, d, e) {
            var f, g, h, i, k, l, n, o = [d || y],
            p = j.call(b, "type") ? b.type: b,
            q = j.call(b, "namespace") ? b.namespace.split(".") : [];
            h = l = d = d || y;
            if (d.nodeType === 3 || d.nodeType === 8) {
                return
            }
            if ($.test(p + m.event.triggered)) {
                return
            }
            if (p.indexOf(".") >= 0) {
                q = p.split(".");
                p = q.shift();
                q.sort()
            }
            g = p.indexOf(":") < 0 && "on" + p;
            b = b[m.expando] ? b: new m.Event(p, typeof b === "object" && b);
            b.isTrigger = e ? 2 : 3;
            b.namespace = q.join(".");
            b.namespace_re = b.namespace ? new RegExp("(^|\\.)" + q.join("\\.(?:.*\\.|)") + "(\\.|$)") : null;
            b.result = undefined;
            if (!b.target) {
                b.target = d
            }
            c = c == null ? [b] : m.makeArray(c, [b]);
            k = m.event.special[p] || {};
            if (!e && k.trigger && k.trigger.apply(d, c) === false) {
                return
            }
            if (!e && !k.noBubble && !m.isWindow(d)) {
                i = k.delegateType || p;
                if (!$.test(i + p)) {
                    h = h.parentNode
                }
                for (; h; h = h.parentNode) {
                    o.push(h);
                    l = h
                }
                if (l === (d.ownerDocument || y)) {
                    o.push(l.defaultView || l.parentWindow || a)
                }
            }
            n = 0;
            while ((h = o[n++]) && !b.isPropagationStopped()) {
                b.type = n > 1 ? i: k.bindType || p;
                f = (m._data(h, "events") || {})[b.type] && m._data(h, "handle");
                if (f) {
                    f.apply(h, c)
                }
                f = g && h[g];
                if (f && f.apply && m.acceptData(h)) {
                    b.result = f.apply(h, c);
                    if (b.result === false) {
                        b.preventDefault()
                    }
                }
            }
            b.type = p;
            if (!e && !b.isDefaultPrevented()) {
                if ((!k._default || k._default.apply(o.pop(), c) === false) && m.acceptData(d)) {
                    if (g && d[p] && !m.isWindow(d)) {
                        l = d[g];
                        if (l) {
                            d[g] = null
                        }
                        m.event.triggered = p;
                        try {
                            d[p]()
                        } catch(r) {}
                        m.event.triggered = undefined;
                        if (l) {
                            d[g] = l
                        }
                    }
                }
            }
            return b.result
        },
        dispatch: function(a) {
            a = m.event.fix(a);
            var b, c, e, f, g, h = [],
            i = d.call(arguments),
            j = (m._data(this, "events") || {})[a.type] || [],
            k = m.event.special[a.type] || {};
            i[0] = a;
            a.delegateTarget = this;
            if (k.preDispatch && k.preDispatch.call(this, a) === false) {
                return
            }
            h = m.event.handlers.call(this, a, j);
            b = 0;
            while ((f = h[b++]) && !a.isPropagationStopped()) {
                a.currentTarget = f.elem;
                g = 0;
                while ((e = f.handlers[g++]) && !a.isImmediatePropagationStopped()) {
                    if (!a.namespace_re || a.namespace_re.test(e.namespace)) {
                        a.handleObj = e;
                        a.data = e.data;
                        c = ((m.event.special[e.origType] || {}).handle || e.handler).apply(f.elem, i);
                        if (c !== undefined) {
                            if ((a.result = c) === false) {
                                a.preventDefault();
                                a.stopPropagation()
                            }
                        }
                    }
                }
            }
            if (k.postDispatch) {
                k.postDispatch.call(this, a)
            }
            return a.result
        },
        handlers: function(a, b) {
            var c, d, e, f, g = [],
            h = b.delegateCount,
            i = a.target;
            if (h && i.nodeType && (!a.button || a.type !== "click")) {
                for (; i != this; i = i.parentNode || this) {
                    if (i.nodeType === 1 && (i.disabled !== true || a.type !== "click")) {
                        e = [];
                        for (f = 0; f < h; f++) {
                            d = b[f];
                            c = d.selector + " ";
                            if (e[c] === undefined) {
                                e[c] = d.needsContext ? m(c, this).index(i) >= 0 : m.find(c, this, null, [i]).length
                            }
                            if (e[c]) {
                                e.push(d)
                            }
                        }
                        if (e.length) {
                            g.push({
                                elem: i,
                                handlers: e
                            })
                        }
                    }
                }
            }
            if (h < b.length) {
                g.push({
                    elem: this,
                    handlers: b.slice(h)
                })
            }
            return g
        },
        fix: function(a) {
            if (a[m.expando]) {
                return a
            }
            var b, c, d, e = a.type,
            f = a,
            g = this.fixHooks[e];
            if (!g) {
                this.fixHooks[e] = g = Z.test(e) ? this.mouseHooks: Y.test(e) ? this.keyHooks: {}
            }
            d = g.props ? this.props.concat(g.props) : this.props;
            a = new m.Event(f);
            b = d.length;
            while (b--) {
                c = d[b];
                a[c] = f[c]
            }
            if (!a.target) {
                a.target = f.srcElement || y
            }
            if (a.target.nodeType === 3) {
                a.target = a.target.parentNode
            }
            a.metaKey = !!a.metaKey;
            return g.filter ? g.filter(a, f) : a
        },
        props: "altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),
        fixHooks: {},
        keyHooks: {
            props: "char charCode key keyCode".split(" "),
            filter: function(a, b) {
                if (a.which == null) {
                    a.which = b.charCode != null ? b.charCode: b.keyCode
                }
                return a
            }
        },
        mouseHooks: {
            props: "button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),
            filter: function(a, b) {
                var c, d, e, f = b.button,
                g = b.fromElement;
                if (a.pageX == null && b.clientX != null) {
                    d = a.target.ownerDocument || y;
                    e = d.documentElement;
                    c = d.body;
                    a.pageX = b.clientX + (e && e.scrollLeft || c && c.scrollLeft || 0) - (e && e.clientLeft || c && c.clientLeft || 0);
                    a.pageY = b.clientY + (e && e.scrollTop || c && c.scrollTop || 0) - (e && e.clientTop || c && c.clientTop || 0)
                }
                if (!a.relatedTarget && g) {
                    a.relatedTarget = g === a.target ? b.toElement: g
                }
                if (!a.which && f !== undefined) {
                    a.which = f & 1 ? 1 : f & 2 ? 3 : f & 4 ? 2 : 0
                }
                return a
            }
        },
        special: {
            load: {
                noBubble: true
            },
            focus: {
                trigger: function() {
                    if (this !== ca() && this.focus) {
                        try {
                            this.focus();
                            return false
                        } catch(a) {}
                    }
                },
                delegateType: "focusin"
            },
            blur: {
                trigger: function() {
                    if (this === ca() && this.blur) {
                        this.blur();
                        return false
                    }
                },
                delegateType: "focusout"
            },
            click: {
                trigger: function() {
                    if (m.nodeName(this, "input") && this.type === "checkbox" && this.click) {
                        this.click();
                        return false
                    }
                },
                _default: function(a) {
                    return m.nodeName(a.target, "a")
                }
            },
            beforeunload: {
                postDispatch: function(a) {
                    if (a.result !== undefined && a.originalEvent) {
                        a.originalEvent.returnValue = a.result
                    }
                }
            }
        },
        simulate: function(a, b, c, d) {
            var e = m.extend(new m.Event, c, {
                type: a,
                isSimulated: true,
                originalEvent: {}
            });
            if (d) {
                m.event.trigger(e, null, b)
            } else {
                m.event.dispatch.call(b, e)
            }
            if (e.isDefaultPrevented()) {
                c.preventDefault()
            }
        }
    };
    m.removeEvent = y.removeEventListener ?
    function(a, b, c) {
        if (a.removeEventListener) {
            a.removeEventListener(b, c, false)
        }
    }: function(a, b, c) {
        var d = "on" + b;
        if (a.detachEvent) {
            if (typeof a[d] === K) {
                a[d] = null
            }
            a.detachEvent(d, c)
        }
    };
    m.Event = function(a, b) {
        if (! (this instanceof m.Event)) {
            return new m.Event(a, b)
        }
        if (a && a.type) {
            this.originalEvent = a;
            this.type = a.type;
            this.isDefaultPrevented = a.defaultPrevented || a.defaultPrevented === undefined && a.returnValue === false ? aa: ba
        } else {
            this.type = a
        }
        if (b) {
            m.extend(this, b)
        }
        this.timeStamp = a && a.timeStamp || m.now();
        this[m.expando] = true
    };
    m.Event.prototype = {
        isDefaultPrevented: ba,
        isPropagationStopped: ba,
        isImmediatePropagationStopped: ba,
        preventDefault: function() {
            var a = this.originalEvent;
            this.isDefaultPrevented = aa;
            if (!a) {
                return
            }
            if (a.preventDefault) {
                a.preventDefault()
            } else {
                a.returnValue = false
            }
        },
        stopPropagation: function() {
            var a = this.originalEvent;
            this.isPropagationStopped = aa;
            if (!a) {
                return
            }
            if (a.stopPropagation) {
                a.stopPropagation()
            }
            a.cancelBubble = true
        },
        stopImmediatePropagation: function() {
            var a = this.originalEvent;
            this.isImmediatePropagationStopped = aa;
            if (a && a.stopImmediatePropagation) {
                a.stopImmediatePropagation()
            }
            this.stopPropagation()
        }
    };
    m.each({
        mouseenter: "mouseover",
        mouseleave: "mouseout",
        pointerenter: "pointerover",
        pointerleave: "pointerout"
    },
    function(a, b) {
        m.event.special[a] = {
            delegateType: b,
            bindType: b,
            handle: function(a) {
                var c, d = this,
                e = a.relatedTarget,
                f = a.handleObj;
                if (!e || e !== d && !m.contains(d, e)) {
                    a.type = f.origType;
                    c = f.handler.apply(this, arguments);
                    a.type = b
                }
                return c
            }
        }
    });
    if (!k.submitBubbles) {
        m.event.special.submit = {
            setup: function() {
                if (m.nodeName(this, "form")) {
                    return false
                }
                m.event.add(this, "click._submit keypress._submit",
                function(a) {
                    var b = a.target,
                    c = m.nodeName(b, "input") || m.nodeName(b, "button") ? b.form: undefined;
                    if (c && !m._data(c, "submitBubbles")) {
                        m.event.add(c, "submit._submit",
                        function(a) {
                            a._submit_bubble = true
                        });
                        m._data(c, "submitBubbles", true)
                    }
                })
            },
            postDispatch: function(a) {
                if (a._submit_bubble) {
                    delete a._submit_bubble;
                    if (this.parentNode && !a.isTrigger) {
                        m.event.simulate("submit", this.parentNode, a, true)
                    }
                }
            },
            teardown: function() {
                if (m.nodeName(this, "form")) {
                    return false
                }
                m.event.remove(this, "._submit")
            }
        }
    }
    if (!k.changeBubbles) {
        m.event.special.change = {
            setup: function() {
                if (X.test(this.nodeName)) {
                    if (this.type === "checkbox" || this.type === "radio") {
                        m.event.add(this, "propertychange._change",
                        function(a) {
                            if (a.originalEvent.propertyName === "checked") {
                                this._just_changed = true
                            }
                        });
                        m.event.add(this, "click._change",
                        function(a) {
                            if (this._just_changed && !a.isTrigger) {
                                this._just_changed = false
                            }
                            m.event.simulate("change", this, a, true)
                        })
                    }
                    return false
                }
                m.event.add(this, "beforeactivate._change",
                function(a) {
                    var b = a.target;
                    if (X.test(b.nodeName) && !m._data(b, "changeBubbles")) {
                        m.event.add(b, "change._change",
                        function(a) {
                            if (this.parentNode && !a.isSimulated && !a.isTrigger) {
                                m.event.simulate("change", this.parentNode, a, true)
                            }
                        });
                        m._data(b, "changeBubbles", true)
                    }
                })
            },
            handle: function(a) {
                var b = a.target;
                if (this !== b || a.isSimulated || a.isTrigger || b.type !== "radio" && b.type !== "checkbox") {
                    return a.handleObj.handler.apply(this, arguments)
                }
            },
            teardown: function() {
                m.event.remove(this, "._change");
                return ! X.test(this.nodeName)
            }
        }
    }
    if (!k.focusinBubbles) {
        m.each({
            focus: "focusin",
            blur: "focusout"
        },
        function(a, b) {
            var c = function(a) {
                m.event.simulate(b, a.target, m.event.fix(a), true)
            };
            m.event.special[b] = {
                setup: function() {
                    var d = this.ownerDocument || this,
                    e = m._data(d, b);
                    if (!e) {
                        d.addEventListener(a, c, true)
                    }
                    m._data(d, b, (e || 0) + 1)
                },
                teardown: function() {
                    var d = this.ownerDocument || this,
                    e = m._data(d, b) - 1;
                    if (!e) {
                        d.removeEventListener(a, c, true);
                        m._removeData(d, b)
                    } else {
                        m._data(d, b, e)
                    }
                }
            }
        })
    }
    m.fn.extend({
        on: function(a, b, c, d, e) {
            var f, g;
            if (typeof a === "object") {
                if (typeof b !== "string") {
                    c = c || b;
                    b = undefined
                }
                for (f in a) {
                    this.on(f, b, c, a[f], e)
                }
                return this;
            }
            if (c == null && d == null) {
                d = b;
                c = b = undefined
            } else if (d == null) {
                if (typeof b === "string") {
                    d = c;
                    c = undefined
                } else {
                    d = c;
                    c = b;
                    b = undefined
                }
            }
            if (d === false) {
                d = ba
            } else if (!d) {
                return this
            }
            if (e === 1) {
                g = d;
                d = function(a) {
                    m().off(a);
                    return g.apply(this, arguments)
                };
                d.guid = g.guid || (g.guid = m.guid++)
            }
            return this.each(function() {
                m.event.add(this, a, d, c, b)
            })
        },
        one: function(a, b, c, d) {
            return this.on(a, b, c, d, 1)
        },
        off: function(a, b, c) {
            var d, e;
            if (a && a.preventDefault && a.handleObj) {
                d = a.handleObj;
                m(a.delegateTarget).off(d.namespace ? d.origType + "." + d.namespace: d.origType, d.selector, d.handler);
                return this
            }
            if (typeof a === "object") {
                for (e in a) {
                    this.off(e, b, a[e])
                }
                return this
            }
            if (b === false || typeof b === "function") {
                c = b;
                b = undefined
            }
            if (c === false) {
                c = ba
            }
            return this.each(function() {
                m.event.remove(this, a, c, b)
            })
        },
        trigger: function(a, b) {
            return this.each(function() {
                m.event.trigger(a, b, this)
            })
        },
        triggerHandler: function(a, b) {
            var c = this[0];
            if (c) {
                return m.event.trigger(a, b, c, true)
            }
        }
    });
    function da(a) {
        var b = ea.split("|"),
        c = a.createDocumentFragment();
        if (c.createElement) {
            while (b.length) {
                c.createElement(b.pop())
            }
        }
        return c
    }
    var ea = "abbr|article|aside|audio|bdi|canvas|data|datalist|details|figcaption|figure|footer|" + "header|hgroup|mark|meter|nav|output|progress|section|summary|time|video",
    fa = / jQuery\d+="(?:null|\d+)"/g,
    ga = new RegExp("<(?:" + ea + ")[\\s/>]", "i"),
    ha = /^\s+/,
    ia = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi,
    ja = /<([\w:]+)/,
    ka = /<tbody/i,
    la = /<|&#?\w+;/,
    ma = /<(?:script|style|link)/i,
    na = /checked\s*(?:[^=]|=\s*.checked.)/i,
    oa = /^$|\/(?:java|ecma)script/i,
    pa = /^true\/(.*)/,
    qa = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g,
    ra = {
        option: [1, "<select multiple='multiple'>", "</select>"],
        legend: [1, "<fieldset>", "</fieldset>"],
        area: [1, "<map>", "</map>"],
        param: [1, "<object>", "</object>"],
        thead: [1, "<table>", "</table>"],
        tr: [2, "<table><tbody>", "</tbody></table>"],
        col: [2, "<table><tbody></tbody><colgroup>", "</colgroup></table>"],
        td: [3, "<table><tbody><tr>", "</tr></tbody></table>"],
        _default: k.htmlSerialize ? [0, "", ""] : [1, "X<div>", "</div>"]
    },
    sa = da(y),
    ta = sa.appendChild(y.createElement("div"));
    ra.optgroup = ra.option;
    ra.tbody = ra.tfoot = ra.colgroup = ra.caption = ra.thead;
    ra.th = ra.td;
    function ua(a, b) {
        var c, d, e = 0,
        f = typeof a.getElementsByTagName !== K ? a.getElementsByTagName(b || "*") : typeof a.querySelectorAll !== K ? a.querySelectorAll(b || "*") : undefined;
        if (!f) {
            for (f = [], c = a.childNodes || a; (d = c[e]) != null; e++) {
                if (!b || m.nodeName(d, b)) {
                    f.push(d)
                } else {
                    m.merge(f, ua(d, b))
                }
            }
        }
        return b === undefined || b && m.nodeName(a, b) ? m.merge([a], f) : f
    }
    function va(a) {
        if (W.test(a.type)) {
            a.defaultChecked = a.checked
        }
    }
    function wa(a, b) {
        return m.nodeName(a, "table") && m.nodeName(b.nodeType !== 11 ? b: b.firstChild, "tr") ? a.getElementsByTagName("tbody")[0] || a.appendChild(a.ownerDocument.createElement("tbody")) : a
    }
    function xa(a) {
        a.type = (m.find.attr(a, "type") !== null) + "/" + a.type;
        return a
    }
    function ya(a) {
        var b = pa.exec(a.type);
        if (b) {
            a.type = b[1]
        } else {
            a.removeAttribute("type")
        }
        return a
    }
    function za(a, b) {
        var c, d = 0;
        for (; (c = a[d]) != null; d++) {
            m._data(c, "globalEval", !b || m._data(b[d], "globalEval"))
        }
    }
    function Aa(a, b) {
        if (b.nodeType !== 1 || !m.hasData(a)) {
            return
        }
        var c, d, e, f = m._data(a),
        g = m._data(b, f),
        h = f.events;
        if (h) {
            delete g.handle;
            g.events = {};
            for (c in h) {
                for (d = 0, e = h[c].length; d < e; d++) {
                    m.event.add(b, c, h[c][d])
                }
            }
        }
        if (g.data) {
            g.data = m.extend({},
            g.data)
        }
    }
    function Ba(a, b) {
        var c, d, e;
        if (b.nodeType !== 1) {
            return
        }
        c = b.nodeName.toLowerCase();
        if (!k.noCloneEvent && b[m.expando]) {
            e = m._data(b);
            for (d in e.events) {
                m.removeEvent(b, d, e.handle)
            }
            b.removeAttribute(m.expando)
        }
        if (c === "script" && b.text !== a.text) {
            xa(b).text = a.text;
            ya(b)
        } else if (c === "object") {
            if (b.parentNode) {
                b.outerHTML = a.outerHTML
            }
            if (k.html5Clone && (a.innerHTML && !m.trim(b.innerHTML))) {
                b.innerHTML = a.innerHTML
            }
        } else if (c === "input" && W.test(a.type)) {
            b.defaultChecked = b.checked = a.checked;
            if (b.value !== a.value) {
                b.value = a.value
            }
        } else if (c === "option") {
            b.defaultSelected = b.selected = a.defaultSelected
        } else if (c === "input" || c === "textarea") {
            b.defaultValue = a.defaultValue
        }
    }
    m.extend({
        clone: function(a, b, c) {
            var d, e, f, g, h, i = m.contains(a.ownerDocument, a);
            if (k.html5Clone || m.isXMLDoc(a) || !ga.test("<" + a.nodeName + ">")) {
                f = a.cloneNode(true)
            } else {
                ta.innerHTML = a.outerHTML;
                ta.removeChild(f = ta.firstChild)
            }
            if ((!k.noCloneEvent || !k.noCloneChecked) && (a.nodeType === 1 || a.nodeType === 11) && !m.isXMLDoc(a)) {
                d = ua(f);
                h = ua(a);
                for (g = 0; (e = h[g]) != null; ++g) {
                    if (d[g]) {
                        Ba(e, d[g])
                    }
                }
            }
            if (b) {
                if (c) {
                    h = h || ua(a);
                    d = d || ua(f);
                    for (g = 0; (e = h[g]) != null; g++) {
                        Aa(e, d[g])
                    }
                } else {
                    Aa(a, f)
                }
            }
            d = ua(f, "script");
            if (d.length > 0) {
                za(d, !i && ua(a, "script"))
            }
            d = h = e = null;
            return f
        },
        buildFragment: function(a, b, c, d) {
            var e, f, g, h, i, j, l, n = a.length,
            o = da(b),
            p = [],
            q = 0;
            for (; q < n; q++) {
                f = a[q];
                if (f || f === 0) {
                    if (m.type(f) === "object") {
                        m.merge(p, f.nodeType ? [f] : f)
                    } else if (!la.test(f)) {
                        p.push(b.createTextNode(f))
                    } else {
                        h = h || o.appendChild(b.createElement("div"));
                        i = (ja.exec(f) || ["", ""])[1].toLowerCase();
                        l = ra[i] || ra._default;
                        h.innerHTML = l[1] + f.replace(ia, "<$1></$2>") + l[2];
                        e = l[0];
                        while (e--) {
                            h = h.lastChild
                        }
                        if (!k.leadingWhitespace && ha.test(f)) {
                            p.push(b.createTextNode(ha.exec(f)[0]))
                        }
                        if (!k.tbody) {
                            f = i === "table" && !ka.test(f) ? h.firstChild: l[1] === "<table>" && !ka.test(f) ? h: 0;
                            e = f && f.childNodes.length;
                            while (e--) {
                                if (m.nodeName(j = f.childNodes[e], "tbody") && !j.childNodes.length) {
                                    f.removeChild(j)
                                }
                            }
                        }
                        m.merge(p, h.childNodes);
                        h.textContent = "";
                        while (h.firstChild) {
                            h.removeChild(h.firstChild)
                        }
                        h = o.lastChild
                    }
                }
            }
            if (h) {
                o.removeChild(h)
            }
            if (!k.appendChecked) {
                m.grep(ua(p, "input"), va)
            }
            q = 0;
            while (f = p[q++]) {
                if (d && m.inArray(f, d) !== -1) {
                    continue
                }
                g = m.contains(f.ownerDocument, f);
                h = ua(o.appendChild(f), "script");
                if (g) {
                    za(h)
                }
                if (c) {
                    e = 0;
                    while (f = h[e++]) {
                        if (oa.test(f.type || "")) {
                            c.push(f)
                        }
                    }
                }
            }
            h = null;
            return o
        },
        cleanData: function(a, b) {
            var d, e, f, g, h = 0,
            i = m.expando,
            j = m.cache,
            l = k.deleteExpando,
            n = m.event.special;
            for (; (d = a[h]) != null; h++) {
                if (b || m.acceptData(d)) {
                    f = d[i];
                    g = f && j[f];
                    if (g) {
                        if (g.events) {
                            for (e in g.events) {
                                if (n[e]) {
                                    m.event.remove(d, e)
                                } else {
                                    m.removeEvent(d, e, g.handle)
                                }
                            }
                        }
                        if (j[f]) {
                            delete j[f];
                            if (l) {
                                delete d[i]
                            } else if (typeof d.removeAttribute !== K) {
                                d.removeAttribute(i)
                            } else {
                                d[i] = null
                            }
                            c.push(f)
                        }
                    }
                }
            }
        }
    });
    m.fn.extend({
        text: function(a) {
            return V(this,
            function(a) {
                return a === undefined ? m.text(this) : this.empty().append((this[0] && this[0].ownerDocument || y).createTextNode(a))
            },
            null, a, arguments.length)
        },
        append: function() {
            return this.domManip(arguments,
            function(a) {
                if (this.nodeType === 1 || this.nodeType === 11 || this.nodeType === 9) {
                    var b = wa(this, a);
                    b.appendChild(a)
                }
            })
        },
        prepend: function() {
            return this.domManip(arguments,
            function(a) {
                if (this.nodeType === 1 || this.nodeType === 11 || this.nodeType === 9) {
                    var b = wa(this, a);
                    b.insertBefore(a, b.firstChild)
                }
            })
        },
        before: function() {
            return this.domManip(arguments,
            function(a) {
                if (this.parentNode) {
                    this.parentNode.insertBefore(a, this)
                }
            })
        },
        after: function() {
            return this.domManip(arguments,
            function(a) {
                if (this.parentNode) {
                    this.parentNode.insertBefore(a, this.nextSibling)
                }
            })
        },
        remove: function(a, b) {
            var c, d = a ? m.filter(a, this) : this,
            e = 0;
            for (; (c = d[e]) != null; e++) {
                if (!b && c.nodeType === 1) {
                    m.cleanData(ua(c))
                }
                if (c.parentNode) {
                    if (b && m.contains(c.ownerDocument, c)) {
                        za(ua(c, "script"))
                    }
                    c.parentNode.removeChild(c)
                }
            }
            return this
        },
        empty: function() {
            var a, b = 0;
            for (; (a = this[b]) != null; b++) {
                if (a.nodeType === 1) {
                    m.cleanData(ua(a, false))
                }
                while (a.firstChild) {
                    a.removeChild(a.firstChild)
                }
                if (a.options && m.nodeName(a, "select")) {
                    a.options.length = 0
                }
            }
            return this
        },
        clone: function(a, b) {
            a = a == null ? false: a;
            b = b == null ? a: b;
            return this.map(function() {
                return m.clone(this, a, b)
            })
        },
        html: function(a) {
            return V(this,
            function(a) {
                var b = this[0] || {},
                c = 0,
                d = this.length;
                if (a === undefined) {
                    return b.nodeType === 1 ? b.innerHTML.replace(fa, "") : undefined
                }
                if (typeof a === "string" && !ma.test(a) && (k.htmlSerialize || !ga.test(a)) && (k.leadingWhitespace || !ha.test(a)) && !ra[(ja.exec(a) || ["", ""])[1].toLowerCase()]) {
                    a = a.replace(ia, "<$1></$2>");
                    try {
                        for (; c < d; c++) {
                            b = this[c] || {};
                            if (b.nodeType === 1) {
                                m.cleanData(ua(b, false));
                                b.innerHTML = a
                            }
                        }
                        b = 0
                    } catch(e) {}
                }
                if (b) {
                    this.empty().append(a)
                }
            },
            null, a, arguments.length)
        },
        replaceWith: function() {
            var a = arguments[0];
            this.domManip(arguments,
            function(b) {
                a = this.parentNode;
                m.cleanData(ua(this));
                if (a) {
                    a.replaceChild(b, this)
                }
            });
            return a && (a.length || a.nodeType) ? this: this.remove()
        },
        detach: function(a) {
            return this.remove(a, true)
        },
        domManip: function(a, b) {
            a = e.apply([], a);
            var c, d, f, g, h, i, j = 0,
            l = this.length,
            n = this,
            o = l - 1,
            p = a[0],
            q = m.isFunction(p);
            if (q || l > 1 && typeof p === "string" && !k.checkClone && na.test(p)) {
                return this.each(function(c) {
                    var d = n.eq(c);
                    if (q) {
                        a[0] = p.call(this, c, d.html())
                    }
                    d.domManip(a, b)
                })
            }
            if (l) {
                i = m.buildFragment(a, this[0].ownerDocument, false, this);
                c = i.firstChild;
                if (i.childNodes.length === 1) {
                    i = c
                }
                if (c) {
                    g = m.map(ua(i, "script"), xa);
                    f = g.length;
                    for (; j < l; j++) {
                        d = i;
                        if (j !== o) {
                            d = m.clone(d, true, true);
                            if (f) {
                                m.merge(g, ua(d, "script"))
                            }
                        }
                        b.call(this[j], d, j)
                    }
                    if (f) {
                        h = g[g.length - 1].ownerDocument;
                        m.map(g, ya);
                        for (j = 0; j < f; j++) {
                            d = g[j];
                            if (oa.test(d.type || "") && !m._data(d, "globalEval") && m.contains(h, d)) {
                                if (d.src) {
                                    if (m._evalUrl) {
                                        m._evalUrl(d.src)
                                    }
                                } else {
                                    m.globalEval((d.text || d.textContent || d.innerHTML || "").replace(qa, ""))
                                }
                            }
                        }
                    }
                    i = c = null
                }
            }
            return this
        }
    });
    m.each({
        appendTo: "append",
        prependTo: "prepend",
        insertBefore: "before",
        insertAfter: "after",
        replaceAll: "replaceWith"
    },
    function(a, b) {
        m.fn[a] = function(a) {
            var c, d = 0,
            e = [],
            g = m(a),
            h = g.length - 1;
            for (; d <= h; d++) {
                c = d === h ? this: this.clone(true);
                m(g[d])[b](c);
                f.apply(e, c.get())
            }
            return this.pushStack(e)
        }
    });
    var Ca, Da = {};
    function Ea(b, c) {
        var d, e = m(c.createElement(b)).appendTo(c.body),
        f = a.getDefaultComputedStyle && (d = a.getDefaultComputedStyle(e[0])) ? d.display: m.css(e[0], "display");
        e.detach();
        return f
    }
    function Fa(a) {
        var b = y,
        c = Da[a];
        if (!c) {
            c = Ea(a, b);
            if (c === "none" || !c) {
                Ca = (Ca || m("<iframe frameborder='0' width='0' height='0'/>")).appendTo(b.documentElement);
                b = (Ca[0].contentWindow || Ca[0].contentDocument).document;
                b.write();
                b.close();
                c = Ea(a, b);
                Ca.detach()
            }
            Da[a] = c
        }
        return c
    } (function() {
        var a;
        k.shrinkWrapBlocks = function() {
            if (a != null) {
                return a
            }
            a = false;
            var b, c, d;
            c = y.getElementsByTagName("body")[0];
            if (!c || !c.style) {
                return
            }
            b = y.createElement("div");
            d = y.createElement("div");
            d.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px";
            c.appendChild(d).appendChild(b);
            if (typeof b.style.zoom !== K) {
                b.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;" + "box-sizing:content-box;display:block;margin:0;border:0;" + "padding:1px;width:1px;zoom:1";
                b.appendChild(y.createElement("div")).style.width = "5px";
                a = b.offsetWidth !== 3
            }
            c.removeChild(d);
            return a
        }
    })();
    var Ga = /^margin/;
    var Ha = new RegExp("^(" + S + ")(?!px)[a-z%]+$", "i");
    var Ia, Ja, Ka = /^(top|right|bottom|left)$/;
    if (a.getComputedStyle) {
        Ia = function(b) {
            if (b.ownerDocument.defaultView.opener) {
                return b.ownerDocument.defaultView.getComputedStyle(b, null)
            }
            return a.getComputedStyle(b, null)
        };
        Ja = function(a, b, c) {
            var d, e, f, g, h = a.style;
            c = c || Ia(a);
            g = c ? c.getPropertyValue(b) || c[b] : undefined;
            if (c) {
                if (g === "" && !m.contains(a.ownerDocument, a)) {
                    g = m.style(a, b)
                }
                if (Ha.test(g) && Ga.test(b)) {
                    d = h.width;
                    e = h.minWidth;
                    f = h.maxWidth;
                    h.minWidth = h.maxWidth = h.width = g;
                    g = c.width;
                    h.width = d;
                    h.minWidth = e;
                    h.maxWidth = f
                }
            }
            return g === undefined ? g: g + ""
        }
    } else if (y.documentElement.currentStyle) {
        Ia = function(a) {
            return a.currentStyle
        };
        Ja = function(a, b, c) {
            var d, e, f, g, h = a.style;
            c = c || Ia(a);
            g = c ? c[b] : undefined;
            if (g == null && h && h[b]) {
                g = h[b]
            }
            if (Ha.test(g) && !Ka.test(b)) {
                d = h.left;
                e = a.runtimeStyle;
                f = e && e.left;
                if (f) {
                    e.left = a.currentStyle.left
                }
                h.left = b === "fontSize" ? "1em": g;
                g = h.pixelLeft + "px";
                h.left = d;
                if (f) {
                    e.left = f
                }
            }
            return g === undefined ? g: g + "" || "auto"
        }
    }
    function La(a, b) {
        return {
            get: function() {
                var c = a();
                if (c == null) {
                    return
                }
                if (c) {
                    delete this.get;
                    return
                }
                return (this.get = b).apply(this, arguments)
            }
        }
    } (function() {
        var b, c, d, e, f, g, h;
        b = y.createElement("div");
        b.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>";
        d = b.getElementsByTagName("a")[0];
        c = d && d.style;
        if (!c) {
            return
        }
        c.cssText = "float:left;opacity:.5";
        k.opacity = c.opacity === "0.5";
        k.cssFloat = !!c.cssFloat;
        b.style.backgroundClip = "content-box";
        b.cloneNode(true).style.backgroundClip = "";
        k.clearCloneStyle = b.style.backgroundClip === "content-box";
        k.boxSizing = c.boxSizing === "" || c.MozBoxSizing === "" || c.WebkitBoxSizing === "";
        m.extend(k, {
            reliableHiddenOffsets: function() {
                if (g == null) {
                    i()
                }
                return g
            },
            boxSizingReliable: function() {
                if (f == null) {
                    i()
                }
                return f
            },
            pixelPosition: function() {
                if (e == null) {
                    i()
                }
                return e
            },
            reliableMarginRight: function() {
                if (h == null) {
                    i()
                }
                return h
            }
        });
        function i() {
            var b, c, d, i;
            c = y.getElementsByTagName("body")[0];
            if (!c || !c.style) {
                return
            }
            b = y.createElement("div");
            d = y.createElement("div");
            d.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px";
            c.appendChild(d).appendChild(b);
            b.style.cssText = "-webkit-box-sizing:border-box;-moz-box-sizing:border-box;" + "box-sizing:border-box;display:block;margin-top:1%;top:1%;" + "border:1px;padding:1px;width:4px;position:absolute";
            e = f = false;
            h = true;
            if (a.getComputedStyle) {
                e = (a.getComputedStyle(b, null) || {}).top !== "1%";
                f = (a.getComputedStyle(b, null) || {
                    width: "4px"
                }).width === "4px";
                i = b.appendChild(y.createElement("div"));
                i.style.cssText = b.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;" + "box-sizing:content-box;display:block;margin:0;border:0;padding:0";
                i.style.marginRight = i.style.width = "0";
                b.style.width = "1px";
                h = !parseFloat((a.getComputedStyle(i, null) || {}).marginRight);
                b.removeChild(i)
            }
            b.innerHTML = "<table><tr><td></td><td>t</td></tr></table>";
            i = b.getElementsByTagName("td");
            i[0].style.cssText = "margin:0;border:0;padding:0;display:none";
            g = i[0].offsetHeight === 0;
            if (g) {
                i[0].style.display = "";
                i[1].style.display = "none";
                g = i[0].offsetHeight === 0
            }
            c.removeChild(d)
        }
    })();
    m.swap = function(a, b, c, d) {
        var e, f, g = {};
        for (f in b) {
            g[f] = a.style[f];
            a.style[f] = b[f]
        }
        e = c.apply(a, d || []);
        for (f in b) {
            a.style[f] = g[f]
        }
        return e
    };
    var Ma = /alpha\([^)]*\)/i,
    Na = /opacity\s*=\s*([^)]*)/,
    Oa = /^(none|table(?!-c[ea]).+)/,
    Pa = new RegExp("^(" + S + ")(.*)$", "i"),
    Qa = new RegExp("^([+-])=(" + S + ")", "i"),
    Ra = {
        position: "absolute",
        visibility: "hidden",
        display: "block"
    },
    Sa = {
        letterSpacing: "0",
        fontWeight: "400"
    },
    Ta = ["Webkit", "O", "Moz", "ms"];
    function Ua(a, b) {
        if (b in a) {
            return b
        }
        var c = b.charAt(0).toUpperCase() + b.slice(1),
        d = b,
        e = Ta.length;
        while (e--) {
            b = Ta[e] + c;
            if (b in a) {
                return b
            }
        }
        return d
    }
    function Va(a, b) {
        var c, d, e, f = [],
        g = 0,
        h = a.length;
        for (; g < h; g++) {
            d = a[g];
            if (!d.style) {
                continue
            }
            f[g] = m._data(d, "olddisplay");
            c = d.style.display;
            if (b) {
                if (!f[g] && c === "none") {
                    d.style.display = ""
                }
                if (d.style.display === "" && U(d)) {
                    f[g] = m._data(d, "olddisplay", Fa(d.nodeName))
                }
            } else {
                e = U(d);
                if (c && c !== "none" || !e) {
                    m._data(d, "olddisplay", e ? c: m.css(d, "display"))
                }
            }
        }
        for (g = 0; g < h; g++) {
            d = a[g];
            if (!d.style) {
                continue
            }
            if (!b || d.style.display === "none" || d.style.display === "") {
                d.style.display = b ? f[g] || "": "none"
            }
        }
        return a
    }
    function Wa(a, b, c) {
        var d = Pa.exec(b);
        return d ? Math.max(0, d[1] - (c || 0)) + (d[2] || "px") : b
    }
    function Xa(a, b, c, d, e) {
        var f = c === (d ? "border": "content") ? 4 : b === "width" ? 1 : 0,
        g = 0;
        for (; f < 4; f += 2) {
            if (c === "margin") {
                g += m.css(a, c + T[f], true, e)
            }
            if (d) {
                if (c === "content") {
                    g -= m.css(a, "padding" + T[f], true, e)
                }
                if (c !== "margin") {
                    g -= m.css(a, "border" + T[f] + "Width", true, e)
                }
            } else {
                g += m.css(a, "padding" + T[f], true, e);
                if (c !== "padding") {
                    g += m.css(a, "border" + T[f] + "Width", true, e)
                }
            }
        }
        return g
    }
    function Ya(a, b, c) {
        var d = true,
        e = b === "width" ? a.offsetWidth: a.offsetHeight,
        f = Ia(a),
        g = k.boxSizing && m.css(a, "boxSizing", false, f) === "border-box";
        if (e <= 0 || e == null) {
            e = Ja(a, b, f);
            if (e < 0 || e == null) {
                e = a.style[b]
            }
            if (Ha.test(e)) {
                return e
            }
            d = g && (k.boxSizingReliable() || e === a.style[b]);
            e = parseFloat(e) || 0
        }
        return e + Xa(a, b, c || (g ? "border": "content"), d, f) + "px"
    }
    m.extend({
        cssHooks: {
            opacity: {
                get: function(a, b) {
                    if (b) {
                        var c = Ja(a, "opacity");
                        return c === "" ? "1": c
                    }
                }
            }
        },
        cssNumber: {
            columnCount: true,
            fillOpacity: true,
            flexGrow: true,
            flexShrink: true,
            fontWeight: true,
            lineHeight: true,
            opacity: true,
            order: true,
            orphans: true,
            widows: true,
            zIndex: true,
            zoom: true
        },
        cssProps: {
            "float": k.cssFloat ? "cssFloat": "styleFloat"
        },
        style: function(a, b, c, d) {
            if (!a || a.nodeType === 3 || a.nodeType === 8 || !a.style) {
                return
            }
            var e, f, g, h = m.camelCase(b),
            i = a.style;
            b = m.cssProps[h] || (m.cssProps[h] = Ua(i, h));
            g = m.cssHooks[b] || m.cssHooks[h];
            if (c !== undefined) {
                f = typeof c;
                if (f === "string" && (e = Qa.exec(c))) {
                    c = (e[1] + 1) * e[2] + parseFloat(m.css(a, b));
                    f = "number"
                }
                if (c == null || c !== c) {
                    return
                }
                if (f === "number" && !m.cssNumber[h]) {
                    c += "px"
                }
                if (!k.clearCloneStyle && c === "" && b.indexOf("background") === 0) {
                    i[b] = "inherit"
                }
                if (!g || !("set" in g) || (c = g.set(a, c, d)) !== undefined) {
                    try {
                        i[b] = c
                    } catch(j) {}
                }
            } else {
                if (g && "get" in g && (e = g.get(a, false, d)) !== undefined) {
                    return e
                }
                return i[b]
            }
        },
        css: function(a, b, c, d) {
            var e, f, g, h = m.camelCase(b);
            b = m.cssProps[h] || (m.cssProps[h] = Ua(a.style, h));
            g = m.cssHooks[b] || m.cssHooks[h];
            if (g && "get" in g) {
                f = g.get(a, true, c)
            }
            if (f === undefined) {
                f = Ja(a, b, d)
            }
            if (f === "normal" && b in Sa) {
                f = Sa[b]
            }
            if (c === "" || c) {
                e = parseFloat(f);
                return c === true || m.isNumeric(e) ? e || 0 : f
            }
            return f
        }
    });
    m.each(["height", "width"],
    function(a, b) {
        m.cssHooks[b] = {
            get: function(a, c, d) {
                if (c) {
                    return Oa.test(m.css(a, "display")) && a.offsetWidth === 0 ? m.swap(a, Ra,
                    function() {
                        return Ya(a, b, d)
                    }) : Ya(a, b, d)
                }
            },
            set: function(a, c, d) {
                var e = d && Ia(a);
                return Wa(a, c, d ? Xa(a, b, d, k.boxSizing && m.css(a, "boxSizing", false, e) === "border-box", e) : 0)
            }
        }
    });
    if (!k.opacity) {
        m.cssHooks.opacity = {
            get: function(a, b) {
                return Na.test((b && a.currentStyle ? a.currentStyle.filter: a.style.filter) || "") ? .01 * parseFloat(RegExp.$1) + "": b ? "1": ""
            },
            set: function(a, b) {
                var c = a.style,
                d = a.currentStyle,
                e = m.isNumeric(b) ? "alpha(opacity=" + b * 100 + ")": "",
                f = d && d.filter || c.filter || "";
                c.zoom = 1;
                if ((b >= 1 || b === "") && m.trim(f.replace(Ma, "")) === "" && c.removeAttribute) {
                    c.removeAttribute("filter");
                    if (b === "" || d && !d.filter) {
                        return
                    }
                }
                c.filter = Ma.test(f) ? f.replace(Ma, e) : f + " " + e
            }
        }
    }
    m.cssHooks.marginRight = La(k.reliableMarginRight,
    function(a, b) {
        if (b) {
            return m.swap(a, {
                display: "inline-block"
            },
            Ja, [a, "marginRight"])
        }
    });
    m.each({
        margin: "",
        padding: "",
        border: "Width"
    },
    function(a, b) {
        m.cssHooks[a + b] = {
            expand: function(c) {
                var d = 0,
                e = {},
                f = typeof c === "string" ? c.split(" ") : [c];
                for (; d < 4; d++) {
                    e[a + T[d] + b] = f[d] || f[d - 2] || f[0]
                }
                return e
            }
        };
        if (!Ga.test(a)) {
            m.cssHooks[a + b].set = Wa
        }
    });
    m.fn.extend({
        css: function(a, b) {
            return V(this,
            function(a, b, c) {
                var d, e, f = {},
                g = 0;
                if (m.isArray(b)) {
                    d = Ia(a);
                    e = b.length;
                    for (; g < e; g++) {
                        f[b[g]] = m.css(a, b[g], false, d)
                    }
                    return f
                }
                return c !== undefined ? m.style(a, b, c) : m.css(a, b)
            },
            a, b, arguments.length > 1)
        },
        show: function() {
            return Va(this, true)
        },
        hide: function() {
            return Va(this)
        },
        toggle: function(a) {
            if (typeof a === "boolean") {
                return a ? this.show() : this.hide()
            }
            return this.each(function() {
                if (U(this)) {
                    m(this).show()
                } else {
                    m(this).hide()
                }
            })
        }
    });
    function Za(a, b, c, d, e) {
        return new Za.prototype.init(a, b, c, d, e)
    }
    m.Tween = Za;
    Za.prototype = {
        constructor: Za,
        init: function(a, b, c, d, e, f) {
            this.elem = a;
            this.prop = c;
            this.easing = e || "swing";
            this.options = b;
            this.start = this.now = this.cur();
            this.end = d;
            this.unit = f || (m.cssNumber[c] ? "": "px")
        },
        cur: function() {
            var a = Za.propHooks[this.prop];
            return a && a.get ? a.get(this) : Za.propHooks._default.get(this)
        },
        run: function(a) {
            var b, c = Za.propHooks[this.prop];
            if (this.options.duration) {
                this.pos = b = m.easing[this.easing](a, this.options.duration * a, 0, 1, this.options.duration)
            } else {
                this.pos = b = a
            }
            this.now = (this.end - this.start) * b + this.start;
            if (this.options.step) {
                this.options.step.call(this.elem, this.now, this)
            }
            if (c && c.set) {
                c.set(this)
            } else {
                Za.propHooks._default.set(this)
            }
            return this
        }
    };
    Za.prototype.init.prototype = Za.prototype;
    Za.propHooks = {
        _default: {
            get: function(a) {
                var b;
                if (a.elem[a.prop] != null && (!a.elem.style || a.elem.style[a.prop] == null)) {
                    return a.elem[a.prop]
                }
                b = m.css(a.elem, a.prop, "");
                return ! b || b === "auto" ? 0 : b
            },
            set: function(a) {
                if (m.fx.step[a.prop]) {
                    m.fx.step[a.prop](a)
                } else if (a.elem.style && (a.elem.style[m.cssProps[a.prop]] != null || m.cssHooks[a.prop])) {
                    m.style(a.elem, a.prop, a.now + a.unit)
                } else {
                    a.elem[a.prop] = a.now
                }
            }
        }
    };
    Za.propHooks.scrollTop = Za.propHooks.scrollLeft = {
        set: function(a) {
            if (a.elem.nodeType && a.elem.parentNode) {
                a.elem[a.prop] = a.now
            }
        }
    };
    m.easing = {
        linear: function(a) {
            return a
        },
        swing: function(a) {
            return.5 - Math.cos(a * Math.PI) / 2
        }
    };
    m.fx = Za.prototype.init;
    m.fx.step = {};
    var $a, _a, ab = /^(?:toggle|show|hide)$/,
    bb = new RegExp("^(?:([+-])=|)(" + S + ")([a-z%]*)$", "i"),
    cb = /queueHooks$/,
    db = [ib],
    eb = {
        "*": [function(a, b) {
            var c = this.createTween(a, b),
            d = c.cur(),
            e = bb.exec(b),
            f = e && e[3] || (m.cssNumber[a] ? "": "px"),
            g = (m.cssNumber[a] || f !== "px" && +d) && bb.exec(m.css(c.elem, a)),
            h = 1,
            i = 20;
            if (g && g[3] !== f) {
                f = f || g[3];
                e = e || [];
                g = +d || 1;
                do {
                    h = h || ".5";
                    g = g / h;
                    m.style(c.elem, a, g + f)
                } while ( h !== ( h = c . cur () / d) && h !== 1 && --i)
            }
            if (e) {
                g = c.start = +g || +d || 0;
                c.unit = f;
                c.end = e[1] ? g + (e[1] + 1) * e[2] : +e[2]
            }
            return c
        }]
    };
    function fb() {
        setTimeout(function() {
            $a = undefined
        });
        return $a = m.now()
    }
    function gb(a, b) {
        var c, d = {
            height: a
        },
        e = 0;
        b = b ? 1 : 0;
        for (; e < 4; e += 2 - b) {
            c = T[e];
            d["margin" + c] = d["padding" + c] = a
        }
        if (b) {
            d.opacity = d.width = a
        }
        return d
    }
    function hb(a, b, c) {
        var d, e = (eb[b] || []).concat(eb["*"]),
        f = 0,
        g = e.length;
        for (; f < g; f++) {
            if (d = e[f].call(c, b, a)) {
                return d
            }
        }
    }
    function ib(a, b, c) {
        var d, e, f, g, h, i, j, l, n = this,
        o = {},
        p = a.style,
        q = a.nodeType && U(a),
        r = m._data(a, "fxshow");
        if (!c.queue) {
            h = m._queueHooks(a, "fx");
            if (h.unqueued == null) {
                h.unqueued = 0;
                i = h.empty.fire;
                h.empty.fire = function() {
                    if (!h.unqueued) {
                        i()
                    }
                }
            }
            h.unqueued++;
            n.always(function() {
                n.always(function() {
                    h.unqueued--;
                    if (!m.queue(a, "fx").length) {
                        h.empty.fire()
                    }
                })
            })
        }
        if (a.nodeType === 1 && ("height" in b || "width" in b)) {
            c.overflow = [p.overflow, p.overflowX, p.overflowY];
            j = m.css(a, "display");
            l = j === "none" ? m._data(a, "olddisplay") || Fa(a.nodeName) : j;
            if (l === "inline" && m.css(a, "float") === "none") {
                if (!k.inlineBlockNeedsLayout || Fa(a.nodeName) === "inline") {
                    p.display = "inline-block"
                } else {
                    p.zoom = 1
                }
            }
        }
        if (c.overflow) {
            p.overflow = "hidden";
            if (!k.shrinkWrapBlocks()) {
                n.always(function() {
                    p.overflow = c.overflow[0];
                    p.overflowX = c.overflow[1];
                    p.overflowY = c.overflow[2]
                })
            }
        }
        for (d in b) {
            e = b[d];
            if (ab.exec(e)) {
                delete b[d];
                f = f || e === "toggle";
                if (e === (q ? "hide": "show")) {
                    if (e === "show" && r && r[d] !== undefined) {
                        q = true
                    } else {
                        continue
                    }
                }
                o[d] = r && r[d] || m.style(a, d)
            } else {
                j = undefined
            }
        }
        if (!m.isEmptyObject(o)) {
            if (r) {
                if ("hidden" in r) {
                    q = r.hidden
                }
            } else {
                r = m._data(a, "fxshow", {})
            }
            if (f) {
                r.hidden = !q
            }
            if (q) {
                m(a).show()
            } else {
                n.done(function() {
                    m(a).hide()
                })
            }
            n.done(function() {
                var b;
                m._removeData(a, "fxshow");
                for (b in o) {
                    m.style(a, b, o[b])
                }
            });
            for (d in o) {
                g = hb(q ? r[d] : 0, d, n);
                if (! (d in r)) {
                    r[d] = g.start;
                    if (q) {
                        g.end = g.start;
                        g.start = d === "width" || d === "height" ? 1 : 0
                    }
                }
            }
        } else if ((j === "none" ? Fa(a.nodeName) : j) === "inline") {
            p.display = j
        }
    }
    function jb(a, b) {
        var c, d, e, f, g;
        for (c in a) {
            d = m.camelCase(c);
            e = b[d];
            f = a[c];
            if (m.isArray(f)) {
                e = f[1];
                f = a[c] = f[0]
            }
            if (c !== d) {
                a[d] = f;
                delete a[c]
            }
            g = m.cssHooks[d];
            if (g && "expand" in g) {
                f = g.expand(f);
                delete a[d];
                for (c in f) {
                    if (! (c in a)) {
                        a[c] = f[c];
                        b[c] = e
                    }
                }
            } else {
                b[d] = e
            }
        }
    }
    function kb(a, b, c) {
        var d, e, f = 0,
        g = db.length,
        h = m.Deferred().always(function() {
            delete i.elem
        }),
        i = function() {
            if (e) {
                return false
            }
            var b = $a || fb(),
            c = Math.max(0, j.startTime + j.duration - b),
            d = c / j.duration || 0,
            f = 1 - d,
            g = 0,
            i = j.tweens.length;
            for (; g < i; g++) {
                j.tweens[g].run(f)
            }
            h.notifyWith(a, [j, f, c]);
            if (f < 1 && i) {
                return c
            } else {
                h.resolveWith(a, [j]);
                return false
            }
        },
        j = h.promise({
            elem: a,
            props: m.extend({},
            b),
            opts: m.extend(true, {
                specialEasing: {}
            },
            c),
            originalProperties: b,
            originalOptions: c,
            startTime: $a || fb(),
            duration: c.duration,
            tweens: [],
            createTween: function(b, c) {
                var d = m.Tween(a, j.opts, b, c, j.opts.specialEasing[b] || j.opts.easing);
                j.tweens.push(d);
                return d
            },
            stop: function(b) {
                var c = 0,
                d = b ? j.tweens.length: 0;
                if (e) {
                    return this
                }
                e = true;
                for (; c < d; c++) {
                    j.tweens[c].run(1)
                }
                if (b) {
                    h.resolveWith(a, [j, b])
                } else {
                    h.rejectWith(a, [j, b])
                }
                return this
            }
        }),
        k = j.props;
        jb(k, j.opts.specialEasing);
        for (; f < g; f++) {
            d = db[f].call(j, a, k, j.opts);
            if (d) {
                return d
            }
        }
        m.map(k, hb, j);
        if (m.isFunction(j.opts.start)) {
            j.opts.start.call(a, j)
        }
        m.fx.timer(m.extend(i, {
            elem: a,
            anim: j,
            queue: j.opts.queue
        }));
        return j.progress(j.opts.progress).done(j.opts.done, j.opts.complete).fail(j.opts.fail).always(j.opts.always)
    }
    m.Animation = m.extend(kb, {
        tweener: function(a, b) {
            if (m.isFunction(a)) {
                b = a;
                a = ["*"]
            } else {
                a = a.split(" ")
            }
            var c, d = 0,
            e = a.length;
            for (; d < e; d++) {
                c = a[d];
                eb[c] = eb[c] || [];
                eb[c].unshift(b)
            }
        },
        prefilter: function(a, b) {
            if (b) {
                db.unshift(a)
            } else {
                db.push(a)
            }
        }
    });
    m.speed = function(a, b, c) {
        var d = a && typeof a === "object" ? m.extend({},
        a) : {
            complete: c || !c && b || m.isFunction(a) && a,
            duration: a,
            easing: c && b || b && !m.isFunction(b) && b
        };
        d.duration = m.fx.off ? 0 : typeof d.duration === "number" ? d.duration: d.duration in m.fx.speeds ? m.fx.speeds[d.duration] : m.fx.speeds._default;
        if (d.queue == null || d.queue === true) {
            d.queue = "fx"
        }
        d.old = d.complete;
        d.complete = function() {
            if (m.isFunction(d.old)) {
                d.old.call(this)
            }
            if (d.queue) {
                m.dequeue(this, d.queue)
            }
        };
        return d
    };
    m.fn.extend({
        fadeTo: function(a, b, c, d) {
            return this.filter(U).css("opacity", 0).show().end().animate({
                opacity: b
            },
            a, c, d)
        },
        animate: function(a, b, c, d) {
            var e = m.isEmptyObject(a),
            f = m.speed(b, c, d),
            g = function() {
                var b = kb(this, m.extend({},
                a), f);
                if (e || m._data(this, "finish")) {
                    b.stop(true)
                }
            };
            g.finish = g;
            return e || f.queue === false ? this.each(g) : this.queue(f.queue, g)
        },
        stop: function(a, b, c) {
            var d = function(a) {
                var b = a.stop;
                delete a.stop;
                b(c)
            };
            if (typeof a !== "string") {
                c = b;
                b = a;
                a = undefined
            }
            if (b && a !== false) {
                this.queue(a || "fx", [])
            }
            return this.each(function() {
                var b = true,
                e = a != null && a + "queueHooks",
                f = m.timers,
                g = m._data(this);
                if (e) {
                    if (g[e] && g[e].stop) {
                        d(g[e])
                    }
                } else {
                    for (e in g) {
                        if (g[e] && g[e].stop && cb.test(e)) {
                            d(g[e])
                        }
                    }
                }
                for (e = f.length; e--;) {
                    if (f[e].elem === this && (a == null || f[e].queue === a)) {
                        f[e].anim.stop(c);
                        b = false;
                        f.splice(e, 1)
                    }
                }
                if (b || !c) {
                    m.dequeue(this, a)
                }
            })
        },
        finish: function(a) {
            if (a !== false) {
                a = a || "fx"
            }
            return this.each(function() {
                var b, c = m._data(this),
                d = c[a + "queue"],
                e = c[a + "queueHooks"],
                f = m.timers,
                g = d ? d.length: 0;
                c.finish = true;
                m.queue(this, a, []);
                if (e && e.stop) {
                    e.stop.call(this, true)
                }
                for (b = f.length; b--;) {
                    if (f[b].elem === this && f[b].queue === a) {
                        f[b].anim.stop(true);
                        f.splice(b, 1)
                    }
                }
                for (b = 0; b < g; b++) {
                    if (d[b] && d[b].finish) {
                        d[b].finish.call(this)
                    }
                }
                delete c.finish
            })
        }
    });
    m.each(["toggle", "show", "hide"],
    function(a, b) {
        var c = m.fn[b];
        m.fn[b] = function(a, d, e) {
            return a == null || typeof a === "boolean" ? c.apply(this, arguments) : this.animate(gb(b, true), a, d, e)
        }
    });
    m.each({
        slideDown: gb("show"),
        slideUp: gb("hide"),
        slideToggle: gb("toggle"),
        fadeIn: {
            opacity: "show"
        },
        fadeOut: {
            opacity: "hide"
        },
        fadeToggle: {
            opacity: "toggle"
        }
    },
    function(a, b) {
        m.fn[a] = function(a, c, d) {
            return this.animate(b, a, c, d)
        }
    });
    m.timers = [];
    m.fx.tick = function() {
        var a, b = m.timers,
        c = 0;
        $a = m.now();
        for (; c < b.length; c++) {
            a = b[c];
            if (!a() && b[c] === a) {
                b.splice(c--, 1)
            }
        }
        if (!b.length) {
            m.fx.stop()
        }
        $a = undefined
    };
    m.fx.timer = function(a) {
        m.timers.push(a);
        if (a()) {
            m.fx.start()
        } else {
            m.timers.pop()
        }
    };
    m.fx.interval = 13;
    m.fx.start = function() {
        if (!_a) {
            _a = setInterval(m.fx.tick, m.fx.interval)
        }
    };
    m.fx.stop = function() {
        clearInterval(_a);
        _a = null
    };
    m.fx.speeds = {
        slow: 600,
        fast: 200,
        _default: 400
    };
    m.fn.delay = function(a, b) {
        a = m.fx ? m.fx.speeds[a] || a: a;
        b = b || "fx";
        return this.queue(b,
        function(b, c) {
            var d = setTimeout(b, a);
            c.stop = function() {
                clearTimeout(d)
            }
        })
    }; (function() {
        var a, b, c, d, e;
        b = y.createElement("div");
        b.setAttribute("className", "t");
        b.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>";
        d = b.getElementsByTagName("a")[0];
        c = y.createElement("select");
        e = c.appendChild(y.createElement("option"));
        a = b.getElementsByTagName("input")[0];
        d.style.cssText = "top:1px";
        k.getSetAttribute = b.className !== "t";
        k.style = /top/.test(d.getAttribute("style"));
        k.hrefNormalized = d.getAttribute("href") === "/a";
        k.checkOn = !!a.value;
        k.optSelected = e.selected;
        k.enctype = !!y.createElement("form").enctype;
        c.disabled = true;
        k.optDisabled = !e.disabled;
        a = y.createElement("input");
        a.setAttribute("value", "");
        k.input = a.getAttribute("value") === "";
        a.value = "t";
        a.setAttribute("type", "radio");
        k.radioValue = a.value === "t"
    })();
    var lb = /\r/g;
    m.fn.extend({
        val: function(a) {
            var b, c, d, e = this[0];
            if (!arguments.length) {
                if (e) {
                    b = m.valHooks[e.type] || m.valHooks[e.nodeName.toLowerCase()];
                    if (b && "get" in b && (c = b.get(e, "value")) !== undefined) {
                        return c
                    }
                    c = e.value;
                    return typeof c === "string" ? c.replace(lb, "") : c == null ? "": c
                }
                return
            }
            d = m.isFunction(a);
            return this.each(function(c) {
                var e;
                if (this.nodeType !== 1) {
                    return
                }
                if (d) {
                    e = a.call(this, c, m(this).val())
                } else {
                    e = a
                }
                if (e == null) {
                    e = ""
                } else if (typeof e === "number") {
                    e += ""
                } else if (m.isArray(e)) {
                    e = m.map(e,
                    function(a) {
                        return a == null ? "": a + ""
                    })
                }
                b = m.valHooks[this.type] || m.valHooks[this.nodeName.toLowerCase()];
                if (!b || !("set" in b) || b.set(this, e, "value") === undefined) {
                    this.value = e
                }
            })
        }
    });
    m.extend({
        valHooks: {
            option: {
                get: function(a) {
                    var b = m.find.attr(a, "value");
                    return b != null ? b: m.trim(m.text(a))
                }
            },
            select: {
                get: function(a) {
                    var b, c, d = a.options,
                    e = a.selectedIndex,
                    f = a.type === "select-one" || e < 0,
                    g = f ? null: [],
                    h = f ? e + 1 : d.length,
                    i = e < 0 ? h: f ? e: 0;
                    for (; i < h; i++) {
                        c = d[i];
                        if ((c.selected || i === e) && (k.optDisabled ? !c.disabled: c.getAttribute("disabled") === null) && (!c.parentNode.disabled || !m.nodeName(c.parentNode, "optgroup"))) {
                            b = m(c).val();
                            if (f) {
                                return b
                            }
                            g.push(b)
                        }
                    }
                    return g
                },
                set: function(a, b) {
                    var c, d, e = a.options,
                    f = m.makeArray(b),
                    g = e.length;
                    while (g--) {
                        d = e[g];
                        if (m.inArray(m.valHooks.option.get(d), f) >= 0) {
                            try {
                                d.selected = c = true
                            } catch(h) {
                                d.scrollHeight
                            }
                        } else {
                            d.selected = false
                        }
                    }
                    if (!c) {
                        a.selectedIndex = -1
                    }
                    return e
                }
            }
        }
    });
    m.each(["radio", "checkbox"],
    function() {
        m.valHooks[this] = {
            set: function(a, b) {
                if (m.isArray(b)) {
                    return a.checked = m.inArray(m(a).val(), b) >= 0
                }
            }
        };
        if (!k.checkOn) {
            m.valHooks[this].get = function(a) {
                return a.getAttribute("value") === null ? "on": a.value
            }
        }
    });
    var mb, nb, ob = m.expr.attrHandle,
    pb = /^(?:checked|selected)$/i,
    qb = k.getSetAttribute,
    rb = k.input;
    m.fn.extend({
        attr: function(a, b) {
            return V(this, m.attr, a, b, arguments.length > 1)
        },
        removeAttr: function(a) {
            return this.each(function() {
                m.removeAttr(this, a)
            })
        }
    });
    m.extend({
        attr: function(a, b, c) {
            var d, e, f = a.nodeType;
            if (!a || f === 3 || f === 8 || f === 2) {
                return
            }
            if (typeof a.getAttribute === K) {
                return m.prop(a, b, c)
            }
            if (f !== 1 || !m.isXMLDoc(a)) {
                b = b.toLowerCase();
                d = m.attrHooks[b] || (m.expr.match.bool.test(b) ? nb: mb)
            }
            if (c !== undefined) {
                if (c === null) {
                    m.removeAttr(a, b)
                } else if (d && "set" in d && (e = d.set(a, c, b)) !== undefined) {
                    return e
                } else {
                    a.setAttribute(b, c + "");
                    return c
                }
            } else if (d && "get" in d && (e = d.get(a, b)) !== null) {
                return e
            } else {
                e = m.find.attr(a, b);
                return e == null ? undefined: e
            }
        },
        removeAttr: function(a, b) {
            var c, d, e = 0,
            f = b && b.match(E);
            if (f && a.nodeType === 1) {
                while (c = f[e++]) {
                    d = m.propFix[c] || c;
                    if (m.expr.match.bool.test(c)) {
                        if (rb && qb || !pb.test(c)) {
                            a[d] = false
                        } else {
                            a[m.camelCase("default-" + c)] = a[d] = false
                        }
                    } else {
                        m.attr(a, c, "")
                    }
                    a.removeAttribute(qb ? c: d)
                }
            }
        },
        attrHooks: {
            type: {
                set: function(a, b) {
                    if (!k.radioValue && b === "radio" && m.nodeName(a, "input")) {
                        var c = a.value;
                        a.setAttribute("type", b);
                        if (c) {
                            a.value = c
                        }
                        return b
                    }
                }
            }
        }
    });
    nb = {
        set: function(a, b, c) {
            if (b === false) {
                m.removeAttr(a, c)
            } else if (rb && qb || !pb.test(c)) {
                a.setAttribute(!qb && m.propFix[c] || c, c)
            } else {
                a[m.camelCase("default-" + c)] = a[c] = true
            }
            return c
        }
    };
    m.each(m.expr.match.bool.source.match(/\w+/g),
    function(a, b) {
        var c = ob[b] || m.find.attr;
        ob[b] = rb && qb || !pb.test(b) ?
        function(a, b, d) {
            var e, f;
            if (!d) {
                f = ob[b];
                ob[b] = e;
                e = c(a, b, d) != null ? b.toLowerCase() : null;
                ob[b] = f
            }
            return e
        }: function(a, b, c) {
            if (!c) {
                return a[m.camelCase("default-" + b)] ? b.toLowerCase() : null
            }
        }
    });
    if (!rb || !qb) {
        m.attrHooks.value = {
            set: function(a, b, c) {
                if (m.nodeName(a, "input")) {
                    a.defaultValue = b
                } else {
                    return mb && mb.set(a, b, c)
                }
            }
        }
    }
    if (!qb) {
        mb = {
            set: function(a, b, c) {
                var d = a.getAttributeNode(c);
                if (!d) {
                    a.setAttributeNode(d = a.ownerDocument.createAttribute(c))
                }
                d.value = b += "";
                if (c === "value" || b === a.getAttribute(c)) {
                    return b
                }
            }
        };
        ob.id = ob.name = ob.coords = function(a, b, c) {
            var d;
            if (!c) {
                return (d = a.getAttributeNode(b)) && d.value !== "" ? d.value: null
            }
        };
        m.valHooks.button = {
            get: function(a, b) {
                var c = a.getAttributeNode(b);
                if (c && c.specified) {
                    return c.value
                }
            },
            set: mb.set
        };
        m.attrHooks.contenteditable = {
            set: function(a, b, c) {
                mb.set(a, b === "" ? false: b, c)
            }
        };
        m.each(["width", "height"],
        function(a, b) {
            m.attrHooks[b] = {
                set: function(a, c) {
                    if (c === "") {
                        a.setAttribute(b, "auto");
                        return c
                    }
                }
            }
        })
    }
    if (!k.style) {
        m.attrHooks.style = {
            get: function(a) {
                return a.style.cssText || undefined
            },
            set: function(a, b) {
                return a.style.cssText = b + ""
            }
        }
    }
    var sb = /^(?:input|select|textarea|button|object)$/i,
    tb = /^(?:a|area)$/i;
    m.fn.extend({
        prop: function(a, b) {
            return V(this, m.prop, a, b, arguments.length > 1)
        },
        removeProp: function(a) {
            a = m.propFix[a] || a;
            return this.each(function() {
                try {
                    this[a] = undefined;
                    delete this[a]
                } catch(b) {}
            })
        }
    });
    m.extend({
        propFix: {
            "for": "htmlFor",
            "class": "className"
        },
        prop: function(a, b, c) {
            var d, e, f, g = a.nodeType;
            if (!a || g === 3 || g === 8 || g === 2) {
                return
            }
            f = g !== 1 || !m.isXMLDoc(a);
            if (f) {
                b = m.propFix[b] || b;
                e = m.propHooks[b]
            }
            if (c !== undefined) {
                return e && "set" in e && (d = e.set(a, c, b)) !== undefined ? d: a[b] = c
            } else {
                return e && "get" in e && (d = e.get(a, b)) !== null ? d: a[b]
            }
        },
        propHooks: {
            tabIndex: {
                get: function(a) {
                    var b = m.find.attr(a, "tabindex");
                    return b ? parseInt(b, 10) : sb.test(a.nodeName) || tb.test(a.nodeName) && a.href ? 0 : -1
                }
            }
        }
    });
    if (!k.hrefNormalized) {
        m.each(["href", "src"],
        function(a, b) {
            m.propHooks[b] = {
                get: function(a) {
                    return a.getAttribute(b, 4)
                }
            }
        })
    }
    if (!k.optSelected) {
        m.propHooks.selected = {
            get: function(a) {
                var b = a.parentNode;
                if (b) {
                    b.selectedIndex;
                    if (b.parentNode) {
                        b.parentNode.selectedIndex
                    }
                }
                return null
            }
        }
    }
    m.each(["tabIndex", "readOnly", "maxLength", "cellSpacing", "cellPadding", "rowSpan", "colSpan", "useMap", "frameBorder", "contentEditable"],
    function() {
        m.propFix[this.toLowerCase()] = this
    });
    if (!k.enctype) {
        m.propFix.enctype = "encoding"
    }
    var ub = /[\t\r\n\f]/g;
    m.fn.extend({
        addClass: function(a) {
            var b, c, d, e, f, g, h = 0,
            i = this.length,
            j = typeof a === "string" && a;
            if (m.isFunction(a)) {
                return this.each(function(b) {
                    m(this).addClass(a.call(this, b, this.className))
                })
            }
            if (j) {
                b = (a || "").match(E) || [];
                for (; h < i; h++) {
                    c = this[h];
                    d = c.nodeType === 1 && (c.className ? (" " + c.className + " ").replace(ub, " ") : " ");
                    if (d) {
                        f = 0;
                        while (e = b[f++]) {
                            if (d.indexOf(" " + e + " ") < 0) {
                                d += e + " "
                            }
                        }
                        g = m.trim(d);
                        if (c.className !== g) {
                            c.className = g
                        }
                    }
                }
            }
            return this
        },
        removeClass: function(a) {
            var b, c, d, e, f, g, h = 0,
            i = this.length,
            j = arguments.length === 0 || typeof a === "string" && a;
            if (m.isFunction(a)) {
                return this.each(function(b) {
                    m(this).removeClass(a.call(this, b, this.className))
                })
            }
            if (j) {
                b = (a || "").match(E) || [];
                for (; h < i; h++) {
                    c = this[h];
                    d = c.nodeType === 1 && (c.className ? (" " + c.className + " ").replace(ub, " ") : "");
                    if (d) {
                        f = 0;
                        while (e = b[f++]) {
                            while (d.indexOf(" " + e + " ") >= 0) {
                                d = d.replace(" " + e + " ", " ")
                            }
                        }
                        g = a ? m.trim(d) : "";
                        if (c.className !== g) {
                            c.className = g
                        }
                    }
                }
            }
            return this
        },
        toggleClass: function(a, b) {
            var c = typeof a;
            if (typeof b === "boolean" && c === "string") {
                return b ? this.addClass(a) : this.removeClass(a)
            }
            if (m.isFunction(a)) {
                return this.each(function(c) {
                    m(this).toggleClass(a.call(this, c, this.className, b), b)
                })
            }
            return this.each(function() {
                if (c === "string") {
                    var b, d = 0,
                    e = m(this),
                    f = a.match(E) || [];
                    while (b = f[d++]) {
                        if (e.hasClass(b)) {
                            e.removeClass(b)
                        } else {
                            e.addClass(b)
                        }
                    }
                } else if (c === K || c === "boolean") {
                    if (this.className) {
                        m._data(this, "__className__", this.className)
                    }
                    this.className = this.className || a === false ? "": m._data(this, "__className__") || ""
                }
            })
        },
        hasClass: function(a) {
            var b = " " + a + " ",
            c = 0,
            d = this.length;
            for (; c < d; c++) {
                if (this[c].nodeType === 1 && (" " + this[c].className + " ").replace(ub, " ").indexOf(b) >= 0) {
                    return true
                }
            }
            return false
        }
    });
    m.each(("blur focus focusin focusout load resize scroll unload click dblclick " + "mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave " + "change select submit keydown keypress keyup error contextmenu").split(" "),
    function(a, b) {
        m.fn[b] = function(a, c) {
            return arguments.length > 0 ? this.on(b, null, a, c) : this.trigger(b)
        }
    });
    m.fn.extend({
        hover: function(a, b) {
            return this.mouseenter(a).mouseleave(b || a)
        },
        bind: function(a, b, c) {
            return this.on(a, null, b, c)
        },
        unbind: function(a, b) {
            return this.off(a, null, b)
        },
        delegate: function(a, b, c, d) {
            return this.on(b, a, c, d)
        },
        undelegate: function(a, b, c) {
            return arguments.length === 1 ? this.off(a, "**") : this.off(b, a || "**", c)
        }
    });
    var vb = m.now();
    var wb = /\?/;
    var xb = /(,)|(\[|{)|(}|])|"(?:[^"\\\r\n]|\\["\\\/bfnrt]|\\u[\da-fA-F]{4})*"\s*:?|true|false|null|-?(?!0\d)\d+(?:\.\d+|)(?:[eE][+-]?\d+|)/g;
    m.parseJSON = function(b) {
        if (a.JSON && a.JSON.parse) {
            return a.JSON.parse(b + "")
        }
        var c, d = null,
        e = m.trim(b + "");
        return e && !m.trim(e.replace(xb,
        function(a, b, e, f) {
            if (c && b) {
                d = 0
            }
            if (d === 0) {
                return a
            }
            c = e || b;
            d += !f - !e;
            return ""
        })) ? Function("return " + e)() : m.error("Invalid JSON: " + b)
    };
    m.parseXML = function(b) {
        var c, d;
        if (!b || typeof b !== "string") {
            return null
        }
        try {
            if (a.DOMParser) {
                d = new DOMParser;
                c = d.parseFromString(b, "text/xml")
            } else {
                c = new ActiveXObject("Microsoft.XMLDOM");
                c.async = "false";
                c.loadXML(b)
            }
        } catch(e) {
            c = undefined
        }
        if (!c || !c.documentElement || c.getElementsByTagName("parsererror").length) {
            m.error("Invalid XML: " + b)
        }
        return c
    };
    var yb, zb, Ab = /#.*$/,
    Bb = /([?&])_=[^&]*/,
    Cb = /^(.*?):[ \t]*([^\r\n]*)\r?$/gm,
    Db = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/,
    Eb = /^(?:GET|HEAD)$/,
    Fb = /^\/\//,
    Gb = /^([\w.+-]+:)(?:\/\/(?:[^\/?#]*@|)([^\/?#:]*)(?::(\d+)|)|)/,
    Hb = {},
    Ib = {},
    Jb = "*/".concat("*");
    try {
        zb = location.href
    } catch(Kb) {
        zb = y.createElement("a");
        zb.href = "";
        zb = zb.href
    }
    yb = Gb.exec(zb.toLowerCase()) || [];
    function Lb(a) {
        return function(b, c) {
            if (typeof b !== "string") {
                c = b;
                b = "*"
            }
            var d, e = 0,
            f = b.toLowerCase().match(E) || [];
            if (m.isFunction(c)) {
                while (d = f[e++]) {
                    if (d.charAt(0) === "+") {
                        d = d.slice(1) || "*"; (a[d] = a[d] || []).unshift(c)
                    } else { (a[d] = a[d] || []).push(c)
                    }
                }
            }
        }
    }
    function Mb(a, b, c, d) {
        var e = {},
        f = a === Ib;
        function g(h) {
            var i;
            e[h] = true;
            m.each(a[h] || [],
            function(a, h) {
                var j = h(b, c, d);
                if (typeof j === "string" && !f && !e[j]) {
                    b.dataTypes.unshift(j);
                    g(j);
                    return false
                } else if (f) {
                    return ! (i = j)
                }
            });
            return i
        }
        return g(b.dataTypes[0]) || !e["*"] && g("*")
    }
    function Nb(a, b) {
        var c, d, e = m.ajaxSettings.flatOptions || {};
        for (d in b) {
            if (b[d] !== undefined) { (e[d] ? a: c || (c = {}))[d] = b[d]
            }
        }
        if (c) {
            m.extend(true, a, c)
        }
        return a
    }
    function Ob(a, b, c) {
        var d, e, f, g, h = a.contents,
        i = a.dataTypes;
        while (i[0] === "*") {
            i.shift();
            if (e === undefined) {
                e = a.mimeType || b.getResponseHeader("Content-Type")
            }
        }
        if (e) {
            for (g in h) {
                if (h[g] && h[g].test(e)) {
                    i.unshift(g);
                    break
                }
            }
        }
        if (i[0] in c) {
            f = i[0]
        } else {
            for (g in c) {
                if (!i[0] || a.converters[g + " " + i[0]]) {
                    f = g;
                    break
                }
                if (!d) {
                    d = g
                }
            }
            f = f || d
        }
        if (f) {
            if (f !== i[0]) {
                i.unshift(f)
            }
            return c[f]
        }
    }
    function Pb(a, b, c, d) {
        var e, f, g, h, i, j = {},
        k = a.dataTypes.slice();
        if (k[1]) {
            for (g in a.converters) {
                j[g.toLowerCase()] = a.converters[g]
            }
        }
        f = k.shift();
        while (f) {
            if (a.responseFields[f]) {
                c[a.responseFields[f]] = b
            }
            if (!i && d && a.dataFilter) {
                b = a.dataFilter(b, a.dataType)
            }
            i = f;
            f = k.shift();
            if (f) {
                if (f === "*") {
                    f = i
                } else if (i !== "*" && i !== f) {
                    g = j[i + " " + f] || j["* " + f];
                    if (!g) {
                        for (e in j) {
                            h = e.split(" ");
                            if (h[1] === f) {
                                g = j[i + " " + h[0]] || j["* " + h[0]];
                                if (g) {
                                    if (g === true) {
                                        g = j[e]
                                    } else if (j[e] !== true) {
                                        f = h[0];
                                        k.unshift(h[1])
                                    }
                                    break
                                }
                            }
                        }
                    }
                    if (g !== true) {
                        if (g && a["throws"]) {
                            b = g(b)
                        } else {
                            try {
                                b = g(b)
                            } catch(l) {
                                return {
                                    state: "parsererror",
                                    error: g ? l: "No conversion from " + i + " to " + f
                                }
                            }
                        }
                    }
                }
            }
        }
        return {
            state: "success",
            data: b
        }
    }
    m.extend({
        active: 0,
        lastModified: {},
        etag: {},
        ajaxSettings: {
            url: zb,
            type: "GET",
            isLocal: Db.test(yb[1]),
            global: true,
            processData: true,
            async: true,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            accepts: {
                "*": Jb,
                text: "text/plain",
                html: "text/html",
                xml: "application/xml, text/xml",
                json: "application/json, text/javascript"
            },
            contents: {
                xml: /xml/,
                html: /html/,
                json: /json/
            },
            responseFields: {
                xml: "responseXML",
                text: "responseText",
                json: "responseJSON"
            },
            converters: {
                "* text": String,
                "text html": true,
                "text json": m.parseJSON,
                "text xml": m.parseXML
            },
            flatOptions: {
                url: true,
                context: true
            }
        },
        ajaxSetup: function(a, b) {
            return b ? Nb(Nb(a, m.ajaxSettings), b) : Nb(m.ajaxSettings, a)
        },
        ajaxPrefilter: Lb(Hb),
        ajaxTransport: Lb(Ib),
        ajax: function(a, b) {
            if (typeof a === "object") {
                b = a;
                a = undefined
            }
            b = b || {};
            var c, d, e, f, g, h, i, j, k = m.ajaxSetup({},
            b),
            l = k.context || k,
            n = k.context && (l.nodeType || l.jquery) ? m(l) : m.event,
            o = m.Deferred(),
            p = m.Callbacks("once memory"),
            q = k.statusCode || {},
            r = {},
            s = {},
            t = 0,
            u = "canceled",
            v = {
                readyState: 0,
                getResponseHeader: function(a) {
                    var b;
                    if (t === 2) {
                        if (!j) {
                            j = {};
                            while (b = Cb.exec(f)) {
                                j[b[1].toLowerCase()] = b[2]
                            }
                        }
                        b = j[a.toLowerCase()]
                    }
                    return b == null ? null: b
                },
                getAllResponseHeaders: function() {
                    return t === 2 ? f: null
                },
                setRequestHeader: function(a, b) {
                    var c = a.toLowerCase();
                    if (!t) {
                        a = s[c] = s[c] || a;
                        r[a] = b
                    }
                    return this
                },
                overrideMimeType: function(a) {
                    if (!t) {
                        k.mimeType = a
                    }
                    return this
                },
                statusCode: function(a) {
                    var b;
                    if (a) {
                        if (t < 2) {
                            for (b in a) {
                                q[b] = [q[b], a[b]]
                            }
                        } else {
                            v.always(a[v.status])
                        }
                    }
                    return this
                },
                abort: function(a) {
                    var b = a || u;
                    if (i) {
                        i.abort(b)
                    }
                    x(0, b);
                    return this
                }
            };
            o.promise(v).complete = p.add;
            v.success = v.done;
            v.error = v.fail;
            k.url = ((a || k.url || zb) + "").replace(Ab, "").replace(Fb, yb[1] + "//");
            k.type = b.method || b.type || k.method || k.type;
            k.dataTypes = m.trim(k.dataType || "*").toLowerCase().match(E) || [""];
            if (k.crossDomain == null) {
                c = Gb.exec(k.url.toLowerCase());
                k.crossDomain = !!(c && (c[1] !== yb[1] || c[2] !== yb[2] || (c[3] || (c[1] === "http:" ? "80": "443")) !== (yb[3] || (yb[1] === "http:" ? "80": "443"))))
            }
            if (k.data && k.processData && typeof k.data !== "string") {
                k.data = m.param(k.data, k.traditional)
            }
            Mb(Hb, k, b, v);
            if (t === 2) {
                return v
            }
            h = m.event && k.global;
            if (h && m.active++===0) {
                m.event.trigger("ajaxStart")
            }
            k.type = k.type.toUpperCase();
            k.hasContent = !Eb.test(k.type);
            e = k.url;
            if (!k.hasContent) {
                if (k.data) {
                    e = k.url += (wb.test(e) ? "&": "?") + k.data;
                    delete k.data
                }
                if (k.cache === false) {
                    k.url = Bb.test(e) ? e.replace(Bb, "$1_=" + vb++) : e + (wb.test(e) ? "&": "?") + "_=" + vb++
                }
            }
            if (k.ifModified) {
                if (m.lastModified[e]) {
                    v.setRequestHeader("If-Modified-Since", m.lastModified[e])
                }
                if (m.etag[e]) {
                    v.setRequestHeader("If-None-Match", m.etag[e])
                }
            }
            if (k.data && k.hasContent && k.contentType !== false || b.contentType) {
                v.setRequestHeader("Content-Type", k.contentType)
            }
            v.setRequestHeader("Accept", k.dataTypes[0] && k.accepts[k.dataTypes[0]] ? k.accepts[k.dataTypes[0]] + (k.dataTypes[0] !== "*" ? ", " + Jb + "; q=0.01": "") : k.accepts["*"]);
            for (d in k.headers) {
                v.setRequestHeader(d, k.headers[d])
            }
            if (k.beforeSend && (k.beforeSend.call(l, v, k) === false || t === 2)) {
                return v.abort()
            }
            u = "abort";
            for (d in {
                success: 1,
                error: 1,
                complete: 1
            }) {
                v[d](k[d])
            }
            i = Mb(Ib, k, b, v);
            if (!i) {
                x( - 1, "No Transport")
            } else {
                v.readyState = 1;
                if (h) {
                    n.trigger("ajaxSend", [v, k])
                }
                if (k.async && k.timeout > 0) {
                    g = setTimeout(function() {
                        v.abort("timeout")
                    },
                    k.timeout)
                }
                try {
                    t = 1;
                    i.send(r, x)
                } catch(w) {
                    if (t < 2) {
                        x( - 1, w)
                    } else {
                        throw w
                    }
                }
            }
            function x(a, b, c, d) {
                var j, r, s, u, w, x = b;
                if (t === 2) {
                    return
                }
                t = 2;
                if (g) {
                    clearTimeout(g)
                }
                i = undefined;
                f = d || "";
                v.readyState = a > 0 ? 4 : 0;
                j = a >= 200 && a < 300 || a === 304;
                if (c) {
                    u = Ob(k, v, c)
                }
                u = Pb(k, u, v, j);
                if (j) {
                    if (k.ifModified) {
                        w = v.getResponseHeader("Last-Modified");
                        if (w) {
                            m.lastModified[e] = w
                        }
                        w = v.getResponseHeader("etag");
                        if (w) {
                            m.etag[e] = w
                        }
                    }
                    if (a === 204 || k.type === "HEAD") {
                        x = "nocontent"
                    } else if (a === 304) {
                        x = "notmodified"
                    } else {
                        x = u.state;
                        r = u.data;
                        s = u.error;
                        j = !s
                    }
                } else {
                    s = x;
                    if (a || !x) {
                        x = "error";
                        if (a < 0) {
                            a = 0
                        }
                    }
                }
                v.status = a;
                v.statusText = (b || x) + "";
                if (j) {
                    o.resolveWith(l, [r, x, v])
                } else {
                    o.rejectWith(l, [v, x, s])
                }
                v.statusCode(q);
                q = undefined;
                if (h) {
                    n.trigger(j ? "ajaxSuccess": "ajaxError", [v, k, j ? r: s])
                }
                p.fireWith(l, [v, x]);
                if (h) {
                    n.trigger("ajaxComplete", [v, k]);
                    if (!--m.active) {
                        m.event.trigger("ajaxStop")
                    }
                }
            }
            return v
        },
        getJSON: function(a, b, c) {
            return m.get(a, b, c, "json")
        },
        getScript: function(a, b) {
            return m.get(a, undefined, b, "script")
        }
    });
    m.each(["get", "post"],
    function(a, b) {
        m[b] = function(a, c, d, e) {
            if (m.isFunction(c)) {
                e = e || d;
                d = c;
                c = undefined
            }
            return m.ajax({
                url: a,
                type: b,
                dataType: e,
                data: c,
                success: d
            })
        }
    });
    m._evalUrl = function(a) {
        return m.ajax({
            url: a,
            type: "GET",
            dataType: "script",
            async: false,
            global: false,
            "throws": true
        })
    };
    m.fn.extend({
        wrapAll: function(a) {
            if (m.isFunction(a)) {
                return this.each(function(b) {
                    m(this).wrapAll(a.call(this, b))
                })
            }
            if (this[0]) {
                var b = m(a, this[0].ownerDocument).eq(0).clone(true);
                if (this[0].parentNode) {
                    b.insertBefore(this[0])
                }
                b.map(function() {
                    var a = this;
                    while (a.firstChild && a.firstChild.nodeType === 1) {
                        a = a.firstChild
                    }
                    return a
                }).append(this)
            }
            return this
        },
        wrapInner: function(a) {
            if (m.isFunction(a)) {
                return this.each(function(b) {
                    m(this).wrapInner(a.call(this, b))
                })
            }
            return this.each(function() {
                var b = m(this),
                c = b.contents();
                if (c.length) {
                    c.wrapAll(a)
                } else {
                    b.append(a)
                }
            })
        },
        wrap: function(a) {
            var b = m.isFunction(a);
            return this.each(function(c) {
                m(this).wrapAll(b ? a.call(this, c) : a)
            })
        },
        unwrap: function() {
            return this.parent().each(function() {
                if (!m.nodeName(this, "body")) {
                    m(this).replaceWith(this.childNodes)
                }
            }).end()
        }
    });
    m.expr.filters.hidden = function(a) {
        return a.offsetWidth <= 0 && a.offsetHeight <= 0 || !k.reliableHiddenOffsets() && (a.style && a.style.display || m.css(a, "display")) === "none"
    };
    m.expr.filters.visible = function(a) {
        return ! m.expr.filters.hidden(a)
    };
    var Qb = /%20/g,
    Rb = /\[\]$/,
    Sb = /\r?\n/g,
    Tb = /^(?:submit|button|image|reset|file)$/i,
    Ub = /^(?:input|select|textarea|keygen)/i;
    function Vb(a, b, c, d) {
        var e;
        if (m.isArray(b)) {
            m.each(b,
            function(b, e) {
                if (c || Rb.test(a)) {
                    d(a, e)
                } else {
                    Vb(a + "[" + (typeof e === "object" ? b: "") + "]", e, c, d)
                }
            })
        } else if (!c && m.type(b) === "object") {
            for (e in b) {
                Vb(a + "[" + e + "]", b[e], c, d)
            }
        } else {
            d(a, b)
        }
    }
    m.param = function(a, b) {
        var c, d = [],
        e = function(a, b) {
            b = m.isFunction(b) ? b() : b == null ? "": b;
            d[d.length] = encodeURIComponent(a) + "=" + encodeURIComponent(b)
        };
        if (b === undefined) {
            b = m.ajaxSettings && m.ajaxSettings.traditional
        }
        if (m.isArray(a) || a.jquery && !m.isPlainObject(a)) {
            m.each(a,
            function() {
                e(this.name, this.value)
            })
        } else {
            for (c in a) {
                Vb(c, a[c], b, e)
            }
        }
        return d.join("&").replace(Qb, "+")
    };
    m.fn.extend({
        serialize: function() {
            return m.param(this.serializeArray())
        },
        serializeArray: function() {
            return this.map(function() {
                var a = m.prop(this, "elements");
                return a ? m.makeArray(a) : this
            }).filter(function() {
                var a = this.type;
                return this.name && !m(this).is(":disabled") && Ub.test(this.nodeName) && !Tb.test(a) && (this.checked || !W.test(a))
            }).map(function(a, b) {
                var c = m(this).val();
                return c == null ? null: m.isArray(c) ? m.map(c,
                function(a) {
                    return {
                        name: b.name,
                        value: a.replace(Sb, "\r\n")
                    }
                }) : {
                    name: b.name,
                    value: c.replace(Sb, "\r\n")
                }
            }).get()
        }
    });
    m.ajaxSettings.xhr = a.ActiveXObject !== undefined ?
    function() {
        return ! this.isLocal && /^(get|post|head|put|delete|options)$/i.test(this.type) && Zb() || $b()
    }: Zb;
    var Wb = 0,
    Xb = {},
    Yb = m.ajaxSettings.xhr();
    if (a.attachEvent) {
        a.attachEvent("onunload",
        function() {
            for (var a in Xb) {
                Xb[a](undefined, true)
            }
        })
    }
    k.cors = !!Yb && "withCredentials" in Yb;
    Yb = k.ajax = !!Yb;
    if (Yb) {
        m.ajaxTransport(function(a) {
            if (!a.crossDomain || k.cors) {
                var b;
                return {
                    send: function(c, d) {
                        var e, f = a.xhr(),
                        g = ++Wb;
                        f.open(a.type, a.url, a.async, a.username, a.password);
                        if (a.xhrFields) {
                            for (e in a.xhrFields) {
                                f[e] = a.xhrFields[e]
                            }
                        }
                        if (a.mimeType && f.overrideMimeType) {
                            f.overrideMimeType(a.mimeType)
                        }
                        if (!a.crossDomain && !c["X-Requested-With"]) {
                            c["X-Requested-With"] = "XMLHttpRequest"
                        }
                        for (e in c) {
                            if (c[e] !== undefined) {
                                f.setRequestHeader(e, c[e] + "")
                            }
                        }
                        f.send(a.hasContent && a.data || null);
                        b = function(c, e) {
                            var h, i, j;
                            if (b && (e || f.readyState === 4)) {
                                delete Xb[g];
                                b = undefined;
                                f.onreadystatechange = m.noop;
                                if (e) {
                                    if (f.readyState !== 4) {
                                        f.abort()
                                    }
                                } else {
                                    j = {};
                                    h = f.status;
                                    if (typeof f.responseText === "string") {
                                        j.text = f.responseText
                                    }
                                    try {
                                        i = f.statusText
                                    } catch(k) {
                                        i = ""
                                    }
                                    if (!h && a.isLocal && !a.crossDomain) {
                                        h = j.text ? 200 : 404
                                    } else if (h === 1223) {
                                        h = 204
                                    }
                                }
                            }
                            if (j) {
                                d(h, i, j, f.getAllResponseHeaders())
                            }
                        };
                        if (!a.async) {
                            b()
                        } else if (f.readyState === 4) {
                            setTimeout(b)
                        } else {
                            f.onreadystatechange = Xb[g] = b
                        }
                    },
                    abort: function() {
                        if (b) {
                            b(undefined, true)
                        }
                    }
                }
            }
        })
    }
    function Zb() {
        try {
            return new a.XMLHttpRequest
        } catch(b) {}
    }
    function $b() {
        try {
            return new a.ActiveXObject("Microsoft.XMLHTTP")
        } catch(b) {}
    }
    m.ajaxSetup({
        accepts: {
            script: "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"
        },
        contents: {
            script: /(?:java|ecma)script/
        },
        converters: {
            "text script": function(a) {
                m.globalEval(a);
                return a
            }
        }
    });
    m.ajaxPrefilter("script",
    function(a) {
        if (a.cache === undefined) {
            a.cache = false
        }
        if (a.crossDomain) {
            a.type = "GET";
            a.global = false
        }
    });
    m.ajaxTransport("script",
    function(a) {
        if (a.crossDomain) {
            var b, c = y.head || m("head")[0] || y.documentElement;
            return {
                send: function(d, e) {
                    b = y.createElement("script");
                    b.async = true;
                    if (a.scriptCharset) {
                        b.charset = a.scriptCharset
                    }
                    b.src = a.url;
                    b.onload = b.onreadystatechange = function(a, c) {
                        if (c || !b.readyState || /loaded|complete/.test(b.readyState)) {
                            b.onload = b.onreadystatechange = null;
                            if (b.parentNode) {
                                b.parentNode.removeChild(b)
                            }
                            b = null;
                            if (!c) {
                                e(200, "success")
                            }
                        }
                    };
                    c.insertBefore(b, c.firstChild)
                },
                abort: function() {
                    if (b) {
                        b.onload(undefined, true)
                    }
                }
            }
        }
    });
    var _b = [],
    ac = /(=)\?(?=&|$)|\?\?/;
    m.ajaxSetup({
        jsonp: "callback",
        jsonpCallback: function() {
            var a = _b.pop() || m.expando + "_" + vb++;
            this[a] = true;
            return a
        }
    });
    m.ajaxPrefilter("json jsonp",
    function(b, c, d) {
        var e, f, g, h = b.jsonp !== false && (ac.test(b.url) ? "url": typeof b.data === "string" && !(b.contentType || "").indexOf("application/x-www-form-urlencoded") && ac.test(b.data) && "data");
        if (h || b.dataTypes[0] === "jsonp") {
            e = b.jsonpCallback = m.isFunction(b.jsonpCallback) ? b.jsonpCallback() : b.jsonpCallback;
            if (h) {
                b[h] = b[h].replace(ac, "$1" + e)
            } else if (b.jsonp !== false) {
                b.url += (wb.test(b.url) ? "&": "?") + b.jsonp + "=" + e
            }
            b.converters["script json"] = function() {
                if (!g) {
                    m.error(e + " was not called")
                }
                return g[0]
            };
            b.dataTypes[0] = "json";
            f = a[e];
            a[e] = function() {
                g = arguments
            };
            d.always(function() {
                a[e] = f;
                if (b[e]) {
                    b.jsonpCallback = c.jsonpCallback;
                    _b.push(e)
                }
                if (g && m.isFunction(f)) {
                    f(g[0])
                }
                g = f = undefined
            });
            return "script"
        }
    });
    m.parseHTML = function(a, b, c) {
        if (!a || typeof a !== "string") {
            return null
        }
        if (typeof b === "boolean") {
            c = b;
            b = false
        }
        b = b || y;
        var d = u.exec(a),
        e = !c && [];
        if (d) {
            return [b.createElement(d[1])]
        }
        d = m.buildFragment([a], b, e);
        if (e && e.length) {
            m(e).remove()
        }
        return m.merge([], d.childNodes)
    };
    var bc = m.fn.load;
    m.fn.load = function(a, b, c) {
        if (typeof a !== "string" && bc) {
            return bc.apply(this, arguments)
        }
        var d, e, f, g = this,
        h = a.indexOf(" ");
        if (h >= 0) {
            d = m.trim(a.slice(h, a.length));
            a = a.slice(0, h)
        }
        if (m.isFunction(b)) {
            c = b;
            b = undefined
        } else if (b && typeof b === "object") {
            f = "POST"
        }
        if (g.length > 0) {
            m.ajax({
                url: a,
                type: f,
                dataType: "html",
                data: b
            }).done(function(a) {
                e = arguments;
                g.html(d ? m("<div>").append(m.parseHTML(a)).find(d) : a)
            }).complete(c &&
            function(a, b) {
                g.each(c, e || [a.responseText, b, a])
            })
        }
        return this
    };
    m.each(["ajaxStart", "ajaxStop", "ajaxComplete", "ajaxError", "ajaxSuccess", "ajaxSend"],
    function(a, b) {
        m.fn[b] = function(a) {
            return this.on(b, a)
        }
    });
    m.expr.filters.animated = function(a) {
        return m.grep(m.timers,
        function(b) {
            return a === b.elem
        }).length
    };
    var cc = a.document.documentElement;
    function dc(a) {
        return m.isWindow(a) ? a: a.nodeType === 9 ? a.defaultView || a.parentWindow: false
    }
    m.offset = {
        setOffset: function(a, b, c) {
            var d, e, f, g, h, i, j, k = m.css(a, "position"),
            l = m(a),
            n = {};
            if (k === "static") {
                a.style.position = "relative"
            }
            h = l.offset();
            f = m.css(a, "top");
            i = m.css(a, "left");
            j = (k === "absolute" || k === "fixed") && m.inArray("auto", [f, i]) > -1;
            if (j) {
                d = l.position();
                g = d.top;
                e = d.left
            } else {
                g = parseFloat(f) || 0;
                e = parseFloat(i) || 0
            }
            if (m.isFunction(b)) {
                b = b.call(a, c, h)
            }
            if (b.top != null) {
                n.top = b.top - h.top + g
            }
            if (b.left != null) {
                n.left = b.left - h.left + e
            }
            if ("using" in b) {
                b.using.call(a, n)
            } else {
                l.css(n)
            }
        }
    };
    m.fn.extend({
        offset: function(a) {
            if (arguments.length) {
                return a === undefined ? this: this.each(function(b) {
                    m.offset.setOffset(this, a, b)
                })
            }
            var b, c, d = {
                top: 0,
                left: 0
            },
            e = this[0],
            f = e && e.ownerDocument;
            if (!f) {
                return
            }
            b = f.documentElement;
            if (!m.contains(b, e)) {
                return d
            }
            if (typeof e.getBoundingClientRect !== K) {
                d = e.getBoundingClientRect()
            }
            c = dc(f);
            return {
                top: d.top + (c.pageYOffset || b.scrollTop) - (b.clientTop || 0),
                left: d.left + (c.pageXOffset || b.scrollLeft) - (b.clientLeft || 0)
            }
        },
        position: function() {
            if (!this[0]) {
                return
            }
            var a, b, c = {
                top: 0,
                left: 0
            },
            d = this[0];
            if (m.css(d, "position") === "fixed") {
                b = d.getBoundingClientRect()
            } else {
                a = this.offsetParent();
                b = this.offset();
                if (!m.nodeName(a[0], "html")) {
                    c = a.offset()
                }
                c.top += m.css(a[0], "borderTopWidth", true);
                c.left += m.css(a[0], "borderLeftWidth", true)
            }
            return {
                top: b.top - c.top - m.css(d, "marginTop", true),
                left: b.left - c.left - m.css(d, "marginLeft", true)
            }
        },
        offsetParent: function() {
            return this.map(function() {
                var a = this.offsetParent || cc;
                while (a && (!m.nodeName(a, "html") && m.css(a, "position") === "static")) {
                    a = a.offsetParent
                }
                return a || cc
            })
        }
    });
    m.each({
        scrollLeft: "pageXOffset",
        scrollTop: "pageYOffset"
    },
    function(a, b) {
        var c = /Y/.test(b);
        m.fn[a] = function(d) {
            return V(this,
            function(a, d, e) {
                var f = dc(a);
                if (e === undefined) {
                    return f ? b in f ? f[b] : f.document.documentElement[d] : a[d]
                }
                if (f) {
                    f.scrollTo(!c ? e: m(f).scrollLeft(), c ? e: m(f).scrollTop())
                } else {
                    a[d] = e
                }
            },
            a, d, arguments.length, null)
        }
    });
    m.each(["top", "left"],
    function(a, b) {
        m.cssHooks[b] = La(k.pixelPosition,
        function(a, c) {
            if (c) {
                c = Ja(a, b);
                return Ha.test(c) ? m(a).position()[b] + "px": c
            }
        })
    });
    m.each({
        Height: "height",
        Width: "width"
    },
    function(a, b) {
        m.each({
            padding: "inner" + a,
            content: b,
            "": "outer" + a
        },
        function(c, d) {
            m.fn[d] = function(d, e) {
                var f = arguments.length && (c || typeof d !== "boolean"),
                g = c || (d === true || e === true ? "margin": "border");
                return V(this,
                function(b, c, d) {
                    var e;
                    if (m.isWindow(b)) {
                        return b.document.documentElement["client" + a]
                    }
                    if (b.nodeType === 9) {
                        e = b.documentElement;
                        return Math.max(b.body["scroll" + a], e["scroll" + a], b.body["offset" + a], e["offset" + a], e["client" + a])
                    }
                    return d === undefined ? m.css(b, c, g) : m.style(b, c, d, g)
                },
                b, f ? d: undefined, f, null)
            }
        })
    });
    m.fn.size = function() {
        return this.length
    };
    m.fn.andSelf = m.fn.addBack;
    if (typeof define === "function" && define.amd) {
        define("jquery", [],
        function() {
            return m
        })
    }
    var ec = a.jQuery,
    fc = a.$;
    m.noConflict = function(b) {
        if (a.$ === m) {
            a.$ = fc
        }
        if (b && a.jQuery === m) {
            a.jQuery = ec
        }
        return m
    };
    if (typeof b === K) {
        a.jQuery = a.$ = m
    }
    return m
}); (function() {
    "use strict";
    var a = {};
    a.parse = function(a) {
        if (typeof a !== "string") {
            return {}
        }
        a = a.replace(/^(\?|#)/, "");
        if (!a) {
            return {}
        }
        return a.split("&").reduce(function(a, b) {
            var c = b.replace(/\+/g, " ").split("=");
            var d = c[0];
            var e = c[1];
            d = decodeURIComponent(d);
            e = e === undefined ? null: decodeURIComponent(e);
            if (!a.hasOwnProperty(d)) {
                a[d] = e
            } else if (Array.isArray(a[d])) {
                a[d].push(e)
            } else {
                a[d] = [a[d], e]
            }
            return a
        },
        {})
    };
    a.stringify = function(a) {
        return a ? Object.keys(a).map(function(b) {
            var c = a[b];
            if (Array.isArray(c)) {
                return c.map(function(a) {
                    return encodeURIComponent(b) + "=" + encodeURIComponent(a)
                }).join("&")
            }
            return encodeURIComponent(b) + "=" + encodeURIComponent(c)
        }).join("&") : ""
    };
    if (typeof define === "function" && define.amd) {
        define("queryString", [],
        function() {
            return a
        })
    } else if (typeof module !== "undefined" && module.exports) {
        module.exports = a
    } else {
        window.queryString = a
    }
})(); (function() {
    var a = this;
    var b = a._;
    var c = Array.prototype,
    d = Object.prototype,
    e = Function.prototype;
    var f = c.push,
    g = c.slice,
    h = d.toString,
    i = d.hasOwnProperty;
    var j = Array.isArray,
    k = Object.keys,
    l = e.bind,
    m = Object.create;
    var n = function() {};
    var o = function(a) {
        if (a instanceof o) return a;
        if (! (this instanceof o)) return new o(a);
        this._wrapped = a
    };
    if (typeof exports !== "undefined") {
        if (typeof module !== "undefined" && module.exports) {
            exports = module.exports = o
        }
        exports._ = o
    } else {
        a._ = o
    }
    o.VERSION = "1.8.3";
    var p = function(a, b, c) {
        if (b === void 0) return a;
        switch (c == null ? 3 : c) {
        case 1:
            return function(c) {
                return a.call(b, c)
            };
        case 2:
            return function(c, d) {
                return a.call(b, c, d)
            };
        case 3:
            return function(c, d, e) {
                return a.call(b, c, d, e)
            };
        case 4:
            return function(c, d, e, f) {
                return a.call(b, c, d, e, f)
            }
        }
        return function() {
            return a.apply(b, arguments)
        }
    };
    var q = function(a, b, c) {
        if (a == null) return o.identity;
        if (o.isFunction(a)) return p(a, b, c);
        if (o.isObject(a)) return o.matcher(a);
        return o.property(a)
    };
    o.iteratee = function(a, b) {
        return q(a, b, Infinity)
    };
    var r = function(a, b) {
        return function(c) {
            var d = arguments.length;
            if (d < 2 || c == null) return c;
            for (var e = 1; e < d; e++) {
                var f = arguments[e],
                g = a(f),
                h = g.length;
                for (var i = 0; i < h; i++) {
                    var j = g[i];
                    if (!b || c[j] === void 0) c[j] = f[j]
                }
            }
            return c
        }
    };
    var s = function(a) {
        if (!o.isObject(a)) return {};
        if (m) return m(a);
        n.prototype = a;
        var b = new n;
        n.prototype = null;
        return b
    };
    var t = function(a) {
        return function(b) {
            return b == null ? void 0 : b[a]
        }
    };
    var u = Math.pow(2, 53) - 1;
    var v = t("length");
    var w = function(a) {
        var b = v(a);
        return typeof b == "number" && b >= 0 && b <= u
    };
    o.each = o.forEach = function(a, b, c) {
        b = p(b, c);
        var d, e;
        if (w(a)) {
            for (d = 0, e = a.length; d < e; d++) {
                b(a[d], d, a)
            }
        } else {
            var f = o.keys(a);
            for (d = 0, e = f.length; d < e; d++) {
                b(a[f[d]], f[d], a)
            }
        }
        return a
    };
    o.map = o.collect = function(a, b, c) {
        b = q(b, c);
        var d = !w(a) && o.keys(a),
        e = (d || a).length,
        f = Array(e);
        for (var g = 0; g < e; g++) {
            var h = d ? d[g] : g;
            f[g] = b(a[h], h, a)
        }
        return f
    };
    function x(a) {
        function b(b, c, d, e, f, g) {
            for (; f >= 0 && f < g; f += a) {
                var h = e ? e[f] : f;
                d = c(d, b[h], h, b)
            }
            return d
        }
        return function(c, d, e, f) {
            d = p(d, f, 4);
            var g = !w(c) && o.keys(c),
            h = (g || c).length,
            i = a > 0 ? 0 : h - 1;
            if (arguments.length < 3) {
                e = c[g ? g[i] : i];
                i += a
            }
            return b(c, d, e, g, i, h)
        }
    }
    o.reduce = o.foldl = o.inject = x(1);
    o.reduceRight = o.foldr = x( - 1);
    o.find = o.detect = function(a, b, c) {
        var d;
        if (w(a)) {
            d = o.findIndex(a, b, c)
        } else {
            d = o.findKey(a, b, c)
        }
        if (d !== void 0 && d !== -1) return a[d]
    };
    o.filter = o.select = function(a, b, c) {
        var d = [];
        b = q(b, c);
        o.each(a,
        function(a, c, e) {
            if (b(a, c, e)) d.push(a)
        });
        return d
    };
    o.reject = function(a, b, c) {
        return o.filter(a, o.negate(q(b)), c)
    };
    o.every = o.all = function(a, b, c) {
        b = q(b, c);
        var d = !w(a) && o.keys(a),
        e = (d || a).length;
        for (var f = 0; f < e; f++) {
            var g = d ? d[f] : f;
            if (!b(a[g], g, a)) return false
        }
        return true
    };
    o.some = o.any = function(a, b, c) {
        b = q(b, c);
        var d = !w(a) && o.keys(a),
        e = (d || a).length;
        for (var f = 0; f < e; f++) {
            var g = d ? d[f] : f;
            if (b(a[g], g, a)) return true
        }
        return false
    };
    o.contains = o.includes = o.include = function(a, b, c, d) {
        if (!w(a)) a = o.values(a);
        if (typeof c != "number" || d) c = 0;
        return o.indexOf(a, b, c) >= 0
    };
    o.invoke = function(a, b) {
        var c = g.call(arguments, 2);
        var d = o.isFunction(b);
        return o.map(a,
        function(a) {
            var e = d ? b: a[b];
            return e == null ? e: e.apply(a, c)
        })
    };
    o.pluck = function(a, b) {
        return o.map(a, o.property(b))
    };
    o.where = function(a, b) {
        return o.filter(a, o.matcher(b))
    };
    o.findWhere = function(a, b) {
        return o.find(a, o.matcher(b))
    };
    o.max = function(a, b, c) {
        var d = -Infinity,
        e = -Infinity,
        f, g;
        if (b == null && a != null) {
            a = w(a) ? a: o.values(a);
            for (var h = 0,
            i = a.length; h < i; h++) {
                f = a[h];
                if (f > d) {
                    d = f
                }
            }
        } else {
            b = q(b, c);
            o.each(a,
            function(a, c, f) {
                g = b(a, c, f);
                if (g > e || g === -Infinity && d === -Infinity) {
                    d = a;
                    e = g
                }
            })
        }
        return d
    };
    o.min = function(a, b, c) {
        var d = Infinity,
        e = Infinity,
        f, g;
        if (b == null && a != null) {
            a = w(a) ? a: o.values(a);
            for (var h = 0,
            i = a.length; h < i; h++) {
                f = a[h];
                if (f < d) {
                    d = f
                }
            }
        } else {
            b = q(b, c);
            o.each(a,
            function(a, c, f) {
                g = b(a, c, f);
                if (g < e || g === Infinity && d === Infinity) {
                    d = a;
                    e = g
                }
            })
        }
        return d
    };
    o.shuffle = function(a) {
        var b = w(a) ? a: o.values(a);
        var c = b.length;
        var d = Array(c);
        for (var e = 0,
        f; e < c; e++) {
            f = o.random(0, e);
            if (f !== e) d[e] = d[f];
            d[f] = b[e]
        }
        return d
    };
    o.sample = function(a, b, c) {
        if (b == null || c) {
            if (!w(a)) a = o.values(a);
            return a[o.random(a.length - 1)]
        }
        return o.shuffle(a).slice(0, Math.max(0, b))
    };
    o.sortBy = function(a, b, c) {
        b = q(b, c);
        return o.pluck(o.map(a,
        function(a, c, d) {
            return {
                value: a,
                index: c,
                criteria: b(a, c, d)
            }
        }).sort(function(a, b) {
            var c = a.criteria;
            var d = b.criteria;
            if (c !== d) {
                if (c > d || c === void 0) return 1;
                if (c < d || d === void 0) return - 1
            }
            return a.index - b.index
        }), "value")
    };
    var y = function(a) {
        return function(b, c, d) {
            var e = {};
            c = q(c, d);
            o.each(b,
            function(d, f) {
                var g = c(d, f, b);
                a(e, d, g)
            });
            return e
        }
    };
    o.groupBy = y(function(a, b, c) {
        if (o.has(a, c)) a[c].push(b);
        else a[c] = [b]
    });
    o.indexBy = y(function(a, b, c) {
        a[c] = b
    });
    o.countBy = y(function(a, b, c) {
        if (o.has(a, c)) a[c]++;
        else a[c] = 1
    });
    o.toArray = function(a) {
        if (!a) return [];
        if (o.isArray(a)) return g.call(a);
        if (w(a)) return o.map(a, o.identity);
        return o.values(a)
    };
    o.size = function(a) {
        if (a == null) return 0;
        return w(a) ? a.length: o.keys(a).length
    };
    o.partition = function(a, b, c) {
        b = q(b, c);
        var d = [],
        e = [];
        o.each(a,
        function(a, c, f) { (b(a, c, f) ? d: e).push(a)
        });
        return [d, e]
    };
    o.first = o.head = o.take = function(a, b, c) {
        if (a == null) return void 0;
        if (b == null || c) return a[0];
        return o.initial(a, a.length - b)
    };
    o.initial = function(a, b, c) {
        return g.call(a, 0, Math.max(0, a.length - (b == null || c ? 1 : b)))
    };
    o.last = function(a, b, c) {
        if (a == null) return void 0;
        if (b == null || c) return a[a.length - 1];
        return o.rest(a, Math.max(0, a.length - b))
    };
    o.rest = o.tail = o.drop = function(a, b, c) {
        return g.call(a, b == null || c ? 1 : b)
    };
    o.compact = function(a) {
        return o.filter(a, o.identity)
    };
    var z = function(a, b, c, d) {
        var e = [],
        f = 0;
        for (var g = d || 0,
        h = v(a); g < h; g++) {
            var i = a[g];
            if (w(i) && (o.isArray(i) || o.isArguments(i))) {
                if (!b) i = z(i, b, c);
                var j = 0,
                k = i.length;
                e.length += k;
                while (j < k) {
                    e[f++] = i[j++]
                }
            } else if (!c) {
                e[f++] = i
            }
        }
        return e
    };
    o.flatten = function(a, b) {
        return z(a, b, false)
    };
    o.without = function(a) {
        return o.difference(a, g.call(arguments, 1))
    };
    o.uniq = o.unique = function(a, b, c, d) {
        if (!o.isBoolean(b)) {
            d = c;
            c = b;
            b = false
        }
        if (c != null) c = q(c, d);
        var e = [];
        var f = [];
        for (var g = 0,
        h = v(a); g < h; g++) {
            var i = a[g],
            j = c ? c(i, g, a) : i;
            if (b) {
                if (!g || f !== j) e.push(i);
                f = j
            } else if (c) {
                if (!o.contains(f, j)) {
                    f.push(j);
                    e.push(i)
                }
            } else if (!o.contains(e, i)) {
                e.push(i)
            }
        }
        return e
    };
    o.union = function() {
        return o.uniq(z(arguments, true, true))
    };
    o.intersection = function(a) {
        var b = [];
        var c = arguments.length;
        for (var d = 0,
        e = v(a); d < e; d++) {
            var f = a[d];
            if (o.contains(b, f)) continue;
            for (var g = 1; g < c; g++) {
                if (!o.contains(arguments[g], f)) break
            }
            if (g === c) b.push(f)
        }
        return b
    };
    o.difference = function(a) {
        var b = z(arguments, true, true, 1);
        return o.filter(a,
        function(a) {
            return ! o.contains(b, a)
        })
    };
    o.zip = function() {
        return o.unzip(arguments)
    };
    o.unzip = function(a) {
        var b = a && o.max(a, v).length || 0;
        var c = Array(b);
        for (var d = 0; d < b; d++) {
            c[d] = o.pluck(a, d)
        }
        return c
    };
    o.object = function(a, b) {
        var c = {};
        for (var d = 0,
        e = v(a); d < e; d++) {
            if (b) {
                c[a[d]] = b[d]
            } else {
                c[a[d][0]] = a[d][1]
            }
        }
        return c
    };
    function A(a) {
        return function(b, c, d) {
            c = q(c, d);
            var e = v(b);
            var f = a > 0 ? 0 : e - 1;
            for (; f >= 0 && f < e; f += a) {
                if (c(b[f], f, b)) return f
            }
            return - 1
        }
    }
    o.findIndex = A(1);
    o.findLastIndex = A( - 1);
    o.sortedIndex = function(a, b, c, d) {
        c = q(c, d, 1);
        var e = c(b);
        var f = 0,
        g = v(a);
        while (f < g) {
            var h = Math.floor((f + g) / 2);
            if (c(a[h]) < e) f = h + 1;
            else g = h
        }
        return f
    };
    function B(a, b, c) {
        return function(d, e, f) {
            var h = 0,
            i = v(d);
            if (typeof f == "number") {
                if (a > 0) {
                    h = f >= 0 ? f: Math.max(f + i, h)
                } else {
                    i = f >= 0 ? Math.min(f + 1, i) : f + i + 1
                }
            } else if (c && f && i) {
                f = c(d, e);
                return d[f] === e ? f: -1
            }
            if (e !== e) {
                f = b(g.call(d, h, i), o.isNaN);
                return f >= 0 ? f + h: -1
            }
            for (f = a > 0 ? h: i - 1; f >= 0 && f < i; f += a) {
                if (d[f] === e) return f
            }
            return - 1
        }
    }
    o.indexOf = B(1, o.findIndex, o.sortedIndex);
    o.lastIndexOf = B( - 1, o.findLastIndex);
    o.range = function(a, b, c) {
        if (b == null) {
            b = a || 0;
            a = 0
        }
        c = c || 1;
        var d = Math.max(Math.ceil((b - a) / c), 0);
        var e = Array(d);
        for (var f = 0; f < d; f++, a += c) {
            e[f] = a
        }
        return e
    };
    var C = function(a, b, c, d, e) {
        if (! (d instanceof b)) return a.apply(c, e);
        var f = s(a.prototype);
        var g = a.apply(f, e);
        if (o.isObject(g)) return g;
        return f
    };
    o.bind = function(a, b) {
        if (l && a.bind === l) return l.apply(a, g.call(arguments, 1));
        if (!o.isFunction(a)) throw new TypeError("Bind must be called on a function");
        var c = g.call(arguments, 2);
        var d = function() {
            return C(a, d, b, this, c.concat(g.call(arguments)))
        };
        return d
    };
    o.partial = function(a) {
        var b = g.call(arguments, 1);
        var c = function() {
            var d = 0,
            e = b.length;
            var f = Array(e);
            for (var g = 0; g < e; g++) {
                f[g] = b[g] === o ? arguments[d++] : b[g]
            }
            while (d < arguments.length) f.push(arguments[d++]);
            return C(a, c, this, this, f)
        };
        return c
    };
    o.bindAll = function(a) {
        var b, c = arguments.length,
        d;
        if (c <= 1) throw new Error("bindAll must be passed function names");
        for (b = 1; b < c; b++) {
            d = arguments[b];
            a[d] = o.bind(a[d], a)
        }
        return a
    };
    o.memoize = function(a, b) {
        var c = function(d) {
            var e = c.cache;
            var f = "" + (b ? b.apply(this, arguments) : d);
            if (!o.has(e, f)) e[f] = a.apply(this, arguments);
            return e[f]
        };
        c.cache = {};
        return c
    };
    o.delay = function(a, b) {
        var c = g.call(arguments, 2);
        return setTimeout(function() {
            return a.apply(null, c)
        },
        b)
    };
    o.defer = o.partial(o.delay, o, 1);
    o.throttle = function(a, b, c) {
        var d, e, f;
        var g = null;
        var h = 0;
        if (!c) c = {};
        var i = function() {
            h = c.leading === false ? 0 : o.now();
            g = null;
            f = a.apply(d, e);
            if (!g) d = e = null
        };
        return function() {
            var j = o.now();
            if (!h && c.leading === false) h = j;
            var k = b - (j - h);
            d = this;
            e = arguments;
            if (k <= 0 || k > b) {
                if (g) {
                    clearTimeout(g);
                    g = null
                }
                h = j;
                f = a.apply(d, e);
                if (!g) d = e = null
            } else if (!g && c.trailing !== false) {
                g = setTimeout(i, k)
            }
            return f
        }
    };
    o.debounce = function(a, b, c) {
        var d, e, f, g, h;
        var i = function() {
            var j = o.now() - g;
            if (j < b && j >= 0) {
                d = setTimeout(i, b - j)
            } else {
                d = null;
                if (!c) {
                    h = a.apply(f, e);
                    if (!d) f = e = null
                }
            }
        };
        return function() {
            f = this;
            e = arguments;
            g = o.now();
            var j = c && !d;
            if (!d) d = setTimeout(i, b);
            if (j) {
                h = a.apply(f, e);
                f = e = null
            }
            return h
        }
    };
    o.wrap = function(a, b) {
        return o.partial(b, a)
    };
    o.negate = function(a) {
        return function() {
            return ! a.apply(this, arguments)
        }
    };
    o.compose = function() {
        var a = arguments;
        var b = a.length - 1;
        return function() {
            var c = b;
            var d = a[b].apply(this, arguments);
            while (c--) d = a[c].call(this, d);
            return d
        }
    };
    o.after = function(a, b) {
        return function() {
            if (--a < 1) {
                return b.apply(this, arguments)
            }
        }
    };
    o.before = function(a, b) {
        var c;
        return function() {
            if (--a > 0) {
                c = b.apply(this, arguments)
            }
            if (a <= 1) b = null;
            return c
        }
    };
    o.once = o.partial(o.before, 2);
    var D = !{
        toString: null
    }.propertyIsEnumerable("toString");
    var E = ["valueOf", "isPrototypeOf", "toString", "propertyIsEnumerable", "hasOwnProperty", "toLocaleString"];
    function F(a, b) {
        var c = E.length;
        var e = a.constructor;
        var f = o.isFunction(e) && e.prototype || d;
        var g = "constructor";
        if (o.has(a, g) && !o.contains(b, g)) b.push(g);
        while (c--) {
            g = E[c];
            if (g in a && a[g] !== f[g] && !o.contains(b, g)) {
                b.push(g)
            }
        }
    }
    o.keys = function(a) {
        if (!o.isObject(a)) return [];
        if (k) return k(a);
        var b = [];
        for (var c in a) if (o.has(a, c)) b.push(c);
        if (D) F(a, b);
        return b
    };
    o.allKeys = function(a) {
        if (!o.isObject(a)) return [];
        var b = [];
        for (var c in a) b.push(c);
        if (D) F(a, b);
        return b
    };
    o.values = function(a) {
        var b = o.keys(a);
        var c = b.length;
        var d = Array(c);
        for (var e = 0; e < c; e++) {
            d[e] = a[b[e]]
        }
        return d
    };
    o.mapObject = function(a, b, c) {
        b = q(b, c);
        var d = o.keys(a),
        e = d.length,
        f = {},
        g;
        for (var h = 0; h < e; h++) {
            g = d[h];
            f[g] = b(a[g], g, a)
        }
        return f
    };
    o.pairs = function(a) {
        var b = o.keys(a);
        var c = b.length;
        var d = Array(c);
        for (var e = 0; e < c; e++) {
            d[e] = [b[e], a[b[e]]]
        }
        return d
    };
    o.invert = function(a) {
        var b = {};
        var c = o.keys(a);
        for (var d = 0,
        e = c.length; d < e; d++) {
            b[a[c[d]]] = c[d]
        }
        return b
    };
    o.functions = o.methods = function(a) {
        var b = [];
        for (var c in a) {
            if (o.isFunction(a[c])) b.push(c)
        }
        return b.sort()
    };
    o.extend = r(o.allKeys);
    o.extendOwn = o.assign = r(o.keys);
    o.findKey = function(a, b, c) {
        b = q(b, c);
        var d = o.keys(a),
        e;
        for (var f = 0,
        g = d.length; f < g; f++) {
            e = d[f];
            if (b(a[e], e, a)) return e
        }
    };
    o.pick = function(a, b, c) {
        var d = {},
        e = a,
        f, g;
        if (e == null) return d;
        if (o.isFunction(b)) {
            g = o.allKeys(e);
            f = p(b, c)
        } else {
            g = z(arguments, false, false, 1);
            f = function(a, b, c) {
                return b in c
            };
            e = Object(e)
        }
        for (var h = 0,
        i = g.length; h < i; h++) {
            var j = g[h];
            var k = e[j];
            if (f(k, j, e)) d[j] = k
        }
        return d
    };
    o.omit = function(a, b, c) {
        if (o.isFunction(b)) {
            b = o.negate(b)
        } else {
            var d = o.map(z(arguments, false, false, 1), String);
            b = function(a, b) {
                return ! o.contains(d, b)
            }
        }
        return o.pick(a, b, c)
    };
    o.defaults = r(o.allKeys, true);
    o.create = function(a, b) {
        var c = s(a);
        if (b) o.extendOwn(c, b);
        return c
    };
    o.clone = function(a) {
        if (!o.isObject(a)) return a;
        return o.isArray(a) ? a.slice() : o.extend({},
        a)
    };
    o.tap = function(a, b) {
        b(a);
        return a
    };
    o.isMatch = function(a, b) {
        var c = o.keys(b),
        d = c.length;
        if (a == null) return ! d;
        var e = Object(a);
        for (var f = 0; f < d; f++) {
            var g = c[f];
            if (b[g] !== e[g] || !(g in e)) return false
        }
        return true
    };
    var G = function(a, b, c, d) {
        if (a === b) return a !== 0 || 1 / a === 1 / b;
        if (a == null || b == null) return a === b;
        if (a instanceof o) a = a._wrapped;
        if (b instanceof o) b = b._wrapped;
        var e = h.call(a);
        if (e !== h.call(b)) return false;
        switch (e) {
        case "[object RegExp]":
        case "[object String]":
            return "" + a === "" + b;
        case "[object Number]":
            if ( + a !== +a) return + b !== +b;
            return + a === 0 ? 1 / +a === 1 / b: +a === +b;
        case "[object Date]":
        case "[object Boolean]":
            return + a === +b
        }
        var f = e === "[object Array]";
        if (!f) {
            if (typeof a != "object" || typeof b != "object") return false;
            var g = a.constructor,
            i = b.constructor;
            if (g !== i && !(o.isFunction(g) && g instanceof g && o.isFunction(i) && i instanceof i) && ("constructor" in a && "constructor" in b)) {
                return false
            }
        }
        c = c || [];
        d = d || [];
        var j = c.length;
        while (j--) {
            if (c[j] === a) return d[j] === b
        }
        c.push(a);
        d.push(b);
        if (f) {
            j = a.length;
            if (j !== b.length) return false;
            while (j--) {
                if (!G(a[j], b[j], c, d)) return false
            }
        } else {
            var k = o.keys(a),
            l;
            j = k.length;
            if (o.keys(b).length !== j) return false;
            while (j--) {
                l = k[j];
                if (! (o.has(b, l) && G(a[l], b[l], c, d))) return false
            }
        }
        c.pop();
        d.pop();
        return true
    };
    o.isEqual = function(a, b) {
        return G(a, b)
    };
    o.isEmpty = function(a) {
        if (a == null) return true;
        if (w(a) && (o.isArray(a) || o.isString(a) || o.isArguments(a))) return a.length === 0;
        return o.keys(a).length === 0
    };
    o.isElement = function(a) {
        return !! (a && a.nodeType === 1)
    };
    o.isArray = j ||
    function(a) {
        return h.call(a) === "[object Array]"
    };
    o.isObject = function(a) {
        var b = typeof a;
        return b === "function" || b === "object" && !!a
    };
    o.each(["Arguments", "Function", "String", "Number", "Date", "RegExp", "Error"],
    function(a) {
        o["is" + a] = function(b) {
            return h.call(b) === "[object " + a + "]"
        }
    });
    if (!o.isArguments(arguments)) {
        o.isArguments = function(a) {
            return o.has(a, "callee")
        }
    }
    if (typeof / . / !="function" && typeof Int8Array != "object") {
        o.isFunction = function(a) {
            return typeof a == "function" || false
        }
    }
    o.isFinite = function(a) {
        return isFinite(a) && !isNaN(parseFloat(a))
    };
    o.isNaN = function(a) {
        return o.isNumber(a) && a !== +a
    };
    o.isBoolean = function(a) {
        return a === true || a === false || h.call(a) === "[object Boolean]"
    };
    o.isNull = function(a) {
        return a === null
    };
    o.isUndefined = function(a) {
        return a === void 0
    };
    o.has = function(a, b) {
        return a != null && i.call(a, b)
    };
    o.noConflict = function() {
        a._ = b;
        return this
    };
    o.identity = function(a) {
        return a
    };
    o.constant = function(a) {
        return function() {
            return a
        }
    };
    o.noop = function() {};
    o.property = t;
    o.propertyOf = function(a) {
        return a == null ?
        function() {}: function(b) {
            return a[b]
        }
    };
    o.matcher = o.matches = function(a) {
        a = o.extendOwn({},
        a);
        return function(b) {
            return o.isMatch(b, a)
        }
    };
    o.times = function(a, b, c) {
        var d = Array(Math.max(0, a));
        b = p(b, c, 1);
        for (var e = 0; e < a; e++) d[e] = b(e);
        return d
    };
    o.random = function(a, b) {
        if (b == null) {
            b = a;
            a = 0
        }
        return a + Math.floor(Math.random() * (b - a + 1))
    };
    o.now = Date.now ||
    function() {
        return (new Date).getTime()
    };
    var H = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#x27;",
        "`": "&#x60;"
    };
    var I = o.invert(H);
    var J = function(a) {
        var b = function(b) {
            return a[b]
        };
        var c = "(?:" + o.keys(a).join("|") + ")";
        var d = RegExp(c);
        var e = RegExp(c, "g");
        return function(a) {
            a = a == null ? "": "" + a;
            return d.test(a) ? a.replace(e, b) : a
        }
    };
    o.escape = J(H);
    o.unescape = J(I);
    o.result = function(a, b, c) {
        var d = a == null ? void 0 : a[b];
        if (d === void 0) {
            d = c
        }
        return o.isFunction(d) ? d.call(a) : d
    };
    var K = 0;
    o.uniqueId = function(a) {
        var b = ++K + "";
        return a ? a + b: b
    };
    o.templateSettings = {
        evaluate: /<%([\s\S]+?)%>/g,
        interpolate: /<%=([\s\S]+?)%>/g,
        escape: /<%-([\s\S]+?)%>/g
    };
    var L = /(.)^/;
    var M = {
        "'": "'",
        "\\": "\\",
        "\r": "r",
        "\n": "n",
        "\u2028": "u2028",
        "\u2029": "u2029"
    };
    var N = /\\|'|\r|\n|\u2028|\u2029/g;
    var O = function(a) {
        return "\\" + M[a]
    };
    o.template = function(a, b, c) {
        if (!b && c) b = c;
        b = o.defaults({},
        b, o.templateSettings);
        var d = RegExp([(b.escape || L).source, (b.interpolate || L).source, (b.evaluate || L).source].join("|") + "|$", "g");
        var e = 0;
        var f = "__p+='";
        a.replace(d,
        function(b, c, d, g, h) {
            f += a.slice(e, h).replace(N, O);
            e = h + b.length;
            if (c) {
                f += "'+\n((__t=(" + c + "))==null?'':_.escape(__t))+\n'"
            } else if (d) {
                f += "'+\n((__t=(" + d + "))==null?'':__t)+\n'"
            } else if (g) {
                f += "';\n" + g + "\n__p+='"
            }
            return b
        });
        f += "';\n";
        if (!b.variable) f = "with(obj||{}){\n" + f + "}\n";
        f = "var __t,__p='',__j=Array.prototype.join," + "print=function(){__p+=__j.call(arguments,'');};\n" + f + "return __p;\n";
        try {
            var g = new Function(b.variable || "obj", "_", f)
        } catch(h) {
            h.source = f;
            throw h
        }
        var i = function(a) {
            return g.call(this, a, o)
        };
        var j = b.variable || "obj";
        i.source = "function(" + j + "){\n" + f + "}";
        return i
    };
    o.chain = function(a) {
        var b = o(a);
        b._chain = true;
        return b
    };
    var P = function(a, b) {
        return a._chain ? o(b).chain() : b
    };
    o.mixin = function(a) {
        o.each(o.functions(a),
        function(b) {
            var c = o[b] = a[b];
            o.prototype[b] = function() {
                var a = [this._wrapped];
                f.apply(a, arguments);
                return P(this, c.apply(o, a))
            }
        })
    };
    o.mixin(o);
    o.each(["pop", "push", "reverse", "shift", "sort", "splice", "unshift"],
    function(a) {
        var b = c[a];
        o.prototype[a] = function() {
            var c = this._wrapped;
            b.apply(c, arguments);
            if ((a === "shift" || a === "splice") && c.length === 0) delete c[0];
            return P(this, c)
        }
    });
    o.each(["concat", "join", "slice"],
    function(a) {
        var b = c[a];
        o.prototype[a] = function() {
            return P(this, b.apply(this._wrapped, arguments))
        }
    });
    o.prototype.value = function() {
        return this._wrapped
    };
    o.prototype.valueOf = o.prototype.toJSON = o.prototype.value;
    o.prototype.toString = function() {
        return "" + this._wrapped
    };
    if (typeof define === "function" && define.amd) {
        define("underscore", [],
        function() {
            return o
        })
    }
}).call(this);
define("app/util/events", ["require", "underscore"],
function(a) {
    var b = a("underscore");
    var c = [];
    var d = c.push;
    var e = c.slice;
    var f = c.splice;
    var g = {
        on: function(a, b, c) {
            if (!i(this, "on", a, [b, c]) || !b) return this;
            this._events || (this._events = {});
            var d = this._events[a] || (this._events[a] = []);
            d.push({
                callback: b,
                context: c,
                ctx: c || this
            });
            return this
        },
        once: function(a, c, d) {
            if (!i(this, "once", a, [c, d]) || !c) return this;
            var e = this;
            var f = b.once(function() {
                e.off(a, f);
                c.apply(this, arguments)
            });
            f._callback = c;
            return this.on(a, f, d)
        },
        off: function(a, c, d) {
            var e, f, g, h, j, k, l, m;
            if (!this._events || !i(this, "off", a, [c, d])) return this;
            if (!a && !c && !d) {
                this._events = void 0;
                return this
            }
            h = a ? [a] : b.keys(this._events);
            for (j = 0, k = h.length; j < k; j++) {
                a = h[j];
                if (g = this._events[a]) {
                    this._events[a] = e = [];
                    if (c || d) {
                        for (l = 0, m = g.length; l < m; l++) {
                            f = g[l];
                            if (c && c !== f.callback && c !== f.callback._callback || d && d !== f.context) {
                                e.push(f)
                            }
                        }
                    }
                    if (!e.length) delete this._events[a]
                }
            }
            return this
        },
        trigger: function(a) {
            if (!this._events) return this;
            var b = e.call(arguments, 1);
            if (!i(this, "trigger", a, b)) return this;
            var c = this._events[a];
            var d = this._events.all;
            if (c) j(c, b);
            if (d) j(d, arguments);
            return this
        },
        stopListening: function(a, c, d) {
            var e = this._listeningTo;
            if (!e) return this;
            var f = !c && !d;
            if (!d && typeof c === "object") d = this;
            if (a)(e = {})[a._listenId] = a;
            for (var g in e) {
                a = e[g];
                a.off(c, d, this);
                if (f || b.isEmpty(a._events)) delete this._listeningTo[g]
            }
            return this
        }
    };
    var h = /\s+/;
    var i = function(a, b, c, d) {
        if (!c) return true;
        if (typeof c === "object") {
            for (var e in c) {
                a[b].apply(a, [e, c[e]].concat(d))
            }
            return false
        }
        if (h.test(c)) {
            var f = c.split(h);
            for (var g = 0,
            i = f.length; g < i; g++) {
                a[b].apply(a, [f[g]].concat(d))
            }
            return false
        }
        return true
    };
    var j = function(a, b) {
        var c, d = -1,
        e = a.length,
        f = b[0],
        g = b[1],
        h = b[2];
        switch (b.length) {
        case 0:
            while (++d < e)(c = a[d]).callback.call(c.ctx);
            return;
        case 1:
            while (++d < e)(c = a[d]).callback.call(c.ctx, f);
            return;
        case 2:
            while (++d < e)(c = a[d]).callback.call(c.ctx, f, g);
            return;
        case 3:
            while (++d < e)(c = a[d]).callback.call(c.ctx, f, g, h);
            return;
        default:
            while (++d < e)(c = a[d]).callback.apply(c.ctx, b);
            return
        }
    };
    var k = {
        listenTo: "on",
        listenToOnce: "once"
    };
    b.each(k,
    function(a, c) {
        g[c] = function(c, d, e) {
            var f = this._listeningTo || (this._listeningTo = {});
            var g = c._listenId || (c._listenId = b.uniqueId("l"));
            f[g] = c;
            if (!e && typeof d === "object") e = this;
            c[a](d, e, this);
            return this
        }
    });
    g.bind = g.on;
    g.unbind = g.off;
    return g
}); (function(a) {
    if (typeof define === "function" && define.amd) {
        define("cookie", ["jquery"], a)
    } else if (typeof exports === "object") {
        a(require("jquery"))
    } else {
        a($)
    }
})(function(a) {
    var b = /\+/g;
    function c(a) {
        return h.raw ? a: encodeURIComponent(a)
    }
    function d(a) {
        return h.raw ? a: decodeURIComponent(a)
    }
    function e(a) {
        return c(h.json ? JSON.stringify(a) : String(a))
    }
    function f(a) {
        if (a.indexOf('"') === 0) {
            a = a.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, "\\")
        }
        try {
            a = decodeURIComponent(a.replace(b, " "));
            return h.json ? JSON.parse(a) : a
        } catch(c) {}
    }
    function g(b, c) {
        var d = h.raw ? b: f(b);
        return a.isFunction(c) ? c(d) : d
    }
    var h = a.cookie = function(b, f, i) {
        if (arguments.length > 1 && !a.isFunction(f)) {
            i = a.extend({},
            h.defaults, i);
            if (typeof i.expires === "number") {
                var j = i.expires,
                k = i.expires = new Date;
                k.setTime( + k + j * 864e5)
            }
            return document.cookie = [c(b), "=", e(f), i.expires ? "; expires=" + i.expires.toUTCString() : "", i.path ? "; path=" + i.path: "", i.domain ? "; domain=" + i.domain: "", i.secure ? "; secure": ""].join("")
        }
        var l = b ? undefined: {};
        var m = document.cookie ? document.cookie.split("; ") : [];
        for (var n = 0,
        o = m.length; n < o; n++) {
            var p = m[n].split("=");
            var q = d(p.shift());
            var r = p.join("=");
            if (b && b === q) {
                l = g(r, f);
                break
            }
            if (!b && (r = g(r)) !== undefined) {
                l[q] = r
            }
        }
        return l
    };
    h.defaults = {};
    a.removeCookie = function(b, c) {
        if (a.cookie(b) === undefined) {
            return false
        }
        a.cookie(b, "", a.extend({},
        c, {
            expires: -1
        }));
        return ! a.cookie(b)
    }
});
define("app/util/ajax", ["require", "jquery", "underscore", "./events", "cookie"],
function(a) {
    "use strict";
    var b = a("jquery");
    var c = a("underscore");
    var d = a("./events");
    a("cookie");
    var e = {
        initialize: function() {},
        requestSuccessCallback: function(a) {
            this.trigger("done", a)
        },
        requestErrorCallback: function(a) {
            this.trigger("fail", a)
        },
        requestAlwaysCallback: function(a) {
            this.trigger("always", a)
        },
        defaultOptions: {
            type: "GET",
            dataType: "json"
        },
        defaultCallbacks: {}
    };
    function f(a) {
        function f(b, d, e) {
            if (c.isString(b)) {
                b = {
                    url: b
                }
            }
            this.opts = b || {};
            this.extraOptions = d || {};
            a.initialize();
            this.bindDefaultCallback();
            this.isSyncAdapter = e;
            this.send()
        }
        c.extend(f.prototype, d, {
            send: function() {
                var d = this;
                if (this.isSyncAdapter) {
                    this.ajax = null;
                    return this
                } else {
                    var e = c.extend({},
                    a.defaultOptions, this.opts);
                    this.ajax = b.ajax(e).done(function() {
                        a.requestSuccessCallback.apply(d, arguments)
                    }).fail(function() {
                        a.requestErrorCallback.apply(d, arguments)
                    }).always(function() {
                        a.requestAlwaysCallback.apply(d, arguments)
                    });
                    return this
                }
            },
            bindDefaultCallback: function() {
                var b = this;
                var d = this.extraOptions;
                var e = [];
                var f = c.keys(a.defaultCallbacks);
                var g = d.preventDefaultCallbacks || [];
                if (g === "all") {
                    e = []
                } else if (c.isArray(g)) {
                    e = c.without.apply(null, [f].concat(g))
                }
                c.forEach(e,
                function(c) {
                    b.on(c, a.defaultCallbacks[c])
                })
            },
            abort: function() {
                this.ajax.abort();
                this.trigger("abort");
                return this
            },
            resend: function() {
                this.abort();
                this.send();
                this.trigger("resend");
                return this
            },
            done: function(a) {
                this.on("done", a);
                return this
            },
            fail: function(a) {
                this.on("fail", a);
                return this
            },
            always: function(a) {
                this.on("always", a);
                return this
            }
        });
        function h(a, b) {
            return new f(a, b)
        }
        var i = true;
        h.syncAdapter = function() {
            return new f(null, null, i)
        };
        h.ajaxSetup = function(b) {
            return c.extend(a.defaultOptions, b)
        };
        h.setDefaultCallbacks = function(b) {
            return c.extend(a.defaultCallbacks, b)
        };
        h.setConfig = function(b) {
            a = g.defaults(a, b);
            return a
        };
        a = g.defaults(e, a);
        return h
    }
    var g = {
        defaults: function(a, b) {
            if (!b) {
                return c.extend({},
                a)
            }
            var d = {};
            var e = c.keys(a);
            c.forEach(e,
            function(e) {
                var f = b[e];
                var g = a[e];
                if (c.isFunction(g)) {
                    d[e] = f || g
                } else if (c.isObject(g)) {
                    d[e] = c.extend({},
                    g, f)
                } else if (c.isArray(g)) {
                    if (c.isArray(f)) {
                        d[e] = f.slice(0)
                    } else {
                        d[e] = g.slice(0)
                    }
                } else {
                    d[e] = f || g
                }
            });
            return d
        }
    };
    function h() {
        var a = 5381;
        var c = b.cookie("skey");
        if (c === null || typeof c === "undefined") {
            c = ""
        }
        var d = 0;
        for (d; d < c.length; d++) {
            a += (a << 5) + c.charCodeAt(d)
        }
        return a & 34359738367
    }
    return f
});
define("app/common/ur_ajax", ["require", "app/util/ajax"],
function(a) {
    var b = a("app/util/ajax");
    return new b
});
define("app/common/api/template", ["require", "exports", "module", "app/common/ur_ajax", "jquery"],
function(a, b, c) {
    var d = a("app/common/ur_ajax");
    var e = a("jquery");
    return {
        getUsageTpye: function(a) {
            var b = a ? "/ctsu": "/survey";
            b = b + "/get_usage_type";
            return d({
                url: b,
                type: "GET"
            })
        },
        getTradeType: function(a) {
            var b = a ? "/ctsu": "/survey";
            b = b + "/get_trade_type";
            return d({
                url: b,
                type: "GET"
            })
        },
        getTemplateList: function(a, b) {
            var c = {
                trade_type_id: undefined,
                usage_type_id: undefined,
                template_name: undefined,
                limit: 10,
                hot: 0,
                page: undefined
            };
            e.extend(c, a);
            var f = b ? "/ctsu": "/survey";
            f = f + "/get_std_template_list";
            return d({
                url: f,
                type: "GET",
                data: c
            })
        },
        getTemplateContentById: function(a, b) {
            var c = b ? "/ctsu": "/survey";
            c = c + "/get_std_template_json";
            return d({
                url: c,
                type: "GET",
                data: {
                    template_id: a
                }
            })
        },
        createTemplate: function(a) {
            var b = {
                trade_type_id: "",
                usage_type_id: "",
                template_id: "",
                template_json: "",
                template_name: "",
                replace_rule: ""
            };
            e.extend(true, b, a);
            return d({
                url: "/ctsu/add_std_template",
                type: "POST",
                data: b
            })
        },
        deleteTemplate: function(a) {
            return d({
                url: "/ctsu/delete_std_template",
                type: "POST",
                data: {
                    template_id: a
                }
            })
        }
    }
}); (function a(b, c, d) {
    function e(g, h) {
        if (!c[g]) {
            if (!b[g]) {
                var i = typeof require == "function" && require;
                if (!h && i) return i(g, !0);
                if (f) return f(g, !0);
                var j = new Error("Cannot find module '" + g + "'");
                throw j.code = "MODULE_NOT_FOUND",
                j
            }
            var k = c[g] = {
                exports: {}
            };
            b[g][0].call(k.exports,
            function(a) {
                var c = b[g][1][a];
                return e(c ? c: a)
            },
            k, k.exports, a, b, c, d)
        }
        return c[g].exports
    }
    var f = typeof require == "function" && require;
    for (var g = 0; g < d.length; g++) e(d[g]);
    return e
})({
    1 : [function(a, b, c) {
        var d = a("cssfilter").FilterCSS;
        var e = a("./util");
        var f = {
            a: ["target", "href", "title"],
            abbr: ["title"],
            address: [],
            area: ["shape", "coords", "href", "alt"],
            article: [],
            aside: [],
            audio: ["autoplay", "controls", "loop", "preload", "src"],
            b: [],
            bdi: ["dir"],
            bdo: ["dir"],
            big: [],
            blockquote: ["cite"],
            br: [],
            caption: [],
            center: [],
            cite: [],
            code: [],
            col: ["align", "valign", "span", "width"],
            colgroup: ["align", "valign", "span", "width"],
            dd: [],
            del: ["datetime"],
            details: ["open"],
            div: [],
            dl: [],
            dt: [],
            em: [],
            font: ["color", "size", "face"],
            footer: [],
            h1: [],
            h2: [],
            h3: [],
            h4: [],
            h5: [],
            h6: [],
            header: [],
            hr: [],
            i: [],
            img: ["src", "alt", "title", "width", "height"],
            ins: ["datetime"],
            li: [],
            mark: [],
            nav: [],
            ol: [],
            p: [],
            pre: [],
            s: [],
            section: [],
            small: [],
            span: [],
            sub: [],
            sup: [],
            strong: [],
            table: ["width", "border", "align", "valign"],
            tbody: ["align", "valign"],
            td: ["width", "colspan", "align", "valign"],
            tfoot: ["align", "valign"],
            th: ["width", "colspan", "align", "valign"],
            thead: ["align", "valign"],
            tr: ["rowspan", "align", "valign"],
            tt: [],
            u: [],
            ul: [],
            video: ["autoplay", "controls", "loop", "preload", "src", "height", "width"]
        };
        var g = new d;
        function h(a, b, c) {}
        function i(a, b, c) {}
        function j(a, b, c) {}
        function k(a, b, c) {}
        function l(a) {
            return a.replace(n, "&lt;").replace(o, "&gt;")
        }
        function m(a, b, c, d) {
            d = d || g;
            c = F(c);
            if (b === "href" || b === "src") {
                c = e.trim(c);
                if (c === "#") return "#";
                if (! (c.substr(0, 7) === "http://" || c.substr(0, 8) === "https://" || c.substr(0, 7) === "mailto:" || c[0] === "/")) {
                    return ""
                }
            } else if (b === "background") {
                v.lastIndex = 0;
                if (v.test(c)) {
                    return ""
                }
            } else if (b === "style") {
                y.lastIndex = 0;
                if (y.test(c)) {
                    return ""
                }
                z.lastIndex = 0;
                if (z.test(c)) {
                    v.lastIndex = 0;
                    if (v.test(c)) {
                        return ""
                    }
                }
                c = d.process(c)
            }
            c = G(c);
            return c
        }
        var n = /</g;
        var o = />/g;
        var p = /"/g;
        var q = /&quot;/g;
        var r = /&#([a-zA-Z0-9]*);?/gim;
        var s = /&colon;?/gim;
        var t = /&newline;?/gim;
        var u = /\/\*|\*\//gm;
        var v = /((j\s*a\s*v\s*a|v\s*b|l\s*i\s*v\s*e)\s*s\s*c\s*r\s*i\s*p\s*t\s*|m\s*o\s*c\s*h\s*a)\:/gi;
        var w = /^[\s"'`]*(d\s*a\s*t\s*a\s*)\:/gi;
        var x = /^[\s"'`]*(d\s*a\s*t\s*a\s*)\:\s*image\//gi;
        var y = /e\s*x\s*p\s*r\s*e\s*s\s*s\s*i\s*o\s*n\s*\(.*/gi;
        var z = /u\s*r\s*l\s*\(.*/gi;
        function A(a) {
            return a.replace(p, "&quot;")
        }
        function B(a) {
            return a.replace(q, '"')
        }
        function C(a) {
            return a.replace(r,
            function b(a, c) {
                return c[0] === "x" || c[0] === "X" ? String.fromCharCode(parseInt(c.substr(1), 16)) : String.fromCharCode(parseInt(c, 10))
            })
        }
        function D(a) {
            return a.replace(s, ":").replace(t, " ")
        }
        function E(a) {
            var b = "";
            for (var c = 0,
            d = a.length; c < d; c++) {
                b += a.charCodeAt(c) < 32 ? " ": a.charAt(c)
            }
            return e.trim(b)
        }
        function F(a) {
            a = B(a);
            a = C(a);
            a = D(a);
            a = E(a);
            return a
        }
        function G(a) {
            a = A(a);
            a = l(a);
            return a
        }
        function H() {
            return ""
        }
        function I(a, b) {
            if (typeof b !== "function") {
                b = function() {}
            }
            var c = !Array.isArray(a);
            function d(b) {
                if (c) return true;
                return e.indexOf(a, b) !== -1
            }
            var f = [];
            var g = false;
            return {
                onIgnoreTag: function(a, c, e) {
                    if (d(a)) {
                        if (e.isClosing) {
                            var h = "[/removed]";
                            var i = e.position + h.length;
                            f.push([g !== false ? g: e.position, i]);
                            g = false;
                            return h
                        } else {
                            if (!g) {
                                g = e.position
                            }
                            return "[removed]"
                        }
                    } else {
                        return b(a, c, e)
                    }
                },
                remove: function(a) {
                    var b = "";
                    var c = 0;
                    e.forEach(f,
                    function(d) {
                        b += a.slice(c, d[0]);
                        c = d[1]
                    });
                    b += a.slice(c);
                    return b
                }
            }
        }
        function J(a) {
            return a.replace(K, "")
        }
        var K = /<!--[\s\S]*?-->/g;
        function L(a) {
            var b = a.split("");
            b = b.filter(function(a) {
                var b = a.charCodeAt(0);
                if (b === 127) return false;
                if (b <= 31) {
                    if (b === 10 || b === 13) return true;
                    return false
                }
                return true
            });
            return b.join("")
        }
        c.whiteList = f;
        c.onTag = h;
        c.onIgnoreTag = i;
        c.onTagAttr = j;
        c.onIgnoreTagAttr = k;
        c.safeAttrValue = m;
        c.escapeHtml = l;
        c.escapeQuote = A;
        c.unescapeQuote = B;
        c.escapeHtmlEntities = C;
        c.escapeDangerHtml5Entities = D;
        c.clearNonPrintableCharacter = E;
        c.friendlyAttrValue = F;
        c.escapeAttrValue = G;
        c.onIgnoreTagStripAll = H;
        c.StripTagBody = I;
        c.stripCommentTag = J;
        c.stripBlankChar = L;
        c.cssFilter = g
    },
    {
        "./util": 4,
        cssfilter: 8
    }],
    2 : [function(a, b, c) {
        var d = a("./default");
        var e = a("./parser");
        var f = a("./xss");
        function g(a, b) {
            var c = new f(b);
            return c.process(a)
        }
        c = b.exports = g;
        c.FilterXSS = f;
        for (var h in d) c[h] = d[h];
        for (var h in e) c[h] = e[h];
        if (typeof define === "function" && define.amd) {
            define("xss", [],
            function() {
                return b.exports
            })
        }
        if (typeof window !== "undefined") {
            window.filterXSS = b.exports
        }
    },
    {
        "./default": 1,
        "./parser": 3,
        "./xss": 5
    }],
    3 : [function(a, b, c) {
        var d = a("./util");
        function e(a) {
            var b = a.indexOf(" ");
            if (b === -1) {
                var c = a.slice(1, -1)
            } else {
                var c = a.slice(1, b + 1)
            }
            c = d.trim(c).toLowerCase();
            if (c.slice(0, 1) === "/") c = c.slice(1);
            if (c.slice( - 1) === "/") c = c.slice(0, -1);
            return c
        }
        function f(a) {
            return a.slice(0, 2) === "</"
        }
        function g(a, b, c) {
            "user strict";
            var d = "";
            var g = 0;
            var h = false;
            var i = false;
            var j = 0;
            var k = a.length;
            var l = "";
            var m = "";
            for (j = 0; j < k; j++) {
                var n = a.charAt(j);
                if (h === false) {
                    if (n === "<") {
                        h = j;
                        continue
                    }
                } else {
                    if (i === false) {
                        if (n === "<") {
                            d += c(a.slice(g, j));
                            h = j;
                            g = j;
                            continue
                        }
                        if (n === ">") {
                            d += c(a.slice(g, h));
                            l = a.slice(h, j + 1);
                            m = e(l);
                            d += b(h, d.length, m, l, f(l));
                            g = j + 1;
                            h = false;
                            continue
                        }
                        if (n === '"' || n === "'") {
                            i = n;
                            continue
                        }
                    } else {
                        if (n === i) {
                            i = false;
                            continue
                        }
                    }
                }
            }
            if (g < a.length) {
                d += c(a.substr(g))
            }
            return d
        }
        var h = /[^a-zA-Z0-9_:\.\-]/gim;
        function i(a, b) {
            "user strict";
            var c = 0;
            var e = [];
            var f = false;
            var g = a.length;
            function i(a, c) {
                a = d.trim(a);
                a = a.replace(h, "").toLowerCase();
                if (a.length < 1) return;
                e.push(b(a, c || ""))
            }
            for (var j = 0; j < g; j++) {
                var k = a.charAt(j),
                l;
                if (f === false && k === "=") {
                    f = a.slice(c, j);
                    c = j + 1;
                    continue
                }
                if (f !== false) {
                    if (j === c && (k === '"' || k === "'")) {
                        var m = a.indexOf(k, j + 1);
                        if (m === -1) {
                            break
                        } else {
                            l = d.trim(a.slice(c + 1, m));
                            i(f, l);
                            f = false;
                            j = m;
                            c = j + 1;
                            continue
                        }
                    }
                }
                if (k === " ") {
                    l = d.trim(a.slice(c, j));
                    if (f === false) {
                        i(l)
                    } else {
                        i(f, l)
                    }
                    f = false;
                    c = j + 1;
                    continue
                }
            }
            if (c < a.length) {
                if (f === false) {
                    i(a.slice(c))
                } else {
                    i(f, a.slice(c))
                }
            }
            return d.trim(e.join(" "))
        }
        c.parseTag = g;
        c.parseAttr = i
    },
    {
        "./util": 4
    }],
    4 : [function(a, b, c) {
        b.exports = {
            indexOf: function(a, b) {
                var c, d;
                if (Array.prototype.indexOf) {
                    return a.indexOf(b)
                }
                for (c = 0, d = a.length; c < d; c++) {
                    if (a[c] === b) {
                        return c
                    }
                }
                return - 1
            },
            forEach: function(a, b, c) {
                var d, e;
                if (Array.prototype.forEach) {
                    return a.forEach(b, c)
                }
                for (d = 0, e = a.length; d < e; d++) {
                    b.call(c, a[d], d, a)
                }
            },
            trim: function(a) {
                if (String.prototype.trim) {
                    return a.trim()
                }
                return a.replace(/(^\s*)|(\s*$)/g, "")
            }
        }
    },
    {}],
    5 : [function(a, b, c) {
        var d = a("cssfilter").FilterCSS;
        var e = a("./default");
        var f = a("./parser");
        var g = f.parseTag;
        var h = f.parseAttr;
        var i = a("./util");
        function j(a) {
            return a === undefined || a === null
        }
        function k(a) {
            var b = a.indexOf(" ");
            if (b === -1) {
                return {
                    html: "",
                    closing: a[a.length - 2] === "/"
                }
            }
            a = i.trim(a.slice(b + 1, -1));
            var c = a[a.length - 1] === "/";
            if (c) a = i.trim(a.slice(0, -1));
            return {
                html: a,
                closing: c
            }
        }
        function l(a) {
            a = a || {};
            if (a.stripIgnoreTag) {
                if (a.onIgnoreTag) {
                    console.error('Notes: cannot use these two options "stripIgnoreTag" and "onIgnoreTag" at the same time')
                }
                a.onIgnoreTag = e.onIgnoreTagStripAll
            }
            a.whiteList = a.whiteList || e.whiteList;
            a.onTag = a.onTag || e.onTag;
            a.onTagAttr = a.onTagAttr || e.onTagAttr;
            a.onIgnoreTag = a.onIgnoreTag || e.onIgnoreTag;
            a.onIgnoreTagAttr = a.onIgnoreTagAttr || e.onIgnoreTagAttr;
            a.safeAttrValue = a.safeAttrValue || e.safeAttrValue;
            a.escapeHtml = a.escapeHtml || e.escapeHtml;
            a.css = a.css || {};
            this.options = a;
            this.cssFilter = new d(a.css)
        }
        l.prototype.process = function(a) {
            a = a || "";
            a = a.toString();
            if (!a) return "";
            var b = this;
            var c = b.options;
            var d = c.whiteList;
            var f = c.onTag;
            var l = c.onIgnoreTag;
            var m = c.onTagAttr;
            var n = c.onIgnoreTagAttr;
            var o = c.safeAttrValue;
            var p = c.escapeHtml;
            var q = b.cssFilter;
            if (c.stripBlankChar) {
                a = e.stripBlankChar(a)
            }
            if (!c.allowCommentTag) {
                a = e.stripCommentTag(a)
            }
            if (c.stripIgnoreTagBody) {
                var r = e.StripTagBody(c.stripIgnoreTagBody, l);
                l = r.onIgnoreTag
            } else {
                r = false
            }
            var s = g(a,
            function(a, b, c, e, g) {
                var r = {
                    sourcePosition: a,
                    position: b,
                    isClosing: g,
                    isWhite: c in d
                };
                var s = f(c, e, r);
                if (!j(s)) return s;
                if (r.isWhite) {
                    if (r.isClosing) {
                        return "</" + c + ">"
                    }
                    var t = k(e);
                    var u = d[c];
                    var v = h(t.html,
                    function(a, b) {
                        var d = i.indexOf(u, a) !== -1;
                        var e = m(c, a, b, d);
                        if (!j(e)) return e;
                        if (d) {
                            b = o(c, a, b, q);
                            if (b) {
                                return a + '="' + b + '"'
                            } else {
                                return a
                            }
                        } else {
                            var e = n(c, a, b, d);
                            if (!j(e)) return e;
                            return
                        }
                    });
                    var e = "<" + c;
                    if (v) e += " " + v;
                    if (t.closing) e += " /";
                    e += ">";
                    return e
                } else {
                    var s = l(c, e, r);
                    if (!j(s)) return s;
                    return p(e)
                }
            },
            p);
            if (r) {
                s = r.remove(s)
            }
            return s
        };
        b.exports = l
    },
    {
        "./default": 1,
        "./parser": 3,
        "./util": 4,
        cssfilter: 8
    }],
    6 : [function(a, b, c) {
        var d = a("./default");
        var e = a("./parser");
        var f = a("./util");
        function g(a) {
            return a === undefined || a === null
        }
        function h(a) {
            a = a || {};
            a.whiteList = a.whiteList || d.whiteList;
            a.onAttr = a.onAttr || d.onAttr;
            a.onIgnoreAttr = a.onIgnoreAttr || d.onIgnoreAttr;
            this.options = a
        }
        h.prototype.process = function(a) {
            a = a || "";
            a = a.toString();
            if (!a) return "";
            var b = this;
            var c = b.options;
            var d = c.whiteList;
            var f = c.onAttr;
            var h = c.onIgnoreAttr;
            var i = e(a,
            function(a, b, c, e, i) {
                var j = d[c];
                var k = false;
                if (j === true) k = j;
                else if (typeof j === "function") k = j(e);
                else if (j instanceof RegExp) k = j.test(e);
                if (k !== true) k = false;
                var l = {
                    position: b,
                    sourcePosition: a,
                    source: i,
                    isWhite: k
                };
                if (k) {
                    var m = f(c, e, l);
                    if (g(m)) {
                        return c + ":" + e
                    } else {
                        return m
                    }
                } else {
                    var m = h(c, e, l);
                    if (!g(m)) {
                        return m
                    }
                }
            });
            return i
        };
        b.exports = h
    },
    {
        "./default": 7,
        "./parser": 9,
        "./util": 10
    }],
    7 : [function(a, b, c) {
        var d = {};
        d["align-content"] = false;
        d["align-items"] = false;
        d["align-self"] = false;
        d["alignment-adjust"] = false;
        d["alignment-baseline"] = false;
        d["all"] = false;
        d["anchor-point"] = false;
        d["animation"] = false;
        d["animation-delay"] = false;
        d["animation-direction"] = false;
        d["animation-duration"] = false;
        d["animation-fill-mode"] = false;
        d["animation-iteration-count"] = false;
        d["animation-name"] = false;
        d["animation-play-state"] = false;
        d["animation-timing-function"] = false;
        d["azimuth"] = false;
        d["backface-visibility"] = false;
        d["background"] = true;
        d["background-attachment"] = true;
        d["background-clip"] = true;
        d["background-color"] = true;
        d["background-image"] = true;
        d["background-origin"] = true;
        d["background-position"] = true;
        d["background-repeat"] = true;
        d["background-size"] = true;
        d["baseline-shift"] = false;
        d["binding"] = false;
        d["bleed"] = false;
        d["bookmark-label"] = false;
        d["bookmark-level"] = false;
        d["bookmark-state"] = false;
        d["border"] = true;
        d["border-bottom"] = true;
        d["border-bottom-color"] = true;
        d["border-bottom-left-radius"] = true;
        d["border-bottom-right-radius"] = true;
        d["border-bottom-style"] = true;
        d["border-bottom-width"] = true;
        d["border-collapse"] = true;
        d["border-color"] = true;
        d["border-image"] = true;
        d["border-image-outset"] = true;
        d["border-image-repeat"] = true;
        d["border-image-slice"] = true;
        d["border-image-source"] = true;
        d["border-image-width"] = true;
        d["border-left"] = true;
        d["border-left-color"] = true;
        d["border-left-style"] = true;
        d["border-left-width"] = true;
        d["border-radius"] = true;
        d["border-right"] = true;
        d["border-right-color"] = true;
        d["border-right-style"] = true;
        d["border-right-width"] = true;
        d["border-spacing"] = true;
        d["border-style"] = true;
        d["border-top"] = true;
        d["border-top-color"] = true;
        d["border-top-left-radius"] = true;
        d["border-top-right-radius"] = true;
        d["border-top-style"] = true;
        d["border-top-width"] = true;
        d["border-width"] = true;
        d["bottom"] = false;
        d["box-decoration-break"] = true;
        d["box-shadow"] = true;
        d["box-sizing"] = true;
        d["box-snap"] = true;
        d["box-suppress"] = true;
        d["break-after"] = true;
        d["break-before"] = true;
        d["break-inside"] = true;
        d["caption-side"] = false;
        d["chains"] = false;
        d["clear"] = true;
        d["clip"] = false;
        d["clip-path"] = false;
        d["clip-rule"] = false;
        d["color"] = true;
        d["color-interpolation-filters"] = true;
        d["column-count"] = false;
        d["column-fill"] = false;
        d["column-gap"] = false;
        d["column-rule"] = false;
        d["column-rule-color"] = false;
        d["column-rule-style"] = false;
        d["column-rule-width"] = false;
        d["column-span"] = false;
        d["column-width"] = false;
        d["columns"] = false;
        d["contain"] = false;
        d["content"] = false;
        d["counter-increment"] = false;
        d["counter-reset"] = false;
        d["counter-set"] = false;
        d["crop"] = false;
        d["cue"] = false;
        d["cue-after"] = false;
        d["cue-before"] = false;
        d["cursor"] = false;
        d["direction"] = false;
        d["display"] = true;
        d["display-inside"] = true;
        d["display-list"] = true;
        d["display-outside"] = true;
        d["dominant-baseline"] = false;
        d["elevation"] = false;
        d["empty-cells"] = false;
        d["filter"] = false;
        d["flex"] = false;
        d["flex-basis"] = false;
        d["flex-direction"] = false;
        d["flex-flow"] = false;
        d["flex-grow"] = false;
        d["flex-shrink"] = false;
        d["flex-wrap"] = false;
        d["float"] = false;
        d["float-offset"] = false;
        d["flood-color"] = false;
        d["flood-opacity"] = false;
        d["flow-from"] = false;
        d["flow-into"] = false;
        d["font"] = true;
        d["font-family"] = true;
        d["font-feature-settings"] = true;
        d["font-kerning"] = true;
        d["font-language-override"] = true;
        d["font-size"] = true;
        d["font-size-adjust"] = true;
        d["font-stretch"] = true;
        d["font-style"] = true;
        d["font-synthesis"] = true;
        d["font-variant"] = true;
        d["font-variant-alternates"] = true;
        d["font-variant-caps"] = true;
        d["font-variant-east-asian"] = true;
        d["font-variant-ligatures"] = true;
        d["font-variant-numeric"] = true;
        d["font-variant-position"] = true;
        d["font-weight"] = true;
        d["grid"] = false;
        d["grid-area"] = false;
        d["grid-auto-columns"] = false;
        d["grid-auto-flow"] = false;
        d["grid-auto-rows"] = false;
        d["grid-column"] = false;
        d["grid-column-end"] = false;
        d["grid-column-start"] = false;
        d["grid-row"] = false;
        d["grid-row-end"] = false;
        d["grid-row-start"] = false;
        d["grid-template"] = false;
        d["grid-template-areas"] = false;
        d["grid-template-columns"] = false;
        d["grid-template-rows"] = false;
        d["hanging-punctuation"] = false;
        d["height"] = true;
        d["hyphens"] = false;
        d["icon"] = false;
        d["image-orientation"] = false;
        d["image-resolution"] = false;
        d["ime-mode"] = false;
        d["initial-letters"] = false;
        d["inline-box-align"] = false;
        d["justify-content"] = false;
        d["justify-items"] = false;
        d["justify-self"] = false;
        d["left"] = false;
        d["letter-spacing"] = true;
        d["lighting-color"] = true;
        d["line-box-contain"] = false;
        d["line-break"] = false;
        d["line-grid"] = false;
        d["line-height"] = false;
        d["line-snap"] = false;
        d["line-stacking"] = false;
        d["line-stacking-ruby"] = false;
        d["line-stacking-shift"] = false;
        d["line-stacking-strategy"] = false;
        d["list-style"] = true;
        d["list-style-image"] = true;
        d["list-style-position"] = true;
        d["list-style-type"] = true;
        d["margin"] = true;
        d["margin-bottom"] = true;
        d["margin-left"] = true;
        d["margin-right"] = true;
        d["margin-top"] = true;
        d["marker-offset"] = false;
        d["marker-side"] = false;
        d["marks"] = false;
        d["mask"] = false;
        d["mask-box"] = false;
        d["mask-box-outset"] = false;
        d["mask-box-repeat"] = false;
        d["mask-box-slice"] = false;
        d["mask-box-source"] = false;
        d["mask-box-width"] = false;
        d["mask-clip"] = false;
        d["mask-image"] = false;
        d["mask-origin"] = false;
        d["mask-position"] = false;
        d["mask-repeat"] = false;
        d["mask-size"] = false;
        d["mask-source-type"] = false;
        d["mask-type"] = false;
        d["max-height"] = true;
        d["max-lines"] = false;
        d["max-width"] = true;
        d["min-height"] = true;
        d["min-width"] = true;
        d["move-to"] = false;
        d["nav-down"] = false;
        d["nav-index"] = false;
        d["nav-left"] = false;
        d["nav-right"] = false;
        d["nav-up"] = false;
        d["object-fit"] = false;
        d["object-position"] = false;
        d["opacity"] = false;
        d["order"] = false;
        d["orphans"] = false;
        d["outline"] = false;
        d["outline-color"] = false;
        d["outline-offset"] = false;
        d["outline-style"] = false;
        d["outline-width"] = false;
        d["overflow"] = false;
        d["overflow-wrap"] = false;
        d["overflow-x"] = false;
        d["overflow-y"] = false;
        d["padding"] = true;
        d["padding-bottom"] = true;
        d["padding-left"] = true;
        d["padding-right"] = true;
        d["padding-top"] = true;
        d["page"] = false;
        d["page-break-after"] = false;
        d["page-break-before"] = false;
        d["page-break-inside"] = false;
        d["page-policy"] = false;
        d["pause"] = false;
        d["pause-after"] = false;
        d["pause-before"] = false;
        d["perspective"] = false;
        d["perspective-origin"] = false;
        d["pitch"] = false;
        d["pitch-range"] = false;
        d["play-during"] = false;
        d["position"] = false;
        d["presentation-level"] = false;
        d["quotes"] = false;
        d["region-fragment"] = false;
        d["resize"] = false;
        d["rest"] = false;
        d["rest-after"] = false;
        d["rest-before"] = false;
        d["richness"] = false;
        d["right"] = false;
        d["rotation"] = false;
        d["rotation-point"] = false;
        d["ruby-align"] = false;
        d["ruby-merge"] = false;
        d["ruby-position"] = false;
        d["shape-image-threshold"] = false;
        d["shape-outside"] = false;
        d["shape-margin"] = false;
        d["size"] = false;
        d["speak"] = false;
        d["speak-as"] = false;
        d["speak-header"] = false;
        d["speak-numeral"] = false;
        d["speak-punctuation"] = false;
        d["speech-rate"] = false;
        d["stress"] = false;
        d["string-set"] = false;
        d["tab-size"] = false;
        d["table-layout"] = false;
        d["text-align"] = true;
        d["text-align-last"] = true;
        d["text-combine-upright"] = true;
        d["text-decoration"] = true;
        d["text-decoration-color"] = true;
        d["text-decoration-line"] = true;
        d["text-decoration-skip"] = true;
        d["text-decoration-style"] = true;
        d["text-emphasis"] = true;
        d["text-emphasis-color"] = true;
        d["text-emphasis-position"] = true;
        d["text-emphasis-style"] = true;
        d["text-height"] = true;
        d["text-indent"] = true;
        d["text-justify"] = true;
        d["text-orientation"] = true;
        d["text-overflow"] = true;
        d["text-shadow"] = true;
        d["text-space-collapse"] = true;
        d["text-transform"] = true;
        d["text-underline-position"] = true;
        d["text-wrap"] = true;
        d["top"] = false;
        d["transform"] = false;
        d["transform-origin"] = false;
        d["transform-style"] = false;
        d["transition"] = false;
        d["transition-delay"] = false;
        d["transition-duration"] = false;
        d["transition-property"] = false;
        d["transition-timing-function"] = false;
        d["unicode-bidi"] = false;
        d["vertical-align"] = false;
        d["visibility"] = false;
        d["voice-balance"] = false;
        d["voice-duration"] = false;
        d["voice-family"] = false;
        d["voice-pitch"] = false;
        d["voice-range"] = false;
        d["voice-rate"] = false;
        d["voice-stress"] = false;
        d["voice-volume"] = false;
        d["volume"] = false;
        d["white-space"] = false;
        d["widows"] = false;
        d["width"] = true;
        d["will-change"] = false;
        d["word-break"] = true;
        d["word-spacing"] = true;
        d["word-wrap"] = true;
        d["wrap-flow"] = false;
        d["wrap-through"] = false;
        d["writing-mode"] = false;
        d["z-index"] = false;
        function e(a, b, c) {}
        function f(a, b, c) {}
        c.whiteList = d;
        c.onAttr = e;
        c.onIgnoreAttr = f
    },
    {}],
    8 : [function(a, b, c) {
        var d = a("./default");
        var e = a("./css");
        function f(a, b) {
            var c = new e(b);
            return c.process(a)
        }
        c = b.exports = f;
        c.FilterCSS = e;
        for (var g in d) c[g] = d[g];
        if (typeof define === "function" && define.amd) {}
        if (typeof window !== "undefined") {
            window.filterCSS = b.exports
        }
    },
    {
        "./css": 6,
        "./default": 7
    }],
    9 : [function(a, b, c) {
        var d = a("./util");
        function e(a, b) {
            a = d.trimRight(a);
            if (a[a.length - 1] !== ";") a += ";";
            var c = a.length;
            var e = false;
            var f = 0;
            var g = 0;
            var h = "";
            function i() {
                if (!e) {
                    var c = d.trim(a.slice(f, g));
                    var i = c.indexOf(":");
                    if (i !== -1) {
                        var j = d.trim(c.slice(0, i));
                        var k = d.trim(c.slice(i + 1));
                        if (j) {
                            var l = b(f, h.length, j, k, c);
                            if (l) h += l + "; "
                        }
                    }
                }
                f = g + 1
            }
            for (; g < c; g++) {
                var j = a[g];
                if (j === "/" && a[g + 1] === "*") {
                    var k = a.indexOf("*/", g + 2);
                    if (k === -1) break;
                    g = k + 1;
                    f = g + 1;
                    e = false
                } else if (j === "(") {
                    e = true
                } else if (j === ")") {
                    e = false
                } else if (j === ";") {
                    if (e) {} else {
                        i()
                    }
                } else if (j === "\n") {
                    i()
                }
            }
            return d.trim(h)
        }
        b.exports = e
    },
    {
        "./util": 10
    }],
    10 : [function(a, b, c) {
        b.exports = {
            indexOf: function(a, b) {
                var c, d;
                if (Array.prototype.indexOf) {
                    return a.indexOf(b)
                }
                for (c = 0, d = a.length; c < d; c++) {
                    if (a[c] === b) {
                        return c
                    }
                }
                return - 1
            },
            forEach: function(a, b, c) {
                var d, e;
                if (Array.prototype.forEach) {
                    return a.forEach(b, c)
                }
                for (d = 0, e = a.length; d < e; d++) {
                    b.call(c, a[d], d, a)
                }
            },
            trim: function(a) {
                if (String.prototype.trim) {
                    return a.trim()
                }
                return a.replace(/(^\s*)|(\s*$)/g, "")
            },
            trimRight: function(a) {
                if (String.prototype.trimRight) {
                    return a.trimRight()
                }
                return a.replace(/(\s*$)/g, "")
            }
        }
    },
    {}]
},
{},
[2]);
define("ur-xss", ["require", "underscore", "xss"],
function(a) {
    var b = a("underscore");
    var c = a("xss");
    var d = {};
    d["align-content"] = false;
    d["align-items"] = false;
    d["align-self"] = false;
    d["alignment-adjust"] = false;
    d["alignment-baseline"] = false;
    d["all"] = false;
    d["anchor-point"] = false;
    d["animation"] = false;
    d["animation-delay"] = false;
    d["animation-direction"] = false;
    d["animation-duration"] = false;
    d["animation-fill-mode"] = false;
    d["animation-iteration-count"] = false;
    d["animation-name"] = false;
    d["animation-play-state"] = false;
    d["animation-timing-function"] = false;
    d["azimuth"] = false;
    d["backface-visibility"] = false;
    d["background"] = true;
    d["background-attachment"] = true;
    d["background-clip"] = true;
    d["background-color"] = true;
    d["background-image"] = true;
    d["background-origin"] = true;
    d["background-position"] = true;
    d["background-repeat"] = true;
    d["background-size"] = true;
    d["baseline-shift"] = false;
    d["binding"] = false;
    d["bleed"] = false;
    d["bookmark-label"] = false;
    d["bookmark-level"] = false;
    d["bookmark-state"] = false;
    d["border"] = true;
    d["border-bottom"] = true;
    d["border-bottom-color"] = true;
    d["border-bottom-left-radius"] = true;
    d["border-bottom-right-radius"] = true;
    d["border-bottom-style"] = true;
    d["border-bottom-width"] = true;
    d["border-collapse"] = true;
    d["border-color"] = true;
    d["border-image"] = true;
    d["border-image-outset"] = true;
    d["border-image-repeat"] = true;
    d["border-image-slice"] = true;
    d["border-image-source"] = true;
    d["border-image-width"] = true;
    d["border-left"] = true;
    d["border-left-color"] = true;
    d["border-left-style"] = true;
    d["border-left-width"] = true;
    d["border-radius"] = true;
    d["border-right"] = true;
    d["border-right-color"] = true;
    d["border-right-style"] = true;
    d["border-right-width"] = true;
    d["border-spacing"] = true;
    d["border-style"] = true;
    d["border-top"] = true;
    d["border-top-color"] = true;
    d["border-top-left-radius"] = true;
    d["border-top-right-radius"] = true;
    d["border-top-style"] = true;
    d["border-top-width"] = true;
    d["border-width"] = true;
    d["bottom"] = false;
    d["box-decoration-break"] = true;
    d["box-shadow"] = true;
    d["box-sizing"] = true;
    d["box-snap"] = true;
    d["box-suppress"] = true;
    d["break-after"] = true;
    d["break-before"] = true;
    d["break-inside"] = true;
    d["caption-side"] = false;
    d["chains"] = false;
    d["clear"] = true;
    d["clip"] = false;
    d["clip-path"] = false;
    d["clip-rule"] = false;
    d["color"] = true;
    d["color-interpolation-filters"] = true;
    d["column-count"] = false;
    d["column-fill"] = false;
    d["column-gap"] = false;
    d["column-rule"] = false;
    d["column-rule-color"] = false;
    d["column-rule-style"] = false;
    d["column-rule-width"] = false;
    d["column-span"] = false;
    d["column-width"] = false;
    d["columns"] = false;
    d["contain"] = false;
    d["content"] = false;
    d["counter-increment"] = false;
    d["counter-reset"] = false;
    d["counter-set"] = false;
    d["crop"] = false;
    d["cue"] = false;
    d["cue-after"] = false;
    d["cue-before"] = false;
    d["cursor"] = false;
    d["direction"] = false;
    d["display"] = true;
    d["display-inside"] = true;
    d["display-list"] = true;
    d["display-outside"] = true;
    d["dominant-baseline"] = false;
    d["elevation"] = false;
    d["empty-cells"] = false;
    d["filter"] = false;
    d["flex"] = false;
    d["flex-basis"] = false;
    d["flex-direction"] = false;
    d["flex-flow"] = false;
    d["flex-grow"] = false;
    d["flex-shrink"] = false;
    d["flex-wrap"] = false;
    d["float"] = false;
    d["float-offset"] = false;
    d["flood-color"] = false;
    d["flood-opacity"] = false;
    d["flow-from"] = false;
    d["flow-into"] = false;
    d["font"] = true;
    d["font-family"] = true;
    d["font-feature-settings"] = true;
    d["font-kerning"] = true;
    d["font-language-override"] = true;
    d["font-size"] = true;
    d["font-size-adjust"] = true;
    d["font-stretch"] = true;
    d["font-style"] = true;
    d["font-synthesis"] = true;
    d["font-variant"] = true;
    d["font-variant-alternates"] = true;
    d["font-variant-caps"] = true;
    d["font-variant-east-asian"] = true;
    d["font-variant-ligatures"] = true;
    d["font-variant-numeric"] = true;
    d["font-variant-position"] = true;
    d["font-weight"] = true;
    d["grid"] = false;
    d["grid-area"] = false;
    d["grid-auto-columns"] = false;
    d["grid-auto-flow"] = false;
    d["grid-auto-rows"] = false;
    d["grid-column"] = false;
    d["grid-column-end"] = false;
    d["grid-column-start"] = false;
    d["grid-row"] = false;
    d["grid-row-end"] = false;
    d["grid-row-start"] = false;
    d["grid-template"] = false;
    d["grid-template-areas"] = false;
    d["grid-template-columns"] = false;
    d["grid-template-rows"] = false;
    d["hanging-punctuation"] = false;
    d["height"] = true;
    d["hyphens"] = false;
    d["icon"] = false;
    d["image-orientation"] = false;
    d["image-resolution"] = false;
    d["ime-mode"] = false;
    d["initial-letters"] = false;
    d["inline-box-align"] = false;
    d["justify-content"] = false;
    d["justify-items"] = false;
    d["justify-self"] = false;
    d["left"] = false;
    d["letter-spacing"] = true;
    d["lighting-color"] = true;
    d["line-box-contain"] = false;
    d["line-break"] = false;
    d["line-grid"] = false;
    d["line-height"] = false;
    d["line-snap"] = false;
    d["line-stacking"] = false;
    d["line-stacking-ruby"] = false;
    d["line-stacking-shift"] = false;
    d["line-stacking-strategy"] = false;
    d["list-style"] = true;
    d["list-style-image"] = true;
    d["list-style-position"] = true;
    d["list-style-type"] = true;
    d["margin"] = true;
    d["margin-bottom"] = true;
    d["margin-left"] = true;
    d["margin-right"] = true;
    d["margin-top"] = true;
    d["marker-offset"] = false;
    d["marker-side"] = false;
    d["marks"] = false;
    d["mask"] = false;
    d["mask-box"] = false;
    d["mask-box-outset"] = false;
    d["mask-box-repeat"] = false;
    d["mask-box-slice"] = false;
    d["mask-box-source"] = false;
    d["mask-box-width"] = false;
    d["mask-clip"] = false;
    d["mask-image"] = false;
    d["mask-origin"] = false;
    d["mask-position"] = false;
    d["mask-repeat"] = false;
    d["mask-size"] = false;
    d["mask-source-type"] = false;
    d["mask-type"] = false;
    d["max-height"] = true;
    d["max-lines"] = false;
    d["max-width"] = true;
    d["min-height"] = true;
    d["min-width"] = true;
    d["move-to"] = false;
    d["nav-down"] = false;
    d["nav-index"] = false;
    d["nav-left"] = false;
    d["nav-right"] = false;
    d["nav-up"] = false;
    d["object-fit"] = false;
    d["object-position"] = false;
    d["opacity"] = false;
    d["order"] = false;
    d["orphans"] = false;
    d["outline"] = false;
    d["outline-color"] = false;
    d["outline-offset"] = false;
    d["outline-style"] = false;
    d["outline-width"] = false;
    d["overflow"] = false;
    d["overflow-wrap"] = false;
    d["overflow-x"] = false;
    d["overflow-y"] = false;
    d["padding"] = true;
    d["padding-bottom"] = true;
    d["padding-left"] = true;
    d["padding-right"] = true;
    d["padding-top"] = true;
    d["page"] = false;
    d["page-break-after"] = false;
    d["page-break-before"] = false;
    d["page-break-inside"] = false;
    d["page-policy"] = false;
    d["pause"] = false;
    d["pause-after"] = false;
    d["pause-before"] = false;
    d["perspective"] = false;
    d["perspective-origin"] = false;
    d["pitch"] = false;
    d["pitch-range"] = false;
    d["play-during"] = false;
    d["position"] = false;
    d["presentation-level"] = false;
    d["quotes"] = false;
    d["region-fragment"] = false;
    d["resize"] = false;
    d["rest"] = false;
    d["rest-after"] = false;
    d["rest-before"] = false;
    d["richness"] = false;
    d["right"] = false;
    d["rotation"] = false;
    d["rotation-point"] = false;
    d["ruby-align"] = false;
    d["ruby-merge"] = false;
    d["ruby-position"] = false;
    d["shape-image-threshold"] = false;
    d["shape-outside"] = false;
    d["shape-margin"] = false;
    d["size"] = false;
    d["speak"] = false;
    d["speak-as"] = false;
    d["speak-header"] = false;
    d["speak-numeral"] = false;
    d["speak-punctuation"] = false;
    d["speech-rate"] = false;
    d["stress"] = false;
    d["string-set"] = false;
    d["tab-size"] = false;
    d["table-layout"] = false;
    d["text-align"] = true;
    d["text-align-last"] = true;
    d["text-combine-upright"] = true;
    d["text-decoration"] = true;
    d["text-decoration-color"] = true;
    d["text-decoration-line"] = true;
    d["text-decoration-skip"] = true;
    d["text-decoration-style"] = true;
    d["text-emphasis"] = true;
    d["text-emphasis-color"] = true;
    d["text-emphasis-position"] = true;
    d["text-emphasis-style"] = true;
    d["text-height"] = true;
    d["text-indent"] = true;
    d["text-justify"] = true;
    d["text-orientation"] = true;
    d["text-overflow"] = true;
    d["text-shadow"] = true;
    d["text-space-collapse"] = true;
    d["text-transform"] = true;
    d["text-underline-position"] = true;
    d["text-wrap"] = true;
    d["top"] = false;
    d["transform"] = false;
    d["transform-origin"] = false;
    d["transform-style"] = false;
    d["transition"] = false;
    d["transition-delay"] = false;
    d["transition-duration"] = false;
    d["transition-property"] = false;
    d["transition-timing-function"] = false;
    d["unicode-bidi"] = false;
    d["vertical-align"] = true;
    d["visibility"] = false;
    d["voice-balance"] = false;
    d["voice-duration"] = false;
    d["voice-family"] = false;
    d["voice-pitch"] = false;
    d["voice-range"] = false;
    d["voice-rate"] = false;
    d["voice-stress"] = false;
    d["voice-volume"] = false;
    d["volume"] = false;
    d["white-space"] = false;
    d["widows"] = false;
    d["width"] = true;
    d["will-change"] = false;
    d["word-break"] = true;
    d["word-spacing"] = true;
    d["word-wrap"] = true;
    d["wrap-flow"] = false;
    d["wrap-through"] = false;
    d["writing-mode"] = false;
    d["z-index"] = false;
    var e = new c.FilterXSS({
        whiteList: {
            a: ["target", "href", "title"],
            abbr: ["title"],
            address: ["style"],
            area: ["shape", "coords", "href", "alt"],
            article: [],
            aside: [],
            audio: ["autoplay", "controls", "loop", "preload", "src"],
            b: ["style"],
            bdi: ["dir"],
            bdo: ["dir"],
            big: [],
            blockquote: ["cite"],
            br: [],
            caption: [],
            center: ["style"],
            cite: [],
            code: [],
            col: ["align", "valign", "span", "width"],
            colgroup: ["align", "valign", "span", "width"],
            dd: [],
            del: ["datetime"],
            details: ["open"],
            div: ["style"],
            dl: ["style"],
            dt: ["style"],
            em: ["style"],
            font: ["color", "size", "face"],
            footer: [],
            h1: ["style"],
            h2: ["style"],
            h3: ["style"],
            h4: ["style"],
            h5: ["style"],
            h6: ["style"],
            header: [],
            hr: [],
            i: ["style"],
            img: ["id", "src", "alt", "title", "width", "height", "style"],
            ins: ["datetime"],
            li: ["style"],
            mark: [],
            nav: [],
            ol: ["style"],
            p: ["style"],
            pre: [],
            s: [],
            section: [],
            small: [],
            span: ["style", "data-id", "class"],
            sub: [],
            sup: [],
            strong: ["style"],
            table: ["width", "border", "align", "valign"],
            tbody: ["align", "valign"],
            td: ["width", "colspan", "align", "valign"],
            tfoot: ["align", "valign"],
            th: ["width", "colspan", "align", "valign"],
            thead: ["align", "valign"],
            tr: ["rowspan", "align", "valign"],
            tt: [],
            u: [],
            ul: ["style"],
            video: ["autoplay", "controls", "loop", "preload", "src", "height", "width"]
        },
        css: {
            whiteList: d
        }
    });
    var f = {
        "&lt;": "<",
        "&gt;": ">",
        "&amp;": "&",
        "&#39;": "'",
        "&quot;": '"',
        "&#96;": "`"
    };
    var g = "(?:" + b.keys(f).join("|") + ")";
    var h = new RegExp(g, "g");
    function i(a) {
        return f[a]
    }
    function j(a) {
        return a.replace(h, i)
    }
    return function(a) {
        a = a || "";
        a = j(a);
        return e.process(a)
    }
});
define("app/common/api/survey", ["require", "jquery", "underscore", "app/common/api/template", "app/common/ur_ajax", "ur-xss"],
function(a) {
    var b = a("jquery");
    var c = a("underscore");
    var d = a("app/common/api/template");
    var e = a("app/common/ur_ajax");
    var f = a("ur-xss");
    var g = 30;
    function h(a) {
        if (a.pages) {
            a.pages.forEach(function(a) {
                a.questions.forEach(function(a) {
                    if (a.options) {
                        a.options.forEach(function(a) {
                            if (a.attachment && a.attachment.length) {
                                a.text += '<img alt="" src="' + a.attachment + '" />';
                                delete a.attachment
                            }
                        })
                    }
                })
            })
        }
        return a
    }
    function i(a) {
        if (a.title) {
            a.title = f(a.title)
        }
        if (a.prefix) {
            a.prefix = f(a.prefix)
        }
        if (a.suffix) {
            a.suffix = f(a.suffix)
        }
        if (a.pages) {
            a.pages.forEach(function(a) {
                a.questions.forEach(function(a) {
                    k(a)
                })
            })
        }
        return a
    }
    function j(a) {
        var c = function(a) {
            a = a.replace(/<img.*?src="(.*?)".*?>/g,
            function(a, b) {
                var c = true;
                var d = new RegExp("^(?:http:|https:)?//");
                if (d.test(b)) {
                    var e = new RegExp("^(?:http:|https:)?//[\\w\\d.]*.qq.com/");
                    if (e.test(b)) {
                        c = false
                    }
                } else {
                    c = false
                }
                if (c) {
                    return "[出于安全原因，图片无法正常显示]"
                } else {
                    return a
                }
            });
            return a
        };
        var d = function(a) {
            if (a.title) {
                a.title = c(a.title)
            }
            if (a.description) {
                a.description = c(a.description)
            }
            if (a.options) {
                a.options.forEach(function(a) {
                    a.text = c(a.text)
                })
            }
            if (a.subTitles) {
                a.subTitles.forEach(function(a) {
                    a.text = c(a.text)
                })
            }
            if (a.groups) {
                d(a.groups)
            }
            function d(a) {
                b.each(a,
                function(a, b) {
                    b[1] = c(b[1]);
                    if (b[2]) {
                        d(b[2])
                    }
                })
            }
            return a
        };
        if (a.title) {
            a.title = c(a.title)
        }
        if (a.prefix) {
            a.prefix = c(a.prefix)
        }
        if (a.suffix) {
            a.suffix = c(a.suffix)
        }
        if (a.pages) {
            a.pages.forEach(function(a) {
                a.questions.forEach(function(a) {
                    d(a)
                })
            })
        }
        return a
    }
    function k(a) {
        if (a.title) {
            a.title = f(a.title)
        }
        if (a.description) {
            a.description = f(a.description)
        }
        if (a.options) {
            a.options.forEach(function(a) {
                a.text = f(a.text)
            })
        }
        if (a.subTitles) {
            a.subTitles.forEach(function(a) {
                a.text = f(a.text)
            })
        }
        if (a.groups) {
            c(a.groups)
        }
        function c(a) {
            b.each(a,
            function(a, b) {
                b[1] = f(b[1]);
                if (b[2]) {
                    c(b[2])
                }
            })
        }
        return a
    }
    function l(a) {
        a.title = b(a.title);
        if (a.prefix) {
            a.prefix = b(a.prefix)
        }
        if (a.suffix) {
            a.suffix = b(a.suffix)
        }
        c.forEach(a.pages,
        function(a) {
            c.forEach(a.questions,
            function(a) {
                a.title = b(a.title)
            })
        });
        function b(a) {
            if (!/^<p>|<\/p>$/.test(a)) {
                return "<p>" + f(a) + "</p>"
            } else {
                return f(a)
            }
        }
    } (function() {
        var a = "onprogress" in b.ajaxSettings.xhr();
        if (!a) {
            return
        }
        var c = b.ajaxSettings.xhr;
        b.ajaxSettings.xhr = function() {
            var a = c();
            if (a instanceof window.XMLHttpRequest) {
                a.addEventListener("progress", this.progress, false)
            }
            if (a.upload) {
                a.upload.addEventListener("progress", this.progressUpload, false)
            }
            return a
        }
    })();
    return {
        getList: function(a, b) {
            return e({
                type: "POST",
                url: "/survey/list",
                data: {
                    p_num: a,
                    limit: b
                }
            }).done(function(a) {
                a.data.list.forEach(function(a) {
                    h(a);
                    i(a)
                })
            })
        },
        _getAllList: function(a, b, c) {
            var d = this;
            d.getList(a, g).done(function(e) {
                b.list = b.list.concat(e.data.list);
                if (e.data.page.p_num >= e.data.page.p_count) {
                    b.page = e.data.page;
                    c.resolve(b)
                } else {
                    d._getAllList(a + 1, b, c)
                }
            })
        },
        getAllList: function() {
            var a = this;
            var c = b.Deferred();
            var d = 1;
            var e = {
                list: [],
                page: {
                    p_num: 1,
                    p_count: 0
                }
            };
            this._getAllList(d, e, c);
            return c
        },
        exportSurvey: function(a, b) {
            return e({
                url: "/survey/export_template_in",
                type: "POST",
                data: {
                    survey_id: a,
                    content: b
                }
            })
        },
        getSurveyRecycle: function(a) {
            return e({
                type: "GET",
                url: "/survey/get_raw_detail_count",
                data: {
                    survey_id: a
                }
            }).done(function(a) {
                a.data = h(a.data);
                a.data = i(a.data)
            })
        },
        getSurveyRecycleEveryDay: function(a) {
            return e({
                type: "GET",
                url: "/survey/stat_every_day_count",
                data: {
                    survey_id: a
                }
            }).done(function(a) {
                a.data = h(a.data);
                a.data = i(a.data)
            })
        },
        getSurvey: function(a, b) {
            return e({
                type: "GET",
                url: "/survey/get_survey",
                data: {
                    id: a
                }
            },
            {
                preventDefaultCallbacks: b
            }).done(function(a) {
                a.data = h(a.data);
                a.data = i(a.data)
            }).fail(function(a) {
                window.location.href = "https://wj.qq.com"
            })
        },
        getSurveyPublic: function(a, b, c, d) {
            return e({
                type: "GET",
                url: "/syx/rest/sur/get_survey",// 加上了[/syx/rest]前缀
                data: {
                    id: a,
                    hash: b,
                    logger: c ? 1 : 0
                }
            },
            {
                preventDefaultCallbacks: d
            }).done(function(a) {
                a.data = h(a.data);
                a.data = i(a.data);
                a.data = j(a.data)
            })
        },
        getReportStat: function(a, b) {
            return e({
                type: "GET",
                url: "/sur/report_stat",
                data: {
                    id: a,
                    hash: b
                }
            }).done(function(a) {
                a.data = h(a.data);
                a.data = i(a.data);
                a.data = j(a.data)
            })
        },
        getSurveyForThumbnail: function(a) {
            return e({
                type: "GET",
                url: "/sur/get_thumb",
                data: {
                    id: a,
                    s: "28bc699656ce8109022787b8bf886415"
                }
            }).done(function(a) {
                a.data = h(a.data);
                a.data = i(a.data)
            })
        },
        createSurvey: function(a, b) {
            return e({
                type: "POST",
                url: "/survey/create_ajax",
                data: {
                    type: 1,
                    ticket: a,
                    randstr: b
                }
            })
        },
        createSurveyByTemplate: function(a) {
            var c = b.Deferred();
            var e = this;
            d.getTemplateContentById(a).done(function(a) {
                var b = JSON.parse(a.data.template_json);
                e.createSurvey().done(function(a) {
                    var d = a.data;
                    b.id = d.survey_id;
                    b.title = b.title + "_副本";
                    e.saveSurvey(b).done(function() {
                        c.resolve(b.id)
                    })
                })
            });
            return c
        },
        saveSurvey: function(a, b) {
            l(a);
            a = c.omit(a, "admin_ids", "whitelist");
            return e({
                type: "POST",
                url: "/survey/save_survey",
                data: {
                    id: a.id,
                    survey_json: JSON.stringify(a)
                },
                progressUpload: b
            })
        },
        saveSurveySetting: function(a, c) {
            var d = b.extend({},
            c);
            if (c.answer_count !== undefined) {
                d.answer_count = c.answer_count === true ? 1 : 0
            }
            if (c.reward !== undefined) {
                d.reward = c.reward === null ? "no": c.reward
            }
            if (c.whitelist !== undefined) {
                if (c.whitelist) {
                    if (c.whitelist_dirty) {
                        d.whitelist = c.whitelist
                    } else {
                        delete d.whitelist
                    }
                } else {
                    d.whitelist = "no"
                }
            }
            return e({
                type: "POST",
                url: "/survey/save_setting",
                data: {
                    id: a,
                    survey_json: JSON.stringify(d)
                }
            })
        },
        getWhiteList: function(a) {
            return e({
                type: "POST",
                url: "/survey/rtx_map_userid",
                data: {
                    rtx_name: a
                }
            })
        },
        getVerifyCode: function(a, b, c) {
            return e({
                type: "POST",
                url: "/survey/answer_notice_verify_send",
                data: {
                    survey_id: a,
                    type: 1,
                    contact: b
                }
            },
            c)
        },
        answerNoticeVerify: function(a, b, c, d) {
            return e({
                type: "POST",
                url: "/survey/answer_notice_verify",
                data: {
                    survey_id: a,
                    type: 1,
                    contact: b,
                    verify_code: c
                }
            },
            d)
        },
        reEditSurvey: function(a) {
            return e({
                type: "POST",
                url: "/survey/survey_status",
                data: {
                    survey_id: a,
                    status: 0
                }
            })
        },
        getSurveyLock: function(a) {
            return e({
                type: "GET",
                url: "/survey/get_lock",
                data: {
                    survey_id: a
                }
            })
        },
        setSurveyLock: function(a) {
            return e({
                type: "POST",
                url: "/survey/set_lock",
                data: {
                    survey_id: a
                }
            })
        },
        clearSurvey: function(a) {
            return e({
                type: "POST",
                url: "/survey/clear_survey",
                data: {
                    survey_id: a
                }
            })
        },
        publishSurvey: function(a) {
            return e({
                type: "POST",
                url: "/survey/survey_status",
                data: {
                    survey_id: a,
                    status: 2
                }
            })
        },
        closeSurvey: function(a) {
            return e({
                type: "POST",
                url: "/survey/survey_status",
                data: {
                    survey_id: a,
                    status: 3
                }
            })
        },
        deleteSurvey: function(a) {
            return e({
                type: "POST",
                url: "/survey/delete",
                data: {
                    survey_id: a
                }
            })
        },
        copySurvey: function(a) {
            return e({
                type: "POST",
                url: "/survey/copy_survey",
                data: {
                    survey_id: a
                }
            }).done(function(a) {
                a.data = h(a.data);
                a.data = i(a.data)
            })
        },
        collectAnswer: function(a, b) {
            var c = a;
            c.referrer = document.referrer || "";
            return e({
                type: "POST",
                url: "/sur/collect_answer",
                data: {
                    survey_id: a.id,
                    answer_survey: JSON.stringify(c)
                }
            },
            {
                preventDefaultCallbacks: b
            })
        },
        report_survey: function(a, b) {
            return e({
                type: "POST",
                url: "/sur/report",
                data: {
                    survey_id: a,
                    hash: b
                }
            })
        },
        getOverview: function(a, b) {
            var d = "?survey_id=" + a;
            if (b.date.start && b.date.end) {
                d += "&startdate=" + b.date.start;
                d += "&enddate=" + b.date.end
            }
            var f = {};
            if (b.filters) {
                c.forEach(b.filters,
                function(a, b) {
                    f["rule-" + b + "-question"] = a.question;
                    f["rule-" + b + "-logic"] = a.logic;
                    f["rule-" + b + "-option"] = a.option;
                    f["rule-" + b + "-type"] = a.type;
                    f["rule-" + b + "-cn"] = a.cn;
                    f["rule-" + b + "-page"] = a.page
                })
            }
            return e({
                type: "POST",
                url: "/survey/report_stat" + d,
                data: f
            }).done(function(a) {
                a.data = h(a.data);
                a.data = i(a.data)
            })
        },
        getCA: function(a) {
            function b(a) {
                var b = k(a.dp_question);
                var d = k(a.indp_question);
                var e = c.forEach(a.series,
                function(a) {
                    a.name = f(a.name)
                });
                return a
            }
            return e({
                type: "GET",
                url: "/survey/survey_cross_analysis",
                data: a
            }).done(function(a) {
                c.forEach(a.data,
                function(a) {
                    b(a)
                })
            })
        },
        getVvInc: function(a) {
            return e({
                type: "GET",
                url: "/survey/report_overview?id=" + a
            })
        },
        listDel: function(a, b) {
            return e({
                type: "POST",
                url: "survey/report_detail_list_del",
                data: {
                    id: a,
                    answer_ids: "[" + b.toString() + "]"
                }
            })
        },
        getDetailList: function(a, b) {
            var c = "?id=" + a;
            if (b.date.start && b.date.end) {
                c += "&date_start=" + b.date.start;
                c += "&date_end=" + b.date.end
            }
            if (b.page_num) {
                c += "&p_num=" + b.page_num;
                c += "&p_limit=" + b.page_limit
            }
            if (b.ip_unique) {
                c += "&ip_unique=" + b.ip_unique
            }
            if (b.device_unique) {
                c += "&device_unique=" + b.device_unique
            }
            if (b.order) {
                c += "&order=" + b.order
            }
            return e({
                type: "GET",
                url: "/survey/report_detail_list" + c
            }).done(function(a) {
                a.data = h(a.data);
                a.data = i(a.data)
            })
        },
        listUpdate: function(a, b, c) {
            return e({
                type: "POST",
                url: "survey/report_detail_one_update",
                data: {
                    id: a,
                    answer_id: b,
                    answer: JSON.stringify(c)
                }
            })
        },
        getAnswerQuestionList: function(a, d, f, g, h, i, j) {
            var k = {
                survey_id: a,
                question_id: d,
                page_num: f,
                page_limit: g,
                file_name_dst: h
            };
            var l = {};
            if (j && j.length) {
                c.forEach(j,
                function(a, b) {
                    l["rule-" + b + "-question"] = a.question;
                    l["rule-" + b + "-logic"] = a.logic;
                    l["rule-" + b + "-option"] = a.option;
                    l["rule-" + b + "-type"] = a.type;
                    l["rule-" + b + "-cn"] = a.cn;
                    l["rule-" + b + "-page"] = a.page
                })
            }
            if (i && i.start && i.end) {
                k.startdate = i.start;
                k.enddate = i.end
            }
            return e({
                type: "POST",
                url: "/sfile/survey/answer_question_list?" + b.param(k),
                data: l
            })
        },
        deleteUploadFiles: function(a, b, c) {
            var d = {
                survey_id: a,
                question_id: b
            };
            if (c && c.length) {
                d.file_name = c.join(",")
            }
            return e({
                type: "POST",
                url: "/sfile/survey/answer_file_delete",
                data: d
            })
        },
        feedback: function() {
            return e({
                type: "GET",
                url: "/survey/tucao_openid_api"
            })
        },
        checkDownloadFile: function(a) {
            var c = {
                survey_id: undefined,
                question_id: undefined,
                type: "excel",
                date_start: undefined,
                date_end: undefined,
                keyword: undefined,
                ip_unique: undefined,
                device_unique: undefined,
                download_old: 0,
                file_limit_count: 5e4,
                file_timestamp: undefined
            };
            c = b.extend({},
            c, a);
            return e({
                type: "GET",
                url: "/survey/report_detail_list_export_check",
                data: c
            })
        },
        packageDownloadFile: function(a) {
            var c = {
                survey_id: undefined,
                question_id: undefined,
                type: "excel",
                date_start: undefined,
                date_end: undefined,
                keyword: undefined,
                ip_unique: undefined,
                device_unique: undefined,
                download_old: 0,
                file_limit_count: 5e4,
                file_timestamp: undefined
            };
            c = b.extend({},
            c, a);
            return e({
                type: "GET",
                url: "/survey/report_detail_list_export_zip",
                data: c
            })
        },
        _download: function(a, b, c, d, e, f, g, h, i, j) {
            console.log("开始下载!")
        },
        execDownloadForZip: function(a, b, c, d, e, f, g, h, i, j) {
            console.log("开始下载打包数据!")
        },
        accessibilityReport: function(a, b, c, d, f, g) {
            return e({
                type: "GET",
                url: "/api/accessibility_report",
                data: {
                    type: a,
                    sid: b,
                    reason: c,
                    json_load_time: d,
                    finish_rate: f,
                    uid: g
                }
            })
        },
        remarkNameEdit: function(a, b) {
            return e({
                type: "POST",
                url: "/survey/remark_name_edit",
                data: {
                    id: a,
                    remark_name: b
                }
            })
        }
    }
});
define("model", ["require", "underscore", "app/util/events"],
function(a) {
    var b = a("underscore");
    var c = a("app/util/events");
    function d(a) {
        e(this, d.prototype.__defaults, a);
        this.initialize(a);
        return this.__getInterface()
    }
    b.extend(d.prototype, c, {
        __defaults: {},
        initialize: function() {},
        toJSON: function() {
            return g(this)
        },
        __getInterface: function() {
            return new f(this)
        },
        __beforeDispatch: function() {
            return true
        },
        __afterDispatch: function() {
            return true
        }
    });
    d.extend = function(a, c) {
        var f = this;
        var g = function(a) {
            e(this, g.prototype.__defaults, a, c);
            this.initialize.apply(this, arguments);
            return this.__getInterface()
        };
        h(g.prototype, f.prototype, a);
        g.prototype.__defaults = e({},
        f.prototype.__defaults, a.defaults);
        g.prototype.__superclass = f;
        g.prototype.__selfclass = g;
        var i = b.keys(f);
        b.forEach(i,
        function(a) {
            g[a] = f[a]
        });
        g.extend = d.extend;
        return g
    };
    function e(a, c, d, e) {
        if (e && e.notDeepCopy) {
            return b.extend(a, i(c), d)
        }
        return b.extend(a, i(c), i(d))
    }
    function f(a) {
        this.__model = a
    }
    b.extend(f.prototype, {
        get: function(a, c) {
            var d = a.split(".");
            var e = this.__model;
            b.forEach(d,
            function(a) {
                e = e[a]
            });
            if (c && c.ref) {
                return e
            }
            return i(e)
        },
        toJSON: function(a, c) {
            if (a) {
                var d = a.split(".");
                var e = this.__model;
                b.forEach(d,
                function(a) {
                    e = e[a]
                });
                return g(e)
            } else {
                if (c && c.ref) {
                    return this.__model
                }
                return g(this.__model)
            }
        },
        exec: function(a) {
            var c = b.toArray(arguments).slice(1);
            return this.__model[a].apply(this.__model, c)
        },
        dispatch: function(a) {
            var c;
            var d = b.toArray(arguments).slice(1);
            var e = this.__model.__beforeDispatch.apply(this.__model, arguments);
            if (!e) {
                return
            }
            c = this.__model["action_" + a].apply(this.__model, d);
            this.__model.__afterDispatch.apply(this.__model, arguments);
            return c
        }
    });
    b.mapObject(c,
    function(a, b) {
        f.prototype[b] = function() {
            return this.__model[b].apply(this.__model, arguments)
        }
    });
    function g(a) {
        var c = b.pick(a, b.without(b.keys(a), "_events"));
        try {
            return i(c)
        } catch(d) {
            if (d instanceof RangeError) {
                return b.extend({},
                c)
            }
        }
    }
    function h() {
        var a, c, d, e, g, i, j = arguments[0] || {},
        k = 1,
        l = arguments.length,
        m = false;
        if (typeof j === "boolean") {
            m = j;
            j = arguments[k] || {};
            k++
        }
        if (typeof j !== "object" && !b.isFunction(j)) {
            j = {}
        }
        if (k === l) {
            j = this;
            k--
        }
        for (; k < l; k++) {
            if ((a = arguments[k]) != null) {
                for (c in a) {
                    d = j[c];
                    e = a[c];
                    if (j === e) {
                        continue
                    }
                    if (m && e && ((g = b.isArray(e)) || b.isObject(e) && !b.isFunction(e))) {
                        if (g) {
                            g = false;
                            i = d && b.isArray(d) ? d: []
                        } else {
                            i = d && b.isObject(d) && !b.isFunction(d) ? d: {}
                        }
                        if (e && e instanceof f) {
                            e = e.toJSON()
                        }
                        j[c] = h(m, i, e)
                    } else if (e !== undefined) {
                        j[c] = e
                    }
                }
            }
        }
        return j
    }
    function i(a) {
        if (a === null || a === undefined) {
            return a
        } else if (b.isFunction(a)) {
            return null
        } else if (b.isArray(a)) {
            return h(true, [], a)
        } else if (b.isObject(a)) {
            return h(true, {},
            a)
        } else {
            return a
        }
    }
    return d
}); !
function(a, b) {
    if (typeof module != "undefined" && module.exports) module.exports["browser"] = b();
    else if (typeof define == "function" && define.amd) define("bowser", b);
    else this[a] = b()
} ("bowser",
function() {
    var a = true;
    function b(b) {
        function c(a) {
            var c = b.match(a);
            return c && c.length > 1 && c[1] || ""
        }
        var d = c(/(ipod|iphone|ipad)/i).toLowerCase(),
        e = /like android/i.test(b),
        f = !e && /android/i.test(b),
        g = c(/version\/(\d+(\.\d+)?)/i),
        h = /tablet/i.test(b),
        i = !h && /[^-]mobi/i.test(b),
        j;
        if (/opera|opr/i.test(b)) {
            j = {
                name: "Opera",
                opera: a,
                version: g || c(/(?:opera|opr)[\s\/](\d+(\.\d+)?)/i)
            }
        } else if (/windows phone/i.test(b)) {
            j = {
                name: "Windows Phone",
                windowsphone: a,
                msie: a,
                version: c(/iemobile\/(\d+(\.\d+)?)/i)
            }
        } else if (/msie|trident/i.test(b)) {
            j = {
                name: "Internet Explorer",
                msie: a,
                version: c(/(?:msie |rv:)(\d+(\.\d+)?)/i)
            }
        } else if (/chrome|crios|crmo/i.test(b)) {
            j = {
                name: "Chrome",
                chrome: a,
                version: c(/(?:chrome|crios|crmo)\/(\d+(\.\d+)?)/i)
            }
        } else if (d) {
            j = {
                name: d == "iphone" ? "iPhone": d == "ipad" ? "iPad": "iPod"
            };
            if (g) {
                j.version = g
            }
        } else if (/sailfish/i.test(b)) {
            j = {
                name: "Sailfish",
                sailfish: a,
                version: c(/sailfish\s?browser\/(\d+(\.\d+)?)/i)
            }
        } else if (/seamonkey\//i.test(b)) {
            j = {
                name: "SeaMonkey",
                seamonkey: a,
                version: c(/seamonkey\/(\d+(\.\d+)?)/i)
            }
        } else if (/firefox|iceweasel/i.test(b)) {
            j = {
                name: "Firefox",
                firefox: a,
                version: c(/(?:firefox|iceweasel)[ \/](\d+(\.\d+)?)/i)
            };
            if (/\((mobile|tablet);[^\)]*rv:[\d\.]+\)/i.test(b)) {
                j.firefoxos = a
            }
        } else if (/silk/i.test(b)) {
            j = {
                name: "Amazon Silk",
                silk: a,
                version: c(/silk\/(\d+(\.\d+)?)/i)
            }
        } else if (f) {
            j = {
                name: "Android",
                version: g
            }
        } else if (/phantom/i.test(b)) {
            j = {
                name: "PhantomJS",
                phantom: a,
                version: c(/phantomjs\/(\d+(\.\d+)?)/i)
            }
        } else if (/blackberry|\bbb\d+/i.test(b) || /rim\stablet/i.test(b)) {
            j = {
                name: "BlackBerry",
                blackberry: a,
                version: g || c(/blackberry[\d]+\/(\d+(\.\d+)?)/i)
            }
        } else if (/(web|hpw)os/i.test(b)) {
            j = {
                name: "WebOS",
                webos: a,
                version: g || c(/w(?:eb)?osbrowser\/(\d+(\.\d+)?)/i)
            };
            /touchpad\//i.test(b) && (j.touchpad = a)
        } else if (/bada/i.test(b)) {
            j = {
                name: "Bada",
                bada: a,
                version: c(/dolfin\/(\d+(\.\d+)?)/i)
            }
        } else if (/tizen/i.test(b)) {
            j = {
                name: "Tizen",
                tizen: a,
                version: c(/(?:tizen\s?)?browser\/(\d+(\.\d+)?)/i) || g
            }
        } else if (/safari/i.test(b)) {
            j = {
                name: "Safari",
                safari: a,
                version: g
            }
        } else j = {};
        if (/(apple)?webkit/i.test(b)) {
            j.name = j.name || "Webkit";
            j.webkit = a;
            if (!j.version && g) {
                j.version = g
            }
        } else if (!j.opera && /gecko\//i.test(b)) {
            j.name = j.name || "Gecko";
            j.gecko = a;
            j.version = j.version || c(/gecko\/(\d+(\.\d+)?)/i)
        }
        if (f || j.silk) {
            j.android = a
        } else if (d) {
            j[d] = a;
            j.ios = a
        }
        var k = "";
        if (d) {
            k = c(/os (\d+([_\s]\d+)*) like mac os x/i);
            k = k.replace(/[_\s]/g, ".")
        } else if (f) {
            k = c(/android[ \/-](\d+(\.\d+)*)/i)
        } else if (j.windowsphone) {
            k = c(/windows phone (?:os)?\s?(\d+(\.\d+)*)/i)
        } else if (j.webos) {
            k = c(/(?:web|hpw)os\/(\d+(\.\d+)*)/i)
        } else if (j.blackberry) {
            k = c(/rim\stablet\sos\s(\d+(\.\d+)*)/i)
        } else if (j.bada) {
            k = c(/bada\/(\d+(\.\d+)*)/i)
        } else if (j.tizen) {
            k = c(/tizen[\/\s](\d+(\.\d+)*)/i)
        }
        if (k) {
            j.osversion = k
        }
        var l = k.split(".")[0];
        if (h || d == "ipad" || f && (l == 3 || l == 4 && !i) || j.silk) {
            j.tablet = a
        } else if (i || d == "iphone" || d == "ipod" || f || j.blackberry || j.webos || j.bada) {
            j.mobile = a
        }
        if (j.msie && j.version >= 10 || j.chrome && j.version >= 20 || j.firefox && j.version >= 20 || j.safari && j.version >= 6 || j.opera && j.version >= 10 || j.ios && j.osversion && j.osversion.split(".")[0] >= 6 || j.blackberry && j.version >= 10.1) {
            j.a = a
        } else if (j.msie && j.version < 10 || j.chrome && j.version < 20 || j.firefox && j.version < 20 || j.safari && j.version < 6 || j.opera && j.version < 10 || j.ios && j.osversion && j.osversion.split(".")[0] < 6) {
            j.c = a
        } else j.x = a;
        return j
    }
    var c = b(typeof navigator !== "undefined" ? navigator.userAgent: "");
    c._detect = b;
    return c
});
define("config/global", ["require"],
function(a) {
    return {
        mode: "production",
        version: "1.2.0"
    }
});
define("app/util/log", ["require", "underscore", "./events"],
function(a) {
    var b = a("underscore");
    var c = a("./events");
    function d(a, b) {
        this.level = a.toUpperCase();
        this.time = new Date;
        this.content = b
    }
    function e() {
        this._level = 30;
        this.logs = []
    }
    var f = {
        DEBUG: 10,
        INFO: 20,
        WARNING: 30,
        ERROR: 40,
        CRITICAL: 50
    };
    b.extend(e.prototype, c, {
        setLevel: function(a) {
            this._level = a
        },
        addLevel: function(a, b) {
            var c = a.toUpperCase();
            var e = a.toLowerCase();
            this[c] = b;
            this[e] = function() {
                if (b < this._level) {
                    return
                }
                var a = new d(e, Array.prototype.slice.call(arguments));
                this.logs.push(a);
                this.trigger(e, a);
                this.trigger("log", a)
            }
        }
    });
    b.each(b.keys(f),
    function(a) {
        a = a.toLowerCase();
        e.prototype[a] = function() {
            if (f[a.toUpperCase()] < this._level) {
                return
            }
            var b = new d(a, Array.prototype.slice.call(arguments));
            this.logs.push(b);
            this.trigger(a, b);
            this.trigger("log", b)
        }
    });
    var g = new e;
    b.extend(g, f);
    window.LOG = g;
    return g
}); (function(a, b) {
    if (typeof define === "function" && define.amd) {
        define("app/util/safe_json", [], b)
    } else if (typeof exports === "object") {
        module.exports = b()
    } else {
        a.SafeJSON = b()
    }
})(this,
function() {
    var a = function(a) {
        var b = typeof a;
        return b === "function" || b === "object" && !!a
    };
    var b = Array.isArray ||
    function(a) {
        return Object.prototype.toString.call(a) === "[object Array]"
    };
    var c = function(a) {
        return Object.prototype.toString.call(a) === "[object String]"
    };
    var d = null;
    var e = {
        TOKEN_NAME: "xbJDIod5",
        TOKEN_FORMAT: "%>$$<%",
        CIRCULAR_MARK: "__circular_references__",
        ROOT_SYMBOL: "obj"
    };
    function f(a) {
        var b = e.ROOT_SYMBOL;
        return g(a, b, [])
    }
    function g(c, d, f) {
        var h;
        var i = e.TOKEN_NAME;
        if (a(c)) {
            if (b(c)) {
                h = []
            } else {
                h = {}
            }
            if (f.indexOf(c) > -1) {
                return e.TOKEN_FORMAT.replace("$$", c[i])
            }
            c[i] = d;
            f.push(c);
            for (var j in c) {
                if (c.hasOwnProperty(j) && j !== i) {
                    h[j] = g(c[j], d + "." + j, f)
                }
            }
            f.pop();
            delete c[i];
            return h
        } else {
            return c
        }
    }
    function h(e, f) {
        for (var g in e) {
            if (e.hasOwnProperty(g)) {
                var i = e[g];
                if (a(i) || b(i)) {
                    e[g] = h(i, f)
                } else if (c(i)) {
                    if (d.test(i)) {
                        var j = i.match(d)[1];
                        var k = j.split(".");
                        var l = f;
                        for (var m = 1; m < k.length; m++) {
                            l = l[k[m]]
                        }
                        e[g] = l
                    }
                }
            }
        }
        return e
    }
    e.stringify = function(a) {
        var b;
        try {
            b = JSON.stringify(a)
        } catch(c) {
            var d = f(a);
            d[e.CIRCULAR_MARK] = 1;
            b = JSON.stringify(d)
        }
        return b
    };
    e.parse = function(a) {
        var b = JSON.parse(a);
        if (b[e.CIRCULAR_MARK]) {
            delete b[e.CIRCULAR_MARK];
            d = new RegExp(("^" + e.TOKEN_FORMAT + "$").replace("$$", "(.*?)"));
            return h(b, b)
        } else {
            return b
        }
    };
    return e
});
define("ur-log", ["require", "underscore", "jquery", "bowser", "config/global", "app/util/log", "app/util/safe_json"],
function(a) {
    var b = a("underscore");
    var c = a("jquery");
    var d = a("bowser");
    var e = a("config/global");
    var f = a("app/util/log");
    var g = a("app/util/safe_json");
    f.addLevel("mark", 999);
    var h = {
        DEBUG: ["black", "white"],
        INFO: ["blue", "white"],
        WARNING: ["white", "orange"],
        ERROR: ["white", "red"],
        CRITICAL: ["white", "red"],
        MARK: ["green", "white"]
    };
    if (!window.console) {
        window.console = {
            log: function() {},
            info: function() {},
            error: function() {}
        }
    }
    if (e.mode === "development") {
        f.setLevel(f.DEBUG);
        if (window.console && window.console.log) {
            if (d.chrome === true) {
                f.on("log",
                function(a) {
                    console.log.apply(console, i(a))
                })
            } else {
                f.on("log",
                function(a) {
                    a = i(a);
                    console.log(a[0], "  ", a[1], "  ", a[2])
                })
            }
        }
    } else {
        f.setLevel(f.INFO);
        window.onerror = function() {
            f.error("uncaught exception:", arguments)
        };
        f.on("warning error critical",
        function(a) {
            l(a)
        });
        f.on("mark",
        function(a) {
            var b = a.content[0];
            var c = window.localStorage.getItem("_log_system_mark_");
            var d = JSON.parse(c) || {};
            if (!d[b]) {
                d[b] = true;
                window.localStorage.setItem("_log_system_mark_", JSON.stringify(d));
                l(a)
            }
        });
        if (window.console && window.console.log) {
            window.console.log = function() {}
        }
    }
    function i(a) {
        var b = o(a.time);
        if (d.chrome) {
            return ["%c" + b + " " + a.level + ":", "color:" + h[a.level][0] + ";" + "background:" + h[a.level][1] + ";"].concat(a.content)
        } else {
            return [b, a.level, a.content]
        }
    }
    var j = null;
    var k = 30 * 1e3;
    function l(a) {
        if (j) {
            return
        }
        var d = {
            ua: window.navigator.userAgent,
            version: e.version,
            url: window.location.href,
            referrer: document.referrer,
            level: a.level,
            metaStack: b.map(f.logs.slice( - 15), n),
            stack: b.map(f.logs.slice( - 3), m)
        };
        j = c.ajax({
            url: "/survey/fe_log",
            type: "POST",
            data: {
                content: JSON.stringify(d)
            }
        });
        window.setTimeout(function() {
            j = null
        },
        k)
    }
    function m(a) {
        var b = o(a.time);
        return [b + " " + a.level + ":"].concat(g.stringify(a.content)).join(" ")
    }
    function n(a) {
        var c = o(a.time);
        var d = b.filter(a.content,
        function(a) {
            return ! b.isObject(a)
        });
        return [c + " " + a.level + ":"].concat(g.stringify(d)).join(" ")
    }
    function o(a) {
        var b = a.getFullYear();
        var c = h(a.getMonth() + 1, 2);
        var d = h(a.getDate(), 2);
        var e = h(a.getHours(), 2);
        var f = h(a.getMinutes(), 2);
        var g = h(a.getSeconds(), 2);
        function h(a, b) {
            var c = String(a);
            var d = b - c.length;
            while (d--) {
                c = "0" + c
            }
            return c
        }
        return "" + b + "-" + c + "-" + d + " " + e + ":" + f + ":" + g
    }
    if (window.console && typeof window.console.time == "undefined") {
        console.time = function(a, b) {
            if (!a) {
                return
            }
            var c = (new Date).getTime();
            if (!console.timeCounters) {
                console.timeCounters = {}
            }
            var d = "KEY" + a.toString();
            if (!b && console.timeCounters[d]) {
                return
            }
            console.timeCounters[d] = c
        };
        console.timeEnd = function(a) {
            var b = (new Date).getTime();
            if (!console.timeCounters) {
                return
            }
            var c = "KEY" + a.toString();
            var d = console.timeCounters[c];
            var e;
            if (d) {
                e = b - d;
                var f = a + ": " + e + "ms";
                console.info(f);
                delete console.timeCounters[c]
            }
            return e
        }
    }
    return f
});
define("app/util/uuid", ["require"],
function(a) {
    return function() {
        var a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");
        return function(b, c) {
            var d = a,
            e = [],
            f = Math.random;
            c = c || d.length;
            if (b) {
                for (var g = 0; g < b; g++) e[g] = d[0 | f() * c]
            } else {
                var h;
                e[8] = e[13] = e[18] = e[23] = "-";
                e[14] = "4";
                for (var g = 0; g < 36; g++) {
                    if (!e[g]) {
                        h = 0 | f() * 16;
                        e[g] = d[g == 19 ? h & 3 | 8 : h & 15]
                    }
                }
            }
            return e.join("")
        }
    } ()
});
define("app/common/models/collect_answer/_basic", ["require", "underscore", "model"],
function(a) {
    var b = a("underscore");
    var c = a("model");
    var d = c.extend({
        defaults: {
            id: null,
            title: "",
            description: "",
            required: true,
            "goto": "",
            answer_texts: [],
            blanks: [],
            blank_setting: []
        },
        getRenderFormat: function() {
            return this.toJSON()
        },
        isEmpty: function(a) {
            return !! a
        },
        validateAnswer: function(a, b) {
            return b
        },
        getSubmitFormat: function(a) {
            return a
        },
        isValidAnswer: function(a, b) {
            a.id = a.id + "";
            a.type = this.type;
            if (!this.required && this.isEmpty(a, b)) {
                return b
            }
            if (this.required && this.isEmpty(a, b)) {
                b.error({
                    id: a.id,
                    type: "这道题必须回答哦"
                });
                return b
            }
            this.validateAnswerForFillblank(a, b);
            return this.validateAnswer(a, b)
        },
        getProgressTotal: function() {
            var a = 1;
            return a + this.getProgressTotalForBlank()
        },
        getProgressAnswered: function(a) {
            var b = this.isEmpty(a) ? 0 : 1;
            return b + this.getProgressAnsweredForBlank(a)
        },
        getProgressTotalForBlank: function() {
            var a = this.blank_setting.filter(function(a) {
                if (a.type === "option") {
                    return false
                }
                return a.required === true
            });
            return a.length
        },
        getProgressAnsweredForBlank: function(a) {
            var c = [];
            b.each(this.blank_setting,
            function(a) {
                if (a.type === "option") {
                    return
                }
                if (a.required === true) {
                    c.push(a.id)
                }
            });
            var d = a.blanks.filter(function(a) {
                return c.indexOf(a.id) >= 0 && a.value !== ""
            });
            return d.length
        },
        validateAnswerForFillblank: function(a, c) {
            b.each(this.blank_setting, d);
            function d(b) {
                var d = b.id;
                var f = a.blanks.filter(function(a) {
                    return a.id === d
                });
                var g = f.length === 0 ? "": f[0].value;
                if (b.type === "question") {
                    if (b.required === true && g === "") {
                        c.error({
                            id: d,
                            type: "这个空必填"
                        });
                        return
                    }
                }
                if (b.type === "option") {
                    if (b.required === true && g === "") {
                        var h = 0;
                        for (h; h < a.options.length; h++) {
                            if (a.options[h].id === b.attach_id && a.options[h].checked) {
                                c.error({
                                    id: d,
                                    type: "这个空必填"
                                })
                            }
                        }
                        return
                    }
                }
                if (b.maxLength !== "" && g.length > b.maxLength) {
                    c.error({
                        id: d,
                        type: "这个填空字数超长"
                    });
                    return
                }
                if (b.validate) {
                    var i = e[b.validate](g);
                    if (!i.valid) {
                        c.error({
                            id: d,
                            type: i.message
                        })
                    }
                }
            }
        }
    });
    var e = {
        number: function(a) {
            return {
                message: "请输入数字",
                valid: /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(a)
            }
        },
        date: function(a) {
            function b(a, c, d) {
                a = a.toString();
                c = c.toString();
                d = d.toString();
                return a.length >= d ? a: b(c + a, c, d)
            }
            var c = new Date;
            var d = c.getFullYear();
            var e = b(c.getMonth() + 1, 0, 2);
            var f = b(c.getDate(), 0, 2);
            return {
                message: "请输入日期，如" + d + "-" + e + "-" + f,
                valid: /^\d{4}[\/\-]\d{1,2}[\/\-]\d{1,2}$/.test(a)
            }
        },
        email: function(a) {
            return {
                message: "请输入电子邮箱",
                valid: /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(a)
            }
        },
        chinese: function(a) {
            return {
                message: "请输入中文",
                valid: /^[\u4e00-\u9fa5]+$/.test(a)
            }
        },
        english: function(a) {
            return {
                message: "请输入英文",
                valid: /^[a-z]+$/i.test(a)
            }
        },
        url: function(a) {
            return {
                message: "请输入网址",
                valid: /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(a)
            }
        },
        idCard: function(a) {
            return {
                message: "请输入身份证号码",
                valid: /(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/.test(a)
            }
        },
        qq: function(a) {
            return {
                message: "请输入QQ号码",
                valid: /^[1-9]\d{4,11}$/.test(a)
            }
        },
        mobile: function(a) {
            return {
                message: "请输入手机号码",
                valid: /^(\+\d{1,3})?1\d{10}$/.test(a)
            }
        },
        phone: function(a) {
            return {
                message: "请输入电话号码",
                valid: /^(\d{3,4}-?)?\d{7,9}$/g.test(a)
            }
        }
    };
    return d
});
define("app/common/models/collect_answer/_radio_class", ["require", "underscore", "./_basic"],
function(a) {
    var b = a("underscore");
    var c = a("./_basic");
    var d = c.extend({
        defaults: {
            random: false,
            options: [],
            refer: null
        },
        getRenderFormat: function() {
            if (this.random) {
                var a = this.options;
                var c = [];
                var d = b.filter(this.options,
                function(a, b) {
                    a.o_index = b;
                    if (a.noRandom) {
                        return false
                    } else {
                        c.push(b);
                        return true
                    }
                });
                var e = b.shuffle(d);
                b.forEach(c,
                function(b, c) {
                    a[b] = e[c]
                })
            }
            return this.toJSON()
        },
        getSubmitFormat: function(a) {
            var c = {};
            var d = this;
            var e = d.type === "select";
            if (a && a.options) {
                b.forEach(a.options,
                function(a) {
                    c[a.id] = a
                })
            }
            var f = a.blanks.filter(function(a) {
                return a.attach_id === "98"
            }).join(",");
            var g = b.sortBy(this.options,
            function(a) {
                return a.o_index
            });
            g = b.map(g,
            function(a) {
                var b = a.id;
                var d = c[a.id];
                var g = d && d.checked ? 1 : 0;
                var h = {
                    id: b,
                    checked: g,
                    text: ""
                };
                if (!g) {
                    return h
                }
                if (b == "98") {
                    h.text = f
                } else {
                    h.text = a.text
                }
                if (!e) {
                    h.answer_texts = d.answer_texts.length !== 0 ? d.answer_texts: undefined
                }
                return h
            });
            return {
                id: this.id,
                type: this.type,
                text: "",
                options: g,
                answer_texts: a.answer_texts.length !== 0 ? a.answer_texts: undefined,
                blanks: a.blanks
            }
        },
        isEmpty: function(a) {
            return b.every(a.options,
            function(a) {
                return a.checked === false
            })
        },
        getStat: function(a, c) {
            var d = this;
            if (!d.stat_info) {
                d.stat_info = {
                    options: {},
                    total: {
                        answer_total: 0
                    }
                }
            }
            d.stats_choose_count = b.map(d.options,
            function(a) {
                var b = a.id;
                var c = d.stat_info.options[b];
                if (c) {
                    return c.count
                } else {
                    return 0
                }
            });
            d.stats_answer_count = d.stat_info.total.answer_total;
            var e = d.stats_answer_count + 1;
            var f = b.map(a.options,
            function(a, b) {
                if (a.checked) {
                    return d.stats_choose_count[b] + 1
                } else {
                    return d.stats_choose_count[b]
                }
            });
            var g = b.map(f,
            function(a, b) {
                return Math.round(a * 1e3 / e) / 10
            });
            return {
                count: f,
                rate: g
            }
        }
    });
    return d
});
define("app/common/models/collect_answer/radio", ["require", "underscore", "./_radio_class"],
function(a) {
    var b = a("underscore");
    var c = a("./_radio_class");
    var d = c.extend({
        defaults: {
            type: "radio",
            maxRow: "1"
        },
        validateAnswer: function(a, c) {
            var d = b.countBy(a.options,
            function(a) {
                return a.checked ? "yes": "no"
            });
            if (d.yes > 1) {
                c.error({
                    id: a.id,
                    type: "单选题只能选择一个选项"
                })
            }
            return c
        }
    });
    return d
});
define("app/common/models/collect_answer/checkbox", ["require", "underscore", "./radio"],
function(a) {
    var b = a("underscore");
    var c = a("./radio");
    var d = c.extend({
        defaults: {
            type: "checkbox",
            maxlength: 0
        },
        validateAnswer: function(a, c) {
            var d = b.countBy(a.options,
            function(a) {
                return a.checked ? "yes": "no"
            });
            if (Number(this.maxlength) !== 0 && d.yes > Number(this.maxlength)) {
                c.error({
                    id: a.id,
                    type: "最多选" + this.maxlength + "项"
                })
            }
            return c
        }
    });
    return d
});
define("app/common/models/collect_answer/select", ["require", "./_radio_class", "underscore"],
function(a) {
    var b = a("./_radio_class");
    var c = a("underscore");
    var d = b.extend({
        defaults: {
            type: "select"
        },
        validateAnswer: function(a, b) {
            var d = c.countBy(b.options,
            function(a) {
                return a.checked ? "yes": "no"
            });
            if (d.yes > 1) {
                a.error({
                    id: b.id,
                    type: "下拉题只能选择一个选项"
                })
            }
            return a
        }
    });
    return d
});
define("app/common/models/collect_answer/_text_class", ["require", "jquery", "./_basic"],
function(a) {
    var b = a("jquery");
    var c = a("./_basic");
    var d = c.extend({
        defaults: {},
        isEmpty: function(a) {
            return b.trim(a.text).length === 0
        },
        getSubmitFormat: function(a) {
            var b = a ? a.text: "";
            return {
                id: this.id,
                type: this.type,
                text: b,
                options: [],
                answer_texts: a.answer_texts.length !== 0 ? a.answer_texts: undefined,
                blanks: a.blanks
            }
        }
    });
    return d
});
define("app/common/models/collect_answer/text", ["require", "./_text_class"],
function(a) {
    var b = a("./_text_class");
    var c = {
        number: function(a) {
            return {
                message: "请输入数字",
                valid: /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(a)
            }
        },
        date: function(a) {
            function b(a, c, d) {
                a = a.toString();
                c = c.toString();
                d = d.toString();
                return a.length >= d ? a: b(c + a, c, d)
            }
            var c = new Date;
            var d = c.getFullYear();
            var e = b(c.getMonth() + 1, 0, 2);
            var f = b(c.getDate(), 0, 2);
            return {
                message: "请输入日期，如" + d + "-" + e + "-" + f,
                valid: /^\d{4}[\/\-]\d{1,2}[\/\-]\d{1,2}$/.test(a)
            }
        },
        email: function(a) {
            return {
                message: "请输入电子邮箱",
                valid: /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(a)
            }
        },
        chinese: function(a) {
            return {
                message: "请输入中文",
                valid: /^[\u4e00-\u9fa5]+$/.test(a)
            }
        },
        english: function(a) {
            return {
                message: "请输入英文",
                valid: /^[a-z]+$/i.test(a)
            }
        },
        url: function(a) {
            return {
                message: "请输入网址",
                valid: /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(a)
            }
        },
        idCard: function(a) {
            return {
                message: "请输入身份证号码",
                valid: /(^\d{15}$)|(^\d{17}([0-9]|X|x)$)/.test(a)
            }
        },
        qq: function(a) {
            return {
                message: "请输入QQ号码",
                valid: /^[1-9]\d{4,11}$/.test(a)
            }
        },
        mobile: function(a) {
            return {
                message: "请输入手机号码",
                valid: /^(\+\d{1,3})?1\d{10}$/.test(a)
            }
        },
        phone: function(a) {
            return {
                message: "请输入电话号码",
                valid: /^(\d{3,4}-?)?\d{7,9}$/g.test(a)
            }
        }
    };
    var d = b.extend({
        defaults: {
            type: "text",
            minlength: "",
            maxlength: "",
            validate: ""
        },
        validateAnswer: function(a, b) {
            if (this.maxlength) {
                if ($.trim(a.text).length > this.maxlength) {
                    b.error({
                        id: a.id,
                        type: "最多只能输入" + this.maxlength + "个字"
                    })
                }
            }
            if (this.validate) {
                var d = c[this.validate]($.trim(a.text));
                if (!d.valid) {
                    b.error({
                        id: a.id,
                        type: d.message
                    })
                }
            }
            return b
        }
    });
    return d
});
define("app/common/models/collect_answer/textarea", ["require", "./_text_class"],
function(a) {
    var b = a("./_text_class");
    var c = b.extend({
        defaults: {
            type: "textarea",
            cols: "",
            rows: ""
        }
    });
    return c
});
define("app/common/models/collect_answer/_matrix_class", ["require", "exports", "module", "underscore", "./_basic"],
function(a, b, c) {
    var d = a("underscore");
    var e = a("./_basic");
    var f = e.extend({
        defaults: {
            subTitles: [],
            options: []
        },
        isEmpty: function(a, b) {
            if (this.refer) {
                console.log(b);
                var c = this.getProgressTotal(b);
                var e = this.getProgressAnswered(a);
                return c != e
            } else {
                return d.some(a.groups,
                function(a) {
                    return d.every(a.options,
                    function(a) {
                        return Boolean(a.checked) === false
                    })
                })
            }
        },
        getProgressTotal: function(a) {
            var b;
            if (this.refer) {
                var c = this.id;
                b = d.find(a.refer,
                function(a) {
                    return a.id === c
                }).options.length
            } else {
                b = this.subTitles.length
            }
            return b + this.getProgressTotalForBlank()
        },
        getProgressAnswered: function(a) {
            var b = 0;
            d.each(a.groups,
            function(a) {
                if (d.some(a.options,
                function(a) {
                    return a.checked === true
                })) {
                    b += 1
                }
            });
            return b + this.getProgressAnsweredForBlank(a)
        },
        getSubmitFormat: function(a) {
            var b = this;
            var c = {};
            if (a && a.groups) {
                d.each(a.groups,
                function(a) {
                    c[a.id] = {};
                    d.each(a.options,
                    function(b) {
                        c[a.id][b.id] = b
                    })
                })
            }
            var e = d.map(this.subTitles,
            function(a) {
                var e = d.map(b.options,
                function(b) {
                    var d;
                    if (c[a.id] && c[a.id][b.id] && c[a.id][b.id].checked) {
                        d = 1
                    } else {
                        d = 0
                    }
                    return {
                        id: b.id,
                        checked: d,
                        text: d ? b.text: ""
                    }
                });
                return {
                    id: a.id,
                    options: e
                }
            });
            return {
                id: this.id,
                type: this.type,
                text: "",
                groups: e,
                answer_texts: a.answer_texts.length !== 0 ? a.answer_texts: undefined,
                blanks: a.blanks
            }
        }
    });
    c.exports = f
});
define("app/common/models/collect_answer/matrix_radio", ["require", "exports", "module", "./_matrix_class"],
function(a, b, c) {
    var d = a("./_matrix_class");
    var e = d.extend({
        defaults: {
            type: "matrix_radio"
        }
    });
    c.exports = e
});
define("app/common/models/collect_answer/matrix_checkbox", ["require", "./_matrix_class"],
function(a) {
    var b = a("./_matrix_class");
    var c = b.extend({
        defaults: {
            type: "matrix_checkbox"
        }
    });
    return c
});
define("app/common/models/collect_answer/matrix_star", ["require", "exports", "module", "underscore", "./_matrix_class"],
function(a, b, c) {
    var d = a("underscore");
    var e = a("./_matrix_class");
    var f = e.extend({
        defaults: {
            type: "matrix_star"
        },
        isEmpty: function(a, b) {
            if (this.refer) {
                var c = this.getProgressTotal(b);
                var e = this.getProgressAnswered(a);
                return c != e
            } else {
                if (a.groups.length === 0) {
                    return true
                } else {
                    return d.some(a.groups,
                    function(a) {
                        return ! a.text || a.text === ""
                    })
                }
            }
        },
        getProgressAnswered: function(a) {
            var b = 0;
            d.each(a.groups,
            function(a) {
                if (a.text) {
                    b += 1
                }
            });
            return b
        },
        getSubmitFormat: function(a) {
            var b = d.map(a.groups,
            function(a) {
                return {
                    id: a.id,
                    text: a.text
                }
            });
            return {
                id: this.id,
                type: this.type,
                groups: b,
                starNum: this.starNum,
                answer_texts: a.answer_texts.length !== 0 ? a.answer_texts: undefined,
                blanks: a.blanks
            }
        }
    });
    c.exports = f
});
define("app/common/models/collect_answer/star", ["require", "./_basic"],
function(a) {
    var b = a("./_basic");
    var c = b.extend({
        defaults: {
            type: "star",
            starShow: "满意",
            otherStr: "不清楚",
            isOther: false,
            starNum: "5",
            starType: 1,
            revertSort: false,
            starShowCustomStart: "",
            starShowCustomEnd: ""
        },
        isEmpty: function(a) {
            return ! a.rating
        },
        getSubmitFormat: function(a) {
            var b = a ? a.rating: "";
            return {
                id: this.id,
                type: this.type,
                text: b,
                options: [],
                starNum: this.starNum,
                answer_texts: a.answer_texts.length !== 0 ? a.answer_texts: undefined,
                blanks: a.blanks
            }
        }
    });
    return c
});
define("app/common/models/collect_answer/sort", ["require", "./_basic"],
function(a) {
    var b = a("./_basic");
    var c = b.extend({
        defaults: {
            type: "sort"
        },
        isEmpty: function(a) {
            return a.options.length !== this.options.length
        },
        getProgressTotal: function() {
            var a = this.options.length;
            return a + this.getProgressTotalForBlank()
        },
        getProgressAnswered: function(a) {
            var b = a.options.length;
            return b + this.getProgressAnsweredForBlank(a)
        },
        getSubmitFormat: function(a) {
            return {
                id: this.id,
                type: this.type,
                options: a.options,
                answer_texts: a.answer_texts.length !== 0 ? a.answer_texts: undefined,
                blanks: a.blanks
            }
        }
    });
    return c
});
define("app/common/models/collect_answer/chained_selects", ["require", "underscore", "./_basic"],
function(a) {
    var b = a("underscore");
    var c = a("./_basic");
    var d = c.extend({
        defaults: {},
        getSubmitFormat: function(a) {
            a.text_list = [];
            a.type = "chained_selects";
            var c = this.groups;
            b.forEach(a.id_list,
            function(d) {
                var e = b.find(c,
                function(a) {
                    return a[0] === d
                });
                a.text_list.push(e[1]);
                c = e[2]
            });
            return a
        },
        getRenderFormat: function() {
            return this
        },
        getProgressTotal: function() {
            var a = this.levels.length;
            return a + this.getProgressTotalForBlank()
        },
        getProgressAnswered: function(a) {
            var b;
            if (this.isEmpty(a)) {
                b = a.id_list.length
            } else {
                b = this.levels.length
            }
            return b + this.getProgressAnsweredForBlank(a)
        },
        isEmpty: function(a) {
            var c = this.groups;
            var d = a.id_list;
            b.forEach(d,
            function(a) {
                var d = b.find(c,
                function(b) {
                    return b[0] === a
                });
                c = d[2]
            });
            return !! c
        }
    },
    {
        notDeepCopy: true
    });
    return d
});
define("app/common/models/collect_answer/upload", ["require", "jquery", "./_basic"],
function(a) {
    var b = a("jquery");
    var c = a("./_basic");
    var d = c.extend({
        defaults: {},
        isEmpty: function(a) {
            return ! b.trim(a.file_name_src).length || !b.trim(a.file_name_src).length
        },
        getSubmitFormat: function(a) {
            return {
                id: this.id,
                type: this.type,
                file_name_src: a.file_name_src,
                file_name_dst: a.file_name_dst,
                answer_texts: a.answer_texts.length !== 0 ? a.answer_texts: undefined,
                blanks: a.blanks
            }
        }
    });
    return d
});
define("app/common/models/collect_answer/description", ["require", "underscore", "model"],
function(a) {
    var b = a("underscore");
    var c = a("model");
    var d = c.extend({
        defaults: {
            id: null,
            title: "",
            description: "",
            "goto": "",
            answer_texts: []
        },
        getRenderFormat: function() {
            return this.toJSON()
        },
        isEmpty: function(a) {
            return false
        },
        validateAnswer: function(a, b) {
            return b
        },
        getSubmitFormat: function(a) {
            return a
        },
        isValidAnswer: function(a, b) {
            return b
        }
    });
    return d
});
define("app/common/models/collect_answer/text_multiple", ["require", "exports", "module", "./_basic"],
function(a, b, c) {
    var d = a("./_basic");
    var e = d.extend({
        defaults: {
            type: "text_multiple"
        },
        isEmpty: function(a) {
            return a.blanks.length === 0 && a.answer_texts.length === 0
        },
        getSubmitFormat: function(a) {
            return {
                id: this.id,
                type: this.type,
                answer_texts: a.answer_texts.length !== 0 ? a.answer_texts: undefined,
                blanks: a.blanks
            }
        }
    });
    return e
});
define("app/util/recording", ["require", "exports", "module", "underscore"],
function(a, b, c) {
    var d = a("underscore");
    function e() {
        this.index = -1;
        this.history = []
    }
    d.extend(e.prototype, {
        push: function(a) {
            if (this.history[this.index + 1] === a) {
                this.index += 1;
                return
            }
            if (this.index !== this.history.length - 1) {
                this.history.length = this.index + 1
            }
            this.history.push(a);
            this.index = this.history.length - 1
        },
        go: function(a) {
            var b = this.history.length - 1;
            var c = this.index + a;
            if (c < 0) c = 0;
            if (c > b) c = b;
            this.index = c;
            return this.history[this.index]
        },
        back: function() {
            return this.go( - 1)
        },
        forward: function() {
            return this.go(1)
        },
        stack: function() {
            return this.history.slice(0, this.index + 1)
        }
    });
    c.exports = e
});
define("app/util/random", ["require"],
function(a) {
    return function() {
        function a(a) {
            this._seed = a % 2147483647;
            if (this._seed <= 0) this._seed += 2147483646
        }
        function b() {
            return this._seed = this._seed * 16807 % 2147483647
        }
        function c(a) {
            this._seed = this._seed * 16807 % 2147483647;
            return this._seed % a
        }
        return {
            seed: a,
            next: b,
            get: c
        }
    }
});
define("app/util/standard_result", ["require", "underscore"],
function(a) {
    var b = a("underscore");
    var c = {
        success: 0,
        warning: 1,
        error: 2
    };
    var d = ["success", "warning", "error"];
    function e(a, b) {
        this.result = "success";
        this.info = {
            error: [],
            warning: []
        };
        if (a) {
            this._quickInit(a, b)
        }
    }
    e.RESULT_CODE = c;
    b.extend(e.prototype, {
        _quickInit: function(a, c) {
            if (b.isString(a)) {
                if (c) {
                    this.info[a].push(c)
                }
            } else {
                throw new Error
            }
        },
        isSuccess: function() {
            return this.result === "success"
        },
        isError: function() {
            return this.result === "error"
        },
        isWarning: function() {
            return this.result === "warning"
        },
        success: function() {},
        warning: function(a) {
            this.setResult("warning");
            this.info["warning"].push(a)
        },
        error: function(a) {
            this.setResult("error");
            this.info["error"].push(a)
        },
        setResult: function(a) {
            if (c[this.result] < c[a]) {
                this.result = a
            }
        }
    });
    return e
});
define("app/common/models/collect_answer", ["require", "underscore", "app/common/api/survey", "model", "ur-log", "app/util/uuid", "./collect_answer/radio", "./collect_answer/checkbox", "./collect_answer/select", "./collect_answer/text", "./collect_answer/textarea", "./collect_answer/matrix_radio", "./collect_answer/matrix_checkbox", "./collect_answer/matrix_star", "./collect_answer/star", "./collect_answer/sort", "./collect_answer/chained_selects", "./collect_answer/upload", "./collect_answer/description", "./collect_answer/text_multiple", "app/util/recording", "app/util/random", "app/util/standard_result"],
function(a) {
    var b = a("underscore");
    var c = a("app/common/api/survey");
    var d = a("model");
    var e = a("ur-log");
    var f = a("app/util/uuid");
    var g = a("./collect_answer/radio");
    var h = a("./collect_answer/checkbox");
    var i = a("./collect_answer/select");
    var j = a("./collect_answer/text");
    var k = a("./collect_answer/textarea");
    var l = a("./collect_answer/matrix_radio");
    var m = a("./collect_answer/matrix_checkbox");
    var n = a("./collect_answer/matrix_star");
    var o = a("./collect_answer/star");
    var p = a("./collect_answer/sort");
    var q = a("./collect_answer/chained_selects");
    var r = a("./collect_answer/upload");
    var s = a("./collect_answer/description");
    var t = a("./collect_answer/text_multiple");
    var u = a("app/util/recording");
    var v = a("app/util/random");
    var w = a("app/util/standard_result");
    var x = {
        radio: g,
        checkbox: h,
        select: i,
        text: j,
        textarea: k,
        matrix_radio: l,
        matrix_checkbox: m,
        matrix_star: n,
        star: o,
        sort: p,
        chained_selects: q,
        upload: r,
        description: s,
        text_multiple: t
    };
    var y = d.extend({
        defaults: {
            survey: {
                id: null,
                title: "",
                prefix: "",
                pages: [],
                suffix: "",
                prev: false,
                titleIndex: true,
                style: {
                    theme: "default",
                    custom: {}
                }
            },
            isImpaired: false,
            logic: {},
            currentPageIndex: 0,
            history: null,
            pidMap: {},
            qidMap: {},
            qIndexMap: {},
            isPreview: false,
            isRewarding: false,
            isVoting: false,
            isUseLibrary: false,
            answer: null,
            hide: null,
            randomHide: null,
            qindex: null,
            pindex: null,
            progress: 0,
            openid: null
        },
        initialize: function() {
            this.formatSurvey();
            this.history = new u;
            this.makeIndexMap();
            this.makeLogicObject();
            this.history.push(String(this.survey.pages[0].id));
            this.updateCurrentData();
            this.startTime = (new Date).getTime();
            e.info("success init survey", this)
        },
        formatSurvey: function() {
            if (this.survey.reward && this.survey.reward.reward_flag === 1) {
                this.isRewarding = true
            }
            if (this.survey.default_reward) {
                this.isUseLibrary = true
            }
            if (this.survey.show_stat) {
                this.isVoting = true
            }
            b.each(this.survey.pages,
            function(a) {
                a.id = String(a.id);
                b.each(a.questions,
                function(b, c) {
                    b.id = String(b.id);
                    var d = new x[b.type](b);
                    a.questions[c] = d.exec("getRenderFormat")
                })
            })
        },
        saveToLocal: function(a) {
            var b = JSON.stringify(a);
            window.localStorage.setItem("survey/" + this.survey.id, b)
        },
        restore: function() {
            var a = JSON.parse(window.localStorage.getItem("survey/" + this.survey.id));
            if (a) {
                this.setAnswers(a)
            }
        },
        setAnswers: function(a) {
            this.inputChange(a);
            this.trigger("setAnswers", a)
        },
        updateCurrentData: function() {
            var a = this;
            var d = b.some(a.survey.pages,
            function(a) {
                return b.some(a.questions,
                function(a) {
                    return a.show_stat
                })
            });
            if (d) {
                c.getReportStat(a.survey.id, a.survey.hash).done(function(c) {
                    var d = a.qidMap;
                    b.each(c.data.pages,
                    function(a) {
                        b.each(a.questions,
                        function(a) {
                            var b = a.id;
                            d[b]["stat_info"] = a.stat_info
                        })
                    })
                })
            }
        },
        preValidate: function() {},
        makeIndexMap: function() {
            var a = 0;
            var c = 0;
            var d = this.pidMap;
            var e = this.qidMap;
            var f = this.qIndexMap;
            b.forEach(this.survey.pages,
            function(g) {
                d[g.id] = g;
                g.p_index = a;
                a++;
                b.forEach(g.questions,
                function(a) {
                    e[a.id] = a;
                    a.q_index = c;
                    f[c] = a;
                    c++
                })
            })
        },
        makeLogicObject: function() {
            var a = this.qidMap;
            var c = this.pidMap;
            var d = this.qIndexMap;
            var f = {};
            var g = {
                unconditional: {},
                optional: {}
            };
            var h = {};
            var i = {};
            var j = {};
            var k = function(c) {
                b.forEach(c,
                function(b) {
                    var c = b.rule["=="][0]["var"].split(".");
                    if (c[0] === "q") {
                        var d = c[1];
                        if (d in a) {
                            if (! (d in j)) j[d] = {};
                            var e = "end";
                            if (b.action[0].q_target_id != "end") {
                                e = b.action[0].q_target_id
                            }
                            if (c[2] == "answered") {
                                j[d]["default"] = e
                            }
                            if (c[4] == "selected") {
                                j[d][c[3]] = e
                            }
                        }
                    }
                });
                for (var e in j) {
                    var f = b.keys(a);
                    console.log(a, f);
                    if (b.keys(j[e]).length == 1 && !j[e]["default"]) {
                        var g = null;
                        var h = a[e].q_index;
                        if (h == f.length - 1) {
                            g = null
                        } else {
                            g = d[h + 1].id
                        }
                        j[e]["default"] = g
                    }
                }
                return {
                    list: j
                }
            };
            var l = function(c, d) {
                if (A(c)) {
                    var g = {};
                    b.forEach(c.options,
                    function(f) {
                        if (b.isArray(f.display)) {
                            f.display = {}
                        }
                        if (f.display && b.keys(f.display).length) {
                            var h = b.keys(f.display);
                            var i = b.filter(h,
                            function(b) {
                                var g = c.q_index < a[b].q_index;
                                if (!g) {
                                    e.error("invalid display logic", d.id, c.id, f.id)
                                }
                                return g
                            });
                            g[f.id] = i
                        }
                    });
                    if (b.keys(g).length) {
                        f[c.id] = g
                    }
                }
            };
            var m = function(a, d) {
                if (a["goto"]) {
                    var f = a["goto"] == -1 || d.p_index < c[a["goto"]].p_index;
                    if (!f) {
                        e.error("invalid unconditional goto logic", d.id, a.id)
                    } else {
                        g.unconditional[a.id] = a["goto"]
                    }
                }
                if (C(a)) {
                    var h = {};
                    b.each(a.options,
                    function(b) {
                        if (b["goto"]) {
                            var f = b["goto"] == -1 || d.p_index < c[b["goto"]].p_index;
                            if (!f) {
                                e.error("invalid unconditional goto logic", d.id, a.id, b.id)
                            } else {
                                h[b.id] = b["goto"]
                            }
                        }
                    });
                    if (b.keys(h).length) {
                        g.optional[a.id] = h
                    }
                }
            };
            var n = function(a) {
                if (a.refer) {
                    if (h[a.refer]) {
                        h[a.refer].push(a.id)
                    } else {
                        h[a.refer] = [a.id]
                    }
                }
            };
            if (this.survey.survey_rules && this.survey.survey_rules.version == "1.0") {
                this.logic.v2 = k(this.survey.survey_rules.list)
            }
            b.each(this.survey.pages,
            function(a) {
                b.each(a.questions,
                function(b) {
                    l(b, a);
                    m(b, a);
                    n(b)
                })
            });
            this.getRandomSeed();
            var o = function(a, b) {
                var c = a.questions_random;
                if (c) {
                    c.forEach(function(a) {
                        var c = a,
                        e = c[0],
                        f = c[1],
                        g = f - e + 1,
                        h = c[2],
                        j = [];
                        if (e >= f || g <= 0 || h <= 0 || h > g) {
                            return
                        }
                        for (var k = e; k <= f; k++) {
                            j.push(d[k].id)
                        }
                        for (var k = 0; k < h; k++) {
                            var l = j.length;
                            var m = b.get(l);
                            j.splice(m, 1)
                        }
                        j.forEach(function(a) {
                            i[a] = true
                        })
                    })
                }
            };
            o(this.survey, this.random);
            this.logic.display = f;
            this.logic.goto = g;
            this.logic.refer = h;
            this.randomHide = i
        },
        inputChange: function(a) {
            e.info("start", "handle user input", a);
            var b = new w;
            this.runReferLogic(a, b);
            this.runDisplayLogic(a, b);
            this.runAnswerLogic(a, b);
            this.runRandomLogic(a, b);
            this.updateIndex(a, b);
            this.runGotoLogic(a, b);
            this.updateProgress(a, b);
            e.info("end", "handle user input", a, b);
            return b
        },
        prevPage: function() {
            e.info("start", "handle prev page");
            var a = new w;
            a.pid = this.history.back();
            this.currentPageIndex = this.pidMap[a.pid].p_index;
            e.info("end", "handle prev page", a);
            return a
        },
        nextPage: function(a) {
            e.info("start", "handle next page", a);
            var b = new w;
            this.runReferLogic(a, b);
            this.runDisplayLogic(a, b);
            this.runRandomLogic(a, b);
            this.answerIsValid(a, b);
            if (!b.isError()) {
                this.runGotoLogic(a, b);
                if (b["goto"] == -1) {
                    if (!this._reportingUnfinishMessage) {
                        c.accessibilityReport("submitSuccess", this.survey.id, this.isImpaired ? "isImpaired": "notImpaired", parseInt((new Date).getTime() - this.startTime, 10), this.progress, this.uid);
                        this._reportingUnfinishMessage = true
                    }
                    this._submitAnswers(a, b);
                    b.progress = 100
                } else {
                    this.currentPageIndex = this.pidMap[b["goto"]].p_index;
                    if (this.autoGotoNextPage(b)) {
                        return this.nextPage(a)
                    }
                    this.updateIndex(a, b);
                    this.updateProgress(a, b);
                    this.history.push(String(b["goto"]))
                }
            } else if (!this._reportingUnfinishMessage) {
                c.accessibilityReport("submitFail", this.survey.id, this.isImpaired ? "isImpaired": "notImpaired", parseInt((new Date).getTime() - this.startTime, 10), this.progress, this.uid);
                this._reportingUnfinishMessage = true
            }
            e.info("end", "handle next page", a, b);
            return b
        },
        autoGotoNextPage: function(a) {
            var c = this.pidMap[a["goto"]];
            var d = c.questions.map(function(a) {
                return a.id
            });
            var e = a.hide;
            var f = [];
            b.forEach(d,
            function(a) {
                if (! (a in e)) {
                    f.push(a)
                }
            });
            return f.length === 0
        },
        _submitAnswers: function(a, d) {
            if (this.isPreview) {
                this._afterSubmitSuccess();
                return
            }
            if (this.saving) {
                return
            }
            var g = this;
            var h = this.qidMap;
            var i = {};
            b.forEach(a,
            function(a) {
                b.forEach(a.answers,
                function(a) {
                    i[a.id] = a
                })
            });
            var j = b.map(this.survey.pages,
            function(a) {
                return {
                    id: a.id,
                    questions: b.compact(b.map(a.questions,
                    function(a) {
                        var b = i[a.id];
                        if (!b) {
                            return false
                        }
                        var c = new x[a.type](a);
                        return c.exec("getSubmitFormat", b)
                    }))
                }
            });
            e.info(null, "success foramt answers", j);
            var k = (new Date).getTime();
            e.info("start", "submit answers", a);
            this.saving = c.collectAnswer({
                id: this.survey.id,
                survey_type: this.survey.survey_type,
                jsonLoadTime: parseInt((k - this.startTime) / 1e3, 10),
                ldw: f(),
                time: parseInt((new Date).getTime() / 1e3, 10),
                ua: navigator.userAgent,
                openid: this.openid,
                pages: j
            },
            "all").always(function() {
                g.saving = null
            }).done(function(a) {
                e.info("end", "submit answers");
                g._afterSubmitSuccess(a)
            }).fail(function(a) {
                if (a && a.info) {
                    g.trigger("submitError", a.info)
                } else {
                    e.error("submit answers", arguments)
                }
            })
        },
        _afterSubmitSuccess: function(a) {
            this.trigger("submitSuccess", a);
            var b = localStorage.getItem("random_seed") || "{}";
            var c = JSON.parse(b);
            delete c[this.survey.id];
            localStorage.setItem("random_seed", JSON.stringify(c))
        },
        updateIndex: function(a, c) {
            var d = this.pidMap;
            c.pindex = {};
            c.qindex = {};
            var e = 0;
            var f = 0;
            b.forEach(this.history.history,
            function(a) {
                c.pindex[a] = e++;
                b.forEach(d[a].questions,
                function(a) {
                    if (!c.hide[a.id] && a.type !== "description") {
                        c.qindex[a.id] = f++
                    }
                })
            });
            this.pindex = c.pindex;
            this.qindex = c.qindex
        },
        updateProgress: function(a, c) {
            var d = this.pidMap;
            var e = this.qidMap;
            var f;
            if (c["goto"] == -1) {
                f = this.survey.pages.length
            } else {
                f = b.findIndex(this.survey.pages, {
                    id: c["goto"]
                })
            }
            var g = this.survey.pages.slice(f);
            var h = b.map(this.history.stack(),
            function(a) {
                return d[a]
            });
            var i = h.concat(g);
            var j = 0;
            b.forEach(i,
            function(a) {
                b.forEach(a.questions,
                function(a) {
                    if (c.hide[a.id]) {
                        return
                    }
                    if (e[a.id].required) {
                        var b = new x[a.type](a);
                        j += b.exec("getProgressTotal", c)
                    }
                })
            });
            j += i.length;
            j -= 1;
            var k = 0;
            b.forEach(a,
            function(a) {
                b.forEach(a.answers,
                function(a) {
                    var b = a.id;
                    var d = e[b];
                    var f = new x[d.type](d);
                    if (e[b].required) {
                        k += f.exec("getProgressAnswered", a, c)
                    }
                })
            });
            k += h.length;
            k -= 1;
            if (j !== 0) {
                c.progress = parseInt(k * 100 / j, 10)
            } else {
                c.progress = 0
            }
            this.progress = c.progress
        },
        _sortAnswer: function(a) {
            var c = this.qidToIndexMap;
            var d = b.sortBy(a,
            function(a) {
                return c[a.id]
            });
            if (b.isEqual(d, a)) {
                e.error("答案传入顺序有误")
            }
            return d
        },
        runReferLogic: function(a, c) {
            var d = this.logic.refer;
            c.refer = [];
            b.forEach(a,
            function(a) {
                b.forEach(a.answers,
                function(a) {
                    if (d[a.id]) {
                        var e = [];
                        b.forEach(a.options,
                        function(a) {
                            if (a.checked) {
                                e.push(a.id)
                            }
                        });
                        b.forEach(d[a.id],
                        function(a) {
                            c.refer.push({
                                id: a,
                                options: e
                            })
                        })
                    }
                })
            })
        },
        findIntersection: function(a, b) {
            var c = 0,
            d = 0;
            var e = [];
            while (c < a.length && d < b.length) {
                if (a[c] < b[d]) {
                    c++
                } else if (a[c] > b[d]) {
                    d++
                } else {
                    e.push(a[c]);
                    c++;
                    d++
                }
            }
            return e
        },
        runDisplayLogic: function(a, c) {
            var d = this;
            var e = this.history.history;
            var f = this.qidMap;
            var g = this.pidMap;
            var h = this.logic.display;
            var i = [];
            function j(d) {
                var g = d;
                console.log(g);
                console.log("allAnswers", a);
                var h;
                var j = [];
                for (var k = 0; k < a.length; ++k) {
                    if (a[k].answers.length > 0) {
                        console.log("有第一题");
                        h = a[k].answers[0].id;
                        console.log("找到第一题为", h);
                        j.push(a[k].answers[0].id);
                        break
                    }
                }
                console.log("本问卷第一题为", h);
                var l = false;
                b.forEach(a,
                function(d, e) {
                    if (l) return false;
                    b.forEach(d.answers,
                    function(i, k) {
                        if (l) return false;
                        if (i.id == h) {
                            console.log("找到下一题", i.id);
                            var m = null;
                            var n = new x[f[i.id].type](f[i.id]);
                            var o = !n.exec("isEmpty", i, c);
                            console.log(i.id, "是否已经回答", o);
                            b.forEach(i.options,
                            function(a) {
                                if (a.checked && g[i.id] && g[i.id][a.id]) {
                                    console.log(i.id, g[i.id][a.id]);
                                    if (g[i.id][a.id] == "end") {
                                        console.log("当前选项会跳转到结束，默认隐藏后面所有题目");
                                        l = true;
                                        return false
                                    }
                                    if (!m || f[g[i.id][a.id]].q_index < m) {
                                        m = f[g[i.id][a.id]].q_index;
                                        h = g[i.id][a.id];
                                        console.log("当前选项被选中，且有跳转逻辑", a, m, h)
                                    }
                                }
                                if (!o && !a.checked && g[i.id] && g[i.id][a.id]) {
                                    console.log(i.id, g[i.id][a.id]);
                                    if (g[i.id][a.id] == "end") {
                                        console.log("当前选项会跳转到结束，默认隐藏后面所有题目");
                                        l = true;
                                        return false
                                    }
                                    h = g[i.id][a.id];
                                    l = true;
                                    console.log("当前选项没选中，但有跳转逻辑，则需要跳到需要跳转到的那一题继续做判断", a)
                                }
                            });
                            if (!l && !m && g[i.id] && g[i.id]["default"]) {
                                if (g[i.id]["default"] == "end") {
                                    l = true;
                                    console.log("默认输出跳转到结束", i)
                                } else {
                                    console.log("默认输出跳转到对应的题", i);
                                    m = f[g[i.id]["default"]].q_index;
                                    h = g[i.id]["default"]
                                }
                            }
                            if (!l && !m) {
                                if (d.answers[k + 1]) {
                                    m = f[d.answers[k + 1].id].q_index;
                                    h = d.answers[k + 1].id;
                                    console.log("本页还有下一题", m, h)
                                } else {
                                    for (var p = e + 1; p < a.length; ++p) {
                                        if (a[p].answers.length > 0) {
                                            m = f[a[p].answers[0].id].q_index;
                                            h = a[p].answers[0].id;
                                            console.log("后续几页还有下一题", m, h);
                                            break
                                        }
                                    }
                                }
                            }
                            if (m) {
                                j.push(h)
                            }
                        } else {
                            console.log("跳过这一题", i.id)
                        }
                    })
                });
                console.log("showQuestions", j);
                i = b.difference(b.keys(f), j);
                console.log("FINAL HIDE QUESTIONS:", i);
                b.forEach(a,
                function(a) {
                    if (e.indexOf(String(a.id)) === -1) {
                        a.answers = [];
                        return
                    }
                    b.forEach(a.answers,
                    function(a) {
                        if (i.indexOf(a.id) > -1) {
                            a.invalid = true;
                            return
                        }
                    });
                    a.answers = b.filter(a.answers,
                    function(a) {
                        if (a.invalid) {
                            return false
                        } else {
                            return true
                        }
                    })
                });
                c.hide = {};
                i.forEach(function(a) {
                    c.hide[a] = true
                });
                return c.hide
            }
            function k() {
                b.forEach(h,
                function(a) {
                    b.forEach(a,
                    function(a) {
                        i.push(a)
                    })
                });
                i = b.uniq(b.flatten(i));
                b.forEach(a,
                function(a) {
                    if (e.indexOf(String(a.id)) === -1) {
                        a.answers = [];
                        return
                    }
                    b.forEach(a.answers,
                    function(a) {
                        if (i.indexOf(a.id) > -1) {
                            a.invalid = true;
                            return
                        }
                        if (A(f[a.id])) {
                            b.forEach(a.options,
                            function(c) {
                                if (c.checked && h[a.id] && h[a.id][c.id]) {
                                    i = b.difference(i, h[a.id][c.id])
                                }
                            })
                        }
                    });
                    a.answers = b.filter(a.answers,
                    function(a) {
                        if (a.invalid) {
                            return false
                        } else {
                            return true
                        }
                    })
                });
                c.hide = {};
                i.forEach(function(a) {
                    c.hide[a] = true
                });
                return c.hide
            }
            if (this.survey.survey_rules && this.survey.survey_rules.version == "1.0") {
                this.hide = j(this.logic.v2.list);
                return
            }
            this.hide = k()
        },
        runGotoLogic: function(a, c) {
            var d = this.pidMap;
            var e = this.qidMap;
            var f = null;
            var g = this.logic["goto"];
            var h = [];
            var i = [];
            b.forEach(a[this.currentPageIndex].answers,
            function(a) {
                var d = new x[e[a.id].type](e[a.id]);
                if (g.unconditional[a.id] && !d.exec("isEmpty", a, c)) {
                    h.push(g.unconditional[a.id])
                } else {
                    if (A(e[a.id])) {
                        b.forEach(a.options,
                        function(b) {
                            if (b.checked && g.optional[a.id] && g.optional[a.id][b.id]) {
                                i.push(g.optional[a.id][b.id])
                            }
                        })
                    }
                }
            });
            if (h.length) {
                c["goto"] = b.min(h,
                function(a) {
                    if (a == -1) {
                        return - 1
                    }
                    return d[a].p_index
                })
            } else if (i.length) {
                c["goto"] = b.min(i,
                function(a) {
                    if (a == -1) {
                        return - 1
                    }
                    return d[a].p_index
                })
            } else {
                if (this.survey.pages[this.currentPageIndex + 1]) {
                    c["goto"] = this.survey.pages[this.currentPageIndex + 1].id
                } else {
                    c["goto"] = -1
                }
            }
        },
        runAnswerLogic: function(a, c) {
            var d = this;
            var e = this.qidMap;
            c.stat = {};
            b.forEach(a,
            function(a) {
                b.forEach(a.answers,
                function(a) {
                    var b = a.id;
                    var d = e[b];
                    var f = new x[d.type](d);
                    if (!f.exec("isEmpty", a, c)) {
                        f.exec("validateAnswer", a, c)
                    }
                    if (f.get("show_stat")) {
                        if (!f.exec("isEmpty", a, c)) {
                            c.stat[b] = f.exec("getStat", a, c)
                        } else {
                            c.stat[b] = null
                        }
                    }
                })
            })
        },
        runRandomLogic: function(a, c) {
            var d = this.randomHide;
            b.assign(c.hide, d);
            b.forEach(a,
            function(a) {
                b.forEach(a.answers,
                function(a) {
                    if (b.keys(d).indexOf(a.id) > -1) {
                        a.invalid = true
                    }
                });
                a.answers = b.filter(a.answers,
                function(a) {
                    if (a.invalid) {
                        return false
                    } else {
                        return true
                    }
                })
            });
            console.log("hide!!!", c.hide, d)
        },
        answerIsValid: function(a, c) {
            var d = this;
            var e = this.qidMap;
            b.forEach(a[this.currentPageIndex].answers,
            function(a) {
                var b = a.id;
                var d = e[b];
                var f = new x[d.type](d);
                f.exec("isValidAnswer", a, c)
            })
        },
        setIsImpaired: function(a, b) {
            this.isImpaired = a;
            this.uid = b
        },
        setUseGiftLibrary: function(a) {
            this.isUseLibrary = a;
            this.isImpaired = isImpaired;
            this.uid = userid
        },
        getRandomSeed: function() {
            var a = localStorage.getItem("random_seed") || "{}";
            var b = JSON.parse(a);
            var c = Number(b[this.survey.id]);
            if (!c) {
                c = Date.now();
                b[this.survey.id] = Number(c);
                localStorage.setItem("random_seed", JSON.stringify(b))
            }
            this.random = new v;
            this.random.seed(c);
            this.seed = c
        }
    },
    {
        notDeepCopy: true
    });
    var z = ["radio", "checkbox", "select"];
    function A(a) {
        return z.indexOf(a.type) !== -1
    }
    var B = ["radio", "checkbox", "select"];
    function C(a) {
        return B.indexOf(a.type) !== -1
    }
    return y
});
define("art-dialog/popup", ["require", "jquery"],
function(a) {
    var b = a("jquery");
    var c = 0;
    var d = !("minWidth" in b("html")[0].style);
    var e = !d;
    function f() {
        this.destroyed = false;
        this.__popup = b("<div />").css({
            display: "none",
            position: "absolute",
            outline: 0
        }).html(this.innerHTML).appendTo("body");
        this.__backdrop = this.__mask = b("<div />").css({
            opacity: .7,
            background: "#000"
        });
        this.node = this.__popup[0];
        this.backdrop = this.__backdrop[0];
        c++
    }
    b.extend(f.prototype, {
        node: null,
        backdrop: null,
        fixed: false,
        destroyed: true,
        open: false,
        returnValue: "",
        autofocus: true,
        align: "bottom left",
        innerHTML: "",
        className: "ui-popup",
        show: function(a, c) {
            if (this.destroyed) {
                return this
            }
            var g = this;
            var h = this.__popup;
            var i = this.__backdrop;
            this.__activeElement = this.__getActive();
            this.open = true;
            this.follow = a || this.follow;
            if (!this.__ready) {
                h.addClass(this.className).attr("role", this.modal ? "alertdialog": "dialog").css("position", this.fixed ? "fixed": "absolute");
                if (!d) {
                    b(window).on("resize", b.proxy(this.reset, this))
                }
                if (this.modal) {
                    var j = {
                        position: "fixed",
                        left: 0,
                        top: 0,
                        width: "100%",
                        height: "100%",
                        overflow: "hidden",
                        userSelect: "none",
                        zIndex: this.zIndex || f.zIndex
                    };
                    h.addClass(this.className + "-modal");
                    if (!e) {
                        b.extend(j, {
                            position: "absolute",
                            width: b(window).width() + "px",
                            height: b(document).height() + "px"
                        })
                    }
                    i.css(j).on("focus", b.proxy(this.focus, this));
                    this.__mask = i.clone(true).attr("style", "").insertAfter(h);
                    i.addClass(this.className + "-backdrop").insertBefore(h);
                    this.__ready = true
                }
                if (!h.html()) {
                    h.html(this.innerHTML)
                }
            }
            h.addClass(this.className + "-show").show();
            i.show();
            this.reset();
            if (c !== true) {
                this.focus()
            }
            this.__dispatchEvent("show");
            return this
        },
        showModal: function() {
            this.modal = true;
            return this.show.apply(this, arguments)
        },
        close: function(a) {
            if (!this.destroyed && this.open) {
                if (a !== undefined) {
                    this.returnValue = a
                }
                this.__popup.hide().removeClass(this.className + "-show");
                this.__backdrop.hide();
                this.open = false;
                this.blur();
                this.__dispatchEvent("close")
            }
            return this
        },
        remove: function() {
            if (this.destroyed) {
                return this
            }
            this.__dispatchEvent("beforeremove");
            if (f.current === this) {
                f.current = null
            }
            this.__popup.remove();
            this.__backdrop.remove();
            this.__mask.remove();
            if (!d) {
                b(window).off("resize", this.reset)
            }
            this.__dispatchEvent("remove");
            for (var a in this) {
                delete this[a]
            }
            return this
        },
        reset: function() {
            var a = this.follow;
            if (a) {
                this.__follow(a)
            } else {
                this.__center()
            }
            this.__dispatchEvent("reset");
            return this
        },
        focus: function() {
            var a = this.node;
            var c = this.__popup;
            var d = f.current;
            var e = this.zIndex = f.zIndex++;
            if (d && d !== this) {
                d.blur(false)
            }
            if (!b.contains(a, this.__getActive())) {
                var g = c.find("[autofocus]")[0];
                if (!this._autofocus && g) {
                    this._autofocus = true
                } else {
                    g = a
                }
                this.__focus(g)
            }
            c.css("zIndex", e);
            f.current = this;
            c.addClass(this.className + "-focus");
            this.__dispatchEvent("focus");
            return this
        },
        blur: function() {
            var a = this.__activeElement;
            var b = arguments[0];
            if (b !== false) {}
            this._autofocus = false;
            this.__popup.removeClass(this.className + "-focus");
            this.__dispatchEvent("blur");
            return this
        },
        addEventListener: function(a, b) {
            this.__getEventListener(a).push(b);
            return this
        },
        removeEventListener: function(a, b) {
            var c = this.__getEventListener(a);
            for (var d = 0; d < c.length; d++) {
                if (b === c[d]) {
                    c.splice(d--, 1)
                }
            }
            return this
        },
        __getEventListener: function(a) {
            var b = this.__listener;
            if (!b) {
                b = this.__listener = {}
            }
            if (!b[a]) {
                b[a] = []
            }
            return b[a]
        },
        __dispatchEvent: function(a) {
            var b = this.__getEventListener(a);
            if (this["on" + a]) {
                this["on" + a]()
            }
            for (var c = 0; c < b.length; c++) {
                b[c].call(this)
            }
        },
        __focus: function(a) {
            try {
                if (this.autofocus && !/^iframe$/i.test(a.nodeName)) {
                    a.focus()
                }
            } catch(b) {}
        },
        __getActive: function() {
            try {
                var a = document.activeElement;
                var b = a.contentDocument;
                var c = b && b.activeElement || a;
                return c
            } catch(d) {}
        },
        __center: function() {
            var a = this.__popup;
            if (b.zepto) {
                a.show()
            }
            var c = b(window);
            var d = b(document);
            var e = this.fixed;
            var f = e ? 0 : d.scrollLeft() || 0;
            var g = e ? 0 : d.scrollTop() || 0;
            var h = c.width();
            var i = c.height();
            var j = a.width() || a[0].offsetWidth;
            var k = a.height() || a[0].offsetHeight;
            var l = (h - j) / 2 + f;
            var m = (i - k) * 382 / 1e3 + g;
            var n = a[0].style;
            n.left = Math.max(parseInt(l), f) + "px";
            n.top = Math.max(parseInt(m), g) + "px"
        },
        __follow: function(a) {
            var c = a.parentNode && b(a);
            var d = this.__popup;
            if (this.__followSkin) {
                d.removeClass(this.__followSkin)
            }
            if (c) {
                var e = c.offset();
                if (e.left * e.top < 0) {
                    return this.__center()
                }
            }
            var f = this;
            var g = this.fixed;
            var h = b(window);
            var i = b(document);
            var j = h.width();
            var k = h.height();
            var l = i.scrollLeft();
            var m = i.scrollTop();
            var n = d.width();
            var o = d.height();
            var p = c ? c.outerWidth() : 0;
            var q = c ? c.outerHeight() : 0;
            var r = this.__offset(a);
            var s = r.left;
            var t = r.top;
            var u = g ? s - l: s;
            var v = g ? t - m: t;
            var w = g ? 0 : l;
            var x = g ? 0 : m;
            var y = w + j - n;
            var z = x + k - o;
            var A = {};
            var B = this.align.split(" ");
            var C = this.className + "-";
            var D = {
                top: "bottom",
                bottom: "top",
                left: "right",
                right: "left"
            };
            var E = {
                top: "top",
                bottom: "top",
                left: "left",
                right: "left"
            };
            var F = [{
                top: v - o,
                bottom: v + q,
                left: u - n,
                right: u + p
            },
            {
                top: v,
                bottom: v - o + q,
                left: u,
                right: u - n + p
            }];
            var G = {
                left: u + p / 2 - n / 2,
                top: v + q / 2 - o / 2
            };
            var H = {
                left: [w, y],
                top: [x, z]
            };
            b.each(B,
            function(a, b) {
                if (F[a][b] > H[E[b]][1]) {
                    b = B[a] = D[b]
                }
                if (F[a][b] < H[E[b]][0]) {
                    B[a] = D[b]
                }
            });
            if (!B[1]) {
                E[B[1]] = E[B[0]] === "left" ? "top": "left";
                F[1][B[1]] = G[E[B[1]]]
            }
            C += B.join("-") + " " + this.className + "-follow";
            f.__followSkin = C;
            if (c) {
                d.addClass(C)
            }
            A[E[B[0]]] = parseInt(F[0][B[0]]);
            A[E[B[1]]] = parseInt(F[1][B[1]]);
            d.css(A)
        },
        __offset: function(a) {
            var c = a.parentNode;
            var d = c ? b(a).offset() : {
                left: a.pageX,
                top: a.pageY
            };
            a = c ? a: a.target;
            var e = a.ownerDocument;
            var f = e.defaultView || e.parentWindow;
            if (f == window) {
                return d
            }
            var g = f.frameElement;
            var h = b(e);
            var i = h.scrollLeft();
            var j = h.scrollTop();
            var k = b(g).offset();
            var l = k.left;
            var m = k.top;
            return {
                left: d.left + l - i,
                top: d.top + m - j
            }
        }
    });
    f.zIndex = 1024;
    f.current = null;
    return f
});
define("art-dialog/dialog-config", {
    backdropBackground: "#000",
    backdropOpacity: .7,
    content: '<span class="ui-dialog-loading">Loading..</span>',
    title: "",
    statusbar: "",
    button: null,
    ok: null,
    cancel: null,
    okValue: "ok",
    cancelValue: "cancel",
    cancelDisplay: true,
    width: "",
    height: "",
    padding: "",
    skin: "",
    quickClose: false,
    cssUri: null,
    innerHTML: '<div i="dialog" class="ui-dialog">' + '<div class="ui-dialog-arrow-a"></div>' + '<div class="ui-dialog-arrow-b"></div>' + '<table class="ui-dialog-grid">' + "<tr>" + '<td i="header" class="ui-dialog-header">' + '<button i="close" class="ui-dialog-close">&#215;</button>' + '<div i="title" class="ui-dialog-title"></div>' + "</td>" + "</tr>" + "<tr>" + '<td i="body" class="ui-dialog-body">' + '<div i="content" class="ui-dialog-content"></div>' + "</td>" + "</tr>" + "<tr>" + '<td i="footer" class="ui-dialog-footer">' + '<div i="statusbar" class="ui-dialog-statusbar"></div>' + '<div i="button" class="ui-dialog-button"></div>' + "</td>" + "</tr>" + "</table>" + "</div>"
});
define("art-dialog/dialog", ["require", "jquery", "./popup", "./dialog-config"],
function(a) {
    var b = a("jquery");
    var c = a("./popup");
    var d = a("./dialog-config");
    var e = d.cssUri;
    if (e) {
        var f = a[a.toUrl ? "toUrl": "resolve"];
        if (f) {
            e = f(e);
            e = '<link rel="stylesheet" href="' + e + '" />';
            if (b("base")[0]) {
                b("base").before(e)
            } else {
                b("head").append(e)
            }
        }
    }
    var g = 0;
    var h = new Date - 0;
    var i = !("minWidth" in b("html")[0].style);
    var j = "createTouch" in document && !("onmousemove" in document) || /(iPhone|iPad|iPod)/i.test(navigator.userAgent);
    var k = !i && !j;
    var l = function(a, c, d) {
        var e = a = a || {};
        if (typeof a === "string" || a.nodeType === 1) {
            a = {
                content: a,
                fixed: !j
            }
        }
        a = b.extend(true, {},
        l.defaults, a);
        a.original = e;
        var f = a.id = a.id || h + g;
        var i = l.get(f);
        if (i) {
            return i.focus()
        }
        if (!k) {
            a.fixed = false
        }
        if (a.quickClose) {
            a.modal = true;
            a.backdropOpacity = 0
        }
        if (!b.isArray(a.button)) {
            a.button = []
        }
        if (d !== undefined) {
            a.cancel = d
        }
        if (a.cancel) {
            a.button.push({
                id: "cancel",
                value: a.cancelValue,
                callback: a.cancel,
                display: a.cancelDisplay
            })
        }
        if (c !== undefined) {
            a.ok = c
        }
        if (a.ok) {
            a.button.push({
                id: "ok",
                value: a.okValue,
                callback: a.ok,
                autofocus: true
            })
        }
        return l.list[f] = new l.create(a)
    };
    var m = function() {};
    m.prototype = c.prototype;
    var n = l.prototype = new m;
    l.create = function(a) {
        var d = this;
        b.extend(this, new c);
        var e = a.original;
        var f = b(this.node).html(a.innerHTML);
        var h = b(this.backdrop);
        this.options = a;
        this._popup = f;
        b.each(a,
        function(a, b) {
            if (typeof d[a] === "function") {
                d[a](b)
            } else {
                d[a] = b
            }
        });
        if (a.zIndex) {
            c.zIndex = a.zIndex
        }
        f.attr({
            "aria-labelledby": this._$("title").attr("id", "title:" + this.id).attr("id"),
            "aria-describedby": this._$("content").attr("id", "content:" + this.id).attr("id")
        });
        this._$("close").css("display", this.cancel === false ? "none": "").attr("title", this.cancelValue).on("click",
        function(a) {
            d._trigger("cancel");
            a.preventDefault()
        });
        this._$("dialog").addClass(this.skin);
        this._$("body").css("padding", this.padding);
        if (a.quickClose) {
            h.on("onmousedown" in document ? "mousedown": "click",
            function() {
                d._trigger("cancel");
                return false
            })
        }
        this.addEventListener("show",
        function() {
            h.css({
                opacity: 0,
                background: a.backdropBackground
            }).animate({
                opacity: a.backdropOpacity
            },
            150)
        });
        this._esc = function(a) {
            var b = a.target;
            var e = b.nodeName;
            var f = /^input|textarea$/i;
            var g = c.current === d;
            var h = a.keyCode;
            if (!g || f.test(e) && b.type !== "button") {
                return
            }
            if (h === 27) {
                d._trigger("cancel")
            }
        };
        b(document).on("keydown", this._esc);
        this.addEventListener("remove",
        function() {
            b(document).off("keydown", this._esc);
            delete l.list[this.id]
        });
        g++;
        l.oncreate(this);
        return this
    };
    l.create.prototype = n;
    b.extend(n, {
        content: function(a) {
            var c = this._$("content");
            if (typeof a === "object") {
                a = b(a);
                c.empty("").append(a.show());
                this.addEventListener("beforeremove",
                function() {
                    b("body").append(a.hide())
                })
            } else {
                c.html(a)
            }
            return this.reset()
        },
        title: function(a) {
            this._$("title").text(a);
            this._$("header")[a ? "show": "hide"]();
            return this
        },
        width: function(a) {
            this._$("content").css("width", a);
            return this.reset()
        },
        height: function(a) {
            this._$("content").css("height", a);
            return this.reset()
        },
        button: function(a) {
            a = a || [];
            var c = this;
            var d = "";
            var e = 0;
            this.callbacks = {};
            if (typeof a === "string") {
                d = a;
                e++
            } else {
                b.each(a,
                function(a, f) {
                    var g = f.id = f.id || f.value;
                    var h = "";
                    c.callbacks[g] = f.callback;
                    if (f.display === false) {
                        h = ' style="display:none"'
                    } else {
                        e++
                    }
                    d += "<button" + ' type="button"' + ' i-id="' + g + '"' + h + (f.disabled ? " disabled": "") + (f.autofocus ? ' autofocus class="ui-dialog-autofocus"': "") + ">" + f.value + "</button>";
                    c._$("button").on("click", "[i-id=" + g + "]",
                    function(a) {
                        var d = b(this);
                        if (!d.attr("disabled")) {
                            c._trigger(g)
                        }
                        a.preventDefault()
                    })
                })
            }
            this._$("button").html(d);
            this._$("footer")[e ? "show": "hide"]();
            return this
        },
        statusbar: function(a) {
            this._$("statusbar").html(a)[a ? "show": "hide"]();
            return this
        },
        _$: function(a) {
            return this._popup.find("[i=" + a + "]")
        },
        _trigger: function(a) {
            var b = this.callbacks[a];
            return typeof b !== "function" || b.call(this) !== false ? this.close().remove() : this
        }
    });
    l.oncreate = b.noop;
    l.getCurrent = function() {
        return c.current
    };
    l.get = function(a) {
        return a === undefined ? l.list: l.list[a]
    };
    l.list = {};
    l.defaults = d;
    return l
});
define("app/common/api/user", ["require", "exports", "module", "app/common/ur_ajax"],
function(a, b, c) {
    "use strict";
    var d = a("app/common/ur_ajax");
    c.exports = {
        userInfo: function(a) {
            return d({
                type: "GET",
                url: "/syx/rest/survey/user_info"
            },
            {
                preventDefaultCallbacks: a
            })
        },
        userRight: function() {
            return d({
                type: "GET",
                url: "/api/account/permissions"
            })
        },
        logout: function() {
            return d({
                type: "GET",
                url: "/sur/logout"
            })
        },
        isIntranet: function() {
            return d({
                type: "GET",
                url: "/api/login_option"
            })
        },
        oaLogin: function(a) {
            var b = e(a);
            return d({
                type: "GET",
                url: "/api/callback/tof",
                data: {
                    code: a,
                    state_code: b
                }
            })
        }
    };
    function e(a) {
        if (typeof a !== "string") {
            a = a.toString()
        }
        return a.split("").reverse().join("")
    }
});
define("app/common/models/user", ["require", "jquery", "underscore", "app/util/events", "bowser", "app/common/api/user", "cookie"],
function(a) {
    "use strict";
    var b = a("jquery");
    var c = a("underscore");
    var d = a("app/util/events");
    var e = a("bowser");
    var f = a("app/common/api/user");
    a("cookie");
    var g = ["not-logged-in"];
    function h() {
        this.isLoggedIn = false;
        this.name = null;
        this.avatar = null;
        this.id = null
    }
    c.extend(h.prototype, d, {
        autoLogin: function() {
            this.getUserInfo(g)
        },
        getUserInfo: function(a) {
            var b = this;
            return f.userInfo(a).on("done",
            function(a) {
                b.name = a.data.user_english_name;
                b.avatar = a.data.user_avatar || "/image/default_avatar.png";
                b.id = a.data.user_id;
                b.isLoggedIn = true;
                b.isRTX = a.data.user_type === "rtx";
                b.hasCommonRight = false;
                if (b.isRTX) {
                    f.userRight().done(function(a) {
                        b.hasCommonRight = a.data.hide_tencent_relevant_allowed
                    })
                }
                b.trigger("logged-in")
            }).on("not-logged-in",
            function() {
                b.isLoggedIn = false;
                b.trigger("not-logged-in")
            })
        },
        getUserInfoWithoutTrigger: function() {
            return f.userInfo(true)
        },
        isStillLoggedIn: function() {
            var a = this;
            return f.userInfo().on("not-logged-in",
            function() {
                a.isLoggedIn = false;
                a.trigger("not-logged-in")
            })
        },
        login: function(a, b, c) {
            console.error("User.login() is deprecated!");
            var d = this.isSurveyHTML();
            var f = this.isWeChat();
            var g = !!e.mobile;
            if (!a || a.length === 0) {
                a = window.location.href
            }
            if (!g) {
                if (d) {
                    this.showPTLoginDialog(a)
                } else {
                    this.jumpLoginPage(a)
                }
            } else {
                if (d) {
                    if (f) {
                        this.jumpWeChatForReader(a)
                    } else {
                        this.jumpPT(a)
                    }
                } else {
                    this.jumpLoginPage()
                }
            }
        },
        logout: function() {
            f.logout().done(function() {
                pt.logout(function() {
                    location.replace("/index.html")
                })
            })
        },
        jumpPT: function(a) {
            var c = b.param({
                style: 9,
                appid: 550011611,
                s_url: encodeURI(a)
            });
            var d = "//ui.ptlogin2.qq.com/cgi-bin/login?" + c;
            location.replace(d)
        },
        jumpWeChatForAuthor: function(a) {
            if (!a) {
                a = location.href
            }
            var c = "https://open.weixin.qq.com/connect/oauth2/authorize?";
            var d = location.protocol + "//wj.qq.com/mobile/auth.html?step=author";
            var e = b.param({
                appid: "wx2d48bb387916ceca",
                redirect_uri: encodeURI(d),
                response_type: "code",
                scope: "snsapi_userinfo"
            });
            var f = "#wechat_redirect";
            sessionStorage.setItem("originURL", a);
            location.replace(c + e + f)
        },
        jumpWeChatForReader: function(a) {
            if (!a) {
                a = location.href
            }
            var c = "https://open.weixin.qq.com/connect/oauth2/authorize?";
            var d = location.protocol + "//wj.qq.com/mobile/auth.html?step=reader";
            var e = b.param({
                appid: "wx2d48bb387916ceca",
                redirect_uri: encodeURI(d),
                response_type: "code",
                scope: "snsapi_userinfo"
            });
            var f = "#wechat_redirect";
            sessionStorage.setItem("originURL", a);
            location.replace(c + e + f)
        },
        jumpLoginPage: function() {
            var a = e.mobile ? "/mobile": "";
            var b = a + "/login.html";
            sessionStorage.setItem("originURL", location.href);
            location.replace(b)
        },
        showPTLoginDialog: function(a) {
            pt.setParams({
                appid: 550011611,
                "daid:": 354,
                style: 20,
                protocol: "https:",
                domain: "qq.com",
                border_radius: 1,
                maskOpacity: 40,
                hide_close_icon: 1
            });
            pt.setParam("s_url", a);
            pt.showPtui()
        },
        isWeChat: function() {
            var a = navigator.userAgent.toLowerCase();
            return Boolean(a.match(/micromessenger\/([\d\.]+)/))
        },
        isMQQ: function() {
            var a = navigator.userAgent.toLowerCase();
            return Boolean(a.match(/qq\/([\d\.]+)/))
        },
        isQzone: function() {
            var a = navigator.userAgent.toLowerCase();
            return Boolean(a.indexOf("qzone/"))
        },
        isAndroid: function() {
            var a = navigator.userAgent.toLowerCase();
            return Boolean(a.indexOf("android"))
        },
        isSurveyHTML: function() {
            return Boolean(location.pathname.indexOf("/survey.html") >= 0)
        }
    });
    if (!window.console) {
        window.console = {
            log: function() {},
            info: function() {},
            error: function() {}
        }
    }
    return new h
});
define("bind_events", ["require", "underscore"],
function(a) {
    var b = require("underscore");
    return {
        bindModelEvents: function(a, c, d) {
            var e = b.keys(d);
            e.forEach(function(e) {
                var f;
                if (b.isFunction(d[e])) {
                    f = d[e]
                } else {
                    f = a[d[e]]
                }
                c.on(e, b.bind(f, a))
            })
        },
        bindUIEvents: function(a, c, d) {
            var e = b.keys(d);
            e.forEach(function(e) {
                var f = e.split(/\s+/);
                var g = f[0];
                var h = e.slice(g.length);
                var i;
                if (b.isFunction(d[e])) {
                    i = d[e]
                } else {
                    i = a[d[e]]
                }
                c.on(g, h, b.bind(i, a))
            })
        }
    }
});
define("app/common/accessibility_detect", ["require", "jquery", "underscore", "bind_events", "app/common/api/survey"],
function(a) {
    var b = a("jquery");
    var c = a("underscore");
    var d = a("bind_events");
    var e = a("app/common/api/survey");
    function f(a) {
        this.$el = b("body");
        this.model = a.surveyModel;
        var c = this;
        a.userModel.getUserInfoWithoutTrigger().done(function(a) {
            c.userid = a.data.user_id
        });
        this.init()
    }
    c.extend(f.prototype, {
        reported: false,
        rightArrow: 0,
        tabTimes: 0,
        weight: 0,
        bindEvents: function() {
            d.bindUIEvents(this, this.$el, {
                "focus .survey_title": "detectItem",
                keydown: "keydownHandler"
            })
        },
        init: function() {
            this.bindEvents()
        },
        detectItem: function() {
            if (this.reported) {
                return
            }
            this.reported = true;
            this.model.exec("setIsImpaired", true, this.userid);
            e.accessibilityReport("isImpaired", this.model.get("survey.id"), "focus到检测元素上了", 0, "", this.userid)
        },
        keydownHandler: function(a) {
            if (this.reported) {
                return
            }
            var b = "";
            if (a.keyCode === 9) {
                this.tabTimes += 1;
                if (this.tabTimes >= 10) {
                    this.weight += 25
                }
                b = "多次使用TAB"
            }
            if (a.keyCode === 39) {
                this.rightArrow += 1;
                if (this.rightArrow >= 10) {
                    this.weight += 25
                }
                b = "多次使用右箭头"
            }
            if (this.weight >= 25) {
                this.reported = true;
                this.model.exec("setIsImpaired", true, this.userid);
                e.accessibilityReport("isImpaired", this.model.get("survey.id"), b, 0, "", this.userid)
            }
        }
    });
    return f
});
define("app/util/lang", ["require"],
function(a) {
    return {
        zh: {
            translation: {
                start: "开始填写",
                choose: "请选择",
                next: "下一页",
                prev: "上一页",
                finish: "您已经完成",
                submit: "提交",
                close: "关闭",
                comfirm: "确定"
            }
        },
        zh_tw: {
            translation: {
                start: "開始填寫",
                choose: "請選擇",
                next: "下一頁",
                prev: "上一頁",
                finish: "您已經完成",
                submit: "提交",
                close: "關閉",
                comfirm: "確定"
            }
        },
        zh_hk: {
            translation: {
                start: "開始填寫",
                choose: "請選擇",
                next: "下一頁",
                prev: "上一頁",
                finish: "您已經完成",
                submit: "提交",
                close: "關閉",
                comfirm: "確定"
            }
        },
        "zh-hk": {
            translation: {
                start: "開始填寫",
                choose: "請選擇",
                next: "下一頁",
                prev: "上一頁",
                finish: "您已經完成",
                submit: "提交",
                close: "關閉",
                comfirm: "確定"
            }
        },
        "zh-tw": {
            translation: {
                start: "開始填寫",
                choose: "請選擇",
                next: "下一頁",
                prev: "上一頁",
                finish: "您已經完成",
                submit: "提交",
                close: "關閉",
                comfirm: "確定"
            }
        },
        en: {
            translation: {
                start: "Start",
                choose: "Please choose",
                next: "next",
                prev: "previous",
                finish: "Congratulations on completing the questionnaire",
                submit: "Submit",
                close: "Close",
                comfirm: "comfirm"
            }
        },
        fr: {
            translation: {
                start: "commencer",
                choose: "choisir s'il vous plaît",
                next: "suivant",
                prev: "précédent",
                finish: "terminé",
                submit: "soummetre",
                close: "fermer",
                comfirm: "déterminer"
            }
        },
        de: {
            translation: {
                start: "Start",
                choose: "Bitte wählen Sie",
                next: "nächste",
                prev: "zurük",
                finish: "End",
                submit: "Vorlegen",
                close: "Schließen",
                comfirm: "bestimmen"
            }
        },
        kor: {
            translation: {
                start: "시작",
                choose: "선택하세요",
                next: "다음",
                prev: "앞",
                finish: "당신은 이미 완료했어요",
                submit: "제출/제기",
                close: "닫기",
                comfirm: "결정"
            }
        },
        ko: {
            translation: {
                start: "시작",
                choose: "선택하세요",
                next: "다음",
                prev: "앞",
                finish: "당신은 이미 완료했어요",
                submit: "제출/제기",
                close: "닫기",
                comfirm: "결정"
            }
        },
        kr: {
            translation: {
                start: "시작",
                choose: "선택하세요",
                next: "다음",
                prev: "앞",
                finish: "당신은 이미 완료했어요",
                submit: "제출/제기",
                close: "닫기",
                comfirm: "결정"
            }
        },
        ja: {
            translation: {
                start: "スタート",
                choose: "選択してください",
                next: "次へ",
                prev: "前へ",
                finish: "これでアンケートは完了しました",
                submit: "提出する",
                close: "閉じる",
                comfirm: "定めます"
            }
        }
    }
}); (function(a, b) {
    typeof exports === "object" && typeof module !== "undefined" ? module.exports = b() : typeof define === "function" && define.amd ? define("app/util/i18nextMax", b) : a.i18next = b()
})(this,
function() {
    "use strict";
    var a = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ?
    function(a) {
        return typeof a
    }: function(a) {
        return a && typeof Symbol === "function" && a.constructor === Symbol && a !== Symbol.prototype ? "symbol": typeof a
    };
    if (document.documentMode != "8" && document.documentMode != "7") {
        var b = function(a, b) {
            if (! (a instanceof b)) {
                throw new TypeError("Cannot call a class as a function")
            }
        }
    } else {
        b = function(a, b) {}
    }
    var c = Object.assign ||
    function(a) {
        for (var b = 1; b < arguments.length; b++) {
            var c = arguments[b];
            for (var d in c) {
                if (Object.prototype.hasOwnProperty.call(c, d)) {
                    a[d] = c[d]
                }
            }
        }
        return a
    };
    if (document.documentMode != "8" && document.documentMode != "7") {
        var d = function(a, b) {
            if (typeof b !== "function" && b !== null) {
                throw new TypeError("Super expression must either be null or a function, not " + typeof b)
            }
            a.prototype = Object.create(b && b.prototype, {
                constructor: {
                    value: a,
                    enumerable: false,
                    writable: true,
                    configurable: true
                }
            });
            if (b) Object.setPrototypeOf ? Object.setPrototypeOf(a, b) : a.__proto__ = b
        }
    } else {
        d = function(a, b) {}
    }
    var e = function(a, b) {
        if (!a) {
            throw new ReferenceError("this hasn't been initialised - super() hasn't been called")
        }
        return b && (typeof b === "object" || typeof b === "function") ? b: a
    };
    var f = function() {
        function a(a, b) {
            var c = [];
            var d = true;
            var e = false;
            var f = undefined;
            try {
                for (var g = a[Symbol.iterator](), h; ! (d = (h = g.next()).done); d = true) {
                    c.push(h.value);
                    if (b && c.length === b) break
                }
            } catch(i) {
                e = true;
                f = i
            } finally {
                try {
                    if (!d && g["return"]) g["return"]()
                } finally {
                    if (e) throw f
                }
            }
            return c
        }
        return function(b, c) {
            if (Array.isArray(b)) {
                return b
            } else if (Symbol.iterator in Object(b)) {
                return a(b, c)
            } else {
                throw new TypeError("Invalid attempt to destructure non-iterable instance")
            }
        }
    } ();
    var g = function(a) {
        if (Array.isArray(a)) {
            for (var b = 0,
            c = Array(a.length); b < a.length; b++) c[b] = a[b];
            return c
        } else {
            return Array.from(a)
        }
    };
    var h = {
        type: "logger",
        log: function N(a) {
            this.output("log", a)
        },
        warn: function O(a) {
            this.output("warn", a)
        },
        error: function P(a) {
            this.output("error", a)
        },
        output: function Q(a, b) {
            var c;
            if (console && console[a])(c = console)[a].apply(c, g(b))
        }
    };
    var i = function() {
        function a(c) {
            var d = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
            b(this, a);
            this.init(c, d)
        }
        a.prototype.init = function d(a) {
            var b = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
            this.prefix = b.prefix || "i18next:";
            this.logger = a || h;
            this.options = b;
            this.debug = b.debug
        };
        a.prototype.setDebug = function e(a) {
            this.debug = a
        };
        a.prototype.log = function f() {
            for (var a = arguments.length,
            b = Array(a), c = 0; c < a; c++) {
                b[c] = arguments[c]
            }
            return this.forward(b, "log", "", true)
        };
        a.prototype.warn = function g() {
            for (var a = arguments.length,
            b = Array(a), c = 0; c < a; c++) {
                b[c] = arguments[c]
            }
            return this.forward(b, "warn", "", true)
        };
        a.prototype.error = function i() {
            for (var a = arguments.length,
            b = Array(a), c = 0; c < a; c++) {
                b[c] = arguments[c]
            }
            return this.forward(b, "error", "")
        };
        a.prototype.deprecate = function j() {
            for (var a = arguments.length,
            b = Array(a), c = 0; c < a; c++) {
                b[c] = arguments[c]
            }
            return this.forward(b, "warn", "WARNING DEPRECATED: ", true)
        };
        a.prototype.forward = function k(a, b, c, d) {
            if (d && !this.debug) return null;
            if (typeof a[0] === "string") a[0] = "" + c + this.prefix + " " + a[0];
            return this.logger[b](a)
        };
        a.prototype.create = function l(b) {
            return new a(this.logger, c({
                prefix: this.prefix + ":" + b + ":"
            },
            this.options))
        };
        return a
    } ();
    var j = new i;
    var k = function() {
        function a() {
            b(this, a);
            this.observers = {}
        }
        a.prototype.on = function c(a, b) {
            var c = this;
            a.split(" ").forEach(function(a) {
                c.observers[a] = c.observers[a] || [];
                c.observers[a].push(b)
            })
        };
        a.prototype.off = function d(a, b) {
            var c = this;
            if (!this.observers[a]) {
                return
            }
            this.observers[a].forEach(function() {
                if (!b) {
                    delete c.observers[a]
                } else {
                    var d = c.observers[a].indexOf(b);
                    if (d > -1) {
                        c.observers[a].splice(d, 1)
                    }
                }
            })
        };
        a.prototype.emit = function e(a) {
            for (var b = arguments.length,
            c = Array(b > 1 ? b - 1 : 0), d = 1; d < b; d++) {
                c[d - 1] = arguments[d]
            }
            if (this.observers[a]) {
                var e = [].concat(this.observers[a]);
                e.forEach(function(a) {
                    a.apply(undefined, c)
                })
            }
            if (this.observers["*"]) {
                var f = [].concat(this.observers["*"]);
                f.forEach(function(b) {
                    var d;
                    b.apply(b, (d = [a]).concat.apply(d, c))
                })
            }
        };
        return a
    } ();
    function l(a) {
        if (a == null) return "";
        return "" + a
    }
    function m(a, b, c) {
        a.forEach(function(a) {
            if (b[a]) c[a] = b[a]
        })
    }
    function n(a, b, c) {
        function d(a) {
            return a && a.indexOf("###") > -1 ? a.replace(/###/g, ".") : a
        }
        function e() {
            return ! a || typeof a === "string"
        }
        var f = typeof b !== "string" ? [].concat(b) : b.split(".");
        while (f.length > 1) {
            if (e()) return {};
            var g = d(f.shift());
            if (!a[g] && c) a[g] = new c;
            a = a[g]
        }
        if (e()) return {};
        return {
            obj: a,
            k: d(f.shift())
        }
    }
    function o(a, b, c) {
        var d = n(a, b, Object),
        e = d.obj,
        f = d.k;
        e[f] = c
    }
    function p(a, b, c, d) {
        var e = n(a, b, Object),
        f = e.obj,
        g = e.k;
        f[g] = f[g] || [];
        if (d) f[g] = f[g].concat(c);
        if (!d) f[g].push(c)
    }
    function q(a, b) {
        var c = n(a, b),
        d = c.obj,
        e = c.k;
        if (!d) return undefined;
        return d[e]
    }
    function r(a, b, c) {
        for (var d in b) {
            if (d in a) {
                if (typeof a[d] === "string" || a[d] instanceof String || typeof b[d] === "string" || b[d] instanceof String) {
                    if (c) a[d] = b[d]
                } else {
                    r(a[d], b[d], c)
                }
            } else {
                a[d] = b[d]
            }
        }
        return a
    }
    function s(a) {
        return a.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&")
    }
    var t = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;",
        "/": "&#x2F;"
    };
    function u(a) {
        if (typeof a === "string") {
            return a.replace(/[&<>"'\/]/g,
            function(a) {
                return t[a]
            })
        }
        return a
    }
    var v = function(a) {
        d(f, a);
        function f(c) {
            var d = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {
                ns: ["translation"],
                defaultNS: "translation"
            };
            b(this, f);
            var g = e(this, a.call(this));
            g.data = c || {};
            g.options = d;
            return g
        }
        f.prototype.addNamespaces = function g(a) {
            if (this.options.ns.indexOf(a) < 0) {
                this.options.ns.push(a)
            }
        };
        f.prototype.removeNamespaces = function h(a) {
            var b = this.options.ns.indexOf(a);
            if (b > -1) {
                this.options.ns.splice(b, 1)
            }
        };
        f.prototype.getResource = function i(a, b, c) {
            var d = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : {};
            var e = d.keySeparator || this.options.keySeparator;
            if (e === undefined) e = ".";
            var f = [a, b];
            if (c && typeof c !== "string") f = f.concat(c);
            if (c && typeof c === "string") f = f.concat(e ? c.split(e) : c);
            if (a.indexOf(".") > -1) {
                f = a.split(".")
            }
            return q(this.data, f)
        };
        f.prototype.addResource = function j(a, b, c, d) {
            var e = arguments.length > 4 && arguments[4] !== undefined ? arguments[4] : {
                silent: false
            };
            var f = this.options.keySeparator;
            if (f === undefined) f = ".";
            var g = [a, b];
            if (c) g = g.concat(f ? c.split(f) : c);
            if (a.indexOf(".") > -1) {
                g = a.split(".");
                d = b;
                b = g[1]
            }
            this.addNamespaces(b);
            o(this.data, g, d);
            if (!e.silent) this.emit("added", a, b, c, d)
        };
        f.prototype.addResources = function k(a, b, c) {
            for (var d in c) {
                if (typeof c[d] === "string") this.addResource(a, b, d, c[d], {
                    silent: true
                })
            }
            this.emit("added", a, b, c)
        };
        f.prototype.addResourceBundle = function l(a, b, d, e, f) {
            var g = [a, b];
            if (a.indexOf(".") > -1) {
                g = a.split(".");
                e = d;
                d = b;
                b = g[1]
            }
            this.addNamespaces(b);
            var h = q(this.data, g) || {};
            if (e) {
                r(h, d, f)
            } else {
                h = c({},
                h, d)
            }
            o(this.data, g, h);
            this.emit("added", a, b, d)
        };
        f.prototype.removeResourceBundle = function m(a, b) {
            if (this.hasResourceBundle(a, b)) {
                delete this.data[a][b]
            }
            this.removeNamespaces(b);
            this.emit("removed", a, b)
        };
        f.prototype.hasResourceBundle = function n(a, b) {
            return this.getResource(a, b) !== undefined
        };
        f.prototype.getResourceBundle = function p(a, b) {
            if (!b) b = this.options.defaultNS;
            if (this.options.compatibilityAPI === "v1") return c({},
            this.getResource(a, b));
            return this.getResource(a, b)
        };
        f.prototype.toJSON = function s() {
            return this.data
        };
        return f
    } (k);
    var w = {
        processors: {},
        addPostProcessor: function R(a) {
            this.processors[a.name] = a
        },
        handle: function S(a, b, c, d, e) {
            var f = this;
            a.forEach(function(a) {
                if (f.processors[a]) b = f.processors[a].process(b, c, d, e)
            });
            return b
        }
    };
    var x = function(f) {
        d(g, f);
        function g(a) {
            var c = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
            b(this, g);
            var d = e(this, f.call(this));
            m(["resourceStore", "languageUtils", "pluralResolver", "interpolator", "backendConnector"], a, d);
            d.options = c;
            d.logger = j.create("translator");
            return d
        }
        g.prototype.changeLanguage = function h(a) {
            if (a) this.language = a
        };
        g.prototype.exists = function i(a) {
            var b = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {
                interpolation: {}
            };
            return this.resolve(a, b) !== undefined
        };
        g.prototype.extractFromKey = function k(a, b) {
            var c = b.nsSeparator || this.options.nsSeparator;
            if (c === undefined) c = ":";
            var d = b.keySeparator || this.options.keySeparator || ".";
            var e = b.ns || this.options.defaultNS;
            if (c && a.indexOf(c) > -1) {
                var f = a.split(c);
                if (c !== d || c === d && this.options.ns.indexOf(f[0]) > -1) e = f.shift();
                a = f.join(d)
            }
            if (typeof e === "string") e = [e];
            return {
                key: a,
                namespaces: e
            }
        };
        g.prototype.translate = function l(b) {
            var d = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
            if ((typeof d === "undefined" ? "undefined": a(d)) !== "object") {
                d = this.options.overloadTranslationOptionHandler(arguments)
            }
            if (b === undefined || b === null || b === "") return "";
            if (typeof b === "number") b = String(b);
            if (typeof b === "string") b = [b];
            var e = d.keySeparator || this.options.keySeparator || ".";
            var f = this.extractFromKey(b[b.length - 1], d),
            g = f.key,
            h = f.namespaces;
            var i = h[h.length - 1];
            var j = d.lng || this.language;
            var k = d.appendNamespaceToCIMode || this.options.appendNamespaceToCIMode;
            if (j && j.toLowerCase() === "cimode") {
                if (k) {
                    var l = d.nsSeparator || this.options.nsSeparator;
                    return i + l + g
                }
                return g
            }
            var m = this.resolve(b, d);
            var n = Object.prototype.toString.apply(m);
            var o = ["[object Number]", "[object Function]", "[object RegExp]"];
            var p = d.joinArrays !== undefined ? d.joinArrays: this.options.joinArrays;
            if (m && typeof m !== "string" && o.indexOf(n) < 0 && !(p && n === "[object Array]")) {
                if (!d.returnObjects && !this.options.returnObjects) {
                    this.logger.warn("accessing an object - but returnObjects options is not enabled!");
                    return this.options.returnedObjectHandler ? this.options.returnedObjectHandler(g, m, d) : "key '" + g + " (" + this.language + ")' returned an object instead of string."
                }
                if (d.keySeparator || this.options.keySeparator) {
                    var q = n === "[object Array]" ? [] : {};
                    for (var r in m) {
                        if (Object.prototype.hasOwnProperty.call(m, r)) {
                            q[r] = this.translate("" + g + e + r, c({},
                            d, {
                                joinArrays: false,
                                ns: h
                            }))
                        }
                    }
                    m = q
                }
            } else if (p && n === "[object Array]") {
                m = m.join(p);
                if (m) m = this.extendTranslation(m, g, d)
            } else {
                var s = false;
                var t = false;
                if (!this.isValidLookup(m) && d.defaultValue !== undefined) {
                    s = true;
                    m = d.defaultValue
                }
                if (!this.isValidLookup(m)) {
                    t = true;
                    m = g
                }
                if (t || s) {
                    this.logger.log("missingKey", j, i, g, m);
                    var u = [];
                    var v = this.languageUtils.getFallbackCodes(this.options.fallbackLng, d.lng || this.language);
                    if (this.options.saveMissingTo === "fallback" && v && v[0]) {
                        for (var w = 0; w < v.length; w++) {
                            u.push(v[w])
                        }
                    } else if (this.options.saveMissingTo === "all") {
                        u = this.languageUtils.toResolveHierarchy(d.lng || this.language)
                    } else {
                        u.push(d.lng || this.language)
                    }
                    if (this.options.saveMissing) {
                        if (this.options.missingKeyHandler) {
                            this.options.missingKeyHandler(u, i, g, m)
                        } else if (this.backendConnector && this.backendConnector.saveMissing) {
                            this.backendConnector.saveMissing(u, i, g, m)
                        }
                    }
                    this.emit("missingKey", u, i, g, m)
                }
                m = this.extendTranslation(m, g, d);
                if (t && m === g && this.options.appendNamespaceToMissingKey) m = i + ":" + g;
                if (t && this.options.parseMissingKeyHandler) m = this.options.parseMissingKeyHandler(m)
            }
            return m
        };
        g.prototype.extendTranslation = function n(a, b, d) {
            var e = this;
            if (d.interpolation) this.interpolator.init(c({},
            d, {
                interpolation: c({},
                this.options.interpolation, d.interpolation)
            }));
            var f = d.replace && typeof d.replace !== "string" ? d.replace: d;
            if (this.options.interpolation.defaultVariables) f = c({},
            this.options.interpolation.defaultVariables, f);
            a = this.interpolator.interpolate(a, f, d.lng || this.language);
            if (d.nest !== false) a = this.interpolator.nest(a,
            function() {
                return e.translate.apply(e, arguments)
            },
            d);
            if (d.interpolation) this.interpolator.reset();
            var g = d.postProcess || this.options.postProcess;
            var h = typeof g === "string" ? [g] : g;
            if (a !== undefined && h && h.length && d.applyPostProcessor !== false) {
                a = w.handle(h, a, b, d, this)
            }
            return a
        };
        g.prototype.resolve = function o(a) {
            var b = this;
            var c = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
            var d = void 0;
            if (typeof a === "string") a = [a];
            a.forEach(function(a) {
                if (b.isValidLookup(d)) return;
                var e = b.extractFromKey(a, c);
                var f = e.key;
                var g = e.namespaces;
                if (b.options.fallbackNS) g = g.concat(b.options.fallbackNS);
                var h = c.count !== undefined && typeof c.count !== "string";
                var i = c.context !== undefined && typeof c.context === "string" && c.context !== "";
                var j = c.lngs ? c.lngs: b.languageUtils.toResolveHierarchy(c.lng || b.language);
                g.forEach(function(a) {
                    if (b.isValidLookup(d)) return;
                    j.forEach(function(e) {
                        if (b.isValidLookup(d)) return;
                        var g = f;
                        var j = [g];
                        var k = void 0;
                        if (h) k = b.pluralResolver.getSuffix(e, c.count);
                        if (h && i) j.push(g + k);
                        if (i) j.push(g += "" + b.options.contextSeparator + c.context);
                        if (h) j.push(g += k);
                        var l = void 0;
                        while (l = j.pop()) {
                            if (!b.isValidLookup(d)) {
                                d = b.getResource(e, a, l, c)
                            }
                        }
                    })
                })
            });
            return d
        };
        g.prototype.isValidLookup = function p(a) {
            return a !== undefined && !(!this.options.returnNull && a === null) && !(!this.options.returnEmptyString && a === "")
        };
        g.prototype.getResource = function q(a, b, c) {
            var d = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : {};
            return this.resourceStore.getResource(a, b, c, d)
        };
        return g
    } (k);
    function y(a) {
        return a.charAt(0).toUpperCase() + a.slice(1)
    }
    var z = function() {
        function a(c) {
            b(this, a);
            this.options = c;
            this.whitelist = this.options.whitelist || false;
            this.logger = j.create("languageUtils")
        }
        a.prototype.getScriptPartFromCode = function c(a) {
            if (!a || a.indexOf("-") < 0) return null;
            var b = a.split("-");
            if (b.length === 2) return null;
            b.pop();
            return this.formatLanguageCode(b.join("-"))
        };
        a.prototype.getLanguagePartFromCode = function d(a) {
            if (!a || a.indexOf("-") < 0) return a;
            var b = a.split("-");
            return this.formatLanguageCode(b[0])
        };
        a.prototype.formatLanguageCode = function e(a) {
            if (typeof a === "string" && a.indexOf("-") > -1) {
                var b = ["hans", "hant", "latn", "cyrl", "cans", "mong", "arab"];
                var c = a.split("-");
                if (this.options.lowerCaseLng) {
                    c = c.map(function(a) {
                        return a.toLowerCase()
                    })
                } else if (c.length === 2) {
                    c[0] = c[0].toLowerCase();
                    c[1] = c[1].toUpperCase();
                    if (b.indexOf(c[1].toLowerCase()) > -1) c[1] = y(c[1].toLowerCase())
                } else if (c.length === 3) {
                    c[0] = c[0].toLowerCase();
                    if (c[1].length === 2) c[1] = c[1].toUpperCase();
                    if (c[0] !== "sgn" && c[2].length === 2) c[2] = c[2].toUpperCase();
                    if (b.indexOf(c[1].toLowerCase()) > -1) c[1] = y(c[1].toLowerCase());
                    if (b.indexOf(c[2].toLowerCase()) > -1) c[2] = y(c[2].toLowerCase())
                }
                return c.join("-")
            }
            return this.options.cleanCode || this.options.lowerCaseLng ? a.toLowerCase() : a
        };
        a.prototype.isWhitelisted = function f(a) {
            if (this.options.load === "languageOnly" || this.options.nonExplicitWhitelist) {
                a = this.getLanguagePartFromCode(a)
            }
            return ! this.whitelist || !this.whitelist.length || this.whitelist.indexOf(a) > -1
        };
        a.prototype.getFallbackCodes = function g(a, b) {
            if (!a) return [];
            if (typeof a === "string") a = [a];
            if (Object.prototype.toString.apply(a) === "[object Array]") return a;
            if (!b) return a.defaults || [];
            var c = a[b];
            if (!c) c = a[this.getScriptPartFromCode(b)];
            if (!c) c = a[this.formatLanguageCode(b)];
            if (!c) c = a.defaults;
            return c || []
        };
        a.prototype.toResolveHierarchy = function h(a, b) {
            var c = this;
            var d = this.getFallbackCodes(b || this.options.fallbackLng || [], a);
            var e = [];
            var f = function g(a) {
                if (!a) return;
                if (c.isWhitelisted(a)) {
                    e.push(a)
                } else {
                    c.logger.warn("rejecting non-whitelisted language code: " + a)
                }
            };
            if (typeof a === "string" && a.indexOf("-") > -1) {
                if (this.options.load !== "languageOnly") f(this.formatLanguageCode(a));
                if (this.options.load !== "languageOnly" && this.options.load !== "currentOnly") f(this.getScriptPartFromCode(a));
                if (this.options.load !== "currentOnly") f(this.getLanguagePartFromCode(a))
            } else if (typeof a === "string") {
                f(this.formatLanguageCode(a))
            }
            d.forEach(function(a) {
                if (e.indexOf(a) < 0) f(c.formatLanguageCode(a))
            });
            return e
        };
        return a
    } ();
    var A = [{
        lngs: ["ach", "ak", "am", "arn", "br", "fil", "gun", "ln", "mfe", "mg", "mi", "oc", "tg", "ti", "tr", "uz", "wa"],
        nr: [1, 2],
        fc: 1
    },
    {
        lngs: ["af", "an", "ast", "az", "bg", "bn", "ca", "da", "de", "dev", "el", "en", "eo", "es", "et", "eu", "fi", "fo", "fur", "fy", "gl", "gu", "ha", "he", "hi", "hu", "hy", "ia", "it", "kn", "ku", "lb", "mai", "ml", "mn", "mr", "nah", "nap", "nb", "ne", "nl", "nn", "no", "nso", "pa", "pap", "pms", "ps", "pt", "rm", "sco", "se", "si", "so", "son", "sq", "sv", "sw", "ta", "te", "tk", "ur", "yo"],
        nr: [1, 2],
        fc: 2
    },
    {
        lngs: ["ay", "bo", "cgg", "fa", "id", "ja", "jbo", "ka", "kk", "km", "ko", "ky", "lo", "ms", "sah", "su", "th", "tt", "ug", "vi", "wo", "zh"],
        nr: [1],
        fc: 3
    },
    {
        lngs: ["be", "bs", "dz", "hr", "ru", "sr", "uk"],
        nr: [1, 2, 5],
        fc: 4
    },
    {
        lngs: ["ar"],
        nr: [0, 1, 2, 3, 11, 100],
        fc: 5
    },
    {
        lngs: ["cs", "sk"],
        nr: [1, 2, 5],
        fc: 6
    },
    {
        lngs: ["csb", "pl"],
        nr: [1, 2, 5],
        fc: 7
    },
    {
        lngs: ["cy"],
        nr: [1, 2, 3, 8],
        fc: 8
    },
    {
        lngs: ["fr"],
        nr: [1, 2],
        fc: 9
    },
    {
        lngs: ["ga"],
        nr: [1, 2, 3, 7, 11],
        fc: 10
    },
    {
        lngs: ["gd"],
        nr: [1, 2, 3, 20],
        fc: 11
    },
    {
        lngs: ["is"],
        nr: [1, 2],
        fc: 12
    },
    {
        lngs: ["jv"],
        nr: [0, 1],
        fc: 13
    },
    {
        lngs: ["kw"],
        nr: [1, 2, 3, 4],
        fc: 14
    },
    {
        lngs: ["lt"],
        nr: [1, 2, 10],
        fc: 15
    },
    {
        lngs: ["lv"],
        nr: [1, 2, 0],
        fc: 16
    },
    {
        lngs: ["mk"],
        nr: [1, 2],
        fc: 17
    },
    {
        lngs: ["mnk"],
        nr: [0, 1, 2],
        fc: 18
    },
    {
        lngs: ["mt"],
        nr: [1, 2, 11, 20],
        fc: 19
    },
    {
        lngs: ["or"],
        nr: [2, 1],
        fc: 2
    },
    {
        lngs: ["ro"],
        nr: [1, 2, 20],
        fc: 20
    },
    {
        lngs: ["sl"],
        nr: [5, 1, 2, 3],
        fc: 21
    }];
    var B = {
        1 : function T(a) {
            return Number(a > 1)
        },
        2 : function U(a) {
            return Number(a != 1)
        },
        3 : function V(a) {
            return 0
        },
        4 : function W(a) {
            return Number(a % 10 == 1 && a % 100 != 11 ? 0 : a % 10 >= 2 && a % 10 <= 4 && (a % 100 < 10 || a % 100 >= 20) ? 1 : 2)
        },
        5 : function X(a) {
            return Number(a === 0 ? 0 : a == 1 ? 1 : a == 2 ? 2 : a % 100 >= 3 && a % 100 <= 10 ? 3 : a % 100 >= 11 ? 4 : 5)
        },
        6 : function Y(a) {
            return Number(a == 1 ? 0 : a >= 2 && a <= 4 ? 1 : 2)
        },
        7 : function Z(a) {
            return Number(a == 1 ? 0 : a % 10 >= 2 && a % 10 <= 4 && (a % 100 < 10 || a % 100 >= 20) ? 1 : 2)
        },
        8 : function $(a) {
            return Number(a == 1 ? 0 : a == 2 ? 1 : a != 8 && a != 11 ? 2 : 3)
        },
        9 : function _(a) {
            return Number(a >= 2)
        },
        10 : function aa(a) {
            return Number(a == 1 ? 0 : a == 2 ? 1 : a < 7 ? 2 : a < 11 ? 3 : 4)
        },
        11 : function ba(a) {
            return Number(a == 1 || a == 11 ? 0 : a == 2 || a == 12 ? 1 : a > 2 && a < 20 ? 2 : 3)
        },
        12 : function ca(a) {
            return Number(a % 10 != 1 || a % 100 == 11)
        },
        13 : function da(a) {
            return Number(a !== 0)
        },
        14 : function ea(a) {
            return Number(a == 1 ? 0 : a == 2 ? 1 : a == 3 ? 2 : 3)
        },
        15 : function fa(a) {
            return Number(a % 10 == 1 && a % 100 != 11 ? 0 : a % 10 >= 2 && (a % 100 < 10 || a % 100 >= 20) ? 1 : 2)
        },
        16 : function ga(a) {
            return Number(a % 10 == 1 && a % 100 != 11 ? 0 : a !== 0 ? 1 : 2)
        },
        17 : function ha(a) {
            return Number(a == 1 || a % 10 == 1 ? 0 : 1)
        },
        18 : function ia(a) {
            return Number(a == 0 ? 0 : a == 1 ? 1 : 2)
        },
        19 : function ja(a) {
            return Number(a == 1 ? 0 : a === 0 || a % 100 > 1 && a % 100 < 11 ? 1 : a % 100 > 10 && a % 100 < 20 ? 2 : 3)
        },
        20 : function ka(a) {
            return Number(a == 1 ? 0 : a === 0 || a % 100 > 0 && a % 100 < 20 ? 1 : 2)
        },
        21 : function la(a) {
            return Number(a % 100 == 1 ? 1 : a % 100 == 2 ? 2 : a % 100 == 3 || a % 100 == 4 ? 3 : 0)
        }
    };
    function C() {
        var a = {};
        A.forEach(function(b) {
            b.lngs.forEach(function(c) {
                a[c] = {
                    numbers: b.nr,
                    plurals: B[b.fc]
                }
            })
        });
        return a
    }
    var D = function() {
        function a(c) {
            var d = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
            b(this, a);
            this.languageUtils = c;
            this.options = d;
            this.logger = j.create("pluralResolver");
            this.rules = C()
        }
        a.prototype.addRule = function c(a, b) {
            this.rules[a] = b
        };
        a.prototype.getRule = function d(a) {
            return this.rules[this.languageUtils.getLanguagePartFromCode(a)]
        };
        a.prototype.needsPlural = function e(a) {
            var b = this.getRule(a);
            return b && b.numbers.length > 1
        };
        a.prototype.getSuffix = function f(a, b) {
            var c = this;
            var d = this.getRule(a);
            if (d) {
                if (d.numbers.length === 1) return "";
                var e = d.noAbs ? d.plurals(b) : d.plurals(Math.abs(b));
                var f = d.numbers[e];
                if (this.options.simplifyPluralSuffix && d.numbers.length === 2 && d.numbers[0] === 1) {
                    if (f === 2) {
                        f = "plural"
                    } else if (f === 1) {
                        f = ""
                    }
                }
                var g = function h() {
                    return c.options.prepend && f.toString() ? c.options.prepend + f.toString() : f.toString()
                };
                if (this.options.compatibilityJSON === "v1") {
                    if (f === 1) return "";
                    if (typeof f === "number") return "_plural_" + f.toString();
                    return g()
                } else if (this.options.compatibilityJSON === "v2" || d.numbers.length === 2 && d.numbers[0] === 1) {
                    return g()
                } else if (d.numbers.length === 2 && d.numbers[0] === 1) {
                    return g()
                }
                return this.options.prepend && e.toString() ? this.options.prepend + e.toString() : e.toString()
            }
            this.logger.warn("no plural rule found for: " + a);
            return ""
        };
        return a
    } ();
    var E = function() {
        function a() {
            var c = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};
            b(this, a);
            this.logger = j.create("interpolator");
            this.init(c, true)
        }
        a.prototype.init = function d() {
            var a = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};
            var b = arguments[1];
            if (b) {
                this.options = a;
                this.format = a.interpolation && a.interpolation.format ||
                function(a) {
                    return a
                };
                this.escape = a.interpolation && a.interpolation.escape || u
            }
            if (!a.interpolation) a.interpolation = {
                escapeValue: true
            };
            var c = a.interpolation;
            this.escapeValue = c.escapeValue !== undefined ? c.escapeValue: true;
            this.prefix = c.prefix ? s(c.prefix) : c.prefixEscaped || "{{";
            this.suffix = c.suffix ? s(c.suffix) : c.suffixEscaped || "}}";
            this.formatSeparator = c.formatSeparator ? c.formatSeparator: c.formatSeparator || ",";
            this.unescapePrefix = c.unescapeSuffix ? "": c.unescapePrefix || "-";
            this.unescapeSuffix = this.unescapePrefix ? "": c.unescapeSuffix || "";
            this.nestingPrefix = c.nestingPrefix ? s(c.nestingPrefix) : c.nestingPrefixEscaped || s("$t(");
            this.nestingSuffix = c.nestingSuffix ? s(c.nestingSuffix) : c.nestingSuffixEscaped || s(")");
            this.resetRegExp()
        };
        a.prototype.reset = function e() {
            if (this.options) this.init(this.options)
        };
        a.prototype.resetRegExp = function f() {
            var a = this.prefix + "(.+?)" + this.suffix;
            this.regexp = new RegExp(a, "g");
            var b = "" + this.prefix + this.unescapePrefix + "(.+?)" + this.unescapeSuffix + this.suffix;
            this.regexpUnescape = new RegExp(b, "g");
            var c = this.nestingPrefix + "(.+?)" + this.nestingSuffix;
            this.nestingRegexp = new RegExp(c, "g")
        };
        a.prototype.interpolate = function g(a, b, c) {
            var d = this;
            var e = void 0;
            var f = void 0;
            function g(a) {
                return a.replace(/\$/g, "$$$$")
            }
            var h = function i(a) {
                if (a.indexOf(d.formatSeparator) < 0) return q(b, a);
                var e = a.split(d.formatSeparator);
                var f = e.shift().trim();
                var g = e.join(d.formatSeparator).trim();
                return d.format(q(b, f), g, c)
            };
            this.resetRegExp();
            while (e = this.regexpUnescape.exec(a)) {
                f = h(e[1].trim());
                a = a.replace(e[0], f);
                this.regexpUnescape.lastIndex = 0
            }
            while (e = this.regexp.exec(a)) {
                f = h(e[1].trim());
                if (typeof f !== "string") f = l(f);
                if (!f) {
                    this.logger.warn("missed to pass in variable " + e[1] + " for interpolating " + a);
                    f = ""
                }
                f = this.escapeValue ? g(this.escape(f)) : g(f);
                a = a.replace(e[0], f);
                this.regexp.lastIndex = 0
            }
            return a
        };
        a.prototype.nest = function h(a, b) {
            var d = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : {};
            var e = void 0;
            var f = void 0;
            var g = c({},
            d);
            g.applyPostProcessor = false;
            function h(a) {
                if (a.indexOf(",") < 0) return a;
                var b = a.split(",");
                a = b.shift();
                var c = b.join(",");
                c = this.interpolate(c, g);
                c = c.replace(/'/g, '"');
                try {
                    g = JSON.parse(c)
                } catch(d) {
                    this.logger.error("failed parsing options string in nesting for key " + a, d)
                }
                return a
            }
            while (e = this.nestingRegexp.exec(a)) {
                f = b(h.call(this, e[1].trim()), g);
                if (f && e[0] === a && typeof f !== "string") return f;
                if (typeof f !== "string") f = l(f);
                if (!f) {
                    this.logger.warn("missed to resolve " + e[1] + " for nesting " + a);
                    f = ""
                }
                a = a.replace(e[0], f);
                this.regexp.lastIndex = 0
            }
            return a
        };
        return a
    } ();
    function F(a, b) {
        var c = a.indexOf(b);
        while (c !== -1) {
            a.splice(c, 1);
            c = a.indexOf(b)
        }
    }
    var G = function(a) {
        d(g, a);
        function g(c, d, f) {
            var h = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : {};
            b(this, g);
            var i = e(this, a.call(this));
            i.backend = c;
            i.store = d;
            i.languageUtils = f.languageUtils;
            i.options = h;
            i.logger = j.create("backendConnector");
            i.state = {};
            i.queue = [];
            if (i.backend && i.backend.init) {
                i.backend.init(f, h.backend, h)
            }
            return i
        }
        g.prototype.queueLoad = function h(a, b, c) {
            var d = this;
            var e = [];
            var f = [];
            var g = [];
            var h = [];
            a.forEach(function(a) {
                var c = true;
                b.forEach(function(b) {
                    var g = a + "|" + b;
                    if (d.store.hasResourceBundle(a, b)) {
                        d.state[g] = 2
                    } else if (d.state[g] < 0) {} else if (d.state[g] === 1) {
                        if (f.indexOf(g) < 0) f.push(g)
                    } else {
                        d.state[g] = 1;
                        c = false;
                        if (f.indexOf(g) < 0) f.push(g);
                        if (e.indexOf(g) < 0) e.push(g);
                        if (h.indexOf(b) < 0) h.push(b)
                    }
                });
                if (!c) g.push(a)
            });
            if (e.length || f.length) {
                this.queue.push({
                    pending: f,
                    loaded: {},
                    errors: [],
                    callback: c
                })
            }
            return {
                toLoad: e,
                pending: f,
                toLoadLanguages: g,
                toLoadNamespaces: h
            }
        };
        g.prototype.loaded = function i(a, b, c) {
            var d = this;
            var e = a.split("|"),
            g = f(e, 2),
            h = g[0],
            i = g[1];
            if (b) this.emit("failedLoading", h, i, b);
            if (c) {
                this.store.addResourceBundle(h, i, c)
            }
            this.state[a] = b ? -1 : 2;
            this.queue.forEach(function(c) {
                p(c.loaded, [h], i);
                F(c.pending, a);
                if (b) c.errors.push(b);
                if (c.pending.length === 0 && !c.done) {
                    d.emit("loaded", c.loaded);
                    c.done = true;
                    if (c.errors.length) {
                        c.callback(c.errors)
                    } else {
                        c.callback()
                    }
                }
            });
            this.queue = this.queue.filter(function(a) {
                return ! a.done
            })
        };
        g.prototype.read = function k(a, b, c) {
            var d = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : 0;
            var e = this;
            var f = arguments.length > 4 && arguments[4] !== undefined ? arguments[4] : 250;
            var g = arguments[5];
            if (!a.length) return g(null, {});
            return this.backend[c](a, b,
            function(h, i) {
                if (h && i && d < 5) {
                    setTimeout(function() {
                        e.read.call(e, a, b, c, d + 1, f * 2, g)
                    },
                    f);
                    return
                }
                g(h, i)
            })
        };
        g.prototype.load = function l(a, b, d) {
            var e = this;
            if (!this.backend) {
                this.logger.warn("No backend was added via i18next.use. Will not load resources.");
                return d && d()
            }
            var g = c({},
            this.backend.options, this.options.backend);
            if (typeof a === "string") a = this.languageUtils.toResolveHierarchy(a);
            if (typeof b === "string") b = [b];
            var h = this.queueLoad(a, b, d);
            if (!h.toLoad.length) {
                if (!h.pending.length) d();
                return null
            }
            if (g.allowMultiLoading && this.backend.readMulti) {
                this.read(h.toLoadLanguages, h.toLoadNamespaces, "readMulti", null, null,
                function(a, b) {
                    if (a) e.logger.warn("loading namespaces " + h.toLoadNamespaces.join(", ") + " for languages " + h.toLoadLanguages.join(", ") + " via multiloading failed", a);
                    if (!a && b) e.logger.log("successfully loaded namespaces " + h.toLoadNamespaces.join(", ") + " for languages " + h.toLoadLanguages.join(", ") + " via multiloading", b);
                    h.toLoad.forEach(function(c) {
                        var d = c.split("|"),
                        g = f(d, 2),
                        h = g[0],
                        i = g[1];
                        var j = q(b, [h, i]);
                        if (j) {
                            e.loaded(c, a, j)
                        } else {
                            var k = "loading namespace " + i + " for language " + h + " via multiloading failed";
                            e.loaded(c, k);
                            e.logger.error(k)
                        }
                    })
                })
            } else {
                h.toLoad.forEach(function(a) {
                    e.loadOne(a)
                })
            }
        };
        g.prototype.reload = function m(a, b) {
            var d = this;
            if (!this.backend) {
                this.logger.warn("No backend was added via i18next.use. Will not load resources.")
            }
            var e = c({},
            this.backend.options, this.options.backend);
            if (typeof a === "string") a = this.languageUtils.toResolveHierarchy(a);
            if (typeof b === "string") b = [b];
            if (e.allowMultiLoading && this.backend.readMulti) {
                this.read(a, b, "readMulti", null, null,
                function(c, e) {
                    if (c) d.logger.warn("reloading namespaces " + b.join(", ") + " for languages " + a.join(", ") + " via multiloading failed", c);
                    if (!c && e) d.logger.log("successfully reloaded namespaces " + b.join(", ") + " for languages " + a.join(", ") + " via multiloading", e);
                    a.forEach(function(a) {
                        b.forEach(function(b) {
                            var f = q(e, [a, b]);
                            if (f) {
                                d.loaded(a + "|" + b, c, f)
                            } else {
                                var g = "reloading namespace " + b + " for language " + a + " via multiloading failed";
                                d.loaded(a + "|" + b, g);
                                d.logger.error(g)
                            }
                        })
                    })
                })
            } else {
                a.forEach(function(a) {
                    b.forEach(function(b) {
                        d.loadOne(a + "|" + b, "re")
                    })
                })
            }
        };
        g.prototype.loadOne = function n(a) {
            var b = this;
            var c = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : "";
            var d = a.split("|"),
            e = f(d, 2),
            g = e[0],
            h = e[1];
            this.read(g, h, "read", null, null,
            function(d, e) {
                if (d) b.logger.warn(c + "loading namespace " + h + " for language " + g + " failed", d);
                if (!d && e) b.logger.log(c + "loaded namespace " + h + " for language " + g, e);
                b.loaded(a, d, e)
            })
        };
        g.prototype.saveMissing = function o(a, b, c, d) {
            if (this.backend && this.backend.create) this.backend.create(a, b, c, d);
            if (!a || !a[0]) return;
            this.store.addResource(a[0], b, c, d)
        };
        return g
    } (k);
    var H = function(a) {
        d(f, a);
        function f(c, d, g) {
            var h = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : {};
            b(this, f);
            var i = e(this, a.call(this));
            i.cache = c;
            i.store = d;
            i.services = g;
            i.options = h;
            i.logger = j.create("cacheConnector");
            if (i.cache && i.cache.init) i.cache.init(g, h.cache, h);
            return i
        }
        f.prototype.load = function g(a, b, d) {
            var e = this;
            if (!this.cache) return d && d();
            var f = c({},
            this.cache.options, this.options.cache);
            var g = typeof a === "string" ? this.services.languageUtils.toResolveHierarchy(a) : a;
            if (f.enabled) {
                this.cache.load(g,
                function(a, b) {
                    if (a) e.logger.error("loading languages " + g.join(", ") + " from cache failed", a);
                    if (b) {
                        for (var c in b) {
                            if (Object.prototype.hasOwnProperty.call(b, c)) {
                                for (var f in b[c]) {
                                    if (Object.prototype.hasOwnProperty.call(b[c], f)) {
                                        if (f !== "i18nStamp") {
                                            var h = b[c][f];
                                            if (h) e.store.addResourceBundle(c, f, h)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (d) d()
                })
            } else if (d) {
                d()
            }
        };
        f.prototype.save = function h() {
            if (this.cache && this.options.cache && this.options.cache.enabled) this.cache.save(this.store.data)
        };
        return f
    } (k);
    function I() {
        return {
            debug: false,
            initImmediate: true,
            ns: ["translation"],
            defaultNS: ["translation"],
            fallbackLng: ["dev"],
            fallbackNS: false,
            whitelist: false,
            nonExplicitWhitelist: false,
            load: "all",
            preload: false,
            simplifyPluralSuffix: true,
            keySeparator: ".",
            nsSeparator: ":",
            pluralSeparator: "_",
            contextSeparator: "_",
            saveMissing: false,
            saveMissingTo: "fallback",
            missingKeyHandler: false,
            postProcess: false,
            returnNull: true,
            returnEmptyString: true,
            returnObjects: false,
            joinArrays: false,
            returnedObjectHandler: function a() {},
            parseMissingKeyHandler: false,
            appendNamespaceToMissingKey: false,
            appendNamespaceToCIMode: false,
            overloadTranslationOptionHandler: function b(a) {
                return {
                    defaultValue: a[1]
                }
            },
            interpolation: {
                escapeValue: true,
                format: function c(a, b, d) {
                    return a
                },
                prefix: "{{",
                suffix: "}}",
                formatSeparator: ",",
                unescapePrefix: "-",
                nestingPrefix: "$t(",
                nestingSuffix: ")"
            }
        }
    }
    function J(a) {
        if (typeof a.ns === "string") a.ns = [a.ns];
        if (typeof a.fallbackLng === "string") a.fallbackLng = [a.fallbackLng];
        if (typeof a.fallbackNS === "string") a.fallbackNS = [a.fallbackNS];
        if (a.whitelist && a.whitelist.indexOf("cimode") < 0) a.whitelist.push("cimode");
        return a
    }
    function K() {}
    var L = function(a) {
        d(f, a);
        function f() {
            var c = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};
            var d = arguments[1];
            b(this, f);
            var g = e(this, a.call(this));
            g.options = J(c);
            g.services = {};
            g.logger = j;
            g.modules = {
                external: []
            };
            if (d && !g.isInitialized && !c.isClone) {
                var h;
                if (!g.options.initImmediate) return h = g.init(c, d),
                e(g, h);
                setTimeout(function() {
                    g.init(c, d)
                },
                0)
            }
            return g
        }
        f.prototype.init = function g() {
            var a = this;
            var b = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};
            var d = arguments[1];
            if (typeof b === "function") {
                d = b;
                b = {}
            }
            this.options = c({},
            I(), this.options, J(b));
            this.format = this.options.interpolation.format;
            if (!d) d = K;
            function e(a) {
                if (!a) return null;
                if (typeof a === "function") return new a;
                return a
            }
            if (!this.options.isClone) {
                if (this.modules.logger) {
                    j.init(e(this.modules.logger), this.options)
                } else {
                    j.init(null, this.options)
                }
                var f = new z(this.options);
                this.store = new v(this.options.resources, this.options);
                var g = this.services;
                g.logger = j;
                g.resourceStore = this.store;
                g.resourceStore.on("added removed",
                function(a, b) {
                    g.cacheConnector.save()
                });
                g.languageUtils = f;
                g.pluralResolver = new D(f, {
                    prepend: this.options.pluralSeparator,
                    compatibilityJSON: this.options.compatibilityJSON,
                    simplifyPluralSuffix: this.options.simplifyPluralSuffix
                });
                g.interpolator = new E(this.options);
                g.backendConnector = new G(e(this.modules.backend), g.resourceStore, g, this.options);
                g.backendConnector.on("*",
                function(b) {
                    for (var c = arguments.length,
                    d = Array(c > 1 ? c - 1 : 0), e = 1; e < c; e++) {
                        d[e - 1] = arguments[e]
                    }
                    a.emit.apply(a, [b].concat(d))
                });
                g.backendConnector.on("loaded",
                function(a) {
                    g.cacheConnector.save()
                });
                g.cacheConnector = new H(e(this.modules.cache), g.resourceStore, g, this.options);
                g.cacheConnector.on("*",
                function(b) {
                    for (var c = arguments.length,
                    d = Array(c > 1 ? c - 1 : 0), e = 1; e < c; e++) {
                        d[e - 1] = arguments[e]
                    }
                    a.emit.apply(a, [b].concat(d))
                });
                if (this.modules.languageDetector) {
                    g.languageDetector = e(this.modules.languageDetector);
                    g.languageDetector.init(g, this.options.detection, this.options)
                }
                this.translator = new x(this.services, this.options);
                this.translator.on("*",
                function(b) {
                    for (var c = arguments.length,
                    d = Array(c > 1 ? c - 1 : 0), e = 1; e < c; e++) {
                        d[e - 1] = arguments[e]
                    }
                    a.emit.apply(a, [b].concat(d))
                });
                this.modules.external.forEach(function(b) {
                    if (b.init) b.init(a)
                })
            }
            var h = ["getResource", "addResource", "addResources", "addResourceBundle", "removeResourceBundle", "hasResourceBundle", "getResourceBundle"];
            h.forEach(function(b) {
                a[b] = function() {
                    var c;
                    return (c = a.store)[b].apply(c, arguments)
                }
            });
            var i = function k() {
                a.changeLanguage(a.options.lng,
                function(b, c) {
                    a.isInitialized = true;
                    a.logger.log("initialized", a.options);
                    a.emit("initialized", a.options);
                    d(b, c)
                })
            };
            if (this.options.resources || !this.options.initImmediate) {
                i()
            } else {
                setTimeout(i, 0)
            }
            return this
        };
        f.prototype.loadResources = function h() {
            var a = this;
            var b = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : K;
            if (!this.options.resources) {
                if (this.language && this.language.toLowerCase() === "cimode") return b();
                var c = [];
                var d = function f(b) {
                    if (!b) return;
                    var d = a.services.languageUtils.toResolveHierarchy(b);
                    d.forEach(function(a) {
                        if (c.indexOf(a) < 0) c.push(a)
                    })
                };
                if (!this.language) {
                    var e = this.services.languageUtils.getFallbackCodes(this.options.fallbackLng);
                    e.forEach(function(a) {
                        return d(a)
                    })
                } else {
                    d(this.language)
                }
                if (this.options.preload) {
                    this.options.preload.forEach(function(a) {
                        return d(a)
                    })
                }
                this.services.cacheConnector.load(c, this.options.ns,
                function() {
                    a.services.backendConnector.load(c, a.options.ns, b)
                })
            } else {
                b(null)
            }
        };
        f.prototype.reloadResources = function i(a, b) {
            if (!a) a = this.languages;
            if (!b) b = this.options.ns;
            this.services.backendConnector.reload(a, b)
        };
        f.prototype.use = function k(a) {
            if (a.type === "backend") {
                this.modules.backend = a
            }
            if (a.type === "cache") {
                this.modules.cache = a
            }
            if (a.type === "logger" || a.log && a.warn && a.error) {
                this.modules.logger = a
            }
            if (a.type === "languageDetector") {
                this.modules.languageDetector = a
            }
            if (a.type === "postProcessor") {
                w.addPostProcessor(a)
            }
            if (a.type === "3rdParty") {
                this.modules.external.push(a)
            }
            return this
        };
        f.prototype.changeLanguage = function l(a, b) {
            var c = this;
            var d = function f(a, d) {
                if (d) {
                    c.emit("languageChanged", d);
                    c.logger.log("languageChanged", d)
                }
                if (b) b(a,
                function() {
                    return c.t.apply(c, arguments)
                })
            };
            var e = function g(a) {
                if (a) {
                    c.language = a;
                    c.languages = c.services.languageUtils.toResolveHierarchy(a);
                    c.translator.changeLanguage(a);
                    if (c.services.languageDetector) c.services.languageDetector.cacheUserLanguage(a)
                }
                c.loadResources(function(b) {
                    d(b, a)
                })
            };
            if (!a && this.services.languageDetector && !this.services.languageDetector.async) {
                e(this.services.languageDetector.detect())
            } else if (!a && this.services.languageDetector && this.services.languageDetector.async) {
                this.services.languageDetector.detect(e)
            } else {
                e(a)
            }
        };
        f.prototype.getFixedT = function m(a, b) {
            var d = this;
            var e = function f(a) {
                var b = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
                var e = c({},
                b);
                e.lng = e.lng || f.lng;
                e.lngs = e.lngs || f.lngs;
                e.ns = e.ns || f.ns;
                return d.t(a, e)
            };
            if (typeof a === "string") {
                e.lng = a
            } else {
                e.lngs = a
            }
            e.ns = b;
            return e
        };
        f.prototype.t = function n() {
            var a;
            return this.translator && (a = this.translator).translate.apply(a, arguments)
        };
        f.prototype.exists = function o() {
            var a;
            return this.translator && (a = this.translator).exists.apply(a, arguments)
        };
        f.prototype.setDefaultNamespace = function p(a) {
            this.options.defaultNS = a
        };
        f.prototype.loadNamespaces = function q(a, b) {
            var c = this;
            if (!this.options.ns) return b && b();
            if (typeof a === "string") a = [a];
            a.forEach(function(a) {
                if (c.options.ns.indexOf(a) < 0) c.options.ns.push(a)
            });
            this.loadResources(b)
        };
        f.prototype.loadLanguages = function r(a, b) {
            if (typeof a === "string") a = [a];
            var c = this.options.preload || [];
            var d = a.filter(function(a) {
                return c.indexOf(a) < 0
            });
            if (!d.length) return b();
            this.options.preload = c.concat(d);
            this.loadResources(b)
        };
        f.prototype.dir = function s(a) {
            if (!a) a = this.languages && this.languages.length > 0 ? this.languages[0] : this.language;
            if (!a) return "rtl";
            var b = ["ar", "shu", "sqr", "ssh", "xaa", "yhd", "yud", "aao", "abh", "abv", "acm", "acq", "acw", "acx", "acy", "adf", "ads", "aeb", "aec", "afb", "ajp", "apc", "apd", "arb", "arq", "ars", "ary", "arz", "auz", "avl", "ayh", "ayl", "ayn", "ayp", "bbz", "pga", "he", "iw", "ps", "pbt", "pbu", "pst", "prp", "prd", "ur", "ydd", "yds", "yih", "ji", "yi", "hbo", "men", "xmn", "fa", "jpr", "peo", "pes", "prs", "dv", "sam"];
            return b.indexOf(this.services.languageUtils.getLanguagePartFromCode(a)) >= 0 ? "rtl": "ltr"
        };
        f.prototype.createInstance = function t() {
            var a = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};
            var b = arguments[1];
            return new f(a, b)
        };
        f.prototype.cloneInstance = function u() {
            var a = this;
            var b = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};
            var d = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : K;
            var e = c({},
            this.options, b, {
                isClone: true
            });
            var g = new f(e, d);
            var h = ["store", "services", "language"];
            h.forEach(function(b) {
                g[b] = a[b]
            });
            g.translator = new x(g.services, g.options);
            g.translator.on("*",
            function(a) {
                for (var b = arguments.length,
                c = Array(b > 1 ? b - 1 : 0), d = 1; d < b; d++) {
                    c[d - 1] = arguments[d]
                }
                g.emit.apply(g, [a].concat(c))
            });
            g.init(e, d);
            return g
        };
        return f
    } (k);
    var M = new L;
    return M
}); (function(a, b) {
    typeof exports === "object" && typeof module !== "undefined" ? module.exports = b() : typeof define === "function" && define.amd ? define("lngDetector", b) : a.i18nextBrowserLanguageDetector = b()
})(this,
function() {
    "use strict";
    var a = {};
    a.classCallCheck = function(a, b) {
        if (! (a instanceof b)) {
            throw new TypeError("Cannot call a class as a function")
        }
    };
    a.createClass = function() {
        function a(a, b) {
            for (var c = 0; c < b.length; c++) {
                var d = b[c];
                d.enumerable = d.enumerable || false;
                d.configurable = true;
                if ("value" in d) d.writable = true;
                if (document.documentMode != "8" && document.documentMode != "7") {
                    Object.defineProperty(a, d.key, d)
                } else {
                    return
                }
            }
        }
        return function(b, c, d) {
            if (c) a(b.prototype, c);
            if (d) a(b, d);
            return b
        }
    } ();
    a;
    var b = [];
    var c = b.forEach;
    var d = b.slice;
    function e(a) {
        c.call(d.call(arguments, 1),
        function(b) {
            if (b) {
                for (var c in b) {
                    if (a[c] === undefined) a[c] = b[c]
                }
            }
        });
        return a
    }
    var f = {
        create: function q(a, b, c, d) {
            var e = void 0;
            if (c) {
                var f = new Date;
                f.setTime(f.getTime() + c * 60 * 1e3);
                e = "; expires=" + f.toGMTString()
            } else e = "";
            d = d ? "domain=" + d + ";": "";
            document.cookie = a + "=" + b + e + ";" + d + "path=/"
        },
        read: function r(a) {
            var b = a + "=";
            var c = document.cookie.split(";");
            for (var d = 0; d < c.length; d++) {
                var e = c[d];
                while (e.charAt(0) === " ") {
                    e = e.substring(1, e.length)
                }
                if (e.indexOf(b) === 0) return e.substring(b.length, e.length)
            }
            return null
        },
        remove: function s(a) {
            this.create(a, "", -1)
        }
    };
    var g = {
        name: "cookie",
        lookup: function t(a) {
            var b = void 0;
            if (a.lookupCookie && typeof document !== "undefined") {
                var c = f.read(a.lookupCookie);
                if (c) b = c
            }
            return b
        },
        cacheUserLanguage: function u(a, b) {
            if (b.lookupCookie && typeof document !== "undefined") {
                f.create(b.lookupCookie, a, b.cookieMinutes, b.cookieDomain)
            }
        }
    };
    var h = {
        name: "querystring",
        lookup: function v(a) {
            var b = void 0;
            if (typeof window !== "undefined") {
                var c = window.location.search.substring(1);
                var d = c.split("&");
                for (var e = 0; e < d.length; e++) {
                    var f = d[e].indexOf("=");
                    if (f > 0) {
                        var g = d[e].substring(0, f);
                        if (g === a.lookupQuerystring) {
                            b = d[e].substring(f + 1)
                        }
                    }
                }
            }
            return b
        }
    };
    var i = void 0;
    try {
        i = window !== "undefined" && window.localStorage !== null;
        var j = "i18next.translate.boo";
        window.localStorage.setItem(j, "foo");
        window.localStorage.removeItem(j)
    } catch(k) {
        i = false
    }
    var l = {
        name: "localStorage",
        lookup: function w(a) {
            var b = void 0;
            if (a.lookupLocalStorage && i) {
                var c = window.localStorage.getItem(a.lookupLocalStorage);
                if (c) b = c
            }
            return b
        },
        cacheUserLanguage: function x(a, b) {
            if (b.lookupLocalStorage && i) {
                window.localStorage.setItem(b.lookupLocalStorage, a)
            }
        }
    };
    var m = {
        name: "navigator",
        lookup: function y(a) {
            var b = [];
            if (typeof navigator !== "undefined") {
                if (navigator.languages) {
                    for (var c = 0; c < navigator.languages.length; c++) {
                        b.push(navigator.languages[c])
                    }
                }
                if (navigator.userLanguage) {
                    b.push(navigator.userLanguage)
                }
                if (navigator.language) {
                    b.push(navigator.language)
                }
            }
            return b.length > 0 ? b: undefined
        }
    };
    var n = {
        name: "htmlTag",
        lookup: function z(a) {
            var b = void 0;
            var c = a.htmlTag || (typeof document !== "undefined" ? document.documentElement: null);
            if (c && typeof c.getAttribute === "function") {
                b = c.getAttribute("lang")
            }
            return b
        }
    };
    function o() {
        return {
            order: ["querystring", "cookie", "localStorage", "navigator", "htmlTag"],
            lookupQuerystring: "lng",
            lookupCookie: "i18next",
            lookupLocalStorage: "i18nextLng",
            caches: ["localStorage"],
            excludeCacheFor: ["cimode"]
        }
    }
    var p = function() {
        function b(c) {
            var d = arguments.length <= 1 || arguments[1] === undefined ? {}: arguments[1];
            a.classCallCheck(this, b);
            this.type = "languageDetector";
            this.detectors = {};
            this.init(c, d)
        }
        a.createClass(b, [{
            key: "init",
            value: function c(a) {
                var b = arguments.length <= 1 || arguments[1] === undefined ? {}: arguments[1];
                var c = arguments.length <= 2 || arguments[2] === undefined ? {}: arguments[2];
                this.services = a;
                this.options = e(b, this.options || {},
                o());
                this.i18nOptions = c;
                this.addDetector(g);
                this.addDetector(h);
                this.addDetector(l);
                this.addDetector(m);
                this.addDetector(n)
            }
        },
        {
            key: "addDetector",
            value: function d(a) {
                this.detectors[a.name] = a
            }
        },
        {
            key: "detect",
            value: function f(a) {
                var b = this;
                if (!a) a = this.options.order;
                var c = [];
                a.forEach(function(a) {
                    if (b.detectors[a]) {
                        var d = b.detectors[a].lookup(b.options);
                        if (d && typeof d === "string") d = [d];
                        if (d) c = c.concat(d)
                    }
                });
                var d = void 0;
                c.forEach(function(a) {
                    if (d) return;
                    var c = b.services.languageUtils.formatLanguageCode(a);
                    if (b.services.languageUtils.isWhitelisted(c)) d = c
                });
                return d || this.i18nOptions.fallbackLng[0]
            }
        },
        {
            key: "cacheUserLanguage",
            value: function i(a, b) {
                var c = this;
                if (!b) b = this.options.caches;
                if (!b) return;
                if (this.options.excludeCacheFor && this.options.excludeCacheFor.indexOf(a) > -1) return;
                b.forEach(function(b) {
                    if (c.detectors[b]) c.detectors[b].cacheUserLanguage(a, c.options)
                })
            }
        }]);
        return b
    } ();
    p.type = "languageDetector";
    return p
});
define("app/util/i18next", ["require", "jquery", "app/util/lang", "app/util/i18nextMax", "lngDetector"],
function(a) {
    var b = a("jquery");
    var c = a("app/util/lang");
    if (document.documentMode != "8" && document.documentMode != "7") {
        var d = a("app/util/i18nextMax");
        var e = a("lngDetector")
    }
    var f = {
        order: ["navigator"],
        lookupQuerystring: "lng",
        lookupCookie: "i18next",
        lookupLocalStorage: "i18nextLng",
        caches: [""],
        excludeCacheFor: ["cimode"],
        cookieMinutes: 10,
        cookieDomain: "myDomain",
        htmlTag: document.documentElement
    };
    if (document.documentMode != "8" && document.documentMode != "7") {
        d.use(e).init({
            fallbackLng: "en",
            lowerCaseLng: true,
            detection: f,
            debug: false,
            resources: c,
            load: "all"
        })
    } else {
        var g = navigator.userLanguage.toLowerCase(),
        h;
        b.each(c,
        function(a) {
            if (g.indexOf(a) > -1) {
                h = a
            }
        });
        return {
            translateButtons: function() {
                var a = [{
                    key: b(".survey_nextpage"),
                    str: "next"
                },
                {
                    key: b(".survey_prevpage"),
                    str: "prev"
                },
                {
                    key: b(".survey_submit"),
                    str: "submit"
                }];
                function d(a) {
                    var d, e;
                    for (var f = 0; f < a.length; f++) {
                        d = a[f].str;
                        e = c[h].translation[d];
                        a[f].key.text(e);
                        b.attr(a[f].key, {
                            title: e,
                            "aria-label": e
                        })
                    }
                }
                d(a)
            },
            translate: function(a) {
                return c[h].translation[a]
            }
        }
    }
    var i = {
        translateButtons: function() {
            var a = [{
                key: b(".survey_nextpage"),
                str: "next"
            },
            {
                key: b(".survey_prevpage"),
                str: "prev"
            },
            {
                key: b(".survey_submit"),
                str: "submit"
            }];
            function c(a) {
                for (var b = 0; b < a.length; b++) {
                    a[b].key.html(d.t(a[b].str)).attr("title", d.t(a[b].str)).attr("aria-label", d.t(a[b].str))
                }
            }
            c(a)
        },
        translate: function(a) {
            return d.t(a)
        }
    };
    return i
});
define("app/util/mo_push", ["require", "exports", "module", "jquery"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = "/sur/mo_push";
    var f = function(a) {
        this.id = a;
        this.reportData = {};
        return this
    };
    f.prototype.send = function() {
        var a = Math.pow(10, Number((Math.random() * 10).toFixed(0)));
        var b = 36889694;
        var c = this.reportData;
        if (d.isEmptyObject(c)) {
            return false
        }
        d.extend(c, {
            random: a,
            total: 1
        });
        var f = d.param(c);
        var g = f.length + b;
        var h = this.id;
        return d.ajax({
            url: e,
            data: {
                msg: f,
                hash: g,
                id: h
            },
            method: "post"
        })
    };
    c.exports = f
});
define("app/util/report_menshen", ["require", "exports", "module", "app/util/mo_push"],
function(a, b, c) {
    "use strict";
    var d = a("app/util/mo_push");
    var e = new d(4003);
    c.exports = function(a) {
        e.reportData["CGI"] = a;
        e.send()
    }
});
define("app/common/ur_app", ["require", "underscore", "bowser", "app/util/events", "./models/user", "./ur_ajax", "app/util/report_menshen"],
function(a) {
    "use strict";
    var b = a("underscore");
    var c = a("bowser");
    var d = a("app/util/events");
    var e = a("./models/user");
    var f = a("./ur_ajax");
    var g = a("app/util/report_menshen");
    var h = {
        initialize: function() {},
        requestSuccessCallback: function(a) {
            var b = a.status;
            var c = a.info;
            switch (b) {
            case - 1 : this.trigger("not-invited", a);
                break;
            case - 2 : this.trigger("not-logged-in", a);
                break;
            case 1:
                this.trigger("done", a);
                break;
            default:
                var d = ["验证码输入不正确002", "验证码输入不正确003", "验证码输入不正确004"];
                if (d.indexOf(c) > -1) {
                    this.trigger("verify", a)
                } else {
                    this.trigger("fail", a)
                }
            }
        },
        requestErrorCallback: function(a) {
            var b = a.status;
            var c = this.opts.url || "unknown url";
            switch (b) {
            case 501:
                g(c);
                alert("答题内容含有非法字符，请联系客服解决");
                break;
            default:
                break
            }
        },
        defaultOptions: {
            cache: false
        },
        defaultCallbacks: {
            "not-logged-in": function(a) {
                window.onbeforeunload = function() {};
                this.trigger("require-login");
                e.login()
            },
            fail: function(a) {
                if (a.info) {
                    alert(a.info)
                }
            }
        }
    };
    function i(a) {
        this.opts = a;
        this.loadConfig();
        this.init()
    }
    b.extend(i.prototype, d, {
        loadConfig: function() {
            f.setConfig(h)
        },
        init: function() {
            this.checkUA();
            this.login()
        },
        checkUA: function() {
            if (c.msie && parseInt(c.version, 10) < 7) {}
        },
        login: function() {
            var a = this;
            var b = this.opts.needLogin;
            var c = this.opts.preventAutoLogin;
            if (b) {
                e.on("not-logged-in",
                function() {
                    e.login()
                });
                e.on("logged-in",
                function() {
                    a.trigger("start")
                })
            } else {
                e.on("not-logged-in",
                function() {
                    a.trigger("start")
                });
                e.on("logged-in",
                function() {
                    a.trigger("start")
                })
            }
            if (!c) {
                e.autoLogin()
            } else {
                setTimeout(function() {
                    a.trigger("start")
                },
                0)
            }
        }
    });
    return i
});
define("app/util/report_performance", ["require", "exports", "module", "jquery", "app/util/mo_push"],
function(a, b, c) {
    "use strict";
    if (!window.performance || !window.performance.timing) {
        return function() {}
    }
    var d = a("jquery");
    var e = a("app/util/mo_push");
    var f = new e(4001);
    function g() {
        var a = {};
        var b = window.performance.timing;
        var c = b.navigationStart;
        var d = (new Date).getTime();
        a["dns"] = h(b.domainLookupEnd - b.domainLookupStart);
        a["tcp"] = h(b.connectEnd - b.connectStart);
        a["rrt"] = h(b.redirectEnd - b.redirectStart);
        a["ttfb"] = h(b.responseStart - c);
        a["domready"] = h(b.domContentLoadedEventStart - c);
        a["onload"] = h(b.loadEventStart - c);
        a["pathname"] = location.pathname;
        a["now"] = h(d - c);
        return a
    }
    function h(a) {
        var b = (a / 1e3).toFixed(3);
        if (b >= 0 && b < 60) {
            return b
        }
        return 0
    }
    function i() {
        f.reportData = g();
        f.send()
    }
    function j() {
        if (document.readyState === "complete") {
            i()
        } else {
            d(document).on("load", i)
        }
    }
    c.exports = j
});
define("app/util/sns", ["require", "exports", "module", "jquery"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e;
    var f = window.navigator.userAgent.toLowerCase();
    var g = function(a, b, c) {};
    if (/micromessenger/.test(f)) {
        e = "micromessenger"
    } else if (/qq/.test(f)) {
        e = "qq"
    } else {
        e = "browser"
    }
    switch (e) {
    case "micromessenger":
        g = function(b, c, e) {
            a(["https://res.wx.qq.com/open/js/jweixin-1.0.0.js"],
            function(a) {
                var f = window.location.href.replace(window.location.hash, "");
                d.getJSON("//wj.qq.com/s315/signature.php?url=" + encodeURIComponent(f)).done(function(b) {
                    a.config({
                        debug: false,
                        appId: b.appId,
                        timestamp: b.timestamp,
                        nonceStr: b.nonceStr,
                        signature: b.signature,
                        jsApiList: ["onMenuShareTimeline", "onMenuShareAppMessage", "onMenuShareQQ", "onMenuShareQZone"]
                    })
                });
                a.ready(function() {
                    a.onMenuShareTimeline({
                        title: b,
                        link: e,
                        imgUrl: "http://wj.qq.com/image/wx_share_logo.png",
                        success: function() {},
                        cancel: function() {}
                    });
                    a.onMenuShareAppMessage({
                        title: b,
                        desc: c,
                        link: e,
                        imgUrl: "http://wj.qq.com/image/wx_share_logo.png",
                        type: "link",
                        dataUrl: "",
                        success: function() {},
                        cancel: function() {}
                    });
                    a.onMenuShareQQ({
                        title: b,
                        desc: c,
                        link: e,
                        imgUrl: "http://wj.qq.com/image/wx_share_logo.png",
                        success: function() {},
                        cancel: function() {}
                    });
                    a.onMenuShareQZone({
                        title: b,
                        desc: c,
                        link: e,
                        imgUrl: "http://wj.qq.com/image/wx_share_logo.png",
                        success: function() {},
                        cancel: function() {}
                    })
                })
            })
        };
        break;
    case "qq":
        g = function(b, c, d) {
            a(["https://open.mobile.qq.com/sdk/qqapi.js?_bid=152"],
            function(a) {
                a = window.mqq;
                a.data.setShareInfo({
                    share_url: d,
                    title: b,
                    desc: c,
                    image_url: "http://wj.qq.com/image/wx_share_logo.png"
                })
            })
        };
        break;
    case "browser":
        break;
    default:
        break
    }
    c.exports = {
        initShare: function(a) {
            var b = a.get("survey");
            var c = d.trim(d("<div>" + b.title + "</div>").text());
            var e = d.trim(d("<div>" + b.prefix + "</div>").text());
            var f = window.location.href;
            g(c, e, f)
        },
        shareCommon: function(a, b, c) {
            g(a, b, c)
        }
    }
}); (function(a) {
    if (typeof exports === "object" && typeof module !== "undefined") {
        module.exports = a()
    } else if (typeof define === "function" && define.amd) {
        define("dragula", [], a)
    } else {
        var b;
        if (typeof window !== "undefined") {
            b = window
        } else if (typeof global !== "undefined") {
            b = global
        } else if (typeof self !== "undefined") {
            b = self
        } else {
            b = this
        }
        b.dragula = a()
    }
})(function() {
    var a, b, c;
    return function d(a, b, c) {
        function e(g, h) {
            if (!b[g]) {
                if (!a[g]) {
                    var i = typeof require == "function" && require;
                    if (!h && i) return i(g, !0);
                    if (f) return f(g, !0);
                    var j = new Error("Cannot find module '" + g + "'");
                    throw j.code = "MODULE_NOT_FOUND",
                    j
                }
                var k = b[g] = {
                    exports: {}
                };
                a[g][0].call(k.exports,
                function(b) {
                    var c = a[g][1][b];
                    return e(c ? c: b)
                },
                k, k.exports, d, a, b, c)
            }
            return b[g].exports
        }
        var f = typeof require == "function" && require;
        for (var g = 0; g < c.length; g++) e(c[g]);
        return e
    } ({
        1 : [function(a, b, c) {
            "use strict";
            var d = {};
            var e = "(?:^|\\s)";
            var f = "(?:\\s|$)";
            function g(a) {
                var b = d[a];
                if (b) {
                    b.lastIndex = 0
                } else {
                    d[a] = b = new RegExp(e + a + f, "g")
                }
                return b
            }
            function h(a, b) {
                var c = a.className;
                if (!c.length) {
                    a.className = b
                } else if (!g(b).test(c)) {
                    a.className += " " + b
                }
            }
            function i(a, b) {
                a.className = a.className.replace(g(b), " ").trim()
            }
            b.exports = {
                add: h,
                rm: i
            }
        },
        {}],
        2 : [function(a, b, c) { (function(c) {
                "use strict";
                var d = a("contra/emitter");
                var e = a("crossvent");
                var f = a("./classes");
                function g(a, b) {
                    var c = arguments.length;
                    if (c === 1 && Array.isArray(a) === false) {
                        b = a;
                        a = []
                    }
                    var e = document.body;
                    var g = document.documentElement;
                    var j;
                    var q;
                    var s;
                    var t;
                    var u;
                    var v;
                    var w;
                    var x;
                    var y;
                    var z = null;
                    var A;
                    var B;
                    var C = 5;
                    var D;
                    var E;
                    function F() {
                        var a = navigator.userAgent.toLowerCase();
                        var b = a.match(/android\s([0-9\.]*)/);
                        return b ? parseInt(b[1], 10) : false
                    }
                    function G() {
                        var a = navigator.userAgent.toLowerCase();
                        var b = a.match(/mqqbrowser/);
                        return !! b
                    }
                    if (F() == "5" && G()) {
                        E = true
                    }
                    var H = b || {};
                    if (H.moves === void 0) {
                        H.moves = m
                    }
                    if (H.accepts === void 0) {
                        H.accepts = m
                    }
                    if (H.invalid === void 0) {
                        H.invalid = U
                    }
                    if (H.containers === void 0) {
                        H.containers = a || []
                    }
                    if (H.isContainer === void 0) {
                        H.isContainer = l
                    }
                    if (H.copy === void 0) {
                        H.copy = false
                    }
                    if (H.revertOnSpill === void 0) {
                        H.revertOnSpill = false
                    }
                    if (H.removeOnSpill === void 0) {
                        H.removeOnSpill = false
                    }
                    if (H.direction === void 0) {
                        H.direction = "vertical"
                    }
                    if (H.mirrorContainer === void 0) {
                        H.mirrorContainer = e
                    }
                    var I = d({
                        containers: H.containers,
                        start: S,
                        end: V,
                        cancel: $,
                        remove: Z,
                        destroy: N,
                        dragging: false
                    });
                    if (H.removeOnSpill === true) {
                        I.on("over", da).on("out", ea)
                    }
                    K();
                    return I;
                    function J(a) {
                        return I.containers.indexOf(a) !== -1 || H.isContainer(a)
                    }
                    function K(a) {
                        var b = a ? "remove": "add";
                        h(g, b, "mousedown", P);
                        h(g, b, "mouseup", X)
                    }
                    function L(a) {
                        var b = a ? "remove": "add";
                        h(g, b, "mousemove", Q)
                    }
                    function M(a) {
                        var b = a ? "remove": "add";
                        h(g, b, "selectstart", O);
                        h(g, b, "click", O)
                    }
                    function N() {
                        K(true);
                        X({})
                    }
                    function O(a) {
                        if (A) {
                            a.preventDefault()
                        }
                    }
                    function P(a) {
                        var b = a.which !== 0 && a.which !== 1 || a.metaKey || a.ctrlKey;
                        if (b) {
                            return
                        }
                        var c = a.target;
                        var d = R(c);
                        if (!d) {
                            return
                        }
                        A = d;
                        B = {
                            x: a.pageX,
                            y: a.pageY
                        };
                        L();
                        if (a.type === "mousedown") {
                            a.preventDefault();
                            if (c.tagName === "INPUT" || c.tagName === "TEXTAREA") {
                                c.focus()
                            }
                        }
                    }
                    function Q(a) {
                        if (a.type !== "touchmove") {
                            if (Math.abs(a.pageX - B.x) < C && Math.abs(a.pageY - B.y) < C) {
                                return
                            }
                        }
                        var b = A;
                        L(true);
                        M();
                        V();
                        T(b);
                        var c = i(s);
                        t = r("pageX", a) - c.left;
                        u = r("pageY", a) - c.top;
                        f.add(x || s, "gu-transit");
                        fa();
                        ca(a)
                    }
                    function R(a) {
                        if (I.dragging && j) {
                            return
                        }
                        if (J(a)) {
                            return
                        }
                        var b = a;
                        while (a.parentElement && J(a.parentElement) === false) {
                            if (H.invalid(a, b)) {
                                return
                            }
                            a = a.parentElement;
                            if (!a) {
                                return
                            }
                        }
                        var c = a.parentElement;
                        if (!c) {
                            return
                        }
                        if (H.invalid(a, b)) {
                            return
                        }
                        var d = H.moves(a, c, b);
                        if (!d) {
                            return
                        }
                        return {
                            item: a,
                            source: c
                        }
                    }
                    function S(a) {
                        var b = R(a);
                        if (b) {
                            T(b)
                        }
                    }
                    function T(a) {
                        if (ja(a.item, a.source)) {
                            x = a.item.cloneNode(true);
                            I.emit("cloned", x, a.item, "copy")
                        }
                        q = a.source;
                        s = a.item;
                        v = w = p(a.item);
                        I.dragging = true;
                        I.emit("drag", s, q)
                    }
                    function U() {
                        return false
                    }
                    function V() {
                        if (!I.dragging) {
                            return
                        }
                        var a = x || s;
                        Y(a, a.parentElement)
                    }
                    function W() {
                        A = false;
                        L(true);
                        M(true)
                    }
                    function X(a) {
                        if (E && D) {
                            D()
                        }
                        W();
                        if (!I.dragging) {
                            return
                        }
                        var b = x || s;
                        var c = r("clientX", a);
                        var d = r("clientY", a);
                        var e = k(j, c, d);
                        var f = ba(e, c, d);
                        if (f && (!x || f !== q)) {
                            Y(b, f)
                        } else if (H.removeOnSpill) {
                            Z()
                        } else {
                            $()
                        }
                    }
                    function Y(a, b) {
                        if (aa(b)) {
                            I.emit("cancel", a, q)
                        } else {
                            I.emit("drop", a, b, q)
                        }
                        _()
                    }
                    function Z() {
                        if (!I.dragging) {
                            return
                        }
                        var a = x || s;
                        var b = a.parentElement;
                        if (b) {
                            b.removeChild(a)
                        }
                        I.emit(x ? "cancel": "remove", a, b);
                        _()
                    }
                    function $(a) {
                        if (!I.dragging) {
                            return
                        }
                        var b = arguments.length > 0 ? a: H.revertOnSpill;
                        var c = x || s;
                        var d = c.parentElement;
                        if (d === q && x) {
                            d.removeChild(x)
                        }
                        var e = aa(d);
                        if (e === false && !x && b) {
                            q.insertBefore(c, v)
                        }
                        if (e || b) {
                            I.emit("cancel", c, q)
                        } else {
                            I.emit("drop", c, d, q)
                        }
                        _()
                    }
                    function _() {
                        var a = x || s;
                        W();
                        ga();
                        if (a) {
                            f.rm(a, "gu-transit")
                        }
                        if (y) {
                            clearTimeout(y)
                        }
                        I.dragging = false;
                        I.emit("out", a, z, q);
                        I.emit("dragend", a);
                        q = s = x = v = w = y = z = null
                    }
                    function aa(a, b) {
                        var c;
                        if (b !== void 0) {
                            c = b
                        } else if (j) {
                            c = w
                        } else {
                            c = p(x || s)
                        }
                        return a === q && c === v
                    }
                    function ba(a, b, c) {
                        var d = a;
                        while (d && !e()) {
                            d = d.parentElement
                        }
                        return d;
                        function e() {
                            var e = J(d);
                            if (e === false) {
                                return false
                            }
                            var f = ha(d, a);
                            var g = ia(d, f, b, c);
                            var h = aa(d, g);
                            if (h) {
                                return true
                            }
                            return H.accepts(s, d, q, g)
                        }
                    }
                    function ca(a) {
                        if (!j) {
                            return
                        }
                        a.preventDefault();
                        var b = r("clientX", a);
                        var c = r("clientY", a);
                        var d = b - t;
                        var e = c - u;
                        j.style.left = d + "px";
                        j.style.top = e + "px";
                        var f = x || s;
                        var g = k(j, b, c);
                        var h = ba(g, b, c);
                        var i = h !== null && h !== z;
                        if (i || h === null) {
                            y();
                            z = h;
                            o()
                        }
                        if (h === q && x) {
                            if (f.parentElement) {
                                f.parentElement.removeChild(f)
                            }
                            return
                        }
                        var l;
                        var m = ha(h, g);
                        if (m !== null) {
                            l = ia(h, m, b, c)
                        } else if (H.revertOnSpill === true && !x) {
                            l = v;
                            h = q
                        } else {
                            if (x && f.parentElement) {
                                f.parentElement.removeChild(f)
                            }
                            return
                        }
                        if (l === null || l !== f && l !== p(f) && l !== w) {
                            w = l;
                            if (E) {
                                D = function() {
                                    h.insertBefore(f, l);
                                    I.emit("shadow", f, h)
                                }
                            } else {
                                h.insertBefore(f, l);
                                I.emit("shadow", f, h)
                            }
                        }
                        function n(a) {
                            I.emit(a, f, z, q)
                        }
                        function o() {
                            if (i) {
                                n("over")
                            }
                        }
                        function y() {
                            if (z) {
                                n("out")
                            }
                        }
                    }
                    function da(a) {
                        f.rm(a, "gu-hide")
                    }
                    function ea(a) {
                        if (I.dragging) {
                            f.add(a, "gu-hide")
                        }
                    }
                    function fa() {
                        if (j) {
                            return
                        }
                        var a = s.getBoundingClientRect();
                        j = s.cloneNode(true);
                        j.style.width = n(a) + "px";
                        j.style.height = o(a) + "px";
                        f.rm(j, "gu-transit");
                        f.add(j, "gu-mirror");
                        H.mirrorContainer.appendChild(j);
                        h(g, "add", "mousemove", ca);
                        f.add(H.mirrorContainer, "gu-unselectable");
                        I.emit("cloned", j, s, "mirror")
                    }
                    function ga() {
                        if (j) {
                            f.rm(H.mirrorContainer, "gu-unselectable");
                            h(g, "remove", "mousemove", ca);
                            j.parentElement.removeChild(j);
                            j = null
                        }
                    }
                    function ha(a, b) {
                        var c = b;
                        while (c !== a && c.parentElement !== a) {
                            c = c.parentElement
                        }
                        if (c === g) {
                            return null
                        }
                        return c
                    }
                    function ia(a, b, c, d) {
                        var e = H.direction === "horizontal";
                        var f = b !== a ? h() : g();
                        return f;
                        function g() {
                            var b = a.children.length;
                            var f;
                            var g;
                            var h;
                            for (f = 0; f < b; f++) {
                                g = a.children[f];
                                h = g.getBoundingClientRect();
                                if (e && h.left > c) {
                                    return g
                                }
                                if (!e && h.top > d) {
                                    return g
                                }
                            }
                            return null
                        }
                        function h() {
                            var a = b.getBoundingClientRect();
                            if (e) {
                                return i(c > a.left + n(a) / 2)
                            }
                            return i(d > a.top + o(a) / 2)
                        }
                        function i(a) {
                            return a ? p(b) : b
                        }
                    }
                    function ja(a, b) {
                        return typeof H.copy === "boolean" ? H.copy: H.copy(a, b)
                    }
                }
                function h(a, b, d, f) {
                    var g = {
                        mouseup: "touchend",
                        mousedown: "touchstart",
                        mousemove: "touchmove"
                    };
                    var h = {
                        mouseup: "MSPointerUp",
                        mousedown: "MSPointerDown",
                        mousemove: "MSPointerMove"
                    };
                    if (c.navigator.msPointerEnabled) {
                        e[b](a, h[d], f)
                    }
                    e[b](a, g[d], f);
                    e[b](a, d, f)
                }
                function i(a) {
                    var b = a.getBoundingClientRect();
                    return {
                        left: b.left + j("scrollLeft", "pageXOffset"),
                        top: b.top + j("scrollTop", "pageYOffset")
                    }
                }
                function j(a, b) {
                    if (typeof c[b] !== "undefined") {
                        return c[b]
                    }
                    var d = document.documentElement;
                    if (d.clientHeight) {
                        return d[a]
                    }
                    var e = document.body;
                    return e[a]
                }
                function k(a, b, c) {
                    var d = a || {};
                    var e = d.className;
                    var f;
                    d.className += " gu-hide";
                    f = document.elementFromPoint(b, c);
                    d.className = e;
                    return f
                }
                function l() {
                    return false
                }
                function m() {
                    return true
                }
                function n(a) {
                    return a.width || a.right - a.left
                }
                function o(a) {
                    return a.height || a.bottom - a.top
                }
                function p(a) {
                    return a.nextElementSibling || b();
                    function b() {
                        var b = a;
                        do {
                            b = b.nextSibling
                        } while ( b && b . nodeType !== 1 );
                        return b
                    }
                }
                function q(a) {
                    if (a.targetTouches && a.targetTouches.length) {
                        return a.targetTouches[0]
                    }
                    if (a.changedTouches && a.changedTouches.length) {
                        return a.changedTouches[0]
                    }
                    return a
                }
                function r(a, b) {
                    var c = q(b);
                    var d = {
                        pageX: "clientX",
                        pageY: "clientY"
                    };
                    if (a in d && !(a in c) && d[a] in c) {
                        a = d[a]
                    }
                    return c[a]
                }
                b.exports = g
            }).call(this, typeof global !== "undefined" ? global: typeof self !== "undefined" ? self: typeof window !== "undefined" ? window: {})
        },
        {
            "./classes": 1,
            "contra/emitter": 4,
            crossvent: 8
        }],
        3 : [function(a, b, c) {
            "use strict";
            var d = a("ticky");
            b.exports = function e(a, b, c) {
                if (!a) {
                    return
                }
                d(function e() {
                    a.apply(c || null, b || [])
                })
            }
        },
        {
            ticky: 6
        }],
        4 : [function(a, b, c) {
            "use strict";
            var d = a("atoa");
            var e = a("./debounce");
            b.exports = function f(a, b) {
                var c = b || {};
                var f = {};
                if (a === undefined) {
                    a = {}
                }
                a.on = function(b, c) {
                    if (!f[b]) {
                        f[b] = [c]
                    } else {
                        f[b].push(c)
                    }
                    return a
                };
                a.once = function(b, c) {
                    c._once = true;
                    a.on(b, c);
                    return a
                };
                a.off = function(b, c) {
                    var d = arguments.length;
                    if (d === 1) {
                        delete f[b]
                    } else if (d === 0) {
                        f = {}
                    } else {
                        var e = f[b];
                        if (!e) {
                            return a
                        }
                        e.splice(e.indexOf(c), 1)
                    }
                    return a
                };
                a.emit = function() {
                    var b = d(arguments);
                    return a.emitterSnapshot(b.shift()).apply(this, b)
                };
                a.emitterSnapshot = function(b) {
                    var g = (f[b] || []).slice(0);
                    return function() {
                        var f = d(arguments);
                        var h = this || a;
                        if (b === "error" && c.throws !== false && !g.length) {
                            throw f.length === 1 ? f[0] : f
                        }
                        g.forEach(function i(d) {
                            if (c.async) {
                                e(d, f, h)
                            } else {
                                d.apply(h, f)
                            }
                            if (d._once) {
                                a.off(b, d)
                            }
                        });
                        return a
                    }
                };
                return a
            }
        },
        {
            "./debounce": 3,
            atoa: 5
        }],
        5 : [function(a, b, c) {
            b.exports = function d(a, b) {
                return Array.prototype.slice.call(a, b)
            }
        },
        {}],
        6 : [function(a, b, c) {
            var d = typeof setImmediate === "function",
            e;
            if (d) {
                e = function(a) {
                    setImmediate(a)
                }
            } else {
                e = function(a) {
                    setTimeout(a, 0)
                }
            }
            b.exports = e
        },
        {}],
        7 : [function(a, b, c) { (function(a) {
                var c = a.CustomEvent;
                function d() {
                    try {
                        var a = new c("cat", {
                            detail: {
                                foo: "bar"
                            }
                        });
                        return "cat" === a.type && "bar" === a.detail.foo
                    } catch(b) {}
                    return false
                }
                b.exports = d() ? c: "function" === typeof document.createEvent ?
                function e(a, b) {
                    var c = document.createEvent("CustomEvent");
                    if (b) {
                        c.initCustomEvent(a, b.bubbles, b.cancelable, b.detail)
                    } else {
                        c.initCustomEvent(a, false, false, void 0)
                    }
                    return c
                }: function f(a, b) {
                    var c = document.createEventObject();
                    c.type = a;
                    if (b) {
                        c.bubbles = Boolean(b.bubbles);
                        c.cancelable = Boolean(b.cancelable);
                        c.detail = b.detail
                    } else {
                        c.bubbles = false;
                        c.cancelable = false;
                        c.detail = void 0
                    }
                    return c
                }
            }).call(this, typeof global !== "undefined" ? global: typeof self !== "undefined" ? self: typeof window !== "undefined" ? window: {})
        },
        {}],
        8 : [function(a, b, c) { (function(c) {
                "use strict";
                var d = a("custom-event");
                var e = a("./eventmap");
                var f = c.document;
                var g = j;
                var h = l;
                var i = [];
                if (!c.addEventListener) {
                    g = k;
                    h = m
                }
                b.exports = {
                    add: g,
                    remove: h,
                    fabricate: n
                };
                function j(a, b, c, d) {
                    return a.addEventListener(b, c, d)
                }
                function k(a, b, c) {
                    return a.attachEvent("on" + b, p(a, b, c))
                }
                function l(a, b, c, d) {
                    return a.removeEventListener(b, c, d)
                }
                function m(a, b, c) {
                    var d = q(a, b, c);
                    if (d) {
                        return a.detachEvent("on" + b, d)
                    }
                }
                function n(a, b, c) {
                    var g = e.indexOf(b) === -1 ? i() : h();
                    if (a.dispatchEvent) {
                        a.dispatchEvent(g)
                    } else {
                        a.fireEvent("on" + b, g)
                    }
                    function h() {
                        var a;
                        if (f.createEvent) {
                            a = f.createEvent("Event");
                            a.initEvent(b, true, true)
                        } else if (f.createEventObject) {
                            a = f.createEventObject()
                        }
                        return a
                    }
                    function i() {
                        return new d(b, {
                            detail: c
                        })
                    }
                }
                function o(a, b, d) {
                    return function e(b) {
                        var e = b || c.event;
                        e.target = e.target || e.srcElement;
                        e.preventDefault = e.preventDefault ||
                        function f() {
                            e.returnValue = false
                        };
                        e.stopPropagation = e.stopPropagation ||
                        function g() {
                            e.cancelBubble = true
                        };
                        e.which = e.which || e.keyCode;
                        d.call(a, e)
                    }
                }
                function p(a, b, c) {
                    var d = q(a, b, c) || o(a, b, c);
                    i.push({
                        wrapper: d,
                        element: a,
                        type: b,
                        fn: c
                    });
                    return d
                }
                function q(a, b, c) {
                    var d = r(a, b, c);
                    if (d) {
                        var e = i[d].wrapper;
                        i.splice(d, 1);
                        return e
                    }
                }
                function r(a, b, c) {
                    var d, e;
                    for (d = 0; d < i.length; d++) {
                        e = i[d];
                        if (e.element === a && e.type === b && e.fn === c) {
                            return d
                        }
                    }
                }
            }).call(this, typeof global !== "undefined" ? global: typeof self !== "undefined" ? self: typeof window !== "undefined" ? window: {})
        },
        {
            "./eventmap": 9,
            "custom-event": 7
        }],
        9 : [function(a, b, c) { (function(a) {
                "use strict";
                var c = [];
                var d = "";
                var e = /^on/;
                for (d in a) {
                    if (e.test(d)) {
                        c.push(d.slice(2))
                    }
                }
                b.exports = c
            }).call(this, typeof global !== "undefined" ? global: typeof self !== "undefined" ? self: typeof window !== "undefined" ? window: {})
        },
        {}]
    },
    {},
    [2])(2)
}); (function() {
    "use strict";
    function a(b, d) {
        var e;
        d = d || {};
        this.trackingClick = false;
        this.trackingClickStart = 0;
        this.targetElement = null;
        this.touchStartX = 0;
        this.touchStartY = 0;
        this.lastTouchIdentifier = 0;
        this.touchBoundary = d.touchBoundary || 10;
        this.layer = b;
        this.tapDelay = d.tapDelay || 200;
        this.tapTimeout = d.tapTimeout || 700;
        if (a.notNeeded(b)) {
            return
        }
        function f(a, b) {
            return function() {
                return a.apply(b, arguments)
            }
        }
        var g = ["onMouse", "onClick", "onTouchStart", "onTouchMove", "onTouchEnd", "onTouchCancel"];
        var h = this;
        for (var i = 0,
        j = g.length; i < j; i++) {
            h[g[i]] = f(h[g[i]], h)
        }
        if (c) {
            b.addEventListener("mouseover", this.onMouse, true);
            b.addEventListener("mousedown", this.onMouse, true);
            b.addEventListener("mouseup", this.onMouse, true)
        }
        b.addEventListener("click", this.onClick, true);
        b.addEventListener("touchstart", this.onTouchStart, false);
        b.addEventListener("touchmove", this.onTouchMove, false);
        b.addEventListener("touchend", this.onTouchEnd, false);
        b.addEventListener("touchcancel", this.onTouchCancel, false);
        if (!Event.prototype.stopImmediatePropagation) {
            b.removeEventListener = function(a, c, d) {
                var e = Node.prototype.removeEventListener;
                if (a === "click") {
                    e.call(b, a, c.hijacked || c, d)
                } else {
                    e.call(b, a, c, d)
                }
            };
            b.addEventListener = function(a, c, d) {
                var e = Node.prototype.addEventListener;
                if (a === "click") {
                    e.call(b, a, c.hijacked || (c.hijacked = function(a) {
                        if (!a.propagationStopped) {
                            c(a)
                        }
                    }), d)
                } else {
                    e.call(b, a, c, d)
                }
            }
        }
        if (typeof b.onclick === "function") {
            e = b.onclick;
            b.addEventListener("click",
            function(a) {
                e(a)
            },
            false);
            b.onclick = null
        }
    }
    var b = navigator.userAgent.indexOf("Windows Phone") >= 0;
    var c = navigator.userAgent.indexOf("Android") > 0 && !b;
    var d = /iP(ad|hone|od)/.test(navigator.userAgent) && !b;
    var e = d && /OS 4_\d(_\d)?/.test(navigator.userAgent);
    var f = d && /OS [6-7]_\d/.test(navigator.userAgent);
    var g = navigator.userAgent.indexOf("BB10") > 0;
    a.prototype.needsClick = function(a) {
        switch (a.nodeName.toLowerCase()) {
        case "button":
        case "select":
        case "textarea":
            if (a.disabled) {
                return true
            }
            break;
        case "input":
            if (d && a.type === "file" || a.disabled) {
                return true
            }
            break;
        case "label":
        case "iframe":
        case "video":
            return true
        }
        return /\bneedsclick\b/.test(a.className)
    };
    a.prototype.needsFocus = function(a) {
        switch (a.nodeName.toLowerCase()) {
        case "textarea":
            return true;
        case "select":
            return ! c;
        case "input":
            switch (a.type) {
            case "button":
            case "checkbox":
            case "file":
            case "image":
            case "radio":
            case "submit":
                return false
            }
            return ! a.disabled && !a.readOnly;
        default:
            return /\bneedsfocus\b/.test(a.className)
        }
    };
    a.prototype.sendClick = function(a, b) {
        var c, d;
        if (document.activeElement && document.activeElement !== a) {
            document.activeElement.blur()
        }
        d = b.changedTouches[0];
        c = document.createEvent("MouseEvents");
        c.initMouseEvent(this.determineEventType(a), true, true, window, 1, d.screenX, d.screenY, d.clientX, d.clientY, false, false, false, false, 0, null);
        c.forwardedTouchEvent = true;
        a.dispatchEvent(c)
    };
    a.prototype.determineEventType = function(a) {
        if (c && a.tagName.toLowerCase() === "select") {
            return "mousedown"
        }
        return "click"
    };
    a.prototype.focus = function(a) {
        var b;
        if (d && a.setSelectionRange && a.type.indexOf("date") !== 0 && a.type !== "time" && a.type !== "month") {
            b = a.value.length;
            a.setSelectionRange(b, b)
        } else {
            a.focus()
        }
    };
    a.prototype.updateScrollParent = function(a) {
        var b, c;
        b = a.fastClickScrollParent;
        if (!b || !b.contains(a)) {
            c = a;
            do {
                if (c.scrollHeight > c.offsetHeight) {
                    b = c;
                    a.fastClickScrollParent = c;
                    break
                }
                c = c.parentElement
            } while ( c )
        }
        if (b) {
            b.fastClickLastScrollTop = b.scrollTop
        }
    };
    a.prototype.getTargetElementFromEventTarget = function(a) {
        if (a.nodeType === Node.TEXT_NODE) {
            return a.parentNode
        }
        return a
    };
    a.prototype.onTouchStart = function(a) {
        var b, c, f;
        if (a.targetTouches.length > 1) {
            return true
        }
        b = this.getTargetElementFromEventTarget(a.target);
        c = a.targetTouches[0];
        if (d) {
            f = window.getSelection();
            if (f.rangeCount && !f.isCollapsed) {
                return true
            }
            if (!e) {
                if (c.identifier && c.identifier === this.lastTouchIdentifier) {
                    a.preventDefault();
                    return false
                }
                this.lastTouchIdentifier = c.identifier;
                this.updateScrollParent(b)
            }
        }
        this.trackingClick = true;
        this.trackingClickStart = a.timeStamp;
        this.targetElement = b;
        this.touchStartX = c.pageX;
        this.touchStartY = c.pageY;
        if (a.timeStamp - this.lastClickTime < this.tapDelay) {
            a.preventDefault()
        }
        return true
    };
    a.prototype.touchHasMoved = function(a) {
        var b = a.changedTouches[0],
        c = this.touchBoundary;
        if (Math.abs(b.pageX - this.touchStartX) > c || Math.abs(b.pageY - this.touchStartY) > c) {
            return true
        }
        return false
    };
    a.prototype.onTouchMove = function(a) {
        if (!this.trackingClick) {
            return true
        }
        if (this.targetElement !== this.getTargetElementFromEventTarget(a.target) || this.touchHasMoved(a)) {
            this.trackingClick = false;
            this.targetElement = null
        }
        return true
    };
    a.prototype.findControl = function(a) {
        if (a.control !== undefined) {
            return a.control
        }
        if (a.htmlFor) {
            return document.getElementById(a.htmlFor)
        }
        return a.querySelector("button, input:not([type=hidden]), keygen, meter, output, progress, select, textarea")
    };
    a.prototype.onTouchEnd = function(a) {
        var b, g, h, i, j, k = this.targetElement;
        if (!this.trackingClick) {
            return true
        }
        if (a.timeStamp - this.lastClickTime < this.tapDelay) {
            this.cancelNextClick = true;
            return true
        }
        if (a.timeStamp - this.trackingClickStart > this.tapTimeout) {
            return true
        }
        this.cancelNextClick = false;
        this.lastClickTime = a.timeStamp;
        g = this.trackingClickStart;
        this.trackingClick = false;
        this.trackingClickStart = 0;
        if (f) {
            j = a.changedTouches[0];
            k = document.elementFromPoint(j.pageX - window.pageXOffset, j.pageY - window.pageYOffset) || k;
            k.fastClickScrollParent = this.targetElement.fastClickScrollParent
        }
        h = k.tagName.toLowerCase();
        if (h === "label") {
            b = this.findControl(k);
            if (b) {
                this.focus(k);
                if (c) {
                    return false
                }
                k = b
            }
        } else if (this.needsFocus(k)) {
            if (a.timeStamp - g > 100 || d && window.top !== window && h === "input") {
                this.targetElement = null;
                return false
            }
            this.focus(k);
            this.sendClick(k, a);
            if (!d || h !== "select") {
                this.targetElement = null;
                a.preventDefault()
            }
            return false
        }
        if (d && !e) {
            i = k.fastClickScrollParent;
            if (i && i.fastClickLastScrollTop !== i.scrollTop) {
                return true
            }
        }
        if (!this.needsClick(k)) {
            a.preventDefault();
            this.sendClick(k, a)
        }
        return false
    };
    a.prototype.onTouchCancel = function() {
        this.trackingClick = false;
        this.targetElement = null
    };
    a.prototype.onMouse = function(a) {
        if (!this.targetElement) {
            return true
        }
        if (a.forwardedTouchEvent) {
            return true
        }
        if (!a.cancelable) {
            return true
        }
        if (!this.needsClick(this.targetElement) || this.cancelNextClick) {
            if (a.stopImmediatePropagation) {
                a.stopImmediatePropagation()
            } else {
                a.propagationStopped = true
            }
            a.stopPropagation();
            a.preventDefault();
            return false
        }
        return true
    };
    a.prototype.onClick = function(a) {
        var b;
        if (this.trackingClick) {
            this.targetElement = null;
            this.trackingClick = false;
            return true
        }
        if (a.target.type === "submit" && a.detail === 0) {
            return true
        }
        b = this.onMouse(a);
        if (!b) {
            this.targetElement = null
        }
        return b
    };
    a.prototype.destroy = function() {
        var a = this.layer;
        if (c) {
            a.removeEventListener("mouseover", this.onMouse, true);
            a.removeEventListener("mousedown", this.onMouse, true);
            a.removeEventListener("mouseup", this.onMouse, true)
        }
        a.removeEventListener("click", this.onClick, true);
        a.removeEventListener("touchstart", this.onTouchStart, false);
        a.removeEventListener("touchmove", this.onTouchMove, false);
        a.removeEventListener("touchend", this.onTouchEnd, false);
        a.removeEventListener("touchcancel", this.onTouchCancel, false)
    };
    a.notNeeded = function(a) {
        var b;
        var d;
        var e;
        var f;
        if (typeof window.ontouchstart === "undefined") {
            return true
        }
        d = +(/Chrome\/([0-9]+)/.exec(navigator.userAgent) || [, 0])[1];
        if (d) {
            if (c) {
                b = document.querySelector("meta[name=viewport]");
                if (b) {
                    if (b.content.indexOf("user-scalable=no") !== -1) {
                        return true
                    }
                    if (d > 31 && document.documentElement.scrollWidth <= window.outerWidth) {
                        return true
                    }
                }
            } else {
                return true
            }
        }
        if (g) {
            e = navigator.userAgent.match(/Version\/([0-9]*)\.([0-9]*)/);
            if (e[1] >= 10 && e[2] >= 3) {
                b = document.querySelector("meta[name=viewport]");
                if (b) {
                    if (b.content.indexOf("user-scalable=no") !== -1) {
                        return true
                    }
                    if (document.documentElement.scrollWidth <= window.outerWidth) {
                        return true
                    }
                }
            }
        }
        if (a.style.msTouchAction === "none" || a.style.touchAction === "manipulation") {
            return true
        }
        f = +(/Firefox\/([0-9]+)/.exec(navigator.userAgent) || [, 0])[1];
        if (f >= 27) {
            b = document.querySelector("meta[name=viewport]");
            if (b && (b.content.indexOf("user-scalable=no") !== -1 || document.documentElement.scrollWidth <= window.outerWidth)) {
                return true
            }
        }
        if (a.style.touchAction === "none" || a.style.touchAction === "manipulation") {
            return true
        }
        return false
    };
    a.attach = function(b, c) {
        return new a(b, c)
    };
    if (typeof define === "function" && typeof define.amd === "object" && define.amd) {
        define("fastclick", [],
        function() {
            return a
        })
    } else if (typeof module !== "undefined" && module.exports) {
        module.exports = a.attach;
        module.exports.FastClick = a
    } else {
        window.FastClick = a
    }
})();
define("app/common/views/getQuestionInput", ["require", "exports", "module", "jquery", "underscore"],
function(a, b, c) {
    var d = a("jquery");
    var e = a("underscore");
    var f = d(document).width() < 800;
    var g = {
        _updateFillblank: function(a, b) {
            b.find(".title .fill_blank, .description .fill_blank, .description_content .fill_blank").each(function(b, c) {
                var e = d(c);
                var f = e.val();
                var g = e.attr("id");
                if (f === "") {
                    return
                }
                if (g) {
                    a.blanks.push({
                        id: g,
                        value: f
                    })
                } else {
                    a.answer_texts.push(f)
                }
            })
        }
    };
    g.radio = function(a) {
        var b = {
            id: String(a.data("id")),
            answer_texts: [],
            options: [],
            blanks: []
        };
        g._updateFillblank(b, a);
        a.find("[type=radio], [type=checkbox]").each(function(a, c) {
            var e = d(c);
            var f = String(e.data("oid"));
            var g = d(c).next();
            var h = [];
            g.find(".fill_blank").each(function(a, c) {
                var e = d(c);
                var f = e.val();
                var g = e.attr("id");
                if (g) {
                    b.blanks.push({
                        id: g,
                        value: f
                    })
                } else {
                    h.push(f)
                }
            });
            b.options.push({
                id: f,
                checked: e[0].checked,
                answer_texts: h
            })
        });
        return b
    };
    g.checkbox = g.radio;
    g.select = function(a) {
        var b = {
            id: String(a.data("id")),
            options: [],
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(b, a);
        var c = a.find("select").data("options");
        e.forEach(c,
        function(a) {
            if (a.id == "98") {
                b.options.push({
                    id: "98",
                    checked: false,
                    text: ""
                })
            } else {
                b.options.push({
                    id: a.id,
                    checked: false
                })
            }
        });
        a.find("option").each(function(c, f) {
            f = d(f);
            var g = f.data("oid");
            var h;
            if (!g) {
                return
            }
            g = String(g);
            if (g == "98") {
                h = e.find(b.options, {
                    id: g
                });
                h.checked = f[0].selected;
                h.text = a.find(".survey_form_input_other").val()
            } else {
                h = e.find(b.options, {
                    id: g
                });
                h.checked = f[0].selected
            }
        });
        return b
    };
    g.text = function(a) {
        var b = {
            id: String(a.data("id")),
            text: d("#text_" + a.data("id")).val(),
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(b, a);
        return b
    };
    g.textarea = function(a) {
        var b = {
            id: String(a.data("id")),
            text: d("#textarea_" + a.data("id")).val(),
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(b, a);
        return b
    };
    g.star = function(a) {
        var b = null;
        var c = {
            id: String(a.data("id")),
            rating: b,
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(c, a);
        a.find("input[type=radio]").each(function(a, c) {
            if (c.checked) {
                b = c.value
            }
        });
        c.rating = b;
        return c
    };
    g.matrix_radio = function(a) {
        var b = [];
        var c = {
            id: String(a.data("id")),
            groups: b,
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(c, a);
        a.find(".matrix_group .matrix_item").each(function(a, c) {
            var e = d(c);
            var f = [];
            e.find("input[type=radio], input[type=checkbox]").each(function(a, b) {
                var c = d(b);
                f.push({
                    id: String(c.data("oid")),
                    checked: c[0].checked
                })
            });
            b.push({
                id: String(e.data("tid")),
                options: f
            })
        });
        c.groups = b;
        return c
    };
    g.matrix_checkbox = g.matrix_radio;
    g.matrix_star = function(a) {
        var b = [];
        var c = {
            id: String(a.data("id")),
            groups: b,
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(c, a);
        a.find(".matrix_group .matrix_item").each(function(a, c) {
            var e = d(c);
            var f = null;
            var g = e.attr("selected") || false;
            var h = e.find("input");
            if (h.length === 1) {
                f = g ? h.prop("value") : ""
            } else {
                h.each(function(a, b) {
                    if (b.checked) {
                        f = b.value
                    }
                })
            }
            b.push({
                id: String(e.data("tid")),
                text: f
            })
        });
        return c
    };
    g.sort = function(a) {
        var b = [];
        var c = a.find(".index_container .index");
        var e = a.find(".sort_container .sort_option_item");
        var f = c.map(function(a, b) {
            var c = d(b);
            if (c.hasClass("active") && c.css("display") === "block") {
                return b
            } else {
                return null
            }
        });
        var h = e.map(function(a, b) {
            var c = d(b);
            if (c.css("display") === "block") {
                return b
            } else {
                return null
            }
        });
        var i = e.length;
        var j = {
            id: String(a.data("id")),
            options: b,
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(j, a);
        if (f.length === h.length) {
            h.each(function(a, c) {
                b.push({
                    id: String(d(c).data("oid")),
                    sort_no: a + 1
                })
            });
            if (h.length !== 0) {
                e.not(h).each(function(a, c) {
                    b.push({
                        id: String(d(c).data("oid")),
                        sort_no: i
                    })
                })
            }
        }
        j.options = b;
        return j
    };
    g.chained_selects = function(a) {
        var b = [];
        var c = {
            id: String(a.data("id")),
            id_list: b,
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(c, a);
        if (!f) {
            a.find(".option.selected").each(function(a, c) {
                b.push(d(c).data("id"))
            })
        } else {
            a.find(".level.selected select").each(function(a, c) {
                var e = d(c).val();
                if (e && e !== "") {
                    b.push(e)
                }
            })
        }
        c.id_list = b;
        return c
    };
    g.upload = function(a) {
        var b = {
            id: String(a.data("id")),
            file_name_src: a.data("file_name_src"),
            file_name_dst: a.data("file_name_dst"),
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(b, a);
        return b
    };
    g.description = function(a) {
        var b = {
            id: String(a.data("id")),
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(b, a);
        return b
    };
    g.text_multiple = function(a) {
        var b = {
            id: String(a.data("id")),
            answer_texts: [],
            blanks: []
        };
        g._updateFillblank(b, a);
        return b
    };
    c.exports = g
});
define("app/common/api/reward", ["require", "exports", "module", "app/common/ur_ajax"],
function(a, b, c) {
    "use strict";
    var d = a("app/common/ur_ajax");
    c.exports = {
        startReward: function(a, b, c) {
            return d({
                type: "POST",
                url: "/sur/start_reward",
                data: {
                    survey_id: a,
                    ticket: b,
                    randstr: c
                }
            })
        },
        contactReward: function(a, b, c) {
            return d({
                type: "POST",
                url: "/sur/contact_reward",
                data: {
                    tel: a,
                    name: b,
                    code: c
                }
            })
        },
        getList: function(a) {
            return d({
                type: "GET",
                url: "/survey/default_reward_list",
                data: {
                    id: a
                }
            }).done(function(a) {
                a.data.forEach(function(a) {
                    var b = a.title.split("-");
                    a.title = b[0];
                    a.desc = b[1]
                })
            })
        },
        initReward: function(a, b) {
            return d({
                type: "GET",
                url: "/sur/default_reward_list_by_user",
                data: {
                    id: a,
                    token: b
                }
            },
            {
                preventDefaultCallbacks: true
            })
        },
        getReward: function(a, b, c) {
            return d({
                type: "POST",
                url: "/sur/start_default_reward",
                data: {
                    id: a,
                    token: c,
                    user_id: b
                }
            })
        }
    }
});
typeof window.localStorage == "undefined" && ~
function() {
    var a = window.localStorage = {},
    b = "data-userdata",
    c = document,
    d = c.body,
    e = function(a, c, e, f) {
        d.load(b);
        e = d.getAttribute(b) || "";
        f = RegExp("\\b" + a + "\\b,?", "i");
        var g = f.test(e) ? 1 : 0;
        e = c ? e.replace(f, "") : g ? e: e === "" ? a: e.split(",").concat(a).join(",");
        d.setAttribute(b, e);
        d.save(b)
    };
    d.addBehavior("#default#userData");
    a.getItem = function(a) {
        d.load(a);
        return d.getAttribute(a)
    };
    a.setItem = function(a, b) {
        d.setAttribute(a, b);
        d.save(a);
        e(a)
    };
    a.removeItem = function(a) {
        d.removeAttribute(a);
        d.save(a);
        e(a, 1)
    };
    a.clear = function() {
        d.load(b);
        var a = d.getAttribute(b).split(","),
        c = a.length;
        if (a[0] === "") return;
        for (var e = 0; e < c; e++) {
            d.removeAttribute(a[e]);
            d.save(a[e])
        }
        d.setAttribute(b, "");
        d.save(b)
    }
} ();
define("localStorage/localStorage",
function() {});
define("app/common/views/dialog_verify", ["require", "exports", "module", "jquery", "art-dialog/dialog"],
function(a, b, c) {
    var d = a("jquery");
    var e = a("art-dialog/dialog");
    var f = "550011611";
    var g = Math.random();
    var h = "";
    var i = "2052";
    var j = 1;
    var k = 2;
    var l;
    var m = false;
    var n = false;
    var o;
    var p = function(a) {
        if (a.ret === 0) {
            if (n === false) {
                var b = capGetTicket();
                console.log("用户验证成功", a);
                o.close();
                o.onSuccess(b.ticket, b.randstr)
            }
            n = true
        } else {
            alert("验证失败")
        }
    };
    var q = function(a, b) {
        if (o) {
            return o
        }
        o = this;
        o.isMobile = k === 1;
        o._def = o.getScript(a, b);
        if (!o.isMobile) {
            o._dialog = e({
                title: "请完成验证",
                content: '<div id="dialog_verify_container"></div>',
                skin: "dialog_verify",
                cancel: function() {
                    return false
                },
                cancelDisplay: false,
                onshow: function() {
                    if (!m) {
                        capInit(document.getElementById("dialog_verify_container"), {
                            type: "embed",
                            callback: p
                        });
                        m = true
                    } else {
                        capRefresh()
                    }
                }
            })
        }
        return o
    };
    q.prototype.showModal = function() {
        var a = this;
        a._def.then(function() {
            if (!a.isMobile) {
                a._dialog.showModal()
            } else {
                capInit(document.getElementById("dialog_verify_container"), {
                    showHeader: false,
                    callback: p
                })
            }
        })
    };
    q.prototype.close = function() {
        if (this.isMobile) {
            return
        }
        this._dialog.close()
    };
    q.prototype.onSuccess = function(a, b) {
        console.log(a, b)
    };
    q.prototype.getScript = function(a, b) {
        var c = "https://ssl.captcha.qq.com/template/TCapIframeApi.js?aid=" + f + "&rand=" + g + "&clientype=" + k + "&uin=" + h + "&lang=" + i + "&apptype=" + j;
        if (a && b) {
            c += "&captype=" + a + "&disturblevel=" + b
        }
        return d.getScript(c)
    };
    c.exports = function(a) {
        k = a ? 1 : 2;
        return q
    }
}); !
function() {
    function a(a, b) {
        return (/string|function/.test(typeof b) ? h: g)(a, b)
    }
    function b(a, c) {
        return "string" != typeof a && (c = typeof a, "number" === c ? a += "": a = "function" === c ? b(a.call(a)) : ""),
        a
    }
    function c(a) {
        return l[a]
    }
    function d(a) {
        return b(a).replace(/&(?![\w#]+;)|[<>"']/g, c)
    }
    function e(a, b) {
        if (m(a)) for (var c = 0,
        d = a.length; d > c; c++) b.call(a, a[c], c, a);
        else for (c in a) b.call(a, a[c], c)
    }
    function f(a, b) {
        var c = /(\/)[^\/]+\1\.\.\1/,
        d = ("./" + a).replace(/[^\/]+$/, ""),
        e = d + b;
        for (e = e.replace(/\/\.\//g, "/"); e.match(c);) e = e.replace(c, "/");
        return e
    }
    function g(b, c) {
        var d = a.get(b) || i({
            filename: b,
            name: "Render Error",
            message: "Template not found"
        });
        return c ? d(c) : d
    }
    function h(a, b) {
        if ("string" == typeof b) {
            var c = b;
            b = function() {
                return new k(c)
            }
        }
        var d = j[a] = function(c) {
            try {
                return new b(c, a) + ""
            } catch(d) {
                return i(d)()
            }
        };
        return d.prototype = b.prototype = n,
        d.toString = function() {
            return b + ""
        },
        d
    }
    function i(a) {
        var b = "{Template Error}",
        c = a.stack || "";
        if (c) c = c.split("\n").slice(0, 2).join("\n");
        else for (var d in a) c += "<" + d + ">\n" + a[d] + "\n\n";
        return function() {
            return "object" == typeof console && console.error(b + "\n\n" + c),
            b
        }
    }
    var j = a.cache = {},
    k = this.String,
    l = {
        "<": "&#60;",
        ">": "&#62;",
        '"': "&#34;",
        "'": "&#39;",
        "&": "&#38;"
    },
    m = Array.isArray ||
    function(a) {
        return "[object Array]" === {}.toString.call(a)
    },
    n = a.utils = {
        $helpers: {},
        $include: function(a, b, c) {
            return a = f(c, a),
            g(a, b)
        },
        $string: b,
        $escape: d,
        $each: e
    },
    o = a.helpers = n.$helpers;
    a.get = function(a) {
        return j[a.replace(/^\.\//, "")]
    },
    a.helper = function(a, b) {
        o[a] = b
    },
    define("amd-templates/template", [],
    function() {
        return a
    }),
    a.helper("number2array",
    function(a, b) {
        var c, d = [];
        if (b) for (c = 0; a > c; c++) d.push(a - c);
        else for (c = 0; a > c; c++) d.push(c + 1);
        return d
    })
} ();
define("amd-templates/survey/common/reward_bar", ["../../template"],
function(a) {
    return a("survey/common/reward_bar", ' <div class="reward_bar"> <div class="inner"> <i class="gift"></i> 此问卷完成后可参与抽奖，奖品由问卷发布者提供和发放。 <a href="javascript:;" class="btn_reward">抽奖详情</a> <i class="arrow"></i> </div> </div> ')
});
define("amd-templates/survey/pc/reward_result", ["../../template"],
function(a) {
    return a("survey/pc/reward_result",
    function(a) {
        "use strict";
        var b = this,
        c = (b.$helpers, a.prize),
        d = b.$escape,
        e = a.reward,
        f = "";
        return f += " ",
        c ? (f += ' <div class="reward_result reward_result_win"> <div class="row row_single"> <div class="ico_prize_win"></div> <div class="reward_title">恭喜中奖</div> <p>您抽中 <span class="highlight">', f += d(c.name), f += '</span><span class="highlight">（价值', f += d(c.value), f += '）</span></p> <p>请尽快提交您的个人联系方式，以便发放奖品，<span class="reward_warning">不提交将视为自动放弃本次抽奖</span></p> </div> <div class="row row_name"> <div class="title"><label for="reward_name">领奖姓名</label></div> <div class="content"><input type="text" name="name" id="reward_name"/></div> </div> <div class="row row_contact"> <div class="title"><label for="reward_tel">联系方式</label></div> <div class="content"><input type="text" name="tel" id="reward_tel"/></div> </div> <div class="row row_info"> <div class="title">发奖信息</div> <div class="content"> <p>', f += d(e.name), f += " ", f += d(e.contact), f += "</p> <p>", f += d(e.deliverMethod), f += "</p> <p>腾讯问卷建议您记录发布者信息以便联系</p> </div> </div> </div> ") : f += ' <div class="reward_result reward_result_lose"> <div class="row row_single"> <div class="ico_prize_lose"></div> <div class="reward_title">很遗憾没有抽中，谢谢参与</div> </div> </div> ',
        f += " ",
        new String(f)
    })
});
define("amd-templates/survey/mobile/reward_result", ["../../template"],
function(a) {
    return a("survey/mobile/reward_result",
    function(a) {
        "use strict";
        var b = this,
        c = (b.$helpers, a.prize),
        d = b.$escape,
        e = a.reward,
        f = "";
        return f += " ",
        c ? (f += ' <div class="reward_result reward_result_win"> <div class="row row_single"> <div class="ico_prize_win"></div> <div class="reward_title">恭喜中奖</div> <p>您抽中 <span class="highlight">', f += d(c.name), f += '</span><span class="highlight">（价值', f += d(c.value), f += '）</span></p> <p class="reward_tips">请尽快提交您的个人联系方式，以便发放奖品，<span class="reward_warning">不提交将视为自动放弃本次抽奖</span></p> </div> <div class="row row_form"> <div class="content"> <input type="text" name="name" id="reward_name" placeholder="姓名"/> <input type="text" name="tel" id="reward_tel" placeholder="联系方式"/> </div> <div class="content"> <a href="javascript:;" class="btn_reward_contact">提交</a> </div> </div> <div class="row row_info"> <div class="title">发奖信息</div> <div class="content"> <p>', f += d(e.name), f += " ", f += d(e.contact), f += "</p> <p>", f += d(e.deliverMethod), f += "</p> <p>腾讯问卷建议您记录发布者信息以便联系</p> </div> </div> </div> ") : f += ' <div class="reward_result reward_result_lose"> <div class="row row_single"> <div class="ico_prize_lose"></div> <div class="reward_title">很遗憾没有抽中，谢谢参与</div> </div> <div class="row row_control"> <a href="javascript:;" class="btn_reward_close">确认</a> </div> </div> ',
        f += " ",
        new String(f)
    })
});
define("amd-templates/survey/pc/reward_detail", ["../../template"],
function(a) {
    return a("survey/pc/reward_detail",
    function(a) {
        "use strict";
        var b = this,
        c = (b.$helpers, b.$each),
        d = a.list,
        e = (a.$value, a.$index, b.$escape),
        f = a.deliverMethod,
        g = "";
        return g += ' <div class="reward_detail"> <div class="row gift_list"> <div class="title">奖品详情</div> <div class="content"> <ul> ',
        c(d,
        function(a) {
            g += ' <li><span class="name">',
            g += e(a.name),
            g += '</span> <span class="value">价值',
            g += e(a.value),
            g += "</span></li> "
        }),
        g += ' </ul> </div> </div> <div class="row"> <div class="title">发奖方式</div> <div class="content">',
        g += e(f),
        g += "</div> </div> </div> ",
        new String(g)
    })
});
define("app/survey/common/views/reward", ["require", "exports", "module", "jquery", "underscore", "app/common/api/reward", "bind_events", "art-dialog/dialog", "localStorage/localStorage", "app/common/views/dialog_verify", "amd-templates/survey/common/reward_bar", "amd-templates/survey/pc/reward_result", "amd-templates/survey/mobile/reward_result", "amd-templates/survey/pc/reward_detail"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("underscore");
    var f = a("app/common/api/reward");
    var g = a("bind_events");
    var h = a("art-dialog/dialog");
    a("localStorage/localStorage");
    var i = window.innerWidth < 800;
    var j = "g_dialog_v2 dialog_reward";
    var k = 460;
    if (i) {
        j += " " + "dialog_reward_mobile";
        k = null
    }
    var l = a("app/common/views/dialog_verify")(i);
    var m;
    var n;
    var o = a("amd-templates/survey/common/reward_bar")();
    var p = a("amd-templates/survey/pc/reward_result");
    var q = a("amd-templates/survey/mobile/reward_result");
    var r = function(a) {
        this.surveyModel = a.surveyModel;
        var b = this.surveyModel.get("isRewarding");
        m = this.surveyModel.get("survey.reward");
        this.$el = d(".mod_reward_bar");
        if (!b) {
            f.useGiftLibrary(this.surveyModel.get("survey.id")).done(function(a) {
                m.exec("setUseGiftLibrary", a.data)
            })
        }
        this.init()
    };
    e.extend(r.prototype, {
        init: function(a) {
            this.$el.html(o);
            this.initDetailDialog();
            this.bindEvent();
            var b = "reward-" + this.surveyModel.get("survey.id");
            this.hasReward = !!window.localStorage.getItem(b)
        },
        bindEvent: function() {
            g.bindUIEvents(this, this.$el, {
                "click .btn_reward": "showDetailHandler"
            })
        },
        initDetailDialog: function() {
            var b = function() {
                this.close();
                return false
            };
            var c = i ? b: false;
            this.detailDialog = h({
                title: "抽奖详情",
                skin: j,
                width: k,
                content: a("amd-templates/survey/pc/reward_detail")(m),
                button: [{
                    value: "知道了",
                    autofocus: true,
                    callback: function() {
                        this.close();
                        return false
                    }
                }],
                cancel: c,
                onclose: function() {
                    d(document.body).css("overflow", "")
                },
                onshow: t
            })
        },
        showDetailHandler: function() {
            this.detailDialog.showModal()
        },
        showRewardBtn: function(a) {
            var b = this.surveyModel;
            var c = b.get("survey.id");
            var e = "reward-" + c;
            var g = d('<a href="javascript:void(0);" class="survey_btn survey_reward">点击抽奖</a>');
            if (!this.hasReward) {
                d(".survey_control .inner").append(g)
            } else {
                d(".survey_control .inner").append("<p>您已参与抽奖</p>")
            }
            g.on("click",
            function() {
                if (d(this).attr("disabled") === "disabled") {
                    return false
                }
                var b = new l;
                b.onSuccess = function(d, h) {
                    f.startReward(c, d, h).done(s).always(function() {
                        a && a();
                        window.localStorage.setItem(e, "hasReward");
                        g.hide()
                    });
                    b.close()
                };
                b.showModal()
            })
        }
    });
    var s = function(a) {
        n = a.data;
        if (n) {
            if (i) {
                v(a.data)
            } else {
                h({
                    skin: j,
                    content: p({
                        reward: m,
                        prize: a.data
                    }),
                    ok: function() {
                        return u()
                    },
                    okValue: "提交",
                    onclose: function() {
                        d(document.body).css("overflow", "")
                    },
                    cancel: false
                }).showModal()
            }
        } else {
            if (i) {
                v()
            } else {
                h({
                    skin: j,
                    width: k,
                    content: p({
                        reward: null,
                        prize: null
                    }),
                    cancel: false,
                    button: [{
                        value: "确认",
                        autofocus: true
                    }],
                    onclose: function() {
                        d(document.body).css("overflow", "")
                    }
                }).showModal()
            }
        }
    };
    var t = function() {
        if (!i) {
            return
        }
        var a = d(this.node);
        a.css({
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
        });
        d(document.body).scrollTop(0);
        d(document.body).css("overflow", "hidden")
    };
    var u = function() {
        var a = d("#reward_name").val();
        var b = d("#reward_tel").val();
        var c = n.code;
        var e = [];
        if (a && b) {
            f.contactReward(b, a, c);
            return true
        } else {
            if (a === "") {
                e.push("#reward_name")
            }
            if (b === "") {
                e.push("#reward_tel")
            }
            var g = e.join(",");
            d(g).css("border-color", "red");
            return false
        }
    };
    var v = function(a) {
        var b = d('<div class="dialog_reward"></div>');
        var c = d(q({
            reward: m,
            prize: a
        }));
        var e = d("#container");
        b.html(c);
        var f = function() {
            c.css("display", "none");
            e.css("display", "block")
        };
        b.on("click", ".btn_reward_contact",
        function(a) {
            if (u()) {
                f()
            }
        });
        b.on("click", ".btn_reward_close", f);
        d("body").append(b);
        e.css("display", "none")
    };
    c.exports = r
});
define("app/survey/common/views/survey_title", ["require", "exports", "module", "jquery", "underscore"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("underscore");
    var f = function(a) {
        this.$el = d(".survey_title");
        this.$titleContent = this.$el.find(".title_content");
        this.surveyModel = a.surveyModel;
        this.render()
    };
    e.extend(f.prototype, {
        render: function() {
            var a = this.surveyModel.get("survey.title");
            this.$titleContent.html(a)
        },
        show: function() {
            this.$el.css("display", "block")
        },
        hide: function() {
            this.$el.css("display", "none")
        }
    });
    c.exports = f
});
define("app/survey/common/views/survey_prefix", ["require", "exports", "module", "jquery", "underscore"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("underscore");
    var f = function(a) {
        this.$el = d(".survey_prefix");
        this.$prefixContent = this.$el.find(".prefix_content");
        this.surveyModel = a.surveyModel;
        this.render()
    };
    e.extend(f.prototype, {
        render: function() {
            var a = this.surveyModel.get("survey.prefix");
            this.$prefixContent.html(a)
        },
        show: function() {
            this.$el.css("display", "block")
        },
        hide: function() {
            this.$el.css("display", "none")
        }
    });
    c.exports = f
});
define("app/survey/common/views/survey_suffix", ["require", "exports", "module", "jquery", "underscore"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("underscore");
    var f = function(a) {
        this.$el = d(".survey_suffix");
        this.$suffixContent = this.$el.find(".suffix_content");
        this.surveyModel = a.surveyModel;
        this.render()
    };
    e.extend(f.prototype, {
        render: function() {
            var a = this.surveyModel.get("survey.suffix");
            this.$suffixContent.html(a)
        },
        show: function() {
            this.$el.css("display", "block")
        },
        hide: function() {
            this.$el.css("display", "none")
        }
    });
    c.exports = f
});
define("app/common/views/footer", ["require", "exports", "module", "jquery"],
function(a, b, c) {
    var d = a("jquery");
    return {
        render: function(a, b) {
            var c = (new Date).getFullYear();
            d("#footer").find(".copyright_end").text(c)
        }
    }
});
define("app/survey/common/model/report_page", ["require", "exports", "module", "jquery", "app/util/mo_push"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("app/util/mo_push");
    var f = d(document).width() < 800;
    var g = new e(4101);
    function h(a) {
        var b = {};
        b["p" + a] = 1;
        b["platform"] = f ? "mobile": "pc";
        return b
    }
    function i(a) {
        if (a < 0 || a > 4) {
            return false
        }
        g.reportData = h(a);
        g.send()
    }
    c.exports = i
});
define("amd-templates/survey/mobile/survey_progress", ["../../template"],
function(a) {
    return a("survey/mobile/survey_progress", '<div role="progressbar">  <div class="g_progress_placeholder"></div> <div class="g_progress">  <div class="progress_container"> <div class="progress_bar"> <div class="bar_inner" style="width: 0%;"></div> </div> </div>  <div class="progress_page"> <a href="javascript:;" class="btn btn_prev"><i></i></a> <a href="javascript:;" class="btn btn_next"><i></i></a> </div>  <div class="progress_finish"> <a href="javascript:;" class="btn btn_submit">提交</a> </div> </div> </div> ')
});
define("app/survey/common/views/survey_progress", ["require", "exports", "module", "jquery", "underscore", "bind_events", "amd-templates/survey/mobile/survey_progress"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("underscore");
    var f = a("bind_events");
    var g = function(b) {
        this.surveyView = b.surveyView;
        this.surveyModel = b.surveyModel;
        this.$el = d(a("amd-templates/survey/mobile/survey_progress")());
        this.init()
    };
    e.extend(g.prototype, {
        isPreview: true,
        init: function() {
            this.$bar = this.$el.find(".progress_bar .bar_inner");
            this.isPreview = this.surveyModel.get("survey.prev");
            this.isNext = this.surveyModel.get("survey.pages").length > 1;
            this._render();
            this._bindEvents()
        },
        _bindEvents: function() {
            d(".btn_prev, .btn_next").on("click",
            function(a) {
                var b = d(a.target);
                if (b.attr("disabled") === "disabled") {
                    return false
                }
            })
        },
        _pageClickHandler: function(a) {
            var b = d(a.target);
            if (b.attr("disabled") === "disabled") {
                return false
            }
        },
        _render: function() {
            d(document.body).append(this.$el)
        },
        update: function(a) {
            this.$bar.css({
                width: a + "%"
            })
        },
        hide: function() {
            this.$el.hide()
        }
    });
    c.exports = g
});
define("support", ["require", "exports", "module"],
function(a, b, c) {
    c.exports = {
        css: function(a) {
            var b = document.createElement("div");
            var c = "webkit ms".split(" ");
            var d = c.length;
            if (a in b.style) {
                return true
            }
            a = a.replace(/^[a-z]/,
            function(a) {
                return a.toUpperCase()
            });
            while (d--) {
                if (c[d] + a in b.style) {
                    return true
                }
            }
            return false
        }
    }
});
define("app/common/views/survey_style", ["require", "exports", "module", "jquery", "underscore", "support"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("underscore");
    var f = a("support");
    c.exports = function(a) {
        var b = a === "mobile" ? true: false;
        var c = function(a) {
            var b = a.theme;
            var c = d("#theme_css");
            if (b === "default") {
                c.remove();
                return
            }
            var e = "/themes/" + b + "/theme.css";
            if (c.length !== 0) {
                c.attr("href", e)
            } else {
                c = d('<link id="theme_css" rel="stylesheet" type="text/css" href="' + e + '"/>');
                d("head").append(c)
            }
        };
        var g = e.throttle(function(a) {
            var c = d(".survey_background_wrap");
            if (c.length === 0) {
                c = d('<div class="survey_background_wrap"></div>');
                d(".g_container").prepend(c)
            }
            var e = d(".survey_wrap");
            var g = d(".g_footer");
            var i = false;
            var j = function(a) {
                var c;
                if (a && a.theme && a.theme !== "default") {
                    c = b ? "/themes/" + a.theme + "/assets/background_mobile.jpg": "/themes/" + a.theme + "/assets/background.jpg"
                }
                if (a && a.custom && a.custom.backgroundImage) {
                    i = true;
                    c = "/" + a.custom.backgroundImage;
                    c = location.protocol + "//" + "cdn.ur.qq.com/" + c
                }
                return c
            };
            var k = j(a);
            if (!k) {
                c.css({
                    backgroundImage: "",
                    backgroundSize: "",
                    backgroundAttachment: ""
                });
                c.children().remove();
                e.css({
                    backgroundColor: ""
                });
                g.css({
                    backgroundColor: ""
                });
                g.removeClass("has_background");
                return
            }
            if (f.css("backgroundSize")) {
                c.css({
                    backgroundImage: 'url("' + k + '")',
                    backgroundSize: "cover",
                    backgroundPosition: "center center"
                })
            } else {
                var l = d('<img src="' + k + '"/>');
                c.html(l);
                l.on("load", h)
            }
            c.css({
                height: d(window).height()
            });
            if (i) {
                e.css({
                    backgroundColor: "rgba(255, 255, 255, 0.9)"
                })
            }
            g.css({
                backgroundColor: "transparent"
            });
            g.addClass("has_background")
        },
        200);
        var h = function() {
            var a = d(this);
            var b = d(window).width() / d(window).height();
            var c = a.width() / a.height();
            if (b < c) {
                a.css({
                    height: "100%",
                    width: "auto"
                })
            } else {
                a.css({
                    width: "100%",
                    height: "auto"
                })
            }
        };
        if (!b) {
            d(window).on("resize",
            function() {
                var a = d(".survey_background_wrap");
                a.css({
                    height: d(window).height()
                })
            })
        }
        return {
            setStyle: function(a) {
                c(a);
                g(a)
            },
            render: function(a) {
                if (!a) {
                    var b = d(".survey_content").css("margin-top");
                    var c = d(window).height();
                    if (parseInt(b) > c) {
                        d(".survey_content").css("margin-top", "20%")
                    }
                    d(".survey_prevpage, .survey_nextpage").click(function() {
                        setTimeout(function() {
                            if (d(".survey_title").is(":visible")) {
                                d(".survey_content").css("margin-top", b)
                            } else {
                                d(".survey_content").css("margin-top", 0)
                            }
                        },
                        20)
                    });
                    d(".survey_submit").click(function() {
                        d(".survey_content").css("margin-top", 0)
                    })
                } else {
                    this.setStyle(a)
                }
            }
        }
    }
});
define("app/common/views/table_freeze", ["require", "exports", "module", "jquery"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e;
    var f;
    var g;
    var h;
    var i = function(a) {
        f = a;
        if (a.hasClass("table_freezed")) {
            return
        }
        e = f.parent().parent();
        if (a.width() <= e.width()) {
            return
        }
        e.css({
            position: "relative"
        });
        g = f.position();
        j();
        k();
        f.addClass("table_freezed")
    };
    var j = function() {
        var a = [];
        var b = [];
        f.find("tr").each(function(c, e) {
            var f = d(e).find("th, td").first();
            a.push(f.height());
            b.push(f.clone())
        });
        h = d('<table class="matrix_group_mirror"><thead></thead><tbody></tbody></table>');
        d.each(b,
        function(b, c) {
            var e = d('<tr class="matrix_item"></tr>');
            e.html(c);
            if (b === 0) {
                c.css({
                    height: a[b] + "px"
                });
                h.find("thead").append(e)
            } else {
                c.css({
                    height: a[b] + 1 + "px"
                });
                if (!a[b]) {
                    e.hide()
                }
                h.find("tbody").append(e)
            }
        })
    };
    var k = function() {
        var a = f.find("td:visible").outerWidth();
        h.css({
            position: "absolute",
            left: g.left + f.parent() + "px",
            top: g.top + "px",
            width: a + "px",
            minWidth: "auto"
        });
        var b = f.data("mirror");
        if (b) {
            b.remove()
        }
        e.append(h);
        f.data("mirror", h);
        f.parent().css({
            position: "relative",
            marginLeft: a + "px"
        });
        f.css({
            marginLeft: "-" + a + "px"
        })
    };
    c.exports = i
}); (function(a) {
    if (typeof define === "function" && define.amd) {
        define("rangeSlider", ["jquery"],
        function(b) {
            a(b, document, window, navigator)
        })
    } else {
        a(jQuery, document, window, navigator)
    }
})(function(a, b, c, d, e) {
    "use strict";
    var f = 0;
    var g = function() {
        var b = d.userAgent,
        c = /msie\s\d+/i,
        e;
        if (b.search(c) > 0) {
            e = c.exec(b).toString();
            e = e.split(" ")[1];
            if (e < 9) {
                a("html").addClass("lt-ie9");
                return true
            }
        }
        return false
    } ();
    if (!Function.prototype.bind) {
        Function.prototype.bind = function m(a) {
            var b = this;
            var c = [].slice;
            if (typeof b != "function") {
                throw new TypeError
            }
            var d = c.call(arguments, 1),
            e = function() {
                if (this instanceof e) {
                    var f = function() {};
                    f.prototype = b.prototype;
                    var g = new f;
                    var h = b.apply(g, d.concat(c.call(arguments)));
                    if (Object(h) === h) {
                        return h
                    }
                    return g
                } else {
                    return b.apply(a, d.concat(c.call(arguments)))
                }
            };
            return e
        }
    }
    if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function(a, b) {
            var c;
            if (this == null) {
                throw new TypeError('"this" is null or not defined')
            }
            var d = Object(this);
            var e = d.length >>> 0;
            if (e === 0) {
                return - 1
            }
            var f = +b || 0;
            if (Math.abs(f) === Infinity) {
                f = 0
            }
            if (f >= e) {
                return - 1
            }
            c = Math.max(f >= 0 ? f: e - Math.abs(f), 0);
            while (c < e) {
                if (c in d && d[c] === a) {
                    return c
                }
                c++
            }
            return - 1
        }
    }
    var h = '<span class="irs">' + '<span class="irs-line" tabindex="-1"><span class="irs-line-left"></span><span class="irs-line-mid"></span><span class="irs-line-right"></span></span>' + '<span class="irs-min">0</span><span class="irs-max">1</span>' + '<span class="irs-from">0</span><span class="irs-to">0</span><span class="irs-single" role="presentation">0</span>' + "</span>" + '<span class="irs-grid"></span>' + '<span class="irs-bar"></span>';
    var i = '<span class="irs-bar-edge"></span>' + '<span class="irs-shadow shadow-single"></span>' + '<span class="irs-slider single"></span>';
    var j = '<span class="irs-shadow shadow-from"></span>' + '<span class="irs-shadow shadow-to"></span>' + '<span class="irs-slider from"></span>' + '<span class="irs-slider to"></span>';
    var k = '<span class="irs-disable-mask"></span>';
    var l = function(d, e, f) {
        this.VERSION = "2.1.4";
        this.input = d;
        this.plugin_count = f;
        this.current_plugin = 0;
        this.calc_count = 0;
        this.update_tm = 0;
        this.old_from = 0;
        this.old_to = 0;
        this.old_min_interval = null;
        this.raf_id = null;
        this.dragging = false;
        this.force_redraw = false;
        this.no_diapason = false;
        this.is_key = false;
        this.is_update = false;
        this.is_start = true;
        this.is_finish = false;
        this.is_active = false;
        this.is_resize = false;
        this.is_click = false;
        this.$cache = {
            win: a(c),
            body: a(b.body),
            input: a(d),
            cont: null,
            rs: null,
            min: null,
            max: null,
            from: null,
            to: null,
            single: null,
            bar: null,
            line: null,
            s_single: null,
            s_from: null,
            s_to: null,
            shad_single: null,
            shad_from: null,
            shad_to: null,
            edge: null,
            grid: null,
            grid_labels: []
        };
        this.coords = {
            x_gap: 0,
            x_pointer: 0,
            w_rs: 0,
            w_rs_old: 0,
            w_handle: 0,
            p_gap: 0,
            p_gap_left: 0,
            p_gap_right: 0,
            p_step: 0,
            p_pointer: 0,
            p_handle: 0,
            p_single_fake: 0,
            p_single_real: 0,
            p_from_fake: 0,
            p_from_real: 0,
            p_to_fake: 0,
            p_to_real: 0,
            p_bar_x: 0,
            p_bar_w: 0,
            grid_gap: 0,
            big_num: 0,
            big: [],
            big_w: [],
            big_p: [],
            big_x: []
        };
        this.labels = {
            w_min: 0,
            w_max: 0,
            w_from: 0,
            w_to: 0,
            w_single: 0,
            p_min: 0,
            p_max: 0,
            p_from_fake: 0,
            p_from_left: 0,
            p_to_fake: 0,
            p_to_left: 0,
            p_single_fake: 0,
            p_single_left: 0
        };
        var g = this.$cache.input,
        h = g.prop("value"),
        i,
        j,
        k;
        i = {
            type: "single",
            min: 10,
            max: 100,
            from: null,
            to: null,
            step: 1,
            min_interval: 0,
            max_interval: 0,
            drag_interval: false,
            values: [],
            p_values: [],
            from_fixed: false,
            from_min: null,
            from_max: null,
            from_shadow: false,
            to_fixed: false,
            to_min: null,
            to_max: null,
            to_shadow: false,
            prettify_enabled: true,
            prettify_separator: " ",
            prettify: null,
            force_edges: false,
            keyboard: false,
            keyboard_step: 5,
            grid: false,
            grid_margin: true,
            grid_num: 4,
            grid_snap: false,
            hide_min_max: false,
            hide_from_to: false,
            prefix: "",
            postfix: "",
            max_postfix: "",
            decorate_both: true,
            values_separator: " — ",
            input_values_separator: ";",
            disable: false,
            onStart: null,
            onChange: null,
            onFinish: null,
            onUpdate: null
        };
        j = {
            type: g.data("type"),
            min: g.data("min"),
            max: g.data("max"),
            from: g.data("from"),
            to: g.data("to"),
            step: g.data("step"),
            min_interval: g.data("minInterval"),
            max_interval: g.data("maxInterval"),
            drag_interval: g.data("dragInterval"),
            values: g.data("values"),
            from_fixed: g.data("fromFixed"),
            from_min: g.data("fromMin"),
            from_max: g.data("fromMax"),
            from_shadow: g.data("fromShadow"),
            to_fixed: g.data("toFixed"),
            to_min: g.data("toMin"),
            to_max: g.data("toMax"),
            to_shadow: g.data("toShadow"),
            prettify_enabled: g.data("prettifyEnabled"),
            prettify_separator: g.data("prettifySeparator"),
            force_edges: g.data("forceEdges"),
            keyboard: g.data("keyboard"),
            keyboard_step: g.data("keyboardStep"),
            grid: g.data("grid"),
            grid_margin: g.data("gridMargin"),
            grid_num: g.data("gridNum"),
            grid_snap: g.data("gridSnap"),
            hide_min_max: g.data("hideMinMax"),
            hide_from_to: g.data("hideFromTo"),
            prefix: g.data("prefix"),
            postfix: g.data("postfix"),
            max_postfix: g.data("maxPostfix"),
            decorate_both: g.data("decorateBoth"),
            values_separator: g.data("valuesSeparator"),
            input_values_separator: g.data("inputValuesSeparator"),
            disable: g.data("disable")
        };
        j.values = j.values && j.values.split(",");
        for (k in j) {
            if (j.hasOwnProperty(k)) {
                if (!j[k] && j[k] !== 0) {
                    delete j[k]
                }
            }
        }
        if (h) {
            h = h.split(j.input_values_separator || e.input_values_separator || ";");
            if (h[0] && h[0] == +h[0]) {
                h[0] = +h[0]
            }
            if (h[1] && h[1] == +h[1]) {
                h[1] = +h[1]
            }
            if (e && e.values && e.values.length) {
                i.from = h[0] && e.values.indexOf(h[0]);
                i.to = h[1] && e.values.indexOf(h[1])
            } else {
                i.from = h[0] && +h[0];
                i.to = h[1] && +h[1]
            }
        }
        a.extend(i, e);
        a.extend(i, j);
        this.options = i;
        this.validate();
        this.result = {
            input: this.$cache.input,
            slider: null,
            min: this.options.min,
            max: this.options.max,
            from: this.options.from,
            from_percent: 0,
            from_value: null,
            to: this.options.to,
            to_percent: 0,
            to_value: null
        };
        this.init()
    };
    l.prototype = {
        init: function(a) {
            this.no_diapason = false;
            this.coords.p_step = this.convertToPercent(this.options.step, true);
            this.target = "base";
            this.toggleInput();
            this.append();
            this.setMinMax();
            if (a) {
                this.force_redraw = true;
                this.calc(true);
                this.callOnUpdate()
            } else {
                this.force_redraw = true;
                this.calc(true);
                this.callOnStart()
            }
            this.updateScene()
        },
        append: function() {
            var a = '<span class="irs js-irs-' + this.plugin_count + '"></span>';
            this.$cache.input.before(a);
            this.$cache.input.prop("readonly", true);
            this.$cache.cont = this.$cache.input.prev();
            this.result.slider = this.$cache.cont;
            this.$cache.cont.html(h);
            this.$cache.rs = this.$cache.cont.find(".irs");
            this.$cache.min = this.$cache.cont.find(".irs-min");
            this.$cache.max = this.$cache.cont.find(".irs-max");
            this.$cache.from = this.$cache.cont.find(".irs-from");
            this.$cache.to = this.$cache.cont.find(".irs-to");
            this.$cache.single = this.$cache.cont.find(".irs-single");
            this.$cache.bar = this.$cache.cont.find(".irs-bar");
            this.$cache.line = this.$cache.cont.find(".irs-line");
            this.$cache.grid = this.$cache.cont.find(".irs-grid");
            if (this.options.type === "single") {
                this.$cache.cont.append(i);
                this.$cache.edge = this.$cache.cont.find(".irs-bar-edge");
                this.$cache.s_single = this.$cache.cont.find(".single");
                this.$cache.from[0].style.visibility = "hidden";
                this.$cache.to[0].style.visibility = "hidden";
                this.$cache.shad_single = this.$cache.cont.find(".shadow-single")
            } else {
                this.$cache.cont.append(j);
                this.$cache.s_from = this.$cache.cont.find(".from");
                this.$cache.s_to = this.$cache.cont.find(".to");
                this.$cache.shad_from = this.$cache.cont.find(".shadow-from");
                this.$cache.shad_to = this.$cache.cont.find(".shadow-to");
                this.setTopHandler()
            }
            if (this.options.hide_from_to) {
                this.$cache.from[0].style.display = "none";
                this.$cache.to[0].style.display = "none";
                this.$cache.single[0].style.display = "none"
            }
            this.appendGrid();
            if (this.options.disable) {
                this.appendDisableMask();
                this.$cache.input[0].disabled = true
            } else {
                this.$cache.cont.removeClass("irs-disabled");
                this.$cache.input[0].disabled = false;
                this.bindEvents()
            }
            if (this.options.drag_interval) {
                this.$cache.bar[0].style.cursor = "ew-resize"
            }
        },
        setTopHandler: function() {
            var a = this.options.min,
            b = this.options.max,
            c = this.options.from,
            d = this.options.to;
            if (c > a && d === b) {
                this.$cache.s_from.addClass("type_last")
            } else if (d < b) {
                this.$cache.s_to.addClass("type_last")
            }
        },
        changeLevel: function(a) {
            switch (a) {
            case "single":
                this.coords.p_gap = this.toFixed(this.coords.p_pointer - this.coords.p_single_fake);
                break;
            case "from":
                this.coords.p_gap = this.toFixed(this.coords.p_pointer - this.coords.p_from_fake);
                this.$cache.s_from.addClass("state_hover");
                this.$cache.s_from.addClass("type_last");
                this.$cache.s_to.removeClass("type_last");
                break;
            case "to":
                this.coords.p_gap = this.toFixed(this.coords.p_pointer - this.coords.p_to_fake);
                this.$cache.s_to.addClass("state_hover");
                this.$cache.s_to.addClass("type_last");
                this.$cache.s_from.removeClass("type_last");
                break;
            case "both":
                this.coords.p_gap_left = this.toFixed(this.coords.p_pointer - this.coords.p_from_fake);
                this.coords.p_gap_right = this.toFixed(this.coords.p_to_fake - this.coords.p_pointer);
                this.$cache.s_to.removeClass("type_last");
                this.$cache.s_from.removeClass("type_last");
                break
            }
        },
        appendDisableMask: function() {
            this.$cache.cont.append(k);
            this.$cache.cont.addClass("irs-disabled")
        },
        remove: function() {
            this.$cache.cont.remove();
            this.$cache.cont = null;
            this.$cache.line.off("keydown.irs_" + this.plugin_count);
            this.$cache.body.off("touchmove.irs_" + this.plugin_count);
            this.$cache.body.off("mousemove.irs_" + this.plugin_count);
            this.$cache.win.off("touchend.irs_" + this.plugin_count);
            this.$cache.win.off("mouseup.irs_" + this.plugin_count);
            if (g) {
                this.$cache.body.off("mouseup.irs_" + this.plugin_count);
                this.$cache.body.off("mouseleave.irs_" + this.plugin_count)
            }
            this.$cache.grid_labels = [];
            this.coords.big = [];
            this.coords.big_w = [];
            this.coords.big_p = [];
            this.coords.big_x = [];
            cancelAnimationFrame(this.raf_id)
        },
        bindEvents: function() {
            if (this.no_diapason) {
                return
            }
            this.$cache.body.on("touchmove.irs_" + this.plugin_count, this.pointerMove.bind(this));
            this.$cache.body.on("mousemove.irs_" + this.plugin_count, this.pointerMove.bind(this));
            this.$cache.win.on("touchend.irs_" + this.plugin_count, this.pointerUp.bind(this));
            this.$cache.win.on("mouseup.irs_" + this.plugin_count, this.pointerUp.bind(this));
            this.$cache.line.on("touchstart.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"));
            this.$cache.line.on("mousedown.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"));
            if (this.options.drag_interval && this.options.type === "double") {
                this.$cache.bar.on("touchstart.irs_" + this.plugin_count, this.pointerDown.bind(this, "both"));
                this.$cache.bar.on("mousedown.irs_" + this.plugin_count, this.pointerDown.bind(this, "both"))
            } else {
                this.$cache.bar.on("touchstart.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"));
                this.$cache.bar.on("mousedown.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"))
            }
            if (this.options.type === "single") {
                this.$cache.single.on("touchstart.irs_" + this.plugin_count, this.pointerDown.bind(this, "single"));
                this.$cache.s_single.on("touchstart.irs_" + this.plugin_count, this.pointerDown.bind(this, "single"));
                this.$cache.shad_single.on("touchstart.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"));
                this.$cache.single.on("mousedown.irs_" + this.plugin_count, this.pointerDown.bind(this, "single"));
                this.$cache.s_single.on("mousedown.irs_" + this.plugin_count, this.pointerDown.bind(this, "single"));
                this.$cache.edge.on("mousedown.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"));
                this.$cache.shad_single.on("mousedown.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"))
            } else {
                this.$cache.single.on("touchstart.irs_" + this.plugin_count, this.pointerDown.bind(this, null));
                this.$cache.single.on("mousedown.irs_" + this.plugin_count, this.pointerDown.bind(this, null));
                this.$cache.from.on("touchstart.irs_" + this.plugin_count, this.pointerDown.bind(this, "from"));
                this.$cache.s_from.on("touchstart.irs_" + this.plugin_count, this.pointerDown.bind(this, "from"));
                this.$cache.to.on("touchstart.irs_" + this.plugin_count, this.pointerDown.bind(this, "to"));
                this.$cache.s_to.on("touchstart.irs_" + this.plugin_count, this.pointerDown.bind(this, "to"));
                this.$cache.shad_from.on("touchstart.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"));
                this.$cache.shad_to.on("touchstart.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"));
                this.$cache.from.on("mousedown.irs_" + this.plugin_count, this.pointerDown.bind(this, "from"));
                this.$cache.s_from.on("mousedown.irs_" + this.plugin_count, this.pointerDown.bind(this, "from"));
                this.$cache.to.on("mousedown.irs_" + this.plugin_count, this.pointerDown.bind(this, "to"));
                this.$cache.s_to.on("mousedown.irs_" + this.plugin_count, this.pointerDown.bind(this, "to"));
                this.$cache.shad_from.on("mousedown.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"));
                this.$cache.shad_to.on("mousedown.irs_" + this.plugin_count, this.pointerClick.bind(this, "click"))
            }
            if (this.options.keyboard) {
                this.$cache.line.on("keydown.irs_" + this.plugin_count, this.key.bind(this, "keyboard"))
            }
            if (g) {
                this.$cache.body.on("mouseup.irs_" + this.plugin_count, this.pointerUp.bind(this));
                this.$cache.body.on("mouseleave.irs_" + this.plugin_count, this.pointerUp.bind(this))
            }
        },
        pointerMove: function(a) {
            if (!this.dragging) {
                return
            }
            var b = a.pageX || a.originalEvent.touches && a.originalEvent.touches[0].pageX;
            this.coords.x_pointer = b - this.coords.x_gap;
            this.calc()
        },
        pointerUp: function(b) {
            if (this.current_plugin !== this.plugin_count) {
                return
            }
            if (this.is_active) {
                this.is_active = false
            } else {
                return
            }
            this.$cache.cont.find(".state_hover").removeClass("state_hover");
            this.force_redraw = true;
            if (g) {
                a("*").prop("unselectable", false)
            }
            this.updateScene();
            this.restoreOriginalMinInterval();
            if (a.contains(this.$cache.cont[0], b.target) || this.dragging) {
                this.is_finish = true;
                this.callOnFinish()
            }
            this.dragging = false
        },
        pointerDown: function(b, c) {
            c.preventDefault();
            var d = c.pageX || c.originalEvent.touches && c.originalEvent.touches[0].pageX;
            if (c.button === 2) {
                return
            }
            if (b === "both") {
                this.setTempMinInterval()
            }
            if (!b) {
                b = this.target
            }
            this.current_plugin = this.plugin_count;
            this.target = b;
            this.is_active = true;
            this.dragging = true;
            this.coords.x_gap = this.$cache.rs.offset().left;
            this.coords.x_pointer = d - this.coords.x_gap;
            this.calcPointerPercent();
            this.changeLevel(b);
            if (g) {
                a("*").prop("unselectable", true)
            }
            this.$cache.line.trigger("focus");
            this.updateScene()
        },
        pointerClick: function(a, b) {
            b.preventDefault();
            var c = b.pageX || b.originalEvent.touches && b.originalEvent.touches[0].pageX;
            if (b.button === 2) {
                return
            }
            this.current_plugin = this.plugin_count;
            this.target = a;
            this.is_click = true;
            this.coords.x_gap = this.$cache.rs.offset().left;
            this.coords.x_pointer = +(c - this.coords.x_gap).toFixed();
            this.force_redraw = true;
            this.calc();
            this.$cache.line.trigger("focus")
        },
        key: function(a, b) {
            if (this.current_plugin !== this.plugin_count || b.altKey || b.ctrlKey || b.shiftKey || b.metaKey) {
                return
            }
            switch (b.which) {
            case 83:
            case 65:
            case 40:
            case 37:
                b.preventDefault();
                this.moveByKey(false);
                break;
            case 87:
            case 68:
            case 38:
            case 39:
                b.preventDefault();
                this.moveByKey(true);
                break
            }
            return true
        },
        moveByKey: function(a) {
            var b = this.coords.p_pointer;
            if (a) {
                b += this.options.keyboard_step
            } else {
                b -= this.options.keyboard_step
            }
            this.coords.x_pointer = this.toFixed(this.coords.w_rs / 100 * b);
            this.is_key = true;
            this.calc()
        },
        setMinMax: function() {
            if (!this.options) {
                return
            }
            if (this.options.hide_min_max) {
                this.$cache.min[0].style.display = "none";
                this.$cache.max[0].style.display = "none";
                return
            }
            if (this.options.values.length) {
                this.$cache.min.html(this.decorate(this.options.p_values[this.options.min]));
                this.$cache.max.html(this.decorate(this.options.p_values[this.options.max]))
            } else {
                this.$cache.min.html(this.decorate(this._prettify(this.options.min), this.options.min));
                this.$cache.max.html(this.decorate(this._prettify(this.options.max), this.options.max))
            }
            this.labels.w_min = this.$cache.min.outerWidth(false);
            this.labels.w_max = this.$cache.max.outerWidth(false)
        },
        setTempMinInterval: function() {
            var a = this.result.to - this.result.from;
            if (this.old_min_interval === null) {
                this.old_min_interval = this.options.min_interval
            }
            this.options.min_interval = a
        },
        restoreOriginalMinInterval: function() {
            if (this.old_min_interval !== null) {
                this.options.min_interval = this.old_min_interval;
                this.old_min_interval = null
            }
        },
        calc: function(a) {
            if (!this.options) {
                return
            }
            this.calc_count++;
            if (this.calc_count === 10 || a) {
                this.calc_count = 0;
                this.coords.w_rs = this.$cache.rs.outerWidth(false);
                this.calcHandlePercent()
            }
            if (!this.coords.w_rs) {
                return
            }
            this.calcPointerPercent();
            var b = this.getHandleX();
            if (this.target === "click") {
                this.coords.p_gap = this.coords.p_handle / 2;
                b = this.getHandleX();
                if (this.options.drag_interval) {
                    this.target = "both_one"
                } else {
                    this.target = this.chooseHandle(b)
                }
            }
            switch (this.target) {
            case "base":
                var c = (this.options.max - this.options.min) / 100,
                d = (this.result.from - this.options.min) / c,
                e = (this.result.to - this.options.min) / c;
                this.coords.p_single_real = this.toFixed(d);
                this.coords.p_from_real = this.toFixed(d);
                this.coords.p_to_real = this.toFixed(e);
                this.coords.p_single_real = this.checkDiapason(this.coords.p_single_real, this.options.from_min, this.options.from_max);
                this.coords.p_from_real = this.checkDiapason(this.coords.p_from_real, this.options.from_min, this.options.from_max);
                this.coords.p_to_real = this.checkDiapason(this.coords.p_to_real, this.options.to_min, this.options.to_max);
                this.coords.p_single_fake = this.convertToFakePercent(this.coords.p_single_real);
                this.coords.p_from_fake = this.convertToFakePercent(this.coords.p_from_real);
                this.coords.p_to_fake = this.convertToFakePercent(this.coords.p_to_real);
                this.target = null;
                break;
            case "single":
                if (this.options.from_fixed) {
                    break
                }
                this.coords.p_single_real = this.convertToRealPercent(b);
                this.coords.p_single_real = this.calcWithStep(this.coords.p_single_real);
                this.coords.p_single_real = this.checkDiapason(this.coords.p_single_real, this.options.from_min, this.options.from_max);
                this.coords.p_single_fake = this.convertToFakePercent(this.coords.p_single_real);
                break;
            case "from":
                if (this.options.from_fixed) {
                    break
                }
                this.coords.p_from_real = this.convertToRealPercent(b);
                this.coords.p_from_real = this.calcWithStep(this.coords.p_from_real);
                if (this.coords.p_from_real > this.coords.p_to_real) {
                    this.coords.p_from_real = this.coords.p_to_real
                }
                this.coords.p_from_real = this.checkDiapason(this.coords.p_from_real, this.options.from_min, this.options.from_max);
                this.coords.p_from_real = this.checkMinInterval(this.coords.p_from_real, this.coords.p_to_real, "from");
                this.coords.p_from_real = this.checkMaxInterval(this.coords.p_from_real, this.coords.p_to_real, "from");
                this.coords.p_from_fake = this.convertToFakePercent(this.coords.p_from_real);
                break;
            case "to":
                if (this.options.to_fixed) {
                    break
                }
                this.coords.p_to_real = this.convertToRealPercent(b);
                this.coords.p_to_real = this.calcWithStep(this.coords.p_to_real);
                if (this.coords.p_to_real < this.coords.p_from_real) {
                    this.coords.p_to_real = this.coords.p_from_real
                }
                this.coords.p_to_real = this.checkDiapason(this.coords.p_to_real, this.options.to_min, this.options.to_max);
                this.coords.p_to_real = this.checkMinInterval(this.coords.p_to_real, this.coords.p_from_real, "to");
                this.coords.p_to_real = this.checkMaxInterval(this.coords.p_to_real, this.coords.p_from_real, "to");
                this.coords.p_to_fake = this.convertToFakePercent(this.coords.p_to_real);
                break;
            case "both":
                if (this.options.from_fixed || this.options.to_fixed) {
                    break
                }
                b = this.toFixed(b + this.coords.p_handle * .1);
                this.coords.p_from_real = this.convertToRealPercent(b) - this.coords.p_gap_left;
                this.coords.p_from_real = this.calcWithStep(this.coords.p_from_real);
                this.coords.p_from_real = this.checkDiapason(this.coords.p_from_real, this.options.from_min, this.options.from_max);
                this.coords.p_from_real = this.checkMinInterval(this.coords.p_from_real, this.coords.p_to_real, "from");
                this.coords.p_from_fake = this.convertToFakePercent(this.coords.p_from_real);
                this.coords.p_to_real = this.convertToRealPercent(b) + this.coords.p_gap_right;
                this.coords.p_to_real = this.calcWithStep(this.coords.p_to_real);
                this.coords.p_to_real = this.checkDiapason(this.coords.p_to_real, this.options.to_min, this.options.to_max);
                this.coords.p_to_real = this.checkMinInterval(this.coords.p_to_real, this.coords.p_from_real, "to");
                this.coords.p_to_fake = this.convertToFakePercent(this.coords.p_to_real);
                break;
            case "both_one":
                if (this.options.from_fixed || this.options.to_fixed) {
                    break
                }
                var f = this.convertToRealPercent(b),
                g = this.result.from_percent,
                h = this.result.to_percent,
                i = h - g,
                j = i / 2,
                k = f - j,
                l = f + j;
                if (k < 0) {
                    k = 0;
                    l = k + i
                }
                if (l > 100) {
                    l = 100;
                    k = l - i
                }
                this.coords.p_from_real = this.calcWithStep(k);
                this.coords.p_from_real = this.checkDiapason(this.coords.p_from_real, this.options.from_min, this.options.from_max);
                this.coords.p_from_fake = this.convertToFakePercent(this.coords.p_from_real);
                this.coords.p_to_real = this.calcWithStep(l);
                this.coords.p_to_real = this.checkDiapason(this.coords.p_to_real, this.options.to_min, this.options.to_max);
                this.coords.p_to_fake = this.convertToFakePercent(this.coords.p_to_real);
                break
            }
            if (this.options.type === "single") {
                this.coords.p_bar_x = this.coords.p_handle / 2;
                this.coords.p_bar_w = this.coords.p_single_fake;
                this.result.from_percent = this.coords.p_single_real;
                this.result.from = this.convertToValue(this.coords.p_single_real);
                if (this.options.values.length) {
                    this.result.from_value = this.options.values[this.result.from]
                }
            } else {
                this.coords.p_bar_x = this.toFixed(this.coords.p_from_fake + this.coords.p_handle / 2);
                this.coords.p_bar_w = this.toFixed(this.coords.p_to_fake - this.coords.p_from_fake);
                this.result.from_percent = this.coords.p_from_real;
                this.result.from = this.convertToValue(this.coords.p_from_real);
                this.result.to_percent = this.coords.p_to_real;
                this.result.to = this.convertToValue(this.coords.p_to_real);
                if (this.options.values.length) {
                    this.result.from_value = this.options.values[this.result.from];
                    this.result.to_value = this.options.values[this.result.to]
                }
            }
            this.calcMinMax();
            this.calcLabels()
        },
        calcPointerPercent: function() {
            if (!this.coords.w_rs) {
                this.coords.p_pointer = 0;
                return
            }
            if (this.coords.x_pointer < 0 || isNaN(this.coords.x_pointer)) {
                this.coords.x_pointer = 0
            } else if (this.coords.x_pointer > this.coords.w_rs) {
                this.coords.x_pointer = this.coords.w_rs
            }
            this.coords.p_pointer = this.toFixed(this.coords.x_pointer / this.coords.w_rs * 100)
        },
        convertToRealPercent: function(a) {
            var b = 100 - this.coords.p_handle;
            return a / b * 100
        },
        convertToFakePercent: function(a) {
            var b = 100 - this.coords.p_handle;
            return a / 100 * b
        },
        getHandleX: function() {
            var a = 100 - this.coords.p_handle,
            b = this.toFixed(this.coords.p_pointer - this.coords.p_gap);
            if (b < 0) {
                b = 0
            } else if (b > a) {
                b = a
            }
            return b
        },
        calcHandlePercent: function() {
            if (this.options.type === "single") {
                this.coords.w_handle = this.$cache.s_single.outerWidth(false)
            } else {
                this.coords.w_handle = this.$cache.s_from.outerWidth(false)
            }
            this.coords.p_handle = this.toFixed(this.coords.w_handle / this.coords.w_rs * 100)
        },
        chooseHandle: function(a) {
            if (this.options.type === "single") {
                return "single"
            } else {
                var b = this.coords.p_from_real + (this.coords.p_to_real - this.coords.p_from_real) / 2;
                if (a >= b) {
                    return this.options.to_fixed ? "from": "to"
                } else {
                    return this.options.from_fixed ? "to": "from"
                }
            }
        },
        calcMinMax: function() {
            if (!this.coords.w_rs) {
                return
            }
            this.labels.p_min = this.labels.w_min / this.coords.w_rs * 100;
            this.labels.p_max = this.labels.w_max / this.coords.w_rs * 100
        },
        calcLabels: function() {
            if (!this.coords.w_rs || this.options.hide_from_to) {
                return
            }
            if (this.options.type === "single") {
                this.labels.w_single = this.$cache.single.outerWidth(false);
                this.labels.p_single_fake = this.labels.w_single / this.coords.w_rs * 100;
                this.labels.p_single_left = this.coords.p_single_fake + this.coords.p_handle / 2 - this.labels.p_single_fake / 2;
                this.labels.p_single_left = this.checkEdges(this.labels.p_single_left, this.labels.p_single_fake)
            } else {
                this.labels.w_from = this.$cache.from.outerWidth(false);
                this.labels.p_from_fake = this.labels.w_from / this.coords.w_rs * 100;
                this.labels.p_from_left = this.coords.p_from_fake + this.coords.p_handle / 2 - this.labels.p_from_fake / 2;
                this.labels.p_from_left = this.toFixed(this.labels.p_from_left);
                this.labels.p_from_left = this.checkEdges(this.labels.p_from_left, this.labels.p_from_fake);
                this.labels.w_to = this.$cache.to.outerWidth(false);
                this.labels.p_to_fake = this.labels.w_to / this.coords.w_rs * 100;
                this.labels.p_to_left = this.coords.p_to_fake + this.coords.p_handle / 2 - this.labels.p_to_fake / 2;
                this.labels.p_to_left = this.toFixed(this.labels.p_to_left);
                this.labels.p_to_left = this.checkEdges(this.labels.p_to_left, this.labels.p_to_fake);
                this.labels.w_single = this.$cache.single.outerWidth(false);
                this.labels.p_single_fake = this.labels.w_single / this.coords.w_rs * 100;
                this.labels.p_single_left = (this.labels.p_from_left + this.labels.p_to_left + this.labels.p_to_fake) / 2 - this.labels.p_single_fake / 2;
                this.labels.p_single_left = this.toFixed(this.labels.p_single_left);
                this.labels.p_single_left = this.checkEdges(this.labels.p_single_left, this.labels.p_single_fake)
            }
        },
        updateScene: function() {
            if (this.raf_id) {
                cancelAnimationFrame(this.raf_id);
                this.raf_id = null
            }
            clearTimeout(this.update_tm);
            this.update_tm = null;
            if (!this.options) {
                return
            }
            this.drawHandles();
            if (this.is_active) {
                this.raf_id = requestAnimationFrame(this.updateScene.bind(this))
            } else {
                this.update_tm = setTimeout(this.updateScene.bind(this), 300)
            }
        },
        drawHandles: function() {
            this.coords.w_rs = this.$cache.rs.outerWidth(false);
            if (!this.coords.w_rs) {
                return
            }
            if (this.coords.w_rs !== this.coords.w_rs_old) {
                this.target = "base";
                this.is_resize = true
            }
            if (this.coords.w_rs !== this.coords.w_rs_old || this.force_redraw) {
                this.setMinMax();
                this.calc(true);
                this.drawLabels();
                if (this.options.grid) {
                    this.calcGridMargin();
                    this.calcGridLabels()
                }
                this.force_redraw = true;
                this.coords.w_rs_old = this.coords.w_rs;
                this.drawShadow()
            }
            if (!this.coords.w_rs) {
                return
            }
            if (!this.dragging && !this.force_redraw && !this.is_key) {
                return
            }
            if (this.old_from !== this.result.from || this.old_to !== this.result.to || this.force_redraw || this.is_key) {
                this.drawLabels();
                this.$cache.bar[0].style.left = this.coords.p_bar_x + "%";
                this.$cache.bar[0].style.width = this.coords.p_bar_w + "%";
                if (this.options.type === "single") {
                    this.$cache.s_single[0].style.left = this.coords.p_single_fake + "%";
                    this.$cache.single[0].style.left = this.labels.p_single_left + "%";
                    if (this.options.values.length) {
                        this.$cache.input.prop("value", this.result.from_value)
                    } else {
                        this.$cache.input.prop("value", this.result.from)
                    }
                    this.$cache.input.data("from", this.result.from)
                } else {
                    this.$cache.s_from[0].style.left = this.coords.p_from_fake + "%";
                    this.$cache.s_to[0].style.left = this.coords.p_to_fake + "%";
                    if (this.old_from !== this.result.from || this.force_redraw) {
                        this.$cache.from[0].style.left = this.labels.p_from_left + "%"
                    }
                    if (this.old_to !== this.result.to || this.force_redraw) {
                        this.$cache.to[0].style.left = this.labels.p_to_left + "%"
                    }
                    this.$cache.single[0].style.left = this.labels.p_single_left + "%";
                    if (this.options.values.length) {
                        this.$cache.input.prop("value", this.result.from_value + this.options.input_values_separator + this.result.to_value)
                    } else {
                        this.$cache.input.prop("value", this.result.from + this.options.input_values_separator + this.result.to)
                    }
                    this.$cache.input.data("from", this.result.from);
                    this.$cache.input.data("to", this.result.to)
                }
                if ((this.old_from !== this.result.from || this.old_to !== this.result.to) && !this.is_start) {
                    this.$cache.input.trigger("change")
                }
                this.old_from = this.result.from;
                this.old_to = this.result.to;
                if (!this.is_resize && !this.is_update && !this.is_start && !this.is_finish) {
                    this.callOnChange()
                }
                if (this.is_key || this.is_click) {
                    this.is_key = false;
                    this.is_click = false;
                    this.callOnFinish()
                }
                this.is_update = false;
                this.is_resize = false;
                this.is_finish = false
            }
            this.is_start = false;
            this.is_key = false;
            this.is_click = false;
            this.force_redraw = false
        },
        drawLabels: function() {
            if (!this.options) {
                return
            }
            var a = this.options.values.length,
            b = this.options.p_values,
            c, d, e;
            if (this.options.hide_from_to) {
                return
            }
            if (this.options.type === "single") {
                if (a) {
                    c = this.decorate(b[this.result.from]);
                    this.$cache.single.html(c)
                } else {
                    c = this.decorate(this._prettify(this.result.from), this.result.from);
                    this.$cache.single.html(c)
                }
                this.calcLabels();
                if (this.labels.p_single_left < this.labels.p_min + 1) {
                    this.$cache.min[0].style.visibility = "hidden"
                } else {
                    this.$cache.min[0].style.visibility = "visible"
                }
                if (this.labels.p_single_left + this.labels.p_single_fake > 100 - this.labels.p_max - 1) {
                    this.$cache.max[0].style.visibility = "hidden"
                } else {
                    this.$cache.max[0].style.visibility = "visible"
                }
            } else {
                if (a) {
                    if (this.options.decorate_both) {
                        c = this.decorate(b[this.result.from]);
                        c += this.options.values_separator;
                        c += this.decorate(b[this.result.to])
                    } else {
                        c = this.decorate(b[this.result.from] + this.options.values_separator + b[this.result.to])
                    }
                    d = this.decorate(b[this.result.from]);
                    e = this.decorate(b[this.result.to]);
                    this.$cache.single.html(c);
                    this.$cache.from.html(d);
                    this.$cache.to.html(e)
                } else {
                    if (this.options.decorate_both) {
                        c = this.decorate(this._prettify(this.result.from), this.result.from);
                        c += this.options.values_separator;
                        c += this.decorate(this._prettify(this.result.to), this.result.to)
                    } else {
                        c = this.decorate(this._prettify(this.result.from) + this.options.values_separator + this._prettify(this.result.to), this.result.to)
                    }
                    d = this.decorate(this._prettify(this.result.from), this.result.from);
                    e = this.decorate(this._prettify(this.result.to), this.result.to);
                    this.$cache.single.html(c);
                    this.$cache.from.html(d);
                    this.$cache.to.html(e)
                }
                this.calcLabels();
                var f = Math.min(this.labels.p_single_left, this.labels.p_from_left),
                g = this.labels.p_single_left + this.labels.p_single_fake,
                h = this.labels.p_to_left + this.labels.p_to_fake,
                i = Math.max(g, h);
                if (this.labels.p_from_left + this.labels.p_from_fake >= this.labels.p_to_left) {
                    this.$cache.from[0].style.visibility = "hidden";
                    this.$cache.to[0].style.visibility = "hidden";
                    this.$cache.single[0].style.visibility = "visible";
                    if (this.result.from === this.result.to) {
                        if (this.target === "from") {
                            this.$cache.from[0].style.visibility = "visible"
                        } else if (this.target === "to") {
                            this.$cache.to[0].style.visibility = "visible"
                        } else if (!this.target) {
                            this.$cache.from[0].style.visibility = "visible"
                        }
                        this.$cache.single[0].style.visibility = "hidden";
                        i = h
                    } else {
                        this.$cache.from[0].style.visibility = "hidden";
                        this.$cache.to[0].style.visibility = "hidden";
                        this.$cache.single[0].style.visibility = "visible";
                        i = Math.max(g, h)
                    }
                } else {
                    this.$cache.from[0].style.visibility = "visible";
                    this.$cache.to[0].style.visibility = "visible";
                    this.$cache.single[0].style.visibility = "hidden"
                }
                if (f < this.labels.p_min + 1) {
                    this.$cache.min[0].style.visibility = "hidden"
                } else {
                    this.$cache.min[0].style.visibility = "visible"
                }
                if (i > 100 - this.labels.p_max - 1) {
                    this.$cache.max[0].style.visibility = "hidden"
                } else {
                    this.$cache.max[0].style.visibility = "visible"
                }
            }
        },
        drawShadow: function() {
            var a = this.options,
            b = this.$cache,
            c = typeof a.from_min === "number" && !isNaN(a.from_min),
            d = typeof a.from_max === "number" && !isNaN(a.from_max),
            e = typeof a.to_min === "number" && !isNaN(a.to_min),
            f = typeof a.to_max === "number" && !isNaN(a.to_max),
            g,
            h,
            i,
            j;
            if (a.type === "single") {
                if (a.from_shadow && (c || d)) {
                    g = this.convertToPercent(c ? a.from_min: a.min);
                    h = this.convertToPercent(d ? a.from_max: a.max) - g;
                    g = this.toFixed(g - this.coords.p_handle / 100 * g);
                    h = this.toFixed(h - this.coords.p_handle / 100 * h);
                    g = g + this.coords.p_handle / 2;
                    b.shad_single[0].style.display = "block";
                    b.shad_single[0].style.left = g + "%";
                    b.shad_single[0].style.width = h + "%"
                } else {
                    b.shad_single[0].style.display = "none"
                }
            } else {
                if (a.from_shadow && (c || d)) {
                    g = this.convertToPercent(c ? a.from_min: a.min);
                    h = this.convertToPercent(d ? a.from_max: a.max) - g;
                    g = this.toFixed(g - this.coords.p_handle / 100 * g);
                    h = this.toFixed(h - this.coords.p_handle / 100 * h);
                    g = g + this.coords.p_handle / 2;
                    b.shad_from[0].style.display = "block";
                    b.shad_from[0].style.left = g + "%";
                    b.shad_from[0].style.width = h + "%"
                } else {
                    b.shad_from[0].style.display = "none"
                }
                if (a.to_shadow && (e || f)) {
                    i = this.convertToPercent(e ? a.to_min: a.min);
                    j = this.convertToPercent(f ? a.to_max: a.max) - i;
                    i = this.toFixed(i - this.coords.p_handle / 100 * i);
                    j = this.toFixed(j - this.coords.p_handle / 100 * j);
                    i = i + this.coords.p_handle / 2;
                    b.shad_to[0].style.display = "block";
                    b.shad_to[0].style.left = i + "%";
                    b.shad_to[0].style.width = j + "%"
                } else {
                    b.shad_to[0].style.display = "none"
                }
            }
        },
        callOnStart: function() {
            if (this.options.onStart && typeof this.options.onStart === "function") {
                this.options.onStart(this.result)
            }
        },
        callOnChange: function() {
            if (this.options.onChange && typeof this.options.onChange === "function") {
                this.options.onChange(this.result)
            }
        },
        callOnFinish: function() {
            if (this.options.onFinish && typeof this.options.onFinish === "function") {
                this.options.onFinish(this.result)
            }
        },
        callOnUpdate: function() {
            if (this.options.onUpdate && typeof this.options.onUpdate === "function") {
                this.options.onUpdate(this.result)
            }
        },
        toggleInput: function() {
            this.$cache.input.toggleClass("irs-hidden-input")
        },
        convertToPercent: function(a, b) {
            var c = this.options.max - this.options.min,
            d = c / 100,
            e, f;
            if (!c) {
                this.no_diapason = true;
                return 0
            }
            if (b) {
                e = a
            } else {
                e = a - this.options.min
            }
            f = e / d;
            return this.toFixed(f)
        },
        convertToValue: function(a) {
            var b = this.options.min,
            c = this.options.max,
            d = b.toString().split(".")[1],
            e = c.toString().split(".")[1],
            f,
            g,
            h = 0,
            i = 0;
            if (a === 0) {
                return this.options.min
            }
            if (a === 100) {
                return this.options.max
            }
            if (d) {
                f = d.length;
                h = f
            }
            if (e) {
                g = e.length;
                h = g
            }
            if (f && g) {
                h = f >= g ? f: g
            }
            if (b < 0) {
                i = Math.abs(b);
                b = +(b + i).toFixed(h);
                c = +(c + i).toFixed(h)
            }
            var j = (c - b) / 100 * a + b,
            k = this.options.step.toString().split(".")[1],
            l;
            if (k) {
                j = +j.toFixed(k.length)
            } else {
                j = j / this.options.step;
                j = j * this.options.step;
                j = +j.toFixed(0)
            }
            if (i) {
                j -= i
            }
            if (k) {
                l = +j.toFixed(k.length)
            } else {
                l = this.toFixed(j)
            }
            if (l < this.options.min) {
                l = this.options.min
            } else if (l > this.options.max) {
                l = this.options.max
            }
            return l
        },
        calcWithStep: function(a) {
            var b = Math.round(a / this.coords.p_step) * this.coords.p_step;
            if (b > 100) {
                b = 100
            }
            if (a === 100) {
                b = 100
            }
            return this.toFixed(b)
        },
        checkMinInterval: function(a, b, c) {
            var d = this.options,
            e, f;
            if (!d.min_interval) {
                return a
            }
            e = this.convertToValue(a);
            f = this.convertToValue(b);
            if (c === "from") {
                if (f - e < d.min_interval) {
                    e = f - d.min_interval
                }
            } else {
                if (e - f < d.min_interval) {
                    e = f + d.min_interval
                }
            }
            return this.convertToPercent(e)
        },
        checkMaxInterval: function(a, b, c) {
            var d = this.options,
            e, f;
            if (!d.max_interval) {
                return a
            }
            e = this.convertToValue(a);
            f = this.convertToValue(b);
            if (c === "from") {
                if (f - e > d.max_interval) {
                    e = f - d.max_interval
                }
            } else {
                if (e - f > d.max_interval) {
                    e = f + d.max_interval
                }
            }
            return this.convertToPercent(e)
        },
        checkDiapason: function(a, b, c) {
            var d = this.convertToValue(a),
            e = this.options;
            if (typeof b !== "number") {
                b = e.min
            }
            if (typeof c !== "number") {
                c = e.max
            }
            if (d < b) {
                d = b
            }
            if (d > c) {
                d = c
            }
            return this.convertToPercent(d)
        },
        toFixed: function(a) {
            a = a.toFixed(9);
            return + a
        },
        _prettify: function(a) {
            if (!this.options.prettify_enabled) {
                return a
            }
            if (this.options.prettify && typeof this.options.prettify === "function") {
                return this.options.prettify(a)
            } else {
                return this.prettify(a)
            }
        },
        prettify: function(a) {
            var b = a.toString();
            return b.replace(/(\d{1,3}(?=(?:\d\d\d)+(?!\d)))/g, "$1" + this.options.prettify_separator)
        },
        checkEdges: function(a, b) {
            if (!this.options.force_edges) {
                return this.toFixed(a)
            }
            if (a < 0) {
                a = 0
            } else if (a > 100 - b) {
                a = 100 - b
            }
            return this.toFixed(a)
        },
        validate: function() {
            var a = this.options,
            b = this.result,
            c = a.values,
            d = c.length,
            e, f;
            if (typeof a.min === "string") a.min = +a.min;
            if (typeof a.max === "string") a.max = +a.max;
            if (typeof a.from === "string") a.from = +a.from;
            if (typeof a.to === "string") a.to = +a.to;
            if (typeof a.step === "string") a.step = +a.step;
            if (typeof a.from_min === "string") a.from_min = +a.from_min;
            if (typeof a.from_max === "string") a.from_max = +a.from_max;
            if (typeof a.to_min === "string") a.to_min = +a.to_min;
            if (typeof a.to_max === "string") a.to_max = +a.to_max;
            if (typeof a.keyboard_step === "string") a.keyboard_step = +a.keyboard_step;
            if (typeof a.grid_num === "string") a.grid_num = +a.grid_num;
            if (a.max < a.min) {
                a.max = a.min
            }
            if (d) {
                a.p_values = [];
                a.min = 0;
                a.max = d - 1;
                a.step = 1;
                a.grid_num = a.max;
                a.grid_snap = true;
                for (f = 0; f < d; f++) {
                    e = +c[f];
                    if (!isNaN(e)) {
                        c[f] = e;
                        e = this._prettify(e)
                    } else {
                        e = c[f]
                    }
                    a.p_values.push(e)
                }
            }
            if (typeof a.from !== "number" || isNaN(a.from)) {
                a.from = a.min
            }
            if (typeof a.to !== "number" || isNaN(a.from)) {
                a.to = a.max
            }
            if (a.type === "single") {
                if (a.from < a.min) {
                    a.from = a.min
                }
                if (a.from > a.max) {
                    a.from = a.max
                }
            } else {
                if (a.from < a.min || a.from > a.max) {
                    a.from = a.min
                }
                if (a.to > a.max || a.to < a.min) {
                    a.to = a.max
                }
                if (a.from > a.to) {
                    a.from = a.to
                }
            }
            if (typeof a.step !== "number" || isNaN(a.step) || !a.step || a.step < 0) {
                a.step = 1
            }
            if (typeof a.keyboard_step !== "number" || isNaN(a.keyboard_step) || !a.keyboard_step || a.keyboard_step < 0) {
                a.keyboard_step = 5
            }
            if (typeof a.from_min === "number" && a.from < a.from_min) {
                a.from = a.from_min
            }
            if (typeof a.from_max === "number" && a.from > a.from_max) {
                a.from = a.from_max
            }
            if (typeof a.to_min === "number" && a.to < a.to_min) {
                a.to = a.to_min
            }
            if (typeof a.to_max === "number" && a.from > a.to_max) {
                a.to = a.to_max
            }
            if (b) {
                if (b.min !== a.min) {
                    b.min = a.min
                }
                if (b.max !== a.max) {
                    b.max = a.max
                }
                if (b.from < b.min || b.from > b.max) {
                    b.from = a.from
                }
                if (b.to < b.min || b.to > b.max) {
                    b.to = a.to
                }
            }
            if (typeof a.min_interval !== "number" || isNaN(a.min_interval) || !a.min_interval || a.min_interval < 0) {
                a.min_interval = 0
            }
            if (typeof a.max_interval !== "number" || isNaN(a.max_interval) || !a.max_interval || a.max_interval < 0) {
                a.max_interval = 0
            }
            if (a.min_interval && a.min_interval > a.max - a.min) {
                a.min_interval = a.max - a.min
            }
            if (a.max_interval && a.max_interval > a.max - a.min) {
                a.max_interval = a.max - a.min
            }
        },
        decorate: function(a, b) {
            var c = "",
            d = this.options;
            if (d.prefix) {
                c += d.prefix
            }
            c += a;
            if (d.max_postfix) {
                if (d.values.length && a === d.p_values[d.max]) {
                    c += d.max_postfix;
                    if (d.postfix) {
                        c += " "
                    }
                } else if (b === d.max) {
                    c += d.max_postfix;
                    if (d.postfix) {
                        c += " "
                    }
                }
            }
            if (d.postfix) {
                c += d.postfix
            }
            return c
        },
        updateFrom: function() {
            this.result.from = this.options.from;
            this.result.from_percent = this.convertToPercent(this.result.from);
            if (this.options.values) {
                this.result.from_value = this.options.values[this.result.from]
            }
        },
        updateTo: function() {
            this.result.to = this.options.to;
            this.result.to_percent = this.convertToPercent(this.result.to);
            if (this.options.values) {
                this.result.to_value = this.options.values[this.result.to]
            }
        },
        updateResult: function() {
            this.result.min = this.options.min;
            this.result.max = this.options.max;
            this.updateFrom();
            this.updateTo()
        },
        appendGrid: function() {
            if (!this.options.grid) {
                return
            }
            var a = this.options,
            b, c, d = a.max - a.min,
            e = a.grid_num,
            f = 0,
            g = 0,
            h = 4,
            i, j, k = 0,
            l, m = "";
            this.calcGridMargin();
            if (a.grid_snap) {
                e = d / a.step;
                f = this.toFixed(a.step / (d / 100))
            } else {
                f = this.toFixed(100 / e)
            }
            if (e > 4) {
                h = 3
            }
            if (e > 7) {
                h = 2
            }
            if (e > 14) {
                h = 1
            }
            if (e > 28) {
                h = 0
            }
            for (b = 0; b < e + 1; b++) {
                i = h;
                g = this.toFixed(f * b);
                if (g > 100) {
                    g = 100;
                    i -= 2;
                    if (i < 0) {
                        i = 0
                    }
                }
                this.coords.big[b] = g;
                j = (g - f * (b - 1)) / (i + 1);
                for (c = 1; c <= i; c++) {
                    if (g === 0) {
                        break
                    }
                    k = this.toFixed(g - j * c);
                    m += '<span class="irs-grid-pol small" style="left: ' + k + '%"></span>'
                }
                m += '<span class="irs-grid-pol" style="left: ' + g + '%"></span>';
                l = this.convertToValue(g);
                if (a.values.length) {
                    l = a.p_values[l]
                } else {
                    l = this._prettify(l)
                }
                m += '<span class="irs-grid-text js-grid-text-' + b + '" style="left: ' + g + '%">' + l + "</span>"
            }
            this.coords.big_num = Math.ceil(e + 1);
            this.$cache.cont.addClass("irs-with-grid");
            this.$cache.grid.html(m);
            this.cacheGridLabels()
        },
        cacheGridLabels: function() {
            var a, b, c = this.coords.big_num;
            for (b = 0; b < c; b++) {
                a = this.$cache.grid.find(".js-grid-text-" + b);
                this.$cache.grid_labels.push(a)
            }
            this.calcGridLabels()
        },
        calcGridLabels: function() {
            var a, b, c = [],
            d = [],
            e = this.coords.big_num;
            for (a = 0; a < e; a++) {
                this.coords.big_w[a] = this.$cache.grid_labels[a].outerWidth(false);
                this.coords.big_p[a] = this.toFixed(this.coords.big_w[a] / this.coords.w_rs * 100);
                this.coords.big_x[a] = this.toFixed(this.coords.big_p[a] / 2);
                c[a] = this.toFixed(this.coords.big[a] - this.coords.big_x[a]);
                d[a] = this.toFixed(c[a] + this.coords.big_p[a])
            }
            if (this.options.force_edges) {
                if (c[0] < -this.coords.grid_gap) {
                    c[0] = -this.coords.grid_gap;
                    d[0] = this.toFixed(c[0] + this.coords.big_p[0]);
                    this.coords.big_x[0] = this.coords.grid_gap
                }
                if (d[e - 1] > 100 + this.coords.grid_gap) {
                    d[e - 1] = 100 + this.coords.grid_gap;
                    c[e - 1] = this.toFixed(d[e - 1] - this.coords.big_p[e - 1]);
                    this.coords.big_x[e - 1] = this.toFixed(this.coords.big_p[e - 1] - this.coords.grid_gap)
                }
            }
            this.calcGridCollision(2, c, d);
            this.calcGridCollision(4, c, d);
            for (a = 0; a < e; a++) {
                b = this.$cache.grid_labels[a][0];
                b.style.marginLeft = -this.coords.big_x[a] + "%"
            }
        },
        calcGridCollision: function(a, b, c) {
            var d, e, f, g = this.coords.big_num;
            for (d = 0; d < g; d += a) {
                e = d + a / 2;
                if (e >= g) {
                    break
                }
                f = this.$cache.grid_labels[e][0];
                if (c[d] <= b[e]) {
                    f.style.visibility = "visible"
                } else {
                    f.style.visibility = "hidden"
                }
            }
        },
        calcGridMargin: function() {
            if (!this.options.grid_margin) {
                return
            }
            this.coords.w_rs = this.$cache.rs.outerWidth(false);
            if (!this.coords.w_rs) {
                return
            }
            if (this.options.type === "single") {
                this.coords.w_handle = this.$cache.s_single.outerWidth(false)
            } else {
                this.coords.w_handle = this.$cache.s_from.outerWidth(false)
            }
            this.coords.p_handle = this.toFixed(this.coords.w_handle / this.coords.w_rs * 100);
            this.coords.grid_gap = this.toFixed(this.coords.p_handle / 2 - .1);
            this.$cache.grid[0].style.width = this.toFixed(100 - this.coords.p_handle) + "%";
            this.$cache.grid[0].style.left = this.coords.grid_gap + "%"
        },
        update: function(b) {
            if (!this.input) {
                return
            }
            this.is_update = true;
            this.options.from = this.result.from;
            this.options.to = this.result.to;
            this.options = a.extend(this.options, b);
            this.validate();
            this.updateResult(b);
            this.toggleInput();
            this.remove();
            this.init(true)
        },
        reset: function() {
            if (!this.input) {
                return
            }
            this.updateResult();
            this.update()
        },
        destroy: function() {
            if (!this.input) {
                return
            }
            this.toggleInput();
            this.$cache.input.prop("readonly", false);
            a.data(this.input, "ionRangeSlider", null);
            this.remove();
            this.input = null;
            this.options = null
        }
    };
    a.fn.ionRangeSlider = function(b) {
        return this.each(function() {
            if (!a.data(this, "ionRangeSlider")) {
                a.data(this, "ionRangeSlider", new l(this, b, (f++)))
            }
        })
    }; (function() {
        var a = 0;
        var b = ["ms", "moz", "webkit", "o"];
        for (var d = 0; d < b.length && !c.requestAnimationFrame; ++d) {
            c.requestAnimationFrame = c[b[d] + "RequestAnimationFrame"];
            c.cancelAnimationFrame = c[b[d] + "CancelAnimationFrame"] || c[b[d] + "CancelRequestAnimationFrame"]
        }
        if (!c.requestAnimationFrame) c.requestAnimationFrame = function(b, d) {
            var e = (new Date).getTime();
            var f = Math.max(0, 16 - (e - a));
            var g = c.setTimeout(function() {
                b(e + f)
            },
            f);
            a = e + f;
            return g
        };
        if (!c.cancelAnimationFrame) c.cancelAnimationFrame = function(a) {
            clearTimeout(a)
        }
    })()
});
define("app/common/views/question", ["require", "exports", "module", "jquery", "rangeSlider"],
function(a, b, c) {
    var d = a("jquery");
    a("rangeSlider");
    c.exports = e;
    function e() {}
    e.adjustMatrixStar = f;
    function f(a) {
        if (!a.is(":visible")) {
            return
        }
        var b = Boolean(Number(a.attr("data-revert")));
        a.find("input.star_range").each(function() {
            c(d(this), b)
        });
        function c(a, b) {
            if (!a.is(".star_range")) {
                return
            }
            var c = a.attr("min");
            var d = a.attr("max");
            var g = a.attr("disabled");
            var h = a.attr("value");
            if (!a.data("ionRangeSlider")) {
                a.ionRangeSlider({
                    type: "single",
                    from: f(d, b, h),
                    values: e(c, d, b),
                    onChange: function(a) {
                        a.input.parents(".matrix_item").attr("selected", "selected")
                    },
                    disable: Boolean(g)
                });
                if (h) {
                    a.parents(".matrix_item").attr("selected", "selected")
                }
            }
        }
        function e(a, b, c) {
            var d = [];
            var e;
            a = a || 1;
            b = b || 6;
            for (e = a; e <= b; e++) {
                d.push(e)
            }
            return c ? d.reverse() : d
        }
        function f(a, b, c) {
            if (c) {
                return b ? a - c: c - 1
            }
            if (b) {
                return (a / 2).toFixed()
            } else {
                return (a / 2).toFixed() - 1
            }
        }
    }
});
define("app/survey/common/model/mobile_guide", ["require", "exports", "module", "jquery", "art-dialog/dialog"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("art-dialog/dialog");
    var f = d(document).width() < 800;
    var g = document.referrer.indexOf("wj.qq.com/mobile/edit.html") > 0;
    var h = function() {
        var a = e({
            skin: "dialog_share_survey",
            title: "问卷已经开始回收",
            content: "您可以复制链接或者分享给朋友作答"
        }).showModal();
        setTimeout(function() {
            a.close().remove()
        },
        2e3)
    };
    c.exports = function() {
        if (f && g) {
            h()
        }
    }
});
define("app/util/richtext", ["require", "jquery", "underscore"],
function(a) {
    var b = a("jquery");
    var c = a("underscore");
    var d = {
        outputFilter: function(a) {
            return a.replace(/(<img.*?data-style="(.*?)")(.*?style=")(.*?)(".*?\/>)/g,
            function() {
                var a = arguments;
                return a[1] + a[3] + a[2] + a[5]
            })
        },
        plainTextFilter: function(a) {
            var c = this.HTMLDeCode(a.toString());
            c = b("<div>" + c + "</div>");
            c.find("img").replaceWith("[图片]");
            c = c.text();
            return b.trim(c)
        },
        HTMLDeCode: function(a) {
            var b = "";
            if (a.length == 0) return "";
            b = a.replace(/&amp;/g, "&");
            b = b.replace(/&lt;/g, "<");
            b = b.replace(/&gt;/g, ">");
            b = b.replace(/&nbsp;/g, " ");
            b = b.replace(/&apos;/g, "'");
            b = b.replace(/&#39;/g, "'");
            b = b.replace(/&#34;/g, '"');
            b = b.replace(/&quot;/g, '"');
            b = b.replace(/<br>/g, "\n");
            return b
        },
        unWrapper: function(a) {
            var c = a;
            for (var d = 0; d < 3; d++) {
                c = b("<p>" + c + "</p>").text()
            }
            return c
        },
        plainRichTextFilter: function(a, d) {
            if (!a) {
                return ""
            }
            var e = this.HTMLDeCode(a.toString());
            var f = [];
            var g = [];
            var h;
            e = b("<p>" + e + "</p>");
            e.find("img").each(function(a, c) {
                if (b(c).attr("data-sourceurl")) {
                    f.push(b(c).attr("data-sourceurl").replace(/^(\'|\"){1}/, "").replace(/(\'|\"){1}$/, ""))
                } else {
                    f.push(b(c).attr("src").replace(/^(\'|\"){1}/, "").replace(/(\'|\"){1}$/, ""))
                }
                b(c).replaceWith("[图片]")
            });
            c.each(e.find(".mod_fillblank"),
            function(a) {
                g.push(b(a).clone());
                b(a).replaceWith("[填空]")
            });
            e = i(e.text());
            c.forEach(g,
            function(a) {
                var b = a.prop("outerHTML");
                e = e.replace("[填空]", b)
            });
            if (d && d.preview) {
                c.forEach(f,
                function(a) {
                    e = e.replace("[图片]", "<img class='preview_image' data-sourceurl='" + a + "' src='//wj.qq.com/image/pc/image_thumb_icon.png'>")
                })
            } else {
                c.forEach(f,
                function(a) {
                    e = e.replace("[图片]", "<img class='preview_image' src='" + a + "'>")
                })
            }
            return b.trim(e);
            function i(a) {
                var b = document.createElement("div");
                b.innerHTML = a;
                return b.innerText || b.textContent
            }
        },
        formatRichTextAnswer: function(a, b) {
            if (a.answer) {
                c.map(a.answer,
                function(a) {
                    c.map(a.questions,
                    function(a) {
                        if (a.text) {
                            a.text = d.plainRichTextFilter(a.text, b)
                        }
                        c.map(a.options,
                        function(a) {
                            if (a.text) {
                                a.text = d.plainRichTextFilter(a.text, b)
                            }
                            return a
                        });
                        return a
                    });
                    return a
                })
            }
            return a
        },
        formatRichTextQuestion: function(a, b) {
            if (a.title) {
                a.title = c(a.title, b)
            }
            if (a.description) {
                a.description = c(a.description, b)
            }
            if (a.options && a.options.length) {
                a.options.forEach(function(a) {
                    a.text = c(a.text, b)
                })
            }
            if (a.subTitles && a.subTitles.length) {
                a.subTitles.forEach(function(a) {
                    a.text = c(a.text, b)
                })
            }
            function c(a, b) {
                return d.plainRichTextFilter(a, b)
            }
            return a
        },
        formatPlainTextQuestion: function(a) {
            if (a.title) {
                a.title = b(a.title)
            }
            if (a.description) {
                a.description = b(a.description)
            }
            if (a.options && a.options.length) {
                a.options.forEach(function(a) {
                    if (a.text) {
                        a.text = b(a.text)
                    }
                })
            }
            if (a.subTitles && a.subTitles.length) {
                a.subTitles.forEach(function(a) {
                    if (a.text) {
                        a.text = b(a.text)
                    }
                })
            }
            if (a.blank_setting && a.blank_setting.length) {
                a.blank_setting = []
            }
            function b(a) {
                return d.plainTextFilter(a)
            }
            return a
        },
        formatRichTextSurvey: function(a, b) {
            if (a.title) {
                a.title = d.plainRichTextFilter(a.title, b)
            }
            if (a.prefix) {
                a.prefix = d.plainRichTextFilter(a.prefix, b)
            }
            if (a.suffix) {
                a.suffix = d.plainRichTextFilter(a.suffix, b)
            }
            if (a.pages) {
                a.pages.forEach(function(a) {
                    a.questions.forEach(function(a) {
                        d.formatRichTextQuestion(a, b)
                    })
                })
            }
            return a
        },
        formatPlainTextSurvey: function(a) {
            if (a.title) {
                a.title = d.plainTextFilter(a.title)
            }
            if (a.prefix) {
                a.prefix = d.plainTextFilter(a.prefix)
            }
            if (a.suffix) {
                a.suffix = d.plainTextFilter(a.suffix)
            }
            if (a.pages) {
                a.pages.forEach(function(a) {
                    a.questions.forEach(function(a) {
                        d.formatPlainTextQuestion(a)
                    })
                })
            }
            return a
        },
        removeReferForTextSurvey: function(a) {
            if (a.pages) {
                a.pages.forEach(function(a) {
                    a.questions.forEach(function(a) {
                        if (a.refer) {
                            a.refer = null
                        }
                    })
                })
            }
            return a
        },
        replaceFillBlank: f
    };
    var e = function(a) {
        var b = {
            ELEMENT_NODE: 1,
            ATTRIBUTE_NODE: 2,
            TEXT_NODE: 3,
            CDATA_SECTION_NODE: 4,
            ENTITY_REFERENCE_NODE: 5,
            ENTITY_NODE: 6,
            PROCESSING_INSTRUCTION_NODE: 7,
            COMMENT_NODE: 8,
            DOCUMENT_NODE: 9,
            DOCUMENT_TYPE_NODE: 10,
            DOCUMENT_FRAGMENT_NODE: 11,
            NOTATION_NODE: 12
        };
        var c, d = "",
        f = 0,
        g = a.nodeType;
        if (!g) {
            while (c = a[f++]) {
                d += e(c)
            }
        } else if (g === b.ELEMENT_NODE || g === b.DOCUMENT_NODE || g === b.DOCUMENT_FRAGMENT_NODE) {
            if (typeof a.textContent === "string") {
                return a.textContent
            } else {
                for (a = a.firstChild; a; a = a.nextSibling) {
                    d += e(a)
                }
            }
        } else if (g === b.TEXT_NODE || g === b.CDATA_SECTION_NODE) {
            return a.nodeValue
        }
        return d
    };
    function f(a, c) {
        var d = c ? 'aria-labelledby="questionTitle_' + c + '"': "";
        if (a === "") {
            return a
        }
        if (!/^<p>/.test(a)) {
            a = "<p>" + a + "</p>"
        }
        var e = 14;
        var f = b('<input class="fill_blank needsclick" role="textbox" ' + d + ' style="width: inherit;">');
        var g = "";
        var h = b.parseHTML(a) || [];
        h.forEach(function(a) {
            var c = b(a);
            var d = c.find(".mod_fillblank");
            if (d.length === 0) {
                if (a.nodeType === 3) {
                    g += a.data
                } else {
                    g += a.outerHTML
                }
                return
            }
            d.each(function(a, c) {
                var d = b(c);
                var g = d.text().length * e + "px";
                var h = d.attr("data-id");
                var i = f.clone().css("width", g);
                i.attr("id", h);
                d.replaceWith(i)
            });
            g += a.outerHTML
        });
        return g
    }
    return d
});
define("app/survey/common/views/accessibility/RadioGroup", ["require", "exports", "module", "underscore", "jquery"],
function(a, b, c) {
    var d = a("underscore");
    var e = a("jquery");
    var f = function(a, b) {
        this.group = a;
        this.selector = b;
        this.buttons = [];
        this.firstBtn = null;
        this.lastBtn = null
    };
    d.extend(f.prototype, {
        KEYCODE: {
            RETURN: 13,
            SPACE: 32,
            END: 35,
            HOME: 36,
            LEFT: 37,
            UP: 38,
            RIGHT: 39,
            DOWN: 40
        },
        init: function() {
            var a = this.group.find(this.selector);
            var b = this;
            var c;
            for (var d = 0; d < a.length; d++) {
                c = a[d];
                b.buttonInit(c);
                b.buttons.push(c);
                if (!b.firstBtn) {
                    b.firstBtn = c;
                    b.firstBtn.tabIndex = 0
                }
                b.lastBtn = c
            }
        },
        setChecked: function(a) {
            if (a.tagName === "LABEL") {
                e(a).trigger("click")
            } else {
                e(a).find("label").trigger("click")
            }
        },
        setCheckedToPreviousItem: function(a) {
            var b;
            if (a === this.firstBtn) {
                this.setChecked(this.lastBtn)
            } else {
                b = this.buttons.indexOf(a);
                this.setChecked(this.buttons[b - 1])
            }
        },
        setCheckedToNextItem: function(a) {
            var b;
            if (a === this.lastBtn) {
                this.setChecked(this.firstBtn)
            } else {
                b = this.buttons.indexOf(a);
                this.setChecked(this.buttons[b + 1])
            }
        },
        buttonInit: function(a) {
            e(a).bind("keydown", this.handleKeydown.bind(this))
        },
        handleKeydown: function(a) {
            var b = a.currentTarget,
            c = false;
            switch (a.keyCode) {
            case this.KEYCODE.SPACE:
            case this.KEYCODE.RETURN:
                this.setChecked(a.target);
                c = true;
                break;
            case this.KEYCODE.UP:
                this.setCheckedToPreviousItem(a.target);
                c = true;
                break;
            case this.KEYCODE.DOWN:
                this.setCheckedToNextItem(a.target);
                c = true;
                break;
            case this.KEYCODE.LEFT:
                this.setCheckedToPreviousItem(a.target);
                c = true;
                break;
            case this.KEYCODE.RIGHT:
                this.setCheckedToNextItem(a.target);
                c = true;
                break;
            default:
                break
            }
            if (c) {
                a.stopPropagation();
                a.preventDefault()
            }
        }
    });
    c.exports = f
});
define("app/survey/common/views/accessibility/SortGroup", ["require", "exports", "module", "./RadioGroup", "underscore"],
function(a, b, c) {
    var d = a("./RadioGroup");
    var e = a("underscore");
    var f = function(a, b) {
        this.group = a;
        this.selector = b;
        this.buttons = [];
        this.firstBtn = null;
        this.lastBtn = null
    };
    e.extend(f.prototype, new d, {
        setChecked: function(a) {
            $(a).trigger("click");
            var b = $(this.group).find(".option_container .sort_option_item");
            var c = $(this.group).find(".answer_container .sort_option_item");
            b.attr({
                "aria-checked": "false",
                tabindex: "-1"
            });
            c.attr({
                "aria-checked": "true",
                tabindex: "0"
            });
            if (b.length > 0) {
                b.eq(0).attr({
                    tabindex: "0"
                }).focus()
            } else {
                c.eq( - 1).focus()
            }
        },
        setCheckedFirst: function(a) {
            var b = $(this.group).find(".answer_container");
            $(a).prependTo(b).focus()
        },
        focusNext: function(a) {
            var b = $(a).next(".sort_option_item");
            if (b.length > 0) {
                b.focus()
            }
        },
        focusPrev: function(a) {
            var b = $(a).prev(".sort_option_item");
            if (b.length > 0) {
                b.focus()
            }
        },
        handleKeydown: function(a) {
            var b = a.currentTarget,
            c = false,
            d;
            switch (a.keyCode) {
            case this.KEYCODE.SPACE:
            case this.KEYCODE.RETURN:
                if (a.target.getAttribute("aria-checked") === "false") {
                    this.setChecked(a.target)
                } else {
                    this.setCheckedFirst(a.target)
                }
                c = true;
                break;
            case this.KEYCODE.UP:
                this.focusPrev(a.target);
                c = true;
                break;
            case this.KEYCODE.DOWN:
                this.focusNext(a.target);
                c = true;
                break;
            case this.KEYCODE.LEFT:
                if (a.target.getAttribute("aria-checked") === "false") {
                    this.setChecked(a.target)
                } else {
                    this.focusPrev(a.target)
                }
                c = true;
                break;
            case this.KEYCODE.RIGHT:
                if (a.target.getAttribute("aria-checked") === "false") {
                    this.focusNext(a.target)
                }
                c = true;
                break;
            default:
                break
            }
            if (c) {
                a.stopPropagation();
                a.preventDefault()
            }
        }
    });
    c.exports = f
});
define("app/survey/common/views/accessibility/CheckboxGroup", ["require", "exports", "module", "./RadioGroup", "jquery"],
function(a, b, c) {
    var d = a("./RadioGroup");
    var e = a("jquery");
    var f = function(a, b) {
        this.group = a;
        this.selector = b;
        this.buttons = [];
        this.firstBtn = null;
        this.lastBtn = null
    };
    _.extend(f.prototype, new d, {
        focusPrev: function(a) {
            var b;
            if (a === this.firstBtn) {
                this.lastBtn.focus()
            } else {
                b = this.buttons.indexOf(a);
                this.buttons[b - 1].focus()
            }
        },
        focusNext: function(a) {
            var b;
            if (a === this.lastBtn) {
                this.firstBtn.focus()
            } else {
                b = this.buttons.indexOf(a);
                this.buttons[b + 1].focus()
            }
        },
        handleKeydown: function(a) {
            var b = a.currentTarget,
            c = false;
            switch (a.keyCode) {
            case this.KEYCODE.SPACE:
            case this.KEYCODE.RETURN:
                this.setChecked(a.target);
                c = true;
                break;
            case this.KEYCODE.UP:
            case this.KEYCODE.LEFT:
                this.focusPrev(a.target);
                c = true;
                break;
            case this.KEYCODE.DOWN:
            case this.KEYCODE.RIGHT:
                this.focusNext(a.target);
                c = true;
                break;
            default:
                break
            }
            if (c) {
                a.stopPropagation();
                a.preventDefault()
            }
        }
    });
    c.exports = f
});
define("app/survey/common/views/accessibility/ChainsGroup", ["require", "exports", "module", "./RadioGroup", "jquery"],
function(a, b, c) {
    var d = a("./RadioGroup");
    var e = a("jquery");
    var f = function(a, b, c) {
        this.selector = c;
        this.group = a
    };
    _.extend(f.prototype, new d, {
        init: function() {
            var a = this.group.find(this.selector);
            var b = this;
            var c;
            this.buttons = [];
            a.each(function(a, c) {
                b.buttonInit(c);
                b.buttons.push(c)
            })
        },
        setChecked: function(a) {
            e(a).trigger("click")
        },
        setFocusToPreviousItem: function(a) {
            e(a).prev(this.selector).focus()
        },
        setFocusToNextItem: function(a) {
            e(a).next(this.selector).focus()
        },
        buttonInit: function(a) {
            e(a).bind("keydown", this.handleKeydown.bind(this))
        },
        handleKeydown: function(a) {
            var b = a.currentTarget,
            c = false;
            switch (a.keyCode) {
            case this.KEYCODE.SPACE:
            case this.KEYCODE.RETURN:
            case this.KEYCODE.RIGHT:
                this.setChecked(b);
                c = true;
                break;
            case this.KEYCODE.UP:
                this.setFocusToPreviousItem(b);
                c = true;
                break;
            case this.KEYCODE.DOWN:
                this.setFocusToNextItem(b);
                c = true;
                break;
            default:
                break
            }
            if (c) {
                a.stopPropagation();
                a.preventDefault()
            }
        }
    });
    c.exports = f
});
define("app/survey/common/views/accessibility/SliderGroup", ["require", "exports", "module", "./RadioGroup", "underscore", "jquery"],
function(a, b, c) {
    var d = a("./RadioGroup");
    var e = a("underscore");
    var f = a("jquery");
    var g = function(a, b, c) {
        this.target = a.find(b);
        this.handler = a.find(c);
        this.group = a;
        this.maxValue = parseInt(this.target.attr("aria-valuemax"));
        this.minValue = parseInt(this.target.attr("aria-valuemin"));
        this.valueNow = parseInt((this.maxValue + this.minValue) / 2)
    };
    e.extend(g.prototype, new d, {
        init: function() {
            f(this.group).bind("keydown", this.handleKeydown.bind(this))
        },
        setValue: function(a) {
            this.valueNow = a;
            this.target.data("ionRangeSlider").update({
                from: a - 1
            });
            this.group.attr({
                selected: "selected"
            })
        },
        handleKeydown: function(a) {
            var b = this.valueNow,
            c = false;
            switch (a.keyCode) {
            case this.KEYCODE.SPACE:
            case this.KEYCODE.RETURN:
                this.setValue(b);
                c = true;
                break;
            case this.KEYCODE.UP:
            case this.KEYCODE.LEFT:
                if (b > 1) {
                    this.setValue(b - 1)
                }
                c = true;
                break;
            case this.KEYCODE.DOWN:
            case this.KEYCODE.RIGHT:
                if (b < this.maxValue) {
                    this.setValue(b + 1)
                }
                c = true;
                break;
            default:
                break
            }
            if (c) {
                a.stopPropagation();
                a.preventDefault()
            }
        }
    });
    c.exports = g
});
define("amd-templates/survey/common/survey_wrap", ["../../template"],
function(a) {
    return a("survey/common/survey_wrap", '<div class="survey_wrap"> <div class="survey_main">  <h1 class="survey_title"> <div class="inner"> <div class="title_content"></div> </div> </h1> <div class="survey_content">  <div class="survey_prefix"> <div class="inner"> <h2 class="prefix_content"></h2> </div> </div>  <div class="survey_container"></div>  <div class="survey_suffix" style="display: none;"> <div class="inner"> <div class="suffix_content"></div> </div> </div>  <div id="survey_assess_result" class="survey_assess_result" style="display: none;"> <div class="inner"> <div class="assess_result" style="text-align: center; font-size: 16px;"> 您的成绩是<span class="score" style="color: #d92424; margin: 0 2px;"></span>分，继续加油哦 </div> </div> </div>  <div class="survey_control"> <div class="inner"> <a href="javascript:;" title="prev" aria-label="prev" role="button" class="survey_btn survey_prevpage" style="display: none;">prev</a> <a href="javascript:;" title="submit" aria-label="submit" role="button" class="survey_btn survey_submit" style="display: none;">Submit</a> <a href="javascript:;" title="next" aria-label="next" role="button" class="survey_btn survey_nextpage" style="display: none;">next</a> </div> </div>  <div class="survey_ads" style="display: none; padding-top: 70px;"> <a aria-label="问卷广告，点击回到腾讯问卷首页" target="_blank" href="/"></a> </div> </div> </div> </div> ')
});
define("amd-templates/survey/common/copy_right", ["../../template"],
function(a) {
    return a("survey/common/copy_right",
    function(a) {
        "use strict";
        var b = this,
        c = (b.$helpers, a.hide_tencent_relevant),
        d = a.custom_copyright,
        e = b.$escape,
        f = "";
        return f += '<div class="g_content"> ',
        c ? (f += ' <p> <span class="declaration"> ', d ? (f += " ", f += e(d), f += " ") : f += ' Copyright &copy; 1998-<span class="copyright_end"></span> act.1700.cn All Rights Reserved. ', f += ' </span> </p> <p> <span class="declaration"> <a aria-label="举报该问卷 - report illegal stuffes" href="javascript:;" class="link" type="举报该问卷" id="btn_report_survey">举报该问卷</a> </span> </p> ') : f += ' <p>Copyright &copy; 1998-<span class="copyright_end"></span> Tencent.</p> <p> <span class="declaration">系统由 <a aria-label="跳转到腾讯问卷官网 - link to tencent survey" href="//wj.qq.com/"><img src="./image/logo@2x.png" alt="腾讯问卷" width="16" height="16"/>腾讯问卷</a>提供 </span> <span class="declaration"> <a aria-label="服务协议 - service agreement" href="http://www.qq.com/contract.shtml" class="link" target="_blank" title="服务协议">服务协议</a> <a aria-label="隐私政策 - privacy policy" href="http://www.qq.com/privacy.htm" class="link" target="_blank" title="隐私政策">隐私政策</a> <a aria-label="举报该问卷 - report illegal stuffes" href="javascript:;" class="link" type="举报该问卷" id="btn_report_survey">举报该问卷</a> </span> </p> ',
        f += " </div>",
        new String(f)
    })
});
define("amd-templates/common/pc/survey/questions/chained_selects/option", ["../../../../../template"],
function(a) {
    return a("common/pc/survey/questions/chained_selects/option",
    function(a) {
        "use strict";
        var b = this,
        c = (b.$helpers, a.selected),
        d = b.$escape,
        e = a.id,
        f = a.text,
        g = a.index,
        h = a.last,
        i = "";
        return i += '<div class="option ',
        c && (i += "selected"),
        i += '" data-id="',
        i += d(e),
        i += '" role="radio" aria-label="',
        i += d(f),
        i += '" ',
        i += 0 === g ? ' tabindex="0" ': ' tabindex="-1" ',
        i += ' aria-checked="false"> <div class="option_text">',
        i += d(f),
        i += "</div> ",
        h || (i += ' <i class="right_arrow"></i> '),
        i += " </div> ",
        new String(i)
    })
});
define("amd-templates/common/pc/survey/questions/title", ["../../../../template"],
function(a) {
    return a("common/pc/survey/questions/title",
    function(a) {
        "use strict";
        var b = this,
        c = (b.$helpers, b.$escape),
        d = a.id,
        e = a.index,
        f = b.$string,
        g = a.title,
        h = a.required,
        i = a.description,
        j = "";
        return j += '<div class="title" id="questionTitle_',
        j += c(d),
        j += '"> <div role="presetation" > <span class="title_index"> <span class="question_index" data-for=',
        j += c(d),
        j += ">",
        j += c(e),
        j += '</span>. </span> <h3 class="title_text"> ',
        j += f(g),
        j += " </h3> ",
        h && (j += ' <span class="required" title="必答题" aria-label="必答题" alt="必答题" style="display: none;">*', j += c(h), j += "</span> "),
        j += ' </div> <span class="tips" role="alert">  </span> </div> <div class="description"> <div class="description_text"> ',
        j += f(i),
        j += " </div> </div> ",
        new String(j)
    })
});
define("amd-templates/common/pc/survey/questions/radio", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/radio",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = a.required,
        h = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return n += f
        },
        i = a.refer,
        j = c.$each,
        k = a.options,
        l = (a.option, a.index, a.maxRow),
        m = c.$string,
        n = "";
        return n += '<div class="question question_',
        n += d(e),
        n += '" id="question_',
        n += d(f),
        n += '" data-type="',
        n += d(e),
        n += '" data-id="',
        n += d(f),
        n += '" role="group" data-role="',
        n += d(e),
        n += 'group" ',
        g && (n += ' aria-required="true" '),
        n += '> <div class="inner"> ',
        h("./title"),
        n += " ",
        i ? (n += ' <div class="no_refer_answer">此题选项来源于<b class="question_index" data-for="', n += d(i), n += '"></b>题中选项，请先填写第<b class="question_index" data-for="', n += d(i), n += '"></b>题</div> <div class="inputs" style="display:none;"> ') : n += ' <div class="inputs"> ',
        n += " ",
        j(k,
        function(a) {
            n += ' <div class="option_item" style="width: ',
            n += d(100 / l),
            n += '%;" role="',
            n += d(e),
            n += '" tabindex=',
            n += d("checkbox" === e ? "0": "-1"),
            n += ' aria-checked="false"> <input class="survey_form_checkbox" type="',
            n += d(e),
            n += '" name="answer_',
            n += d(f),
            n += '" data-oid="',
            n += d(a.id),
            n += '" data-exclusive="',
            n += d(a.exclusive ? 1 : 0),
            n += '" id="option_',
            n += d(f),
            n += "_",
            n += d(a.id),
            n += '" value="',
            n += d(a.text),
            n += '" /> <label for="option_',
            n += d(f),
            n += "_",
            n += d(a.id),
            n += '"> <i class="survey_form_ui"></i> <div class="option_text">',
            n += m(a.text),
            n += '</div> <div class="stat"> <div class="bar"></div> <span class="rate"></span> <span class="count"></span> </div> </label> </div> '
        }),
        n += " </div> </div> </div> ",
        new String(n)
    })
});
define("amd-templates/common/pc/survey/questions/checkbox", ["../../../../template", "./radio"],
function(a) {
    return a("common/pc/survey/questions/checkbox",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("./radio"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/pc/survey/questions/select", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/select",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return k += f
        },
        h = a.refer,
        i = c.$each,
        j = a.options,
        k = (a.option, a.index, "");
        return k += '<div class="question question_',
        k += d(e),
        k += '" id="question_',
        k += d(f),
        k += '" data-type="',
        k += d(e),
        k += '" data-id="',
        k += d(f),
        k += '"> <div class="inner"> ',
        g("./title"),
        k += " ",
        h && (k += ' <div class="no_refer_answer">此题选项来源于<b class="question_index" data-for="', k += d(h), k += '"></b>题中选项，请先填写第<b class="question_index" data-for="', k += d(h), k += '"></b>题</div> '),
        k += ' <div class="inputs" style="',
        h && (k += "display: none;"),
        k += '"> <select name="answer_',
        k += d(f),
        k += '" class="survey_form_input" aria-labelledby="questionTitle_',
        k += d(f),
        k += '"> <option disabled selected value aria-disabled="true" aria-label="请选择">--请选择--</option> ',
        i(j,
        function(a) {
            k += ' <option aria-label="',
            k += d(a.text),
            k += '" aria-checked="false" aria-selected="false" id="',
            "98" === a.id ? (k += "option_", k += d(f), k += "_other") : (k += "option_", k += d(f), k += "_", k += d(a.id)),
            k += '" value="',
            k += d(a.id),
            k += '" data-oid="',
            k += d(a.id),
            k += '" class="',
            "98" === a.id && (k += "others"),
            k += '"> ',
            k += d(a.text),
            k += " </option> "
        }),
        k += ' </select> <input class="survey_form_input survey_form_input_other" style="display: none; vertical-align: middle;" type="text" id="option_',
        k += d(f),
        k += '_other_text\'" value=""/> </div> </div> </div> ',
        new String(k)
    })
});
define("amd-templates/common/pc/survey/questions/text", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/text",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return k += f
        },
        h = a.size,
        i = a.maxlength,
        j = a.defaultValue,
        k = "";
        return k += '<div class="question question_',
        k += d(e),
        k += '" data-type="',
        k += d(e),
        k += '" id="question_',
        k += d(f),
        k += '" data-id="',
        k += d(f),
        k += '"> <div class="inner"> ',
        g("./title"),
        k += ' <div class="inputs"> <input aria-labelledby="questionTitle_',
        k += d(f),
        k += '" class="survey_form_input" type="text" size="',
        k += d(h),
        k += '" maxlength="',
        k += d(i),
        k += '" id="text_',
        k += d(f),
        k += '" name="answer_',
        k += d(f),
        k += '" value="',
        k += d(j),
        k += '" /> </div> </div> </div> ',
        new String(k)
    })
});
define("amd-templates/common/pc/survey/questions/textarea", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/textarea",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return k += f
        },
        h = a.rows,
        i = a.cols,
        j = a.defaultValue,
        k = "";
        return k += '<div class="question question_',
        k += d(e),
        k += '" data-type="',
        k += d(e),
        k += '" id="question_',
        k += d(f),
        k += '" data-id="',
        k += d(f),
        k += '"> <div class="inner"> ',
        g("./title"),
        k += ' <div class="inputs"> <textarea role="textbox" aria-labelledby="questionTitle_',
        k += d(f),
        k += '" class="survey_form_input" id="textarea_',
        k += d(f),
        k += '" name="answer_',
        k += d(f),
        k += '" rows="',
        k += h ? d(h) : "5",
        k += '" cols="',
        k += i ? d(i) : "45",
        k += '">',
        k += d(j),
        k += "</textarea> </div> </div> </div> ",
        new String(k)
    })
});
define("amd-templates/common/pc/survey/questions/star", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/star",
    function(a, b) {
        "use strict";
        var c = this,
        d = c.$helpers,
        e = c.$escape,
        f = a.type,
        g = a.id,
        h = a.required,
        i = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return r += f
        },
        j = a.revertSort,
        k = a.starType,
        l = a.starShowCustomStart,
        m = a.starShow,
        n = c.$each,
        o = d.number2array,
        p = a.starNum,
        q = (a.value, a.index, a.starShowCustomEnd),
        r = "";
        return r += '<div class="question question_',
        r += e(f),
        r += '" data-type="',
        r += e(f),
        r += '" id="question_',
        r += e(g),
        r += '" data-id="',
        r += e(g),
        r += '" role="radiogroup" data-role="radiogroup" ',
        h && (r += ' aria-required="true" '),
        r += '> <div class="inner"> ',
        i("./title"),
        r += " ",
        j ? (r += ' <div class="inputs"> <div class="rating_area rating_type_', r += e(k), r += '"> <span class="star_tip"">', r += e(q ? q: "非常" + m), r += "</span> ", n(o(p),
        function(a) {
            r += ' <div class="star_item" role="radio" tabindex="-1" aria-checked="false"> <input name="answer_',
            r += e(g),
            r += '" type="radio" class="rating survey_form_rating" value="',
            r += e(p - a + 1),
            r += '" id="option_',
            r += e(g),
            r += "_",
            r += e(p - a + 1),
            r += '"/> <label for="option_',
            r += e(g),
            r += "_",
            r += e(p - a + 1),
            r += '"> <i class="survey_form_ui"></i> <span>',
            r += e(p - a + 1),
            r += "</span> </label> </div> "
        }), r += ' <span class="star_tip">', r += e(l ? l: "非常不" + m), r += "</span> </div> </div> ") : (r += ' <div class="inputs"> <div class="rating_area rating_type_', r += e(k), r += '"> <span class="star_tip">', r += e(l ? l: "非常不" + m), r += "</span> ", n(o(p),
        function(a) {
            r += ' <div class="star_item" role="radio" tabindex="-1" aria-checked="false"> <input name="answer_',
            r += e(g),
            r += '" type="radio" class="rating survey_form_rating" value="',
            r += e(a),
            r += '" id="option_',
            r += e(g),
            r += "_",
            r += e(a),
            r += '"/> <label for="option_',
            r += e(g),
            r += "_",
            r += e(a),
            r += '"> <i class="survey_form_ui"></i> <span>',
            r += e(a),
            r += "</span> </label> </div> "
        }), r += ' <span class="star_tip">', r += e(q ? q: "非常" + m), r += "</span> </div> </div> "),
        r += " </div> </div> ",
        new String(r)
    })
});
define("amd-templates/common/pc/survey/questions/matrix_radio", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/matrix_radio",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return m += f
        },
        h = a.refer,
        i = c.$each,
        j = a.options,
        k = (a.option, a.index, c.$string),
        l = a.subTitles,
        m = (a.subTitle, "");
        return m += '<div class="question question_',
        m += d(e),
        m += '" data-type="',
        m += d(e),
        m += '" id="question_',
        m += d(f),
        m += '" data-id="',
        m += d(f),
        m += '"> <div class="inner"> ',
        g("./title"),
        m += " ",
        h ? (m += ' <div class="no_refer_answer">此题选项来源于<b class="question_index" data-for="', m += d(h), m += '"></b>题中选项，请先填写第<b class="question_index" data-for="', m += d(h), m += '"></b>题</div> <div class="inputs" style="display:none;"> ') : m += ' <div class="inputs"> ',
        m += ' <div class="matrix_group table_scroll"> <table> <thead> <tr> <td></td> ',
        i(j,
        function(a, b) {
            m += ' <th class="option_title" scope="col"> <div class="option_text"> <label id="',
            m += d(f + a.id + b),
            m += '">',
            m += k(a.text),
            m += "</label> </div> </th> "
        }),
        m += " </tr> </thead> <tbody> ",
        i(l,
        function(a) {
            m += ' <tr class="matrix_item" data-tid="',
            m += d(a.id),
            m += '" data-role="',
            m += d("matrix_checkbox" === e ? "checkboxgroup": "radiogroup"),
            m += '" role="group"> <th class="sub_title" scope="row"> <div class="subtitle_text">',
            m += k(a.text),
            m += "</div> </th> ",
            i(j,
            function(b, c) {
                m += ' <td> <div class="matrix_option" role="',
                m += d("matrix_checkbox" === e ? "checkbox": "radio"),
                m += '" tabindex="-1" aria-checked="false" aria-labelledby="',
                m += d(f + b.id + c),
                m += '" aria-label="',
                m += d(b.text),
                m += '">  <input data-oid=',
                m += d(b.id),
                m += ' class="survey_form_checkbox" name="answer_',
                m += d(f),
                m += "_",
                m += d(a.id),
                m += '" id="option_',
                m += d(f),
                m += "_",
                m += d(a.id),
                m += "_",
                m += d(b.id),
                m += '" type="',
                m += d(e.slice(7)),
                m += '" /> <label for="option_',
                m += d(f),
                m += "_",
                m += d(a.id),
                m += "_",
                m += d(b.id),
                m += '"> <i class="survey_form_ui"></i> </label> </div> </td> '
            }),
            m += " </tr> "
        }),
        m += " </tbody> </table> </div> </div> </div> </div> ",
        new String(m)
    })
});
define("amd-templates/common/pc/survey/questions/matrix_checkbox", ["../../../../template", "./matrix_radio"],
function(a) {
    return a("common/pc/survey/questions/matrix_checkbox",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("./matrix_radio"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/pc/survey/questions/matrix_star", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/matrix_star",
    function(a, b) {
        "use strict";
        var c = this,
        d = c.$helpers,
        e = c.$escape,
        f = a.type,
        g = a.id,
        h = a.revertSort,
        i = a.starNum,
        j = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return s += f
        },
        k = a.refer,
        l = a.starShowCustomStart,
        m = a.starShow,
        n = a.starShowCustomEnd,
        o = c.$each,
        p = a.subTitles,
        q = (a.subTitle, a.index, c.$string),
        r = d.number2array,
        s = (a.value, "");
        return s += '<div class="question question_',
        s += e(f),
        s += '" data-type="',
        s += e(f),
        s += '" id="question_',
        s += e(g),
        s += '" data-id="',
        s += e(g),
        s += '" data-revert="',
        s += e(h),
        s += '" data-optionnum="',
        s += e(i),
        s += '" > <div class="inner"> ',
        j("./title"),
        s += " ",
        k && (s += ' <div class="no_refer_answer">此题选项来源于<b class="question_index" data-for="', s += e(k), s += '"></b>题中选项，请先填写第<b class="question_index" data-for="', s += e(k), s += '"></b>题</div> '),
        s += ' <div class="inputs" ',
        k && (s += 'style="display:none;"'),
        s += '> <div class="matrix_group table_scroll"> ',
        i > 7 ? (s += ' <table> <thead> <tr> <td class="sub_title" scope="col"></td> <th scope="col"> <div class="star_tips"> ', h ? (s += ' <div class="star_tips_first"> ', s += e(n ? n: "非常" + m), s += ' </div> <div class="star_tips_last"> ', s += e(l ? l: "非常不" + m), s += " </div> ") : (s += ' <div class="star_tips_first"> ', s += e(l ? l: "非常不" + m), s += ' </div> <div class="star_tips_last"> ', s += e(n ? n: "非常" + m), s += " </div> "), s += " </div> </th> </tr> </thead> <tbody> ", o(p,
        function(a) {
            s += ' <tr class="matrix_item" data-tid="',
            s += e(a.id),
            s += '"> <td class="sub_title" scope="row"> <div class="subtitle_text"> ',
            s += q(a.text),
            s += ' </div> </td> <td> <div class="star_bar_container" data-aria="slider" tabIndex="0"> <div class="star_bar"> <input class="star_range" type="range" min="1" step = "1" max="',
            s += e(i),
            s += '" aria-valuemin="1" aria-valuemax="',
            s += e(i),
            s += '" aria-valuenow="',
            s += e(i / 2),
            s += '" role="slider" > </div> </div> </td> </tr> '
        }), s += " </tbody> </table> ") : (s += ' <table> <thead> <tr> <td class="sub_title"></td> ', o(r(i),
        function(a, b) {
            s += " <th> ",
            0 === b && (s += ' <div class="star_tips"> ', h ? (s += " ", s += e(n ? n: "非常" + m), s += " ") : (s += " ", s += e(l ? l: "非常不" + m), s += " "), s += " </div> "),
            s += " ",
            b === i - 1 && (s += ' <div class="star_tips"> ', h ? (s += " ", s += e(l ? l: "非常不" + m), s += " ") : (s += " ", s += e(n ? n: "非常" + m), s += " "), s += " </div> "),
            s += " </th> "
        }), s += " </tr> </thead> <tbody> ", o(p,
        function(a) {
            s += ' <tr class="matrix_item" data-tid="',
            s += e(a.id),
            s += '" data-role="radiogroup"> <th class="sub_title"> <div class="subtitle_text"> ',
            s += q(a.text),
            s += " </div> </th> ",
            o(r(i),
            function(b) {
                s += ' <td> <div class="matrix_option" role="radio" tabindex="-1" aria-checked="false"> ',
                h ? (s += ' <div class="star_item"> <input name="answer_', s += e(g), s += "_", s += e(a.id), s += '" type="radio" class="rating survey_form_rating" value="', s += e(i - b + 1), s += '" id="option_', s += e(g), s += "_", s += e(a.id), s += "_", s += e(i - b + 1), s += '" /> <label for="option_', s += e(g), s += "_", s += e(a.id), s += "_", s += e(i - b + 1), s += '"> <i class="survey_form_ui"></i> <span>', s += e(i - b + 1), s += "</span> </label> </div> ") : (s += ' <div class="star_item"> <input name="answer_', s += e(g), s += "_", s += e(a.id), s += '" type="radio" class="rating survey_form_rating" value="', s += e(b), s += '" id="option_', s += e(g), s += "_", s += e(a.id), s += "_", s += e(b), s += '" /> <label for="option_', s += e(g), s += "_", s += e(a.id), s += "_", s += e(b), s += '"> <i class="survey_form_ui"></i> <span>', s += e(b), s += "</span> </label> </div> "),
                s += " </div> </td> "
            }),
            s += " </tr> "
        }), s += " </tbody> </table> "),
        s += " </div> </div> </div> </div> ",
        new String(s)
    })
});
define("amd-templates/common/pc/survey/questions/sort", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/sort",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return l += f
        },
        h = a.refer,
        i = c.$each,
        j = a.options,
        k = (a.option, a.index, c.$string),
        l = "";
        return l += '<div class="question question_',
        l += d(e),
        l += '" id="question_',
        l += d(f),
        l += '" data-type="',
        l += d(e),
        l += '" data-id="',
        l += d(f),
        l += '" data-role="sortgroup" > <div class="inner"> ',
        g("./title"),
        l += " ",
        h && (l += ' <div class="no_refer_answer">此题选项来源于<b class="question_index" data-for="', l += d(h), l += '"></b>题中选项，请先填写第<b class="question_index" data-for="', l += d(h), l += '"></b>题</div> '),
        l += ' <div class="inputs" ',
        h && (l += 'style="display:none;"'),
        l += '> <div class="sort_tip"> Tips:拖动或点击右侧的选项到左边的次序位置进行排序 </div> <div class="sort_container"> <div class="index_container"> ',
        i(j,
        function(a, b) {
            l += ' <div class="index"> ',
            l += d(b + 1),
            l += " </div> "
        }),
        l += ' </div> <div class="answer_container"></div> <div class="option_container"> ',
        i(j,
        function(a) {
            l += ' <div class="sort_option_item" data-oid="',
            l += d(a.id),
            l += '" role="option" aria-dropeffect="move" tabindex="-1" aria-checked="false"> <div class="text">',
            l += k(a.text),
            l += '</div> <div class="handle"> <i class="ico" role="tooltip" alt="点击或者拖动进行排序" aria-hidden="true"></i> </div> </div> '
        }),
        l += " </div> </div> </div> </div> </div> ",
        new String(l)
    })
});
define("amd-templates/common/pc/survey/questions/chained_selects", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/chained_selects",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return k += f
        },
        h = a.refer,
        i = c.$each,
        j = a.levels,
        k = (a.level, a.i, "");
        return k += '<div class="question question_',
        k += d(e),
        k += '" id="question_',
        k += d(f),
        k += '" data-type="',
        k += d(e),
        k += '" data-id="',
        k += d(f),
        k += '" data-role="chainsgroup"> <div class="inner"> ',
        g("./title"),
        k += ' <div class="inputs" style="',
        h && (k += "display: none;"),
        k += '"> <div class="options_container"> ',
        i(j,
        function(a, b) {
            k += ' <div class="level" data-role="chainsgroupLever"> <label class="name" for="question_',
            k += d(f),
            k += "_options_",
            k += d(b),
            k += '"><b data-level="',
            k += d(b + 1),
            k += '">',
            k += d(a),
            k += '</b></label> <div class="options" data-level="',
            k += d(b + 1),
            k += '" id="question_',
            k += d(f),
            k += "_options_",
            k += d(b),
            k += '" role="radiogroup"></div> </div> '
        }),
        k += " </div> </div> </div> </div> ",
        new String(k)
    })
});
define("amd-templates/common/pc/survey/questions/upload", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/upload",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return h += f
        },
        h = "";
        return h += '<div class="question question_',
        h += d(e),
        h += '" data-type="',
        h += d(e),
        h += '" id="question_',
        h += d(f),
        h += '" data-id="',
        h += d(f),
        h += '"> <div class="inner"> ',
        g("./title"),
        h += ' <div class="inputs"> <div class="fileupload_wrap"> <div class="empty"> <input aria-labelledby="questionTitle_',
        h += d(f),
        h += '" type="file" name="file" class="fileupload" id="fileupload_',
        h += d(f),
        h += '" /> <div class="tip"> <div class="tip_content"> <i class="ico ico_upload"></i> <p> <span style="color: #58a6e7;">点击添加文件</span><span class="support_dnd" style="color: #333;">或将文件拖至此处</span> <br /> <span style="color: #999;">文件大小限制: 5M </span> </p> </div> </div> </div> <div class="uploading"> <img src="" class="thumbnail" /> <div class="uploadingbar"> <img src="/image/uploading.gif" alt="正在上传中" /> </div> <i class="ico ico_filetype"></i> <div class="progress_bar"> <div class="loaded"></div> <div class="total"></div> </div> <div class="info"> <span class="filename"></span> <span class="progress">(<span class="loaded"></span>/<span class="total"></span>)</span> <span style="color: #58a6e7; cursor: pointer" class="abort">取消</span> </div> </div> <div class="uploaded"> <img src="" class="thumbnail"/> <i class="ico ico_filetype"></i> <div class="info"> <span class="filename"></span> <span class="filesize"></span> <span style="color: #58a6e7; cursor: pointer" class="cancel">删除</span> </div> </div> </div> </div> </div> </div> ',
        new String(h)
    })
});
define("amd-templates/common/pc/survey/questions/description", ["../../../../template"],
function(a) {
    return a("common/pc/survey/questions/description",
    function(a) {
        "use strict";
        var b = this,
        c = (b.$helpers, b.$escape),
        d = a.type,
        e = a.id,
        f = b.$string,
        g = a.title,
        h = "";
        return h += '<div class="question question_',
        h += c(d),
        h += '" id="question_',
        h += c(e),
        h += '" data-type="',
        h += c(d),
        h += '" data-id="',
        h += c(e),
        h += '"> <div class="inner"> <div class="description_content"> ',
        h += f(g),
        h += " </div> </div> </div> ",
        new String(h)
    })
});
define("amd-templates/common/pc/survey/questions/text_multiple", ["../../../../template", "./title"],
function(a) {
    return a("common/pc/survey/questions/text_multiple",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return h += f
        },
        h = "";
        return h += '<div class="question question_',
        h += d(e),
        h += '" data-type="',
        h += d(e),
        h += '" id="question_',
        h += d(f),
        h += '" data-id="',
        h += d(f),
        h += '"> <div class="inner"> ',
        g("./title"),
        h += " </div> </div> ",
        new String(h)
    })
});
define("amd-templates/common/pc/survey/question", ["../../../template", "./questions/radio", "./questions/checkbox", "./questions/select", "./questions/text", "./questions/textarea", "./questions/star", "./questions/matrix_radio", "./questions/matrix_checkbox", "./questions/matrix_star", "./questions/sort", "./questions/chained_selects", "./questions/upload", "./questions/description", "./questions/text_multiple"],
function(a) {
    return a("common/pc/survey/question",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, a.type),
        e = function(d, e) {
            e = e || a;
            var g = c.$include(d, e, b);
            return f += g
        },
        f = "";
        return "radio" == d && (f += " ", e("./questions/radio"), f += " "),
        f += " ",
        "checkbox" == d && (f += " ", e("./questions/checkbox"), f += " "),
        f += " ",
        "select" == d && (f += " ", e("./questions/select"), f += " "),
        f += " ",
        "text" == d && (f += " ", e("./questions/text"), f += " "),
        f += " ",
        "textarea" == d && (f += " ", e("./questions/textarea"), f += " "),
        f += " ",
        "star" == d && (f += " ", e("./questions/star"), f += " "),
        f += " ",
        "matrix_radio" == d && (f += " ", e("./questions/matrix_radio"), f += " "),
        f += " ",
        "matrix_checkbox" == d && (f += " ", e("./questions/matrix_checkbox"), f += " "),
        f += " ",
        "matrix_star" == d && (f += " ", e("./questions/matrix_star"), f += " "),
        f += " ",
        "sort" == d && (f += " ", e("./questions/sort"), f += " "),
        f += " ",
        "chained_selects" == d && (f += " ", e("./questions/chained_selects"), f += " "),
        f += " ",
        "upload" == d && (f += " ", e("./questions/upload"), f += " "),
        f += " ",
        "description" == d && (f += " ", e("./questions/description"), f += " "),
        f += " ",
        "text_multiple" === d && (f += " ", e("./questions/text_multiple"), f += " "),
        f += " ",
        new String(f)
    })
});
define("amd-templates/common/mobile/survey/questions/radio", ["../../../../template", "../../../pc/survey/questions/radio"],
function(a) {
    return a("common/mobile/survey/questions/radio",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/radio"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/checkbox", ["../../../../template", "./radio"],
function(a) {
    return a("common/mobile/survey/questions/checkbox",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("./radio"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/select", ["../../../../template", "../../../pc/survey/questions/select"],
function(a) {
    return a("common/mobile/survey/questions/select",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/select"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/text", ["../../../../template", "../../../pc/survey/questions/text"],
function(a) {
    return a("common/mobile/survey/questions/text",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/text"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/textarea", ["../../../../template", "../../../pc/survey/questions/textarea"],
function(a) {
    return a("common/mobile/survey/questions/textarea",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/textarea"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/star", ["../../../../template", "../../../pc/survey/questions/star"],
function(a) {
    return a("common/mobile/survey/questions/star",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/star"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/matrix_radio", ["../../../../template", "../../../pc/survey/questions/matrix_radio"],
function(a) {
    return a("common/mobile/survey/questions/matrix_radio",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/matrix_radio"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/matrix_checkbox", ["../../../../template", "./matrix_radio"],
function(a) {
    return a("common/mobile/survey/questions/matrix_checkbox",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("./matrix_radio"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/matrix_star", ["../../../../template", "../../../pc/survey/questions/matrix_star"],
function(a) {
    return a("common/mobile/survey/questions/matrix_star",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/matrix_star"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/title", ["../../../../template", "../../../pc/survey/questions/title"],
function(a) {
    return a("common/mobile/survey/questions/title",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/title"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/sort", ["../../../../template", "./title"],
function(a) {
    return a("common/mobile/survey/questions/sort",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return l += f
        },
        h = a.refer,
        i = c.$each,
        j = a.options,
        k = (a.option, a.index, c.$string),
        l = "";
        return l += '<div class="question question_',
        l += d(e),
        l += '" id="question_',
        l += d(f),
        l += '" data-type="',
        l += d(e),
        l += '" data-id="',
        l += d(f),
        l += '"> <div class="inner"> ',
        g("./title"),
        l += " ",
        h && (l += ' <div class="no_refer_answer">此题选项来源于<b class="question_index" data-for="', l += d(h), l += '"></b>题中选项，请先填写第<b class="question_index" data-for="', l += d(h), l += '"></b>题</div> '),
        l += ' <div class="inputs" ',
        h && (l += 'style="display:none;"'),
        l += '> <div class="sort_tip"> Tips:拖动右边把手排序 </div> <div class="sort_container"> <div class="index_container"> ',
        i(j,
        function(a, b) {
            l += ' <div class="index"> ',
            l += d(b + 1),
            l += " </div> "
        }),
        l += ' </div> <div class="answer_container"> ',
        i(j,
        function(a) {
            l += ' <div class="sort_option_item" data-oid="',
            l += d(a.id),
            l += '" role="radio" tabindex="-1" aria-checked="false"> <div class="text">',
            l += k(a.text),
            l += '</div> <div class="handle"> <i class="ico"></i> </div> </div> '
        }),
        l += " </div> </div> </div> </div> </div> ",
        new String(l)
    })
});
define("amd-templates/common/mobile/survey/questions/chained_selects", ["../../../../template", "./title"],
function(a) {
    return a("common/mobile/survey/questions/chained_selects",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return k += f
        },
        h = a.refer,
        i = c.$each,
        j = a.levels,
        k = (a.level, a.index, "");
        return k += '<div class="question question_',
        k += d(e),
        k += '" id="question_',
        k += d(f),
        k += '" data-type="',
        k += d(e),
        k += '" data-id="',
        k += d(f),
        k += '"> <div class="inner"> ',
        g("./title"),
        k += ' <div class="inputs" style="',
        h && (k += "display: none;"),
        k += '"> ',
        i(j,
        function(a, b) {
            k += ' <div class="level" style="display: none;"> <b>',
            k += d(a),
            k += '</b> <select class="chained_select" data-level="',
            k += d(b + 1),
            k += '" selected > <option disabled selected value>--请选择',
            k += d(a),
            k += "--</option> </select> </div> "
        }),
        k += " </div> </div> </div> ",
        new String(k)
    })
});
define("amd-templates/common/mobile/survey/questions/upload", ["../../../../template", "../../../pc/survey/questions/upload"],
function(a) {
    return a("common/mobile/survey/questions/upload",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/upload"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/description", ["../../../../template", "../../../pc/survey/questions/description"],
function(a) {
    return a("common/mobile/survey/questions/description",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers,
        function(d, f) {
            f = f || a;
            var g = c.$include(d, f, b);
            return e += g
        }),
        e = "";
        return d("../../../pc/survey/questions/description"),
        e += " ",
        new String(e)
    })
});
define("amd-templates/common/mobile/survey/questions/text_multiple", ["../../../../template", "./title"],
function(a) {
    return a("common/mobile/survey/questions/text_multiple",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, c.$escape),
        e = a.type,
        f = a.id,
        g = function(d, e) {
            e = e || a;
            var f = c.$include(d, e, b);
            return h += f
        },
        h = "";
        return h += '<div class="question question_',
        h += d(e),
        h += '" data-type="',
        h += d(e),
        h += '" id="question_',
        h += d(f),
        h += '" data-id="',
        h += d(f),
        h += '"> <div class="inner"> ',
        g("./title"),
        h += " </div> </div> ",
        new String(h)
    })
});
define("amd-templates/common/mobile/survey/question", ["../../../template", "./questions/radio", "./questions/checkbox", "./questions/select", "./questions/text", "./questions/textarea", "./questions/star", "./questions/matrix_radio", "./questions/matrix_checkbox", "./questions/matrix_star", "./questions/sort", "./questions/chained_selects", "./questions/upload", "./questions/description", "./questions/text_multiple"],
function(a) {
    return a("common/mobile/survey/question",
    function(a, b) {
        "use strict";
        var c = this,
        d = (c.$helpers, a.type),
        e = function(d, e) {
            e = e || a;
            var g = c.$include(d, e, b);
            return f += g
        },
        f = "";
        return "radio" == d && (f += " ", e("./questions/radio"), f += " "),
        f += " ",
        "checkbox" == d && (f += " ", e("./questions/checkbox"), f += " "),
        f += " ",
        "select" == d && (f += " ", e("./questions/select"), f += " "),
        f += " ",
        "text" == d && (f += " ", e("./questions/text"), f += " "),
        f += " ",
        "textarea" == d && (f += " ", e("./questions/textarea"), f += " "),
        f += " ",
        "star" == d && (f += " ", e("./questions/star"), f += " "),
        f += " ",
        "matrix_radio" == d && (f += " ", e("./questions/matrix_radio"), f += " "),
        f += " ",
        "matrix_checkbox" == d && (f += " ", e("./questions/matrix_checkbox"), f += " "),
        f += " ",
        "matrix_star" == d && (f += " ", e("./questions/matrix_star"), f += " "),
        f += " ",
        "sort" == d && (f += " ", e("./questions/sort"), f += " "),
        f += " ",
        "chained_selects" == d && (f += " ", e("./questions/chained_selects"), f += " "),
        f += " ",
        "upload" == d && (f += " ", e("./questions/upload"), f += " "),
        f += " ",
        "description" == d && (f += " ", e("./questions/description"), f += " "),
        f += " ",
        "text_multiple" === d && (f += " ", e("./questions/text_multiple"), f += " "),
        f += " ",
        new String(f)
    })
});
define("amd-templates/survey/common/gift_dialog", ["../../template"],
function(a) {
    return a("survey/common/gift_dialog",
    function(a) {
        "use strict";
        var b = this,
        c = (b.$helpers, b.$escape),
        d = a.imgSrc,
        e = "";
        return e += '<div class="giftDialog"> <div class="giftDialogInner"> <div class="giftHeader"> <div class="box"> <div class="float_1"></div> <div class="float_2"></div> </div> <div class="hideCover"> <div class="hideCoverLeft"></div> <div class="hideCoverRight"></div> </div> <div class="qrRegion"> <div id="qrcode" class="qrcode"><img src=',
        e += c(d),
        e += ' alt=""></div> </div> </div> <div class="giftFooter"> <p>请使用QQ或微信扫描二维码抽奖，</p> <p>该二维码限扫一次！</p> </div> <a href="#" class="close" id="close"></a> </div> </div> ',
        new String(e)
    })
});
define("upload", ["require", "jquery"],
function(a) {
    var b = a("jquery"); (function() {
        var a = function() {};
        var b = window.console = window.console || {};
        if (!b.log) {
            b.log = a
        }
    })();
    if (b.fn.upload) {
        return
    }
    b.extend(b.fn, {
        upload: function(a) {
            var c = this;
            var d = c.prop("id");
            var e = a.uploader;
            var f = a.formData || {};
            var g = a.success ||
            function() {};
            var h = a.progress ||
            function() {};
            var i = a.error ||
            function() {};
            var j = a.abort ||
            function() {};
            var k = "jQUploadForm-" + d;
            var l = "jQUpladFrame-" + d;
            var m = function() {
                return window.FileReader && window.File && window.FileList && window.Blob
            };
            var n = function(a, c, d, e) {
                var g = '<form  action="' + e + '" method="POST" id="' + c + '" enctype="multipart/form-data" target="' + d + '"></form>';
                var h = b('<iframe id="' + d + '" name="' + d + '" style="display: none;"></iframe>');
                a.wrap(g);
                var i = b("#" + c);
                h.insertAfter(i);
                b.each(f,
                function(a, c) {
                    b('<input type="hidden" name="' + c.name + '" value="' + c.value + '">').appendTo(i)
                });
                b('<input type="hidden" name="submit_flag" value="iframe">').appendTo(i)
            };
            var o = function(a, c, d) {
                b("#" + d).on("load",
                function() {
                    console.log("iframe loaded");
                    var e = b(this).contents().find("body").text();
                    p(a, c, d);
                    if (typeof g == "function") {
                        g.call(this, e)
                    }
                });
                b("#" + c).submit()
            };
            var p = function(a, c, d) {
                var e = b("#" + c);
                var f = b("#" + d);
                a.insertBefore(e);
                e.remove();
                f.remove()
            };
            if (m()) {
                return b.uploadFilesByXHR(b.extend(true, {},
                a, {
                    name: c.attr("name"),
                    files: c.prop("files")
                }))
            } else {
                n(c, k, l, e);
                o(c, k, l);
                return {
                    abort: function() {
                        a.abort && a.abort()
                    }
                }
            }
        }
    });
    b.extend({
        uploadFilesByXHR: function(a) {
            if (!window.XMLHttpRequest) {
                alert("XMLHttpRequest not support");
                return
            }
            var b = a.name;
            var d = a.files;
            var e = a.uploader;
            var f = a.fileSizeLimit;
            var g = a.fileTypeExts;
            var h = a.formData || {};
            var i = a.success ||
            function() {};
            var j = a.progress ||
            function() {};
            var k = a.error ||
            function() {};
            var l = a.abort ||
            function() {};
            var m = a.typeError ||
            function() {};
            var n = a.sizeError ||
            function() {};
            var o = a.emptyError ||
            function() {};
            var p = function() {
                return window.FileReader && window.File && window.FileList && window.Blob
            };
            var q = function(a, e) {
                var f = new RegExp(a, "i");
                var g = [];
                for (var h = 0,
                i = d.length; h < i; h++) {
                    var j = d[h].name;
                    if (!f.test(c(j))) {
                        m(d[h], a);
                        return false
                    }
                    if (d[h].size > e) {
                        n(d[h], e);
                        return false
                    }
                    g.push({
                        name: b,
                        value: d[h]
                    })
                }
                if (!g.length) {
                    o()
                }
                return g
            };
            var r = function(a) {
                var b = new FormData;
                a = a.concat(h);
                for (var c = 0,
                d = a.length; c < d; c++) {
                    b.append(a[c].name, a[c].value)
                }
                return b
            };
            var s = function(a, b) {
                var c = new XMLHttpRequest;
                c.open("POST", b, true);
                c.upload.addEventListener("progress", j, false);
                c.addEventListener("load",
                function() {
                    var a = c.responseText;
                    if (typeof i == "function") {
                        i.call(this, a)
                    }
                },
                false);
                c.addEventListener("error", k, false);
                c.addEventListener("abort", l, false);
                c.send(a);
                return c
            };
            if (p()) {
                var t = q(g, f);
                if (t) {
                    var u = r(t);
                    return s(u, e)
                }
            }
        }
    });
    function c(a) {
        var b = /.*\.(\w+)$/.exec(a);
        return b ? b[1] : null
    }
});
define("scrollIntoViewIfNeeded", ["require", "exports", "module", "jquery"],
function(a, b, c) {
    var d = a("jquery");
    d.fn.scrollIntoView = function() {
        if (this.length > 0) this[0].scrollIntoView();
        return this
    };
    d.fn.scrollIntoViewIfNeeded = function(a) {
        if (this.length > 0) {
            a = a[0];
            var b = this[0],
            c = a.getBoundingClientRect(),
            d = b.getBoundingClientRect();
            if (d.top < c.top || d.bottom > c.bottom) {
                b.scrollIntoView()
            }
        }
        return this
    }
});
define("app/util/sortable", ["require", "jquery"],
function(a) {
    var b = a("jquery");
    var c = window.Modernizr && Modernizr.touch === true ||
    function() {
        return !! ("ontouchstart" in window || window.DocumentTouch && document instanceof DocumentTouch)
    } ();
    var d = {
        start: c ? "touchstart": "mousedown",
        move: c ? "touchmove": "mousemove",
        end: c ? "touchend": "mouseup"
    };
    function e(a, b) {
        a.each(function() {
            var a = this.style;
            a.webkitTransform = a.MsTransform = a.msTransform = a.MozTransform = a.OTransform = a.transform = b
        })
    }
    return {
        init: function(a, f) {
            var g, h, i, j, k, l, m, n, o, p, q, a;
            function r(c) {
                c = c.originalEvent;
                h = false;
                g = true;
                i = c.type === "touchstart" ? c.targetTouches[0].pageY: c.pageY;
                k = b(this).closest(".sort_option_item");
                m = a.children();
                c.preventDefault();
                f.onTouchStart && f.onTouchStart()
            }
            function s(c) {
                c = c.originalEvent;
                if (!g || !k) return;
                var d = c.type === "touchmove" ? c.targetTouches[0].pageX: c.pageX;
                var r = c.type === "touchmove" ? c.targetTouches[0].pageY: c.pageY;
                if (!h) {
                    k.addClass("sorting");
                    a.addClass("sortable-sorting");
                    n = k[0].offsetTop;
                    o = k.parent().height() - k[0].offsetTop - k.height();
                    l = k[0].offsetHeight
                }
                h = true;
                c.preventDefault();
                j = r - i;
                var s = j;
                if (s < -n) s = -n;
                if (s > o) s = o;
                e(k, "translate3d(0," + s + "px,0)");
                q = p = undefined;
                m.each(function() {
                    var a = b(this);
                    if (a[0] === k[0]) return;
                    var c = a[0].offsetTop;
                    var d = a.height();
                    var f = k[0].offsetTop + s;
                    if (f >= c - d / 2 && k.index() < a.index()) {
                        e(a, "translate3d(0, " + -l + "px,0)");
                        p = a;
                        q = undefined
                    } else if (f <= c + d / 2 && k.index() > a.index()) {
                        e(a, "translate3d(0, " + l + "px,0)");
                        p = undefined;
                        if (!q) q = a
                    } else {
                        e(b(this), "translate3d(0, 0%,0)")
                    }
                });
                f.onTouchMove && f.onTouchMove()
            }
            function t(b) {
                b = b.originalEvent;
                if (!g || !h) {
                    g = false;
                    h = false;
                    return
                }
                b.preventDefault();
                e(m, "");
                k.removeClass("sorting");
                a.removeClass("sortable-sorting");
                var c, d, i;
                if (p) {
                    k.insertAfter(p);
                    k.trigger("sort")
                }
                if (q) {
                    k.insertBefore(q);
                    k.trigger("sort")
                }
                if ((p || q) && a.hasClass("virtual-list")) {
                    c = a[0].f7VirtualList;
                    d = k[0].f7VirtualListIndex;
                    i = q ? q[0].f7VirtualListIndex: p[0].f7VirtualListIndex;
                    if (c) c.moveItem(d, i)
                }
                p = q = undefined;
                g = false;
                h = false;
                f.onTouchEnd && f.onTouchEnd()
            }
            b(a).on(d.start, ".handle", r);
            if (c) {
                b(a).on(d.move, ".handle", s);
                b(a).on(d.end, ".handle", t)
            } else {
                b(a).on(d.move, s);
                b(a).on(d.end, t)
            }
        }
    }
});
define("app/survey/common/views/survey", ["require", "exports", "module", "jquery", "bowser", "underscore", "bind_events", "art-dialog/dialog", "dragula", "fastclick", "app/common/api/survey", "app/common/views/getQuestionInput", "./reward", "./survey_title", "./survey_prefix", "./survey_suffix", "app/common/views/footer", "app/util/i18next", "../model/report_page", "./survey_progress", "app/common/views/survey_style", "app/common/views/table_freeze", "app/common/views/question", "../model/mobile_guide", "app/util/richtext", "./accessibility/RadioGroup", "./accessibility/SortGroup", "./accessibility/CheckboxGroup", "./accessibility/ChainsGroup", "./accessibility/SliderGroup", "amd-templates/survey/common/survey_wrap", "amd-templates/survey/common/copy_right", "amd-templates/common/pc/survey/questions/chained_selects/option", "amd-templates/common/pc/survey/question", "amd-templates/common/mobile/survey/question", "amd-templates/survey/common/gift_dialog", "app/common/models/user", "upload", "scrollIntoViewIfNeeded", "app/util/sortable"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("bowser");
    var f = a("underscore");
    var g = a("bind_events");
    var h = a("art-dialog/dialog");
    var i = a("dragula");
    var j = a("fastclick");
    var k = a("app/common/api/survey");
    var l = a("app/common/views/getQuestionInput");
    var m = a("./reward");
    var n = a("./survey_title");
    var o = a("./survey_prefix");
    var p = a("./survey_suffix");
    var q = a("app/common/views/footer");
    var r = a("app/util/i18next");
    var s = a("../model/report_page");
    var t = a("./survey_progress");
    var u = a("app/common/views/survey_style");
    var v = a("app/common/views/table_freeze");
    var w = a("app/common/views/question");
    var x = a("../model/mobile_guide");
    var y = a("app/util/richtext").replaceFillBlank;
    var z = a("./accessibility/RadioGroup");
    var A = a("./accessibility/SortGroup");
    var B = a("./accessibility/CheckboxGroup");
    var C = a("./accessibility/ChainsGroup");
    var D = a("./accessibility/SliderGroup");
    var E = a("amd-templates/survey/common/survey_wrap");
    var F = a("amd-templates/survey/common/copy_right");
    var G = a("amd-templates/common/pc/survey/questions/chained_selects/option");
    var H = a("amd-templates/common/pc/survey/question");
    var I = a("amd-templates/common/mobile/survey/question");
    var J = a("amd-templates/survey/common/gift_dialog");
    var K = a("app/common/models/user");
    a("upload");
    a("scrollIntoViewIfNeeded");
    var L = a("app/util/sortable");
    var M = d(document).width() < 800;
    var N = false;
    var O = window.navigator.userAgent.toLowerCase().indexOf("tbs") > 0;
    try {
        N = top !== window && top.location.pathname === "/edit.html"
    } catch(P) {}
    var Q = function(a) {
        this.$el = d(".g_survey");
        this.surveyModel = a.surveyModel;
        this.init()
    };
    f.extend(Q.prototype, {
        pages: [],
        questions: [],
        surveyTitle: {},
        surveyPrefix: {},
        surveySuffix: {},
        surveyProgress: {},
        init: function() {
            this.renderLayout();
            var a = this.surveyModel;
            var b = a.get("survey");
            this.surveyTitle = new n({
                surveyModel: a
            });
            this.surveyPrefix = new o({
                surveyModel: a
            });
            this.surveySuffix = new p({
                surveyModel: a
            });
            this.surveyProgress = new t({
                surveyModel: a,
                surveyView: this
            });
            this.surveySuffix = new p({
                surveyModel: a
            });
            d("#footer").html(F(b));
            q.render();
            if (a.get("isRewarding")) {
                this.surveyReward = new m({
                    surveyModel: a
                })
            }
            if (!window.SURVEY) {
                var c = a.get("survey.style");
                new u(M ? "mobile": null).render(c)
            } else {
                new u(M ? "mobile": null).render()
            }
            this.preparePage();
            this.renderPage();
            this.bindUIEvents();
            this.bindModelEvents();
            this.showPageHandler();
            r.translateButtons();
            if (a.get("survey.titleIndex")) {
                this.$el.find(".survey_wrap").removeClass("no_title_index")
            } else {
                this.$el.find(".survey_wrap").addClass("no_title_index")
            }
            if (b.hide_tencent_relevant) {
                document.title = U(a.get("survey.title"))
            } else {
                document.title = U(a.get("survey.title")) + " - 腾讯问卷"
            }
            x()
        },
        preparePage: function() {
            var a = this;
            var b = this.surveyModel.get("survey.pages", {
                ref: true
            });
            var c = this.surveyModel.get("randomHide");
            f.each(b,
            function(b) {
                var e = d('<div class="survey_page" data-pid="' + b.id + '" style="display:none;"></div>');
                f.each(b.questions,
                function(b) {
                    if (c[b.id]) {
                        return
                    }
                    var d = a.renderQuestion(b);
                    e.append(d)
                });
                a.pages.push(e)
            });
            if (a.pages.length > 1) {
                s(1)
            }
        },
        bindUIEvents: function() {
            g.bindUIEvents(this, this.$el, {
                "click .question_chained_selects .option": "updateChainedSelects",
                "change .question_chained_selects select": "updateChainedSelects",
                "change .question_upload input[type=file]": "uploadFile",
                "click .question_upload .cancel": "cancelUploadFile",
                "click .question_upload .abort": "abortUploadFile",
                "change .survey_container :radio, .survey_container :checkbox": "userInterfaceHandler",
                "change .survey_container select:not(.chained_select)": "userInterfaceHandler",
                "blur .survey_container :text, .survey_container textarea": "userInterfaceHandler",
                "focus .survey_container .survey_form_input_other": "userInterfaceHandler",
                "focus .survey_container .option_item .fill_blank": "userInterfaceHandler",
                "change .survey_container .star_range": "userInterfaceHandler",
                "click .survey_container .irs-slider": "userInterfaceHandler",
                "click .survey_nextpage": "nextPage",
                "click .survey_prevpage": "prevPage",
                "click .survey_submit": "nextPage",
                "click a[href^=http]": "_checkLinkHandler"
            });
            g.bindUIEvents(this, d(document), {
                "click .btn_next": "nextPage",
                "click .btn_prev": "prevPage",
                "click .btn_submit": "nextPage",
                "click #btn_report_survey": "report_survey"
            });
            if (N) {
                S();
                T()
            }
        },
        bindModelEvents: function() {
            g.bindModelEvents(this, this.surveyModel, {
                submitSuccess: "submitSuccessHandler",
                submitError: "submitErrorHandler"
            })
        },
        submitSuccessHandler: function(a) {
            var b = this.surveyModel;
            this.$el.find(".survey_title, .survey_prefix, .survey_container, .survey_btn").hide();
            d(".g_footer").hide();
            this.$el.find(".survey_suffix").show();
            d(document).scrollTop(0);
            if (!this.surveyModel.get("survey.hide_tencent_relevant")) {
                this.showAd()
            }
            this.surveyProgress.hide();
            var c = b.get("isVoting");
            var e = b.get("isRewarding");
            var f = b.get("isUseLibrary");
            if (e) {
                this.surveyReward.showRewardBtn()
            }
            if (f) {
                g()
            }
            if (c) {
                h()
            }
            function g() {
                var a = b.get("survey.id");
                var c = b.get("survey.hash");
                var e;
                var f = "/sur/lottery_link_type?survey_id=" + a + "&hash=" + c + "&type=1&color=%235cb0ff";
                var g = d(J({
                    imgSrc: f + "&is_qrcode=1"
                }));
                var h = f + "&is_qrcode=0";
                if (K.isWeChat() || K.isMQQ()) {
                    e = d('<a href="' + h + '" class="survey_btn">点击抽奖</a>')
                } else {
                    e = d('<a href="javascript:void(0);" class="survey_btn">点击抽奖</a>');
                    d("body").on("click", "#close",
                    function(a) {
                        g.removeClass("active animated");
                        a.preventDefault()
                    });
                    setTimeout(function() {
                        e.on("click",
                        function(a) {
                            g.addClass("active");
                            setTimeout(function() {
                                g.addClass("animated")
                            });
                            a.preventDefault()
                        });
                        g.appendTo("body")
                    },
                    500)
                }
                d(".survey_control .inner").append(e)
            }
            function h() {
                var a = b.get("survey.id");
                var c = b.get("survey.hash");
                var e = b.get("survey.survey_type") === 1 ? "答题": "投票";
                var f = d('<a target="_blank" href="/survey_stat.html?id=' + a + "&hash=" + c + '" class="survey_btn survey_show_stat">' + e + "结果</a>");
                d(".survey_control .inner").append(f)
            }
            if (b.get("survey.survey_type") === 1) {
                if (a) {
                    d("#survey_assess_result").show();
                    d("#survey_assess_result .score").text(a.data);
                    d(".survey_show_stat").text("答题结果")
                }
            }
            if (b.get("survey").redirect_url) {
                var i = 3;
                var j = d('<p>将在<span id="redirect_delay">' + i + "</span>秒后跳转...</p>");
                d(".suffix_content").append(j);
                var k = setInterval(function() {
                    d("#redirect_delay").html(--i);
                    if (i <= 0) {
                        clearInterval(k);
                        var a = b.get("survey.redirect_url");
                        var c = a + (a.indexOf("?") !== -1 ? "&": "?") + "referer=" + encodeURIComponent(location.href);
                        location.href = c
                    }
                },
                1e3)
            }
        },
        submitErrorHandler: function(a) {
            switch (a) {
            case "您已经回答过问卷":
                h({
                    skin:
                    "errors",
                    content: a,
                    okValue: r.translate("comfirm"),
                    ok: function() {}
                }).showModal();
                break;
            case "问卷已暂停回收状态":
                h({
                    skin:
                    "errors",
                    content: "问卷已暂停回收，提交将不记录数据.",
                    okValue: r.translate("comfirm"),
                    ok: function() {}
                }).showModal();
                break;
            case "问卷投放截止日期已到":
                h({
                    skin:
                    "errors",
                    content: "问卷投放截止日期已到，提交将不记录数据",
                    okValue: r.translate("comfirm"),
                    ok: function() {}
                }).showModal();
                break;
            default:
                h({
                    skin:
                    "errors",
                    content: a,
                    okValue: r.translate("comfirm"),
                    ok: function() {}
                }).showModal()
            }
            d(".survey_submit").css("pointer-events", "").text("提交")
        },
        referLogicHandler: function(a) {
            var b = this;
            function c(a, b) {
                var c = a.find("select");
                var e = c[0];
                var g = c.val();
                var h = c.data("options");
                while (e.length > 1) {
                    e.remove(1)
                }
                f.each(h,
                function(a) {
                    if (b.indexOf(a.id) !== -1) {
                        var c = document.createElement("option");
                        c.value = a.id;
                        c.text = a.text;
                        if (a.id === g) {
                            c.selected = true
                        }
                        d(c).data("oid", a.id);
                        e.add(c)
                    }
                })
            }
            function e(a, b) {
                a.find(".inputs .option_item").each(function(a, c) {
                    var e = d(c).find("input[type=radio], input[type=checkbox]");
                    var f = String(e.data("oid"));
                    if (b.indexOf(f) === -1) {
                        d(c).hide();
                        e.prop("checked", false)
                    } else {
                        d(c).show()
                    }
                })
            }
            function g(a, b) {
                a.find(".inputs .matrix_item").each(function(a, c) {
                    var e = String(d(c).data("tid"));
                    var f = e.replace("g", "o");
                    if (b.indexOf(f) === -1) {
                        d(c).hide();
                        d(c).find("input[type=radio], input[type=checkbox]").each(function(a, b) {
                            b.checked = false
                        })
                    } else {
                        d(c).show()
                    }
                });
                a.find("table").removeClass("table_freezed")
            }
            function h(a, b) {
                a.find(".inputs .matrix_item").each(function(a, c) {
                    var e = d(c);
                    var f = String(e.data("tid"));
                    var g = f.replace("g", "o");
                    if (b.indexOf(g) === -1) {
                        e.hide();
                        e.find("input[type=radio], input[type=checkbox]").each(function(a, b) {
                            b.checked = false
                        });
                        var h = e.find("input[type=range]").data("ionRangeSlider");
                        if (h) {
                            h.reset();
                            setTimeout(function() {
                                e.attr("selected", false)
                            },
                            100)
                        }
                    } else {
                        e.show()
                    }
                });
                a.find("table").removeClass("table_freezed")
            }
            function i(a, b) {
                a.find(".sort_option_item").each(function(a, c) {
                    var e = d(c);
                    var f = e.data("oid").toString();
                    if (b.indexOf(f) === -1) {
                        e.hide()
                    } else {
                        e.show()
                    }
                });
                a.find(".index_container .index").each(function(a, c) {
                    var e = d(c);
                    if (a >= b.length) {
                        e.hide()
                    } else {
                        e.show()
                    }
                });
                V(a)
            }
            f.each(a,
            function(a) {
                var b = d("#question_" + a.id);
                var f = a.options;
                var j = b.data("type");
                if (a.options.length) {
                    b.find(".no_refer_answer").hide();
                    b.find(".inputs").show()
                } else {
                    b.find(".no_refer_answer").show();
                    b.find(".inputs").hide()
                }
                switch (j) {
                case "select":
                    c(b, f);
                    break;
                case "radio":
                case "checkbox":
                    e(b, f);
                    break;
                case "matrix_radio":
                case "matrix_checkbox":
                    g(b, f);
                    break;
                case "matrix_star":
                    h(b, f);
                    break;
                case "sort":
                    i(b, f);
                    break;
                default:
                    break
                }
            })
        },
        displayLogicHandler: function(a) {
            console.time("show all question");
            this.$el.find(".question").css("display", "block");
            console.timeEnd("show all question");
            console.time("hide question");
            var b = f.map(a,
            function(a, b) {
                return "#question_" + b
            }).join(",");
            d(b).css("display", "none");
            console.timeEnd("hide question")
        },
        invalidAnswerHandler: function(a) {},
        updateIndexHandler: function(a) {
            this.$el.find(".question_index").each(function(b, c) {
                var e = d(c).data("for");
                var f = d(c).parents(".title");
                var g = Number(a[e]) + 1;
                d(c).text(g);
                f.attr({
                    "aria-label": "第" + g + "题：" + f.find(".title_text").text()
                })
            })
        },
        updateProgressHandler: function(a) {
            this.surveyProgress.update(a)
        },
        updateStatHandler: function(a) {
            f.forEach(f.keys(a),
            function(b) {
                var c = d("#question_" + b);
                var e = c.find(".stat");
                if (a[b] === null) {
                    c.removeClass("with_stat");
                    e.css("display", "none");
                    return
                }
                c.addClass("with_stat");
                var f = a[b];
                e.css("display", "block");
                e.each(function(a, b) {
                    d(".count", b).text(f.count[a] + "票");
                    d(".rate", b).text(f.rate[a] + "%");
                    d(".bar", b).width(f.rate[a] + "%")
                })
            })
        },
        showPageHandler: function(a) {
            var b = this.$el.find(".survey_page");
            if (!a) {
                a = this.surveyModel.get("survey.pages.0.id")
            }
            b.hide();
            b.filter("[data-pid=" + a + "]").show();
            this.updateView();
            d(document).scrollTop(0);
            if (M && !O) {
                d(".btn_next, .btn_prev").each(function(a, b) {
                    j.attach(b)
                })
            }
        },
        updateChainedSelects: function(a) {
            if (!M) {
                var b = d(a.currentTarget);
                var c = Number(b.closest(".options").data("level"));
                var e = b.closest(".question");
                var g = e.find(".level");
                var h = e.find(".options");
                var i = e.data("data");
                b.addClass("selected").siblings().removeClass("selected");
                g.slice(c).hide();
                h.slice(c).hide().html("");
                var j = [];
                if (e.find(".option.selected").length < c) {
                    h.slice(0, c - 1).each(function(a, b) {
                        d(b).children().eq(0).addClass("selected")
                    })
                }
                e.find(".option.selected").slice(0, c).each(function(a, b) {
                    j.push(d(b).data("id"))
                });
                var k = g.eq(c);
                var l = h.eq(c);
                var m = i;
                var n = 0;
                f.forEach(j,
                function(a) {
                    m = f.find(m,
                    function(b) {
                        return b[0] === a
                    })[2]
                });
                if (m && m.length) {
                    var o = "";
                    f.forEach(m,
                    function(a, b) {
                        o += G({
                            index: b,
                            id: a[0],
                            text: a[1],
                            selected: false,
                            last: !a[2] || a[2].length === 0
                        })
                    });
                    k.show();
                    l.html(o).show()
                }
                setTimeout(function() {
                    var a = new C(l, "[data-role=chainsgroupLever]", "[role=radio]");
                    a.init();
                    l.find(".option:first-child").focus()
                },
                0)
            } else {
                var p = d(a.currentTarget);
                var c = Number(p.data("level"));
                var e = p.closest(".question");
                var q = e.find(".level");
                var i = e.data("data");
                var j = [];
                q.slice(0, c).each(function(a, b) {
                    var c = d(b).find("select").val();
                    if (c !== "") {
                        j.push(c)
                    }
                });
                var r = j.length;
                var m = i;
                var n = 0;
                f.forEach(j,
                function(a) {
                    m = f.find(m,
                    function(b) {
                        return b[0] === a
                    })[2]
                });
                q.addClass("selected");
                if (m && m.length) {
                    var s = q.eq(r).find("select")[0];
                    while (s.length > 1) {
                        s.remove(1)
                    }
                    f.forEach(m,
                    function(a) {
                        var b = document.createElement("option");
                        b.value = a[0];
                        b.text = a[1];
                        s.add(b)
                    });
                    q.eq(r).show();
                    q.slice(r + 1).hide().removeClass("selected")
                } else {
                    q.slice(r).hide().removeClass("selected")
                }
            }
            this.userInterfaceHandler(a)
        },
        uploadFile: function(a) {
            var b = d(a.target).closest(".question");
            if (!d(a.target).val()) {
                b.find(".uploading, .uploaded").hide();
                b.find(".empty").show();
                return
            }
            this._uploadFile(b, null, a)
        },
        _uploadFile: function(a, b, c) {
            var e = this;
            var f = this.surveyModel.get("survey.id");
            var g = this.surveyModel.get("survey.hash");
            var i = a.data("id");
            var j = a.find("input[type=file]");
            var k = {
                uploader: "/sfile/sur/answer_question_upload_file",
                fileSizeLimit: 5 * 1024 * 1024,
                fileTypeExts: "bmp|jpg|gif|jpeg|png|docx|doc|xlsx|xls|pptx|ppt|rar|gzip|zip|txt|pdf",
                formData: [{
                    name: "survey_id",
                    value: f
                },
                {
                    name: "hash",
                    value: g
                },
                {
                    name: "question_id",
                    value: i
                }],
                success: function(b) {
                    try {
                        b = JSON.parse(b)
                    } catch(d) {
                        alert("上传失败: 原因未知");
                        a.data("file_name_src", null);
                        a.data("file_name_dst", null);
                        j.val("");
                        r("empty");
                        e.userInterfaceHandler(c);
                        return
                    }
                    if (b.status == 1 && b.info == "success") {
                        a.data("file_name_src", b.data.file_name_src);
                        a.data("file_name_dst", b.data.file_name_dst);
                        if (!window.FileReader) {
                            a.find(".filename").text(b.data.file_name_src);
                            a.find(".ico_filetype").show()
                        }
                        j.val("");
                        r("uploaded");
                        e.userInterfaceHandler(c)
                    } else {
                        alert(b.info);
                        a.data("file_name_src", null);
                        a.data("file_name_dst", null);
                        j.val("");
                        r("empty");
                        e.userInterfaceHandler(c)
                    }
                },
                progress: function(b) {
                    var c = b.loaded;
                    var d = b.total;
                    var e = c / d;
                    a.find(".uploading img").css({
                        opacity: e
                    });
                    a.find(".uploading .progress_bar .loaded").width(e * 90 + "%");
                    a.find(".uploading .info .loaded").text(q(c));
                    a.find(".uploading .info .total").text(q(d));
                    a.find(".uploaded .filesize").text(q(d))
                },
                error: function() {
                    r("empty");
                    j.val("")
                },
                typeError: function(a, b) {
                    h({
                        skin: "errors",
                        width: 300,
                        content: '<div style="text-align:left">您上传的文件类型有误，支持的文件类型<br/><ul style="margin: 15px 0; padding-left: 37px;"><li>jpg/gif/jpeg/png/bmp</li><li>docx/doc/xlsx/xls/pptx/ppt</li><li>rar/gzip/zip</li><li>txt/pdf</li></ul></div>',
                        okValue: "确定",
                        ok: function() {}
                    }).showModal();
                    r("empty");
                    j.val("")
                },
                sizeError: function(a, b) {
                    h({
                        skin: "errors",
                        width: 300,
                        content: "您上传的文件超过大小限制（5MB）",
                        okValue: "确定",
                        ok: function() {}
                    }).showModal();
                    r("empty");
                    j.val("")
                },
                emptyError: function() {
                    h({
                        skin: "errors",
                        width: 250,
                        content: "上传文件不能为空",
                        okValue: "确定",
                        ok: function() {}
                    }).showModal();
                    r("empty");
                    j.val("")
                }
            };
            var l;
            if (!b) {
                b = j[0].files;
                l = d(j).upload(k)
            } else {
                k.files = [b[0]];
                k.name = "file";
                l = d.uploadFilesByXHR(k)
            }
            if (!l) {
                return
            }
            a.data("uploadXHR", l);
            var m, n, o;
            if (b) {
                m = b[0];
                n = m.name;
                o = s(n)
            } else {
                n = j[0].value;
                o = s(n)
            }
            r("uploading");
            a.find(".filename").text(n);
            if (!window.FileReader) {
                if (/jpg|gif|jpeg|png|bmp|docx|doc|xlsx|xls|pptx|ppt|rar|gzip|zip|txt|pdf/i.test(o)) {
                    a.find(".uploadingbar").show();
                    a.find(".thumbnail").hide();
                    a.find(".ico_filetype").hide().each(function(a, b) {
                        b.className = "ico ico_filetype " + o
                    })
                }
            } else {
                if (/jpg|gif|jpeg|png/i.test(o)) {
                    a.find(".ico_filetype").hide();
                    var p = new FileReader;
                    p.onloadend = function() {
                        a.find(".thumbnail").show().each(function(b, c) {
                            c.src = p.result;
                            if (b === 0) {
                                c.onload = function() {
                                    a.find(".progress_bar").width(c.width)
                                }
                            }
                        })
                    };
                    p.readAsDataURL(m)
                } else if (/bmp|docx|doc|xlsx|xls|pptx|ppt|rar|gzip|zip|txt|pdf/i.test(o)) {
                    a.find(".thumbnail").hide();
                    a.find(".uploadingbar").hide();
                    a.find(".ico_filetype").show().each(function(a, b) {
                        b.className = "ico ico_filetype " + o
                    });
                    a.find(".progress_bar").width(70)
                }
            }
            function q(a) {
                return parseInt(a / 1024, 10) + "KB"
            }
            function r(b) {
                a.find(".empty, .uploading, .uploaded").hide();
                a.find("." + b).show()
            }
            function s(a) {
                var b = /.*\.(\w+)$/.exec(a);
                return b ? b[1] : null
            }
        },
        abortUploadFile: function(a) {
            var b = d(a.target).closest(".question");
            b.data("uploadXHR").abort();
            b.find(".empty, .uploading, .uploaded").hide();
            b.find(".empty").show();
            b.data("file_name_src", null);
            b.data("file_name_dst", null);
            b.find("input[type=file]").val("");
            this.userInterfaceHandler(a)
        },
        cancelUploadFile: function(a) {
            var b = this;
            var c = d(a.target).closest(".question");
            h({
                title: "删除附件",
                content: "确认要删除所选的附件吗？",
                skin: "ui-dialog-small",
                width: 300,
                height: 80,
                okValue: "确定",
                cancelValue: "取消",
                autofocus: true,
                ok: function() {
                    c.find(".empty, .uploading, .uploaded").hide();
                    c.find(".empty").show();
                    c.data("file_name_src", null);
                    c.data("file_name_dst", null);
                    var d = c.find("input[type=file]");
                    d.replaceWith(d.clone(true));
                    b.userInterfaceHandler(a)
                },
                cancel: function() {}
            }).showModal()
        },
        adjustMatrix: function(a) {
            var b = function(a) {
                var b = a.find(".matrix_group table");
                if (b.is(":visible")) {
                    v(b)
                }
            };
            if (!a) {
                this.$el.find(".question_matrix_radio, .question_matrix_checkbox, .question_matrix_star").each(function(a, c) {
                    if (!d(c).is(":visible")) {
                        return
                    }
                    b(d(c))
                })
            } else {
                b(a)
            }
        },
        adjustMatrixStar: function(a) {
            if (!a) {
                this.$el.find(".question_matrix_star").each(function(a, b) {
                    w.adjustMatrixStar(d(b))
                })
            } else {
                w.adjustMatrixStar(a)
            }
        },
        adjustSort: function() {
            var a = d(".survey_container .question_sort");
            a.each(function(a, b) {
                var c = d(b);
                if (!c.is(":visible")) {
                    return
                }
                if (c.data("rendered")) {
                    return
                }
                setTimeout(function() {
                    V(c)
                },
                10);
                c.data("rendered", true)
            })
        },
        userInterfaceHandler: function(a) {
            var b = d(a.currentTarget);
            var c = b.closest(".question");
            function e(a, b) {
                if (d(a).data("exclusive")) {
                    if (a.checked) {
                        b.find("input[type=checkbox]").each(function(b, c) {
                            if (a !== c) {
                                c.checked = false
                            }
                        })
                    }
                } else {
                    if (a.checked) {
                        b.find("input[type=checkbox]").each(function(a, b) {
                            if (d(b).data("exclusive")) {
                                b.checked = false
                            }
                        })
                    }
                }
            }
            if (a.type === "change") {
                switch (c.data("type")) {
                case "select":
                    b.find("option").attr({
                        "aria-selected":
                        "false",
                        "aria-checked": "false"
                    });
                    b.find("#" + b.val()).attr({
                        "aria-selected": "true",
                        "aria-checked": "true"
                    });
                    break;
                case "checkbox":
                    e(a.currentTarget, c);
                    b.closest(".option_item").attr({
                        "aria-checked": a.currentTarget.checked
                    }).focus();
                    break;
                case "radio":
                    c.find(".option_item").attr({
                        "aria-checked":
                        "false",
                        tabindex: -1
                    });
                    b.closest(".option_item").attr({
                        "aria-checked": "true",
                        tabindex: 0
                    }).focus();
                    break;
                case "star":
                    c.find(".star_item").attr({
                        "aria-checked":
                        "false",
                        tabindex: -1
                    });
                    b.closest(".star_item").attr({
                        "aria-checked": "true",
                        tabindex: 0
                    }).focus();
                    break;
                case "matrix_radio":
                    var f = b.closest(".matrix_item");
                    f.find(".matrix_option").attr({
                        "aria-checked": "false",
                        tabindex: -1
                    });
                    b.closest(".matrix_option").attr({
                        "aria-checked": "true",
                        tabindex: 0
                    }).focus();
                    break;
                case "matrix_checkbox":
                    b.closest(".matrix_option").attr({
                        "aria-checked":
                        a.currentTarget.checked,
                        tabindex: a.currentTarget.checked ? 0 : -1
                    }).focus();
                case "matrix_star":
                    if (c.data("optionnum") < 7) {
                        var f = b.closest(".matrix_item");
                        f.find(".matrix_option").attr({
                            "aria-checked": "false",
                            tabindex: -1
                        });
                        b.closest(".matrix_option").attr({
                            "aria-checked": "true",
                            tabindex: 0
                        }).focus()
                    }
                    break;
                case "chained_selects":
                    var g = b.closest(".level");
                    g.find(".option").attr({
                        "aria-checked": "false",
                        tabindex: -1
                    });
                    b.closest(".option").attr({
                        "aria-checked": "true",
                        tabindex: 0
                    });
                    break
                }
            }
            if (a.type === "focusin" && b.hasClass("fill_blank")) {
                var h = b.parents(".option_item");
                var i = h.find("input")[0];
                i.checked = true;
                if (d(i).is("[type=checkbox]")) {
                    e(i, c)
                }
            }
            if (a.type === "change" && b[0].checked && c.is("[data-type=radio],[data-type=checkbox]")) {
                var h = b.parents(".option_item");
                h.find(".fill_blank").first().trigger("focus")
            }
            console.time("total update view");
            this.updateView();
            console.timeEnd("total update view")
        },
        buttonsHandler: function(a) {
            var b = this.surveyModel.get("survey.prev");
            var c = this.surveyModel.get("currentPageIndex");
            var e = this.surveyModel.get("progress");
            d(".survey_btn").css("display", "none");
            d(".progress_finish").hide("display", "none");
            d(".btn_prev, .btn_next").attr("disabled", "disabled");
            if (a == -1) {
                d(".survey_submit").css("display", "inline-block")
            } else {
                d(".survey_nextpage").css("display", "inline-block")
            }
            if (b && c > 0) {
                d(".survey_prevpage").css("display", "inline-block")
            }
        },
        nextPage: function() {
            var a = this.getAllInput();
            var b = this.surveyModel.exec("nextPage", a);
            this.displayLogicHandler(b.hide);
            if (!b.isError()) {
                var c = d(".survey_content");
                if (c.css("marginTop") !== "0px") {
                    c.css("marginTop", "0px")
                }
                if (b["goto"] != -1) {
                    this.updateIndexHandler(b.qindex);
                    this.updateProgressHandler(b.progress);
                    this.showPageHandler(b["goto"]);
                    var e = this.surveyModel.get("currentPageIndex");
                    s(e + 1);
                    d(".survey_page:visible").find(".question:first-child .title").attr({
                        tabindex: "0"
                    }).focus()
                } else {
                    d(".survey_submit").css("pointer-events", "none").text("提交中");
                    this.updateProgressHandler(b.progress)
                }
            }
            R(b.info.error)
        },
        prevPage: function() {
            var a = this.surveyModel.exec("prevPage");
            var b = a.pid;
            this.showPageHandler(b);
            var c = this.surveyModel.get("currentPageIndex");
            if (c === 0) {
                d(".survey_content").css("marginTop", "")
            }
        },
        getAllInput: function() {
            var a = [];
            d(".survey_page").each(function(b, c) {
                c = d(c);
                var e = {
                    id: String(c.data("pid")),
                    answers: []
                };
                c.find(".question").each(function(a, b) {
                    var c = d(b);
                    e.answers.push(l[c.data("type")](c))
                });
                a.push(e)
            });
            return a
        },
        renderPage: function() {
            var a = this.$el.find(".survey_container");
            a.append(this.pages)
        },
        renderLayout: function() {
            this.$el.find(".g_content").html(E())
        },
        renderQuestion: function(a) {
            console.log("[renderQuestion]");
            var b = this;
            a.index++;
            if (a.type === "checkbox" || a.type === "radio") {
                a = d.extend(true, {},
                a);
                a.options.map(function(b) {
                    if (b.id === "98" && !b.text.match(/_{2,}/)) {
                        b.text = b.text.replace(/<p>(.*)<\/p>\s*$/, "<p>$1____</p>")
                    }
                    b.text = y(b.text, a.id);
                    return b
                })
            }
            a.title = y(a.title, a.id);
            a.description = y(a.description, a.id);
            var c = d((M ? I: H)(a));
            function e() {
                var b = new z(c, "[role=radio]");
                b.init();
                if (!M) {
                    return
                }
                var e = a.starNum;
                var f = e < 5 ? 100 / e + "%": "20%";
                c.find(".star_item").each(function(a, b) {
                    d(this).css("width", f)
                })
            }
            function g() {
                var a = new z(c, "[role=radio]");
                a.init()
            }
            function h() {
                var a = new B(c, "[role=checkbox]");
                a.init();
                c.find(".title_text>p").last().append('<span style="color:#53aaf3;margin-left:3px;">[多选题]</span>')
            }
            function k() {
                c.find("select").data("options", a.options)
            }
            function l() {
                var a = f.throttle(V, 100);
                var e;
                var g = c.find(".answer_container");
                var h = c.find(".option_container");
                if (M) {
                    L.init(g, {
                        onTouchEnd: function() {
                            a(c, true)
                        }
                    });
                    setTimeout(function() {
                        var b = c.find(".index_container .index");
                        c.find(".answer_container").children().each(function(a, c) {
                            var e = d(c);
                            var f = e.height() + 1;
                            b.eq(a).height(f)
                        });
                        a(c)
                    },
                    10);
                    var j = g.find("img").length;
                    g.find("img").each(function(b, d) {
                        d.onload = function() {
                            j--;
                            a(c)
                        }
                    })
                } else {
                    e = i([g[0], h[0]], {
                        revertOnSpill: true,
                        accepts: function(a, b) {
                            return b != h[0]
                        }
                    });
                    var j = h.find("img").length;
                    h.find("img").each(function(b, d) {
                        d.onload = function() {
                            j--;
                            var b = h.height();
                            a(c)
                        }
                    });
                    e.on("shadow",
                    function() {});
                    e.on("drop",
                    function(d) {
                        a(c, true);
                        b.updateView()
                    });
                    h.find(".sort_option_item").on("click",
                    function(e) {
                        d(this).appendTo(g);
                        a(c, true);
                        b.updateView();
                        e.preventDefault()
                    });
                    var k = new A(c, "[role=option]");
                    k.init()
                }
            }
            function m() {
                function b(a, b) {
                    var d = "";
                    f.forEach(b,
                    function(a, b) {
                        d += G({
                            index: b,
                            id: a[0],
                            text: a[1],
                            selected: false,
                            last: !a[2] || a[2].length === 0
                        })
                    });
                    c.find(".options").eq(a).html(d);
                }
                c.data("data", a.groups);
                var e = a.groups;
                var g = 0;
                if (!M) {
                    while (e && e.length) {
                        b(g, e);
                        e = e[0][2];
                        g++
                    }
                    c.find(".options").slice(g).hide();
                    setTimeout(function() {
                        c.find(".options").each(function(a, b) {
                            var c = new C(d(b), "[data-role=chainsgroupLever]", "[role=radio]");
                            c.init()
                        })
                    },
                    0)
                } else {
                    if (e && e.length) {
                        var h = c.find("select")[0];
                        f.forEach(e,
                        function(a) {
                            var b = document.createElement("option");
                            b.value = a[0];
                            b.text = a[1];
                            h.add(b)
                        });
                        c.find(".level").eq(0).show()
                    }
                }
            }
            function n() {
                if ("draggable" in document.createElement("span")) {
                    c.find(".fileupload_wrap")[0].ondragover = function(a) {
                        d(this).addClass("file_over");
                        a.preventDefault();
                        return false
                    };
                    c.find(".fileupload_wrap")[0].ondragend = function(a) {
                        d(this).removeClass("file_over");
                        a.preventDefault();
                        return false
                    };
                    c.find(".fileupload_wrap")[0].ondrop = function(a) {
                        d(this).removeClass("file_over");
                        a.preventDefault();
                        b._uploadFile(c, a.dataTransfer.files)
                    }
                } else {
                    c.find(".support_dnd").hide()
                }
                if (window.FileReader && window.File && window.FileList && window.Blob) {
                    c.find(".uploading .uploadingbar").hide()
                } else {
                    c.find(".uploading .info").hide();
                    c.find(".uploading .progress_bar").hide();
                    c.find(".uploading .thumbnail").hide()
                }
            }
            function o() {
                var b;
                if (parseInt(a.starNum) > 7) {
                    c.find(".matrix_item").each(function(a, c) {
                        b = new D(d(c), ".star_range", ".irs-slider");
                        b.init()
                    })
                } else {
                    c.find('[data-role="radiogroup"]').each(function(a, c) {
                        b = new z(d(c), "[role=radio]");
                        b.init()
                    })
                }
            }
            function p() {
                var a;
                c.find(".matrix_item").each(function(b, c) {
                    a = new z(d(c), "[role=radio]");
                    a.init()
                })
            }
            function q() {
                var a;
                c.find(".matrix_item").each(function(b, c) {
                    a = new B(d(c), "[role=checkbox]");
                    a.init()
                })
            }
            switch (a.type) {
            case "radio":
                g();
                break;
            case "star":
                e();
                break;
            case "checkbox":
                h();
                break;
            case "select":
                k();
                break;
            case "sort":
                l();
                break;
            case "chained_selects":
                m();
                break;
            case "upload":
                n();
                break;
            case "matrix_star":
                o();
                break;
            case "matrix_checkbox":
                q();
                break;
            case "matrix_radio":
                p();
                break
            }
            if (a.required) {
                c.addClass("required")
            }
            if (!O) {
                c.find("label").each(function(a, b) {
                    j.attach(b)
                })
            }
            return c
        },
        updateView: function() {
            console.time("get inputs");
            var a = this.getAllInput();
            console.timeEnd("get inputs");
            console.time("exec inputs");
            var b = this.surveyModel.exec("inputChange", a);
            console.timeEnd("exec inputs");
            console.time("render logic");
            console.time("render refer logic");
            this.referLogicHandler(b.refer);
            console.timeEnd("render refer logic");
            console.time("render display logic");
            this.displayLogicHandler(b.hide);
            console.timeEnd("render display logic");
            console.timeEnd("render logic");
            console.time("render ui");
            console.time("render qindex");
            this.updateIndexHandler(b.qindex);
            console.timeEnd("render qindex");
            console.time("render progress");
            this.updateProgressHandler(b.progress);
            console.timeEnd("render progress");
            console.time("render stat");
            this.updateStatHandler(b.stat);
            console.timeEnd("render stat");
            console.time("render buttons");
            console.log("goto goto", b.goto);
            this.buttonsHandler(b.goto);
            console.timeEnd("render buttons");
            console.time("render adjust matrix");
            this.adjustMatrix();
            console.timeEnd("render adjust matrix");
            console.time("render adjust matrix star");
            this.adjustMatrixStar();
            console.timeEnd("render adjust matrix star");
            console.time("render adjust sort");
            this.adjustSort();
            console.timeEnd("render adjust sort");
            console.timeEnd("render ui");
            console.time("render prefix title");
            var c = this.surveyModel.get("currentPageIndex");
            if (c === 0) {
                this.surveyTitle.show();
                this.surveyPrefix.show()
            } else {
                this.surveyTitle.hide();
                this.surveyPrefix.hide()
            }
            console.timeEnd("render prefix title");
            console.time("render tips");
            R(b.info.error);
            console.timeEnd("render tips")
        },
        showAd: function() {
            var a = d(".survey_ads");
            var b;
            if (!M) {
                b = '<img alt="腾讯问卷" class="pc" src="/image/survey_banner_ad_pc.png"/>';
                d(".survey_main").css({
                    "padding-bottom": "0px"
                });
                a.find("a").html(b)
            } else {
                b = '<img alt="腾讯投票，实时有趣的小投票" class="mobile fixed" src="/image/survey_banner_ad_mobile.png"/>';
                a.find("a").attr({
                    style: "padding:0;height:auto;border:0;"
                }).html(b)
            }
            a.css("display", "block")
        },
        _checkLinkHandler: function(a) {
            var b = d(a.currentTarget);
            var c = b.attr("href");
            var e = "//safejmp.com/?appid=4&appkey=wjdc&unknown=1&url=";
            if (!c) {
                return true
            }
            var f = ["www.qq.com", "wj.qq.com", "safejmp.com", "h5app.qq.com"];
            var g = new RegExp("^(http|https)://[\\w\\.]*(" + f.join("|") + ")");
            if (g.test(c)) {
                return true
            }
            if (b.text() !== c) {
                var h = this.surveyModel.get("survey.id");
                var i = this.surveyModel.get("survey.hash");
                k.report_survey(h, i).always(function() {});
                return false
            }
            if (c.indexOf("http://safejmp.com") !== -1) {
                c = decodeURIComponent(c.split("url=")[1])
            }
            window.open(c);
            return false
        },
        report_survey: function(a) {
            var b = d(a.target);
            if (b.data("isReported")) {
                return
            }
            var c = this.surveyModel.get("survey.id");
            var e = this.surveyModel.get("survey.hash");
            if (confirm("确定要举报该问卷?")) {
                k.report_survey(c, e).always(function() {
                    alert("举报成功");
                    b.data("isReported", true)
                })
            }
        }
    });
    var R = function(a) {
        setTimeout(function() {
            d(".tips_error").remove();
            f.forEach(a,
            function(a, c) {
                var d = a.id;
                var e = a.type;
                if (c === 0) {
                    b(d, e, true)
                } else {
                    b(d, e)
                }
            })
        },
        20);
        function b(a, b, c) {
            if (a.indexOf("fillblank") !== -1) {
                var e = d("#" + a);
                if (e.length !== 0) {
                    h({
                        skin: "tips_error tips_error_blank",
                        align: "top",
                        content: b
                    }).show(e[0])
                }
            } else {
                var f = d("#question_" + a);
                var g = d('<span class="tips_error" tabindex="0" aria-label=' + b + "></span>");
                g.text(b);
                f.find(".tips").html(g);
                if (c) {
                    f.scrollIntoView();
                    setTimeout(function() {
                        g.one("webkitAnimationEnd animationend",
                        function() {
                            g.removeClass("shake")
                        });
                        g.addClass("shake");
                        g.focus()
                    },
                    300)
                }
            }
        }
    };
    var S = function() {
        d("#footer a, .survey_ads a").on("click",
        function() {
            return false
        })
    };
    var T = function() {
        if (!e.mobile && d(window).width() < 800) {
            var a = document.body;
            var b, c;
            var f = false;
            d(a).on("mousedown",
            function(a) {
                b = a.pageX;
                c = a.pageY;
                f = true
            });
            d(a).on("mousemove",
            function(a) {
                if (f) {
                    var e = a.pageX;
                    var g = a.pageY;
                    var h = b - e;
                    var i = c - g;
                    var j = d(document).scrollTop();
                    d(document).scrollTop(j + i * .6);
                    b = e;
                    c = g
                }
            });
            d(a).on("mouseup",
            function(a) {
                f = false
            });
            a.onselectstart = function() {
                return false
            };
            d(a).addClass("simulation")
        }
    };
    var U = function(a) {
        var b = d("<p>" + a + "</p>");
        return d.trim(b.text())
    };
    function V(a, b) {
        var c = a.find(".index_container .index").filter(":visible");
        var e = a.find(".option_container .sort_option_item").filter(":visible");
        var g = a.find(".answer_container .sort_option_item").filter(":visible");
        var h = g.length;
        c.removeClass("active");
        var i = b || a.data("isSorted");
        f.each(g,
        function(a, b) {
            c.eq(b).height(d(a).outerHeight() - 1);
            if (i) {
                c.eq(b).addClass("active")
            }
        });
        f.each(e,
        function(a, b) {
            var e = h + b;
            c.eq(e).height(d(a).outerHeight(true))
        });
        if (b) {
            a.data("isSorted", true)
        }
    }
    c.exports = Q
});
define("app/survey/pc/main", ["require", "exports", "module", "jquery", "queryString", "app/common/models/collect_answer", "art-dialog/dialog", "app/common/models/user", "app/common/ur_ajax", "app/common/accessibility_detect", "app/util/i18next", "app/common/ur_app", "app/common/api/survey", "app/util/report_performance", "app/util/sns", "../common/views/survey"],
function(a, b, c) {
    "use strict";
    var d = a("jquery");
    var e = a("queryString");
    var f = a("app/common/models/collect_answer");
    var g = a("art-dialog/dialog");
    var h = a("app/common/models/user");
    var i = a("app/common/ur_ajax");
    var j = a("app/common/accessibility_detect");
    var k = a("app/util/i18next");
    var l = a("app/common/ur_app");
    var m = a("app/common/api/survey");
    var n = a("app/util/report_performance");
    var o = a("app/util/sns");
    var p = a("../common/views/survey");
    var q = d(document).width() < 800;
    var r = {
        initialize: function() {},
        requestSuccessCallback: function(a) {
            var b = a.status;
            switch (b) {
            case - 1 : this.trigger("not-invited", a);
                break;
            case - 2 : this.trigger("not-logged-in", a);
                break;
            case 1:
                this.trigger("done", a);
                break;
            default:
                this.trigger("fail", a)
            }
        },
        defaultOptions: {
            cache: false
        },
        defaultCallbacks: {
            "not-logged-in": function(a) {
                window.onbeforeunload = function() {};
                this.trigger("require-login");
                h.login()
            },
            fail: function(a) {
                if (a.info) {
                    alert(a.info)
                }
            }
        }
    };
    var s = function(a) {
        var b = d(".mod_warning_info");
        var c = "";
        switch (a) {
        case 0:
            break;
        case 1:
            c = "当前问卷可能含有敏感内容，请您仔细阅读后填写";
            b.find(".text").text(c);
            b.show();
            setTimeout(function() {
                b.css("opacity", "0")
            },
            4e3);
            setTimeout(function() {
                b.hide()
            },
            5e3);
            break;
        case 2:
            c = "当前问卷可能包含不良内容，请您谨慎填写";
            b.find(".text").text(c);
            b.show();
            setTimeout(function() {
                b.css("opacity", "0")
            },
            4e3);
            setTimeout(function() {
                b.hide()
            },
            5e3);
            break;
        case 3:
            g({
                skin:
                "errors",
                okValue: k.translate("comfirm"),
                content: "因涉嫌不安全或违规内容，问卷访问被限制，详细情况请联系客服。",
                ok: function() {
                    window.location.href = "/index.html"
                }
            }).showModal();
            break;
        default:
            break
        }
    };
    var t = function(a) {
        var b = g({
            skin: "errors",
            okValue: k.translate("comfirm"),
            ok: function() {}
        });
        switch (a) {
        case 0:
            break;
        case 4:
            b.content("问卷已暂停回收<br>提交将不记录数据").showModal();
            break;
        case 5:
            b.content("问卷投放截止日期已到<br>提交将不记录数据").showModal();
            break;
        case 6:
            g({
                skin:
                "errors",
                content: "问卷设置了必须登录才能作答。",
                okValue: k.translate("comfirm"),
                width: 200,
                ok: function() {
                    if (q) {
                        if (h.isWeChat()) {
                            h.jumpWeChatForReader(location.href)
                        } else {
                            h.jumpPT(location.href)
                        }
                    } else {
                        h.showPTLoginDialog(location.href)
                    }
                }
            }).showModal();
            break;
        case 7:
            b.content("抽奖已经结束").showModal();
            break;
        case 8:
            b.content("您已经抽过奖了").showModal();
            break;
        case 9:
            b.content("您已经回答过问卷").showModal();
            break;
        case 21:
            g({
                skin:
                "errors",
                content: "问卷设置了必须登录才能作答。",
                okValue: k.translate("comfirm"),
                width: 200,
                ok: function() {
                    if (q) {
                        h.jumpPT(location.href)
                    } else {
                        h.showPTLoginDialog(location.href)
                    }
                }
            }).showModal();
            break
        }
    };
    var u = function() {
        var a = location.pathname;
        var b = location.search;
        var c = a.split("/");
        var f = {};
        if (c[1] && c[1] === "s") {
            f.id = c[2];
            f.hash = c[3];
            f = d.extend(f, e.parse(b))
        } else {
            f = e.parse(b)
        }
        return f
    };
    var v = function() {
        var a = false;
        try {
            a = top !== window && top.location.href.indexOf("wj.qq.com") >= 0
        } catch(b) {
            console.log(b)
        }
        return a
    };
    var w = function(a) {
        i.setConfig(r);
        s(a.credit_level);
        if (!v()) {
            t(a.code)
        }
        var b = u();
        var c = b.openid || "";
        var d = new f({
            survey: a,
            openid: c
        });
        new p({
            surveyModel: d,
            lang: window.LANG
        });
        n();
        new j({
            surveyModel: d,
            userModel: h
        });
        o.initShare(d)
    };
    if (window.SURVEY) {
        w(window.SURVEY)
    } else {
        var x = new l({
            needLogin: false,
            preventAutoLogin: true
        });
        var y = function(a) {
            d("#loading").hide();
            w(a.data)
        };
        var z = function(a) {
            d("#loading").hide();
            var b = g({
                skin: "errors",
                okValue: k.translate("comfirm"),
                ok: function() {
                    window.location.href = "/index.html"
                }
            });
            switch (a.info) {
            case 1:
                b.content("问卷ID不能为空").showModal();
                break;
            case 2:
                b.content("问卷不存在").showModal();
                break;
            case 3:
                b.content("问卷hash不匹配").showModal();
                break;
            case 4:
                b.content("问卷已暂停回收，提交将不记录数据").showModal();
                break;
            case 5:
                b.content("问卷投放截止日期已到<br>提交将不记录数据").showModal();
                break;
            case 6:
                g({
                    skin:
                    "errors",
                    content: "问卷设置了必须登录才能作答。",
                    okValue: k.translate("comfirm"),
                    width: 200,
                    ok: function() {
                        if (q) {
                            if (h.isWeChat()) {
                                h.jumpWeChatForReader(location.href)
                            } else {
                                h.jumpPT(location.href)
                            }
                        } else {
                            h.showPTLoginDialog(location.href)
                        }
                    }
                }).showModal();
                break;
            case 7:
                b.content("抽奖已经结束").showModal();
                break;
            case 8:
                b.content("您已经抽过奖了").showModal();
                break;
            case 9:
                b.content("您已经回答过问卷").showModal();
                break;
            case 20:
                console.log(a);
                b.content("根据创建者设置，您无权回答此问卷<br>如有疑问，请联系问卷创建者").showModal();
                break;
            case 21:
                g({
                    skin:
                    "errors",
                    content: "问卷设置了必须登录才能作答。",
                    okValue: k.translate("comfirm"),
                    width: 200,
                    ok: function() {
                        if (q) {
                            h.jumpPT(location.href)
                        } else {
                            h.showPTLoginDialog(location.href)
                        }
                    }
                }).showModal();
                break;
            default:
                g({
                    skin:
                    "errors",
                    content: a.info,
                    width: 200,
                    okValue: k.translate("comfirm"),
                    ok: function() {
                        window.location.href = "/index.html"
                    }
                }).showModal();
                break
            }
        };
        x.on("start",
        function() {
            var a = u();
            var b = a.id;
            var c = a.hash;
            var d = m.getSurveyPublic(b, c, true, ["fail"]);
            d.done(y).fail(z)
        })
    }
});
require(["app/survey/pc/main"]);