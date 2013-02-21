(defproject kestrel-clojure "0.1.0-SNAPSHOT"
      :dependencies [[org.clojure/clojure "1.4.0"]
                     [org.clojure/clojure-contrib "1.2.0"]
                     [org.clojure/tools.logging "0.2.3"]
                     [log4j "1.2.17" :exclusions[javax.mail/mail
                                                 javax.jms/jms
                                                 com.sun.jdmk/jmxtools
                                                 com.sun.jmx/jmxri]]
                     [clj-xmemcached "0.1.1"]
                     [com.googlecode.xmemcached/xmemcached "1.3.5"]]
      :profiles
      {:dev {:dependencies [[log4j/log4j "1.2.16"]
                            [org.slf4j/slf4j-log4j12 "1.5.6"]]}})
