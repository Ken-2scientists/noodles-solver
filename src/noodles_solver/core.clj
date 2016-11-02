(ns noodles-solver.core
  (:require [clojure.set :as set])
  (:gen-class))

(def directions-sq [:n :e :s :w])
(def directions-hex [:n :ne :se :s :sw :nw])

(defn mirror-directions
  "Creates a mirrored (180 degree rotation) collection of
   directions based on a vector of directions"
  [dirs]
  (let [n (/ (count dirs) 2)]
    (lazy-cat (drop n dirs) (take n dirs))))

(def mirrors-sq
  "A map from the square-grid directions to their mirror directions"
  (zipmap directions-sq (mirror-directions directions-sq)))

(defn possible-orientations
  [piece]
  (let [{type :type} piece]
    (case type
      :cap [#{:n} #{:e} #{:w} #{:s}]
      :corner [#{:n :e} #{:e :s} #{:s :w} #{:w :n}]
      :line [#{:n :s} #{:e :w}]
      :tee [#{:n :e :s} #{:e :s :w} #{:s :w :n} #{:w :n :e}])))

(defn has-no-forbidden? [excls orientation]
  "Returns true if a piece's orientation points in any excluded directions"
  (empty? (set/intersection excls orientation)))

(defn has-required? [incls orientation]
  "Returns true if a piece's orientation points in all required directions"
  (set/subset? incls orientation))

(defn satisfies-constraints? [constraints orientation]
  "Returns true if a piece's orientation points in all required directions
  and does not point in any excluded directions"
  (let [{excls :forbidden incls :required} constraints]
    (and
      (has-required? incls orientation)
      (has-no-forbidden? excls orientation))))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
