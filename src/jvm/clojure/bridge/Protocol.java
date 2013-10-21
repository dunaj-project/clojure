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
import dunaj.compare.IEquiv;

public class Protocol {

    public static void init() {
    }
    
    public static boolean satisfiesIEquiv(Object o){
        if (o instanceof IEquiv)
            return true;
        // TODO: add test for extensions
        return false;
    }
    
    public static boolean bridgeIEquivequiv(Object k1, Object k2){
        if (k1 instanceof IEquiv)
            return RT.booleanCast(((IEquiv)k1)._equiv(k2));
        else if (k2 instanceof IEquiv)
            return RT.booleanCast(((IEquiv)k2)._equiv(k1));
        else
            return false;
        // TODO: add handling if extended
    }
    
}
