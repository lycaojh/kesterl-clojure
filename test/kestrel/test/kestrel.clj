(ns test.kestrel
  (:require [clojure.string :as string]
            [kestrel :as kestrel])
  (:use clojure.test)
  (:import (net.rubyeye.xmemcached MemcachedClient XMemcachedClientBuilder)
           (net.rubyeye.xmemcached.utils AddrUtil)
           (net.rubyeye.xmemcached.command KestrelCommandFactory)))

(def test-server "localhost:22133")
(def test-queue "queue")
(def test-kestrel-client (kestrel/kestrel test-server))

;;test micro
(deftest test-set
  (let [cli test-kestrel-client]
    (is (kestrel/kset cli test-queue "test1"))
    (is (kestrel/kset cli test-queue "test2"))
    (is (= (kestrel/kget cli test-queue) "test1" ) )
    (is (= (kestrel/kget cli test-queue) "test2"))))

;;test fn
(deftest test-fn-kestrel
  (let [cli test-kestrel-client]
    (is (kestrel/set cli test-queue "test1"))
    (is (kestrel/set cli test-queue "test2"))
    (is (= (kestrel/get cli test-queue) "test1" ) )
    (is (= (kestrel/get cli test-queue) "test2")))
  )
