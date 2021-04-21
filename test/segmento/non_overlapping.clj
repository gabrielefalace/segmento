(ns segmento.non_overlapping
  (:require [clojure.test :refer :all]
            [segmento.core :refer :all])
  (:import (segmento.core Interval)))

;; convenience function
(defn i [start end] (Interval. start end))

(deftest middle-slot
  (testing "intervals with slot in the middle"
    (is (= [(i 0 1), (i 9 12), (i 2 3)]
           (diff (i 0 12) [(i 3 4), (i 1 2), (i 4 9)])
           )))
  )

(deftest initial-final
  (testing "intervals with only initial and final slot"
    (is (= [(i 0 1), (i 9 12)]
           (diff (i 0 12) [(i 3 4), (i 1 3), (i 4 9)])
           ))))

(deftest only-initial
  (testing "intervals with only initial slot"
    (is (= [(i 0 1)]
           (diff (i 0 12) [(i 3 4), (i 1 3), (i 4 12)])
           ))))

(deftest only-final
  (testing "intervals with only final slot"
    (is (= [(i 7 12)]
           (diff (i 0 12) [(i 3 4), (i 0 3), (i 4 7)])
           ))))

(deftest multiple-slots
  (testing "intervals with only final slot"
    (is (= [(i 12, 20), (i 2, 3), (i 7 8)]
           (diff (i 0 20) [(i 3 4), (i 0 2), (i 4 7), (i 8, 12)])
           ))))