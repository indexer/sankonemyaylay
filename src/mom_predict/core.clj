(ns mom-predict.core
  (:gen-class)
  (require [clojure.data.csv :as csv]
           [clojure.java.io :as io]))

(defn readcsv []
  (with-open [reader (io/reader "data.csv")]
    (doall
     (csv/read-csv reader))))


(defn groupbydate [x]
  (group-by :date (into [] (for [record (x)
                                 :while (seq (first record))]
                             (zipmap [:no :name :date :status] record)))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (groupbydate readcsv))

