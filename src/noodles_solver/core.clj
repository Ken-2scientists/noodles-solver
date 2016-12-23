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
  "Returns true if a piece's orientation satisfies all constraints,
  that is, it points in all required directions
  and does not point in any excluded directions"
  (let [{excls :forbidden incls :required} constraints]
    (and
      (has-required? incls orientation)
      (has-no-forbidden? excls orientation))))

(defrecord piece [type orientation])
(defrecord loc [x y])

(def game
  { (->loc 1 1) (->piece :cap #{:w})
    (->loc 2 1) (->piece :tee #{:s :w :n})
    (->loc 3 1) (->piece :tee #{:w :n :e})
    (->loc 4 1) (->piece :line #{:n :s})
    (->loc 5 1) (->piece :cap #{:s})
    (->loc 1 2) (->piece :corner #{:s :w})
    (->loc 2 2) (->piece :tee #{:e :s :w})
    (->loc 3 2) (->piece :tee #{:s :w :n})
    (->loc 4 2) (->piece :line #{:n :s})
    (->loc 5 2) (->piece :corner #{:e :s})
    (->loc 1 3) (->piece :line #{:e :w})
    (->loc 2 3) (->piece :cap #{:s})
    (->loc 3 3) (->piece :corner #{:s :w})
    (->loc 4 3) (->piece :corner #{:w :n})
    (->loc 5 3) (->piece :cap #{:s})
    (->loc 1 4) (->piece :tee #{:w :n :e})
    (->loc 2 4) (->piece :tee #{:n :e :s})
    (->loc 3 4) (->piece :corner #{:e :s})
    (->loc 4 4) (->piece :tee #{:s :w :n})
    (->loc 5 4) (->piece :corner #{:e :s})
    (->loc 1 5) (->piece :cap #{:e})
    (->loc 2 5) (->piece :cap #{:s})
    (->loc 3 5) (->piece :cap #{:w})
    (->loc 4 5) (->piece :cap #{:s})
    (->loc 5 5) (->piece :cap #{:w})})


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
