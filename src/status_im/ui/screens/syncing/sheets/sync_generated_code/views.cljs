(ns status-im.ui.screens.syncing.sheets.sync-generated-code.views
  (:require [clojure.string :as string]
            [quo.react-native :as rn]
            [status-im.ui.screens.syncing.sheets.sync-device-notice.styles :as styles]
            [quo.core :as quo]
            [quo.design-system.colors :as colors]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [status-im.ui.components.react :as react]
            [status-im.i18n.i18n :as i18n]
            [status-im.utils.types :as types]
            [status-im.utils.utils :as utils]
            [quo2.components.button :as quo2-button]
            [status-im.react-native.resources :as resources]))

(defn views []
  [:<>
      [rn/text {:style {:font-weight :600}} "This a QR Code"]
   ])
