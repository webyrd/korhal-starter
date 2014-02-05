(ns korhal.rules.rules
  (:refer-clojure :exclude [==])
  (:require [korhal.interop.interop :refer :all]
            [korhal.tools.util :refer [swap-key swap-keys profile]]
            [korhal.tools.repl :refer :all]
            [korhal.tools.queue :refer :all]
            [clara.rules.accumulators :as acc]
            [clara.rules :refer :all]))

(defn- can-afford? [unit-kw]
  (let [unit-type (get-unit-type (unit-type-kws unit-kw))]
    (and (>= (my-minerals) (mineral-price unit-type))
         (>= (my-gas) (gas-price unit-type))
         (>= (- (my-supply-total) (my-supply-used))
             (supply-required unit-type)))))

(defrecord Drone [x y])

  ;; ;; start mining
  ;; (doseq [drone (filter idle? (my-drones))]
  ;;   (let [dist-to-drone (fn [mineral] (dist drone mineral))
  ;;         closest-mineral (apply min-key dist-to-drone (minerals))]
  ;;     (right-click drone closest-mineral)))

  ;; ;; build up to 6 drones
  ;; (when (and (can-afford? :drone)
  ;;            (< (my-supply-used) 6))
  ;;   (let [larva (first (my-larvae))]
  ;;     (morph larva :drone)))

  ;; ;; build spawning pool with one of those drones
  ;; (when (and (can-afford? :spawning-pool)
  ;;            (zero? (count (my-spawning-pools))))
  ;;   (let [drone (first (filter completed? (my-drones)))
  ;;         hatchery (first (my-hatcheries))
  ;;         tx (if (< (tile-x hatchery) 40) (+ 5 (tile-x hatchery)) (- (tile-x hatchery) 5))
  ;;         ty (tile-y hatchery)]
  ;;     (build drone tx ty :spawning-pool)))

  ;; ;; build zerglings
  ;; (when (and (can-afford? :zergling)
  ;;            (not (empty? (filter completed? (my-spawning-pools)))))
  ;;   (let [larva (first (my-larvae))]
  ;;     (morph larva :zergling)))

  ;; ;; rush the shit out of them
  ;; (let [enemy-base (first (enemy-start-locations))]
  ;;   (doseq [zergling (filter idle? (my-zerglings))]
  ;;     (attack zergling (pixel-x enemy-base) (pixel-y enemy-base))))
