(ns mom-predict.core
  (:gen-class)
  (require [clojure.data.csv :as csv]
           [clojure.java.io :as io]
           [clj-time.core :as ti]))

(defn readcsv []
  (with-open [reader (io/reader "data.csv")]
    (doall
     (csv/read-csv reader))))

(defn selectstatus[statusvalue,x]
  (println statusvalue
           (format "%3f"
                   (float ( / (count (get x statusvalue)) (count x))))))

(defn pairwithkey[y]
  (zipmap [:id :finnumber
           :passtype :hashnumber :status :date :zg] y))


(defn mapwithkey [x]
  (selectstatus "Pending",
                (group-by :status (into [] (for [record (x) :while (seq (first record))]
                                             (pairwithkey record))))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (mapwithkey readcsv))

