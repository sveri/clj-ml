(ns de.sveri.cfml.chaptertwo
  (:require [clatrix.core :as cl]
            [incanter.charts :as charts]
            [incanter.core :as ic]
            [incanter.stats :as stats]
            [incanter.datasets :as id]))

(comment (def X (cl/matrix [8.401 14.475 13.396 12.127 5.044
                            8.339 15.692 17.108 9.253 12.029]))

         (def Y (cl/matrix [-1.57 2.32 0.424 0.814 -2.3
                            0.01 1.954 2.296 -0.635 0.328]))

         (def linear-samp-scatter (charts/scatter-plot X Y))

         (defn plot-scatter [] (ic/view linear-samp-scatter))


         (def samp-linear-model (stats/linear-model Y X))

         (defn plot-model []
           (ic/view (charts/add-lines linear-samp-scatter
                                      X (:fitted samp-linear-model)))))


(def gradient-descent-precision 0.001)

(defn gradient-descent [F' x-start step]
  (loop [x-old x-start]
    (let [x-new (- x-old (* step (F' x-old)))
          dx (- x-new x-old)]
      (if (< dx gradient-descent-precision)
        x-new
        (recur x-new)))))






(def iris (ic/to-matrix (id/get-dataset :iris)))

(def X (ic/sel iris :cols (range 1 5)))
(def Y (ic/sel iris :cols 0))

(def iris-linear-model (stats/linear-model Y X))

(defn plot-iris-linear-model []
  (let [x (range -100 100)
        y (:fitted iris-linear-model)]
    (ic/view (charts/xy-plot x y))))