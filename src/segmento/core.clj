(ns segmento.core
  (:gen-class)
  (:import (java.util ArrayList)
           (java.util.concurrent.atomic AtomicInteger)))

(defrecord Interval [^Integer start ^Integer end])

(defn diff ^ArrayList [main list]
  (let [acc (ArrayList.)
        intervals (sort #(compare (.start %1) (.start %2)) list)
        intervalsByEnd (sort #(compare (.end %1) (.end %2)) list)
        cur (AtomicInteger. 0)]

    (if (> (.start (first intervals)) (.start main))
      (.add acc (Interval. (.start main) (.start (first intervals)))))

    (if (< (.end (last intervalsByEnd)) (.end main))
      (.add acc (Interval. (.end (last intervalsByEnd)) (.end main))))

    (loop [next 1]
      (when (< next (count intervals))
        (if (< (.end (nth intervals (.get cur))) (.start (nth intervals next)))
          (do (.add acc (Interval. (.end (nth intervals (.get cur))) (.start (nth intervals next))))
              (.incrementAndGet cur))
          (if (> (.end (nth intervals next)) (.end (nth intervals (.get cur))))
            (.incrementAndGet cur))
          )
        (recur (+ next 1))
        )
      )
    acc))