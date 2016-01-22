(ns de.sveri.cfml.chaptertwo
  (:require [clatrix.core :as cl]
            [incanter.charts :as charts]
            [incanter.core :as ic]
            [incanter.stats :as stats]))

(def X (cl/matrix [8.401 14.475 13.396 12.127 5.044
                   8.339 15.692 17.108 9.253 12.029]))

(def Y (cl/matrix [-1.57 2.32  0.424  0.814 -2.3
                   0.01 1.954 2.296 -0.635 0.328]))

(def linear-samp-scatter (charts/scatter-plot X Y))

(defn plot-scatter [] (ic/view linear-samp-scatter))


(def samp-linear-model (stats/linear-model Y X))

(defn plot-model []
  (ic/view (charts/add-lines linear-samp-scatter
                             X (:fitted samp-linear-model))))

;(defn plot-model []
;  (view (add-lines linear-samp-scatter X (:fitted samp-linear-model))))
