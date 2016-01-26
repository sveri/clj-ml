(ns de.sveri.ml.stanford.linear-regression)


(def x [1 2 3])

(def y [10 20 30])

(defn some-fn [a]
  (+ a 3))

(defn cost-function [h x y]
  (let [res (/ (reduce (fn [a [xn yn]]
                       (+ a (- (h xn) yn)))
                     0 (map list x y))
               (* 2 (count x)))]
    [res (double res)]))