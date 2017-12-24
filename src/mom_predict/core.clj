(ns mom-predict.core
  (:gen-class)
  (require [clojure.data.csv :as csv]
           [clojure.java.io :as io]
           [clj-time.core :as ti]
           [clj-time.format :as f]
           ))

(defn readcsv []
  (with-open [reader (io/reader "data.csv")]
    (doall
     (csv/read-csv reader))))

(def month-formatter (f/formatter "dd/MM/yyyy"))

(defn showpercentage[status,value,total]
  (println "Total" status  (count (get value status)))
  (format "%3f"
          (* (float ( / (count (get value status)) (count (total))))100)))


(defn selectMonth[statusvalue,filter,total]
  (println "Month of year "  statusvalue
           "Total Apply" (count (get filter statusvalue)))
  (println "Total Percentage"
           (showpercentage "Approved",
                           (group-by :status
                                     (for [current (get filter statusvalue)
                                           :while (seq (first current))]
                                       current)),total)))



(defn pairwithkey[row]
  (zipmap [:id :finnumber
           :passtype :hashnumber :status :date :zg]
          (assoc row 5 (ti/month
                        (f/parse month-formatter (get row 5))))))


(defn mapwithkey [totalcsv] 
  (selectMonth 11, (group-by :date
                             (into [] (for [row (totalcsv) :while (seq (first row))]
                                        (pairwithkey row)))),totalcsv))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (mapwithkey readcsv))

