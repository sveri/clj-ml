(ns de.sveri.ml.cfml.chaptertwo
  (:require [clatrix.core :as cl]
            [clojure.core.matrix :as m]
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
           (ic/view (charts/add-lines linear-samp-scatter))))


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

(defn linear-model-ols
  "Estimates the coefficients of a multi-var linear
  regression model using Ordinary Least Squares (OLS) method"
  [MX MY]
  (let [X (ic/bind-columns (repeat (m/row-count MX) 1) MX)
        Xt (cl/matrix (m/transpose X))
        Xt-X (cl/* Xt X)]
    (cl/* (m/inverse Xt-X) Xt MY)))

(def ols-linear-model
  (linear-model-ols X Y))

(def ols-linear-model-coefs
  (cl/as-vec ols-linear-model))



(defn predict [coefs X]
  {:pre [(= (count coefs) (+ 1 (count X)))]}
  (let [X-with-1 (conj X 1)
        products (map * coefs X-with-1)]
    (reduce + products)))