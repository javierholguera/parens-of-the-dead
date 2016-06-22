(ns parens-of-the-dead.game-test
  (:require [parens-of-the-dead.game :refer :all]
            [expectations :refer :all]))

;; test helpers

(defn- find-face-index [face game]
  (first (keep-indexed (fn [index tile]
                         (when (and (= face (:face tile))
                                    (not (:revealed? tile)))
                           index))
                       (:tiles game))))

(defn reveal-one
  [face game]
  (reveal-tile (find-face-index face game) game))


;; create-game

(expect {:h1 2 :h2 2 :h3 2 :h4 2 :h5 2
         :fg 2 :zo 3 :gy 1}
        (->> (create-game) :tiles (map :face) frequencies))

(expect #(< 10 %)
        (count (set (repeatedly 100 create-game))))

(expect {:remaining 30}
        (frequencies (:sand (create-game))))


;; reveal-tile

(expect 1
        (->> (create-game)
             (reveal-tile 0)
             :tiles
             (filter :revealed?)
             count))

;; reveal-one

(expect #{{:face :h1 :revealed? true}
          {:face :h2 :revealed? true}}
        (->> (create-game)
             (reveal-one :h1)
             (reveal-one :h2)
             (reveal-one :h3)
             :tiles
             (filter :revealed?)
             (set)))

(expect [{:face :h1 :matched? true}
         {:face :h1 :matched? true}]
        (->> (create-game)
             (reveal-one :h1)
             (reveal-one :h1)
             :tiles
             (filter :matched?)))

(expect #{{:face :h3 :revealed? true}}
        (->> (create-game)
             (reveal-one :h3)
             :tiles
             (filter :revealed?)
             (set)))

