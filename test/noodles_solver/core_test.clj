(ns noodles-solver.core-test
  (:require [clojure.test :refer :all]
            [noodles-solver.core :refer :all]))

(deftest mirror-test
  (testing "Should return the mirrored compass headings"
    (is (= (mirror-directions directions-sq) [:s :w :n :e]))
    (is (= (mirror-directions directions-hex) [:s :sw :nw :n :ne :se]))))


(deftest has-required-test
  (testing "has-required returns expected values"
    (is (has-required? #{:n :e} #{:n :e :s}))
    (is (has-required? #{:n :e :s} #{:n :e :s}))
    (is (not (has-required? #{:w} #{:n :e :s})))))
