(ns mom-predict.core
  (:gen-class)
  (require [clojure.data.csv :as csv]
           [clojure.java.io :as io]
           [clj-time.core :as ti]))

(defn readcsv []
  (with-open [reader (io/reader "data.csv")]
    (doall
     (csv/read-csv reader))))


(defn selectstatus[statusvalue,filter,total]  
  (println statusvalue
           (format "%3f"
                   (* (float ( / (count (get filter statusvalue)) (count (total))))100))))


(defn pairwithkey[row]
  (zipmap [:id :finnumber
           :passtype :hashnumber :status :date :zg] row))


(defn mapwithkey [totalcsv]
  (selectstatus "Approved",
                (group-by :status
                          (into [] (for [row (totalcsv) :while (seq (first row))]
                                     (pairwithkey row)))),totalcsv))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (mapwithkey readcsv))

