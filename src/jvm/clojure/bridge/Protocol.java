/**
 *   Copyright (c) Jozef Wagner. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 * 	 the terms of this license.
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
import dunaj.compare.IEquiv;

/**
 *  Dunaj uses protocols instead of interfaces to deliver greater
 *  flexibility. In order to change clojure source as rarely as
 *  possible, a bridge is implemented which injects protocol logic
 *  into existing java sources, so that we can have both clojure
 *  original interfaces and dunaj protocols. The drawback is that this
 *  bridge slows down things a lot, mainly Util.equiv, which is used
 *  everywhere.
 *
 *  Dummy versions of protocols, their methods and satisfies? function
 *  are created so that protocol methods can be called before they are
 *  really created. This requires that we have compiled protocol
 *  interface at hand.
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

    private static void silence(Var f, Var p) {
        // silences protocol method overwrite warning
        f.setMeta(new PersistentArrayMap(new Object[]{Keyword.intern(null, "protocol"), p}));
    }

    ////////// dunaj.compare
    static public Namespace COMPARE_NS = null;
    static public Var PEQUIV = null;
    static public Var EQUIV = null;
    private static final ThreadLocal<Integer> equivRecur = new ThreadLocal<Integer>();

    public static void init() {
        CLOJURE_NS = namespace("clojure.core");
        SAT = Var.intern(CLOJURE_NS, Symbol.intern("satisfies?"), dummyFn);

        //// dunaj.compare
        COMPARE_NS = namespace("dunaj.compare");
        PEQUIV = proto(COMPARE_NS, "IEquiv");
        EQUIV = protoMethod(PEQUIV, "-equiv");
    }

    ////////// dunaj.compare
    
    public static boolean satisfiesIEquiv(Object o) {
        if (o instanceof IEquiv)
            return true;
        if (equivRecur.get() == null) {
            // prevents satisfies? calling back itself through Util.equiv
            equivRecur.set(1);
            boolean ret = (SAT != null) && (PEQUIV.get() != null) && (RT.booleanCast(SAT.invoke(PEQUIV.get(), o)));
            equivRecur.set(null);
            return ret;
        } else
            return false;
    }
    
    public static boolean bridgeIEquivequiv(Object k1, Object k2) {
        if (k1 instanceof IEquiv)
            return RT.booleanCast(((IEquiv)k1)._equiv(k2));
        else if (k2 instanceof IEquiv)
            return RT.booleanCast(((IEquiv)k2)._equiv(k1));
        else if (satisfiesIEquiv(k1))
            return (EQUIV != null) && RT.booleanCast(EQUIV.invoke(k1, k2));
        else
            return (EQUIV != null) && RT.booleanCast(EQUIV.invoke(k2, k1));
    }
    
}
