(ns status-im.ui.screens.chat.message.reactions-row
  (:require [status-im.constants :as constants]
            [status-im.ui.screens.chat.message.styles :as styles]
            [quo.react-native :as rn]
            [quo2.components.markdown.text :as quo2.text]))

(defn reaction [_ {:keys [own emoji-id quantity]} _]
  [rn/view {:style (styles/reaction-style {:own      own})}
   [rn/image {:source (get constants/reactions emoji-id)
              :style  {:width        16
                       :height       16
                       :margin-right 4}}]
   [quo2.text/text {:accessibility-label (str "emoji-" emoji-id "-is-own-" own)
                    :weight              :medium
                    :color               :primary
                    :ellipsize-mode      :tail
                    :number-of-lines     1
                    :style               styles/reaction-quantity-style}
    quantity]])

(defn message-reactions [message reactions timeline]
  (when (seq reactions)
    [rn/view {:style (styles/reactions-row timeline)}
     (for [emoji-reaction reactions]
       ^{:key (str emoji-reaction)}
       [reaction message emoji-reaction timeline])]))
