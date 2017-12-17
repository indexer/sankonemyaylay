(ns mom-predict.core
  (:gen-class)
  (require [clojure.data.csv :as csv]
           [clojure.java.io :as io]))

(defn readcsv []
  (with-open [reader (io/reader "data.csv")]
    (doall
     (csv/read-csv reader))))



(defn statusfilter [x]
  (println (group-by :date (for [values (x)]
                             (zipmap [:userid :name :date :status] values)))))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (statusfilter readcsv))

