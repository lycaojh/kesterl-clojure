(ns
  ^{:doc "The core clojure kestrel use xmemcached"
    :author "Junhui cao"}
  kestrel
  (:import (net.rubyeye.xmemcached MemcachedClient XMemcachedClientBuilder)
           (net.rubyeye.xmemcached.utils AddrUtil)
           (net.rubyeye.xmemcached.command KestrelCommandFactory)))


;;(def servers "localhost:11211")
;;(def queue "queue")
(def processors (+ 1 (.availableProcessors (Runtime/getRuntime))))
(def thread (+ 20 processors))

(defn kestrel
  [servers]
  (let [builder (XMemcachedClientBuilder. (AddrUtil/getAddresses servers))]
    (doto builder
      (.setCommandFactory (KestrelCommandFactory.))
      (.setConnectionPoolSize thread))
    (let [rt (.build builder)]
        (.setPrimitiveAsString rt true)
        (.setOpTimeout rt 5000)
        rt)))

(defmacro enqueue
  [method]
  (let [m (symbol method)]
    ` (fn ([^MemcachedClient cli# ^String queue# value#] (. cli# ~m queue# 0 value#))
        ([^MemcachedClient cli# ^String queue# value# ^Integer exp#] (. cli# ~m queue# exp# value#)))))

(defmacro dequeue
  [method]
  (let [m (symbol method)]
    ` (fn ([^MemcachedClient cli# ^String queue#] (. cli# ~m queue# )))))


(def
  ^{:arglists '([client queue value] [client queue value expire])
    :doc "Set an item with key and value."}
  kset
  (enqueue  "set"))
(comment
  (set kestrel queue entity))

(def
  ^{:arglists '([client queue ] )
    :doc "Set an item with key and value."}
  kget
  (dequeue  "get"))
(comment
  (get kestrel queue))

(defn set
  ^{:doc "set string to kestrel" :tag String}
  [kestrel #^{:tag MemcachedClient} queue #^{:tag String} entity]
  (.set kestrel queue 0 entity))

(defn get
  ^{:doc "get string from kestrel" :tag String}
  [^MemcachedClient kestel ^String queue]
  (.get kestrel queue))
