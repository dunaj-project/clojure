/**
 *   Copyright (c) 2013, 2015, Jozef Wagner. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *   the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package clojure.bridge;

import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Namespace;
import clojure.lang.Var;
import clojure.lang.IFn;
import clojure.lang.AFn;
import clojure.lang.Keyword;
import clojure.lang.PersistentArrayMap;

/**
 *  Dunaj uses protocols instead of interfaces to improve
 *  flexibility. In order to change clojure source as rarely as
 *  possible, a bridge is implemented that injects protocol logic
 *  into existing java sources, so that we can have both clojure
 *  original interfaces and dunaj protocols. The drawback is that this
 *  bridge slows down things a bit, and may hinder inlining.
 *
 *  Dummy versions of protocols, their methods and satisfies? function
 *  are created so that protocol methods can be called before they are
 *  really created.
 */

public class Protocol {

    final static IFn dummyFn = new AFn() {
            public Object invoke() {return null;}
            public Object invoke(Object arg1) {return null;}
            public Object invoke(Object arg1, Object arg2) {return null;}
            public Object invoke(Object arg1, Object arg2, Object arg3) {return null;}
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {return null;}
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {return null;}
        };

    static public Namespace CLOJURE_NS = null; // clojure.core

    static public Var SAT = null; // satisfies? Var

    private static Namespace namespace(String s) {
        return Namespace.findOrCreate(Symbol.intern(s));
    }

    private static Var proto(Namespace n, String s) {
        return Var.intern(n, Symbol.intern(s), null);
    }

    private static Var protoMethod(Var p, String s) {
        Var ret = Var.intern(p.ns, Symbol.intern(s), dummyFn);
        silence(ret, p);
        return ret;
    }

    private static Var fun(Namespace n, String s) {
        Var ret = Var.intern(n, Symbol.intern(s), dummyFn);
        return ret;
    }

    private static void silence(Var f, Var p) {
        // silences protocol method overwrite warning
        f.setMeta(new PersistentArrayMap(new Object[]{Keyword.intern(null, "protocol"), p}));
    }

    public static boolean satisfies(Var proto, Object o) {
        boolean ret = (SAT != null) && (proto.get() != null) && (RT.booleanCast(SAT.invoke(proto.get(), o)));
        return ret;
    }

    ////////// clojure.bridge
    static public Namespace BRIDGE_NS = null;
    static public Var RED2SEQ = null;
    static public Var ITER2SEQ = null;

    ////////// dunaj.coll
    static public Namespace COLL_NS = null;
    static public Var PRED = null;
    static public Var PLOOKUP = null;
    static public Var PINDEXED = null;
    static public Var PCOUNTED = null;
    static public Var GET = null;
    static public Var CONTAINS = null;
    static public Var NTH = null;
    static public Var COUNT = null;

    public static void init() {
        CLOJURE_NS = namespace("clojure.core");
        SAT = Var.intern(CLOJURE_NS, Symbol.intern("satisfies?"), dummyFn);

        //// clojure.bridge
        BRIDGE_NS = namespace("clojure.bridge");
        RED2SEQ = fun(BRIDGE_NS, "red-to-seq");

        //// dunaj.coll
        COLL_NS = namespace("dunaj.coll");
        PRED = proto(COLL_NS, "IRed");
        PLOOKUP = proto(COLL_NS, "ILookup");
        PINDEXED = proto(COLL_NS, "IIndexed");
        PCOUNTED = proto(COLL_NS, "ICounted");
        GET = protoMethod(PLOOKUP, "-get");
        CONTAINS = protoMethod(PLOOKUP, "-contains?");
        NTH = protoMethod(PINDEXED, "-nth");
        COUNT = protoMethod(PCOUNTED, "-count");
    }

    ////////// clojure.bridge

    public static Object bridgeRed2Seq(Object o) {
        return RED2SEQ.invoke(o);
    }

    ////////// dunaj.coll

    public static boolean satisfiesIRed(Object o) {
        return satisfies(PRED, o);
    }

    public static boolean satisfiesICount(Object o) {
        return satisfies(PCOUNTED, o);
    }

    public static boolean satisfiesILookup(Object o) {
        return satisfies(PLOOKUP, o);
    }

    public static boolean satisfiesIIndexed(Object o) {
        return satisfies(PINDEXED, o);
    }

    public static Object bridgeIIndexedNth(Object o, Object k, Object nf) {
        return NTH.invoke(o, k, nf);
    }

    public static Object bridgeILookupGet(Object o, Object k, Object nf) {
        return GET.invoke(o, k, nf);
    }

    public static Object bridgeILookupContains(Object o, Object k) {
        return CONTAINS.invoke(o, k);
    }

    public static Object bridgeICountedCount(Object o) {
        return COUNT.invoke(o);
    }

}
