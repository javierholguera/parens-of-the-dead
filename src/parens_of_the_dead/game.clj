(ns parens-of-the-dead.game)

(def faces [:h1 :h1 :h2 :h2 :h3 :h3 :h4 :h4 :h5 :h5
            :fg :fg :zo :zo :zo :gy])

(defn ->tile [face]
  {:face face})

(defn revealed-tiles
  [game]
  (->> game :tiles (filter :revealed?)))

(defn- can-reveal?
  [game]
  (> 2 (count (revealed-tiles game))))

(defn- match-revealed [tiles]
  (mapv (fn [tile]
          (if (:revealed? tile)
            (-> tile (assoc :matched? true) (dissoc :revealed?))
            tile))
        tiles))

(defn- get-match?
  [game]
  (let [revealed (revealed-tiles game)]
    (when (and (= 2 (count revealed))
               (= 1 (count (set (map :face revealed)))))
      (:face (first revealed)))))

(defn- replace-remaining
  [sand replacement]
  (concat
   (take-while (complement #{:remaining}) sand)
   replacement
   (drop (count replacement)
         (drop-while (complement #{:remaining}) sand))))

(defn- wake-the-dead
  [tiles]
  (mapv (fn [tile]
          (if (= :gy (:face tile))
            (assoc tile :face :zo)
            tile))
        tiles))

(defn- perform-match-actions
  [game match]
  (case match
    :fg (assoc game :foggy? true)
    :zo (-> game
            (update-in [:sand] #(replace-remaining % (repeat 3 :zombie)))
            (update-in [:tiles] wake-the-dead))
    game))

(defn- check-for-match [game]
  (if-let [match (get-match? game)]
    (-> game
        (update-in [:tiles] match-revealed)
        (perform-match-actions match))
    game))

(defn reveal-tile
  [index game]
  (if (can-reveal? game)
    (-> game
        (assoc-in [:tiles index :revealed?] true)
        (check-for-match))
    game))

(defn create-game []
  {:tiles (shuffle (map ->tile faces))
   :sand (repeat 30 :remaining)})
