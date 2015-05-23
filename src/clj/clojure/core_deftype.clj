;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(in-ns 'clojure.core)

;;;;;;;;;;;;;;;;;;;;;;;;;;;; definterface ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;(set! *warn-on-reflection* true)

(defn namespace-munge
  "Convert a Clojure namespace name to a legal Java package name."
  {:added "1.2"}
  [ns]
  (.replace (str ns) \- \_))

;for now, built on gen-interface
(defmacro definterface
  "Creates a new Java interface with the given name and method sigs.
  The method return types and parameter types may be specified with type hints,
  defaulting to Object if omitted.

  (definterface MyInterface
    (^int method1 [x])
    (^Bar method2 [^Baz b ^Quux q]))"
  {:added "1.2"} ;; Present since 1.2, but made public in 1.5.
  [name & sigs]
  (let [tag (fn [x] (or (:tag (meta x)) Object))
        psig (fn [[name [& args]]]
               (vector name (vec (map tag args)) (tag name) (map meta args)))
        cname (with-meta (symbol (str (namespace-munge *ns*) "." name)) (meta name))]
    `(let [] 
       (gen-interface :name ~cname :methods ~(vec (map psig sigs)))
       (import ~cname))))

(defmacro definterface2
  "Like definterface, but does not import interface into *ns*."
  [name & sigs]
  (let [tag (fn [x] (or (:tag (meta x)) Object))
        psig (fn [[name [& args]]]
               (vector name (vec (map tag args)) (tag name) (map meta args)))
        cname (with-meta (symbol (str (namespace-munge *ns*) "." name)) (meta name))]
    `(let [] 
       (gen-interface :name ~cname :methods ~(vec (map psig sigs))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;; reify/deftype ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- parse-opts [s]
  (loop [opts {} [k v & rs :as s] s]
    (if (keyword? k)
      (recur (assoc opts k v) rs)
      [opts s])))

(def obj-specs
  '#{"clone" "equals" "finalize" "getClass" "hashCode" "notify" "notifyAll" "toString" "wait"})

(defn- translate-name [proto spec]
  (if (and (not (class? proto))
           (not (nil? proto))
           (var? (resolve proto))
           (:clojure.core/protocol (deref (resolve proto))))
    (let [tr-map (:method-map (deref (resolve proto)))
          n (name (first spec))
          trk (get tr-map (keyword n))
          trk (if (and (nil? trk) (obj-specs n)) n trk)]
      (assert trk (str "Translation of method " (first spec)
                       " not found in protocol " proto
                       " which contains following data "
                       (deref (resolve proto))))
      (cons (with-meta (symbol (name trk)) (meta (first spec)))
            (next spec)))
    spec))

(defn- parse-impls-translated [specs]
  (loop [ret {} s specs]
    (if (seq s)
      (recur (assoc ret (first s)
                    (map #(translate-name (first s) %)
                         (take-while seq? (next s))))
             (drop-while seq? (next s)))
      ret)))

(defn- group-similar
  "make ((-foo [o] :foo) (-foo [o x] :bar)) into
        ((-foo ([o] :foo) ([o x] :bar)))"
  [s]
  (let [add-next (fn [m [n & b]]
                   (update-in m [n] conj b))
        gm (reduce1 add-next {} s)]
    (map #(if (next (second %))
            ;; multiple implementations
            (cons (first %) (second %))
            ;; just one impl, maybe multiarity!!!
            (cons (first %) (first (second %)))) gm)))

(defn- parse-impls [specs]
  (loop [ret {} s specs]
    (if (seq s)
      (recur (assoc ret (first s)
                    (group-similar (take-while seq? (next s))))
             (drop-while seq? (next s)))
      ret)))

(defn- parse-opts+specs [opts+specs]
  (let [[opts specs] (parse-opts opts+specs)
        impls (parse-impls-translated specs)
        interfaces (-> (mapcat #(if (var? (resolve %))
                                  (let [p (deref (resolve %))]
                                    (if (:marker p)
                                      [(:on p) (:marker p)]
                                      [(:on p)]))
                                  [%])
                            (keys impls))
                       set
                       (disj 'Object 'java.lang.Object)
                       vec)
        methods (map (fn [[name params & body]]
                       (cons name (maybe-destructured params body)))
                     (apply concat (vals impls)))]
    (when-let [bad-opts (seq (remove #{:no-print} (keys opts)))]
      (throw (IllegalArgumentException.
              ^java.lang.String
              (apply print-str "Unsupported option(s) -" bad-opts))))
    [interfaces methods opts]))

(defmacro reify 
  "reify is a macro with the following structure:

 (reify options* specs*)
  
  Currently there are no options.

  Each spec consists of the protocol or interface name followed by zero
  or more method bodies:

  protocol-or-interface-or-Object
  (methodName [args+] body)*

  Methods should be supplied for all methods of the desired
  protocol(s) and interface(s). You can also define overrides for
  methods of Object. Note that the first parameter must be supplied to
  correspond to the target object ('this' in Java parlance). Thus
  methods for interfaces will take one more argument than do the
  interface declarations.  Note also that recur calls to the method
  head should *not* pass the target object, it will be supplied
  automatically and can not be substituted.

  The return type can be indicated by a type hint on the method name,
  and arg types can be indicated by a type hint on arg names. If you
  leave out all hints, reify will try to match on same name/arity
  method in the protocol(s)/interface(s) - this is preferred. If you
  supply any hints at all, no inference is done, so all hints (or
  default of Object) must be correct, for both arguments and return
  type. If a method is overloaded in a protocol/interface, multiple
  independent method definitions must be supplied.  If overloaded with
  same arity in an interface you must specify complete hints to
  disambiguate - a missing hint implies Object.

  recur works to method heads The method bodies of reify are lexical
  closures, and can refer to the surrounding local scope:
  
  (str (let [f \"foo\"] 
       (reify Object 
         (toString [this] f))))
  == \"foo\"

  (seq (let [f \"foo\"] 
       (reify clojure.lang.Seqable 
         (seq [this] (seq f)))))
  == (\\f \\o \\o))
  
  reify always implements clojure.lang.IObj and transfers meta
  data of the form to the created object.
  
  (meta ^{:k :v} (reify Object (toString [this] \"foo\")))
  == {:k :v}"
  {:added "1.2"} 
  [& opts+specs]
  (let [[interfaces methods] (parse-opts+specs opts+specs)]
    (with-meta `(reify* ~interfaces ~@methods) (meta &form))))

(defn hash-combine [x y] 
  (clojure.lang.Util/hashCombine x (clojure.lang.Util/hash y)))

(defn munge [s]
  ((if (symbol? s) symbol str) (clojure.lang.Compiler/munge (str s))))

(defn- imap-cons
  [^IPersistentMap this o]
  (cond
   (instance? java.util.Map$Entry o)
     (let [^java.util.Map$Entry pair o]
       (.assoc this (.getKey pair) (.getValue pair)))
   (instance? clojure.lang.IPersistentVector o)
     (let [^clojure.lang.IPersistentVector vec o]
       (.assoc this (.nth vec 0) (.nth vec 1)))
   :else (loop [this this
                o o]
      (if (seq o)
        (let [^java.util.Map$Entry pair (first o)]
          (recur (.assoc this (.getKey pair) (.getValue pair)) (rest o)))
        this))))

(defn- emit-defrecord 
  "Do not use this directly - use defrecord"
  {:added "1.2"}
  [tagname name fields interfaces methods]
  (let [classname (with-meta (symbol (str (namespace-munge *ns*) "." name)) (meta name))
        interfaces (vec interfaces)
        interface-set (set (map resolve interfaces))
        methodname-set (set (map first methods))
        hinted-fields fields
        fields (vec (map #(with-meta % nil) fields))
        base-fields fields
        fields (conj fields '__meta '__extmap)
        type-hash (hash classname)]
    (when (some #{:volatile-mutable :unsynchronized-mutable} (mapcat (comp keys meta) hinted-fields))
      (throw (IllegalArgumentException. ":volatile-mutable or :unsynchronized-mutable not supported for record fields")))
    (let [gs (gensym)]
    (letfn 
     [(irecord [[i m]]
        [(conj i 'clojure.lang.IRecord)
         m])
      (eqhash [[i m]] 
        [(conj i 'clojure.lang.IHashEq)
         (conj m
               `(hasheq [this#] (bit-xor ~type-hash (clojure.lang.APersistentMap/mapHasheq this#)))
               `(hashCode [this#] (clojure.lang.APersistentMap/mapHash this#))
               `(equals [this# ~gs] (clojure.lang.APersistentMap/mapEquals this# ~gs)))])
      (iobj [[i m]] 
            [(conj i 'clojure.lang.IObj)
             (conj m `(meta [this#] ~'__meta)
                   `(withMeta [this# ~gs] (new ~tagname ~@(replace {'__meta gs} fields))))])
      (ilookup [[i m]] 
         [(conj i 'clojure.lang.ILookup 'clojure.lang.IKeywordLookup)
          (conj m `(valAt [this# k#] (.valAt this# k# nil))
                `(valAt [this# k# else#] 
                   (case k# ~@(mapcat (fn [fld] [(keyword fld) fld]) 
                                       base-fields)
                         (get ~'__extmap k# else#)))
                `(getLookupThunk [this# k#]
                   (let [~'gclass (class this#)]              
                     (case k#
                           ~@(let [hinted-target (with-meta 'gtarget {:tag tagname})] 
                               (mapcat 
                                (fn [fld]
                                  [(keyword fld)
                                   `(reify clojure.lang.ILookupThunk
                                           (get [~'thunk ~'gtarget]
                                                (if (identical? (class ~'gtarget) ~'gclass)
                                                  (. ~hinted-target ~(symbol (str "-" fld)))
                                                  ~'thunk)))])
                                base-fields))
                           nil))))])
      (imap [[i m]] 
            [(conj i 'clojure.lang.IPersistentMap)
             (conj m 
                   `(count [this#] (+ ~(count base-fields) (count ~'__extmap)))
                   `(empty [this#] (throw (UnsupportedOperationException. (str "Can't create empty: " ~(str classname)))))
                   `(cons [this# e#] ((var imap-cons) this# e#))
                   `(equiv [this# ~gs] 
                        (boolean 
                         (or (identical? this# ~gs)
                             (when (identical? (class this#) (class ~gs))
                               (let [~gs ~(with-meta gs {:tag tagname})]
                                 (and  ~@(map (fn [fld] `(= ~fld (. ~gs ~(symbol (str "-" fld))))) base-fields)
                                       (= ~'__extmap (. ~gs ~'__extmap))))))))
                   `(containsKey [this# k#] (not (identical? this# (.valAt this# k# this#))))
                   `(entryAt [this# k#] (let [v# (.valAt this# k# this#)]
                                            (when-not (identical? this# v#)
                                              (clojure.lang.MapEntry. k# v#))))
                   `(seq [this#] (seq (concat [~@(map #(list `new `clojure.lang.MapEntry (keyword %) %) base-fields)] 
                                              ~'__extmap)))
                   `(iterator [~gs]
                        (clojure.lang.RecordIterator. ~gs [~@(map keyword base-fields)] (RT/iter ~'__extmap)))
                   `(assoc [this# k# ~gs]
                     (condp identical? k#
                       ~@(mapcat (fn [fld]
                                   [(keyword fld) (list* `new tagname (replace {fld gs} fields))])
                                 base-fields)
                       (new ~tagname ~@(remove #{'__extmap} fields) (assoc ~'__extmap k# ~gs))))
                   `(without [this# k#] (if (contains? #{~@(map keyword base-fields)} k#)
                                            (dissoc (with-meta (into {} this#) ~'__meta) k#)
                                            (new ~tagname ~@(remove #{'__extmap} fields) 
                                                 (not-empty (dissoc ~'__extmap k#))))))])
      (ijavamap [[i m]]
                [(conj i 'java.util.Map 'java.io.Serializable)
                 (conj m
                       `(size [this#] (.count this#))
                       `(isEmpty [this#] (= 0 (.count this#)))
                       `(containsValue [this# v#] (boolean (some #{v#} (vals this#))))
                       `(get [this# k#] (.valAt this# k#))
                       `(put [this# k# v#] (throw (UnsupportedOperationException.)))
                       `(remove [this# k#] (throw (UnsupportedOperationException.)))
                       `(putAll [this# m#] (throw (UnsupportedOperationException.)))
                       `(clear [this#] (throw (UnsupportedOperationException.)))
                       `(keySet [this#] (set (keys this#)))
                       `(values [this#] (vals this#))
                       `(entrySet [this#] (set this#)))])
      ]
     (let [[i m] (-> [interfaces methods] irecord eqhash iobj ilookup imap ijavamap)]
       `(deftype* ~tagname ~classname ~(conj hinted-fields '__meta '__extmap) 
          :implements ~(vec i) 
          ~@m))))))

(defn- build-positional-factory
  "Used to build a positional factory for a given type/record.  Because of the
  limitation of 20 arguments to Clojure functions, this factory needs to be
  constructed to deal with more arguments.  It does this by building a straight
  forward type/record ctor call in the <=20 case, and a call to the same
  ctor pulling the extra args out of the & overage parameter.  Finally, the
  arity is constrained to the number of expected fields and an ArityException
  will be thrown at runtime if the actual arg count does not match."
  [nom classname fields]
  (let [fn-name (symbol (str '-> nom))
        [field-args over] (split-at 20 fields)
        field-count (count fields)
        arg-count (count field-args)
        over-count (count over)
        docstring (str "Positional factory function for class " classname ".")]
    `(defn ~fn-name
       ~docstring
       [~@field-args ~@(if (seq over) '[& overage] [])]
       ~(if (seq over)
          `(if (= (count ~'overage) ~over-count)
             (new ~classname
                  ~@field-args
                  ~@(for [i (range 0 (count over))]
                      (list `nth 'overage i)))
             (throw (clojure.lang.ArityException. (+ ~arg-count (count ~'overage)) (name '~fn-name))))
          `(new ~classname ~@field-args)))))

(defn- validate-fields
  ""
  [fields name]
  (when-not (vector? fields)
    (throw (AssertionError. "No fields vector given.")))
  (let [specials #{'__meta '__extmap}]
    (when (some specials fields)
      (throw (AssertionError. (str "The names in " specials " cannot be used as field names for types or records.")))))
  (let [non-syms (remove symbol? fields)]
    (when (seq non-syms)
      (throw (clojure.lang.Compiler$CompilerException.
              *file*
              (.deref clojure.lang.Compiler/LINE)
              (.deref clojure.lang.Compiler/COLUMN)
              (AssertionError.
               (str "defrecord and deftype fields must be symbols, "
                    *ns* "." name " had: "
                    (apply str (interpose ", " non-syms)))))))))

(defmacro defrecord
  "(defrecord name [fields*]  options* specs*)
  
  Currently there are no options.

  Each spec consists of a protocol or interface name followed by zero
  or more method bodies:

  protocol-or-interface-or-Object
  (methodName [args*] body)*

  Dynamically generates compiled bytecode for class with the given
  name, in a package with the same name as the current namespace, the
  given fields, and, optionally, methods for protocols and/or
  interfaces.

  The class will have the (immutable) fields named by
  fields, which can have type hints. Protocols/interfaces and methods
  are optional. The only methods that can be supplied are those
  declared in the protocols/interfaces.  Note that method bodies are
  not closures, the local environment includes only the named fields,
  and those fields can be accessed directly.

  Method definitions take the form:

  (methodname [args*] body)

  The argument and return types can be hinted on the arg and
  methodname symbols. If not supplied, they will be inferred, so type
  hints should be reserved for disambiguation.

  Methods should be supplied for all methods of the desired
  protocol(s) and interface(s). You can also define overrides for
  methods of Object. Note that a parameter must be supplied to
  correspond to the target object ('this' in Java parlance). Thus
  methods for interfaces will take one more argument than do the
  interface declarations. Note also that recur calls to the method
  head should *not* pass the target object, it will be supplied
  automatically and can not be substituted.

  In the method bodies, the (unqualified) name can be used to name the
  class (for calls to new, instance? etc).

  The class will have implementations of several (clojure.lang)
  interfaces generated automatically: IObj (metadata support) and
  IPersistentMap, and all of their superinterfaces.

  In addition, defrecord will define type-and-value-based =,
  and will defined Java .hashCode and .equals consistent with the
  contract for java.util.Map.

  When AOT compiling, generates compiled bytecode for a class with the
  given name (a symbol), prepends the current ns as the package, and
  writes the .class file to the *compile-path* directory.

  Two constructors will be defined, one taking the designated fields
  followed by a metadata map (nil for none) and an extension field
  map (nil for none), and one taking only the fields (using nil for
  meta and extension fields). Note that the field names __meta
  and __extmap are currently reserved and should not be used when
  defining your own records.

  Given (defrecord TypeName ...), two factory functions will be
  defined: ->TypeName, taking positional parameters for the fields,
  and map->TypeName, taking a map of keywords to field values."
  {:added "1.2"
   :arglists '([name [& fields] & opts+specs])}

  [name fields & opts+specs]
  (validate-fields fields name)
  (let [gname name
        [interfaces methods opts] (parse-opts+specs opts+specs)
        ns-part (namespace-munge *ns*)
        classname (symbol (str ns-part "." gname))
        hinted-fields fields
        fields (vec (map #(with-meta % nil) fields))]
    `(let []
       (declare ~(symbol (str  '-> gname)))
       (declare ~(symbol (str 'map-> gname)))
       ~(emit-defrecord name gname (vec hinted-fields) (vec interfaces) methods)
       (import ~classname)
       ~(build-positional-factory gname classname fields)
       (defn ~(symbol (str 'map-> gname))
         ~(str "Factory function for class " classname ", taking a map of keywords to field values.")
         ([m#] (~(symbol (str classname "/create"))
                (if (instance? clojure.lang.MapEquivalence m#) m# (into {} m#)))))
       ~classname)))

(defmacro defrecord2
  "Like defrecord but does not import Class and defines type object map
   with the structure similar to the protocol object map."
  {:arglists '([name [& fields] & opts+specs])}
  [name fields & opts+specs]
  (validate-fields fields name)
  (let [gname name
        [interfaces methods opts] (parse-opts+specs opts+specs)
        ns-part (namespace-munge *ns*)
        classname (symbol (str ns-part "." gname))
        hinted-fields fields
        fields (vec (map #(with-meta % nil) fields))]
    `(let []      
       (declare ~(symbol (str  '-> gname)))
       (declare ~(symbol (str 'map-> gname)))
       ~(emit-defrecord name gname (vec hinted-fields) (vec interfaces) methods)
       ~(build-positional-factory gname classname fields)
       (defn ~(symbol (str 'map-> gname))
         ~(str "Factory function for class " classname ", taking a map of keywords to field values.")
         ([m#] (~(symbol (str classname "/create"))
                (if (instance? clojure.lang.MapEquivalence m#) m# (into {} m#)))))
       (def ~name {:on '~classname
                   :on-class ~classname
                   ::record true}))))

(defn record?
  "Returns true if x is a record"
  {:added "1.6"
   :static true}
  [x]
  (instance? clojure.lang.IRecord x))

(defn- emit-deftype*
  "Do not use this directly - use deftype"
  [tagname name fields interfaces methods]
  (let [classname (with-meta (symbol (str (namespace-munge *ns*) "." name)) (meta name))
        interfaces (conj interfaces 'clojure.lang.IType)]
    `(deftype* ~tagname ~classname ~fields 
       :implements ~interfaces 
       ~@methods)))

(defmacro deftype
  "(deftype name [fields*]  options* specs*)
  
  Currently there are no options.

  Each spec consists of a protocol or interface name followed by zero
  or more method bodies:

  protocol-or-interface-or-Object
  (methodName [args*] body)*

  Dynamically generates compiled bytecode for class with the given
  name, in a package with the same name as the current namespace, the
  given fields, and, optionally, methods for protocols and/or
  interfaces. 

  The class will have the (by default, immutable) fields named by
  fields, which can have type hints. Protocols/interfaces and methods
  are optional. The only methods that can be supplied are those
  declared in the protocols/interfaces.  Note that method bodies are
  not closures, the local environment includes only the named fields,
  and those fields can be accessed directly. Fields can be qualified
  with the metadata :volatile-mutable true or :unsynchronized-mutable
  true, at which point (set! afield aval) will be supported in method
  bodies. Note well that mutable fields are extremely difficult to use
  correctly, and are present only to facilitate the building of higher
  level constructs, such as Clojure's reference types, in Clojure
  itself. They are for experts only - if the semantics and
  implications of :volatile-mutable or :unsynchronized-mutable are not
  immediately apparent to you, you should not be using them.

  Method definitions take the form:

  (methodname [args*] body)

  The argument and return types can be hinted on the arg and
  methodname symbols. If not supplied, they will be inferred, so type
  hints should be reserved for disambiguation.

  Methods should be supplied for all methods of the desired
  protocol(s) and interface(s). You can also define overrides for
  methods of Object. Note that a parameter must be supplied to
  correspond to the target object ('this' in Java parlance). Thus
  methods for interfaces will take one more argument than do the
  interface declarations. Note also that recur calls to the method
  head should *not* pass the target object, it will be supplied
  automatically and can not be substituted.

  In the method bodies, the (unqualified) name can be used to name the
  class (for calls to new, instance? etc).

  When AOT compiling, generates compiled bytecode for a class with the
  given name (a symbol), prepends the current ns as the package, and
  writes the .class file to the *compile-path* directory.

  One constructor will be defined, taking the designated fields.  Note
  that the field names __meta and __extmap are currently reserved and
  should not be used when defining your own types.

  Given (deftype TypeName ...), a factory function called ->TypeName
  will be defined, taking positional parameters for the fields"
  {:added "1.2"
   :arglists '([name [& fields] & opts+specs])}

  [name fields & opts+specs]
  (validate-fields fields name)
  (let [gname name
        [interfaces methods opts] (parse-opts+specs opts+specs)
        ns-part (namespace-munge *ns*)
        classname (symbol (str ns-part "." gname))
        hinted-fields fields
        fields (vec (map #(with-meta % nil) fields))
        [field-args over] (split-at 20 fields)]
    `(do
       ~(emit-deftype* name gname (vec hinted-fields) (vec interfaces) methods)
       (import ~classname)
       ~(build-positional-factory gname classname fields)
       ~classname)))

(defmacro deftype2
  "Like deftype but does not import Class and defines type object map with
   the structure similar to the protocol object map."
  {:arglists '([name [& fields] & opts+specs])}
  [name fields & opts+specs]
  (validate-fields fields name)
  (let [gname name
        [interfaces methods opts] (parse-opts+specs opts+specs)
        ns-part (namespace-munge *ns*)
        classname (symbol (str ns-part "." gname))
        hinted-fields fields
        fields (vec (map #(with-meta % nil) fields))
        [field-args over] (split-at 20 fields)]
    `(do
       (declare ~(symbol (str  '-> gname)))
       ~(emit-deftype* name gname (vec hinted-fields)
                       (vec interfaces) methods)
       ~(build-positional-factory gname classname fields)
       (def ~name
         {:on '~classname
          :on-class ~classname
          :tsig ~(:tsig (meta name))
          ::type true}))))

;;;;;;;;;;;;;;;;;;;;;;; protocols ;;;;;;;;;;;;;;;;;;;;;;;;

(defn- expand-method-impl-cache [^clojure.lang.MethodImplCache cache c f]
  (if (.map cache)
    (let [cs (assoc (.map cache) c (clojure.lang.MethodImplCache$Entry. c f))]
      (clojure.lang.MethodImplCache. (.protocol cache) (.methodk cache) cs))
    (let [cs (into1 {} (remove (fn [[c e]] (nil? e)) (map vec (partition 2 (.table cache)))))
          cs (assoc cs c (clojure.lang.MethodImplCache$Entry. c f))]
      (if-let [[shift mask] (maybe-min-hash (map hash (keys cs)))]
        (let [table (make-array Object (* 2 (inc mask)))
              table (reduce1 (fn [^objects t [c e]]
                               (let [i (* 2 (int (shift-mask shift mask (hash c))))]
                                 (aset t i c)
                                 (aset t (inc i) e)
                                 t))
                             table cs)]
          (clojure.lang.MethodImplCache. (.protocol cache) (.methodk cache) shift mask table))
        (clojure.lang.MethodImplCache. (.protocol cache) (.methodk cache) cs)))))

(defn- super-chain [^Class c]
  (when c
    (cons c (super-chain (.getSuperclass c)))))

(defn- pref
  ([] nil)
  ([a] a) 
  ([^Class a ^Class b]
     (if (.isAssignableFrom a b) b a)))

(defn find-protocol-impl [protocol x]
  (if (and (instance? ^java.lang.Class (:on-interface protocol) x)
           (or (not (:marker-interface protocol))
               (:marker-soft protocol)
               (instance? ^java.lang.Class (:marker-interface protocol) x)
               (some #(instance? ^java.lang.Class % x)
                     (:marker-types protocol))))
    (or x true)
    (let [c (class x)
          impl #(get (:impls protocol) %)]
      (or (impl c)
          (and c (or (first (remove nil? (map impl (butlast (super-chain c)))))
                     (when-let [t (reduce1 pref (filter impl (disj (supers c) Object)))]
                       (impl t))
                     (impl Object)))
          (and c (.isArray c) (impl :array))))))

(defn find-protocol-method [protocol methodk x]
  (get (find-protocol-impl protocol x) methodk))

(defn- protocol?
  [maybe-p]
  (boolean (:on-interface maybe-p)))

(defn- implements? [protocol atype]
  (and atype (.isAssignableFrom ^Class (:on-interface protocol) atype)))

(defn ^java.lang.Boolean extends? 
  "Returns true if atype extends protocol"
  {:added "1.2"}
  ^boolean [protocol atype]
  (boolean (or (implements? protocol atype) 
               (get (:impls protocol) atype))))

(defn extenders 
  "Returns a collection of the types explicitly extending protocol"
  {:added "1.2"}
  [protocol]
  (keys (:impls protocol)))

(defrecord PRR [satisfies-dispatch impls on-interface
              marker-interface marker-soft marker-types])

(def ep (->PRR nil nil nil nil nil nil))

(defn ^java.lang.Boolean satisfies-direct?
  ^boolean [^clojure.core.PRR protocol x]
  (and (instance? ^java.lang.Class (.-on-interface protocol) x)
       (or (not (.-marker-interface protocol))
           (boolean (.-marker-soft protocol))
           (instance? ^java.lang.Class (.-marker-interface protocol) x)
           (boolean (some #(instance? ^java.lang.Class % x)
                          (.-marker-types protocol))))))

(defn satisfies?
  "Returns true if x satisfies the protocol"
  {:added "1.2"
   :tag Boolean}
  ^boolean [^clojure.core.PRR protocol x]
  (boolean (or (satisfies-direct? protocol x)
               (let [c (class x)
                     impl #(get (.-impls protocol) %)]
                 (or (impl c)
                     (let [arr ^objects (.-satisfies-dispatch protocol)]
                       (and c arr
                            (let [l (alength arr)]
                              (loop [i 0]
                                (cond (== i l) false
                                      (instance? ^java.lang.Class (aget arr i) x) true
                                      :else (recur (unchecked-inc i))))))))))))

(defn -cache-protocol-fn [^clojure.lang.AFunction pf x ^Class c ^clojure.lang.IFn interf]
  (let [cache  (.__methodImplCache pf)
        f (if (.isInstance c x)
            interf 
            (find-protocol-method (.protocol cache) (.methodk cache) x))]
    (when-not f
      (throw (IllegalArgumentException. (str "No implementation of method: " (.methodk cache) 
                                             " of protocol: " (:var (.protocol cache)) 
                                             " found for class: " (if (nil? x) "nil" (.getName (class x)))))))
    (set! (.__methodImplCache pf) (expand-method-impl-cache cache (class x) f))
    f))

(defn- emit-method-builder [on-interface method on-method arglists]
  (let [methodk (keyword method)
        gthis (with-meta (gensym) {:tag 'clojure.lang.AFunction})
        ginterf (gensym)]
    `(fn [cache#]
       (let [~ginterf
             (fn
               ~@(map 
                  (fn [args]
                    (let [gargs (map #(gensym (str "gf__" % "__")) args)
                          target (first gargs)]
                      `([~@gargs]
                          (. ~(with-meta target {:tag on-interface}) (~(or on-method method) ~@(rest gargs))))))
                  arglists))
             ^clojure.lang.AFunction f#
             (fn ~gthis
               ~@(map 
                  (fn [args]
                    (let [gargs (map #(gensym (str "gf__" % "__")) args)
                          target (first gargs)]
                      `([~@gargs]
                          (let [cache# (.__methodImplCache ~gthis)
                                f# (.fnFor cache# (clojure.lang.Util/classOf ~target))]
                            (if f# 
                              (f# ~@gargs)
                              ((-cache-protocol-fn ~gthis ~target ~on-interface ~ginterf) ~@gargs))))))
                  arglists))]
         (set! (.__methodImplCache f#) cache#)
         f#))))

(defn- emit-method-builder2 [on-interface method on-method arglists hints]
  (let [methodk (keyword method)
        gthis (with-meta (gensym) {:tag 'clojure.lang.AFunction})
        ginterf (gensym)]
    `(fn [cache#]
       (let [~ginterf
             (fn
               ~@(map 
                  (fn [args]
                    (let [gargs (map #(gensym (str "gf__" % "__")) args)
                          target (first gargs)
                          attach-hint (fn [s h] (if (nil? h) s (with-meta s {:tag h})))
                          rgargs (map attach-hint (rest gargs) (concat hints (repeat nil)))]
                      `([~@gargs]
                          (. ~(with-meta target {:tag on-interface}) (~(or on-method method) ~@rgargs)))))
                  arglists))
             ^clojure.lang.AFunction f#
             (fn ~gthis
               ~@(map 
                  (fn [args]
                    (let [gargs (map #(gensym (str "gf__" % "__")) args)
                          target (first gargs)]
                      `([~@gargs]
                          (let [cache# (.__methodImplCache ~gthis)
                                f# (.fnFor cache# (clojure.lang.Util/classOf ~target))]
                            (if f# 
                              (f# ~@gargs)
                              ((-cache-protocol-fn ~gthis ~target ~on-interface ~ginterf) ~@gargs))))))
                  arglists))]
         (set! (.__methodImplCache f#) cache#)
         f#))))

(defn -reset-methods [protocol]
  (doseq [[^clojure.lang.Var v build] (:method-builders protocol)]
    (let [cache (clojure.lang.MethodImplCache. protocol (keyword (.sym v)))]
      (.bindRoot v (build cache)))))

(defn- assert-same-protocol [protocol-var method-syms]
  (doseq [m method-syms]
    (let [v (resolve m)
          p (:protocol (meta v))]
      (when (and v (bound? v) (not= protocol-var p))
        (binding [*out* *err*]
          (println "Warning: protocol" protocol-var "is overwriting"
                   (if p
                     (str "method " (.sym v) " of protocol " (.sym p))
                     (str "function " (.sym v)))))))))

;; massive hacks, i'm really sorry

(defmacro finish-emit-protocol [name opts+sigs]
  (let [iname (symbol (str (munge (namespace-munge *ns*)) "." (munge name)))
        forbid-extensions? (:forbid-extensions (meta name))
        [opts sigs]
        (loop [opts {:on (list 'quote iname) :on-interface iname} sigs opts+sigs]
          (condp #(%1 %2) (first sigs)
            string? (recur (assoc opts :doc (first sigs)) (next sigs))
            keyword? (recur (assoc opts (first sigs) (second sigs)) (nnext sigs))
            [opts sigs]))
        sigs (when sigs
               (reduce1 (fn [m s]
                          (let [name-meta (meta (first s))
                                [oh hints] (when-let [pf (:pf name-meta)] (pf))
                                name-meta (dissoc name-meta :pf)
                                name-meta (if (and oh (not (:tag name-meta)))
                                            (assoc name-meta :tag oh)
                                            name-meta)
                                name-meta (if (and hints (not (:hints name-meta)))
                                            (assoc name-meta :hints hints)
                                            name-meta)
                                mname (with-meta (first s) nil)
                                [arglists doc]
                                (loop [as [] rs (rest s)]
                                  (if (vector? (first rs))
                                    (recur (conj as (first rs)) (next rs))
                                    [(seq as) (first rs)]))
                                doc (or doc (get name-meta :doc))]
                            (when (some #{0} (map count arglists))
                              (throw (IllegalArgumentException. (str "Definition of function " mname " in protocol " name " must take at least one arg."))))
                            (when (m (keyword mname))
                              (throw (IllegalArgumentException. (str "Function " mname " in protocol " name " was redefined. Specify all arities in single definition."))))
                            (assoc m (keyword mname)
                                   (merge name-meta
                                          {:name (vary-meta mname assoc :doc doc :arglists arglists)
                                           :arglists arglists
                                           :doc doc}))))
                        {} sigs))]
  `(do
     (alter-var-root (var ~name) merge 
                     (assoc ~opts
                       ::protocol true
                       :sigs '~sigs 
                       :var (var ~name)
                       :forbid-extensions ~forbid-extensions?
                       :method-map 
                         ~(and (:on opts)
                               (apply hash-map 
                                      (mapcat 
                                       (fn [s] 
                                         [(keyword (:name s)) (keyword (or (:on s) (:name s)))])
                                       (vals sigs))))
                       :method-builders 
                        ~(apply hash-map 
                                (mapcat 
                                 (fn [s]
                                   [`(intern *ns* (with-meta '~(:name s) (merge '~s {:protocol (var ~name)})))
                                    (emit-method-builder (:on-interface opts) (:name s) (:on s) (:arglists s))])
                                 (vals sigs)))))
     (-reset-methods ~name)
     '~name)))

(defn- emit-protocol [name opts+sigs]
  (let [iname (symbol (str (munge (namespace-munge *ns*)) "." (munge name)))
        forbid-extensions? (:forbid-extensions (meta name))
        [opts sigs]
        (loop [opts {:on (list 'quote iname) :on-interface iname} sigs opts+sigs]
          (condp #(%1 %2) (first sigs)
            string? (recur (assoc opts :doc (first sigs)) (next sigs))
            keyword? (recur (assoc opts (first sigs) (second sigs)) (nnext sigs))
            [opts sigs]))
        sigs (when sigs
               (reduce1 (fn [m s]
                          (let [name-meta (meta (first s))
                                name-meta (dissoc name-meta :pf)
                                mname (with-meta (first s) nil)
                                [arglists doc]
                                (loop [as [] rs (rest s)]
                                  (if (vector? (first rs))
                                    (recur (conj as (first rs)) (next rs))
                                    [(seq as) (first rs)]))
                                doc (or doc (get name-meta :doc))]
                            (when (some #{0} (map count arglists))
                              (throw (IllegalArgumentException. (str "Definition of function " mname " in protocol " name " must take at least one arg."))))
                            (when (m (keyword mname))
                              (throw (IllegalArgumentException. (str "Function " mname " in protocol " name " was redefined. Specify all arities in single definition."))))
                            (assoc m (keyword mname)
                                   (merge name-meta
                                          {:name (vary-meta mname assoc :doc doc :arglists arglists)
                                           :arglists arglists
                                           :doc doc}))))
                        {} sigs))
        meths (mapcat (fn [sig]
                        (let [m (munge (:name sig))]
                          (map #(vector m (vec (repeat (dec (count %))'Object)) 'Object) 
                               (:arglists sig))))
                      (vals sigs))]
  `(do
     (defonce ~name ep)
     (alter-var-root (var ~name) #(merge ep %))
     (gen-interface :name ~iname :methods ~meths)
     (alter-meta! (var ~name) assoc :doc ~(:doc opts))
     (alter-meta! (var ~name) merge ~(meta name))
     ~(when sigs
        `(#'assert-same-protocol (var ~name) '~(map :name (vals sigs))))
     (alter-var-root (var ~name) merge 
                     (assoc ~opts
                       ::protocol true
                       :sigs '~sigs 
                       :forbid-extensions ~forbid-extensions?
                       :var (var ~name))))))

(defmacro finish-emit-protocol2 [name opts+sigs]
  (let [iname (:on-interface (meta name))
        soft-marker? (not (:distinct (meta name)))
        forbid-extensions? (:forbid-extensions (meta name))
        imarker (symbol (str (munge (namespace-munge *ns*)) "." (munge name) (munge "MARKER")))
        [opts sigs]
        (loop [opts {:on (list 'quote iname) :on-interface iname} sigs opts+sigs]
          (condp #(%1 %2) (first sigs) 
            string? (recur (assoc opts :doc (first sigs)) (next sigs))
            keyword? (recur (assoc opts (first sigs) (second sigs)) (nnext sigs))
            [opts sigs]))
        sigs (when sigs
               (reduce1 (fn [m s]
                          (let [name-meta (meta (first s))
                                [oh hints] (when-let [pf (:pf name-meta)] (pf))
                                name-meta (dissoc name-meta :pf)
                                name-meta (if (and oh (not (:tag name-meta)))
                                            (assoc name-meta :tag oh)
                                            name-meta)
                                name-meta (if (and hints (not (:hints name-meta)))
                                            (assoc name-meta :hints hints)
                                            name-meta)
                                mname (with-meta (first s) nil)
                                [arglists doc]
                                (loop [as [] rs (rest s)]
                                  (if (vector? (first rs))
                                    (recur (conj as (first rs)) (next rs))
                                    [(seq as) (first rs)]))
                                doc (or doc (get name-meta :doc))]
                            (when (some #{0} (map count arglists))
                              (throw (IllegalArgumentException. (str "Definition of function " mname " in protocol " name " must take at least one arg."))))
                            (when (m (keyword mname))
                              (throw (IllegalArgumentException. (str "Function " mname " in protocol " name " was redefined. Specify all arities in single definition."))))
                            (assoc m (keyword mname)
                                   (merge name-meta
                                          {:name (vary-meta mname assoc :doc doc :arglists arglists)
                                           :arglists arglists
                                           :doc doc}))))
                        {} sigs))]
  `(do
     (alter-var-root (var ~name) merge 
                     (assoc ~opts
                       ::protocol true
                       :marker '~imarker
                       :marker-interface ~imarker
                       :marker-types #{}
                       :marker-soft ~soft-marker?
                       :forbid-extensions ~forbid-extensions?
                       :sigs '~sigs 
                       :var (var ~name)
                       :method-map 
                         ~(and (:on opts)
                               (apply hash-map 
                                      (mapcat 
                                       (fn [s] 
                                         [(keyword (:name s)) (keyword (or (:on s) (:name s)))])
                                       (vals sigs))))
                       :method-builders 
                        ~(apply hash-map 
                                (mapcat 
                                 (fn [s]
                                   [`(intern *ns* (with-meta '~(:name s) (merge '~s {:protocol (var ~name)})))
                                    (emit-method-builder2 (:on-interface opts) (:name s) (:on s) (:arglists s) (:hints s))])
                                 (vals sigs)))))
     (-reset-methods ~name)
     '~name)))

(defn- emit-protocol2 [name opts+sigs]
  (let [iname (:on-interface (meta name))
        soft-marker? (not (:distinct (meta name)))
        forbid-extensions? (:forbid-extensions (meta name))
        imarker (symbol (str (munge (namespace-munge *ns*)) "." (munge name) (munge "MARKER")))
        [opts sigs]
        (loop [opts {:on (list 'quote iname) :on-interface iname} sigs opts+sigs]
          (condp #(%1 %2) (first sigs) 
            string? (recur (assoc opts :doc (first sigs)) (next sigs))
            keyword? (recur (assoc opts (first sigs) (second sigs)) (nnext sigs))
            [opts sigs]))
        sigs (when sigs
               (reduce1 (fn [m s]
                          (let [name-meta (meta (first s))
                                name-meta (dissoc name-meta :pf)
                                mname (with-meta (first s) nil)
                                [arglists doc]
                                (loop [as [] rs (rest s)]
                                  (if (vector? (first rs))
                                    (recur (conj as (first rs)) (next rs))
                                    [(seq as) (first rs)]))
                                doc (or doc (get name-meta :doc))]
                            (when (some #{0} (map count arglists))
                              (throw (IllegalArgumentException. (str "Definition of function " mname " in protocol " name " must take at least one arg."))))
                            (when (m (keyword mname))
                              (throw (IllegalArgumentException. (str "Function " mname " in protocol " name " was redefined. Specify all arities in single definition."))))
                            (assoc m (keyword mname)
                                   (merge name-meta
                                          {:name (vary-meta mname assoc :doc doc :arglists arglists)
                                           :arglists arglists
                                           :doc doc}))))
                        {} sigs))
        meths (mapcat (fn [sig]
                        (let [m (munge (:name sig))]
                          (map #(vector m (vec (repeat (dec (count %))'Object)) 'Object) 
                               (:arglists sig))))
                      (vals sigs))]
  `(do
     (defonce ~name ep)
     (alter-var-root (var ~name) #(merge ep %))
     (gen-interface :name ~imarker :methods ())
     (alter-meta! (var ~name) assoc :doc ~(:doc opts))
     (alter-meta! (var ~name) merge ~(meta name))
     ~(when sigs
        `(#'assert-same-protocol (var ~name) '~(map :name (vals sigs))))
     (alter-var-root (var ~name) merge 
                     (assoc ~opts
                       ::protocol true
                       :marker '~imarker
                       :marker-interface ~imarker
                       :marker-types #{}
                       :marker-soft ~soft-marker?
                       :forbid-extensions ~forbid-extensions?
                       :var (var ~name))))))

(defmacro defprotocol 
  "A protocol is a named set of named methods and their signatures:
  (defprotocol AProtocolName

    ;optional doc string
    \"A doc string for AProtocol abstraction\"

  ;method signatures
    (bar [this a b] \"bar docs\")
    (baz [this a] [this a b] [this a b c] \"baz docs\"))

  No implementations are provided. Docs can be specified for the
  protocol overall and for each method. The above yields a set of
  polymorphic functions and a protocol object. All are
  namespace-qualified by the ns enclosing the definition The resulting
  functions dispatch on the type of their first argument, which is
  required and corresponds to the implicit target object ('this' in 
  Java parlance). defprotocol is dynamic, has no special compile-time 
  effect, and defines no new types or classes. Implementations of 
  the protocol methods can be provided using extend.

  defprotocol will automatically generate a corresponding interface,
  with the same name as the protocol, i.e. given a protocol:
  my.ns/Protocol, an interface: my.ns.Protocol. The interface will
  have methods corresponding to the protocol functions, and the
  protocol will automatically work with instances of the interface.

  Note that you should not use this interface with deftype or
  reify, as they support the protocol directly:

  (defprotocol P 
    (foo [this]) 
    (bar-me [this] [this y]))

  (deftype Foo [a b c] 
   P
    (foo [this] a)
    (bar-me [this] b)
    (bar-me [this y] (+ c y)))
  
  (bar-me (Foo. 1 2 3) 42)
  => 45

  (foo 
    (let [x 42]
      (reify P 
        (foo [this] 17)
        (bar-me [this] x)
        (bar-me [this y] x))))
  => 17"
  {:added "1.2"} 
  [name & opts+sigs]
  `(do
     ~(emit-protocol name opts+sigs)
     (finish-emit-protocol ~name ~opts+sigs)))

(defmacro defprotocol2
  "Like defprotocol but parasites on existing interface."
  [name & opts+sigs]
  `(do
     ~(emit-protocol2 name opts+sigs)
     (finish-emit-protocol2 ~name ~opts+sigs)))

(defn conj-arr [arr t]
  (to-array (seq (set (remove nil? (cons t (seq arr)))))))

(defn extend 
  "Implementations of protocol methods can be provided using the extend construct:

  (extend AType
    AProtocol
     {:foo an-existing-fn
      :bar (fn [a b] ...)
      :baz (fn ([a]...) ([a b] ...)...)}
    BProtocol 
      {...} 
    ...)
 
  extend takes a type/class (or interface, see below), and one or more
  protocol + method map pairs. It will extend the polymorphism of the
  protocol's methods to call the supplied methods when an AType is
  provided as the first argument. 

  Method maps are maps of the keyword-ized method names to ordinary
  fns. This facilitates easy reuse of existing fns and fn maps, for
  code reuse/mixins without derivation or composition. You can extend
  an interface to a protocol. This is primarily to facilitate interop
  with the host (e.g. Java) but opens the door to incidental multiple
  inheritance of implementation since a class can inherit from more
  than one interface, both of which extend the protocol. It is TBD how
  to specify which impl to use. You can extend a protocol on nil.

  If you are supplying the definitions explicitly (i.e. not reusing
  exsting functions or mixin maps), you may find it more convenient to
  use the extend-type or extend-protocol macros.

  Note that multiple independent extend clauses can exist for the same
  type, not all protocols need be defined in a single extend call.

  See also:
  extends?, satisfies?, extenders"
  {:added "1.2"} 
  [atype & proto+mmaps]
  (let [array? (= :array atype)
        vac (if (or array? (class? atype))
              atype
              (:var atype))
        atype (if (or array? (class? atype))
                atype
                (:on-class atype))]
    (doseq [[proto mmap] (partition 2 proto+mmaps)]
      (when-not (protocol? proto)
        (throw (IllegalArgumentException.
                (str proto " is not a protocol"))))
      (when (and (:forbid-extensions proto)
                 (not (implements? proto atype)))
        (throw (IllegalArgumentException.
                (str "Extensions are forbidden for protocol " (:var proto)))))
      (when (and (not (:marker proto)) (not array?) (implements? proto atype))
        (throw (IllegalArgumentException. 
                (str vac " already directly implements " (:on-interface proto) " for protocol:"  
                     (:var proto)))))
      (if array?
        (-reset-methods (alter-var-root (:var proto) assoc-in [:impls atype] mmap))
        (do
          (when-not (identical? java.lang.Object atype)
            (alter-var-root (:var proto) update-in [:satisfies-dispatch] conj-arr atype))
          (if-not (implements? proto atype)
            (-reset-methods (alter-var-root (:var proto) assoc-in [:impls atype] mmap))
            (if (empty? mmap)
              (alter-var-root (:var proto) update-in [:marker-types] conj atype)
              (throw (IllegalArgumentException. 
                      (str vac " already directly implements " (:on-interface proto) " for marker protocol:"  
                           (:var proto) ". Maybe you wanted to just mark the type?"))))))))))

(defn- emit-impl [[p fs]]
  [p (zipmap (map #(-> % first name keyword) fs)
             (map #(cons `fn (drop 1 %)) fs))])

(defn- emit-hinted-impl [c [p fs]]
  (let [ce (eval c)
        c (if (class? ce) c (:on ce))
        hint (fn [specs]
               (let [specs (if (vector? (first specs)) 
                                        (list specs) 
                                        specs)]
                 (map (fn [[[target & args] & body]]
                        (cons (apply vector (vary-meta target assoc :tag c) args)
                              body))
                      specs)))]
    [p (zipmap (map #(-> % first name keyword) fs)
               (map #(cons `fn (hint (drop 1 %))) fs))]))

(defn- emit-extend-type [c specs]
  (let [impls (parse-impls specs)]
    `(extend ~c
             ~@(mapcat (partial emit-hinted-impl c) impls))))

(defmacro extend-type 
  "A macro that expands into an extend call. Useful when you are
  supplying the definitions explicitly inline, extend-type
  automatically creates the maps required by extend.  Propagates the
  class as a type hint on the first argument of all fns.

  (extend-type MyType 
    Countable
      (cnt [c] ...)
    Foo
      (bar [x y] ...)
      (baz ([x] ...) ([x y & zs] ...)))

  expands into:

  (extend MyType
   Countable
     {:cnt (fn [c] ...)}
   Foo
     {:baz (fn ([x] ...) ([x y & zs] ...))
      :bar (fn [x y] ...)})"
  {:added "1.2"} 
  [t & specs]
  (emit-extend-type t specs))

(defn- emit-extend-protocol [p specs]
  (let [impls (parse-impls specs)]
    `(do
       ~@(map (fn [[t fs]]
                `(extend-type ~t ~p ~@fs))
              impls))))

(defmacro extend-protocol 
  "Useful when you want to provide several implementations of the same
  protocol all at once. Takes a single protocol and the implementation
  of that protocol for one or more types. Expands into calls to
  extend-type:

  (extend-protocol Protocol
    AType
      (foo [x] ...)
      (bar [x y] ...)
    BType
      (foo [x] ...)
      (bar [x y] ...)
    AClass
      (foo [x] ...)
      (bar [x y] ...)
    nil
      (foo [x] ...)
      (bar [x y] ...))

  expands into:

  (do
   (clojure.core/extend-type AType Protocol 
     (foo [x] ...) 
     (bar [x y] ...))
   (clojure.core/extend-type BType Protocol 
     (foo [x] ...) 
     (bar [x y] ...))
   (clojure.core/extend-type AClass Protocol 
     (foo [x] ...) 
     (bar [x y] ...))
   (clojure.core/extend-type nil Protocol 
     (foo [x] ...) 
     (bar [x y] ...)))"
  {:added "1.2"}

  [p & specs]
  (emit-extend-protocol p specs))

