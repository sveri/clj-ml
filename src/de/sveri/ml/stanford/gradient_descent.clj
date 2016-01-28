(ns de.sveri.ml.stanford.gradient-descent
  (:require [incanter.optimize :as ino]))


(def x [1 2 3])

(def y [10 20 30])

(defn lin-fn [theta_0 theta_1 x]
  (+ theta_0 (* theta_1 x)))

(defn cost-function [h x y]
  (let [res (/ (reduce (fn [a [xn yn]]
                       (+ a (- (h xn) yn)))
                     0 (map list x y))
               (* 2 (count x)))]
    [res (double res)]))