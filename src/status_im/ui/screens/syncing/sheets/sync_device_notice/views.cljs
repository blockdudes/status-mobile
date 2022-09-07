(ns status-im.ui.screens.syncing.sheets.sync-device-notice.views
  (:require [clojure.string :as string]
            [quo.react-native :as rn]
            [status-im.ui.screens.syncing.sheets.sync-device-notice.styles :as styles]
            [status-im.ui.screens.syncing.sheets.sync-generated-code.views :as sync-generated-code]
            [status-im.ui.screens.syncing.sheets.scan-code.views :as scan-code]
            [quo.core :as quo]
            [quo.design-system.colors :as colors]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [status-im.ui.components.react :as react]
            [status-im.i18n.i18n :as i18n]
            [status-im.utils.types :as types]
            [status-im.utils.utils :as utils]
            [quo2.components.button :as quo2-button]
            [status-im.utils.handlers :refer [>evt]]
            [status-im.qr-scanner.core :as qr-scanner]
            [status-im.react-native.resources :as resources]))

(defn hide-sheet-and-dispatch [event]
  (re-frame/dispatch [:bottom-sheet/hide])
  (re-frame/dispatch event))

(defn views []
  [:<>
   [rn/view {:style styles/sync-devices-header}
    [react/image {:source (resources/get-image :sync-new-device)
                  :style styles/sync-devices-header-image}]
    ]
   [rn/view {:style styles/sync-devices-body-container}
    [rn/text {:style styles/header-text} "Sync a new device"]
    [rn/text {:style styles/instructions-text} "You own your data. Syncronize it amoung all your devices."]
    [rn/text {:style styles/list-item-text} "1.  Verify login with password or KeyCard"]
    [rn/text {:style styles/list-item-text} "2.  Reveal a temporary QR and Sync Code"]
    [rn/text {:style styles/list-item-text} "3.  Share that info with your new device"]
    [quo2-button/button {:type   :secondary
                         :size    40
                         :style   styles/setup-syncing-button
                         :before  :main-icons2/face-id20
                         :on-press #(>evt [:bottom-sheet/show-sheet
                                                               { :showHandle? false
                                                                 :content (fn []
                                                                            [sync-generated-code/views])}])}
     "Setup Syncing"]

    [rn/view {:style styles/secondary-body-container}
     [rn/text {:style styles/header-text} "Have a Sync Code?"]

     [quo2-button/button {:type   :secondary
                          :size     40
                          :style    styles/setup-syncing-button
                          :before   :main-icons2/face-id20
                          :on-press #(utils/show-confirmation {:title "We don't do that here"
                                                              :confirm-button-text "Okay fineee"
                                                              :cancel-button-text "but?"})}
      "Enter Sync Code"]

     [rn/text {:style styles/instructions-text} "OR"]
     [quo2-button/button {:type   :secondary
                          :size     40
                          :style    styles/setup-syncing-button
                          :before   :main-icons2/face-id20
                          :on-press #(do
                                      (re-frame/dispatch [:bottom-sheet/hide])
                                      (re-frame/dispatch [::qr-scanner/scan-code
                                                          {:handler ::qr-scanner/on-scan-success}]))}
      "Scan Sync Code"]
     ]

    ]
   ])
