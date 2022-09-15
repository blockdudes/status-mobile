(ns status-im.ui.screens.chat.message.reactions-row
  (:require [status-im.constants :as constants]
            [status-im.ui.screens.chat.message.styles :as styles]
            [quo.react-native :as rn]
            [quo2.components.reactions.react :as quo2.react]))

(defn message-reactions [{:keys [content-type]} reactions timeline on-emoji-press on-open]
  (when (seq reactions)
    [rn/view {:style (styles/reactions-row timeline (if (= content-type constants/content-type-text) -3 5))}
     (for [{:keys [own emoji-id quantity] :as emoji-reaction} reactions]
       ^{:key (str emoji-reaction)}
       [rn/view {:style {:margin-right 6 :margin-top 5}}
        [quo2.react/render-react {:emoji (get constants/reactions emoji-id)
                                  :neutral? own
                                  :clicks quantity
                                  :on-press #(on-emoji-press emoji-id)}]])
      ;; on-press won't work until we integrate Message Context Drawer
     [quo2.react/open-reactions-menu {:on-press on-open}]]))
