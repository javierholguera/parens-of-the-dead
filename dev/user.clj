(ns user
  (:require [reloaded.repl :refer [system reset stop]]
            [parens-of-the-dead.system]))

(reloaded.repl/set-init! #'parens-of-the-dead.system/create-system)
