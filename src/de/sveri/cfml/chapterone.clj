(ns de.sveri.cfml.chapterone
  (:require [clojure.core.matrix :refer [matrix] :as m]
            [clatrix.core :as cl]
            [clojure.core.matrix.operators :as M]
            [incanter.core :as ic]
            [incanter.charts :as charts]))

(def A (cl/matrix [[0 1 2] [3 4 5]]))

(defn square-mat [n e]
  (let [repeater #(repeat n %)]
    (cl/matrix (-> e repeater repeater))))

(defn id-mat [n]
  (let [init (square-mat n 0)
        id-f (fn [i j _] (if (= i j) 1 0))]
    (cl/map-indexed id-f init)))


(defn rand-mat [n]
  (cl/map rand-int (square-mat n 100)))

(defn lmatrix [n]
  (m/compute-matrix :clatrix [n (+ n 2)]
                  (fn [i j] ({0 -1 , 1 2, 2 -1} (- j i) 0))))


(defn problem
  "Return a map of the problem setup for a given matrix size,
   number of observed values and regularization parameter"
  [n n-observed lambda]
  (let [i (shuffle (range n))]
    {:L        (M/* (lmatrix n) lambda)
     :observed (take n-observed i)
     :hidden   (drop n-observed i)
     :observed-values (matrix :clatrix
                              (repeatedly n-observed rand))}))

(defn solve
  "Return a map containing the approximated value y of each hidden point x"
  [{:keys [L observed hidden observed-values] :as problem}]
  (let [nc  (m/column-count L)
        nr  (m/row-count L)
        L1  (cl/get L (range nr) hidden)
        L2  (cl/get L (range nr) observed)
        l11 (m/mmul (m/transpose L1) L1)
        l12 (m/mmul (m/transpose L1) L2)]
    (assoc problem :hidden-values
                   (m/mmul -1 (m/inverse l11) l12 observed-values))))


(defn plot-points [s]
  (let [X (concat (:hidden s) (:observed s))
  ;(let [X (concat (:hidden s) (:observed s))
        Y (concat (:hidden-values s) (:observed-values s))]
    (ic/view (charts/add-points (charts/xy-plot X Y) (:observed s) (:observed-values s)))))

(defn plot-rand-sample []
  (plot-points (solve (problem 150 10 30))))