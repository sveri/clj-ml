(ns de.sveri.ml.stanford.excersize-two
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [incanter.core :as ic]
            [incanter.charts :as charts]
            [clojure.core.matrix :as m]))

(defn load-dat [file-name]
  (mapv read-string (str/split (slurp (io/resource file-name)) #"\n")))

(defn add-ones-col [x]
  (m/matrix (reduce (fn [a b] (conj a [1 b])) [] x)))

(def X (add-ones-col (load-dat "ex2x.dat")))
(def Y (load-dat "ex2y.dat"))

(def M (m/row-count Y))

(def THETA [0 0])

(def ALPHA 0.07)

(def MAX_ITER 1500)

(defn ->grad
  "    grad = (1/m).* x' * ((x * theta) - y)"
  [m x y theta]
  (m/mul (/ 1 m) (m/mmul (m/transpose x) (m/sub (m/mmul x theta) y))))

(defn update-theta [grad alpha theta]
  (m/sub theta (m/mul alpha grad)))

(defn gradient-descent [m x y theta alpha max_iter]
  (->> (loop [theta theta i max_iter]
         (if (= 0 i)
           theta
           (recur (-> (->grad m x y theta)
                      (update-theta alpha theta))
                  (dec i))))
       (m/mmul x)))




(defn plot-x-y [x y]
  (ic/view (charts/xy-plot x y)))