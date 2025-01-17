(ns status-im.navigation2.screens
  (:require [status-im.ui2.screens.chat.view :as chat]
            [status-im.switcher.shell-stack :as shell-stack]))

;; We have to use the home screen name :chat-stack for now, for compatibility with navigation.cljs
(def screens [{:name      :chat-stack                       ;; TODO(parvesh) - rename to shell-stack
               :insets    {:top false}
               :component shell-stack/shell-stack}])

;; These screens will overwrite navigation/screens.cljs screens on enabling new UI toggle
(def screen-overwrites
  [{:name      :chat
    :options   {:topBar {:visible false}}
    :component chat/chat}])
