(ns status-im.ui.screens.syncing.sheets.sync-generated-code.views
  (:require [clojure.string :as string]
            [quo.react-native :as rn]
            [status-im.ui.screens.syncing.sheets.sync-generated-code.styles :as styles]
            [quo.core :as quo]
            [re-frame.core :as re-frame]
            [status-im.ui.components.react :as react]
            [quo2.components.button :as quo2]
            [quo2.components.information-box :as information-box]
            [status-im.ui.components.qr-code-viewer.views :as qr-code-viewer]
            [status-im.react-native.resources :as resources]))

(defn views []
      (let [window-width @(re-frame/subscribe [:dimensions/window-width])]
        [:<>
          [rn/view {:style styles/body-container}
             [rn/text {:style styles/header-text} "Sync code generated"]
             [qr-code-viewer/qr-code-view (* window-width 0.808) "some non sense"]
             [information-box/information-box {:type      :informative
                                               :closable? false
                                               :icon      :main-icons2/placeholder
                                               :style     {:margin-top 20}} "On your other device, navigate to the Syncing screen and select “Scan sync”"]
          ]

       ]
      )
)
